package com.thupx.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thupx.tms.web.rest.TestUtil;

public class HospitalDepartmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HospitalDepartment.class);
        HospitalDepartment hospitalDepartment1 = new HospitalDepartment();
        hospitalDepartment1.setId(1L);
        HospitalDepartment hospitalDepartment2 = new HospitalDepartment();
        hospitalDepartment2.setId(hospitalDepartment1.getId());
        assertThat(hospitalDepartment1).isEqualTo(hospitalDepartment2);
        hospitalDepartment2.setId(2L);
        assertThat(hospitalDepartment1).isNotEqualTo(hospitalDepartment2);
        hospitalDepartment1.setId(null);
        assertThat(hospitalDepartment1).isNotEqualTo(hospitalDepartment2);
    }
}
