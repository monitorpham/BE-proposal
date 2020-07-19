package com.thupx.tms.web.rest;

import com.thupx.tms.service.HospitalDepartmentService;
import com.thupx.tms.web.rest.errors.BadRequestAlertException;
import com.thupx.tms.service.dto.HospitalDepartmentDTO;

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
 * REST controller for managing {@link com.thupx.tms.domain.HospitalDepartment}.
 */
@RestController
@RequestMapping("/api")
public class HospitalDepartmentResource {

    private final Logger log = LoggerFactory.getLogger(HospitalDepartmentResource.class);

    private static final String ENTITY_NAME = "hospitalDepartment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HospitalDepartmentService hospitalDepartmentService;

    public HospitalDepartmentResource(HospitalDepartmentService hospitalDepartmentService) {
        this.hospitalDepartmentService = hospitalDepartmentService;
    }

    /**
     * {@code POST  /hospital-departments} : Create a new hospitalDepartment.
     *
     * @param hospitalDepartmentDTO the hospitalDepartmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hospitalDepartmentDTO, or with status {@code 400 (Bad Request)} if the hospitalDepartment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hospital-departments")
    public ResponseEntity<HospitalDepartmentDTO> createHospitalDepartment(@RequestBody HospitalDepartmentDTO hospitalDepartmentDTO) throws URISyntaxException {
        log.debug("REST request to save HospitalDepartment : {}", hospitalDepartmentDTO);
        if (hospitalDepartmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new hospitalDepartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HospitalDepartmentDTO result = hospitalDepartmentService.save(hospitalDepartmentDTO);
        return ResponseEntity.created(new URI("/api/hospital-departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hospital-departments} : Updates an existing hospitalDepartment.
     *
     * @param hospitalDepartmentDTO the hospitalDepartmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalDepartmentDTO,
     * or with status {@code 400 (Bad Request)} if the hospitalDepartmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hospitalDepartmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hospital-departments")
    public ResponseEntity<HospitalDepartmentDTO> updateHospitalDepartment(@RequestBody HospitalDepartmentDTO hospitalDepartmentDTO) throws URISyntaxException {
        log.debug("REST request to update HospitalDepartment : {}", hospitalDepartmentDTO);
        if (hospitalDepartmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HospitalDepartmentDTO result = hospitalDepartmentService.save(hospitalDepartmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hospitalDepartmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hospital-departments} : get all the hospitalDepartments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hospitalDepartments in body.
     */
    @GetMapping("/hospital-departments")
    public List<HospitalDepartmentDTO> getAllHospitalDepartments() {
        log.debug("REST request to get all HospitalDepartments");
        return hospitalDepartmentService.findAll();
    }

    /**
     * {@code GET  /hospital-departments/:id} : get the "id" hospitalDepartment.
     *
     * @param id the id of the hospitalDepartmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hospitalDepartmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hospital-departments/{id}")
    public ResponseEntity<HospitalDepartmentDTO> getHospitalDepartment(@PathVariable Long id) {
        log.debug("REST request to get HospitalDepartment : {}", id);
        Optional<HospitalDepartmentDTO> hospitalDepartmentDTO = hospitalDepartmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hospitalDepartmentDTO);
    }

    /**
     * {@code DELETE  /hospital-departments/:id} : delete the "id" hospitalDepartment.
     *
     * @param id the id of the hospitalDepartmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hospital-departments/{id}")
    public ResponseEntity<Void> deleteHospitalDepartment(@PathVariable Long id) {
        log.debug("REST request to delete HospitalDepartment : {}", id);

        hospitalDepartmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
