package com.thupx.tms.service;

import com.thupx.tms.domain.Proposal;
import com.thupx.tms.service.dto.ProposalDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.thupx.tms.domain.Proposal}.
 */
public interface ProposalService {

    /**
     * Save a proposal.
     *
     * @param proposalDTO the entity to save.
     * @return the persisted entity.
     */
    ProposalDTO save(ProposalDTO proposalDTO);

    /**
     * Get all the proposals.
     *
     * @return the list of entities.
     */
    List<ProposalDTO> findAllDTO();

    List<Proposal> findAll();
    /**
     * Get the "id" proposal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProposalDTO> findOne(Long id);

    /**
     * Delete the "id" proposal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
