package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.Cognate;
import com.mindmap.jane.domain.Link;
import com.mindmap.jane.repository.CognateRepository;
import com.mindmap.jane.service.CognateService;
import com.mindmap.jane.service.dto.CognateDTO;
import com.mindmap.jane.service.mapper.CognateMapper;
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
 * Test class for the CognateResource REST controller.
 *
 * @see CognateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class CognateResourceIntTest {

    private static final Link DEFAULT_VALUE = new Link("AAAAAAAAAA", "AAAAAAAAAA");
    private static final Link UPDATED_VALUE = new Link("BBBBBBBBBB", "BBBBBBBBBB");

    @Autowired
    private CognateRepository cognateRepository;

    @Autowired
    private CognateMapper cognateMapper;

    @Autowired
    private CognateService cognateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCognateMockMvc;

    private Cognate cognate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CognateResource cognateResource = new CognateResource(cognateService);
        this.restCognateMockMvc = MockMvcBuilders.standaloneSetup(cognateResource)
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
    public static Cognate createEntity() {
        Cognate cognate = new Cognate()
            .link(DEFAULT_VALUE);
        return cognate;
    }

    @Before
    public void initTest() {
        cognateRepository.deleteAll();
        cognate = createEntity();
    }

    @Test
    public void createCognate() throws Exception {
        int databaseSizeBeforeCreate = cognateRepository.findAll().size();

        // Create the Cognate
        CognateDTO cognateDTO = cognateMapper.toDto(cognate);
        restCognateMockMvc.perform(post("/api/cognates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cognateDTO)))
            .andExpect(status().isCreated());

        // Validate the Cognate in the database
        List<Cognate> cognateList = cognateRepository.findAll();
        assertThat(cognateList).hasSize(databaseSizeBeforeCreate + 1);
        Cognate testCognate = cognateList.get(cognateList.size() - 1);
        assertThat(testCognate.getLink()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createCognateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cognateRepository.findAll().size();

        // Create the Cognate with an existing ID
        cognate.setId("existing_id");
        CognateDTO cognateDTO = cognateMapper.toDto(cognate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCognateMockMvc.perform(post("/api/cognates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cognateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cognate in the database
        List<Cognate> cognateList = cognateRepository.findAll();
        assertThat(cognateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCognates() throws Exception {
        // Initialize the database
        cognateRepository.save(cognate);

        // Get all the cognateList
        restCognateMockMvc.perform(get("/api/cognates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cognate.getId())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    public void getCognate() throws Exception {
        // Initialize the database
        cognateRepository.save(cognate);

        // Get the cognate
        restCognateMockMvc.perform(get("/api/cognates/{id}", cognate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cognate.getId()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingCognate() throws Exception {
        // Get the cognate
        restCognateMockMvc.perform(get("/api/cognates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCognate() throws Exception {
        // Initialize the database
        cognateRepository.save(cognate);
        int databaseSizeBeforeUpdate = cognateRepository.findAll().size();

        // Update the cognate
        Cognate updatedCognate = cognateRepository.findOne(cognate.getId());
        updatedCognate
            .link(UPDATED_VALUE);
        CognateDTO cognateDTO = cognateMapper.toDto(updatedCognate);

        restCognateMockMvc.perform(put("/api/cognates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cognateDTO)))
            .andExpect(status().isOk());

        // Validate the Cognate in the database
        List<Cognate> cognateList = cognateRepository.findAll();
        assertThat(cognateList).hasSize(databaseSizeBeforeUpdate);
        Cognate testCognate = cognateList.get(cognateList.size() - 1);
        assertThat(testCognate.getLink()).isEqualTo(UPDATED_VALUE);
    }


    @Test
    public void updateNonExistingCognate() throws Exception {
        int databaseSizeBeforeUpdate = cognateRepository.findAll().size();

        // Create the Cognate
        CognateDTO cognateDTO = cognateMapper.toDto(cognate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCognateMockMvc.perform(put("/api/cognates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cognateDTO)))
            .andExpect(status().isCreated());

        // Validate the Cognate in the database
        List<Cognate> cognateList = cognateRepository.findAll();
        assertThat(cognateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCognate() throws Exception {
        // Initialize the database
        cognateRepository.save(cognate);
        int databaseSizeBeforeDelete = cognateRepository.findAll().size();

        // Get the cognate
        restCognateMockMvc.perform(delete("/api/cognates/{id}", cognate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cognate> cognateList = cognateRepository.findAll();
        assertThat(cognateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cognate.class);
        Cognate cognate1 = new Cognate();
        cognate1.setId("id1");
        Cognate cognate2 = new Cognate();
        cognate2.setId(cognate1.getId());
        assertThat(cognate1).isEqualTo(cognate2);
        cognate2.setId("id2");
        assertThat(cognate1).isNotEqualTo(cognate2);
        cognate1.setId(null);
        assertThat(cognate1).isNotEqualTo(cognate2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CognateDTO.class);
        CognateDTO cognateDTO1 = new CognateDTO();
        cognateDTO1.setId("id1");
        CognateDTO cognateDTO2 = new CognateDTO();
        assertThat(cognateDTO1).isNotEqualTo(cognateDTO2);
        cognateDTO2.setId(cognateDTO1.getId());
        assertThat(cognateDTO1).isEqualTo(cognateDTO2);
        cognateDTO2.setId("id2");
        assertThat(cognateDTO1).isNotEqualTo(cognateDTO2);
        cognateDTO1.setId(null);
        assertThat(cognateDTO1).isNotEqualTo(cognateDTO2);
    }
}
