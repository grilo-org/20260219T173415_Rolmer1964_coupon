package com.tenda.coupon.mapper;


import com.tenda.coupon.enums.CouponStatus;
import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.request.NewCouponRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CouponMapperTest {

    @Test
    void testToCouponModel() {
        // Arrange
        NewCouponRequest request = new NewCouponRequest();
        request.setCode("ABC123");
        request.setDescription("10% off");

        // Act
        CouponModel model = CouponMapper.INSTANCE.toCouponModel(request);

        // Assert
        assertNull(model.getId(), "ID should be null (ignored)");
        assertEquals("ABC123", model.getCode());
        assertEquals("10% off", model.getDescription());
        assertEquals(CouponStatus.ACTIVE, model.getStatus(), "Status should be set to ACTIVE");

    }
}