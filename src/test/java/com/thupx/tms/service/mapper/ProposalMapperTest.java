package com.thupx.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProposalMapperTest {

    private ProposalMapper proposalMapper;

    @BeforeEach
    public void setUp() {
        proposalMapper = new ProposalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(proposalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(proposalMapper.fromId(null)).isNull();
    }
}
