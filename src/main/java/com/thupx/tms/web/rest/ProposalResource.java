package com.thupx.tms.web.rest;

import com.thupx.tms.domain.ProgessDetaill;
import com.thupx.tms.domain.Progress;
import com.thupx.tms.domain.ProgressStage;
import com.thupx.tms.domain.Proposal;
import com.thupx.tms.domain.ProposalData;
import com.thupx.tms.domain.ProposalData2;
import com.thupx.tms.domain.UserExtra;
import com.thupx.tms.repository.ProposalRepository;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.Date;

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

	@Autowired
	ProposalRepository proposalRepository;

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

		Optional<UserExtra> userExtraa = extraRepository.findById(proposalDTO.getUserExtraId());
		if (userExtraa.isEmpty()) {
			return new ResponseEntity<>("User ID not found", HttpStatus.BAD_REQUEST);
		}

		ZonedDateTime time = ZonedDateTime.now();

		// proposalDTO.setStartDate(time);
		proposalDTO.setStatus(false);
		proposalDTO
				.setRemainingDate(calRemainingDate(ZonedDateTime.now(), proposalDTO.getStartDate(), ChronoUnit.DAYS));
		proposalDTO.setCurrentProgressId(1);
		proposalDTO.setCurrentProgressName("Tạo mới");
		proposalDTO.setStatusChart(false);
		// proposalDTO.setUserExtraId(userService.getUserid());

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
//		log.debug("REST request to get all Proposals");
		return proposalService.findAll();
	}

	public ProgessDetaill getCurrentProgessDetaill(Long idProposal) {
//		log.debug("REST request to get ProgessDetaill : {}", idProposal);
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

		for (int i = progessDetaills.size() - 1; i > 0; i--) {
//			log.debug("issueeeeeeeeeeeeeeeeeeeeee", progessDetaills.get(i).getEndDate());
			if (progessDetaills.get(i).getEndDate() != null) {
				return progessDetaills.get(i);

			}
		}

		return progessDetaills.get(0);
	}

	public ProgessDetaillDTO getCurrentProgessDetaillDTO(Long idProposal) {
//		log.debug("REST request to get current ProgessDetaillDTO : {}", idProposal);
		List<ProgessDetaillDTO> progessDetaills = progessDetaillService.findAllDTOByProposalId(idProposal);

		for (ProgessDetaillDTO progessDetaill : progessDetaills) {
			if (progessDetaill.getEndDate() == null) {
				return progessDetaill;
			}
		}
		return progessDetaills.get(progessDetaills.size() - 1);
	}

	@GetMapping("/proposals-data-table")
	public ResponseEntity<Page<ProposalData2>> getAllProposalsDataTable(@RequestParam int pageNum,
			@RequestParam int pageSize, @RequestParam(defaultValue = "") String sortBy, Sort.Direction direction
//			@RequestParam(defaultValue = "ASC") String sortDir
//			@RequestParam(required = false) String title
	) {
//		log.debug("REST request to get all Proposals-table");
//		Pageable pageable = PageRequest.of(pageNum, pageSize,Sort.by("id").ascending());
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(direction, sortBy));
		long countDays = 0;

		List<ProgressDTO> progressDTOs = progressService.findAll();

		for (ProgressDTO progressDTO : progressDTOs) {
			countDays = countDays + progressDTO.getLimit();
		}

		Page<Proposal> proposals = proposalRepository.findAll(pageable);

		List<ProposalData2> proposalDatas = new ArrayList<>();

//		List<ProgressDTO> progesses = progressService.findAll();

		int group = userService.checkAdmin();

		log.debug("groupppppppppppppppppppppp: {}", group);

		// super admin
		if (group == 0) {
//			Page<Proposal> proposals = proposalRepository.findAll(pageable);
			for (Proposal proposal : proposals) {
				ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
				log.debug("proposallllllllllllllllll: {}", proposal.isStatus());
				if (proposal.isStatus()) {

					proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
							currentDetaill.getProgress().getContentTask(),
							proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
							calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
				} else {
					proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
							currentDetaill.getProgress().getContentTask(),
							proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
							calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
				}

			}
//			return proposalDatas;
			Page<ProposalData2> holder = new PageImpl<>(proposalDatas, pageable, proposals.getTotalElements());
			return ResponseEntity.ok(holder);
		}

		// to truong
		if (group != -1) {
//			List<Proposal> proposals = proposalRepository.findAll();
			List<UserExtra> userExtras = extraRepository.findAllByEquiqmentGroupId(Long.valueOf(group));
			for (Proposal proposal : proposals) {
				for (UserExtra userExtra : userExtras) {
					if (proposal.getUserExtra().getId().equals(userExtra.getId())) {
						ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
						if (proposal.isStatus()) {
							proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
									currentDetaill.getProgress().getContentTask(),
									proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
									calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
						} else {
							proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
									currentDetaill.getProgress().getContentTask(),
									proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
									calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
						}
					}
				}
			}
//			log.debug("totruong: {}", group);
//			return proposalDatas;
			Page<ProposalData2> holder = new PageImpl<>(proposalDatas, pageable, proposals.getTotalElements());
			return ResponseEntity.ok(holder);
		}

		// thanh vien
//		log.debug("totruong: {}", group);
		UserExtra extra = extraRepository.findById(userService.getUserid()).get();
//		log.debug("extra: {}", extra);
//		List<Proposal> proposals = proposalRepository.findAll();
		for (Proposal proposal : proposals) {
			if (proposal.getUserExtra().getId().equals(extra.getId())) {
				ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
				if (proposal.isStatus()) {
					proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
							currentDetaill.getProgress().getContentTask(),
							proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
							calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
				} else {
					proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
							currentDetaill.getProgress().getContentTask(),
							proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
							calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
				}
			}
		}

//		return proposalDatas;
		Page<ProposalData2> holder = new PageImpl<>(proposalDatas, pageable, proposals.getTotalElements());
		return ResponseEntity.ok(holder);
	}

	@GetMapping("/proposals-data")
	public ResponseEntity<Page<Proposal>> getAllProposalsData(@RequestParam int pageNum, @RequestParam int pageSize,
			@RequestParam(defaultValue = "") String sortBy, Sort.Direction direction,
			@RequestParam(defaultValue = "") String search) {
		
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(direction, sortBy));
		long countDays = 0;

		int group = userService.checkAdmin();

		// super admin
		if (group == 0) {
			Page<Proposal> proposals = proposalRepository.findAll(pageable,search);
			return ResponseEntity.ok(proposals);
		}

		// to truong
		if (group != -1) {
			List<UserExtra> userExtras = extraRepository.findAllByEquiqmentGroupId(Long.valueOf(group));

			for (UserExtra userExtra : userExtras) {
				log.debug("totruong: {}", group);
				Page<Proposal> proposals = proposalRepository.findByUserExtraEquiqmentId(pageable, userExtra.getEquiqmentGroup().getId(),search);
				return ResponseEntity.ok(proposals);
			}
		}

		
		// thanh vien
//		log.debug("totruong: {}", group);
		UserExtra extra = extraRepository.findById(userService.getUserid()).get();
		Page<Proposal> proposals = proposalRepository.findByUserExtraUserId(pageable, extra.getId(),search);
		return ResponseEntity.ok(proposals);
	}
	
	
	@GetMapping("/proposal/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=proposals_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

//        List<Proposal> listProposals = proposalRepository.findAllOrderById();
//        ProposalExcelExporter excelExporter = new ProposalExcelExporter(listProposals);
        
        int group = userService.checkAdmin();

		// super admin
		if (group == 0) {
			List<Proposal> listProposals = proposalRepository.findAllOrderById();
			ProposalExcelExporter excelExporter = new ProposalExcelExporter(listProposals);
			excelExporter.export(response);    
		}

		// to truong
		if (group != -1) {
			List<UserExtra> userExtras = extraRepository.findAllByEquiqmentGroupId(Long.valueOf(group));

			for (UserExtra userExtra : userExtras) {
//				Page<Proposal> proposals = proposalRepository.findByUserExtraUserId(pageable, userExtra.getId(),search);
				List<Proposal> listProposals = proposalRepository.findAllByIdOrderById(userExtra.getId());
				ProposalExcelExporter excelExporter = new ProposalExcelExporter(listProposals);
				excelExporter.export(response);    
			}
		}

		
		// thanh vien
//		log.debug("totruong: {}", group);
		UserExtra extra = extraRepository.findById(userService.getUserid()).get();
		List<Proposal> listProposals = proposalRepository.findAllByIdOrderById(extra.getId());
		ProposalExcelExporter excelExporter = new ProposalExcelExporter(listProposals);		
        excelExporter.export(response);    
    }  
	

	@GetMapping("/proposals-data-table-all")
	public List<ProposalData2> getAllProposalsDataTableAll() {
//		log.debug("REST request to get all Proposals-table");

		long countDays = 0;

		List<ProgressDTO> progressDTOs = progressService.findAll();

		for (ProgressDTO progressDTO : progressDTOs) {
			countDays = countDays + progressDTO.getLimit();
		}

		List<Proposal> proposals = proposalService.findAll();
//		final Page<Proposal> proposals3 = proposalService.findAllPage(pageable);
//		 final Page<Proposal> proposals = new PageImpl<>(proposals3, pageable,proposals3.size());
//		 final Page<Proposal> proposals = new PageImpl<>(proposals1, pageable,proposals1.size());
//	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//	        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		List<ProposalData2> proposalDatas = new ArrayList<>();

//		List<ProgressDTO> progesses = progressService.findAll();

		int group = userService.checkAdmin();

//		log.debug("groupppppppppppppppppppppp: {}", group);

		// super admin
		if (group == 0) {
			for (Proposal proposal : proposals) {
				ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
				if (proposal.isStatus()) {
					proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
							currentDetaill.getProgress().getContentTask(),
							proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
							calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
				} else {
					proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
							currentDetaill.getProgress().getContentTask(),
							proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
							calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
				}

			}

//			 final Page<ProposalData2> page = new PageImpl<ProposalData2>(proposalDatas, pageable,proposalDatas.size());
//			 log.debug("pageeee: {}", page);
//			 HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//		     return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

			return proposalDatas;
		}

		// to truong
		if (group != -1) {
			List<UserExtra> userExtras = extraRepository.findAllByEquiqmentGroupId(Long.valueOf(group));
			for (Proposal proposal : proposals) {
				for (UserExtra userExtra : userExtras) {
					if (proposal.getUserExtra().getId().equals(userExtra.getId())) {
						ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
						if (proposal.isStatus()) {
							proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
									currentDetaill.getProgress().getContentTask(),
									proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
									calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
						} else {
							proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
									currentDetaill.getProgress().getContentTask(),
									proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
									calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
						}
					}
				}

			}
//			log.debug("totruong: {}", group);
			return proposalDatas;
//			 final Page<ProposalData2> page = new PageImpl<ProposalData2>(proposalDatas, pageable,proposalDatas.size());
//		        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//		        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

		}

		// thanh vien
//		log.debug("totruong: {}", group);
		UserExtra extra = extraRepository.findById(userService.getUserid()).get();
//		log.debug("extra: {}", extra);
		for (Proposal proposal : proposals) {
			if (proposal.getUserExtra().getId().equals(extra.getId())) {
				ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
				if (proposal.isStatus()) {
					proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
							currentDetaill.getProgress().getContentTask(),
							proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
							calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
				} else {
					proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
							currentDetaill.getProgress().getContentTask(),
							proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
							calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
				}
			}
		}

		return proposalDatas;
	}

	@GetMapping("/proposals-data-table-alert/{number}")
	public List<ProposalData2> getAllProposalsDataTableAlert(@PathVariable Long number) {
//		log.debug("REST request to get all Proposals-table");

		long countDays = 0;

		List<ProgressDTO> progressDTOs = progressService.findAll();

		for (ProgressDTO progressDTO : progressDTOs) {
			countDays = countDays + progressDTO.getLimit();
		}

		List<Proposal> proposals = proposalService.findStatus(false);
		List<ProposalData2> proposalDatas = new ArrayList<>();

		int group = userService.checkAdmin();

//		log.debug("groupppppppppppppppppppppp: {}", group);

		// super admin

		if (group == 0) {
			for (Proposal proposal : proposals) {
				ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
				List<ProgessDetaillDTO> progesses = progessDetaillService.findAllDTOByProposalId(proposal.getId());
//				log.debug("idddddddd: {}", proposal.isStatus());
//				log.debug("progessessssssss: {}", progesses.get(2).getId());
				try {
//					log.debug("call ngày: {}", calRemainingDate(proposal.getStartDate(),progesses.get(2).getEndDate(), ChronoUnit.DAYS));
					if (progesses.get(1).getEndDate() == null && progesses.get(2).getEndDate() == null
							&& progesses.get(3).getEndDate() == null && progesses.get(4).getEndDate() == null
							&& progesses.get(5).getEndDate() == null && progesses.get(6).getEndDate() == null
							&& progesses.get(7).getEndDate() == null && progesses.get(8).getEndDate() == null
							&& calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(),
									ChronoUnit.DAYS) >= number) {
						if (proposal.isStatus()) {
							proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
									currentDetaill.getProgress().getContentTask(),
									proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
									calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
						} else {
							proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
									currentDetaill.getProgress().getContentTask(),
									proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
									calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
						}
					} else if (progesses.get(2).getEndDate() == null && progesses.get(3).getEndDate() == null
							&& progesses.get(4).getEndDate() == null && progesses.get(5).getEndDate() == null
							&& progesses.get(6).getEndDate() == null && progesses.get(7).getEndDate() == null
							&& progesses.get(8).getEndDate() == null && calRemainingDate(ZonedDateTime.now(),
									progesses.get(1).getEndDate(), ChronoUnit.DAYS) >= number) {
						if (proposal.isStatus()) {
							proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
									currentDetaill.getProgress().getContentTask(),
									proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
									calRemainingDate(proposal.getEndDate(), proposal.getStartDate(), ChronoUnit.DAYS)));
						} else {
							proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
									currentDetaill.getProgress().getContentTask(),
									proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
									calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS)));
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
//			log.debug("proposalDatasssssss1: {}", proposalDatas);
			return proposalDatas;
		}

		// to truong
		if (group != -1) {
			List<UserExtra> userExtras = extraRepository.findAllByEquiqmentGroupId(Long.valueOf(group));
			for (Proposal proposal : proposals) {
				for (UserExtra userExtra : userExtras) {
					log.debug("IDDDDDDDDDDDDDDDDD: {}", userExtra.getId());
					if (proposal.getUserExtra().getId().equals(userExtra.getId())) {
						ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
						List<ProgessDetaillDTO> progesses = progessDetaillService
								.findAllDTOByProposalId(proposal.getId());
//						log.debug("idddddddd: {}", proposal.getId());

						try {
							if (progesses.get(1).getEndDate() == null && progesses.get(2).getEndDate() == null
									&& progesses.get(3).getEndDate() == null && progesses.get(4).getEndDate() == null
									&& progesses.get(5).getEndDate() == null && progesses.get(6).getEndDate() == null
									&& progesses.get(7).getEndDate() == null && progesses.get(8).getEndDate() == null
									&& calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(),
											ChronoUnit.DAYS) >= number) {
								if (proposal.isStatus()) {
									proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
											currentDetaill.getProgress().getContentTask(),
											proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
											calRemainingDate(proposal.getEndDate(), proposal.getStartDate(),
													ChronoUnit.DAYS)));
								} else {
									proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
											currentDetaill.getProgress().getContentTask(),
											proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
											calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(),
													ChronoUnit.DAYS)));
								}
							} else if (progesses.get(2).getEndDate() == null && progesses.get(3).getEndDate() == null
									&& progesses.get(4).getEndDate() == null && progesses.get(5).getEndDate() == null
									&& progesses.get(6).getEndDate() == null && progesses.get(7).getEndDate() == null
									&& progesses.get(8).getEndDate() == null && calRemainingDate(ZonedDateTime.now(),
											progesses.get(1).getEndDate(), ChronoUnit.DAYS) >= number) {
								if (proposal.isStatus()) {
									proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
											currentDetaill.getProgress().getContentTask(),
											proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
											calRemainingDate(proposal.getEndDate(), proposal.getStartDate(),
													ChronoUnit.DAYS)));
								} else {
									proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
											currentDetaill.getProgress().getContentTask(),
											proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
											calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(),
													ChronoUnit.DAYS)));
								}
							}

						} catch (Exception e) {
							// TODO: handle exception
						}

					}

				}
//			log.debug("totruong: {}", group);
//				log.debug("proposalDatasssssss: {}", proposalDatas);
				return proposalDatas;
			}
		}
			// thanh vien
			log.debug("totruong: {}", group);

			UserExtra extra = extraRepository.findById(userService.getUserid()).get();
//			log.debug("extra: {}", extra);
//			log.debug("IDDDDDDDDDDDDDDDDD: {}", extra.getId());
			for (Proposal proposal : proposals) {
				if (proposal.getUserExtra().getId().equals(extra.getId())) {
					ProgessDetaill currentDetaill = getCurrentProgessDetaill(proposal.getId());
					List<ProgessDetaillDTO> progesses = progessDetaillService.findAllDTOByProposalId(proposal.getId());
					log.debug("idddddddd: {}", extra.getId());
//					log.debug("progessessssssss: {}", progesses.get(2));
					try {
						if (progesses.get(1).getEndDate() == null && progesses.get(2).getEndDate() == null
								&& progesses.get(3).getEndDate() == null && progesses.get(4).getEndDate() == null
								&& progesses.get(5).getEndDate() == null && progesses.get(6).getEndDate() == null
								&& progesses.get(7).getEndDate() == null && progesses.get(8).getEndDate() == null
								&& calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(),
										ChronoUnit.DAYS) >= number) {
							if (proposal.isStatus()) {
								proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
										currentDetaill.getProgress().getContentTask(),
										proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
										calRemainingDate(proposal.getEndDate(), proposal.getStartDate(),
												ChronoUnit.DAYS)));
							} else {
								proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
										currentDetaill.getProgress().getContentTask(),
										proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
										calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(),
												ChronoUnit.DAYS)));
							}
						} else if (progesses.get(2).getEndDate() == null && progesses.get(3).getEndDate() == null
								&& progesses.get(4).getEndDate() == null && progesses.get(5).getEndDate() == null
								&& progesses.get(6).getEndDate() == null && progesses.get(7).getEndDate() == null
								&& progesses.get(8).getEndDate() == null && calRemainingDate(ZonedDateTime.now(),
										progesses.get(1).getEndDate(), ChronoUnit.DAYS) >= number) {
							if (proposal.isStatus()) {
								proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
										currentDetaill.getProgress().getContentTask(),
										proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
										calRemainingDate(proposal.getEndDate(), proposal.getStartDate(),
												ChronoUnit.DAYS)));
							} else {
								proposalDatas.add(new ProposalData2(proposal, currentDetaill.getId(),
										currentDetaill.getProgress().getContentTask(),
										proposal.getStartDate().plusDays(countDays + proposal.getAdditionalDate()),
										calRemainingDate(ZonedDateTime.now(), proposal.getStartDate(),
												ChronoUnit.DAYS)));
							}

						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		
//		log.debug("proposalDatassssss11s: {}", proposalDatas);
		return proposalDatas;

	}

	@GetMapping("/get-All-ProgressDetail-By-ProposalId")
	public List<ProgressStage> getAllProgressDetailByProposalId(@RequestParam Long id) {
//		log.debug("REST request to get-All-ProgressDetail-By-ProposalId");
		List<ProgessDetaill> progessDetaills = progessDetaillService.findAllByProposalId(id);

		List<ProgressStage> progressStages = new ArrayList<>();

//		Progress startProgress = new Progress();
//		startProgress.setContentTask("Tạo mới");
//		progressStages.add(new ProgressStage(Long.valueOf(0), null, null, null, startProgress,"Khởi tạo"));

		for (ProgessDetaill progessDetaill : progessDetaills) {
			progressStages.add(new ProgressStage(progessDetaill.getId(), progessDetaill.getStartDate(),
					progessDetaill.getEndDate(), progessDetaill.getLastModifiedBy(), progessDetaill.getProgress(),
					progessDetaill.getNote(), progessDetaill.getNoteAdmin()));
		}
//		Progress completeProgress = new Progress();
//		completeProgress.setContentTask("Hoàn thành");
//		
//		
//		progressStages.add(new ProgressStage(Long.valueOf(8), null, proposalService.findOne(id).get().getEndDate(), null, completeProgress, "hoàn thành"));

		return progressStages;
	}

	
	@GetMapping("/get-All-Data-By-Status/{status}")
	public  ResponseEntity<Page<Proposal>> getAllDataByStatus(@RequestParam int pageNum, @RequestParam int pageSize,
			@RequestParam(defaultValue = "") String sortBy, Sort.Direction direction,
			@RequestParam(defaultValue = "") String search,@RequestParam Boolean status) {
//		log.debug("REST request to get-All-Data-By-Status");
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(direction, sortBy));
		long countDays = 0;

		int group = userService.checkAdmin();

		// super admin
		if (group == 0) {
			Page<Proposal> proposals = proposalRepository.findStatus(pageable,search,status);
			return ResponseEntity.ok(proposals);
		}

		// to truong
		if (group != -1) {
			List<UserExtra> userExtras = extraRepository.findAllByEquiqmentGroupId(Long.valueOf(group));

			for (UserExtra userExtra : userExtras) {
				Page<Proposal> proposals = proposalRepository.findByUserExtraEquiqmentIdStatus(pageable, userExtra.getEquiqmentGroup().getId(),search,status);
				return ResponseEntity.ok(proposals);
			}
		}

		
		// thanh vien
//		log.debug("totruong: {}", group);
		UserExtra extra = extraRepository.findById(userService.getUserid()).get();
		Page<Proposal> proposals = proposalRepository.findByUserExtraUserIdStatus(pageable, extra.getId(),search,status);
		return ResponseEntity.ok(proposals);
	}

	@PutMapping("/update-All-ProgressDetail-By-ProposalId")
	public List<ProgessDetaillDTO> updateAllProgressDetailByProposalId(@RequestBody List<ProgressStage> progressStages,
			@RequestParam Long proposalId) {
//		log.debug("REST request to update-All-ProgressDetail-By-ProposalId");

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

		List<ProgressDTO> progressDTOs = progressService.findAll();

		for (ProgressStage progressStage : progressStages) {
//			if(!progressStage.getId().equals(new Long(0)) && !progressStage.getId().equals(new Long(8))) {
			ProgessDetaillDTO detaillDTO = new ProgessDetaillDTO();
			detaillDTO.setId(progressStage.getId());
			detaillDTO.setProgressId(progressStage.getProgress().getId());
			detaillDTO.setProposalId(proposalId);
			detaillDTO.setNote(progressStage.getNote());
			detaillDTO.setNoteAdmin(progressStage.getNoteAdmin());
			detaillDTO.setStartDate(progressStage.getTimeStart());
			detaillDTO.setEndDate(progressStage.getTimeEnd());
			detaillDTOs.add(detaillDTO);
			progessDetaillService.save(detaillDTO);
//			}
		}

		for (int i = progressStages.size() - 1; i > 0; i--) {
			if (progressStages.get(i).getTimeEnd() != null) {
				ProposalDTO proposalDTO = proposalService.findOne(proposalId).get();
				proposalDTO.setEndDate(progressStages.get(i).getTimeEnd());
				proposalDTO.setNote(progressStages.get(i).getNote());

				Integer currentProgressId = (int) (long) progressStages.get(i).getProgress().getId();
				proposalDTO.setCurrentProgressId(currentProgressId);

				proposalDTO.setCurrentProgressName(progressStages.get(i).getProgress().getContentTask());
				proposalService.save(proposalDTO);
//				log.debug("log get getProgress:{}", progressStages.get(i).getProgress().getContentTask());
				break;
			}
		}

		if (progressStages.get(progressStages.size() - 1).getTimeEnd() != null) {
			ProposalDTO proposalDTO = proposalService.findOne(proposalId).get();
//			proposalDTO.setEndDate(progressStages.get(progressStages.size() - 1).getTimeEnd());
			proposalDTO.setStatus(true);
			proposalDTO.setRemainingDate(
					calRemainingDate(proposalDTO.getEndDate(), proposalDTO.getStartDate(), ChronoUnit.DAYS));
			proposalService.save(proposalDTO);
		}

		if (progressStages.get(progressStages.size() - 3).getTimeEnd() != null
				|| progressStages.get(progressStages.size() - 2).getTimeEnd() != null
				|| progressStages.get(progressStages.size() - 1).getTimeEnd() != null) {
			ProposalDTO proposalDTO = proposalService.findOne(proposalId).get();
//			proposalDTO.setEndDate(progressStages.get(progressStages.size() - 1).getTimeEnd());
			proposalDTO.setStatusChart(true);

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
//		log.debug("REST request to get Proposal : {}", id);
		Optional<ProposalDTO> proposalDTO = proposalService.findOne(id);
		return ResponseUtil.wrapOrNotFound(proposalDTO);
	}

	@GetMapping("/proposals/{statusChart}&{one_date}/{two_date}")
	public List<Proposal> getProposalStatusBetween(@RequestParam Boolean statusChart,
			@PathVariable(value = "one_date") ZonedDateTime fromDate,
			@PathVariable(value = "two_date") ZonedDateTime toDate) {
		// log.debug("REST request to get Proposal : {}", id);
		// List<Proposal> proposalData = proposalService.findStatusDate(status,
		// one_date, two_date);
//		for (Proposal proposal : proposalData) {
//			if(proposal.getEndDate()==null) {
//				
//			}
//		}
		return proposalService.findStatusDate(statusChart, fromDate, toDate);
	}

	/**
	 * {@code DELETE  /proposals/:id} : delete the "id" proposal.
	 *
	 * @param id the id of the proposalDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/proposals/{id}")
	public ResponseEntity<Void> deleteProposal(@PathVariable Long id) {
//		log.debug("REST request to delete Proposal : {}", id);

		List<ProgessDetaill> progessDetaills = progessDetaillService.findAllByProposalId(id);

		for (ProgessDetaill detaill : progessDetaills) {
			progessDetaillService.delete(detaill.getId());
		}

		proposalService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	private Integer calRemainingDate(ZonedDateTime currentDate, ZonedDateTime createDateProposal, ChronoUnit unit) {
		return (int) (long) unit.between(createDateProposal, currentDate);
	}
}
