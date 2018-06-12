package com.sof.portalapp.service.mapper;

import com.sof.portalapp.domain.*;
import com.sof.portalapp.service.dto.EmpresaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Empresa and its DTO EmpresaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {



    default Empresa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }
}
