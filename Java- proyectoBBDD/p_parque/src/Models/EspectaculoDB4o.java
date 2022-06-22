package Models;

import java.util.ArrayList;
import java.util.Objects;

public class EspectaculoDB4o {
    private int id;
    private String nombre;
    private int aforo;
    private String descripcion;

    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<EmpleadoDB4o> empleados = new ArrayList<>();

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<EmpleadoDB4o> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<EmpleadoDB4o> empleados) {
        this.empleados = empleados;
    }


    public EspectaculoDB4o(String nombre, String lugar, String fecha, String horario) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.horario = horario;
    }



    private String lugar;
    private String fecha;
    private String horario;
    private double coste; //o float
    private EmpleadoDB4o organizador; //por ejemplo, se puede cambiar depende del planteamiento

    private String responsable;

    public EspectaculoDB4o(int id) {
        this.id = id;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }


    public EspectaculoDB4o(int id, EmpleadoDB4o empleado, String nombre, int aforo, String descripcion, String lugar, double coste) {
        this.id = id;
        this.nombre = nombre;
        this.aforo = aforo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.coste = coste;
        this.organizador = empleado;
    }

    //constructor vacío
    public EspectaculoDB4o() {
    }


    public EspectaculoDB4o(EmpleadoDB4o emp) {
        this.organizador = emp;
    }

    public EspectaculoDB4o(int id, String nombre, int aforo, String descripcion, String lugar, String fecha, String horario, double precio, EmpleadoDB4o organizador) {

        this.id = id;
        this.nombre = nombre;
        this.aforo = aforo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.horario = horario;
        this.coste = precio;
        this.organizador = organizador;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public EmpleadoDB4o getOrganizador() {
        return organizador;
    }

    public void setOrganizador(EmpleadoDB4o organizador) {
        this.organizador = organizador;
    }


    //el id es pk, no se cambia
    public void editarEspectaculoDb4o(EspectaculoDB4o espectaculo) {
        this.id = espectaculo.getId();
        this.nombre = espectaculo.getNombre();
        this.aforo = espectaculo.getAforo();
        this.descripcion = espectaculo.getDescripcion();
        this.lugar = espectaculo.getLugar();
        this.fecha = espectaculo.getFecha();
        this.horario = espectaculo.getHorario();
        this.coste = espectaculo.getCoste();
        this.organizador = espectaculo.getOrganizador();
    }

    //equals


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EspectaculoDB4o espectaculo = (EspectaculoDB4o) o;
        return id == espectaculo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //toString()


    @Override
    public String toString() {
        String cadena = nombre + " (" + fecha + ")";
        return String.format("%d- %20s", id, cadena);
    }
}
