package com.sof.portalapp.service.impl;

import com.sof.portalapp.service.GrupoService;
import com.sof.portalapp.domain.Grupo;
import com.sof.portalapp.repository.GrupoRepository;
import com.sof.portalapp.service.dto.GrupoDTO;
import com.sof.portalapp.service.mapper.GrupoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Grupo.
 */
@Service
@Transactional
public class GrupoServiceImpl implements GrupoService {

    private final Logger log = LoggerFactory.getLogger(GrupoServiceImpl.class);

    private final GrupoRepository grupoRepository;

    private final GrupoMapper grupoMapper;

    public GrupoServiceImpl(GrupoRepository grupoRepository, GrupoMapper grupoMapper) {
        this.grupoRepository = grupoRepository;
        this.grupoMapper = grupoMapper;
    }

    /**
     * Save a grupo.
     *
     * @param grupoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GrupoDTO save(GrupoDTO grupoDTO) {
        log.debug("Request to save Grupo : {}", grupoDTO);
        Grupo grupo = grupoMapper.toEntity(grupoDTO);
        grupo = grupoRepository.save(grupo);
        return grupoMapper.toDto(grupo);
    }

    /**
     * Get all the grupos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GrupoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Grupos");
        return grupoRepository.findAll(pageable)
            .map(grupoMapper::toDto);
    }

    /**
     * Get one grupo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GrupoDTO findOne(Long id) {
        log.debug("Request to get Grupo : {}", id);
        Grupo grupo = grupoRepository.findOneWithEagerRelationships(id);
        return grupoMapper.toDto(grupo);
    }

    /**
     * Delete the grupo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Grupo : {}", id);
        grupoRepository.delete(id);
    }
}
