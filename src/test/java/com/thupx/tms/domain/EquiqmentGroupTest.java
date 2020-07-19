package com.thupx.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thupx.tms.web.rest.TestUtil;

public class EquiqmentGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquiqmentGroup.class);
        EquiqmentGroup equiqmentGroup1 = new EquiqmentGroup();
        equiqmentGroup1.setId(1L);
        EquiqmentGroup equiqmentGroup2 = new EquiqmentGroup();
        equiqmentGroup2.setId(equiqmentGroup1.getId());
        assertThat(equiqmentGroup1).isEqualTo(equiqmentGroup2);
        equiqmentGroup2.setId(2L);
        assertThat(equiqmentGroup1).isNotEqualTo(equiqmentGroup2);
        equiqmentGroup1.setId(null);
        assertThat(equiqmentGroup1).isNotEqualTo(equiqmentGroup2);
    }
}
