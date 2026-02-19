package com.tenda.coupon.enums;

import lombok.Getter;

@Getter
public enum CouponStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo"),
    DELETED("Deletado");

    private final String description;

    CouponStatus(String description) {
        this.description = description;
    }



}