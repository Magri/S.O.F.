package com.sof.portalapp.web.rest;

import com.sof.portalapp.PortalApp;

import com.sof.portalapp.domain.LoginApp;
import com.sof.portalapp.repository.LoginAppRepository;
import com.sof.portalapp.service.LoginAppService;
import com.sof.portalapp.service.dto.LoginAppDTO;
import com.sof.portalapp.service.mapper.LoginAppMapper;
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
import java.util.List;

import static com.sof.portalapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LoginAppResource REST controller.
 *
 * @see LoginAppResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PortalApp.class)
public class LoginAppResourceIntTest {

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_SENHA = "AAAAAAAAAA";
    private static final String UPDATED_SENHA = "BBBBBBBBBB";

    @Autowired
    private LoginAppRepository loginAppRepository;

    @Autowired
    private LoginAppMapper loginAppMapper;

    @Autowired
    private LoginAppService loginAppService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLoginAppMockMvc;

    private LoginApp loginApp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoginAppResource loginAppResource = new LoginAppResource(loginAppService);
        this.restLoginAppMockMvc = MockMvcBuilders.standaloneSetup(loginAppResource)
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
    public static LoginApp createEntity(EntityManager em) {
        LoginApp loginApp = new LoginApp()
            .usuario(DEFAULT_USUARIO)
            .senha(DEFAULT_SENHA);
        return loginApp;
    }

    @Before
    public void initTest() {
        loginApp = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoginApp() throws Exception {
        int databaseSizeBeforeCreate = loginAppRepository.findAll().size();

        // Create the LoginApp
        LoginAppDTO loginAppDTO = loginAppMapper.toDto(loginApp);
        restLoginAppMockMvc.perform(post("/api/login-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginAppDTO)))
            .andExpect(status().isCreated());

        // Validate the LoginApp in the database
        List<LoginApp> loginAppList = loginAppRepository.findAll();
        assertThat(loginAppList).hasSize(databaseSizeBeforeCreate + 1);
        LoginApp testLoginApp = loginAppList.get(loginAppList.size() - 1);
        assertThat(testLoginApp.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testLoginApp.getSenha()).isEqualTo(DEFAULT_SENHA);
    }

    @Test
    @Transactional
    public void createLoginAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loginAppRepository.findAll().size();

        // Create the LoginApp with an existing ID
        loginApp.setId(1L);
        LoginAppDTO loginAppDTO = loginAppMapper.toDto(loginApp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginAppMockMvc.perform(post("/api/login-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginAppDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoginApp in the database
        List<LoginApp> loginAppList = loginAppRepository.findAll();
        assertThat(loginAppList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = loginAppRepository.findAll().size();
        // set the field null
        loginApp.setUsuario(null);

        // Create the LoginApp, which fails.
        LoginAppDTO loginAppDTO = loginAppMapper.toDto(loginApp);

        restLoginAppMockMvc.perform(post("/api/login-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginAppDTO)))
            .andExpect(status().isBadRequest());

        List<LoginApp> loginAppList = loginAppRepository.findAll();
        assertThat(loginAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSenhaIsRequired() throws Exception {
        int databaseSizeBeforeTest = loginAppRepository.findAll().size();
        // set the field null
        loginApp.setSenha(null);

        // Create the LoginApp, which fails.
        LoginAppDTO loginAppDTO = loginAppMapper.toDto(loginApp);

        restLoginAppMockMvc.perform(post("/api/login-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginAppDTO)))
            .andExpect(status().isBadRequest());

        List<LoginApp> loginAppList = loginAppRepository.findAll();
        assertThat(loginAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoginApps() throws Exception {
        // Initialize the database
        loginAppRepository.saveAndFlush(loginApp);

        // Get all the loginAppList
        restLoginAppMockMvc.perform(get("/api/login-apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO.toString())))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())));
    }

    @Test
    @Transactional
    public void getLoginApp() throws Exception {
        // Initialize the database
        loginAppRepository.saveAndFlush(loginApp);

        // Get the loginApp
        restLoginAppMockMvc.perform(get("/api/login-apps/{id}", loginApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loginApp.getId().intValue()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO.toString()))
            .andExpect(jsonPath("$.senha").value(DEFAULT_SENHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLoginApp() throws Exception {
        // Get the loginApp
        restLoginAppMockMvc.perform(get("/api/login-apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoginApp() throws Exception {
        // Initialize the database
        loginAppRepository.saveAndFlush(loginApp);
        int databaseSizeBeforeUpdate = loginAppRepository.findAll().size();

        // Update the loginApp
        LoginApp updatedLoginApp = loginAppRepository.findOne(loginApp.getId());
        // Disconnect from session so that the updates on updatedLoginApp are not directly saved in db
        em.detach(updatedLoginApp);
        updatedLoginApp
            .usuario(UPDATED_USUARIO)
            .senha(UPDATED_SENHA);
        LoginAppDTO loginAppDTO = loginAppMapper.toDto(updatedLoginApp);

        restLoginAppMockMvc.perform(put("/api/login-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginAppDTO)))
            .andExpect(status().isOk());

        // Validate the LoginApp in the database
        List<LoginApp> loginAppList = loginAppRepository.findAll();
        assertThat(loginAppList).hasSize(databaseSizeBeforeUpdate);
        LoginApp testLoginApp = loginAppList.get(loginAppList.size() - 1);
        assertThat(testLoginApp.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testLoginApp.getSenha()).isEqualTo(UPDATED_SENHA);
    }

    @Test
    @Transactional
    public void updateNonExistingLoginApp() throws Exception {
        int databaseSizeBeforeUpdate = loginAppRepository.findAll().size();

        // Create the LoginApp
        LoginAppDTO loginAppDTO = loginAppMapper.toDto(loginApp);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLoginAppMockMvc.perform(put("/api/login-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginAppDTO)))
            .andExpect(status().isCreated());

        // Validate the LoginApp in the database
        List<LoginApp> loginAppList = loginAppRepository.findAll();
        assertThat(loginAppList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLoginApp() throws Exception {
        // Initialize the database
        loginAppRepository.saveAndFlush(loginApp);
        int databaseSizeBeforeDelete = loginAppRepository.findAll().size();

        // Get the loginApp
        restLoginAppMockMvc.perform(delete("/api/login-apps/{id}", loginApp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LoginApp> loginAppList = loginAppRepository.findAll();
        assertThat(loginAppList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginApp.class);
        LoginApp loginApp1 = new LoginApp();
        loginApp1.setId(1L);
        LoginApp loginApp2 = new LoginApp();
        loginApp2.setId(loginApp1.getId());
        assertThat(loginApp1).isEqualTo(loginApp2);
        loginApp2.setId(2L);
        assertThat(loginApp1).isNotEqualTo(loginApp2);
        loginApp1.setId(null);
        assertThat(loginApp1).isNotEqualTo(loginApp2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginAppDTO.class);
        LoginAppDTO loginAppDTO1 = new LoginAppDTO();
        loginAppDTO1.setId(1L);
        LoginAppDTO loginAppDTO2 = new LoginAppDTO();
        assertThat(loginAppDTO1).isNotEqualTo(loginAppDTO2);
        loginAppDTO2.setId(loginAppDTO1.getId());
        assertThat(loginAppDTO1).isEqualTo(loginAppDTO2);
        loginAppDTO2.setId(2L);
        assertThat(loginAppDTO1).isNotEqualTo(loginAppDTO2);
        loginAppDTO1.setId(null);
        assertThat(loginAppDTO1).isNotEqualTo(loginAppDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(loginAppMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(loginAppMapper.fromId(null)).isNull();
    }
}
