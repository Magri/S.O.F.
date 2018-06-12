package com.sof.portalapp.service.impl;

import com.sof.portalapp.service.CotacaoService;
import com.sof.portalapp.domain.Cotacao;
import com.sof.portalapp.repository.CotacaoRepository;
import com.sof.portalapp.service.dto.CotacaoDTO;
import com.sof.portalapp.service.mapper.CotacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Cotacao.
 */
@Service
@Transactional
public class CotacaoServiceImpl implements CotacaoService {

    private final Logger log = LoggerFactory.getLogger(CotacaoServiceImpl.class);

    private final CotacaoRepository cotacaoRepository;

    private final CotacaoMapper cotacaoMapper;

    public CotacaoServiceImpl(CotacaoRepository cotacaoRepository, CotacaoMapper cotacaoMapper) {
        this.cotacaoRepository = cotacaoRepository;
        this.cotacaoMapper = cotacaoMapper;
    }

    /**
     * Save a cotacao.
     *
     * @param cotacaoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CotacaoDTO save(CotacaoDTO cotacaoDTO) {
        log.debug("Request to save Cotacao : {}", cotacaoDTO);
        Cotacao cotacao = cotacaoMapper.toEntity(cotacaoDTO);
        cotacao = cotacaoRepository.save(cotacao);
        return cotacaoMapper.toDto(cotacao);
    }

    /**
     * Get all the cotacaos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CotacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cotacaos");
        return cotacaoRepository.findAll(pageable)
            .map(cotacaoMapper::toDto);
    }

    /**
     * Get one cotacao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CotacaoDTO findOne(Long id) {
        log.debug("Request to get Cotacao : {}", id);
        Cotacao cotacao = cotacaoRepository.findOneWithEagerRelationships(id);
        return cotacaoMapper.toDto(cotacao);
    }

    /**
     * Delete the cotacao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cotacao : {}", id);
        cotacaoRepository.delete(id);
    }
}
