package com.company;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.*;
import java.util.regex.Pattern;

public class HiloServidor extends Thread {


    int contador = 0;

    // Atributo
    //private String nombre;
    SSLSocket cliente; // Tiene que recibir un socket obligatoriamente

    // Constructor con el socket
    public HiloServidor(SSLSocket cliente, int contador) throws IOException {
        this.cliente = cliente;
        this.contador = contador;
    }

    @Override
    // EL servidor va recibiendo las peticiones de los clientes que están en un while(true)
    // Aquí se pone la lógica del servidor
    public void run() {


/*
        ArrayList<Jugador> listaJugadores = new ArrayList<>();

        Jugador jugador1 = new Jugador(1,"Juanan", "Ruiz", "jonrula", 50, "1234",37);
        Jugador jugador2 = new Jugador(2,"Paco", "Lopez", "paco", 34, "1234",70);
        Jugador jugador3 = new Jugador(3,"Luis", "Ramires", "luismi", 48, "1234",50);
        Jugador jugador4 = new Jugador(4,"Sara", "Mendez", "sarita", 25, "1234",56);

        listaJugadores.add(jugador1);
        listaJugadores.add(jugador2);
        listaJugadores.add(jugador3);
        listaJugadores.add(jugador4);

*/

        escribirLog("Cargando jugadores, preguntas y respuestas de la base datos ...");


        // Declaro unos ArrayLIOst y lo lleno con los datos de la BD SQLite
        ArrayList<Jugador> jugadores = PreguntaBD.LeerJugadorSQLite();
        ArrayList<Pregunta> preguntas = PreguntaBD.LeerPreguntaSQLite();
        ArrayList<Respuesta> respuestas = PreguntaBD.LeerRespuestaSQLite();

        // Añado a cada Pregunta su correspondiente ArrayList de respuestas
        for (Pregunta pregunta : preguntas) {
            for (Respuesta respuesta : respuestas) {
                if (pregunta.getId() == respuesta.getPregunta_id()) {
                    pregunta.getRespuestas().add(respuesta);
                    respuesta.setPregunta(pregunta);
                }
            }

        }


        // Saco por pantalla para confirmar

        System.out.println("RECIBIENDO DATOS DESDE LA BASE DE DATOS TRIVIAL ...");


        System.out.println();


        boolean correcto = false;
        String nombre = "";
        String contrasena = "";
        String nombreNuevo = "";
        String apellidoNuevo = "";
        String edad = "";
        String nick = "";
        String contrasenaNueva = "";
        int opcion = 0;


        try {

/*            // Creo el flujo de entrada de datos de tipo Object para leer los datos del cliente
            ObjectInputStream recibiendoMensaje = new ObjectInputStream(cliente.getInputStream());*/


            // Declaro flujos de entrada y salida
            DataOutputStream envioDato = new DataOutputStream(cliente.getOutputStream());
            DataInputStream reciboDato = new DataInputStream(cliente.getInputStream());

            String menu = ("""
                    SERVIDOR:
                    MENU INICIO
                    Elige una opción:
                                        
                        1 Entrar
                        2 Registar Usuario
                        3 Salir
                        
                    Opción: """);


            // Enviar menu al cliente
            envioDato.writeUTF(menu);

            // Recibo opcion del cliente
            System.out.println("Recibiendo opción del menú del cliente ...");
            String respuestaMenu = reciboDato.readUTF().trim();
            System.out.println("SERVIDOR: Opción " + respuestaMenu);

            // Comprobamos si la opcion del menú es correcta
            boolean menuCorrecto = numeroCorrectoEntreLimites(respuestaMenu, 1, 3);
            envioDato.writeBoolean(menuCorrecto);

            while (!menuCorrecto) {
                envioDato.writeUTF("SERVIDOR: Error !! ... introduce una opción correcta !!\nSERVIDOR: Opción: ");
                respuestaMenu = reciboDato.readUTF().trim();
                System.out.println(edad);
                menuCorrecto = numeroCorrectoEntreLimites(respuestaMenu, 1, 3);
                envioDato.writeBoolean(menuCorrecto);
            }

            // Parseo el valor  String que recojo del usuario
            int opcionEnvio = Integer.parseInt(respuestaMenu);
            while (opcionEnvio != 3) {

                /* IMPORTANTE:
                Refresco el arrayList de jugadores dentro del 'while',
                porque se puede haber instanciado otro jugador y el valor de jugando puede haber cambiado,
                y dentro del 'while' no se refresca el nuevo estado de 'jugando', solo al comienzo del programa y no están actualizados esos datos.
                Así me aseguro que todos los datos del jugador están actualizados, si por ejemplo:
                    - Se instancia un nuevo jugador (cliente o hilo) y este sale del juego dando 'fin' su estado de jugando se actualiza a 0
                    - Desde el mismo jugador que estaba instanciado en otro hilo, intentaba jugar, no me dejaba porque tenía 'jugando':1
                      pero si sale el mismo jugador desde el otro hilo , pasa 'jugando' a 0 y ya puedo entrar desde otro hilo con el mismo nombrede jugador.
                      Para eso se tienen que actualizar los datos dentro del bucle 'while'.
                */

                jugadores = PreguntaBD.LeerJugadorSQLite();

                switch (opcionEnvio) {
                    case 1:

                        envioDato.writeUTF("SERVIDOR Nick: ");
                        //nombre = reciboDato.readUTF();
                        //System.out.println(nombre);

                        // Recibo mensaje cifrado y lo descifro (lo meto en una variable String)
                        nombre = descifrarMensajeServidor(cliente);

                        envioDato.writeUTF("SERVIDOR Contraseña: ");
                        contrasena = reciboDato.readUTF();
                        System.out.println("Contraseña encriptada recibide del cliente " + nombre + ": " + contrasena);

                        // Compruebo Usuario y contraseña y que no este jugando --> 0
                        for (Jugador jugador : jugadores) {
                            if (jugador.getNick().equalsIgnoreCase(nombre) &&
                                    jugador.getContrasena().equalsIgnoreCase(contrasena) &&
                                    jugador.getJugando() == 0) {

                                correcto = true;

                                // actualizamos el valor de 'jugando' en la BD para que no juegue otra vez, mientras está jugando
                                PreguntaBD.actualizarJugando(1, nombre);
                            }
                        }


                        // Le mando al cliente la comprobación de usuario y contraseña si es correcta, para que pueda entrar a jugar
                        envioDato.writeBoolean(correcto);

                        if (correcto) {

                            escribirLog("Usuario " + nombre.toUpperCase() + " validado");

                            // Servidor
                            // Generar clave publica y privada
                            // mandar clave publica
                            // Mandar reglas de juego (Firma digital con privada)


                            // Se verifica, si esta validado la firma le ofrecemos el juego

                            String juego = ("""
                                    SERVIDOR: Usuario validado ...
                                                                      
                                    MENU JUGAR
                                    Elige una opción:
                                      
                                        1 Aceptar reglas y jugar 
                                        2 Volver
                                        
                                    Opción: """);

                            envioDato.writeUTF(juego);

                            /*
                           // Recibo opcion del cliente
                            System.out.println("Recibiendo opción del menú del cliente ...");
                            opcion = reciboDato.readInt();
                            System.out.println("SERVIDOR: Opción " + opcion);
                            */


                            // Recibo opcion del cliente
                            System.out.println("Recibiendo opción del menú del cliente ...");
                            respuestaMenu = reciboDato.readUTF().trim();
                            System.out.println("SERVIDOR: Opción " + respuestaMenu);

                            // Comprobamos si la opcion del menú es correcta
                            menuCorrecto = numeroCorrectoEntreLimites(respuestaMenu, 1, 3);
                            envioDato.writeBoolean(menuCorrecto);

                            while (!menuCorrecto) {
                                envioDato.writeUTF("SERVIDOR: Error !! ... introduce una opción correcta (1,2)\nSERVIDOR: Opción: ");
                                respuestaMenu = reciboDato.readUTF().trim();
                                System.out.println(edad);
                                menuCorrecto = numeroCorrectoEntreLimites(respuestaMenu, 1, 3);
                                envioDato.writeBoolean(menuCorrecto);
                            }

                            // Parseo el valor  String que recojo del usuario
                            opcion = Integer.parseInt(respuestaMenu);

                            switch (opcion) {
                                case 1:
                                    // Recibimos la firma digital, lo firmamos y lo mandamos
                                    firmaDigitalServidor(cliente);

                                    // Si el cliente verifica que la firma es correcta,me manda una booleana que s true y empiezo a mandarle las preguntas

                                    boolean firmaOK = reciboDato.readBoolean();

                                    //System.out.println("CLIENTE: " + firmaOK);

                                    if (firmaOK) {

                                        escribirLog("Usuario " + nombre.toUpperCase() + " --> firma digital validada");


                                        String fin = "";
                                        String respuestasServidor = "";
                                        String preguntasServidor = "";
                                        String bienvenida = "";
                                        String puntos = "0";

                                        bienvenida = "\n************************************************************ TRIVIAL ************************************************************\n\n" +
                                                "Bienvenido " + nombre.toUpperCase() + " a Trivial\n";

                                        envioDato.writeUTF(bienvenida);


                                        while (!fin.equalsIgnoreCase("fin") && preguntas.size() > 0) {

                                            opcion = 0;
                                            int contador = 1;

                                            // Empezamos a mandar las preguntas

                                            preguntasServidor = "SERVIDOR: Elige una pregunta: \n\n";


                                            for (Pregunta pregunta : preguntas) {
                                                preguntasServidor += "\t" + contador + "  " + pregunta.getPregunta() + "\n";
                                                contador++;
                                            }

                                            preguntasServidor += "\nOPCIÓN: ";


                                            // Mando las preguntas al cliente
                                            envioDato.writeUTF(preguntasServidor);

                                            // Recibo la opción de la pregunta escogida, como String para verificar si es un número !! (NO CIFRADA)
                                            //String eleccionPregunta = reciboDato.readUTF();

                                            // Recibo la opción de la pregunta escogida, como String para verificar si es un número !! (CIFRADA)
                                            String eleccionPregunta = descifrarMensajeServidor(cliente);

                                            // Compruebo el dato si es valido y le mando la verificación al cliente
                                            boolean comprobarNumeroPregunta = numeroCorrectoEntreLimites(eleccionPregunta, 1, preguntas.size());
                                            envioDato.writeBoolean(comprobarNumeroPregunta);

                                            // Compruebo los datos hasta que sean correctos
                                            while (!comprobarNumeroPregunta) {
                                                envioDato.writeUTF("SERVIDOR: Error !!!  introduce una opción correcta entre 1 y " + preguntas.size() + "\n" + preguntasServidor);
                                                //eleccionPregunta = reciboDato.readUTF(); // SIN CIFRAR
                                                eleccionPregunta = descifrarMensajeServidor(cliente); // CIFRADA
                                                comprobarNumeroPregunta = numeroCorrectoEntreLimites(eleccionPregunta, 1, preguntas.size());
                                                envioDato.writeBoolean(comprobarNumeroPregunta);
                                            }


                                            // Recorro el Arraylist de preguntas y selecciono la pregunta escogida , voy rellenando el String y se la mando al cliente

                                            contador = 1;

                                            // Convierto la pregunta seleccionada a número
                                            opcion = Integer.parseInt(eleccionPregunta);

                                            respuestasServidor = "\nSERVIDOR: Elige una respuesta: \n\n";

                                            respuestasServidor += preguntas.get(opcion - 1).getPregunta() + "\n\n";

                                            for (Respuesta respuesta : preguntas.get(opcion - 1).getRespuestas()) {
                                                respuestasServidor += "\t\t" + contador + " " + respuesta.getRespuesta() + "\n";
                                                contador++;
                                            }

                                            respuestasServidor += "\nRespuesta: ";

                                            // Mando la pregunta seleccionada junto a sus respuestas
                                            envioDato.writeUTF(respuestasServidor);

                                            // Recibo la respuesta seleccionada (Sin cifrar)
                                            //String eleccionRespuesta = reciboDato.readUTF();

                                            // Recibo la respuesta seleccionada (Para descifrar)
                                            String eleccionRespuesta = descifrarMensajeServidor(cliente);

                                            // Compruebo el dato si es valido y le mando la verificación al cliente
                                            boolean comprobarNumeroRespuesta = numeroCorrectoEntreLimites(eleccionRespuesta, 1, respuestas.size());
                                            envioDato.writeBoolean(comprobarNumeroRespuesta);

                                            // Compruebo que la respuesta elegida es un número:

                                            while (!comprobarNumeroRespuesta) {
                                                envioDato.writeUTF("\nSERVIDOR: ERROR !!!  introduce una opcion correcta entre 1 y " + preguntas.get(opcion - 1).getRespuestas().size() + "\n" + respuestasServidor);
                                                //eleccionRespuesta = reciboDato.readUTF(); // Sin cifrar
                                                eleccionRespuesta = descifrarMensajeServidor(cliente); // Respuesta para descifrar
                                                comprobarNumeroRespuesta = numeroCorrectoEntreLimites(eleccionRespuesta, 1, respuestas.size());
                                                envioDato.writeBoolean(comprobarNumeroRespuesta);

                                            }


                                            // Comprobar si la respuesta es correcta
                                            boolean respuestaCorrecta = false;

                                            // Convierto a int el String de la respuesta seleccionada
                                            int respuesta = Integer.parseInt(eleccionRespuesta);


                                            if (preguntas.get(opcion - 1).getRespuestas().get(respuesta - 1).getCorrecta() == 1) {
                                                System.out.println("OK");
                                                respuestaCorrecta = true;
                                            }


                                            if (respuestaCorrecta) {
                                                envioDato.writeUTF("\nSERVIDOR: Respuesta correcta !!\nQuieres seguir jugando ? (intro o 'fin' para salir) ");
                                                for (Jugador jugador : jugadores) {
                                                    if (jugador.getNick().equalsIgnoreCase(nombre)) {
                                                        System.out.println("Puntos Antes: " + jugador.getRecord());
                                                        jugador.setRecord(jugador.getRecord() + 10);
                                                        puntos = String.valueOf(jugador.getRecord());
                                                        System.out.println("Puntos Actualizados de " + nombre + ": " + jugador.getRecord());

                                                        // Actualizamos los puntos en la base de datos
                                                        PreguntaBD.actualizarPuntos(jugador.getRecord(), nombre);
                                                        escribirLog("Usuario " + nombre.toUpperCase() + " --> Respuesta correcta !!, pregunta " + eleccionPregunta + " Puntos: " + jugador.getRecord());

                                                    }
                                                }


                                            } else {
                                                envioDato.writeUTF("SERVIDOR: Fallo !!\nQuieres seguir jugando ? (intro o 'fin' para salir) ");
                                                escribirLog("Usuario " + nombre.toUpperCase() + " --> Respuesta incorrecta !!, pregunta " + eleccionPregunta + " Puntos: " + puntos);
                                            }

                                            //Borro la pregunta del array para que no se repita, pero NO de la BD

                                            preguntas.remove(opcion - 1);


                                            // Recibo la respuesta del cliente si quiere seguir:

                                            fin = reciboDato.readUTF();
                                            System.out.println("FIN: " + fin);

                                        }

                                        // Envio los puntos totales al finalizar el juego.
                                        envioDato.writeUTF("SERVIDOR: Finalización del juego, puntos totales de " + nombre + ": " + puntos);
                                        escribirLog("Usuario " + nombre.toUpperCase() + " --> Finalización del juego, puntos totales: " + puntos);

                                    } else {
                                        escribirLog("Usuario " + nombre.toUpperCase() + " --> firma digital NO validada");

                                    }


                                    break;

                            }


                            // Aquí ya sale del juego en la Opcion 1,y va al MENÚ PRINCIPAL si se ha validado correctamente (nick, contraseña y jugando:1) y  le ha dado a fin, hay que poner jugando:0
                            // SI quiere entrar otra vez, se tiene que validar en la opción 1 (nick, contraseña y jugando)

                            PreguntaBD.actualizarJugando(0, nombre);

                        }

                        // Le mando al cliente la comprobación de usuario y contraseña NO es correcta, no puede entrar a jugar
                        else {
                            escribirLog("Usuario " + nombre.toUpperCase() + " NO validado o ya está jugando ...");
                            envioDato.writeUTF("SERVIDOR Usuario NO validado o ya está jugando");
                            PreguntaBD.mensaje("SERVIDOR Usuario NO validado o ya está jugando");
                        }

                        break;
                    case 2:
                        escribirLog("Registrando usuario nuevo");

                        do {
                            // Mando al cliente que rellene el nombre
                            envioDato.writeUTF("REGISTRO USUARIO NUEVO\n" +
                                    "SERVIDOR Nombre: ");
                            nombreNuevo = reciboDato.readUTF().trim();
                            System.out.println(nombreNuevo);
                            nombreNuevo = nombreNuevo.replace("CLIENTE: ", "");


                            // Comprobamos si el nombre es correcto
                            boolean nombreCorrecto = comprobarNombreoApellidos(nombreNuevo);
                            envioDato.writeBoolean(nombreCorrecto);

                            while (!nombreCorrecto) {
                                envioDato.writeUTF("SERVIDOR: Error !! ... introduce un nombre correcto\nSERVIDOR: Nombre: ");
                                nombreNuevo = reciboDato.readUTF().trim();
                                System.out.println(nombreNuevo);
                                nombreCorrecto = comprobarNombreoApellidos(nombreNuevo);
                                envioDato.writeBoolean(nombreCorrecto);
                            }


                            // Mando al cliente que rellene los apellidos nuevos
                            envioDato.writeUTF("SERVIDOR Apellido: ");
                            apellidoNuevo = reciboDato.readUTF().trim();
                            System.out.println(apellidoNuevo);
                            apellidoNuevo = apellidoNuevo.replace("CLIENTE: ", "");


                            // Comprobamos si el apellido es correcto
                            boolean apellidoCorrecto = comprobarNombreoApellidos(apellidoNuevo);
                            envioDato.writeBoolean(apellidoCorrecto);

                            while (!apellidoCorrecto) {
                                envioDato.writeUTF("SERVIDOR: Error !! ... introduce un apellido correcto\nSERVIDOR: Apellido: ");
                                apellidoNuevo = reciboDato.readUTF().trim();
                                System.out.println(apellidoNuevo);
                                apellidoCorrecto = comprobarNombreoApellidos(apellidoNuevo);
                                envioDato.writeBoolean(apellidoCorrecto);
                            }

                            if (jugadores.contains(new Jugador(nombreNuevo, apellidoNuevo))) {
                                boolean jugadorRepetido = false;
                                envioDato.writeBoolean(jugadorRepetido);
                                envioDato.writeUTF("SERVIDOR: EL usuario: '" + nombreNuevo + " " + apellidoNuevo + "' ya existe !!");

                            } else {
                                boolean jugadorRepetido = true;
                                envioDato.writeBoolean(jugadorRepetido);
                            }


                        } while (jugadores.contains(new Jugador(nombreNuevo, apellidoNuevo)));


                        // Mando al cliente que rellene la edad
                        envioDato.writeUTF("SERVIDOR Edad: ");
                        edad = reciboDato.readUTF().trim();
                        System.out.println(edad);
                        edad = edad.replace("CLIENTE: ", "");


                        // Comprobamos si la edad es correcta
                        boolean edadCorrecta = numeroCorrecto(edad);
                        envioDato.writeBoolean(edadCorrecta);

                        while (!edadCorrecta) {
                            envioDato.writeUTF("SERVIDOR: Error !! ... introduce una edad correcta, mayor de 18 años\nSERVIDOR: Edad: ");
                            edad = reciboDato.readUTF().trim();
                            System.out.println(edad);
                            edadCorrecta = numeroCorrecto(edad);
                            envioDato.writeBoolean(edadCorrecta);
                        }


                        // Mando al cliente que rellene el nick
                        envioDato.writeUTF("SERVIDOR Nick: ");
                        nick = reciboDato.readUTF().trim();
                        System.out.println(nick);
                        nick = nick.replace("CLIENTE: ", "");


                        // Comprobamos si el nick es correcta
                        boolean nickCorrecto = comprobarNick(nick);
                        envioDato.writeBoolean(nickCorrecto);

                        while (!nickCorrecto) {
                            envioDato.writeUTF("SERVIDOR: Error !! ... introduce un nick correcto de entre 4 y 20 carácteres sin espacios\nSERVIDOR: Nick: ");
                            nick = reciboDato.readUTF().trim();
                            System.out.println(nick);
                            nickCorrecto = comprobarNick(nick);
                            envioDato.writeBoolean(nickCorrecto);
                        }


                        // Mando al cliente que rellene la contraseña

                        envioDato.writeUTF("SERVIDOR Contraseña: ");
                        contrasenaNueva = reciboDato.readUTF().trim();
                        System.out.println(contrasenaNueva);
                        contrasenaNueva = contrasenaNueva.replace("CLIENTE: ", "");


                        // Comprobamos si la contraseña es correcta
                        boolean contrasenaCorrecta = comprobarContrasena(contrasenaNueva);
                        envioDato.writeBoolean(contrasenaCorrecta);

                        while (!contrasenaCorrecta) {
                            envioDato.writeUTF("SERVIDOR: Error !! ... introduce una contraseña correcta de entre 4 y 20 carácteres sin espacios ni comillas\nSERVIDOR: Contraseña: ");
                            contrasenaNueva = reciboDato.readUTF().trim();
                            System.out.println(contrasenaNueva);
                            contrasenaCorrecta = comprobarContrasena(contrasenaNueva);
                            envioDato.writeBoolean(contrasenaCorrecta);
                        }


                        // Añadimos el nuevo jugador a arrayList de jugadores:
                        //int idJugador = PreguntaBD.LeerIDdMaximo() + 1;
                        //jugadores.add(new Jugador(idJugador, nombreNuevo, apellidoNuevo, nick, Integer.parseInt(edad), contrasenaNueva, 0));


                        escribirLog("Usuario nuevo " + nombreNuevo.toUpperCase() + " registrado");

                        // Añado nuevo jugador a la BD (No hace falta pasar el id, SQLite te lo indexa automáticamente)
                        // Le pongo el estado de 'jugando' a 1, como si ya se hubiese logueado, lo hago directamente en la función , mirar en PreguntaBD
                        PreguntaBD.jugadorNuevo(nombreNuevo, 0, apellidoNuevo, nick, Integer.parseInt(edad), contrasenaNueva);

                        // Actualizo el arrayList
                        jugadores = PreguntaBD.LeerJugadorSQLite();

                        // Le mando al cliente la lista actualizada de jugadores
                        String listaJugadores = "\nSERVIDOR: Lista actualizada de jugadores:\n";
                        listaJugadores += "ID NOMBRE APELLIDOS  NICK  EDAD  CONTRASEÑA RECORD\n";
                        for (Jugador jugador : jugadores) {
                            listaJugadores += jugador.getId() + "  " + jugador.getNombre() + "  " + jugador.getApellidos() + "  " + jugador.getNick() + "  " + jugador.getEdad() + "  " + jugador.getContrasena() + "  " + jugador.getRecord() + "\n";
                        }

                        envioDato.writeUTF(listaJugadores);


                        String juego = ("""
                                SERVIDOR: Usuario nuevo añadido ...
                                                                  
                                MENU JUGAR
                                Elige una opción:
                                  
                                    1 Aceptar reglas y jugar
                                    2 Salir
                                    
                                Opción: """);

                        envioDato.writeUTF(juego);

/*                        // Recibo opcion del cliente
                        System.out.println("Recibiendo opción del menú del cliente ...");
                        opcion = reciboDato.readInt();
                        System.out.println("SERVIDOR: Opción " + opcion);*/


                        // Recibo opcion del cliente
                        System.out.println("Recibiendo opción del menú del cliente ...");
                        respuestaMenu = reciboDato.readUTF().trim();
                        System.out.println("SERVIDOR: Opción " + respuestaMenu);

                        // Comprobamos si la opcion del menú es correcta
                        menuCorrecto = numeroCorrectoEntreLimites(respuestaMenu, 1, 3);
                        envioDato.writeBoolean(menuCorrecto);

                        while (!menuCorrecto) {
                            envioDato.writeUTF("\nSERVIDOR: Error !! ... introduce una opción correcta (1,2)\nOpción: ");
                            respuestaMenu = reciboDato.readUTF().trim();
                            System.out.println(edad);
                            menuCorrecto = numeroCorrectoEntreLimites(respuestaMenu, 1, 3);
                            envioDato.writeBoolean(menuCorrecto);
                        }

                        // Parseo el valor  String que recojo del usuario
                        opcion = Integer.parseInt(respuestaMenu);


                        switch (opcion) {
                            case 1:
                                // Recibimos la firma digital, lo firmamos y lo mandamos
                                firmaDigitalServidor(cliente);

                                // Si el cliente verifica que la firma es correcta,me manda una booleana que s true y empiezo a mandarle las preguntas

                                boolean firmaOK = reciboDato.readBoolean();

                                //System.out.println("CLIENTE: " + firmaOK);

                                if (firmaOK) {

                                    escribirLog("Usuario " + nombreNuevo.toUpperCase() + " --> firma digital validada");

                                    // Cargo de nuevo todas las preguntas y sus respuestas para el nuevo usuario con todas las preguntas y sus respuestas
                                    jugadores = PreguntaBD.LeerJugadorSQLite();
                                    preguntas = PreguntaBD.LeerPreguntaSQLite();
                                    respuestas = PreguntaBD.LeerRespuestaSQLite();

                                    // Añado a cada Pregunta su correspondiente ArrayList de respuestas
                                    for (Pregunta pregunta : preguntas) {
                                        for (Respuesta respuesta : respuestas) {
                                            if (pregunta.getId() == respuesta.getPregunta_id()) {
                                                pregunta.getRespuestas().add(respuesta);
                                                respuesta.setPregunta(pregunta);
                                            }
                                        }

                                    }

                                    String fin = "";
                                    String respuestasServidor = "";
                                    String preguntasServidor = "";
                                    String bienvenida = "";
                                    String puntos = "0";

                                    nombreNuevo = nombreNuevo.replaceAll("CLIENTE: ", "");

                                    bienvenida = "\n************************************************************ TRIVIAL ************************************************************\n\n" +
                                            "Bienvenido " + nombreNuevo.toUpperCase() + " a Trivial\n";

                                    envioDato.writeUTF(bienvenida);


                                    while (!fin.equalsIgnoreCase("fin") && preguntas.size() > 0) {

                                        opcion = 0;
                                        int contador = 1;


                                        // Empezamos a mandar las preguntas

                                        preguntasServidor = "SERVIDOR: Elige una pregunta: \n\n";


                                        for (Pregunta pregunta : preguntas) {
                                            preguntasServidor += "\t" + contador + "  " + pregunta.getPregunta() + "\n";
                                            contador++;
                                        }

                                        preguntasServidor += "\nOPCIÓN: ";

                                        // Mando las preguntas al cliente
                                        envioDato.writeUTF(preguntasServidor);

                                        // Recibo la opción de la pregunta escogida, como String para verificar si es un número !! (NO CIFRADA)
                                        //String eleccionPregunta = reciboDato.readUTF();

                                        // Recibo la opción de la pregunta escogida, como String para verificar si es un número !! (CIFRADA)
                                        String eleccionPregunta = descifrarMensajeServidor(cliente);

                                        // Compruebo el dato si es valido y le mando la verificación al cliente
                                        boolean comprobarNumeroPregunta = numeroCorrectoEntreLimites(eleccionPregunta, 1, preguntas.size());
                                        envioDato.writeBoolean(comprobarNumeroPregunta);

                                        // Compruebo los datos hasta que sean correctos
                                        while (!comprobarNumeroPregunta) {
                                            envioDato.writeUTF("SERVIDOR: Error !!!  introduce una opción correcta entre 1 y " + preguntas.size() + "\n" + preguntasServidor);
                                            //eleccionPregunta = reciboDato.readUTF(); // SIN CIFRAR
                                            eleccionPregunta = descifrarMensajeServidor(cliente); // CIFRADA
                                            comprobarNumeroPregunta = numeroCorrectoEntreLimites(eleccionPregunta, 1, preguntas.size());
                                            envioDato.writeBoolean(comprobarNumeroPregunta);

                                        }


                                        // Recorro el Arraylist de preguntas y selecciono la pregunta escogida , voy rellenando el String y se la mando al cliente

                                        contador = 1;

                                        // Convierto la pregunta seleccionada a número
                                        opcion = Integer.parseInt(eleccionPregunta);

                                        respuestasServidor = "\nSERVIDOR: Elige una respuesta: \n\n";

                                        respuestasServidor += preguntas.get(opcion - 1).getPregunta() + "\n\n";

                                        for (Respuesta respuesta : preguntas.get(opcion - 1).getRespuestas()) {
                                            respuestasServidor += "\t\t" + contador + " " + respuesta.getRespuesta() + "\n";
                                            contador++;
                                        }

                                        respuestasServidor += "\nRespuesta: ";

                                        // Mando la pregunta seleccionada junto a sus respuestas
                                        envioDato.writeUTF(respuestasServidor);

                                        // Recibo la respuesta seleccionada (Sin cifrar)
                                        //String eleccionRespuesta = reciboDato.readUTF();

                                        // Recibo la respuesta seleccionada (Para descifrar)
                                        String eleccionRespuesta = descifrarMensajeServidor(cliente);

                                        // Compruebo el dato si es valido y le mando la verificación al cliente
                                        boolean comprobarNumeroRespuesta = numeroCorrectoEntreLimites(eleccionRespuesta, 1, respuestas.size());
                                        envioDato.writeBoolean(comprobarNumeroRespuesta);

                                        // Compruebo que la respuesta elegida es un número:

                                        while (!comprobarNumeroRespuesta) {
                                            envioDato.writeUTF("\nSERVIDOR: Error !!  introduce una opcion correcta entre 1 y " + preguntas.get(opcion - 1).getRespuestas().size() + "\n" + respuestasServidor);
                                            //eleccionRespuesta = reciboDato.readUTF(); // Sin cifrar
                                            eleccionRespuesta = descifrarMensajeServidor(cliente); // Respuesta para descifrar
                                            comprobarNumeroRespuesta = numeroCorrectoEntreLimites(eleccionRespuesta, 1, respuestas.size());
                                            envioDato.writeBoolean(comprobarNumeroRespuesta);

                                        }


                                        // Comprobar si la respuesta es correcta
                                        boolean respuestaCorrecta = false;

                                        // Convierto a int el String de la respuesta seleccionada
                                        int respuesta = Integer.parseInt(eleccionRespuesta);


                                        if (preguntas.get(opcion - 1).getRespuestas().get(respuesta - 1).getCorrecta() == 1) {
                                            System.out.println("OK");
                                            respuestaCorrecta = true;
                                        }


                                        if (respuestaCorrecta) {
                                            envioDato.writeUTF("\nSERVIDOR: Respuesta correcta !!\nQuieres seguir jugando ? (intro o 'fin' para salir) ");
                                            for (Jugador jugador : jugadores) {
                                                if (jugador.getNick().equalsIgnoreCase(nombre)) {
                                                    System.out.println("Puntos Antes: " + jugador.getRecord());
                                                    jugador.setRecord(jugador.getRecord() + 10);
                                                    puntos = String.valueOf(jugador.getRecord());
                                                    System.out.println("Puntos Actualizados de " + nombre + ": " + jugador.getRecord());

                                                    // Actualizamos los puntos en la base de datos
                                                    PreguntaBD.actualizarPuntos(jugador.getRecord(), nick);
                                                    // Guardamos mensaje
                                                    escribirLog("Usuario " + nombreNuevo.toUpperCase() + " --> Respuesta correcta !!, pregunta " + eleccionPregunta + ", Puntos: " + jugador.getRecord());

                                                }
                                            }


                                        } else {
                                            escribirLog("Usuario " + nombreNuevo.toUpperCase() + " --> Respuesta incorrecta !!, pregunta " + eleccionPregunta + ", Puntos: " + puntos);
                                            envioDato.writeUTF("SERVIDOR: Fallo !!\nQuieres seguir jugando ? (intro o 'fin' para salir) ");
                                        }

                                        //Borro la pregunta del array para que no se repita, pero NO de la BD

                                        preguntas.remove(opcion - 1);


                                        // Recibo la respuesta del cliente si quiere seguir:

                                        fin = reciboDato.readUTF();
                                        System.out.println("FIN: " + fin);

                                    }

                                    // Envio los puntos totales al finalizar el juego.
                                    envioDato.writeUTF("SERVIDOR: Finalización del juego, puntos totales de " + nombreNuevo.toUpperCase() + ": " + puntos);
                                    escribirLog("Usuario " + nombreNuevo.toUpperCase() + "  --> Finalización del juego, puntos totales: " + puntos);

                                } else {
                                    escribirLog("Usuario " + nombre.toUpperCase() + " --> firma digital NO validada");

                                }


                                break;

                        }


                        // Aquí ya sale del juego en la Opcion 2,y va al MENÚ PRINCIPAL si se ha validado correctamente (nick, contraseña y jugando:1) y  le ha dado a fin, hay que poner jugando:0
                        // SI quiere entrar otra vez, se tiene que validar en la opción 1 (nick, contraseña y jugando)
                        PreguntaBD.actualizarJugando(0, nick);



                        break;


                }

 /*             // Envío de nuevo el menú hasta que no de a salir (opción 3)
                envioDato.writeUTF(menu);
                opcion = reciboDato.readInt();*/


                // Enviar menu al cliente
                envioDato.writeUTF(menu);

                // Recibo opcion del cliente
                System.out.println("Recibiendo opción del menú del cliente ...");
                respuestaMenu = reciboDato.readUTF().trim();
                System.out.println("SERVIDOR: Opción " + respuestaMenu);

                // Comprobamos si la opcion del menú es correcta
                menuCorrecto = numeroCorrectoEntreLimites(respuestaMenu, 1, 3);
                envioDato.writeBoolean(menuCorrecto);

                while (!menuCorrecto) {
                    envioDato.writeUTF("\nSERVIDOR: Error !! ... introduce una opción correcta (1,2,3)\nOpción: ");
                    respuestaMenu = reciboDato.readUTF().trim();
                    System.out.println(edad);
                    menuCorrecto = numeroCorrectoEntreLimites(respuestaMenu, 1, 3);
                    envioDato.writeBoolean(menuCorrecto);
                }

                // Parseo el valor  String que recojo del usuario y se lo paso otra vez al buche 'while', para que compare si es diferente de 3
                opcionEnvio = Integer.parseInt(respuestaMenu);


            }

            // Aquí he salido del menú plunsando la opción 3

            /*
            PreguntaBD.actualizarJugando(0,nick);
            PreguntaBD.actualizarJugando(0,nombre);
            */

            if (!nombreNuevo.equalsIgnoreCase("")) {

                envioDato.writeUTF("SERVIDOR: Adios " + nombreNuevo.toUpperCase());
                escribirLog("Usuario " + nombreNuevo.toUpperCase() + " saliendo de Trivial");

                // Al finalizar el juego pongo al jugador nuevo (coger su nick, para pasarle a la función) a 0 otra vez
                PreguntaBD.actualizarJugando(0, nick);


            } else {

                envioDato.writeUTF("SERVIDOR: Adios " + nombre.toUpperCase());
                escribirLog("Usuario " + nombre.toUpperCase() + " saliendo de Trivial");
                PreguntaBD.actualizarJugando(0, nombre);

            }

            // Creo un ranking
            String rankingTrivial = ranking();

            // Le mando el ranking al cliente
            envioDato.writeUTF(rankingTrivial);

            // Cerramos flujos de datos
            envioDato.close();
            reciboDato.close();


        } catch (IOException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (BadPaddingException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (InvalidKeyException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
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


    private String ranking() {
        ArrayList<Jugador> jugadores;
        // Refresco el ArrayList con los valores de la BD
        jugadores = PreguntaBD.LeerJugadorSQLite();

        // Creo un ArrayList de tipo Ranking para scar luego los datos ordenados
        ArrayList<Ranking> rankingfinal = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            //System.out.println(jugador.getNombre()+" "+jugador.getRecord());
            Ranking ranking = new Ranking(jugador.getNombre(), jugador.getRecord());
            rankingfinal.add(ranking);
        }

        // Ordenamos el ArrayList 'rankingFinal' por puntos
        Collections.sort(rankingfinal, Collections.reverseOrder());

        // Voy construyendo el String del ranking y lo saco para el servidor y para el cliente:

        String rankingTrivial = "";
        rankingTrivial += "\nSERVIDOR: LISTA DE PUNTOS ACTUALIZADA:\n";
        System.out.println("\n************************************************* RANKING PUNTOS TRIVIAL ****************************************************\n");
        rankingTrivial += "\n************************************************* RANKING PUNTOS TRIVIAL ****************************************************\n\n";
        System.out.format("%-30s%-20s%-30s%-10s\n", " ", "PUESTO", "NOMBRE", "PUNTOS");
        rankingTrivial += "                                     PUESTO               NOMBRE               PUNTOS\n";
        for (int i = 0; i < rankingfinal.size(); i++) {

            System.out.format("%-32s%-18d%-30s%4d\n", " ", i + 1, rankingfinal.get(i).getNombre(), rankingfinal.get(i).getRanking());
            rankingTrivial += "                                     " + (i + 1) + "                    " + rankingfinal.get(i).getNombre() + "                " + rankingfinal.get(i).getRanking() + "\n";
        }
        System.out.println("\n************************************************************************************************************************************\n");
        rankingTrivial += "\n************************************************************************************************************************************\n";


        return rankingTrivial;
    }


    public static void firmaDigitalServidor(Socket cliente) {

        try {
            // PASO 1, FIRMAR
            // Creamos el par de claves usando el algoritmo RSA
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            KeyPair par = keygen.generateKeyPair();
            PrivateKey privada = par.getPrivate();
            PublicKey publica = par.getPublic();

            //Creamos los flujos
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

            // Mandamos la clave publica
            oos.writeObject(publica);


            // Creamos la firma digital
            System.out.println("Enviamos el mensaje para comprobar");
            String mensaje = """
                    SERVIDOR: 
                    Estas son las reglas del juego TRIVIAL :
                        - Solo puede jugar un jugador a la vez (no hay duplicados de jugadores).
                        - Hay un mínimo de 2 jugadores.
                        - Cada pregunta se contesta solo 1 vez, luego se borra.
                        - Solo hay una respuesta correcta por cada pregunta.
                        - Se puede salir de la aplicación sin contestar todas las preguntas.
                        - Se empieza de nuevo si se sale de la aplicación, no se almacenan los resultados.
                        - Cada respuesta correcta son 10 puntos.
                        - Gana el que más puntos obtenga, al finalizar todos (hilos) los jugadores que salgan del juego
                                 
                    Estas de acuerdo con las reglas del juego (s/n) ?""";

            // Mando el mensaje al cliente
            oos.writeObject(mensaje);

            // Recibo la respuesta
            String respuesta = ois.readObject().toString();
            System.out.println(respuesta);

            // Compruebo la respuesta
            while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                oos.writeObject("SERVIDOR: Error !! ... introduce una opción correcta (s/n)");
                respuesta = ois.readObject().toString();
                System.out.println(respuesta);

            }


            if (respuesta.equalsIgnoreCase("s")) {
                // FIRMA CON CLAVE PRIVADA EL MENSAJE
                // AL  OBJETO Signature SE LE SUMINISTRAN LOS DATOS A FIRMAR
                Signature dsa = Signature.getInstance("SHA1WITHRSA");
                dsa.initSign(privada);

                // Prueba intento de falsificación --> Error
                // mensaje="s";

                dsa.update(mensaje.getBytes());
                byte[] firma = dsa.sign(); //MENSAJE FIRMADO
                oos.writeObject(firma);
            } else {
                respuesta = "SERVIDOR: Sentimos que no estes de acuerdo con las reglas de juego ... ";
                oos.writeObject(respuesta);


            }


        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        }


    }


    public static String descifrarMensajeServidor(Socket cliente) throws IOException, ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {


        String mensaje_descifrado = "";
        try {
            // Creamos los flujos
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

            System.out.println("Leemos la clave");

            // 1 Recibimos la clave publica que nos ha enviado el cliente
            PublicKey clave = (PublicKey) ois.readObject();
            System.out.println("La clave recibida es: " + clave.getAlgorithm());

            // Desciframos con la clave publica (que nos ha enviado anteriormente el cliente)
            // Enviado por el cliente que ha sido cifrado con la privada

            // 2 Recibimos texto encriptado del cliente

            byte[] mensaje = (byte[]) ois.readObject();
            //preparamos el Cipher para descifrar
            System.out.println("El mensaje cifrado recibido es:" + new String(mensaje));

            Cipher descipher = Cipher.getInstance("RSA");
            descipher.init(Cipher.DECRYPT_MODE, clave);

            mensaje_descifrado = new String(descipher.doFinal(mensaje));

            System.out.println("Mensaje descifrado con clave privada: " + mensaje_descifrado);

            //ois.close();
            //oos.close();


        } catch (IOException e) {
            //e.printStackTrace();
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (InvalidKeyException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        } catch (BadPaddingException e) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        }


        return mensaje_descifrado;

    }


    public static boolean numeroCorrectoEntreLimites(String respuesta, int numAbajo, int numArriba) {
        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean correcto = true;

        int numero;


        try {
            numero = Integer.parseInt(respuesta);
            if (numero < numAbajo || numero > numArriba) {
                System.out.println("Error !! ... introduce un número entre 1 y " + numArriba);
                correcto = false;
            }

        } catch (NumberFormatException e) {
            System.out.println("Error !! ... introduce un número");
            correcto = false;
        }

        return correcto;
    }

    public static boolean comprobarNombreoApellidos(String nombre) {

        // Comprueba nombres compuestos tildes de entre 3 y 20 carácteres en total
        boolean correcto = false;
        String regexp = "(?=.{3,20}$)[a-zA-ZáéíóúüñÁÉÍÓÚÑ]+(?:[\\s][a-zA-ZáéíóúüñÁÉÍÓÚÑ]+)*$";

        if (Pattern.matches(regexp, nombre)) {
            correcto = true;
        } else {
            System.out.println("Introduce un formato correcto de nombre o apellido entre 3 y 20 carácteres !!");
        }

        return correcto;
    }


    public static boolean comprobarNick(String nick) {

        // Comprueba nombres sin espacios, ni tildes, solo letras y números entre 4 y 20 carácteres en total

        boolean correcto = false;
        String regexp = "^(?=.{4,20}$)[a-zA-Z0-9ZáéíóúüñÁÉÍÓÚÑ]*$";

        if (Pattern.matches(regexp, nick)) {
            correcto = true;
        } else {
            System.out.println("Introduce un formato correcto de nick, solo letras y números entre 4 y 20 carácteres !!");
        }


        return correcto;
    }

    public static boolean comprobarContrasena(String contrasena) {

        // Comprueba la contraseña sin espacios, con todos los símbolos y que tenga entre 4 y 70 carácteres en total

        boolean correcto = false;
        String regexp = "^(?=.{4,70}$)[a-zA-Z0-9-!|_·$%&/()=?¿ºª'¡<>`*+¨,;.ç#@$ZáéíóúüñÁÉÍÓÚÑ]*$";

        if (Pattern.matches(regexp, contrasena)) {
            correcto = true;
        } else {
            System.out.println("Introduce un formato correcto de contraseña , solo letras y números entre 4 y 20 carácteres sin comillas!!");
        }


        return correcto;
    }


    public static boolean numeroCorrecto(String numero) {

        boolean correcto = true;

        try {
            int comprobaNumero = Integer.parseInt(numero);
            if (comprobaNumero < 18 || comprobaNumero > 120) {
                correcto = false;
                System.out.println("No se puede jugar si no se tiene más de 18 años");
            }
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            System.out.println("Error !!, número incorrecto");
            correcto = false;
        }


        return correcto;
    }


}
