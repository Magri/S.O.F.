package com.sof.portalapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sof.portalapp.service.NoticiaService;
import com.sof.portalapp.web.rest.errors.BadRequestAlertException;
import com.sof.portalapp.web.rest.util.HeaderUtil;
import com.sof.portalapp.web.rest.util.PaginationUtil;
import com.sof.portalapp.service.dto.NoticiaDTO;
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
 * REST controller for managing Noticia.
 */
@RestController
@RequestMapping("/api")
public class NoticiaResource {

    private final Logger log = LoggerFactory.getLogger(NoticiaResource.class);

    private static final String ENTITY_NAME = "noticia";

    private final NoticiaService noticiaService;

    public NoticiaResource(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    /**
     * POST  /noticias : Create a new noticia.
     *
     * @param noticiaDTO the noticiaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new noticiaDTO, or with status 400 (Bad Request) if the noticia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/noticias")
    @Timed
    public ResponseEntity<NoticiaDTO> createNoticia(@Valid @RequestBody NoticiaDTO noticiaDTO) throws URISyntaxException {
        log.debug("REST request to save Noticia : {}", noticiaDTO);
        if (noticiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new noticia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoticiaDTO result = noticiaService.save(noticiaDTO);
        return ResponseEntity.created(new URI("/api/noticias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /noticias : Updates an existing noticia.
     *
     * @param noticiaDTO the noticiaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated noticiaDTO,
     * or with status 400 (Bad Request) if the noticiaDTO is not valid,
     * or with status 500 (Internal Server Error) if the noticiaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/noticias")
    @Timed
    public ResponseEntity<NoticiaDTO> updateNoticia(@Valid @RequestBody NoticiaDTO noticiaDTO) throws URISyntaxException {
        log.debug("REST request to update Noticia : {}", noticiaDTO);
        if (noticiaDTO.getId() == null) {
            return createNoticia(noticiaDTO);
        }
        NoticiaDTO result = noticiaService.save(noticiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, noticiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /noticias : get all the noticias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of noticias in body
     */
    @GetMapping("/noticias")
    @Timed
    public ResponseEntity<List<NoticiaDTO>> getAllNoticias(Pageable pageable) {
        log.debug("REST request to get a page of Noticias");
        Page<NoticiaDTO> page = noticiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/noticias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /noticias/:id : get the "id" noticia.
     *
     * @param id the id of the noticiaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the noticiaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/noticias/{id}")
    @Timed
    public ResponseEntity<NoticiaDTO> getNoticia(@PathVariable Long id) {
        log.debug("REST request to get Noticia : {}", id);
        NoticiaDTO noticiaDTO = noticiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(noticiaDTO));
    }

    /**
     * DELETE  /noticias/:id : delete the "id" noticia.
     *
     * @param id the id of the noticiaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/noticias/{id}")
    @Timed
    public ResponseEntity<Void> deleteNoticia(@PathVariable Long id) {
        log.debug("REST request to delete Noticia : {}", id);
        noticiaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
