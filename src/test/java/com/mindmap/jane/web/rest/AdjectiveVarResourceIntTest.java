package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.AdjectiveVar;
import com.mindmap.jane.repository.AdjectiveVarRepository;
import com.mindmap.jane.service.AdjectiveVarService;
import com.mindmap.jane.service.dto.AdjectiveVarDTO;
import com.mindmap.jane.service.mapper.AdjectiveVarMapper;
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
 * Test class for the AdjectiveVarResource REST controller.
 *
 * @see AdjectiveVarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class AdjectiveVarResourceIntTest {

    @Autowired
    private AdjectiveVarRepository adjectiveVarRepository;

    @Autowired
    private AdjectiveVarMapper adjectiveVarMapper;

    @Autowired
    private AdjectiveVarService adjectiveVarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAdjectiveVarMockMvc;

    private AdjectiveVar adjectiveVar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdjectiveVarResource adjectiveVarResource = new AdjectiveVarResource(adjectiveVarService);
        this.restAdjectiveVarMockMvc = MockMvcBuilders.standaloneSetup(adjectiveVarResource)
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
    public static AdjectiveVar createEntity() {
        AdjectiveVar adjectiveVar = new AdjectiveVar();
        return adjectiveVar;
    }

    @Before
    public void initTest() {
        adjectiveVarRepository.deleteAll();
        adjectiveVar = createEntity();
    }

    @Test
    public void createAdjectiveVar() throws Exception {
        int databaseSizeBeforeCreate = adjectiveVarRepository.findAll().size();

        // Create the AdjectiveVar
        AdjectiveVarDTO adjectiveVarDTO = adjectiveVarMapper.toDto(adjectiveVar);
        restAdjectiveVarMockMvc.perform(post("/api/adjective-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjectiveVarDTO)))
            .andExpect(status().isCreated());

        // Validate the AdjectiveVar in the database
        List<AdjectiveVar> adjectiveVarList = adjectiveVarRepository.findAll();
        assertThat(adjectiveVarList).hasSize(databaseSizeBeforeCreate + 1);
        AdjectiveVar testAdjectiveVar = adjectiveVarList.get(adjectiveVarList.size() - 1);
    }

    @Test
    public void createAdjectiveVarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adjectiveVarRepository.findAll().size();

        // Create the AdjectiveVar with an existing ID
        adjectiveVar.setId("existing_id");
        AdjectiveVarDTO adjectiveVarDTO = adjectiveVarMapper.toDto(adjectiveVar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjectiveVarMockMvc.perform(post("/api/adjective-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjectiveVarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdjectiveVar in the database
        List<AdjectiveVar> adjectiveVarList = adjectiveVarRepository.findAll();
        assertThat(adjectiveVarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAdjectiveVars() throws Exception {
        // Initialize the database
        adjectiveVarRepository.save(adjectiveVar);

        // Get all the adjectiveVarList
        restAdjectiveVarMockMvc.perform(get("/api/adjective-vars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjectiveVar.getId())));
    }

    @Test
    public void getAdjectiveVar() throws Exception {
        // Initialize the database
        adjectiveVarRepository.save(adjectiveVar);

        // Get the adjectiveVar
        restAdjectiveVarMockMvc.perform(get("/api/adjective-vars/{id}", adjectiveVar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adjectiveVar.getId()));
    }

    @Test
    public void getNonExistingAdjectiveVar() throws Exception {
        // Get the adjectiveVar
        restAdjectiveVarMockMvc.perform(get("/api/adjective-vars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdjectiveVar() throws Exception {
        // Initialize the database
        adjectiveVarRepository.save(adjectiveVar);
        int databaseSizeBeforeUpdate = adjectiveVarRepository.findAll().size();

        // Update the adjectiveVar
        AdjectiveVar updatedAdjectiveVar = adjectiveVarRepository.findOne(adjectiveVar.getId());
        AdjectiveVarDTO adjectiveVarDTO = adjectiveVarMapper.toDto(updatedAdjectiveVar);

        restAdjectiveVarMockMvc.perform(put("/api/adjective-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjectiveVarDTO)))
            .andExpect(status().isOk());

        // Validate the AdjectiveVar in the database
        List<AdjectiveVar> adjectiveVarList = adjectiveVarRepository.findAll();
        assertThat(adjectiveVarList).hasSize(databaseSizeBeforeUpdate);
        AdjectiveVar testAdjectiveVar = adjectiveVarList.get(adjectiveVarList.size() - 1);
    }

    @Test
    public void updateNonExistingAdjectiveVar() throws Exception {
        int databaseSizeBeforeUpdate = adjectiveVarRepository.findAll().size();

        // Create the AdjectiveVar
        AdjectiveVarDTO adjectiveVarDTO = adjectiveVarMapper.toDto(adjectiveVar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdjectiveVarMockMvc.perform(put("/api/adjective-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjectiveVarDTO)))
            .andExpect(status().isCreated());

        // Validate the AdjectiveVar in the database
        List<AdjectiveVar> adjectiveVarList = adjectiveVarRepository.findAll();
        assertThat(adjectiveVarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAdjectiveVar() throws Exception {
        // Initialize the database
        adjectiveVarRepository.save(adjectiveVar);
        int databaseSizeBeforeDelete = adjectiveVarRepository.findAll().size();

        // Get the adjectiveVar
        restAdjectiveVarMockMvc.perform(delete("/api/adjective-vars/{id}", adjectiveVar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdjectiveVar> adjectiveVarList = adjectiveVarRepository.findAll();
        assertThat(adjectiveVarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjectiveVar.class);
        AdjectiveVar adjectiveVar1 = new AdjectiveVar();
        adjectiveVar1.setId("id1");
        AdjectiveVar adjectiveVar2 = new AdjectiveVar();
        adjectiveVar2.setId(adjectiveVar1.getId());
        assertThat(adjectiveVar1).isEqualTo(adjectiveVar2);
        adjectiveVar2.setId("id2");
        assertThat(adjectiveVar1).isNotEqualTo(adjectiveVar2);
        adjectiveVar1.setId(null);
        assertThat(adjectiveVar1).isNotEqualTo(adjectiveVar2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjectiveVarDTO.class);
        AdjectiveVarDTO adjectiveVarDTO1 = new AdjectiveVarDTO();
        adjectiveVarDTO1.setId("id1");
        AdjectiveVarDTO adjectiveVarDTO2 = new AdjectiveVarDTO();
        assertThat(adjectiveVarDTO1).isNotEqualTo(adjectiveVarDTO2);
        adjectiveVarDTO2.setId(adjectiveVarDTO1.getId());
        assertThat(adjectiveVarDTO1).isEqualTo(adjectiveVarDTO2);
        adjectiveVarDTO2.setId("id2");
        assertThat(adjectiveVarDTO1).isNotEqualTo(adjectiveVarDTO2);
        adjectiveVarDTO1.setId(null);
        assertThat(adjectiveVarDTO1).isNotEqualTo(adjectiveVarDTO2);
    }
}
