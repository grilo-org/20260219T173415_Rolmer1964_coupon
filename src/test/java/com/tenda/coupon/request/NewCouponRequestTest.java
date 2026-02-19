package com.tenda.coupon.request;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.*;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NewCouponRequestTest {

    private static Validator validator;

    static ValidatorFactory factory;

    @BeforeAll
    static void setupValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void cleanValidator() {
        factory.close();
    }

    @Test
    void testBuilderAndGetters_HappyPath() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        NewCouponRequest request = NewCouponRequest.builder()
                .code("ABC123")
                .description("10% OFF")
                .discountValue(1.0)
                .expirationDate(futureDate)
                .published(true)
                .build();

        assertEquals("ABC123", request.getCode());
        assertEquals("10% OFF", request.getDescription());
        assertEquals(1.0, request.getDiscountValue());
        assertEquals(futureDate, request.getExpirationDate());
        assertTrue(request.isPublished());

        Set<ConstraintViolation<NewCouponRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Should have no validation errors");
    }

    @Test
    void testNoArgsConstructor_Defaults() {
        NewCouponRequest request = new NewCouponRequest();
        assertNull(request.getCode());
        assertNull(request.getDescription());
        assertEquals(0.0, request.getDiscountValue());
        assertNull(request.getExpirationDate());
        assertFalse(request.isPublished());
    }

    @Test
    void testInvalidCode_Blank() {
        NewCouponRequest request = NewCouponRequest.builder()
                .code("")
                .description("desc")
                .discountValue(1.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        Set<ConstraintViolation<NewCouponRequest>> violations = validator.validate(request);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("code")));
    }

    @Test
    void testInvalidCode_ShortLength() {
        NewCouponRequest request = NewCouponRequest.builder()
                .code("123")
                .description("desc")
                .discountValue(1.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        Set<ConstraintViolation<NewCouponRequest>> violations = validator.validate(request);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("code")));
    }

    @Test
    void testInvalidDescription_Blank() {
        NewCouponRequest request = NewCouponRequest.builder()
                .code("ABC123")
                .description("")
                .discountValue(1.0)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        Set<ConstraintViolation<NewCouponRequest>> violations = validator.validate(request);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }

    @Test
    void testInvalidDiscountValue_TooLow() {
        NewCouponRequest request = NewCouponRequest.builder()
                .code("ABC123")
                .description("desc")
                .discountValue(0.1)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        Set<ConstraintViolation<NewCouponRequest>> violations = validator.validate(request);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("discountValue")));
    }

    @Test
    void testInvalidExpirationDate_Past() {
        NewCouponRequest request = NewCouponRequest.builder()
                .code("ABC123")
                .description("desc")
                .discountValue(1.0)
                .expirationDate(LocalDateTime.now().minusDays(1))
                .build();

        Set<ConstraintViolation<NewCouponRequest>> violations = validator.validate(request);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("expirationDate")));
    }
}