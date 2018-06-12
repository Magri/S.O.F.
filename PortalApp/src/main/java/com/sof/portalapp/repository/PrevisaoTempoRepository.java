package com.sof.portalapp.repository;

import com.sof.portalapp.domain.PrevisaoTempo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the PrevisaoTempo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrevisaoTempoRepository extends JpaRepository<PrevisaoTempo, Long> {
    @Query("select distinct previsao_tempo from PrevisaoTempo previsao_tempo left join fetch previsao_tempo.produtors left join fetch previsao_tempo.grupos")
    List<PrevisaoTempo> findAllWithEagerRelationships();

    @Query("select previsao_tempo from PrevisaoTempo previsao_tempo left join fetch previsao_tempo.produtors left join fetch previsao_tempo.grupos where previsao_tempo.id =:id")
    PrevisaoTempo findOneWithEagerRelationships(@Param("id") Long id);

}
