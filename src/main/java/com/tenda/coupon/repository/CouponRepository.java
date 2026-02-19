package com.tenda.coupon.repository;



import com.tenda.coupon.model.CouponModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CouponRepository extends CrudRepository<CouponModel, UUID> {

}