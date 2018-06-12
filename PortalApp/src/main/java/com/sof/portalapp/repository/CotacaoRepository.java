package com.sof.portalapp.repository;

import com.sof.portalapp.domain.Cotacao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Cotacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {
    @Query("select distinct cotacao from Cotacao cotacao left join fetch cotacao.produtors left join fetch cotacao.grupos")
    List<Cotacao> findAllWithEagerRelationships();

    @Query("select cotacao from Cotacao cotacao left join fetch cotacao.produtors left join fetch cotacao.grupos where cotacao.id =:id")
    Cotacao findOneWithEagerRelationships(@Param("id") Long id);

}
