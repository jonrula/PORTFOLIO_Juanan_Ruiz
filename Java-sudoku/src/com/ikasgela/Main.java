package com.ikasgela;

import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static String black = "\033[30m";
    static String red = "\033[31m";
    static String green = "\033[32m";
    static String yellow = "\033[33m";
    static String blue = "\033[34m";
    static String purple = "\033[35m";
    static String cyan = "\033[36m";
    static String white = "\033[37m";
    static String reset = "\u001B[0m";

    static int fila;
    static int columna = 0;
    static int valor = 0;


    static int[][] sudoku = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9},
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        boolean correcto = true;

        String fin = "";

        System.out.println(
                green+ "\n\n\n                                                *********** REGLAS DEL SUDOKU ***********\n\n"+
                "El sudoku se presenta normalmente como una tabla de 9 × 9, compuesta por subtablas de 3 × 3 denominadas \"regiones\" (también se le llaman \"cajas\" o \"bloques\").\n" +
                "\n" +
                "Algunas celdas ya contienen números, conocidos como \"números dados\" (o a veces \"pistas\"). El objetivo es rellenar las celdas vacías, con un número en cada una de ellas, de tal forma que cada columna, fila y región contenga los números 1–9 solo una vez.\n" +
                "\n" +
                "Además, cada número de la solución aparece solo una vez en cada una de las tres \"direcciones\", de ahí el \"los números deben estar solos\" que evoca el nombre del juego.\n\n"+
                reset);

        System.out.println();
        System.out.println("ESTE ES EL SUDOKU A RESOLVER: ");

        visualizarTablero();
        System.out.println();

        // Ahora le pregunto a l usuario por los 3 datos, fila, columna y valor, pero teniendo en cuenta que si escribe 'fin' en cualquiera de los tres, el programa acaba

        do {

            do {

                do {
                    correcto = true;
                    System.out.print("Introduce una fila (1-9): ");
                    fin = br.readLine();

                    if (!fin.equalsIgnoreCase("fin")) {
                        try {
                            fila = Integer.parseInt(fin) - 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Error !");
                            correcto = false;
                        }
                    }
                } while (fila + 1 < 1 || fila + 1 > 9);

            } while (!fin.equalsIgnoreCase("fin") && !correcto); // Aquí pide la 'fila', si el usuario pone 'fin' sale del bucle

            if (!fin.equalsIgnoreCase("fin")) { // Si no pone 'fin', sige adelante y se mete el siguiente bucle

                do {

                    do {
                        correcto = true;
                        System.out.print("Introduce una columna (1-9): ");
                        fin = br.readLine();

                        if (!fin.equalsIgnoreCase("fin")) {
                            try {
                                columna = Integer.parseInt(fin) - 1;
                            } catch (NumberFormatException e) {
                                System.out.println("Error!");
                                correcto = false;
                            }
                        }
                    } while (columna + 1 < 1 || columna + 1 > 9);

                } while (!fin.equalsIgnoreCase("fin") && !correcto); // Aquí pide la 'columna', si el usuario pone 'fin' sale del bucle

                if (!fin.equalsIgnoreCase("fin")) { // Si no pone 'fin', sige adelante y se mete el siguiente bucle

                    do {

                        do {
                            correcto = true;
                            System.out.print("Introduce una valor (1-9): ");
                            fin = br.readLine();

                            if (!fin.equalsIgnoreCase("fin")) {
                                try {
                                    valor = Integer.parseInt(fin);
                                } catch (NumberFormatException e) {
                                    System.out.println("Error !!");
                                    correcto = false;
                                }
                            }

                        } while ((valor < 1 || valor > 9) && !fin.equalsIgnoreCase("fin")); // --> Aquí hay que decirle que el 'valor' que mete si es'fin' que salga del bucle

                    } while (!correcto); // Aquí pide el' valor', si el usuario pone 'fin' sale del bucle

                    System.out.println(); // Línea en blanco después de pedir el 'valor'


                    if (!fin.equalsIgnoreCase("fin")) {

                        if (sudoku[fila][columna] == 0) { // Si la casilla está vacía

                            // Saco por separado cada error, excepto el de 'comprobarRegiones' que me lo comprueba el primero al comprobar las tres funciones -->  if (comprobarRegiones() && comprobarFilas() && comprobarColumnas())

                            if (!comprobarFilas()) {
                                System.out.println("Error !!... el número " + valor + " ya existe en la fila " + (fila + 1));
                            }
                            if (!comprobarColumnas()) {
                                System.out.println("Error !!... el número " + valor + " ya existe en la columna " + (columna + 1));
                            }
                            if (comprobarRegiones() && comprobarFilas() && comprobarColumnas()) { // Se tienen que cumplir las 3 condiciones , entonces inserto el valor en la casilla
                                sudoku[fila][columna] = valor; // Inserto el valor
                                visualizarTablero();
                            }

                        } else {
                            System.out.println("Error, esa casilla ya está ocupada por el número " + sudoku[fila][columna]);
                            visualizarTablero();
                        }
                        System.out.println();
                    }

                }
            }


        } while (!fin.equalsIgnoreCase("fin") && casillasVacias() != 0);

        /* Aquí para unir las dos condiciones y salir del 'do-while' hay que poner '&&', para que me devuelva siempre un 'true',
           porque con '||' siempre sale 'false' y no sale del bucle
           Por regla general cuando tengamos un '!' en alguna de las dos o más condiciones, unimos esas condiciones con un '&&' para que devuelva un 'true' y salga del bucle
         */
        System.out.println();

        if (fin.equalsIgnoreCase("fin")) {
            System.out.println(blue + "Hasta luego, nos vemos en la próxima..." + reset);
        } else {
            System.out.print(blue + "Enhorabuena !!, has solucionado el sudoku !!" + reset);
        }

        System.out.println();

    }

    public static int casillasVacias() {
        int casillasVacias = 0;

        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                if (sudoku[i][j] == 0) {
                    casillasVacias = casillasVacias + 1;
                }
            }
        }
        return casillasVacias;
    }

    public static boolean comprobarFilas() {

        boolean filaCorrecta = true; // lo inicializo con 'true' y si no encuentra ningún valor que se repita, pues la función es cierta

        // Compruebo que al añadir 'valor' en la casilla del sudoku, esta tiene que estar vacía (sudoku[fila][columna] == 0) y BUSCO el primer dato que coincida con el 'valor' introducido por el usuario en toda la fila y eso me devuelve 'false' --> sudoku[fila][j] == valor
        for (int i = 0; i < sudoku.length; i++) {
            if (sudoku[fila][columna] == 0 && sudoku[fila][i] == valor) { //
                filaCorrecta = false; // busco el primer valor que se repita
                break; // Que no siga buscando cuando encuentre el primer dato que se repita con el valor que quiero introducir
            }
        }
        return filaCorrecta;
    }

    // Compruebo que al añadir 'valor' en la casilla del sudoku, esta tiene que estar vacía (sudoku[fila][columna] == 0) y BUSCO el primer dato que coincida con el 'valor' introducido por el usuario en toda la columna y eso me devuelve 'false'--> sudoku[i][columna] == valor

    public static boolean comprobarColumnas() {

        boolean columnaCorrecta = true;

        for (int i = 0; i < sudoku.length; i++) {
            if (sudoku[fila][columna] == 0 && sudoku[i][columna] == valor) {
                columnaCorrecta = false;
                break;
            }
        }
        return columnaCorrecta;
    }

    // HAgo lo mismo con las regiones, compruebo que al añadir 'valor' en la casilla del sudoku, esta tiene que estar vacía (sudoku[fila][columna] == 0) y BUSCO el primer dato que coincida con el 'valor' introducido por el usuario en toda la matriz de la cuadrícula y eso me devuelve 'false'--> sudoku[fila][columna] == valor
    // Hagolo mismo con cada cuadrícula, en total 9, para sacr el error en caso de que lo hubiese, para guiarle al usuario

    public static boolean comprobarRegiones() {

        boolean regionCorrecta = true;

        // Primera cuadrícula superior izquierda

        if (fila <= 2 && columna <= 2) {
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (sudoku[fila][columna] == 0 && sudoku[i][j] == valor) { //
                        regionCorrecta = false;
                        System.out.println("Error !!... el número " + valor + " ya existe en la cuadrícula superior izquierda ");
                        visualizarTablero();
                        break;
                    }
                }
            }
        }

        // Segunda cuadrícula superior media

        if (fila <= 2 && columna >= 3 && columna <= 5) {
            for (int i = 0; i <= 2; i++) {
                for (int j = 3; j <= 5; j++) {
                    if (sudoku[fila][columna] == 0 && sudoku[i][j] == valor) {
                        regionCorrecta = false;
                        System.out.println("Error !!... el número " + valor + " ya existe en la cuadrícula superior media ");
                        break;
                    }
                }
            }
        }

        // Tercera cuadrícula superior derecha

        if (fila <= 2 && columna >= 6 && columna <= 8) {
            for (int i = 0; i <= 2; i++) {
                for (int j = 6; j <= 8; j++) {
                    if (sudoku[fila][columna] == 0 && sudoku[i][j] == valor) {
                        regionCorrecta = false;
                        System.out.println("Error !!... el número " + valor + " ya existe en la cuadrícula superior derecha ");
                        break;
                    }
                }
            }
        }

        // Cuarta cadrícula medio izquierda

        if (fila >= 3 && fila <= 5 && columna <= 2) {
            for (int i = 3; i <= 5; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (sudoku[fila][columna] == 0 && sudoku[i][j] == valor) {
                        regionCorrecta = false;
                        System.out.println("Error !!... el número " + valor + " ya existe en la cuadrícula medio izquierda ");
                        break;
                    }
                }
            }
        }

        // Quinta cuadrícula medio mitad

        if (fila >= 3 && fila <= 5 && columna >= 3 && columna <= 5) {
            for (int i = 3; i <= 5; i++) {
                for (int j = 3; j <= 5; j++) {
                    if (sudoku[fila][columna] == 0 && sudoku[i][j] == valor) {
                        regionCorrecta = false;
                        System.out.println("Error !!... el número " + valor + " ya existe en la cuadrícula medio mitad ");
                        break;
                    }
                }
            }
        }

        // Sexta cuadrícula medio derecha

        if (fila >= 3 && fila <= 5 && columna >= 6 && columna <= 8) {
            for (int i = 3; i <= 5; i++) {
                for (int j = 6; j <= 8; j++) {
                    if (sudoku[fila][columna] == 0 && sudoku[i][j] == valor) {
                        regionCorrecta = false;
                        System.out.println("Error !!... el número " + valor + " ya existe en la cuadrícula medio derecha");
                        break;
                    }
                }
            }
        }

        // Séptima cuadrícula inferior izquierda

        if (fila >= 6 && fila <= 8 && columna <= 2) {
            for (int i = 6; i <= 8; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (sudoku[fila][columna] == 0 && sudoku[i][j] == valor) {
                        regionCorrecta = false;
                        System.out.println("Error !!... el número " + valor + " ya existe en la cuadrícula inferior izquierda");
                        break;
                    }
                }
            }
        }

        // Octava cuadrícula inferior mitad

        if (fila >= 6 && fila <= 8 && columna >= 3 && columna <= 5) {
            for (int i = 6; i <= 8; i++) {
                for (int j = 3; j <= 5; j++) {
                    if (sudoku[fila][columna] == 0 && sudoku[i][j] == valor) {
                        regionCorrecta = false;
                        System.out.println("Error !!... el número " + valor + " ya existe en la cuadrícula inferior mitad");
                        visualizarTablero();
                        break;
                    }
                }
            }
        }

        // Novena cuadrícula inferior derecha

        if (fila >= 6 && fila <= 8 && columna >= 6 && columna <= 8) {
            for (int i = 6; i <= 8; i++) {
                for (int j = 6; j <= 8; j++) {
                    if (sudoku[fila][columna] == 0 && sudoku[i][j] == valor) {
                        regionCorrecta = false;
                        System.out.println("Error !!... el número " + valor + " ya existe en la cuadrícula inferior derecha");
                        break;
                    }
                }
            }
        }

        return regionCorrecta;

    }


    public static void visualizarTablero() {

        System.out.println();
        System.out.println(red + "┏━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┓" + reset);

        for (int i = 0; i < sudoku.length; i++) {

            if (i == 3) {
                System.out.print(red + "┣" + reset);
                System.out.print(blue + "━━━━━━━━━━╋━━━━━━━━━━╋━━━━━━━━━━" + reset);
                System.out.println(red + "┫" + reset);
            }
            if (i == 6) {
                System.out.print(red + "┣" + reset);
                System.out.print(blue + "━━━━━━━━━━╋━━━━━━━━━━╋━━━━━━━━━━" + reset);
                System.out.println(red + "┫" + reset);

            }
            System.out.print(red + "┃" + reset);

            for (int j = 0; j < sudoku.length; j++) {

                // Meto el valor en la fila y columna que le he pedido al usuario, siempre que la casilla este vacía, osea ==0

                if (sudoku[i][j] == 0) {
                    System.out.format(green + "%3d" + reset, sudoku[i][j]);
                } else {
                    System.out.format("%3d", sudoku[i][j]);
                }

                if (j == 2) {
                    System.out.print(blue + " ┃" + reset);
                }
                if (j == 5) {
                    System.out.print(blue + " ┃" + reset);
                }


            }

            System.out.format("%3s", red + " ┃\n" + reset);

        }

        System.out.print(red + "┗━━━━━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━┛" + reset);
        System.out.println();

    }
}

