package com.ikasgela;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;

public class Main {

    static String red = "\033[31m";
    static String reset = "\u001B[0m";
    static String blue = "\033[34m";
    static String green= "\u001B[32m";

    static int jugador;
    static int depositoDerecha;
    static int depositoIzquierda;
    static boolean turno = true; // Esta variable la saco fuera del bucle , para que no se inicialize. Solo interesa el valor anterior dentro del bucle
    static boolean ceroUno = true;

    static int[][] oware = {
            {4, 4, 4, 4, 4, 4},
            {4, 4, 4, 4, 4, 4},
    };


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println();
        System.out.println(green +
                "*** DESCRIPCION ***\n\n" +
                "\tEl 'oware' es un juego de estrategia para dos personas.\n" +
                "\tPertenece a la familia de los juegos de mancala, denominados de cuenta-y-captura por la peculiaridad del desarrollo de una partida.\n" +
                "\tConsiste en la distribución de las fichas del juego por el tablero y la posterior supresión de las mismas del tablero cuando se cumplen ciertas condiciones.\n\n" +
                "*** REGLAS ***\n\n" +
                "\t- Se requiere un tablero y cuarenta y ocho fichas, llamadas semillas.\n" +
                "\t- El tablero estará compuesto por dos hileras de seis hoyos, situadas una enfrente de la otra.\n"+
                "\t- Dos hoyos más grandes a ambos lados tablero serviran para guardar las semillas que los jugadores capturen en el desarrollo de la partida.\n"+
                "\t- La hilera inferior pertenece al jugador que mueve primero, llamado SUR, y la hilera superior al segundo jugador o NORTE.\n"+
                "\t- En la posición inicial, cada hoyo excepto los dos más grandes (hoyos de captura), contiene exactamente cuatro semillas.\n" +
                "\t- Los jugadores realizan movimientos en turnos alternativos hasta que alguno de ellos ha capturado más de 24 semillas.\n"+
                "\t- En el caso de que ambos jugadores hayan capturado el mismo número de semillas al finalizar, el resultado es EMPATE.\n"+
                "\t- Cada movimiento del juego se hace en tres fases: la recolección, la siembra y la captura.\n\n"+
                "\t\tRECOLECCIÓN:\n"+
                "\t\tEl jugador al cual le toca mover escoge uno de los hoyos que le pertenecen y recoge todas las semillas que contiene, dejando aquel hoyo vacío.\n\n"+
                "\t\tSIEMBRA:\n"+
                "\t\tDurante la siembra, el jugador reparte por el tablero, en sentido antihorario, las semillas recogidas en la recolección; dejando una semilla en cada hoyo propio y del jugador contrario hasta que las haya distribuido todas. El jugador nunca sembrará semillas en los hoyos de captura.\n"+
                "\t\tAl finalizar la siembra el hoyo del cual el jugador ha recogido las semillas estará vacío.\n"+
                "\t\tSe puede dar el caso que el jugador siembre doce o más semillas. Cuando esto suceda el jugador las sembrará dando una o más vueltas al tablero, dejando una semilla en cada uno de los hoyos en cada vuelta, pero SALTANDO siempre el hoyo del cual se ha hecho la recolección.\n\n"+
                "\t\tCAPTURA DE SEMILLAS:\n"+
                "\t\tSi al finalizar la siembra la última semilla se ha dejado en uno de los hoyos que pertenecen al adversario y después de dejar la semilla este hoyo contiene exactamente dos o tres semillas, el jugador las capturará. Tomando todas las semillas y depositándolas en su hoyo de capturas.\n"+
                "\t\tSi después de capturar las semillas de un hoyo resulta que los hoyos anteriores inmediatamente a su derecha también contiene dos o tres semillas, el jugador también capturará las semillas de aquel/los hoyo/s.\n"+
                "\t\tTener en cuenta que los jugadores sólo pueden capturar semillas que se encuentren en hoyos de su adversario, nunca de sus propios hoyos.\n"+
                "\t\tUn jugador no puede capturar NUNCA todas las semillas del adversario. Si el jugador hace un movimiento que después de las capturas dejaría todos los hoyos del adversario vacíos, aquel jugador hará la siembra normalmente pero no capturará ninguna semilla.\n\n"+
                "*** FINALIZACIÖN DE LA PARTIDA ***\n\n"+
                "\t-El juego se termina cuando uno de los dos jugadores ha capturado más de 24 semillas o cuando los dos han capturado 24.\n"+
                "\t-Puede suceder que en el turno de un jugador este no pueda hacer ningún movimiento legal, en este supuesto, cada jugador capturará las semillas que se encuentren en los hoyos de su lado del tablero y el juego finalizará.\n"+
                "\t-Una situación especial es cuando el juego entra en un ciclo, de manera que las mismas posiciones y movimientos se irían repitiendo indefinidamente, se acuerda quedar en TABLAS"+

            reset);

        System.out.println(blue + "\nEmpezamos a jugar al 'OWARE', este es el tablero inicial, cada casilla tiene 4 semillas, empieza el jugador 'SUR'\n" + reset);

        visualizarTablero();

        int casilla;
        int posicion;
        int contador;
        int contadorTotal; // Esta variable la uso solamente para sembrar desde arriba y para situar la variable 'contador' dentro de los rangos co 'contadorTotal' igual que en sembrar desde abajo
        boolean casillaCorrecta;
        boolean correcto = true;

        do {

            // Actualizamos las variables

            casilla = 0;
            posicion = 0;
            contador = 0;
            contadorTotal = 0; // Esta variable la uso solamente para sembrar desde arriba y para situar la variable 'contador' dentro de los rangos co 'contadorTotal' igual que en sembrar desde abajo

            System.out.println();


            do {
                correcto = true;

                try {

                    do {
                        casillaCorrecta = true;

                        /* operación ternaria (boolean) --> (turno ? true : false) ;
                           jugador NORTE --> jugador = 0
                           jugador SUR   --> jugador = 1

                         */


                        System.out.print("Tu turno jugador " + (turno ? "SUR --> Elige casilla (A/B/C/D/E/F) o 's' para salir: " : "NORTE --> Elige casilla (a/b/c/d/e/f) o 's' para salir: "));
                        jugador = (ceroUno ? 1 : 0); // Cambio el valor 'int' del jugador en función si:

                        casilla = br.readLine().charAt(0);

                        if (jugador == 1 && casilla != 'A' && casilla != 'B' && casilla != 'C' && casilla != 'D' && casilla != 'E' && casilla != 'F' && casilla != 's') {
                            System.out.println(red + "Error !! ...  Elige una casilla correcta --> (A/B/C/D/E/F) !! " + reset);
                        }

                        if (jugador == 0 && casilla != 'a' && casilla != 'b' && casilla != 'c' && casilla != 'd' && casilla != 'e' && casilla != 'f' && casilla != 's') {
                            System.out.println(red + "Error !! ... Elige una casilla  correcta --> (a/b/c/d/e/f) !!" + reset);
                        }


                        if (jugador == 1 && casilla == 'A') {
                            if (oware[1][0] != 0) {
                                contador = posicion + valorCasilla(casilla);
                                // A = oware[1][0];

                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }

                        if (jugador == 1 && casilla == 'B') {
                            if (oware[1][1] != 0) {
                                posicion = 1;
                                contador = posicion + valorCasilla(casilla);
                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }


                        if (jugador == 1 && casilla == 'C') {
                            if (oware[1][2] != 0) {
                                posicion = 2;
                                contador = posicion + valorCasilla(casilla);
                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }
                        if (jugador == 1 && casilla == 'D') {
                            if (oware[1][3] != 0) {
                                posicion = 3;
                                contador = posicion + valorCasilla(casilla);
                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }
                        if (jugador == 1 && casilla == 'E') {
                            if (oware[1][4] != 0) {
                                posicion = 4;
                                contador = posicion + valorCasilla(casilla);
                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }
                        if (jugador == 1 && casilla == 'F') {
                            if (oware[1][5] != 0) {
                                posicion = 5;
                                contador = posicion + valorCasilla(casilla);
                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset); // Convertimos 'casilla' a char --> (char)casilla
                                casillaCorrecta = false;
                            }
                            System.out.println();
                        }
                        if (jugador == 0 && casilla == 'a') {
                            if (oware[0][5] != 0) {
                                posicion = 5;
                                contadorTotal = oware[0][5];
                                if (valorCasilla(casilla) > 5) {
                                    contador = posicion + valorCasilla(casilla);
                                } else {
                                    contador = posicion - valorCasilla(casilla);
                                }

                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }
                        if (jugador == 0 && casilla == 'b') {
                            if (oware[0][4] != 0) {
                                posicion = 4;
                                contadorTotal = oware[0][4] + 1;
                                if (valorCasilla(casilla) > 4) {
                                    contador = posicion + valorCasilla(casilla);
                                } else {
                                    contador = posicion - valorCasilla(casilla);
                                }

                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }
                        if (jugador == 0 && casilla == 'c') {
                            if (oware[0][3] != 0) {
                                posicion = 3;
                                contadorTotal = oware[0][3] + 2;
                                if (valorCasilla(casilla) > 3) {
                                    contador = posicion + valorCasilla(casilla);
                                } else {
                                    contador = posicion - valorCasilla(casilla);
                                }

                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }
                        if (jugador == 0 && casilla == 'd') {
                            if (oware[0][2] != 0) {
                                posicion = 2;
                                contadorTotal = oware[0][2] + 3;
                                if (valorCasilla(casilla) > 2) {
                                    contador = posicion + valorCasilla(casilla);
                                } else {
                                    contador = posicion - valorCasilla(casilla);
                                }

                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }
                        if (jugador == 0 && casilla == 'e') {
                            if (oware[0][1] != 0) {
                                posicion = 1;
                                contadorTotal = oware[0][1] + 4;
                                if (valorCasilla(casilla) > 1) {
                                    contador = posicion + valorCasilla(casilla);
                                } else {
                                    contador = posicion - valorCasilla(casilla);
                                }


                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }
                        if (jugador == 0 && casilla == 'f') {
                            if (oware[0][0] != 0) {
                                contadorTotal = oware[0][0] + 5;
                                //posicion = 0; --> ya está inicializado con este valor
                                contador = posicion + valorCasilla(casilla);
                            } else {
                                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                                casillaCorrecta = false;
                            }
                        }


                    } while ((jugador == 1 && casilla != 'A' && casilla != 'B' && casilla != 'C' && casilla != 'D' && casilla != 'E' && casilla != 'F' && casilla != 's') || (jugador == 0 && casilla != 'a' && casilla != 'b' && casilla != 'c' && casilla != 'd' && casilla != 'e' && casilla != 'f' && casilla != 's') || !casillaCorrecta);


                    turno = !turno; // Cambiamos el mensaje de pantalla entre el jugador NORTE y el jugador SUR
                    ceroUno = !ceroUno;  // Cambiamos el valor del jugador NORTE --> 0 y el jugador SUR --> 1

                } catch (Exception ex) {
                    System.out.println(red + "Error !! ...  Elige una casilla correcta --> (A/B/C/D/E/F) ó (a/b/c/d/e/f) !! " + reset);
                    correcto = false;
                }

            } while (!correcto);

            /*
            // Variables de control
            System.out.println();
            System.out.println();
            System.out.println("El valor de " + (char) casilla + " es: " + (contador - posicion));
            System.out.println("La posicion de " + (char) casilla + " es: " + posicion);
            System.out.println("El contador (posición + valor " + (char) casilla + " ) es: " + contador);
            System.out.println("El resto (contador-5) es: " + (contador - 5));
            System.out.println("La casilla " + (char) casilla + " en ASCII es : " + casilla); // --> Me sale el dato en ASCII
            System.out.println("jugador: " + jugador);
            System.out.println("contadorTotal " + contadorTotal);
            System.out.println();

            */


            if (casilla != 's') {
                System.out.println("Así queda el tablero después de la siembra desde la casilla '" + (char) casilla + "' y sembrar " + valorCasilla(casilla) + " semillas: ");
                System.out.println();

                if (jugador == 1) { // Jugador 1 --> SUR
                    sembrarDesdeAbajo(posicion, contador, contadorTotal);
                } else { // Jugador 0 --> NORTE
                    sembrarDesdeArriba(posicion, contador, contadorTotal);
                }
                visualizarTablero();
            }


        } while (depositoDerecha < 24 && depositoIzquierda < 24 && casilla != 's');


        if (depositoDerecha > depositoIzquierda && casilla != 's') {
            System.out.println();
            System.out.println(blue + "Has ganado jugador SUR !! \uD83D\uDC4F \uD83D\uDC4F" + reset);
        } else if (depositoDerecha == depositoIzquierda && casilla != 's') {
            System.out.println();
            System.out.println(blue + "Empate !!" + reset);
        } else if (casilla == 's') {
            System.out.println();
            System.out.println(blue + "Hasta luego Lucas !!" + reset);
        } else {
            System.out.println();
            System.out.println(blue + "Has ganado jugador NORTE !! \uD83D\uDC4F \uD83D\uDC4F" + reset);
        }

    }

    // Al final no lo he metido en el 'main' porque no he conseguido que me haga bien la restricción
    public static boolean comprobarFilaAdversario(int posicion) {
        boolean filaAdversariocorrecta = true;
        boolean filaArriba = false;
        boolean filaAbajo = false;
        /*
        Parto de que siempre se puede sembrar en el adversario, excepto cuando se llega a una situación límite,
        donde el adversario tiene todas las casillas a 0 y solo puede sembrar desde los extremos 'F' o'f' (entre 1 y 6 semillas),
        lo que luego le imposibilitaría mover en la siguiente jugada porque estaría con todas las casillas a 0.

         */
        // Jugador 0
        for (int i = 0; i <= 4; i++) {
            if (jugador == 0 && oware[1][i] == 0 && oware[0][5] <= 6) {
                filaAbajo = true;
            }
        }
        if (jugador == 0 && oware[0][posicion] <= 5 - posicion) {
            filaArriba = true;
        }

        // Jugador 1
        for (int i = 1; i <= 5; i++) {
            if (jugador == 1 && oware[0][i] == 0 && oware[0][0] <= 6) {
                filaArriba = true;
            }
        }

        if (jugador == 1 && oware[1][posicion] <= 5 - posicion) {
            filaAbajo = true;

        }

        // Comprobamos que si se cumplen que son 'true', osea que dejas al adversario en la siguiente jugada con todos los hoyos a cero
        if (filaArriba && filaAbajo) {
            filaAdversariocorrecta = false;
        }

        return filaAdversariocorrecta;
    }

    public static int valorCasilla(int casilla) {

        int valor = 0;

        switch (casilla) {
            case 'A':
                valor = oware[1][0];
                break;
            case 'B':
                valor = oware[1][1];
                break;
            case 'C':
                valor = oware[1][2];
                break;
            case 'D':
                valor = oware[1][3];
                break;
            case 'E':
                valor = oware[1][4];
                break;
            case 'F':
                valor = oware[1][5];
                break;
            case 'f':
                valor = oware[0][0];
                break;
            case 'e':
                valor = oware[0][1];
                break;
            case 'd':
                valor = oware[0][2];
                break;
            case 'c':
                valor = oware[0][3];
                break;
            case 'b':
                valor = oware[0][4];
                break;
            case 'a':
                valor = oware[0][5];
                break;
        }
        return valor;

    }

    // Esta función, no la utilizo al final
    public static int elegirCasilla(int casilla, int posicion, int contador, int contadorTotal) {
        boolean casillaCorrecta = true;

        if (jugador == 1 && casilla != 'A' && casilla != 'B' && casilla != 'C' && casilla != 'D' && casilla != 'E' && casilla != 'F') {
            System.out.println(red + "Error !! ...  Elige una casilla correcta --> (A/B/C/D/E/F) !! " + reset);
        }
        if (jugador == 0 && casilla != 'a' && casilla != 'b' && casilla != 'c' && casilla != 'd' && casilla != 'e' && casilla != 'f') {
            System.out.println(red + "Error !! ... Elige una casilla  correcta --> (a/b/c/d/e/f) !!" + reset);
        }

        if (jugador == 1 && casilla == 'A') {
            if (oware[1][0] != 0) {
                contador = posicion + valorCasilla(casilla);
                // A = oware[1][0];


            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }
        if (jugador == 1 && casilla == 'B') {
            if (oware[1][1] != 0) {
                posicion = 1;
                contador = posicion + valorCasilla(casilla);
                System.out.println("prueba" + contador);
            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }

        if (jugador == 1 && casilla == 'C') {
            if (oware[1][2] != 0) {
                posicion = 2;
                contador = posicion + valorCasilla(casilla);
            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }
        if (jugador == 1 && casilla == 'D') {
            if (oware[1][3] != 0) {
                posicion = 3;
                contador = posicion + valorCasilla(casilla);
            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }
        if (jugador == 1 && casilla == 'E') {
            if (oware[1][4] != 0) {
                posicion = 4;
                contador = posicion + valorCasilla(casilla);
            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }
        if (jugador == 1 && casilla == 'F') {
            if (oware[1][5] != 0) {
                posicion = 5;
                contador = posicion + valorCasilla(casilla);
            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset); // Convertimos 'casilla' a char --> (char)casilla
                casillaCorrecta = false;
            }
            System.out.println();
        }
        if (jugador == 0 && casilla == 'a') {
            if (oware[0][5] != 0) {
                posicion = 5;
                contadorTotal = oware[0][5];
                if (valorCasilla(casilla) > 5) {
                    contador = posicion + valorCasilla(casilla);
                } else {
                    contador = posicion - valorCasilla(casilla);
                }

            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }
        if (jugador == 0 && casilla == 'b') {
            if (oware[0][4] != 0) {
                posicion = 4;
                contadorTotal = oware[0][4] + 1;
                if (valorCasilla(casilla) > 4) {
                    contador = posicion + valorCasilla(casilla);
                } else {
                    contador = posicion - valorCasilla(casilla);
                }

            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }
        if (jugador == 0 && casilla == 'c') {
            if (oware[0][3] != 0) {
                posicion = 3;
                contadorTotal = oware[0][3] + 2;
                if (valorCasilla(casilla) > 3) {
                    contador = posicion + valorCasilla(casilla);
                } else {
                    contador = posicion - valorCasilla(casilla);
                }

            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }
        if (jugador == 0 && casilla == 'd') {
            if (oware[0][2] != 0) {
                posicion = 2;
                contadorTotal = oware[0][2] + 3;
                if (valorCasilla(casilla) > 2) {
                    contador = posicion + valorCasilla(casilla);
                } else {
                    contador = posicion - valorCasilla(casilla);
                }

            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }
        if (jugador == 0 && casilla == 'e') {
            if (oware[0][1] != 0) {
                posicion = 1;
                contadorTotal = oware[0][1] + 4;
                if (valorCasilla(casilla) > 1) {
                    contador = posicion + valorCasilla(casilla);
                } else {
                    contador = posicion - valorCasilla(casilla);
                }


            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }
        if (jugador == 0 && casilla == 'f') {
            if (oware[0][0] != 0) {
                contadorTotal = oware[0][0] + 5;
                //posicion = 0; --> ya está inicializado con este valor
                contador = posicion + valorCasilla(casilla);
            } else {
                System.out.print(red + "La casilla " + (char) casilla + " está vacia !!  ... prueba con otra\n" + reset);
                casillaCorrecta = false;
            }
        }

        return contador;


    }


    public static void capturas(int contador, int jugador, int posicion, int contadorTotal) {

        // jugador 1 --> jugador SUR
        // jugador 0 --> jugador NORTE

        int posicionFinal = 0;

        if (jugador == 1) { //jugador 1 SUR

            if (contador >= 6 && contador <= 11) {
                posicionFinal = 11 - contador;
            }
            if (contador >= 17 && contador <= 23) { // Aquí el contador ya ha dado 1 VUELTA, el contador ha sumado 1 posicion más, luego hay que restarla
                posicionFinal = 23 - contador;
            }
            if (contador >= 28 && contador <= 35) { // Aquí el contador ya ha dado 2 VUELTAS,el contador ha sumado 2 posiciones más, luego hay que restarlas
                posicionFinal = 35 - contador;
            }
        } else { // jugador 0 NORTE


            if (contadorTotal >= 6 && contadorTotal <= 11) {
                posicionFinal = contadorTotal - 6;
            }
            if (contadorTotal >= 17 && contadorTotal <= 22) {
                posicionFinal = contadorTotal - 17;
            }
            if (contadorTotal >= 28 && contadorTotal <= 33) {
                posicionFinal = contadorTotal - 28;

            }

        }

        /*
        Hay que comprobar que toda la fila no está lleno de 0, 2 o 3 semillas, para después de hacer la captura no dejar a 0 todos los hoyos de tu adversario
        que es una de los conciciones del juego, solo se hace la siembra pero no se captura.
         */


        boolean ceros = true; // Parto de que todos son ceros
        boolean dosTres = true; // Parto de que son todos 2 ó 3

        // COMPRUEBO LOS 0
        if (jugador == 1) { // jugador 1 SUR, compruebo si hay ceros en la parte libre que no se ha sembrado, que no son ceros
            for (int i = 0; i < posicionFinal; i++) {

                if (oware[0][i] != 0) {
                    ceros = false; // Aquí encuentra el primer valor que sea diferente de cero
                }
            }
        } else { // jugador 0 NORTE., compruebo en la parte libre del array que no se ha sembrado, que no son ceros
            for (int i = 5; i > posicionFinal; i--) {

                if (oware[1][i] != 0) {
                    ceros = false; // Aquí encuentra el primer valor que sea diferente de cero
                }
            }
        }

        // COMPRUEBO LOS 2 y 3
        if (jugador == 1) { // jugador 1 SUR
            for (int i = posicionFinal; i <= 5; i++) {
                if (oware[0][i] != 2 && oware[0][i] != 3) { // Es mejor poner '&&' para que el número no sea 2 Y que no sea 3. Con '||' no sale, porque solo es necesario que se cumpla una condición de las 2.
                    // Por regla general siempre que haya una expresión del tipo '!=' usar --> '&&' así le obligas a que se cumpla las 2 condiciones.
                    dosTres = false; // Todos los número tienen que ser 2 ó 3
                }
            }
        } else { // jugador 0 NORTE
            for (int i = posicionFinal; i >= 0; i--) {
                if (oware[1][i] != 2 && oware[1][i] != 3) {
                    dosTres = false;
                }
            }
        }

        if (ceros && dosTres) { // Si se cumplen las 2 condiciones, que sean ciertas (true), es que está lleno de ceros y lleno de 2 y 3, entonces se quedarían todos los hoyos vacíos al hace rlas capturas
            System.out.println(red + "No puedes hacer las capturas y dejar a tu adversario con todos los hoyos vacíos, solo SEMBRAR\n\n" + reset);

        } else { // Si no hay ceros, se pueden capturar

            dosTres = true; // Parto de que son todos 2 ó 3

            if (jugador == 1) { // jugador 1 SUR
                for (int i = posicionFinal; i <= 5; i++) {
                    if (oware[0][i] != 2 && oware[0][i] != 3) {
                        dosTres = false; // el primer valor que se encuentre distinto de 2 ó 3
                    }
                    if (dosTres) {
                        depositoDerecha = depositoDerecha + oware[0][i];
                        oware[0][i] = 0;
                    }
                }
            } else { // jugador 0 NORTE
                for (int i = posicionFinal; i >= 0; i--) {
                    if (oware[1][i] != 2 && oware[1][i] != 3) {
                        dosTres = false;
                    }
                    if (dosTres) {
                        depositoIzquierda = depositoIzquierda + oware[1][i];
                        oware[1][i] = 0;
                    }
                }
            }
        }

        /*

        // Esta es otra solución, más larga...

        do {
            capturaCorrecta = false;

            if ((11 - contador + avanzaUno) <= 5 && (oware[0][11 - contador + avanzaUno] == 2 || oware[0][11 - contador + avanzaUno] == 3)) {

                depositoDerecha = depositoDerecha + oware[0][11 - contador + avanzaUno];
                oware[0][11 - contador + avanzaUno] = 0;
                capturaCorrecta = true;
                avanzaUno++;
            }

        } while (capturaCorrecta);

        //Estas son variables de control, después de realizar las capturas:



        System.out.println();
        System.out.println("ceros " + ceros);
        System.out.println("dosTres " + dosTres);
        System.out.println("posición final " + posicionFinal);
        System.out.println("jugador: " + jugador);
        System.out.println("posicion" + posicion);
        System.out.println("contador " + contador);
        System.out.println("contadorTotal " + contadorTotal);

         */

    }

    // Esta función, no la utilizo al final
    public static void sembrar(int posicion, int contador, int contadorTotal) {


        int valorCasilla = contador - posicion;
        jugador = (ceroUno ? 1 : 0); // Cambio el valor 'int' del jugador en función si:
        int contrarioJugador = (ceroUno ? 0 : 1);

        System.out.println(jugador);
        System.out.println(contrarioJugador);

        // Sembrar solo fila de arriba
        if (contadorTotal <= 5 && contadorTotal != 0) {
            for (int i = posicion; i >= contadorTotal; i--) {
                oware[jugador][i] = oware[jugador][i] + 1; // le sumo 1 a la siguiente posición
                oware[jugador][posicion] = 0;
            }
        }

        // Sembrar fila de arriba y abajo
        if (contadorTotal >= 6 && contadorTotal <= 11) {
            // Fila de arriba, la propia , la relleno entera
            for (int i = posicion; i <= 5; i++) {
                oware[jugador][i] = oware[jugador][i] + 1;
                oware[jugador][posicion] = 0;
            }
            // FILA DEL ADVERSARIO, aquí pongo la condición de las casillas que tengan 2 o 3 semillas, que las sume y lleve al deposito suyo
            for (int i = 11 - contadorTotal; i <= 5; i++) {
                oware[contrarioJugador][i] = oware[contrarioJugador][i] + 1;
            }
            capturas(contador, jugador, posicion, contadorTotal); // Comprobamos las capturas
            System.out.println();
        }
    }


    public static void sembrarDesdeArriba(int posicion, int contador, int contadorTotal) {

        int valorCasilla = contador - posicion;

        // Sembrar solo fila de arriba
        if (contadorTotal <= 5 && contadorTotal != 0) {
            for (int i = posicion; i >= contador; i--) {
                oware[0][i] = oware[0][i] + 1; // le sumo 1 a la siguiente posición
                oware[0][posicion] = 0;
            }
        }
        // Sembrar fila de arriba y abajo
        if (contadorTotal >= 6 && contadorTotal <= 11) {
            // Fila de arriba, la propia , la relleno entera
            for (int i = posicion; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                oware[0][posicion] = 0;
            }
            // FILA DEL ADVERSARIO, aquí pongo la condición de las casillas que tengan 2 o 3 semillas, que las sume y lleve al deposito suyo
            for (int i = 0; i <= contadorTotal - 6; i++) { // valorCasilla - (posicion + 1)
                oware[1][i] = oware[1][i] + 1;
            }
            capturas(contador, jugador, posicion, contadorTotal); // Comprobamos las capturas

        }
        if (contadorTotal >= 12 && contadorTotal <= 16) {
            // Fila de arriba, la propia, la relleno entera
            for (int i = posicion; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                oware[0][posicion] = 0;
            }
            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> 1 VUELTA
            for (int i = 5; i >= 17 - contadorTotal; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) { // Al hacer la suma de 1 en todas las casillas, la que tenía 0, se queda con 1, por eso lo he igualado a 1, porque anteriormente era 0
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal' para que no la afecte al siguiente 'if'
        }

        if (contadorTotal >= 17 && contadorTotal <= 22) {
            // Fila de arriba, la propia, la relleno entera
            for (int i = posicion; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                oware[0][posicion] = 0;
            }
            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  1 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) { // Al hacer la suma de 1 en todas las casillas, la que tenía 0, se queda con 1, por eso lo he igualado a 1, porque anteriormente era 0
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1;// Resto 1 al 'contadorTotal' para que no la afecte al siguiente 'contadorTotal'

            // FILA DEL ADVERSARIO, aquí pongo la condición de las casillas que tengan 2 o 3 semillas, que las sume y lleve al deposito suyo
            for (int i = 0; i <= contadorTotal - 17; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            capturas(contador, jugador, posicion, contadorTotal); // Comprobamos las capturas
        }

        if (contadorTotal >= 23 && contadorTotal <= 27) {
            // Fila de arriba, la propia, la relleno entera
            for (int i = posicion; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                oware[0][posicion] = 0;
            }
            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  1 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1;// Resto 1 al 'contadorTotal'

            //Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }

            // Fila de arriba --> La relleno hasta donde llegue,  2 VUELTA
            for (int i = 5; i >= 28 - contadorTotal; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) { // Al hacer la suma de 1 en todas las casillas, la que tenía 0, se queda con 1, por eso lo he igualado a 1, porque anteriormente era 0
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1;// Resto 1 al 'contadorTotal'
        }
        if (contadorTotal >= 28 && contadorTotal <= 33) {
            // Fila de arriba, la propia, la relleno entera
            for (int i = posicion; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                oware[0][posicion] = 0;
            }
            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  1 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1;// Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  2 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'

            // FILA DEL ADVERSARIO, aquí pongo la condición de las casillas que tengan 2 o 3 semillas, que las sume y lleve al deposito suyo
            for (int i = 0; i <= contadorTotal - 28; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            capturas(contador, jugador, posicion, contadorTotal); // Comprobamos las capturas
        }
        if (contadorTotal >= 34 && contadorTotal <= 38) {
            // Fila de arriba, la propia, la relleno entera
            for (int i = posicion; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                oware[0][posicion] = 0;
            }
            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  1 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1;// Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  2 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno hasta donde llegue el contadorTotal,  3 VUELTA
            for (int i = 5; i >= 39 - contadorTotal; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'

        }
        if (contadorTotal >= 39 && contadorTotal <= 44) {
            // Fila de arriba, la propia, la relleno entera
            for (int i = posicion; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                oware[0][posicion] = 0;
            }
            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  1 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1;// Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  2 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno hasta donde llegue el contadorTotal,  3 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'

            // FILA DEL ADVERSARIO, IMPORTANTE --> Aquí no hace falta comprobar si hay 2 o 3 semillas, porque al dar mas de tres vueltas, siempre va haber más de 3 semillas
            // Aún así al encontrarse más de 3 semillas la función 'capturas' lo va a tener en cuenta y no va hacer ninguna captura

            for (int i = 0; i <= contadorTotal - 39; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            capturas(contador, jugador, posicion, contadorTotal); // Comprobamos las capturas
        }
        if (contadorTotal >= 45 && contadorTotal <= 49 && valorCasilla <= 48) {
            // Fila de arriba, la propia, la relleno entera
            for (int i = posicion; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                oware[0][posicion] = 0;
            }
            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  1 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1;// Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  2 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  3 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno hasta donde llegue el 'contadorTotal',  4 VUELTA
            for (int i = 5; i >= 50 - contadorTotal; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'
        }
        if (contadorTotal >= 50 && contadorTotal <= 53 && valorCasilla <= 48) {
            // Fila de arriba, la propia, la relleno entera
            for (int i = posicion; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                oware[0][posicion] = 0;
            }
            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  1 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1;// Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  2 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  3 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'

            // Fila de abajo , la rellena entera
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            // Fila de arriba --> La relleno entera,  4 VUELTA
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
                if (oware[0][posicion] == 1) {
                    contadorTotal = contadorTotal + 1;
                    oware[0][i] = 0;
                }
            }
            contadorTotal = contadorTotal - 1; // Resto 1 al 'contadorTotal'

            // FILA DEL ADVERSARIO, IMPORTANTE --> Aquí no hace falta comprobar si hay 2 o 3 semillas, porque al dar mas de tres vueltas, siempre va haber más de 3 semillas
            // Aún así al encontrarse más de 3 semillas la función 'capturas' lo va a tener en cuenta y no va hacer ninguna captura

            for (int i = 0; i <= contadorTotal - 50; i++) {
                oware[1][i] = oware[1][i] + 1;
            }
            capturas(contador, jugador, posicion, contadorTotal); // Comprobamos las capturas
            System.out.println();
        }
        if (valorCasilla > 48) {
            System.out.println(red + "Error !! ... una casilla no puede albergar más de 48 semillas !! \n" + reset);
        }
    }


    public static void sembrarDesdeAbajo(int posicion, int contador, int contadorTotal) {

        /* Se van depositando semilas de 1 en una en sentido antihorario,
        hasta quedarte  sin ellas y luego recoges todas las de tu contrario que sean 2 o 3
        y las depositas en tu deposito
         */

        // Sembrar solo fila de abajo
        if (contador <= 5 && contador != 0) {
            for (int i = posicion; i <= contador; i++) {
                oware[1][i] = oware[1][i] + 1; // le sumo 1 a la siguiente posición
                oware[1][posicion] = 0;
            }
        }

        // Sembrar fila de abajo y arriba --> LLEGO AL CAMPO DEL ADVERSARIO

        if (contador > 5 && contador <= 11) {
            // Fila de abajo, la propia
            for (int i = posicion; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1; // le sumo 1 a la siguiente posición
                oware[1][posicion] = 0;
            }
            // FILA DEL ADVERSARIO, aquí pongo la condición de las casillas que tengan 2 o 3 semillas, que las sume y lleve al deposito suyo
            for (int i = 5; i >= 11 - contador; i--) {
                oware[0][i] = oware[0][i] + 1; // le sumo 1 a la siguiente posición
            }
            capturas(contador, jugador, posicion, contadorTotal); // Comprobamos las capturas

        }

        if (contador >= 12 && contador <= 16) {
            // Primera fila, la de abajo
            for (int i = posicion; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                oware[1][posicion] = 0;
            }
            // Segunda fila adversario, la de arriba, la relleno toda
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            // Tercera fila, la de abajo, la propia --> 1 vuelta entera contador
            for (int i = 0; i <= contador - 12; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) { // Al hacer la suma de 1 en todas las casillas, la que tenía 0, se queda con 1, por eso lo he igualado a 1, porque anteriormente era 0
                    contador = contador + 1;
                    oware[1][i] = 0;
                }
            }
            contador = contador - 1; // Le resto al contador 1 posición, de la vuelta que ha dado.
        }

        if (contador >= 17 && contador <= 22) {
            // Primera fila, la de abajo, la relleno entera
            for (int i = posicion; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                oware[1][posicion] = 0;
            }
            // Segunda fila adversario, la de arriba, la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }

            // Tercera fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 1 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }

            // Cuarta fila, la de arriba , la del adversario, aqui realizo las capturas si las hubiese
            for (int i = 5; i >= 23 - contador; i--) {
                oware[0][i] = oware[0][i] + 1;
            }

            capturas(contador, jugador, posicion, contadorTotal); // Comprobamos las capturas
            contador = contador - 1;

        }

        if (contador >= 23 && contador <= 27) {
            // Primera fila, la de abajo, la relleno entera
            for (int i = posicion; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                oware[1][posicion] = 0;
            }
            // Segunda fila adversario, la de arriba, la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;

            }
            // Tercera fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 1 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }

            // Cuarta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            // Quinta fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 2 vuelta entera contador
            for (int i = 0; i <= 5 - (29 - contador); i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            contador = contador - 2;

        }
        if (contador >= 28 && contador <= 33) {
            // Primera fila, la de abajo, la relleno entera
            for (int i = posicion; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                oware[1][posicion] = 0;
            }
            // Segunda fila adversario, la de arriba, la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }

            // Tercera fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 1 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }

            // Cuarta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            // Quinta fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 2 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }

            // Sexta fila, la de arriba , la del adversario, aqui realizo las capturas si las hubiese
            for (int i = 5; i >= 35 - contador; i--) { // Aqui el contador vale 30
                oware[0][i] = oware[0][i] + 1;
            }
            capturas(contador, jugador, posicion, contadorTotal);
            contador = contador - 2;

        }
        if (contador >= 34 && contador <= 35) {
            // Primera fila, la de abajo, la relleno entera
            for (int i = posicion; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                oware[1][posicion] = 0;
            }

            // Segunda fila adversario, la de arriba, la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }

            // Tercera fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 1 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Cuarta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            // Quinta fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 2 vuelta entera contado
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Sexta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            // Séptima fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 3 vuelta entera contador
            for (int i = 0; i <= contador - 36; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            contador = contador - 3; // Esto ES IMPORTANTE, le resto 3 posiciones al contador , por las 3 vueltas que ha hecho, porque sino entra en el siguiente 'if' --> (contador >= 36 && contador <= 38)

        }

        if (contador >= 36 && contador <= 38) {
            // Primera fila, la de abajo, la relleno entera
            for (int i = posicion; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                oware[1][posicion] = 0;
            }
            // Segunda fila adversario, la de arriba, la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;

            }
            // Tercera fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 1 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Cuarta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1; // le sumo 1 a la  siguiente posición
            }

            // Quinta fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 2 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }

            // Sexta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }

            // Séptima fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 3 vuelta entera contador
            for (int i = 0; i <= 6 - (42 - contador); i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            contador = contador - 3;

        }
        if (contador >= 39 && contador <= 44) {
            // Primera fila, la de abajo, la relleno entera
            for (int i = posicion; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                oware[1][posicion] = 0;
            }

            // Segunda fila adversario, la de arriba, la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }

            // Tercera fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 1 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Cuarta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            // Quinta fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 2 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Sexta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }

            // Séptima fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 3 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Octava fila, la de arriba , la relleno hasta el contador. IMPORTANTE --> Aquí no hace falta comprobar si hay 2 o 3 semillas, porque al dar mas de tres vueltas, siempre va haber mas de 3 semillas
            for (int i = 5; i >= 47 - contador; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            contador = contador - 3;

        }
        if (contador - posicion <= 48 && contador >= 45 && contador <= 49) {
            // Primera fila, la de abajo, la relleno entera
            for (int i = posicion; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                oware[1][posicion] = 0;
            }
            // Segunda fila adversario, la de arriba, la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;

            }
            // Tercera fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 1 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Cuarta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            // Quinta fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 2 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }

            // Sexta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }

            // Séptima fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 3 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Octava fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            // Novena fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 4 vuelta entera contador
            for (int i = 0; i <= 6 - (54 - contador); i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            contador = contador - 4; // Corregimos el contador por las 4 vueltas que ha dado

        }

        if (contador - posicion <= 48 && contador >= 50 && contador <= 53) {
            // Primera fila, la de abajo, la relleno entera
            for (int i = posicion; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                oware[1][posicion] = 0;
            }
            // Segunda fila adversario, la de arriba, la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;

            }
            // Tercera fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 1 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Cuarta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            // Quinta fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 2 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Sexta fila, la de arriba , la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1; // le sumo 1 a la  siguiente posición
            }

            // Séptima fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 3 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Octava fila, la de arriba, la relleno entera
            for (int i = 5; i >= 0; i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            // Novena fila, la de abajo, la relleno entera y me salto desde la casilla donde parto --> 4 vuelta entera contador
            for (int i = 0; i <= 5; i++) {
                oware[1][i] = oware[1][i] + 1;
                if (oware[1][posicion] == 1) {
                    oware[1][posicion] = 0;
                    contador = contador + 1;
                }
            }
            // Décima fila, la de arriba , la relleno hasta la posición del contador
            for (int i = 5; i >= (59 - contador); i--) {
                oware[0][i] = oware[0][i] + 1;
            }
            contador = contador - 4; // Coregimos el contador por las 4 vueltas que ha dado

        }


    }


    public static void visualizarTablero() {

        System.out.println("     ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("     ┃  f ┃  e ┃  d ┃  c ┃  b ┃  a ┃");
        System.out.println("┏━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━┓");

        for (int i = 0; i < oware.length; i++) {
            if (i == 1) {
                System.out.format("┃ %2d ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫ %2d ┃\n", depositoIzquierda, depositoDerecha);
            }

            System.out.print("┃    ┃");
            for (int j = 0; j < oware[i].length; j++) {

                //System.out.print(oware[i][j] + " ┃  ");
                System.out.format(blue + "%3d" + reset + " ┃", oware[i][j]);

            }
            System.out.print("    ┃");
            System.out.println();

        }
        System.out.println("┗━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━┛");
        System.out.println("     ┃  A ┃  B ┃  C ┃  D ┃  E ┃  F ┃");
        System.out.println("     ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

    }
}
