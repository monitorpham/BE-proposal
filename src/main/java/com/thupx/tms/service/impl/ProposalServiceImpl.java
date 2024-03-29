package com.thupx.tms.service.impl;

import com.thupx.tms.service.ProposalService;
import com.thupx.tms.domain.Proposal;
import com.thupx.tms.repository.ProposalRepository;
import com.thupx.tms.service.dto.ProposalDTO;
import com.thupx.tms.service.mapper.ProposalMapper;
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
 * Service Implementation for managing {@link Proposal}.
 */
@Service
@Transactional
public class ProposalServiceImpl implements ProposalService {

    private final Logger log = LoggerFactory.getLogger(ProposalServiceImpl.class);

    private final ProposalRepository proposalRepository;

    private final ProposalMapper proposalMapper;

    public ProposalServiceImpl(ProposalRepository proposalRepository, ProposalMapper proposalMapper) {
        this.proposalRepository = proposalRepository;
        this.proposalMapper = proposalMapper;
    }

    /**
     * Save a proposal.
     *
     * @param proposalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProposalDTO save(ProposalDTO proposalDTO) {
//        log.debug("Request to save Proposal : {}", proposalDTO);
        Proposal proposal = proposalMapper.toEntity(proposalDTO);
        proposal = proposalRepository.save(proposal);
        return proposalMapper.toDto(proposal);
    }

    /**
     * Get all the proposals.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProposalDTO> findAllDTO() {
//        log.debug("Request to get all ProposalsDTO");
        return proposalRepository.findAll().stream()
            .map(proposalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Proposal> findAll() {
//        log.debug("Request to get all Proposals");
        return proposalRepository.findAll().stream()
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one proposal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProposalDTO> findOne(Long id) {
//        log.debug("Request to get Proposal : {}", id);
        return proposalRepository.findById(id)
            .map(proposalMapper::toDto);
    }

    /**
     * Delete the proposal by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
//        log.debug("Request to delete Proposal : {}", id);

        proposalRepository.deleteById(id);
    }

	@Override
	public List<Proposal> findStatus(Boolean status) {
		// TODO Auto-generated method stub
//		log.debug("Request to get all Proposals status");
		return proposalRepository.findByStatus(status).stream()
	            .collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public List<Proposal> findStatusDate(Boolean statusChart, ZonedDateTime one_date, ZonedDateTime two_date) {
		// TODO Auto-generated method stub
		return proposalRepository.findByStatusDateBetween(statusChart,one_date,two_date).stream()
	            .collect(Collectors.toCollection(LinkedList::new));
	}

}