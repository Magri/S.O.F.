package com.sof.portalapp.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Produtor entity.
 */
public class ProdutorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String email;

    @NotNull
    private Instant dataNascimento;

    @NotNull
    private String cpf;

    @NotNull
    private String cadpro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCadpro() {
        return cadpro;
    }

    public void setCadpro(String cadpro) {
        this.cadpro = cadpro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProdutorDTO produtorDTO = (ProdutorDTO) o;
        if(produtorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produtorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProdutorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", cadpro='" + getCadpro() + "'" +
            "}";
    }
}
