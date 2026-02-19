package com.tenda.coupon.controller;


import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.service.CouponGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
public class CouponGetController {

    @Autowired
    CouponGetService service;

    @GetMapping("/{id}")
    public ResponseEntity<CouponModel> get(@PathVariable UUID id) {
        CouponModel couponModel = service.get(id);
        return ResponseEntity.ok(couponModel);

    }


}
