package com.ikasgela;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {

    static int[][] ajedrez = {
            {3, 5, 7, 9, 11, 7, 5 , 3},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 2, 2, 2, 2, 2, 2},
            {4, 6, 8, 10, 12, 8, 6, 4},
    };


    static String rojo = "\033[31m";
    static String verde = "\033[32m";
    static String azul = "\033[34m";
    static String reset = "\u001B[0m";
    static String prueba1 = "\033[47m";

    // Pongo como 'static' la ventana 'frame' que me abre la ventana para visualizar el tablero, para que SOLO  trabaje con una ventana y borre la anterior

    static JFrame frame = new JFrame("Ajedrez");


    // Variables para saber la posición en el tablero para el movimiento de cada jugador

    static int columnaOrigen = 0;
    static int columnaDestino = 0;
    static int filaOrigen = 0;
    static int filaDestino = 0;

    // Variables para saber la posición de la figura que hace 'jaque' en el tablero

    static int jaqueFilaOrigen;
    static int jaqueColumnaOrigen;
    static int jaqueFilaDestino;
    static int jaqueColumnaDestino;
    static int tableroFilaOrigen;
    static int tableroColumnaOrigen;
    static int tableroFilaDestino;
    static int tableroColumnaDestino;

    // Tipo de pieza, para el mensaje si has comido pieza o no

    static String piezaOrigen;
    static String piezaDestino;

    static boolean hayJaqueMate = false;
    static boolean comprobarJaque = false;
    static boolean hayEnroqueCorto = true;
    static boolean hayEnroqueLargo = true;
    static boolean hayJaquePropioParaEnroque = false;
    static boolean capturaAlPaso = false;
    static boolean movimientoCorrecto = true;

    // Mensajes de error para el 'enroque Largo'

    static String el1;
    static String el2;
    static String el3;
    static String el4;
    static String el5;
    static String el6;
    static String el7;
    static String el8;
    static String el9;
    static String el10;
    static String el11;
    static String el12;
    static String el13;

    // Este es un mensaje de enroque largo o corto correcto

    static String ec;

    // Este es un mensaje de enroque largo o corto incorrectos

    static String ec0;
    static String ec1;
    static String ec2;
    static String ec3;
    static String ec4;
    static String ec5;
    static String ec6;
    static String ec7;
    static String ec8;
    static String ec9;
    static String ec10;
    static String ec11;
    static String ec12;
    static String ec13;

    // Mensajes de error al comprobar que el propio rey está en jaque y no puede hacer el enroque largo o corto

    static String jp;
    static String jp1;
    static String jp2;
    static String jp3;
    static String jp4;
    static String jp5;
    static String jp6;
    static String jp7;
    static String jp8;
    static String jp9;
    static String jp10;
    static String jp11;
    static String jp12;
    static String jp13;
    static String jp14;
    static String jp15;
    static String jp16;

    // Mensajes de error  de movimientos incorrectos según la pieza elegida

    static String rey;
    static String dama;
    static String torre;
    static String peon;
    static String alfil;
    static String caballo;

    // Mensajes de error para la 'captura al paso' del peón

    static String mensajeCapturaAlPaso;

    // Mensajes de error para cuando es 'Jaque' , para saber la posición de la pieza que hace 'jaque' y que puede ser más de una pieza

    static String j1;
    static String j2;
    static String j3;
    static String j4;
    static String j5;
    static String j6;
    static String j7;
    static String j8;
    static String j9;
    static String j10;
    static String j11;
    static String j12;
    static String j13;
    static String j14;
    static String j15;
    static String j16;
    static String j17;
    static String j18;
    static String j19;
    static String j20;
    static String j21;
    static String j22;
    static String j23;
    static String j24;
    static String j25;
    static String j26;
    static String j27;


    // Variables para comprobar el 'enroque', para saber que solo se mueven la primera vez, lo inicializo a 'false', pero luego NO lo actualizo en el 'do-while', para que se queden con el valor 'true' hasta finalizar la partida.

    static boolean primerMovimientoTorreBlancaIzquierda = false;
    static boolean primerMovimientoTorreBlancaDerecha = false;
    static boolean primerMovimientoTorreAzulIzquierda = false;
    static boolean primerMovimientoTorreAzulDerecha = false;

    static boolean primerMovimientoReyAzul = false;
    static boolean primerMovimientoReyBlancas = false;

    // Mensaje de error para saber si se ha movido previamente la torre y/o el rey y no se puede realizar el enroque

    static String tyreyAzul;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int jugador = 0;
        char cOrigen = 0;
        int fOrigen = 0;
        char cDestino = 0;
        int fDestino = 0;
        boolean correcto;
        boolean jugada = true;
        boolean unoDos = true;


        Random r = new Random();

        /* La siguiente función es una prueba para ver como queda las casillas en blanco y negro, pero necesito hacerlas muy grandes para que se vean.
           Tendría que aumentar el tamaño de la fuente de la consola, pero luego se me vería el texto muy grande y solo me interesa el tablero.

           //tableroNegroyBlanco();

         */


        System.out.println();
        visualizarTablero();
        System.out.println();

        jugador = 1 + r.nextInt(2);

        // Jugador 1 --> Azules
        // Jugador 2 --> Blancas

        String sorteo = "";

        if (jugador == 1) {
            jugada = true;
            unoDos = true;
            sorteo = azul + "AZULES" + reset;
        }
        if (jugador == 2) {
            jugada = false;
            unoDos = false;
            sorteo = "BLANCAS";
        }

        System.out.println();
        System.out.println("Empieza por sorteo: " + sorteo + "\n");


        do {

            // A partir de aquí hay que hacer el 'do-while para alternar entre jugador 1 y 2 (azules y blancas)

            System.out.println(jugada ? azul + "AZULES(arriba): \n" + reset : "BLANCAS (abajo): \n"); // Luego lo pongo
            jugador = (unoDos ? 1 : 2);

            // Actualizo los mensajes de error para que esten 'vacíos', para que no se almacene el valor de la anterior jugada, por cada cambio de jugador, para cuando salga del 'do-while' de movimiento correcto y entre en el 'do-while' de jaqueMate:

            j1 = "";
            j2 = "";
            j3 = "";
            j4 = "";
            j5 = "";
            j6 = "";
            j7 = "";
            j8 = "";
            j9 = "";
            j10 = "";
            j11 = "";
            j12 = "";
            j13 = "";
            j14 = "";
            j15 = "";
            j16 = "";
            j17 = "";
            j18 = "";
            j19 = "";
            j20 = "";
            j21 = "";
            j22 = "";
            j23 = "";
            j24 = "";
            j25 = "";
            j26 = "";
            j27 = "";


            // Actualizo las variables al hacer el 'enroque' para la siguiente jugada, excepto las torres y el rey, que no me interesa actualizar, que se queden con el valor de la variables después de moverse

            hayEnroqueCorto = true;
            hayEnroqueLargo = true;
            hayJaquePropioParaEnroque = false;

            // Actualizo variables cada vez que hacemos una jugada
            // hayJaqueMate = false; --> no hace falta porque solo se va a realizar una vez !!

            capturaAlPaso = false;
            comprobarJaque = false;
            mensajeCapturaAlPaso = "";

            // Actualizo las variables de los mensajes al hacer bien el enroque, este lo saco fuera, porque solo se comprueban cuando sale del bucle'do-while' --> que es cuaqndo el movimiento es correcto

            ec = "";


            do {
                // Lo actualizo aquí, para que se repita el 'do-while' mientras no sea una jugada correcta y no cambie de turno !!
                movimientoCorrecto = true;

                // Actualizo las variables 'piezaOrigen' y 'piezaDestino' mientras el movimiento no sea correcto y se repita el bucle 'do-while' y no se almacene el anterior valor.
                piezaOrigen = "";
                piezaDestino = "";

                // Actualizo todas las variable de mensajes de error mientras se repita el bucle 'do-while'.

                ec0 = "";
                ec1 = "";
                ec2 = "";
                ec3 = "";
                ec4 = "";
                ec5 = "";
                ec6 = "";
                ec7 = "";
                ec8 = "";
                ec9 = "";
                ec10 = "";
                ec11 = "";
                ec12 = "";
                ec13 = "";

                jp = "";
                jp1 = "";
                jp2 = "";
                jp3 = "";
                jp4 = "";
                jp5 = "";
                jp6 = "";
                jp7 = "";
                jp8 = "";
                jp9 = "";
                jp10 = "";
                jp11 = "";
                jp12 = "";
                jp13 = "";
                jp14 = "";
                jp15 = "";
                jp16 = "";

                el1 = "";
                el2 = "";
                el3 = "";
                el4 = "";
                el5 = "";
                el6 = "";
                el7 = "";
                el8 = "";
                el9 = "";
                el10 = "";
                el11 = "";
                el12 = "";
                el13 = "";

                rey = "";
                dama = "";
                torre = "";
                peon = "";
                alfil = "";
                caballo = "";

                tyreyAzul = "";




                do {
                    do {
                        correcto = true;
                        System.out.print("      Elige columna de origen (a/b/c/d/e/f/g/h):");
                        try {
                            cOrigen = br.readLine().toLowerCase().charAt(0);
                        }catch(IndexOutOfBoundsException e){
                            System.out.println(rojo + "      Error !! ... elige una opción correcta --> a/b/c/d/e/f/g/h " + reset);
                            correcto = false;
                        }
                    } while (!correcto);

                } while (!elegirCorigen(cOrigen));

                do {
                    do {
                        correcto = true;
                        System.out.print("      Elige fila de origen (1/2/3/4/5/6/7/8):");

                        try {
                            fOrigen = Integer.parseInt(br.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println(rojo + "      Error !! ... elige una opción correcta --> 1/2/3/4/5/6/7/8 " + reset);
                            correcto = false;
                        }
                    } while (!correcto);
                } while (fOrigen < 1 || fOrigen > 8);

                do {

                    do {
                        correcto = true;
                        System.out.print("      Elige columna de destino (a/b/c/d/e/f/g/h):");
                        try {
                            cDestino = br.readLine().toLowerCase().charAt(0);
                        }catch(IndexOutOfBoundsException e){
                            System.out.println(rojo + "      Error !! ... elige una opción correcta --> a/b/c/d/e/f/g/h " + reset);
                            correcto = false;
                        }
                    } while (!correcto);

                } while (!elegirCdestino(cDestino));


                do {
                    do {
                        correcto = true;
                        System.out.print("      Elige fila de destino (1/2/3/4/5/6/7/8):");
                        try {
                            fDestino = Integer.parseInt(br.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println(rojo + "      Error !! ... elige una opción correcta --> 1/2/3/4/5/6/7/8 " + reset);
                            correcto = false;
                        }
                    } while (!correcto);
                } while (fDestino < 1 || fDestino > 8);

                System.out.println();

                conversionNotacionAlgebraica(cOrigen, fOrigen, cDestino, fDestino);
                tipoPieza();

                // Mensajes de error, relacionadas con la elección de origen y destino de las casillas, elegir piezas contrarias, casillas en origen vacías, elegir pieza en destino propia, misma casilla de origen y destino....

                if (fOrigen == fDestino && cOrigen == cDestino) {
                    System.out.println(rojo + "      Error !! ... No pueden coincidir la casilla de origen con la casilla de destino... " + reset);
                    movimientoCorrecto = false;
                }
                if (ajedrez[filaOrigen][columnaOrigen] == 0) {
                    System.out.println(rojo + "      Error !! ... La casilla que has elegido en origen está vacía !! ... elige otra\n" + reset);
                    movimientoCorrecto = false;
                } else if (jugador == 1 && (ajedrez[filaOrigen][columnaOrigen] == 2 || ajedrez[filaOrigen][columnaOrigen] == 4 || ajedrez[filaOrigen][columnaOrigen] == 6 || ajedrez[filaOrigen][columnaOrigen] == 8 || ajedrez[filaOrigen][columnaOrigen] == 10 || ajedrez[filaOrigen][columnaOrigen] == 12)) {
                    System.out.println(rojo + "      Error !! ... no puedes elegir casillas de tu adversario !! ... la casilla " + reset + "'" + cOrigen + fOrigen + "'" + rojo + " es " + reset + "BLANCA" + rojo + ", tienes que elegir piezas " + azul + "AZULES" + reset);
                    movimientoCorrecto = false;
                } else if (jugador == 2 && (ajedrez[filaOrigen][columnaOrigen] == 1 || ajedrez[filaOrigen][columnaOrigen] == 3 || ajedrez[filaOrigen][columnaOrigen] == 5 || ajedrez[filaOrigen][columnaOrigen] == 7 || ajedrez[filaOrigen][columnaOrigen] == 9 || ajedrez[filaOrigen][columnaOrigen] == 11)) {
                    System.out.println(rojo + "      Error !! ... no puedes elegir casillas de tu adversario !! ... la casilla " + reset + "'" + cOrigen + fOrigen + "'" + rojo + " es " + azul + "AZUL" + rojo + ", tienes que elegir piezas " + reset + "BLANCAS");
                    movimientoCorrecto = false;
                } else if (jugador == 1 && (ajedrez[filaOrigen][columnaOrigen] == 1 || ajedrez[filaOrigen][columnaOrigen] == 3 || ajedrez[filaOrigen][columnaOrigen] == 5 || ajedrez[filaOrigen][columnaOrigen] == 7 || ajedrez[filaOrigen][columnaOrigen] == 9 || ajedrez[filaOrigen][columnaOrigen] == 11) && (ajedrez[filaDestino][columnaDestino] == 1 || ajedrez[filaDestino][columnaDestino] == 3 || ajedrez[filaDestino][columnaDestino] == 5 || ajedrez[filaDestino][columnaDestino] == 7 || ajedrez[filaDestino][columnaDestino] == 9 || ajedrez[filaDestino][columnaDestino] == 11)) {
                    System.out.println(rojo + "      Error !! ... no puedes elegir casillas de destino PROPIAS !! ... la casilla " + reset + "'" + cDestino + fDestino + "'" + rojo + " es " + reset + azul + "AZUL" + reset + rojo + ", tienes que elegir piezas de destino " + reset + "BLANCAS");
                    movimientoCorrecto = false;
                } else if (jugador == 2 && (ajedrez[filaOrigen][columnaOrigen] == 2 || ajedrez[filaOrigen][columnaOrigen] == 4 || ajedrez[filaOrigen][columnaOrigen] == 6 || ajedrez[filaOrigen][columnaOrigen] == 8 || ajedrez[filaOrigen][columnaOrigen] == 10 || ajedrez[filaOrigen][columnaOrigen] == 12) && (ajedrez[filaDestino][columnaDestino] == 2 || ajedrez[filaDestino][columnaDestino] == 4 || ajedrez[filaDestino][columnaDestino] == 6 || ajedrez[filaDestino][columnaDestino] == 8 || ajedrez[filaDestino][columnaDestino] == 10 || ajedrez[filaDestino][columnaDestino] == 12)) {
                    System.out.println(rojo + "      Error !! ... no puedes elegir casillas de destino PROPIAS !! ... la casilla " + reset + "'" + cDestino + fDestino + "'" + rojo + " es " + reset + "BLANCA" + rojo + ", tienes que elegir piezas de destino" + reset + azul + "AZULES" + reset);
                    movimientoCorrecto = false;
                }


                comprobarMovimiento(jugador);

                // Ahora compruebo despues de realizar el movimiento, si es válido y sino saco el error, --> Compruebo los enroques cortos (ec) enroques largos (el) y los posibles jaques propios (jp)... hay que actualizar los valores de las variables de error al repetir el bucle 'do-while'

                if (!movimientoCorrecto) {
                    System.out.println(rojo + "      Error !! ... movimiento incorrecto !!  '" + cOrigen + fOrigen + "' --> '" + cDestino + fDestino + "'  --> " + ec0 + tyreyAzul + jp + jp1 + jp2 + jp3 + jp4 + jp5 + jp6 + jp7 + jp8 + jp9 + jp10 + jp11 + jp12 + jp13 + jp14 + jp15 + jp16 + ec1 + ec2 + ec3 + ec4 + ec5 + ec6 + ec7 + ec8 + ec9 + ec10 + ec11 + ec12 + ec13 + el1 + el2 + el3 + el4 + el5 + el6 + el7 + el8 + el9 + el10 + el11 + el12 + el13 + peon + torre + alfil + caballo + rey + dama + reset);
                }

                System.out.println();

                // Se repite el bucle mientras el usuario no meta bien los valores de origen y destino (no puede elegir piezas contrarias y/o casillas vacías)

            } while (!movimientoCorrecto);

            visualizarTablero();


            System.out.println();


            //valorVariables(jugador, cOrigen, cDestino, fOrigen, fDestino);

            /*
            Ahora saco mensaje por pantalla de 2 operaciones ternarias anidadas:

              (boolean1 ?) true : false;
              (boolean1 ?) true : (boolean2 ?) true : false ;
              (hayJaqueMate ?) true : (movimientoCorrecto ?) true : false;

             */

            System.out.print(hayJaqueMate ? verde + "JAQUE MATE !!" + reset : movimientoCorrecto ? verde + "Movimiento OK: " + piezaOrigen + "'" + cOrigen + fOrigen + "' --> " + piezaDestino + "'" + cDestino + fDestino + "'\n" + ec + j1 + j2 + j3 + j4 + j5 + j6 + j7 + j8 + j9 + j10 + j11 + j12 + j13 + j14 + j15 + j16 + j17 + j18 + j19 + j20 + j21 + j22 + j23 + j24 + j25 + j26 + j27 + reset : rojo + "Error !!... movimiento incorrecto !!\n" + reset);

            // Mensaje de 'Captura al paso' si se ha realizado correctamente o no

            System.out.print(capturaAlPaso ? verde + "CAPTURA AL PASO !! ... " + mensajeCapturaAlPaso : "");
            System.out.println();

            // Ahora cambio de 'jugador' (1 o 2) y de jugada ('AZULES' o 'BLANCAS')

            jugada = !jugada;
            unoDos = !unoDos;

        } while (!hayJaqueMate);

        // hayJaqueMate = false; --> Haz lo contrario de 'hayJaqueMate' -->  !hayJaqueMate = true;
        //  'NO' --> '!'   Mientras 'NO - hayjaqueMate()'  ... sigue ejecutando el bucle...

        mensajeGanador(jugador);

    }

    public static void mensajeGanador(int jugador) {

        if (jugador == 1) {
            System.out.println(verde + "Enhorabuena ganan " + azul + "AZULES" + reset + " !!");
        } else {
            System.out.println(verde + "Enhorabuena ganan " + reset + "BLANCAS !!");
        }

    }

    public static void tableroNegroyBlanco() {

        // comprobación de colores
        //Color colorRosa=new Color(255, 175, 175);
        //Figuras figura= new Figuras("♜");

        System.out.println(" \uD83D\uDC34 \uD83D\uDC51 \uD83D\uDC78 \uD83C\uDFF0 ⚫ ⚪♖ ⬜ ♔ ♕ ♘ ♙ ♗ ♚♛♝♞♜♟"); // Para hacer pruebas
        System.out.println();
        System.out.println("     " + azul + prueba1 + "  ♜  " + reset + azul + "  ♞  " + reset + prueba1 + azul + "  ♝  " + reset + azul + "  ♛  " + reset + prueba1 + azul + "  ♚  " + reset + azul + "  ♝  " + reset + prueba1 + azul + "  ♞  " + reset + azul + "  ♜  " + reset);
        System.out.println("     " + azul + "  ♟  " + reset + prueba1 + azul + "  ♟  " + reset + azul + "  ♟  " + reset + prueba1 + azul + "  ♟  " + reset + azul + "  ♟  " + reset + prueba1 + azul + "  ♟  " + reset + azul + "  ♟  " + reset + prueba1 + azul + "  ♟  " + reset);
        System.out.println("     " + prueba1 + "      " + reset + "      " + prueba1 + "      " + reset + "     " + prueba1 + "      " + reset + "      " + prueba1 + "      " + reset + "     ");
        System.out.println("     " + "      " + prueba1 + "      " + reset + "      " + reset + prueba1 + "     " + reset + "      " + reset + prueba1 + "      " + reset + "      " + reset + prueba1 + "      " + reset);
        System.out.println("     " + prueba1 + "      " + reset + "      " + prueba1 + "      " + reset + "     " + prueba1 + "      " + reset + "      " + prueba1 + "      " + reset + "     ");
        System.out.println("     " + "      " + prueba1 + "      " + reset + "      " + reset + prueba1 + "     " + reset + "      " + reset + prueba1 + "      " + reset + "      " + reset + prueba1 + "      " + reset);
        System.out.println("     " + prueba1 + "  ♜  " + reset + "  ♞  " + reset + prueba1 + "  ♝  " + reset + "  ♛  " + reset + prueba1 + "  ♚  " + reset + "  ♝  " + reset + prueba1 + "  ♞  " + reset + "  ♜  " + reset);
        System.out.println("     " + "  ♟  " + reset + prueba1 + "  ♟  " + reset + "  ♟  " + reset + reset + prueba1 + "  ♟  " + reset + "  ♟  " + reset + prueba1 + "  ♟  " + reset + "  ♟  " + reset + prueba1 + "  ♟  " + reset);
    }

    public static void valorVariables(int jugador, char cOrigen, char cDestino, int fOrigen, int fDestino) {

        // Valores de las variables:
        System.out.println();
        System.out.println("Valor de las variables: ");
        System.out.println("Fila origen: " + filaOrigen);
        System.out.println("Columna Origen: " + columnaOrigen);
        System.out.println("Fila Destino: " + filaDestino);
        System.out.println("Columna Destino: " + columnaDestino);
        System.out.println("Jugador: " + jugador);
        System.out.println("Valor casilla origen '" + cOrigen + fOrigen + "': --> " + ajedrez[filaOrigen][columnaOrigen]);
        System.out.println("Valor casilla destino '" + cDestino + fDestino + "': --> " + ajedrez[filaDestino][columnaDestino]);
        System.out.println("Movimiento pedido: '" + cOrigen + fOrigen + "' --> '" + cDestino + fDestino + "'");
        System.out.println("Jaque Mate: " + hayJaqueMate);
        System.out.println("Captura al paso: " + capturaAlPaso);
        System.out.println("Jaque: " + comprobarJaque);
        System.out.println("Movimiento correcto: " + movimientoCorrecto);
        System.out.println("ENROQUE:");
        System.out.println("Torre azul izquierda: " + primerMovimientoTorreAzulIzquierda);
        System.out.println("Torre azul derecha: " + primerMovimientoTorreAzulDerecha);
        System.out.println("Torre blanca izquierda: " + primerMovimientoTorreBlancaIzquierda);
        System.out.println("Torre blanca derecha: " + primerMovimientoTorreBlancaDerecha);
        System.out.println("Rey azul: " + primerMovimientoReyAzul);
        System.out.println("Rey Blancas: " + primerMovimientoReyBlancas);
        System.out.println("Enroque Corto: " + hayEnroqueCorto);
        System.out.println("Enroque Largo: " + hayEnroqueLargo);
        System.out.println("Jaque a Rey: " + hayJaquePropioParaEnroque);

        System.out.println();
    }

    public static boolean elegirCorigen(char cOrigen) {

        char[] letras = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        boolean correcto = false; // Parto de que el valor que me va a dar el usuario no es correcto (false) y busco el que coincida con las respuestas válidas --> true.


        for (int i = 0; i < letras.length; i++) {
            if (cOrigen == letras[i]) {
                correcto = true;
                break;
            }else{
                correcto = false;
            }


        }

        if (!correcto) {
            System.out.println(rojo + "      Error !! ... elige una opción correcta --> a/b/c/d/e/f/g/h " + reset);
        }


        return correcto;
    }

    public static boolean elegirCdestino(char cDestino) {

        char[] letras = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        boolean correcto = false; // Parto de que el valor que me va a dar el usuario no es correcto (false) y busco el que coincida con las respuestas válidas --> true.


        for (int i = 0; i < letras.length; i++) {
            if (cDestino == letras[i]) {
                correcto = true;
                break;
            }
        }
        if (!correcto) {
            System.out.println(rojo + "      Error !! ... elige una opción correcta --> a/b/c/d/e/f/g/h " + reset);
        }

        return correcto;
    }

    public static void comprobarMovimiento(int jugador) throws IOException {
        // Comprobamos los movimientos de la figuras, en función de la casilla de origen, elegida por el usuario:

        // PEON
        if (ajedrez[filaOrigen][columnaOrigen] == 1 || ajedrez[filaOrigen][columnaOrigen] == 2) {
            moverPeon(jugador);
            comprobarJaque(jugador);
        }

        // TORRE
        if (ajedrez[filaOrigen][columnaOrigen] == 3 || ajedrez[filaOrigen][columnaOrigen] == 4) {
            moverTorre(jugador);
            comprobarJaque(jugador);
        }


        // ALFIL
        if (ajedrez[filaOrigen][columnaOrigen] == 7 || ajedrez[filaOrigen][columnaOrigen] == 8) {
            moverAlfil(jugador);
            comprobarJaque(jugador);
        }


        // CABALLO
        if (ajedrez[filaOrigen][columnaOrigen] == 5 || ajedrez[filaOrigen][columnaOrigen] == 6) {
            moverCaballo(jugador);
            comprobarJaque(jugador);
        }

        // DAMA
        if (ajedrez[filaOrigen][columnaOrigen] == 9 || ajedrez[filaOrigen][columnaOrigen] == 10) {
            moverDama(jugador);
            comprobarJaque(jugador);
        }

        // REY
        if (ajedrez[filaOrigen][columnaOrigen] == 11 || ajedrez[filaOrigen][columnaOrigen] == 12) {
            moverRey(jugador);
            comprobarJaque(jugador);
        }

    }

    public static void conversionNotacionAlgebraica(char cOrigen, int fOrigen, char cDestino, int fDestino) {

        if (cOrigen == 'a') {
            columnaOrigen = 0;
        }
        if (cDestino == 'a') {
            columnaDestino = 0;
        }
        if (cOrigen == 'b') {
            columnaOrigen = 1;
        }
        if (cDestino == 'b') {
            columnaDestino = 1;
        }
        if (cOrigen == 'c') {
            columnaOrigen = 2;
        }
        if (cDestino == 'c') {
            columnaDestino = 2;
        }
        if (cOrigen == 'd') {
            columnaOrigen = 3;
        }
        if (cDestino == 'd') {
            columnaDestino = 3;
        }
        if (cOrigen == 'e') {
            columnaOrigen = 4;
        }
        if (cDestino == 'e') {
            columnaDestino = 4;
        }
        if (cOrigen == 'f') {
            columnaOrigen = 5;
        }
        if (cDestino == 'f') {
            columnaDestino = 5;
        }
        if (cOrigen == 'g') {
            columnaOrigen = 6;
        }
        if (cDestino == 'g') {
            columnaDestino = 6;
        }
        if (cOrigen == 'h') {
            columnaOrigen = 7;
        }
        if (cDestino == 'h') {
            columnaDestino = 7;
        }

        if (fOrigen == 8) {
            filaOrigen = 0;
        }
        if (fDestino == 8) {
            filaDestino = 0;
        }
        if (fOrigen == 7) {
            filaOrigen = 1;
        }
        if (fDestino == 7) {
            filaDestino = 1;
        }
        if (fOrigen == 6) {
            filaOrigen = 2;
        }
        if (fDestino == 6) {
            filaDestino = 2;
        }
        if (fOrigen == 5) {
            filaOrigen = 3;
        }
        if (fDestino == 5) {
            filaDestino = 3;
        }
        if (fOrigen == 4) {
            filaOrigen = 4;
        }
        if (fDestino == 4) {
            filaDestino = 4;
        }
        if (fOrigen == 3) {
            filaOrigen = 5;
        }
        if (fDestino == 3) {
            filaDestino = 5;
        }
        if (fOrigen == 2) {
            filaOrigen = 6;
        }
        if (fDestino == 2) {
            filaDestino = 6;
        }
        if (fOrigen == 1) {
            filaOrigen = 7;
        }
        if (fDestino == 1) {
            filaDestino = 7;
        }
    }

    public static void tipoPieza() {
        if (ajedrez[filaOrigen][columnaOrigen] == 1 || ajedrez[filaOrigen][columnaOrigen] == 2) {
            piezaOrigen = "Peón ";
        }
        if (ajedrez[filaOrigen][columnaOrigen] == 3 || ajedrez[filaOrigen][columnaOrigen] == 4) {
            piezaOrigen = "Torre ";
        }
        if (ajedrez[filaOrigen][columnaOrigen] == 7 || ajedrez[filaOrigen][columnaOrigen] == 8) {
            piezaOrigen = "Alfil ";
        }
        if (ajedrez[filaOrigen][columnaOrigen] == 5 || ajedrez[filaOrigen][columnaOrigen] == 6) {
            piezaOrigen = "Caballo ";
        }
        if (ajedrez[filaOrigen][columnaOrigen] == 9 || ajedrez[filaOrigen][columnaOrigen] == 10) {
            piezaOrigen = "Dama ";
        }
        if (ajedrez[filaOrigen][columnaOrigen] == 11 || ajedrez[filaOrigen][columnaOrigen] == 12) {
            piezaOrigen = "Rey ";
        }


        if (ajedrez[filaDestino][columnaDestino] == 1 || ajedrez[filaDestino][columnaDestino] == 2) {
            piezaDestino = "Peón ";
        }
        if (ajedrez[filaDestino][columnaDestino] == 3 || ajedrez[filaDestino][columnaDestino] == 4) {
            piezaDestino = "Torre ";
        }
        if (ajedrez[filaDestino][columnaDestino] == 7 || ajedrez[filaDestino][columnaDestino] == 8) {
            piezaDestino = "Alfil ";
        }
        if (ajedrez[filaDestino][columnaDestino] == 5 || ajedrez[filaDestino][columnaDestino] == 6) {
            piezaDestino = "Caballo ";
        }
        if (ajedrez[filaDestino][columnaDestino] == 9 || ajedrez[filaDestino][columnaDestino] == 10) {
            piezaDestino = "Dama ";
        }
        if (ajedrez[filaDestino][columnaDestino] == 11 || ajedrez[filaDestino][columnaDestino] == 12) {
            piezaDestino = "Rey ";
        }


    }

    public static void moverPeon(int jugador) throws IOException {

        /*
         1 --> - Al empezar, en las filas  del tablero 2 y 7:
               - El usuario puede elegir entre mover 2 casillas (válido solo la primera vez que mueve el peón) o una casilla, siempre que no tenga ninguna pieza delante
               - Comprobar Jaque

         2 --> Después del primer movimiento, obligatoriamente un casilla hacia delante , siempre que la siguiente casilla este vacía y NO se puede retroceder.

         3 --> Movimiento de captura a la casilla con pieza contraria (solo la casilla en diagonal de la izquierda o la derecha)
               - Comprobar Jaque - Mate !!

         4 --> Si llegas a la primera fila de tu adversario, puedes 'promocionar el peón':
               - Puedes elegir entre las siguientes figuras: peón, torre, caballo, alfil y dama ... luego sustituyes el peón por la pieza que has elegido
                 puedes tener más de 2 torres, o más de 2 alfiles o más de 2 caballo o más de 2 damas
               - Comprobar Jaque
               - Aquí tiene prioridad el  Jaque - Mate !! antes que la 'promoción del peón' (hay que hacer una excepción)
         */

        // JUGADOR 1 AZULES

        // AVANZA 2 casillas --> Movimiento en origen es un peón y la fila origen en el array es el '1' (fila 7 en el tablero), avanzamos 2 casillas (si queremos), y no puede haber piezas por medio --> 'ajedrez[filaOrigen + 1][columnaDestino] == 0'
        if (jugador == 1 && filaOrigen == 1 && ajedrez[filaDestino][columnaDestino] == 0 && ajedrez[filaOrigen + 1][columnaDestino] == 0 && ajedrez[filaOrigen][columnaOrigen] == 1 && filaOrigen + 2 == filaDestino && columnaOrigen == columnaDestino) {

            if (columnaOrigen >= 1 && columnaOrigen <= 6 && ajedrez[filaOrigen][columnaOrigen] == 1 && ajedrez[filaOrigen + 1][columnaDestino] == 0 && ajedrez[filaDestino][columnaDestino] == 0 && ajedrez[filaOrigen][columnaOrigen] == 1 && ajedrez[filaDestino][columnaDestino + 1] == 2 && ajedrez[filaDestino][columnaDestino - 1] == 2) {

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                boolean correcto;
                int opcion = 0;
                do {

                    do {
                        correcto = true;
                        System.out.println();
                        System.out.print(verde + "CAPTURA AL PASO !! ... Elige que peón  quieres utilizar: \n" +
                                (columnaOrigen - 1) + "- Peón izquierdo\n" +
                                (columnaOrigen + 1) + "- Peón derecho\n\n" +

                                "Opción: " + reset);
                        try {
                            opcion = Integer.parseInt(br.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println(rojo + "Error !! ... debes elegir la opcion: " + (columnaOrigen - 1) + " o la opción: " + (columnaOrigen + 1) + reset + "\n");
                            correcto = false;
                        }
                    } while (!correcto);

                } while (opcion != (columnaOrigen - 1) && opcion != (columnaOrigen + 1));

                if (opcion == columnaOrigen - 1) {
                    capturaAlPaso = true;

                    jaqueColumnaDestino = columnaDestino;
                    jaqueColumnaOrigen = columnaDestino - 1;
                    convertiraTablero();

                    ajedrez[filaOrigen][columnaOrigen] = 0;
                    ajedrez[filaOrigen + 2][columnaOrigen - 1] = 0;
                    mensajeCapturaAlPaso = verde + "Peón Blancas '" + (char) tableroColumnaOrigen + "5' --> come a Peón Azul '" + (char) tableroColumnaDestino + "6'\n" + reset;
                } else {
                    capturaAlPaso = true;

                    jaqueColumnaDestino = columnaDestino;
                    jaqueColumnaOrigen = columnaDestino + 1;
                    convertiraTablero();

                    ajedrez[filaOrigen][columnaOrigen] = 0;
                    ajedrez[filaOrigen + 2][columnaOrigen + 1] = 0;
                    mensajeCapturaAlPaso = verde + "Peón Blancas '" + (char) tableroColumnaOrigen + "5' --> come a Peón Azul '" + (char) tableroColumnaDestino + "6'\n" + reset;
                }

                ajedrez[filaOrigen + 1][columnaOrigen] = 2; // En ambos casos va a ir a esta posición que es común

            } else if (columnaOrigen <= 6 && ajedrez[filaOrigen][columnaOrigen] == 1 && ajedrez[filaOrigen + 1][columnaDestino] == 0 && ajedrez[filaDestino][columnaDestino] == 0 && ajedrez[filaOrigen][columnaOrigen] == 1 && ajedrez[filaDestino][columnaDestino + 1] == 2) {
                capturaAlPaso = true;

                jaqueColumnaDestino = columnaDestino;
                jaqueColumnaOrigen = columnaDestino + 1;
                convertiraTablero();


                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaOrigen + 2][columnaOrigen + 1] = 0;
                ajedrez[filaOrigen + 1][columnaOrigen] = 2;
                mensajeCapturaAlPaso = verde + "Peón Blancas '" + (char) tableroColumnaOrigen + "5' --> come a Peón Azul '" + (char) tableroColumnaDestino + "6'\n" + reset;

            } else if (columnaOrigen >= 1 && ajedrez[filaOrigen][columnaOrigen] == 1 && ajedrez[filaOrigen + 1][columnaDestino] == 0 && ajedrez[filaDestino][columnaDestino] == 0 && ajedrez[filaOrigen][columnaOrigen] == 1 && ajedrez[filaDestino][columnaDestino - 1] == 2) {
                capturaAlPaso = true;

                jaqueColumnaDestino = columnaDestino;
                jaqueColumnaOrigen = columnaDestino - 1;
                convertiraTablero();

                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaOrigen + 2][columnaOrigen - 1] = 0;
                ajedrez[filaOrigen + 1][columnaOrigen] = 2;
                mensajeCapturaAlPaso = verde + "Peón Blancas '" + (char) tableroColumnaOrigen + "5' --> come a Peón Azul '" + (char) tableroColumnaDestino + "6'\n" + reset;
            } else {
                ajedrez[filaOrigen][columnaOrigen] = 0; // La casilla de la que parte se queda vacía
                ajedrez[filaDestino][columnaDestino] = 1; // Avanza 2 casillas hacia delante
            }
        }

        // AVANZA 1 casilla --> Movimiento en origen es un peón y de la fila destino en el array '7' (en el tablero la fila 1, donde se hace la 'promoción del peón'), avanzamos 1 casilla y la casilla delantera está vacía y siempre hacia delante.
        else if (jugador == 1 && filaDestino != 7 && ajedrez[filaDestino][columnaDestino] == 0 && ajedrez[filaOrigen][columnaOrigen] == 1 && filaOrigen + 1 == filaDestino && columnaOrigen == columnaDestino) {
            ajedrez[filaOrigen][columnaOrigen] = 0;
            ajedrez[filaDestino][columnaDestino] = 1;
        }
        // CAPTURAS EN LA DIAGONAL DERECHA O IZQUIERDA  --> Movimiento en origen es un peon y la fila destino en el array  NO es el '7' y que para comer las piezas que estan delante a la izquierda o derecha no son las propias !! -->  avanzamos 1 casilla y comprobamos 'Jaque Mate'
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 1 && filaDestino != 7 && (filaOrigen + 1 == filaDestino && columnaOrigen + 1 == columnaDestino || filaOrigen + 1 == filaDestino && columnaOrigen - 1 == columnaDestino) && ajedrez[filaDestino][columnaDestino] != 0 && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {

            if (ajedrez[filaDestino][columnaDestino] == 0) {
                ajedrez[filaOrigen][columnaOrigen] = 0; // La casilla de la que parte se queda vacía
                ajedrez[filaDestino][columnaDestino] = 1; // Avanza 1 casillas hacia delante
            } else if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 14;

                hayJaqueMate = true;

            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 1;


            } else {
                movimientoCorrecto = false;

            }

        }


        // Tengo que poner esta excepción en el caso que el rey esté en la última fila del array '7' (en el tablero la fila 1) y le de prioridad al 'Jaque Mate' y no salte la excepción de 'promoción del Peón '
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 1 && filaDestino == 7 && filaOrigen != 1 && ajedrez[filaDestino][columnaDestino] == 12 && (filaOrigen + 1 == filaDestino && columnaOrigen + 1 == columnaDestino || filaOrigen + 1 == filaDestino && columnaOrigen - 1 == columnaDestino) && ajedrez[filaDestino][columnaDestino] != 0 && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            ajedrez[filaOrigen][columnaOrigen] = 0;
            ajedrez[filaDestino][columnaDestino] = 14;

            hayJaqueMate = true;
        }

        // Si llega  a la última fila del array '7' (en el tablero fila 1)  podemos 'promocionar al peón', y se convierte en la pieza que queramos, MENOS EL REY !!
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 1 && filaDestino == 7 && (ajedrez[filaDestino][columnaDestino] != 12 && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11)) {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int opcion = 0;
            boolean correcto;
            System.out.println(verde + "Enhorabuena !!, has llegado a la última fila, ahora puedes 'promocionar al peón'\n");

            do {
                do {
                    correcto = true;
                    System.out.print(verde + "Elige una opcion: \n\n" +

                            "1 Peón\n" +
                            "3 Torre\n" +
                            "5 Caballo\n" +
                            "7 Alfil\n" +
                            "9 Dama\n\n" +

                            "Opción: ");

                    try {
                        opcion = Integer.parseInt(br.readLine());
                    } catch (NumberFormatException e) {
                        System.out.println(rojo + "Error !! ... elige una opción correcta: 1/3/5/7/9 " + reset);
                        correcto = false;
                    }
                } while (!correcto);
                if (opcion != 1 && opcion != 3 && opcion != 5 && opcion != 7 && opcion != 9) {
                    System.out.println(rojo + "Error !! ... elige una opción correcta: 1/3/5/7/9\n" + reset);
                }

            } while (opcion != 1 && opcion != 3 && opcion != 5 && opcion != 7 && opcion != 9);

            ajedrez[filaDestino][columnaDestino] = opcion;
            ajedrez[filaOrigen][columnaOrigen] = 0;

            // Ahora compruebo el total de piezas que tiene:
            int numpiezas = 0;
            for (int i = 0; i < ajedrez.length; i++) {
                for (int j = 0; j < ajedrez[i].length; j++) {
                    if (ajedrez[i][j] == opcion) {
                        numpiezas++;
                    }
                }
            }
            if (opcion == 1) {
                System.out.println("Ahora tienes " + numpiezas + " Peon/es -- > " + azul + "♙" + reset);
            }
            if (opcion == 3) {
                System.out.println("Ahora tienes " + numpiezas + " Torre/s --> " + azul + "♖" + reset);
            }
            if (opcion == 5) {
                System.out.println("Ahora tienes " + numpiezas + " Caballo/s --> " + azul + "♘" + reset);
            }
            if (opcion == 7) {
                System.out.println("Ahora tienes " + numpiezas + " Alfil/es -->" + azul + "♗" + reset);
            }
            if (opcion == 9) {
                System.out.println("Ahora tienes " + numpiezas + " Dama/s --> " + azul + "♕" + reset);
            }


        }

        // JUGADOR 2 BLANCAS
        // AVANZA 2 casillas --> Movimiento en origen es un peón y la fila origen en el array es el '6' (fila 2 en el tablero), avanzamos 2 casillas (si queremos), y no puede haber piezas por medio --> 'ajedrez[filaOrigen - 1][columnaDestino] == 0'
        else if (jugador == 2 && filaOrigen == 6 && ajedrez[filaDestino][columnaDestino] == 0 && ajedrez[filaOrigen - 1][columnaDestino] == 0 && ajedrez[filaOrigen][columnaOrigen] == 2 && filaDestino == 4 && columnaOrigen == columnaDestino) {
            //ajedrez[filaOrigen][columnaOrigen] = 0; // La casilla de la que parte se queda vacía
            //ajedrez[filaDestino][columnaDestino] = 2; // Avanza 2 casillas hacia delante

            if (columnaOrigen >= 1 && columnaOrigen <= 6 && ajedrez[filaOrigen][columnaOrigen] == 2 && ajedrez[filaOrigen - 1][columnaDestino] == 0 && ajedrez[filaDestino][columnaDestino] == 0 && ajedrez[filaDestino][columnaDestino + 1] == 1 && ajedrez[filaDestino][columnaDestino - 1] == 1) {

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                boolean correcto;
                int opcion = 0;
                do {

                    do {
                        correcto = true;
                        System.out.println();
                        System.out.print(verde + "CAPTURA AL PASO !! ... Elige que peón  quieres utilizar: \n" +
                                (columnaOrigen - 1) + "- Peón izquierdo\n" +
                                (columnaOrigen + 1) + "- Peón derecho\n\n" +

                                "Opción: " + reset);
                        try {
                            opcion = Integer.parseInt(br.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println(rojo + "Error !! ... debes elegir la opcion: " + (columnaOrigen - 1) + " o la opción: " + (columnaOrigen + 1) + reset + "\n");
                            correcto = false;
                        }
                    } while (!correcto);

                } while (opcion != (columnaOrigen - 1) && opcion != (columnaOrigen + 1));

                if (opcion == columnaOrigen - 1) {
                    capturaAlPaso = true;

                    jaqueColumnaDestino = columnaDestino;
                    jaqueColumnaOrigen = columnaDestino - 1;
                    convertiraTablero();

                    ajedrez[filaOrigen][columnaOrigen] = 0;
                    ajedrez[filaOrigen - 2][columnaOrigen - 1] = 0;
                    mensajeCapturaAlPaso = verde + "Peón Azul '" + (char) tableroColumnaOrigen + "4' --> come a Peón blancas '" + (char) tableroColumnaDestino + "3'\n" + reset;
                } else {
                    capturaAlPaso = true;

                    jaqueColumnaDestino = columnaDestino;
                    jaqueColumnaOrigen = columnaDestino + 1;
                    convertiraTablero();

                    ajedrez[filaOrigen][columnaOrigen] = 0;
                    ajedrez[filaOrigen - 2][columnaOrigen + 1] = 0;
                    mensajeCapturaAlPaso = verde + "Peón azul '" + (char) tableroColumnaOrigen + "4' --> come a Peón Blancas '" + (char) tableroColumnaDestino + "3'\n" + reset;
                }

                ajedrez[filaOrigen - 1][columnaOrigen] = 1; // En ambos casos va a ir a esta posición que es común

            } else if (columnaOrigen <= 6 && ajedrez[filaOrigen][columnaOrigen] == 2 && ajedrez[filaOrigen - 1][columnaDestino] == 0 && ajedrez[filaDestino][columnaDestino] == 0 && ajedrez[filaDestino][columnaDestino + 1] == 1) {
                capturaAlPaso = true;

                jaqueColumnaDestino = columnaDestino;
                jaqueColumnaOrigen = columnaDestino + 1;
                convertiraTablero();


                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaOrigen - 2][columnaOrigen + 1] = 0;
                ajedrez[filaOrigen - 1][columnaOrigen] = 1;
                mensajeCapturaAlPaso = verde + "Peón azul '" + (char) tableroColumnaOrigen + "4' -->  come a Peón Blancas '" + (char) tableroColumnaDestino + "3'\n" + reset;

            } else if (columnaOrigen >= 1 && ajedrez[filaOrigen][columnaOrigen] == 2 && ajedrez[filaOrigen - 1][columnaDestino] == 0 && ajedrez[filaDestino][columnaDestino] == 0 && ajedrez[filaDestino][columnaDestino - 1] == 1) {
                capturaAlPaso = true;

                jaqueColumnaDestino = columnaDestino;
                jaqueColumnaOrigen = columnaDestino - 1;
                convertiraTablero();

                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaOrigen - 2][columnaOrigen - 1] = 0;
                ajedrez[filaOrigen - 1][columnaOrigen] = 1;
                mensajeCapturaAlPaso = verde + "Peón azul '" + (char) tableroColumnaOrigen + "4' -->  come a Peón Blancas '" + (char) tableroColumnaDestino + "3'\n" + reset;
            } else {
                ajedrez[filaOrigen][columnaOrigen] = 0; // La casilla de la que parte se queda vacía
                ajedrez[filaDestino][columnaDestino] = 2; // Avanza 2 casillas hacia delante
            }


        }

        // AVANZA 1 casilla --> Movimiento en origen es un peón y de la fila destino en el array '6' (en el tablero la fila 2, donde se hace la 'promoción del peón'), avanzamos 1 casilla y la casilla delantera está vacía y siempre hacia delante.
        else if (jugador == 2 && filaDestino != 0 && ajedrez[filaDestino][columnaDestino] == 0 && ajedrez[filaOrigen][columnaOrigen] == 2 && filaOrigen - 1 == filaDestino && columnaOrigen == columnaDestino) {
            ajedrez[filaOrigen][columnaOrigen] = 0;
            ajedrez[filaDestino][columnaDestino] = 2;
        }
        // CAPTURAS EN LA DIAGONAL DERECHA O IZQUIERDA  --> Movimiento en origen es un peón y la fila destino en el array  NO es el '0' y que para comer las piezas que estan delante a la izquierda o derecha no son las propias !! -->  avanzamos 1 casilla y comprobamos 'Jaque Mate'
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 2 && filaDestino != 0 && (filaOrigen - 1 == filaDestino && columnaOrigen + 1 == columnaDestino || filaOrigen - 1 == filaDestino && columnaOrigen - 1 == columnaDestino) && ajedrez[filaDestino][columnaDestino] != 0 && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {

            if (ajedrez[filaDestino][columnaDestino] == 0) {
                ajedrez[filaOrigen][columnaOrigen] = 0; // La casilla de la que parte se queda vacía
                ajedrez[filaDestino][columnaDestino] = 2; // Avanza 1 casillas hacia delante
            } else if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 14;

                hayJaqueMate = true;

            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 2;

            } else {
                movimientoCorrecto = false;
            }

        }

        // Tengo que poner esta excepción en el caso que el rey esté en la primera fila del array '0' (en el tablero la fila 8) y le de prioridad al 'Jaque Mate' y no salte la excepción de 'promoción del Peón '
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 2 && filaDestino == 0 && filaOrigen != 6 && ajedrez[filaDestino][columnaDestino] == 11 && (filaOrigen - 1 == filaDestino && columnaOrigen + 1 == columnaDestino || filaOrigen - 1 == filaDestino && columnaOrigen - 1 == columnaDestino) && ajedrez[filaDestino][columnaDestino] != 0 && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            ajedrez[filaOrigen][columnaOrigen] = 0;
            ajedrez[filaDestino][columnaDestino] = 14;

            hayJaqueMate = true;
        }
        // Si llega  a la primera fila del array '0' (en el tablero fila 8)  podemos 'promocionar al peón', y se convierte en la pieza que queramos, MENOS EL REY !!
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 2 && filaDestino == 0 && (ajedrez[filaDestino][columnaDestino] != 11 && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12)) {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int opcion = 0;
            boolean correcto;
            System.out.println(verde + "Enhorabuena !!, has llegado a la última fila, ahora puedes 'promocionar al peón'\n");

            do {
                do {
                    correcto = true;
                    System.out.print(verde + "Elige una opcion: \n\n" +

                            "2 Peón\n" +
                            "4 Torre\n" +
                            "6 Caballo\n" +
                            "8 Alfil\n" +
                            "10 Dama\n\n" +

                            "Opción: ");

                    try {
                        opcion = Integer.parseInt(br.readLine());
                    } catch (NumberFormatException e) {
                        System.out.println(rojo + "Error !! ... elige una opción correcta: 2/4/6/8/10 " + reset);
                        correcto = false;
                    }
                } while (!correcto);
                if (opcion != 2 && opcion != 4 && opcion != 6 && opcion != 8 && opcion != 10) {
                    System.out.println(rojo + "Error !! ... elige una opción correcta: 2/4/6/8/10 \n" + reset);
                }

            } while (opcion != 2 && opcion != 4 && opcion != 6 && opcion != 8 && opcion != 10);

            ajedrez[filaDestino][columnaDestino] = opcion;
            ajedrez[filaOrigen][columnaOrigen] = 0;

            // Ahora compruebo el total de piezas que tiene:
            int numpiezas = 0;
            for (int i = 0; i < ajedrez.length; i++) {
                for (int j = 0; j < ajedrez[i].length; j++) {
                    if (ajedrez[i][j] == opcion) {
                        numpiezas++;
                    }
                }
            }
            if (opcion == 2) {
                System.out.println("Ahora tienes " + numpiezas + " Peon/es -- > " + reset + "♙");
            }
            if (opcion == 4) {
                System.out.println("Ahora tienes " + numpiezas + " Torre/s --> " + reset + "♖");
            }
            if (opcion == 6) {
                System.out.println("Ahora tienes " + numpiezas + " Caballo/s --> " + reset + "♘");
            }
            if (opcion == 8) {
                System.out.println("Ahora tienes " + numpiezas + " Alfil/es --> " + reset + "♗");
            }
            if (opcion == 10) {
                System.out.println("Ahora tienes " + numpiezas + " Dama/s --> " + reset + "♕");
            }
        } else {
            movimientoCorrecto = false;
            peon = rojo + "El peón solo puede avanzar una casilla hacia delante estando vacía o en diagonal para comer piezas, excepto dos casillas al principio !!\n" + reset;
        }
    }

    public static void moverTorre(int jugador) {

        // Jugador 1 AZULES --> Movimiento vertical hacia abajo
        boolean encontrada = false; // Busco si hay piezas por medio, hasta llegas a la casilla destino, porque no se puede hacer el movimiento

        if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 3 && filaDestino > filaOrigen && columnaOrigen == columnaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            // Vertical hacia abajo
            for (int i = filaOrigen + 1; i < filaDestino; i++) {
                if (ajedrez[i][columnaOrigen] != 0) {
                    encontrada = true;
                }
            }

            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 13;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 3;

                // Estas variables es para saber si esla PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 0 && columnaOrigen == 0) {
                    primerMovimientoTorreAzulIzquierda = true;
                }
                if (filaOrigen == 0 && columnaOrigen == 7) {
                    primerMovimientoTorreAzulDerecha = true;
                }

            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                torre = rojo + "La Torre NO puede saltar piezas  !!\n" + reset;
            }

            // Jugador 1 AZULES --> Movimiento vertical hacia arriba
        } else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 3 && filaOrigen > filaDestino && columnaOrigen == columnaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {

            // Vertical hacia arriba
            for (int i = filaOrigen - 1; i >= filaDestino + 1; i--) {
                if (ajedrez[i][columnaOrigen] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 13;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 3;
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                torre = rojo + "La Torre NO puede saltar piezas  !!\n" + reset;
            }

        }

        // Jugador 1 AZULES --> Movimiento horizontal a derechas
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 3 && columnaDestino > columnaOrigen && filaOrigen == filaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = columnaOrigen + 1; i < columnaDestino; i++) {
                if (ajedrez[filaOrigen][i] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 13;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 3;

                // Estas variables es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 0 && columnaOrigen == 0) {
                    primerMovimientoTorreAzulIzquierda = true;
                }
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                torre = rojo + "La Torre NO puede saltar piezas  !!\n" + reset;
            }
        }
        // Jugador 1 AZULES --> Movimiento horizontal a izquierdas
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 3 && columnaOrigen > columnaDestino && filaOrigen == filaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = columnaOrigen - 1; i >= columnaDestino + 1; i--) {
                if (ajedrez[filaOrigen][i] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 13;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 3;

                // Estas variables es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 0 && columnaOrigen == 7) {
                    primerMovimientoTorreAzulDerecha = true;
                }
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                torre = rojo + "La Torre NO puede saltar piezas  !!\n" + reset;
            }
        }

        // Jugador 2 BLANCAS --> Movimiento vertical hacia abajo
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 4 && filaDestino > filaOrigen && columnaOrigen == columnaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {

            for (int i = filaOrigen + 1; i < filaDestino; i++) {
                if (ajedrez[i][columnaOrigen] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 13;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 4;

            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                torre = rojo + "La Torre NO puede saltar piezas  !!\n" + reset;
            }

        }
        // Jugador 2 BLANCAS --> Movimiento vertical hacia arriba
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 4 && filaOrigen > filaDestino && columnaOrigen == columnaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {

            for (int i = filaOrigen - 1; i >= filaDestino + 1; i--) {
                if (ajedrez[i][columnaOrigen] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 13;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 4;

                // Estas variables es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 7 && columnaOrigen == 0) {
                    primerMovimientoTorreBlancaIzquierda = true;
                }
                if (filaOrigen == 7 && columnaOrigen == 7) {
                    primerMovimientoTorreBlancaDerecha = true;
                }
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                torre = rojo + "La Torre NO puede saltar piezas !!\n" + reset;
            }

        }

        // Jugador 2 BLANCAS --> Movimiento horizontal a derechas
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 4 && columnaDestino > columnaOrigen && filaOrigen == filaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {

            for (int i = columnaOrigen + 1; i < columnaDestino; i++) {
                if (ajedrez[filaOrigen][i] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 13;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 4;

                // Estas variables es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 7 && columnaOrigen == 0) {
                    primerMovimientoTorreBlancaIzquierda = true;
                }
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                torre = rojo + "La Torre NO puede saltar piezas !!\n" + reset;
            }


        }
        // Jugador 2 BLANCAS --> Movimiento horizontal a izquierdas
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 4 && columnaOrigen > columnaDestino && filaOrigen == filaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {

            for (int i = columnaOrigen - 1; i >= columnaDestino + 1; i--) {
                if (ajedrez[filaOrigen][i] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 13;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 4;

                // Estas variables es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 7 && columnaOrigen == 7) {
                    primerMovimientoTorreBlancaDerecha = true;
                }
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                torre = rojo + "La Torre NO puede saltar piezas !!\n" + reset;

            }

        }

        // Aquí saco error si hace algún movimiento que no sea horizontal o vertical
        else {
            movimientoCorrecto = false;
            torre = rojo + "La Torre solo puede desplazarse horizontalmente o verticalmente !!\n" + reset;
        }

    }

    public static void moverAlfil(int jugador) {

        // Jugador 1 AZULES  "/" --> Diagonal arriba a derechas, la siguiente cuenta obliga que el destino sea diagonal, tiene que formar un cuadrado perfecto (todos los lados son iguales) --> 'columnaDestino - columnaOrigen == filaOrigen - filaDestino'
        // Esta es la condición que indica que es diagonal arriba a derechas: 'columnaDestino > columnaOrigen && filaDestino < filaOrigen'
        // Esto es la condición que mantiene la 'diagonalidad' , tiene que estar dentro de un cuadrado, los lados son iguales !! --> 'columnaDestino - columnaOrigen == filaOrigen - filaDestino'

        boolean encontrada = false; // Busco si hay piezas por medio, hasta llegas a la casilla destino, porque no se puede hacer el movimiento

        if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 7 && columnaDestino > columnaOrigen && filaDestino < filaOrigen && columnaDestino - columnaOrigen == filaOrigen - filaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            // En esta matriz que empieza en una posición más adelante que la 'filaOrigen' (i=1) y 'columnaOrigen' (j=1), quiero ver si se encuentra alguna pieza por medio hasta llegar a 'filaDestino' y 'columnaDedstino'
            // Para comprobar solo los valores en la diagonal, tiene que ser 'i==j'
            // Los valores válidos empiezan a contar desde i=1 j=1, porque la casilla siguiente en la diagonal, desde la casilla origen es válido, porque puede estar vacía o no, pero NO hay que comprobar que hay piezas en el medio, porque no existe ese hueco.
            for (int i = 1; i < (filaOrigen - filaDestino); i++) {
                for (int j = 1; j < (columnaDestino - columnaOrigen); j++) {
                    if (i == j && ajedrez[filaOrigen - i][columnaOrigen + j] != 0) {
                        encontrada = true;
                        // Saco este valor por pantalla para saber el valor de la diagonal
                        //System.out.println("i: " + i + "  j: " + j + "  valor casilla: " + ajedrez[filaOrigen - i][columnaOrigen + j]);
                    }
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 15;

                hayJaqueMate = true;
            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 7;
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                alfil = rojo + "El alfil NO puede saltar piezas !!\n" + reset;
            }

        }

        // Jugador 1 AZULES  "\" --> Diagonal abajo a derechas,  la siguiente cuenta obliga que el destino sea diagonal, tiene que formar un cuadrado perfecto (todos los lados son iguales) --> 'columnaDestino - columnaOrigen == filaDestino - filaOrigen'
        // Este es más fácil, solo hay que sumar +1 a las 'i' y a las 'j', para mantener la diagonal hacia abajo y a la derecha

        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 7 && columnaDestino > columnaOrigen && filaDestino > filaOrigen && columnaDestino - columnaOrigen == filaDestino - filaOrigen && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = 1; i < filaDestino - filaOrigen; i++) {
                for (int j = 1; j < columnaDestino - columnaOrigen; j++) {
                    if (i == j && ajedrez[filaOrigen + i][columnaOrigen + j] != 0) {
                        encontrada = true;
                    }
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 15;

                hayJaqueMate = true;
            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 7;
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                alfil = rojo + "El alfil NO puede saltar piezas !!\n" + reset;
            }

        }

        // Jugador 1 AZULES  "/" --> Diagonal abajo a izquierdas --> 'columnaOrigen - columnaDestino == filaDestino - filaOrigen'
        // Esta es la condición que indica que es diagonal abajo a izquierdas: 'columnaOrigen > columnaDestino && filaDestino > filaOrigen'
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 7 && columnaOrigen > columnaDestino && filaDestino > filaOrigen && columnaOrigen - columnaDestino == filaDestino - filaOrigen && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = 1; i < (filaDestino - filaOrigen); i++) {
                for (int j = 1; j < (columnaOrigen - columnaDestino); j++) {
                    if (i == j && ajedrez[filaOrigen + i][columnaOrigen - j] != 0) {
                        encontrada = true;
                    }
                }
            }

            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 15;

                hayJaqueMate = true;
            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 7;
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                alfil = rojo + "El alfil NO puede saltar piezas !!\n" + reset;
            }
        }
        // Jugador 1 AZULES  "\" --> Diagonal arriba a izquierdas --> 'columnaOrigen - columnaDestino == filaDestino - filaOrigen'
        // Esta es la condición que indica que es diagonal arriba a izquierdas: ' columnaDestino < columnaOrigen && filaOrigen > filaDestino'
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 7 && columnaDestino < columnaOrigen && filaOrigen > filaDestino && columnaOrigen - columnaDestino == filaOrigen - filaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = 1; i < (filaOrigen - filaDestino); i++) {
                for (int j = 1; j < (columnaOrigen - columnaDestino); j++) {
                    if (i == j && ajedrez[filaOrigen - i][columnaOrigen - j] != 0) {
                        encontrada = true;
                    }
                }
            }

            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 15;

                hayJaqueMate = true;
            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 7;
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                alfil = rojo + "El alfil NO puede saltar piezas !!\n" + reset;
            }
        }
        // Jugador 2 BLANCAS  "/" --> Diagonal arriba a derechas, la siguiente cuenta obliga que el destino sea diagonal, tiene que formar un cuadrado perfecto (todos los lados son iguales) --> 'columnaDestino - columnaOrigen == filaOrigen - filaDestino'
        // Esta es la condición que indica que es diagonal arriba a derechas: 'columnaDestino > columnaOrigen && filaDestino < filaOrigen'
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 8 && columnaDestino > columnaOrigen && filaDestino < filaOrigen && columnaDestino - columnaOrigen == filaOrigen - filaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = 1; i < (filaOrigen - filaDestino); i++) {
                for (int j = 1; j < (columnaDestino - columnaOrigen); j++) {
                    if (i == j && ajedrez[filaOrigen - i][columnaOrigen + j] != 0) {
                        encontrada = true;
                    }
                }
            }


            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 15;

                hayJaqueMate = true;
            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 8;
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                alfil = rojo + "El alfil NO puede saltar piezas !!\n" + reset;
            }
        }
        // Jugador 2 BLANCAS  "\" --> Diagonal abajo a derechas,  la siguiente cuenta obliga que el destino sea diagonal, tiene que formar un cuadrado perfecto (todos los lados son iguales) --> 'columnaDestino - columnaOrigen == filaDestino - filaOrigen'
        // Este es más fácil, solo hay que sumar +1 a las 'i' y a las 'j', para mantener la diagonal hacia abajo y a la derecha
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 8 && columnaDestino > columnaOrigen && filaDestino > filaOrigen && columnaDestino - columnaOrigen == filaDestino - filaOrigen && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = 1; i < filaDestino - filaOrigen; i++) {
                for (int j = 1; j < columnaDestino - columnaOrigen; j++) {
                    if (i == j && ajedrez[filaOrigen + i][columnaOrigen + j] != 0) {
                        encontrada = true;
                    }
                }
            }

            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 15;

                hayJaqueMate = true;
            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 8;
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                alfil = rojo + "El alfil NO puede saltar piezas !!\n" + reset;
            }
        }
        // Jugador 2 BLANCAS  "/" --> Diagonal abajo a izquierdas --> 'columnaOrigen - columnaDestino == filaDestino - filaOrigen'
        // Esta es la condición que indica que es diagonal abajo a izquierdas: 'columnaOrigen > columnaDestino && filaDestino > filaOrigen'
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 8 && columnaOrigen > columnaDestino && filaDestino > filaOrigen && columnaOrigen - columnaDestino == filaDestino - filaOrigen && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = 1; i < (filaDestino - filaOrigen); i++) {
                for (int j = 1; j < (columnaOrigen - columnaDestino); j++) {
                    if (i == j && ajedrez[filaOrigen + i][columnaOrigen - j] != 0) {
                        encontrada = true;
                    }
                }
            }

            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 15;

                hayJaqueMate = true;
            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 8;
            }
            // Aquí saco error, si intenta saltar piezas
            else {
                movimientoCorrecto = false;
                alfil = rojo + "El alfil NO puede saltar piezas !!\n" + reset;
            }
        }
        // Jugador 2 BLANCAS  "\" --> Diagonal arriba a izquierdas --> 'columnaOrigen - columnaDestino == filaDestino - filaOrigen'
        // Esta es la condición que indica que es diagonal arriba a izquierdas: ' columnaDestino < columnaOrigen && filaOrigen > filaDestino'
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 8 && columnaDestino < columnaOrigen && filaOrigen > filaDestino && columnaOrigen - columnaDestino == filaOrigen - filaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = 1; i < (filaOrigen - filaDestino); i++) {
                for (int j = 1; j < (columnaOrigen - columnaDestino); j++) {
                    if (i == j && ajedrez[filaOrigen - i][columnaOrigen - j] != 0) {
                        encontrada = true;
                    }
                }
            }

            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 15;

                hayJaqueMate = true;
            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 8;
            }
            // Aquí saco error, si intenta saltar piezas, aunque sea en horizontal
            else {
                movimientoCorrecto = false;
                alfil = rojo + "El alfil NO puede saltar piezas !!\n" + reset;
            }
        } else {
            movimientoCorrecto = false;
            alfil = rojo + "El alfil solo puede avanzar en diagonal en las cuatro direcciones !!\n" + reset;
        }

    }

    public static void moverCaballo(int jugador) {

        /* Elcaballo, estando en una posición central,tiene 8 posibles jugadas, pudiendo saltar fichas, en total hay 16 excepciones (8 AZULES y 8 BLANCAS)
                                                                 X
         1 opción: 2 casillas hacia delante y una arriba --> X X X    Hay que tener en cuenta que no se puede salir del tablero --> ' columnaOrigen < ajedrez.length && filaOrigen > 0' y hay que ponerlo al principio para que no casque lo que viene después
                                                                                                          O O X
         Para probar, que el movimiento es correcto, tiene que estar dentro de un rectángulo de 3 x 2 --> X X X     Lo consigo así: 'columnaDestino - columnaOrigen == 2 && filaOrigen - filaDestino == 1'

         Jugador 1 AZULES       X
                            X X X
        */

        if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 5 && columnaOrigen + 2 < ajedrez.length && filaOrigen - 1 >= 0 && columnaDestino - columnaOrigen == 2 && filaOrigen - filaDestino == 1 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 1 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 3 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 5 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 7 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 9 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 11) {

            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 5;
            } else {
                movimientoCorrecto = false;
            }
        }
        // JUGADOR 1 AZULES
        // 2 opción --> X X X
        //                  X
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 5 && columnaOrigen + 2 < ajedrez.length && filaOrigen + 1 < ajedrez.length && columnaDestino - columnaOrigen == 2 && filaDestino - filaOrigen == 1 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 1 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 3 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 5 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 7 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 9 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 11) {

            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 5;
            } else {
                movimientoCorrecto = false;
            }
        }
        // JUGADOR 1 AZULES
        //  3 opción --> X
        //               X
        //               X X

        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 5 && columnaOrigen + 1 < ajedrez.length && filaOrigen + 2 < ajedrez.length && columnaDestino - columnaOrigen == 1 && filaDestino - filaOrigen == 2 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 1 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 3 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 5 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 7 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 9 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 11) {

            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 5;
            } else {
                movimientoCorrecto = false;
            }

        }
        // JUGADOR 1 AZULES
        //  4 opción -->  X
        //                X
        //              X X

        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 5 && columnaOrigen - 1 >= 0 && filaOrigen + 2 < ajedrez.length && columnaOrigen - columnaDestino == 1 && filaDestino - filaOrigen == 2 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 1 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 3 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 5 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 7 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 9 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 11) {

            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 5;
            } else {
                movimientoCorrecto = false;
            }

        }
        // JUGADOR 1 AZULES
        //  5 opción -->  X
        //                X X X


        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 5 && columnaOrigen - 2 >= 0 && filaOrigen - 1 >= 0 && columnaOrigen - columnaDestino == 2 && filaOrigen - filaDestino == 1 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 1 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 3 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 5 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 7 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 9 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 11) {

            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 5;
            } else {
                movimientoCorrecto = false;
            }

        }
        // JUGADOR 1 AZULES
        //  6 opción -->
        //               X X X
        //               X

        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 5 && columnaOrigen - 2 >= 0 && filaOrigen + 1 < ajedrez.length && columnaOrigen - columnaDestino == 2 && filaDestino - filaOrigen == 1 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 1 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 3 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 5 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 7 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 9 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 11) {

            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 5;
            } else {
                movimientoCorrecto = false;
            }

        }
        // JUGADOR 1 AZULES
        //  7 opción --> X X
        //                 X
        //                 X

        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 5 && columnaOrigen - 1 >= 0 && filaOrigen - 2 >= 0 && columnaOrigen - columnaDestino == 1 && filaOrigen - filaDestino == 2 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 1 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 3 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 5 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 7 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 9 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 11) {

            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 5;
            } else {
                movimientoCorrecto = false;
            }

        }
        // JUGADOR 1 AZULES
        //  8 opción -->  X X
        //                X
        //                X

        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 5 && columnaOrigen + 1 < ajedrez.length && filaOrigen - 2 >= 0 && columnaDestino - columnaOrigen == 1 && filaOrigen - filaDestino == 2 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 1 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 3 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 5 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 7 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 9 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 11) {

            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 5;
            } else {
                movimientoCorrecto = false;
            }

        }


        // Jugador 2 BLANCAS       X
        //                     X X X


        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 6 && columnaOrigen + 2 < ajedrez.length && filaOrigen - 1 >= 0 && columnaDestino - columnaOrigen == 2 && filaOrigen - filaDestino == 1 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 2 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 4 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 6 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 8 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 10 && ajedrez[filaOrigen - 1][columnaOrigen + 2] != 12) {

            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 6;
            } else {
                movimientoCorrecto = false;
            }
        }
        // JUGADOR 2 BLANCAS
        // 2 opción --> X X X
        //                  X
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 6 && columnaOrigen + 2 < ajedrez.length && filaOrigen + 1 < ajedrez.length && columnaDestino - columnaOrigen == 2 && filaDestino - filaOrigen == 1 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 2 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 4 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 6 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 8 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 10 && ajedrez[filaOrigen + 1][columnaOrigen + 2] != 12) {

            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 6;
            } else {
                movimientoCorrecto = false;
            }
        }
        // JUGADOR 2 BLANCAS
        //  3 opción --> X
        //               X
        //               X X

        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 6 && columnaOrigen + 1 < ajedrez.length && filaOrigen + 2 < ajedrez.length && columnaDestino - columnaOrigen == 1 && filaDestino - filaOrigen == 2 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 2 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 4 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 6 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 8 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 10 && ajedrez[filaOrigen + 2][columnaOrigen + 1] != 12) {

            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 6;
            } else {
                movimientoCorrecto = false;
            }

        }
        // JUGADOR 2 BLANCAS
        //  4 opción -->  X
        //                X
        //              X X

        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 6 && columnaOrigen - 1 >= 0 && filaOrigen + 2 < ajedrez.length && columnaOrigen - columnaDestino == 1 && filaDestino - filaOrigen == 2 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 2 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 4 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 6 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 8 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 10 && ajedrez[filaOrigen + 2][columnaOrigen - 1] != 12) {

            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 6;
            } else {
                movimientoCorrecto = false;
            }

        }
        // JUGADOR 2 BLANCAS
        //  5 opción -->  X
        //                X X X


        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 6 && columnaOrigen - 2 >= 0 && filaOrigen - 1 >= 0 && columnaOrigen - columnaDestino == 2 && filaOrigen - filaDestino == 1 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 2 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 4 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 6 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 8 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 10 && ajedrez[filaOrigen - 1][columnaOrigen - 2] != 12) {

            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 6;
            } else {
                movimientoCorrecto = false;
            }

        }
        // JUGADOR 2 BLANCAS
        //  6 opción -->
        //               X X X
        //               X

        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 6 && columnaOrigen - 2 >= 0 && filaOrigen + 1 < ajedrez.length && columnaOrigen - columnaDestino == 2 && filaDestino - filaOrigen == 1 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 2 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 4 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 6 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 8 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 10 && ajedrez[filaOrigen + 1][columnaOrigen - 2] != 12) {

            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 6;
            } else {
                movimientoCorrecto = false;
            }

        }
        // JUGADOR 2 BLANCAS
        //  7 opción --> X X
        //                 X
        //                 X

        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 6 && columnaOrigen - 1 >= 0 && filaOrigen - 2 >= 0 && columnaOrigen - columnaDestino == 1 && filaOrigen - filaDestino == 2 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 2 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 4 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 6 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 8 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 10 && ajedrez[filaOrigen - 2][columnaOrigen - 1] != 12) {

            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 6;
            } else {
                movimientoCorrecto = false;
            }

        }
        // JUGADOR 2 BLANCAS
        //  8 opción -->  X X
        //                X
        //                X

        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 6 && columnaOrigen + 1 < ajedrez.length && filaOrigen - 2 >= 0 && columnaDestino - columnaOrigen == 1 && filaOrigen - filaDestino == 2 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 2 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 4 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 6 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 8 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 10 && ajedrez[filaOrigen - 2][columnaOrigen + 1] != 12) {

            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 16;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 6;
            } else {
                movimientoCorrecto = false;
            }

        } else {
            movimientoCorrecto = false;
            caballo = rojo + "El caballo solo puede moverse a destino formando una 'L' y SI puede saltar piezas\n" + reset;
        }
    }

    public static void moverRey(int jugador) {

        // Jugador 1 AZULES --> Movimiento horizontal hacia delante y atrás
        if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 11 && (columnaOrigen + 1 == columnaDestino || columnaOrigen - 1 == columnaDestino && filaOrigen == filaDestino) && ajedrez[filaDestino][columnaDestino] != 11 && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9) {
            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 17;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 11;

                // Estas variable es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 0 && columnaOrigen == 4) {
                    primerMovimientoReyAzul = true;
                }

            } else {
                movimientoCorrecto = false;
            }
        }

        // Jugador 1 AZULES --> Movimiento vertical, arriba y abajo
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 11 && (filaOrigen + 1 == filaDestino || filaOrigen - 1 == filaDestino) && columnaOrigen == columnaDestino && ajedrez[filaDestino][columnaDestino] != 11 && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9) {
            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 17;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 11;

                // Estas variable es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 0 && columnaOrigen == 4) {
                    primerMovimientoReyAzul = true;
                }
            } else {
                movimientoCorrecto = false;
            }
        }
        // Jugador 1 AZULES --> Movimiento diagonal en los dos sentidos
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 11 && ((filaOrigen + 1 == filaDestino && columnaOrigen + 1 == columnaDestino) || (filaOrigen - 1 == filaDestino && columnaOrigen - 1 == columnaDestino) || (filaOrigen - 1 == filaDestino && columnaOrigen + 1 == columnaDestino) || (filaOrigen + 1 == filaDestino && columnaOrigen - 1 == columnaDestino)) && ajedrez[filaDestino][columnaDestino] != 11 && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9) {
            if (ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 17;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 11;

                // Estas variable es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 0 && columnaOrigen == 4) {
                    primerMovimientoReyAzul = true;
                }
            } else {
                movimientoCorrecto = false;
            }
        }

        // Jugador 2 BLANCAS --> Movimiento horizontal hacia delante y atrás
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 12 && (columnaOrigen + 1 == columnaDestino || columnaOrigen - 1 == columnaDestino && filaOrigen == filaDestino) && ajedrez[filaDestino][columnaDestino] != 12 && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10) {
            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 17;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 12;

                // Estas variable es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 7 && columnaOrigen == 4) {
                    primerMovimientoReyBlancas = true;
                }
            } else {
                movimientoCorrecto = false;
            }
        }

        // Jugador 2 BLANCAS --> Movimiento vertical, arriba y abajo
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 12 && (filaOrigen + 1 == filaDestino || filaOrigen - 1 == filaDestino) && columnaOrigen == columnaDestino && ajedrez[filaDestino][columnaDestino] != 12 && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10) {
            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 17;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 12;

                // Estas variable es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 7 && columnaOrigen == 4) {
                    primerMovimientoReyBlancas = true;
                }
            } else {
                movimientoCorrecto = false;
            }
        }
        // Jugador 2 BLANCAS --> Movimiento diagonal en los dos sentidos
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 12 && ((filaOrigen + 1 == filaDestino && columnaOrigen + 1 == columnaDestino) || (filaOrigen - 1 == filaDestino && columnaOrigen - 1 == columnaDestino) || (filaOrigen - 1 == filaDestino && columnaOrigen + 1 == columnaDestino) || (filaOrigen + 1 == filaDestino && columnaOrigen - 1 == columnaDestino)) && ajedrez[filaDestino][columnaDestino] != 12 && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10) {
            if (ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 17;

                hayJaqueMate = true;
            } else if (ajedrez[filaDestino][columnaDestino] != 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 12;

                // Estas variable es para saber si es la PRIMERA VEZ que se mueve la pieza, y parten de la posición de inicio, para saber si pueden hacer el 'enroque'
                if (filaOrigen == 7 && columnaOrigen == 4) {
                    primerMovimientoReyBlancas = true;
                }
            } else {
                movimientoCorrecto = false;
            }
        }

        /*
        El enroque sólo es admisible si todos cumplen las siguientes condiciones:

              Atención: para enrocarte mueve siempre primero el rey

            - Ninguna de las piezas que intervienen en el enroque puede haber sido movido previamente (La torre y el rey) . --> !primerMovimientoTorreAzulIzquierda && !primerMovimientoReyAzul
            - No debe haber ninguna pieza entre el rey y la torre.                                     -->  ajedrez[0][1] == 0 && ajedrez[0][2] == 0 && ajedrez[0][3] == 0
            - El rey no puede estar en jaque, ni tampoco podrá pasar a través de casillas que están bajo ataque por parte de las piezas enemigas.
              Al igual que con cualquier movimiento, el enroque es ilegal si pusiera al rey en jaque. --> !comprobarJaque

         */

        // ENROQUE LARGO AZULES (con la torre azul izquierda)

        else if (jugador == 1 && !primerMovimientoTorreAzulIzquierda && !primerMovimientoReyAzul && ajedrez[filaOrigen][columnaOrigen] == 11 && filaOrigen == filaDestino && columnaDestino == columnaOrigen - 2 && ajedrez[0][0] == 3 && ajedrez[0][1] == 0 && ajedrez[0][2] == 0 && ajedrez[0][3] == 0 && ajedrez[0][4] == 11) {

            // Comprobamos antes las siguientes funciones para saber si estamos sobre 'Jaque' en nuestro propio rey y que las casillas por las que va a pasar el rey para hacer el 'enroque'  no están amenazadas.
            comprobarJaquePropioParaEnroque(jugador);
            comprobarEnroqueLargo(jugador);

            // Comprobamos el valor de 'hayEnroqueLargo' y 'comprobarJaquePropioParaEnroque'
            if (!hayJaquePropioParaEnroque && hayEnroqueLargo) {
                // El rey avanza dos casillas a la izquierda
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 11;

                // La torre se pone al otro lado del rey
                ajedrez[0][0] = 0;
                ajedrez[0][3] = 3;

                // Ya no se va a poder hacer más enroques
                primerMovimientoTorreAzulIzquierda = true;
                primerMovimientoReyAzul = true;

                ec = verde + "Enroque Correcto !!\n" + reset;
            } else {
                movimientoCorrecto = false;
                ec0 = rojo + "Enroque incorrecto !!\n" + reset;
            }
        }

        // ENROQUE CORTO AZULES (con la torre azul derecha)
        else if (jugador == 1 && !primerMovimientoTorreAzulDerecha && !primerMovimientoReyAzul && ajedrez[filaOrigen][columnaOrigen] == 11 && filaOrigen == filaDestino && columnaDestino == columnaOrigen + 2 && ajedrez[0][4] == 11 && ajedrez[0][5] == 0 && ajedrez[0][6] == 0 && ajedrez[0][7] == 3) {

            // Comprobamos antes las siguientes funciones para saber si estamos sobre 'Jaque' en nuestro propio rey y que las casillas por las que va a pasar el rey para hacer el 'enroque'  no están amenazadas.
            comprobarJaquePropioParaEnroque(jugador);
            comprobarEnroqueCorto(jugador);

            // Comprobamos el valor de 'hayEnroquecorto' y 'comprobarJaquePropioParaEnroque'
            if (!hayJaquePropioParaEnroque && hayEnroqueCorto) {
                // El rey avanza dos casillas a la izquierda
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 11;

                // La torre se pone al otro lado del rey
                ajedrez[0][7] = 0;
                ajedrez[0][5] = 3;

                // Ya no se va a poder hacer más enroques
                primerMovimientoTorreAzulDerecha = true;
                primerMovimientoReyAzul = true;

                ec = verde + "Enroque Correcto !!\n" + reset;
            } else {
                movimientoCorrecto = false;
                ec0 = rojo + "Enroque incorrecto !! \n" + reset;

            }
        }


        // ENROQUE LARGO BLANCAS (con la torre blanca izquierda)
        else if (jugador == 2 && !primerMovimientoTorreBlancaIzquierda && !primerMovimientoReyBlancas && ajedrez[filaOrigen][columnaOrigen] == 12 && filaOrigen == filaDestino && columnaDestino == columnaOrigen - 2 && ajedrez[7][0] == 4 && ajedrez[7][1] == 0 && ajedrez[7][2] == 0 && ajedrez[7][3] == 0 && ajedrez[7][4] == 12) {

            // Comprobamos antes las siguientes funciones para saber si estamos sobre 'Jaque' en nuestro propio rey y que las casillas por las que va a pasar el rey para hacer el 'enroque'  no están amenazadas.
            comprobarJaquePropioParaEnroque(jugador);
            comprobarEnroqueLargo(jugador);

            // Comprobamos el valor de 'hayEnroqueLargo' y 'comprobarJaquePropioParaEnroque'
            if (!hayJaquePropioParaEnroque && hayEnroqueLargo) {
                // El rey avanza dos casillas a la izquierda
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 12;

                // La torre se pone al otro lado del rey
                ajedrez[7][0] = 0;
                ajedrez[7][3] = 4;

                // Ya no se va a poder hacer más enroques
                primerMovimientoTorreBlancaIzquierda = true;
                primerMovimientoReyBlancas = true;

                ec = verde + "Enroque Correcto !!\n" + reset;
            } else {
                movimientoCorrecto = false;
                ec0 = rojo + "Enroque incorrecto !! \n" + reset;
            }
        }


        // ENROQUE CORTO BLANCAS (con la torre blanca derecha)
        else if (jugador == 2 && !primerMovimientoTorreBlancaDerecha && !primerMovimientoReyBlancas && ajedrez[filaOrigen][columnaOrigen] == 12 && filaOrigen == filaDestino && columnaDestino == columnaOrigen + 2 && ajedrez[7][4] == 12 && ajedrez[7][5] == 0 && ajedrez[7][6] == 0 && ajedrez[0][7] == 4) {

            // Comprobamos antes las siguientes funciones para saber si estamos sobre 'Jaque' en nuestro propio rey y que las casillas por las que va a pasar el rey para hacer el 'enroque'  no están amenazadas.
            comprobarJaquePropioParaEnroque(jugador);
            comprobarEnroqueCorto(jugador);

            // Comprobamos el valor de 'hayEnroqueLargo' y 'comprobarJaquePropioParaEnroque'
            if (!hayJaquePropioParaEnroque && hayEnroqueCorto) {
                // El rey avanza dos casillas a la izquierda
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 12;

                // La torre se pone al otro lado del rey
                ajedrez[7][7] = 0;
                ajedrez[7][5] = 4;

                // Ya no se va a poder hacer más enroques
                primerMovimientoTorreBlancaDerecha = true;
                primerMovimientoReyBlancas = true;

                ec = verde + "Enroque Correcto !!\n" + reset;
            } else {
                movimientoCorrecto = false;
                ec0 = rojo + "Enroque incorrecto !! \n" + reset;
            }
            // Mensajes comprobando si se ha movido previamente la torre o el rey que intervienen enel enroque

        } else if (primerMovimientoTorreAzulIzquierda && (filaDestino == 0 && columnaDestino == 2)) {
            movimientoCorrecto = false;
            tyreyAzul = rojo + "Has movido con anterioridad la torre y/o rey ... y no puedes realizar el enroque\n" + reset;

        } else if (primerMovimientoReyAzul && filaDestino == 0 && (columnaDestino == 2 || columnaDestino == 6)) {
            movimientoCorrecto = false;
            tyreyAzul = rojo + "Has movido con anterioridad la torre y/o rey ... y no puedes realizar el enroque\n" + reset;

        } else if (primerMovimientoTorreAzulDerecha && (filaDestino == 0 && columnaDestino == 6)) {
            movimientoCorrecto = false;
            tyreyAzul = rojo + "Has movido con anterioridad la torre y/o rey ... y no puedes realizar el enroque\n" + reset;

        } else if (primerMovimientoTorreBlancaIzquierda && (filaDestino == 7 && columnaDestino == 2)) {
            movimientoCorrecto = false;
            tyreyAzul = rojo + "Has movido con anterioridad la torre y/o rey ... y no puedes realizar el enroque\n" + reset;

        } else if (primerMovimientoReyBlancas && filaDestino == 7 && (columnaDestino == 2 || columnaDestino == 6)) {
            movimientoCorrecto = false;
            tyreyAzul = rojo + "Has movido con anterioridad la torre y/o rey ... y no puedes realizar el enroque\n" + reset;

        } else if (primerMovimientoTorreBlancaDerecha && (filaDestino == 7 && columnaDestino == 6)) {
            movimientoCorrecto = false;
            tyreyAzul = rojo + "Has movido con anterioridad la torre y/o rey ... y no puedes realizar el enroque\n" + reset;

        } else {
            movimientoCorrecto = false;
            rey = rojo + "EL rey solo puede avanzar una casilla en cualquier dirección, excepto cuando realiza el 'enroque'\n" + reset;
        }


    }

    public static void moverDama(int jugador) {

        // La dama tiene la suma de los movimientos del 'alfil' y la 'torre'

        boolean encontrada = false; // Busco si hay piezas por medio, hasta llegas a la casilla destino, porque no se puede hacer el movimiento

        //  JUGADOR 1 AZULES --> Movimiento vertical hacia abajo
        if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 9 && filaDestino > filaOrigen && columnaOrigen == columnaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = filaOrigen + 1; i < filaDestino; i++) {
                if (ajedrez[i][columnaOrigen] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 9;
            }
            // Aquí saco error, si intenta saltar piezas, aunque sea en horizontal
            else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        // JUGADOR 1 AZULES --> Movimiento vertical hacia arriba
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 9 && filaOrigen > filaDestino && columnaOrigen == columnaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = filaOrigen - 1; i >= filaDestino + 1; i--) {
                if (ajedrez[i][columnaOrigen] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 9;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        // JUGADOR 1 AZULES --> Movimiento horizontal a derechas
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 9 && columnaDestino > columnaOrigen && filaOrigen == filaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = columnaOrigen + 1; i < columnaDestino; i++) {
                if (ajedrez[filaOrigen][i] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 9;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        // JUGADOR 1 AZULES --> Movimiento horizontal a izquierdas
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 9 && columnaOrigen > columnaDestino && filaOrigen == filaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = columnaOrigen - 1; i >= columnaDestino + 1; i--) {
                if (ajedrez[filaOrigen][i] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 9;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        // JUGADOR 1 AZULES --> Movimiento diagonal arriba a derechas
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 9 && columnaDestino > columnaOrigen && filaDestino < filaOrigen && columnaDestino - columnaOrigen == filaOrigen - filaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = 1; i < (filaOrigen - filaDestino); i++) {
                for (int j = 1; j < (columnaDestino - columnaOrigen); j++) {
                    if (i == j && ajedrez[filaOrigen - i][columnaOrigen + j] != 0) {
                        encontrada = true;
                    }
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 9;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        // JUGADOR 1 AZULES --> Movimiento diagonal abajo a derechas
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 9 && columnaDestino > columnaOrigen && filaDestino > filaOrigen && columnaDestino - columnaOrigen == filaDestino - filaOrigen && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = 1; i < filaDestino - filaOrigen; i++) {
                for (int j = 1; j < columnaDestino - columnaOrigen; j++) {
                    if (i == j && ajedrez[filaOrigen + i][columnaOrigen + j] != 0) {
                        encontrada = true;
                    }
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 9;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        // JUGADOR 1 AZULES --> Movimiento diagonal abajo a izquierdas
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 9 && columnaOrigen > columnaDestino && filaDestino > filaOrigen && columnaOrigen - columnaDestino == filaDestino - filaOrigen && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = 1; i < (filaDestino - filaOrigen); i++) {
                for (int j = 1; j < (columnaOrigen - columnaDestino); j++) {
                    if (i == j && ajedrez[filaOrigen + i][columnaOrigen - j] != 0) {
                        encontrada = true;
                    }
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 9;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        // JUGADOR 1 AZULES --> Movimiento diagonal arriba a izquierdas
        else if (jugador == 1 && ajedrez[filaOrigen][columnaOrigen] == 9 && columnaDestino < columnaOrigen && filaOrigen > filaDestino && columnaOrigen - columnaDestino == filaOrigen - filaDestino && ajedrez[filaDestino][columnaDestino] != 1 && ajedrez[filaDestino][columnaDestino] != 3 && ajedrez[filaDestino][columnaDestino] != 5 && ajedrez[filaDestino][columnaDestino] != 7 && ajedrez[filaDestino][columnaDestino] != 9 && ajedrez[filaDestino][columnaDestino] != 11) {
            for (int i = 1; i < (filaOrigen - filaDestino); i++) {
                for (int j = 1; j < (columnaOrigen - columnaDestino); j++) {
                    if (i == j && ajedrez[filaOrigen - i][columnaOrigen - j] != 0) {
                        encontrada = true;
                    }
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 12) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 9;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }

        //  JUGADOR 2 BLANCAS --> Movimiento vertical hacia abajo
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 10 && filaDestino > filaOrigen && columnaOrigen == columnaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = filaOrigen + 1; i < filaDestino; i++) {
                if (ajedrez[i][columnaOrigen] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 10;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        //  JUGADOR 2 BLANCAS --> Movimiento vertical hacia arriba
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 10 && filaOrigen > filaDestino && columnaOrigen == columnaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = filaOrigen - 1; i >= filaDestino + 1; i--) {
                if (ajedrez[i][columnaOrigen] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 10;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        //  JUGADOR 2 BLANCAS --> Movimiento horizontal a derechas
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 10 && columnaDestino > columnaOrigen && filaOrigen == filaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = columnaOrigen + 1; i < columnaDestino; i++) {
                if (ajedrez[filaOrigen][i] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 10;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        //  JUGADOR 2 BLANCAS --> Movimiento horizontal a izquierdas
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 10 && columnaOrigen > columnaDestino && filaOrigen == filaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = columnaOrigen - 1; i >= columnaDestino + 1; i--) {
                if (ajedrez[filaOrigen][i] != 0) {
                    encontrada = true;
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 10;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        //  JUGADOR 2 BLANCAS --> Movimiento diagonal arriba a derechas
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 10 && columnaDestino > columnaOrigen && filaDestino < filaOrigen && columnaDestino - columnaOrigen == filaOrigen - filaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = 1; i < (filaOrigen - filaDestino); i++) {
                for (int j = 1; j < (columnaDestino - columnaOrigen); j++) {
                    if (i == j && ajedrez[filaOrigen - i][columnaOrigen + j] != 0) {
                        encontrada = true;
                    }
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 10;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        //  JUGADOR 2 BLANCAS --> Movimiento diagonal abajo a derechas
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 10 && columnaDestino > columnaOrigen && filaDestino > filaOrigen && columnaDestino - columnaOrigen == filaDestino - filaOrigen && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = 1; i < filaDestino - filaOrigen; i++) {
                for (int j = 1; j < columnaDestino - columnaOrigen; j++) {
                    if (i == j && ajedrez[filaOrigen + i][columnaOrigen + j] != 0) {
                        encontrada = true;
                    }
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 10;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        //  JUGADOR 2 BLANCAS --> Movimiento diagonal abajo a izquierdas
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 10 && columnaOrigen > columnaDestino && filaDestino > filaOrigen && columnaOrigen - columnaDestino == filaDestino - filaOrigen && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = 1; i < (filaDestino - filaOrigen); i++) {
                for (int j = 1; j < (columnaOrigen - columnaDestino); j++) {
                    if (i == j && ajedrez[filaOrigen + i][columnaOrigen - j] != 0) {
                        encontrada = true;
                    }
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 10;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        }
        //  JUGADOR 2 BLANCAS --> Movimiento diagonal arriba a izquierdas
        else if (jugador == 2 && ajedrez[filaOrigen][columnaOrigen] == 10 && columnaDestino < columnaOrigen && filaOrigen > filaDestino && columnaOrigen - columnaDestino == filaOrigen - filaDestino && ajedrez[filaDestino][columnaDestino] != 2 && ajedrez[filaDestino][columnaDestino] != 4 && ajedrez[filaDestino][columnaDestino] != 6 && ajedrez[filaDestino][columnaDestino] != 8 && ajedrez[filaDestino][columnaDestino] != 10 && ajedrez[filaDestino][columnaDestino] != 12) {
            for (int i = 1; i < (filaOrigen - filaDestino); i++) {
                for (int j = 1; j < (columnaOrigen - columnaDestino); j++) {
                    if (i == j && ajedrez[filaOrigen - i][columnaOrigen - j] != 0) {
                        encontrada = true;
                    }
                }
            }
            if (!encontrada && ajedrez[filaDestino][columnaDestino] == 11) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 18;

                hayJaqueMate = true;

            } else if (!encontrada) {
                ajedrez[filaOrigen][columnaOrigen] = 0;
                ajedrez[filaDestino][columnaDestino] = 10;
            } else {
                movimientoCorrecto = false;
                dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
            }
        } else {
            movimientoCorrecto = false;
            dama = rojo + "La dama solo se puede mover en diagonal y horizontal en cualquiera de las 8 direcciones, pero NO puede saltar piezas !!\n" + reset;
        }
    }

    public static void comprobarEnroqueLargo(int jugador) {

        // Ahora compruebo aqui los posibles 'jaques' que puede haber pasando el rey por la columnas 2 y 3
        for (int i = 0; i < ajedrez.length; i++) {
            for (int j = 0; j < ajedrez[i].length; j++) {

                // AZULES
                if (jugador == 1 && i == 0 && (j == 2 || j == 3)) {

                    //PEON --> Come al rey a su derecha
                    if (ajedrez[i][j] == 0 && ajedrez[i + 1][j - 1] == 2) {
                        hayEnroqueLargo = false;

                        jaqueColumnaOrigen = j - 1;
                        jaqueFilaOrigen = i + 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el1 = rojo + "      El peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }
                    //PEON --> Come al rey a su izquierda
                    if (ajedrez[i][j] == 0 && ajedrez[i + 1][j + 1] == 2) {
                        hayEnroqueLargo = false;

                        jaqueColumnaOrigen = j + 1;
                        jaqueFilaOrigen = i + 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el2 = rojo + "      El peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia abajo a izquierdas
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 8) {
                        hayEnroqueLargo = false;

                        jaqueColumnaOrigen = j - avanzaUno;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el3 = rojo + "      El alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia abajo a derechas
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 8) {
                        hayEnroqueLargo = false;

                        jaqueColumnaOrigen = j + avanzaUno;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el4 = rojo + "      El alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // TORRE --> vertical hacia abajo
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 4) {
                        hayEnroqueLargo = false;

                        jaqueColumnaOrigen = j;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el5 = rojo + "      La torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // CABALLO
                    if (ajedrez[i + 2][j - 1] == 6) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j - 1;
                        jaqueFilaOrigen = i + 2;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el6 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (ajedrez[i + 2][j + 1] == 6) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j + 1;
                        jaqueFilaOrigen = i + 2;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el7 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (ajedrez[i + 1][j - 2] == 6) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j - 2;
                        jaqueFilaOrigen = i + 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el8 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (ajedrez[i + 1][j + 2] == 6) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j + 2;
                        jaqueFilaOrigen = i + 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el9 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // REY
                    if (ajedrez[i + 1][j] == 12 || ajedrez[i + 1][j - 1] == 12 || ajedrez[i + 1][j + 1] == 12) {
                        hayEnroqueLargo = false;

                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el10 = rojo + "      El rey entre 'b7' y 'e7' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // DAMA --> la suma del alfil diagonales hacia arriba y la torre hacia arriba
                    // Torre hacia abajo

                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 10) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el11 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia abajo a izquierdas
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 10) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j - avanzaUno;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el12 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia abajo a derechas
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 10) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j + avanzaUno;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el13 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                }

                // BLANCAS

                if (jugador == 2 && i == 7 && (j == 2 || j == 3)) {

                    //PEON --> Solo tengo que comprobar que las columnas 2 y 3 por donde va a pasar el rey no estan 'amenazadas' por ninguna pieza
                    if (ajedrez[i][j] == 0 && ajedrez[i - 1][j + 1] == 1) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j + 1;
                        jaqueFilaOrigen = i - 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el1 = rojo + "      El peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }
                    //PEON --> Solo tengo que comprobar que las columnas 2 y 3 por donde va a pasar el rey no estan 'amenazadas' por ninguna pieza
                    if (ajedrez[i][j] == 0 && ajedrez[i - 1][j - 1] == 1) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j - 1;
                        jaqueFilaOrigen = i - 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el2 = rojo + "      El peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia arriba a izquierdas
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 7) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j - avanzaUno;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el3 = rojo + "      El alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia abajo a derechas
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 7) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j + avanzaUno;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el4 = rojo + "      El alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // TORRE --> vertical hacia abajo
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 3) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el5 = rojo + "      La torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // CABALLO
                    if (ajedrez[i - 2][j - 1] == 5) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j - 1;
                        jaqueFilaOrigen = i - 2;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el6 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (ajedrez[i - 2][j + 1] == 5) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j + 1;
                        jaqueFilaOrigen = i - 2;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el7 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (ajedrez[i - 1][j - 2] == 5) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j - 2;
                        jaqueFilaOrigen = i - 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el8 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (ajedrez[i - 1][j + 2] == 5) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j + 2;
                        jaqueFilaOrigen = i - 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el9 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // REY
                    if (ajedrez[i - 1][j] == 11 || ajedrez[i - 1][j - 1] == 11 || ajedrez[i - 1][j + 1] == 11) {
                        hayEnroqueLargo = false;

                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el10 = rojo + "      El rey entre 'b2' y 'e2' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // DAMA --> la suma del alfil diagonales hacia arriba y la torre hacia arriba
                    // Torre hacia arriba

                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 9) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el11 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia arriba a izquierdas
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 9) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j - avanzaUno;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el12 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia arriba a derechas
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 9) {
                        hayEnroqueLargo = false;
                        jaqueColumnaOrigen = j + avanzaUno;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        el13 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                }

            }

        }

    }

    public static void comprobarEnroqueCorto(int jugador) {

        // Ahora compruebo aqui los posibles 'jaques' que puede haber pasando el rey por la columnas 5 y 6
        for (int i = 0; i < ajedrez.length; i++) {
            for (int j = 0; j < ajedrez[i].length; j++) {

                // AZULES
                if (jugador == 1 && i == 0 && (j == 5 || j == 6)) {

                    // Solo tengo que comprobar que las columnas 5 y 6 por donde va a pasar el rey no estan 'amenazadas' por ninguna pieza
                    //PEON --> Come a su derecha
                    if (ajedrez[i][j] == 0 && ajedrez[i + 1][j + 1] == 2) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j + 1;
                        jaqueFilaOrigen = i + 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec1 = rojo + "      El peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }
                    //PEON --> Come a su izquierda
                    if (ajedrez[i][j] == 0 && ajedrez[i + 1][j - 1] == 2) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j - 1;
                        jaqueFilaOrigen = i + 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec2 = rojo + "      El peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia abajo a izquierdas
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 8) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j - avanzaUno;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec3 = rojo + "      El alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia abajo a derechas
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 8) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j + avanzaUno;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec4 = rojo + "      El alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // TORRE --> vertical hacia abajo
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 4) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec5 = rojo + "      La torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // CABALLO
                    if (ajedrez[i + 2][j - 1] == 6) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j - 1;
                        jaqueFilaOrigen = i + 2;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec6 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (ajedrez[i + 2][j + 1] == 6) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j + 1;
                        jaqueFilaOrigen = i + 2;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec7 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (ajedrez[i + 1][j - 2] == 6) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j - 2;
                        jaqueFilaOrigen = i + 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec8 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (j + 2 <= 7 && ajedrez[i + 1][j + 2] == 6) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j + 2;
                        jaqueFilaOrigen = i + 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec9 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // REY
                    if (ajedrez[i + 1][j] == 12 || ajedrez[i + 1][j - 1] == 12 || ajedrez[i + 1][j + 1] == 12) {
                        hayEnroqueCorto = false;

                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec10 = rojo + "      El rey entre 'e7' y 'h7' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // DAMA --> la suma del alfil diagonales hacia arriba y la torre hacia arriba
                    // Torre hacia abajo

                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 10) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec11 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia abajo a izquierdas
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 10) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j - avanzaUno;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec12 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia abajo a derechas
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 10) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j + avanzaUno;
                        jaqueFilaOrigen = i + avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec13 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }


                }

                // BLANCAS

                if (jugador == 2 && i == 7 && (j == 5 || j == 6)) {

                    // Solo tengo que comprobar que las columnas 5 y 6 por donde va a pasar el rey no estan 'amenazadas' por ninguna pieza
                    //PEON --> Come a su derecha
                    if (ajedrez[i][j] == 0 && ajedrez[i - 1][j + 1] == 1) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j + 1;
                        jaqueFilaOrigen = i - 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec1 = rojo + "      El peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }
                    //PEON --> Come a su izquierda
                    if (ajedrez[i][j] == 0 && ajedrez[i - 1][j - 1] == 1) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j - 1;
                        jaqueFilaOrigen = i - 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec2 = rojo + "      El peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia arriba a izquierdas
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 7) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j - avanzaUno;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec3 = rojo + "      El alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia abajo a derechas
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 7) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j + avanzaUno;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec4 = rojo + "      El alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // TORRE --> vertical hacia abajo
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 3) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec5 = rojo + "      La torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // CABALLO
                    if (ajedrez[i - 2][j - 1] == 5) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j - 1;
                        jaqueFilaOrigen = i - 2;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec6 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (ajedrez[i - 2][j + 1] == 5) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j + 1;
                        jaqueFilaOrigen = i - 2;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec7 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (ajedrez[i - 1][j - 2] == 5) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j - 2;
                        jaqueFilaOrigen = i - 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec8 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    if (j + 2 <= 7 && ajedrez[i - 1][j + 2] == 5) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j + 2;
                        jaqueFilaOrigen = i - 2;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec9 = rojo + "      El caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // REY
                    if (ajedrez[i - 1][j] == 11 || ajedrez[i - 1][j - 1] == 11 || ajedrez[i - 1][j + 1] == 11) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j;
                        jaqueFilaOrigen = i - 1;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec10 = rojo + "      El rey entre 'e2' y 'h2' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // DAMA --> la suma del alfil diagonales hacia arriba y la torre hacia arriba
                    // Torre hacia arriba

                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 9) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec11 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia arriba a izquierdas
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 9) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j - avanzaUno;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec12 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                    // ALFIL --> hacia arriba a derechas
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 9) {
                        hayEnroqueCorto = false;
                        jaqueColumnaOrigen = j + avanzaUno;
                        jaqueFilaOrigen = i - avanzaUno;
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j;

                        convertiraTablero();

                        ec13 = rojo + "      La dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' hace jaque al rey a su paso por '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    }

                }

            }

        }

    }

    public static void comprobarJaquePropioParaEnroque(int jugador) {
        // Aquí compruebo si mi propio rey está amenazado para poder realizar el enroque
        for (int i = 0; i < ajedrez.length; i++) {
            for (int j = 0; j < ajedrez[i].length; j++) {
                if (jugador == 1 && i == 0 && j == 4 && ajedrez[i][j] == 11) {
                    // Compruebo los JAQUES a MI PROPIO REY
                    //PEON
                    if (ajedrez[i + 1][j + 1] == 2) {
                        // Los valores 'jaqueFilaOrigen' y 'jaqueColumnaOrigen' se mantienen en adelante
                        jaqueFilaOrigen = i;
                        jaqueColumnaOrigen = j;

                        jaqueFilaDestino = i + 1;
                        jaqueColumnaDestino = j + 1;
                        convertiraTablero();

                        jp = rojo + "      Estás sobre JAQUE !! -->  Peón '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    //PEON
                    if (ajedrez[i + 1][j - 1] == 2) {
                        jaqueFilaDestino = i + 1;
                        jaqueColumnaDestino = j - 1;
                        convertiraTablero();

                        jp1 = rojo + "      Estás sobre JAQUE !! -->  Peón '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    //TORRE hacia abajo
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 4) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        jp2 = rojo + "      Estás sobre JAQUE !! -->  Torre '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    //TORRE hacia izquierdas
                    avanzaUno = 1;
                    while (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 4) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        jp3 = rojo + "      Estás sobre JAQUE !! -->  Torre '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    //TORRE hacia derechas
                    avanzaUno = 1;
                    while (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 4) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        jp4 = rojo + "      Estás sobre JAQUE !! -->  Torre '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    // ALFIL --> hacia abajo a izquierdas
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 8) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        jp5 = rojo + "      Estás sobre JAQUE !! -->  Alfil '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    // ALFIL --> hacia abajo a derechas
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 8) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        jp6 = rojo + "      Estás sobre JAQUE !! -->  Alfil '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    // CABALLO
                    if (ajedrez[i + 2][j - 1] == 6) {
                        jaqueFilaDestino = i + 2;
                        jaqueColumnaDestino = j - 1;
                        convertiraTablero();

                        jp7 = rojo + "      Estás sobre JAQUE !! -->  Caballo '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    if (ajedrez[i + 2][j + 1] == 6) {
                        jaqueFilaDestino = i + 2;
                        jaqueColumnaDestino = j + 1;
                        convertiraTablero();

                        jp8 = rojo + "      Estás sobre JAQUE !! -->  Caballo '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    if (ajedrez[i + 1][j - 2] == 6) {
                        jaqueFilaDestino = i + 1;
                        jaqueColumnaDestino = j - 2;
                        convertiraTablero();

                        jp9 = rojo + "      Estás sobre JAQUE !! -->  Caballo '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    if (ajedrez[i + 1][j + 2] == 6) {
                        jaqueFilaDestino = i + 1;
                        jaqueColumnaDestino = j + 2;
                        convertiraTablero();

                        jp10 = rojo + "      Estás sobre JAQUE !! -->  Caballo '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    // REY
                    if (ajedrez[i + 1][j] == 12 || ajedrez[i + 1][j - 1] == 12 || ajedrez[i + 1][j + 1] == 12 || ajedrez[i][j - 1] == 12 || ajedrez[i][j + 1] == 12) {
                        convertiraTablero();

                        jp11 = rojo + "      Estás sobre JAQUE !! -->  El Rey  --> a tu Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    // DAMA --> la suma del alfil diagonales hacia arriba y la torre hacia arriba
                    // Torre hacia abajo
                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 10) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        jp12 = rojo + "      Estás sobre JAQUE !! -->  Dama '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    // Torre hacia derechas
                    avanzaUno = 1;
                    while (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 10) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        jp13 = rojo + "      Estás sobre JAQUE !! -->  Dama '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    // Torre hacia izquierdas
                    avanzaUno = 1;
                    while (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 10) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        jp14 = rojo + "      Estás sobre JAQUE !! -->  Dama '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    // ALFIL --> hacia abajo a izquierdas

                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 10) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        jp15 = rojo + "      Estás sobre JAQUE !! -->  Dama '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    // ALFIL --> hacia abajo a derechas

                    avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 10) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        jp16 = rojo + "      Estás sobre JAQUE !! -->  Dama '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                }

                // BLANCAS
                if (jugador == 2 && j == 4 && i == 7 && ajedrez[i][j] == 12) {
                    // Compruebo los JAQUES a MI PROPIO REY
                    //PEON
                    if (ajedrez[i - 1][j + 1] == 1) {
                        jaqueFilaOrigen = i;
                        jaqueColumnaOrigen = j;

                        jaqueFilaDestino = i - 1;
                        jaqueColumnaDestino = j + 1;
                        convertiraTablero();

                        jp = rojo + "      Estás sobre JAQUE !! -->  Peón '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    if (ajedrez[i - 1][j - 1] == 1) {
                        jaqueFilaOrigen = i;
                        jaqueColumnaOrigen = j;

                        jaqueFilaDestino = i - 1;
                        jaqueColumnaDestino = j - 1;
                        convertiraTablero();

                        jp1 = rojo + "      Estás sobre JAQUE !! -->  Peón '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    //TORRE hacia arriba
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 3) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        jp2 = rojo + "      Estás sobre JAQUE !! -->  Torre '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    //TORRE hacia izquierdas
                    avanzaUno = 1;
                    while (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 3) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        jp3 = rojo + "      Estás sobre JAQUE !! -->  Torre '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    //TORRE hacia derechas
                    avanzaUno = 1;
                    while (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 3) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        jp4 = rojo + "      Estás sobre JAQUE !! -->  Torre '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    // ALFIL --> hacia arriba a izquierdas
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 7) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        jp5 = rojo + "      Estás sobre JAQUE !! -->  Alfil '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    // ALFIL --> hacia arriba a derechas
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 7) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        jp6 = rojo + "      Estás sobre JAQUE !! -->  Alfil '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    // CABALLO
                    if (ajedrez[i - 2][j - 1] == 5) {
                        jaqueFilaDestino = i - 2;
                        jaqueColumnaDestino = j - 1;
                        convertiraTablero();

                        jp7 = rojo + "      Estás sobre JAQUE !! -->  Caballo '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    if (ajedrez[i - 2][j + 1] == 5) {
                        jaqueFilaDestino = i - 2;
                        jaqueColumnaDestino = j + 1;
                        convertiraTablero();

                        jp8 = rojo + "      Estás sobre JAQUE !! -->  Caballo '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    if (ajedrez[i - 1][j - 2] == 5) {
                        jaqueFilaDestino = i - 1;
                        jaqueColumnaDestino = j - 2;
                        convertiraTablero();

                        jp9 = rojo + "      Estás sobre JAQUE !! -->  Caballo '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    if (ajedrez[i - 1][j + 2] == 5) {
                        jaqueFilaDestino = i - 1;
                        jaqueColumnaDestino = j + 2;
                        convertiraTablero();

                        jp10 = rojo + "      Estás sobre JAQUE !! -->  Caballo '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    // REY
                    if (ajedrez[i - 1][j] == 11 || ajedrez[i - 1][j - 1] == 11 || ajedrez[i - 1][j + 1] == 11 || ajedrez[i][j - 1] == 11 || ajedrez[i][j + 1] == 11) {
                        convertiraTablero();

                        jp11 = rojo + "      Estás sobre JAQUE !! -->  El Rey  --> a tu Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    // DAMA --> la suma del alfil diagonales hacia arriba y la torre hacia arriba
                    // Torre hacia arriba
                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 9) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        jp12 = rojo + "      Estás sobre JAQUE !! -->  Dama '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    // Torre hacia derechas
                    avanzaUno = 1;
                    while (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 9) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        jp13 = rojo + "      Estás sobre JAQUE !! -->  Dama '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }
                    // Torre hacia izquierdas
                    avanzaUno = 1;
                    while (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }
                    if (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 9) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        jp14 = rojo + "      Estás sobre JAQUE !! -->  Dama '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    // ALFIL --> hacia arriba a izquierdas

                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 9) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        jp15 = rojo + "      Estás sobre JAQUE !! -->  Dama '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                    // ALFIL --> hacia arriba a derechas

                    avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 9) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        jp16 = rojo + "      Estás sobre JAQUE !! -->  Dama '" + (char) tableroColumnaDestino + tableroFilaDestino + "' --> a Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "'\n" + reset;
                        hayJaquePropioParaEnroque = true;
                    }

                }
            }

        }

    }

    public static void convertiraTablero() {
        if (jaqueFilaOrigen == 0) {
            tableroFilaOrigen = 8;
        }

        if (jaqueFilaOrigen == 1) {
            tableroFilaOrigen = 7;
        }
        if (jaqueFilaOrigen == 2) {
            tableroFilaOrigen = 6;

        }
        if (jaqueFilaOrigen == 3) {
            tableroFilaOrigen = 5;
        }
        if (jaqueFilaOrigen == 4) {
            tableroFilaOrigen = 4;
        }
        if (jaqueFilaOrigen == 5) {
            tableroFilaOrigen = 3;
        }
        if (jaqueFilaOrigen == 6) {
            tableroFilaOrigen = 2;
        }
        if (jaqueFilaOrigen == 7) {
            tableroFilaOrigen = 1;
        }
        if (jaqueFilaDestino == 0) {
            tableroFilaDestino = 8;
        }
        if (jaqueFilaDestino == 1) {
            tableroFilaDestino = 7;
        }
        if (jaqueFilaDestino == 2) {
            tableroFilaDestino = 6;
        }
        if (jaqueFilaDestino == 3) {
            tableroFilaDestino = 5;
        }
        if (jaqueFilaDestino == 4) {
            tableroFilaDestino = 4;
        }
        if (jaqueFilaDestino == 5) {
            tableroFilaDestino = 3;
        }
        if (jaqueFilaDestino == 6) {
            tableroFilaDestino = 2;
        }
        if (jaqueFilaDestino == 7) {
            tableroFilaDestino = 1;
        }


        if (jaqueColumnaOrigen == 0) {
            tableroColumnaOrigen = 'a';
        }
        if (jaqueColumnaOrigen == 1) {
            tableroColumnaOrigen = 'b';
        }
        if (jaqueColumnaOrigen == 2) {
            tableroColumnaOrigen = 'c';
        }
        if (jaqueColumnaOrigen == 3) {
            tableroColumnaOrigen = 'd';
        }
        if (jaqueColumnaOrigen == 4) {
            tableroColumnaOrigen = 'e';
        }
        if (jaqueColumnaOrigen == 5) {
            tableroColumnaOrigen = 'f';
        }
        if (jaqueColumnaOrigen == 6) {
            tableroColumnaOrigen = 'g';
        }
        if (jaqueColumnaOrigen == 7) {
            tableroColumnaOrigen = 'h';
        }
        if (jaqueColumnaDestino == 0) {
            tableroColumnaDestino = 'a';
        }
        if (jaqueColumnaDestino == 1) {
            tableroColumnaDestino = 'b';
        }
        if (jaqueColumnaDestino == 2) {
            tableroColumnaDestino = 'c';
        }
        if (jaqueColumnaDestino == 3) {
            tableroColumnaDestino = 'd';
        }
        if (jaqueColumnaDestino == 4) {
            tableroColumnaDestino = 'e';
        }
        if (jaqueColumnaDestino == 5) {
            tableroColumnaDestino = 'f';
        }
        if (jaqueColumnaDestino == 6) {
            tableroColumnaDestino = 'g';
        }
        if (jaqueColumnaDestino == 7) {
            tableroColumnaDestino = 'h';
        }

    }

    public static void comprobarJaque(int jugador) {

        for (int i = 0; i < ajedrez.length; i++) {
            for (int j = 0; j < ajedrez[i].length; j++) {
                // AZULES JUGADOR 1
                //PEON
                if (j > 0 && j < 7 && jugador == 1 && ajedrez[i][j] == 1 && ajedrez[i + 1][j + 1] == 12) {
                    // Las variables 'jaqueFilaOrigen' y 'jaqueColumnaOrigen' se mantienen con el mismo valor en adelante 'i' y 'j'
                    jaqueFilaOrigen = i;
                    jaqueColumnaOrigen = j;
                    jaqueFilaDestino = i + 1;
                    jaqueColumnaDestino = j + 1;

                    // Aquí reconvierto las variables 'jaqueColumnaOrigen' y 'jaqueColumnaOrigen' a los valores del tablero con 'char' --> '(char) jaqueColumnaDestino'
                    convertiraTablero();

                    j1 = verde + "JAQUE !!  Peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;

                    comprobarJaque = true;
                }


                if (j > 0 && j < 7 && jugador == 1 && ajedrez[i][j] == 1 && ajedrez[i + 1][j - 1] == 12) {
                    jaqueFilaDestino = i + 1;
                    jaqueColumnaDestino = j - 1;
                    convertiraTablero();

                    j2 = verde + "JAQUE !!  Peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                    comprobarJaque = true;

                }

                // TORRE --> Vertical hacia abajo
                if (jugador == 1 && ajedrez[i][j] == 3) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 12) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        j3 = verde + "JAQUE !!  Torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // TORRE --> Vertical hacia arriba
                if (jugador == 1 && ajedrez[i][j] == 3) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 12) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        j4 = verde + "JAQUE !!  Torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // TORRE --> Horizontal a derechas
                if (jugador == 1 && ajedrez[i][j] == 3) {
                    int avanzaUno = 1;
                    while (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 12) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j5 = verde + "JAQUE !!  Torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // TORRE --> Horizontal a izquierdas
                if (jugador == 1 && ajedrez[i][j] == 3) {
                    int avanzaUno = 1;
                    while (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 12) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j6 = verde + "JAQUE !!  Torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // ALFIL --> Diagonal  arriba a derechas
                if (jugador == 1 && ajedrez[i][j] == 7) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 12) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j7 = verde + "JAQUE !!  Alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;

                    }
                }
                // ALFIL --> Diagonal abajo a derechas
                if (jugador == 1 && ajedrez[i][j] == 7) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 12) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j8 = verde + "JAQUE !!  Alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // ALFIL --> Diagonal abajo a izquierdas
                if (jugador == 1 && ajedrez[i][j] == 7) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 12) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j9 = verde + "JAQUE !!  Alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // ALFIL --> Diagonal arriba a izquierdas
                if (jugador == 1 && ajedrez[i][j] == 7) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 12) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j10 = verde + "JAQUE !!  Alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                //                                      X
                // CABALLO --> derechas arriba -->  X X X

                if (jugador == 1 && ajedrez[i][j] == 5) {

                    if (i - 1 >= 0 && j + 2 <= 7 && ajedrez[i - 1][j + 2] == 12) {
                        jaqueFilaDestino = i - 1;
                        jaqueColumnaDestino = j + 2;
                        convertiraTablero();

                        j11 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }

                // CABALLO --> derechas abajo -->  X X X
                //                                     X

                if (jugador == 1 && ajedrez[i][j] == 5) {

                    if (i + 1 <= 7 && j + 2 <= 7 && ajedrez[i + 1][j + 2] == 12) {
                        jaqueFilaDestino = i + 1;
                        jaqueColumnaDestino = j + 2;
                        convertiraTablero();

                        j12 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO --> abajo a derechas -->  X
                //                                   X
                //                                   X X

                if (jugador == 1 && ajedrez[i][j] == 5) {

                    if (i + 2 <= 7 && j + 1 <= 7 && ajedrez[i + 2][j + 1] == 12) {
                        jaqueFilaDestino = i + 2;
                        jaqueColumnaDestino = j + 1;
                        convertiraTablero();

                        j13 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO --> abajo a izquierdas -->  X
                //                                     X
                //                                   X X

                if (jugador == 1 && ajedrez[i][j] == 5) {

                    if (i + 2 <= 7 && j - 1 >= 0 && ajedrez[i + 2][j - 1] == 12) {
                        jaqueFilaDestino = i + 2;
                        jaqueColumnaDestino = j - 1;
                        convertiraTablero();

                        j14 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO --> izquierdas abajo -->  X X X
                //                                   X

                if (jugador == 1 && ajedrez[i][j] == 5) {

                    if (i + 1 <= 7 && j - 2 >= 0 && ajedrez[i + 1][j - 2] == 12) {
                        jaqueFilaDestino = i + 1;
                        jaqueColumnaDestino = j - 2;
                        convertiraTablero();

                        j15 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO --> izquierdas arriba --> X
                //                                   X X X

                if (jugador == 1 && ajedrez[i][j] == 5) {

                    if (i - 1 >= 0 && j - 2 >= 0 && ajedrez[i - 1][j - 2] == 12) {
                        jaqueFilaDestino = i - 1;
                        jaqueColumnaDestino = j - 2;
                        convertiraTablero();

                        j16 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO -->  arriba izquierdas  -->  X X
                //                                        X
                //                                        X

                if (jugador == 1 && ajedrez[i][j] == 5) {

                    if (i - 2 >= 0 && j - 1 >= 0 && ajedrez[i - 2][j - 1] == 12) {
                        jaqueFilaDestino = i - 2;
                        jaqueColumnaDestino = j - 1;
                        convertiraTablero();

                        j17 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO -->  arriba derechas  -->  X X
                //                                    X
                //                                    X

                if (jugador == 1 && ajedrez[i][j] == 5) {

                    if (i - 2 >= 0 && j + 1 <= 7 && ajedrez[i - 2][j + 1] == 12) {
                        jaqueFilaDestino = i - 2;
                        jaqueColumnaDestino = j + 1;
                        convertiraTablero();

                        j18 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                //  REY
                if (jugador == 1 && ajedrez[i][j] == 11) {

                    if ((i - 1) >= 0 && (j - 1) >= 0 && (i + 1) <= 7 && (j + 1) <= 7 && (ajedrez[i - 1][j] == 12 || ajedrez[i + 1][j] == 12 || ajedrez[i][j + 1] == 12 || ajedrez[i][j - 1] == 12 || ajedrez[i - 1][j + 1] == 12 || ajedrez[i + 1][j + 1] == 12 || ajedrez[i + 1][j - 1] == 12 || ajedrez[i - 1][j - 1] == 12)) {
                        jaqueFilaOrigen = i;
                        jaqueColumnaOrigen = j;
                        convertiraTablero();

                        j19 = verde + "JAQUE !!  Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey \n" + reset;
                        comprobarJaque = true;
                    }
                }
                //  DAMA  --> Voy a poner cada caso en una variable por si hay más de una dama (Por si promociona al peón)
                // Movimientos de torre en vertical hacia abajo
                if (jugador == 1 && ajedrez[i][j] == 9) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 12) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        j20 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de torre en vertical hacia arriba
                if (jugador == 1 && ajedrez[i][j] == 9) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 12) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        j21 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de torre en horizontal a derechas
                if (jugador == 1 && ajedrez[i][j] == 9) {
                    int avanzaUno = 1;
                    while (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 12) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j22 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de torre en horizontal a izquierdas
                if (jugador == 1 && ajedrez[i][j] == 9) {
                    int avanzaUno = 1;
                    while (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 12) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j23 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de alfil en diagonal arriba a derechas
                if (jugador == 1 && ajedrez[i][j] == 9) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 12) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j24 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de alfil en diagonal abajo a derechas
                if (jugador == 1 && ajedrez[i][j] == 9) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 12) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j25 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de alfil en diagonal abajo a izquierdas
                if (jugador == 1 && ajedrez[i][j] == 9) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 12) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j26 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de alfil en diagonal arriba a izquierdas
                if (jugador == 1 && ajedrez[i][j] == 9) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 12) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j27 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }

                //  BLANCAS  --> JUGADOR = 2
                //PEON
                if (j > 0 && j < 7 && jugador == 2 && ajedrez[i][j] == 2 && ajedrez[i - 1][j + 1] == 11) {
                    jaqueFilaDestino = i - 1;
                    jaqueColumnaDestino = j + 1;
                    convertiraTablero();

                    j1 = verde + "JAQUE !!  Peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                }

                if (j > 0 && j < 7 && jugador == 2 && ajedrez[i][j] == 2 && ajedrez[i - 1][j - 1] == 11) {
                    jaqueFilaDestino = i - 1;
                    jaqueColumnaDestino = j - 1;
                    convertiraTablero();

                    j2 = verde + "JAQUE !!  Peón '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;

                }
                // TORRE --> Vertical hacia abajo
                if (jugador == 2 && ajedrez[i][j] == 4) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 11) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        j3 = verde + "JAQUE !!  Torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // TORRE --> Vertical hacia arriba
                if (jugador == 2 && ajedrez[i][j] == 4) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 0) {
                        avanzaUno++;
                    }
                    if (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 11) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        j4 = verde + "JAQUE !!  Torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // TORRE --> Horizontal a derechas
                if (jugador == 2 && ajedrez[i][j] == 4) {
                    int avanzaUno = 1;
                    while (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 11) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j5 = verde + "JAQUE !!  Torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // TORRE --> Horizontal a izquierdas
                if (jugador == 2 && ajedrez[i][j] == 4) {
                    int avanzaUno = 1;
                    while (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 11) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j6 = verde + "JAQUE !!  Torre '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // ALFIL --> Diagonal  arriba a derechas
                if (jugador == 2 && ajedrez[i][j] == 8) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 11) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j7 = verde + "JAQUE !!  Alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // ALFIL --> Diagonal abajo a derechas
                if (jugador == 2 && ajedrez[i][j] == 8) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 11) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j8 = verde + "JAQUE !!  Alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // ALFIL --> Diagonal abajo a izquierdas
                if (jugador == 2 && ajedrez[i][j] == 8) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 11) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j9 = verde + "JAQUE !!  Alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // ALFIL --> Diagonal arriba a izquierdas
                if (jugador == 2 && ajedrez[i][j] == 8) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 11) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j10 = verde + "JAQUE !!  Alfil '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                //                                      X
                // CABALLO --> derechas arriba -->  X X X

                if (jugador == 2 && ajedrez[i][j] == 6) {

                    if (i - 1 >= 0 && j + 2 <= 7 && ajedrez[i - 1][j + 2] == 11) {
                        jaqueFilaDestino = i - 1;
                        jaqueColumnaDestino = j + 2;
                        convertiraTablero();

                        j11 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }

                // CABALLO --> derechas abajo -->  X X X
                //                                     X

                if (jugador == 2 && ajedrez[i][j] == 6) {

                    if (i + 1 <= 7 && j + 2 <= 7 && ajedrez[i + 1][j + 2] == 11) {
                        jaqueFilaDestino = i + 1;
                        jaqueColumnaDestino = j + 2;
                        convertiraTablero();

                        j12 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO --> abajo a derechas -->  X
                //                                   X
                //                                   X X

                if (jugador == 2 && ajedrez[i][j] == 6) {

                    if (i + 2 <= 7 && j + 1 <= 7 && ajedrez[i + 2][j + 1] == 11) {
                        jaqueFilaDestino = i + 2;
                        jaqueColumnaDestino = j + 1;
                        convertiraTablero();

                        j13 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO --> abajo a izquierdas -->  X
                //                                     X
                //                                   X X

                if (jugador == 2 && ajedrez[i][j] == 6) {

                    if (i + 2 <= 7 && j - 1 >= 0 && ajedrez[i + 2][j - 1] == 11) {
                        jaqueFilaDestino = i + 2;
                        jaqueColumnaDestino = j - 1;
                        convertiraTablero();

                        j14 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO --> izquierdas abajo -->  X X X
                //                                   X

                if (jugador == 2 && ajedrez[i][j] == 6) {

                    if (i + 1 <= 7 && j - 2 >= 0 && ajedrez[i + 1][j - 2] == 11) {
                        jaqueFilaDestino = i + 1;
                        jaqueColumnaDestino = j - 2;
                        convertiraTablero();

                        j15 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO --> izquierdas arriba --> X
                //                                   X X X

                if (jugador == 2 && ajedrez[i][j] == 6) {

                    if (i - 1 >= 0 && j - 2 >= 0 && ajedrez[i - 1][j - 2] == 11) {
                        jaqueFilaDestino = i - 1;
                        jaqueColumnaDestino = j - 2;
                        convertiraTablero();

                        j16 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO -->  arriba izquierdas  -->  X X
                //                                        X
                //                                        X

                if (jugador == 2 && ajedrez[i][j] == 6) {

                    if (i - 2 >= 0 && j - 1 >= 0 && ajedrez[i - 2][j - 1] == 11) {
                        jaqueFilaDestino = i - 2;
                        jaqueColumnaDestino = j - 1;
                        convertiraTablero();

                        j17 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // CABALLO -->  arriba derechas  -->  X X
                //                                    X
                //                                    X

                if (jugador == 2 && ajedrez[i][j] == 6) {

                    if (i - 2 >= 0 && j + 1 <= 7 && ajedrez[i - 2][j + 1] == 11) {
                        jaqueFilaDestino = i - 2;
                        jaqueColumnaDestino = j + 1;
                        convertiraTablero();

                        j18 = verde + "JAQUE !!  Caballo '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                //  REY
                if (jugador == 2 && ajedrez[i][j] == 12) {

                    if ((i - 1) >= 0 && (j - 1) >= 0 && (i + 1) <= 7 && (j + 1) <= 7 && (ajedrez[i - 1][j] == 11 || ajedrez[i + 1][j] == 11 || ajedrez[i][j + 1] == 11 || ajedrez[i][j - 1] == 11 || ajedrez[i - 1][j + 1] == 11 || ajedrez[i + 1][j + 1] == 11 || ajedrez[i + 1][j - 1] == 11 || ajedrez[i - 1][j - 1] == 11)) {
                        jaqueFilaOrigen = i;
                        jaqueColumnaOrigen = j;
                        convertiraTablero();

                        j19 = verde + "JAQUE !!  Rey '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey \n" + reset;
                        comprobarJaque = true;
                    }
                }
                //  DAMA --> Voy a poner cada caso en una variablepor si hay más de una dama (Por si promociona al peón)
                // Movimientos de torre en vertical hacia abajo
                if (jugador == 2 && ajedrez[i][j] == 10) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && ajedrez[i + avanzaUno][j] == 11) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        j20 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de torre en vertical hacia arriba
                if (jugador == 2 && ajedrez[i][j] == 10) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && ajedrez[i - avanzaUno][j] == 11) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j;
                        convertiraTablero();

                        j21 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de torre en horizontal a derechas
                if (jugador == 2 && ajedrez[i][j] == 10) {
                    int avanzaUno = 1;
                    while (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (j + avanzaUno <= 7 && ajedrez[i][j + avanzaUno] == 11) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j22 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de torre en horizontal a izquierdas
                if (jugador == 2 && ajedrez[i][j] == 10) {
                    int avanzaUno = 1;
                    while (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (j - avanzaUno >= 0 && ajedrez[i][j - avanzaUno] == 11) {
                        jaqueFilaDestino = i;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j23 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de alfil en diagonal arriba a derechas
                if (jugador == 2 && ajedrez[i][j] == 10) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j + avanzaUno <= 7 && ajedrez[i - avanzaUno][j + avanzaUno] == 11) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j24 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de alfil en diagonal abajo a derechas
                if (jugador == 2 && ajedrez[i][j] == 10) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j + avanzaUno <= 7 && ajedrez[i + avanzaUno][j + avanzaUno] == 11) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j + avanzaUno;
                        convertiraTablero();

                        j25 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de alfil en diagonal abajo a izquierdas
                if (jugador == 2 && ajedrez[i][j] == 10) {
                    int avanzaUno = 1;
                    while (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i + avanzaUno <= 7 && j - avanzaUno >= 0 && ajedrez[i + avanzaUno][j - avanzaUno] == 11) {
                        jaqueFilaDestino = i + avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j26 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }
                // Movimientos de alfil en diagonal arriba a izquierdas
                if (jugador == 2 && ajedrez[i][j] == 10) {
                    int avanzaUno = 1;
                    while (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 0) {
                        avanzaUno++;
                    }

                    if (i - avanzaUno >= 0 && j - avanzaUno >= 0 && ajedrez[i - avanzaUno][j - avanzaUno] == 11) {
                        jaqueFilaDestino = i - avanzaUno;
                        jaqueColumnaDestino = j - avanzaUno;
                        convertiraTablero();

                        j27 = verde + "JAQUE !!  Dama '" + (char) tableroColumnaOrigen + tableroFilaOrigen + "' --> a Rey '" + (char) tableroColumnaDestino + tableroFilaDestino + "'\n" + reset;
                        comprobarJaque = true;
                    }
                }

            }
        }


    }

    // Funcion de visualizar tablero a través de la línea de comandos (CLI)
    /*

    public static void visualizarTablero() {
        System.out.println();
        System.out.println("                        ┏━━━━━━━┳━━━━━━┳━━━━━━┳━━━━━━┳━━━━━━┳━━━━━━┳━━━━━━┳━━━━━━┓");

        for (int i = 0; i < ajedrez.length; i++) {

            if (i == 0) {
                System.out.print(verde + "                    8" + reset + "   ┃");
            }
            if (i == 1) {
                System.out.println("                        ┣━━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━┫");
                System.out.print(verde + "                    7" + reset + "   ┃");
            }
            if (i == 2) {
                System.out.println("                        ┣━━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━┫");
                System.out.print(verde + "                    6" + reset + "   ┃");
            }
            if (i == 3) {
                System.out.println("                        ┣━━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━┫");
                System.out.print(verde + "                    5" + reset + "   ┃");
            }
            if (i == 4) {
                System.out.println("                        ┣━━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━┫");
                System.out.print(verde + "                    4" + reset + "   ┃");
            }
            if (i == 5) {
                System.out.println("                        ┣━━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━┫");
                System.out.print(verde + "                    3" + reset + "   ┃");
            }
            if (i == 6) {
                System.out.println("                        ┣━━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━┫");
                System.out.print(verde + "                    2" + reset + "   ┃");
            }
            if (i == 7) {
                System.out.println("                        ┣━━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━╋━━━━━━┫");
                System.out.print(verde + "                    1" + reset + "   ┃");
            }

            for (int j = 0; j < ajedrez[i].length; j++) {
                if (ajedrez[i][j] == 0) {
                    System.out.format("%-7s", "");
                }
                if (ajedrez[i][j] == 1) {
                    System.out.format(azul + "%6s" + reset, "♙");
                }
                if (ajedrez[i][j] == 2) {
                    System.out.format("%6s", "♙");
                }
                if (ajedrez[i][j] == 3) {
                    System.out.print(azul + "   ♖  " + reset);
                }
                if (ajedrez[i][j] == 4) {
                    System.out.print("   ♖  ");
                }
                if (ajedrez[i][j] == 5) {
                    System.out.print(azul + "   ♘  " + reset);
                }
                if (ajedrez[i][j] == 6) {
                    System.out.print("   ♘  ");
                }
                if (ajedrez[i][j] == 7) {
                    System.out.print(azul + "    ♗  " + reset);
                }
                if (ajedrez[i][j] == 8) {
                    System.out.print("    ♗  ");
                }
                if (ajedrez[i][j] == 9) {
                    System.out.format(azul + "   ♕  " + reset);
                }
                if (ajedrez[i][j] == 10) {
                    System.out.print("   ♕  ");
                }
                if (ajedrez[i][j] == 11) {
                    System.out.format(azul + "   ♔  " + reset);
                }
                if (ajedrez[i][j] == 12) {
                    System.out.print("   ♔  ");
                }
                if (ajedrez[i][j] == 13) {
                    System.out.print(verde + "  ♖" + reset + rojo + "♔  " + reset);
                }
                if (ajedrez[i][j] == 14) {
                    System.out.print(verde + "  ♙" + reset + rojo + "♔  " + reset);
                }
                if (ajedrez[i][j] == 15) {
                    System.out.print(verde + "  ♗" + reset + rojo + "♔  " + reset);
                }
                if (ajedrez[i][j] == 16) {
                    System.out.print(verde + "  ♘" + reset + rojo + "♔" + reset);
                }
                if (ajedrez[i][j] == 17) {
                    System.out.print(verde + "   ♔" + reset + rojo + "♔  " + reset);
                }
                if (ajedrez[i][j] == 18) {
                    System.out.print(verde + "   ♕" + reset + rojo + "♔  " + reset);
                }

            }

            System.out.println();
        }

        System.out.println("                        ┗━━━━━━━┻━━━━━━┻━━━━━━┻━━━━━━┻━━━━━━┻━━━━━━┻━━━━━━┻━━━━━━┛");
        System.out.println(verde + "                            a       b      c      d      e      f      g      h  " + reset);

        System.out.println();
    }

     */


    // Esta función es para sacar el tablero por la GUI
    public static void visualizarTablero() {


        //JFrame frame = new JFrame("Ajedrez"); --> la pongo como global arriba, para que solo me genere una ventana siempre que llame a la función

        frame.setContentPane(new VTablero(ajedrez).getPanelTablero());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(50, 50); // Lo pongo en una posición que quiero en la pantalla
        //frame.setLocationRelativeTo(null); // Saca la ventana al centro
        //frame.setSize(200, 200); // Fijo el tamaño de la ventana, mejor lo quito y asi evito el 'parpadeo' , al redimensionar
        frame.setAlwaysOnTop(true); // Se queda siempre por encima de todas las aplicaciones

        // Recojo en un 'try-catch' el error que me genera 'frame.setUndecorated(true)' -->
        try {
            frame.setUndecorated(true); // Quitar la barra de estado
        } catch (Exception e) {

        }

        frame.pack();
        frame.setVisible(true);


         /*

         //Ahora tiene que cerrarse la ventana de la anterior jugada , Esto no me sirve, pongo como Global la ventana frame y solo me genera 1 ventana

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelTablero);
        frame.dispose();

         // Llamo a la ventana anterior que he abierto 'frame' y tengo 3 formas para que no me genere otra ventana, he probado esto y tampoco funciona.

        repaint();
        dispose();
        pack();

         */


    }
}
