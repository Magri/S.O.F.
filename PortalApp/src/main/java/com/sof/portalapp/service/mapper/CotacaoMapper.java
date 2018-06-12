package com.sof.portalapp.service.mapper;

import com.sof.portalapp.domain.*;
import com.sof.portalapp.service.dto.CotacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cotacao and its DTO CotacaoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProdutoMapper.class, ProdutorMapper.class, GrupoMapper.class})
public interface CotacaoMapper extends EntityMapper<CotacaoDTO, Cotacao> {

    @Mapping(source = "produto.id", target = "produtoId")
    CotacaoDTO toDto(Cotacao cotacao);

    @Mapping(source = "produtoId", target = "produto")
    Cotacao toEntity(CotacaoDTO cotacaoDTO);

    default Cotacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cotacao cotacao = new Cotacao();
        cotacao.setId(id);
        return cotacao;
    }
}
