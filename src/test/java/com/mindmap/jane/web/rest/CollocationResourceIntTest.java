package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.Collocation;
import com.mindmap.jane.domain.Sentence;
import com.mindmap.jane.repository.CollocationRepository;
import com.mindmap.jane.service.CollocationService;
import com.mindmap.jane.service.dto.CollocationDTO;
import com.mindmap.jane.service.mapper.CollocationMapper;
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
 * Test class for the CollocationResource REST controller.
 *
 * @see CollocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class CollocationResourceIntTest {

    private static final Sentence DEFAULT_VALUE = new Sentence("AAAAAAAAAA");
    private static final Sentence UPDATED_VALUE = new Sentence("BBBBBBBBBB");

    @Autowired
    private CollocationRepository collocationRepository;

    @Autowired
    private CollocationMapper collocationMapper;

    @Autowired
    private CollocationService collocationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCollocationMockMvc;

    private Collocation collocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollocationResource collocationResource = new CollocationResource(collocationService);
        this.restCollocationMockMvc = MockMvcBuilders.standaloneSetup(collocationResource)
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
    public static Collocation createEntity() {
        Collocation collocation = new Collocation()
            .sentence(DEFAULT_VALUE);
        return collocation;
    }

    @Before
    public void initTest() {
        collocationRepository.deleteAll();
        collocation = createEntity();
    }

    @Test
    public void createCollocation() throws Exception {
        int databaseSizeBeforeCreate = collocationRepository.findAll().size();

        // Create the Collocation
        CollocationDTO collocationDTO = collocationMapper.toDto(collocation);
        restCollocationMockMvc.perform(post("/api/collocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collocationDTO)))
            .andExpect(status().isCreated());

        // Validate the Collocation in the database
        List<Collocation> collocationList = collocationRepository.findAll();
        assertThat(collocationList).hasSize(databaseSizeBeforeCreate + 1);
        Collocation testCollocation = collocationList.get(collocationList.size() - 1);
        assertThat(testCollocation.getSentence()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createCollocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collocationRepository.findAll().size();

        // Create the Collocation with an existing ID
        collocation.setId("existing_id");
        CollocationDTO collocationDTO = collocationMapper.toDto(collocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollocationMockMvc.perform(post("/api/collocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collocation in the database
        List<Collocation> collocationList = collocationRepository.findAll();
        assertThat(collocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCollocations() throws Exception {
        // Initialize the database
        collocationRepository.save(collocation);

        // Get all the collocationList
        restCollocationMockMvc.perform(get("/api/collocations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collocation.getId())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    public void getCollocation() throws Exception {
        // Initialize the database
        collocationRepository.save(collocation);

        // Get the collocation
        restCollocationMockMvc.perform(get("/api/collocations/{id}", collocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collocation.getId()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingCollocation() throws Exception {
        // Get the collocation
        restCollocationMockMvc.perform(get("/api/collocations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCollocation() throws Exception {
        // Initialize the database
        collocationRepository.save(collocation);
        int databaseSizeBeforeUpdate = collocationRepository.findAll().size();

        // Update the collocation
        Collocation updatedCollocation = collocationRepository.findOne(collocation.getId());
        updatedCollocation
            .sentence(UPDATED_VALUE);
        CollocationDTO collocationDTO = collocationMapper.toDto(updatedCollocation);

        restCollocationMockMvc.perform(put("/api/collocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collocationDTO)))
            .andExpect(status().isOk());

        // Validate the Collocation in the database
        List<Collocation> collocationList = collocationRepository.findAll();
        assertThat(collocationList).hasSize(databaseSizeBeforeUpdate);
        Collocation testCollocation = collocationList.get(collocationList.size() - 1);
        assertThat(testCollocation.getSentence()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingCollocation() throws Exception {
        int databaseSizeBeforeUpdate = collocationRepository.findAll().size();

        // Create the Collocation
        CollocationDTO collocationDTO = collocationMapper.toDto(collocation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollocationMockMvc.perform(put("/api/collocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collocationDTO)))
            .andExpect(status().isCreated());

        // Validate the Collocation in the database
        List<Collocation> collocationList = collocationRepository.findAll();
        assertThat(collocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCollocation() throws Exception {
        // Initialize the database
        collocationRepository.save(collocation);
        int databaseSizeBeforeDelete = collocationRepository.findAll().size();

        // Get the collocation
        restCollocationMockMvc.perform(delete("/api/collocations/{id}", collocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Collocation> collocationList = collocationRepository.findAll();
        assertThat(collocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collocation.class);
        Collocation collocation1 = new Collocation();
        collocation1.setId("id1");
        Collocation collocation2 = new Collocation();
        collocation2.setId(collocation1.getId());
        assertThat(collocation1).isEqualTo(collocation2);
        collocation2.setId("id2");
        assertThat(collocation1).isNotEqualTo(collocation2);
        collocation1.setId(null);
        assertThat(collocation1).isNotEqualTo(collocation2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollocationDTO.class);
        CollocationDTO collocationDTO1 = new CollocationDTO();
        collocationDTO1.setId("id1");
        CollocationDTO collocationDTO2 = new CollocationDTO();
        assertThat(collocationDTO1).isNotEqualTo(collocationDTO2);
        collocationDTO2.setId(collocationDTO1.getId());
        assertThat(collocationDTO1).isEqualTo(collocationDTO2);
        collocationDTO2.setId("id2");
        assertThat(collocationDTO1).isNotEqualTo(collocationDTO2);
        collocationDTO1.setId(null);
        assertThat(collocationDTO1).isNotEqualTo(collocationDTO2);
    }
}
