package com.sof.portalapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sof.portalapp.service.LoginAppService;
import com.sof.portalapp.web.rest.errors.BadRequestAlertException;
import com.sof.portalapp.web.rest.util.HeaderUtil;
import com.sof.portalapp.web.rest.util.PaginationUtil;
import com.sof.portalapp.service.dto.LoginAppDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LoginApp.
 */
@RestController
@RequestMapping("/api")
public class LoginAppResource {

    private final Logger log = LoggerFactory.getLogger(LoginAppResource.class);

    private static final String ENTITY_NAME = "loginApp";

    private final LoginAppService loginAppService;

    public LoginAppResource(LoginAppService loginAppService) {
        this.loginAppService = loginAppService;
    }

    /**
     * POST  /login-apps : Create a new loginApp.
     *
     * @param loginAppDTO the loginAppDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loginAppDTO, or with status 400 (Bad Request) if the loginApp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/login-apps")
    @Timed
    public ResponseEntity<LoginAppDTO> createLoginApp(@Valid @RequestBody LoginAppDTO loginAppDTO) throws URISyntaxException {
        log.debug("REST request to save LoginApp : {}", loginAppDTO);
        if (loginAppDTO.getId() != null) {
            throw new BadRequestAlertException("A new loginApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoginAppDTO result = loginAppService.save(loginAppDTO);
        return ResponseEntity.created(new URI("/api/login-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /login-apps : Updates an existing loginApp.
     *
     * @param loginAppDTO the loginAppDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loginAppDTO,
     * or with status 400 (Bad Request) if the loginAppDTO is not valid,
     * or with status 500 (Internal Server Error) if the loginAppDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/login-apps")
    @Timed
    public ResponseEntity<LoginAppDTO> updateLoginApp(@Valid @RequestBody LoginAppDTO loginAppDTO) throws URISyntaxException {
        log.debug("REST request to update LoginApp : {}", loginAppDTO);
        if (loginAppDTO.getId() == null) {
            return createLoginApp(loginAppDTO);
        }
        LoginAppDTO result = loginAppService.save(loginAppDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loginAppDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /login-apps : get all the loginApps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of loginApps in body
     */
    @GetMapping("/login-apps")
    @Timed
    public ResponseEntity<List<LoginAppDTO>> getAllLoginApps(Pageable pageable) {
        log.debug("REST request to get a page of LoginApps");
        Page<LoginAppDTO> page = loginAppService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/login-apps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /login-apps/:id : get the "id" loginApp.
     *
     * @param id the id of the loginAppDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loginAppDTO, or with status 404 (Not Found)
     */
    @GetMapping("/login-apps/{id}")
    @Timed
    public ResponseEntity<LoginAppDTO> getLoginApp(@PathVariable Long id) {
        log.debug("REST request to get LoginApp : {}", id);
        LoginAppDTO loginAppDTO = loginAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(loginAppDTO));
    }

    /**
     * DELETE  /login-apps/:id : delete the "id" loginApp.
     *
     * @param id the id of the loginAppDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/login-apps/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoginApp(@PathVariable Long id) {
        log.debug("REST request to delete LoginApp : {}", id);
        loginAppService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
