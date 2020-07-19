package com.thupx.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thupx.tms.web.rest.TestUtil;

public class HospitalDepartmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HospitalDepartmentDTO.class);
        HospitalDepartmentDTO hospitalDepartmentDTO1 = new HospitalDepartmentDTO();
        hospitalDepartmentDTO1.setId(1L);
        HospitalDepartmentDTO hospitalDepartmentDTO2 = new HospitalDepartmentDTO();
        assertThat(hospitalDepartmentDTO1).isNotEqualTo(hospitalDepartmentDTO2);
        hospitalDepartmentDTO2.setId(hospitalDepartmentDTO1.getId());
        assertThat(hospitalDepartmentDTO1).isEqualTo(hospitalDepartmentDTO2);
        hospitalDepartmentDTO2.setId(2L);
        assertThat(hospitalDepartmentDTO1).isNotEqualTo(hospitalDepartmentDTO2);
        hospitalDepartmentDTO1.setId(null);
        assertThat(hospitalDepartmentDTO1).isNotEqualTo(hospitalDepartmentDTO2);
    }
}
