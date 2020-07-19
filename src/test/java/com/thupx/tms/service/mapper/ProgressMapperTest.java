package com.thupx.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProgressMapperTest {

    private ProgressMapper progressMapper;

    @BeforeEach
    public void setUp() {
        progressMapper = new ProgressMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(progressMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(progressMapper.fromId(null)).isNull();
    }
}
