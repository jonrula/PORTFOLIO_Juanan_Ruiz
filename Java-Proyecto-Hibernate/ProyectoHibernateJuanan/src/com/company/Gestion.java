package com.company;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
public class Gestion {
    private int idGestion;
    private double cantidadPiezas;
    private Piezas piezaByIdPieza;
    private Proveedores proveedorByIdProveedor;
    private Proyectos proyectoByIdProyecto;

    @Id
    @Column(name = "ID_gestion", nullable = false)
    public int getIdGestion() {
        return idGestion;
    }


    public void setIdGestion(int idGestion) {
        this.idGestion = idGestion;
    }

    @Basic
    @Column(name = "cantidad", nullable = true, precision = 2)
    public double getCantidad() {
        return cantidadPiezas;
    }

    public void setCantidad(double cantidad) {
        this.cantidadPiezas = cantidad;
    }

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "ID_gestion_pieza", referencedColumnName = "id_pieza")
    public Piezas getPiezaByIdPieza() {
        return piezaByIdPieza;
    }
    public void setPiezaByIdPieza (Piezas piezaByIdPieza) {
        this.piezaByIdPieza = piezaByIdPieza;
    }

    @ManyToOne
    @JoinColumn(name = "ID_gestion_proveedor", referencedColumnName = "id_proveedor")
    public Proveedores getProveedorByIdProveedor() {
        return  proveedorByIdProveedor;
    }
    public void setProveedorByIdProveedor (Proveedores proveedorByIdProveedor) {
        this.proveedorByIdProveedor = proveedorByIdProveedor;
    }


    @ManyToOne
    @JoinColumn(name = "ID_gestion_proyecto", referencedColumnName = "id_proyecto")
    public Proyectos getProyectoByIdProyecto() {
        return  proyectoByIdProyecto;
    }
    public void setProyectoByIdProyecto (Proyectos proyectoByIdProyecto) {
        this.proyectoByIdProyecto = proyectoByIdProyecto;
    }





    /*    @Basic
    @Column(name = "ID_gestion_pieza", nullable = false)
    public Piezas getIdGestionPieza() {
        return idGestionPieza;
    }

    public void setIdGestionPieza(Piezas idGestionPieza) {
        this.idGestionPieza = idGestionPieza;
    }

    @Basic
    @Column(name = "ID_gestion_proveedor", nullable = false)
    public Proveedores getIdGestionProveedor() {
        return idGestionProveedor;
    }

    public void setIdGestionProveedor(Proveedores idGestionProveedor) {
        this.idGestionProveedor = idGestionProveedor;
    }

    @Basic
    @Column(name = "ID_gestion_proyecto", nullable = false)
    public Proyectos getIdGestionProyecto() {
        return idGestionProyecto;
    }

    public void setIdGestionProyecto(Proyectos idGestionProyecto) {
        this.idGestionProyecto = idGestionProyecto;
    }*/



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gestion gestion = (Gestion) o;
        return Objects.equals(idGestion, gestion.idGestion) && Objects.equals(cantidadPiezas, gestion.cantidadPiezas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGestion, cantidadPiezas);
    }


}
