package com.ikasgela;

import java.util.ArrayList;
import java.util.List;

public class Ingrediente_receta {

    // Propiedades
    private int id;
    private String cantidad;
    private int ingrediente_id;
    private int receta_id;

    // Asociación
    List<Receta> recetas = new ArrayList<>();
    List<Ingrediente> ingredientes = new ArrayList<>();

    // Constructor

    public Ingrediente_receta(int id, String cantidad, int ingrediente_id, int receta_id) {
        this.id = id;
        this.cantidad = cantidad;
        this.ingrediente_id = ingrediente_id;
        this.receta_id = receta_id;
    }

    public Ingrediente_receta(String cantidad) {
        this.cantidad = cantidad;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIngrediente_id() {
        return ingrediente_id;
    }

    public void setIngrediente_id(int ingrediente_id) {
        this.ingrediente_id = ingrediente_id;
    }

    public int getReceta_id() {
        return receta_id;
    }

    public void setReceta_id(int receta_id) {
        this.receta_id = receta_id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    // Métodos
    @Override
    public String toString() {
        return "Ingrediente_receta{" +
                "id=" + id +
                ", cantidad='" + cantidad + '\'' +
                ", ingrediente_id=" + ingrediente_id +
                ", receta_id=" + receta_id +
                '}';
    }
}
