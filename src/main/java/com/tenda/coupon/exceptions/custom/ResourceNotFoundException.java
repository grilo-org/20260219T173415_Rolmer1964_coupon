package com.tenda.coupon.exceptions.custom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private HttpStatus httpStatus;
    private String jsonResponse;

    // Construtor com mensagem customizada
    public ResourceNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

}
