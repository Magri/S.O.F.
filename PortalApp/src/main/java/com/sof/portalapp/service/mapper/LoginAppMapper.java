package com.sof.portalapp.service.mapper;

import com.sof.portalapp.domain.*;
import com.sof.portalapp.service.dto.LoginAppDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LoginApp and its DTO LoginAppDTO.
 */
@Mapper(componentModel = "spring", uses = {ProdutorMapper.class})
public interface LoginAppMapper extends EntityMapper<LoginAppDTO, LoginApp> {

    @Mapping(source = "produtor.id", target = "produtorId")
    LoginAppDTO toDto(LoginApp loginApp);

    @Mapping(source = "produtorId", target = "produtor")
    LoginApp toEntity(LoginAppDTO loginAppDTO);

    default LoginApp fromId(Long id) {
        if (id == null) {
            return null;
        }
        LoginApp loginApp = new LoginApp();
        loginApp.setId(id);
        return loginApp;
    }
}
