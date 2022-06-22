package com.company;

import java.util.ArrayList;

public class Ranking implements Comparable<Ranking> {

    // Atributos

    private String nombre;
    private int ranking;


    // Constructores

    public Ranking(String nombre, int ranking) {
        this.nombre = nombre;
        this.ranking = ranking;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "nombre='" + nombre + '\'' +
                ", ranking=" + ranking +
                '}';
    }

    public void mostrarRankin(ArrayList<Ranking> ranking2) {
        int totalValoraciones= ranking2.size();

        System.out.format("%-20s%-20s%-20s\n", "PUESTO", "ALBÚM", "PUNTOS");

        for (int i = 1; i <= totalValoraciones; i++) {

            System.out.format("%-20d%-20s%-20d\n", i, nombre, ranking);

        }

    }

    @Override
    public int compareTo(Ranking o) {
        return Integer.compare(ranking, o.ranking);
    }
}
