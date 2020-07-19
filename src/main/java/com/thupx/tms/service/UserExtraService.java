package com.thupx.tms.service;

import com.thupx.tms.domain.UserExtra;
import com.thupx.tms.service.dto.UserExtraDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.thupx.tms.domain.UserExtra}.
 */
public interface UserExtraService {

    /**
     * Save a userExtra.
     *
     * @param userExtraDTO the entity to save.
     * @return the persisted entity.
     */
    UserExtraDTO save(UserExtraDTO userExtraDTO);
    
    UserExtra save2(UserExtra userExtra);

    /**
     * Get all the userExtras.
     *
     * @return the list of entities.
     */
    List<UserExtraDTO> findAll();
    
    List<UserExtra> findAll2();

    /**
     * Get the "id" userExtra.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserExtraDTO> findOne(Long id);

    /**
     * Delete the "id" userExtra.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
