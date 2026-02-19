package com.tenda.coupon.controller;


import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.request.NewCouponRequest;
import com.tenda.coupon.service.CouponCreateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
public class CouponCreateController {

    @Autowired
    CouponCreateService service;

    @PostMapping
    public ResponseEntity<CouponModel> create(@Valid @RequestBody NewCouponRequest request) {
        CouponModel created = service.create(request);
        return ResponseEntity.ok(created);
 }


}
