package com.thupx.tms.service;

import com.thupx.tms.domain.ProgessDetaill;
import com.thupx.tms.service.dto.ProgessDetaillDTO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.thupx.tms.domain.ProgessDetaill}.
 */
public interface ProgessDetaillService {

    /**
     * Save a progessDetaill.
     *
     * @param progessDetaillDTO the entity to save.
     * @return the persisted entity.
     */
    ProgessDetaillDTO save(ProgessDetaillDTO progessDetaillDTO);

    /**
     * Get all the progessDetaills.
     *
     * @return the list of entities.
     */
    List<ProgessDetaillDTO> findAll();
    
    List<ProgessDetaill> findAllByProposalId(Long id);
    
    List<ProgessDetaillDTO> findAllDTOByProposalId(Long id);
    
    int setDoneProgress(ZonedDateTime endDate, Long id); 


    /**
     * Get the "id" progessDetaill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgessDetaillDTO> findOne(Long id);

    /**
     * Delete the "id" progessDetaill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
