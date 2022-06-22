package Models;

import java.util.Objects;

public class Usuario {

// Atributos

    private int IdPass;
    private String usuario;
    private String password;


    // Constructor

    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    public Usuario() {
    }


    // Getters y Setters

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdPass() {
        return IdPass;
    }

    public void setIdPass(int idPass) {
        IdPass = idPass;
    }
// To String


    @Override
    public String toString() {
        return usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario1 = (Usuario) o;
        return getUsuario().equalsIgnoreCase(usuario1.getUsuario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsuario());
    }
}
