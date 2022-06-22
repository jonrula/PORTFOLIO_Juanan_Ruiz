package com.ikasgela;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PreguntasBD {

    private static final Logger logger = LogManager.getLogger();
    private static Connection conexion = null;

    public static void conectar() {
        try {

            // Cadena de conexión
            String servidor = Configuracion.leer("DB_HOST");
            String puerto = Configuracion.leer("DB_PORT");
            String bd = Configuracion.leer("DB_DATABASE");
            String login = Configuracion.leer("DB_USERNAME");
            String password = Configuracion.leer("DB_PASSWORD");
            String url = "jdbc:oracle:thin:@" + servidor + ":" + puerto + ":" + bd;


            // Establecimiento de conexión
            conexion = DriverManager.getConnection(url, login, password);

            logger.info("Conexión abierta");

        } catch (SQLException e) {
            logger.fatal(e);
        }
    }

    // Metiendo como parámetro de entrada la variable 'receta' ... que es el item de tipo 'Receta' que escojo del combo, tengo acceso a todas sus propiedades y asi cojo su 'id' --> 'receta.getId' --> que me devuelve un INT
    public static void BorrarRecetaOracleSQL(Receta receta) {

        try {

            PreparedStatement st = conexion.prepareStatement("DELETE FROM RECETA WHERE ID = ?");
            st.setInt(1, receta.getId());

            int filas = st.executeUpdate();
            System.out.println("Filas BorrarReceta afectadas: " + filas);

        } catch (SQLException e) {
            logger.error(e);
        }


    }

    // Me retorna cual es la 'id' máxima de la tabla de recetas
    public static int LeerRecetaIDMaximo() {

        int num = 0;

        try {

            Statement stmt = conexion.createStatement();

            ResultSet rset = stmt.executeQuery("SELECT MAX(ID) AS MAXIMO FROM RECETA");
            while (rset.next()) {
                num = rset.getInt("MAXIMO");
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;
    }

    // Con esta función inserto en la base de datos la nueva 'id' de receta que ya he generado en la función anterior y el nombre de la receta
    public static void introducirSoloIDyRecetaNuevaOracleSQL(int id_RecetaNueva, String receta) {
        try {

            PreparedStatement st = conexion.prepareStatement("INSERT INTO RECETA (ID, RECETA) VALUES (?, ?)");

            st.setInt(1, id_RecetaNueva);
            st.setString(2, receta);

            int filas = st.executeUpdate();
            System.out.println("Filas añadir ID Receta nueva afectadas: " + filas);

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    // Me retorna cual es la 'id' máxima de la tabla de 'Ingrediente_recetas'
    public static int LeerIngrediente_RecetaIDMaximo() {

        int num = 0;

        try {

            Statement stmt = conexion.createStatement();

            ResultSet rset = stmt.executeQuery("SELECT MAX(ID) AS MAXIMO FROM INGREDIENTE_RECETA");
            while (rset.next()) {
                num = rset.getInt("MAXIMO");
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;
    }

    // Con esta función inserto en la base de datos la nueva 'id' de 'Ingrediente_receta' que ya he generado en la función anterior, la `'id' de receta nueva y la cantidad y el 'id' del ingrediente nuevo
    // Con el parámetro de entrada de tipo 'Ingrediente' -->  Ingrediente id_ingredienteNuevo --> accedo a todas las propiedades del objeto de tipo 'Ingrediente' que he escogido del combo --> 'id_ingredienteNuevo.getId' que me devuelve un INT
    public static void introducirIngrediente_RecetaNuevoOracleSQL(int id_ingrediente_receta, String cantidad, Ingrediente id_ingredienteNuevo, int id_recetaNueva) {
        try {

            PreparedStatement st = conexion.prepareStatement("INSERT INTO INGREDIENTE_RECETA VALUES (?, ? ,? ,?)");

            st.setInt(1, id_ingrediente_receta);
            st.setString(2, cantidad);
            st.setInt(3, id_ingredienteNuevo.getId());
            st.setInt(4, id_recetaNueva);


            int filas = st.executeUpdate();
            System.out.println("Filas ingrediente_Receta afectadas: " + filas);

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    // Me retorna cual es la 'id' máxima de la tabla de 'Ingrediente'
    public static int LeerIngredienteIDMaximo() {
        // Estructura de datos

        int num = 0;

        try {

            Statement stmt = conexion.createStatement();

            ResultSet rset = stmt.executeQuery("SELECT MAX(ID) AS MAXIMO FROM INGREDIENTE");
            while (rset.next()) {
                num = rset.getInt("MAXIMO");
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;
    }

    // Inserto en la base de datos el 'id' del ingrediente nuevo, que he hallado su 'id' en la función anterior y  su nombre
    public static void introducirIngredienteNuevoOracleSQL(int id_ingredienteNuevo, String ingrediente) {
        try {

            PreparedStatement st = conexion.prepareStatement("INSERT INTO INGREDIENTE VALUES (?, ?)");

            st.setInt(1, id_ingredienteNuevo);
            st.setString(2, ingrediente);

            int filas = st.executeUpdate();
            System.out.println("Filas ingrediente nuevo afectadas: " + filas);

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    // Inserto en la base de datos las instrucciones que me faltaban en el último paso al darle al botón 'Añadir receta'
    public static void introducirInstruccionesRecetaNuevaOracleSQL(String instrucciones,int id_RecetaNueva) {
        try {

            PreparedStatement st = conexion.prepareStatement("UPDATE RECETA SET INSTRUCCIONES=? WHERE ID=?");

            st.setString(1, instrucciones);
            st.setInt(2, id_RecetaNueva);

            int filas = st.executeUpdate();
            System.out.println("Filas añadir Instrucciones Receta nueva afectadas: " + filas);

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    // En las siguientes funciones, importo la lista de las diferentes tablas de la base de datos a los ArrayList y los cargo en el constructor de la 'VPrincipal'
    public static List<Ingrediente> ListaIngredienteOracleSQL() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        try {

            Statement stmt = conexion.createStatement();

            ResultSet rset = stmt.executeQuery("SELECT * FROM INGREDIENTE");
            while (rset.next()) {
                Ingrediente ingrediente = new Ingrediente(
                        rset.getInt("ID"),
                        rset.getString("INGREDIENTE"));

                ingredientes.add(ingrediente);
            }

            stmt.close();

        } catch (SQLException e) {
            logger.error(e);
        }
        return ingredientes;
    }

    public static List<Ingrediente> ingredientesReceta(int receta_id) {
        List<Ingrediente> ingredientes = new ArrayList<>();

        try {

            PreparedStatement st = conexion.prepareStatement("SELECT * FROM INGREDIENTE I, INGREDIENTE_RECETA IR WHERE IR.INGREDIENTE_ID = I.ID AND RECETA_ID=?");
            st.setInt(1, receta_id);

            ResultSet rset = st.executeQuery();
            while (rset.next()) {
                Ingrediente ingrediente = new Ingrediente(
                        rset.getInt("ID"),
                        rset.getString("INGREDIENTE"));
                ingredientes.add(ingrediente);
            }


        } catch (SQLException e) {
            logger.error(e);
        }

        return ingredientes;
    }

    public static List<Receta> RecetaOracleSQL() {
        List<Receta> recetas = new ArrayList<>();
        try {

            Statement stmt = conexion.createStatement();

            ResultSet rset = stmt.executeQuery("SELECT * FROM RECETA");
            while (rset.next()) {
                Receta receta = new Receta(
                        rset.getInt("ID"),
                        rset.getString("RECETA"),
                        rset.getString("INSTRUCCIONES"));

                // Asocio los ingredientes a la receta, para que me los importe Java
                receta.getIngredientes().addAll(ingredientesReceta(receta.getId()));

                recetas.add(receta);
            }


            stmt.close();

        } catch (SQLException e) {
            logger.error(e);
        }
        return recetas;
    }

    public static List<Ingrediente_receta> ListaIngrediente_recetaOracleSQL() {
        List<Ingrediente_receta> ingrediente_recetas = new ArrayList<>();
        try {

            Statement stmt = conexion.createStatement();

            ResultSet rset = stmt.executeQuery("SELECT * FROM INGREDIENTE_RECETA");
            while (rset.next()) {

                Ingrediente_receta ingredientereceta = new Ingrediente_receta(
                        rset.getInt("ID"),
                        rset.getString("CANTIDAD"),
                        rset.getInt("INGREDIENTE_ID"),
                        rset.getInt("RECETA_ID"));

                ingrediente_recetas.add(ingredientereceta);
            }


            stmt.close();

        } catch (SQLException e) {
            logger.error(e);
        }
        return ingrediente_recetas;
    }



    // Las siguientes funciones las tenía para contar los registros que tenía cada tabla y luego sumarle 1, para hallar el 'id', pero al final daban errores porque podían coincidir con 'id' anteriores
    public static int contarRecetasOracleSQL() {
        int numRecetas = 0;

        try {

            Statement stmt = conexion.createStatement();

            ResultSet rset = stmt.executeQuery("SELECT COUNT(*)  AS TOTAL FROM RECETA");
            while (rset.next()) {

                numRecetas = rset.getInt("TOTAL");
            }

            stmt.close();


        } catch (SQLException e) {
            logger.error(e);
        }
        return numRecetas;
    }

    public static int contarIngrediente_RecetasOracleSQL() {
        int numIngrediente_recetas = 0;

        try {

            Statement stmt = conexion.createStatement();

            ResultSet rset = stmt.executeQuery("SELECT COUNT(*)  AS TOTAL FROM INGREDIENTE_RECETA");
            while (rset.next()) {

                numIngrediente_recetas = rset.getInt("TOTAL");
            }

            stmt.close();


        } catch (SQLException e) {
            logger.error(e);
        }
        return numIngrediente_recetas;
    }

    public static int contarIngredientesOracleSQL() {
        int numIngredientes = 0;

        try {

            Statement stmt = conexion.createStatement();

            ResultSet rset = stmt.executeQuery("SELECT COUNT(*)  AS TOTAL FROM INGREDIENTE");
            while (rset.next()) {

                numIngredientes = rset.getInt("TOTAL");
            }


            stmt.close();


        } catch (SQLException e) {
            logger.error(e);
        }


        return numIngredientes;
    }

}
