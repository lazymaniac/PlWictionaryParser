package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.repository.WikiUnitRepository;
import com.mindmap.jane.service.WikiUnitService;
import com.mindmap.jane.service.dto.WikiUnitDTO;
import com.mindmap.jane.service.mapper.WikiUnitMapper;
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
 * Test class for the WikiUnitResource REST controller.
 *
 * @see WikiUnitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class WikiUnitResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    @Autowired
    private WikiUnitRepository wikiUnitRepository;

    @Autowired
    private WikiUnitMapper wikiUnitMapper;

    @Autowired
    private WikiUnitService wikiUnitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restWikiUnitMockMvc;

    private WikiUnit wikiUnit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WikiUnitResource wikiUnitResource = new WikiUnitResource(wikiUnitService);
        this.restWikiUnitMockMvc = MockMvcBuilders.standaloneSetup(wikiUnitResource)
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
    public static WikiUnit createEntity() {
        WikiUnit wikiUnit = new WikiUnit()
            .name(DEFAULT_NAME)
            .topic(DEFAULT_TOPIC);
        return wikiUnit;
    }

    @Before
    public void initTest() {
        wikiUnitRepository.deleteAll();
        wikiUnit = createEntity();
    }

    @Test
    public void createWikiUnit() throws Exception {
        int databaseSizeBeforeCreate = wikiUnitRepository.findAll().size();

        // Create the WikiUnit
        WikiUnitDTO wikiUnitDTO = wikiUnitMapper.toDto(wikiUnit);
        restWikiUnitMockMvc.perform(post("/api/wiki-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wikiUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the WikiUnit in the database
        List<WikiUnit> wikiUnitList = wikiUnitRepository.findAll();
        assertThat(wikiUnitList).hasSize(databaseSizeBeforeCreate + 1);
        WikiUnit testWikiUnit = wikiUnitList.get(wikiUnitList.size() - 1);
        assertThat(testWikiUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWikiUnit.getTopic()).isEqualTo(DEFAULT_TOPIC);
    }

    @Test
    public void createWikiUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wikiUnitRepository.findAll().size();

        // Create the WikiUnit with an existing ID
        wikiUnit.setId("existing_id");
        WikiUnitDTO wikiUnitDTO = wikiUnitMapper.toDto(wikiUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWikiUnitMockMvc.perform(post("/api/wiki-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wikiUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WikiUnit in the database
        List<WikiUnit> wikiUnitList = wikiUnitRepository.findAll();
        assertThat(wikiUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllWikiUnits() throws Exception {
        // Initialize the database
        wikiUnitRepository.save(wikiUnit);

        // Get all the wikiUnitList
        restWikiUnitMockMvc.perform(get("/api/wiki-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wikiUnit.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC.toString())));
    }

    @Test
    public void getWikiUnit() throws Exception {
        // Initialize the database
        wikiUnitRepository.save(wikiUnit);

        // Get the wikiUnit
        restWikiUnitMockMvc.perform(get("/api/wiki-units/{id}", wikiUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wikiUnit.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC.toString()));
    }

    @Test
    public void getNonExistingWikiUnit() throws Exception {
        // Get the wikiUnit
        restWikiUnitMockMvc.perform(get("/api/wiki-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateWikiUnit() throws Exception {
        // Initialize the database
        wikiUnitRepository.save(wikiUnit);
        int databaseSizeBeforeUpdate = wikiUnitRepository.findAll().size();

        // Update the wikiUnit
        WikiUnit updatedWikiUnit = wikiUnitRepository.findOne(wikiUnit.getId());
        updatedWikiUnit
            .name(UPDATED_NAME)
            .topic(UPDATED_TOPIC);
        WikiUnitDTO wikiUnitDTO = wikiUnitMapper.toDto(updatedWikiUnit);

        restWikiUnitMockMvc.perform(put("/api/wiki-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wikiUnitDTO)))
            .andExpect(status().isOk());

        // Validate the WikiUnit in the database
        List<WikiUnit> wikiUnitList = wikiUnitRepository.findAll();
        assertThat(wikiUnitList).hasSize(databaseSizeBeforeUpdate);
        WikiUnit testWikiUnit = wikiUnitList.get(wikiUnitList.size() - 1);
        assertThat(testWikiUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWikiUnit.getTopic()).isEqualTo(UPDATED_TOPIC);
    }

    @Test
    public void updateNonExistingWikiUnit() throws Exception {
        int databaseSizeBeforeUpdate = wikiUnitRepository.findAll().size();

        // Create the WikiUnit
        WikiUnitDTO wikiUnitDTO = wikiUnitMapper.toDto(wikiUnit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWikiUnitMockMvc.perform(put("/api/wiki-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wikiUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the WikiUnit in the database
        List<WikiUnit> wikiUnitList = wikiUnitRepository.findAll();
        assertThat(wikiUnitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteWikiUnit() throws Exception {
        // Initialize the database
        wikiUnitRepository.save(wikiUnit);
        int databaseSizeBeforeDelete = wikiUnitRepository.findAll().size();

        // Get the wikiUnit
        restWikiUnitMockMvc.perform(delete("/api/wiki-units/{id}", wikiUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WikiUnit> wikiUnitList = wikiUnitRepository.findAll();
        assertThat(wikiUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WikiUnit.class);
        WikiUnit wikiUnit1 = new WikiUnit();
        wikiUnit1.setId("id1");
        WikiUnit wikiUnit2 = new WikiUnit();
        wikiUnit2.setId(wikiUnit1.getId());
        assertThat(wikiUnit1).isEqualTo(wikiUnit2);
        wikiUnit2.setId("id2");
        assertThat(wikiUnit1).isNotEqualTo(wikiUnit2);
        wikiUnit1.setId(null);
        assertThat(wikiUnit1).isNotEqualTo(wikiUnit2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WikiUnitDTO.class);
        WikiUnitDTO wikiUnitDTO1 = new WikiUnitDTO();
        wikiUnitDTO1.setId("id1");
        WikiUnitDTO wikiUnitDTO2 = new WikiUnitDTO();
        assertThat(wikiUnitDTO1).isNotEqualTo(wikiUnitDTO2);
        wikiUnitDTO2.setId(wikiUnitDTO1.getId());
        assertThat(wikiUnitDTO1).isEqualTo(wikiUnitDTO2);
        wikiUnitDTO2.setId("id2");
        assertThat(wikiUnitDTO1).isNotEqualTo(wikiUnitDTO2);
        wikiUnitDTO1.setId(null);
        assertThat(wikiUnitDTO1).isNotEqualTo(wikiUnitDTO2);
    }
}
