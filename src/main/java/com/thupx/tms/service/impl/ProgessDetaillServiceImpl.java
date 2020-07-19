package com.thupx.tms.service.impl;

import com.thupx.tms.service.ProgessDetaillService;
import com.thupx.tms.domain.ProgessDetaill;
import com.thupx.tms.repository.ProgessDetaillRepository;
import com.thupx.tms.service.dto.ProgessDetaillDTO;
import com.thupx.tms.service.mapper.ProgessDetaillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProgessDetaill}.
 */
@Service
@Transactional
public class ProgessDetaillServiceImpl implements ProgessDetaillService {

    private final Logger log = LoggerFactory.getLogger(ProgessDetaillServiceImpl.class);

    private final ProgessDetaillRepository progessDetaillRepository;

    private final ProgessDetaillMapper progessDetaillMapper;

    public ProgessDetaillServiceImpl(ProgessDetaillRepository progessDetaillRepository, ProgessDetaillMapper progessDetaillMapper) {
        this.progessDetaillRepository = progessDetaillRepository;
        this.progessDetaillMapper = progessDetaillMapper;
    }

    /**
     * Save a progessDetaill.
     *
     * @param progessDetaillDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProgessDetaillDTO save(ProgessDetaillDTO progessDetaillDTO) {
        log.debug("Request to save ProgessDetaill : {}", progessDetaillDTO);
        ProgessDetaill progessDetaill = progessDetaillMapper.toEntity(progessDetaillDTO);
        progessDetaill = progessDetaillRepository.save(progessDetaill);
        return progessDetaillMapper.toDto(progessDetaill);
    }

    /**
     * Get all the progessDetaills.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProgessDetaillDTO> findAll() {
        log.debug("Request to get all ProgessDetaills");
        return progessDetaillRepository.findAll().stream()
            .map(progessDetaillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProgessDetaill> findAllByProposalId(Long proposalId) {
        log.debug("Request to get all ProgessDetaills");
        return progessDetaillRepository.findAllByProposalIdOrderByIdAsc(proposalId).stream()
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProgessDetaillDTO> findAllDTOByProposalId(Long proposalId) {
        log.debug("Request to get all ProgessDetaills");
        return progessDetaillRepository.findAllByProposalIdOrderByIdAsc(proposalId).stream()
        	.map(progessDetaillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Override
    public int setDoneProgress(ZonedDateTime endDate, Long id) {
        log.debug("set done progress");
        return progessDetaillRepository.setDoneProgress(endDate, id);
    }


    /**
     * Get one progessDetaill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProgessDetaillDTO> findOne(Long id) {
        log.debug("Request to get ProgessDetaill : {}", id);
        return progessDetaillRepository.findById(id)
            .map(progessDetaillMapper::toDto);
    }

    /**
     * Delete the progessDetaill by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProgessDetaill : {}", id);

        progessDetaillRepository.deleteById(id);
    }
}
