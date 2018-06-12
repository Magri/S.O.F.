package com.sof.portalapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Noticia.
 */
@Entity
@Table(name = "noticia")
public class Noticia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_link", nullable = false)
    private String link;

    @NotNull
    @Column(name = "texto", nullable = false)
    private String texto;

    @NotNull
    @Column(name = "data_publicacao", nullable = false)
    private Instant dataPublicacao;

    @ManyToMany
    @JoinTable(name = "noticia_produtor",
               joinColumns = @JoinColumn(name="noticias_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="produtors_id", referencedColumnName="id"))
    private Set<Produtor> produtors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "noticia_grupo",
               joinColumns = @JoinColumn(name="noticias_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="grupos_id", referencedColumnName="id"))
    private Set<Grupo> grupos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public Noticia link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTexto() {
        return texto;
    }

    public Noticia texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Instant getDataPublicacao() {
        return dataPublicacao;
    }

    public Noticia dataPublicacao(Instant dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    public void setDataPublicacao(Instant dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Set<Produtor> getProdutors() {
        return produtors;
    }

    public Noticia produtors(Set<Produtor> produtors) {
        this.produtors = produtors;
        return this;
    }

    public Noticia addProdutor(Produtor produtor) {
        this.produtors.add(produtor);
        produtor.getNoticias().add(this);
        return this;
    }

    public Noticia removeProdutor(Produtor produtor) {
        this.produtors.remove(produtor);
        produtor.getNoticias().remove(this);
        return this;
    }

    public void setProdutors(Set<Produtor> produtors) {
        this.produtors = produtors;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public Noticia grupos(Set<Grupo> grupos) {
        this.grupos = grupos;
        return this;
    }

    public Noticia addGrupo(Grupo grupo) {
        this.grupos.add(grupo);
        grupo.getNoticias().add(this);
        return this;
    }

    public Noticia removeGrupo(Grupo grupo) {
        this.grupos.remove(grupo);
        grupo.getNoticias().remove(this);
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
        Noticia noticia = (Noticia) o;
        if (noticia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), noticia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Noticia{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            ", texto='" + getTexto() + "'" +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            "}";
    }
}
