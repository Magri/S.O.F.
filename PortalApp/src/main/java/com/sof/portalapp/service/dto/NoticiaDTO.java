package com.sof.portalapp.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Noticia entity.
 */
public class NoticiaDTO implements Serializable {

    private Long id;

    @NotNull
    private String link;

    @NotNull
    private String texto;

    @NotNull
    private Instant dataPublicacao;

    private Set<ProdutorDTO> produtors = new HashSet<>();

    private Set<GrupoDTO> grupos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Instant getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Instant dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
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

        NoticiaDTO noticiaDTO = (NoticiaDTO) o;
        if(noticiaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), noticiaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NoticiaDTO{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            ", texto='" + getTexto() + "'" +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            "}";
    }
}
