package com.sof.portalapp.service.mapper;

import com.sof.portalapp.domain.*;
import com.sof.portalapp.service.dto.NoticiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Noticia and its DTO NoticiaDTO.
 */
@Mapper(componentModel = "spring", uses = {ProdutorMapper.class, GrupoMapper.class})
public interface NoticiaMapper extends EntityMapper<NoticiaDTO, Noticia> {



    default Noticia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Noticia noticia = new Noticia();
        noticia.setId(id);
        return noticia;
    }
}
