package com.company;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class ServidorMuchosClientes {

    // No igualar a cero, para que no salga el mismo valor
    static int contadorServidor;

    public static void main(String[] args) throws IOException {

        // Actualizo a 0 todos los jugadores cada vez que arranco el servidor
        PreguntaBD.actualizarJugandoTodosJugadores();

        // Reseteo todos los puntos de los jugadores a 0, al arrancar el servidor
        PreguntaBD.actualizarPuntosJugadoresaCero();


        // Creo un serversocket y solo le paso el puerto
        System.setProperty("javax.net.ssl.keyStore", "AlmacenSSL.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "12345678");


        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault(); // Dejo constantemente abierta la escucha
        SSLServerSocket clienteSSL = (SSLServerSocket) factory.createServerSocket(4999);
        System.out.println("Servidor escuchando...");

        escribirLog("Reseteado de todos los puntos de los jugadores a 0 y estado jugando a 0");
        escribirLog("Servidor escuchando...");




        // Aqui solo escucho y derivo a un hilo las peticiones del cliente, cada vez que ejcuto el play del cliente (EN un bucle sin parar)
        // No lo cierro, continuamente escucha
        while (true) {
            // Genero la conexion del cliente hacia el servidor, tengo que instanciar un cliente socktet
            contadorServidor++;
            SSLSocket cliente = (SSLSocket) clienteSSL.accept();
            System.out.println("Establecida conexión desde el cliente " + contadorServidor + " ...");
            escribirLog("Establecida conexión desde el cliente " + contadorServidor + " ...");
            HiloServidor h = new HiloServidor(cliente, contadorServidor);

            h.start();

        }

    }



    public static void escribirLog(String mensaje) {
        Logger logger = Logger.getLogger("Juanan");
        FileHandler fh;

        try {
            fh = new FileHandler("Registros.log", true);

            logger.addHandler(fh);
            logger.setUseParentHandlers(false);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.log(Level.INFO, mensaje);

            fh.close();

        } catch (SecurityException | IOException e) {
            //e.printStackTrace();
        }
    }

}
