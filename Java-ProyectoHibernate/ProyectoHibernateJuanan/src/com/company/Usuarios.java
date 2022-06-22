package com.company;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Usuarios {
    private int idUserPass;
    private String usuario;
    private String contrasena;

    @Id
    @Column(name = "id_UserPass", nullable = false)
    public int getIdUserPass() {
        return idUserPass;
    }

    public void setIdUserPass(int idUserPass) {
        this.idUserPass = idUserPass;
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
    @Column(name = "contrasena", nullable = false, length = 70)
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuarios usuarios = (Usuarios) o;
        return Objects.equals(idUserPass, usuarios.idUserPass) && Objects.equals(usuario, usuarios.usuario) && Objects.equals(contrasena, usuarios.contrasena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserPass, usuario, contrasena);
    }
}
