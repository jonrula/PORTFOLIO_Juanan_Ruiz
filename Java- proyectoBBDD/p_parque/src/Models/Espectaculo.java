package Models;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;


public class Espectaculo {

    // Atributos
    private int no_Espect;
    private String nombreEspec;
    private int aforo;
    private String descripcion;
    private String lugar;
    private Date fecha_Espec; //en sql es Date
    private Time horario_espec; //en sql es Time
    private double precio;
    private String responsable;


    // Relaciones con las otras clases 1 espectaculo tiene muchos clientes y tiene muchos empleados
    // IMPORTANTE al declarar el ArrayList, poner al final '=new ArrayList<>()' porque sino nos genera un 'NullPointerexception'

    private ArrayList<Cliente> clientes =new ArrayList<>();
    private ArrayList<Empleado> empleados = new ArrayList<>();

    // Constructor

    public Espectaculo(int no_Espect, String nombreEspec, int aforo, String descripcion, String lugar, Date fecha_Espec, Time horario_espec, double precio, String responsable) {
        this.no_Espect = no_Espect;
        this.nombreEspec = nombreEspec;
        this.aforo = aforo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha_Espec = fecha_Espec;
        this.horario_espec = horario_espec;
        this.precio=precio;
        this.responsable = responsable;
    }

    public Espectaculo(String nombreEspec, String lugar, Date fecha_Espec, Time horario_espec) {
        this.nombreEspec = nombreEspec;
        this.lugar = lugar;
        this.fecha_Espec = fecha_Espec;
        this.horario_espec = horario_espec;
    }

    // Constructor vacío
    public Espectaculo() {
    }

    public Espectaculo(String nombreEspec, int aforo, String descripcion, String lugar, Date fecha_Espec, Time horario_espec, double precio,String responsable) {
        this.nombreEspec = nombreEspec;
        this.aforo = aforo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha_Espec = fecha_Espec;
        this.horario_espec = horario_espec;
        this.precio = precio;
        this.responsable=responsable;
    }

    public Espectaculo(String nombreEspec) {
        this.nombreEspec = nombreEspec;
    }

    // Getters y Setters

    public int getNo_Espect() {
        return no_Espect;
    }

    public void setNo_Espect(int no_Espect) {
        this.no_Espect = no_Espect;
    }

    public String getNombreEspec() {
        return nombreEspec;
    }

    public void setNombreEspec(String nombreEspec) {
        this.nombreEspec = nombreEspec;
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

    public Date getFecha_Espec() {
        return fecha_Espec;
    }

    public void setFecha_Espec(Date fecha_Espec) {
        this.fecha_Espec = fecha_Espec;
    }

    public Time getHorario_espec() {
        return horario_espec;
    }

    public void setHorario_espec(Time horario_espec) {
        this.horario_espec = horario_espec;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }



    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }


    //toString() -> ya se pondrá bonito... solo pongo el nombre de momento para no ocupar tanto espacio
    @Override
    public String toString() {
        return nombreEspec;
    }

    public void mostrarEspectaculos(ArrayList<Espectaculo>espectaculos){
        System.out.println("\nDatos del arrayList 'espectáculos':\n");
        System.out.format("%-5s%-20s%-10s%-50s%-20s%-12s%-12s%-20s\n","ID","ESPECTACULO", "AFORO","ESPECTACULO","LUGAR","FECHA", "HORA", "RESPONSABLE");

        for (Espectaculo ep : espectaculos) {
            System.out.format("%-5d%-20s%-10d%-50s%-20s%-12s%-12s%-20s%n", ep.getNo_Espect(), ep.getNombreEspec(), ep.getAforo(), ep.getDescripcion(), ep.getLugar(), ep.getFecha_Espec(), ep.getHorario_espec(),ep.getResponsable());
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Espectaculo)) return false;
        Espectaculo that = (Espectaculo) o;
        return getNombreEspec().equalsIgnoreCase(that.getNombreEspec()) && getLugar().equalsIgnoreCase(that.getLugar()) && getFecha_Espec().equals(that.getFecha_Espec()) && getHorario_espec().equals(that.getHorario_espec());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombreEspec(), getLugar(), getFecha_Espec(), getHorario_espec());
    }

}
