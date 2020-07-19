package com.thupx.tms.web.rest;

import com.thupx.tms.service.EquiqmentGroupService;
import com.thupx.tms.web.rest.errors.BadRequestAlertException;
import com.thupx.tms.service.dto.EquiqmentGroupDTO;

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
 * REST controller for managing {@link com.thupx.tms.domain.EquiqmentGroup}.
 */
@RestController
@RequestMapping("/api")
public class EquiqmentGroupResource {

    private final Logger log = LoggerFactory.getLogger(EquiqmentGroupResource.class);

    private static final String ENTITY_NAME = "equiqmentGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquiqmentGroupService equiqmentGroupService;

    public EquiqmentGroupResource(EquiqmentGroupService equiqmentGroupService) {
        this.equiqmentGroupService = equiqmentGroupService;
    }

    /**
     * {@code POST  /equiqment-groups} : Create a new equiqmentGroup.
     *
     * @param equiqmentGroupDTO the equiqmentGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equiqmentGroupDTO, or with status {@code 400 (Bad Request)} if the equiqmentGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equiqment-groups")
    public ResponseEntity<EquiqmentGroupDTO> createEquiqmentGroup(@RequestBody EquiqmentGroupDTO equiqmentGroupDTO) throws URISyntaxException {
        log.debug("REST request to save EquiqmentGroup : {}", equiqmentGroupDTO);
        if (equiqmentGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new equiqmentGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquiqmentGroupDTO result = equiqmentGroupService.save(equiqmentGroupDTO);
        return ResponseEntity.created(new URI("/api/equiqment-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equiqment-groups} : Updates an existing equiqmentGroup.
     *
     * @param equiqmentGroupDTO the equiqmentGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equiqmentGroupDTO,
     * or with status {@code 400 (Bad Request)} if the equiqmentGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equiqmentGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equiqment-groups")
    public ResponseEntity<EquiqmentGroupDTO> updateEquiqmentGroup(@RequestBody EquiqmentGroupDTO equiqmentGroupDTO) throws URISyntaxException {
        log.debug("REST request to update EquiqmentGroup : {}", equiqmentGroupDTO);
        if (equiqmentGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EquiqmentGroupDTO result = equiqmentGroupService.save(equiqmentGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equiqmentGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equiqment-groups} : get all the equiqmentGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equiqmentGroups in body.
     */
    @GetMapping("/equiqment-groups")
    public List<EquiqmentGroupDTO> getAllEquiqmentGroups() {
        log.debug("REST request to get all EquiqmentGroups");
        return equiqmentGroupService.findAll();
    }

    /**
     * {@code GET  /equiqment-groups/:id} : get the "id" equiqmentGroup.
     *
     * @param id the id of the equiqmentGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equiqmentGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equiqment-groups/{id}")
    public ResponseEntity<EquiqmentGroupDTO> getEquiqmentGroup(@PathVariable Long id) {
        log.debug("REST request to get EquiqmentGroup : {}", id);
        Optional<EquiqmentGroupDTO> equiqmentGroupDTO = equiqmentGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equiqmentGroupDTO);
    }

    /**
     * {@code DELETE  /equiqment-groups/:id} : delete the "id" equiqmentGroup.
     *
     * @param id the id of the equiqmentGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equiqment-groups/{id}")
    public ResponseEntity<Void> deleteEquiqmentGroup(@PathVariable Long id) {
        log.debug("REST request to delete EquiqmentGroup : {}", id);

        equiqmentGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
