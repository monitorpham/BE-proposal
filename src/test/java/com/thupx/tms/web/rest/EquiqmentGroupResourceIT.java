package com.thupx.tms.web.rest;

import com.thupx.tms.TaskManagementSoftwareServerApp;
import com.thupx.tms.domain.EquiqmentGroup;
import com.thupx.tms.repository.EquiqmentGroupRepository;
import com.thupx.tms.service.EquiqmentGroupService;
import com.thupx.tms.service.dto.EquiqmentGroupDTO;
import com.thupx.tms.service.mapper.EquiqmentGroupMapper;

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
 * Integration tests for the {@link EquiqmentGroupResource} REST controller.
 */
@SpringBootTest(classes = TaskManagementSoftwareServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EquiqmentGroupResourceIT {

    private static final String DEFAULT_NAME_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_NAME_GROUP = "BBBBBBBBBB";

    @Autowired
    private EquiqmentGroupRepository equiqmentGroupRepository;

    @Autowired
    private EquiqmentGroupMapper equiqmentGroupMapper;

    @Autowired
    private EquiqmentGroupService equiqmentGroupService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquiqmentGroupMockMvc;

    private EquiqmentGroup equiqmentGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquiqmentGroup createEntity(EntityManager em) {
        EquiqmentGroup equiqmentGroup = new EquiqmentGroup()
            .nameGroup(DEFAULT_NAME_GROUP);
        return equiqmentGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquiqmentGroup createUpdatedEntity(EntityManager em) {
        EquiqmentGroup equiqmentGroup = new EquiqmentGroup()
            .nameGroup(UPDATED_NAME_GROUP);
        return equiqmentGroup;
    }

    @BeforeEach
    public void initTest() {
        equiqmentGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquiqmentGroup() throws Exception {
        int databaseSizeBeforeCreate = equiqmentGroupRepository.findAll().size();
        // Create the EquiqmentGroup
        EquiqmentGroupDTO equiqmentGroupDTO = equiqmentGroupMapper.toDto(equiqmentGroup);
        restEquiqmentGroupMockMvc.perform(post("/api/equiqment-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiqmentGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the EquiqmentGroup in the database
        List<EquiqmentGroup> equiqmentGroupList = equiqmentGroupRepository.findAll();
        assertThat(equiqmentGroupList).hasSize(databaseSizeBeforeCreate + 1);
        EquiqmentGroup testEquiqmentGroup = equiqmentGroupList.get(equiqmentGroupList.size() - 1);
        assertThat(testEquiqmentGroup.getNameGroup()).isEqualTo(DEFAULT_NAME_GROUP);
    }

    @Test
    @Transactional
    public void createEquiqmentGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equiqmentGroupRepository.findAll().size();

        // Create the EquiqmentGroup with an existing ID
        equiqmentGroup.setId(1L);
        EquiqmentGroupDTO equiqmentGroupDTO = equiqmentGroupMapper.toDto(equiqmentGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquiqmentGroupMockMvc.perform(post("/api/equiqment-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiqmentGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EquiqmentGroup in the database
        List<EquiqmentGroup> equiqmentGroupList = equiqmentGroupRepository.findAll();
        assertThat(equiqmentGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEquiqmentGroups() throws Exception {
        // Initialize the database
        equiqmentGroupRepository.saveAndFlush(equiqmentGroup);

        // Get all the equiqmentGroupList
        restEquiqmentGroupMockMvc.perform(get("/api/equiqment-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equiqmentGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameGroup").value(hasItem(DEFAULT_NAME_GROUP)));
    }
    
    @Test
    @Transactional
    public void getEquiqmentGroup() throws Exception {
        // Initialize the database
        equiqmentGroupRepository.saveAndFlush(equiqmentGroup);

        // Get the equiqmentGroup
        restEquiqmentGroupMockMvc.perform(get("/api/equiqment-groups/{id}", equiqmentGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equiqmentGroup.getId().intValue()))
            .andExpect(jsonPath("$.nameGroup").value(DEFAULT_NAME_GROUP));
    }
    @Test
    @Transactional
    public void getNonExistingEquiqmentGroup() throws Exception {
        // Get the equiqmentGroup
        restEquiqmentGroupMockMvc.perform(get("/api/equiqment-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquiqmentGroup() throws Exception {
        // Initialize the database
        equiqmentGroupRepository.saveAndFlush(equiqmentGroup);

        int databaseSizeBeforeUpdate = equiqmentGroupRepository.findAll().size();

        // Update the equiqmentGroup
        EquiqmentGroup updatedEquiqmentGroup = equiqmentGroupRepository.findById(equiqmentGroup.getId()).get();
        // Disconnect from session so that the updates on updatedEquiqmentGroup are not directly saved in db
        em.detach(updatedEquiqmentGroup);
        updatedEquiqmentGroup
            .nameGroup(UPDATED_NAME_GROUP);
        EquiqmentGroupDTO equiqmentGroupDTO = equiqmentGroupMapper.toDto(updatedEquiqmentGroup);

        restEquiqmentGroupMockMvc.perform(put("/api/equiqment-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiqmentGroupDTO)))
            .andExpect(status().isOk());

        // Validate the EquiqmentGroup in the database
        List<EquiqmentGroup> equiqmentGroupList = equiqmentGroupRepository.findAll();
        assertThat(equiqmentGroupList).hasSize(databaseSizeBeforeUpdate);
        EquiqmentGroup testEquiqmentGroup = equiqmentGroupList.get(equiqmentGroupList.size() - 1);
        assertThat(testEquiqmentGroup.getNameGroup()).isEqualTo(UPDATED_NAME_GROUP);
    }

    @Test
    @Transactional
    public void updateNonExistingEquiqmentGroup() throws Exception {
        int databaseSizeBeforeUpdate = equiqmentGroupRepository.findAll().size();

        // Create the EquiqmentGroup
        EquiqmentGroupDTO equiqmentGroupDTO = equiqmentGroupMapper.toDto(equiqmentGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquiqmentGroupMockMvc.perform(put("/api/equiqment-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equiqmentGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EquiqmentGroup in the database
        List<EquiqmentGroup> equiqmentGroupList = equiqmentGroupRepository.findAll();
        assertThat(equiqmentGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquiqmentGroup() throws Exception {
        // Initialize the database
        equiqmentGroupRepository.saveAndFlush(equiqmentGroup);

        int databaseSizeBeforeDelete = equiqmentGroupRepository.findAll().size();

        // Delete the equiqmentGroup
        restEquiqmentGroupMockMvc.perform(delete("/api/equiqment-groups/{id}", equiqmentGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EquiqmentGroup> equiqmentGroupList = equiqmentGroupRepository.findAll();
        assertThat(equiqmentGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
