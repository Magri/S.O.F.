package com.sof.portalapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cotacao.
 */
@Entity
@Table(name = "cotacao")
public class Cotacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_cotacao", nullable = false)
    private Instant dataCotacao;

    @NotNull
    @Column(name = "preco", nullable = false)
    private Long preco;

    @NotNull
    @Column(name = "descricao_customizada_produto", nullable = false)
    private String descricaoCustomizadaProduto;

    @OneToOne
    @JoinColumn(unique = true)
    private Produto produto;

    @ManyToMany
    @JoinTable(name = "cotacao_produtor",
               joinColumns = @JoinColumn(name="cotacaos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="produtors_id", referencedColumnName="id"))
    private Set<Produtor> produtors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "cotacao_grupo",
               joinColumns = @JoinColumn(name="cotacaos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="grupos_id", referencedColumnName="id"))
    private Set<Grupo> grupos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataCotacao() {
        return dataCotacao;
    }

    public Cotacao dataCotacao(Instant dataCotacao) {
        this.dataCotacao = dataCotacao;
        return this;
    }

    public void setDataCotacao(Instant dataCotacao) {
        this.dataCotacao = dataCotacao;
    }

    public Long getPreco() {
        return preco;
    }

    public Cotacao preco(Long preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(Long preco) {
        this.preco = preco;
    }

    public String getDescricaoCustomizadaProduto() {
        return descricaoCustomizadaProduto;
    }

    public Cotacao descricaoCustomizadaProduto(String descricaoCustomizadaProduto) {
        this.descricaoCustomizadaProduto = descricaoCustomizadaProduto;
        return this;
    }

    public void setDescricaoCustomizadaProduto(String descricaoCustomizadaProduto) {
        this.descricaoCustomizadaProduto = descricaoCustomizadaProduto;
    }

    public Produto getProduto() {
        return produto;
    }

    public Cotacao produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Set<Produtor> getProdutors() {
        return produtors;
    }

    public Cotacao produtors(Set<Produtor> produtors) {
        this.produtors = produtors;
        return this;
    }

    public Cotacao addProdutor(Produtor produtor) {
        this.produtors.add(produtor);
        produtor.getCotacaos().add(this);
        return this;
    }

    public Cotacao removeProdutor(Produtor produtor) {
        this.produtors.remove(produtor);
        produtor.getCotacaos().remove(this);
        return this;
    }

    public void setProdutors(Set<Produtor> produtors) {
        this.produtors = produtors;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public Cotacao grupos(Set<Grupo> grupos) {
        this.grupos = grupos;
        return this;
    }

    public Cotacao addGrupo(Grupo grupo) {
        this.grupos.add(grupo);
        grupo.getCotacaos().add(this);
        return this;
    }

    public Cotacao removeGrupo(Grupo grupo) {
        this.grupos.remove(grupo);
        grupo.getCotacaos().remove(this);
        return this;
    }

    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cotacao cotacao = (Cotacao) o;
        if (cotacao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cotacao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cotacao{" +
            "id=" + getId() +
            ", dataCotacao='" + getDataCotacao() + "'" +
            ", preco=" + getPreco() +
            ", descricaoCustomizadaProduto='" + getDescricaoCustomizadaProduto() + "'" +
            "}";
    }
}
