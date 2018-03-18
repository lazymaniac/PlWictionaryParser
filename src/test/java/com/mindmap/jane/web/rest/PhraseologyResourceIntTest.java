package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;
import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.Phraseology;
import com.mindmap.jane.repository.PhraseologyRepository;
import com.mindmap.jane.service.PhraseologyService;
import com.mindmap.jane.service.dto.PhraseologyDTO;
import com.mindmap.jane.service.mapper.PhraseologyMapper;
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
 * Test class for the PhraseologyResource REST controller.
 *
 * @see PhraseologyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class PhraseologyResourceIntTest {

    private static final Link DEFAULT_VALUE = new Link("AAAAAAAAAA");
    private static final Link UPDATED_VALUE = new Link("BBBBBBBBBB");

    @Autowired
    private PhraseologyRepository phraseologyRepository;

    @Autowired
    private PhraseologyMapper phraseologyMapper;

    @Autowired
    private PhraseologyService phraseologyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPhraseologyMockMvc;

    private Phraseology phraseology;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PhraseologyResource phraseologyResource = new PhraseologyResource(phraseologyService);
        this.restPhraseologyMockMvc = MockMvcBuilders.standaloneSetup(phraseologyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phraseology createEntity() {
        Phraseology phraseology = new Phraseology()
            .value(DEFAULT_VALUE);
        return phraseology;
    }

    @Before
    public void initTest() {
        phraseologyRepository.deleteAll();
        phraseology = createEntity();
    }

    @Test
    public void createPhraseology() throws Exception {
        int databaseSizeBeforeCreate = phraseologyRepository.findAll().size();

        // Create the Phraseology
        PhraseologyDTO phraseologyDTO = phraseologyMapper.toDto(phraseology);
        restPhraseologyMockMvc.perform(post("/api/phraseologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phraseologyDTO)))
            .andExpect(status().isCreated());

        // Validate the Phraseology in the database
        List<Phraseology> phraseologyList = phraseologyRepository.findAll();
        assertThat(phraseologyList).hasSize(databaseSizeBeforeCreate + 1);
        Phraseology testPhraseology = phraseologyList.get(phraseologyList.size() - 1);
        assertThat(testPhraseology.getLink()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createPhraseologyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phraseologyRepository.findAll().size();

        // Create the Phraseology with an existing ID
        phraseology.setId("existing_id");
        PhraseologyDTO phraseologyDTO = phraseologyMapper.toDto(phraseology);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhraseologyMockMvc.perform(post("/api/phraseologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phraseologyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Phraseology in the database
        List<Phraseology> phraseologyList = phraseologyRepository.findAll();
        assertThat(phraseologyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPhraseologies() throws Exception {
        // Initialize the database
        phraseologyRepository.save(phraseology);

        // Get all the phraseologyList
        restPhraseologyMockMvc.perform(get("/api/phraseologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phraseology.getId())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    public void getPhraseology() throws Exception {
        // Initialize the database
        phraseologyRepository.save(phraseology);

        // Get the phraseology
        restPhraseologyMockMvc.perform(get("/api/phraseologies/{id}", phraseology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(phraseology.getId()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingPhraseology() throws Exception {
        // Get the phraseology
        restPhraseologyMockMvc.perform(get("/api/phraseologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePhraseology() throws Exception {
        // Initialize the database
        phraseologyRepository.save(phraseology);
        int databaseSizeBeforeUpdate = phraseologyRepository.findAll().size();

        // Update the phraseology
        Phraseology updatedPhraseology = phraseologyRepository.findOne(phraseology.getId());
        updatedPhraseology
            .value(UPDATED_VALUE);
        PhraseologyDTO phraseologyDTO = phraseologyMapper.toDto(updatedPhraseology);

        restPhraseologyMockMvc.perform(put("/api/phraseologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phraseologyDTO)))
            .andExpect(status().isOk());

        // Validate the Phraseology in the database
        List<Phraseology> phraseologyList = phraseologyRepository.findAll();
        assertThat(phraseologyList).hasSize(databaseSizeBeforeUpdate);
        Phraseology testPhraseology = phraseologyList.get(phraseologyList.size() - 1);
        assertThat(testPhraseology.getLink()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingPhraseology() throws Exception {
        int databaseSizeBeforeUpdate = phraseologyRepository.findAll().size();

        // Create the Phraseology
        PhraseologyDTO phraseologyDTO = phraseologyMapper.toDto(phraseology);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPhraseologyMockMvc.perform(put("/api/phraseologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phraseologyDTO)))
            .andExpect(status().isCreated());

        // Validate the Phraseology in the database
        List<Phraseology> phraseologyList = phraseologyRepository.findAll();
        assertThat(phraseologyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePhraseology() throws Exception {
        // Initialize the database
        phraseologyRepository.save(phraseology);
        int databaseSizeBeforeDelete = phraseologyRepository.findAll().size();

        // Get the phraseology
        restPhraseologyMockMvc.perform(delete("/api/phraseologies/{id}", phraseology.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Phraseology> phraseologyList = phraseologyRepository.findAll();
        assertThat(phraseologyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phraseology.class);
        Phraseology phraseology1 = new Phraseology();
        phraseology1.setId("id1");
        Phraseology phraseology2 = new Phraseology();
        phraseology2.setId(phraseology1.getId());
        assertThat(phraseology1).isEqualTo(phraseology2);
        phraseology2.setId("id2");
        assertThat(phraseology1).isNotEqualTo(phraseology2);
        phraseology1.setId(null);
        assertThat(phraseology1).isNotEqualTo(phraseology2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhraseologyDTO.class);
        PhraseologyDTO phraseologyDTO1 = new PhraseologyDTO();
        phraseologyDTO1.setId("id1");
        PhraseologyDTO phraseologyDTO2 = new PhraseologyDTO();
        assertThat(phraseologyDTO1).isNotEqualTo(phraseologyDTO2);
        phraseologyDTO2.setId(phraseologyDTO1.getId());
        assertThat(phraseologyDTO1).isEqualTo(phraseologyDTO2);
        phraseologyDTO2.setId("id2");
        assertThat(phraseologyDTO1).isNotEqualTo(phraseologyDTO2);
        phraseologyDTO1.setId(null);
        assertThat(phraseologyDTO1).isNotEqualTo(phraseologyDTO2);
    }
}
