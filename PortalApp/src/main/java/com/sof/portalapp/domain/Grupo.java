package com.sof.portalapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Grupo.
 */
@Entity
@Table(name = "grupo")
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private Long codigo;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToOne
    @JoinColumn(unique = true)
    private Empresa empresa;

    @ManyToMany
    @JoinTable(name = "grupo_produtor",
               joinColumns = @JoinColumn(name="grupos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="produtors_id", referencedColumnName="id"))
    private Set<Produtor> produtors = new HashSet<>();

    @ManyToMany(mappedBy = "grupos")
    @JsonIgnore
    private Set<Noticia> noticias = new HashSet<>();

    @ManyToMany(mappedBy = "grupos")
    @JsonIgnore
    private Set<PrevisaoTempo> previsaoTempos = new HashSet<>();

    @ManyToMany(mappedBy = "grupos")
    @JsonIgnore
    private Set<Cotacao> cotacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public Grupo codigo(Long codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Grupo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Grupo empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Set<Produtor> getProdutors() {
        return produtors;
    }

    public Grupo produtors(Set<Produtor> produtors) {
        this.produtors = produtors;
        return this;
    }

    public Grupo addProdutor(Produtor produtor) {
        this.produtors.add(produtor);
        produtor.getGrupos().add(this);
        return this;
    }

    public Grupo removeProdutor(Produtor produtor) {
        this.produtors.remove(produtor);
        produtor.getGrupos().remove(this);
        return this;
    }

    public void setProdutors(Set<Produtor> produtors) {
        this.produtors = produtors;
    }

    public Set<Noticia> getNoticias() {
        return noticias;
    }

    public Grupo noticias(Set<Noticia> noticias) {
        this.noticias = noticias;
        return this;
    }

    public Grupo addNoticia(Noticia noticia) {
        this.noticias.add(noticia);
        noticia.getGrupos().add(this);
        return this;
    }

    public Grupo removeNoticia(Noticia noticia) {
        this.noticias.remove(noticia);
        noticia.getGrupos().remove(this);
        return this;
    }

    public void setNoticias(Set<Noticia> noticias) {
        this.noticias = noticias;
    }

    public Set<PrevisaoTempo> getPrevisaoTempos() {
        return previsaoTempos;
    }

    public Grupo previsaoTempos(Set<PrevisaoTempo> previsaoTempos) {
        this.previsaoTempos = previsaoTempos;
        return this;
    }

    public Grupo addPrevisaoTempo(PrevisaoTempo previsaoTempo) {
        this.previsaoTempos.add(previsaoTempo);
        previsaoTempo.getGrupos().add(this);
        return this;
    }

    public Grupo removePrevisaoTempo(PrevisaoTempo previsaoTempo) {
        this.previsaoTempos.remove(previsaoTempo);
        previsaoTempo.getGrupos().remove(this);
        return this;
    }

    public void setPrevisaoTempos(Set<PrevisaoTempo> previsaoTempos) {
        this.previsaoTempos = previsaoTempos;
    }

    public Set<Cotacao> getCotacaos() {
        return cotacaos;
    }

    public Grupo cotacaos(Set<Cotacao> cotacaos) {
        this.cotacaos = cotacaos;
        return this;
    }

    public Grupo addCotacao(Cotacao cotacao) {
        this.cotacaos.add(cotacao);
        cotacao.getGrupos().add(this);
        return this;
    }

    public Grupo removeCotacao(Cotacao cotacao) {
        this.cotacaos.remove(cotacao);
        cotacao.getGrupos().remove(this);
        return this;
    }

    public void setCotacaos(Set<Cotacao> cotacaos) {
        this.cotacaos = cotacaos;
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
        Grupo grupo = (Grupo) o;
        if (grupo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grupo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Grupo{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
