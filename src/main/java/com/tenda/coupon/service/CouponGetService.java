package com.tenda.coupon.service;


import com.tenda.coupon.exceptions.custom.ResourceNotFoundException;
import com.tenda.coupon.log.LogService;
import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CouponGetService {


    @Autowired
    CouponRepository couponRepository;

    private static final LogService logService = LogService.getLogger(CouponGetService.class);

    public CouponModel get(UUID id) {
        Optional<CouponModel> retrieved = couponRepository.findById(id);
        if (retrieved.isPresent()) {
            logService.info("Cupom encontrado para o ID: " + id);
            return retrieved.get();
        } else {
            logService.warn("Cupom não encontrado para o ID: " + id);
            throw new ResourceNotFoundException("Cupom não encontrado para o ID: " + id);
        }
    }
}
