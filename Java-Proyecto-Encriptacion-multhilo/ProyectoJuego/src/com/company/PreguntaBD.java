package com.company;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaBD {

    public static void jugadorNuevo(String nombre, int record, String apellidos, String nick, int edad, String contrasena) {

        PreparedStatement ps;

        int idJugador = LeerIDdMaximo() + 1; // No hace falta pasar el id, SQLite te lo indexa automáticamente

        try {

            Connection conexion = DriverManager.getConnection("jdbc:sqlite:Trivia.db");
            Statement sentencia = conexion.createStatement();
            ps = conexion.prepareStatement("insert into JUGADOR(NOMBRE,RECORD,APELLIDOS, NICK, EDAD ,CONTRASENA, JUGANDO) values (?,?,?,?,?,?,?)");


            //ps.setInt(1,idJugador); // No hace falta pasar el id, SQLite te lo indexa automáticamente
            ps.setString(1, nombre);
            ps.setInt(2, record);
            ps.setString(3, apellidos);
            ps.setString(4, nick);
            ps.setInt(5, edad);
            ps.setString(6, contrasena);
            ps.setInt(7, 1); // Ya pongo el jugador a jugando directamente a 1

            ps.executeUpdate();

            // Mensaje confirmación
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
            System.out.println("Se han insertado los datos");
            mensaje("Se han insertado los datos");

            // Cerrar conexiones
            sentencia.close();
            conexion.close();


        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }




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
            //e.printStackTrace();
        }

        return num;
    }

    public static int LeerIDdMaximo() {
        // Estructura de datos

        int num = 0;


        // Cadena de conexión
        String cadenaConexion = "jdbc:sqlite:Trivia.db";

        try {
            // Conectar a la BD
            Connection conexion = DriverManager.getConnection(cadenaConexion);

            // Operaciones con la BD

            // Consulta simple
            String sql = "SELECT MAX(ID) AS MAXIMO FROM JUGADOR";
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
            //e.printStackTrace();
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
            //e.printStackTrace();
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
            //e.printStackTrace();
        }

        return num;
    }

    public static ArrayList<Jugador> LeerJugadorSQLite() {
        // Estructura de datos
        ArrayList<Jugador> jugadores = new ArrayList<>();

        // Cadena de conexión
        String cadenaConexion = "jdbc:sqlite:Trivia.db";

        try {
            // Conectar a la BD
            Connection conexion = DriverManager.getConnection(cadenaConexion);

            // Operaciones con la BD

            // Consulta simple
            String sql = "SELECT * FROM JUGADOR ORDER BY ID";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // Recorrer el ResultSet
            while (rs.next()) {

                Jugador jugador = new Jugador(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"),
                        rs.getString("NICK"),
                        rs.getInt("EDAD"),
                        rs.getString("CONTRASENA"),
                        rs.getInt("RECORD"),
                        rs.getInt("JUGANDO")

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
            //e.printStackTrace();
        }

        return jugadores;
    }


    public static void actualizarJugando(int jugando, String jugador) {

        PreparedStatement ps;

        try {


            Connection conexion = DriverManager.getConnection("jdbc:sqlite:Trivia.db");
            Statement sentencia = conexion.createStatement();
            ps = conexion.prepareStatement("update JUGADOR set  JUGANDO=? where NICK=?");

            ps.setInt(1, jugando);
            ps.setString(2, jugador);


            ps.executeUpdate();

            // Confirmación mensaje
            //JOptionPane.showMessageDialog(null, "Se ha actualizado el jugador "+jugador.toUpperCase()+" ha 'JUGANDO' (1) ...");
            System.out.println("Se ha actualizado el jugador " + jugador.toUpperCase() + " a 'Jugando':" + jugando + " ...");
            mensaje("Se ha actualizado el jugador " + jugador.toUpperCase() + " a 'Jugando':" + jugando + " ...");
            // Cerrar conexiones
            sentencia.close();
            conexion.close();


        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }


    public static void actualizarPuntos(int puntos, String jugador) {

        PreparedStatement ps;

        try {


            Connection conexion = DriverManager.getConnection("jdbc:sqlite:Trivia.db");
            Statement sentencia = conexion.createStatement();
            ps = conexion.prepareStatement("update JUGADOR set  RECORD=? where NICK=?");

            ps.setInt(1, puntos);
            ps.setString(2, jugador);


            ps.executeUpdate();

            // Confirmación mensaje
            //JOptionPane.showMessageDialog(null, "Se ha actualizado el jugador "+jugador.toUpperCase()+" ha 'JUGANDO' (1) ...");
            System.out.println("Se ha actualizado los puntos del jugador " + jugador.toUpperCase() + " a " + puntos + " puntos ...");
            mensaje("Se ha actualizado los puntos del jugador " + jugador.toUpperCase() + " a " + puntos + " puntos ...");
            // Cerrar conexiones
            sentencia.close();
            conexion.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void actualizarJugandoTodosJugadores() {

        PreparedStatement ps;

        try {


            Connection conexion = DriverManager.getConnection("jdbc:sqlite:Trivia.db");
            Statement sentencia = conexion.createStatement();
            ps = conexion.prepareStatement("update JUGADOR set JUGANDO=?");

            ps.setInt(1, 0);


            ps.executeUpdate();

            // Confirmación mensaje
            //JOptionPane.showMessageDialog(null, "Se han actualizado todos los jugadores a 'NO JUGANDO' (0) ");
            System.out.println("Servidor: Se han actualizado todos los jugadores a 'NO JUGANDO' (0) ");
            mensaje("Servidor: Se han actualizado todos los jugadores a 'NO JUGANDO' (0) ");

            // Cerrar conexiones
            sentencia.close();
            conexion.close();


        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public static void actualizarPuntosJugadoresaCero() {

        PreparedStatement ps;

        try {


            Connection conexion = DriverManager.getConnection("jdbc:sqlite:Trivia.db");
            Statement sentencia = conexion.createStatement();
            ps = conexion.prepareStatement("update JUGADOR set  RECORD=?");

            ps.setInt(1, 0);

            ps.executeUpdate();

            // Confirmación mensaje
            //JOptionPane.showMessageDialog(null, "Se han actualizado todos los puntos de los jugadores a 0");
            System.out.println("Servidor: Se han actualizado todos los puntos de los jugadores a 0");
            mensaje("Servidor: Se han actualizado todos los puntos de los jugadores a 0");

            // Cerrar conexiones
            sentencia.close();
            conexion.close();


        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public static ArrayList<Pregunta> LeerPreguntaSQLite() {
        // Estructura de datos
        ArrayList<Pregunta> preguntas = new ArrayList<>();

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
            //e.printStackTrace();
        }

        return preguntas;
    }

    public static ArrayList<Respuesta> LeerRespuestaSQLite() {

        // Estructura de datos
        ArrayList<Respuesta> respuestas = new ArrayList<>();

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
            //e.printStackTrace();
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
            //e.printStackTrace();
        }

    }

    // Función para que salga la ventana siempre en primer plano
    public static void mensaje(String mensaje) {
        JDialog dialog = new JOptionPane(mensaje,JOptionPane.ERROR_MESSAGE,JOptionPane.DEFAULT_OPTION).createDialog(" Mensaje");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        dialog.dispose();
    }
}

