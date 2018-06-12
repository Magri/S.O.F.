package com.sof.portalapp.web.rest;

import com.sof.portalapp.PortalApp;

import com.sof.portalapp.domain.Noticia;
import com.sof.portalapp.repository.NoticiaRepository;
import com.sof.portalapp.service.NoticiaService;
import com.sof.portalapp.service.dto.NoticiaDTO;
import com.sof.portalapp.service.mapper.NoticiaMapper;
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
 * Test class for the NoticiaResource REST controller.
 *
 * @see NoticiaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortalApp.class)
public class NoticiaResourceIntTest {

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_PUBLICACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_PUBLICACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private NoticiaMapper noticiaMapper;

    @Autowired
    private NoticiaService noticiaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNoticiaMockMvc;

    private Noticia noticia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NoticiaResource noticiaResource = new NoticiaResource(noticiaService);
        this.restNoticiaMockMvc = MockMvcBuilders.standaloneSetup(noticiaResource)
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
    public static Noticia createEntity(EntityManager em) {
        Noticia noticia = new Noticia()
            .link(DEFAULT_LINK)
            .texto(DEFAULT_TEXTO)
            .dataPublicacao(DEFAULT_DATA_PUBLICACAO);
        return noticia;
    }

    @Before
    public void initTest() {
        noticia = createEntity(em);
    }

    @Test
    @Transactional
    public void createNoticia() throws Exception {
        int databaseSizeBeforeCreate = noticiaRepository.findAll().size();

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);
        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeCreate + 1);
        Noticia testNoticia = noticiaList.get(noticiaList.size() - 1);
        assertThat(testNoticia.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testNoticia.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testNoticia.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
    }

    @Test
    @Transactional
    public void createNoticiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noticiaRepository.findAll().size();

        // Create the Noticia with an existing ID
        noticia.setId(1L);
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setLink(null);

        // Create the Noticia, which fails.
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextoIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setTexto(null);

        // Create the Noticia, which fails.
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataPublicacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setDataPublicacao(null);

        // Create the Noticia, which fails.
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        restNoticiaMockMvc.perform(post("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNoticias() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);

        // Get all the noticiaList
        restNoticiaMockMvc.perform(get("/api/noticias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticia.getId().intValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO.toString())))
            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())));
    }

    @Test
    @Transactional
    public void getNoticia() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);

        // Get the noticia
        restNoticiaMockMvc.perform(get("/api/noticias/{id}", noticia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(noticia.getId().intValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO.toString()))
            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNoticia() throws Exception {
        // Get the noticia
        restNoticiaMockMvc.perform(get("/api/noticias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoticia() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);
        int databaseSizeBeforeUpdate = noticiaRepository.findAll().size();

        // Update the noticia
        Noticia updatedNoticia = noticiaRepository.findOne(noticia.getId());
        // Disconnect from session so that the updates on updatedNoticia are not directly saved in db
        em.detach(updatedNoticia);
        updatedNoticia
            .link(UPDATED_LINK)
            .texto(UPDATED_TEXTO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO);
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(updatedNoticia);

        restNoticiaMockMvc.perform(put("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isOk());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeUpdate);
        Noticia testNoticia = noticiaList.get(noticiaList.size() - 1);
        assertThat(testNoticia.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testNoticia.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testNoticia.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingNoticia() throws Exception {
        int databaseSizeBeforeUpdate = noticiaRepository.findAll().size();

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNoticiaMockMvc.perform(put("/api/noticias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noticiaDTO)))
            .andExpect(status().isCreated());

        // Validate the Noticia in the database
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNoticia() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);
        int databaseSizeBeforeDelete = noticiaRepository.findAll().size();

        // Get the noticia
        restNoticiaMockMvc.perform(delete("/api/noticias/{id}", noticia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Noticia> noticiaList = noticiaRepository.findAll();
        assertThat(noticiaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Noticia.class);
        Noticia noticia1 = new Noticia();
        noticia1.setId(1L);
        Noticia noticia2 = new Noticia();
        noticia2.setId(noticia1.getId());
        assertThat(noticia1).isEqualTo(noticia2);
        noticia2.setId(2L);
        assertThat(noticia1).isNotEqualTo(noticia2);
        noticia1.setId(null);
        assertThat(noticia1).isNotEqualTo(noticia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoticiaDTO.class);
        NoticiaDTO noticiaDTO1 = new NoticiaDTO();
        noticiaDTO1.setId(1L);
        NoticiaDTO noticiaDTO2 = new NoticiaDTO();
        assertThat(noticiaDTO1).isNotEqualTo(noticiaDTO2);
        noticiaDTO2.setId(noticiaDTO1.getId());
        assertThat(noticiaDTO1).isEqualTo(noticiaDTO2);
        noticiaDTO2.setId(2L);
        assertThat(noticiaDTO1).isNotEqualTo(noticiaDTO2);
        noticiaDTO1.setId(null);
        assertThat(noticiaDTO1).isNotEqualTo(noticiaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(noticiaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(noticiaMapper.fromId(null)).isNull();
    }
}
