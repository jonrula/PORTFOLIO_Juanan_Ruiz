import Models.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class VPrincipal_SQLite {
    private JPanel VPanelPrincipal;
    private JTabbedPane tabbedPane1;
    private JPanel PestanaEspectaculos;
    private JList<EspectaculoSQLite> listaEspectaculosSQLite;
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
    private JList<Cliente> list_clientesSQLite;
    private JButton bt_nuevoCli;
    private JButton bt_modificarCli;
    private JButton bt_eliminarCli;
    private JPanel PestanaEmpleados;
    private JList<EmpleadoSQLite> listaEmpleadosSQLite;
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
    private JList<Cliente> listadoClientesEspectaculosSQLite;
    private JScrollPane resultadoClientesEspectaculosSQLite;
    private JList<EmpleadoSQLite> listaEmpleadosEspectaculosSQLite;
    private JScrollPane resultadoEmpleadosEspectaculosSQLite;
    private JLabel lb_infoMySql;
    private JTextArea textAreaInfoMySql;
    private JButton infoButton;
    private JLabel lb_responsable;
    private JComboBox<EmpleadoSQLite> comboBoxEmpleadosSQLite;
    private JButton bt_salir;
    private JButton bt_AnadirEspecCliente;
    private JButton bt_BorrarEspecCliente;

    private JButton bt_GuardarEspecCliente;
    private JButton bt_AnadirEspecEmpleado;
    private JButton bt_BorrarEspecEmpleado;
    private JButton bt_GuardarEspecEmpleado;


    private ArrayList<EspectaculoSQLite> espectaculosSQLite = new ArrayList<>();
    private ArrayList<Cliente> clientesSQLite = new ArrayList<>();
    private ArrayList<Espectaculos_Cliente> espectaculosClientesSQLite = new ArrayList<>();
    private ArrayList<Espectaculos_Empleado> espectaculosEmpleadosSQLite = new ArrayList<>();
    private ArrayList<EmpleadoSQLite> empleadosSQLite = new ArrayList<>();

    private JTable tabla;
    private JTable tabla2;
    private JComboBox<EspectaculoSQLite> comboBoxEspectaculosSQLite;
    private JComboBox<EspectaculoSQLite> comboBoxEspectaculosSQLite2;

    private Cliente clienteSQLite;
    private EmpleadoSQLite empleadoSQLite;
    private EspectaculoSQLite espectaculoSQLite;
    private Espectaculos_Empleado espectaculoEmpleadoSQLite;
    private Espectaculos_Cliente espectaculoClienteSQLite;


    public VPrincipal_SQLite() {

        //Comprobar si el usuario conectado es el Admin y mostrar u ocultar la informacion de la base de datos
        if(VAccesoSQLite.usuarioSQLite.equalsIgnoreCase("Admin") && VAccesoSQLite.passwdBDSQLite.equalsIgnoreCase("0000")){
            infoButton.setEnabled(true);
        } else {
            infoButton.setEnabled(false);
            textAreaInfoMySql.setText("Acceso Denegado");
            textAreaInfoMySql.setEditable(false);
        }

        //Funcion para hacer todas las cargas y refrescar datos
        CargaryRefrescarTodoSQLite();

        //Poner los botones de guardar en oculto
        bt_guardarCli.setVisible(false);
        bt_GuardarEspectaculo.setVisible(false);
        bt_GuardarEmple.setVisible(false);
        comboBoxEspectaculosSQLite.setVisible(false);
        bt_GuardarEspecCliente.setVisible(false);
        comboBoxEspectaculosSQLite2.setVisible(false);
        bt_GuardarEspecEmpleado.setVisible(false);

        list_clientesSQLite.addListSelectionListener(e -> actualizarClientesSQLite());
        listaEmpleadosSQLite.addListSelectionListener(e -> actualizarEmpleadosSQLite());
        listaEspectaculosSQLite.addListSelectionListener(e -> actualizarEspectaculosSQLite());

        //Botones pestaña Clientes
        bt_nuevoCli.addActionListener(e -> {
            campo_dni.setEnabled(true);
            limpiarCampos();
            bt_guardarCli.setVisible(true);
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
                clienteSQLite = new Cliente(campo_dni.getText(), campo_nombre.getText(), campo_apellido.getText(), edadInt);
                clientesSQLite.remove(clienteSQLite);
                clienteSQLite.mostrarClientes(clientesSQLite);
                new SQLite.Carga().eliminarCliente(clienteSQLite);
            }
            CargaryRefrescarTodoSQLite();
            limpiarCampos();
            bt_guardarCli.setVisible(false);
        });
        bt_guardarCli.addActionListener(e -> {
            if (!comprobarCamposVaciosClientesSQLite()) {
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
                clienteSQLite = new Cliente(campo_dni.getText(), campo_nombre.getText(), campo_apellido.getText(), edadInt);
                clientesSQLite.add(clienteSQLite);
                clienteSQLite.mostrarClientes(clientesSQLite);
                new SQLite.Carga().modificarCliente(clienteSQLite);

                CargaryRefrescarTodoSQLite();
            } else {
                String nombreRepeSQLite = comprobarDNIcliente(clientesSQLite, campo_dni.getText());
                if (clientesSQLite.contains(new Cliente(campo_dni.getText()))) {
                    JOptionPane.showMessageDialog(null, "Ya existe el cliente '" + nombreRepeSQLite.toUpperCase() + "' con el dni '" + campo_dni.getText() + "'\nIntroduce otro DNI...");

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
                        clienteSQLite = new Cliente(campo_dni.getText(), campo_nombre.getText(), campo_apellido.getText(), edadInt);
                        clientesSQLite.add(clienteSQLite);
                        clienteSQLite.mostrarClientes(clientesSQLite);
                        new SQLite.Carga().clienteNuevo(clienteSQLite);
                    }


                }
            }
            CargaryRefrescarTodoSQLite();
            limpiarCampos();
            bt_guardarCli.setVisible(false);
        });


        //Botones pestaña espectaculos
        bt_AnadirEsp.addActionListener(e -> {
            et_ID_Espec.setEnabled(true);
            et_ID_Espec.setVisible(false);
            limpiarCampos();
            bt_GuardarEspectaculo.setVisible(true);
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

                String precioString = String.valueOf(et_precio.getText());
                double precioDouble = Double.parseDouble(precioString);
                String idString = String.valueOf(et_ID_Espec.getText());
                int idInt = Integer.parseInt(idString);
                String responsable = String.valueOf(comboBoxEmpleadosSQLite.getSelectedItem());
                espectaculoSQLite = new EspectaculoSQLite(idInt, et_Espectaculo.getText(), aforoInt, et_Descripcion.getText(), et_lugar.getText(), et_fecha.getText(), et_horario.getText(), precioDouble, responsable);

                espectaculosSQLite.remove(espectaculoSQLite);
                espectaculoSQLite.mostrarEspectaculosSQLite(espectaculosSQLite);
                new SQLite.Carga().eliminarEspectaculo(espectaculoSQLite);
            }
            CargaryRefrescarTodoSQLite();
            limpiarCampos();
            bt_GuardarEspectaculo.setVisible(false);
        });
        bt_GuardarEspectaculo.addActionListener(e -> {
            if (!comprobarCamposVaciosEspectaculosSQLite()) {

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
                    double precioDouble = Double.parseDouble(precioString);
                    String responsable = String.valueOf(comboBoxEmpleadosSQLite.getSelectedItem());
                    espectaculoSQLite = new EspectaculoSQLite(et_Espectaculo.getText(), aforoInt, et_Descripcion.getText(), et_lugar.getText(), et_fecha.getText(), et_horario.getText(), precioDouble, responsable);

                    espectaculosSQLite.add(espectaculoSQLite);
                    espectaculoSQLite.mostrarEspectaculosSQLite(espectaculosSQLite);
                    new SQLite.Carga().modificarEspectaculo(espectaculoSQLite);
                    CargaryRefrescarTodoSQLite();
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
                    double precioDouble = Double.parseDouble(precioString);
                    String responsable = String.valueOf(comboBoxEmpleadosSQLite.getSelectedItem());
                    espectaculoSQLite = new EspectaculoSQLite(et_Espectaculo.getText(), aforoInt, et_Descripcion.getText(), et_lugar.getText(), et_fecha.getText(), et_horario.getText(), precioDouble, responsable);

                    espectaculosSQLite.add(espectaculoSQLite);
                    espectaculoSQLite.mostrarEspectaculosSQLite(espectaculosSQLite);

                    new SQLite.Carga().espectaculoNuevo(espectaculoSQLite);

                    // Hayo el id máximo del ultima inserción de espectaculos y se lo paso a la tabla de espectaculos_Empleados para hacer el insert
                    int idmax = new SQLite.Carga().idMaxEspectaculos();
                    // Comprobacion por pantalla del iD máximo:
                    System.out.println("\nID máximo de la tabla espectáculo es: " + idmax);

                    String idEmpleSQLite = "";
                    String empleadoSQLite = (String) comboBoxEmpleadosSQLite.getSelectedItem();
                    for (EmpleadoSQLite empleadoSQLite1 : empleadosSQLite) {
                        if (empleadoSQLite1.getNombreEmple().equalsIgnoreCase(empleadoSQLite)) {
                            idEmpleSQLite = empleadoSQLite1.getDniEmple();
                        }
                    }
                    // Añado el empleado responsable a la tabla de espectaculos_empleados, con el dni del empleado escogido del comboBox String) y el espectaculo recien añadido (int)
                    espectaculoEmpleadoSQLite = new Espectaculos_Empleado(idEmpleSQLite, idmax);
                    espectaculosEmpleadosSQLite.add(espectaculoEmpleadoSQLite);

                    // Añado al empleado relacionado con su espectáculo  a la tabla de espectáculos_empleados
                    new SQLite.Carga().anadirEmpleadoEspectaculoSQLite(idEmpleSQLite, idmax);
                    espectaculoEmpleadoSQLite.mostrarEspectaculosEmpleados(espectaculosEmpleadosSQLite);

                    CargaryRefrescarTodoSQLite();
                }
            }
            limpiarCampos();
            bt_GuardarEspectaculo.setVisible(false);
            //et_ID_Espec.setVisible(true);
        });


        //Botones pestaña empleados
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
            EmpleadoSQLite empSQLite = listaEmpleadosSQLite.getSelectedValue();
            String espectEmpleadoQueQuieroBorrar = "";
            for (EspectaculoSQLite espectSQLite : espectaculosSQLite) {
                if (espectSQLite.getResponsable().equalsIgnoreCase(empSQLite.getNombreEmple())) {
                    System.out.println("Empleado responsable espectáculo: " + espectSQLite.getResponsable());
                    System.out.println("Empleado seleccionado de la lista: " + empSQLite.getNombreEmple());
                    correcto = false;
                    espectEmpleadoQueQuieroBorrar += espectSQLite.getNombreEspec() + "\n";
                }
            }

            if (correcto) {
                int opcion = JOptionPane.showConfirmDialog(null, "Seguro que lo quieres eliminar?", "Eliminar", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    empleadoSQLite = new EmpleadoSQLite(et_dniEmp.getText(), et_emple.getText(), et_apeEmple.getText(), et_NacEmp.getText(), et_fechaContrEmp.getText(), et_NacioEmpl.getText(), et_CargoEmpl.getText());
                    empleadosSQLite.remove(empleadoSQLite);
                    empleadoSQLite.mostrarEmpleadosSQLite(empleadosSQLite);

                    new SQLite.Carga().eliminarEmpleado(empleadoSQLite);

                }
            } else {
                JOptionPane.showMessageDialog(null, "Si quieres borrar a " + et_emple.getText().toUpperCase() + " tienes que cambiar de responsable en los siquientes espectáculos:\n" + espectEmpleadoQueQuieroBorrar.toUpperCase());
            }
            CargaryRefrescarTodoSQLite();

            limpiarCampos();
            bt_GuardarEmple.setVisible(false);
        });
        bt_GuardarEmple.addActionListener(e -> {
            if (!comprobarCamposVaciosEmpleadosSQLite()) {
                panelMensajePersonalizado("Campos Vacíos", "No puede haber campos vacíos. Comprueba todos los campos", 0);
            } else if (!et_dniEmp.isEnabled()) {

                String errores = "";
                if (!validarDNI(et_dniEmp.getText())) {
                    errores += "DNI: No es un dni válido\n";
                    et_dniEmp.getText();

                }
                if (!formatoFecha(et_fecha.getText())) {
                    errores += "FECHA: Introduce una fecha correcta --> 'yyyy-mm-dd'\n";
                    et_NacEmp.setText("");

                }
                if (!formatoFecha(et_fechaContrEmp.getText())) {
                    errores += "FECHA: Introduce una fecha correcta --> 'yyyy-mm-dd'\n";
                    et_fechaContrEmp.setText("");

                }
                if (!errores.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, errores);

                } else {
                    empleadoSQLite = new EmpleadoSQLite(et_dniEmp.getText(), et_emple.getText(), et_apeEmple.getText(), et_NacEmp.getText(), et_fechaContrEmp.getText(), et_NacioEmpl.getText(), et_CargoEmpl.getText());
                    empleadosSQLite.add(empleadoSQLite);
                    empleadoSQLite.mostrarEmpleadosSQLite(empleadosSQLite);

                    new SQLite.Carga().modificarEmpleado(empleadoSQLite);
                }
                CargaryRefrescarTodoSQLite();

            } else {
                String errores = "";
                String nombreRepe = comprobarDNIempleado(empleadosSQLite, et_dniEmp.getText());
                if (!validarDNI(et_dniEmp.getText())) {
                    errores += "DNI: No es un dni válido\n";
                    et_dniEmp.getText();

                }
                if (!formatoFecha(et_fecha.getText())) {
                    errores += "FECHA: Introduce una fecha correcta --> 'yyyy-mm-dd'\n";
                    et_NacEmp.setText("");

                }
                if (!formatoFecha(et_fechaContrEmp.getText())) {
                    errores += "FECHA: Introduce una fecha correcta --> 'yyyy-mm-dd'\n";
                    et_fechaContrEmp.setText("");

                }
                if (!errores.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, errores);

                } else {
                    if (empleadosSQLite.contains(new EmpleadoSQLite(et_dniEmp.getText()))) {
                        JOptionPane.showMessageDialog(null, "Ya existe el empleado '" + nombreRepe.toUpperCase() + "' con el dni '" + et_dniEmp.getText() + "'\nIntroduce otro DNI...");

                    } else {
                        empleadoSQLite = new EmpleadoSQLite(et_dniEmp.getText(), et_emple.getText(), et_apeEmple.getText(), et_NacEmp.getText(), et_fechaContrEmp.getText(), et_NacioEmpl.getText(), et_CargoEmpl.getText());
                        empleadosSQLite.add(empleadoSQLite);
                        empleadoSQLite.mostrarEmpleadosSQLite(empleadosSQLite);
                        new SQLite.Carga().empleadoNuevo(empleadoSQLite);
                    }
                }

                CargaryRefrescarTodoSQLite();

            }
            limpiarCampos();
            bt_GuardarEmple.setVisible(false);
        });

        //Botones de la pestaña espectaculosCliente
        bt_AnadirEspecCliente.addActionListener(e -> {
            comboBoxEspectaculosSQLite.setVisible(true);
            bt_GuardarEspecCliente.setVisible(true);
        });
        bt_BorrarEspecCliente.addActionListener(e -> {
            try {
                if (tabla.getSelectedRow() != -1){
                    TableModel m = tabla.getModel();

                    Cliente cli = listadoClientesEspectaculosSQLite.getSelectedValue();
                    System.out.println("Cliente elegido: " + cli.getNombre() + " " + cli.getDni());
                    System.out.println("Id fila seleccionada: " + tabla.getSelectedRow());

                    int idEspectSQLite = (int) m.getValueAt(tabla.getSelectedRow(), 0);
                    System.out.println("Id del espectaculo: " +idEspectSQLite);

                    espectaculoClienteSQLite = new Espectaculos_Cliente(cli.getDni(), idEspectSQLite);
                    new SQLite.Carga().eliminarEspectaculoClienteSQLite(espectaculoClienteSQLite);
                    espectaculosClientesSQLite.remove(espectaculoClienteSQLite);
                    espectaculoClienteSQLite.mostrarEspectaculosCliente(espectaculosClientesSQLite);
                    tabla.remove(tabla.getSelectedRow());
                    tabla.repaint();

                    CargaryRefrescarTodoSQLite();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un espectáculo del cliente !!");

                }
            } catch (Exception ex){
                //ex.printStackTrace();
            }

        });
        bt_GuardarEspecCliente.addActionListener(e -> {
            if (listadoClientesEspectaculosSQLite.getSelectedValue() != null){
                Cliente cli = listadoClientesEspectaculosSQLite.getSelectedValue();
                EspectaculoSQLite espectSQLite = (EspectaculoSQLite) comboBoxEspectaculosSQLite.getSelectedItem();
                assert espectSQLite != null;
                System.out.println("Espectaculo: " + espectSQLite.getNombreEspec() + ", " + espectSQLite.getNo_Espect());
                espectaculoClienteSQLite = new Espectaculos_Cliente(cli.getDni(), espectSQLite.getNo_Espect());
                String nombreCliSQLite = cli.getNombre() + " " + cli.getApellidos();
                if (espectaculosClientesSQLite.contains(espectaculoClienteSQLite)){
                    JOptionPane.showMessageDialog(null, "Error !!... el cliente '" + nombreCliSQLite.toUpperCase() + "' ya tiene el espectáculo '" + comboBoxEspectaculosSQLite.getSelectedItem() + "'  ");
                } else {
                    espectaculosClientesSQLite.add(espectaculoClienteSQLite);
                    espectaculoClienteSQLite.mostrarEspectaculosCliente(espectaculosClientesSQLite);
                    new SQLite.Carga().anadirClienteEspectaculoSQLite(cli.getDni(), espectSQLite.getNo_Espect());
                    CargaryRefrescarTodoSQLite();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un cliente !!");
            }
            comboBoxEspectaculosSQLite.setVisible(false);
            bt_GuardarEspecCliente.setVisible(false);
        });

        //Botones de la pestaña espectaculosEmpleado
        bt_AnadirEspecEmpleado.addActionListener(e -> {
            comboBoxEspectaculosSQLite2.setVisible(true);
            bt_GuardarEspecEmpleado.setVisible(true);
        });
        bt_BorrarEspecEmpleado.addActionListener(e -> {
            TableModel m = tabla2.getModel();
            boolean correcto = true;
            EmpleadoSQLite empleBorrarSQLite = listaEmpleadosEspectaculosSQLite.getSelectedValue();
            String espectEmpleResponsQueQuieroBorrarSQLite = "";
            int idEspectElegidoSQLite = (int) m.getValueAt(tabla2.getSelectedRow(), 0);
            for (EspectaculoSQLite espectSQLite: espectaculosSQLite){
                if (empleBorrarSQLite.getNombreEmple().equalsIgnoreCase(espectSQLite.getResponsable())&& espectSQLite.getNo_Espect() == idEspectElegidoSQLite){
                    correcto = false;
                    espectEmpleResponsQueQuieroBorrarSQLite = espectSQLite.getNombreEspec();
                }
            }
            if (correcto){
                try {
                    if (tabla2.getSelectedRow() != -1){
                        EmpleadoSQLite empleSQLite = listaEmpleadosEspectaculosSQLite.getSelectedValue();
                        System.out.println("Empleado elegido: " + empleSQLite.getNombreEmple() + " " + empleSQLite.getDniEmple());
                        System.out.println("Id fila seleccionada: " + tabla2.getSelectedRow());
                        int idEspect = (int) m.getValueAt(tabla2.getSelectedRow(), 0);
                        System.out.println("Id del espectaculo: " + idEspect);
                        espectaculoEmpleadoSQLite = new Espectaculos_Empleado(empleSQLite.getDniEmple(), idEspectElegidoSQLite);
                        new SQLite.Carga().eliminarEmpleadoEspectaculoSQLite(espectaculoEmpleadoSQLite);
                        espectaculosEmpleadosSQLite.remove(espectaculoEmpleadoSQLite);
                        espectaculoEmpleadoSQLite.mostrarEspectaculosEmpleados(espectaculosEmpleadosSQLite);
                        CargaryRefrescarTodoSQLite();
                    } else {
                        JOptionPane.showMessageDialog(null, "Selecciona un espectáculo del cliente !!");

                    }
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
            } else {
                System.out.println(espectEmpleResponsQueQuieroBorrarSQLite);
                JOptionPane.showMessageDialog(null, "No puedes borrar '" + espectEmpleResponsQueQuieroBorrarSQLite.toUpperCase() + "' porque " + empleBorrarSQLite.getNombreEmple().toUpperCase()+ " es el  responsable, tienes que cambiar ANTES de responsable.");
            }
        });
        bt_GuardarEspecEmpleado.addActionListener(e -> {
            if (listaEmpleadosEspectaculosSQLite.getSelectedValue() != null){
                EmpleadoSQLite empleSQLite = listaEmpleadosEspectaculosSQLite.getSelectedValue();
                EspectaculoSQLite espectSQLite = (EspectaculoSQLite) comboBoxEspectaculosSQLite2.getSelectedItem();
                assert espectSQLite != null;
                System.out.println("Espectaculo: " + espectSQLite.getNombreEspec() + ", " + espectSQLite.getNo_Espect());
                Espectaculos_Empleado especEmpleSQLite = new Espectaculos_Empleado(empleSQLite.getDniEmple(), espectSQLite.getNo_Espect());
                String nombreEmpleSQLite = empleSQLite.getNombreEmple() + " " + empleSQLite.getApeEmple();

                if (espectaculosEmpleadosSQLite.contains(especEmpleSQLite)){
                    JOptionPane.showMessageDialog(null, "Error !!... el empleado '" + nombreEmpleSQLite.toUpperCase() + "' ya tiene el espectáculo '" + comboBoxEspectaculosSQLite2.getSelectedItem() + "'  ");
                } else {
                    espectaculosEmpleadosSQLite.add(especEmpleSQLite);
                    especEmpleSQLite.mostrarEspectaculosEmpleados(espectaculosEmpleadosSQLite);
                    new SQLite.Carga().anadirEmpleadoEspectaculoSQLite(empleSQLite.getDniEmple(), espectSQLite.getNo_Espect());
                    CargaryRefrescarTodoSQLite();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un cliente !!");
            }
            comboBoxEspectaculosSQLite2.setVisible(false);
            bt_GuardarEspecEmpleado.setVisible(false);
        });

        //Boton pestaña información de la base de datos
        infoButton.addActionListener(e -> {
            String infoBaseDatos = new SQLite.Carga().infoMySql(textAreaInfoMySql);

            textAreaInfoMySql.setText(infoBaseDatos);
        });
        bt_salir.addActionListener(e -> {
            VAccesoSQLite.estasConectado=false;
            JOptionPane.showMessageDialog(null, "Adios");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VPanelPrincipal);
            topFrame.dispose();
            //System.exit(0);
        });



    }


    //Funciones de carga y refrescado de todos los datos de SQLite
    public void CargaryRefrescarTodoSQLite() {
        cargaDatosSQLite();
        cargar_tablas_relaciones_SQLite();
        cargar_j_list_clientesSQLite();
        cargar_j_list_empleadosSQLite();
        cargar_j_list_espectaculosSQLite();
        cargar_combobox_responsablesSQLite();
        cargar_combobox_espectaculosClientesSQLite();
        cargar_j_list_jtable_espectaculosClientesSQLite();
        cargar_j_list_jtable_espectaculosEmpleadosSQLite();
    }
    public void cargaDatosSQLite() {
        empleadosSQLite = new SQLite.Carga().listaEmpleadosSQLite();

        espectaculosSQLite = new SQLite.Carga().listaEspectaculosSQLite();

        clientesSQLite = new SQLite.Carga().listaClientesSQLite();

        espectaculosClientesSQLite = new SQLite.Carga().listaClientesEspectaculosSQLite();

        espectaculosEmpleadosSQLite = new SQLite.Carga().listaEmpleadosEspetaculosSQLite();
    }
    private void cargar_tablas_relaciones_SQLite() {
        for (int i = 0; i < espectaculosSQLite.size(); i++) {
            for (int j = 0; j < espectaculosClientesSQLite.size(); j++) {

                for (int k = 0; k < clientesSQLite.size(); k++) {
                    if (espectaculosSQLite.get(i).getNo_Espect() == espectaculosClientesSQLite.get(j).getEspectaculo() && clientesSQLite.get(k).getDni().equalsIgnoreCase(espectaculosClientesSQLite.get(j).getCliente())) {
                        Cliente c = new Cliente(clientesSQLite.get(k).getDni(), clientesSQLite.get(k).getNombre(), clientesSQLite.get(k).getApellidos(), clientesSQLite.get(k).getEdad());
                        EspectaculoSQLite eSQLite = new EspectaculoSQLite(espectaculosSQLite.get(i).getNo_Espect(), espectaculosSQLite.get(i).getNombreEspec(), espectaculosSQLite.get(i).getAforo(), espectaculosSQLite.get(i).getDescripcion(), espectaculosSQLite.get(i).getLugar(), espectaculosSQLite.get(i).getFecha_Espec(), espectaculosSQLite.get(i).getHorario_espec(), espectaculosSQLite.get(i).getPrecio(), espectaculosSQLite.get(i).getResponsable());
                        // Añado el cliente al ArrayList de clientes dentro del espectáculo elegido y luego le hago un 'set' al array de espectáculos del cliente
                        espectaculosSQLite.get(i).getClientes().add(c);
                        c.setEspectaculosSQLite(clientesSQLite.get(k).getEspectaculosSQLite());
                        // Añado el espectaculo al Array de espectáculos del cliente elegido y luego le hago un 'set' al array de clientes del espectáculo
                        clientesSQLite.get(k).getEspectaculosSQLite().add(eSQLite);
                        eSQLite.setClientes(espectaculosSQLite.get(i).getClientes());
                    }
                }
            }
        }
        for (int i = 0; i < espectaculosSQLite.size(); i++) {
            for (int j = 0; j < espectaculosEmpleadosSQLite.size(); j++) {
                for (int k = 0; k < empleadosSQLite.size(); k++) {
                    if (espectaculosSQLite.get(i).getNo_Espect() == espectaculosEmpleadosSQLite.get(j).getEspectaculo() && empleadosSQLite.get(k).getDniEmple().equalsIgnoreCase(espectaculosEmpleadosSQLite.get(j).getEmpleado())) {
                        EmpleadoSQLite empleado = new EmpleadoSQLite(empleadosSQLite.get(k).getDniEmple(), empleadosSQLite.get(k).getNombreEmple(), empleadosSQLite.get(k).getApeEmple(), empleadosSQLite.get(k).getFechaNac(), empleadosSQLite.get(k).getFechaContr(), empleadosSQLite.get(k).getNacionalidad(), empleadosSQLite.get(k).getCargo());
                        EspectaculoSQLite e = new EspectaculoSQLite(espectaculosSQLite.get(i).getNo_Espect(), espectaculosSQLite.get(i).getNombreEspec(), espectaculosSQLite.get(i).getAforo(), espectaculosSQLite.get(i).getDescripcion(), espectaculosSQLite.get(i).getLugar(), espectaculosSQLite.get(i).getFecha_Espec(), espectaculosSQLite.get(i).getHorario_espec(), espectaculosSQLite.get(i).getPrecio(), espectaculosSQLite.get(i).getResponsable());
                        // Añado el empleado al ArrayList de empleados dentro del espectaculo elegido y luego le hago un 'set' al array de espectáculos del empleado
                        espectaculosSQLite.get(i).getEmpleadosSQLite().add(empleado);
                        empleado.setEspectaculosSQLite(empleadosSQLite.get(k).getEspectaculosSQLite());
                        // Añado el espectaculo al Array de espectáculos del empleado y luego le hago un 'set' al array de empleados del espectáculo
                        empleadosSQLite.get(k).getEspectaculosSQLite().add(e);
                        e.setEmpleadosSQLite(espectaculosSQLite.get(k).getEmpleadosSQLite());
                    }
                }
            }
        }
    }
    private void cargar_j_list_clientesSQLite() {
        DefaultListModel<Cliente> model = new DefaultListModel<>();
        for (Cliente cliente : clientesSQLite) {
            model.addElement(cliente);
        }
        list_clientesSQLite.setModel(model);
    }
    private void cargar_j_list_empleadosSQLite() {
        DefaultListModel<EmpleadoSQLite> model = new DefaultListModel<>();
        for (EmpleadoSQLite empleado : empleadosSQLite) {
            model.addElement(empleado);
        }
        listaEmpleadosSQLite.setModel(model);
    }
    private void cargar_j_list_espectaculosSQLite() {
        DefaultListModel<EspectaculoSQLite> model = new DefaultListModel<>();
        for (EspectaculoSQLite espectaculo : espectaculosSQLite) {
            model.addElement(espectaculo);
        }
        listaEspectaculosSQLite.setModel(model);
    }
    private void cargar_combobox_responsablesSQLite() {
        // Carga de los empleados en un model de combobox para seleccionar el responsable, luefo le paso el modelo al objeto comboBox de la ventana
        DefaultComboBoxModel<EmpleadoSQLite> empleModel = new DefaultComboBoxModel<>();
        for (EmpleadoSQLite empleado : empleadosSQLite) {
            empleModel.addElement(empleado);
        }
        comboBoxEmpleadosSQLite.setModel(empleModel);
    }
    private void cargar_combobox_espectaculosClientesSQLite() {
        // Carga de los clientes en un model de combobox para seleccionar el responsable, luefo le paso el modelo al objeto comboBox de la ventana
        DefaultComboBoxModel<EspectaculoSQLite> espectaculoModel = new DefaultComboBoxModel<>();
        for (EspectaculoSQLite espectaculo : espectaculosSQLite) {
            espectaculoModel.addElement(espectaculo);
        }
        comboBoxEspectaculosSQLite.setModel(espectaculoModel);
        comboBoxEspectaculosSQLite2.setModel(espectaculoModel);
    }
    private void cargar_j_list_jtable_espectaculosClientesSQLite() {
        // Jlist clientes Jtable espectaculos
        DefaultListModel<Cliente> modelClienteEspectaculo = new DefaultListModel<>();
        for (Cliente c : clientesSQLite) {
            modelClienteEspectaculo.addElement(c);
        }
        listadoClientesEspectaculosSQLite.setModel(modelClienteEspectaculo);
        tabla = new JTable();
        tabla.setModel(new ListaClientesEspectaculosSQLite(new Cliente()));
        resultadoClientesEspectaculosSQLite.setViewportView(tabla);
        listadoClientesEspectaculosSQLite.addListSelectionListener(e -> {
            tabla.setModel(new ListaClientesEspectaculosSQLite(listadoClientesEspectaculosSQLite.getSelectedValue()));
            System.out.println(listadoClientesEspectaculosSQLite.getSelectedValue().getEspectaculosSQLite());
        });
    }
    private void cargar_j_list_jtable_espectaculosEmpleadosSQLite() {
        // Jlist empleados Jtable espectaculos
        DefaultListModel<EmpleadoSQLite> modelEmpleadoEspectaculo = new DefaultListModel<>();
        for (EmpleadoSQLite e : empleadosSQLite) {
            modelEmpleadoEspectaculo.addElement(e);
        }
        listaEmpleadosEspectaculosSQLite.setModel(modelEmpleadoEspectaculo);
        tabla2 = new JTable();
        tabla2.setModel(new ListaEmpleadosEspectaculosSQLite(new EmpleadoSQLite()));
        resultadoEmpleadosEspectaculosSQLite.setViewportView(tabla2);
        listaEmpleadosEspectaculosSQLite.addListSelectionListener(e -> {
            tabla2.setModel(new ListaEmpleadosEspectaculosSQLite(listaEmpleadosEspectaculosSQLite.getSelectedValue()));
            System.out.println(listaEmpleadosEspectaculosSQLite.getSelectedValue().getEspectaculosSQLite());
        });
    }
    public void actualizarClientesSQLite() {
        Cliente cliente = list_clientesSQLite.getSelectedValue();

        if (cliente != null) {
            campo_dni.setText(cliente.getDni());
            campo_nombre.setText(cliente.getNombre());
            campo_apellido.setText(cliente.getApellidos());
            campo_edad.setText(String.valueOf(cliente.getEdad()));
        }
    }
    public void actualizarEmpleadosSQLite() {
        EmpleadoSQLite empleado = listaEmpleadosSQLite.getSelectedValue();

        if (empleado != null) {
            et_dniEmp.setText(empleado.getDniEmple());
            et_emple.setText(empleado.getNombreEmple());
            et_apeEmple.setText(empleado.getApeEmple());
            et_NacEmp.setText(String.valueOf(empleado.getFechaNac()));
            et_fechaContrEmp.setText(String.valueOf(empleado.getFechaContr()));
            et_NacioEmpl.setText(String.valueOf(empleado.getNacionalidad()));
            et_CargoEmpl.setText(String.valueOf(empleado.getCargo()));
        }
    }
    public void actualizarEspectaculosSQLite() {
        EspectaculoSQLite espectaculo = listaEspectaculosSQLite.getSelectedValue();

        if (espectaculo != null) {
            et_ID_Espec.setText(String.valueOf(espectaculo.getNo_Espect()));
            et_Espectaculo.setText(espectaculo.getNombreEspec());
            et_aforo.setText(String.valueOf(espectaculo.getAforo()));
            et_Descripcion.setText(espectaculo.getDescripcion());
            et_lugar.setText(espectaculo.getLugar());
            et_fecha.setText(String.valueOf(espectaculo.getFecha_Espec()));
            et_horario.setText(String.valueOf(espectaculo.getHorario_espec()));
            et_precio.setText(String.valueOf(espectaculo.getPrecio()));
            comboBoxEmpleadosSQLite.setSelectedItem(espectaculo.getResponsable());
        }
    }


    //Funciones para comprobar si los datos añadidos son del formato adecuado
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
    private String comprobarDNIempleado(ArrayList<EmpleadoSQLite> empleadosSQLite, String et_dniEmp) {
        String nombreRepeSQLite = "";
        for (EmpleadoSQLite empleSQLite: empleadosSQLite){
            if (empleSQLite.getDniEmple().equalsIgnoreCase(et_dniEmp)){
                nombreRepeSQLite = empleSQLite.getNombreEmple() + " " + empleSQLite.getApeEmple();
            }
        }
        return nombreRepeSQLite;
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
    private boolean comprobarCamposVaciosEspectaculosSQLite() {
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
    private boolean comprobarCamposVaciosClientesSQLite() {

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
    private boolean comprobarCamposVaciosEmpleadosSQLite() {

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

    public JPanel getVPanelPrincipal() {
        return VPanelPrincipal;
    }

}
