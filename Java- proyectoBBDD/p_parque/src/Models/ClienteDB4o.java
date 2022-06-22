package Models;

import java.util.ArrayList;
import java.util.Objects;

public class ClienteDB4o {
    private String dni;
    private String nombre;
    private String apellidos;
    private Integer edad;

    private ArrayList<EspectaculoDB4o> espectaculos = new ArrayList<>();

    public ClienteDB4o(String dni, String nombre, String apellidos) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    //si hacemos un login le metemos una password (y en la BBDD
    // o lo hacemos solo con los empleados)


    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public ArrayList<EspectaculoDB4o> getEspectaculos() {
        return espectaculos;
    }

    public void setEspectaculos(ArrayList<EspectaculoDB4o> espectaculos) {
        this.espectaculos = espectaculos;
    }

    public ClienteDB4o(String dni, String nombre, String apellidos, Integer edad, ArrayList<EspectaculoDB4o> espectaculos) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.espectaculos = espectaculos;
    }




    //constructor vacío
    public ClienteDB4o() {
    }


    public ClienteDB4o(String dni, String nombre, String apellidos, Integer edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public ClienteDB4o(String dni) {
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

    //podemos hacer una función para editar los datos en plan:
    public void editarClienteDb4o(ClienteDB4o cliente) {
        this.nombre = cliente.getNombre();
        this.apellidos = cliente.getApellidos();
        this.edad = cliente.getEdad();
    }
    //y llamarla desde insertar_editar de db4o, ya que es todoo desde java y nos
    //ahorramos complicarlo más
    //dni es PK -> no se modifica


    //implementamos el equals & hash code para que no haya dos clientes iguales
    //mediante el campo dni

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDB4o cliente = (ClienteDB4o) o;
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
}
