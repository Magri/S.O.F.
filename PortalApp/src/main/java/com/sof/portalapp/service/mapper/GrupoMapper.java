package com.sof.portalapp.service.mapper;

import com.sof.portalapp.domain.*;
import com.sof.portalapp.service.dto.GrupoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Grupo and its DTO GrupoDTO.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, ProdutorMapper.class})
public interface GrupoMapper extends EntityMapper<GrupoDTO, Grupo> {

    @Mapping(source = "empresa.id", target = "empresaId")
    GrupoDTO toDto(Grupo grupo);

    @Mapping(source = "empresaId", target = "empresa")
    @Mapping(target = "noticias", ignore = true)
    @Mapping(target = "previsaoTempos", ignore = true)
    @Mapping(target = "cotacaos", ignore = true)
    Grupo toEntity(GrupoDTO grupoDTO);

    default Grupo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Grupo grupo = new Grupo();
        grupo.setId(id);
        return grupo;
    }
}
