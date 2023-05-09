package com.ikasgela;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.sampled.SourceDataLine;

public class Main {

    // Variables globales para cambiar los colores de las fichas

    static String red = "\033[31m";
    static String yellow = "\033[33m";
    static String white = "\033[37m";
    static String reset = "\u001B[0m";
    static String blue = "\033[34m";

   
    static int columna;
    static boolean turno = true;
    static int juego = 0;

    // Array global de la matriz de conecta4

    static int[][] conecta4 = {
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1},
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        char jugador;
        boolean correcto = true;


        do {

            System.out.println();

            visualizarTablero();

            System.out.print("Elige ficha: \n"
                    + "0 Ficha amarilla --> " + yellow + "🟡" + reset + "\n"
                    + "1 Ficha roja     --> " + red + "🔴" + reset + "\n"
                    + "Opción (1/0): ");

            jugador = br.readLine().charAt(0);
            System.out.println();

        } while (jugador != '0' && jugador != '1');


        do {

            System.out.println("Tu turno jugador " + (turno ? "0 --> " + yellow + "🟡" + reset : "1 --> " + red + "🔴" + reset));

            // Solamente necesito pedir la columna, para introducir la ficha y esta irá a la posición más baja, pues el tablero está en vertical

            do {
                do {
                    correcto = true;
                    try {
                        System.out.print("Columna: ");
                        columna = Integer.parseInt(br.readLine()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Error !!... mete un valor correcto: 1,2,3,4,5,6,7 ");
                        correcto = false;
                    }
                } while (!correcto);
            } while (columna + 1 < 1 || columna + 1 > 7);

            System.out.println();
            moverFicha();
            visualizarTablero();

            if (comprobarGanador()) {
                System.out.println(blue + "Has ganado jugador " + (turno ? "1" : "0") + " !! \uD83E\uDD17 ✌️ +reset");
            }

            System.out.println();


        } while (juego < 42 && !comprobarGanador());

        if (juego == 42 && !comprobarGanador()) {
            System.out.print("Empate !!... Finalizó el juego ");
        }


    }

    // Compruebo con el método 'moverFicha' que la ficha caiga a la posición más baja que no esté ocupada
    // Obviamente, nunca se va ocupar una casilla que ya esté ocupada, lo soluciono con un 'else' al final !!

    public static void moverFicha() {

        /* Quiero meter las 5 condiciones dentro de un 'for', pero necesito que siempre se haga un 'else if', pero no me sale

        for (int i = 5; i > -1; i--) {
            if (conecta4[i][columna] == -1) { // Empiezo por la fila más baja que esté vacía (-1), lo relleno cun 0 o un 1, según el turno
                conecta4[i][columna] = turno ? 0 : 1; // Aqui pongo el valor de 0 ó 1 en la casilla según el jugador que esté jugando
                turno = !turno; // Alternar el turno entre el jugador 0 y el jugador 1
                juego++; // Aumento una jugada cada vez que paso por el bucle hasta 42 jugadas máximas, qu en este caso habría empate
            }
        }

         */

        if (conecta4[5][columna] == -1) { // Empiezo por la fila más baja que esté vacía (-1), lo relleno con 0 o un 1, según el turno
            conecta4[5][columna] = turno ? 0 : 1; // Aqui pongo el valor de 0 ó 1 en la casilla según el jugador que esté jugando
            turno = !turno; // Alternar el turno entre el jugador 0 y el jugador 1
            juego++; // Aumento una jugada cada vez que paso por el bucle hasta 42 jugadas máximas, qu en este caso habría empate

        } else if (conecta4[4][columna] == -1) {

            conecta4[4][columna] = turno ? 0 : 1;
            turno = !turno; // Alternar el turno entre el jugador 0 y el jugador 1
            juego++; // Aumento 1 cada vez que paso por el bucle

        } else if (conecta4[3][columna] == -1) {
            conecta4[3][columna] = turno ? 0 : 1;
            turno = !turno;
            juego++;

        } else if (conecta4[2][columna] == -1) {
            conecta4[2][columna] = turno ? 0 : 1;
            turno = !turno;
            juego++;

        } else if (conecta4[1][columna] == -1) {
            conecta4[1][columna] = turno ? 0 : 1;
            turno = !turno;
            juego++;

        } else if (conecta4[0][columna] == -1) {
            conecta4[0][columna] = turno ? 0 : 1;
            turno = !turno;
            juego++;

        } else {
            System.out.println("La columna " + (columna + 1) + " ya está llena !! \uD83E\uDD14\n ");
        }


    }

    public static void visualizarTablero() {


        System.out.println(blue +"    1      2     3      4     5     6     7\n");
        System.out.println(blue +"    ⬇️    ⬇️    ⬇️    ⬇️    ⬇️    ⬇️    ⬇️\n️️" + reset);

        for (int i = 0; i < conecta4.length; i++) {
            for (int j = 0; j < conecta4[i].length; j++) {

                if (conecta4[i][j] == 0) {
                    System.out.format("    %4s", yellow + "🟡" + reset);
                } else if (conecta4[i][j] == 1) {
                    System.out.format("    %4s", red + "🔴" + reset);
                } else {
                    System.out.format("    %4s", white + "⚪" + reset);
                }

            }
            System.out.println("\n");
        }
    }


    public static boolean comprobarGanador() {

        /* En el conecta 4hay las siguientes combinaciones ganadoras:
           - Filas (horizontales):  4 soluciones por fila X 6 filas = 24 combinaciones ganadoras en todas las filas
           - Columnas (verticales): 3 soluciones por columna X 7 columnas = 21 combinaciones ganadoras en todas las columnas
           - Diagonales : (1 + 2 + 3 + 3 + 2 + 1) X 2 (diagonales) = 24 combinaciones ganadoras en todas las diagonales
           - Totales = 24 + 21 + 24 = 69 Combinaciones ganadoras X 2 jugadores (0 y 1) = 158 combinaciones ganadoras para hacer el método
         */

        boolean ganador = false;
        /*

        if (conecta4[5][0] == 0 && conecta4[5][1] == 0 && conecta4[5][2] == 0 && conecta4[5][3] == 0) {
            ganador = true;
        }

         */

        // Hago con dos 'for' anidados todas las combinaciones ganadoras para todas las FILAS y para los 2 jugadores (con esto salen 48 combinaciones)

        for (int i = 0; i < conecta4.length; i++) {
            for (int j = 0; j < conecta4[i].length - 4; j++) { // Se aumentan las 4 fichas seguidas hasta la 3ª columna (posición 2)
                if (conecta4[i][j] == 0 && conecta4[i][j + 1] == 0 && conecta4[i][j + 2] == 0 && conecta4[i][j + 3] == 0) {
                    ganador = true;
                }
                if (conecta4[i][j] == 1 && conecta4[i][j + 1] == 1 && conecta4[i][j + 2] == 1 && conecta4[i][j + 3] == 1) {
                    ganador = true;
                }

            }
        }

        // Hago con dos 'for' anidados todas las combinaciones ganadoras para todas las COLUMNAS y para los 2 jugadores (con esto salen 42 combinaciones)

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < conecta4[i].length; j++) {
                if (conecta4[i][j] == 0 && conecta4[i + 1][j] == 0 && conecta4[i + 2][j] == 0 && conecta4[i + 3][j] == 0) {
                    ganador = true;
                }
                if (conecta4[i][j] == 1 && conecta4[i + 1][j] == 1 && conecta4[i + 2][j] == 1 && conecta4[i + 3][j] == 1) {
                    ganador = true;
                }
            }
        }

        // Ahora para las DIAGONALES  de un lado --> \:

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (conecta4[i][j] == 0 && conecta4[i + 1][j + 1] == 0 && conecta4[i + 2][j + 2] == 0 && conecta4[i + 3][j + 3] == 0) {
                    ganador = true;
                }
                if (conecta4[i][j] == 1 && conecta4[i + 1][j + 1] == 1 && conecta4[i + 2][j + 2] == 1 && conecta4[i + 3][j + 3] == 1) {
                    ganador = true;
                }
            }
        }

        // Ahora para las DIAGONALES del otro lado --> /: (ESTE ME HA COSTADO MÁS.... CON LAS  ***** 'i' 'j')


        for (int i = 5; i > 2; i--) {
            for (int j = 0; j < 4; j++) {
                if (conecta4[i][j] == 0 && conecta4[i - 1][j + 1] == 0 && conecta4[i - 2][j + 2] == 0 && conecta4[i - 3][j + 3] == 0) {
                    ganador = true;
                }
                if (conecta4[i][j] == 1 && conecta4[i - 1][j + 1] == 1 && conecta4[i - 2][j + 2] == 1 && conecta4[i - 3][j + 3] == 1) {
                    ganador = true;
                }
            }
        }

        return ganador;

    }

}



