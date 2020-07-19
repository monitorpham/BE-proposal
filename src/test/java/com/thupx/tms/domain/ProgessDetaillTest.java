package com.thupx.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thupx.tms.web.rest.TestUtil;

public class ProgessDetaillTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgessDetaill.class);
        ProgessDetaill progessDetaill1 = new ProgessDetaill();
        progessDetaill1.setId(1L);
        ProgessDetaill progessDetaill2 = new ProgessDetaill();
        progessDetaill2.setId(progessDetaill1.getId());
        assertThat(progessDetaill1).isEqualTo(progessDetaill2);
        progessDetaill2.setId(2L);
        assertThat(progessDetaill1).isNotEqualTo(progessDetaill2);
        progessDetaill1.setId(null);
        assertThat(progessDetaill1).isNotEqualTo(progessDetaill2);
    }
}
