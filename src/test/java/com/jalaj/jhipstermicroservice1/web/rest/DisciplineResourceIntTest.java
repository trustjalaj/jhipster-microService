package com.jalaj.jhipstermicroservice1.web.rest;

import com.jalaj.jhipstermicroservice1.JhipsterMicroService1App;
import com.jalaj.jhipstermicroservice1.domain.Discipline;
import com.jalaj.jhipstermicroservice1.repository.DisciplineRepository;
import com.jalaj.jhipstermicroservice1.service.DisciplineService;
import com.jalaj.jhipstermicroservice1.service.dto.DisciplineDTO;
import com.jalaj.jhipstermicroservice1.service.mapper.DisciplineMapper;
import com.jalaj.jhipstermicroservice1.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.jalaj.jhipstermicroservice1.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DisciplineResource REST controller.
 *
 * @see DisciplineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterMicroService1App.class)
public class DisciplineResourceIntTest {

    private static final String DEFAULT_DISCIPLINE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISCIPLINE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISCIPLINE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISCIPLINE_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_DISCIPLINE_PRICE = 1L;
    private static final Long UPDATED_DISCIPLINE_PRICE = 2L;

    @Autowired
    private DisciplineRepository disciplineRepository;
    @Mock
    private DisciplineRepository disciplineRepositoryMock;

    @Autowired
    private DisciplineMapper disciplineMapper;

    @Mock
    private DisciplineService disciplineServiceMock;

    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDisciplineMockMvc;

    private Discipline discipline;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discipline createEntity(EntityManager em) {
        Discipline discipline = new Discipline()
            .disciplineName(DEFAULT_DISCIPLINE_NAME)
            .disciplineDescription(DEFAULT_DISCIPLINE_DESCRIPTION)
            .disciplinePrice(DEFAULT_DISCIPLINE_PRICE);
        return discipline;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisciplineResource disciplineResource = new DisciplineResource(disciplineService);
        this.restDisciplineMockMvc = MockMvcBuilders.standaloneSetup(disciplineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        discipline = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscipline() throws Exception {
        int databaseSizeBeforeCreate = disciplineRepository.findAll().size();

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);
        restDisciplineMockMvc.perform(post("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isCreated());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeCreate + 1);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getDisciplineName()).isEqualTo(DEFAULT_DISCIPLINE_NAME);
        assertThat(testDiscipline.getDisciplineDescription()).isEqualTo(DEFAULT_DISCIPLINE_DESCRIPTION);
        assertThat(testDiscipline.getDisciplinePrice()).isEqualTo(DEFAULT_DISCIPLINE_PRICE);
    }

    @Test
    @Transactional
    public void createDisciplineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplineRepository.findAll().size();

        // Create the Discipline with an existing ID
        discipline.setId(1L);
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplineMockMvc.perform(post("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDisciplineNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplineRepository.findAll().size();
        // set the field null
        discipline.setDisciplineName(null);

        // Create the Discipline, which fails.
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        restDisciplineMockMvc.perform(post("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isBadRequest());

        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDisciplines() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList
        restDisciplineMockMvc.perform(get("/api/disciplines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discipline.getId().intValue())))
            .andExpect(jsonPath("$.[*].disciplineName").value(hasItem(DEFAULT_DISCIPLINE_NAME.toString())))
            .andExpect(jsonPath("$.[*].disciplineDescription").value(hasItem(DEFAULT_DISCIPLINE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].disciplinePrice").value(hasItem(DEFAULT_DISCIPLINE_PRICE.intValue())));
    }

    public void getAllDisciplinesWithEagerRelationshipsIsEnabled() throws Exception {
        DisciplineResource disciplineResource = new DisciplineResource(disciplineServiceMock);
        when(disciplineServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDisciplineMockMvc = MockMvcBuilders.standaloneSetup(disciplineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDisciplineMockMvc.perform(get("/api/disciplines?eagerload=true"))
            .andExpect(status().isOk());

        verify(disciplineServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllDisciplinesWithEagerRelationshipsIsNotEnabled() throws Exception {
        DisciplineResource disciplineResource = new DisciplineResource(disciplineServiceMock);
        when(disciplineServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
        MockMvc restDisciplineMockMvc = MockMvcBuilders.standaloneSetup(disciplineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDisciplineMockMvc.perform(get("/api/disciplines?eagerload=true"))
            .andExpect(status().isOk());

        verify(disciplineServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get the discipline
        restDisciplineMockMvc.perform(get("/api/disciplines/{id}", discipline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discipline.getId().intValue()))
            .andExpect(jsonPath("$.disciplineName").value(DEFAULT_DISCIPLINE_NAME.toString()))
            .andExpect(jsonPath("$.disciplineDescription").value(DEFAULT_DISCIPLINE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.disciplinePrice").value(DEFAULT_DISCIPLINE_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscipline() throws Exception {
        // Get the discipline
        restDisciplineMockMvc.perform(get("/api/disciplines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Update the discipline
        Discipline updatedDiscipline = disciplineRepository.findById(discipline.getId()).get();
        // Disconnect from session so that the updates on updatedDiscipline are not directly saved in db
        em.detach(updatedDiscipline);
        updatedDiscipline
            .disciplineName(UPDATED_DISCIPLINE_NAME)
            .disciplineDescription(UPDATED_DISCIPLINE_DESCRIPTION)
            .disciplinePrice(UPDATED_DISCIPLINE_PRICE);
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(updatedDiscipline);

        restDisciplineMockMvc.perform(put("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isOk());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getDisciplineName()).isEqualTo(UPDATED_DISCIPLINE_NAME);
        assertThat(testDiscipline.getDisciplineDescription()).isEqualTo(UPDATED_DISCIPLINE_DESCRIPTION);
        assertThat(testDiscipline.getDisciplinePrice()).isEqualTo(UPDATED_DISCIPLINE_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDisciplineMockMvc.perform(put("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeDelete = disciplineRepository.findAll().size();

        // Get the discipline
        restDisciplineMockMvc.perform(delete("/api/disciplines/{id}", discipline.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Discipline.class);
        Discipline discipline1 = new Discipline();
        discipline1.setId(1L);
        Discipline discipline2 = new Discipline();
        discipline2.setId(discipline1.getId());
        assertThat(discipline1).isEqualTo(discipline2);
        discipline2.setId(2L);
        assertThat(discipline1).isNotEqualTo(discipline2);
        discipline1.setId(null);
        assertThat(discipline1).isNotEqualTo(discipline2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisciplineDTO.class);
        DisciplineDTO disciplineDTO1 = new DisciplineDTO();
        disciplineDTO1.setId(1L);
        DisciplineDTO disciplineDTO2 = new DisciplineDTO();
        assertThat(disciplineDTO1).isNotEqualTo(disciplineDTO2);
        disciplineDTO2.setId(disciplineDTO1.getId());
        assertThat(disciplineDTO1).isEqualTo(disciplineDTO2);
        disciplineDTO2.setId(2L);
        assertThat(disciplineDTO1).isNotEqualTo(disciplineDTO2);
        disciplineDTO1.setId(null);
        assertThat(disciplineDTO1).isNotEqualTo(disciplineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(disciplineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(disciplineMapper.fromId(null)).isNull();
    }
}
