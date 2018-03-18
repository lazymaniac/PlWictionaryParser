package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.Example;
import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.Sentence;
import com.mindmap.jane.repository.ExampleRepository;
import com.mindmap.jane.service.ExampleService;
import com.mindmap.jane.service.dto.ExampleDTO;
import com.mindmap.jane.service.mapper.ExampleMapper;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExampleResource REST controller.
 *
 * @see ExampleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class ExampleResourceIntTest {

    private static final Sentence DEFAULT_VALUE = new Sentence("AAAAAAAA");
    private static final Sentence UPDATED_VALUE = new Sentence("BBBBBBBB");

    @Autowired
    private ExampleRepository exampleRepository;

    @Autowired
    private ExampleMapper exampleMapper;

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restExampleMockMvc;

    private Example example;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExampleResource exampleResource = new ExampleResource(exampleService);
        this.restExampleMockMvc = MockMvcBuilders.standaloneSetup(exampleResource)
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
    public static Example createEntity() {
        Example example = new Example()
            .sentence(DEFAULT_VALUE);
        return example;
    }

    @Before
    public void initTest() {
        exampleRepository.deleteAll();
        example = createEntity();
    }

    @Test
    public void createExample() throws Exception {
        int databaseSizeBeforeCreate = exampleRepository.findAll().size();

        // Create the Example
        ExampleDTO exampleDTO = exampleMapper.toDto(example);
        restExampleMockMvc.perform(post("/api/examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exampleDTO)))
            .andExpect(status().isCreated());

        // Validate the Example in the database
        List<Example> exampleList = exampleRepository.findAll();
        assertThat(exampleList).hasSize(databaseSizeBeforeCreate + 1);
        Example testExample = exampleList.get(exampleList.size() - 1);
        assertThat(testExample.getSentence()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createExampleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exampleRepository.findAll().size();

        // Create the Example with an existing ID
        example.setId("existing_id");
        ExampleDTO exampleDTO = exampleMapper.toDto(example);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExampleMockMvc.perform(post("/api/examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exampleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Example in the database
        List<Example> exampleList = exampleRepository.findAll();
        assertThat(exampleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllExamples() throws Exception {
        // Initialize the database
        exampleRepository.save(example);

        // Get all the exampleList
        restExampleMockMvc.perform(get("/api/examples?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(example.getId())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    public void getExample() throws Exception {
        // Initialize the database
        exampleRepository.save(example);

        // Get the example
        restExampleMockMvc.perform(get("/api/examples/{id}", example.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(example.getId()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingExample() throws Exception {
        // Get the example
        restExampleMockMvc.perform(get("/api/examples/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateExample() throws Exception {
        // Initialize the database
        exampleRepository.save(example);
        int databaseSizeBeforeUpdate = exampleRepository.findAll().size();

        // Update the example
        Example updatedExample = exampleRepository.findOne(example.getId());
        updatedExample
            .sentence(UPDATED_VALUE);
        ExampleDTO exampleDTO = exampleMapper.toDto(updatedExample);

        restExampleMockMvc.perform(put("/api/examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exampleDTO)))
            .andExpect(status().isOk());

        // Validate the Example in the database
        List<Example> exampleList = exampleRepository.findAll();
        assertThat(exampleList).hasSize(databaseSizeBeforeUpdate);
        Example testExample = exampleList.get(exampleList.size() - 1);
        assertThat(testExample.getSentence()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingExample() throws Exception {
        int databaseSizeBeforeUpdate = exampleRepository.findAll().size();

        // Create the Example
        ExampleDTO exampleDTO = exampleMapper.toDto(example);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExampleMockMvc.perform(put("/api/examples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exampleDTO)))
            .andExpect(status().isCreated());

        // Validate the Example in the database
        List<Example> exampleList = exampleRepository.findAll();
        assertThat(exampleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteExample() throws Exception {
        // Initialize the database
        exampleRepository.save(example);
        int databaseSizeBeforeDelete = exampleRepository.findAll().size();

        // Get the example
        restExampleMockMvc.perform(delete("/api/examples/{id}", example.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Example> exampleList = exampleRepository.findAll();
        assertThat(exampleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Example.class);
        Example example1 = new Example();
        example1.setId("id1");
        Example example2 = new Example();
        example2.setId(example1.getId());
        assertThat(example1).isEqualTo(example2);
        example2.setId("id2");
        assertThat(example1).isNotEqualTo(example2);
        example1.setId(null);
        assertThat(example1).isNotEqualTo(example2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExampleDTO.class);
        ExampleDTO exampleDTO1 = new ExampleDTO();
        exampleDTO1.setId("id1");
        ExampleDTO exampleDTO2 = new ExampleDTO();
        assertThat(exampleDTO1).isNotEqualTo(exampleDTO2);
        exampleDTO2.setId(exampleDTO1.getId());
        assertThat(exampleDTO1).isEqualTo(exampleDTO2);
        exampleDTO2.setId("id2");
        assertThat(exampleDTO1).isNotEqualTo(exampleDTO2);
        exampleDTO1.setId(null);
        assertThat(exampleDTO1).isNotEqualTo(exampleDTO2);
    }
}
