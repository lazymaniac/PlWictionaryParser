package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.VerbVar;
import com.mindmap.jane.repository.VerbVarRepository;
import com.mindmap.jane.service.VerbVarService;
import com.mindmap.jane.service.dto.VerbVarDTO;
import com.mindmap.jane.service.mapper.VerbVarMapper;
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
 * Test class for the VerbVarResource REST controller.
 *
 * @see VerbVarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class VerbVarResourceIntTest {

    private static final Boolean DEFAULT_IS_PERFECTIVE = false;
    private static final Boolean UPDATED_IS_PERFECTIVE = true;

    private static final Boolean DEFAULT_IS_REFLEXIV_VERB = false;
    private static final Boolean UPDATED_IS_REFLEXIV_VERB = true;

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    private static final String DEFAULT_CONJUGATION = "AAAAAAAAAA";
    private static final String UPDATED_CONJUGATION = "BBBBBBBBBB";

    private static final String DEFAULT_REFLEXIV_PRONOUN = "AAAAAAAAAA";
    private static final String UPDATED_REFLEXIV_PRONOUN = "BBBBBBBBBB";

    private static final String DEFAULT_PERFECTIVE = "AAAAAAAAAA";
    private static final String UPDATED_PERFECTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_IMPERFECTIVE = "AAAAAAAAAA";
    private static final String UPDATED_IMPERFECTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_INFINITIVE = "AAAAAAAAAA";
    private static final String UPDATED_INFINITIVE = "BBBBBBBBBB";

    private static final String DEFAULT_IMPERSONAL_FROM_PAST = "AAAAAAAAAA";
    private static final String UPDATED_IMPERSONAL_FROM_PAST = "BBBBBBBBBB";

    private static final String DEFAULT_ADVERBIAL_PARTICIPLE_CONTEMPORARY = "AAAAAAAAAA";
    private static final String UPDATED_ADVERBIAL_PARTICIPLE_CONTEMPORARY = "BBBBBBBBBB";

    private static final String DEFAULT_ADVERBIAL_PARTICIPLE_PRIOR = "AAAAAAAAAA";
    private static final String UPDATED_ADVERBIAL_PARTICIPLE_PRIOR = "BBBBBBBBBB";

    private static final String DEFAULT_GERUND = "AAAAAAAAAA";
    private static final String UPDATED_GERUND = "BBBBBBBBBB";

    @Autowired
    private VerbVarRepository verbVarRepository;

    @Autowired
    private VerbVarMapper verbVarMapper;

    @Autowired
    private VerbVarService verbVarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVerbVarMockMvc;

    private VerbVar verbVar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VerbVarResource verbVarResource = new VerbVarResource(verbVarService);
        this.restVerbVarMockMvc = MockMvcBuilders.standaloneSetup(verbVarResource)
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
    public static VerbVar createEntity() {
        VerbVar verbVar = new VerbVar()
            .isPerfective(DEFAULT_IS_PERFECTIVE)
            .isReflexivVerb(DEFAULT_IS_REFLEXIV_VERB)
            .topic(DEFAULT_TOPIC)
            .conjugation(DEFAULT_CONJUGATION)
            .reflexivPronoun(DEFAULT_REFLEXIV_PRONOUN)
            .perfective(DEFAULT_PERFECTIVE)
            .imperfective(DEFAULT_IMPERFECTIVE)
            .infinitive(DEFAULT_INFINITIVE)
            .impersonalFromPast(DEFAULT_IMPERSONAL_FROM_PAST)
            .adverbialParticipleContemporary(DEFAULT_ADVERBIAL_PARTICIPLE_CONTEMPORARY)
            .adverbialParticiplePrior(DEFAULT_ADVERBIAL_PARTICIPLE_PRIOR)
            .gerund(DEFAULT_GERUND);
        return verbVar;
    }

    @Before
    public void initTest() {
        verbVarRepository.deleteAll();
        verbVar = createEntity();
    }

    @Test
    public void createVerbVar() throws Exception {
        int databaseSizeBeforeCreate = verbVarRepository.findAll().size();

        // Create the VerbVar
        VerbVarDTO verbVarDTO = verbVarMapper.toDto(verbVar);
        restVerbVarMockMvc.perform(post("/api/verb-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verbVarDTO)))
            .andExpect(status().isCreated());

        // Validate the VerbVar in the database
        List<VerbVar> verbVarList = verbVarRepository.findAll();
        assertThat(verbVarList).hasSize(databaseSizeBeforeCreate + 1);
        VerbVar testVerbVar = verbVarList.get(verbVarList.size() - 1);
        assertThat(testVerbVar.isIsPerfective()).isEqualTo(DEFAULT_IS_PERFECTIVE);
        assertThat(testVerbVar.isIsReflexivVerb()).isEqualTo(DEFAULT_IS_REFLEXIV_VERB);
        assertThat(testVerbVar.getTopic()).isEqualTo(DEFAULT_TOPIC);
        assertThat(testVerbVar.getConjugation()).isEqualTo(DEFAULT_CONJUGATION);
        assertThat(testVerbVar.getReflexivPronoun()).isEqualTo(DEFAULT_REFLEXIV_PRONOUN);
        assertThat(testVerbVar.getPerfective()).isEqualTo(DEFAULT_PERFECTIVE);
        assertThat(testVerbVar.getImperfective()).isEqualTo(DEFAULT_IMPERFECTIVE);
        assertThat(testVerbVar.getInfinitive()).isEqualTo(DEFAULT_INFINITIVE);
        assertThat(testVerbVar.getImpersonalFormPast()).isEqualTo(DEFAULT_IMPERSONAL_FROM_PAST);
        assertThat(testVerbVar.getAdverbialParticipleContemporary()).isEqualTo(DEFAULT_ADVERBIAL_PARTICIPLE_CONTEMPORARY);
        assertThat(testVerbVar.getAdverbialParticiplePrior()).isEqualTo(DEFAULT_ADVERBIAL_PARTICIPLE_PRIOR);
        assertThat(testVerbVar.getGerund()).isEqualTo(DEFAULT_GERUND);
    }

    @Test
    public void createVerbVarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = verbVarRepository.findAll().size();

        // Create the VerbVar with an existing ID
        verbVar.setId("existing_id");
        VerbVarDTO verbVarDTO = verbVarMapper.toDto(verbVar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVerbVarMockMvc.perform(post("/api/verb-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verbVarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VerbVar in the database
        List<VerbVar> verbVarList = verbVarRepository.findAll();
        assertThat(verbVarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllVerbVars() throws Exception {
        // Initialize the database
        verbVarRepository.save(verbVar);

        // Get all the verbVarList
        restVerbVarMockMvc.perform(get("/api/verb-vars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verbVar.getId())))
            .andExpect(jsonPath("$.[*].isPerfective").value(hasItem(DEFAULT_IS_PERFECTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isReflexivVerb").value(hasItem(DEFAULT_IS_REFLEXIV_VERB.booleanValue())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC.toString())))
            .andExpect(jsonPath("$.[*].conjugation").value(hasItem(DEFAULT_CONJUGATION.toString())))
            .andExpect(jsonPath("$.[*].reflexivPronoun").value(hasItem(DEFAULT_REFLEXIV_PRONOUN.toString())))
            .andExpect(jsonPath("$.[*].perfective").value(hasItem(DEFAULT_PERFECTIVE.toString())))
            .andExpect(jsonPath("$.[*].imperfective").value(hasItem(DEFAULT_IMPERFECTIVE.toString())))
            .andExpect(jsonPath("$.[*].infinitive").value(hasItem(DEFAULT_INFINITIVE.toString())))
            .andExpect(jsonPath("$.[*].impersonalFromPast").value(hasItem(DEFAULT_IMPERSONAL_FROM_PAST.toString())))
            .andExpect(jsonPath("$.[*].adverbialParticipleContemporary").value(hasItem(DEFAULT_ADVERBIAL_PARTICIPLE_CONTEMPORARY.toString())))
            .andExpect(jsonPath("$.[*].adverbialParticiplePrior").value(hasItem(DEFAULT_ADVERBIAL_PARTICIPLE_PRIOR.toString())))
            .andExpect(jsonPath("$.[*].gerund").value(hasItem(DEFAULT_GERUND.toString())));
    }

    @Test
    public void getVerbVar() throws Exception {
        // Initialize the database
        verbVarRepository.save(verbVar);

        // Get the verbVar
        restVerbVarMockMvc.perform(get("/api/verb-vars/{id}", verbVar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(verbVar.getId()))
            .andExpect(jsonPath("$.isPerfective").value(DEFAULT_IS_PERFECTIVE.booleanValue()))
            .andExpect(jsonPath("$.isReflexivVerb").value(DEFAULT_IS_REFLEXIV_VERB.booleanValue()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC.toString()))
            .andExpect(jsonPath("$.conjugation").value(DEFAULT_CONJUGATION.toString()))
            .andExpect(jsonPath("$.reflexivPronoun").value(DEFAULT_REFLEXIV_PRONOUN.toString()))
            .andExpect(jsonPath("$.perfective").value(DEFAULT_PERFECTIVE.toString()))
            .andExpect(jsonPath("$.imperfective").value(DEFAULT_IMPERFECTIVE.toString()))
            .andExpect(jsonPath("$.infinitive").value(DEFAULT_INFINITIVE.toString()))
            .andExpect(jsonPath("$.impersonalFromPast").value(DEFAULT_IMPERSONAL_FROM_PAST.toString()))
            .andExpect(jsonPath("$.adverbialParticipleContemporary").value(DEFAULT_ADVERBIAL_PARTICIPLE_CONTEMPORARY.toString()))
            .andExpect(jsonPath("$.adverbialParticiplePrior").value(DEFAULT_ADVERBIAL_PARTICIPLE_PRIOR.toString()))
            .andExpect(jsonPath("$.gerund").value(DEFAULT_GERUND.toString()));
    }

    @Test
    public void getNonExistingVerbVar() throws Exception {
        // Get the verbVar
        restVerbVarMockMvc.perform(get("/api/verb-vars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVerbVar() throws Exception {
        // Initialize the database
        verbVarRepository.save(verbVar);
        int databaseSizeBeforeUpdate = verbVarRepository.findAll().size();

        // Update the verbVar
        VerbVar updatedVerbVar = verbVarRepository.findOne(verbVar.getId());
        updatedVerbVar
            .isPerfective(UPDATED_IS_PERFECTIVE)
            .isReflexivVerb(UPDATED_IS_REFLEXIV_VERB)
            .topic(UPDATED_TOPIC)
            .conjugation(UPDATED_CONJUGATION)
            .reflexivPronoun(UPDATED_REFLEXIV_PRONOUN)
            .perfective(UPDATED_PERFECTIVE)
            .imperfective(UPDATED_IMPERFECTIVE)
            .infinitive(UPDATED_INFINITIVE)
            .impersonalFromPast(UPDATED_IMPERSONAL_FROM_PAST)
            .adverbialParticipleContemporary(UPDATED_ADVERBIAL_PARTICIPLE_CONTEMPORARY)
            .adverbialParticiplePrior(UPDATED_ADVERBIAL_PARTICIPLE_PRIOR)
            .gerund(UPDATED_GERUND);
        VerbVarDTO verbVarDTO = verbVarMapper.toDto(updatedVerbVar);

        restVerbVarMockMvc.perform(put("/api/verb-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verbVarDTO)))
            .andExpect(status().isOk());

        // Validate the VerbVar in the database
        List<VerbVar> verbVarList = verbVarRepository.findAll();
        assertThat(verbVarList).hasSize(databaseSizeBeforeUpdate);
        VerbVar testVerbVar = verbVarList.get(verbVarList.size() - 1);
        assertThat(testVerbVar.isIsPerfective()).isEqualTo(UPDATED_IS_PERFECTIVE);
        assertThat(testVerbVar.isIsReflexivVerb()).isEqualTo(UPDATED_IS_REFLEXIV_VERB);
        assertThat(testVerbVar.getTopic()).isEqualTo(UPDATED_TOPIC);
        assertThat(testVerbVar.getConjugation()).isEqualTo(UPDATED_CONJUGATION);
        assertThat(testVerbVar.getReflexivPronoun()).isEqualTo(UPDATED_REFLEXIV_PRONOUN);
        assertThat(testVerbVar.getPerfective()).isEqualTo(UPDATED_PERFECTIVE);
        assertThat(testVerbVar.getImperfective()).isEqualTo(UPDATED_IMPERFECTIVE);
        assertThat(testVerbVar.getInfinitive()).isEqualTo(UPDATED_INFINITIVE);
        assertThat(testVerbVar.getImpersonalFormPast()).isEqualTo(UPDATED_IMPERSONAL_FROM_PAST);
        assertThat(testVerbVar.getAdverbialParticipleContemporary()).isEqualTo(UPDATED_ADVERBIAL_PARTICIPLE_CONTEMPORARY);
        assertThat(testVerbVar.getAdverbialParticiplePrior()).isEqualTo(UPDATED_ADVERBIAL_PARTICIPLE_PRIOR);
        assertThat(testVerbVar.getGerund()).isEqualTo(UPDATED_GERUND);
    }

    @Test
    public void updateNonExistingVerbVar() throws Exception {
        int databaseSizeBeforeUpdate = verbVarRepository.findAll().size();

        // Create the VerbVar
        VerbVarDTO verbVarDTO = verbVarMapper.toDto(verbVar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVerbVarMockMvc.perform(put("/api/verb-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verbVarDTO)))
            .andExpect(status().isCreated());

        // Validate the VerbVar in the database
        List<VerbVar> verbVarList = verbVarRepository.findAll();
        assertThat(verbVarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVerbVar() throws Exception {
        // Initialize the database
        verbVarRepository.save(verbVar);
        int databaseSizeBeforeDelete = verbVarRepository.findAll().size();

        // Get the verbVar
        restVerbVarMockMvc.perform(delete("/api/verb-vars/{id}", verbVar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VerbVar> verbVarList = verbVarRepository.findAll();
        assertThat(verbVarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VerbVar.class);
        VerbVar verbVar1 = new VerbVar();
        verbVar1.setId("id1");
        VerbVar verbVar2 = new VerbVar();
        verbVar2.setId(verbVar1.getId());
        assertThat(verbVar1).isEqualTo(verbVar2);
        verbVar2.setId("id2");
        assertThat(verbVar1).isNotEqualTo(verbVar2);
        verbVar1.setId(null);
        assertThat(verbVar1).isNotEqualTo(verbVar2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VerbVarDTO.class);
        VerbVarDTO verbVarDTO1 = new VerbVarDTO();
        verbVarDTO1.setId("id1");
        VerbVarDTO verbVarDTO2 = new VerbVarDTO();
        assertThat(verbVarDTO1).isNotEqualTo(verbVarDTO2);
        verbVarDTO2.setId(verbVarDTO1.getId());
        assertThat(verbVarDTO1).isEqualTo(verbVarDTO2);
        verbVarDTO2.setId("id2");
        assertThat(verbVarDTO1).isNotEqualTo(verbVarDTO2);
        verbVarDTO1.setId(null);
        assertThat(verbVarDTO1).isNotEqualTo(verbVarDTO2);
    }
}
