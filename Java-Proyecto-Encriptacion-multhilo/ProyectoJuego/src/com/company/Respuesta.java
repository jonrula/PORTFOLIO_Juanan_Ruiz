package com.company;

public class Respuesta {

    // Propiedades
    private int id;
    private String respuesta;
    private int correcta;
    private int pregunta_id;

    // Asociación
    private Pregunta pregunta;

    // Constructor

    public Respuesta(int id, String respuesta, int correcta, int pregunta_id) {
        this.id = id;
        this.respuesta = respuesta;
        this.correcta = correcta;
        this.pregunta_id = pregunta_id;
    }

    // Getters y Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }

    public int getPregunta_id() {
        return pregunta_id;
    }

    public void setPregunta_id(int pregunta_id) {
        this.pregunta_id = pregunta_id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }



    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    // Métodos


    @Override
    public String toString() {
        return respuesta;

    }
}
