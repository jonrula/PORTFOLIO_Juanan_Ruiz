package com.ikasgela;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Receta {

    // Propiedades
    private int id;
    private String titulo;
    private String instrucciones;

    // Asociación --> 1 receta tiene muchos ingredientes

    List<Ingrediente> ingredientes = new ArrayList<>();
    private Ingrediente_receta receta_ingrediente;

    // Constructor

    public Receta(int id, String titulo, String instrucciones) {
        this.id = id;
        this.titulo = titulo;
        this.instrucciones = instrucciones;
    }

    public Receta(int id) {
        this.id = id;
    }

    public Receta(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Receta(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    // Getters y Setters


    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Ingrediente_receta getReceta_ingrediente() {
        return receta_ingrediente;
    }

    public void setReceta_ingrediente(Ingrediente_receta receta_ingrediente) {
        this.receta_ingrediente = receta_ingrediente;
    }

    // Método

    // Implemento el método 'equals and hasCode()' para comparar si existe la receta (Cuando la he importado de SQL a java y por tanto ya está en el ArrayList de recetas)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receta)) return false;
        Receta receta = (Receta) o;
        return getTitulo().equalsIgnoreCase(receta.getTitulo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitulo());
    }

    @Override
    public String toString() {
        return titulo;
    }
}
