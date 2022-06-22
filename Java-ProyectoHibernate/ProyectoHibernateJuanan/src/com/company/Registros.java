package com.company;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Registros {
    private int idRegistro;
    private String usuario;
    private Timestamp fechaHora;
    private String sentencia;

    @Id
    @Column(name = "id_Registro", nullable = false)
    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    @Basic
    @Column(name = "usuario", nullable = false, length = 40)
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Basic
    @Column(name = "fechaHora", nullable = true)
    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Basic
    @Column(name = "sentencia", nullable = false, length = 300)
    public String getSentencia() {
        return sentencia;
    }

    public void setSentencia(String sentencia) {
        this.sentencia = sentencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registros registros = (Registros) o;
        return Objects.equals(idRegistro, registros.idRegistro) && Objects.equals(usuario, registros.usuario) && Objects.equals(fechaHora, registros.fechaHora) && Objects.equals(sentencia, registros.sentencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRegistro, usuario, fechaHora, sentencia);
    }
}
