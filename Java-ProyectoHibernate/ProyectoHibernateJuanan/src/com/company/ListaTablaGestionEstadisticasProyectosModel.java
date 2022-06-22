package com.company;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ListaTablaGestionEstadisticasProyectosModel extends AbstractTableModel {

    private final String[] columnas = {"ID PROYECTO", "PROYECTO", "CIUDAD", "NUMERO PIEZAS", "CANTIDAD SUMINISTRADA", "NÚMERO PROVEEDORES"};

    ArrayList<Estadistica> piezas = new ArrayList<>();

    public ListaTablaGestionEstadisticasProyectosModel(ArrayList<Estadistica> piezas) {
        this.piezas = piezas;
    }

    @Override
    public int getRowCount() {
        return piezas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    //Nombre de las columnas
    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Estadistica es = piezas.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> es.getId_Est();
            case 1 -> es.getNombreEst();
            case 2 -> es.getDescripcionEst();
            case 3 -> es.getNumPiezasEst();
            case 4 -> es.getCantidadEst();
            case 5 -> es.getNumProyectosEst();

            default -> null;
        };
    }
}
