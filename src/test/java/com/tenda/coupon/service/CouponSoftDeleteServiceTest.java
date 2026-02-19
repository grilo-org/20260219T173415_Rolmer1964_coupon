package com.tenda.coupon.service;

import com.tenda.coupon.enums.CouponStatus;
import com.tenda.coupon.exceptions.custom.ResourceNotFoundException;
import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponSoftDeleteServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponSoftDeleteService couponSoftDeleteService;

    private UUID couponId;
    private CouponModel couponModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        couponId = UUID.randomUUID();
        couponModel = new CouponModel();
        couponModel.setId(couponId);
        couponModel.setStatus(CouponStatus.ACTIVE);
    }

    @Test
    void softDelete_shouldSetStatusToDeletedAndSave() {
        when(couponRepository.findById(couponId)).thenReturn(Optional.of(couponModel));
        when(couponRepository.save(any(CouponModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CouponModel result = couponSoftDeleteService.softDelete(couponId);

        assertEquals(CouponStatus.DELETED, result.getStatus());
        verify(couponRepository).findById(couponId);
        verify(couponRepository).save(couponModel);
    }

    @Test
    void softDelete_shouldThrowResourceNotFoundException_whenCouponNotFound() {
        when(couponRepository.findById(couponId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            couponSoftDeleteService.softDelete(couponId);
        });

        assertTrue(exception.getMessage().contains("Cupom não encontrado"));
        verify(couponRepository).findById(couponId);
        verify(couponRepository, never()).save(any());
    }
}