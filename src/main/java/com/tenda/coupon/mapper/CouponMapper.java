package com.tenda.coupon.mapper;

import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.request.NewCouponRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CouponMapper {
    CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "redeemed", ignore = true)
    CouponModel toCouponModel(NewCouponRequest user);


}