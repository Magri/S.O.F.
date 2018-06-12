package com.sof.portalapp.service.mapper;

import com.sof.portalapp.domain.*;
import com.sof.portalapp.service.dto.ProdutorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Produtor and its DTO ProdutorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProdutorMapper extends EntityMapper<ProdutorDTO, Produtor> {


    @Mapping(target = "grupos", ignore = true)
    @Mapping(target = "noticias", ignore = true)
    @Mapping(target = "previsaoTempos", ignore = true)
    @Mapping(target = "cotacaos", ignore = true)
    Produtor toEntity(ProdutorDTO produtorDTO);

    default Produtor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produtor produtor = new Produtor();
        produtor.setId(id);
        return produtor;
    }
}
