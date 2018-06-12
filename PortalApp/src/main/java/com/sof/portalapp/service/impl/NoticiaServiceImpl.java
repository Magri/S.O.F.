package com.sof.portalapp.service.impl;

import com.sof.portalapp.service.NoticiaService;
import com.sof.portalapp.domain.Noticia;
import com.sof.portalapp.repository.NoticiaRepository;
import com.sof.portalapp.service.dto.NoticiaDTO;
import com.sof.portalapp.service.mapper.NoticiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Noticia.
 */
@Service
@Transactional
public class NoticiaServiceImpl implements NoticiaService {

    private final Logger log = LoggerFactory.getLogger(NoticiaServiceImpl.class);

    private final NoticiaRepository noticiaRepository;

    private final NoticiaMapper noticiaMapper;

    public NoticiaServiceImpl(NoticiaRepository noticiaRepository, NoticiaMapper noticiaMapper) {
        this.noticiaRepository = noticiaRepository;
        this.noticiaMapper = noticiaMapper;
    }

    /**
     * Save a noticia.
     *
     * @param noticiaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NoticiaDTO save(NoticiaDTO noticiaDTO) {
        log.debug("Request to save Noticia : {}", noticiaDTO);
        Noticia noticia = noticiaMapper.toEntity(noticiaDTO);
        noticia = noticiaRepository.save(noticia);
        return noticiaMapper.toDto(noticia);
    }

    /**
     * Get all the noticias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NoticiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Noticias");
        return noticiaRepository.findAll(pageable)
            .map(noticiaMapper::toDto);
    }

    /**
     * Get one noticia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NoticiaDTO findOne(Long id) {
        log.debug("Request to get Noticia : {}", id);
        Noticia noticia = noticiaRepository.findOneWithEagerRelationships(id);
        return noticiaMapper.toDto(noticia);
    }

    /**
     * Delete the noticia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Noticia : {}", id);
        noticiaRepository.delete(id);
    }
}
