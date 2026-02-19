package com.tenda.coupon.controller;

import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.service.CouponGetService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponGetControllerTest {

    @Mock
    private CouponGetService service;

    @InjectMocks
    private CouponGetController controller;


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
    void testGet_ReturnsCouponModel() {
        UUID id = UUID.randomUUID();
        CouponModel coupon = new CouponModel();
        when(service.get(id)).thenReturn(coupon);

        ResponseEntity<CouponModel> response = controller.get(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coupon, response.getBody());
        verify(service, times(1)).get(id);
    }
}