package com.tenda.coupon.controller;


import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.service.CouponSoftDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
public class CouponDeleteController {

    @Autowired
    CouponSoftDeleteService service;

    @DeleteMapping("/{id}")
    public ResponseEntity<CouponModel> delete(@PathVariable UUID id) {
        CouponModel deleted = service.softDelete(id);
        return ResponseEntity.ok(deleted);
    }
}
