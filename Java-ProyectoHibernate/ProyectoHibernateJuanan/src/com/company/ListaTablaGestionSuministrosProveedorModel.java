package com.company;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ListaTablaGestionSuministrosProveedorModel extends AbstractTableModel {

    private final String[] columnas = {"ID GESTIÓN","PROVEEDOR", "PROYECTO", "PIEZA","CANTIDAD"};

    ArrayList<Gestion> gestiones = new ArrayList<>();

    public ListaTablaGestionSuministrosProveedorModel(ArrayList<Gestion> gestiones) {
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
            case 1 -> gestion.getProveedorByIdProveedor().getProveedor();
            case 2 -> gestion.getProyectoByIdProyecto().getProyecto();
            case 3 -> gestion.getPiezaByIdPieza().getPieza();
            case 4 -> gestion.getCantidad();

            default -> null;
        };
    }
}
