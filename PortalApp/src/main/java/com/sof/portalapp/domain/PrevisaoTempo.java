package com.sof.portalapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PrevisaoTempo.
 */
@Entity
@Table(name = "previsao_tempo")
public class PrevisaoTempo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_link", nullable = false)
    private String link;

    @NotNull
    @Column(name = "data_previsao", nullable = false)
    private Instant dataPrevisao;

    @ManyToMany
    @JoinTable(name = "previsao_tempo_produtor",
               joinColumns = @JoinColumn(name="previsao_tempos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="produtors_id", referencedColumnName="id"))
    private Set<Produtor> produtors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "previsao_tempo_grupo",
               joinColumns = @JoinColumn(name="previsao_tempos_id", referencedColumnName="id"),
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

    public PrevisaoTempo link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Instant getDataPrevisao() {
        return dataPrevisao;
    }

    public PrevisaoTempo dataPrevisao(Instant dataPrevisao) {
        this.dataPrevisao = dataPrevisao;
        return this;
    }

    public void setDataPrevisao(Instant dataPrevisao) {
        this.dataPrevisao = dataPrevisao;
    }

    public Set<Produtor> getProdutors() {
        return produtors;
    }

    public PrevisaoTempo produtors(Set<Produtor> produtors) {
        this.produtors = produtors;
        return this;
    }

    public PrevisaoTempo addProdutor(Produtor produtor) {
        this.produtors.add(produtor);
        produtor.getPrevisaoTempos().add(this);
        return this;
    }

    public PrevisaoTempo removeProdutor(Produtor produtor) {
        this.produtors.remove(produtor);
        produtor.getPrevisaoTempos().remove(this);
        return this;
    }

    public void setProdutors(Set<Produtor> produtors) {
        this.produtors = produtors;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public PrevisaoTempo grupos(Set<Grupo> grupos) {
        this.grupos = grupos;
        return this;
    }

    public PrevisaoTempo addGrupo(Grupo grupo) {
        this.grupos.add(grupo);
        grupo.getPrevisaoTempos().add(this);
        return this;
    }

    public PrevisaoTempo removeGrupo(Grupo grupo) {
        this.grupos.remove(grupo);
        grupo.getPrevisaoTempos().remove(this);
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
        PrevisaoTempo previsaoTempo = (PrevisaoTempo) o;
        if (previsaoTempo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), previsaoTempo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrevisaoTempo{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            ", dataPrevisao='" + getDataPrevisao() + "'" +
            "}";
    }
}
