package com.tenda.coupon.model;

import com.tenda.coupon.enums.CouponStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Entity(name = "coupon")
public class CouponModel {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, name = "discount_value")
    private double discountValue;
    @Column(nullable = false, name = "expiration_date")
    private LocalDateTime expirationDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponStatus status;
    @Column(nullable = false)
    private boolean published;
    @Column()
    private boolean redeemed;



}
