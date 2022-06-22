import Models.*;
import MySQL.Carga;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class VPrincipal_MySQL {
    public JPanel VPanelPrincipal;
    private JTabbedPane tabbedPane1;
    private JPanel PestanaEspectaculos;
    private JList<Espectaculo> listaEspectaculos;
    private JButton bt_AnadirEsp;
    private JButton bt_ModEsp;
    private JButton bt_eliminar;
    private JLabel lb_ID;
    private JLabel lb_Esp;
    private JLabel lb_aforo;
    private JLabel lb_Desc;
    private JTextField et_Espectaculo;
    private JTextField et_aforo;
    private JTextField et_Descripcion;
    private JTextField et_ID_Espec;
    private JLabel lb_lugar;
    private JLabel lb_fecha;
    private JTextField et_lugar;
    private JTextField et_fecha;
    private JLabel lb_horario;
    private JLabel lb_precio;
    private JTextField et_horario;
    private JTextField et_precio;
    private JButton bt_GuardarEspectaculo;
    private JPanel PestanaClientes;
    private JPanel panelDatos;
    private JLabel et_dni;
    private JLabel et_nombre;
    private JLabel et_apellido;
    private JLabel et_edad;
    private JTextField campo_nombre;
    private JTextField campo_apellido;
    private JTextField campo_edad;
    private JTextField campo_dni;
    private JButton bt_guardarCli;
    private JList<Cliente> list_clientes;
    private JButton bt_nuevoCli;
    private JButton bt_modificarCli;
    private JButton bt_eliminarCli;
    private JPanel PestanaEmpleados;
    private JList<Empleado> listaEmpleados;
    private JButton bt_EmpleAnadir;
    private JButton bt_ModEmpl;
    private JButton bt_eliminarEmple;
    private JPanel lb_NacioEmpl;
    private JLabel lb_dniEmple;
    private JLabel lb_Empleado;
    private JLabel lb_ApeEmpl;
    private JLabel lb_fechaNacEmpl;
    private JTextField et_emple;
    private JTextField et_apeEmple;
    private JTextField et_NacEmp;
    private JTextField et_dniEmp;
    private JLabel lb_FechaContratoEmpl;
    private JTextField et_fechaContrEmp;
    private JTextField et_NacioEmpl;
    private JLabel lb_CargoEmpl;
    private JTextField et_CargoEmpl;
    private JButton bt_GuardarEmple;
    private JPanel PanelEspectaculos;
    private JList<Cliente> listadoClientesEspectaculos;
    private JList<Empleado> listaEmpleadosEspectaculos;
    private JScrollPane resultadoClientesEspectaculos;

    private JScrollPane resultadoEmpleadosEspectaculos;
    private JLabel lb_responsable;
    private JComboBox<String> comboBoxEmpleados;
    private JLabel lb_infoMySql;
    private JTextArea textAreaInfoMySql;
    private JButton infoButton;
    private JButton bt_salir;
    private JButton bt_AnadirEspecCliente;
    private JButton bt_BorrarEspecCliente;
    private JButton bt_GuardarEspecCliente;
    private JTextField textField1;

    private JTable tabla;
    private JTable tabla2;
    private JComboBox<Espectaculo> comboBoxEspectaculos;
    private JButton bt_AnadirEspecEmpleado;
    private JButton bt_BorrarEspecEmpleado;
    private JComboBox<Espectaculo> comboBoxEspectaculos2;
    private JButton bt_GuardarEspecEmpleado;


    private ArrayList<Espectaculo> espectaculos = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Espectaculos_Cliente> espectaculosClientes = new ArrayList<>();
    private ArrayList<Espectaculos_Empleado> espectaculosEmpleados = new ArrayList<>();
    private ArrayList<Empleado> empleados = new ArrayList<>();

    private Cliente cliente;
    private Empleado empleado;
    private Espectaculo espectaculo;
    private Espectaculos_Empleado espectaculoEmpleado;
    private Espectaculos_Cliente espectaculosCliente;

    public VPrincipal_MySQL() {

        //Comprobar si el usuario conectado es el Admin y mostrar u ocultar la informacion de la base de datos
        if(VAccesoMySQL.usuarioMySQL.equalsIgnoreCase("Admin") && VAccesoMySQL.passwordBDMySQL.equalsIgnoreCase("0000")){
            infoButton.setEnabled(true);
            System.out.println(VAccesoSQLite.usuarioSQLite);
            System.out.println(VAccesoSQLite.passwdBDSQLite);
        } else {
            System.out.println(VAccesoSQLite.usuarioSQLite);
            System.out.println(VAccesoSQLite.passwdBDSQLite);
            infoButton.setEnabled(false);
            textAreaInfoMySql.setText("Acceso Denegado");
            textAreaInfoMySql.setEditable(false);
        }


        //Funcion para hacer todas las cargas y refrescar datos
        CargaryRefrescarTodo();

        //Poner los botones de guardar en oculto
        bt_guardarCli.setVisible(false);
        bt_GuardarEspectaculo.setVisible(false);
        bt_GuardarEmple.setVisible(false);
        comboBoxEspectaculos.setVisible(false);
        bt_GuardarEspecCliente.setVisible(false);
        comboBoxEspectaculos2.setVisible(false);
        bt_GuardarEspecEmpleado.setVisible(false);

        //Cargar las listas
        list_clientes.addListSelectionListener(e -> actualizarClientes());
        listaEspectaculos.addListSelectionListener(e -> actualizarEspectaculos());
        listaEmpleados.addListSelectionListener(e -> actualizarEmpleados());

        //Botones de la pestaña Clientes
        bt_nuevoCli.addActionListener(e -> {
            campo_dni.setEnabled(true);
            limpiarCampos();
            bt_guardarCli.setVisible(true);
        });
        bt_guardarCli.addActionListener(e -> {
            if (!comprobarCamposVacios()) {
                panelMensajePersonalizado("Campos Vacíos", "No puede haber campos vacíos. Comprueba todos los campos", 0);
            } else if (!campo_dni.isEnabled()) {
                String errores = "";
                String edadString = String.valueOf(campo_edad.getText());
                if (!validarDNI(campo_dni.getText())) {
                    errores += "DNI: No es un dni válido\n";
                    campo_dni.setText("");
                }
                if (!numeroCorrecto(edadString)) {
                    errores += "EDAD: Introduce un número correcto\n";
                    campo_edad.setText("");
                }
                if (!errores.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, errores);

                }
                int edadInt = Integer.parseInt(edadString);
                cliente = new Cliente(campo_dni.getText(), campo_nombre.getText(), campo_apellido.getText(), edadInt);
                clientes.add(cliente);
                cliente.mostrarClientes(clientes);
                new Carga().modificarCliente(cliente);

                CargaryRefrescarTodo();
            } else {
                String nombreRepe = comprobarDNIcliente(clientes, campo_dni.getText());

                if (clientes.contains(new Cliente(campo_dni.getText()))) {
                    JOptionPane.showMessageDialog(null, "Ya existe el cliente '" + nombreRepe.toUpperCase() + "' con el dni '" + campo_dni.getText() + "'\nIntroduce otro DNI...");

                } else {
                    String errores = "";
                    String edadString = String.valueOf(campo_edad.getText());

                    if (!validarDNI(campo_dni.getText())) {
                        errores += "DNI: No es un dni válido\n";
                        campo_dni.setText("");

                    }
                    if (!numeroCorrecto(edadString)) {
                        errores += "EDAD: Introduce un número correcto\n";
                        campo_edad.setText("");
                    }
                    if (!errores.equalsIgnoreCase("")) {
                        JOptionPane.showMessageDialog(null, errores);

                    } else {
                        int edadInt = Integer.parseInt(edadString);
                        cliente = new Cliente(campo_dni.getText(), campo_nombre.getText(), campo_apellido.getText(), edadInt);
                        clientes.add(cliente);
                        cliente.mostrarClientes(clientes);
                        new Carga().clienteNuevo(cliente);
                    }


                }
            }
            CargaryRefrescarTodo();
            limpiarCampos();
            bt_guardarCli.setVisible(false);
        });
        bt_modificarCli.addActionListener(e -> {

            campo_dni.setEnabled(false);
            bt_guardarCli.setVisible(true);


        });
        bt_eliminarCli.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(null, "Seguro que lo quieres eliminar?", "Eliminar", JOptionPane.YES_NO_OPTION);

            String edadString = String.valueOf(campo_edad.getText());

            if (opcion == JOptionPane.YES_OPTION) {
                int edadInt = Integer.parseInt(edadString);
                cliente = new Cliente(campo_dni.getText(), campo_nombre.getText(), campo_apellido.getText(), edadInt);
                clientes.remove(cliente);
                cliente.mostrarClientes(clientes);
                new Carga().eliminarCliente(cliente);
            }
            CargaryRefrescarTodo();
            limpiarCampos();
            bt_guardarCli.setVisible(false);

        });


        //Botones de la pestaña Espectaculos
        bt_AnadirEsp.addActionListener(e -> {

            et_ID_Espec.setEnabled(true);
            et_ID_Espec.setVisible(false);
            limpiarCampos();
            bt_GuardarEspectaculo.setVisible(true);

        });
        bt_GuardarEspectaculo.addActionListener(e -> {
            if (!comprobarCamposVaciosEspectaculos()) {

                panelMensajePersonalizado("Campos Vacíos", "No puede haber campos vacíos. Comprueba todos los campos", 0);

            } else if (!et_ID_Espec.isEnabled()) {
                String errores = "";
                String aforoString = String.valueOf(et_aforo.getText());
                String fechaString = String.valueOf(et_fecha.getText());
                String horarioString = String.valueOf(et_horario.getText());
                String precioString = String.valueOf(et_precio.getText());

                if (!numeroCorrecto(aforoString)) {
                    errores += "AFORO: Introduce un número correcto\n";
                    et_aforo.setText("");
                }
                if (!formatoFecha(fechaString)) {
                    errores += "FECHA: Introduce una fecha correcta --> 'yyyy-mm-dd'\n";
                    et_fecha.setText("");
                }
                if (!formatoMinutosSegundos(horarioString)) {
                    errores += "HORARIO: Introduce un formato correcto --> 'HH:mm:ss' \n";
                    et_horario.setText("");
                }
                if (!numeroCorrecto(precioString)) {
                    errores += "PRECIO: Introduce un número correcto\n";
                    et_precio.setText("");
                }

                if (!errores.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, errores);

                } else {

                    int aforoInt = Integer.parseInt(aforoString);
                    Date fechaDate = Date.valueOf(fechaString);
                    Time horarioTime = Time.valueOf(horarioString);
                    double precioDouble = Double.parseDouble(precioString);
                    String responsable = String.valueOf(comboBoxEmpleados.getSelectedItem());

                    espectaculo = new Espectaculo(et_Espectaculo.getText(), aforoInt, et_Descripcion.getText(), et_lugar.getText(), fechaDate, horarioTime, precioDouble, responsable);
                    espectaculos.add(espectaculo);
                    espectaculo.mostrarEspectaculos(espectaculos);

                    new Carga().modificarEspectaculo(espectaculo);
                    CargaryRefrescarTodo();
                }
            } else {
                String errores = "";
                String aforoString = String.valueOf(et_aforo.getText());
                String fechaString = String.valueOf(et_fecha.getText());
                String horarioString = String.valueOf(et_horario.getText());
                String precioString = String.valueOf(et_precio.getText());

                if (!numeroCorrecto(aforoString)) {
                    errores += "AFORO: Introduce un número correcto\n";
                    et_aforo.setText("");
                }
                if (!formatoFecha(fechaString)) {
                    errores += "FECHA: Introduce una fecha correcta --> 'yyyy-mm-dd'\n";
                    et_fecha.setText("");

                }
                if (!formatoMinutosSegundos(horarioString)) {
                    errores += "HORARIO: Introduce un formato correcto --> 'HH:mm:ss' \n";
                    et_horario.setText("");

                }
                if (!numeroCorrecto(precioString)) {
                    errores += "PRECIO: Introduce un número correcto\n";
                    et_precio.setText("");
                }

                if (!errores.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, errores);

                } else {
                    int aforoInt = Integer.parseInt(aforoString);
                    Date fechaDate = Date.valueOf(fechaString);
                    Time horarioTime = Time.valueOf(horarioString);
                    double precioDouble = Double.parseDouble(precioString);

                    if (espectaculos.contains(new Espectaculo(et_Espectaculo.getText(), et_lugar.getText(), fechaDate, horarioTime))) {
                        JOptionPane.showMessageDialog(null, "Ya está actualmente el espectáculo '" + et_Espectaculo.getText().toUpperCase() + "' en cartelera (" + et_lugar.getText() + ", " + fechaDate + ", " + horarioString + ")\nIntroduce otro espectáculo...");

                    } else {
                        String responsable = String.valueOf(comboBoxEmpleados.getSelectedItem());
                        espectaculo = new Espectaculo(et_Espectaculo.getText(), aforoInt, et_Descripcion.getText(), et_lugar.getText(), fechaDate, horarioTime, precioDouble, responsable);

                        espectaculos.add(espectaculo);
                        espectaculo.mostrarEspectaculos(espectaculos);

                        new Carga().espectaculoNuevo(espectaculo);

                        // Hayo el id máximo del ultima inserción de espectaculos y se lo paso a la tabla de espectaculos_Empleados para hacer el insert
                        int idmax = new Carga().idMaxEspectaculos();
                        // Comprobacion por pantalla del iD máximo:
                        System.out.println("\nID máximo de la tabla espectáculo es: " + idmax);

                        String idEmple = "";
                        String empleado = (String) comboBoxEmpleados.getSelectedItem();
                        for (Empleado empleado1 : empleados) {
                            if (empleado1.getNombreEmple().equalsIgnoreCase(empleado)) {
                                idEmple = empleado1.getDniEmple();
                            }
                        }

                        // Añado el empleado responsable a la tabla de espectaculos_empleados, con el dni del empleado escogido del comboBox String) y el espectaculo recien añadido (int)
                        espectaculoEmpleado = new Espectaculos_Empleado(idEmple, idmax);
                        espectaculosEmpleados.add(espectaculoEmpleado);

                        // Añado al empleado relacionado con su espectáculo  a la tabla de espectáculos_empleados
                        new Carga().anadirEmpleadoEspectaculo(idEmple, idmax);
                        // Saco por pantalla una lista con todos los empleados con sus espectáculos que hay en la base de datos de la tabal espectaculos_empleados
                        espectaculoEmpleado.mostrarEspectaculosEmpleados(espectaculosEmpleados);

                        CargaryRefrescarTodo();
                    }
                }


            }
            limpiarCampos();
            bt_GuardarEspectaculo.setVisible(false);
            //et_ID_Espec.setVisible(true);
        });
        bt_ModEsp.addActionListener(e -> {

            et_ID_Espec.setEnabled(false);
            bt_GuardarEspectaculo.setVisible(true);

        });
        bt_eliminar.addActionListener(e -> {

            int opcion = JOptionPane.showConfirmDialog(null, "Seguro que lo quieres eliminar?", "Eliminar", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                String aforoString = String.valueOf(et_aforo.getText());
                int aforoInt = Integer.parseInt(aforoString);
                String fechaString = String.valueOf(et_fecha.getText());
                Date fechaDate = Date.valueOf(fechaString);
                String horarioString = String.valueOf(et_horario.getText());
                Time horarioTime = Time.valueOf(horarioString);
                String precioString = String.valueOf(et_precio.getText());
                double precioDouble = Double.parseDouble(precioString);
                String idString = String.valueOf(et_ID_Espec.getText());
                int idInt = Integer.parseInt(idString);
                String responsable = String.valueOf(comboBoxEmpleados.getSelectedItem());
                espectaculo = new Espectaculo(idInt, et_Espectaculo.getText(), aforoInt, et_Descripcion.getText(), et_lugar.getText(), fechaDate, horarioTime, precioDouble, responsable);

                espectaculos.remove(espectaculo);
                espectaculo.mostrarEspectaculos(espectaculos);
                new Carga().eliminarEspectaculo(espectaculo);
            }
            CargaryRefrescarTodo();
            limpiarCampos();
            bt_GuardarEspectaculo.setVisible(false);

        });


        //Botones de la pestaña empleados
        bt_EmpleAnadir.addActionListener(e -> {
            et_dniEmp.setEnabled(true);
            limpiarCampos();
            bt_GuardarEmple.setVisible(true);
        });
        bt_ModEmpl.addActionListener(e -> {
            et_dniEmp.setEnabled(false);
            bt_GuardarEmple.setVisible(true);
        });
        bt_eliminarEmple.addActionListener(e -> {

            boolean correcto = true;
            Empleado empleado = listaEmpleados.getSelectedValue();
            String espectaculosEmpleadoQueQuieroBorrar = "";
            for (Espectaculo espectaculo1 : espectaculos) {
                if (espectaculo1.getResponsable().equalsIgnoreCase(empleado.getNombreEmple())) {
                    System.out.println("Empleado responsable espectáculo: " + espectaculo1.getResponsable());
                    System.out.println("Empleado seleccionado de la lista: " + empleado.getNombreEmple());
                    correcto = false;
                    espectaculosEmpleadoQueQuieroBorrar += espectaculo1.getNombreEspec() + "\n";

                }
            }
            if (correcto) {


                int opcion = JOptionPane.showConfirmDialog(null, "Seguro que lo quieres eliminar?", "Eliminar", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    String fechaNacString = String.valueOf(et_NacEmp.getText());
                    Date fechaNacDate = Date.valueOf(fechaNacString);
                    String fechaContString = String.valueOf(et_fechaContrEmp.getText());
                    Date fechaContDate = Date.valueOf(fechaContString);

                    empleado = new Empleado(et_dniEmp.getText(), et_emple.getText(), et_apeEmple.getText(), fechaNacDate, fechaContDate, et_NacioEmpl.getText(), et_CargoEmpl.getText());
                    empleados.remove(empleado);
                    empleado.mostrarEmpleados(empleados);

                    new Carga().eliminarEmpleado(empleado);
                }
            } else {

                JOptionPane.showMessageDialog(null, "Si quieres borrar a " + et_emple.getText().toUpperCase() + " tienes que cambiar de responsable en los siquientes espectáculos:\n" + espectaculosEmpleadoQueQuieroBorrar.toUpperCase());

            }
            CargaryRefrescarTodo();

            limpiarCampos();
            bt_GuardarEmple.setVisible(false);

        });
        bt_GuardarEmple.addActionListener(e -> {
            if (!comprobarCamposVaciosEmpleados()) {
                panelMensajePersonalizado("Campos Vacíos", "No puede haber campos vacíos. Comprueba todos los campos", 0);
            } else if (!et_dniEmp.isEnabled()) {
                String errores = "";
                String fechaNacString = String.valueOf(et_NacEmp.getText());
                String fechaContString = String.valueOf(et_fechaContrEmp.getText());

                if (!validarDNI(et_dniEmp.getText())) {
                    errores += "DNI: No es un dni válido\n";
                    et_dniEmp.getText();

                }
                if (!formatoFecha(fechaNacString)) {
                    errores += "FECHA: Introduce una fecha correcta --> 'yyyy-mm-dd'\n";
                    et_NacEmp.setText("");

                }
                if (!formatoFecha(fechaContString)) {
                    errores += "FECHA: Introduce una fecha correcta --> 'yyyy-mm-dd'\n";
                    et_fechaContrEmp.setText("");

                }
                if (!errores.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, errores);

                } else {
                    Date fechaNacDate = Date.valueOf(fechaNacString);
                    Date fechaContDate = Date.valueOf(fechaContString);

                    empleado = new Empleado(et_dniEmp.getText(), et_emple.getText(), et_apeEmple.getText(), fechaNacDate, fechaContDate, et_NacioEmpl.getText(), et_CargoEmpl.getText());
                    empleados.add(empleado);
                    empleado.mostrarEmpleados(empleados);
                    new Carga().modificarEmpleado(empleado);
                }

                CargaryRefrescarTodo();


            } else {
                String errores = "";
                String nombreRepe = comprobarDNIempleado(empleados, et_dniEmp.getText());
                String fechaNacString = String.valueOf(et_NacEmp.getText());
                String fechaContString = String.valueOf(et_fechaContrEmp.getText());

                if (!validarDNI(et_dniEmp.getText())) {
                    errores += "DNI: No es un dni válido\n";
                    et_dniEmp.getText();

                }
                if (!formatoFecha(fechaNacString)) {
                    errores += "FECHA: Introduce una fecha correcta --> 'yyyy-mm-dd'\n";
                    et_NacEmp.setText("");

                }
                if (!formatoFecha(fechaContString)) {
                    errores += "FECHA: Introduce una fecha correcta --> 'yyyy-mm-dd'\n";
                    et_fechaContrEmp.setText("");

                }
                if (!errores.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, errores);

                } else {
                    if (empleados.contains(new Empleado(et_dniEmp.getText()))) {
                        JOptionPane.showMessageDialog(null, "Ya existe el empleado '" + nombreRepe.toUpperCase() + "' con el dni '" + et_dniEmp.getText() + "'\nIntroduce otro DNI...");

                    } else {
                        Date fechaNacDate = Date.valueOf(fechaNacString);
                        Date fechaContDate = Date.valueOf(fechaContString);

                        empleado = new Empleado(et_dniEmp.getText(), et_emple.getText(), et_apeEmple.getText(), fechaNacDate, fechaContDate, et_NacioEmpl.getText(), et_CargoEmpl.getText());
                        empleados.add(empleado);
                        empleado.mostrarEmpleados(empleados);
                        new Carga().empleadoNuevo(empleado);
                    }
                }

                CargaryRefrescarTodo();

            }
            limpiarCampos();
            bt_GuardarEmple.setVisible(false);
        });

        //Botones de la pestaña espectaculosClientes
        bt_AnadirEspecCliente.addActionListener(e -> {
            // Activo los botones para que se vean
            comboBoxEspectaculos.setVisible(true);
            bt_GuardarEspecCliente.setVisible(true);

        });
        bt_BorrarEspecCliente.addActionListener(e -> {
            try {
                // Seleccionar una fila del Jtable: // Devuelve un número
                if (tabla.getSelectedRow() != -1) {

                    TableModel m = tabla.getModel();

                    // Necesito el DNI del cliente y el ID del espectáculo que selecciono del Jtable:

                    // 1º Accedo al cliente que he seleccionado del Jlist, y asi saco su DNI
                    Cliente cliente = listadoClientesEspectaculos.getSelectedValue();
                    System.out.println("Cliente elegido: " + cliente.getNombre() + " " + cliente.getDni());
                    System.out.println("ID fila seleccionada: " + tabla.getSelectedRow());

                    // 2º ID de la fila del JTable, donde selecciono el espectáculo, Suponiendo que el id lo muestras en la primera columna, me coge el valor del ID de la fila seleccionada y la columna 0 (que es el ID)
                    int idEspectaculo = (int) m.getValueAt(tabla.getSelectedRow(), 0);
                    System.out.println("ID del espectáculo: " + idEspectaculo);

                    // 3º Instancio un objeto 'Espectaculos_Cliente' con los parámetros del dni cliente y el id del espectáculo y lo quito del Array de 'Espectaculos_Cliente'

                    espectaculosCliente = new Espectaculos_Cliente(cliente.getDni(), idEspectaculo);

                    // 4º Borro de la BBDD (le paso el dniCliente e ID espectaculo) y del ArrayLIst 'Espectaculos_Cliente'
                    //new Carga().eliminarEspectaculoCliente(cliente.getDni(), idEspectaculo);
                    new Carga().eliminarEspectaculoCliente(espectaculosCliente);
                    espectaculosClientes.remove(espectaculosCliente);

                    espectaculosCliente.mostrarEspectaculosCliente(espectaculosClientes);

                    tabla.remove(tabla.getSelectedRow());
                    tabla.repaint();


                    // 5º Vuelvo a cargar y refrescar
                    CargaryRefrescarTodo();


                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un espectáculo del cliente !!");
                }
            } catch (Exception ex) {
                //ex.printStackTrace();
            }

        });
        bt_GuardarEspecCliente.addActionListener(e -> {
            // Añado un espectáculo a un cliente, y lo añado a la tabla Espectaculos_Clientes, hay que coger el cliente seleccionado del Jlist y el espectaculo de combo

            if (listadoClientesEspectaculos.getSelectedValue() != null) {

                Cliente cliente = listadoClientesEspectaculos.getSelectedValue();

                // Hago un cast del objeto seleccionado del combobox a objeto Cliente, para poder aaceder luego a su dni  y pasárselo al constructor y a la función para añadir a la table espectaculos_clientes
                Espectaculo espectaculo = (Espectaculo) comboBoxEspectaculos.getSelectedItem();
                assert espectaculo != null;
                System.out.println("Espectaculo: " + espectaculo.getNombreEspec() + ", " + espectaculo.getNo_Espect());

                espectaculosCliente = new Espectaculos_Cliente(cliente.getDni(), espectaculo.getNo_Espect());

                String nombreCliente = cliente.getNombre() + " " + cliente.getApellidos();

                if (espectaculosClientes.contains(espectaculosCliente)) {
                    JOptionPane.showMessageDialog(null, "Error !!... el cliente '" + nombreCliente.toUpperCase() + "' ya tiene el espectáculo '" + comboBoxEspectaculos.getSelectedItem() + "'  ");
                } else {
                    espectaculosClientes.add(espectaculosCliente);
                    espectaculosCliente.mostrarEspectaculosCliente(espectaculosClientes);

                    new Carga().anadirClienteEspectaculo(cliente.getDni(), espectaculo.getNo_Espect());

                    CargaryRefrescarTodo();
                }


            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un cliente !!");

            }

            comboBoxEspectaculos.setVisible(false);
            bt_GuardarEspecCliente.setVisible(false);


        });

        //Botones de la pestaña espectaculosEmpleado
        bt_AnadirEspecEmpleado.addActionListener(e -> {
            // Activo los botones para que se vean
            comboBoxEspectaculos2.setVisible(true);
            bt_GuardarEspecEmpleado.setVisible(true);
        });
        bt_BorrarEspecEmpleado.addActionListener(e -> {
            // Primero miro los espectaculos que es responsable el empleado, para que no se quede sin responsable
            // Si quieres borrarlo, antes tienes que cambiar de responsable
            TableModel m = tabla2.getModel();
            boolean correcto = true;
            Empleado empleadoBorrar = listaEmpleadosEspectaculos.getSelectedValue();
            String espectaculoEmpleadoResponsableQueQuieroBorrar = "";
            int idEspElegido = (int) m.getValueAt(tabla2.getSelectedRow(), 0);
            //System.out.println("ID del espectáculo Elegido: " + idEspElegido);
            // Compruebo que si el empleado seleccionado es tambien el responsable del espectáculo seleccionado y también coincide con el ID del espectáculo seleccionado --> NO PUEDO BORRARLO
            for (Espectaculo espectaculo1 : espectaculos) {
                if (empleadoBorrar.getNombreEmple().equalsIgnoreCase(espectaculo1.getResponsable()) && espectaculo1.getNo_Espect() == idEspElegido) {
                    //System.out.println("Empleado responsable espectáculo: " + espectaculo1.getResponsable());
                    //System.out.println("Empleado seleccionado de la lista: " + empleadoBorrar.getNombreEmple());
                    correcto = false;
                    espectaculoEmpleadoResponsableQueQuieroBorrar = espectaculo1.getNombreEspec();
                }
            }
            // System.out.println(correcto);
            if (correcto) {
                try {
                    // Seleccionar una fila del Jtable: // Devuelve un número
                    if (tabla2.getSelectedRow() != -1) {
                        //TableModel m = tabla2.getModel();
                        // Necesito el DNI del cliente y el ID del espectáculo que selecciono del Jtable:
                        // 1º Accedo al cliente que he seleccionado del Jlist, y asi saco su DNI
                        Empleado empleado = listaEmpleadosEspectaculos.getSelectedValue();
                        System.out.println("Empleado elegido: " + empleado.getNombreEmple() + " " + empleado.getDniEmple());
                        System.out.println("ID fila seleccionada: " + tabla2.getSelectedRow());
                        // 2º ID de la fila del JTable, donde selecciono el espectáculo, Suponiendo que el id lo muestras en la primera columna, me coge el valor del ID de la fila seleccionada y la columna 0 (que es el ID)
                        int idEspectaculo = (int) m.getValueAt(tabla2.getSelectedRow(), 0);
                        System.out.println("ID del espectáculo: " + idEspectaculo);
                        // 3º Instancio un objeto 'Espectaculos_Cliente' con los parámetros del dni cliente y el id del espectáculo y lo quito del Array de 'Espectaculos_Cliente'
                        espectaculoEmpleado = new Espectaculos_Empleado(empleado.getDniEmple(), idEspElegido);
                        // 4º Borro de la BBDD (le paso el dniCliente e ID espectaculo) y del ArrayLIst 'Espectaculos_Cliente'
                        new Carga().eliminarEspectaculoEmpleado(espectaculoEmpleado);
                        espectaculosEmpleados.remove(espectaculoEmpleado);
                        espectaculoEmpleado.mostrarEspectaculosEmpleados(espectaculosEmpleados);
                        // 5º Vuelvo a cargar y refrescar
                        CargaryRefrescarTodo();
                    } else {
                        JOptionPane.showMessageDialog(null, "Selecciona un espectáculo del cliente !!");
                    }
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
            } else {
                System.out.println(espectaculoEmpleadoResponsableQueQuieroBorrar);
                JOptionPane.showMessageDialog(null, "No puedes borrar '" + espectaculoEmpleadoResponsableQueQuieroBorrar.toUpperCase() + "' porque " + empleadoBorrar.getNombreEmple().toUpperCase() + " es el  responsable, tienes que cambiar ANTES de responsable.");
            }
        });
        bt_GuardarEspecEmpleado.addActionListener(e -> {
            // Añado un espectáculo a un cliente, y lo añado a la tabla Espectaculos_Clientes, hay que coger el cliente seleccionado del Jlist y el espectaculo de combo

            if (listaEmpleadosEspectaculos.getSelectedValue() != null) {

                Empleado empleado = listaEmpleadosEspectaculos.getSelectedValue();

                // Hago un cast del objeto seleccionado del combobox a objeto Espectáculo, para poder aaceder luego a su dni  y pasárselo al constructor y a la función para añadir a la table espectaculos_empleados
                Espectaculo espectaculo = (Espectaculo) comboBoxEspectaculos2.getSelectedItem();
                assert espectaculo != null;
                System.out.println("Espectáculo: " + espectaculo.getNombreEspec() + ", " + espectaculo.getNo_Espect());

                Espectaculos_Empleado especEmple = new Espectaculos_Empleado(empleado.getDniEmple(), espectaculo.getNo_Espect());

                String nombreEmpleado = empleado.getNombreEmple() + " " + empleado.getApeEmple();

                if (espectaculosEmpleados.contains(especEmple)) {
                    JOptionPane.showMessageDialog(null, "Error !!... el empleado '" + nombreEmpleado.toUpperCase() + "' ya tiene el espectáculo '" + comboBoxEspectaculos2.getSelectedItem() + "'  ");
                } else {
                    espectaculosEmpleados.add(especEmple);
                    especEmple.mostrarEspectaculosEmpleados(espectaculosEmpleados);

                    new Carga().anadirEmpleadoEspectaculo(empleado.getDniEmple(), espectaculo.getNo_Espect());

                    CargaryRefrescarTodo();
                }


            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un cliente !!");

            }

            comboBoxEspectaculos2.setVisible(false);
            bt_GuardarEspecEmpleado.setVisible(false);
        });

        // Información de la base de datos:
        infoButton.addActionListener(e -> {

            String infoBaseDatos = new Carga().infoMySql(textAreaInfoMySql);

            textAreaInfoMySql.setText(infoBaseDatos);

        });

        bt_salir.addActionListener(e -> {
            VAccesoMySQL.estasConectado=false;
            JOptionPane.showMessageDialog(null, "Adios");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VPanelPrincipal);
            topFrame.dispose();

        });
    }

    //Funciones de carga y refrescado de todos los datos de MySQL
    public void CargaryRefrescarTodo() {
        cargaDatos();
        cargar_tablas_relaciones();
        cargar_j_list_clientes();
        cargar_j_list_empleados();
        cargar_j_list_espectaculos();
        cargar_combobox_responsables();
        cargar_combobox_espectaculosClientes();
        cargar_j_list_jtable_espectaculosClientes();
        cargar_j_list_jtable_espectaculosEmpleados();

    }
    public void cargaDatos() {

        empleados = new Carga().listaEmpleados();

        espectaculos = new Carga().listaEspectaculos();

        clientes = new Carga().listaClientes();

        espectaculosClientes = new Carga().listaClientesEspectaculo();

        espectaculosEmpleados = new Carga().listaEmpleadosEspectaculo();

    }
    private void cargar_tablas_relaciones() {
        for (int i = 0; i < espectaculos.size(); i++) {
            for (int j = 0; j < espectaculosClientes.size(); j++) {

                for (int k = 0; k < clientes.size(); k++) {
                    if (espectaculos.get(i).getNo_Espect() == espectaculosClientes.get(j).getEspectaculo() && clientes.get(k).getDni().equalsIgnoreCase(espectaculosClientes.get(j).getCliente())) {
                        Cliente c = new Cliente(clientes.get(k).getDni(), clientes.get(k).getNombre(), clientes.get(k).getApellidos(), clientes.get(k).getEdad());
                        Espectaculo e = new Espectaculo(espectaculos.get(i).getNo_Espect(), espectaculos.get(i).getNombreEspec(), espectaculos.get(i).getAforo(), espectaculos.get(i).getDescripcion(), espectaculos.get(i).getLugar(), espectaculos.get(i).getFecha_Espec(), espectaculos.get(i).getHorario_espec(), espectaculos.get(i).getPrecio(), espectaculos.get(i).getResponsable());
                        // Añado el cliente al ArrayList de clientes dentro del espectáculo elegido y luego le hago un 'set' al array de espectáculos del cliente
                        espectaculos.get(i).getClientes().add(c);
                        c.setEspectaculos(clientes.get(k).getEspectaculos());
                        // Añado el espectaculo al Array de espectáculos del cliente elegido y luego le hago un 'set' al array de clientes del espectáculo
                        clientes.get(k).getEspectaculos().add(e);
                        e.setClientes(espectaculos.get(i).getClientes());
                    }
                }
            }
        }
        for (int i = 0; i < espectaculos.size(); i++) {
            for (int j = 0; j < espectaculosEmpleados.size(); j++) {
                for (int k = 0; k < empleados.size(); k++) {
                    if (espectaculos.get(i).getNo_Espect() == espectaculosEmpleados.get(j).getEspectaculo() && empleados.get(k).getDniEmple().equalsIgnoreCase(espectaculosEmpleados.get(j).getEmpleado())) {
                        Empleado empleado = new Empleado(empleados.get(k).getDniEmple(), empleados.get(k).getNombreEmple(), empleados.get(k).getApeEmple(), empleados.get(k).getFechaNac(), empleados.get(k).getFechaContr(), empleados.get(k).getNacionalidad(), empleados.get(k).getCargo());
                        Espectaculo e = new Espectaculo(espectaculos.get(i).getNo_Espect(), espectaculos.get(i).getNombreEspec(), espectaculos.get(i).getAforo(), espectaculos.get(i).getDescripcion(), espectaculos.get(i).getLugar(), espectaculos.get(i).getFecha_Espec(), espectaculos.get(i).getHorario_espec(), espectaculos.get(i).getPrecio(), espectaculos.get(i).getResponsable());
                        // Añado el empleado al ArrayList de empleados dentro del espectaculo elegido y luego le hago un 'set' al array de espectáculos del empleado
                        espectaculos.get(i).getEmpleados().add(empleado);
                        empleado.setEspectaculos(empleados.get(k).getEspectaculos());
                        // Añado el espectaculo al Array de espectáculos del empleado y luego le hago un 'set' al array de empleados del espectáculo
                        empleados.get(k).getEspectaculos().add(e);
                        e.setEmpleados(espectaculos.get(k).getEmpleados());
                    }
                }
            }
        }
    }
    public void cargar_j_list_clientes() {
        DefaultListModel<Cliente> model = new DefaultListModel<>();
        for (Cliente cliente : clientes) {
            model.addElement(cliente);
        }
        list_clientes.setModel(model);
    }
    public void cargar_j_list_empleados() {
        DefaultListModel<Empleado> model = new DefaultListModel<>();
        for (Empleado empleado : empleados) {
            model.addElement(empleado);
        }
        listaEmpleados.setModel(model);
    }
    public void cargar_j_list_espectaculos() {
        DefaultListModel<Espectaculo> model = new DefaultListModel<>();
        for (Espectaculo espectaculo : espectaculos) {
            model.addElement(espectaculo);
        }
        listaEspectaculos.setModel(model);
    }
    private void cargar_combobox_responsables() {
        // Carga de los empleados en un model de combobox para seleccionar el responsable, luefo le paso el modelo al objeto comboBox de la ventana
        DefaultComboBoxModel<String> empleModel = new DefaultComboBoxModel<>();

        for (Empleado empleado : empleados) {
            empleModel.addElement(empleado.getNombreEmple());

        }
        comboBoxEmpleados.setModel(empleModel);
    }
    private void cargar_combobox_espectaculosClientes() {
        // Carga de los clientes en un model de combobox para seleccionar el responsable, luefo le paso el modelo al objeto comboBox de la ventana
        DefaultComboBoxModel<Espectaculo> espectaculoModel = new DefaultComboBoxModel<>();

        for (Espectaculo espectaculo : espectaculos) {
            espectaculoModel.addElement(espectaculo);

        }

        comboBoxEspectaculos.setModel(espectaculoModel);
        comboBoxEspectaculos2.setModel((espectaculoModel));
    }
    private void cargar_j_list_jtable_espectaculosClientes() {
        // Jlist clientes Jtable espectaculos
        DefaultListModel<Cliente> modelClienteEspectaculo = new DefaultListModel<>();
        for (Cliente c : clientes) {
            modelClienteEspectaculo.addElement(c);
        }

        listadoClientesEspectaculos.setModel(modelClienteEspectaculo);

        tabla = new JTable();
        tabla.setModel(new ListaClientesEspectaculosModel(new Cliente()));
        resultadoClientesEspectaculos.setViewportView(tabla);

        listadoClientesEspectaculos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                tabla.setModel(new ListaClientesEspectaculosModel(listadoClientesEspectaculos.getSelectedValue()));
                System.out.println(listadoClientesEspectaculos.getSelectedValue().getEspectaculos());
            }
        });
    }
    private void cargar_j_list_jtable_espectaculosEmpleados() {
        // Jlist empleados Jtable espectaculos
        DefaultListModel<Empleado> modelEmpleadoEspectaculo = new DefaultListModel<>();
        for (Empleado e : empleados) {
            modelEmpleadoEspectaculo.addElement(e);
        }

        listaEmpleadosEspectaculos.setModel(modelEmpleadoEspectaculo);

        tabla2 = new JTable();
        tabla2.setModel(new ListaEmpleadosEspectaculosModel(new Empleado()));
        resultadoEmpleadosEspectaculos.setViewportView(tabla2);

        listaEmpleadosEspectaculos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                tabla2.setModel(new ListaEmpleadosEspectaculosModel(listaEmpleadosEspectaculos.getSelectedValue()));
                System.out.println(listaEmpleadosEspectaculos.getSelectedValue().getEspectaculos());

            }
        });
    }
    public void actualizarClientes() {
        Cliente cliente = list_clientes.getSelectedValue();

        if (cliente != null) {
            campo_dni.setText(cliente.getDni());
            campo_nombre.setText(cliente.getNombre());
            campo_apellido.setText(cliente.getApellidos());
            campo_edad.setText(String.valueOf(cliente.getEdad()));
        }
    }
    public void actualizarEmpleados() {
        Empleado empleado = listaEmpleados.getSelectedValue();

        if (empleado != null) {
            et_dniEmp.setText(empleado.getDniEmple());
            et_nombre.setText(empleado.getNombreEmple());
            et_apeEmple.setText(empleado.getApeEmple());
            et_NacEmp.setText(String.valueOf(empleado.getFechaNac()));
            et_fechaContrEmp.setText(String.valueOf(empleado.getFechaContr()));
            et_NacioEmpl.setText(String.valueOf(empleado.getNacionalidad()));
            et_CargoEmpl.setText(String.valueOf(empleado.getCargo()));
        }
    }
    public void actualizarEspectaculos() {
        Espectaculo espectaculo = listaEspectaculos.getSelectedValue();

        if (espectaculo != null) {
            et_ID_Espec.setText(String.valueOf(espectaculo.getNo_Espect()));
            et_Espectaculo.setText(espectaculo.getNombreEspec());
            et_aforo.setText(String.valueOf(espectaculo.getAforo()));
            et_Descripcion.setText(espectaculo.getDescripcion());
            et_lugar.setText(espectaculo.getLugar());
            et_fecha.setText(String.valueOf(espectaculo.getFecha_Espec()));
            et_horario.setText(String.valueOf(espectaculo.getHorario_espec()));
            et_precio.setText(String.valueOf(espectaculo.getPrecio()));
            comboBoxEmpleados.setSelectedItem(espectaculo.getResponsable());
        }
    }

    //Funciones para comprobar si los datos añadidos son del formato adecuado
    private boolean numeroCorrecto(String numero) {
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
    public static String comprobarDNIcliente(ArrayList<Cliente> clientes, String campo_dni) {
        String nombreRepe = "";
        for (Cliente cliente : clientes) {
            if (cliente.getDni().equalsIgnoreCase(campo_dni)) {
                nombreRepe = cliente.getNombre() + " " + cliente.getApellidos();
            }
        }
        return nombreRepe;
    }
    public static String comprobarDNIempleado(ArrayList<Empleado> empleados, String et_dniEmp) {
        String nombreRepe = "";
        for (Empleado empleado : empleados) {
            if (empleado.getDniEmple().equalsIgnoreCase(et_dniEmp)) {
                nombreRepe = empleado.getNombreEmple() + " " + empleado.getApeEmple();
            }
        }
        return nombreRepe;
    }
    private boolean formatoMinutosSegundos(String duracion) {
        boolean correcto = false;
        String regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$"; // formato 'MMM:ss' ó 'MM:ss' ó 'M:ss'

        if (Pattern.matches(regexp, duracion)) {
            //System.out.println(Pattern.matches(regexp,duracion));
            correcto = true;
        } else {
            System.out.println("Introduce un formato correcto --> 'HHH:mm' ó 'HH:mm' ó 'H:mm' ");
        }


        return correcto;
    }
    private boolean formatoFecha(String fecha) {
        boolean correcto = false;
        String regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";

        if (Pattern.matches(regexp, fecha)) {
            correcto = true;
        } else {
            System.out.println("Introduce un formato de fecha correcta--> 'yyyy-mm-dd' ");
        }


        return correcto;
    }
    private boolean validarDNI(String dni) {
        String letraMayuscula = ""; //Guardaremos la letra introducida en formato mayúscula

        // Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni y también si el último caracter no es una letra
        if (dni.length() != 9 || !Character.isLetter(dni.charAt(8))) {
            return false;
        }


        // Al superar la primera restricción, la letra la pasamos a mayúscula
        letraMayuscula = (dni.substring(8)).toUpperCase();

        // Por último validamos que sólo tengo 8 dígitos entre los 8 primeros caracteres y que la letra introducida es igual a la de la ecuación
        // Llamamos a los métodos privados de la clase soloNumeros() y letraDNI()
        if (soloNumeros(dni) && letraDNI(dni).equals(letraMayuscula)) {
            return true;
        } else {
            return false;
        }
    }
    private boolean soloNumeros(String dni) {

        int i, j = 0;
        String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra entre los 8 primeros dígitos
        String miDNI = ""; // Guardamos en una cadena los números para después calcular la letra
        String[] unoNueve = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        for (i = 0; i < dni.length() - 1; i++) {
            numero = dni.substring(i, i + 1);

            for (j = 0; j < unoNueve.length; j++) {
                if (numero.equals(unoNueve[j])) {
                    miDNI += unoNueve[j];
                }
            }
        }

        if (miDNI.length() != 8) {
            return false;
        } else {
            return true;
        }
    }
    private String letraDNI(String dni) {
        // El método es privado porque lo voy a usar internamente en esta clase, no se necesita fuera de ella

        // pasar miNumero a integer
        int miDNI = Integer.parseInt(dni.substring(0, 8));
        int resto = 0;
        String miLetra = "";
        String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        resto = miDNI % 23;

        miLetra = asignacionLetra[resto];

        return miLetra;
    }


    public JPanel getVPanelPrincipal() {
        return VPanelPrincipal;
    }

    //Funciones de comprobar y limpiar campos
    private void limpiarCampos() {

        campo_edad.setText("");
        campo_dni.setText("");
        campo_apellido.setText("");
        campo_nombre.setText("");


        et_dniEmp.setText("");
        et_nombre.setText("");
        et_apeEmple.setText("");
        et_NacEmp.setText("");
        et_fechaContrEmp.setText("");
        et_NacioEmpl.setText("");
        et_CargoEmpl.setText("");


        et_ID_Espec.setText("");
        et_Espectaculo.setText("");
        et_aforo.setText("");
        et_Descripcion.setText("");
        et_lugar.setText("");
        et_fecha.setText("");
        et_horario.setText("");
        et_precio.setText("");
        //mirar el combobox


    }
    private boolean comprobarCamposVacios() {

        boolean hayDato = true;
        ArrayList<JTextField> campos = new ArrayList<>();
        campos.add(campo_dni);
        campos.add(campo_nombre);
        campos.add(campo_apellido);
        campos.add(campo_edad);

        for (JTextField campo : campos) {
            if (campo.getText().equalsIgnoreCase("")) {
                hayDato = false;
            }
        }
        return hayDato;
    }
    private boolean comprobarCamposVaciosEmpleados() {

        boolean hayDato = true;
        ArrayList<JTextField> campos = new ArrayList<>();
        campos.add(et_dniEmp);
        campos.add(et_emple);
        campos.add(et_apeEmple);
        campos.add(et_NacEmp);
        campos.add(et_fechaContrEmp);
        campos.add(et_NacioEmpl);
        campos.add(et_CargoEmpl);

        for (JTextField campo : campos) {
            if (campo.getText().equalsIgnoreCase("")) {
                hayDato = false;
            }
        }
        return hayDato;
    }
    private boolean comprobarCamposVaciosEspectaculos() {

        boolean hayDato = true;
        ArrayList<JTextField> campos = new ArrayList<>();
        campos.add(et_ID_Espec);
        campos.add(et_Espectaculo);
        campos.add(et_aforo);
        campos.add(et_Descripcion);
        campos.add(et_lugar);
        campos.add(et_fecha);
        campos.add(et_horario);
        campos.add(et_precio);

        for (JTextField campo : campos) {
            if (campo.getText().equalsIgnoreCase("")) {
                hayDato = false;
            }
        }
        return hayDato;
    }

    //Funcion de panel de mensaje personalizado
    public void panelMensajePersonalizado(String titulo, String mensaje, int tipo) {
        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);
        Object[] options = {okButton};
        final JOptionPane pane = new JOptionPane(mensaje, tipo, JOptionPane.YES_NO_OPTION, null, options);
        JDialog dialog = pane.createDialog(titulo);
        okButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }


}
