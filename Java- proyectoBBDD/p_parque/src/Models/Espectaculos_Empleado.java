package Models;

import java.util.ArrayList;
import java.util.Objects;

public class Espectaculos_Empleado {

    // Atributos

    private int idEspecEmp;
    private String empleado;
    private int espectaculo;

    // Constructor

    public Espectaculos_Empleado(int idEspecEmp, String empleado, int espectaculo) {
        this.idEspecEmp = idEspecEmp;
        this.empleado = empleado;
        this.espectaculo = espectaculo;
    }

    public Espectaculos_Empleado(String empleado, int espectaculo) {
        this.empleado = empleado;
        this.espectaculo = espectaculo;
    }

    public Espectaculos_Empleado() {
    }

    // Getters y Setters


    public int getIdEspecEmp() {
        return idEspecEmp;
    }

    public void setIdEspecEmp(int idEspecEmp) {
        this.idEspecEmp = idEspecEmp;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public int getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(int espectaculo) {
        this.espectaculo = espectaculo;
    }


    // To String()
    @Override
    public String toString() {
        return idEspecEmp+" "+ empleado+ " "+espectaculo;
    }

    public void mostrarEspectaculosEmpleados(ArrayList<Espectaculos_Empleado> espectaculosEmpleados) {

        System.out.println("\nDatos del arrayList 'EspectaculosEmpleados':\n");
        System.out.format("%-5s%-20s%-20s\n", "ID","ID_EMPLEADO", "ID_ESPECTACULO");

        for (Espectaculos_Empleado ep : espectaculosEmpleados) {
            System.out.format("%-5d%-20s%-5d\n", ep.getIdEspecEmp(), ep.getEmpleado(), ep.getEspectaculo());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Espectaculos_Empleado)) return false;
        Espectaculos_Empleado that = (Espectaculos_Empleado) o;
        return getEspectaculo() == that.getEspectaculo() && getEmpleado().equals(that.getEmpleado());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmpleado(), getEspectaculo());
    }
}
