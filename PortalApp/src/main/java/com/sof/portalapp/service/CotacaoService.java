package com.sof.portalapp.service;

import com.sof.portalapp.service.dto.CotacaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cotacao.
 */
public interface CotacaoService {

    /**
     * Save a cotacao.
     *
     * @param cotacaoDTO the entity to save
     * @return the persisted entity
     */
    CotacaoDTO save(CotacaoDTO cotacaoDTO);

    /**
     * Get all the cotacaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CotacaoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cotacao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CotacaoDTO findOne(Long id);

    /**
     * Delete the "id" cotacao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
