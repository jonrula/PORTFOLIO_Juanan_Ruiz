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

public class VentanaProveedores {

    private JTabbedPane tabbedPaneProveedores;
    private JPanel PanelProveedores;
    private JPanel Jpanel;
    private JLabel lb_ListadoCodigoProveedor;
    private JLabel lb_ListadoNombreProveedor;
    private JLabel lb_ListadoApellidoProveedor;
    private JLabel lb_ListadoDireccionProveedor;
    private JTextField et_ListadoCodigoProveedor;
    private JTextField et_ListadoNombreProveedor;
    private JTextField et_ListadoApellidosProveedor;
    private JTextField et_ListadoDireccionProveedor;
    private JLabel lb_contadorListadoProveedores;
    private JButton bt_BajaProveedores;
    private JButton bt_ejecutarConsultaProveedores;
    private JButton bt_inicioProveedores;
    private JButton bt_AnteriorProveedores;
    private JButton bt_SiguienteProveedores;
    private JButton bt_finalProveedores;
    private JButton bt_limpiarProveedores;
    private JButton bt_InsertarProveedores;
    private JButton bt_ModificarProveedores;
    private JButton bt_EliminarProveedores;
    private JLabel lb_TituloProveedoresAltasBjasMod;
    private JLabel lb_GestionCodigoProveedor;
    private JLabel lb_GestionNombreProveedor;
    private JLabel lb_GestionApellidoProveedor;
    private JLabel lb_GestionDireccionProveedor;
    private JPanel PanelGestionProveedores;
    private JTextField et_GestionCodigoProveedor;
    private JTextField et_GestionNombreProveedor;
    private JTextField et_GestionApellidosProveedor;
    private JTextField et_GestionDireccionProveedor;
    private JLabel lb_TituloListaProveedores;
    private JPanel PanelListadoProveedores;
    private JButton bt_limpiarConsultasProveedor;


    // Carga de los proveedores y gestiones  desde la BD

    ArrayList<Proveedores> proveedores = CargaDatos.cargarProveedores();
    ArrayList<Gestion> gestiones = CargaDatos.cargarGestiones();


    // Variable para saber la posición del ArrayList de las búsquedas
    ArrayList<Proveedores> consultaNombreProveedor = new ArrayList<>();
    ArrayList<Proveedores> consultaDireccionProveedor = new ArrayList<>();
    private int indice = 0;


    public VentanaProveedores() {

        // Los cuadros de texto de las consultas de id proveedor y apellidos no se pueden editar
        et_ListadoCodigoProveedor.setEditable(false);
        et_ListadoApellidosProveedor.setEditable(false);


        // Selecciono la pestaña en función de la opción del menú que haya elegido
        if (VentanaPrincipal.abrirPestanaConsultas) {

            tabbedPaneProveedores.setSelectedIndex(1);
            VentanaPrincipal.abrirPestanaConsultas = false;

        }

        // ************************************************************ PESTAÑA GESTION PROVEEDORES *******************************************************************

        // Deteccion de teclas
        et_GestionCodigoProveedor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                // Le tengo que filtrar primero que coja algun dato de la caja de texto digerente a vacío, sino da error

                String teclas = et_GestionCodigoProveedor.getText();


                if (!et_GestionCodigoProveedor.getText().trim().equalsIgnoreCase("")) {
                    if (et_GestionCodigoProveedor.getText().trim().equalsIgnoreCase(et_GestionCodigoProveedor.getText()) && numeroCorrecto(teclas.trim())) {

                        SessionFactory sesion = HibernateUtil.getSessionFactory();
                        Session session = sesion.openSession();

                        // Instancio un proveedor y lo cargo según Hibernate (le paso el código del proveedor parseado, es un int)
                        Proveedores prov = new Proveedores();
                        prov = session.get(Proveedores.class, Integer.parseInt(et_GestionCodigoProveedor.getText()));

                        if (prov != null) {
                            et_GestionNombreProveedor.setText(prov.getProveedor());
                            et_GestionApellidosProveedor.setText(prov.getResponsableVentas());
                            et_GestionDireccionProveedor.setText(prov.getDirCp());
                        }
                    }
                    if (!numeroCorrecto(teclas.trim())) {
                        JOptionPane.showMessageDialog(null, "Código incorrecto !!", "Error", JOptionPane.WARNING_MESSAGE);
                        et_GestionCodigoProveedor.setText("");

                    }

                } else {
                    limpiarCamposGestionProveedor();
                }

            }
        });


        bt_limpiarProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCamposGestionProveedor();

            }
        });


        bt_InsertarProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comprobarCamposVaciosInsercion()) {

                    // Miro si se repite el proveedor
                    if (!proveedorRepetido()) {

                        // Hacemos la inserción
                        CargaDatos.insercionProveedor(et_GestionNombreProveedor.getText().trim().toUpperCase(), et_GestionApellidosProveedor.getText().trim().toUpperCase(), et_GestionDireccionProveedor.getText().trim().toUpperCase());

                        // Actualizamos el ArrayList de 'proveedores' con los nuevos datos actualizados desde la base de datos
                        proveedores = CargaDatos.cargarProveedores();


                    } else {
                        JOptionPane.showMessageDialog(null, "El proveedor " + et_GestionNombreProveedor.getText().toUpperCase() + " ya existe", "Error", JOptionPane.WARNING_MESSAGE);

                    }

                    limpiarCamposGestionProveedor();
                }


            }
        });


        bt_ModificarProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comprobarCamposVacios()) {

                    int idProveedoraModificar = Integer.parseInt(et_GestionCodigoProveedor.getText().trim());

                    if (comprobarExisteProveedor()) {

                        // Modificamos los datos de la BD
                        CargaDatos.modificarProveedor(idProveedoraModificar, et_GestionNombreProveedor.getText().trim(), et_GestionApellidosProveedor.getText().trim(), et_GestionDireccionProveedor.getText().trim());

                        // Actualizamos el ArrayList de 'proveedores' con los nuevos datos actualizados desde la base de datos
                        proveedores = CargaDatos.cargarProveedores();


                    } else {
                        JOptionPane.showMessageDialog(null, "No existe ningún proveedor  con el código " + et_GestionCodigoProveedor.getText(), "Error", JOptionPane.WARNING_MESSAGE);
                    }


                    limpiarCamposGestionProveedor();

                }


            }
        });


        bt_EliminarProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (numeroCorrecto(et_GestionCodigoProveedor.getText().trim())) {

                    int idProveedoraBorrar = Integer.parseInt(et_GestionCodigoProveedor.getText().trim());

                    if (comprobarExisteProveedor()) {

                        if (!comprobarProveedorProyectos(et_GestionCodigoProveedor.getText().trim())) {

                            // Borramos proveedor
                            CargaDatos.borrarProveedor(idProveedoraBorrar);

                            // Actualizamos el ArrayList de 'proveedores' con los nuevos datos actualizados desde la base de datos
                            proveedores = CargaDatos.cargarProveedores();

                        }


                    } else {
                        JOptionPane.showMessageDialog(null, "No existe el proveedor con el código " + et_GestionCodigoProveedor.getText(), "Error", JOptionPane.WARNING_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Introduce un código correcto !!", "Error", JOptionPane.WARNING_MESSAGE);

                }

                limpiarCamposGestionProveedor();


            }
        });


        // ******************************************************************** PESTAÑA CONSULTA PROVEEDORES ******************************************************************************


        bt_ejecutarConsultaProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                indice = 0;

                if (!et_ListadoNombreProveedor.getText().equalsIgnoreCase("") && et_ListadoDireccionProveedor.getText().equalsIgnoreCase("")) {

                    consultaNombreProveedor = CargaDatos.consultaNombreProveedor(et_ListadoNombreProveedor.getText().trim());

                    // Resultado de los datos encontrados en la consulta
                    lb_contadorListadoProveedores.setText("Resultados consulta: " + consultaNombreProveedor.size());

                    resultadoConsultaNombre();

                } else if (!et_ListadoDireccionProveedor.getText().equalsIgnoreCase("") && et_ListadoNombreProveedor.getText().equalsIgnoreCase("")) {

                    consultaDireccionProveedor = CargaDatos.consultaDireccionProveedor(et_ListadoDireccionProveedor.getText().trim());

                    // Resultado de los datos encontrados en la consulta
                    lb_contadorListadoProveedores.setText("Resultados consulta: " + consultaNombreProveedor.size());

                    resultadoConsultaDireccion();

                } else {
                    JOptionPane.showMessageDialog(null, "Introduce solo  un dato en nombre o direccion !!", "Error", JOptionPane.WARNING_MESSAGE);
                    limpiarConsultas();

                }
            }
        });


        bt_SiguienteProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (consultaNombreProveedor.size() > 0 && indice < consultaNombreProveedor.size() - 1) {

                    indice++;

                    resultadoConsultaNombre();

                } else if (consultaDireccionProveedor.size() > 0 && indice < consultaDireccionProveedor.size() - 1) {

                    indice++;

                    resultadoConsultaDireccion();

                }

            }
        });


        bt_AnteriorProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (consultaNombreProveedor.size() > 0 && indice > 0) {
                    indice--;
                    resultadoConsultaNombre();

                } else if (consultaDireccionProveedor.size() > 0 && indice > 0) {
                    indice--;

                    resultadoConsultaDireccion();
                }


            }
        });


        bt_finalProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (consultaNombreProveedor.size() > 0) {
                    indice = consultaNombreProveedor.size() - 1;
                    resultadoConsultaNombre();
                } else if (consultaDireccionProveedor.size() > 0) {
                    indice = consultaDireccionProveedor.size() - 1;
                    resultadoConsultaDireccion();
                }


            }
        });


        bt_inicioProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (consultaNombreProveedor.size() > 0) {
                    indice = 0;
                    resultadoConsultaNombre();
                } else if (consultaDireccionProveedor.size() > 0) {
                    indice = 0;
                    resultadoConsultaDireccion();
                }


            }
        });


        bt_limpiarConsultasProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                limpiarConsultas();

            }
        });


        bt_BajaProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!et_ListadoCodigoProveedor.getText().equalsIgnoreCase("")) {
                    int idProveedoraBorrar = Integer.parseInt(et_ListadoCodigoProveedor.getText().trim());

                    if (!comprobarProveedorProyectos(et_ListadoCodigoProveedor.getText().trim())) {

                        // Borramos proveedor
                        CargaDatos.borrarProveedor(idProveedoraBorrar);

                        // Actualizamos el ArrayList de 'proveedores' con los nuevos datos actualizados desde la base de datos
                        proveedores = CargaDatos.cargarProveedores();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No hay ningún código seleccionado !!", "Error", JOptionPane.WARNING_MESSAGE);

                }

            }
        });


    }

    private boolean proveedorRepetido() {
        // Miramos si ya existe un proveedor igual en la BD, solo compruebo si existe un proveedor con el mismo nombre
        boolean proveedorRepetido = false;

        for (Proveedores proveedor : proveedores) {
            if (proveedor.getProveedor().equalsIgnoreCase(et_GestionNombreProveedor.getText().trim())) {

                proveedorRepetido = true;

            }
        }
        return proveedorRepetido;
    }

    public void limpiarConsultas() {
        et_ListadoCodigoProveedor.setText("");
        et_ListadoNombreProveedor.setText("");
        et_ListadoApellidosProveedor.setText("");
        et_ListadoDireccionProveedor.setText("");

        // Resultado de los datos encontrados en la consulta
        lb_contadorListadoProveedores.setText("Resultados consulta: 0");
    }


    public void resultadoConsultaNombre() {

        if (consultaNombreProveedor.size() > 0) {
            for (int i = 0; i < consultaNombreProveedor.size(); i++) {
                et_ListadoCodigoProveedor.setText(String.valueOf(consultaNombreProveedor.get(indice).getIdProveedor()));
                et_ListadoNombreProveedor.setText(consultaNombreProveedor.get(indice).getProveedor());
                et_ListadoApellidosProveedor.setText(consultaNombreProveedor.get(indice).getResponsableVentas());
                et_ListadoDireccionProveedor.setText(consultaNombreProveedor.get(indice).getDirCp());
            }

            // Resultado de los datos encontrados en la consulta
            lb_contadorListadoProveedores.setText(indice + 1 + " de " + consultaNombreProveedor.size());


        } else {
            JOptionPane.showMessageDialog(null, "No hay resultados !!", "Error", JOptionPane.WARNING_MESSAGE);
            limpiarConsultas();
        }

    }


    public void resultadoConsultaDireccion() {

        if (consultaDireccionProveedor.size() > 0) {
            for (int i = 0; i < consultaDireccionProveedor.size(); i++) {
                et_ListadoCodigoProveedor.setText(String.valueOf(consultaDireccionProveedor.get(indice).getIdProveedor()));
                et_ListadoNombreProveedor.setText(consultaDireccionProveedor.get(indice).getProveedor());
                et_ListadoApellidosProveedor.setText(consultaDireccionProveedor.get(indice).getResponsableVentas());
                et_ListadoDireccionProveedor.setText(consultaDireccionProveedor.get(indice).getDirCp());
            }

            // Resultado de los datos encontrados en la consulta
            lb_contadorListadoProveedores.setText(indice + 1 + " de " + consultaDireccionProveedor.size());


        } else {
            JOptionPane.showMessageDialog(null, "No hay resultados !!", "Error", JOptionPane.WARNING_MESSAGE);
            limpiarConsultas();
        }

    }


    public boolean comprobarProveedorProyectos(String proveedor) {

        boolean comprobarRefProveedores = false;
        String proyectoRef = "";
        String proveedorRef = "";

        int idProveedoraBorrar = Integer.parseInt(proveedor.trim());
        String nombreProveedor = nombreProveedor(idProveedoraBorrar);

        // Voy guardando en una lista los proyectos en los que está el proveedor
        List<String> totalproyectos = new ArrayList<>();

        // Compruebo los proveedores (le paso el ID por la caja de texto en la ventana)
        // Si coincide el ID del proveedor con el que está en la tabla de Gestión, es que está en un proyecto
        for (Gestion gestion : gestiones) {

            if (gestion.getProveedorByIdProveedor().getIdProveedor() == idProveedoraBorrar) {
                comprobarRefProveedores = true;

                // Cojo el nombre del proveedor
                proveedorRef = gestion.getProveedorByIdProveedor().getProveedor().toUpperCase();

                // Voy pasando a la List los proyectos del proveedor
                totalproyectos.add((gestion.getProyectoByIdProyecto().getProyecto()));

            }
        }

        // Actualizo la lista quitando los valores duplicados , para que no se repitan los proyectos
        totalproyectos = totalproyectos.stream().distinct().collect(Collectors.toList());


        if (comprobarRefProveedores) {

            // Saco los proyectos por consola
            for (String totalproyecto : totalproyectos) {
                proyectoRef += totalproyecto.toUpperCase() + "\n";

            }

            System.out.println("No puedes borrar el proveedor " + proveedorRef + " porque esta en los siguientes proyectos:");
            System.out.println(proyectoRef.toUpperCase());

            JOptionPane.showMessageDialog(null, "No puedes borrar el proveedor " + proveedorRef.toUpperCase() + " porque esta en los siguientes proyectos:\n" + proyectoRef.toUpperCase(), "Error", JOptionPane.WARNING_MESSAGE);

        } else {
            System.out.println("Puedes borrar a " + nombreProveedor.toUpperCase());
        }

        return comprobarRefProveedores;


    }

    public String nombreProveedor(int idProveedoraBorrar) {
        String nombreProveedor = "";
        for (Proveedores proveedor : proveedores) {
            if (proveedor.getIdProveedor() == idProveedoraBorrar) {
                nombreProveedor = proveedor.getProveedor();
            }
        }

        return nombreProveedor;
    }


    public void limpiarCamposGestionProveedor() {
        et_GestionCodigoProveedor.setText("");
        et_GestionNombreProveedor.setText("");
        et_GestionApellidosProveedor.setText("");
        et_GestionDireccionProveedor.setText("");
    }


    public boolean comprobarExisteProveedor() {
        // Miramos si ya existe el proveedor a modificar en la BD
        boolean existeProveedor = false;

        int idProveedoraModificar = Integer.parseInt(et_GestionCodigoProveedor.getText().trim());

        for (Proveedores proveedor : proveedores) {
            if (proveedor.getIdProveedor() == idProveedoraModificar) {
                existeProveedor = true;
            }
        }

        return existeProveedor;
    }

    public boolean comprobarCamposVacios() {

        boolean camposVacios = true;

        String errores = "";

        if (!numeroCorrecto(et_GestionCodigoProveedor.getText().trim())) {
            errores += "CÓDIGO: Necesitas un código correcto para la modificación de datos\n";
        }
        if (et_GestionNombreProveedor.getText().trim().equalsIgnoreCase("") || !comprobarNombreApellidos(et_GestionNombreProveedor.getText().trim())) {
            errores += "NOMBRE: Está vacío o fuera del rango de carácteres (3-25)\n";
        }
        if (et_GestionApellidosProveedor.getText().trim().equalsIgnoreCase("") || !comprobarNombreApellidos(et_GestionApellidosProveedor.getText().trim())) {
            errores += "APELLIDOS: Está vacío o fuera del rango de carácteres (3-25)\n";
        }
        if (et_GestionDireccionProveedor.getText().trim().equalsIgnoreCase("") || !comprobarDatosString(et_GestionDireccionProveedor.getText().trim())) {
            errores += "DIRECCION: Está vacío o fuera del rango de carácteres (3-25)\n";
        }
        if (!errores.trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Rellena los siguientes campos:\n" + errores, "Error", JOptionPane.WARNING_MESSAGE);
            //limpiarCamposGestionProveedor();
            camposVacios = false;
        }

        System.out.println("Modificar proveedor: " + camposVacios);
        System.out.println((camposVacios) ? "Estan todos los campos vacios de modificar ?: NO" : "Están todos los campos vacíos de modificar ?: SI");
        return camposVacios;


    }


    public boolean comprobarCamposVaciosInsercion() {

        boolean camposVacios = true;

        String errores = "";

        if (!et_GestionCodigoProveedor.getText().trim().equalsIgnoreCase("")) {
            errores += "CÓDIGO: No es necesario el código para la inserción\n";
            et_GestionCodigoProveedor.setText("");
        }
        if (et_GestionNombreProveedor.getText().trim().equalsIgnoreCase("") || !comprobarNombreApellidos(et_GestionNombreProveedor.getText().trim())) {
            errores += "NOMBRE: Está vacío o fuera del rango de carácteres (3-25) o símblos incorrectos\n";
        }
        if (et_GestionApellidosProveedor.getText().trim().equalsIgnoreCase("") || !comprobarNombreApellidos(et_GestionApellidosProveedor.getText().trim())) {
            errores += "APELLIDOS: Está vacío o fuera del rango de carácteres (3-25) o símbolos incorrectos\n";
        }
        if (et_GestionDireccionProveedor.getText().trim().equalsIgnoreCase("") || !comprobarDatosString(et_GestionDireccionProveedor.getText().trim())) {
            errores += "DIRECCION: Está vacío o fuera del rango de carácteres (3-25)\n";
        }
        if (!errores.trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Rellena los siguientes campos:\n" + errores, "Error", JOptionPane.WARNING_MESSAGE);
            //limpiarCamposGestionProveedor();
            camposVacios = false;
        }

        System.out.println("Insertar proveedor: " + camposVacios);

        System.out.println((camposVacios) ? "Estan todos los campos vacios de insertar?: NO" : "Estan todos los campos vacios de insertar?: SI");


        return camposVacios;

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

    public static boolean comprobarNombreApellidos(String texto) {


        boolean correcto = false;
        // Comprueba cadenas de texto con  letras \w  números \d  y espacios entre palabras \s ( no entran ni tíldes ni símbolos) entre 3 y 25 carácteres
        //String regexp = "^(?=.{3,25}$)[\w\d\s]+( [\w\d\s]+)*$";

        // Comprueba cadenas de texto con espacios entre palabras, tildes y símbolos entre 3 y 25 carácteres en total
        String regexp = "^(?=.{3,25}$)[a-zA-ZáéíóúüñÁÉÍÓÚÑ']+( [a-zA-ZáéíóúüñÁÉÍÓÚÑ' +]+)*$";

        if (Pattern.matches(regexp, texto)) {
            correcto = true;
        } else {
            System.out.println("Introduce un formato correcto de nombre o apellido entre 3 y 20 carácteres !!");
        }

        return correcto;
    }


    public JPanel getPanelListadoProveedores() {
        return Jpanel;
    }

    public JPanel getPanelProveedores() {
        return PanelProveedores;
    }

    public JTextField getEt_GestionCodigoProveedor() {
        return et_GestionCodigoProveedor;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPaneProveedores;
    }
}
