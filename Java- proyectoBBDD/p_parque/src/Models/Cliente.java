package Models;

import java.util.ArrayList;
import java.util.Objects;

public class Cliente {

    private String dni;
    private String nombre;
    private String apellidos;
    private Integer edad;

    // Relacion con espectaculos --> 1 cliente tiene muchos espectáculos
    // IMPORTANTE al declarar el ArrayList, poner al final '=new ArrayList<>()' porque sino nos genera un 'NullPointerexception'

    private ArrayList<Espectaculo> espectaculos= new ArrayList<>();


    //Para SQLite
    private ArrayList<EspectaculoSQLite> espectaculosSQLite= new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String dni, String nombre, String apellidos, Integer edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public Cliente(String dni) {
        this.dni = dni;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public ArrayList<Espectaculo> getEspectaculos() {
        return espectaculos;
    }

    public void setEspectaculos(ArrayList<Espectaculo> espectaculos) {
        this.espectaculos = espectaculos;
    }

    public ArrayList<EspectaculoSQLite> getEspectaculosSQLite() {
        return espectaculosSQLite;
    }

    public void setEspectaculosSQLite(ArrayList<EspectaculoSQLite> espectaculosSQLite) {
        this.espectaculosSQLite = espectaculosSQLite;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return dni.equals(cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    //toString() bien printeado
    @Override
    public String toString() {
        String cadena = nombre + " " + apellidos;
        return String.format("%10s, %s", cadena, dni);
    }

    public void mostrarClientes(ArrayList<Cliente>clientes){

        System.out.println("\nDatos del arrayList 'clientes':\n");
        System.out.format("%-20s%-20s%-20s%-5s\n","ID_DNI","NOMBRE","APELLIDOS","EDAD");

        for (Cliente c : clientes) {
            System.out.format("%-20s%-20s%-20s%-5d\n", c.getDni(), c.getNombre(), c.getApellidos(), c.getEdad());
        }

    }

}
