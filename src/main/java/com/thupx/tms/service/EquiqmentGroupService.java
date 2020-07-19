package com.thupx.tms.service;

import com.thupx.tms.domain.EquiqmentGroup;
import com.thupx.tms.service.dto.EquiqmentGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.thupx.tms.domain.EquiqmentGroup}.
 */
public interface EquiqmentGroupService {

    /**
     * Save a equiqmentGroup.
     *
     * @param equiqmentGroupDTO the entity to save.
     * @return the persisted entity.
     */
    EquiqmentGroupDTO save(EquiqmentGroupDTO equiqmentGroupDTO);

    /**
     * Get all the equiqmentGroups.
     *
     * @return the list of entities.
     */
    List<EquiqmentGroupDTO> findAll();


    /**
     * Get the "id" equiqmentGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EquiqmentGroupDTO> findOne(Long id);
    
    Optional<EquiqmentGroup> findOneById(Long id);

    /**
     * Delete the "id" equiqmentGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
