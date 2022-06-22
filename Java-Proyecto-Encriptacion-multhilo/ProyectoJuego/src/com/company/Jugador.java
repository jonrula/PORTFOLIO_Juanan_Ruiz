package com.company;

import java.util.Objects;

public class Jugador implements Comparable<Integer>{

    // Atributos
    private int id;
    private String nombre;
    private String apellidos;
    private String nick;
    private int edad;
    private String contrasena;
    private int record;
    private int jugando;

    //Constructor


    public Jugador(int id, String nombre, String apellidos, String nick, int edad, String contrasena, int record, int jugando) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nick = nick;
        this.edad = edad;
        this.contrasena = contrasena;
        this.record = record;
        this.jugando= jugando;
    }

    public Jugador(int id, String nombre, int record) {
        this.id = id;
        this.nombre = nombre;
        this.record = record;
    }
    // Para comprobar duplicados
    public Jugador(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getJugando() {
        return jugando;
    }

    public void setJugando(int jugando) {
        this.jugando = jugando;
    }

    // To String()

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nick='" + nick + '\'' +
                ", edad=" + edad +
                ", contrasena='" + contrasena + '\'' +
                ", record=" + record +
                ", jugando=" + jugando +
                '}';
    }

    // Para comprobar duplicados
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return nombre.equalsIgnoreCase(jugador.nombre) && apellidos.equalsIgnoreCase(jugador.apellidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellidos);
    }

    // Para ordenar los datos

    @Override
    public int compareTo(Integer o) {
        return 0;
    }
}
