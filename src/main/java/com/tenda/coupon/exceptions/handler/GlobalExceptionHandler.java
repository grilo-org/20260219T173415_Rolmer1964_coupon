package com.tenda.coupon.exceptions.handler;


import com.tenda.coupon.exceptions.custom.BadRequestException;
import com.tenda.coupon.exceptions.custom.BusinessException;
import com.tenda.coupon.exceptions.custom.ResourceNotFoundException;
import com.tenda.coupon.exceptions.dto.ErrorDetails;
import com.tenda.coupon.exceptions.dto.ErrorForm;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handler para ValidationException genérica
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(ValidationException ex, WebRequest request) {
        // Retorna 400 Bad Request
        return ResponseEntity
                .badRequest()
                .body(buildError(ex, request, HttpStatus.BAD_REQUEST));
    }

    //Captura erros de validação de @Valid em @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        return ResponseEntity.badRequest().body(buildError(ex, request, HttpStatus.BAD_REQUEST));
    }

    //Captura erros de validação de @Valid em @RequestBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(buildError(ex, request, HttpStatus.BAD_REQUEST));
    }


    //Quando o corpo da requisição está mal formado (JSON inváliodo, por exemplo)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(buildError(ex, request, HttpStatus.BAD_REQUEST));
    }

    //Captura BindException (usado com @ModelAttribuite, por exemplo)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorDetails> handleBindException(BindException ex, WebRequest request ) {
        return ResponseEntity.badRequest().body(buildError(ex, request, HttpStatus.BAD_REQUEST));
    }

    //Parâmetro presente mas com valor invalido
    //Exemplo: @RequestParam @Min(18) Integer idade -> idade = 10
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(buildError(ex, request, HttpStatus.BAD_REQUEST));
    }

    //Parâmetro obrigatório, não informado na requisição
    //Exemplo: @RequestParam @NotBlank Seting nome ->  sem o parâmetro nome na request
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDetails> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(buildError(ex, request, HttpStatus.BAD_REQUEST));
    }

    //Uma Falha relacionada a regra de negócio da aplicação
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDetails> handleBusinessException(BusinessException ex, WebRequest request) {
        HttpStatus status = Optional.of(ex.getHttpStatus()).orElse(HttpStatus.UNPROCESSABLE_ENTITY);
        return ResponseEntity
                .status(status)
                .body(buildError(ex, request,status));
    }

    //O recurso não foi encontrado (exemplo: json retornou vazio)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        HttpStatus status = ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(buildError(ex, request,status));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorDetails> handleFeignException(FeignException ex, WebRequest request) {
        HttpStatus status = Optional.of(HttpStatus.valueOf(ex.status())).orElse(HttpStatus.BAD_GATEWAY);
        return ResponseEntity
                .status(status)
                .body(buildFeignError(ex, request, status));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(ex, request, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ErrorDetails buildError(Exception ex, WebRequest request, HttpStatus httpStatus) {
        List<ErrorForm> errorFormList = new ArrayList<>();
        String customMessage = ex.getMessage();

        if (ex instanceof BadRequestException) {
            errorFormList = Optional.ofNullable(((BadRequestException) ex).getErrorFormList()).orElse(List.of());

        } else if (ex instanceof ConstraintViolationException constraintEx) {
            customMessage = "Erro de validação em um ou mais parâmetros da query. Veja a lista para detalhes";
            errorFormList = constraintEx.getConstraintViolations()
                    .stream()
                    .map(
                            validation -> ErrorForm.builder()
                                    .field(validation.getPropertyPath().toString())
                                    .message(validation.getMessage())
                                    .build()
                    ).collect(Collectors.toList());
        } else if (ex instanceof MissingServletRequestParameterException missingEx) {
            ErrorForm errorForm = ErrorForm.builder()
                    .field(missingEx.getParameterName())
                    .message(String.format("O parâmetro '%s' é obrigatório", missingEx.getParameterName()))
                    .build();
            errorFormList.add(errorForm);
        } else if (ex instanceof HttpMessageNotReadableException) {
            ErrorForm errorForm = ErrorForm.builder()
                    .field("Request Body")
                    .message("Corpo da requisição inválido ou mal formatado")
                    .build();
            errorFormList.add(errorForm);
        } else if (ex instanceof MethodArgumentNotValidException) {
            customMessage = "Erro de validação de um ou mais campos do body. Veja a lista para detalhes";
            errorFormList = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors()
                    .stream()
                    .map(
                            fieldError -> ErrorForm.builder()
                                    .field(fieldError.getField())
                                    .message(fieldError.getDefaultMessage())
                                    .build()
                    ).collect(Collectors.toList());
        } else if (ex instanceof BindException be) {
            customMessage = "Ocorreu um erro de binding";
            errorFormList = be.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(
                            fieldError -> ErrorForm.builder()
                                    .field(fieldError.getField())
                                    .message(fieldError.getDefaultMessage())
                                    .build()
                    ).collect(Collectors.toList());
        }else if(ex instanceof BusinessException bse) {
            customMessage = bse.getMessage();
            errorFormList = List.of(ErrorForm.builder()
                            .field("Campo não especificado")
                            .message(bse.getMessage())
                    .build());
        }else if(ex instanceof ResourceNotFoundException rnf){
            customMessage = rnf.getMessage();
            errorFormList = List.of(ErrorForm.builder()
                    .field("Campo não especificado")
                    .message(rnf.getMessage())
                    .build());
        }else if(ex instanceof IllegalArgumentException iae) {
            customMessage = iae.getMessage();
            errorFormList = List.of(ErrorForm.builder()
                    .field("error")
                    .message(iae.getMessage())
                    .build());
        } else if (ex instanceof ValidationException ve) {
            customMessage = "Erro de validação: " + ve.getMessage();
            errorFormList = List.of(ErrorForm.builder()
                    .field("validation")
                    .message(ve.getMessage())
                    .build());
        }else{
            customMessage = ex.getMessage();
            errorFormList = List.of(ErrorForm.builder()
                    .field("error")
                    .message(ex.getMessage())
                    .build());
        }


        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .type(ex.getClass().getTypeName())
                .message(customMessage)
                .fields(errorFormList)
                .status(httpStatus)
                .path(request.getDescription(Boolean.FALSE))
                .build();

    }


    private ErrorDetails buildFeignError(FeignException ex, WebRequest request, HttpStatus httpStatus) {
        String responseBody = ex.contentUTF8();
        List<ErrorForm> errorFormList = new ArrayList<>();
        String customMessage = "Erro ao consumir serviço remoto";

        // Tenta extrair detalhes do corpo da resposta (assumindo JSON)
        if (responseBody != null && !responseBody.isBlank()) {
            // Exemplo: tenta mapear para ErrorDetails ou ErrorForm se a API remota seguir padrão semelhante
            try {
                ObjectMapper  mapper = new ObjectMapper();
                // Tenta mapear para seu modelo de erro padrão
                ErrorDetails remoteError = mapper.readValue(responseBody, ErrorDetails.class);
                customMessage = remoteError.getMessage();
                errorFormList = Optional.ofNullable(remoteError.getFields()).orElse(List.of());
            } catch (Exception e) {
                // Se não conseguir mapear, adiciona o corpo como mensagem bruta
                errorFormList.add(ErrorForm.builder()
                        .field("feign.response")
                        .message(responseBody)
                        .build());
            }
        } else {
            errorFormList.add(ErrorForm.builder()
                    .field("feign.status")
                    .message("Status: " + ex.status())
                    .build());
        }

        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .type(ex.getClass().getTypeName())
                .message(customMessage)
                .fields(errorFormList)
                .status(httpStatus)
                .path(request.getDescription(false))
                .build();
    }
}
