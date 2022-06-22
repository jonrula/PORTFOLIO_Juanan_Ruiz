package Models;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;

public class EspectaculoSQLite {

    //Atributos
    private int no_Espect;
    private String nombreEspec;
    private int aforo;
    private String descripcion;
    private String lugar;
    private String fecha_Espec; //en sqlite es Text
    private String horario_espec; //en sqlite es Text
    private double precio;
    private String responsable;


    private ArrayList<Cliente> clientes =new ArrayList<>();
    private ArrayList<EmpleadoSQLite> empleadosSQLite = new ArrayList<>();


    public EspectaculoSQLite() {
    }

    public EspectaculoSQLite(int no_Espect, String nombreEspec, int aforo, String descripcion, String lugar, String fecha_Espec, String horario_espec, double precio, String responsable) {
        this.no_Espect = no_Espect;
        this.nombreEspec = nombreEspec;
        this.aforo = aforo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha_Espec = fecha_Espec;
        this.horario_espec = horario_espec;
        this.precio = precio;
        this.responsable = responsable;
    }

    public EspectaculoSQLite(String nombreEspec) {
        this.nombreEspec = nombreEspec;
    }

    public EspectaculoSQLite(String nombreEspec, int aforo, String descripcion, String lugar, String fecha_Espec, String horario_espec, double precio, String responsable) {
        this.nombreEspec = nombreEspec;
        this.aforo = aforo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha_Espec = fecha_Espec;
        this.horario_espec = horario_espec;
        this.precio = precio;
        this.responsable = responsable;

    }
    public EspectaculoSQLite(String nombreEspec, String lugar, String fecha_Espec, String horario_espec){
        this.nombreEspec = nombreEspec;
        this.lugar = lugar;
        this.fecha_Espec = fecha_Espec;
        this.horario_espec = horario_espec;
    }

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

    public String getFecha_Espec() {
        return fecha_Espec;
    }

    public void setFecha_Espec(String fecha_Espec) {
        this.fecha_Espec = fecha_Espec;
    }

    public String getHorario_espec() {
        return horario_espec;
    }

    public void setHorario_espec(String horario_espec) {
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

    public ArrayList<EmpleadoSQLite> getEmpleadosSQLite() {
        return empleadosSQLite;
    }

    public void setEmpleadosSQLite(ArrayList<EmpleadoSQLite> empleadosSQLite) {
        this.empleadosSQLite = empleadosSQLite;
    }

    @Override
    public String toString() {
        return nombreEspec;
    }

    public void mostrarEspectaculosSQLite(ArrayList<EspectaculoSQLite> espectaculosSQLite){
        System.out.println("\nDatos del arrayList EspectaculosSQLite:\n");
        System.out.format("%-5s%-20s%-10s%-50s%-20s%-12s%-12s%-20s\n","ID","ESPECTACULO", "AFORO","ESPECTACULO","LUGAR","FECHA", "HORA", "RESPONSABLE");

        for (EspectaculoSQLite ep: espectaculosSQLite){
            System.out.format("%-5d%-20s%-10d%-50s%-20s%-12s%-12s%-20s%n", ep.getNo_Espect(), ep.getNombreEspec(), ep.getAforo(), ep.getDescripcion(), ep.getLugar(), ep.getFecha_Espec(), ep.getHorario_espec(),ep.getResponsable());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EspectaculoSQLite that = (EspectaculoSQLite) o;
        return no_Espect == that.no_Espect && aforo == that.aforo && Double.compare(that.precio, precio) == 0 && Objects.equals(nombreEspec, that.nombreEspec) && Objects.equals(descripcion, that.descripcion) && Objects.equals(lugar, that.lugar) && Objects.equals(fecha_Espec, that.fecha_Espec) && Objects.equals(horario_espec, that.horario_espec) && Objects.equals(responsable, that.responsable) && Objects.equals(clientes, that.clientes) && Objects.equals(empleadosSQLite, that.empleadosSQLite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombreEspec(), getLugar(), getFecha_Espec(), getHorario_espec());
    }
}
