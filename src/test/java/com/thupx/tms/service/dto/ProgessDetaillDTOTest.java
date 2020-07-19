package com.thupx.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thupx.tms.web.rest.TestUtil;

public class ProgessDetaillDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgessDetaillDTO.class);
        ProgessDetaillDTO progessDetaillDTO1 = new ProgessDetaillDTO();
        progessDetaillDTO1.setId(1L);
        ProgessDetaillDTO progessDetaillDTO2 = new ProgessDetaillDTO();
        assertThat(progessDetaillDTO1).isNotEqualTo(progessDetaillDTO2);
        progessDetaillDTO2.setId(progessDetaillDTO1.getId());
        assertThat(progessDetaillDTO1).isEqualTo(progessDetaillDTO2);
        progessDetaillDTO2.setId(2L);
        assertThat(progessDetaillDTO1).isNotEqualTo(progessDetaillDTO2);
        progessDetaillDTO1.setId(null);
        assertThat(progessDetaillDTO1).isNotEqualTo(progessDetaillDTO2);
    }
}
