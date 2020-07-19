package com.thupx.tms.service.impl;

import com.thupx.tms.service.EquiqmentGroupService;
import com.thupx.tms.domain.EquiqmentGroup;
import com.thupx.tms.repository.EquiqmentGroupRepository;
import com.thupx.tms.service.dto.EquiqmentGroupDTO;
import com.thupx.tms.service.mapper.EquiqmentGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EquiqmentGroup}.
 */
@Service
@Transactional
public class EquiqmentGroupServiceImpl implements EquiqmentGroupService {

    private final Logger log = LoggerFactory.getLogger(EquiqmentGroupServiceImpl.class);

    private final EquiqmentGroupRepository equiqmentGroupRepository;

    private final EquiqmentGroupMapper equiqmentGroupMapper;

    public EquiqmentGroupServiceImpl(EquiqmentGroupRepository equiqmentGroupRepository, EquiqmentGroupMapper equiqmentGroupMapper) {
        this.equiqmentGroupRepository = equiqmentGroupRepository;
        this.equiqmentGroupMapper = equiqmentGroupMapper;
    }

    /**
     * Save a equiqmentGroup.
     *
     * @param equiqmentGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EquiqmentGroupDTO save(EquiqmentGroupDTO equiqmentGroupDTO) {
        log.debug("Request to save EquiqmentGroup : {}", equiqmentGroupDTO);
        EquiqmentGroup equiqmentGroup = equiqmentGroupMapper.toEntity(equiqmentGroupDTO);
        equiqmentGroup = equiqmentGroupRepository.save(equiqmentGroup);
        return equiqmentGroupMapper.toDto(equiqmentGroup);
    }

    /**
     * Get all the equiqmentGroups.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EquiqmentGroupDTO> findAll() {
        log.debug("Request to get all EquiqmentGroups");
        return equiqmentGroupRepository.findAll().stream()
            .map(equiqmentGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one equiqmentGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EquiqmentGroupDTO> findOne(Long id) {
        log.debug("Request to get EquiqmentGroup : {}", id);
        return equiqmentGroupRepository.findById(id)
            .map(equiqmentGroupMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<EquiqmentGroup> findOneById(Long id) {
        log.debug("Request to get EquiqmentGroup : {}", id);
        return equiqmentGroupRepository.findById(id);
    }

    /**
     * Delete the equiqmentGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EquiqmentGroup : {}", id);

        equiqmentGroupRepository.deleteById(id);
    }
}
