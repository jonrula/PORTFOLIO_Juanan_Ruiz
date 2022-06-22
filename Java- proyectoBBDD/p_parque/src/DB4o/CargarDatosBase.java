package DB4o;

import Models.*;
import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CargarDatosBase {
    final static String BBDD = "db4oPark.yap";

    //vamos a cargar unos de base:

    public static ArrayList<ClienteDB4o> clientes = new ArrayList<>();
    public static ArrayList<EmpleadoDB4o> empleados = new ArrayList<>();
    public static ArrayList<EspectaculoDB4o> espectaculos = new ArrayList<>();
    public static ArrayList<ClienteEspectaculoDB4o> clienteEspectaculos = new ArrayList<>();
    public static ArrayList<EmpleadoEspectaculoDB4o> empleadoEspectaculos = new ArrayList<>();

    //si hacemos la tabla de clientes_espectaculos pues hacemos una clase para ello
    //y metemos otra lista aquí y en el resto de sitios

    //constructor vacío para acceder
    public CargarDatosBase() {
    }

    public static Date dateformat(String fecha) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(fecha);
        } catch (Exception e) {
            System.out.println("Error al formatear fecha....");
        }
        return date;
    }

    // https://generadordni.es/#home
    public void cargarEmpleadosInicio() {

        //cuidado porque las fechas son con formato YYYY-MM-dd
        //haré una función para asegurarnos de convertir la fecha con el dateFormat
        EmpleadoDB4o empleado = new EmpleadoDB4o("03336665D", "Asier", "Villanueva", dateformat("1985-01-25"), dateformat("2021-01-14"), "España", "RRHH");

        EmpleadoDB4o empleado2 = new EmpleadoDB4o("95864107S", "Ane", "Ortiz", dateformat("1980-02-21"), dateformat("2021-01-14"), "España", "RRHH");

        EmpleadoDB4o empleado3 = new EmpleadoDB4o("01728713X", "Gorka", "Beitia", dateformat("1981-12-02"), dateformat("2021-01-15"), "España", "RRHH");

        EmpleadoDB4o empleado4 = new EmpleadoDB4o("68654514C", "Ion", "Jaureguialzo", dateformat("1985-03-13"), dateformat("2021-01-15"), "España", "Organizador");

        EmpleadoDB4o empleado5 = new EmpleadoDB4o("80288164V", "Ekaitz", "Martínez", dateformat("1986-10-16"), dateformat("2021-01-14"), "España", "Organizador");

        empleados.add(empleado);
        empleados.add(empleado5);
        empleados.add(empleado2);
        empleados.add(empleado3);
        empleados.add(empleado4);

        System.out.println("Empleados base cargados");
    }


    public void cargarClientesInicio() {

        ClienteDB4o cliente = new ClienteDB4o("34089223A", "Juan", "Cuesta", 50);

        ClienteDB4o cliente2 = new ClienteDB4o("93799874D", "Belén", "López", 37);

        ClienteDB4o cliente3 = new ClienteDB4o("04216297Y", "Emilio", "Delgado", 38);

        ClienteDB4o cliente4 = new ClienteDB4o("90558833J", "Mariano", "Delgado", 65);

        clientes.add(cliente);
        clientes.add(cliente2);
        clientes.add(cliente3);
        clientes.add(cliente4);

        System.out.println("Clientes base cargados");

    }

    public void cargarEspectaculosInicio() {


        //cojo empleados ya cargados para pasarlo al espectáculo
        EmpleadoDB4o empleado = empleados.get(1);
        EmpleadoDB4o empleado1 = empleados.get(3);
        EmpleadoDB4o empleado2 = empleados.get(0);
        EmpleadoDB4o empleado3 = empleados.get(4);
        EmpleadoDB4o empleado4 = empleados.get(2);


        EspectaculoDB4o e1 = new EspectaculoDB4o(1, empleado, "Romeo y Julieta", 1000, "Obra de teatro", "Teatro Principal Antzokia", 25.00);

        EspectaculoDB4o e2 = new EspectaculoDB4o(2, empleado1, "Romeo y Julieta II", 1000, "Obra de teatro", "Teatro Principal Antzokia", 25.00);

        EspectaculoDB4o e3 = new EspectaculoDB4o(3, empleado2, "Los Minions", 350, "Obra infantil", "Plaza España", 12.00);

        EspectaculoDB4o e4 = new EspectaculoDB4o(4, empleado3, "Macbeth", 19840, "Obra de teatro", "Mendizorrotza", 30.00);

        EspectaculoDB4o e5 = new EspectaculoDB4o(5, empleado4, "Harlem Globertrotters", 15716, "Obra de teatro", "Buesa Arena", 20.00);


        espectaculos.add(e1);
        espectaculos.add(e2);
        espectaculos.add(e3);
        espectaculos.add(e4);
        espectaculos.add(e5);

        System.out.println("Espectáculos base cargados");

    }

    public void cargarEspectaculosClientes() {

        ClienteDB4o cliente1 = clientes.get(0);
        ClienteDB4o cliente3 = clientes.get(2);
        ClienteDB4o cliente2 = clientes.get(1);
        ClienteDB4o cliente4 = clientes.get(3);

        ClienteEspectaculoDB4o ce1 = new ClienteEspectaculoDB4o(1, cliente1, espectaculos.get(0));
        ClienteEspectaculoDB4o ce2 = new ClienteEspectaculoDB4o(2, cliente1, espectaculos.get(1));

        ClienteEspectaculoDB4o ce3 = new ClienteEspectaculoDB4o(3, cliente3, espectaculos.get(2));
        ClienteEspectaculoDB4o ce4 = new ClienteEspectaculoDB4o(4, cliente2, espectaculos.get(3));
        ClienteEspectaculoDB4o ce5 = new ClienteEspectaculoDB4o(5, cliente4, espectaculos.get(4));

        clienteEspectaculos.add(ce1);
        clienteEspectaculos.add(ce2);
        clienteEspectaculos.add(ce3);
        clienteEspectaculos.add(ce4);
        clienteEspectaculos.add(ce5);


    }

    //me sale un error cuado lo hago de esta manera, así ke lo cambio
    public void insercionesBase() {

        //insertaamos en la BBDD todos los datos de inicio bases:
        ObjectContainer db = new Conn().conexion();

        cargarClientesInicio();
        for (ClienteDB4o cliente : clientes) {
            db.store(cliente);
        }

        cargarEmpleadosInicio();
        for (EmpleadoDB4o empleado : empleados) {
            db.store(empleado);
        }

        cargarEspectaculosInicio();
        for (EspectaculoDB4o espectaculo : espectaculos) {
            db.store(espectaculo);
        }

        cargarEspectaculosClientes();
        for (ClienteEspectaculoDB4o clienteEspectaculo : clienteEspectaculos) {
            db.store(clienteEspectaculo);
        }

        //¡¡¡cerramos!!!
        db.close();

    }

    public void insercionesBaseClientes() {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BBDD);

        ClienteDB4o cliente = new ClienteDB4o("34089223A", "Juan", "Cuesta", 50);
        ClienteDB4o cliente2 = new ClienteDB4o("93799874D", "Belén", "López", 37);
        ClienteDB4o cliente3 = new ClienteDB4o("04216297Y", "Emilio", "Delgado", 38);
        ClienteDB4o cliente4 = new ClienteDB4o("90558833J", "Mariano", "Delgado", 65);

        clientes.add(cliente);
        clientes.add(cliente2);
        clientes.add(cliente3);
        clientes.add(cliente4);

        db.store(cliente);
        db.store(cliente2);
        db.store(cliente3);
        db.store(cliente4);

        db.close();

        System.out.println("Clientes base cargados");
    }

    public static void insercionesBaseDb4o() {


        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BBDD);


        ClienteDB4o cliente001 = new ClienteDB4o("34089223A", "Juan", "Cuesta", 50);
        ClienteDB4o cliente002 = new ClienteDB4o("93799874D", "Belén", "López", 37);
        ClienteDB4o cliente003 = new ClienteDB4o("04216297Y", "Emilio", "Delgado", 38);
        ClienteDB4o cliente004 = new ClienteDB4o("90558833J", "Mariano", "Delgado", 65);

        clientes.add(cliente001);
        clientes.add(cliente002);
        clientes.add(cliente003);
        clientes.add(cliente004);

        db.store(cliente001);
        db.store(cliente002);
        db.store(cliente003);
        db.store(cliente004);


        System.out.println("Clientes base cargados");


        /*

        * Empleado empleado3 = new Empleado("01728713X", "Gorka", "Beitia", dateformat("1981-12-02"), dateformat("2021-01-15"), "España", "RRHH");

        Empleado empleado4 = new Empleado("68654514C", "Ion", "Jaureguialzo", dateformat("1985-03-13"), dateformat("2021-01-15"), "España", "Organizador");

        Empleado empleado5 = new Empleado("80288164V", "Ekaitz", "Martínez", dateformat("1986-10-16"), dateformat("2021-01-14"), "España", "Organizador");

        * */

        EmpleadoDB4o empleado = new EmpleadoDB4o("03336665D", "Asier", "Villanueva", "1985-01-25", "2021-01-14", "España", "RRHH");

        EmpleadoDB4o empleado2 = new EmpleadoDB4o("95864107S", "Ane", "Ortiz", "1980-02-21", "2021-01-14", "España", "RRHH");

        EmpleadoDB4o empleado3 = new EmpleadoDB4o("01728713X", "Gorka", "Beitia", "1981-12-02", "2021-01-15", "España", "RRHH");

        EmpleadoDB4o empleado4 = new EmpleadoDB4o("68654514C", "Ion", "Jaureguialzo", "1985-03-13", "2021-01-15", "España", "Organizador");

        EmpleadoDB4o empleado5 = new EmpleadoDB4o("80288164V", "Ekaitz", "Martínez", "1986-10-16", "2021-01-14", "España", "Organizador");

        empleados.add(empleado);
        empleados.add(empleado2);
        empleados.add(empleado3);
        empleados.add(empleado4);
        empleados.add(empleado5);

        System.out.println(empleados.toString());


        db.store(empleado);
        db.store(empleado2);
        db.store(empleado3);
        db.store(empleado4);
        db.store(empleado5);

        //db.close();

        System.out.println("Empleados base cargados");


        EmpleadoDB4o e1 = empleados.get(0);
        EmpleadoDB4o e2 = empleados.get(1);


        //ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BBDD);
        EspectaculoDB4o esp = new EspectaculoDB4o(1, "Gasteiz Magic Show", 1000, "Un espectáculo de magia", "Plaza de Toros", "2021-01-15", "17:00:00", 6.50, e1);
        EspectaculoDB4o esp2 = new EspectaculoDB4o(2, "Gasteiz Magic Show II", 1000, "Un espectáculo de magia, parte II", "Plaza de Toros", "2021-01-26", "17:00:00", 6.50, e2);
        EspectaculoDB4o esp3 = new EspectaculoDB4o(3, "Cabaret", 3000, "Señoritas...", "Mendizorrotza", "2021-12-21", "22:00:00", 25.00, e2);

        espectaculos.add(esp);
        espectaculos.add(esp2);
        espectaculos.add(esp3);

        db.store(esp);
        db.store(esp2);
        db.store(esp3);

        //db.close();

        System.out.println("Espectáculos base cargados");

        ClienteDB4o cliente1 = clientes.get(0);
        ClienteDB4o cliente3 = clientes.get(2);
        ClienteDB4o cliente2 = clientes.get(1);
        ClienteDB4o cliente4 = clientes.get(3);

        ClienteEspectaculoDB4o ce1 = new ClienteEspectaculoDB4o(1, cliente1, espectaculos.get(0));
        ClienteEspectaculoDB4o ce2 = new ClienteEspectaculoDB4o(2, cliente1, espectaculos.get(1));

        ClienteEspectaculoDB4o ce3 = new ClienteEspectaculoDB4o(3, cliente3, espectaculos.get(2));
        ClienteEspectaculoDB4o ce4 = new ClienteEspectaculoDB4o(4, cliente2, espectaculos.get(2));
        ClienteEspectaculoDB4o ce5 = new ClienteEspectaculoDB4o(5, cliente4, espectaculos.get(1));


        clienteEspectaculos.add(ce1);
        clienteEspectaculos.add(ce2);
        clienteEspectaculos.add(ce3);
        clienteEspectaculos.add(ce4);
        clienteEspectaculos.add(ce5);

        db.store(ce1);
        db.store(ce2);
        db.store(ce3);
        db.store(ce4);
        db.store(ce5);

        //db.close();


        System.out.println("Clientes_Espectáculos base cargados");


        EmpleadoEspectaculoDB4o emp_esp = new EmpleadoEspectaculoDB4o(1, empleado, espectaculos.get(0));
        EmpleadoEspectaculoDB4o emp_esp2 = new EmpleadoEspectaculoDB4o(2, empleado3, espectaculos.get(2));
        EmpleadoEspectaculoDB4o emp_esp3 = new EmpleadoEspectaculoDB4o(3, empleado4, espectaculos.get(1));

        empleadoEspectaculos.add(emp_esp);
        empleadoEspectaculos.add(emp_esp2);
        empleadoEspectaculos.add(emp_esp3);

        db.store(emp_esp);
        db.store(emp_esp2);
        db.store(emp_esp3);


        db.close();

        System.out.println("Empleados_Espectáculos base cargados");

    }

    public static void main(String[] args) {
        insercionesBaseDb4o();
    }

   /* public void insercionesBaseEspec() {


        Empleado e1 = empleados.get(0);
        Empleado e2 = empleados.get(1);


        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BBDD);
        Espectaculo esp = new Espectaculo(1, "Gasteiz Magic Show", 1000, "Un espectáculo de magia", "Plaza de Toros", dateformat("2021-01-15"), "17:00:00", 6.50, e1);
        Espectaculo esp2 = new Espectaculo(2, "Gasteiz Magic Show II", 1000, "Un espectáculo de magia, parte II", "Plaza de Toros", dateformat("2021-01-26"), "17:00:00", 6.50, e2);
        Espectaculo esp3 = new Espectaculo(3, "Cabaret", 3000, "Señoritas...", "Mendizorrotza", dateformat("2021-12-21"), "22:00:00", 25.00, e2);

        espectaculos.add(esp);
        espectaculos.add(esp2);
        espectaculos.add(esp3);

        db.store(esp);
        db.store(esp2);
        db.store(esp3);

        db.close();

        System.out.println("Espectáculos base cargados");
    }

    public void insercionesBaseCli_Esp() {

        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BBDD);

        Cliente cliente1 = clientes.get(0);
        Cliente cliente3 = clientes.get(2);
        Cliente cliente2 = clientes.get(1);
        Cliente cliente4 = clientes.get(3);

        ClienteEspectaculo ce1 = new ClienteEspectaculo(1, cliente1, espectaculos.get(0));
        ClienteEspectaculo ce2 = new ClienteEspectaculo(2, cliente1, espectaculos.get(1));

        ClienteEspectaculo ce3 = new ClienteEspectaculo(3, cliente3, espectaculos.get(2));
        ClienteEspectaculo ce4 = new ClienteEspectaculo(4, cliente2, espectaculos.get(3));
        ClienteEspectaculo ce5 = new ClienteEspectaculo(5, cliente4, espectaculos.get(4));


        clienteEspectaculos.add(ce1);
        clienteEspectaculos.add(ce2);
        clienteEspectaculos.add(ce3);
        clienteEspectaculos.add(ce4);
        clienteEspectaculos.add(ce5);

        db.store(ce1);
        db.store(ce2);
        db.store(ce3);
        db.store(ce4);
        db.store(ce5);

        db.close();


        System.out.println("Clientes_Espectáculos base cargados");
    }*/

}
