package com.tenda.coupon.service;



import com.tenda.coupon.enums.CouponStatus;
import com.tenda.coupon.exceptions.custom.ResourceNotFoundException;
import com.tenda.coupon.log.LogService;
import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CouponSoftDeleteService {

    @Autowired
    CouponRepository couponRepository;

    private static final LogService logService = LogService.getLogger(CouponSoftDeleteService.class);

    public CouponModel softDelete(UUID id) {
        logService.info("Efetuando soft delete");
        CouponModel couponModel = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível deletar. Cupom não encontrado para o ID: " + id));
        couponModel.setStatus(CouponStatus.DELETED);
       return  couponRepository.save(couponModel);
    }
}
