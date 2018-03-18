package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.PronounVar;
import com.mindmap.jane.repository.PronounVarRepository;
import com.mindmap.jane.service.PronounVarService;
import com.mindmap.jane.service.dto.PronounVarDTO;
import com.mindmap.jane.service.mapper.PronounVarMapper;
import com.mindmap.jane.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PronounVarResource REST controller.
 *
 * @see PronounVarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class PronounVarResourceIntTest {

    @Autowired
    private PronounVarRepository pronounVarRepository;

    @Autowired
    private PronounVarMapper pronounVarMapper;

    @Autowired
    private PronounVarService pronounVarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPronounVarMockMvc;

    private PronounVar pronounVar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PronounVarResource pronounVarResource = new PronounVarResource(pronounVarService);
        this.restPronounVarMockMvc = MockMvcBuilders.standaloneSetup(pronounVarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PronounVar createEntity() {
        PronounVar pronounVar = new PronounVar();
        return pronounVar;
    }

    @Before
    public void initTest() {
        pronounVarRepository.deleteAll();
        pronounVar = createEntity();
    }

    @Test
    public void createPronounVar() throws Exception {
        int databaseSizeBeforeCreate = pronounVarRepository.findAll().size();

        // Create the PronounVar
        PronounVarDTO pronounVarDTO = pronounVarMapper.toDto(pronounVar);
        restPronounVarMockMvc.perform(post("/api/pronoun-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronounVarDTO)))
            .andExpect(status().isCreated());

        // Validate the PronounVar in the database
        List<PronounVar> pronounVarList = pronounVarRepository.findAll();
        assertThat(pronounVarList).hasSize(databaseSizeBeforeCreate + 1);
        PronounVar testPronounVar = pronounVarList.get(pronounVarList.size() - 1);
    }

    @Test
    public void createPronounVarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pronounVarRepository.findAll().size();

        // Create the PronounVar with an existing ID
        pronounVar.setId("existing_id");
        PronounVarDTO pronounVarDTO = pronounVarMapper.toDto(pronounVar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPronounVarMockMvc.perform(post("/api/pronoun-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronounVarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PronounVar in the database
        List<PronounVar> pronounVarList = pronounVarRepository.findAll();
        assertThat(pronounVarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPronounVars() throws Exception {
        // Initialize the database
        pronounVarRepository.save(pronounVar);

        // Get all the pronounVarList
        restPronounVarMockMvc.perform(get("/api/pronoun-vars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pronounVar.getId())));
    }

    @Test
    public void getPronounVar() throws Exception {
        // Initialize the database
        pronounVarRepository.save(pronounVar);

        // Get the pronounVar
        restPronounVarMockMvc.perform(get("/api/pronoun-vars/{id}", pronounVar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pronounVar.getId()));
    }

    @Test
    public void getNonExistingPronounVar() throws Exception {
        // Get the pronounVar
        restPronounVarMockMvc.perform(get("/api/pronoun-vars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePronounVar() throws Exception {
        // Initialize the database
        pronounVarRepository.save(pronounVar);
        int databaseSizeBeforeUpdate = pronounVarRepository.findAll().size();

        // Update the pronounVar
        PronounVar updatedPronounVar = pronounVarRepository.findOne(pronounVar.getId());
        PronounVarDTO pronounVarDTO = pronounVarMapper.toDto(updatedPronounVar);

        restPronounVarMockMvc.perform(put("/api/pronoun-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronounVarDTO)))
            .andExpect(status().isOk());

        // Validate the PronounVar in the database
        List<PronounVar> pronounVarList = pronounVarRepository.findAll();
        assertThat(pronounVarList).hasSize(databaseSizeBeforeUpdate);
        PronounVar testPronounVar = pronounVarList.get(pronounVarList.size() - 1);
    }

    @Test
    public void updateNonExistingPronounVar() throws Exception {
        int databaseSizeBeforeUpdate = pronounVarRepository.findAll().size();

        // Create the PronounVar
        PronounVarDTO pronounVarDTO = pronounVarMapper.toDto(pronounVar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPronounVarMockMvc.perform(put("/api/pronoun-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronounVarDTO)))
            .andExpect(status().isCreated());

        // Validate the PronounVar in the database
        List<PronounVar> pronounVarList = pronounVarRepository.findAll();
        assertThat(pronounVarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePronounVar() throws Exception {
        // Initialize the database
        pronounVarRepository.save(pronounVar);
        int databaseSizeBeforeDelete = pronounVarRepository.findAll().size();

        // Get the pronounVar
        restPronounVarMockMvc.perform(delete("/api/pronoun-vars/{id}", pronounVar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PronounVar> pronounVarList = pronounVarRepository.findAll();
        assertThat(pronounVarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PronounVar.class);
        PronounVar pronounVar1 = new PronounVar();
        pronounVar1.setId("id1");
        PronounVar pronounVar2 = new PronounVar();
        pronounVar2.setId(pronounVar1.getId());
        assertThat(pronounVar1).isEqualTo(pronounVar2);
        pronounVar2.setId("id2");
        assertThat(pronounVar1).isNotEqualTo(pronounVar2);
        pronounVar1.setId(null);
        assertThat(pronounVar1).isNotEqualTo(pronounVar2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PronounVarDTO.class);
        PronounVarDTO pronounVarDTO1 = new PronounVarDTO();
        pronounVarDTO1.setId("id1");
        PronounVarDTO pronounVarDTO2 = new PronounVarDTO();
        assertThat(pronounVarDTO1).isNotEqualTo(pronounVarDTO2);
        pronounVarDTO2.setId(pronounVarDTO1.getId());
        assertThat(pronounVarDTO1).isEqualTo(pronounVarDTO2);
        pronounVarDTO2.setId("id2");
        assertThat(pronounVarDTO1).isNotEqualTo(pronounVarDTO2);
        pronounVarDTO1.setId(null);
        assertThat(pronounVarDTO1).isNotEqualTo(pronounVarDTO2);
    }
}
