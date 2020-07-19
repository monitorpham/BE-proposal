package com.thupx.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thupx.tms.web.rest.TestUtil;

public class EquiqmentGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquiqmentGroupDTO.class);
        EquiqmentGroupDTO equiqmentGroupDTO1 = new EquiqmentGroupDTO();
        equiqmentGroupDTO1.setId(1L);
        EquiqmentGroupDTO equiqmentGroupDTO2 = new EquiqmentGroupDTO();
        assertThat(equiqmentGroupDTO1).isNotEqualTo(equiqmentGroupDTO2);
        equiqmentGroupDTO2.setId(equiqmentGroupDTO1.getId());
        assertThat(equiqmentGroupDTO1).isEqualTo(equiqmentGroupDTO2);
        equiqmentGroupDTO2.setId(2L);
        assertThat(equiqmentGroupDTO1).isNotEqualTo(equiqmentGroupDTO2);
        equiqmentGroupDTO1.setId(null);
        assertThat(equiqmentGroupDTO1).isNotEqualTo(equiqmentGroupDTO2);
    }
}
