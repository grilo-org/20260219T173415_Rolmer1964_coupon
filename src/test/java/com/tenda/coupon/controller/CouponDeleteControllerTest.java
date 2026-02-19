package com.tenda.coupon.controller;

import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.service.CouponSoftDeleteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponDeleteControllerTest {

    @InjectMocks
    private CouponDeleteController controller;

    @Mock
    private CouponSoftDeleteService service;


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
    void delete_shouldReturnOkWithDeletedCoupon() {
        // Arrange
        UUID id = UUID.randomUUID();
        CouponModel deletedCoupon = new CouponModel();
        when(service.softDelete(id)).thenReturn(deletedCoupon);

        // Act
        ResponseEntity<CouponModel> response = controller.delete(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deletedCoupon, response.getBody());
        verify(service, times(1)).softDelete(id);
    }
}