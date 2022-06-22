package com.company;

public class Estadistica {

    /*
    CREO esta clase para pasarle a luego al Jtable los campos 'tipo' que coinciden con los mismos que la consulta:
    select  p.id_proyecto,  p.proyecto,  p.ciudad,  count(g.ID_gestion_pieza) as 'Numero Piezas', sum(g.cantidad) as 'Cantidad suministrada', count(g.ID_gestion_proveedor) as 'Numero Proveedores' from Gestion g, proyectos p where g.ID_gestion_proyecto=p.id_proyecto  group by g.ID_gestion_proyecto order by 'Numero Piezas' DESC
                  int         String      String                           int                                         double                                       int
     */


    // Atributos
    private int id_Est;
    private String nombreEst;
    private String descripcionEst;
    private int numPiezasEst;
    private double cantidadEst;
    private int numProyectosEst;


    // Constructor
    public Estadistica(int id_Est, String nombreEst, String descripcionEst, int numPiezasEst, double cantidadEst, int numProyectosEst) {
        this.id_Est = id_Est;
        this.nombreEst = nombreEst;
        this.descripcionEst = descripcionEst;
        this.numPiezasEst = numPiezasEst;
        this.cantidadEst = cantidadEst;
        this.numProyectosEst = numProyectosEst;
    }


    // Getter y Setters
    public int getId_Est() {
        return id_Est;
    }

    public void setId_Est(int id_Est) {
        this.id_Est = id_Est;
    }

    public String getNombreEst() {
        return nombreEst;
    }

    public void setNombreEst(String nombreEst) {
        this.nombreEst = nombreEst;
    }

    public String getDescripcionEst() {
        return descripcionEst;
    }

    public void setDescripcionEst(String descripcionEst) {
        this.descripcionEst = descripcionEst;
    }

    public int getNumPiezasEst() {
        return numPiezasEst;
    }

    public void setNumPiezasEst(int numPiezasEst) {
        this.numPiezasEst = numPiezasEst;
    }

    public double getCantidadEst() {
        return cantidadEst;
    }

    public void setCantidadEst(double cantidadEst) {
        this.cantidadEst = cantidadEst;
    }

    public int getNumProyectosEst() {
        return numProyectosEst;
    }

    public void setNumProyectosEst(int numProyectosEst) {
        this.numProyectosEst = numProyectosEst;
    }
}
