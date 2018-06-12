package com.sof.portalapp.web.rest;

import com.sof.portalapp.PortalApp;

import com.sof.portalapp.domain.Cotacao;
import com.sof.portalapp.repository.CotacaoRepository;
import com.sof.portalapp.service.CotacaoService;
import com.sof.portalapp.service.dto.CotacaoDTO;
import com.sof.portalapp.service.mapper.CotacaoMapper;
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
 * Test class for the CotacaoResource REST controller.
 *
 * @see CotacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortalApp.class)
public class CotacaoResourceIntTest {

    private static final Instant DEFAULT_DATA_COTACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_COTACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_PRECO = 1L;
    private static final Long UPDATED_PRECO = 2L;

    private static final String DEFAULT_DESCRICAO_CUSTOMIZADA_PRODUTO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_CUSTOMIZADA_PRODUTO = "BBBBBBBBBB";

    @Autowired
    private CotacaoRepository cotacaoRepository;

    @Autowired
    private CotacaoMapper cotacaoMapper;

    @Autowired
    private CotacaoService cotacaoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCotacaoMockMvc;

    private Cotacao cotacao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CotacaoResource cotacaoResource = new CotacaoResource(cotacaoService);
        this.restCotacaoMockMvc = MockMvcBuilders.standaloneSetup(cotacaoResource)
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
    public static Cotacao createEntity(EntityManager em) {
        Cotacao cotacao = new Cotacao()
            .dataCotacao(DEFAULT_DATA_COTACAO)
            .preco(DEFAULT_PRECO)
            .descricaoCustomizadaProduto(DEFAULT_DESCRICAO_CUSTOMIZADA_PRODUTO);
        return cotacao;
    }

    @Before
    public void initTest() {
        cotacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createCotacao() throws Exception {
        int databaseSizeBeforeCreate = cotacaoRepository.findAll().size();

        // Create the Cotacao
        CotacaoDTO cotacaoDTO = cotacaoMapper.toDto(cotacao);
        restCotacaoMockMvc.perform(post("/api/cotacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cotacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Cotacao in the database
        List<Cotacao> cotacaoList = cotacaoRepository.findAll();
        assertThat(cotacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Cotacao testCotacao = cotacaoList.get(cotacaoList.size() - 1);
        assertThat(testCotacao.getDataCotacao()).isEqualTo(DEFAULT_DATA_COTACAO);
        assertThat(testCotacao.getPreco()).isEqualTo(DEFAULT_PRECO);
        assertThat(testCotacao.getDescricaoCustomizadaProduto()).isEqualTo(DEFAULT_DESCRICAO_CUSTOMIZADA_PRODUTO);
    }

    @Test
    @Transactional
    public void createCotacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cotacaoRepository.findAll().size();

        // Create the Cotacao with an existing ID
        cotacao.setId(1L);
        CotacaoDTO cotacaoDTO = cotacaoMapper.toDto(cotacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCotacaoMockMvc.perform(post("/api/cotacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cotacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cotacao in the database
        List<Cotacao> cotacaoList = cotacaoRepository.findAll();
        assertThat(cotacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDataCotacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotacaoRepository.findAll().size();
        // set the field null
        cotacao.setDataCotacao(null);

        // Create the Cotacao, which fails.
        CotacaoDTO cotacaoDTO = cotacaoMapper.toDto(cotacao);

        restCotacaoMockMvc.perform(post("/api/cotacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cotacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Cotacao> cotacaoList = cotacaoRepository.findAll();
        assertThat(cotacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotacaoRepository.findAll().size();
        // set the field null
        cotacao.setPreco(null);

        // Create the Cotacao, which fails.
        CotacaoDTO cotacaoDTO = cotacaoMapper.toDto(cotacao);

        restCotacaoMockMvc.perform(post("/api/cotacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cotacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Cotacao> cotacaoList = cotacaoRepository.findAll();
        assertThat(cotacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoCustomizadaProdutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotacaoRepository.findAll().size();
        // set the field null
        cotacao.setDescricaoCustomizadaProduto(null);

        // Create the Cotacao, which fails.
        CotacaoDTO cotacaoDTO = cotacaoMapper.toDto(cotacao);

        restCotacaoMockMvc.perform(post("/api/cotacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cotacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Cotacao> cotacaoList = cotacaoRepository.findAll();
        assertThat(cotacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCotacaos() throws Exception {
        // Initialize the database
        cotacaoRepository.saveAndFlush(cotacao);

        // Get all the cotacaoList
        restCotacaoMockMvc.perform(get("/api/cotacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cotacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCotacao").value(hasItem(DEFAULT_DATA_COTACAO.toString())))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())))
            .andExpect(jsonPath("$.[*].descricaoCustomizadaProduto").value(hasItem(DEFAULT_DESCRICAO_CUSTOMIZADA_PRODUTO.toString())));
    }

    @Test
    @Transactional
    public void getCotacao() throws Exception {
        // Initialize the database
        cotacaoRepository.saveAndFlush(cotacao);

        // Get the cotacao
        restCotacaoMockMvc.perform(get("/api/cotacaos/{id}", cotacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cotacao.getId().intValue()))
            .andExpect(jsonPath("$.dataCotacao").value(DEFAULT_DATA_COTACAO.toString()))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.intValue()))
            .andExpect(jsonPath("$.descricaoCustomizadaProduto").value(DEFAULT_DESCRICAO_CUSTOMIZADA_PRODUTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCotacao() throws Exception {
        // Get the cotacao
        restCotacaoMockMvc.perform(get("/api/cotacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCotacao() throws Exception {
        // Initialize the database
        cotacaoRepository.saveAndFlush(cotacao);
        int databaseSizeBeforeUpdate = cotacaoRepository.findAll().size();

        // Update the cotacao
        Cotacao updatedCotacao = cotacaoRepository.findOne(cotacao.getId());
        // Disconnect from session so that the updates on updatedCotacao are not directly saved in db
        em.detach(updatedCotacao);
        updatedCotacao
            .dataCotacao(UPDATED_DATA_COTACAO)
            .preco(UPDATED_PRECO)
            .descricaoCustomizadaProduto(UPDATED_DESCRICAO_CUSTOMIZADA_PRODUTO);
        CotacaoDTO cotacaoDTO = cotacaoMapper.toDto(updatedCotacao);

        restCotacaoMockMvc.perform(put("/api/cotacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cotacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Cotacao in the database
        List<Cotacao> cotacaoList = cotacaoRepository.findAll();
        assertThat(cotacaoList).hasSize(databaseSizeBeforeUpdate);
        Cotacao testCotacao = cotacaoList.get(cotacaoList.size() - 1);
        assertThat(testCotacao.getDataCotacao()).isEqualTo(UPDATED_DATA_COTACAO);
        assertThat(testCotacao.getPreco()).isEqualTo(UPDATED_PRECO);
        assertThat(testCotacao.getDescricaoCustomizadaProduto()).isEqualTo(UPDATED_DESCRICAO_CUSTOMIZADA_PRODUTO);
    }

    @Test
    @Transactional
    public void updateNonExistingCotacao() throws Exception {
        int databaseSizeBeforeUpdate = cotacaoRepository.findAll().size();

        // Create the Cotacao
        CotacaoDTO cotacaoDTO = cotacaoMapper.toDto(cotacao);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCotacaoMockMvc.perform(put("/api/cotacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cotacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Cotacao in the database
        List<Cotacao> cotacaoList = cotacaoRepository.findAll();
        assertThat(cotacaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCotacao() throws Exception {
        // Initialize the database
        cotacaoRepository.saveAndFlush(cotacao);
        int databaseSizeBeforeDelete = cotacaoRepository.findAll().size();

        // Get the cotacao
        restCotacaoMockMvc.perform(delete("/api/cotacaos/{id}", cotacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cotacao> cotacaoList = cotacaoRepository.findAll();
        assertThat(cotacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cotacao.class);
        Cotacao cotacao1 = new Cotacao();
        cotacao1.setId(1L);
        Cotacao cotacao2 = new Cotacao();
        cotacao2.setId(cotacao1.getId());
        assertThat(cotacao1).isEqualTo(cotacao2);
        cotacao2.setId(2L);
        assertThat(cotacao1).isNotEqualTo(cotacao2);
        cotacao1.setId(null);
        assertThat(cotacao1).isNotEqualTo(cotacao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CotacaoDTO.class);
        CotacaoDTO cotacaoDTO1 = new CotacaoDTO();
        cotacaoDTO1.setId(1L);
        CotacaoDTO cotacaoDTO2 = new CotacaoDTO();
        assertThat(cotacaoDTO1).isNotEqualTo(cotacaoDTO2);
        cotacaoDTO2.setId(cotacaoDTO1.getId());
        assertThat(cotacaoDTO1).isEqualTo(cotacaoDTO2);
        cotacaoDTO2.setId(2L);
        assertThat(cotacaoDTO1).isNotEqualTo(cotacaoDTO2);
        cotacaoDTO1.setId(null);
        assertThat(cotacaoDTO1).isNotEqualTo(cotacaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cotacaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cotacaoMapper.fromId(null)).isNull();
    }
}
