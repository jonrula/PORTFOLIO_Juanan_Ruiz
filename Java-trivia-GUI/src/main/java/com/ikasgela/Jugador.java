package com.ikasgela;

import java.util.Objects;

public class Jugador {

    // Hay que hacer 3 tablas DB Browser for SQLite --> 2 relacionadas entre si por foreign key: Preguntas y respuestas y otra de jugadores

    // Propiedades

    private int id;
    private String nombreJugador;
    private int record;


    // Constructor

    public Jugador(int id, String nombreJugador, int record) {
        this.id = id;
        this.nombreJugador = nombreJugador;
        this.record = record;
    }

    public Jugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }


    // Métodos


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jugador)) return false;
        Jugador jugador = (Jugador) o;
        return getNombreJugador().equalsIgnoreCase(jugador.getNombreJugador());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombreJugador());
    }

    @Override
    public String toString() {

        return nombreJugador + "    \t" + record + "  puntos";


    }
}
