package com.sof.portalapp.service;

import com.sof.portalapp.service.dto.LoginAppDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LoginApp.
 */
public interface LoginAppService {

    /**
     * Save a loginApp.
     *
     * @param loginAppDTO the entity to save
     * @return the persisted entity
     */
    LoginAppDTO save(LoginAppDTO loginAppDTO);

    /**
     * Get all the loginApps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LoginAppDTO> findAll(Pageable pageable);

    /**
     * Get the "id" loginApp.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LoginAppDTO findOne(Long id);

    /**
     * Delete the "id" loginApp.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
