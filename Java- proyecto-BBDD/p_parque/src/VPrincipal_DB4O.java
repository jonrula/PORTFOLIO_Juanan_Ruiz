import Models.*;
import MySQL.Carga;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class VPrincipal_DB4O {
    private JPanel VPanelPrincipal;
    private JTabbedPane tabbedPane1;
    private JPanel PestanaEspectaculos;
    private JList<EspectaculoDB4o> listaEspectaculos;
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
    private JList<ClienteDB4o> list_clientes;
    private JButton bt_nuevoCli;
    private JButton bt_modificarCli;
    private JButton bt_eliminarCli;
    private JPanel PestanaEmpleados;
    private JList<EmpleadoDB4o> listaEmpleados;
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
    private JList<ClienteDB4o> listadoClientesEspectaculos;
    private JScrollPane resultadoClientesEspectaculos;
    private JList<EmpleadoDB4o> listaEmpleadosEspectaculos;
    private JScrollPane resultadoEmpleadosEspectaculos;
    private JLabel lb_responsable;
    private JComboBox<EmpleadoDB4o> comboBoxEmpleadosDB4o;
    private JButton bt_salir;
    private JButton bt_AnadirEspecCliente;
    private JButton bt_BorrarEspecCliente;
    private JComboBox<EspectaculoDB4o> comboBoxEspectaculosDB4o;
    private JButton bt_GuardarEspecCliente;
    private JComboBox<EspectaculoDB4o> comboBoxEspectaculos2DB4o;
    private JButton bt_AnadirEmpleEspec;
    private JButton bt_BorrarEmpleEspec;
    private JButton bt_GuardarEmpleEspec;


    private ArrayList<EspectaculoDB4o> espectaculos = new ArrayList<>();
    private ArrayList<ClienteDB4o> clientes = new ArrayList<>();
    private ArrayList<ClienteEspectaculoDB4o> clientesEspectaculos = new ArrayList<>();
    private ArrayList<EmpleadoEspectaculoDB4o> empleadosEspectaculos = new ArrayList<>();
    private ArrayList<EmpleadoDB4o> empleados = new ArrayList<>();

    private ClienteDB4o cliente;
    private EmpleadoDB4o empleado;
    private EspectaculoDB4o espectaculo;
    private EmpleadoEspectaculoDB4o espectaculoEmpleado;
    private ClienteEspectaculoDB4o espectaculosCliente;

    private JTable tabla;
    private JTable tabla2;

    public VPrincipal_DB4O() {
        /*
            //Comprobar si el usuario conectado es el Admin y mostrar u ocultar la informacion de la base de datos
            if(VAccesoMySQL.usuarioMySQL.equalsIgnoreCase("Admin") && VAccesoMySQL.passwordBDMySQL.equalsIgnoreCase("0000")){
                infoButton.setEnabled(true);
            } else {
                infoButton.setEnabled(false);
                textAreaInfoMySql.setText("Acceso Denegado");
                textAreaInfoMySql.setEditable(false);
            }
        */
        CargaryRefrescarTodo();

        //Cargar las listas
        list_clientes.addListSelectionListener(e -> {
            actualizarClientes();
        });
        listaEmpleados.addListSelectionListener(e -> {
            actualizarEmpleados();
        });
        listaEspectaculos.addListSelectionListener(e -> {
            actualizarEspectaculos();
        });


        //Botones de clientes
        bt_nuevoCli.addActionListener(e -> {
            campo_dni.setEnabled(true);
            limpiarCampos();
            bt_guardarCli.setVisible(true);
            list_clientes.setEnabled(false);
        });
        bt_modificarCli.addActionListener(e -> {
            campo_dni.setEnabled(false);
            bt_guardarCli.setVisible(true);
        });
        bt_eliminarCli.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(null, "Seguro que lo quieres eliminar?", "Eliminar", JOptionPane.YES_NO_OPTION);

            ClienteDB4o cliente = list_clientes.getSelectedValue();
            if (opcion == JOptionPane.YES_OPTION) {
                new DB4o.Borrar().borrarCliente(cliente);
                clientes.remove(cliente);
                for (ClienteDB4o clienteDB4o : clientes) {
                    System.out.println(clienteDB4o);
                }
            }
            CargaryRefrescarTodo();
            limpiarCampos();
            bt_guardarCli.setVisible(false);
        });
        bt_guardarCli.addActionListener(e -> {
            // list_clientes.clearSelection();

            if (!comprobarCamposVaciosCliente()) {
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
                cliente = new ClienteDB4o(campo_dni.getText(), campo_nombre.getText(), campo_apellido.getText(), edadInt);
                clientes.add(cliente);
                for (ClienteDB4o cliente1 : clientes) {
                    System.out.println(cliente1);
                }
                new DB4o.Insertar_Editar().insertarEditarCliente(cliente);

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
                        cliente = new ClienteDB4o(campo_dni.getText(), campo_nombre.getText(), campo_apellido.getText(), edadInt);
                        clientes.add(cliente);

                    }
                    for (ClienteDB4o cliente1 : clientes) {
                        System.out.println(cliente1);
                    }
                    new DB4o.Insertar_Editar().insertarEditarCliente(cliente);


                }
                CargaryRefrescarTodo();
                limpiarCampos();
                bt_guardarCli.setVisible(false);
            }
        });

        //Botones de Empleados
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
            boolean correct = true;
            EmpleadoDB4o empleDB4o = listaEmpleados.getSelectedValue();
            String espectaculosEmpleadoQueQuieroBorrarDB4o = "";
            for (EspectaculoDB4o espectDB4o : espectaculos) {
                if (espectDB4o.getOrganizador().toString().equals(empleDB4o.getNombre())) {
                    System.out.println("Empleado responsable espectáculo: " + espectDB4o.getOrganizador().toString());
                    System.out.println("Empleado seleccionado de la lista: " + empleDB4o.getNombre());
                    correct = false;
                    espectaculosEmpleadoQueQuieroBorrarDB4o += espectDB4o.getNombre() + "\n";
                }
            }
            if (correct) {

            } else {
                //JOptionPane.showMessageDialog(null, "Si quieres borrar a " + et_emple.getText().toUpperCase() + " tienes que cambiar de responsable en los siquientes espectáculos:\n" + espectaculosEmpleadoQueQuieroBorrarDB4o.toUpperCase());
            }
            int opcion = JOptionPane.showConfirmDialog(null, "Seguro que lo quieres eliminar?", "Eliminar", JOptionPane.YES_NO_OPTION);

            EmpleadoDB4o empleadoDB4o = listaEmpleados.getSelectedValue();
            if (opcion == JOptionPane.YES_OPTION) {
                new DB4o.Borrar().borrarEmpleado(empleadoDB4o);
                empleados.remove(empleadoDB4o);
                for (EmpleadoDB4o empleadoDB4o1 : empleados) {
                    System.out.println(empleadoDB4o1);
                }
            }


            CargaryRefrescarTodo();
            limpiarCampos();
            bt_GuardarEmple.setVisible(false);
        });
        bt_GuardarEmple.addActionListener(e -> {
            if (!comprobarCamposVaciosEmpleado()) {
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

                    empleado = new EmpleadoDB4o(et_dniEmp.getText(), et_emple.getText(), et_apeEmple.getText(), fechaNacDate, fechaContDate, et_NacioEmpl.getText(), et_CargoEmpl.getText());
                    empleados.add(empleado);
                    for (EmpleadoDB4o empleadoDB4o1 : empleados) {
                        System.out.println(empleadoDB4o1);
                    }
                    new DB4o.Insertar_Editar().insertarEditarEmpleado(empleado);
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
                    if (empleados.contains(new EmpleadoDB4o(et_dniEmp.getText()))) {
                        JOptionPane.showMessageDialog(null, "Ya existe el empleado '" + nombreRepe.toUpperCase() + "' con el dni '" + et_dniEmp.getText() + "'\nIntroduce otro DNI...");

                    } else {
                        Date fechaNacDate = Date.valueOf(fechaNacString);
                        Date fechaContDate = Date.valueOf(fechaContString);

                        empleado = new EmpleadoDB4o(et_dniEmp.getText(), et_emple.getText(), et_apeEmple.getText(), fechaNacDate, fechaContDate, et_NacioEmpl.getText(), et_CargoEmpl.getText());
                        empleados.add(empleado);
                        for (EmpleadoDB4o empleadoDB4o1 : empleados) {
                            System.out.println(empleadoDB4o1);
                        }
                        new DB4o.Insertar_Editar().insertarEditarEmpleado(empleado);
                    }
                }

                CargaryRefrescarTodo();

            }
            limpiarCampos();
            bt_GuardarEmple.setVisible(false);
        });


        //Botones de espectaculos
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

            EspectaculoDB4o espectaculoDB4o = listaEspectaculos.getSelectedValue();
            if (opcion == JOptionPane.YES_OPTION) {
                new DB4o.Borrar().borrarEspectaculo(espectaculoDB4o);
                espectaculos.remove(espectaculoDB4o);
                for (EmpleadoDB4o empleadoDB4o1 : empleados) {
                    System.out.println(empleadoDB4o1);
                }
            }

            CargaryRefrescarTodo();
            limpiarCampos();
            bt_GuardarEspectaculo.setVisible(false);
        });
        bt_GuardarEspectaculo.addActionListener(e -> {
            if (!comprobarCamposVaciosEspectaculo()) {
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
                    String idString = String.valueOf(et_ID_Espec.getText());
                    int idInt = Integer.parseInt(idString);
                    int aforoInt = Integer.parseInt(aforoString);
                    double precioDouble = Double.parseDouble(precioString);
                    //String responsable = String.valueOf(comboBoxEmpleadosDB4o.getSelectedItem());
                    EmpleadoDB4o empDB4o = (EmpleadoDB4o) comboBoxEmpleadosDB4o.getSelectedItem();

                    espectaculo = new EspectaculoDB4o(idInt, et_Espectaculo.getText(), aforoInt, et_Descripcion.getText(), et_lugar.getText(), String.valueOf(et_fecha.getText()), et_horario.getText(), precioDouble, empDB4o);
                    espectaculos.add(espectaculo);
                    for (EspectaculoDB4o espectaculoDB4o1 : espectaculos) {
                        System.out.println(espectaculoDB4o1);
                    }
                    new DB4o.Insertar_Editar().insertarEditarEspectaculo(espectaculo);
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
                    String idString = String.valueOf(et_ID_Espec.getText());
                    int idInt = Integer.parseInt(idString);
                    int aforoInt = Integer.parseInt(aforoString);
                    double precioDouble = Double.parseDouble(precioString);
                    //String responsable = String.valueOf(comboBoxEmpleadosDB4o.getSelectedItem());
                    EmpleadoDB4o empDB4o = (EmpleadoDB4o) comboBoxEmpleadosDB4o.getSelectedItem();

                    espectaculo = new EspectaculoDB4o(idInt, et_Espectaculo.getText(), aforoInt, et_Descripcion.getText(), et_lugar.getText(), String.valueOf(et_fecha.getText()), et_horario.getText(), precioDouble, empDB4o);
                    espectaculos.add(espectaculo);
                    for (EspectaculoDB4o espectaculoDB4o1 : espectaculos) {
                        System.out.println(espectaculoDB4o1);
                    }
                    new DB4o.Insertar_Editar().insertarEditarEspectaculo(espectaculo);
                }
            }
            limpiarCampos();
            bt_GuardarEspectaculo.setVisible(false);
            CargaryRefrescarTodo();
        });

        //Botones de CLientesEspectaculos
        bt_AnadirEspecCliente.addActionListener(e -> {
            comboBoxEspectaculosDB4o.setVisible(true);
            bt_GuardarEspecCliente.setVisible(true);
        });
        bt_BorrarEspecCliente.addActionListener(e -> {

        });
        bt_GuardarEspecCliente.addActionListener(e -> {

        });

        //Botones EmpleadosEspectaculos
        bt_AnadirEmpleEspec.addActionListener(e -> {

        });
        bt_BorrarEmpleEspec.addActionListener(e -> {

        });
        bt_GuardarEmpleEspec.addActionListener(e -> {

        });

        //Botón de salir
        bt_salir.addActionListener(e -> {
            //VAccesoDB4o.estasConectado=false;
            JOptionPane.showMessageDialog(null, "Adios");
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VPanelPrincipal);
            topFrame.dispose();
        });
    }

    //Funciones para cargar y refrescar los datos
    public void CargaryRefrescarTodo() {
        CargarDatos();
        cargar_j_list_clientes();
        cargar_j_list_empleados();
        cargar_j_list_espectaculos();
        cargar_combobox_responsables();
        cargar_combobox_espectaculosClientes();
        cargar_j_list_jtable_espectaculosClientes();
        cargar_j_list_jtable_espectaculosEmpleados();
    }

    public void CargarDatos() {
        clientes = new DB4o.CargarDatos().cargarClientes();
        empleados = new DB4o.CargarDatos().cargarEmpleados();
        espectaculos = new DB4o.CargarDatos().cargarEspectaculos();
        clientesEspectaculos = new DB4o.CargarDatos().cargarEspectaculosCliente(listadoClientesEspectaculos.getSelectedValue());
        empleadosEspectaculos = new DB4o.CargarDatos().cargarEspectaculosEmpleado(listaEmpleadosEspectaculos.getSelectedValue());
    }

    private void cargar_j_list_clientes() {
        DefaultListModel<ClienteDB4o> model = new DefaultListModel<>();
        for (ClienteDB4o cliente1 : clientes) {
            model.addElement(cliente1);
        }
        list_clientes.setModel(model);
    }

    private void cargar_j_list_empleados() {
        DefaultListModel<EmpleadoDB4o> model = new DefaultListModel<>();
        for (EmpleadoDB4o empleado : empleados) {
            model.addElement(empleado);
        }
        listaEmpleados.setModel(model);
    }

    private void cargar_j_list_espectaculos() {
        DefaultListModel<EspectaculoDB4o> model = new DefaultListModel<>();
        for (EspectaculoDB4o espectaculo : espectaculos) {
            model.addElement(espectaculo);
        }
        listaEspectaculos.setModel(model);

    }

    private void cargar_combobox_responsables() {
        DefaultComboBoxModel<EmpleadoDB4o> model = new DefaultComboBoxModel<>();
        for (EmpleadoDB4o empleado1 : empleados) {
            //String nombreEmp = empleado1.getNombre();
            model.addElement(empleado1);
        }
        comboBoxEmpleadosDB4o.setModel(model);
    }

    private void cargar_combobox_espectaculosClientes() {
        DefaultComboBoxModel<EspectaculoDB4o> espectaculoDB4oModel = new DefaultComboBoxModel<>();

        for (EspectaculoDB4o espectaculoDB4o : espectaculos) {
            espectaculoDB4oModel.addElement(espectaculoDB4o);
        }
        comboBoxEspectaculosDB4o.setModel(espectaculoDB4oModel);
        comboBoxEspectaculos2DB4o.setModel(espectaculoDB4oModel);
    }

    private void cargar_j_list_jtable_espectaculosClientes() {
        DefaultListModel<ClienteDB4o> model = new DefaultListModel<>();
        for (ClienteDB4o cliente1 : clientes) {
            model.addElement(cliente1);
        }
        listadoClientesEspectaculos.setModel(model);

        tabla = new JTable();
        tabla.setModel(new ListaClientesEspectaculosDB4o(new ClienteDB4o()));
        resultadoClientesEspectaculos.setViewportView(tabla);

        listadoClientesEspectaculos.addListSelectionListener(e -> {
            //clis_esp();
            ClienteDB4o cliente = listadoClientesEspectaculos.getSelectedValue();
            tabla.setModel(new ListaClientesEspectaculosDB4o(cliente));
            //System.out.println(jlist_cliEsp.getSelectedValue().getEspectaculos());
        });
    }

    private void cargar_j_list_jtable_espectaculosEmpleados() {
        DefaultListModel<EmpleadoDB4o> model = new DefaultListModel<>();
        for (EmpleadoDB4o empleado1 : empleados) {
            model.addElement(empleado1);
        }
        listaEmpleadosEspectaculos.setModel(model);

        tabla2 = new JTable();
        tabla2.setModel(new ListaEmpleadosEspectaculosDB4o(new EmpleadoDB4o()));
        resultadoEmpleadosEspectaculos.setViewportView(tabla2);

        listaEmpleadosEspectaculos.addListSelectionListener(e -> {
            EmpleadoDB4o empleado = listaEmpleadosEspectaculos.getSelectedValue();
            tabla2.setModel(new ListaEmpleadosEspectaculosDB4o(empleado));
        });
    }

    private void actualizarClientes() {
        ClienteDB4o clienteDB4o = list_clientes.getSelectedValue();

        if (clienteDB4o != null) {
            campo_dni.setText(clienteDB4o.getDni());
            campo_nombre.setText(clienteDB4o.getNombre());
            campo_apellido.setText(clienteDB4o.getApellidos());
            campo_edad.setText(String.valueOf(clienteDB4o.getEdad()));
        }
        //campo_dni.setEnabled(false);
    }

    private void actualizarEmpleados() {
        EmpleadoDB4o empleadoDB4o = listaEmpleados.getSelectedValue();
        if (empleadoDB4o != null) {
            et_dniEmp.setText(empleadoDB4o.getDni());
            et_emple.setText(empleadoDB4o.getNombre());
            et_apeEmple.setText(empleadoDB4o.getPrimerApellido());
            et_NacEmp.setText(empleadoDB4o.getFechaNac());
            et_fechaContrEmp.setText(empleadoDB4o.getFechaContrato());
            et_NacioEmpl.setText(empleadoDB4o.getNacionalidad());
            et_CargoEmpl.setText(empleadoDB4o.getCargo());
        }
    }

    private void actualizarEspectaculos() {
        EspectaculoDB4o espectaculoDB4o = listaEspectaculos.getSelectedValue();
        if (espectaculoDB4o != null) {
            et_ID_Espec.setText(String.valueOf(espectaculoDB4o.getId()));
            et_Espectaculo.setText(espectaculoDB4o.getNombre());
            et_aforo.setText(String.valueOf(espectaculoDB4o.getAforo()));
            et_Descripcion.setText(espectaculoDB4o.getDescripcion());
            et_lugar.setText(espectaculoDB4o.getLugar());
            et_fecha.setText(espectaculoDB4o.getFecha());
            et_horario.setText(espectaculoDB4o.getHorario());
            et_precio.setText(String.valueOf(espectaculoDB4o.getCoste()));
            comboBoxEmpleadosDB4o.setSelectedItem(espectaculoDB4o.getOrganizador());

        }
    }

    //Funciones de comprobar y limpiar campos
    private boolean comprobarCamposVaciosCliente() {
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

    private boolean comprobarCamposVaciosEmpleado() {
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

    private boolean comprobarCamposVaciosEspectaculo() {
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
        campos.add(et_Descripcion);

        for (JTextField campo : campos) {
            if (campo.getText().equalsIgnoreCase("")) {
                hayDato = false;
            }
        }
        return hayDato;
    }

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
        comboBoxEmpleadosDB4o.removeAllItems();
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

    public static String comprobarDNIcliente(ArrayList<ClienteDB4o> clientes, String campo_dni) {
        String nombreRepe = "";
        for (ClienteDB4o cliente : clientes) {
            if (cliente.getDni().equalsIgnoreCase(campo_dni)) {
                nombreRepe = cliente.getNombre() + " " + cliente.getApellidos();
            }
        }
        return nombreRepe;
    }

    public static String comprobarDNIempleado(ArrayList<EmpleadoDB4o> empleados, String et_dniEmp) {
        String nombreRepe = "";
        for (EmpleadoDB4o empleado : empleados) {
            if (empleado.getDni().equalsIgnoreCase(et_dniEmp)) {
                nombreRepe = empleado.getNombre() + " " + empleado.getPrimerApellido();
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
