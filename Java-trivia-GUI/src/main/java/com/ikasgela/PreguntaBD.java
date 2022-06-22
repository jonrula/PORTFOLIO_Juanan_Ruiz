package com.ikasgela;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaBD {


    public static int LeerRecordMaximo() {
        // Estructura de datos

        int num = 0;


        // Cadena de conexión
        String cadenaConexion = "jdbc:sqlite:Trivia.db";

        try {
            // Conectar a la BD
            Connection conexion = DriverManager.getConnection(cadenaConexion);

            // Operaciones con la BD

            // Consulta simple
            String sql = "SELECT MAX(RECORD) AS MAXIMO FROM JUGADOR";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // Recorrer el ResultSet
            if (rs.next()) { // si hay registro lo extraes

                num = rs.getInt("MAXIMO");

            }

            // Desconectar
            conexion.close();

            //System.out.println("El Record de  en el ArrayList de jugadores es: " + num);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;
    }

    public static boolean comprobarLeerJugador(String nombre) {
        // Estructura de datos

        boolean nombreCorrecto = false;
        String comprobarNombre = "";


        // Cadena de conexión
        String cadenaConexion = "jdbc:sqlite:Trivia.db";

        try {
            // Conectar a la BD
            Connection conexion = DriverManager.getConnection(cadenaConexion);

            // Operaciones con la BD

            // Consulta simple
            String sql = "SELECT NOMBRE FROM JUGADOR WHERE NOMBRE=?";
            PreparedStatement pst = conexion.prepareStatement(sql);

            pst.setString(1, nombre);
            ResultSet rs = pst.executeQuery();

            // Recorrer el ResultSet
            while (rs.next()) {

                comprobarNombre = rs.getString("NOMBRE");

            }

            // Desconectar
            conexion.close();


            if (comprobarNombre.equalsIgnoreCase(nombre)) {
                nombreCorrecto = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombreCorrecto;
    }


    public static int LeerJugadorID_SQLite(String nombreIntroducidoCajetin) {
        // Estructura de datos

        int num = 0;


        // Cadena de conexión
        String cadenaConexion = "jdbc:sqlite:Trivia.db";

        try {
            // Conectar a la BD
            Connection conexion = DriverManager.getConnection(cadenaConexion);

            // Operaciones con la BD

            // Consulta simple
            String sql = "SELECT ID FROM JUGADOR WHERE NOMBRE=?";

            PreparedStatement pst = conexion.prepareStatement(sql);

            pst.setString(1, nombreIntroducidoCajetin);
            ResultSet rs = pst.executeQuery();

            // Recorrer el ResultSet
            while (rs.next()) {

                num = rs.getInt("ID");

            }

            // Desconectar
            conexion.close();

            // System.out.println("El id de " + nombreIntroducidoCajetin + " en el ArrayList de jugadores es: " + num);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return num;
    }

    public static List<Jugador> LeerJugadorSQLite() {
        // Estructura de datos
        List<Jugador> jugadores = new ArrayList<>();

        // Cadena de conexión
        String cadenaConexion = "jdbc:sqlite:Trivia.db";

        try {
            // Conectar a la BD
            Connection conexion = DriverManager.getConnection(cadenaConexion);

            // Operaciones con la BD

            // Consulta simple
            String sql = "SELECT * FROM JUGADOR";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // Recorrer el ResultSet
            while (rs.next()) {

                Jugador jugador = new Jugador(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getInt("RECORD")

                );

                jugadores.add(jugador);
            }

            // Desconectar
            conexion.close();

            /*

            // Mostrar los datos
            for (Jugador jg : jugadores) {

                System.out.println(jg);
            }

             */

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jugadores;
    }

    public static List<Pregunta> LeerPreguntaSQLite() {
        // Estructura de datos
        List<Pregunta> preguntas = new ArrayList<>();

        // Cadena de conexión
        String cadenaConexion = "jdbc:sqlite:Trivia.db";

        try {
            // Conectar a la BD
            Connection conexion = DriverManager.getConnection(cadenaConexion);

            // Operaciones con la BD

            // Consulta simple
            String sql = "SELECT * FROM PREGUNTA";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // Recorrer el ResultSet
            while (rs.next()) {

                Pregunta pregunta = new Pregunta(

                        rs.getInt("ID"),
                        rs.getString("TEXTO")

                );

                preguntas.add(pregunta);
            }

            // Desconectar
            conexion.close();

            /* Mostrar los datos
            for (Pregunta rp : preguntas) {
                System.out.println(rp);
            }

             */

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preguntas;
    }

    public static List<Respuesta> LeerRespuestaSQLite() {
        // Estructura de datos
        List<Respuesta> respuestas = new ArrayList<>();

        // Cadena de conexión
        String cadenaConexion = "jdbc:sqlite:Trivia.db";

        try {
            // Conectar a la BD
            Connection conexion = DriverManager.getConnection(cadenaConexion);

            // Operaciones con la BD

            // Consulta simple
            String sql = "SELECT * FROM RESPUESTA";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // Recorrer el ResultSet
            while (rs.next()) {

                Respuesta respuesta = new Respuesta(

                        rs.getInt("ID"),
                        rs.getString("TEXTO"),
                        rs.getInt("CORRECTA"),
                        rs.getInt("PREGUNTA_ID")

                );

                respuestas.add(respuesta);
            }

            // Desconectar
            conexion.close();

            /* Mostrar los datos
            for (Respuesta rp : respuestas) {
                System.out.println(rp);
            }

             */

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return respuestas;
    }

    public static void EscribirSQLite(int recordMaximo, String nombreIntroducidoCajetin) {
        // Cadena de conexión
        String cadenaConexion = "jdbc:sqlite:Trivia.db";

        try {
            // Conectar a la BD
            Connection conexion = DriverManager.getConnection(cadenaConexion);

            // Inserción
            String sql = "UPDATE JUGADOR SET RECORD=? WHERE NOMBRE=?";

            PreparedStatement pst = conexion.prepareStatement(sql);

            pst.setInt(1, recordMaximo);
            pst.setString(2, nombreIntroducidoCajetin);

            int filasModificadas = pst.executeUpdate();

            if (filasModificadas > 0) {
                System.out.println("OK");
            } else {
                System.err.println("ERROR: Algo no ha ido bien...");
            }

            // Desconectar
            conexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

