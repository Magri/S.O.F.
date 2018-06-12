package com.sof.portalapp.service.mapper;

import com.sof.portalapp.domain.*;
import com.sof.portalapp.service.dto.ProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Produto and its DTO ProdutoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {



    default Produto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produto produto = new Produto();
        produto.setId(id);
        return produto;
    }
}
