package com.tenda.coupon.controller;


import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.request.NewCouponRequest;
import com.tenda.coupon.service.CouponCreateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponCreateControllerTest {

    @Mock
    private CouponCreateService service;

    @InjectMocks
    private CouponCreateController controller;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void cleanUp() throws Exception {
        mocks.close();
    }

    @Test
    void testCreate_ReturnsCreatedCoupon() {
        // Arrange
        NewCouponRequest request = new NewCouponRequest();
        CouponModel coupon = new CouponModel();
        when(service.create(request)).thenReturn(coupon);

        // Act
        ResponseEntity<CouponModel> response = controller.create(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupon, response.getBody());
        verify(service, times(1)).create(request);
    }
}