package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.AdverbVar;
import com.mindmap.jane.repository.AdverbVarRepository;
import com.mindmap.jane.service.AdverbVarService;
import com.mindmap.jane.service.dto.AdverbVarDTO;
import com.mindmap.jane.service.mapper.AdverbVarMapper;
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
 * Test class for the AdverbVarResource REST controller.
 *
 * @see AdverbVarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class AdverbVarResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_HIGHER_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_HIGHER_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_HIGHEST_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_HIGHEST_DEGREE = "BBBBBBBBBB";

    @Autowired
    private AdverbVarRepository adverbVarRepository;

    @Autowired
    private AdverbVarMapper adverbVarMapper;

    @Autowired
    private AdverbVarService adverbVarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAdverbVarMockMvc;

    private AdverbVar adverbVar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdverbVarResource adverbVarResource = new AdverbVarResource(adverbVarService);
        this.restAdverbVarMockMvc = MockMvcBuilders.standaloneSetup(adverbVarResource)
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
    public static AdverbVar createEntity() {
        AdverbVar adverbVar = new AdverbVar()
            .value(DEFAULT_VALUE)
            .higherDegree(DEFAULT_HIGHER_DEGREE)
            .highestDegree(DEFAULT_HIGHEST_DEGREE);
        return adverbVar;
    }

    @Before
    public void initTest() {
        adverbVarRepository.deleteAll();
        adverbVar = createEntity();
    }

    @Test
    public void createAdverbVar() throws Exception {
        int databaseSizeBeforeCreate = adverbVarRepository.findAll().size();

        // Create the AdverbVar
        AdverbVarDTO adverbVarDTO = adverbVarMapper.toDto(adverbVar);
        restAdverbVarMockMvc.perform(post("/api/adverb-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adverbVarDTO)))
            .andExpect(status().isCreated());

        // Validate the AdverbVar in the database
        List<AdverbVar> adverbVarList = adverbVarRepository.findAll();
        assertThat(adverbVarList).hasSize(databaseSizeBeforeCreate + 1);
        AdverbVar testAdverbVar = adverbVarList.get(adverbVarList.size() - 1);
        assertThat(testAdverbVar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAdverbVar.getHigherDegree()).isEqualTo(DEFAULT_HIGHER_DEGREE);
        assertThat(testAdverbVar.getHighestDegree()).isEqualTo(DEFAULT_HIGHEST_DEGREE);
    }

    @Test
    public void createAdverbVarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adverbVarRepository.findAll().size();

        // Create the AdverbVar with an existing ID
        adverbVar.setId("existing_id");
        AdverbVarDTO adverbVarDTO = adverbVarMapper.toDto(adverbVar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdverbVarMockMvc.perform(post("/api/adverb-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adverbVarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdverbVar in the database
        List<AdverbVar> adverbVarList = adverbVarRepository.findAll();
        assertThat(adverbVarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAdverbVars() throws Exception {
        // Initialize the database
        adverbVarRepository.save(adverbVar);

        // Get all the adverbVarList
        restAdverbVarMockMvc.perform(get("/api/adverb-vars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adverbVar.getId())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].higherDegree").value(hasItem(DEFAULT_HIGHER_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].highestDegree").value(hasItem(DEFAULT_HIGHEST_DEGREE.toString())));
    }

    @Test
    public void getAdverbVar() throws Exception {
        // Initialize the database
        adverbVarRepository.save(adverbVar);

        // Get the adverbVar
        restAdverbVarMockMvc.perform(get("/api/adverb-vars/{id}", adverbVar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adverbVar.getId()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.higherDegree").value(DEFAULT_HIGHER_DEGREE.toString()))
            .andExpect(jsonPath("$.highestDegree").value(DEFAULT_HIGHEST_DEGREE.toString()));
    }

    @Test
    public void getNonExistingAdverbVar() throws Exception {
        // Get the adverbVar
        restAdverbVarMockMvc.perform(get("/api/adverb-vars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAdverbVar() throws Exception {
        // Initialize the database
        adverbVarRepository.save(adverbVar);
        int databaseSizeBeforeUpdate = adverbVarRepository.findAll().size();

        // Update the adverbVar
        AdverbVar updatedAdverbVar = adverbVarRepository.findOne(adverbVar.getId());
        updatedAdverbVar
            .value(UPDATED_VALUE)
            .higherDegree(UPDATED_HIGHER_DEGREE)
            .highestDegree(UPDATED_HIGHEST_DEGREE);
        AdverbVarDTO adverbVarDTO = adverbVarMapper.toDto(updatedAdverbVar);

        restAdverbVarMockMvc.perform(put("/api/adverb-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adverbVarDTO)))
            .andExpect(status().isOk());

        // Validate the AdverbVar in the database
        List<AdverbVar> adverbVarList = adverbVarRepository.findAll();
        assertThat(adverbVarList).hasSize(databaseSizeBeforeUpdate);
        AdverbVar testAdverbVar = adverbVarList.get(adverbVarList.size() - 1);
        assertThat(testAdverbVar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAdverbVar.getHigherDegree()).isEqualTo(UPDATED_HIGHER_DEGREE);
        assertThat(testAdverbVar.getHighestDegree()).isEqualTo(UPDATED_HIGHEST_DEGREE);
    }

    @Test
    public void updateNonExistingAdverbVar() throws Exception {
        int databaseSizeBeforeUpdate = adverbVarRepository.findAll().size();

        // Create the AdverbVar
        AdverbVarDTO adverbVarDTO = adverbVarMapper.toDto(adverbVar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdverbVarMockMvc.perform(put("/api/adverb-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adverbVarDTO)))
            .andExpect(status().isCreated());

        // Validate the AdverbVar in the database
        List<AdverbVar> adverbVarList = adverbVarRepository.findAll();
        assertThat(adverbVarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAdverbVar() throws Exception {
        // Initialize the database
        adverbVarRepository.save(adverbVar);
        int databaseSizeBeforeDelete = adverbVarRepository.findAll().size();

        // Get the adverbVar
        restAdverbVarMockMvc.perform(delete("/api/adverb-vars/{id}", adverbVar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdverbVar> adverbVarList = adverbVarRepository.findAll();
        assertThat(adverbVarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdverbVar.class);
        AdverbVar adverbVar1 = new AdverbVar();
        adverbVar1.setId("id1");
        AdverbVar adverbVar2 = new AdverbVar();
        adverbVar2.setId(adverbVar1.getId());
        assertThat(adverbVar1).isEqualTo(adverbVar2);
        adverbVar2.setId("id2");
        assertThat(adverbVar1).isNotEqualTo(adverbVar2);
        adverbVar1.setId(null);
        assertThat(adverbVar1).isNotEqualTo(adverbVar2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdverbVarDTO.class);
        AdverbVarDTO adverbVarDTO1 = new AdverbVarDTO();
        adverbVarDTO1.setId("id1");
        AdverbVarDTO adverbVarDTO2 = new AdverbVarDTO();
        assertThat(adverbVarDTO1).isNotEqualTo(adverbVarDTO2);
        adverbVarDTO2.setId(adverbVarDTO1.getId());
        assertThat(adverbVarDTO1).isEqualTo(adverbVarDTO2);
        adverbVarDTO2.setId("id2");
        assertThat(adverbVarDTO1).isNotEqualTo(adverbVarDTO2);
        adverbVarDTO1.setId(null);
        assertThat(adverbVarDTO1).isNotEqualTo(adverbVarDTO2);
    }
}
