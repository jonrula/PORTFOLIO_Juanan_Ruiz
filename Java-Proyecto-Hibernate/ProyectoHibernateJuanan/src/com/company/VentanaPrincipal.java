package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class VentanaPrincipal {
    private JPanel PanelVentanaPrincipal;
    private JMenuBar MenuBar;
    private JMenu menuBaseDatos;
    private JMenu menuProveedores;
    private JMenuItem itemGestionProveedores;
    private JMenu subMenuConsultaProveedores;
    private JMenuItem itemCodigoProveedor;
    private JMenuItem itemNombreProveedor;
    private JMenuItem itemDireccionProveedor;
    private JMenu menuPiezas;
    private JMenu menuProyectos;
    private JMenu menuGestionGlobal;
    private JMenu menuAyuda;
    private JMenuItem itemGestionPiezas;
    private JMenu subMenuConsultaPiezas;
    private JMenuItem itemCodigoPieza;
    private JMenuItem itemNombrePieza;
    private JMenuItem itemGestionProyectos;
    private JMenu submenuConsultaProyectos;
    private JMenuItem itemCodigoProyectos;
    private JMenuItem itemNombreProyecto;
    private JMenuItem itemCiudadProyecto;
    private JMenuItem itemGlobalPiezasProveedoresProyectos;
    private JMenuItem itemSuministrosProveedor;
    private JMenuItem itemSuministrosPiezas;
    private JMenuItem itemEstadisticas;
    private JMenuItem itemAyuda;


    JFrame frameProveedores = new JFrame("Gestión de Proveedores");
    JFrame frameCodigoProveedores = new JFrame("Gestión de Proveedores por Código");
    JFrame framePiezas = new JFrame("Gestión de Piezas");
    JFrame frameCodigoPiezas = new JFrame("Gestión de Piezas por Código");
    JFrame frameProyectos = new JFrame("Gestión de Proyectos");
    JFrame frameCodigoProyectos = new JFrame("Gestión de Proyectos por Código");
    JFrame frameGlobalPiezasProveedoresProyectos = new JFrame("Gestión Global: Proveedores -Piezas - Proyectos");
    JFrame frameGlobalSuministrosProveedor = new JFrame("Gestión Global: Suministros por Proveedor");
    JFrame frameGlobalSuministrosPiezas = new JFrame("Gestión Global: Piezas suministradas a proyectos");
    JFrame frameGlobalEstadisticas = new JFrame("Gestión Global: Resumenes Estadisticos");
    JFrame frameAyuda = new JFrame("Información");


    static boolean abrirPestanaConsultas = false;


    public VentanaPrincipal() {

        //timer();

        //Añadir en la DataBase properties de 'Hibernate@localhost' si da problemas en la conexión a MySql--> URL:  jdbc:mysql://localhost:3306/Hibernate?autoReconnect=true&useSSL=false

        /*
        Quitar los warnings del principio
            import java.util.logging.LogManager;
            import java.util.logging.Logger;
         */

        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);

        JOptionPane.showMessageDialog(null, "Cargando todos los datos, paciencia ...");

        // Cargo todos los ArrayList en la Ventana Principal, y ya tienen accesos a estos datos todas las ventanas y es más rápido

        ArrayList<Proveedores> proveedores = CargaDatos.cargarProveedores();
        ArrayList<Gestion> gestiones = CargaDatos.cargarGestiones();
        ArrayList<Proyectos> proyectos = CargaDatos.cargarProyectos();
        ArrayList<Piezas> piezas = CargaDatos.cargarPiezas();


        // VENTANAS GESTION PROVEEDORES
        itemGestionProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaProveedores(frameProveedores);
            }
        });

        itemCodigoProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaProveedoresPorCodigo(frameCodigoProveedores);
            }
        });

        itemNombreProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Seleccionar pestaña de consultas y abrir ventana
                abrirPestanaConsultas = true;
                ventanaProveedores(frameProveedores);
            }
        });

        itemDireccionProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Seleccionar pestaña de consultas y abrir ventana
                abrirPestanaConsultas = true;
                ventanaProveedores(frameProveedores);
            }
        });


        // VENTANAS GESTION PIEZAS
        itemGestionPiezas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaPiezas(framePiezas);
            }
        });

        itemCodigoPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaPiezasPorCodigo(frameCodigoPiezas);
            }
        });

        itemNombrePieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Seleccionar pestaña de consultas y abrir ventana
                abrirPestanaConsultas = true;
                ventanaPiezas(framePiezas);
            }
        });

        // VENTANAS PROYECTOS

        itemGestionProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaProyectos(frameProyectos);
            }
        });

        itemCodigoProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaProyectosPorCodigo(frameCodigoProyectos);
            }
        });

        itemNombreProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Seleccionar pestaña de consultas y abrir ventana
                abrirPestanaConsultas = true;
                ventanaProyectos(frameProyectos);
            }
        });

        itemCiudadProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Seleccionar pestaña de consultas y abrir ventana
                abrirPestanaConsultas = true;
                ventanaProyectos(frameProveedores);
            }
        });

        // VENTANAS GESTION GLOBAL

        itemGlobalPiezasProveedoresProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaGlobalPiezasProveedoresProyectos(frameGlobalPiezasProveedoresProyectos);
            }
        });

        itemSuministrosProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaGlobalSuministrosProveedor(frameGlobalSuministrosProveedor);


            }
        });


        itemSuministrosPiezas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaSuministrosPiezas(frameGlobalSuministrosPiezas);


            }
        });

        itemEstadisticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaEstadisticas(frameGlobalEstadisticas);

            }
        });


        itemAyuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaAyuda(frameAyuda);
            }
        });

    }


    // Le paso la clase main para que se EJECUTE AL ARRANCAR LA APLICACION
    public static void main(String[] args) throws IOException {


        JFrame frame = new JFrame("Gestión de Proyectos");
        frame.setContentPane(new VentanaPrincipal().PanelVentanaPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1200, 600));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        // Añadir imagen
        frame.add(new JLabel(new ImageIcon(ImageIO.read(new File("Imagenes/hibernate.png")))));
        frame.pack();
        frame.setVisible(true);


    }


    public static void ventanaProveedores(JFrame frame) {

        frame.setContentPane(new VentanaProveedores().getPanelProveedores());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 400));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);

    }


    public static void ventanaProveedoresPorCodigo(JFrame frame) {


        frame.setContentPane(new VentanaCodigoProveedor().getPanelCodigoProveedor());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 400));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);

    }

    public static void ventanaPiezas(JFrame frame) {

        frame.setContentPane(new VentanaPiezas().getPanelPiezas());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 400));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);

    }


    public static void ventanaPiezasPorCodigo(JFrame frame) {

        frame.setContentPane(new VentanaCodigoPieza().getPanelCodigoPieza());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 400));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);
    }


    public static void ventanaProyectos(JFrame frame) {

        frame.setContentPane(new VentanaProyectos().getPanelProyectos());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 400));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);

    }


    public static void ventanaProyectosPorCodigo(JFrame frame) {

        frame.setContentPane(new VentanaCodigoProyecto().getPanelCodigoProyecto());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 400));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);
    }

    public static void ventanaGlobalPiezasProveedoresProyectos(JFrame frame) {



        frame.setContentPane(new VentanaGestionGlobalProveedoresPiezasProyectos().getPanelGestionGlobalProPieProy());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000, 500));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);
    }

    public static void ventanaGlobalSuministrosProveedor(JFrame frame) {

        frame.setContentPane(new VentanaSuministrosProveedor().getPanelSuministrosProveedor());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000, 400));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);
    }

    public static void ventanaSuministrosPiezas(JFrame frame) {

        frame.setContentPane(new VentanaSuministrosPiezas().getPanelSuministrosProyectos());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000, 400));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);
    }

    public static void ventanaAyuda(JFrame frame) {

        frame.setContentPane(new VentanaAyuda().getPanelInformacion());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000, 400));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);
    }


    // Arranca la ventana Principal
    public static void ventanaEstadisticas(JFrame frame) {

        frame.setContentPane(new VentanaEstadisticas().getPanelEstadisticas());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000, 400));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);
    }


    // Ejemplo de una JOptionPane que se cierrea pasado un tiempo (timer)
    public static void timer() {

        int TIME_VISIBLE = 6000;

        JOptionPane pane = new JOptionPane("Cargando todos los datos, paciencia...", JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Mensaje");

        pane.setVisible(true);
        dialog.setModal(false);
        dialog.setVisible(true);

        new Timer(TIME_VISIBLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                pane.setVisible(false);
            }
        }).start();


    }




}
