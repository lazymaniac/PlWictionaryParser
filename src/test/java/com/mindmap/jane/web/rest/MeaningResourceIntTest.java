package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.Meaning;
import com.mindmap.jane.repository.MeaningRepository;
import com.mindmap.jane.service.MeaningService;
import com.mindmap.jane.service.dto.MeaningDTO;
import com.mindmap.jane.service.mapper.MeaningMapper;
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
 * Test class for the ImportanceResource REST controller.
 *
 * @see MeaningResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class MeaningResourceIntTest {

    private static final Boolean DEFAULT_VALUE = false;
    private static final Boolean UPDATED_VALUE = true;

    @Autowired
    private MeaningRepository meaningRepository;

    @Autowired
    private MeaningMapper meaningMapper;

    @Autowired
    private MeaningService meaningService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restImportanceMockMvc;

    private Meaning meaning;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeaningResource meaningResource = new MeaningResource(meaningService);
        this.restImportanceMockMvc = MockMvcBuilders.standaloneSetup(meaningResource)
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
    public static Meaning createEntity() {
        Meaning meaning = new Meaning();
            meaning.setHasLinkToWikipedia(DEFAULT_VALUE);
        return meaning;
    }

    @Before
    public void initTest() {
        meaningRepository.deleteAll();
        meaning = createEntity();
    }

    @Test
    public void createImportance() throws Exception {
        int databaseSizeBeforeCreate = meaningRepository.findAll().size();

        // Create the Meaning
        MeaningDTO meaningDTO = meaningMapper.toDto(meaning);
        restImportanceMockMvc.perform(post("/api/importances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meaningDTO)))
            .andExpect(status().isCreated());

        // Validate the Meaning in the database
        List<Meaning> meaningList = meaningRepository.findAll();
        assertThat(meaningList).hasSize(databaseSizeBeforeCreate + 1);
        Meaning testMeaning = meaningList.get(meaningList.size() - 1);
        assertThat(testMeaning.isHasLinkToWikipedia()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createImportanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meaningRepository.findAll().size();

        // Create the Meaning with an existing ID
        meaning.setId("existing_id");
        MeaningDTO meaningDTO = meaningMapper.toDto(meaning);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImportanceMockMvc.perform(post("/api/importances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meaningDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meaning in the database
        List<Meaning> meaningList = meaningRepository.findAll();
        assertThat(meaningList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllImportances() throws Exception {
        // Initialize the database
        meaningRepository.save(meaning);

        // Get all the importanceList
        restImportanceMockMvc.perform(get("/api/importances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meaning.getId())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    public void getImportance() throws Exception {
        // Initialize the database
        meaningRepository.save(meaning);

        // Get the meaning
        restImportanceMockMvc.perform(get("/api/importances/{id}", meaning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meaning.getId()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingImportance() throws Exception {
        // Get the meaning
        restImportanceMockMvc.perform(get("/api/importances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateImportance() throws Exception {
        // Initialize the database
        meaningRepository.save(meaning);
        int databaseSizeBeforeUpdate = meaningRepository.findAll().size();

        // Update the meaning
        Meaning updatedMeaning = meaningRepository.findOne(meaning.getId());
        updatedMeaning.setHasLinkToWikipedia(UPDATED_VALUE);
        MeaningDTO meaningDTO = meaningMapper.toDto(updatedMeaning);

        restImportanceMockMvc.perform(put("/api/importances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meaningDTO)))
            .andExpect(status().isOk());

        // Validate the Meaning in the database
        List<Meaning> meaningList = meaningRepository.findAll();
        assertThat(meaningList).hasSize(databaseSizeBeforeUpdate);
        Meaning testMeaning = meaningList.get(meaningList.size() - 1);
        assertThat(testMeaning.isHasLinkToWikipedia()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingImportance() throws Exception {
        int databaseSizeBeforeUpdate = meaningRepository.findAll().size();

        // Create the Meaning
        MeaningDTO meaningDTO = meaningMapper.toDto(meaning);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImportanceMockMvc.perform(put("/api/importances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meaningDTO)))
            .andExpect(status().isCreated());

        // Validate the Meaning in the database
        List<Meaning> meaningList = meaningRepository.findAll();
        assertThat(meaningList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteImportance() throws Exception {
        // Initialize the database
        meaningRepository.save(meaning);
        int databaseSizeBeforeDelete = meaningRepository.findAll().size();

        // Get the meaning
        restImportanceMockMvc.perform(delete("/api/importances/{id}", meaning.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Meaning> meaningList = meaningRepository.findAll();
        assertThat(meaningList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meaning.class);
        Meaning meaning1 = new Meaning();
        meaning1.setId("id1");
        Meaning meaning2 = new Meaning();
        meaning2.setId(meaning1.getId());
        assertThat(meaning1).isEqualTo(meaning2);
        meaning2.setId("id2");
        assertThat(meaning1).isNotEqualTo(meaning2);
        meaning1.setId(null);
        assertThat(meaning1).isNotEqualTo(meaning2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeaningDTO.class);
        MeaningDTO meaningDTO1 = new MeaningDTO();
        meaningDTO1.setId("id1");
        MeaningDTO meaningDTO2 = new MeaningDTO();
        assertThat(meaningDTO1).isNotEqualTo(meaningDTO2);
        meaningDTO2.setId(meaningDTO1.getId());
        assertThat(meaningDTO1).isEqualTo(meaningDTO2);
        meaningDTO2.setId("id2");
        assertThat(meaningDTO1).isNotEqualTo(meaningDTO2);
        meaningDTO1.setId(null);
        assertThat(meaningDTO1).isNotEqualTo(meaningDTO2);
    }
}
