package com.company;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Proyectos {
    private int idProyecto;
    private String proyecto;
    private String ciudad;

    @Id
    @Column(name = "id_proyecto", nullable = false)
    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    @Basic
    @Column(name = "proyecto", nullable = true, length = 40)
    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    @Basic
    @Column(name = "ciudad", nullable = true, length = 40)
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proyectos proyectos = (Proyectos) o;
        return Objects.equals(idProyecto, proyectos.idProyecto) && Objects.equals(proyecto, proyectos.proyecto) && Objects.equals(ciudad, proyectos.ciudad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProyecto, proyecto, ciudad);
    }
}
