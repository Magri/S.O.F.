package com.sof.portalapp.web.rest;

import com.sof.portalapp.PortalApp;

import com.sof.portalapp.domain.PrevisaoTempo;
import com.sof.portalapp.repository.PrevisaoTempoRepository;
import com.sof.portalapp.service.PrevisaoTempoService;
import com.sof.portalapp.service.dto.PrevisaoTempoDTO;
import com.sof.portalapp.service.mapper.PrevisaoTempoMapper;
import com.sof.portalapp.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.sof.portalapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrevisaoTempoResource REST controller.
 *
 * @see PrevisaoTempoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortalApp.class)
public class PrevisaoTempoResourceIntTest {

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_PREVISAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_PREVISAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PrevisaoTempoRepository previsaoTempoRepository;

    @Autowired
    private PrevisaoTempoMapper previsaoTempoMapper;

    @Autowired
    private PrevisaoTempoService previsaoTempoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrevisaoTempoMockMvc;

    private PrevisaoTempo previsaoTempo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrevisaoTempoResource previsaoTempoResource = new PrevisaoTempoResource(previsaoTempoService);
        this.restPrevisaoTempoMockMvc = MockMvcBuilders.standaloneSetup(previsaoTempoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrevisaoTempo createEntity(EntityManager em) {
        PrevisaoTempo previsaoTempo = new PrevisaoTempo()
            .link(DEFAULT_LINK)
            .dataPrevisao(DEFAULT_DATA_PREVISAO);
        return previsaoTempo;
    }

    @Before
    public void initTest() {
        previsaoTempo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrevisaoTempo() throws Exception {
        int databaseSizeBeforeCreate = previsaoTempoRepository.findAll().size();

        // Create the PrevisaoTempo
        PrevisaoTempoDTO previsaoTempoDTO = previsaoTempoMapper.toDto(previsaoTempo);
        restPrevisaoTempoMockMvc.perform(post("/api/previsao-tempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(previsaoTempoDTO)))
            .andExpect(status().isCreated());

        // Validate the PrevisaoTempo in the database
        List<PrevisaoTempo> previsaoTempoList = previsaoTempoRepository.findAll();
        assertThat(previsaoTempoList).hasSize(databaseSizeBeforeCreate + 1);
        PrevisaoTempo testPrevisaoTempo = previsaoTempoList.get(previsaoTempoList.size() - 1);
        assertThat(testPrevisaoTempo.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testPrevisaoTempo.getDataPrevisao()).isEqualTo(DEFAULT_DATA_PREVISAO);
    }

    @Test
    @Transactional
    public void createPrevisaoTempoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = previsaoTempoRepository.findAll().size();

        // Create the PrevisaoTempo with an existing ID
        previsaoTempo.setId(1L);
        PrevisaoTempoDTO previsaoTempoDTO = previsaoTempoMapper.toDto(previsaoTempo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrevisaoTempoMockMvc.perform(post("/api/previsao-tempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(previsaoTempoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrevisaoTempo in the database
        List<PrevisaoTempo> previsaoTempoList = previsaoTempoRepository.findAll();
        assertThat(previsaoTempoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = previsaoTempoRepository.findAll().size();
        // set the field null
        previsaoTempo.setLink(null);

        // Create the PrevisaoTempo, which fails.
        PrevisaoTempoDTO previsaoTempoDTO = previsaoTempoMapper.toDto(previsaoTempo);

        restPrevisaoTempoMockMvc.perform(post("/api/previsao-tempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(previsaoTempoDTO)))
            .andExpect(status().isBadRequest());

        List<PrevisaoTempo> previsaoTempoList = previsaoTempoRepository.findAll();
        assertThat(previsaoTempoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataPrevisaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = previsaoTempoRepository.findAll().size();
        // set the field null
        previsaoTempo.setDataPrevisao(null);

        // Create the PrevisaoTempo, which fails.
        PrevisaoTempoDTO previsaoTempoDTO = previsaoTempoMapper.toDto(previsaoTempo);

        restPrevisaoTempoMockMvc.perform(post("/api/previsao-tempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(previsaoTempoDTO)))
            .andExpect(status().isBadRequest());

        List<PrevisaoTempo> previsaoTempoList = previsaoTempoRepository.findAll();
        assertThat(previsaoTempoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrevisaoTempos() throws Exception {
        // Initialize the database
        previsaoTempoRepository.saveAndFlush(previsaoTempo);

        // Get all the previsaoTempoList
        restPrevisaoTempoMockMvc.perform(get("/api/previsao-tempos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(previsaoTempo.getId().intValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].dataPrevisao").value(hasItem(DEFAULT_DATA_PREVISAO.toString())));
    }

    @Test
    @Transactional
    public void getPrevisaoTempo() throws Exception {
        // Initialize the database
        previsaoTempoRepository.saveAndFlush(previsaoTempo);

        // Get the previsaoTempo
        restPrevisaoTempoMockMvc.perform(get("/api/previsao-tempos/{id}", previsaoTempo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(previsaoTempo.getId().intValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.dataPrevisao").value(DEFAULT_DATA_PREVISAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrevisaoTempo() throws Exception {
        // Get the previsaoTempo
        restPrevisaoTempoMockMvc.perform(get("/api/previsao-tempos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrevisaoTempo() throws Exception {
        // Initialize the database
        previsaoTempoRepository.saveAndFlush(previsaoTempo);
        int databaseSizeBeforeUpdate = previsaoTempoRepository.findAll().size();

        // Update the previsaoTempo
        PrevisaoTempo updatedPrevisaoTempo = previsaoTempoRepository.findOne(previsaoTempo.getId());
        // Disconnect from session so that the updates on updatedPrevisaoTempo are not directly saved in db
        em.detach(updatedPrevisaoTempo);
        updatedPrevisaoTempo
            .link(UPDATED_LINK)
            .dataPrevisao(UPDATED_DATA_PREVISAO);
        PrevisaoTempoDTO previsaoTempoDTO = previsaoTempoMapper.toDto(updatedPrevisaoTempo);

        restPrevisaoTempoMockMvc.perform(put("/api/previsao-tempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(previsaoTempoDTO)))
            .andExpect(status().isOk());

        // Validate the PrevisaoTempo in the database
        List<PrevisaoTempo> previsaoTempoList = previsaoTempoRepository.findAll();
        assertThat(previsaoTempoList).hasSize(databaseSizeBeforeUpdate);
        PrevisaoTempo testPrevisaoTempo = previsaoTempoList.get(previsaoTempoList.size() - 1);
        assertThat(testPrevisaoTempo.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testPrevisaoTempo.getDataPrevisao()).isEqualTo(UPDATED_DATA_PREVISAO);
    }

    @Test
    @Transactional
    public void updateNonExistingPrevisaoTempo() throws Exception {
        int databaseSizeBeforeUpdate = previsaoTempoRepository.findAll().size();

        // Create the PrevisaoTempo
        PrevisaoTempoDTO previsaoTempoDTO = previsaoTempoMapper.toDto(previsaoTempo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrevisaoTempoMockMvc.perform(put("/api/previsao-tempos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(previsaoTempoDTO)))
            .andExpect(status().isCreated());

        // Validate the PrevisaoTempo in the database
        List<PrevisaoTempo> previsaoTempoList = previsaoTempoRepository.findAll();
        assertThat(previsaoTempoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrevisaoTempo() throws Exception {
        // Initialize the database
        previsaoTempoRepository.saveAndFlush(previsaoTempo);
        int databaseSizeBeforeDelete = previsaoTempoRepository.findAll().size();

        // Get the previsaoTempo
        restPrevisaoTempoMockMvc.perform(delete("/api/previsao-tempos/{id}", previsaoTempo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrevisaoTempo> previsaoTempoList = previsaoTempoRepository.findAll();
        assertThat(previsaoTempoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrevisaoTempo.class);
        PrevisaoTempo previsaoTempo1 = new PrevisaoTempo();
        previsaoTempo1.setId(1L);
        PrevisaoTempo previsaoTempo2 = new PrevisaoTempo();
        previsaoTempo2.setId(previsaoTempo1.getId());
        assertThat(previsaoTempo1).isEqualTo(previsaoTempo2);
        previsaoTempo2.setId(2L);
        assertThat(previsaoTempo1).isNotEqualTo(previsaoTempo2);
        previsaoTempo1.setId(null);
        assertThat(previsaoTempo1).isNotEqualTo(previsaoTempo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrevisaoTempoDTO.class);
        PrevisaoTempoDTO previsaoTempoDTO1 = new PrevisaoTempoDTO();
        previsaoTempoDTO1.setId(1L);
        PrevisaoTempoDTO previsaoTempoDTO2 = new PrevisaoTempoDTO();
        assertThat(previsaoTempoDTO1).isNotEqualTo(previsaoTempoDTO2);
        previsaoTempoDTO2.setId(previsaoTempoDTO1.getId());
        assertThat(previsaoTempoDTO1).isEqualTo(previsaoTempoDTO2);
        previsaoTempoDTO2.setId(2L);
        assertThat(previsaoTempoDTO1).isNotEqualTo(previsaoTempoDTO2);
        previsaoTempoDTO1.setId(null);
        assertThat(previsaoTempoDTO1).isNotEqualTo(previsaoTempoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(previsaoTempoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(previsaoTempoMapper.fromId(null)).isNull();
    }
}
