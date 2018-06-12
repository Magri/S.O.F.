package com.sof.portalapp.service.impl;

import com.sof.portalapp.service.LoginAppService;
import com.sof.portalapp.domain.LoginApp;
import com.sof.portalapp.repository.LoginAppRepository;
import com.sof.portalapp.service.dto.LoginAppDTO;
import com.sof.portalapp.service.mapper.LoginAppMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing LoginApp.
 */
@Service
@Transactional
public class LoginAppServiceImpl implements LoginAppService {

    private final Logger log = LoggerFactory.getLogger(LoginAppServiceImpl.class);

    private final LoginAppRepository loginAppRepository;

    private final LoginAppMapper loginAppMapper;

    public LoginAppServiceImpl(LoginAppRepository loginAppRepository, LoginAppMapper loginAppMapper) {
        this.loginAppRepository = loginAppRepository;
        this.loginAppMapper = loginAppMapper;
    }

    /**
     * Save a loginApp.
     *
     * @param loginAppDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LoginAppDTO save(LoginAppDTO loginAppDTO) {
        log.debug("Request to save LoginApp : {}", loginAppDTO);
        LoginApp loginApp = loginAppMapper.toEntity(loginAppDTO);
        loginApp = loginAppRepository.save(loginApp);
        return loginAppMapper.toDto(loginApp);
    }

    /**
     * Get all the loginApps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoginAppDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoginApps");
        return loginAppRepository.findAll(pageable)
            .map(loginAppMapper::toDto);
    }

    /**
     * Get one loginApp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LoginAppDTO findOne(Long id) {
        log.debug("Request to get LoginApp : {}", id);
        LoginApp loginApp = loginAppRepository.findOne(id);
        return loginAppMapper.toDto(loginApp);
    }

    /**
     * Delete the loginApp by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoginApp : {}", id);
        loginAppRepository.delete(id);
    }
}
