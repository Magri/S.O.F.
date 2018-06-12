package com.sof.portalapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LoginApp.
 */
@Entity
@Table(name = "login_app")
public class LoginApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "usuario", nullable = false)
    private String usuario;

    @NotNull
    @Column(name = "senha", nullable = false)
    private String senha;

    @OneToOne
    @JoinColumn(unique = true)
    private Produtor produtor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public LoginApp usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public LoginApp senha(String senha) {
        this.senha = senha;
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Produtor getProdutor() {
        return produtor;
    }

    public LoginApp produtor(Produtor produtor) {
        this.produtor = produtor;
        return this;
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
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
        LoginApp loginApp = (LoginApp) o;
        if (loginApp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loginApp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LoginApp{" +
            "id=" + getId() +
            ", usuario='" + getUsuario() + "'" +
            ", senha='" + getSenha() + "'" +
            "}";
    }
}
