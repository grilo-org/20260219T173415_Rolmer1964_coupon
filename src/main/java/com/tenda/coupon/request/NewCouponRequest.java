package com.tenda.coupon.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/*
uso de UUID para:
evitar risco de colisão
uso de espaço e velocidade de busca
*/


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCouponRequest {

    @NotBlank(message = "Please, provide a code")
    @Size(min = 6, message = "Min length for code is 6")
    private String code;
    @NotBlank(message = "Please, provide a description")
    private String description;
    @DecimalMin(value= "0.5", message = "minimum value for discount valeu is 0.5" )
    private double discountValue;
    @FutureOrPresent(message = "Expiration date must be future")
    private LocalDateTime expirationDate;
    @Builder.Default
    private boolean published = false;


}
