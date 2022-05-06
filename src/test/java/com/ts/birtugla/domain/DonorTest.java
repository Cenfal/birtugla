package com.ts.birtugla.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ts.birtugla.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DonorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Donor.class);
        Donor donor1 = new Donor();
        donor1.setId(UUID.randomUUID());
        Donor donor2 = new Donor();
        donor2.setId(donor1.getId());
        assertThat(donor1).isEqualTo(donor2);
        donor2.setId(UUID.randomUUID());
        assertThat(donor1).isNotEqualTo(donor2);
        donor1.setId(null);
        assertThat(donor1).isNotEqualTo(donor2);
    }
}
