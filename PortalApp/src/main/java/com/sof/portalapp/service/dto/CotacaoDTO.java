package com.sof.portalapp.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Cotacao entity.
 */
public class CotacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dataCotacao;

    @NotNull
    private Long preco;

    @NotNull
    private String descricaoCustomizadaProduto;

    private Long produtoId;

    private Set<ProdutorDTO> produtors = new HashSet<>();

    private Set<GrupoDTO> grupos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataCotacao() {
        return dataCotacao;
    }

    public void setDataCotacao(Instant dataCotacao) {
        this.dataCotacao = dataCotacao;
    }

    public Long getPreco() {
        return preco;
    }

    public void setPreco(Long preco) {
        this.preco = preco;
    }

    public String getDescricaoCustomizadaProduto() {
        return descricaoCustomizadaProduto;
    }

    public void setDescricaoCustomizadaProduto(String descricaoCustomizadaProduto) {
        this.descricaoCustomizadaProduto = descricaoCustomizadaProduto;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Set<ProdutorDTO> getProdutors() {
        return produtors;
    }

    public void setProdutors(Set<ProdutorDTO> produtors) {
        this.produtors = produtors;
    }

    public Set<GrupoDTO> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<GrupoDTO> grupos) {
        this.grupos = grupos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CotacaoDTO cotacaoDTO = (CotacaoDTO) o;
        if(cotacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cotacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CotacaoDTO{" +
            "id=" + getId() +
            ", dataCotacao='" + getDataCotacao() + "'" +
            ", preco=" + getPreco() +
            ", descricaoCustomizadaProduto='" + getDescricaoCustomizadaProduto() + "'" +
            "}";
    }
}
