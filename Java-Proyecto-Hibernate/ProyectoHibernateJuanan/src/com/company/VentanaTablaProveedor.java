package com.company;

import javax.swing.*;
import java.util.ArrayList;

public class VentanaTablaProveedor {
    private JPanel PanelTablaProveedor;
    private JScrollPane ListaEstadisticasProvedores;


    public JPanel getPanelTablaProveedor() {
        return PanelTablaProveedor;
    }

    // Declaramos tabla
    private JTable tabla;


    public VentanaTablaProveedor() {


        // Refresco el listado con la nueva consulta
        ArrayList<Estadistica> consultaPiezas = new ArrayList<>();
        consultaPiezas = CargaDatos.piezasCantidadSuministradaProveedores();

        tabla = new JTable();
        tabla.setModel(new ListaTablaGestionEstadisticasProveedoresModel(consultaPiezas));
        ListaEstadisticasProvedores.setViewportView(tabla);

    }
}
