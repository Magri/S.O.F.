package com.sof.portalapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sof.portalapp.service.ProdutorService;
import com.sof.portalapp.web.rest.errors.BadRequestAlertException;
import com.sof.portalapp.web.rest.util.HeaderUtil;
import com.sof.portalapp.web.rest.util.PaginationUtil;
import com.sof.portalapp.service.dto.ProdutorDTO;
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
 * REST controller for managing Produtor.
 */
@RestController
@RequestMapping("/api")
public class ProdutorResource {

    private final Logger log = LoggerFactory.getLogger(ProdutorResource.class);

    private static final String ENTITY_NAME = "produtor";

    private final ProdutorService produtorService;

    public ProdutorResource(ProdutorService produtorService) {
        this.produtorService = produtorService;
    }

    /**
     * POST  /produtors : Create a new produtor.
     *
     * @param produtorDTO the produtorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produtorDTO, or with status 400 (Bad Request) if the produtor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produtors")
    @Timed
    public ResponseEntity<ProdutorDTO> createProdutor(@Valid @RequestBody ProdutorDTO produtorDTO) throws URISyntaxException {
        log.debug("REST request to save Produtor : {}", produtorDTO);
        if (produtorDTO.getId() != null) {
            throw new BadRequestAlertException("A new produtor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProdutorDTO result = produtorService.save(produtorDTO);
        return ResponseEntity.created(new URI("/api/produtors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produtors : Updates an existing produtor.
     *
     * @param produtorDTO the produtorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produtorDTO,
     * or with status 400 (Bad Request) if the produtorDTO is not valid,
     * or with status 500 (Internal Server Error) if the produtorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produtors")
    @Timed
    public ResponseEntity<ProdutorDTO> updateProdutor(@Valid @RequestBody ProdutorDTO produtorDTO) throws URISyntaxException {
        log.debug("REST request to update Produtor : {}", produtorDTO);
        if (produtorDTO.getId() == null) {
            return createProdutor(produtorDTO);
        }
        ProdutorDTO result = produtorService.save(produtorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produtorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produtors : get all the produtors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of produtors in body
     */
    @GetMapping("/produtors")
    @Timed
    public ResponseEntity<List<ProdutorDTO>> getAllProdutors(Pageable pageable) {
        log.debug("REST request to get a page of Produtors");
        Page<ProdutorDTO> page = produtorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produtors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /produtors/:id : get the "id" produtor.
     *
     * @param id the id of the produtorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produtorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/produtors/{id}")
    @Timed
    public ResponseEntity<ProdutorDTO> getProdutor(@PathVariable Long id) {
        log.debug("REST request to get Produtor : {}", id);
        ProdutorDTO produtorDTO = produtorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produtorDTO));
    }

    /**
     * DELETE  /produtors/:id : delete the "id" produtor.
     *
     * @param id the id of the produtorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produtors/{id}")
    @Timed
    public ResponseEntity<Void> deleteProdutor(@PathVariable Long id) {
        log.debug("REST request to delete Produtor : {}", id);
        produtorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
