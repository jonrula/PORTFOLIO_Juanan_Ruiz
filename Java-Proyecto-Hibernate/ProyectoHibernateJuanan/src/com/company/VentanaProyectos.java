package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VentanaProyectos {
    private JTabbedPane tabbedPaneProyectos;
    private JPanel PanelGestionProyectos;
    private JButton bt_limpiarGestionProyecto;
    private JButton bt_InsertarProyecto;
    private JButton bt_ModificarProyecto;
    private JButton bt_EliminarProyecto;
    private JLabel lb_TituloProyectoAltasBjasMod;
    private JLabel lb_GestionCodigoProyecto;
    private JLabel lb_GestionNombreProyecto;
    private JLabel lb_GestionCiudadProyecto;
    private JTextField et_GestionCodigoProyecto;
    private JTextField et_GestionNombreProyecto;
    private JTextField et_GestionCiudadProyecto;
    private JPanel JPanel;
    private JLabel lb_TituloListaProyectos;
    private JLabel lb_ListadoCodigoProyecto;
    private JLabel lb_ListadoNombreProyecto;
    private JLabel lb_ListadoCiudadProyecto;
    private JTextField et_ListadoCodigoProyecto;
    private JTextField et_ListadoNombreProyecto;
    private JTextField et_ListadoCiudadProyecto;
    private JLabel lb_contadorListadoProyectos;
    private JButton bt_inicioProyectos;
    private JButton bt_AnteriorProyecto;
    private JButton bt_SiguienteProyecto;
    private JButton bt_finalProyectos;
    private JButton bt_BajaProyecto;
    private JButton bt_ejecutarConsultaProyecto;
    private JPanel PanelListadoProyectos;
    private JPanel PanelProyectos;
    private JButton bt_LimpiarConsultasProyectos;

    // Carga de los proyectos y gestiones  desde la BD

    ArrayList<Proyectos> proyectos = CargaDatos.cargarProyectos();
    ArrayList<Gestion> gestiones = CargaDatos.cargarGestiones();


    // Variable para saber la posición del ArrayList de las búsquedas
    ArrayList<Proyectos> consultaNombreProyecto = new ArrayList<>();
    ArrayList<Proyectos> consultaCiudadProyecto = new ArrayList<>();
    private int indice = 0;


    public VentanaProyectos() {

        // Los cuadros de texto de las consultas de id proveedor y apellidos no se pueden editar
        et_ListadoCodigoProyecto.setEditable(false);


        // Selecciono la pestaña en función de la opción del menú que haya elegido
        if (VentanaPrincipal.abrirPestanaConsultas) {

            tabbedPaneProyectos.setSelectedIndex(1);
            VentanaPrincipal.abrirPestanaConsultas = false;

        }


        // ************************************************************ PESTAÑA GESTION PROYECTOS *******************************************************************

        // Deteccion de teclas
        et_GestionCodigoProyecto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                // Le tengo que filtrar primero que coja algun dato de la caja de texto digerente a vacío, sino da error

                String teclas = et_GestionCodigoProyecto.getText();


                if (!et_GestionCodigoProyecto.getText().trim().equalsIgnoreCase("")) {
                    if (et_GestionCodigoProyecto.getText().trim().equalsIgnoreCase(et_GestionCodigoProyecto.getText()) && numeroCorrecto(teclas.trim())) {

                        SessionFactory sesion = HibernateUtil.getSessionFactory();
                        Session session = sesion.openSession();

                        // Instancio un proveedor y lo cargo según Hibernate (le paso el código del proveedor parseado, es un int)
                        Proyectos proyecto = new Proyectos();
                        proyecto = session.get(Proyectos.class, Integer.parseInt(et_GestionCodigoProyecto.getText()));

                        if (proyecto != null) {
                            et_GestionNombreProyecto.setText(proyecto.getProyecto());
                            et_GestionCiudadProyecto.setText(proyecto.getCiudad());

                        }
                    }
                    if (!numeroCorrecto(teclas.trim())) {
                        JOptionPane.showMessageDialog(null, "Código incorrecto !!", "Error", JOptionPane.WARNING_MESSAGE);
                        et_GestionCodigoProyecto.setText("");

                    }

                } else {
                    limpiarCamposGestionProyectos();
                }

            }
        });

        bt_InsertarProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comprobarCamposVaciosInsercion()) {

                    // Miro si se repite el proveedor
                    if (!proyectoRepetido()) {

                        // Hacemos la inserción
                        CargaDatos.insercionProyecto(et_GestionNombreProyecto.getText().trim().toUpperCase(), et_GestionCiudadProyecto.getText().trim().toUpperCase());

                        // Actualizamos el ArrayList de 'proveedores' con los nuevos datos actualizados desde la base de datos
                        proyectos = CargaDatos.cargarProyectos();

                        limpiarCamposGestionProyectos();

                    } else {
                        JOptionPane.showMessageDialog(null, "El proyecto " + et_GestionNombreProyecto.getText().toUpperCase() + " ya existe", "Error", JOptionPane.WARNING_MESSAGE);

                    }

                    limpiarCamposGestionProyectos();

                }

            }
        });


        bt_ModificarProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comprobarCamposVacios()) {

                    int idProyectoModificar = Integer.parseInt(et_GestionCodigoProyecto.getText().trim());

                    if (comprobarExisteProyecto()) {

                        // Modificamos los datos de la BD
                        CargaDatos.modificarProyecto(idProyectoModificar, et_GestionNombreProyecto.getText().trim(), et_GestionCiudadProyecto.getText().trim());

                        // Actualizamos el ArrayList de 'proveedores' con los nuevos datos actualizados desde la base de datos
                        proyectos = CargaDatos.cargarProyectos();


                    } else {
                        JOptionPane.showMessageDialog(null, "No existe ningún proyecto  con el código " + et_GestionCodigoProyecto.getText(), "Error", JOptionPane.WARNING_MESSAGE);
                    }

                    limpiarCamposGestionProyectos();

                }

            }
        });


        bt_EliminarProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (numeroCorrecto(et_GestionCodigoProyecto.getText().trim())) {

                    int idProyectoBorrar = Integer.parseInt(et_GestionCodigoProyecto.getText().trim());

                    if (comprobarExisteProyecto()) {

                        if (!comprobarProyectosGestion(et_GestionCodigoProyecto.getText().trim())) {

                            // Borramos proveedor
                            CargaDatos.borrarProyecto(idProyectoBorrar);

                            // Actualizamos el ArrayList de 'proyectos' con los nuevos datos actualizados desde la base de datos
                            proyectos = CargaDatos.cargarProyectos();

/*                            VentanaGestionGlobalProveedoresPiezasProyectos v = new VentanaGestionGlobalProveedoresPiezasProyectos();

                            // Actualizo combobos de Gestion
                            v.getComboBoxProyectoGlobal().removeAllItems();
                            for (Proyectos proyecto : proyectos) {
                                v.getComboBoxProyectoGlobal().addItem(proyecto.getIdProyecto());
                            }*/


                        }


                    } else {
                        JOptionPane.showMessageDialog(null, "No existe el proyecto con el código " + et_GestionCodigoProyecto.getText(), "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Introduce un código correcto !!", "Error", JOptionPane.WARNING_MESSAGE);
                }

                limpiarCamposGestionProyectos();


            }
        });


        bt_limpiarGestionProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposGestionProyectos();
            }
        });


        // ******************************************************************** PESTAÑA CONSULTA PROYECTOS ******************************************************************************


        bt_ejecutarConsultaProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                indice = 0;


                if (!et_ListadoNombreProyecto.getText().equalsIgnoreCase("") && et_ListadoCiudadProyecto.getText().equalsIgnoreCase("")) {

                    consultaNombreProyecto = CargaDatos.consultaNombreProyecto(et_ListadoNombreProyecto.getText().trim());

                    // Resultado de los datos encontrados en la consulta
                    lb_contadorListadoProyectos.setText("Resultados consulta: " + consultaNombreProyecto.size());

                    resultadoConsultaNombre();


                } else if (!et_ListadoCiudadProyecto.getText().equalsIgnoreCase("") && et_ListadoNombreProyecto.getText().equalsIgnoreCase("")) {

                    consultaCiudadProyecto = CargaDatos.consultaCiudadProyecto(et_ListadoCiudadProyecto.getText().trim());

                    // Resultado de los datos encontrados en la consulta
                    lb_contadorListadoProyectos.setText("Resultados consulta: " + consultaNombreProyecto.size());

                    resultadoConsultaCiudad();


                } else {
                    JOptionPane.showMessageDialog(null, "Introduce solo  un dato en nombre o ciudad !!", "Error", JOptionPane.WARNING_MESSAGE);
                   limpiarConsultas();

                }


            }
        });


        bt_SiguienteProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (consultaNombreProyecto.size() > 0 && indice < consultaNombreProyecto.size() - 1) {

                    indice++;

                    resultadoConsultaNombre();

                } else if (consultaCiudadProyecto.size() > 0 && indice < consultaCiudadProyecto.size() - 1) {

                    indice++;

                    resultadoConsultaCiudad();

                }

            }
        });


        bt_AnteriorProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (consultaNombreProyecto.size() > 0 &&   indice > 0) {
                    indice--;

                    resultadoConsultaNombre();
                }
                else if (consultaCiudadProyecto.size() > 0 &&   indice > 0) {
                    indice--;

                    resultadoConsultaCiudad();
                }

            }
        });


        bt_finalProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (consultaNombreProyecto.size() > 0) {
                    indice = consultaNombreProyecto.size() - 1;
                    resultadoConsultaNombre();
                } else if (consultaCiudadProyecto.size() > 0) {
                    indice = consultaCiudadProyecto.size() - 1;
                    resultadoConsultaCiudad();
                }


            }
        });


        bt_inicioProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (consultaNombreProyecto.size() > 0) {
                    indice =0;
                    resultadoConsultaNombre();
                } else if (consultaCiudadProyecto.size() > 0) {
                    indice =0;
                    resultadoConsultaCiudad();
                }




            }
        });

        bt_LimpiarConsultasProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                limpiarConsultas();

            }
        });

        bt_BajaProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!et_ListadoCodigoProyecto.getText().equalsIgnoreCase("")) {
                    int idProyectoBorrar = Integer.parseInt(et_ListadoCodigoProyecto.getText().trim());

                    if (!comprobarProyectosGestion(et_ListadoCodigoProyecto.getText().trim())) {

                        // Borramos proveedor
                        CargaDatos.borrarProyecto(idProyectoBorrar);

                        // Actualizamos el ArrayList de 'proveedores' con los nuevos datos actualizados desde la base de datos
                        proyectos = CargaDatos.cargarProyectos();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No hay ningún código seleccionado !!", "Error", JOptionPane.WARNING_MESSAGE);

                }

            }
        });


    }


    public void limpiarConsultas() {
        et_ListadoCodigoProyecto.setText("");
        et_ListadoNombreProyecto.setText("");
        et_ListadoCiudadProyecto.setText("");

        // Resultado de los datos encontrados en la consulta
        lb_contadorListadoProyectos.setText("Resultados consulta: 0");
    }


    public void resultadoConsultaNombre() {

        if (consultaNombreProyecto.size() > 0) {
            for (int i = 0; i < consultaNombreProyecto.size(); i++) {
                et_ListadoCodigoProyecto.setText(String.valueOf(consultaNombreProyecto.get(indice).getIdProyecto()));
                et_ListadoNombreProyecto.setText(consultaNombreProyecto.get(indice).getProyecto());
                et_ListadoCiudadProyecto.setText(consultaNombreProyecto.get(indice).getCiudad());
            }

            // Resultado de los datos encontrados en la consulta
            lb_contadorListadoProyectos.setText(indice + 1 + " de " + consultaNombreProyecto.size());


        } else {
            JOptionPane.showMessageDialog(null, "No hay resultados !!", "Error", JOptionPane.WARNING_MESSAGE);
            limpiarConsultas();
        }

    }


    public void resultadoConsultaCiudad() {

        if (consultaCiudadProyecto.size() > 0) {
            for (int i = 0; i < consultaCiudadProyecto.size(); i++) {
                et_ListadoCodigoProyecto.setText(String.valueOf(consultaCiudadProyecto.get(indice).getIdProyecto()));
                et_ListadoNombreProyecto.setText(consultaCiudadProyecto.get(indice).getProyecto());
                et_ListadoCiudadProyecto.setText(consultaCiudadProyecto.get(indice).getCiudad());

            }

            // Resultado de los datos encontrados en la consulta
            lb_contadorListadoProyectos.setText(indice + 1 + " de " + consultaCiudadProyecto.size());


        } else {
            JOptionPane.showMessageDialog(null, "No hay resultados !!", "Error", JOptionPane.WARNING_MESSAGE);
            limpiarConsultas();
        }

    }


    public boolean comprobarProyectosGestion(String proyecto) {

        boolean comprobarRefProyectos = false;

        int idProyectoBorrar = Integer.parseInt(proyecto.trim());
        String nombreProyecto = "";


        // Compruebo el proyecto (le paso el ID por la caja de texto en la ventana)
        // Si coincide el ID del proyecto con el que está en la tabla de Gestión, es que está en un proyecto
        for (Gestion gestion : gestiones) {

            if (gestion.getProyectoByIdProyecto().getIdProyecto() == idProyectoBorrar) {
                comprobarRefProyectos = true;

                // Cojo el nombre del proyecto
                nombreProyecto = gestion.getProyectoByIdProyecto().getProyecto();

            }
        }


        if (comprobarRefProyectos) {

            // Saco los proyectos por consola
            System.out.println("No puedes borrar el proyecto " + nombreProyecto.toUpperCase());

            JOptionPane.showMessageDialog(null, "No puedes borrar el proyecto " + nombreProyecto.toUpperCase() + " porque esta en GESTION ", "Error", JOptionPane.WARNING_MESSAGE);

        } else {
            System.out.println("Puedes borrar a " + et_GestionNombreProyecto.getText().toUpperCase());
        }

        return comprobarRefProyectos;


    }

    public boolean comprobarExisteProyecto() {
        // Miramos si ya existe un proyecto a modificar en la BD
        boolean existeProyecto = false;

        int idProyectoModificar = Integer.parseInt(et_GestionCodigoProyecto.getText().trim());

        for (Proyectos proyecto : proyectos) {
            if (proyecto.getIdProyecto() == idProyectoModificar) {
                existeProyecto = true;
            }
        }

        return existeProyecto;
    }

    public boolean comprobarCamposVacios() {

        boolean camposVacios = true;

        String errores = "";

        if (!numeroCorrecto(et_GestionCodigoProyecto.getText().trim()) ) {
            errores += "CÓDIGO: Necesitas un código correcto para la modificación de datos\n";
        }
        if (et_GestionNombreProyecto.getText().trim().equalsIgnoreCase("") || !comprobarProyectoCiudad(et_GestionNombreProyecto.getText().trim())) {
            errores += "NOMBRE: Está vacío o fuera del rango de carácteres (3-25)\n";
        }
        if (et_GestionCiudadProyecto.getText().trim().equalsIgnoreCase("") || !comprobarProyectoCiudad(et_GestionCiudadProyecto.getText().trim())) {
            errores += "CIUDAD: Está vacío o fuera del rango de carácteres (3-25)\n";
        }

        if (!errores.trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Rellena los siguientes campos:\n" + errores, "Error", JOptionPane.WARNING_MESSAGE);
            //limpiarCamposGestionProyectos();
            camposVacios = false;
        }

        System.out.println("Modificar proyecto: " + camposVacios);
        System.out.println((camposVacios) ? "Estan todos los campos vacios de modificar ?: NO" : "Están todos los campos vacíos de modificar ?: SI");
        return camposVacios;


    }


    private boolean proyectoRepetido() {
        // Miramos si ya existe un proyecto igual en la BD, miro el nombre y la ciudad
        boolean proyectoRepetido = false;

        for (Proyectos proyecto : proyectos) {
            if (proyecto.getProyecto().equalsIgnoreCase(et_GestionNombreProyecto.getText().trim()) && proyecto.getCiudad().equalsIgnoreCase(et_GestionCiudadProyecto.getText().trim())) {

                proyectoRepetido = true;

            }
        }
        return proyectoRepetido;
    }

    public boolean comprobarCamposVaciosInsercion() {

        boolean camposVacios = true;

        String errores = "";

        if (!et_GestionCodigoProyecto.getText().trim().equalsIgnoreCase("")) {
            errores += "CÓDIGO: No es necesario el código para la inserción\n";
            et_GestionCodigoProyecto.setText("");
        }

        if (et_GestionNombreProyecto.getText().trim().equalsIgnoreCase("")  || !comprobarProyectoCiudad(et_GestionNombreProyecto.getText().trim())) {
            errores += "NOMBRE: Está vacío o fuera del rango de carácteres (3-25)\n";
        }

        if (et_GestionCiudadProyecto.getText().trim().equalsIgnoreCase("")  || !comprobarProyectoCiudad(et_GestionCiudadProyecto.getText().trim())) {
            errores += "CIUDAD: Está vacío o fuera del rango de carácteres (3-25)\n";
        }
        if (!errores.trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Rellena los siguientes campos:\n" + errores, "Error", JOptionPane.WARNING_MESSAGE);
            //limpiarCamposGestionProyectos();
            camposVacios = false;
        }

        System.out.println("Insertar proyecto: " + camposVacios);

        System.out.println((camposVacios) ? "Estan todos los campos vacios de insertar?: NO" : "Estan todos los campos vacios de insertar?: SI");


        return camposVacios;

    }


    public static boolean comprobarProyectoCiudad(String texto) {


        boolean correcto = false;
        // Comprueba cadenas de texto con  letras \w  números \d  y espacios entre palabras \s ( no entran ni tíldes ni símbolos) entre 3 y 25 carácteres
        //String regexp = "^(?=.{3,25}$)[\w\d\s]+( [\w\d\s]+)*$";

        // Comprueba cadenas de texto con espacios entre palabras, tildes entre 3 y 25 carácteres en total
        String regexp= "^(?=.{3,25}$)[a-zA-ZáéíóúüñÁÉÍÓÚÑ'-]+( [a-zA-ZáéíóúüñÁÉÍÓÚÑ' +-]+)*$";

        if (Pattern.matches(regexp, texto)) {
            correcto = true;
        } else {
            System.out.println("Introduce un formato correcto de nombre o apellido entre 3 y 25 carácteres !!");
        }

        return correcto;
    }





    public void limpiarCamposGestionProyectos() {
        et_GestionCodigoProyecto.setText("");
        et_GestionNombreProyecto.setText("");
        et_GestionCiudadProyecto.setText("");
    }

    public boolean numeroCorrecto(String numero) {

        boolean correcto = true;

        try {
            int comprobaNumero = Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            System.out.println("Error !!, número incorrecto");
            correcto = false;
        }

        return correcto;
    }


    public javax.swing.JPanel getPanelProyectos() {
        return PanelProyectos;
    }
}
