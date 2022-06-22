package DB4o;

import Models.*;
import com.db4o.*;
import com.db4o.ext.Db4oException;

import javax.swing.*;

public class Borrar {
    public void borrarCliente(ClienteDB4o cliente){
        ObjectContainer db = new Conn().conexion();
        String titulo = "Cliente borrado";
        String msj = "Cliente con dni: " + cliente.getDni() + " eliminado";
        int tipoPanel = 1;
        boolean error = false;

        if (db != null) {
            try {
                //para eso tenemos el constructor con el parámetro dni solamente...
                ClienteDB4o cliente1 = new ClienteDB4o(cliente.getDni());
                ObjectSet<ClienteDB4o> resul = db.queryByExample(cliente1);
                //si no devuelve datos...panel informativo de error ;)
                if (resul.size() <= 0) {
                    error = true;
                    //mientras haya datos:
                } else {
                    while (resul.hasNext()) {
                        ClienteDB4o existe = resul.next();
                        //primero borramos sus espectáculos: ¡¡OJO ke aquí no hay on delete cascade!!
                        borrarEspectaculosCliente(db, existe);
                        db.delete(existe);
                    }
                }
            } catch (Db4oException e) {
                error = true;
            }
        } else {
            error = true;
        }

        if (error) {
            titulo = "Error al borrar";
            msj = "Error al eliminar el cliente con dni: " + cliente.getDni();
            tipoPanel = 0;
        }
        mensajePersonalizado(titulo, msj, tipoPanel);

    }

    public void borrarEspectaculo(EspectaculoDB4o esp) {
        ObjectContainer db = new Conn().conexion();
        String titulo = "Espectáculo borrado";
        String msj = "Espectáculo con dni: " + esp.getId() + " eliminado";
        int tipoPanel = 1;
        boolean error = false;

        if (db != null) {
            try {
                //para eso tenemos el constructor con el parámetro dni solamente...
                EspectaculoDB4o espectaculo = new EspectaculoDB4o(esp.getId());
                ObjectSet<EspectaculoDB4o> resul = db.queryByExample(espectaculo);
                //si no devuelve datos...panel informativo de error ;)
                if (resul.size() <= 0) {
                    error = true;
                    //mientras haya datos:
                } else {
                    while (resul.hasNext()) {
                        EspectaculoDB4o existe = resul.next();
                        //primero borramos sus espectáculos: ¡¡OJO ke aquí no hay on delete cascade!!
                        db.delete(existe);
                    }
                }
            } catch (Db4oException e) {
                error = true;
            }
        } else {
            error = true;
        }

        if (error) {
            titulo = "Error al borrar";
            msj = "Error al eliminar el espectáculo: " + esp.getId();
            tipoPanel = 0;
        }
        mensajePersonalizado(titulo, msj, tipoPanel);
    }

    public void borrarEspectaculosCliente(ObjectContainer db, ClienteDB4o cliente) {
        if (db != null) {
            try {
                //se busca por la PK
                ClienteEspectaculoDB4o clienteEspectaculo = new ClienteEspectaculoDB4o(cliente);
                ObjectSet<ClienteEspectaculoDB4o> resul = db.queryByExample(clienteEspectaculo);
                if (resul.size() > 0) {
                    while (resul.hasNext()) {
                        ClienteEspectaculoDB4o existe = resul.next();
                        db.delete(existe);
                    }
                }
            } catch (Db4oException e) {
                e.printStackTrace();
            }
        }
    }

    public void borrarEspectaculosEmpleado(ObjectContainer db, EmpleadoDB4o empleado) {
        if (db != null) {
            try {
                //se busca por la PK
                EmpleadoEspectaculoDB4o empleadoEspectaculo = new EmpleadoEspectaculoDB4o(empleado);
                ObjectSet<EmpleadoEspectaculoDB4o> resul = db.queryByExample(empleadoEspectaculo);
                if (resul.size() > 0) {
                    while (resul.hasNext()) {
                        EmpleadoEspectaculoDB4o existe = resul.next();
                        db.delete(existe);
                    }
                }
            } catch (Db4oException e) {
                e.printStackTrace();
            }
        }
    }


    public void borrarEmpleado(EmpleadoDB4o empleado) {
        ObjectContainer db = new Conn().conexion();
        String titulo = "Empleado borrado";
        String msj = "Empleado con dni: " + empleado.getDni() + " eliminado";
        int tipoPanel = 1;
        boolean error = false;


        if (db != null) {

            try {
                EmpleadoDB4o emp = new EmpleadoDB4o(empleado.getDni());
                ObjectSet<EmpleadoDB4o> result = db.queryByExample(emp);
                if (result.size() <= 0) {
                    error = true;
                } else {
                    while (result.hasNext()) {
                        EmpleadoDB4o existe = result.next();
                        //primero borramos sus espectáculos: ¡¡OJO ke aquí no hay on delete cascade!!
                        borrarEspectaculosEmpleado(db, existe);
                        db.delete(existe);
                    }
                }
            } catch (Db4oException e) {
                error = true;
            }
        } else {
            error = true;
        }

        if (error) {
            titulo = "Error al borrar";
            msj = "Error al eliminar el empleado con dni: " + empleado.getDni();
            tipoPanel = 0;
        }
        mensajePersonalizado(titulo, msj, tipoPanel);
    }

    //Funciones reutilizadas de ejercicios de primero, etc.
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
