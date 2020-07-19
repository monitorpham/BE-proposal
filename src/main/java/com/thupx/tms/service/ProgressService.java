package com.thupx.tms.service;

import com.thupx.tms.service.dto.ProgressDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.thupx.tms.domain.Progress}.
 */
public interface ProgressService {

    /**
     * Save a progress.
     *
     * @param progressDTO the entity to save.
     * @return the persisted entity.
     */
    ProgressDTO save(ProgressDTO progressDTO);

    /**
     * Get all the progresses.
     *
     * @return the list of entities.
     */
    List<ProgressDTO> findAll();


    /**
     * Get the "id" progress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgressDTO> findOne(Long id);

    /**
     * Delete the "id" progress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
