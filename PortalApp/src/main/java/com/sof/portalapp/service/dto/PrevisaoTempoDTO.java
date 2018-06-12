package com.sof.portalapp.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PrevisaoTempo entity.
 */
public class PrevisaoTempoDTO implements Serializable {

    private Long id;

    @NotNull
    private String link;

    @NotNull
    private Instant dataPrevisao;

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

    public Instant getDataPrevisao() {
        return dataPrevisao;
    }

    public void setDataPrevisao(Instant dataPrevisao) {
        this.dataPrevisao = dataPrevisao;
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

        PrevisaoTempoDTO previsaoTempoDTO = (PrevisaoTempoDTO) o;
        if(previsaoTempoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), previsaoTempoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrevisaoTempoDTO{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            ", dataPrevisao='" + getDataPrevisao() + "'" +
            "}";
    }
}
