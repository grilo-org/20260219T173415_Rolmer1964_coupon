package com.tenda.coupon.service;

import com.tenda.coupon.model.CouponModel;
import com.tenda.coupon.repository.CouponRepository;
import com.tenda.coupon.request.NewCouponRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponCreateServiceTest {

    @InjectMocks
    CouponCreateService couponCreateService;

    @Mock
    CouponRepository couponRepository;

    @Test
    void create_shouldSaveCoupon_whenCodeIsValid() {
        NewCouponRequest request = new NewCouponRequest();
        request.setCode("A1B-2C"); // Will be cleaned to "A1B2C"
        // Make it valid length after cleaning
        request.setCode("A1B-2C3"); // Cleaned: "A1B2C3" (6 chars)

        CouponModel model = new CouponModel();
        when(couponRepository.save(any(CouponModel.class))).thenReturn(model);

        CouponModel result = couponCreateService.create(request);

        assertNotNull(result);
        verify(couponRepository).save(any(CouponModel.class));
    }

    @Test
    void create_shouldThrowException_whenCodeIsInvalidAfterCleaning() {
        NewCouponRequest request = new NewCouponRequest();
        request.setCode("A1B-2"); // Cleaned: "A1B2" (only 4 chars)

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            couponCreateService.create(request);
        });

        assertEquals("Code must have 6 alphanumeric characters after cleaning", ex.getMessage());
    }

    @Test
    void codeCleaner_shouldReturnCleanedCode_whenValid() throws Exception {
        // Use reflection to access private method
        var method = CouponCreateService.class.getDeclaredMethod("codeCleaner", String.class);
        method.setAccessible(true);

        String cleaned = (String) method.invoke(couponCreateService, "A1B-2C3");
        assertEquals("A1B2C3", cleaned);
    }

    @Test
    void codeCleaner_shouldThrowException_whenInvalid() throws Exception {
        var method = CouponCreateService.class.getDeclaredMethod("codeCleaner", String.class);
        method.setAccessible(true);

        Exception ex = assertThrows(Exception.class, () -> {
            method.invoke(couponCreateService, "ABC"); // Cleaned: "ABC" (3 chars)
        });
        assertInstanceOf(IllegalArgumentException.class, ex.getCause());
        assertEquals("Code must have 6 alphanumeric characters after cleaning", ex.getCause().getMessage());
    }
}