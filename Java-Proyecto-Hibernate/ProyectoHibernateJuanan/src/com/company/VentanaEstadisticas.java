package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaEstadisticas {
    private JButton bt_noDePiezasProyectos;
    private JButton bt_noDePiezasProveedor;
    private JPanel PanelEstadisticas;
    private JTextField et_nombrePiezaMasCantidad;
    private JTextField et_nombrePiezaMasProyectos;
    private JTextField et_CantidadPiezas;
    private JTextField et_cantidadPiezaProyectos;
    private JTextField et_nombreProveedorMasPiezas;
    private JTextField et_proveedorCantidadMasPiezas;
    private JTextField et_nombreProveedorMasProyectos;
    private JTextField et_proveedorCantidadMasProyectos;
    private JTextField et_nombreProveedorMaspiezas;
    private JTextField et_proveedorCantidadMaspiezas;
    private JLabel lb_suministrosProyectos1;
    private JLabel lb_suministrosProyectos2;
    private JLabel lb_suministrosProveedor1;
    private JLabel lb_suministrosProveedor2;
    private JLabel lb_suministrosProveedor3;

    JFrame frameEstadisticaPiezasProveedor = new JFrame("Gestión Global: Número de piezas y cantidad suministrada por proveedor");
    JFrame frameEstadisticaPiezasProyectos = new JFrame("Gestión Global: Número de piezas y cantidad suministrada en proyectos");

    // Declaramos tabla
    private JTable tabla;


    public VentanaEstadisticas() {

        // Cargo los datos en la ventana (POR PROYECTOS)
        String piezaMasCantidad = CargaDatos.nombrePiezaMasCantidad();
        double cantidadPiezas = CargaDatos.piezaMasCantidad();

        et_nombrePiezaMasCantidad.setText(piezaMasCantidad);
        et_CantidadPiezas.setText(String.valueOf(cantidadPiezas));
        et_nombrePiezaMasCantidad.setEditable(false);
        et_CantidadPiezas.setEditable(false);

        System.out.println();

        String piezaMasCantidadProyectos = CargaDatos.nombrePiezaMasProyectos();
        int cantidadPiezasProyectos = CargaDatos.cantidadPiezaProyectos();

        et_nombrePiezaMasProyectos.setText(piezaMasCantidadProyectos);
        et_cantidadPiezaProyectos.setText(String.valueOf(cantidadPiezasProyectos));
        et_nombrePiezaMasProyectos.setEditable(false);
        et_cantidadPiezaProyectos.setEditable(false);

        System.out.println();

        // Cargo los datos en la ventana (POR PROVEEDORES)

        String nombreProveedorMasPiezas = CargaDatos.nombreProveedorMasPiezas();
        double proveedorCantidadMasPiezas = CargaDatos.proveedorCantidadMasPiezas();

        et_nombreProveedorMasPiezas.setText(nombreProveedorMasPiezas);
        et_proveedorCantidadMasPiezas.setText(String.valueOf(proveedorCantidadMasPiezas));
        et_nombreProveedorMasPiezas.setEditable(false);
        et_proveedorCantidadMasPiezas.setEditable(false);

        System.out.println();

        String nombreProveedorMasProyectos = CargaDatos.nombreProveedorMasProyectos();
        int proveedorCantidadMasProyectos = CargaDatos.proveedorCantidadMasProyectos();

        et_nombreProveedorMasProyectos.setText(nombreProveedorMasProyectos);
        et_proveedorCantidadMasProyectos.setText(String.valueOf(proveedorCantidadMasProyectos));
        et_nombreProveedorMasProyectos.setEditable(false);
        et_proveedorCantidadMasProyectos.setEditable(false);

        System.out.println();

        String nombreProveedorMaspiezas = CargaDatos.nombreProveedorMaspiezas();
        double proveedorCantidadMaspiezas = CargaDatos.proveedorCantidadMaspiezas();

        et_nombreProveedorMaspiezas.setText(nombreProveedorMaspiezas);
        et_proveedorCantidadMaspiezas.setText(String.valueOf(proveedorCantidadMaspiezas));
        et_nombreProveedorMaspiezas.setEditable(false);
        et_proveedorCantidadMaspiezas.setEditable(false);


        System.out.println();


        bt_noDePiezasProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameEstadisticaPiezasProyectos.setContentPane(new VentanaTablaProyectos().getPanelTablaProyectos());
                frameEstadisticaPiezasProyectos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameEstadisticaPiezasProyectos.setMinimumSize(new Dimension(1000, 400));
                frameEstadisticaPiezasProyectos.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
                //frame.setLocationRelativeTo(null); // Saca la ventana al centro
                frameEstadisticaPiezasProyectos.pack();
                frameEstadisticaPiezasProyectos.setVisible(true);


            }
        });
        bt_noDePiezasProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frameEstadisticaPiezasProveedor.setContentPane(new VentanaTablaProveedor().getPanelTablaProveedor());
                frameEstadisticaPiezasProveedor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameEstadisticaPiezasProveedor.setMinimumSize(new Dimension(1000, 400));
                frameEstadisticaPiezasProveedor.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
                //frame.setLocationRelativeTo(null); // Saca la ventana al centro
                frameEstadisticaPiezasProveedor.pack();
                frameEstadisticaPiezasProveedor.setVisible(true);


            }
        });

    }

    public JPanel getPanelEstadisticas() {
        return PanelEstadisticas;
    }
}
