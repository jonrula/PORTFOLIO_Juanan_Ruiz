package DB4o;

import Models.*;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;

import java.util.ArrayList;

public class CargarDatos {
    public CargarDatos() {
    }

    public ArrayList<ClienteDB4o> cargarClientes() {
        ArrayList<ClienteDB4o> clientes = new ArrayList<>();

        ObjectContainer db = new Conn().conexion();
        if (db != null) {
            try {
                ClienteDB4o emp = new ClienteDB4o();
                ObjectSet<ClienteDB4o> resultadoCli = db.queryByExample(emp);

                if (resultadoCli.size() == 0) {
                    System.out.println("No existen clientes en la base de datos");
                }
                while (resultadoCli.hasNext()) {
                    ClienteDB4o cli = resultadoCli.next();
                    clientes.add(cli);
                }
            } catch (Db4oException ex) {
                System.out.println("Error al cargar los clientes");
            }
            db.close();
        }
        return clientes;
    }


    public ArrayList<EmpleadoDB4o> cargarEmpleados() {
        ArrayList<EmpleadoDB4o> empleados = new ArrayList<>();

        ObjectContainer db = new Conn().conexion();
        if (db != null) {
            try {
                EmpleadoDB4o emp = new EmpleadoDB4o();
                ObjectSet<EmpleadoDB4o> resultadoEmp = db.queryByExample(emp);

                if (resultadoEmp.size() == 0) {
                    System.out.println("No existen empleados en la base de datos");
                }
                while (resultadoEmp.hasNext()) {
                    EmpleadoDB4o empl = resultadoEmp.next();
                    empleados.add(empl);
                }
            } catch (Db4oException ex) {
                System.out.println("Error al cargar los empleados");
            }
            db.close();
        }
        return empleados;
    }

    public ArrayList<EspectaculoDB4o> cargarEspectaculos() {
        ArrayList<EspectaculoDB4o> espectaculos = new ArrayList<>();

        ObjectContainer db = new Conn().conexion();
        if (db != null) {
            try {
                EspectaculoDB4o esp = new EspectaculoDB4o();
                ObjectSet<EspectaculoDB4o> resulEspec = db.queryByExample(esp);

                if (resulEspec.size() == 0) {
                    System.out.println("No existen espectáculos en la base de datos");
                }
                while (resulEspec.hasNext()) {
                    EspectaculoDB4o e = resulEspec.next();
                    espectaculos.add(e);
                }
            } catch (Db4oException e) {
                System.out.println("Error al cargar los espectáculos");
            }
            db.close();
        }
        return espectaculos;
    }

    public ArrayList<EmpleadoEspectaculoDB4o> cargarEspectaculosEmpleado(EmpleadoDB4o emp) {
        ArrayList<EmpleadoEspectaculoDB4o> empleadoEspectaculos = new ArrayList<>();

        ObjectContainer bd = new Conn().conexion();
        if (bd != null) {
            try {
                EmpleadoEspectaculoDB4o espectaculo = new EmpleadoEspectaculoDB4o(emp);
                ObjectSet<EmpleadoEspectaculoDB4o> resulEsp = bd.queryByExample(espectaculo);

                if (resulEsp.size() == 0) {
                    System.out.println("El empleado '" + emp.getDni() + "' no organiza ningún espectáculo");
                }
                while (resulEsp.hasNext()) {
                    EmpleadoEspectaculoDB4o e = resulEsp.next();
                    empleadoEspectaculos.add(e);
                }
            } catch (Db4oException ex) {
                System.out.println("Error al cargar las espectáculos del empleado '" + emp.getDni() + "'");
            }
            bd.close();
        }
        return empleadoEspectaculos;
    }


    //este devuelve los de un cliente concreto
    public ArrayList<ClienteEspectaculoDB4o> cargarEspectaculosCliente(ClienteDB4o cliente) {
        ArrayList<ClienteEspectaculoDB4o> clienteEspectaculos = new ArrayList<>();

        ObjectContainer bd = new Conn().conexion();
        if (bd != null) {
            try {
                ClienteEspectaculoDB4o c = new ClienteEspectaculoDB4o(cliente);
                ObjectSet<ClienteEspectaculoDB4o> resulEsp = bd.queryByExample(c);

                if (resulEsp.size() == 0) {
                    System.out.println("El cliente no ha asistido aún a ningún espectáculo");
                }
                while (resulEsp.hasNext()) {
                    ClienteEspectaculoDB4o cliesp = resulEsp.next();
                    clienteEspectaculos.add(cliesp);
                }
            } catch (Db4oException ex) {
                System.out.println("Error al cargar los espectáculos de clientes");
            }
            bd.close();
        }
        return clienteEspectaculos;
    }
}
