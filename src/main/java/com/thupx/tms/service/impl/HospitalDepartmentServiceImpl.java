package com.thupx.tms.service.impl;

import com.thupx.tms.service.HospitalDepartmentService;
import com.thupx.tms.domain.HospitalDepartment;
import com.thupx.tms.repository.HospitalDepartmentRepository;
import com.thupx.tms.service.dto.HospitalDepartmentDTO;
import com.thupx.tms.service.mapper.HospitalDepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link HospitalDepartment}.
 */
@Service
@Transactional
public class HospitalDepartmentServiceImpl implements HospitalDepartmentService {

    private final Logger log = LoggerFactory.getLogger(HospitalDepartmentServiceImpl.class);

    private final HospitalDepartmentRepository hospitalDepartmentRepository;

    private final HospitalDepartmentMapper hospitalDepartmentMapper;

    public HospitalDepartmentServiceImpl(HospitalDepartmentRepository hospitalDepartmentRepository, HospitalDepartmentMapper hospitalDepartmentMapper) {
        this.hospitalDepartmentRepository = hospitalDepartmentRepository;
        this.hospitalDepartmentMapper = hospitalDepartmentMapper;
    }

    /**
     * Save a hospitalDepartment.
     *
     * @param hospitalDepartmentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HospitalDepartmentDTO save(HospitalDepartmentDTO hospitalDepartmentDTO) {
        log.debug("Request to save HospitalDepartment : {}", hospitalDepartmentDTO);
        HospitalDepartment hospitalDepartment = hospitalDepartmentMapper.toEntity(hospitalDepartmentDTO);
        hospitalDepartment = hospitalDepartmentRepository.save(hospitalDepartment);
        return hospitalDepartmentMapper.toDto(hospitalDepartment);
    }

    /**
     * Get all the hospitalDepartments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HospitalDepartmentDTO> findAll() {
        log.debug("Request to get all HospitalDepartments");
        return hospitalDepartmentRepository.findAll().stream()
            .map(hospitalDepartmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one hospitalDepartment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HospitalDepartmentDTO> findOne(Long id) {
        log.debug("Request to get HospitalDepartment : {}", id);
        return hospitalDepartmentRepository.findById(id)
            .map(hospitalDepartmentMapper::toDto);
    }

    /**
     * Delete the hospitalDepartment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HospitalDepartment : {}", id);

        hospitalDepartmentRepository.deleteById(id);
    }
}
