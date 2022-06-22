package com.ikasgela;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ingrediente {

    // Propiedades
    private int id;
    private String nombre;

    // Constructor

    public Ingrediente(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Ingrediente(String nombre) {
        this.nombre = nombre;
    }

    // Asociación --> muchos ingredientes hay en muchas recetas
    List<Receta> recetas = new ArrayList<>();
    private Ingrediente_receta receta_ingrediente;


    // Getters y Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }

    public Ingrediente_receta getReceta_ingrediente() {
        return receta_ingrediente;
    }

    public void setReceta_ingrediente(Ingrediente_receta receta_ingrediente) {
        this.receta_ingrediente = receta_ingrediente;
    }


    // Métodos

    // Implemento el método 'equals and hashCode()' para ver si se repiten ingredientes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingrediente)) return false;
        Ingrediente that = (Ingrediente) o;
        return getNombre().equalsIgnoreCase(that.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre());
    }

    @Override
    public String toString() {
        return nombre;
    }
}
