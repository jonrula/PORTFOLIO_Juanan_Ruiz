package com.company;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Piezas {
    private int idPieza;
    private String pieza;
    private double precio;
    private String descripcion;

    @Id
    @Column(name = "id_pieza", nullable = false)
    public int getIdPieza() {
        return idPieza;
    }

    public void setIdPieza(int idPieza) {
        this.idPieza = idPieza;
    }

    @Basic
    @Column(name = "pieza", nullable = true, length = 40)
    public String getPieza() {
        return pieza;
    }

    public void setPieza(String pieza) {
        this.pieza = pieza;
    }

    @Basic
    @Column(name = "precio", nullable = true, precision = 2)
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Basic
    @Column(name = "descripcion", nullable = true, length = -1)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piezas piezas = (Piezas) o;
        return Objects.equals(idPieza, piezas.idPieza) && Objects.equals(pieza, piezas.pieza) && Objects.equals(precio, piezas.precio) && Objects.equals(descripcion, piezas.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPieza, pieza, precio, descripcion);
    }
}
