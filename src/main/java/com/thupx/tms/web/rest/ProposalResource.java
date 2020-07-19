package com.thupx.tms.web.rest;

import com.thupx.tms.domain.ProgessDetaill;
import com.thupx.tms.domain.Progress;
import com.thupx.tms.domain.ProgressStage;
import com.thupx.tms.domain.Proposal;
import com.thupx.tms.domain.ProposalData;
import com.thupx.tms.domain.ProposalData2;
import com.thupx.tms.domain.UserExtra;
import com.thupx.tms.repository.UserExtraRepository;
import com.thupx.tms.service.ProgressService;
import com.thupx.tms.service.ProposalService;
import com.thupx.tms.service.UserService;
import com.thupx.tms.service.ProgessDetaillService;
import com.thupx.tms.web.rest.errors.BadRequestAlertException;
import com.thupx.tms.service.dto.ProgessDetaillDTO;
import com.thupx.tms.service.dto.ProgressDTO;
import com.thupx.tms.service.dto.ProposalDTO;
import com.thupx.tms.service.dto.UserExtraDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.thupx.tms.domain.Proposal}.
 */
@RestController
@RequestMapping("/api")
public class ProposalResource {

	private final Logger log = LoggerFactory.getLogger(ProposalResource.class);

	private static final String ENTITY_NAME = "proposal";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ProposalService proposalService;

	private final ProgressService progressService;

	private final ProgessDetaillService progessDetaillService;

	private final UserService userService;
	
	private final UserExtraRepository extraRepository;

	public ProposalResource(ProposalService proposalService, ProgressService progressService,
			ProgessDetaillService progessDetaillService, UserService userService, UserExtraRepository extraRepository) {
		this.proposalService = proposalService;
		this.progressService = progressService;
		this.progessDetaillService = progessDetaillService;
		this.userService = userService;
		this.extraRepository = extraRepository;
	}

	/**
	 * {@code POST  /proposals} : Create a new proposal.
	 *
	 * @param proposalDTO the proposalDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new proposalDTO, or with status {@code 400 (Bad Request)} if
	 *         the proposal has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/proposals")
	public ResponseEntity<?> createProposal(@RequestBody ProposalDTO proposalDTO) throws URISyntaxException {
		log.debug("REST request to save Proposal : {}", proposalDTO);
		if (proposalDTO.getId() != null) {
			throw new BadRequestAlertException("A new proposal cannot already have an ID", ENTITY_NAME, "idexists");
		}
		
//		boolean checkUserExtra = false;
//		List<UserExtra> userExtras = extraRepository.findAll();
//		
//		for (UserExtra userExtra : userExtras) {
//			if(userExtra.getId().equals(proposalDTO.getUserExtraId())) {
//				checkUserExtra = true;
//			}
//		}
//		
//		
//		if (checkUserExtra == false) {
//			System.out.println("User with id " + proposalDTO.getUserExtraId() + " not found");
//			ResponseUtil.wrapOrNotFound();
//		}
		
		Optional<UserExtra> userExtra = extraRepository.findById(proposalDTO.getUserExtraId());
		if (userExtra.isEmpty()) {
			 return new ResponseEntity<>(
			          "User ID not found", 
			          HttpStatus.BAD_REQUEST);
		}
		
		
		ZonedDateTime time = ZonedDateTime.now();
		
		//proposalDTO.setStartDate(time);
		proposalDTO.setStatus(false);
		
	    //proposalDTO.setUserExtraId(userService.getUserid());
	    
		
		ProposalDTO result = proposalService.save(proposalDTO);

		List<ProgressDTO> progresses = progressService.findAll();

		for (ProgressDTO progressDTO : progresses) {
			ProgessDetaillDTO progessDetaillDTO = new ProgessDetaillDTO(result.getId(), progressDTO.getId());
			progessDetaillService.save(progessDetaillDTO);
		}

		return ResponseEntity
				.created(new URI("/api/proposals/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /proposals} : Updates an existing proposal.
	 *
	 * @param proposalDTO the proposalDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated proposalDTO, or with status {@code 400 (Bad Request)} if
	 *         the proposalDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the proposalDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/proposals")
	public ResponseEntity<ProposalDTO> updateProposal(@RequestBody ProposalDTO proposalDTO) throws URISyntaxException {
		log.debug("REST request to update Proposal : {}", proposalDTO);
		if (proposalDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ProposalDTO result = proposalService.save(proposalDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, proposalDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /proposals} : get all the proposals.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of proposals in body.
	 */
//	@GetMapping("/proposals")
//	public List<ProposalDTO> getAllProposals() {
//		log.debug("REST request to get all ProposalsDTO");
//		return proposalService.findAllDTO();
//	}
	
	@GetMapping("/proposals")
	public List<Proposal> getAllProposals() {
		log.debug("REST request to get all Proposals");
		return proposalService.findAll();
	}

	public ProgessDetaill getCurrentProgessDetaill(Long idProposal) {
		log.debug("REST request to get ProgessDetaill : {}", idProposal);
		List<ProgessDetaill> progessDetaills = progessDetaillService.findAllByProposalId(idProposal);

		
//		boolean checkAll = false;
//		for (ProgessDetaill progessDetaill : progessDetaills) {
//			if(progessDetaill.getEndDate()!= null) {
//				checkAll = true;
//			}
//		}
//		
//		if(!checkAll) {
//			return progessDetaills.get(0);
//		}
		
		for (int i=progessDetaills.size() -1 ; i>0; i--  ) {
			log.debug("issueeeeeeeeeeeeeeeeeeeeee", progessDetaills.get(i).getEndDate());
			if (progessDetaills.get(i).getEndDate() != null) {
				return progessDetaills.get(i);
				
			}
		}
		
		return progessDetaills.get(0);
	}

	public ProgessDetaillDTO getCurrentProgessDetaillDTO(Long idProposal) {
		log.debug("REST request to get current ProgessDetaillDTO : {}", idProposal);
		List<ProgessDetaillDTO> progessDetaills = progessDetaillService.findAllDTOByProposalId(idProposal);

		for (ProgessDetaillDTO progessDetaill : progessDetaills) {			
			if (progessDetaill.getEndDate() == null) {
				return progessDetaill;
			}
		}
		return progessDetaills.get(progessDetaills.size() - 1);
	}


	@GetMapping("/proposals-data-table")
	public List<ProposalData2> getAllProposalsDataTable() {
		log.debug("REST request to get all Proposals-table");
		
		long countDays = 0;
		
		List<ProgressDTO> progressDTOs = progressService.findAll(); 
		
		for (ProgressDTO progressDTO : progressDTOs) {
			countDays = countDays + progressDTO.getLimit();
		}
		
		
		
		List<Proposal> proposals = proposalService.findAll();
		List<ProposalData2> proposalDatas = new ArrayList<>();
		
		List<ProgressDTO> progesses = progressService.findAll();
		
		int group = userService.checkAdmin();
		
		log.debug("groupppppppppppppppppppppp: {}", group);
		
		// super admin
		if (group == 0) {
			for (Proposal proposal : proposals) {
				ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
				if(proposal.isStatus()){
					proposalDatas.add(new ProposalData2(proposal,currentDetaill.getId(),
							currentDetaill.getProgress().getContentTask(),
							proposal.getStartDate().plusDays(countDays+proposal.getAdditionalDate()),calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
				}else{
					proposalDatas.add(new ProposalData2(proposal,currentDetaill.getId(),
							currentDetaill.getProgress().getContentTask(),
							proposal.getStartDate().plusDays(countDays+proposal.getAdditionalDate()),calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
				}
								
			}
			return proposalDatas;
		}
		
		
		// to truong
		if (group != -1) {
			List<UserExtra> userExtras = extraRepository.findAllByEquiqmentGroupId(Long.valueOf(group));			
			for (Proposal proposal : proposals) {
				for(UserExtra userExtra : userExtras) {
					if(proposal.getUserExtra().getId().equals(userExtra.getId())) {
						ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
						if(proposal.isStatus()){
							proposalDatas.add(new ProposalData2(proposal,currentDetaill.getId(),
									currentDetaill.getProgress().getContentTask(),
									proposal.getStartDate().plusDays(countDays+proposal.getAdditionalDate()),calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
						}else{
							proposalDatas.add(new ProposalData2(proposal,currentDetaill.getId(),
									currentDetaill.getProgress().getContentTask(),
									proposal.getStartDate().plusDays(countDays+proposal.getAdditionalDate()),calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
						}
					}
				}
				
			}
			log.debug("totruong: {}", group);
			return proposalDatas;
		}
		
		// thanh vien
		log.debug("totruong: {}", group);
		UserExtra extra = extraRepository.findById(userService.getUserid()).get();
		log.debug("extra: {}", extra);
		for (Proposal proposal : proposals) {
				if(proposal.getUserExtra().getId().equals(extra.getId())) {
					ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
					if(proposal.isStatus()){
						proposalDatas.add(new ProposalData2(proposal,currentDetaill.getId(),
								currentDetaill.getProgress().getContentTask(),
								proposal.getStartDate().plusDays(countDays+proposal.getAdditionalDate()),calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
					}else{
						proposalDatas.add(new ProposalData2(proposal,currentDetaill.getId(),
								currentDetaill.getProgress().getContentTask(),
								proposal.getStartDate().plusDays(countDays+proposal.getAdditionalDate()),calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
					}
				}						
		}
		
		return proposalDatas;
	}

	@GetMapping("/get-All-ProgressDetail-By-ProposalId")
	public List<ProgressStage> getAllProgressDetailByProposalId(@RequestParam Long id) {
		log.debug("REST request to get-All-ProgressDetail-By-ProposalId");
		List<ProgessDetaill> progessDetaills = progessDetaillService.findAllByProposalId(id);
		
		List<ProgressStage> progressStages = new ArrayList<>();
		
		
//		Progress startProgress = new Progress();
//		startProgress.setContentTask("Tạo mới");
//		progressStages.add(new ProgressStage(Long.valueOf(0), null, null, null, startProgress,"Khởi tạo"));
		
		for(ProgessDetaill progessDetaill : progessDetaills) {
			progressStages.add(new ProgressStage(progessDetaill.getId(), progessDetaill.getStartDate(), progessDetaill.getEndDate(), progessDetaill.getLastModifiedBy(), progessDetaill.getProgress(),progessDetaill.getNote()));
		}
		
//		Progress completeProgress = new Progress();
//		completeProgress.setContentTask("Hoàn thành");
//		
//		
//		progressStages.add(new ProgressStage(Long.valueOf(8), null, proposalService.findOne(id).get().getEndDate(), null, completeProgress, "hoàn thành"));
		
		return progressStages;
	}
	
	
	@PutMapping("/update-All-ProgressDetail-By-ProposalId")
	public List<ProgessDetaillDTO> updateAllProgressDetailByProposalId(@RequestBody List<ProgressStage> progressStages, @RequestParam Long proposalId){
		log.debug("REST request to update-All-ProgressDetail-By-ProposalId");
		
		List<ProgessDetaillDTO> detaillDTOs = new ArrayList<>();
		
//		for(int i = progressStages.size()-2; i > 0; i--) {
//				if(progressStages.get(i).getTimeStart() != null) {
//					for(ProgressStage progressStage1 : progressStages) {
//						if(!progressStages.get(i).getId().equals(new Long(0)) && !progressStages.get(i).getId().equals(new Long(8))) {
//							if(progressStage1.getId().equals(progressStages.get(i).getId())) {
//								break;
//							}
//							
//							if(progressStage1.getTimeStart() == null) {
//								progressStage1.setTimeStart(progressStages.get(i).getTimeStart());
//								progressStage1.setTimeEnd(progressStages.get(i).getTimeEnd());
//							}				
//						}
//					}
//					break;
//				}
//		}		
		
		for(ProgressStage progressStage : progressStages) {
//			if(!progressStage.getId().equals(new Long(0)) && !progressStage.getId().equals(new Long(8))) {
			ProgessDetaillDTO detaillDTO = new ProgessDetaillDTO();
			detaillDTO.setId(progressStage.getId());
			detaillDTO.setProgressId(progressStage.getProgress().getId());
			detaillDTO.setProposalId(proposalId);
			detaillDTO.setNote(progressStage.getNote());
			detaillDTO.setStartDate(progressStage.getTimeStart());
			detaillDTO.setEndDate(progressStage.getTimeEnd());
			detaillDTOs.add(detaillDTO);
			progessDetaillService.save(detaillDTO);	
//			}
		}	
		
		if(progressStages.get(progressStages.size() - 1).getTimeEnd() != null ) {
			ProposalDTO proposalDTO = proposalService.findOne(proposalId).get();
			proposalDTO.setEndDate(progressStages.get(progressStages.size() - 1).getTimeEnd());
			proposalDTO.setStatus(true);
			proposalService.save(proposalDTO);
		}
		
		return detaillDTOs;
	}

	/**
	 * {@code GET  /proposals/:id} : get the "id" proposal.
	 *
	 * @param id the id of the proposalDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the proposalDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/proposals/{id}")
	public ResponseEntity<ProposalDTO> getProposal(@PathVariable Long id) {
		log.debug("REST request to get Proposal : {}", id);
		Optional<ProposalDTO> proposalDTO = proposalService.findOne(id);
		return ResponseUtil.wrapOrNotFound(proposalDTO);
	}

	/**
	 * {@code DELETE  /proposals/:id} : delete the "id" proposal.
	 *
	 * @param id the id of the proposalDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/proposals/{id}")
	public ResponseEntity<Void> deleteProposal(@PathVariable Long id) {
		log.debug("REST request to delete Proposal : {}", id);
		
		List<ProgessDetaill> progessDetaills = progessDetaillService.findAllByProposalId(id);
		
		for(ProgessDetaill detaill : progessDetaills) {
			progessDetaillService.delete(detaill.getId());
		}

		proposalService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}
	
	private Integer calRemainingDate(ZonedDateTime currentDate,ZonedDateTime createDateProposal,ChronoUnit unit) {
	return (int) (long) unit.between(createDateProposal,currentDate);
}
}
