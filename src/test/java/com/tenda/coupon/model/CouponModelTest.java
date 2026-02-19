package com.tenda.coupon.model;


import com.tenda.coupon.enums.CouponStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CouponModelTest {

    @Test
    void testBuilderAndGetters_HappyPath() {
        UUID id = UUID.randomUUID();
        String code = "ABC123";
        String description = "10% OFF";
        double discountValue = 10.0;
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(10);
        CouponStatus status = CouponStatus.ACTIVE;
        boolean published = true;
        boolean redeemed = false;

        CouponModel coupon = CouponModel.builder()
                .id(id)
                .code(code)
                .description(description)
                .discountValue(discountValue)
                .expirationDate(expirationDate)
                .status(status)
                .published(published)
                .redeemed(redeemed)
                .build();

        assertEquals(id, coupon.getId());
        assertEquals(code, coupon.getCode());
        assertEquals(description, coupon.getDescription());
        assertEquals(discountValue, coupon.getDiscountValue());
        assertEquals(expirationDate, coupon.getExpirationDate());
        assertEquals(status, coupon.getStatus());
        assertTrue(coupon.isPublished());
        assertFalse(coupon.isRedeemed());
    }

    @Test
    void testSettersAndGetters_HappyPath() {
        CouponModel coupon = new CouponModel();
        UUID id = UUID.randomUUID();
        coupon.setId(id);
        coupon.setCode("XYZ789");
        coupon.setDescription("20% OFF");
        coupon.setDiscountValue(20.0);
        LocalDateTime expiration = LocalDateTime.now().plusDays(5);
        coupon.setExpirationDate(expiration);
        coupon.setStatus(CouponStatus.INACTIVE);
        coupon.setPublished(false);
        coupon.setRedeemed(true);

        assertEquals(id, coupon.getId());
        assertEquals("XYZ789", coupon.getCode());
        assertEquals("20% OFF", coupon.getDescription());
        assertEquals(20.0, coupon.getDiscountValue());
        assertEquals(expiration, coupon.getExpirationDate());
        assertEquals(CouponStatus.INACTIVE, coupon.getStatus());
        assertFalse(coupon.isPublished());
        assertTrue(coupon.isRedeemed());
    }

    @Test
    void testNoArgsConstructor() {
        CouponModel coupon = new CouponModel();
        assertNotNull(coupon);
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        LocalDateTime expiration = LocalDateTime.now().plusDays(1);
        CouponModel coupon = new CouponModel(
                id,
                "CODE",
                "desc",
                5.0,
                expiration,
                CouponStatus.DELETED,
                false,
                true
        );
        assertEquals(id, coupon.getId());
        assertEquals("CODE", coupon.getCode());
        assertEquals("desc", coupon.getDescription());
        assertEquals(5.0, coupon.getDiscountValue());
        assertEquals(expiration, coupon.getExpirationDate());
        assertEquals(CouponStatus.DELETED, coupon.getStatus());
        assertFalse(coupon.isPublished());
        assertTrue(coupon.isRedeemed());
    }

    // Cenários infelizes: valores nulos e inválidos
    @Test
    void testNullFields() {
        CouponModel coupon = new CouponModel();
        // Por padrão, todos os campos são null/false/0
        assertNull(coupon.getId());
        assertNull(coupon.getCode());
        assertNull(coupon.getDescription());
        assertEquals(0.0, coupon.getDiscountValue());
        assertNull(coupon.getExpirationDate());
        assertNull(coupon.getStatus());
        assertFalse(coupon.isPublished());
        assertFalse(coupon.isRedeemed());
    }

    @Test
    void testNegativeDiscountValue() {
        CouponModel coupon = new CouponModel();
        coupon.setDiscountValue(-10.0);
        // Não há validação na entidade, mas podemos garantir que o valor é setado
        assertEquals(-10.0, coupon.getDiscountValue());
    }
}