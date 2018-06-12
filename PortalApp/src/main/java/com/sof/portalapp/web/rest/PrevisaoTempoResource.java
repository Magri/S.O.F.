package com.sof.portalapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sof.portalapp.service.PrevisaoTempoService;
import com.sof.portalapp.web.rest.errors.BadRequestAlertException;
import com.sof.portalapp.web.rest.util.HeaderUtil;
import com.sof.portalapp.web.rest.util.PaginationUtil;
import com.sof.portalapp.service.dto.PrevisaoTempoDTO;
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
 * REST controller for managing PrevisaoTempo.
 */
@RestController
@RequestMapping("/api")
public class PrevisaoTempoResource {

    private final Logger log = LoggerFactory.getLogger(PrevisaoTempoResource.class);

    private static final String ENTITY_NAME = "previsaoTempo";

    private final PrevisaoTempoService previsaoTempoService;

    public PrevisaoTempoResource(PrevisaoTempoService previsaoTempoService) {
        this.previsaoTempoService = previsaoTempoService;
    }

    /**
     * POST  /previsao-tempos : Create a new previsaoTempo.
     *
     * @param previsaoTempoDTO the previsaoTempoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new previsaoTempoDTO, or with status 400 (Bad Request) if the previsaoTempo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/previsao-tempos")
    @Timed
    public ResponseEntity<PrevisaoTempoDTO> createPrevisaoTempo(@Valid @RequestBody PrevisaoTempoDTO previsaoTempoDTO) throws URISyntaxException {
        log.debug("REST request to save PrevisaoTempo : {}", previsaoTempoDTO);
        if (previsaoTempoDTO.getId() != null) {
            throw new BadRequestAlertException("A new previsaoTempo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrevisaoTempoDTO result = previsaoTempoService.save(previsaoTempoDTO);
        return ResponseEntity.created(new URI("/api/previsao-tempos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /previsao-tempos : Updates an existing previsaoTempo.
     *
     * @param previsaoTempoDTO the previsaoTempoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated previsaoTempoDTO,
     * or with status 400 (Bad Request) if the previsaoTempoDTO is not valid,
     * or with status 500 (Internal Server Error) if the previsaoTempoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/previsao-tempos")
    @Timed
    public ResponseEntity<PrevisaoTempoDTO> updatePrevisaoTempo(@Valid @RequestBody PrevisaoTempoDTO previsaoTempoDTO) throws URISyntaxException {
        log.debug("REST request to update PrevisaoTempo : {}", previsaoTempoDTO);
        if (previsaoTempoDTO.getId() == null) {
            return createPrevisaoTempo(previsaoTempoDTO);
        }
        PrevisaoTempoDTO result = previsaoTempoService.save(previsaoTempoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, previsaoTempoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /previsao-tempos : get all the previsaoTempos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of previsaoTempos in body
     */
    @GetMapping("/previsao-tempos")
    @Timed
    public ResponseEntity<List<PrevisaoTempoDTO>> getAllPrevisaoTempos(Pageable pageable) {
        log.debug("REST request to get a page of PrevisaoTempos");
        Page<PrevisaoTempoDTO> page = previsaoTempoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/previsao-tempos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /previsao-tempos/:id : get the "id" previsaoTempo.
     *
     * @param id the id of the previsaoTempoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the previsaoTempoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/previsao-tempos/{id}")
    @Timed
    public ResponseEntity<PrevisaoTempoDTO> getPrevisaoTempo(@PathVariable Long id) {
        log.debug("REST request to get PrevisaoTempo : {}", id);
        PrevisaoTempoDTO previsaoTempoDTO = previsaoTempoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(previsaoTempoDTO));
    }

    /**
     * DELETE  /previsao-tempos/:id : delete the "id" previsaoTempo.
     *
     * @param id the id of the previsaoTempoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/previsao-tempos/{id}")
    @Timed
    public ResponseEntity<Void> deletePrevisaoTempo(@PathVariable Long id) {
        log.debug("REST request to delete PrevisaoTempo : {}", id);
        previsaoTempoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
