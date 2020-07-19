package com.thupx.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProgessDetaillMapperTest {

    private ProgessDetaillMapper progessDetaillMapper;

    @BeforeEach
    public void setUp() {
        progessDetaillMapper = new ProgessDetaillMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(progessDetaillMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(progessDetaillMapper.fromId(null)).isNull();
    }
}
