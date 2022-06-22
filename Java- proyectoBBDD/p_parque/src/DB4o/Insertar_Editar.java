package DB4o;

import Models.*;
import com.db4o.*;
import com.db4o.ext.Db4oException;

import javax.swing.*;

public class Insertar_Editar {
    public void insertarEditarCliente(ClienteDB4o cli) {
        ObjectContainer bd = new Conn().conexion();
        String titulo = "";
        String mensaje = "";
        int tipo = 0;
        boolean error = false;

        if (bd != null) {
            ClienteDB4o cliQuery = new ClienteDB4o(cli.getDni());
            try {
                ObjectSet<ClienteDB4o> result = bd.queryByExample(cliQuery);

                if (result.size() == 0) {
                    bd.store(cli);
                    titulo = "Cliente insertado";
                    mensaje = "Cliente '" + cli.getDni() + "' insertado con éxito.";
                } else {
                    ClienteDB4o existe = result.next();
                    existe.editarClienteDb4o(cli);
                    bd.store(existe);
                    titulo = "Cliente editado";
                    mensaje = "Cliente '" + cli.getDni() + "' editado con éxito.";
                }
                tipo = 1;

            } catch (Db4oException ex) {
                error = true;
            }
            bd.close();

        } else {
            error = true;
        }

        if (error) {
            titulo = "Error al insertar/editar";
            mensaje = "Error al insertar/editar el cliente '" + cli.getDni() + "'.";
            tipo = 0;
        }
        mensajePersonalizado(titulo, mensaje, tipo);
    }

    public void insertarEditarEmpleado(EmpleadoDB4o empleado) {

        ObjectContainer bd = new Conn().conexion();
        String titulo = "";
        String mensaje = "";
        int tipo = 0;
        boolean error = false;

        if (bd != null) {
            EmpleadoDB4o empQuery = new EmpleadoDB4o(empleado.getDni());
            try {
                ObjectSet<EmpleadoDB4o> result = bd.queryByExample(empQuery);
                if (result.size() == 0) {
                    bd.store(empleado);
                    titulo = "Empleado insertado";
                    mensaje = "Empleado '" + empleado.getDni() + "' insertado con éxito.";
                } else {
                    EmpleadoDB4o existe = result.next();
                    existe.editarEmpleDb4o(empleado);
                    bd.store(existe);
                    titulo = "Empleado editado";
                    mensaje = "Empleado '" + empleado.getDni() + "' editado con éxito.";
                }
            } catch (Db4oException e) {
                error = true;
            }
            bd.close();

        } else {
            error = true;
        }
        if (error) {
            titulo = "Error al insertar/editar";
            mensaje = "Error al insertar/editar el empleado '" + empleado.getDni() + "'.";
            tipo = 0;
        }
        mensajePersonalizado(titulo, mensaje, tipo);
    }

    public void insertarEditarEspectaculo(EspectaculoDB4o esp) {

        ObjectContainer bd = new Conn().conexion();
        String titulo = "";
        String mensaje = "";
        int tipo = 0;
        boolean error = false;

        if (bd != null) {
            EspectaculoDB4o espQuery = new EspectaculoDB4o(esp.getId());
            try {
                ObjectSet<EspectaculoDB4o> result = bd.queryByExample(espQuery);

                if (result.size() == 0) {
                    bd.store(esp);
                    titulo = "Espectáculo insertado";
                    mensaje = "Espectáculo '" + esp.getId() + "' insertado con éxito.";
                } else {
                    EspectaculoDB4o existe = result.next();
                    existe.editarEspectaculoDb4o(esp);
                    bd.store(existe);
                    titulo = "Espectáculo editado";
                    mensaje = "Espectáculo '" + esp.getId() + "' editado con éxito.";
                }
                tipo = 1;

            } catch (Db4oException ex) {
                error = true;
            }
            bd.close();

        } else {
            error = true;
        }

        if (error) {
            titulo = "Error al insertar/editar";
            mensaje = "Error al insertar/editar el espectáculo '" + esp.getId() + "'.";
            tipo = 0;
        }
        mensajePersonalizado(titulo, mensaje, tipo);
    }


    public void insertarEditarEspectaculoACliente(ClienteEspectaculoDB4o cliesp) {
        ObjectContainer bd = new Conn().conexion();
        String titulo = "";
        String mensaje = "";
        int tipo = 0;
        boolean error = false;

        if (bd != null) {
            EspectaculoDB4o esp = new EspectaculoDB4o(cliesp.getEspectaculo().getId());
            ClienteDB4o cliente = new ClienteDB4o(cliesp.getCliente().getDni());
            try {
                ObjectSet<EspectaculoDB4o> result1 = bd.queryByExample(esp);
                ObjectSet<ClienteDB4o> result2 = bd.queryByExample(cliente);

                if (result1.size() >= 1 && result2.size() >= 1) {
                    EspectaculoDB4o espectaculo = result1.next();
                    ClienteDB4o cliente1 = result2.next();
                    ClienteEspectaculoDB4o ces = new ClienteEspectaculoDB4o(cliente1, espectaculo);
                    bd.store(ces);
                    titulo = "Espectaculo-Cliente insertado";
                    mensaje = "Cliente apuntado a espectáculo con éxito.";
                }
                tipo = 1;

            } catch (Db4oException ex) {
                error = true;
            }
            bd.close();
        } else
            error = true;

        if (error) {
            titulo = "Error al insertar/editar";
            mensaje = "Error al insertar/editar el espectáculo del cliente.";
            tipo = 0;
        }
        mensajePersonalizado(titulo, mensaje, tipo);
    }

    //funciones reutilizadas de ejercicios de primero, etc.
    //una función para mostrar el tipo de panel que queramos, solo habría ke cambiar las variables :)
    public void mensajePersonalizado(String titulo, String mensaje, int tipo) {
        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);
        Object[] options = {okButton};
        final JOptionPane pane = new JOptionPane(mensaje, tipo, JOptionPane.YES_NO_OPTION, null, options);
        JDialog dialog = pane.createDialog(titulo);
        okButton.addActionListener(x -> dialog.dispose());
        dialog.setVisible(true);
    }
}
