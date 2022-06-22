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
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VentanaPiezas {
    private JPanel PanelPiezas;
    private JTabbedPane tabbedPanePiezas;
    private JPanel PanelGestionPiezas;
    private JButton bt_limpiarPieza;
    private JButton bt_InsertarPieza;
    private JButton bt_ModificarPieza;
    private JButton bt_EliminarPieza;
    private JLabel lb_TituloPiezasAltasBjasMod;
    private JLabel lb_GestionCodigoPieza;
    private JLabel lb_GestionNombrePieza;
    private JLabel lb_GestionDescripcionPieza;
    private JTextField et_GestionCodigoPieza;
    private JTextField et_GestionNombrePieza;
    private JTextField et_GestionDescripcionPieza;
    private JTextField et_GestionPrecioPieza;
    private JPanel JPanel;
    private JLabel lb_TituloListaPiezas;
    private JLabel lb_ListadoCodigoPieza;
    private JLabel lb_ListadoNombrePieza;
    private JLabel lb_ListadoDescripciónPieza;
    private JLabel lb_ListadoPrecioPieza;
    private JTextField et_ListadoCodigoPieza;
    private JTextField et_ListadoNombrePieza;
    private JTextField et_ListadoDescripcionPieza;
    private JTextField et_ListadoPrecioPieza;
    private JLabel lb_contadorListadoPiezas;
    private JButton bt_inicioPiezas;
    private JButton bt_AnteriorPiezas;
    private JButton bt_SiguientePiezas;
    private JButton bt_finalPiezas;
    private JButton bt_BajaPiezas;
    private JButton bt_ejecutarConsultaPieza;
    private JPanel PanelListadoPiezas;
    private JButton bt_limpiarConsultasPiezas;
    private JLabel lb_GestionPrecioPieza;


    // Carga de las piezas y gestiones  desde la BD

    ArrayList<Piezas> piezas = CargaDatos.cargarPiezas();
    ArrayList<Gestion> gestiones = CargaDatos.cargarGestiones();


    // Variable para saber la posición del ArrayList de las búsquedas
    ArrayList<Piezas> consultaNombrePieza = new ArrayList<>();
    private int indice = 0;


    public VentanaPiezas() {

        //Añadir en la DataBase properties si da problemas en la conexión a MySql-->  jdbc:mysql://localhost:3306/Hibernate?autoReconnect=true&useSSL=false


        // Los cuadros de texto de las consultas de id pieza, descripcion y precio no se pueden editar
        et_ListadoCodigoPieza.setEditable(false);
        et_ListadoPrecioPieza.setEditable(false);
        et_ListadoDescripcionPieza.setEditable(false);


        // Selecciono la pestaña en función de la opción del menú que haya elegido
        if (VentanaPrincipal.abrirPestanaConsultas) {

            tabbedPanePiezas.setSelectedIndex(1);
            VentanaPrincipal.abrirPestanaConsultas = false;

        }


        // ************************************************************************ PESTAÑA GESTION PIEZAS *******************************************************************


        // Deteccion de teclas
        et_GestionCodigoPieza.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                // Le tengo que filtrar primero que coja algun dato de la caja de texto digerente a vacío, sino da error

                String teclas = et_GestionCodigoPieza.getText();


                if (!et_GestionCodigoPieza.getText().trim().equalsIgnoreCase("")) {
                    if (et_GestionCodigoPieza.getText().trim().equalsIgnoreCase(et_GestionCodigoPieza.getText()) && numeroCorrecto(teclas.trim())) {

                        SessionFactory sesion = HibernateUtil.getSessionFactory();
                        Session session = sesion.openSession();

                        // Instancio un proveedor y lo cargo según Hibernate (le paso el código del proveedor parseado, es un int)
                        Piezas pieza = new Piezas();
                        pieza = session.get(Piezas.class, Integer.parseInt(et_GestionCodigoPieza.getText()));

                        if (pieza != null) {
                            et_GestionCodigoPieza.setText(String.valueOf(pieza.getIdPieza()));
                            et_GestionNombrePieza.setText(pieza.getPieza());
                            et_GestionDescripcionPieza.setText(pieza.getDescripcion());
                            et_GestionPrecioPieza.setText(String.valueOf(pieza.getPrecio()));
                        }
                    }
                    if (!numeroCorrecto(teclas.trim())) {
                        JOptionPane.showMessageDialog(null, "Código incorrecto !!", "Error", JOptionPane.WARNING_MESSAGE);
                        et_GestionCodigoPieza.setText("");

                    }



                } else {
                    limpiarCamposGestionPiezas();
                }

            }
        });


        bt_limpiarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposGestionPiezas();

            }
        });


        bt_InsertarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comprobarCamposVaciosInsercion()) {


                    // Lo contrario con lo que he inicializado 'proveedorRepe'
                    if (!piezaRepetida()) {

                        // Hacemos la inserción
                        CargaDatos.insercionPieza(et_GestionNombrePieza.getText().trim().toUpperCase(), et_GestionDescripcionPieza.getText().trim().toUpperCase(), et_GestionPrecioPieza.getText().trim());

                        // Actualizamos el ArrayList de 'proveedores' con los nuevos datos actualizados desde la base de datos
                        piezas = CargaDatos.cargarPiezas();


                    } else {
                        JOptionPane.showMessageDialog(null, "La pieza " + et_GestionNombrePieza.getText().toUpperCase() + "  ya existe", "Error", JOptionPane.WARNING_MESSAGE);
                    }

                    limpiarCamposGestionPiezas();

                }

            }
        });


        bt_ModificarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comprobarCamposVacios()) {

                    int idPiezaModificar = Integer.parseInt(et_GestionCodigoPieza.getText().trim());

                    if (comprobarExistePieza()) {

                        // Modificamos los datos de la BD
                        CargaDatos.modificarPieza(idPiezaModificar, et_GestionNombrePieza.getText().trim(), et_GestionDescripcionPieza.getText().trim(), et_GestionPrecioPieza.getText().trim());

                        // Actualizamos el ArrayList de 'proveedores' con los nuevos datos actualizados desde la base de datos
                        piezas = CargaDatos.cargarPiezas();


                    } else {
                        JOptionPane.showMessageDialog(null, "No existe ninguna pieza con el código " + et_GestionCodigoPieza.getText(), "Error", JOptionPane.WARNING_MESSAGE);
                    }

                    limpiarCamposGestionPiezas();

                }


            }
        });


        bt_EliminarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (numeroCorrecto(et_GestionCodigoPieza.getText().trim())) {

                    int idPiezaBorrar = Integer.parseInt(et_GestionCodigoPieza.getText().trim());

                    if (comprobarExistePieza()) {

                        if (!comprobarPiezasProyectos(et_GestionCodigoPieza.getText().trim())) {

                            // Borramos pieza
                            CargaDatos.borrarPieza(idPiezaBorrar);

                            // Actualizamos el ArrayList de 'piezas' con los nuevos datos actualizados desde la base de datos
                            piezas = CargaDatos.cargarPiezas();


                        }


                    } else {
                        JOptionPane.showMessageDialog(null, "No existe la pieza con el código " + et_GestionCodigoPieza.getText(), "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Introduce un código correcto !!", "Error", JOptionPane.WARNING_MESSAGE);
                }

                limpiarCamposGestionPiezas();

            }
        });


        // *********************************************************************** PESTAÑA CONSULTA PIEZAS *****************************************************************************


        bt_ejecutarConsultaPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (!et_ListadoNombrePieza.getText().equalsIgnoreCase("")) {

                    consultaNombrePieza = CargaDatos.consultaNombrePieza(et_ListadoNombrePieza.getText().trim());

                    // Resultado de los datos encontrados en la consulta
                    lb_contadorListadoPiezas.setText("Resultados consulta: " + consultaNombrePieza.size());

                    resultadoConsulta();

                } else {
                    JOptionPane.showMessageDialog(null, "Introduce solo  un dato en nombre !!", "Error", JOptionPane.WARNING_MESSAGE);
                    et_ListadoNombrePieza.setText("");
                }
            }
        });


        bt_SiguientePiezas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (indice < consultaNombrePieza.size() - 1) {

                    indice++;

                    resultadoConsulta();

                }


            }
        });


        bt_AnteriorPiezas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (indice > 0) {
                    indice--;

                    resultadoConsulta();
                }

            }
        });


        bt_finalPiezas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (consultaNombrePieza.size() > 0) {
                    indice = consultaNombrePieza.size() - 1;
                }


                resultadoConsulta();

            }
        });


        bt_inicioPiezas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                indice = 0;
                resultadoConsulta();


            }
        });
        bt_limpiarConsultasPiezas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                limpiarConsultas();

            }
        });
        bt_BajaPiezas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!et_ListadoCodigoPieza.getText().equalsIgnoreCase("")) {
                    int idPiezaBorrar = Integer.parseInt(et_ListadoCodigoPieza.getText().trim());

                    if (!comprobarPiezasProyectos(et_ListadoCodigoPieza.getText().trim())) {

                        // Borramos proveedor
                        CargaDatos.borrarPieza(idPiezaBorrar);

                        // Actualizamos el ArrayList de 'piezas' con los nuevos datos actualizados desde la base de datos
                        piezas = CargaDatos.cargarPiezas();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No hay ningún código seleccionado !!", "Error", JOptionPane.WARNING_MESSAGE);

                }

            }
        });


    }

    private boolean piezaRepetida() {
        // Miramos si ya existe una pieza igual en la BD, solo voy a comparar el nombre
        boolean piezaRepetida = false;

        for (Piezas pieza : piezas) {
            if (pieza.getPieza().equalsIgnoreCase(et_GestionNombrePieza.getText())) {

                piezaRepetida = true;

            }
        }
        return piezaRepetida;
    }


    public void limpiarConsultas() {
        et_ListadoCodigoPieza.setText("");
        et_ListadoNombrePieza.setText("");
        et_ListadoDescripcionPieza.setText("");
        et_ListadoPrecioPieza.setText("");

        // Resultado de los datos encontrados en la consulta
        lb_contadorListadoPiezas.setText("Resultados consulta: 0");
    }


    public void resultadoConsulta() {

        if (consultaNombrePieza.size() > 0) {
            for (int i = 0; i < consultaNombrePieza.size(); i++) {
                et_ListadoCodigoPieza.setText(String.valueOf(consultaNombrePieza.get(indice).getIdPieza()));
                et_ListadoNombrePieza.setText(consultaNombrePieza.get(indice).getPieza());
                et_ListadoDescripcionPieza.setText(consultaNombrePieza.get(indice).getDescripcion());
                et_ListadoPrecioPieza.setText(String.valueOf(consultaNombrePieza.get(indice).getPrecio()));
            }

            // Resultado de los datos encontrados en la consulta
            lb_contadorListadoPiezas.setText(indice + 1 + " de " + consultaNombrePieza.size());


        } else {
            JOptionPane.showMessageDialog(null, "No hay resultados !!", "Error", JOptionPane.WARNING_MESSAGE);
            limpiarConsultas();
        }

    }


    public boolean comprobarExistePieza() {
        // Miramos si ya existe la pieza a modificar en la BD
        boolean existePieza = false;

        int idPiezaModificar = Integer.parseInt(et_GestionCodigoPieza.getText().trim());

        for (Piezas pieza : piezas) {
            if (pieza.getIdPieza() == idPiezaModificar) {
                existePieza = true;
            }
        }

        return existePieza;
    }

    public boolean comprobarPiezasProyectos(String pieza) {

        boolean comprobarRefPiezas = false;
        String proyectoRef = "";
        String piezaRef = "";

        int idPiezaBorrar = Integer.parseInt(pieza.trim());
        String nombreProveedor = nombrePieza(idPiezaBorrar);

        // Voy guardando en una lista los proyectos en los que están las  piezas
        List<String> totalProyectos = new ArrayList<>();

        // Compruebo los piezas (le paso el ID por la caja de texto en la ventana)
        // Si coincide el ID de la pieza  con el que está en la tabla de Gestión, es que está en un proyecto
        for (Gestion gestion : gestiones) {

            if (gestion.getPiezaByIdPieza().getIdPieza() == idPiezaBorrar) {
                comprobarRefPiezas = true;

                // Cojo el nombre de la pieza
                piezaRef = gestion.getPiezaByIdPieza().getPieza().toUpperCase();

                // Voy pasando a la List los proyectos del proveedor
                totalProyectos.add((gestion.getProyectoByIdProyecto().getProyecto()));

            }
        }

        // Actualizo la lista quitando los valores duplicados , para que no se repitan los proyectos
        totalProyectos = totalProyectos.stream().distinct().collect(Collectors.toList());


        if (comprobarRefPiezas) {

            // Saco los proyectos por consola
            for (String totalproyecto : totalProyectos) {
                proyectoRef += totalproyecto.toUpperCase() + "\n";

            }

            System.out.println("No puedes borrar la pieza " + piezaRef + " porque esta en los siguientes proyectos:");
            System.out.println(proyectoRef.toUpperCase());

            JOptionPane.showMessageDialog(null, "No puedes borrar la pieza " + piezaRef.toUpperCase() + " porque esta en los siguientes proyectos:\n" + proyectoRef.toUpperCase(), "Error", JOptionPane.WARNING_MESSAGE);

        } else {
            System.out.println("Puedes borrar a " + nombreProveedor.toUpperCase());
        }

        return comprobarRefPiezas;


    }

    public String nombrePieza(int idPiezaBorrar) {
        String nombrePieza = "";
        for (Piezas pieza : piezas) {
            if (pieza.getIdPieza() == idPiezaBorrar) {
                nombrePieza = pieza.getPieza();
            }
        }

        return nombrePieza;
    }

    public boolean comprobarCamposVacios() {

        boolean camposVacios = true;

        String errores = "";

        if (!numeroCorrecto(et_GestionCodigoPieza.getText().trim())) {
            errores += "CÓDIGO:  Necesitas un código correcto para la modificación de datos\n";
        }

        if (et_GestionNombrePieza.getText().trim().equalsIgnoreCase("") || !comprobarDatosString(et_GestionNombrePieza.getText().trim())) {
            errores += "NOMBRE: Está vacío o fuera del rango de carácteres (3-25)\n";
        }
        if (et_GestionDescripcionPieza.getText().trim().equalsIgnoreCase("") || !comprobarDatosString(et_GestionDescripcionPieza.getText().trim())) {
            errores += "DESCRIPCIÓN: Está vacío o fuera del rango de carácteres (3-25)\n";
        }
        if (et_GestionPrecioPieza.getText().trim().equalsIgnoreCase("") || !precioCorrecto(et_GestionPrecioPieza.getText().trim())) {
            errores += "PRECIO: Está vacío y/o precio incorrecto !!\n";
        }
        if (!errores.trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Rellena los siguientes campos:\n" + errores, "Error", JOptionPane.WARNING_MESSAGE);
            //limpiarCamposGestionPiezas();
            camposVacios = false;
        }

        System.out.println("Modificar pieza: " + camposVacios);
        System.out.println((camposVacios) ? "Estan todos los campos vacios de modificar?: NO" : "Estan todos los campos vacios de modificar?: SI");
        return camposVacios;


    }

    public boolean comprobarCamposVaciosInsercion() {

        boolean camposVacios = true;

        String errores = "";
        if (!et_GestionCodigoPieza.getText().trim().equalsIgnoreCase("")) {
            errores += "CÓDIGO: No es necesario el código para la inserción\n";
            et_GestionCodigoPieza.setText("");
        }

        if (et_GestionNombrePieza.getText().trim().equalsIgnoreCase("") || !comprobarDatosString(et_GestionNombrePieza.getText().trim())) {
            errores += "NOMBRE: Está vacío o fuera del rango de carácteres (3-25)\n";
        }
        if (et_GestionDescripcionPieza.getText().trim().equalsIgnoreCase("") || !comprobarDatosString(et_GestionDescripcionPieza.getText().trim())) {
            errores += "DESCRIPCIÓN: Está vacío o fuera del rango de carácteres (3-25)\n";
        }
        if (et_GestionPrecioPieza.getText().trim().equalsIgnoreCase("") || !precioCorrecto(et_GestionPrecioPieza.getText().trim())) {
            errores += "PRECIO: Está vacío y/o precio incorrecto !!\n";
        }
        if (!errores.trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Rellena los siguientes campos:\n" + errores, "Error", JOptionPane.WARNING_MESSAGE);
            //limpiarCamposGestionPiezas();
            camposVacios = false;
        }

        System.out.println("Insertar pieza: " + camposVacios);

        System.out.println((camposVacios) ? "Estan todos los campos vacios de insertar?: NO" : "Estan todos los campos vacios de insertar?: SI");


        return camposVacios;

    }

    public void limpiarCamposGestionPiezas() {
        et_GestionCodigoPieza.setText("");
        et_GestionNombrePieza.setText("");
        et_GestionDescripcionPieza.setText("");
        et_GestionPrecioPieza.setText("");
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

    public boolean precioCorrecto(String numero) {

        boolean correcto = true;

        try {
            Double comprobaNumero = Double.parseDouble(numero);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            System.out.println("Error !!, número incorrecto");
            correcto = false;
        }

        return correcto;
    }

    public static boolean comprobarDatosString(String texto) {


        boolean correcto = false;
        // Comprueba cadenas de texto con  letras \w  números \d  y espacios entre palabras \s ( no entran ni tíldes ni símbolos) entre 3 y 25 carácteres
        //String regexp = "^(?=.{3,25}$)[\w\d\s]+( [\w\d\s]+)*$";

        // Comprueba cadenas de texto con espacios entre palabras, tildes y símbolos entre 3 y 25 carácteres en total
        String regexp = "^(?=.{3,25}$)[a-zA-Z0-9_áéíóúüñÁÉÍÓÚÑºª@|$€%&()=,.+*;¨':-]+( [a-zA-Z0-9_áéíóúüñÁÉÍÓÚÑºª@|$€%&()=,.+*;¨': +-]+)*$";

        if (Pattern.matches(regexp, texto)) {
            correcto = true;
        } else {
            System.out.println("Introduce un formato correcto de nombre o apellido entre 3 y 20 carácteres !!");
        }

        return correcto;
    }


    public javax.swing.JPanel getPanelPiezas() {
        return PanelPiezas;
    }
}