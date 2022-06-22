package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.io.Serializable;
import java.util.ArrayList;



public class VentanaGestionGlobalProveedoresPiezasProyectos {
    private JPanel PanelGestionGlobalProPieProy;
    private JButton bt_insertarGlobal;
    private JButton bt_modificarGlobal;
    private JButton bt_eliminarGlobal;
    private JButton bt_listadoGlobal;
    private JComboBox<Integer> comboBoxProveedorGlobal;
    private JComboBox<Integer> comboBoxPiezaGlobal;
    private JComboBox<Integer> comboBoxProyectoGlobal;
    private JTextField et_CantidadGlobal;
    private JTextField et_proveedorGlobalNombre;
    private JTextField et_PiezaGlobalNombre;
    private JTextField et_ProyectoGlobalNombre;
    private JLabel lb_TituloGlobal;
    private JLabel lb_ProveedorGlobal;
    private JLabel lb_PiezaGlobal;
    private JLabel lb_ProyectoGlobal;
    private JLabel lb_CantidadGlobal;
    private JTextField et_proveedorGlobalRespVentas;
    private JTextField et_proveedorGlobalDireccion;
    private JTextField et_PiezaGlobalDescripcion;
    private JTextField et_PiezaGlobalPrecio;
    private JTextField et_ProyectoGlobalCiudad;
    private JComboBox<Integer> comboBoxGestionesGlobal;
    private JScrollPane resultadoListadoGestiones;
    private JButton bt_refrescar;

    // Cargamos los ArrayList con los datos de la BD
    ArrayList<Proveedores> proveedores = CargaDatos.cargarProveedores();
    ArrayList<Piezas> piezas = CargaDatos.cargarPiezas();
    ArrayList<Proyectos> proyectos = CargaDatos.cargarProyectos();
    ArrayList<Gestion> gestiones = CargaDatos.cargarGestiones();

    // Declaramos tabla
    private JTable tabla;

    // Declaro las 'sesion0, como global para acceso de  todos los objetos de la ventana
    SessionFactory sesion = HibernateUtil.getSessionFactory();
    Session session = sesion.openSession();




    public VentanaGestionGlobalProveedoresPiezasProyectos() {



        // Cargo el listado y los combobox (con las id de las tablas , porque es de la única forma de que funciona)

        RefrescarTodo();


        comboBoxProveedorGlobal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comboBoxProveedorGlobal.getSelectedItem() != null) {

                    // Instancio un Proveedor y lo cargo según Hibernate (le paso el código del  proveedor (dentro del combo que es de tipo int)
                    datosProveedor();

                }
            }
        });


        comboBoxPiezaGlobal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comboBoxPiezaGlobal.getSelectedItem() != null) {

                    datosPieza();

                }

            }
        });

        comboBoxProyectoGlobal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comboBoxProyectoGlobal.getSelectedItem() != null) {

                    datosProyecto();
                }

            }
        });


        bt_listadoGlobal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Actualizo el ArrayList de gestiones con los datos de la BD
                gestiones = CargaDatos.cargarGestiones();

                tabla = new JTable();
                tabla.setModel(new ListaTablaGestionModel(gestiones));
                resultadoListadoGestiones.setViewportView(tabla);

            }
        });


        bt_insertarGlobal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comprobarCamposVaciosInsercion()) {

                    if (!gestionRepetida()) {

                        Piezas pieza = session.get(Piezas.class, (Serializable) comboBoxPiezaGlobal.getSelectedItem());
                        Proveedores prov = session.get(Proveedores.class, (Serializable) comboBoxProveedorGlobal.getSelectedItem());
                        Proyectos proyecto = session.get(Proyectos.class, (Serializable) comboBoxProyectoGlobal.getSelectedItem());
                        double precio = Double.parseDouble(et_CantidadGlobal.getText());

                        // Inserto la nueva gestión
                        CargaDatos.insercionGestion(pieza, prov, proyecto, precio);

                        // Actualizamos gestiones:
                        gestiones = CargaDatos.cargarGestiones();

                        // Refesco los ArrayList, los comboBox y el listado
                        RefrescarTodo();
                    }

                }

            }
        });


        bt_refrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                RefrescarTodo();
            }

        });


        bt_eliminarGlobal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TableModel m = tabla.getModel();
                int idGestionElegida = -1;

                if (tabla.getSelectedRow() != -1) {

                    idGestionElegida = (int) m.getValueAt(tabla.getSelectedRow(), 0);

                    // Borramos la gestion seleccionada del listado
                    CargaDatos.borrarGestion(idGestionElegida);

                    // Refesco los ArrayList, los comboBox y el listado
                    RefrescarTodo();

                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una gestión del listado !! ", "Error", JOptionPane.WARNING_MESSAGE);

                }

            }
        });
        bt_modificarGlobal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TableModel m = tabla.getModel();
                int idGestionElegida = -1;

                if (tabla.getSelectedRow() != -1) {

                    if (comprobarCamposVaciosInsercion()) {

                        idGestionElegida = (int) m.getValueAt(tabla.getSelectedRow(), 0);
                        double cantidad = Double.parseDouble(et_CantidadGlobal.getText());

                        // Modificamos la cantidad de la gestion seleccionada
                        CargaDatos.modificarCantidadGestion(idGestionElegida, cantidad);

                        // Refesco los ArrayList, los comboBox y el listado
                        RefrescarTodo();

                    }


                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una gestión del listado !! ", "Error", JOptionPane.WARNING_MESSAGE);
                }

            }
        });


    }


    private void RefrescarTodo() {

        // Actualizo los ArrayList con los nuevos datos desde la BD
        proveedores = CargaDatos.cargarProveedores();
        piezas = CargaDatos.cargarPiezas();
        proyectos = CargaDatos.cargarProyectos();
        gestiones = CargaDatos.cargarGestiones();

        // Borro los anteriores items
        comboBoxProveedorGlobal.removeAllItems();
        comboBoxPiezaGlobal.removeAllItems();
        comboBoxProyectoGlobal.removeAllItems();


        // Actualizo los items de los comboBox

        for (Proveedores proveedor : proveedores) {
            comboBoxProveedorGlobal.addItem(proveedor.getIdProveedor());
        }

        for (Piezas pieza : piezas) {
            comboBoxPiezaGlobal.addItem(pieza.getIdPieza());
        }

        for (Proyectos proyecto : proyectos) {
            comboBoxProyectoGlobal.addItem(proyecto.getIdProyecto());
        }

        // Actualizo los datos del provedor,pieza y proyecto
        datosProveedor();
        datosPieza();
        datosProyecto();


        // Refresco tambien el listado

        tabla = new JTable();
        tabla.setModel(new ListaTablaGestionModel(gestiones));
        resultadoListadoGestiones.setViewportView(tabla);

        // Vacío la casilla de cantidad
        et_CantidadGlobal.setText("");

    }

    private void datosProveedor() {
        // Instancio un Proveedor y lo cargo según Hibernate (le paso el código del  proveedor (dentro del combo que es de tipo int)
        Proveedores prov = session.get(Proveedores.class, (Serializable) comboBoxProveedorGlobal.getSelectedItem());

        if (prov != null) {
            et_proveedorGlobalNombre.setText(prov.getProveedor());
            et_proveedorGlobalRespVentas.setText(prov.getResponsableVentas());
            et_proveedorGlobalDireccion.setText(prov.getDirCp());

            et_proveedorGlobalNombre.setEditable(false);
            et_proveedorGlobalRespVentas.setEditable(false);
            et_proveedorGlobalDireccion.setEditable(false);

        }
    }

    private void datosPieza() {
        // Instancio una pieza y lo cargo según Hibernate (le paso el código de la  pieza (dentro del combo que es de tipo int)
        Piezas pieza = session.get(Piezas.class, (Serializable) comboBoxPiezaGlobal.getSelectedItem());

        if (pieza != null) {
            et_PiezaGlobalNombre.setText(pieza.getPieza());
            et_PiezaGlobalDescripcion.setText(pieza.getDescripcion());
            et_PiezaGlobalPrecio.setText(String.valueOf(pieza.getPrecio()));

            et_PiezaGlobalNombre.setEditable(false);
            et_PiezaGlobalDescripcion.setEditable(false);
            et_PiezaGlobalPrecio.setEditable(false);

        }
    }

    private void datosProyecto() {
        // Instancio un proyecto y lo cargo según Hibernate (le paso el código del proyecto (dentro del combo que es de tipo int)
        Proyectos proyecto = session.get(Proyectos.class, (Serializable) comboBoxProyectoGlobal.getSelectedItem());

        if (proyecto != null) {
            et_ProyectoGlobalNombre.setText(proyecto.getProyecto());
            et_ProyectoGlobalCiudad.setText(proyecto.getCiudad());

            et_ProyectoGlobalNombre.setEditable(false);
            et_ProyectoGlobalCiudad.setEditable(false);

        }
    }


    public JPanel getPanelGestionGlobalProPieProy() {
        return PanelGestionGlobalProPieProy;
    }

    private boolean gestionRepetida() {

        // Miramos si ya existe una gestion igual en la BD, tengo una restricción Unique (Proyectos, Proveedores y Piezas) en la BD
        boolean gestionRepetida = false;
        String errores = "";

        SessionFactory sesion = HibernateUtil.getSessionFactory();
        Session session = sesion.openSession();

        Proveedores prov = session.get(Proveedores.class, (Serializable) comboBoxProveedorGlobal.getSelectedItem());
        Proyectos proyecto = session.get(Proyectos.class, (Serializable) comboBoxProyectoGlobal.getSelectedItem());
        Piezas pieza = session.get(Piezas.class, (Serializable) comboBoxPiezaGlobal.getSelectedItem());


        for (Gestion gestion : gestiones) {

            if (gestion.getProveedorByIdProveedor().getIdProveedor() == prov.getIdProveedor() && gestion.getPiezaByIdPieza().getIdPieza() == pieza.getIdPieza() && gestion.getProyectoByIdProyecto().getIdProyecto() == proyecto.getIdProyecto()) {
                errores = "\t\t\tPROVEEDOR: " + prov.getIdProveedor() + " " + prov.getProveedor() + "\n" +
                        "\t\t\tPROYECTO:   " + proyecto.getIdProyecto() + " " + proyecto.getProyecto() + "\n" +
                        "\t\t\tPIEZA:           " + pieza.getIdPieza() + " " + pieza.getPieza();

                gestionRepetida = true;

                JOptionPane.showMessageDialog(null, "ERROR: Valores Duplicados:\n" + errores, "Error", JOptionPane.WARNING_MESSAGE);
            }
        }

        return gestionRepetida;

    }


    private boolean comprobarCamposVaciosInsercion() {

        boolean camposVacios = true;


        if (!numeroCorrecto(et_CantidadGlobal.getText())) {
            JOptionPane.showMessageDialog(null, "Cantidad incorrecta o campo vacío !! ", "Error", JOptionPane.WARNING_MESSAGE);
            et_CantidadGlobal.setText("");
            camposVacios = false;
        }

        System.out.println("Insertar cantidad: " + camposVacios);

        System.out.println((camposVacios) ? "Estan todos los campos vacios de insertar?: NO" : "Estan todos los campos vacios de insertar?: SI");


        return camposVacios;

    }


    private boolean numeroCorrecto(String numero) {

        boolean correcto = true;

        try {
            double comprobaNumero = Double.parseDouble(numero);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            System.out.println("Error !!, número incorrecto");
            correcto = false;
        }

        return correcto;
    }


}
