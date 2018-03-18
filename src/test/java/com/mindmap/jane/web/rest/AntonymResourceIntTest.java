package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.Antonym;
import com.mindmap.jane.domain.Link;
import com.mindmap.jane.repository.AntonymRepository;
import com.mindmap.jane.service.AntonymService;
import com.mindmap.jane.service.dto.AntonymDTO;
import com.mindmap.jane.service.mapper.AntonymMapper;
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
 * Test class for the AntonymResource REST controller.
 *
 * @see AntonymResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class AntonymResourceIntTest {

    private static final Link DEFAULT_VALUE = new Link("AAAAAAAAAA");
    private static final Link UPDATED_VALUE = new Link("BBBBBBBBBB");

    @Autowired
    private AntonymRepository antonymRepository;

    @Autowired
    private AntonymMapper antonymMapper;

    @Autowired
    private AntonymService antonymService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAntonymMockMvc;

    private Antonym antonym;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AntonymResource antonymResource = new AntonymResource(antonymService);
        this.restAntonymMockMvc = MockMvcBuilders.standaloneSetup(antonymResource)
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
    public static Antonym createEntity() {
        Antonym antonym = new Antonym()
            .link(DEFAULT_VALUE);
        return antonym;
    }

    @Before
    public void initTest() {
        antonymRepository.deleteAll();
        antonym = createEntity();
    }

    @Test
    public void createAntonym() throws Exception {
        int databaseSizeBeforeCreate = antonymRepository.findAll().size();

        // Create the Antonym
        AntonymDTO antonymDTO = antonymMapper.toDto(antonym);
        restAntonymMockMvc.perform(post("/api/antonyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antonymDTO)))
            .andExpect(status().isCreated());

        // Validate the Antonym in the database
        List<Antonym> antonymList = antonymRepository.findAll();
        assertThat(antonymList).hasSize(databaseSizeBeforeCreate + 1);
        Antonym testAntonym = antonymList.get(antonymList.size() - 1);
        assertThat(testAntonym.getLink()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createAntonymWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = antonymRepository.findAll().size();

        // Create the Antonym with an existing ID
        antonym.setId("existing_id");
        AntonymDTO antonymDTO = antonymMapper.toDto(antonym);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntonymMockMvc.perform(post("/api/antonyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antonymDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Antonym in the database
        List<Antonym> antonymList = antonymRepository.findAll();
        assertThat(antonymList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAntonyms() throws Exception {
        // Initialize the database
        antonymRepository.save(antonym);

        // Get all the antonymList
        restAntonymMockMvc.perform(get("/api/antonyms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antonym.getId())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    public void getAntonym() throws Exception {
        // Initialize the database
        antonymRepository.save(antonym);

        // Get the antonym
        restAntonymMockMvc.perform(get("/api/antonyms/{id}", antonym.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(antonym.getId()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingAntonym() throws Exception {
        // Get the antonym
        restAntonymMockMvc.perform(get("/api/antonyms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAntonym() throws Exception {
        // Initialize the database
        antonymRepository.save(antonym);
        int databaseSizeBeforeUpdate = antonymRepository.findAll().size();

        // Update the antonym
        Antonym updatedAntonym = antonymRepository.findOne(antonym.getId());
        updatedAntonym
            .link(UPDATED_VALUE);
        AntonymDTO antonymDTO = antonymMapper.toDto(updatedAntonym);

        restAntonymMockMvc.perform(put("/api/antonyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antonymDTO)))
            .andExpect(status().isOk());

        // Validate the Antonym in the database
        List<Antonym> antonymList = antonymRepository.findAll();
        assertThat(antonymList).hasSize(databaseSizeBeforeUpdate);
        Antonym testAntonym = antonymList.get(antonymList.size() - 1);
        assertThat(testAntonym.getLink()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingAntonym() throws Exception {
        int databaseSizeBeforeUpdate = antonymRepository.findAll().size();

        // Create the Antonym
        AntonymDTO antonymDTO = antonymMapper.toDto(antonym);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAntonymMockMvc.perform(put("/api/antonyms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(antonymDTO)))
            .andExpect(status().isCreated());

        // Validate the Antonym in the database
        List<Antonym> antonymList = antonymRepository.findAll();
        assertThat(antonymList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAntonym() throws Exception {
        // Initialize the database
        antonymRepository.save(antonym);
        int databaseSizeBeforeDelete = antonymRepository.findAll().size();

        // Get the antonym
        restAntonymMockMvc.perform(delete("/api/antonyms/{id}", antonym.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Antonym> antonymList = antonymRepository.findAll();
        assertThat(antonymList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Antonym.class);
        Antonym antonym1 = new Antonym();
        antonym1.setId("id1");
        Antonym antonym2 = new Antonym();
        antonym2.setId(antonym1.getId());
        assertThat(antonym1).isEqualTo(antonym2);
        antonym2.setId("id2");
        assertThat(antonym1).isNotEqualTo(antonym2);
        antonym1.setId(null);
        assertThat(antonym1).isNotEqualTo(antonym2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AntonymDTO.class);
        AntonymDTO antonymDTO1 = new AntonymDTO();
        antonymDTO1.setId("id1");
        AntonymDTO antonymDTO2 = new AntonymDTO();
        assertThat(antonymDTO1).isNotEqualTo(antonymDTO2);
        antonymDTO2.setId(antonymDTO1.getId());
        assertThat(antonymDTO1).isEqualTo(antonymDTO2);
        antonymDTO2.setId("id2");
        assertThat(antonymDTO1).isNotEqualTo(antonymDTO2);
        antonymDTO1.setId(null);
        assertThat(antonymDTO1).isNotEqualTo(antonymDTO2);
    }
}
