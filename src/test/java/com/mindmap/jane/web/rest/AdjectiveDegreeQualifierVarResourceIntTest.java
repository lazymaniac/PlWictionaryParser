package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.AdjectiveDegreeVar;
import com.mindmap.jane.repository.AdjectiveDegreeVarRepository;
import com.mindmap.jane.service.AdjectiveDegreeVarService;
import com.mindmap.jane.service.dto.AdjectiveDegreeVarDTO;
import com.mindmap.jane.service.mapper.AdjectiveDegreeVarMapper;
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

import com.mindmap.jane.domain.enumeration.AdjectiveDegreeQualifier;
/**
 * Test class for the AdjectiveDegreeVarResource REST controller.
 *
 * @see AdjectiveDegreeVarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class AdjectiveDegreeQualifierVarResourceIntTest {

    private static final AdjectiveDegreeQualifier DEFAULT_DEGREE = AdjectiveDegreeQualifier.ZERO;
    private static final AdjectiveDegreeQualifier UPDATED_DEGREE = AdjectiveDegreeQualifier.HIGHER;

    @Autowired
    private AdjectiveDegreeVarRepository adjectiveDegreeVarRepository;

    @Autowired
    private AdjectiveDegreeVarMapper adjectiveDegreeVarMapper;

    @Autowired
    private AdjectiveDegreeVarService adjectiveDegreeVarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAdjectiveDegreeVarMockMvc;

    private AdjectiveDegreeVar adjectiveDegreeVar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdjectiveDegreeVarResource adjectiveDegreeVarResource = new AdjectiveDegreeVarResource(adjectiveDegreeVarService);
        this.restAdjectiveDegreeVarMockMvc = MockMvcBuilders.standaloneSetup(adjectiveDegreeVarResource)
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
    public static AdjectiveDegreeVar createEntity() {
        AdjectiveDegreeVar adjectiveDegreeVar = new AdjectiveDegreeVar()
            .degree(DEFAULT_DEGREE);
        return adjectiveDegreeVar;
    }

    @Before
    public void initTest() {
        adjectiveDegreeVarRepository.deleteAll();
        adjectiveDegreeVar = createEntity();
    }

    @Test
    public void createAdjectiveDegreeVar() throws Exception {
        int databaseSizeBeforeCreate = adjectiveDegreeVarRepository.findAll().size();

        // Create the AdjectiveDegreeVar
        AdjectiveDegreeVarDTO adjectiveDegreeVarDTO = adjectiveDegreeVarMapper.toDto(adjectiveDegreeVar);
        restAdjectiveDegreeVarMockMvc.perform(post("/api/adjective-degree-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjectiveDegreeVarDTO)))
            .andExpect(status().isCreated());

        // Validate the AdjectiveDegreeVar in the database
        List<AdjectiveDegreeVar> adjectiveDegreeVarList = adjectiveDegreeVarRepository.findAll();
        assertThat(adjectiveDegreeVarList).hasSize(databaseSizeBeforeCreate + 1);
        AdjectiveDegreeVar testAdjectiveDegreeVar = adjectiveDegreeVarList.get(adjectiveDegreeVarList.size() - 1);
        assertThat(testAdjectiveDegreeVar.getAdjectiveDegree()).isEqualTo(DEFAULT_DEGREE);
    }

    @Test
    public void createAdjectiveDegreeVarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adjectiveDegreeVarRepository.findAll().size();

        // Create the AdjectiveDegreeVar with an existing ID
        adjectiveDegreeVar.setId("existing_id");
        AdjectiveDegreeVarDTO adjectiveDegreeVarDTO = adjectiveDegreeVarMapper.toDto(adjectiveDegreeVar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjectiveDegreeVarMockMvc.perform(post("/api/adjective-degree-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjectiveDegreeVarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdjectiveDegreeVar in the database
        List<AdjectiveDegreeVar> adjectiveDegreeVarList = adjectiveDegreeVarRepository.findAll();
        assertThat(adjectiveDegreeVarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAdjectiveDegreeVars() throws Exception {
        // Initialize the database
        adjectiveDegreeVarRepository.save(adjectiveDegreeVar);

        // Get all the adjectiveDegreeVarList
        restAdjectiveDegreeVarMockMvc.perform(get("/api/adjective-degree-vars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjectiveDegreeVar.getId())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())));
    }

    @Test
    public void getAdjectiveDegreeVar() throws Exception {
        // Initialize the database
        adjectiveDegreeVarRepository.save(adjectiveDegreeVar);

        // Get the adjectiveDegreeVar
        restAdjectiveDegreeVarMockMvc.perform(get("/api/adjective-degree-vars/{id}", adjectiveDegreeVar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adjectiveDegreeVar.getId()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()));
    }

    @Test
    public void getNonExistingAdjectiveDegreeVar() throws Exception {
        // Get the adjectiveDegreeVar
        restAdjectiveDegreeVarMockMvc.perform(get("/api/adjective-degree-vars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdjectiveDegreeVar() throws Exception {
        // Initialize the database
        adjectiveDegreeVarRepository.save(adjectiveDegreeVar);
        int databaseSizeBeforeUpdate = adjectiveDegreeVarRepository.findAll().size();

        // Update the adjectiveDegreeVar
        AdjectiveDegreeVar updatedAdjectiveDegreeVar = adjectiveDegreeVarRepository.findOne(adjectiveDegreeVar.getId());
        updatedAdjectiveDegreeVar
            .degree(UPDATED_DEGREE);
        AdjectiveDegreeVarDTO adjectiveDegreeVarDTO = adjectiveDegreeVarMapper.toDto(updatedAdjectiveDegreeVar);

        restAdjectiveDegreeVarMockMvc.perform(put("/api/adjective-degree-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjectiveDegreeVarDTO)))
            .andExpect(status().isOk());

        // Validate the AdjectiveDegreeVar in the database
        List<AdjectiveDegreeVar> adjectiveDegreeVarList = adjectiveDegreeVarRepository.findAll();
        assertThat(adjectiveDegreeVarList).hasSize(databaseSizeBeforeUpdate);
        AdjectiveDegreeVar testAdjectiveDegreeVar = adjectiveDegreeVarList.get(adjectiveDegreeVarList.size() - 1);
        assertThat(testAdjectiveDegreeVar.getAdjectiveDegree()).isEqualTo(UPDATED_DEGREE);
    }

    @Test
    public void updateNonExistingAdjectiveDegreeVar() throws Exception {
        int databaseSizeBeforeUpdate = adjectiveDegreeVarRepository.findAll().size();

        // Create the AdjectiveDegreeVar
        AdjectiveDegreeVarDTO adjectiveDegreeVarDTO = adjectiveDegreeVarMapper.toDto(adjectiveDegreeVar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdjectiveDegreeVarMockMvc.perform(put("/api/adjective-degree-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adjectiveDegreeVarDTO)))
            .andExpect(status().isCreated());

        // Validate the AdjectiveDegreeVar in the database
        List<AdjectiveDegreeVar> adjectiveDegreeVarList = adjectiveDegreeVarRepository.findAll();
        assertThat(adjectiveDegreeVarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAdjectiveDegreeVar() throws Exception {
        // Initialize the database
        adjectiveDegreeVarRepository.save(adjectiveDegreeVar);
        int databaseSizeBeforeDelete = adjectiveDegreeVarRepository.findAll().size();

        // Get the adjectiveDegreeVar
        restAdjectiveDegreeVarMockMvc.perform(delete("/api/adjective-degree-vars/{id}", adjectiveDegreeVar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdjectiveDegreeVar> adjectiveDegreeVarList = adjectiveDegreeVarRepository.findAll();
        assertThat(adjectiveDegreeVarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjectiveDegreeVar.class);
        AdjectiveDegreeVar adjectiveDegreeVar1 = new AdjectiveDegreeVar();
        adjectiveDegreeVar1.setId("id1");
        AdjectiveDegreeVar adjectiveDegreeVar2 = new AdjectiveDegreeVar();
        adjectiveDegreeVar2.setId(adjectiveDegreeVar1.getId());
        assertThat(adjectiveDegreeVar1).isEqualTo(adjectiveDegreeVar2);
        adjectiveDegreeVar2.setId("id2");
        assertThat(adjectiveDegreeVar1).isNotEqualTo(adjectiveDegreeVar2);
        adjectiveDegreeVar1.setId(null);
        assertThat(adjectiveDegreeVar1).isNotEqualTo(adjectiveDegreeVar2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjectiveDegreeVarDTO.class);
        AdjectiveDegreeVarDTO adjectiveDegreeVarDTO1 = new AdjectiveDegreeVarDTO();
        adjectiveDegreeVarDTO1.setId("id1");
        AdjectiveDegreeVarDTO adjectiveDegreeVarDTO2 = new AdjectiveDegreeVarDTO();
        assertThat(adjectiveDegreeVarDTO1).isNotEqualTo(adjectiveDegreeVarDTO2);
        adjectiveDegreeVarDTO2.setId(adjectiveDegreeVarDTO1.getId());
        assertThat(adjectiveDegreeVarDTO1).isEqualTo(adjectiveDegreeVarDTO2);
        adjectiveDegreeVarDTO2.setId("id2");
        assertThat(adjectiveDegreeVarDTO1).isNotEqualTo(adjectiveDegreeVarDTO2);
        adjectiveDegreeVarDTO1.setId(null);
        assertThat(adjectiveDegreeVarDTO1).isNotEqualTo(adjectiveDegreeVarDTO2);
    }
}
