package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class VentanaSuministrosPiezas {
    private JPanel PanelSuministrosProyectos;
    private JTextField et_NoProveedoresSuministran;
    private JTextField et_CantidadSuministrada;
    private JButton bt_verPiezasSumnistradas;
    private JLabel lb_NoProyecto;
    private JTextArea resultadoDatosPieza;
    private JTextField et_NumeroProyectos;
    private JComboBox<Integer> comboBoxSuministrosPiezas;
    private JLabel lb_PiezaSuministrosPieza;
    private JScrollPane listaJTableSuministrosPiezas;

    // Cargamos los ArrayList con los datos de la BD
    ArrayList<Piezas> piezas = CargaDatos.cargarPiezas();


    // Declaramos tabla
    private JTable tabla;

    // Declaro las 'sesion0, como global para acceso de  todos los objetos de la ventana
    SessionFactory sesion = HibernateUtil.getSessionFactory();
    Session session = sesion.openSession();


    public VentanaSuministrosPiezas() {

        // Cargamos el comboBox
        for (Piezas pieza : piezas) {
            comboBoxSuministrosPiezas.addItem(pieza.getIdPieza());
        }

        datosPiezas();


        bt_verPiezasSumnistradas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Instancio un proveedor y lo cargo según Hibernate (le paso el código del proveedor parseado, es un int)
                Piezas pieza = session.get(Piezas.class, (Serializable) comboBoxSuministrosPiezas.getSelectedItem());
                int idPieza = pieza.getIdPieza();

                // Refresco el listado con la nueva consulta

                ArrayList<Gestion> consultaGestion = new ArrayList<>();
                // consultaGestion = CargaDatos.cargarProyectosProveedoresGestion(idPieza);
                consultaGestion = CargaDatos.cargarProyectosProveedoresGestion(idPieza);

                tabla = new JTable();
                //tabla.setModel(new ListaTablaGestionModel(consultaGestion));
                tabla.setModel(new ListaTablaGestionSuministrosPiezasModel(consultaGestion));
                listaJTableSuministrosPiezas.setViewportView(tabla);

            }
        });
        comboBoxSuministrosPiezas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comboBoxSuministrosPiezas.getSelectedItem() != null) {
                    datosPiezas();
                }

            }
        });

    }

    private void datosPiezas() {

        // Instancio una pieza y lo cargo según Hibernate (le paso el código del proveedor parseado, es un int)
        Piezas pieza = session.get(Piezas.class, (Serializable) comboBoxSuministrosPiezas.getSelectedItem());
        int idPieza = pieza.getIdPieza();

        // Número de proyectos en los que está el proveedor
        int numeroProyectos = CargaDatos.consultaProyectosPiezaGestion(idPieza);

        // Número de proveedores que suministran la pieza
        int numeroProveedores=CargaDatos.consultaProveedoresSuministranPiezaGestion(idPieza);

        // Cantidad Piezas suministradas por el proveedor
        double cantidadPiezas = CargaDatos.consultaPiezasSuministradasGestion(idPieza);





        // Datos del proveedor en el textArea
        String datosPieza =
                "\n\n\n\tCÓDIGO:            " + pieza.getIdPieza() + "\n" +
                        "\tNOMBRE:           " + pieza.getPieza() + "\n" +
                        "\tDESCRIPCIÓN:   " + pieza.getDescripcion() + "\n" +
                        "\tPRECIO:             " + pieza.getPrecio() + " €\n\n";

        resultadoDatosPieza.setEditable(false);
        resultadoDatosPieza.setText(datosPieza);


        et_CantidadSuministrada.setText(String.valueOf(cantidadPiezas));
        et_CantidadSuministrada.setEditable(false);

        et_NumeroProyectos.setText(String.valueOf(numeroProyectos));
        et_NumeroProyectos.setEditable(false);

        et_NoProveedoresSuministran.setText(String.valueOf(numeroProveedores));
        et_NoProveedoresSuministran.setEditable(false);
    }

    public JPanel getPanelSuministrosProyectos() {
        return PanelSuministrosProyectos;
    }
}
