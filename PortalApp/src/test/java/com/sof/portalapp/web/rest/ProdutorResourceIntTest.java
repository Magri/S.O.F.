package com.sof.portalapp.web.rest;

import com.sof.portalapp.PortalApp;

import com.sof.portalapp.domain.Produtor;
import com.sof.portalapp.repository.ProdutorRepository;
import com.sof.portalapp.service.ProdutorService;
import com.sof.portalapp.service.dto.ProdutorDTO;
import com.sof.portalapp.service.mapper.ProdutorMapper;
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
 * Test class for the ProdutorResource REST controller.
 *
 * @see ProdutorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortalApp.class)
public class ProdutorResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_NASCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_NASCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_CADPRO = "AAAAAAAAAA";
    private static final String UPDATED_CADPRO = "BBBBBBBBBB";

    @Autowired
    private ProdutorRepository produtorRepository;

    @Autowired
    private ProdutorMapper produtorMapper;

    @Autowired
    private ProdutorService produtorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProdutorMockMvc;

    private Produtor produtor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdutorResource produtorResource = new ProdutorResource(produtorService);
        this.restProdutorMockMvc = MockMvcBuilders.standaloneSetup(produtorResource)
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
    public static Produtor createEntity(EntityManager em) {
        Produtor produtor = new Produtor()
            .nome(DEFAULT_NOME)
            .email(DEFAULT_EMAIL)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .cpf(DEFAULT_CPF)
            .cadpro(DEFAULT_CADPRO);
        return produtor;
    }

    @Before
    public void initTest() {
        produtor = createEntity(em);
    }

    @Test
    @Transactional
    public void createProdutor() throws Exception {
        int databaseSizeBeforeCreate = produtorRepository.findAll().size();

        // Create the Produtor
        ProdutorDTO produtorDTO = produtorMapper.toDto(produtor);
        restProdutorMockMvc.perform(post("/api/produtors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtorDTO)))
            .andExpect(status().isCreated());

        // Validate the Produtor in the database
        List<Produtor> produtorList = produtorRepository.findAll();
        assertThat(produtorList).hasSize(databaseSizeBeforeCreate + 1);
        Produtor testProdutor = produtorList.get(produtorList.size() - 1);
        assertThat(testProdutor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProdutor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProdutor.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testProdutor.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testProdutor.getCadpro()).isEqualTo(DEFAULT_CADPRO);
    }

    @Test
    @Transactional
    public void createProdutorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtorRepository.findAll().size();

        // Create the Produtor with an existing ID
        produtor.setId(1L);
        ProdutorDTO produtorDTO = produtorMapper.toDto(produtor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutorMockMvc.perform(post("/api/produtors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produtor in the database
        List<Produtor> produtorList = produtorRepository.findAll();
        assertThat(produtorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtorRepository.findAll().size();
        // set the field null
        produtor.setNome(null);

        // Create the Produtor, which fails.
        ProdutorDTO produtorDTO = produtorMapper.toDto(produtor);

        restProdutorMockMvc.perform(post("/api/produtors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtorDTO)))
            .andExpect(status().isBadRequest());

        List<Produtor> produtorList = produtorRepository.findAll();
        assertThat(produtorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtorRepository.findAll().size();
        // set the field null
        produtor.setEmail(null);

        // Create the Produtor, which fails.
        ProdutorDTO produtorDTO = produtorMapper.toDto(produtor);

        restProdutorMockMvc.perform(post("/api/produtors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtorDTO)))
            .andExpect(status().isBadRequest());

        List<Produtor> produtorList = produtorRepository.findAll();
        assertThat(produtorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtorRepository.findAll().size();
        // set the field null
        produtor.setDataNascimento(null);

        // Create the Produtor, which fails.
        ProdutorDTO produtorDTO = produtorMapper.toDto(produtor);

        restProdutorMockMvc.perform(post("/api/produtors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtorDTO)))
            .andExpect(status().isBadRequest());

        List<Produtor> produtorList = produtorRepository.findAll();
        assertThat(produtorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtorRepository.findAll().size();
        // set the field null
        produtor.setCpf(null);

        // Create the Produtor, which fails.
        ProdutorDTO produtorDTO = produtorMapper.toDto(produtor);

        restProdutorMockMvc.perform(post("/api/produtors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtorDTO)))
            .andExpect(status().isBadRequest());

        List<Produtor> produtorList = produtorRepository.findAll();
        assertThat(produtorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCadproIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtorRepository.findAll().size();
        // set the field null
        produtor.setCadpro(null);

        // Create the Produtor, which fails.
        ProdutorDTO produtorDTO = produtorMapper.toDto(produtor);

        restProdutorMockMvc.perform(post("/api/produtors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtorDTO)))
            .andExpect(status().isBadRequest());

        List<Produtor> produtorList = produtorRepository.findAll();
        assertThat(produtorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdutors() throws Exception {
        // Initialize the database
        produtorRepository.saveAndFlush(produtor);

        // Get all the produtorList
        restProdutorMockMvc.perform(get("/api/produtors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produtor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
            .andExpect(jsonPath("$.[*].cadpro").value(hasItem(DEFAULT_CADPRO.toString())));
    }

    @Test
    @Transactional
    public void getProdutor() throws Exception {
        // Initialize the database
        produtorRepository.saveAndFlush(produtor);

        // Get the produtor
        restProdutorMockMvc.perform(get("/api/produtors/{id}", produtor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produtor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()))
            .andExpect(jsonPath("$.cadpro").value(DEFAULT_CADPRO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProdutor() throws Exception {
        // Get the produtor
        restProdutorMockMvc.perform(get("/api/produtors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdutor() throws Exception {
        // Initialize the database
        produtorRepository.saveAndFlush(produtor);
        int databaseSizeBeforeUpdate = produtorRepository.findAll().size();

        // Update the produtor
        Produtor updatedProdutor = produtorRepository.findOne(produtor.getId());
        // Disconnect from session so that the updates on updatedProdutor are not directly saved in db
        em.detach(updatedProdutor);
        updatedProdutor
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .cpf(UPDATED_CPF)
            .cadpro(UPDATED_CADPRO);
        ProdutorDTO produtorDTO = produtorMapper.toDto(updatedProdutor);

        restProdutorMockMvc.perform(put("/api/produtors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtorDTO)))
            .andExpect(status().isOk());

        // Validate the Produtor in the database
        List<Produtor> produtorList = produtorRepository.findAll();
        assertThat(produtorList).hasSize(databaseSizeBeforeUpdate);
        Produtor testProdutor = produtorList.get(produtorList.size() - 1);
        assertThat(testProdutor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProdutor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProdutor.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testProdutor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testProdutor.getCadpro()).isEqualTo(UPDATED_CADPRO);
    }

    @Test
    @Transactional
    public void updateNonExistingProdutor() throws Exception {
        int databaseSizeBeforeUpdate = produtorRepository.findAll().size();

        // Create the Produtor
        ProdutorDTO produtorDTO = produtorMapper.toDto(produtor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProdutorMockMvc.perform(put("/api/produtors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtorDTO)))
            .andExpect(status().isCreated());

        // Validate the Produtor in the database
        List<Produtor> produtorList = produtorRepository.findAll();
        assertThat(produtorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProdutor() throws Exception {
        // Initialize the database
        produtorRepository.saveAndFlush(produtor);
        int databaseSizeBeforeDelete = produtorRepository.findAll().size();

        // Get the produtor
        restProdutorMockMvc.perform(delete("/api/produtors/{id}", produtor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Produtor> produtorList = produtorRepository.findAll();
        assertThat(produtorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produtor.class);
        Produtor produtor1 = new Produtor();
        produtor1.setId(1L);
        Produtor produtor2 = new Produtor();
        produtor2.setId(produtor1.getId());
        assertThat(produtor1).isEqualTo(produtor2);
        produtor2.setId(2L);
        assertThat(produtor1).isNotEqualTo(produtor2);
        produtor1.setId(null);
        assertThat(produtor1).isNotEqualTo(produtor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdutorDTO.class);
        ProdutorDTO produtorDTO1 = new ProdutorDTO();
        produtorDTO1.setId(1L);
        ProdutorDTO produtorDTO2 = new ProdutorDTO();
        assertThat(produtorDTO1).isNotEqualTo(produtorDTO2);
        produtorDTO2.setId(produtorDTO1.getId());
        assertThat(produtorDTO1).isEqualTo(produtorDTO2);
        produtorDTO2.setId(2L);
        assertThat(produtorDTO1).isNotEqualTo(produtorDTO2);
        produtorDTO1.setId(null);
        assertThat(produtorDTO1).isNotEqualTo(produtorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(produtorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(produtorMapper.fromId(null)).isNull();
    }
}
