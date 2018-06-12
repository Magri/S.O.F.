package com.sof.portalapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.sof.portalapp.domain.enumeration.Origem;

/**
 * A DTO for the Produto entity.
 */
public class ProdutoDTO implements Serializable {

    private Long id;

    @NotNull
    private String unidadeComercial;

    @NotNull
    private String descricao;

    private Origem origem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnidadeComercial() {
        return unidadeComercial;
    }

    public void setUnidadeComercial(String unidadeComercial) {
        this.unidadeComercial = unidadeComercial;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProdutoDTO produtoDTO = (ProdutoDTO) o;
        if(produtoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produtoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
            "id=" + getId() +
            ", unidadeComercial='" + getUnidadeComercial() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", origem='" + getOrigem() + "'" +
            "}";
    }
}
