package com.tenda.coupon.service;




import com.tenda.coupon.log.LogService;
import com.tenda.coupon.mapper.CouponMapper;
import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.repository.CouponRepository;
import com.tenda.coupon.request.NewCouponRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CouponCreateService {

    @Autowired
    CouponRepository couponRepository;

    private static final LogService logService = LogService.getLogger(CouponCreateService.class);

    public CouponModel create(NewCouponRequest request) {
        logService.info("New coupon request received");
        request.setCode(codeCleaner(request.getCode()));
        CouponModel couponModel = CouponMapper.INSTANCE.toCouponModel(request);
        logService.info("CouponModel created");
        return couponRepository.save(couponModel);
    }

    //o desafio prevê que o padrão é 6 caracteres, mas também prevê que o valor entrado deva ser "limpo"
    //Entendi que, após "limpo", ainda precisa ter 6 caracteres
    private String codeCleaner(String couponCode) {
        String cleaned = couponCode.replaceAll("[^a-zA-Z0-9]", "");
        if ((cleaned.length() != 6)) {
            logService.error("Invalid coupon code");
            throw new IllegalArgumentException("Code must have 6 alphanumeric characters after cleaning");
        }
        return cleaned;
    }

}
