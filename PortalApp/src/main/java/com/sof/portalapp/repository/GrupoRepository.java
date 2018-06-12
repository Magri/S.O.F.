package com.sof.portalapp.repository;

import com.sof.portalapp.domain.Grupo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Grupo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    @Query("select distinct grupo from Grupo grupo left join fetch grupo.produtors")
    List<Grupo> findAllWithEagerRelationships();

    @Query("select grupo from Grupo grupo left join fetch grupo.produtors where grupo.id =:id")
    Grupo findOneWithEagerRelationships(@Param("id") Long id);

}
