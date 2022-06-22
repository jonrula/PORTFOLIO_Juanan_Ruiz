package SQLite;

import Models.*;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Carga {
    public static final String driver = "org.sqlite.JDBC";
    public static final String url = "jdbc:sqlite:parqueSQLite.db";


    //Cargar desde la base de datos las listas de espectaculos, empleados, clientes, clienteEspectaculos, empleadoEspectaculos
    public ArrayList<Cliente> listaClientesSQLite() {
        ArrayList<Cliente> clientes = new ArrayList<>();


        try {
            Class.forName(driver);

            Connection conexion = DriverManager.getConnection(url);

            Statement sentencia = conexion.createStatement();

            //sentencia.execute("CREATE TABLE pruebas (id int primary key, nombre varchar)");

            /*String nombre = "Prueba 1";
            for (int i = 0; i < 10; i++) {
                sentencia.execute("INSERT INTO pruebas (id, nombre) values (" + i + ", " + nombre + ")");
            }*/

            ResultSet resul = sentencia.executeQuery("SELECT * FROM clientes");

            while (resul.next()) {
                // Creo un objeto 'espectaculo' vacío
                Cliente cliente = new Cliente();

                // voy pasáandole los atributos al objeto 'espectaculo'
                cliente.setDni(resul.getString(1));
                cliente.setNombre(resul.getString(2));
                cliente.setApellidos(resul.getString(3));
                cliente.setEdad(resul.getInt(4));


                // Añado el objeto 'espectaculo' al ArrayList espectaculos
                clientes.add(cliente);
            }

            resul.close();
            sentencia.close();
            conexion.close();

            /*System.out.println("\nDatos del arrayList 'clientes':\n");

            for (Cliente c : clientes) {
                System.out.format("%-20s%-20s%-20s%-5d%n", c.getDni(), c.getNombre(), c.getApellidos(), c.getEdad());
            }*/

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public ArrayList<EmpleadoSQLite> listaEmpleadosSQLite() {
        ArrayList<EmpleadoSQLite> empleadosSQlite = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();

            ResultSet resul = sentencia.executeQuery("SELECT * FROM empleados");

            while (resul.next()) {
                // Creo un objeto 'espectaculo' vacío
                EmpleadoSQLite empleadoSQLite = new EmpleadoSQLite();


                // voy pasáandole los atributos al objeto 'espectaculo'
                empleadoSQLite.setDniEmple(resul.getString(1));
                empleadoSQLite.setNombreEmple(resul.getString(2));
                empleadoSQLite.setApeEmple(resul.getString(3));
                empleadoSQLite.setFechaNac(resul.getString(4));
                empleadoSQLite.setFechaContr(resul.getString(5));
                empleadoSQLite.setNacionalidad(resul.getString(6));
                empleadoSQLite.setCargo(resul.getString(7));


                // Añado el objeto 'espectaculo' al ArrayList espectaculos
                empleadosSQlite.add(empleadoSQLite);
            }

            resul.close();
            sentencia.close();
            conexion.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return empleadosSQlite;
    }

    public ArrayList<EspectaculoSQLite> listaEspectaculosSQLite() {
        ArrayList<EspectaculoSQLite> espectaculos = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();

            ResultSet resul = sentencia.executeQuery("SELECT * FROM espectaculos");

            while (resul.next()) {
                // Creo un objeto 'espectaculo' vacío
                EspectaculoSQLite espectaculo = new EspectaculoSQLite();

                // voy pasáandole los atributos al objeto 'espectaculo'
                espectaculo.setNo_Espect(resul.getInt(1));
                espectaculo.setNombreEspec(resul.getString(2));
                espectaculo.setAforo(resul.getInt(3));
                espectaculo.setDescripcion(resul.getString(4));
                espectaculo.setLugar(resul.getString(5));
                espectaculo.setFecha_Espec(resul.getString(6));
                espectaculo.setHorario_espec(resul.getString(7));
                espectaculo.setPrecio(resul.getInt(8));


                // Añado el objeto 'espectaculo' al ArrayList espectaculos
                espectaculos.add(espectaculo);
            }

            resul.close();
            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return espectaculos;
    }

    public ArrayList<Espectaculos_Cliente> listaClientesEspectaculosSQLite() {
        ArrayList<Espectaculos_Cliente> espectaculosClientes = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();

            ResultSet resul = sentencia.executeQuery("SELECT * FROM Espectaculos_Clientes");

            while (resul.next()) {
                // Creo un objeto 'espectaculo' vacío
                Espectaculos_Cliente espectaculos_cliente = new Espectaculos_Cliente();

                // voy pasáandole los atributos al objeto 'espectaculo'
                espectaculos_cliente.setIdEspecCli(resul.getInt(1));
                espectaculos_cliente.setCliente(resul.getString(2));
                espectaculos_cliente.setEspectaculo(resul.getInt(3));


                // Añado el objeto 'espectaculo' al ArrayList espectaculos
                espectaculosClientes.add(espectaculos_cliente);
            }

            // Cerrar ResultSet
            resul.close();
            // Cerrar Statement
            sentencia.close();
            // Cerrar conexion
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return espectaculosClientes;
    }

    public ArrayList<Espectaculos_Empleado> listaEmpleadosEspetaculosSQLite() {
        ArrayList<Espectaculos_Empleado> espectaculosEmpleados = new ArrayList<>();


        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();

            ResultSet resul = sentencia.executeQuery("SELECT * FROM Espectaculos_Empleados");

            while (resul.next()) {
                // Creo un objeto 'espectaculo' vacío
                Espectaculos_Empleado espectaculos_empleado = new Espectaculos_Empleado();

                // voy pasáandole los atributos al objeto 'espectaculo'
                espectaculos_empleado.setIdEspecEmp(resul.getInt(1));
                espectaculos_empleado.setEmpleado(resul.getString(2));
                espectaculos_empleado.setEspectaculo(resul.getInt(3));


                // Añado el objeto 'espectaculo' al ArrayList espectaculos
                espectaculosEmpleados.add(espectaculos_empleado);
            }

            // Cerrar ResultSet
            resul.close();
            // Cerrar Statement
            sentencia.close();
            // Cerrar conexion
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return espectaculosEmpleados;
    }

    public ArrayList<Usuario> listaUsuarios(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try {
            Class.forName(driver);

            Connection conexion = DriverManager.getConnection(url);

            Statement sentencia = conexion.createStatement();
            // hace la consulta
            ResultSet resul = sentencia.executeQuery(sql);

            while (resul.next()) {
                // Creo un objeto 'espectaculo' vacío
                Usuario usuario = new Usuario();

                // voy pasáandole los atributos al objeto 'espectaculo'
                usuario.setIdPass(resul.getInt(1));
                usuario.setUsuario(resul.getString(2));
                usuario.setPassword(resul.getString(3));


                // Añado el objeto 'espectaculo' al ArrayList espectaculos
                usuarios.add(usuario);
            }

            // Cerrar ResultSet
            resul.close();
            // Cerrar Statement
            sentencia.close();
            //conexion.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    //Funciones para añadir, modificar y eliminar Clientes
    public void clienteNuevo(Cliente cliente) {

        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();
            sql = "insert into clientes(dniCli, nombreCli, ApesCli, edad) values (?,?,?,?)";

            ps =  conexion.prepareStatement(sql);
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellidos());
            ps.setInt(4, cliente.getEdad());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se han insertado los datos");

            sentencia.close();
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void modificarCliente(Cliente cliente){
        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion =  DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();
            sql = "update clientes set  nombreCli=?, ApesCli=?, edad=? where dniCli=?";

            ps =  conexion.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidos());
            ps.setInt(3, cliente.getEdad());
            ps.setString(4, cliente.getDni());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se han insertado los datos");

            sentencia.close();
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void eliminarCliente(Cliente cliente){
        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion =  DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();
            sql = "delete from clientes where dniCli=?";

            ps =  conexion.prepareStatement(sql);
            ps.setString(1, cliente.getDni());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se han insertado los datos");

            sentencia.close();
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Funciones para añadir, modificar y eliminar Empleados
    public void empleadoNuevo(EmpleadoSQLite empleado) {

        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion =  DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();
            sql = "insert into empleados(dniEmple, nombreEmple, ApeEmple, f_Nac_Emple, f_Cont_Emple, nacionalidad, cargo) values (?,?,?,?,?,?,?)";

            ps =  conexion.prepareStatement(sql);
            ps.setString(1, empleado.getDniEmple());
            ps.setString(2, empleado.getNombreEmple());
            ps.setString(3, empleado.getApeEmple());
            ps.setString(4, empleado.getFechaNac());
            ps.setString(5, empleado.getFechaContr());
            ps.setString(6, empleado.getNacionalidad());
            ps.setString(7, empleado.getCargo());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se han insertado los datos");

            sentencia.close();
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void modificarEmpleado(EmpleadoSQLite empleado){
        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion =  DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();
            sql = "update empleados set  nombreEmple=?, ApeEmple=?, f_Nac_Emple=?, lugar=?, f_Cont_Emple=?, nacionalidad=?, cargo=? where dniEmple=?";

            ps =  conexion.prepareStatement(sql);
            ps.setString(1, empleado.getNombreEmple());
            ps.setString(2, empleado.getApeEmple());
            ps.setString(3, empleado.getFechaNac());
            ps.setString(4, empleado.getFechaContr());
            ps.setString(5, empleado.getNacionalidad());
            ps.setString(6, empleado.getCargo());
            ps.setString(7, empleado.getDniEmple());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se han insertado los datos");

            sentencia.close();
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void eliminarEmpleado(EmpleadoSQLite empleado){
        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion =  DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();
            sql = "delete from empleados where dniEmple=?";

            ps =  conexion.prepareStatement(sql);
            ps.setString(1, empleado.getDniEmple());


            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se ha borrado el empleado " + empleado.getNombreEmple());

            sentencia.close();
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }


    //Funciones para añadir, modificar y eliminar Espectaculos
    public void espectaculoNuevo(EspectaculoSQLite espectaculo) {

        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion =  DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();
            sql = "insert into espectaculos(no_espec, nombreEspec, aforo, descripcion, lugar, fecha_Espec, horario_Espec, precio) values (?,?,?,?,?,?,?,?)";

            ps =  conexion.prepareStatement(sql);
            ps.setInt(1, espectaculo.getNo_Espect());
            ps.setString(2, espectaculo.getNombreEspec());
            ps.setInt(3, espectaculo.getAforo());
            ps.setString(4, espectaculo.getDescripcion());
            ps.setString(5, espectaculo.getLugar());
            ps.setString(6, espectaculo.getFecha_Espec());
            ps.setString(7, espectaculo.getHorario_espec());
            ps.setDouble(8, espectaculo.getPrecio());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se han insertado los datos");

            sentencia.close();
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void modificarEspectaculo(EspectaculoSQLite espectaculo){
        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion =  DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();
            sql = "update espectaculos set  nombreEspec=?, aforo=?, descripcion=?, lugar=?, fecha_Espec=?, horario_Espec=?, precio=?, responsable=? where no_espec=?";

            ps =  conexion.prepareStatement(sql);
            ps.setString(1, espectaculo.getNombreEspec());
            ps.setInt(2, espectaculo.getAforo());
            ps.setString(3, espectaculo.getDescripcion());
            ps.setString(4, espectaculo.getLugar());
            ps.setString(5, espectaculo.getFecha_Espec());
            ps.setString(6, espectaculo.getHorario_espec());
            ps.setDouble(7, espectaculo.getPrecio());
            ps.setString(8, espectaculo.getResponsable());
            ps.setInt(9, espectaculo.getNo_Espect());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se han insertado los datos");

            sentencia.close();
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void eliminarEspectaculo(EspectaculoSQLite espectaculo){
        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conexion =  DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();
            sql = "delete from espectaculos where no_espec=?";

            ps =  conexion.prepareStatement(sql);
            ps.setInt(1, espectaculo.getNo_Espect());


            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se ha borrado el espectaculo " + espectaculo.getNombreEspec());

            sentencia.close();
            conexion.close();


        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }
    public int idMaxEspectaculos() {

        EspectaculoSQLite espectaculo = null;
        String sql = "SELECT * FROM clientes";

        try {
            //Cargar el driver
            Class.forName("org.sqlite.JDBC");

            //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dam3?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET", "elena", "elena123321");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = (Statement) conexion.createStatement();

            // hace la consulta
            ResultSet resul = sentencia.executeQuery("SELECT MAX(no_espec) AS id FROM espectaculos");

            while (resul.next()) {
                // Creo un objeto 'espectaculo' vacío
                espectaculo = new EspectaculoSQLite((String.valueOf(resul.getInt(1))));

                // voy pasáandole los atributos al objeto 'espectaculo'
                espectaculo.setNo_Espect(resul.getInt(1));

            }


            // Cerrar ResultSet
            resul.close();
            // Cerrar Statement
            sentencia.close();
            // Cerrar conexion
            conexion.close();

        } catch (SQLException | ClassNotFoundException e) {
            // hacer algo con la excepcion
        }



        // La función me devuelve el ArrayList de espectaculos
        return espectaculo.getNo_Espect();
    }


    //Funciones para añadir y eliminar clienteEspectaculos
    public void anadirClienteEspectaculoSQLite(String dniCliSQLite, int idEspectSQLite) {
        PreparedStatement ps;
        String sql;
        try {
            Class.forName("org.sqlite.JDBC");

            //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dam3?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET", "elena", "elena123321");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = (Statement) conexion.createStatement();

            sql = "insert into Espectaculos_Clientes(Cliente, Espectaculo) values (?,?)";

            ps = conexion.prepareStatement(sql);

            ps.setString(1, dniCliSQLite);
            ps.setInt(2, idEspectSQLite);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se han insertado los datos");

            sentencia.close();
            conexion.close();

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }
    public void eliminarEspectaculoClienteSQLite(Espectaculos_Cliente espectaculoClienteSQLite) {
        PreparedStatement ps;
        String sql;
        try {
            Class.forName("org.sqlite.JDBC");

            //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dam3?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET", "elena", "elena123321");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");

            Statement sentencia = conexion.createStatement();

            sql = "delete from Espectaculos_Clientes where Cliente=? and Espectaculo=?";

            ps = conexion.prepareStatement(sql);
            ps.setString(1, espectaculoClienteSQLite.getCliente());
            ps.setInt(2, espectaculoClienteSQLite.getEspectaculo());


            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se ha borrado el espectaculo ");

            sentencia.close();
            conexion.close();

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public void anadirEmpleadoEspectaculoSQLite(String dniEmple, int idEspectaculo) {

        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dam3?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET", "elena", "elena123321");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");


            Statement sentencia = conexion.createStatement();
            sql = "insert into Espectaculos_Empleados(Empleado, Espectaculo) values (?,?)";

            ps = conexion.prepareStatement(sql);

            ps.setString(1, dniEmple);

            ps.setInt(2, idEspectaculo);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se han insertado los datos");

            sentencia.close();
            conexion.close();

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }

    }

    public void eliminarEmpleadoEspectaculoSQLite(Espectaculos_Empleado espectEmpleSQLite){
        PreparedStatement ps;
        String sql;

        try {
            Class.forName("org.sqlite.JDBC");

            //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dam3?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET", "elena", "elena123321");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");


            Statement sentencia = conexion.createStatement();
            sql = "delete from Espectaculos_Empleados where Empleado=? and Espectaculo=?";

            ps = conexion.prepareStatement(sql);

            ps.setString(1, espectEmpleSQLite.getEmpleado());
            ps.setInt(2, espectEmpleSQLite.getEspectaculo());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se ha borrado el espectaculo ");

            sentencia.close();
            conexion.close();

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }

    }


    public String infoMySql(JTextArea infoJtextArea) {

        String info = "";
        try {

            // MySQL

            //Cargar el driver

            Class.forName("org.sqlite.JDBC");

            //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dam3?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET", "elena", "elena123321");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:parqueSQLite.db");


            // Preparamos la consulta
            DatabaseMetaData dbmd = conexion.getMetaData();//Creamos objeto DatabaseMetaData

            System.out.println();

            ResultSet resul = null;
            String nombre = dbmd.getDatabaseProductName();
            String driver = dbmd.getDriverName();
            String url = dbmd.getURL();
            String usuario = dbmd.getUserName();

            System.out.println("INFORMACION SOBRE LA BASE DE DATOS: " + nombre);
            System.out.println("Driver : " + driver);
            System.out.println("URL : " + url);
            System.out.println("Usuario: " + usuario);

            resul = dbmd.getTables("Juanan", null, null, null);

            info += "\nINFORMACION SOBRE LA BASE DE DATOS: " + nombre + "\r\n\n";
            info += "Driver : " + driver + "\r\n";
            info += "URL : " + url + "\r\n";
            info += "Usuario: " + usuario + "\r\n\n";


            while (resul.next()) {
                String catalogo = resul.getString(1);//columna 1 que devuelve ResulSet
                String esquema = resul.getString(2); //columna 2
                String tabla = resul.getString(3); //columna 3
                String tipo = resul.getString(4); //columna 4

                System.out.println(tipo + " - Catalogo: " + catalogo + ", Esquema : " + esquema + ", Nombre : " + tabla);

                info += (tipo + " - Catalogo: " + catalogo + ", Esquema : " + esquema + ", Nombre : " + tabla) + "\r\n";

                infoJtextArea.setText(info);
                infoJtextArea.setEditable(false);

            }

            //Cerrar conexión

            conexion.close();

        } catch (ClassNotFoundException | SQLException p) {
            p.printStackTrace();
        }


        return info;
    }

    //Funciones usuarios
    public String mirarPassword(String usuario){
        String sql = "select contrasena from usuarios where usuario=?";
        String password = "";
        Usuario user = new Usuario(usuario,password);

        try {
            //Cargar el driver
            Class.forName(driver);

            //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dam3?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET", "elena", "elena123321");
            Connection conexion = DriverManager.getConnection(url);

            Statement sentencia = conexion.createStatement();

            // hace la consulta
            ResultSet resul = sentencia.executeQuery("select contrasena from usuarios where usuario='"+usuario+"'");

            while (resul.next()) {

                // Creo un objeto 'Usuario' con el usuario que me pasan por parámetro de entrada
                // voy pasándole los atributos al objeto 'espectaculo'
                user.setPassword(resul.getString(1));

            }
            //System.out.println("User: "+user);

            // Cerrar ResultSet
            resul.close();
            // Cerrar Statement
            sentencia.close();
            // Cerrar conexion
            conexion.close();

        } catch (SQLException | ClassNotFoundException e) {
            // hacer algo con la excepcion
        }


        // La función me devuelve el ArrayList de espectaculos
        return user.getPassword();
    }
    public void crearUsuarioNuevo(Usuario usuario){
        PreparedStatement ps;
        String sql;

        try {
            Class.forName(driver);

            //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dam3?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EET", "elena", "elena123321");
            Connection conexion =  DriverManager.getConnection(url);


            Statement sentencia =  conexion.createStatement();

            sql = "insert into usuarios (usuario,contrasena) values (?,?)";

            ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getPassword());


            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se han insertado los datos");

            sentencia.close();
            conexion.close();

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }
}
