package com.sof.portalapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.sof.portalapp.domain.enumeration.Origem;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "unidade_comercial", nullable = false)
    private String unidadeComercial;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "origem")
    private Origem origem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnidadeComercial() {
        return unidadeComercial;
    }

    public Produto unidadeComercial(String unidadeComercial) {
        this.unidadeComercial = unidadeComercial;
        return this;
    }

    public void setUnidadeComercial(String unidadeComercial) {
        this.unidadeComercial = unidadeComercial;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Origem getOrigem() {
        return origem;
    }

    public Produto origem(Origem origem) {
        this.origem = origem;
        return this;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
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
        Produto produto = (Produto) o;
        if (produto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", unidadeComercial='" + getUnidadeComercial() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", origem='" + getOrigem() + "'" +
            "}";
    }
}
