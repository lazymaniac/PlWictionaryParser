package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.CasesVar;
import com.mindmap.jane.domain.enumeration.CasesFormQualifier;
import com.mindmap.jane.repository.CasesVarRepository;
import com.mindmap.jane.service.CasesVarService;
import com.mindmap.jane.service.dto.CasesVarDTO;
import com.mindmap.jane.service.mapper.CasesVarMapper;
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
 * Test class for the CasesVarResource REST controller.
 *
 * @see CasesVarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class CasesVarResourceIntTest {

    private static final CasesFormQualifier DEFAULT_TYPE = CasesFormQualifier.NOUN_SING;
    private static final CasesFormQualifier UPDATED_TYPE = CasesFormQualifier.NOUN_PLUR;

    private static final String DEFAULT_MIANOWNIK = "AAAAAAAAAA";
    private static final String UPDATED_MIANOWNIK = "BBBBBBBBBB";

    private static final String DEFAULT_DOPELNIACZ = "AAAAAAAAAA";
    private static final String UPDATED_DOPELNIACZ = "BBBBBBBBBB";

    private static final String DEFAULT_CELOWNIK = "AAAAAAAAAA";
    private static final String UPDATED_CELOWNIK = "BBBBBBBBBB";

    private static final String DEFAULT_BIERNIK = "AAAAAAAAAA";
    private static final String UPDATED_BIERNIK = "BBBBBBBBBB";

    private static final String DEFAULT_NARZEDNIK = "AAAAAAAAAA";
    private static final String UPDATED_NARZEDNIK = "BBBBBBBBBB";

    private static final String DEFAULT_MIEJSCOWNIK = "AAAAAAAAAA";
    private static final String UPDATED_MIEJSCOWNIK = "BBBBBBBBBB";

    private static final String DEFAULT_WOLACZ = "AAAAAAAAAA";
    private static final String UPDATED_WOLACZ = "BBBBBBBBBB";

    @Autowired
    private CasesVarRepository casesVarRepository;

    @Autowired
    private CasesVarMapper casesVarMapper;

    @Autowired
    private CasesVarService casesVarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCasesVarMockMvc;

    private CasesVar casesVar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CasesVarResource casesVarResource = new CasesVarResource(casesVarService);
        this.restCasesVarMockMvc = MockMvcBuilders.standaloneSetup(casesVarResource)
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
    public static CasesVar createEntity() {
        CasesVar casesVar = new CasesVar()
            .type(DEFAULT_TYPE)
            .mianownik(DEFAULT_MIANOWNIK)
            .dopelniacz(DEFAULT_DOPELNIACZ)
            .celownik(DEFAULT_CELOWNIK)
            .biernik(DEFAULT_BIERNIK)
            .narzednik(DEFAULT_NARZEDNIK)
            .miejscownik(DEFAULT_MIEJSCOWNIK)
            .wolacz(DEFAULT_WOLACZ);
        return casesVar;
    }

    @Before
    public void initTest() {
        casesVarRepository.deleteAll();
        casesVar = createEntity();
    }

    @Test
    public void createCasesVar() throws Exception {
        int databaseSizeBeforeCreate = casesVarRepository.findAll().size();

        // Create the CasesVar
        CasesVarDTO casesVarDTO = casesVarMapper.toDto(casesVar);
        restCasesVarMockMvc.perform(post("/api/cases-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(casesVarDTO)))
            .andExpect(status().isCreated());

        // Validate the CasesVar in the database
        List<CasesVar> casesVarList = casesVarRepository.findAll();
        assertThat(casesVarList).hasSize(databaseSizeBeforeCreate + 1);
        CasesVar testCasesVar = casesVarList.get(casesVarList.size() - 1);
        assertThat(testCasesVar.getCasesType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCasesVar.getMianownik()).isEqualTo(DEFAULT_MIANOWNIK);
        assertThat(testCasesVar.getDopelniacz()).isEqualTo(DEFAULT_DOPELNIACZ);
        assertThat(testCasesVar.getCelownik()).isEqualTo(DEFAULT_CELOWNIK);
        assertThat(testCasesVar.getBiernik()).isEqualTo(DEFAULT_BIERNIK);
        assertThat(testCasesVar.getNarzednik()).isEqualTo(DEFAULT_NARZEDNIK);
        assertThat(testCasesVar.getMiejscownik()).isEqualTo(DEFAULT_MIEJSCOWNIK);
        assertThat(testCasesVar.getWolacz()).isEqualTo(DEFAULT_WOLACZ);
    }

    @Test
    public void createCasesVarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = casesVarRepository.findAll().size();

        // Create the CasesVar with an existing ID
        casesVar.setId("existing_id");
        CasesVarDTO casesVarDTO = casesVarMapper.toDto(casesVar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCasesVarMockMvc.perform(post("/api/cases-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(casesVarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CasesVar in the database
        List<CasesVar> casesVarList = casesVarRepository.findAll();
        assertThat(casesVarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCasesVars() throws Exception {
        // Initialize the database
        casesVarRepository.save(casesVar);

        // Get all the casesVarList
        restCasesVarMockMvc.perform(get("/api/cases-vars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(casesVar.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mianownik").value(hasItem(DEFAULT_MIANOWNIK.toString())))
            .andExpect(jsonPath("$.[*].dopelniacz").value(hasItem(DEFAULT_DOPELNIACZ.toString())))
            .andExpect(jsonPath("$.[*].celownik").value(hasItem(DEFAULT_CELOWNIK.toString())))
            .andExpect(jsonPath("$.[*].biernik").value(hasItem(DEFAULT_BIERNIK.toString())))
            .andExpect(jsonPath("$.[*].narzednik").value(hasItem(DEFAULT_NARZEDNIK.toString())))
            .andExpect(jsonPath("$.[*].miejscownik").value(hasItem(DEFAULT_MIEJSCOWNIK.toString())))
            .andExpect(jsonPath("$.[*].wolacz").value(hasItem(DEFAULT_WOLACZ.toString())));
    }

    @Test
    public void getCasesVar() throws Exception {
        // Initialize the database
        casesVarRepository.save(casesVar);

        // Get the casesVar
        restCasesVarMockMvc.perform(get("/api/cases-vars/{id}", casesVar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(casesVar.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.mianownik").value(DEFAULT_MIANOWNIK.toString()))
            .andExpect(jsonPath("$.dopelniacz").value(DEFAULT_DOPELNIACZ.toString()))
            .andExpect(jsonPath("$.celownik").value(DEFAULT_CELOWNIK.toString()))
            .andExpect(jsonPath("$.biernik").value(DEFAULT_BIERNIK.toString()))
            .andExpect(jsonPath("$.narzednik").value(DEFAULT_NARZEDNIK.toString()))
            .andExpect(jsonPath("$.miejscownik").value(DEFAULT_MIEJSCOWNIK.toString()))
            .andExpect(jsonPath("$.wolacz").value(DEFAULT_WOLACZ.toString()));
    }

    @Test
    public void getNonExistingCasesVar() throws Exception {
        // Get the casesVar
        restCasesVarMockMvc.perform(get("/api/cases-vars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCasesVar() throws Exception {
        // Initialize the database
        casesVarRepository.save(casesVar);
        int databaseSizeBeforeUpdate = casesVarRepository.findAll().size();

        // Update the casesVar
        CasesVar updatedCasesVar = casesVarRepository.findOne(casesVar.getId());
        updatedCasesVar
            .type(UPDATED_TYPE)
            .mianownik(UPDATED_MIANOWNIK)
            .dopelniacz(UPDATED_DOPELNIACZ)
            .celownik(UPDATED_CELOWNIK)
            .biernik(UPDATED_BIERNIK)
            .narzednik(UPDATED_NARZEDNIK)
            .miejscownik(UPDATED_MIEJSCOWNIK)
            .wolacz(UPDATED_WOLACZ);
        CasesVarDTO casesVarDTO = casesVarMapper.toDto(updatedCasesVar);

        restCasesVarMockMvc.perform(put("/api/cases-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(casesVarDTO)))
            .andExpect(status().isOk());

        // Validate the CasesVar in the database
        List<CasesVar> casesVarList = casesVarRepository.findAll();
        assertThat(casesVarList).hasSize(databaseSizeBeforeUpdate);
        CasesVar testCasesVar = casesVarList.get(casesVarList.size() - 1);
        assertThat(testCasesVar.getCasesType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCasesVar.getMianownik()).isEqualTo(UPDATED_MIANOWNIK);
        assertThat(testCasesVar.getDopelniacz()).isEqualTo(UPDATED_DOPELNIACZ);
        assertThat(testCasesVar.getCelownik()).isEqualTo(UPDATED_CELOWNIK);
        assertThat(testCasesVar.getBiernik()).isEqualTo(UPDATED_BIERNIK);
        assertThat(testCasesVar.getNarzednik()).isEqualTo(UPDATED_NARZEDNIK);
        assertThat(testCasesVar.getMiejscownik()).isEqualTo(UPDATED_MIEJSCOWNIK);
        assertThat(testCasesVar.getWolacz()).isEqualTo(UPDATED_WOLACZ);
    }

    @Test
    public void updateNonExistingCasesVar() throws Exception {
        int databaseSizeBeforeUpdate = casesVarRepository.findAll().size();

        // Create the CasesVar
        CasesVarDTO casesVarDTO = casesVarMapper.toDto(casesVar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCasesVarMockMvc.perform(put("/api/cases-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(casesVarDTO)))
            .andExpect(status().isCreated());

        // Validate the CasesVar in the database
        List<CasesVar> casesVarList = casesVarRepository.findAll();
        assertThat(casesVarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCasesVar() throws Exception {
        // Initialize the database
        casesVarRepository.save(casesVar);
        int databaseSizeBeforeDelete = casesVarRepository.findAll().size();

        // Get the casesVar
        restCasesVarMockMvc.perform(delete("/api/cases-vars/{id}", casesVar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CasesVar> casesVarList = casesVarRepository.findAll();
        assertThat(casesVarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CasesVar.class);
        CasesVar casesVar1 = new CasesVar();
        casesVar1.setId("id1");
        CasesVar casesVar2 = new CasesVar();
        casesVar2.setId(casesVar1.getId());
        assertThat(casesVar1).isEqualTo(casesVar2);
        casesVar2.setId("id2");
        assertThat(casesVar1).isNotEqualTo(casesVar2);
        casesVar1.setId(null);
        assertThat(casesVar1).isNotEqualTo(casesVar2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CasesVarDTO.class);
        CasesVarDTO casesVarDTO1 = new CasesVarDTO();
        casesVarDTO1.setId("id1");
        CasesVarDTO casesVarDTO2 = new CasesVarDTO();
        assertThat(casesVarDTO1).isNotEqualTo(casesVarDTO2);
        casesVarDTO2.setId(casesVarDTO1.getId());
        assertThat(casesVarDTO1).isEqualTo(casesVarDTO2);
        casesVarDTO2.setId("id2");
        assertThat(casesVarDTO1).isNotEqualTo(casesVarDTO2);
        casesVarDTO1.setId(null);
        assertThat(casesVarDTO1).isNotEqualTo(casesVarDTO2);
    }
}
