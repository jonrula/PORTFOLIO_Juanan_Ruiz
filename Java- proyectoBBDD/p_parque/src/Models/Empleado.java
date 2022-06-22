package Models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

public class Empleado {

    // Atributos
    private String dniEmple;
    private String nombreEmple;
    private String ApeEmple;
    private Date fechaNac;
    private Date fechaContr;
    private String Nacionalidad;
    private String cargo;

    // Relacion con espectaculo --> 1 empleado tiene muchos espectáculos
    // IMPORTANTE al declarar el ArrayList, poner al final '=new ArrayList<>()' porque sino nos genera un 'NullPointerexception'

    private ArrayList<Espectaculo> espectaculos= new ArrayList<>();


    //Constructores

    public Empleado(String dniEmple, String nombreEmple, String apeEmple, Date fechaNac, Date fechaContr, String nacionalidad, String cargo) {
        this.dniEmple = dniEmple;
        this.nombreEmple = nombreEmple;
        this.ApeEmple = apeEmple;
        this.fechaNac = fechaNac;
        this.fechaContr = fechaContr;
        this.Nacionalidad = nacionalidad;
        this.cargo = cargo;
    }

    public Empleado(String dniEmple) {
        this.dniEmple = dniEmple;
    }

    // Constructor vacío

    public Empleado() {
    }


    // Getters y Setters

    public String getDniEmple() {
        return dniEmple;
    }

    public void setDniEmple(String dniEmple) {
        this.dniEmple = dniEmple;
    }

    public String getNombreEmple() {
        return nombreEmple;
    }

    public void setNombreEmple(String nombreEmple) {
        this.nombreEmple = nombreEmple;
    }

    public String getApeEmple() {
        return ApeEmple;
    }

    public void setApeEmple(String apeEmple) {
        ApeEmple = apeEmple;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Date getFechaContr() {
        return fechaContr;
    }

    public void setFechaContr(Date fechaContr) {
        this.fechaContr = fechaContr;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        Nacionalidad = nacionalidad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public ArrayList<Espectaculo> getEspectaculos() {
        return espectaculos;
    }

    public void setEspectaculos(ArrayList<Espectaculo> espectaculos) {
        this.espectaculos = espectaculos;
    }



    // To String()
    @Override
    public String toString() {
        return nombreEmple + " " + ApeEmple;
    }


    public void mostrarEmpleados(ArrayList<Empleado> empleados) {

        System.out.println("\nDatos del arrayList 'empleados':\n");
        System.out.format("%-15s%-20s%-15s%-15s%-15s%-20s%-20s\n", "ID", "NOMBRE", "APELLIDOS", "NACOMIENTO", "CONTRATO", "NACIONALIDAD", "CARGO");

        for (Empleado em : empleados) {
            System.out.format("%-15s%-20s%-15s%-15s%-15s%-20s%-20s\n", em.getDniEmple(), em.getNombreEmple(), em.getApeEmple(), em.getFechaNac(), em.getFechaContr(), em.getNacionalidad(), em.getCargo());
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleado empleado)) return false;
        return getDniEmple().equals(empleado.getDniEmple());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDniEmple());
    }

}
