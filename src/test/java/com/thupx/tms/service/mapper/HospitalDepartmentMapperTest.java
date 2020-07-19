package com.thupx.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HospitalDepartmentMapperTest {

    private HospitalDepartmentMapper hospitalDepartmentMapper;

    @BeforeEach
    public void setUp() {
        hospitalDepartmentMapper = new HospitalDepartmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(hospitalDepartmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(hospitalDepartmentMapper.fromId(null)).isNull();
    }
}
