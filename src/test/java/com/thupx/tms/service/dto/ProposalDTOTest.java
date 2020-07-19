package com.thupx.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thupx.tms.web.rest.TestUtil;

public class ProposalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProposalDTO.class);
        ProposalDTO proposalDTO1 = new ProposalDTO();
        proposalDTO1.setId(1L);
        ProposalDTO proposalDTO2 = new ProposalDTO();
        assertThat(proposalDTO1).isNotEqualTo(proposalDTO2);
        proposalDTO2.setId(proposalDTO1.getId());
        assertThat(proposalDTO1).isEqualTo(proposalDTO2);
        proposalDTO2.setId(2L);
        assertThat(proposalDTO1).isNotEqualTo(proposalDTO2);
        proposalDTO1.setId(null);
        assertThat(proposalDTO1).isNotEqualTo(proposalDTO2);
    }
}
