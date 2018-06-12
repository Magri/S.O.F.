package com.sof.portalapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Produtor.
 */
@Entity
@Table(name = "produtor")
public class Produtor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private Instant dataNascimento;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @NotNull
    @Column(name = "cadpro", nullable = false)
    private String cadpro;

    @ManyToMany(mappedBy = "produtors")
    @JsonIgnore
    private Set<Grupo> grupos = new HashSet<>();

    @ManyToMany(mappedBy = "produtors")
    @JsonIgnore
    private Set<Noticia> noticias = new HashSet<>();

    @ManyToMany(mappedBy = "produtors")
    @JsonIgnore
    private Set<PrevisaoTempo> previsaoTempos = new HashSet<>();

    @ManyToMany(mappedBy = "produtors")
    @JsonIgnore
    private Set<Cotacao> cotacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Produtor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public Produtor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getDataNascimento() {
        return dataNascimento;
    }

    public Produtor dataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public Produtor cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCadpro() {
        return cadpro;
    }

    public Produtor cadpro(String cadpro) {
        this.cadpro = cadpro;
        return this;
    }

    public void setCadpro(String cadpro) {
        this.cadpro = cadpro;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public Produtor grupos(Set<Grupo> grupos) {
        this.grupos = grupos;
        return this;
    }

    public Produtor addGrupo(Grupo grupo) {
        this.grupos.add(grupo);
        grupo.getProdutors().add(this);
        return this;
    }

    public Produtor removeGrupo(Grupo grupo) {
        this.grupos.remove(grupo);
        grupo.getProdutors().remove(this);
        return this;
    }

    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Set<Noticia> getNoticias() {
        return noticias;
    }

    public Produtor noticias(Set<Noticia> noticias) {
        this.noticias = noticias;
        return this;
    }

    public Produtor addNoticia(Noticia noticia) {
        this.noticias.add(noticia);
        noticia.getProdutors().add(this);
        return this;
    }

    public Produtor removeNoticia(Noticia noticia) {
        this.noticias.remove(noticia);
        noticia.getProdutors().remove(this);
        return this;
    }

    public void setNoticias(Set<Noticia> noticias) {
        this.noticias = noticias;
    }

    public Set<PrevisaoTempo> getPrevisaoTempos() {
        return previsaoTempos;
    }

    public Produtor previsaoTempos(Set<PrevisaoTempo> previsaoTempos) {
        this.previsaoTempos = previsaoTempos;
        return this;
    }

    public Produtor addPrevisaoTempo(PrevisaoTempo previsaoTempo) {
        this.previsaoTempos.add(previsaoTempo);
        previsaoTempo.getProdutors().add(this);
        return this;
    }

    public Produtor removePrevisaoTempo(PrevisaoTempo previsaoTempo) {
        this.previsaoTempos.remove(previsaoTempo);
        previsaoTempo.getProdutors().remove(this);
        return this;
    }

    public void setPrevisaoTempos(Set<PrevisaoTempo> previsaoTempos) {
        this.previsaoTempos = previsaoTempos;
    }

    public Set<Cotacao> getCotacaos() {
        return cotacaos;
    }

    public Produtor cotacaos(Set<Cotacao> cotacaos) {
        this.cotacaos = cotacaos;
        return this;
    }

    public Produtor addCotacao(Cotacao cotacao) {
        this.cotacaos.add(cotacao);
        cotacao.getProdutors().add(this);
        return this;
    }

    public Produtor removeCotacao(Cotacao cotacao) {
        this.cotacaos.remove(cotacao);
        cotacao.getProdutors().remove(this);
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
        Produtor produtor = (Produtor) o;
        if (produtor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produtor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Produtor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", cadpro='" + getCadpro() + "'" +
            "}";
    }
}
