package com.thupx.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EquiqmentGroupMapperTest {

    private EquiqmentGroupMapper equiqmentGroupMapper;

    @BeforeEach
    public void setUp() {
        equiqmentGroupMapper = new EquiqmentGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(equiqmentGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(equiqmentGroupMapper.fromId(null)).isNull();
    }
}
