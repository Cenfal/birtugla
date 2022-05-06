package com.ts.birtugla.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ts.birtugla.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TotalAmountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TotalAmount.class);
        TotalAmount totalAmount1 = new TotalAmount();
        totalAmount1.setId(UUID.randomUUID());
        TotalAmount totalAmount2 = new TotalAmount();
        totalAmount2.setId(totalAmount1.getId());
        assertThat(totalAmount1).isEqualTo(totalAmount2);
        totalAmount2.setId(UUID.randomUUID());
        assertThat(totalAmount1).isNotEqualTo(totalAmount2);
        totalAmount1.setId(null);
        assertThat(totalAmount1).isNotEqualTo(totalAmount2);
    }
}
