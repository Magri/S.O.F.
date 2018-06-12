package com.sof.portalapp.service.impl;

import com.sof.portalapp.service.PrevisaoTempoService;
import com.sof.portalapp.domain.PrevisaoTempo;
import com.sof.portalapp.repository.PrevisaoTempoRepository;
import com.sof.portalapp.service.dto.PrevisaoTempoDTO;
import com.sof.portalapp.service.mapper.PrevisaoTempoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PrevisaoTempo.
 */
@Service
@Transactional
public class PrevisaoTempoServiceImpl implements PrevisaoTempoService {

    private final Logger log = LoggerFactory.getLogger(PrevisaoTempoServiceImpl.class);

    private final PrevisaoTempoRepository previsaoTempoRepository;

    private final PrevisaoTempoMapper previsaoTempoMapper;

    public PrevisaoTempoServiceImpl(PrevisaoTempoRepository previsaoTempoRepository, PrevisaoTempoMapper previsaoTempoMapper) {
        this.previsaoTempoRepository = previsaoTempoRepository;
        this.previsaoTempoMapper = previsaoTempoMapper;
    }

    /**
     * Save a previsaoTempo.
     *
     * @param previsaoTempoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PrevisaoTempoDTO save(PrevisaoTempoDTO previsaoTempoDTO) {
        log.debug("Request to save PrevisaoTempo : {}", previsaoTempoDTO);
        PrevisaoTempo previsaoTempo = previsaoTempoMapper.toEntity(previsaoTempoDTO);
        previsaoTempo = previsaoTempoRepository.save(previsaoTempo);
        return previsaoTempoMapper.toDto(previsaoTempo);
    }

    /**
     * Get all the previsaoTempos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrevisaoTempoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrevisaoTempos");
        return previsaoTempoRepository.findAll(pageable)
            .map(previsaoTempoMapper::toDto);
    }

    /**
     * Get one previsaoTempo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PrevisaoTempoDTO findOne(Long id) {
        log.debug("Request to get PrevisaoTempo : {}", id);
        PrevisaoTempo previsaoTempo = previsaoTempoRepository.findOneWithEagerRelationships(id);
        return previsaoTempoMapper.toDto(previsaoTempo);
    }

    /**
     * Delete the previsaoTempo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrevisaoTempo : {}", id);
        previsaoTempoRepository.delete(id);
    }
}
