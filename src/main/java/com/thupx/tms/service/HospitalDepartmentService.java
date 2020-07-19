package com.thupx.tms.service;

import com.thupx.tms.service.dto.HospitalDepartmentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.thupx.tms.domain.HospitalDepartment}.
 */
public interface HospitalDepartmentService {

    /**
     * Save a hospitalDepartment.
     *
     * @param hospitalDepartmentDTO the entity to save.
     * @return the persisted entity.
     */
    HospitalDepartmentDTO save(HospitalDepartmentDTO hospitalDepartmentDTO);

    /**
     * Get all the hospitalDepartments.
     *
     * @return the list of entities.
     */
    List<HospitalDepartmentDTO> findAll();


    /**
     * Get the "id" hospitalDepartment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HospitalDepartmentDTO> findOne(Long id);

    /**
     * Delete the "id" hospitalDepartment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
