package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.Synonym;
import com.mindmap.jane.repository.SynonymRepository;
import com.mindmap.jane.service.SynonymService;
import com.mindmap.jane.service.dto.SynonymDTO;
import com.mindmap.jane.service.mapper.SynonymMapper;
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
 * Test class for the SynonymResource REST controller.
 *
 * @see SynonymResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class SynonymResourceIntTest {

    private static final Link DEFAULT_VALUE = new Link("AAAAAAAAAA");
    private static final Link UPDATED_VALUE = new Link("BBBBBBBBBB");

    @Autowired
    private SynonymRepository synonymRepository;

    @Autowired
    private SynonymMapper synonymMapper;

    @Autowired
    private SynonymService synonymService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSynonymMockMvc;

    private Synonym synonym;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SynonymResource synonymResource = new SynonymResource(synonymService);
        this.restSynonymMockMvc = MockMvcBuilders.standaloneSetup(synonymResource)
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
    public static Synonym createEntity() {
        Synonym synonym = new Synonym()
            .value(DEFAULT_VALUE);
        return synonym;
    }

    @Before
    public void initTest() {
        synonymRepository.deleteAll();
        synonym = createEntity();
    }

    @Test
    public void createSynonym() throws Exception {
        int databaseSizeBeforeCreate = synonymRepository.findAll().size();

        // Create the Synonym
        SynonymDTO synonymDTO = synonymMapper.toDto(synonym);
        restSynonymMockMvc.perform(post("/api/synonyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(synonymDTO)))
            .andExpect(status().isCreated());

        // Validate the Synonym in the database
        List<Synonym> synonymList = synonymRepository.findAll();
        assertThat(synonymList).hasSize(databaseSizeBeforeCreate + 1);
        Synonym testSynonym = synonymList.get(synonymList.size() - 1);
        assertThat(testSynonym.getLink()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createSynonymWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = synonymRepository.findAll().size();

        // Create the Synonym with an existing ID
        synonym.setId("existing_id");
        SynonymDTO synonymDTO = synonymMapper.toDto(synonym);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSynonymMockMvc.perform(post("/api/synonyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(synonymDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Synonym in the database
        List<Synonym> synonymList = synonymRepository.findAll();
        assertThat(synonymList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllSynonyms() throws Exception {
        // Initialize the database
        synonymRepository.save(synonym);

        // Get all the synonymList
        restSynonymMockMvc.perform(get("/api/synonyms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(synonym.getId())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    public void getSynonym() throws Exception {
        // Initialize the database
        synonymRepository.save(synonym);

        // Get the synonym
        restSynonymMockMvc.perform(get("/api/synonyms/{id}", synonym.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(synonym.getId()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingSynonym() throws Exception {
        // Get the synonym
        restSynonymMockMvc.perform(get("/api/synonyms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSynonym() throws Exception {
        // Initialize the database
        synonymRepository.save(synonym);
        int databaseSizeBeforeUpdate = synonymRepository.findAll().size();

        // Update the synonym
        Synonym updatedSynonym = synonymRepository.findOne(synonym.getId());
        updatedSynonym
            .value(UPDATED_VALUE);
        SynonymDTO synonymDTO = synonymMapper.toDto(updatedSynonym);

        restSynonymMockMvc.perform(put("/api/synonyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(synonymDTO)))
            .andExpect(status().isOk());

        // Validate the Synonym in the database
        List<Synonym> synonymList = synonymRepository.findAll();
        assertThat(synonymList).hasSize(databaseSizeBeforeUpdate);
        Synonym testSynonym = synonymList.get(synonymList.size() - 1);
        assertThat(testSynonym.getLink()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingSynonym() throws Exception {
        int databaseSizeBeforeUpdate = synonymRepository.findAll().size();

        // Create the Synonym
        SynonymDTO synonymDTO = synonymMapper.toDto(synonym);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSynonymMockMvc.perform(put("/api/synonyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(synonymDTO)))
            .andExpect(status().isCreated());

        // Validate the Synonym in the database
        List<Synonym> synonymList = synonymRepository.findAll();
        assertThat(synonymList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteSynonym() throws Exception {
        // Initialize the database
        synonymRepository.save(synonym);
        int databaseSizeBeforeDelete = synonymRepository.findAll().size();

        // Get the synonym
        restSynonymMockMvc.perform(delete("/api/synonyms/{id}", synonym.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Synonym> synonymList = synonymRepository.findAll();
        assertThat(synonymList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Synonym.class);
        Synonym synonym1 = new Synonym();
        synonym1.setId("id1");
        Synonym synonym2 = new Synonym();
        synonym2.setId(synonym1.getId());
        assertThat(synonym1).isEqualTo(synonym2);
        synonym2.setId("id2");
        assertThat(synonym1).isNotEqualTo(synonym2);
        synonym1.setId(null);
        assertThat(synonym1).isNotEqualTo(synonym2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SynonymDTO.class);
        SynonymDTO synonymDTO1 = new SynonymDTO();
        synonymDTO1.setId("id1");
        SynonymDTO synonymDTO2 = new SynonymDTO();
        assertThat(synonymDTO1).isNotEqualTo(synonymDTO2);
        synonymDTO2.setId(synonymDTO1.getId());
        assertThat(synonymDTO1).isEqualTo(synonymDTO2);
        synonymDTO2.setId("id2");
        assertThat(synonymDTO1).isNotEqualTo(synonymDTO2);
        synonymDTO1.setId(null);
        assertThat(synonymDTO1).isNotEqualTo(synonymDTO2);
    }
}
