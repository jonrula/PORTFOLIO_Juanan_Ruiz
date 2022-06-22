package com.company;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ListaTablaGestionModel extends AbstractTableModel {

    private final String[] columnas = {"ID GESTIÓN", "ID PROYECTO", "PROYECTO", "ID PROVEEDOR", "PROVEEDOR", "ID PIEZA", "PIEZA", "CANTIDAD"};

    ArrayList<Gestion> gestiones = new ArrayList<>();

    public ListaTablaGestionModel(ArrayList<Gestion> gestiones) {
        this.gestiones = gestiones;
    }


    @Override
    public int getRowCount() {
        return gestiones.size();
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

        Gestion gestion = gestiones.get(rowIndex);


        return switch (columnIndex) {
            case 0 -> gestion.getIdGestion();
            case 1 -> gestion.getProyectoByIdProyecto().getIdProyecto();
            case 2 -> gestion.getProyectoByIdProyecto().getProyecto();
            case 3 -> gestion.getProveedorByIdProveedor().getIdProveedor();
            case 4 -> gestion.getProveedorByIdProveedor().getProveedor();
            case 5 -> gestion.getPiezaByIdPieza().getIdPieza();
            case 6 -> gestion.getPiezaByIdPieza().getPieza();
            case 7 -> gestion.getCantidad();

            default -> null;
        };
    }
}
