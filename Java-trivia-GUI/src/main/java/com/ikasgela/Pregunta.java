package com.ikasgela;

import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pregunta {

    // Propiedades
    private int id;
    private String pregunta;

    // Asociación
    private List<Respuesta> respuesta=  new ArrayList<>();

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

    public List<Respuesta> getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(List<Respuesta> respuesta) {
        this.respuesta = respuesta;
    }

    // Métodos

    @Override
    public String toString() {
        return pregunta;
    }
}
