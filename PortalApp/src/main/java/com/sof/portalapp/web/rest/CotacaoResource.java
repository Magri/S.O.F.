package com.sof.portalapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sof.portalapp.service.CotacaoService;
import com.sof.portalapp.web.rest.errors.BadRequestAlertException;
import com.sof.portalapp.web.rest.util.HeaderUtil;
import com.sof.portalapp.web.rest.util.PaginationUtil;
import com.sof.portalapp.service.dto.CotacaoDTO;
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
 * REST controller for managing Cotacao.
 */
@RestController
@RequestMapping("/api")
public class CotacaoResource {

    private final Logger log = LoggerFactory.getLogger(CotacaoResource.class);

    private static final String ENTITY_NAME = "cotacao";

    private final CotacaoService cotacaoService;

    public CotacaoResource(CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }

    /**
     * POST  /cotacaos : Create a new cotacao.
     *
     * @param cotacaoDTO the cotacaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cotacaoDTO, or with status 400 (Bad Request) if the cotacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cotacaos")
    @Timed
    public ResponseEntity<CotacaoDTO> createCotacao(@Valid @RequestBody CotacaoDTO cotacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Cotacao : {}", cotacaoDTO);
        if (cotacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cotacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CotacaoDTO result = cotacaoService.save(cotacaoDTO);
        return ResponseEntity.created(new URI("/api/cotacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cotacaos : Updates an existing cotacao.
     *
     * @param cotacaoDTO the cotacaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cotacaoDTO,
     * or with status 400 (Bad Request) if the cotacaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the cotacaoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cotacaos")
    @Timed
    public ResponseEntity<CotacaoDTO> updateCotacao(@Valid @RequestBody CotacaoDTO cotacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Cotacao : {}", cotacaoDTO);
        if (cotacaoDTO.getId() == null) {
            return createCotacao(cotacaoDTO);
        }
        CotacaoDTO result = cotacaoService.save(cotacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cotacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cotacaos : get all the cotacaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cotacaos in body
     */
    @GetMapping("/cotacaos")
    @Timed
    public ResponseEntity<List<CotacaoDTO>> getAllCotacaos(Pageable pageable) {
        log.debug("REST request to get a page of Cotacaos");
        Page<CotacaoDTO> page = cotacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cotacaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cotacaos/:id : get the "id" cotacao.
     *
     * @param id the id of the cotacaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cotacaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cotacaos/{id}")
    @Timed
    public ResponseEntity<CotacaoDTO> getCotacao(@PathVariable Long id) {
        log.debug("REST request to get Cotacao : {}", id);
        CotacaoDTO cotacaoDTO = cotacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cotacaoDTO));
    }

    /**
     * DELETE  /cotacaos/:id : delete the "id" cotacao.
     *
     * @param id the id of the cotacaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cotacaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCotacao(@PathVariable Long id) {
        log.debug("REST request to delete Cotacao : {}", id);
        cotacaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
