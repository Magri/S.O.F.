package com.sof.portalapp.service.mapper;

import com.sof.portalapp.domain.*;
import com.sof.portalapp.service.dto.PrevisaoTempoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PrevisaoTempo and its DTO PrevisaoTempoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProdutorMapper.class, GrupoMapper.class})
public interface PrevisaoTempoMapper extends EntityMapper<PrevisaoTempoDTO, PrevisaoTempo> {



    default PrevisaoTempo fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrevisaoTempo previsaoTempo = new PrevisaoTempo();
        previsaoTempo.setId(id);
        return previsaoTempo;
    }
}
