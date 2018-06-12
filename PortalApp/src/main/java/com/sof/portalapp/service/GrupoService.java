package com.sof.portalapp.service;

import com.sof.portalapp.service.dto.GrupoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Grupo.
 */
public interface GrupoService {

    /**
     * Save a grupo.
     *
     * @param grupoDTO the entity to save
     * @return the persisted entity
     */
    GrupoDTO save(GrupoDTO grupoDTO);

    /**
     * Get all the grupos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GrupoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" grupo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GrupoDTO findOne(Long id);

    /**
     * Delete the "id" grupo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
