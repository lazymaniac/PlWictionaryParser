package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.NounVar;
import com.mindmap.jane.repository.NounVarRepository;
import com.mindmap.jane.service.NounVarService;
import com.mindmap.jane.service.dto.NounVarDTO;
import com.mindmap.jane.service.mapper.NounVarMapper;
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

import com.mindmap.jane.domain.enumeration.GenderQualifier;

/**
 * Test class for the NounVarResource REST controller.
 *
 * @see NounVarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class NounVarResourceIntTest {

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    private static final GenderQualifier DEFAULT_GENDER = GenderQualifier.MESKI;
    private static final GenderQualifier UPDATED_GENDER = GenderQualifier.MESKOOSOBOWY;

    private static final GenderQualifier DEFAULT_SECOND_GENDER = GenderQualifier.MESKI;
    private static final GenderQualifier UPDATED_SECOND_GENDER = GenderQualifier.MESKOOSOBOWY;

    private static final Boolean DEFAULT_VARIETY_ABLE = false;
    private static final Boolean UPDATED_VARIETY_ABLE = true;

    private static final Boolean DEFAULT_NO_SINGULAR = false;
    private static final Boolean UPDATED_NO_SINGULAR = true;

    private static final Boolean DEFAULT_NO_PLURAL = false;
    private static final Boolean UPDATED_NO_PLURAL = true;

    @Autowired
    private NounVarRepository nounVarRepository;

    @Autowired
    private NounVarMapper nounVarMapper;

    @Autowired
    private NounVarService nounVarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restNounVarMockMvc;

    private NounVar nounVar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NounVarResource nounVarResource = new NounVarResource(nounVarService);
        this.restNounVarMockMvc = MockMvcBuilders.standaloneSetup(nounVarResource)
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
    public static NounVar createEntity() {
        NounVar nounVar = new NounVar()
            .topic(DEFAULT_TOPIC)
            .gender(DEFAULT_GENDER)
            .secondGender(DEFAULT_SECOND_GENDER)
            .varietyAble(DEFAULT_VARIETY_ABLE)
            .noSingular(DEFAULT_NO_SINGULAR)
            .noPlural(DEFAULT_NO_PLURAL);
        return nounVar;
    }

    @Before
    public void initTest() {
        nounVarRepository.deleteAll();
        nounVar = createEntity();
    }

    @Test
    public void createNounVar() throws Exception {
        int databaseSizeBeforeCreate = nounVarRepository.findAll().size();

        // Create the NounVar
        NounVarDTO nounVarDTO = nounVarMapper.toDto(nounVar);
        restNounVarMockMvc.perform(post("/api/noun-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nounVarDTO)))
            .andExpect(status().isCreated());

        // Validate the NounVar in the database
        List<NounVar> nounVarList = nounVarRepository.findAll();
        assertThat(nounVarList).hasSize(databaseSizeBeforeCreate + 1);
        NounVar testNounVar = nounVarList.get(nounVarList.size() - 1);
        assertThat(testNounVar.getTopic()).isEqualTo(DEFAULT_TOPIC);
        assertThat(testNounVar.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testNounVar.getSecondGender()).isEqualTo(DEFAULT_SECOND_GENDER);
        assertThat(testNounVar.isVarietyAble()).isEqualTo(DEFAULT_VARIETY_ABLE);
        assertThat(testNounVar.isNoSingular()).isEqualTo(DEFAULT_NO_SINGULAR);
        assertThat(testNounVar.isNoPlural()).isEqualTo(DEFAULT_NO_PLURAL);
    }

    @Test
    public void createNounVarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nounVarRepository.findAll().size();

        // Create the NounVar with an existing ID
        nounVar.setId("existing_id");
        NounVarDTO nounVarDTO = nounVarMapper.toDto(nounVar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNounVarMockMvc.perform(post("/api/noun-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nounVarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NounVar in the database
        List<NounVar> nounVarList = nounVarRepository.findAll();
        assertThat(nounVarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllNounVars() throws Exception {
        // Initialize the database
        nounVarRepository.save(nounVar);

        // Get all the nounVarList
        restNounVarMockMvc.perform(get("/api/noun-vars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nounVar.getId())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].secondGender").value(hasItem(DEFAULT_SECOND_GENDER.toString())))
            .andExpect(jsonPath("$.[*].varietyAble").value(hasItem(DEFAULT_VARIETY_ABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].noSingular").value(hasItem(DEFAULT_NO_SINGULAR.booleanValue())))
            .andExpect(jsonPath("$.[*].noPlural").value(hasItem(DEFAULT_NO_PLURAL.booleanValue())));
    }

    @Test
    public void getNounVar() throws Exception {
        // Initialize the database
        nounVarRepository.save(nounVar);

        // Get the nounVar
        restNounVarMockMvc.perform(get("/api/noun-vars/{id}", nounVar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nounVar.getId()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.secondGender").value(DEFAULT_SECOND_GENDER.toString()))
            .andExpect(jsonPath("$.varietyAble").value(DEFAULT_VARIETY_ABLE.booleanValue()))
            .andExpect(jsonPath("$.noSingular").value(DEFAULT_NO_SINGULAR.booleanValue()))
            .andExpect(jsonPath("$.noPlural").value(DEFAULT_NO_PLURAL.booleanValue()));
    }

    @Test
    public void getNonExistingNounVar() throws Exception {
        // Get the nounVar
        restNounVarMockMvc.perform(get("/api/noun-vars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateNounVar() throws Exception {
        // Initialize the database
        nounVarRepository.save(nounVar);
        int databaseSizeBeforeUpdate = nounVarRepository.findAll().size();

        // Update the nounVar
        NounVar updatedNounVar = nounVarRepository.findOne(nounVar.getId());
        updatedNounVar
            .topic(UPDATED_TOPIC)
            .gender(UPDATED_GENDER)
            .secondGender(UPDATED_SECOND_GENDER)
            .varietyAble(UPDATED_VARIETY_ABLE)
            .noSingular(UPDATED_NO_SINGULAR)
            .noPlural(UPDATED_NO_PLURAL);
        NounVarDTO nounVarDTO = nounVarMapper.toDto(updatedNounVar);

        restNounVarMockMvc.perform(put("/api/noun-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nounVarDTO)))
            .andExpect(status().isOk());

        // Validate the NounVar in the database
        List<NounVar> nounVarList = nounVarRepository.findAll();
        assertThat(nounVarList).hasSize(databaseSizeBeforeUpdate);
        NounVar testNounVar = nounVarList.get(nounVarList.size() - 1);
        assertThat(testNounVar.getTopic()).isEqualTo(UPDATED_TOPIC);
        assertThat(testNounVar.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testNounVar.getSecondGender()).isEqualTo(UPDATED_SECOND_GENDER);
        assertThat(testNounVar.isVarietyAble()).isEqualTo(UPDATED_VARIETY_ABLE);
        assertThat(testNounVar.isNoSingular()).isEqualTo(UPDATED_NO_SINGULAR);
        assertThat(testNounVar.isNoPlural()).isEqualTo(UPDATED_NO_PLURAL);
    }

    @Test
    public void updateNonExistingNounVar() throws Exception {
        int databaseSizeBeforeUpdate = nounVarRepository.findAll().size();

        // Create the NounVar
        NounVarDTO nounVarDTO = nounVarMapper.toDto(nounVar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNounVarMockMvc.perform(put("/api/noun-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nounVarDTO)))
            .andExpect(status().isCreated());

        // Validate the NounVar in the database
        List<NounVar> nounVarList = nounVarRepository.findAll();
        assertThat(nounVarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteNounVar() throws Exception {
        // Initialize the database
        nounVarRepository.save(nounVar);
        int databaseSizeBeforeDelete = nounVarRepository.findAll().size();

        // Get the nounVar
        restNounVarMockMvc.perform(delete("/api/noun-vars/{id}", nounVar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NounVar> nounVarList = nounVarRepository.findAll();
        assertThat(nounVarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NounVar.class);
        NounVar nounVar1 = new NounVar();
        nounVar1.setId("id1");
        NounVar nounVar2 = new NounVar();
        nounVar2.setId(nounVar1.getId());
        assertThat(nounVar1).isEqualTo(nounVar2);
        nounVar2.setId("id2");
        assertThat(nounVar1).isNotEqualTo(nounVar2);
        nounVar1.setId(null);
        assertThat(nounVar1).isNotEqualTo(nounVar2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NounVarDTO.class);
        NounVarDTO nounVarDTO1 = new NounVarDTO();
        nounVarDTO1.setId("id1");
        NounVarDTO nounVarDTO2 = new NounVarDTO();
        assertThat(nounVarDTO1).isNotEqualTo(nounVarDTO2);
        nounVarDTO2.setId(nounVarDTO1.getId());
        assertThat(nounVarDTO1).isEqualTo(nounVarDTO2);
        nounVarDTO2.setId("id2");
        assertThat(nounVarDTO1).isNotEqualTo(nounVarDTO2);
        nounVarDTO1.setId(null);
        assertThat(nounVarDTO1).isNotEqualTo(nounVarDTO2);
    }
}
