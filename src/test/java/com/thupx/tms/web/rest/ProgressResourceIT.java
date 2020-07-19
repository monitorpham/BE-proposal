package com.thupx.tms.web.rest;

import com.thupx.tms.TaskManagementSoftwareServerApp;
import com.thupx.tms.domain.Progress;
import com.thupx.tms.repository.ProgressRepository;
import com.thupx.tms.service.ProgressService;
import com.thupx.tms.service.dto.ProgressDTO;
import com.thupx.tms.service.mapper.ProgressMapper;

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
 * Integration tests for the {@link ProgressResource} REST controller.
 */
@SpringBootTest(classes = TaskManagementSoftwareServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProgressResourceIT {

    private static final String DEFAULT_CONTENT_TASK = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TASK = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIMIT = 1;
    private static final Integer UPDATED_LIMIT = 2;

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private ProgressMapper progressMapper;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgressMockMvc;

    private Progress progress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Progress createEntity(EntityManager em) {
        Progress progress = new Progress()
            .contentTask(DEFAULT_CONTENT_TASK)
            .limit(DEFAULT_LIMIT);
        return progress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Progress createUpdatedEntity(EntityManager em) {
        Progress progress = new Progress()
            .contentTask(UPDATED_CONTENT_TASK)
            .limit(UPDATED_LIMIT);
        return progress;
    }

    @BeforeEach
    public void initTest() {
        progress = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgress() throws Exception {
        int databaseSizeBeforeCreate = progressRepository.findAll().size();
        // Create the Progress
        ProgressDTO progressDTO = progressMapper.toDto(progress);
        restProgressMockMvc.perform(post("/api/progresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(progressDTO)))
            .andExpect(status().isCreated());

        // Validate the Progress in the database
        List<Progress> progressList = progressRepository.findAll();
        assertThat(progressList).hasSize(databaseSizeBeforeCreate + 1);
        Progress testProgress = progressList.get(progressList.size() - 1);
        assertThat(testProgress.getContentTask()).isEqualTo(DEFAULT_CONTENT_TASK);
        assertThat(testProgress.getLimit()).isEqualTo(DEFAULT_LIMIT);
    }

    @Test
    @Transactional
    public void createProgressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = progressRepository.findAll().size();

        // Create the Progress with an existing ID
        progress.setId(1L);
        ProgressDTO progressDTO = progressMapper.toDto(progress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgressMockMvc.perform(post("/api/progresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(progressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Progress in the database
        List<Progress> progressList = progressRepository.findAll();
        assertThat(progressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProgresses() throws Exception {
        // Initialize the database
        progressRepository.saveAndFlush(progress);

        // Get all the progressList
        restProgressMockMvc.perform(get("/api/progresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(progress.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentTask").value(hasItem(DEFAULT_CONTENT_TASK)))
            .andExpect(jsonPath("$.[*].limit").value(hasItem(DEFAULT_LIMIT)));
    }
    
    @Test
    @Transactional
    public void getProgress() throws Exception {
        // Initialize the database
        progressRepository.saveAndFlush(progress);

        // Get the progress
        restProgressMockMvc.perform(get("/api/progresses/{id}", progress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(progress.getId().intValue()))
            .andExpect(jsonPath("$.contentTask").value(DEFAULT_CONTENT_TASK))
            .andExpect(jsonPath("$.limit").value(DEFAULT_LIMIT));
    }
    @Test
    @Transactional
    public void getNonExistingProgress() throws Exception {
        // Get the progress
        restProgressMockMvc.perform(get("/api/progresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgress() throws Exception {
        // Initialize the database
        progressRepository.saveAndFlush(progress);

        int databaseSizeBeforeUpdate = progressRepository.findAll().size();

        // Update the progress
        Progress updatedProgress = progressRepository.findById(progress.getId()).get();
        // Disconnect from session so that the updates on updatedProgress are not directly saved in db
        em.detach(updatedProgress);
        updatedProgress
            .contentTask(UPDATED_CONTENT_TASK)
            .limit(UPDATED_LIMIT);
        ProgressDTO progressDTO = progressMapper.toDto(updatedProgress);

        restProgressMockMvc.perform(put("/api/progresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(progressDTO)))
            .andExpect(status().isOk());

        // Validate the Progress in the database
        List<Progress> progressList = progressRepository.findAll();
        assertThat(progressList).hasSize(databaseSizeBeforeUpdate);
        Progress testProgress = progressList.get(progressList.size() - 1);
        assertThat(testProgress.getContentTask()).isEqualTo(UPDATED_CONTENT_TASK);
        assertThat(testProgress.getLimit()).isEqualTo(UPDATED_LIMIT);
    }

    @Test
    @Transactional
    public void updateNonExistingProgress() throws Exception {
        int databaseSizeBeforeUpdate = progressRepository.findAll().size();

        // Create the Progress
        ProgressDTO progressDTO = progressMapper.toDto(progress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgressMockMvc.perform(put("/api/progresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(progressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Progress in the database
        List<Progress> progressList = progressRepository.findAll();
        assertThat(progressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgress() throws Exception {
        // Initialize the database
        progressRepository.saveAndFlush(progress);

        int databaseSizeBeforeDelete = progressRepository.findAll().size();

        // Delete the progress
        restProgressMockMvc.perform(delete("/api/progresses/{id}", progress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Progress> progressList = progressRepository.findAll();
        assertThat(progressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
