package com.sof.portalapp.repository;

import com.sof.portalapp.domain.Noticia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Noticia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    @Query("select distinct noticia from Noticia noticia left join fetch noticia.produtors left join fetch noticia.grupos")
    List<Noticia> findAllWithEagerRelationships();

    @Query("select noticia from Noticia noticia left join fetch noticia.produtors left join fetch noticia.grupos where noticia.id =:id")
    Noticia findOneWithEagerRelationships(@Param("id") Long id);

}
