package com.sof.portalapp.repository;

import com.sof.portalapp.domain.Produtor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Produtor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutorRepository extends JpaRepository<Produtor, Long> {

}
