package com.mindmap.jane.web.rest;

import com.mindmap.jane.ParserApp;

import com.mindmap.jane.domain.PersonVar;
import com.mindmap.jane.repository.PersonVarRepository;
import com.mindmap.jane.service.PersonVarService;
import com.mindmap.jane.service.dto.PersonVarDTO;
import com.mindmap.jane.service.mapper.PersonVarMapper;
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

import com.mindmap.jane.domain.enumeration.PersonVarTypeEnum;
/**
 * Test class for the PersonVarResource REST controller.
 *
 * @see PersonVarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class PersonVarResourceIntTest {

    private static final PersonVarTypeEnum DEFAULT_VAR_TYPE = PersonVarTypeEnum.CZAS_TERAZ;
    private static final PersonVarTypeEnum UPDATED_VAR_TYPE = PersonVarTypeEnum.CZAS_PRZESZ_M;

    private static final String DEFAULT_PER_1_SING = "AAAAAAAAAA";
    private static final String UPDATED_PER_1_SING = "BBBBBBBBBB";

    private static final String DEFAULT_PER_2_SING = "AAAAAAAAAA";
    private static final String UPDATED_PER_2_SING = "BBBBBBBBBB";

    private static final String DEFAULT_PER_3_SING = "AAAAAAAAAA";
    private static final String UPDATED_PER_3_SING = "BBBBBBBBBB";

    private static final String DEFAULT_PER_1_PLUR = "AAAAAAAAAA";
    private static final String UPDATED_PER_1_PLUR = "BBBBBBBBBB";

    private static final String DEFAULT_PER_2_PLUR = "AAAAAAAAAA";
    private static final String UPDATED_PER_2_PLUR = "BBBBBBBBBB";

    private static final String DEFAULT_PER_3_PLUR = "AAAAAAAAAA";
    private static final String UPDATED_PER_3_PLUR = "BBBBBBBBBB";

    @Autowired
    private PersonVarRepository personVarRepository;

    @Autowired
    private PersonVarMapper personVarMapper;

    @Autowired
    private PersonVarService personVarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPersonVarMockMvc;

    private PersonVar personVar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonVarResource personVarResource = new PersonVarResource(personVarService);
        this.restPersonVarMockMvc = MockMvcBuilders.standaloneSetup(personVarResource)
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
    public static PersonVar createEntity() {
        PersonVar personVar = new PersonVar()
            .varType(DEFAULT_VAR_TYPE)
            .per1sing(DEFAULT_PER_1_SING)
            .per2sing(DEFAULT_PER_2_SING)
            .per3sing(DEFAULT_PER_3_SING)
            .per1plur(DEFAULT_PER_1_PLUR)
            .per2plur(DEFAULT_PER_2_PLUR)
            .per3plur(DEFAULT_PER_3_PLUR);
        return personVar;
    }

    @Before
    public void initTest() {
        personVarRepository.deleteAll();
        personVar = createEntity();
    }

    @Test
    public void createPersonVar() throws Exception {
        int databaseSizeBeforeCreate = personVarRepository.findAll().size();

        // Create the PersonVar
        PersonVarDTO personVarDTO = personVarMapper.toDto(personVar);
        restPersonVarMockMvc.perform(post("/api/person-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personVarDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonVar in the database
        List<PersonVar> personVarList = personVarRepository.findAll();
        assertThat(personVarList).hasSize(databaseSizeBeforeCreate + 1);
        PersonVar testPersonVar = personVarList.get(personVarList.size() - 1);
        assertThat(testPersonVar.getVarType()).isEqualTo(DEFAULT_VAR_TYPE);
        assertThat(testPersonVar.getPer1sing()).isEqualTo(DEFAULT_PER_1_SING);
        assertThat(testPersonVar.getPer2sing()).isEqualTo(DEFAULT_PER_2_SING);
        assertThat(testPersonVar.getPer3sing()).isEqualTo(DEFAULT_PER_3_SING);
        assertThat(testPersonVar.getPer1plur()).isEqualTo(DEFAULT_PER_1_PLUR);
        assertThat(testPersonVar.getPer2plur()).isEqualTo(DEFAULT_PER_2_PLUR);
        assertThat(testPersonVar.getPer3plur()).isEqualTo(DEFAULT_PER_3_PLUR);
    }

    @Test
    public void createPersonVarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personVarRepository.findAll().size();

        // Create the PersonVar with an existing ID
        personVar.setId("existing_id");
        PersonVarDTO personVarDTO = personVarMapper.toDto(personVar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonVarMockMvc.perform(post("/api/person-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personVarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonVar in the database
        List<PersonVar> personVarList = personVarRepository.findAll();
        assertThat(personVarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPersonVars() throws Exception {
        // Initialize the database
        personVarRepository.save(personVar);

        // Get all the personVarList
        restPersonVarMockMvc.perform(get("/api/person-vars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personVar.getId())))
            .andExpect(jsonPath("$.[*].varType").value(hasItem(DEFAULT_VAR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].per1sing").value(hasItem(DEFAULT_PER_1_SING.toString())))
            .andExpect(jsonPath("$.[*].per2sing").value(hasItem(DEFAULT_PER_2_SING.toString())))
            .andExpect(jsonPath("$.[*].per3sing").value(hasItem(DEFAULT_PER_3_SING.toString())))
            .andExpect(jsonPath("$.[*].per1plur").value(hasItem(DEFAULT_PER_1_PLUR.toString())))
            .andExpect(jsonPath("$.[*].per2plur").value(hasItem(DEFAULT_PER_2_PLUR.toString())))
            .andExpect(jsonPath("$.[*].per3plur").value(hasItem(DEFAULT_PER_3_PLUR.toString())));
    }

    @Test
    public void getPersonVar() throws Exception {
        // Initialize the database
        personVarRepository.save(personVar);

        // Get the personVar
        restPersonVarMockMvc.perform(get("/api/person-vars/{id}", personVar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personVar.getId()))
            .andExpect(jsonPath("$.varType").value(DEFAULT_VAR_TYPE.toString()))
            .andExpect(jsonPath("$.per1sing").value(DEFAULT_PER_1_SING.toString()))
            .andExpect(jsonPath("$.per2sing").value(DEFAULT_PER_2_SING.toString()))
            .andExpect(jsonPath("$.per3sing").value(DEFAULT_PER_3_SING.toString()))
            .andExpect(jsonPath("$.per1plur").value(DEFAULT_PER_1_PLUR.toString()))
            .andExpect(jsonPath("$.per2plur").value(DEFAULT_PER_2_PLUR.toString()))
            .andExpect(jsonPath("$.per3plur").value(DEFAULT_PER_3_PLUR.toString()));
    }

    @Test
    public void getNonExistingPersonVar() throws Exception {
        // Get the personVar
        restPersonVarMockMvc.perform(get("/api/person-vars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePersonVar() throws Exception {
        // Initialize the database
        personVarRepository.save(personVar);
        int databaseSizeBeforeUpdate = personVarRepository.findAll().size();

        // Update the personVar
        PersonVar updatedPersonVar = personVarRepository.findOne(personVar.getId());
        updatedPersonVar
            .varType(UPDATED_VAR_TYPE)
            .per1sing(UPDATED_PER_1_SING)
            .per2sing(UPDATED_PER_2_SING)
            .per3sing(UPDATED_PER_3_SING)
            .per1plur(UPDATED_PER_1_PLUR)
            .per2plur(UPDATED_PER_2_PLUR)
            .per3plur(UPDATED_PER_3_PLUR);
        PersonVarDTO personVarDTO = personVarMapper.toDto(updatedPersonVar);

        restPersonVarMockMvc.perform(put("/api/person-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personVarDTO)))
            .andExpect(status().isOk());

        // Validate the PersonVar in the database
        List<PersonVar> personVarList = personVarRepository.findAll();
        assertThat(personVarList).hasSize(databaseSizeBeforeUpdate);
        PersonVar testPersonVar = personVarList.get(personVarList.size() - 1);
        assertThat(testPersonVar.getVarType()).isEqualTo(UPDATED_VAR_TYPE);
        assertThat(testPersonVar.getPer1sing()).isEqualTo(UPDATED_PER_1_SING);
        assertThat(testPersonVar.getPer2sing()).isEqualTo(UPDATED_PER_2_SING);
        assertThat(testPersonVar.getPer3sing()).isEqualTo(UPDATED_PER_3_SING);
        assertThat(testPersonVar.getPer1plur()).isEqualTo(UPDATED_PER_1_PLUR);
        assertThat(testPersonVar.getPer2plur()).isEqualTo(UPDATED_PER_2_PLUR);
        assertThat(testPersonVar.getPer3plur()).isEqualTo(UPDATED_PER_3_PLUR);
    }

    @Test
    public void updateNonExistingPersonVar() throws Exception {
        int databaseSizeBeforeUpdate = personVarRepository.findAll().size();

        // Create the PersonVar
        PersonVarDTO personVarDTO = personVarMapper.toDto(personVar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonVarMockMvc.perform(put("/api/person-vars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personVarDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonVar in the database
        List<PersonVar> personVarList = personVarRepository.findAll();
        assertThat(personVarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePersonVar() throws Exception {
        // Initialize the database
        personVarRepository.save(personVar);
        int databaseSizeBeforeDelete = personVarRepository.findAll().size();

        // Get the personVar
        restPersonVarMockMvc.perform(delete("/api/person-vars/{id}", personVar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonVar> personVarList = personVarRepository.findAll();
        assertThat(personVarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonVar.class);
        PersonVar personVar1 = new PersonVar();
        personVar1.setId("id1");
        PersonVar personVar2 = new PersonVar();
        personVar2.setId(personVar1.getId());
        assertThat(personVar1).isEqualTo(personVar2);
        personVar2.setId("id2");
        assertThat(personVar1).isNotEqualTo(personVar2);
        personVar1.setId(null);
        assertThat(personVar1).isNotEqualTo(personVar2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonVarDTO.class);
        PersonVarDTO personVarDTO1 = new PersonVarDTO();
        personVarDTO1.setId("id1");
        PersonVarDTO personVarDTO2 = new PersonVarDTO();
        assertThat(personVarDTO1).isNotEqualTo(personVarDTO2);
        personVarDTO2.setId(personVarDTO1.getId());
        assertThat(personVarDTO1).isEqualTo(personVarDTO2);
        personVarDTO2.setId("id2");
        assertThat(personVarDTO1).isNotEqualTo(personVarDTO2);
        personVarDTO1.setId(null);
        assertThat(personVarDTO1).isNotEqualTo(personVarDTO2);
    }
}
