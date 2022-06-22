package com.company;

import javax.swing.*;
import java.util.ArrayList;

public class VentanaTablaProyectos {
    private JTable JTableEstadisticasPiezaProyectos;
    private JPanel PanelTablaProyectos;
    private JScrollPane ListaEstadisticasProyectos;

    // Declaramos tabla
    private JTable tabla;

    public JPanel getPanelTablaProyectos() {
        return PanelTablaProyectos;
    }


    public VentanaTablaProyectos() {


        // Refresco el listado con la nueva consulta
        ArrayList<Estadistica> consultaPiezas = new ArrayList<>();
        consultaPiezas = CargaDatos.piezasCantidadSuministradaProyectos();

        tabla = new JTable();
        tabla.setModel(new ListaTablaGestionEstadisticasProyectosModel(consultaPiezas));
        ListaEstadisticasProyectos.setViewportView(tabla);

    }
}
