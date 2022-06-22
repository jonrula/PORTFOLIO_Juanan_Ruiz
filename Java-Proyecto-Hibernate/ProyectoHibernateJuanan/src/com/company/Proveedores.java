package com.company;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Proveedores {
    private int idProveedor;
    private String proveedor;
    private String responsableVentas;
    private String dirCp;

    @Id
    @Column(name = "id_proveedor", nullable = false)
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    @Basic
    @Column(name = "proveedor", nullable = true, length = 40)
    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    @Basic
    @Column(name = "responsableVentas", nullable = true, length = 40)
    public String getResponsableVentas() {
        return responsableVentas;
    }

    public void setResponsableVentas(String responsableVentas) {
        this.responsableVentas = responsableVentas;
    }

    @Basic
    @Column(name = "dirCP", nullable = true, length = 40)
    public String getDirCp() {
        return dirCp;
    }

    public void setDirCp(String dirCp) {
        this.dirCp = dirCp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proveedores that = (Proveedores) o;
        return proveedor.equalsIgnoreCase(that.proveedor) && responsableVentas.equalsIgnoreCase(that.responsableVentas) && dirCp.equalsIgnoreCase(that.dirCp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proveedor, responsableVentas, dirCp);
    }

/*    @Override
    public String toString() {
        return String.valueOf(idProveedor);
    }*/

    @Override
    public String toString() {
        return proveedor;
    }
}
