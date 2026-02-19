package com.tenda.coupon.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CouponStatusTest {

    @Test
    void testActiveDescription() {
        assertEquals("Ativo", CouponStatus.ACTIVE.getDescription());
    }

    @Test
    void testInactiveDescription() {
        assertEquals("Inativo", CouponStatus.INACTIVE.getDescription());
    }

    @Test
    void testDeletedDescription() {
        assertEquals("Deletado", CouponStatus.DELETED.getDescription());
    }

    @Test
    void testEnumValues() {
        CouponStatus[] values = CouponStatus.values();
        assertArrayEquals(
                new CouponStatus[]{CouponStatus.ACTIVE, CouponStatus.INACTIVE, CouponStatus.DELETED},
                values
        );
    }
}