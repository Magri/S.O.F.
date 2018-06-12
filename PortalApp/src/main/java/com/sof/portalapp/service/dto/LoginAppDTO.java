package com.sof.portalapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LoginApp entity.
 */
public class LoginAppDTO implements Serializable {

    private Long id;

    @NotNull
    private String usuario;

    @NotNull
    private String senha;

    private Long produtorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getProdutorId() {
        return produtorId;
    }

    public void setProdutorId(Long produtorId) {
        this.produtorId = produtorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoginAppDTO loginAppDTO = (LoginAppDTO) o;
        if(loginAppDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loginAppDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LoginAppDTO{" +
            "id=" + getId() +
            ", usuario='" + getUsuario() + "'" +
            ", senha='" + getSenha() + "'" +
            "}";
    }
}
