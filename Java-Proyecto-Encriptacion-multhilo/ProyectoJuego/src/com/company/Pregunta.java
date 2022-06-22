package com.company;

import java.util.ArrayList;
import java.util.List;

public class Pregunta {

    // Propiedades
    private int id;
    private String pregunta;

    // Asociación
    private ArrayList<Respuesta> respuestas=  new ArrayList<>();

    // Constructor
    public Pregunta(int id, String pregunta) {
        this.id = id;
        this.pregunta = pregunta;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public ArrayList<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }
// Métodos

    @Override
    public String toString() {
        return pregunta;
    }
}
