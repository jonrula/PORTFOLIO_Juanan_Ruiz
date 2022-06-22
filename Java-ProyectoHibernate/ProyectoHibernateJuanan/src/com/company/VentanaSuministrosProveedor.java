package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class VentanaSuministrosProveedor {
    private JPanel PanelSuministrosProveedor;
    private JComboBox<Integer> comboBoxSuministrosProveedor;
    private JTextField et_SPpiezasSuministradas;
    private JTextField et_SPproyectos;
    private JButton bt_verPiezasSuministradas;
    private JLabel lb_SPsumistrosProveedor;
    private JLabel lb_SPpiezasSuministradas;
    private JLabel lb_SPproyectos;
    private JTextArea resultadosProveedor;
    private JScrollPane listadJTablePiezasSuministradas;


    // Cargamos los ArrayList con los datos de la BD
    ArrayList<Proveedores> proveedores = CargaDatos.cargarProveedores();


    // Declaramos tabla
    private JTable tabla;

    // Declaro las 'sesion0, como global para acceso de  todos los objetos de la ventana
    SessionFactory sesion = HibernateUtil.getSessionFactory();
    Session session = sesion.openSession();


    public VentanaSuministrosProveedor() {




        // Cargamos el comboBox
        for (Proveedores proveedor : proveedores) {
            comboBoxSuministrosProveedor.addItem(proveedor.getIdProveedor());
        }

        // Cargo la ventana con los datos del proveedor que este seleccionado en el comboBoz, en este caso el que esté en el índice primero
        datosProveedor();


        // Cargamos el combo de proveedores con la consulta y al elegir el proveedor del combo, sacamos su resultados
        comboBoxSuministrosProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comboBoxSuministrosProveedor.getSelectedItem() != null) {
                    datosProveedor();
                }
            }
        });


        bt_verPiezasSuministradas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Instancio un proveedor y lo cargo según Hibernate (le paso el código del proveedor parseado, es un int)
                Proveedores prov = session.get(Proveedores.class, (Serializable) comboBoxSuministrosProveedor.getSelectedItem());
                int idProveedor = prov.getIdProveedor();

                // Refresco el listado con la nueva consulta

                ArrayList<Gestion> consultaGestion = new ArrayList<>();
                //consultaGestion = CargaDatos.cargarPiezasProyectosGestion(idProveedor);
                consultaGestion= CargaDatos.cargarPiezasProyectosGestion(idProveedor);

                tabla = new JTable();
                tabla.setModel(new ListaTablaGestionSuministrosProveedorModel(consultaGestion));
                listadJTablePiezasSuministradas.setViewportView(tabla);


            }
        });

    }

    private void datosProveedor() {

        // Instancio un proveedor y lo cargo según Hibernate (le paso el código del proveedor parseado, es un int)
        Proveedores prov = session.get(Proveedores.class, (Serializable) comboBoxSuministrosProveedor.getSelectedItem());
        int idProveedor = prov.getIdProveedor();

        // Número Piezas suministradas por el proveedor
        int numeroPiezas = CargaDatos.consultaPiezasProveedorGestion(idProveedor);

        // Número de proyectos en los que está el proveedor
        //String numeroProyectos=CargaDatos.consultaProyectosProveedorGestion(idProveedor); // Está hecho con una variable 'contador', para cuando itera, porque la query no devuelve un count), sino filas
        int numeroProyectos = CargaDatos.numeroProyectosProveedorGestion(idProveedor); // Está hecho como 'createSQLquery'

        // Datos del proveedor en el textArea
        String datosProveedor =
                "\n\tCÓDIGO:                         " + prov.getProveedor() + "\n" +
                        "\tPROVEEDOR:                   " + prov.getProveedor() + "\n" +
                        "\tRESPONSABLE VENTAS:   " + prov.getResponsableVentas() + "\n" +
                        "\tDIRECCION:                    " + prov.getDirCp() + "\n";

        resultadosProveedor.setEditable(false);
        resultadosProveedor.setText(datosProveedor);

        et_SPpiezasSuministradas.setText(String.valueOf(numeroPiezas));
        et_SPpiezasSuministradas.setEditable(false);

        et_SPproyectos.setText(String.valueOf(numeroProyectos));
        et_SPproyectos.setEditable(false);
    }

    public JPanel getPanelSuministrosProveedor() {
        return PanelSuministrosProveedor;
    }
}
