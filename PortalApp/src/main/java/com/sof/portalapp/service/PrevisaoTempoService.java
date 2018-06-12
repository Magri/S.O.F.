package com.sof.portalapp.service;

import com.sof.portalapp.service.dto.PrevisaoTempoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PrevisaoTempo.
 */
public interface PrevisaoTempoService {

    /**
     * Save a previsaoTempo.
     *
     * @param previsaoTempoDTO the entity to save
     * @return the persisted entity
     */
    PrevisaoTempoDTO save(PrevisaoTempoDTO previsaoTempoDTO);

    /**
     * Get all the previsaoTempos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PrevisaoTempoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" previsaoTempo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PrevisaoTempoDTO findOne(Long id);

    /**
     * Delete the "id" previsaoTempo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
