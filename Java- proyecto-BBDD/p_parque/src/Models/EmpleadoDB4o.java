package Models;

import java.util.ArrayList;
import java.util.Objects;

public class EmpleadoDB4o {
    private String dni;
    private String nombre;
    private String primerApellido;
    private String fechaNac;
    private String fechaContrato;
    //para hacer un patrón de fecha podemos usar esto:
    private java.util.Date fechaNacDb4o;
    private java.util.Date fechaContratoDb4o;
    private String nacionalidad;
    private String cargo;


    private ArrayList<EspectaculoDB4o> espectaculos = new ArrayList<>();


    //aquí cogeríamos las fechas db4o, no las otras
    public EmpleadoDB4o(String dni, String nombre, String primerApellido, java.util.Date fechaNacDb4o, java.util.Date fechaContratoDb4o, String nacionalidad, String cargo) {
        this.dni = dni;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.fechaNacDb4o = fechaNacDb4o;
        this.fechaContratoDb4o = fechaContratoDb4o;
        this.nacionalidad = nacionalidad;
        this.cargo = cargo;
    }

    //constructor vacío
    public EmpleadoDB4o() {
    }


    //aquí cogemos las fechas normales
    /*public Empleado(String dni, String nombre, String primerApellido, Date fechaNac, Date fechaContrato, String nacionalidad, String cargo) {
        this.dni = dni;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.fechaNac = fechaNac;
        this.fechaContrato = fechaContrato;
        this.nacionalidad = nacionalidad;
        this.cargo = cargo;
    }*/

    public EmpleadoDB4o(String dni, String nombre, String primerApellido, String fechaNac, String fechaContrato, String nacionalidad, String cargo) {
        this.dni = dni;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.fechaNac = fechaNac;
        this.fechaContrato = fechaContrato;
        this.nacionalidad = nacionalidad;
        this.cargo = cargo;
    }


    public EmpleadoDB4o(String dni, String nombre, String primerApellido, String fechaNac, String fechaContrato, String nacionalidad, String cargo, ArrayList<EspectaculoDB4o> espectaculos) {
        this.dni = dni;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.fechaNac = fechaNac;
        this.fechaContrato = fechaContrato;
        this.nacionalidad = nacionalidad;
        this.cargo = cargo;
        this.espectaculos = espectaculos;
    }

    public EmpleadoDB4o(String dni) {
        this.dni = dni;
    }


    //al igual que en cliente, hacemos una función para editar
    public void editarEmpleDb4o(EmpleadoDB4o empleado) {
        this.nombre = empleado.getNombre();
        this.primerApellido = empleado.getPrimerApellido();
        this.fechaNac = empleado.getFechaNac();
        this.fechaContrato = empleado.getFechaContrato();
        this.nacionalidad = empleado.getNacionalidad();
        this.cargo = empleado.getCargo();
    }

    public ArrayList<EspectaculoDB4o> getEspectaculos() {
        return espectaculos;
    }

    public void setEspectaculos(ArrayList<EspectaculoDB4o> espectaculos) {
        this.espectaculos = espectaculos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(String fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

   /* public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }*/

    public java.util.Date getFechaNacDb4o() {
        return fechaNacDb4o;
    }

    public void setFechaNacDb4o(java.util.Date fechaNacDb4o) {
        this.fechaNacDb4o = fechaNacDb4o;
    }

    public java.util.Date getFechaContratoDb4o() {
        return fechaContratoDb4o;
    }

    public void setFechaContratoDb4o(java.util.Date fechaContratoDb4o) {
        this.fechaContratoDb4o = fechaContratoDb4o;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    //equals & hashcode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpleadoDB4o empleado = (EmpleadoDB4o) o;
        return dni.equals(empleado.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }


    //toString()
    @Override
    public String toString() {
        String cadena = nombre + " " + primerApellido;
        return String.format("%10s, %s", cadena, dni);
    }
}
