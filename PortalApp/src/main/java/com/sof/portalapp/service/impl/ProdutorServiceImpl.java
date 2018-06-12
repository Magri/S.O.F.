package com.sof.portalapp.service.impl;

import com.sof.portalapp.service.ProdutorService;
import com.sof.portalapp.domain.Produtor;
import com.sof.portalapp.repository.ProdutorRepository;
import com.sof.portalapp.service.dto.ProdutorDTO;
import com.sof.portalapp.service.mapper.ProdutorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Produtor.
 */
@Service
@Transactional
public class ProdutorServiceImpl implements ProdutorService {

    private final Logger log = LoggerFactory.getLogger(ProdutorServiceImpl.class);

    private final ProdutorRepository produtorRepository;

    private final ProdutorMapper produtorMapper;

    public ProdutorServiceImpl(ProdutorRepository produtorRepository, ProdutorMapper produtorMapper) {
        this.produtorRepository = produtorRepository;
        this.produtorMapper = produtorMapper;
    }

    /**
     * Save a produtor.
     *
     * @param produtorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProdutorDTO save(ProdutorDTO produtorDTO) {
        log.debug("Request to save Produtor : {}", produtorDTO);
        Produtor produtor = produtorMapper.toEntity(produtorDTO);
        produtor = produtorRepository.save(produtor);
        return produtorMapper.toDto(produtor);
    }

    /**
     * Get all the produtors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProdutorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Produtors");
        return produtorRepository.findAll(pageable)
            .map(produtorMapper::toDto);
    }

    /**
     * Get one produtor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProdutorDTO findOne(Long id) {
        log.debug("Request to get Produtor : {}", id);
        Produtor produtor = produtorRepository.findOne(id);
        return produtorMapper.toDto(produtor);
    }

    /**
     * Delete the produtor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produtor : {}", id);
        produtorRepository.delete(id);
    }
}
