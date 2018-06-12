package com.sof.portalapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Grupo entity.
 */
public class GrupoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long codigo;

    @NotNull
    private String descricao;

    private Long empresaId;

    private Set<ProdutorDTO> produtors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Set<ProdutorDTO> getProdutors() {
        return produtors;
    }

    public void setProdutors(Set<ProdutorDTO> produtors) {
        this.produtors = produtors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GrupoDTO grupoDTO = (GrupoDTO) o;
        if(grupoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grupoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrupoDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
