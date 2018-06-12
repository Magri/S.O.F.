package com.sof.portalapp.service;

import com.sof.portalapp.service.dto.ProdutorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Produtor.
 */
public interface ProdutorService {

    /**
     * Save a produtor.
     *
     * @param produtorDTO the entity to save
     * @return the persisted entity
     */
    ProdutorDTO save(ProdutorDTO produtorDTO);

    /**
     * Get all the produtors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProdutorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" produtor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProdutorDTO findOne(Long id);

    /**
     * Delete the "id" produtor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
