package com.sof.portalapp.repository;

import com.sof.portalapp.domain.LoginApp;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LoginApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginAppRepository extends JpaRepository<LoginApp, Long> {

}
