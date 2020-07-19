package com.thupx.tms.web.rest;

import com.thupx.tms.service.ProgessDetaillService;
import com.thupx.tms.service.ProposalService;
import com.thupx.tms.web.rest.errors.BadRequestAlertException;
import com.thupx.tms.service.dto.ProgessDetaillDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.thupx.tms.domain.ProgessDetaill}.
 */
@RestController
@RequestMapping("/api")
public class ProgessDetaillResource {

    private final Logger log = LoggerFactory.getLogger(ProgessDetaillResource.class);

    private static final String ENTITY_NAME = "progessDetaill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgessDetaillService progessDetaillService;

    public ProgessDetaillResource(ProgessDetaillService progessDetaillService) {
        this.progessDetaillService = progessDetaillService;
    }

    /**
     * {@code POST  /progess-detaills} : Create a new progessDetaill.
     *
     * @param progessDetaillDTO the progessDetaillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new progessDetaillDTO, or with status {@code 400 (Bad Request)} if the progessDetaill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/progess-detaills")
    public ResponseEntity<ProgessDetaillDTO> createProgessDetaill(@RequestBody ProgessDetaillDTO progessDetaillDTO) throws URISyntaxException {
        log.debug("REST request to save ProgessDetaill : {}", progessDetaillDTO);
        if (progessDetaillDTO.getId() != null) {
            throw new BadRequestAlertException("A new progessDetaill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgessDetaillDTO result = progessDetaillService.save(progessDetaillDTO);
        return ResponseEntity.created(new URI("/api/progess-detaills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /progess-detaills} : Updates an existing progessDetaill.
     *
     * @param progessDetaillDTO the progessDetaillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated progessDetaillDTO,
     * or with status {@code 400 (Bad Request)} if the progessDetaillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the progessDetaillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/progess-detaills")
    public ResponseEntity<ProgessDetaillDTO> updateProgessDetaill(@RequestBody ProgessDetaillDTO progessDetaillDTO) throws URISyntaxException {
        log.debug("REST request to update ProgessDetaill : {}", progessDetaillDTO);
        if (progessDetaillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProgessDetaillDTO dto = progessDetaillService.findOne(progessDetaillDTO.getId()).get();
        
        progessDetaillDTO.setProgressId(dto.getProgressId());
        
        progessDetaillDTO.setProposalId(dto.getProposalId());
        
        ProgessDetaillDTO result = progessDetaillService.save(progessDetaillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, progessDetaillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /progess-detaills} : get all the progessDetaills.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of progessDetaills in body.
     */
    @GetMapping("/progess-detaills")
    public List<ProgessDetaillDTO> getAllProgessDetaills() {
        log.debug("REST request to get all ProgessDetaills");
        return progessDetaillService.findAll();
    }

    /**
     * {@code GET  /progess-detaills/:id} : get the "id" progessDetaill.
     *
     * @param id the id of the progessDetaillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the progessDetaillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/progess-detaills/{id}")
    public ResponseEntity<ProgessDetaillDTO> getProgessDetaill(@PathVariable Long id) {
        log.debug("REST request to get ProgessDetaill : {}", id);
        Optional<ProgessDetaillDTO> progessDetaillDTO = progessDetaillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(progessDetaillDTO);
    }

    /**
     * {@code DELETE  /progess-detaills/:id} : delete the "id" progessDetaill.
     *
     * @param id the id of the progessDetaillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/progess-detaills/{id}")
    public ResponseEntity<Void> deleteProgessDetaill(@PathVariable Long id) {
        log.debug("REST request to delete ProgessDetaill : {}", id);

        progessDetaillService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
