package com.thupx.tms.web.rest;

import com.thupx.tms.TaskManagementSoftwareServerApp;
import com.thupx.tms.domain.HospitalDepartment;
import com.thupx.tms.repository.HospitalDepartmentRepository;
import com.thupx.tms.service.HospitalDepartmentService;
import com.thupx.tms.service.dto.HospitalDepartmentDTO;
import com.thupx.tms.service.mapper.HospitalDepartmentMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HospitalDepartmentResource} REST controller.
 */
@SpringBootTest(classes = TaskManagementSoftwareServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HospitalDepartmentResourceIT {

    private static final String DEFAULT_HOSPITAL_DEPARTMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOSPITAL_DEPARTMENT_NAME = "BBBBBBBBBB";

    @Autowired
    private HospitalDepartmentRepository hospitalDepartmentRepository;

    @Autowired
    private HospitalDepartmentMapper hospitalDepartmentMapper;

    @Autowired
    private HospitalDepartmentService hospitalDepartmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHospitalDepartmentMockMvc;

    private HospitalDepartment hospitalDepartment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HospitalDepartment createEntity(EntityManager em) {
        HospitalDepartment hospitalDepartment = new HospitalDepartment()
            .hospitalDepartmentName(DEFAULT_HOSPITAL_DEPARTMENT_NAME);
        return hospitalDepartment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HospitalDepartment createUpdatedEntity(EntityManager em) {
        HospitalDepartment hospitalDepartment = new HospitalDepartment()
            .hospitalDepartmentName(UPDATED_HOSPITAL_DEPARTMENT_NAME);
        return hospitalDepartment;
    }

    @BeforeEach
    public void initTest() {
        hospitalDepartment = createEntity(em);
    }

    @Test
    @Transactional
    public void createHospitalDepartment() throws Exception {
        int databaseSizeBeforeCreate = hospitalDepartmentRepository.findAll().size();
        // Create the HospitalDepartment
        HospitalDepartmentDTO hospitalDepartmentDTO = hospitalDepartmentMapper.toDto(hospitalDepartment);
        restHospitalDepartmentMockMvc.perform(post("/api/hospital-departments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalDepartmentDTO)))
            .andExpect(status().isCreated());

        // Validate the HospitalDepartment in the database
        List<HospitalDepartment> hospitalDepartmentList = hospitalDepartmentRepository.findAll();
        assertThat(hospitalDepartmentList).hasSize(databaseSizeBeforeCreate + 1);
        HospitalDepartment testHospitalDepartment = hospitalDepartmentList.get(hospitalDepartmentList.size() - 1);
        assertThat(testHospitalDepartment.getHospitalDepartmentName()).isEqualTo(DEFAULT_HOSPITAL_DEPARTMENT_NAME);
    }

    @Test
    @Transactional
    public void createHospitalDepartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hospitalDepartmentRepository.findAll().size();

        // Create the HospitalDepartment with an existing ID
        hospitalDepartment.setId(1L);
        HospitalDepartmentDTO hospitalDepartmentDTO = hospitalDepartmentMapper.toDto(hospitalDepartment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospitalDepartmentMockMvc.perform(post("/api/hospital-departments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalDepartmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HospitalDepartment in the database
        List<HospitalDepartment> hospitalDepartmentList = hospitalDepartmentRepository.findAll();
        assertThat(hospitalDepartmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHospitalDepartments() throws Exception {
        // Initialize the database
        hospitalDepartmentRepository.saveAndFlush(hospitalDepartment);

        // Get all the hospitalDepartmentList
        restHospitalDepartmentMockMvc.perform(get("/api/hospital-departments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospitalDepartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].hospitalDepartmentName").value(hasItem(DEFAULT_HOSPITAL_DEPARTMENT_NAME)));
    }
    
    @Test
    @Transactional
    public void getHospitalDepartment() throws Exception {
        // Initialize the database
        hospitalDepartmentRepository.saveAndFlush(hospitalDepartment);

        // Get the hospitalDepartment
        restHospitalDepartmentMockMvc.perform(get("/api/hospital-departments/{id}", hospitalDepartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hospitalDepartment.getId().intValue()))
            .andExpect(jsonPath("$.hospitalDepartmentName").value(DEFAULT_HOSPITAL_DEPARTMENT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingHospitalDepartment() throws Exception {
        // Get the hospitalDepartment
        restHospitalDepartmentMockMvc.perform(get("/api/hospital-departments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHospitalDepartment() throws Exception {
        // Initialize the database
        hospitalDepartmentRepository.saveAndFlush(hospitalDepartment);

        int databaseSizeBeforeUpdate = hospitalDepartmentRepository.findAll().size();

        // Update the hospitalDepartment
        HospitalDepartment updatedHospitalDepartment = hospitalDepartmentRepository.findById(hospitalDepartment.getId()).get();
        // Disconnect from session so that the updates on updatedHospitalDepartment are not directly saved in db
        em.detach(updatedHospitalDepartment);
        updatedHospitalDepartment
            .hospitalDepartmentName(UPDATED_HOSPITAL_DEPARTMENT_NAME);
        HospitalDepartmentDTO hospitalDepartmentDTO = hospitalDepartmentMapper.toDto(updatedHospitalDepartment);

        restHospitalDepartmentMockMvc.perform(put("/api/hospital-departments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalDepartmentDTO)))
            .andExpect(status().isOk());

        // Validate the HospitalDepartment in the database
        List<HospitalDepartment> hospitalDepartmentList = hospitalDepartmentRepository.findAll();
        assertThat(hospitalDepartmentList).hasSize(databaseSizeBeforeUpdate);
        HospitalDepartment testHospitalDepartment = hospitalDepartmentList.get(hospitalDepartmentList.size() - 1);
        assertThat(testHospitalDepartment.getHospitalDepartmentName()).isEqualTo(UPDATED_HOSPITAL_DEPARTMENT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingHospitalDepartment() throws Exception {
        int databaseSizeBeforeUpdate = hospitalDepartmentRepository.findAll().size();

        // Create the HospitalDepartment
        HospitalDepartmentDTO hospitalDepartmentDTO = hospitalDepartmentMapper.toDto(hospitalDepartment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalDepartmentMockMvc.perform(put("/api/hospital-departments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalDepartmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HospitalDepartment in the database
        List<HospitalDepartment> hospitalDepartmentList = hospitalDepartmentRepository.findAll();
        assertThat(hospitalDepartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHospitalDepartment() throws Exception {
        // Initialize the database
        hospitalDepartmentRepository.saveAndFlush(hospitalDepartment);

        int databaseSizeBeforeDelete = hospitalDepartmentRepository.findAll().size();

        // Delete the hospitalDepartment
        restHospitalDepartmentMockMvc.perform(delete("/api/hospital-departments/{id}", hospitalDepartment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HospitalDepartment> hospitalDepartmentList = hospitalDepartmentRepository.findAll();
        assertThat(hospitalDepartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
