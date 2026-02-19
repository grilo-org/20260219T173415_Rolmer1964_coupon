package com.tenda.coupon.service;


import com.tenda.coupon.exceptions.custom.ResourceNotFoundException;
import com.tenda.coupon.log.LogService;
import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponGetServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Spy
    private LogService logService = LogService.getLogger(CouponGetService.class);

    @InjectMocks
    private CouponGetService couponGetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inject the static logService if needed (reflection, not shown here)
    }

    @Test
    void testGet_CouponFound() {
        UUID id = UUID.randomUUID();
        CouponModel coupon = new CouponModel();
        when(couponRepository.findById(id)).thenReturn(Optional.of(coupon));

        CouponModel result = couponGetService.get(id);

        assertEquals(coupon, result);
        verify(couponRepository).findById(id);
        // Optionally verify log
        // verify(logService).info(contains(id.toString()));
    }

    @Test
    void testGet_CouponNotFound() {
        UUID id = UUID.randomUUID();
        when(couponRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            couponGetService.get(id);
        });

        assertTrue(exception.getMessage().contains(id.toString()));
        verify(couponRepository).findById(id);
        // Optionally verify log
        // verify(logService).warn(contains(id.toString()));
    }
}