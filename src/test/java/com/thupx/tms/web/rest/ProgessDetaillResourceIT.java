package com.thupx.tms.web.rest;

import com.thupx.tms.TaskManagementSoftwareServerApp;
import com.thupx.tms.domain.ProgessDetaill;
import com.thupx.tms.repository.ProgessDetaillRepository;
import com.thupx.tms.service.ProgessDetaillService;
import com.thupx.tms.service.dto.ProgessDetaillDTO;
import com.thupx.tms.service.mapper.ProgessDetaillMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.thupx.tms.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProgessDetaillResource} REST controller.
 */
@SpringBootTest(classes = TaskManagementSoftwareServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProgessDetaillResourceIT {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private ProgessDetaillRepository progessDetaillRepository;

    @Autowired
    private ProgessDetaillMapper progessDetaillMapper;

    @Autowired
    private ProgessDetaillService progessDetaillService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgessDetaillMockMvc;

    private ProgessDetaill progessDetaill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgessDetaill createEntity(EntityManager em) {
        ProgessDetaill progessDetaill = new ProgessDetaill()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .note(DEFAULT_NOTE);
        return progessDetaill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgessDetaill createUpdatedEntity(EntityManager em) {
        ProgessDetaill progessDetaill = new ProgessDetaill()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .note(UPDATED_NOTE);
        return progessDetaill;
    }

    @BeforeEach
    public void initTest() {
        progessDetaill = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgessDetaill() throws Exception {
        int databaseSizeBeforeCreate = progessDetaillRepository.findAll().size();
        // Create the ProgessDetaill
        ProgessDetaillDTO progessDetaillDTO = progessDetaillMapper.toDto(progessDetaill);
        restProgessDetaillMockMvc.perform(post("/api/progess-detaills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(progessDetaillDTO)))
            .andExpect(status().isCreated());

        // Validate the ProgessDetaill in the database
        List<ProgessDetaill> progessDetaillList = progessDetaillRepository.findAll();
        assertThat(progessDetaillList).hasSize(databaseSizeBeforeCreate + 1);
        ProgessDetaill testProgessDetaill = progessDetaillList.get(progessDetaillList.size() - 1);
        assertThat(testProgessDetaill.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProgessDetaill.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProgessDetaill.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createProgessDetaillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = progessDetaillRepository.findAll().size();

        // Create the ProgessDetaill with an existing ID
        progessDetaill.setId(1L);
        ProgessDetaillDTO progessDetaillDTO = progessDetaillMapper.toDto(progessDetaill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgessDetaillMockMvc.perform(post("/api/progess-detaills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(progessDetaillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProgessDetaill in the database
        List<ProgessDetaill> progessDetaillList = progessDetaillRepository.findAll();
        assertThat(progessDetaillList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProgessDetaills() throws Exception {
        // Initialize the database
        progessDetaillRepository.saveAndFlush(progessDetaill);

        // Get all the progessDetaillList
        restProgessDetaillMockMvc.perform(get("/api/progess-detaills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(progessDetaill.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getProgessDetaill() throws Exception {
        // Initialize the database
        progessDetaillRepository.saveAndFlush(progessDetaill);

        // Get the progessDetaill
        restProgessDetaillMockMvc.perform(get("/api/progess-detaills/{id}", progessDetaill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(progessDetaill.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingProgessDetaill() throws Exception {
        // Get the progessDetaill
        restProgessDetaillMockMvc.perform(get("/api/progess-detaills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgessDetaill() throws Exception {
        // Initialize the database
        progessDetaillRepository.saveAndFlush(progessDetaill);

        int databaseSizeBeforeUpdate = progessDetaillRepository.findAll().size();

        // Update the progessDetaill
        ProgessDetaill updatedProgessDetaill = progessDetaillRepository.findById(progessDetaill.getId()).get();
        // Disconnect from session so that the updates on updatedProgessDetaill are not directly saved in db
        em.detach(updatedProgessDetaill);
        updatedProgessDetaill
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .note(UPDATED_NOTE);
        ProgessDetaillDTO progessDetaillDTO = progessDetaillMapper.toDto(updatedProgessDetaill);

        restProgessDetaillMockMvc.perform(put("/api/progess-detaills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(progessDetaillDTO)))
            .andExpect(status().isOk());

        // Validate the ProgessDetaill in the database
        List<ProgessDetaill> progessDetaillList = progessDetaillRepository.findAll();
        assertThat(progessDetaillList).hasSize(databaseSizeBeforeUpdate);
        ProgessDetaill testProgessDetaill = progessDetaillList.get(progessDetaillList.size() - 1);
        assertThat(testProgessDetaill.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProgessDetaill.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProgessDetaill.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingProgessDetaill() throws Exception {
        int databaseSizeBeforeUpdate = progessDetaillRepository.findAll().size();

        // Create the ProgessDetaill
        ProgessDetaillDTO progessDetaillDTO = progessDetaillMapper.toDto(progessDetaill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgessDetaillMockMvc.perform(put("/api/progess-detaills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(progessDetaillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProgessDetaill in the database
        List<ProgessDetaill> progessDetaillList = progessDetaillRepository.findAll();
        assertThat(progessDetaillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgessDetaill() throws Exception {
        // Initialize the database
        progessDetaillRepository.saveAndFlush(progessDetaill);

        int databaseSizeBeforeDelete = progessDetaillRepository.findAll().size();

        // Delete the progessDetaill
        restProgessDetaillMockMvc.perform(delete("/api/progess-detaills/{id}", progessDetaill.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProgessDetaill> progessDetaillList = progessDetaillRepository.findAll();
        assertThat(progessDetaillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
