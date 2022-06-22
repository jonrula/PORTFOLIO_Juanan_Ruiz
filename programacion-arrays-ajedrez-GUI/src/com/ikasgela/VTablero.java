package com.ikasgela;

import javax.swing.*;
import java.awt.*;

public class VTablero {
    private JPanel PanelTablero;
    private JLabel casilla00;
    private JLabel casilla01;
    private JLabel casilla02;
    private JLabel casilla10;
    private JLabel casilla20;
    private JLabel casilla30;
    private JLabel casilla50;
    private JLabel casilla60;
    private JLabel casilla22;
    private JLabel casilla03;
    private JLabel casilla04;
    private JLabel casilla05;
    private JLabel casilla06;
    private JLabel casilla07;
    private JLabel casilla11;
    private JLabel casilla12;
    private JLabel casilla13;
    private JLabel casilla14;
    private JLabel casilla15;
    private JLabel casilla16;
    private JLabel casilla17;
    private JLabel casilla21;
    private JLabel casilla23;
    private JLabel casilla24;
    private JLabel casilla25;
    private JLabel casilla26;
    private JLabel casilla27;
    private JLabel casilla31;
    private JLabel casilla32;
    private JLabel casilla33;
    private JLabel casilla34;
    private JLabel casilla35;
    private JLabel casilla36;
    private JLabel casilla37;
    private JLabel casilla40;
    private JLabel casilla41;
    private JLabel casilla42;
    private JLabel casilla43;
    private JLabel casilla44;
    private JLabel casilla45;
    private JLabel casilla46;
    private JLabel casilla47;
    private JLabel casilla51;
    private JLabel casilla52;
    private JLabel casilla53;
    private JLabel casilla54;
    private JLabel casilla55;
    private JLabel casilla56;
    private JLabel casilla57;
    private JLabel casilla61;
    private JLabel casilla62;
    private JLabel casilla63;
    private JLabel casilla64;
    private JLabel casilla65;
    private JLabel casilla66;
    private JLabel casilla67;
    private JLabel casilla70;
    private JLabel casilla71;
    private JLabel casilla72;
    private JLabel casilla73;
    private JLabel casilla74;
    private JLabel casilla75;
    private JLabel casilla76;
    private JLabel casilla77;


    // Hago un Array de 'JLabels' y asi voy comprobando la posición nueva del Array de ajedrez  equivale a la misma posición del Array de 'JLabels'
    // Osea --> ajedrez [0][0] = casillas[0][0]
    private final JLabel[][] casillas = {
            {casilla00, casilla01, casilla02, casilla03, casilla04, casilla05, casilla06, casilla07},
            {casilla10, casilla11, casilla12, casilla13, casilla14, casilla15, casilla16, casilla17},
            {casilla20, casilla21, casilla22, casilla23, casilla24, casilla25, casilla26, casilla27},
            {casilla30, casilla31, casilla32, casilla33, casilla34, casilla35, casilla36, casilla37},
            {casilla40, casilla41, casilla42, casilla43, casilla44, casilla45, casilla46, casilla47},
            {casilla50, casilla51, casilla52, casilla53, casilla54, casilla55, casilla56, casilla57},
            {casilla60, casilla61, casilla62, casilla63, casilla64, casilla65, casilla66, casilla67},
            {casilla70, casilla71, casilla72, casilla73, casilla74, casilla75, casilla76, casilla77},


    };


    // Getter del tablero para que lo coja desde el 'main()'
    public JPanel getPanelTablero() {
        return PanelTablero;
    }


    // Constructor

    public VTablero(int[][] ajedrez) {


/*
        // Para saber cual es el valor de las casillas:

        System.out.println("filaDestino: " + filaDestino);
        System.out.println("columnaDestino: " + columnaDestino);
        System.out.println("ajedrez casilla origen: " + ajedrez[filaOrigen][columnaOrigen]);
        System.out.println("ajedrez casilla destino: " + ajedrez[filaDestino][columnaDestino]);

 */

        // Actualizo el tablero con los valores de la última jugada, según ha quedado el Array de ajedrez

        actualizarTablero(ajedrez);

        // Ahora no me genera otra ventana nueva del tablero de ajedrez, --> pongo el 'JFrame' como global en la clase 'Main()'


    }

    public void actualizarTablero(int[][] ajedrez) {
        for (int i = 0; i < ajedrez.length; i++) {
            for (int j = 0; j < ajedrez[i].length; j++) {
                // Pieza Azules (arriba)

                // Peón
                if (ajedrez[i][j] == 1) {
                    casillas[i][j].setForeground(Color.blue);
                    casillas[i][j].setText("♙");
                }
                // Torre
                if (ajedrez[i][j] == 3) {
                    casillas[i][j].setForeground(Color.blue);
                    casillas[i][j].setText("♖");
                }
                // Caballo
                if (ajedrez[i][j] == 5) {
                    casillas[i][j].setForeground(Color.blue);
                    casillas[i][j].setText("♘");
                }
                // Alfil
                if (ajedrez[i][j] == 7) {
                    casillas[i][j].setForeground(Color.blue);
                    casillas[i][j].setText("♗");
                }
                // Dama
                if (ajedrez[i][j] == 9) {
                    casillas[i][j].setForeground(Color.blue);
                    casillas[i][j].setText("♕");
                }
                // Rey
                if (ajedrez[i][j] == 11) {
                    casillas[i][j].setForeground(Color.blue);
                    casillas[i][j].setText("♔");
                }

                // Piezas Blancas (abajo)

                // Peón
                if (ajedrez[i][j] == 2) {
                    casillas[i][j].setForeground(Color.lightGray);
                    casillas[i][j].setText("♙");
                }
                // Torre
                if (ajedrez[i][j] == 4) {
                    casillas[i][j].setForeground(Color.lightGray);
                    casillas[i][j].setText("♖");
                }
                // Caballo
                if (ajedrez[i][j] == 6) {
                    casillas[i][j].setForeground(Color.lightGray);
                    casillas[i][j].setText("♘");
                }
                // Alfil
                if (ajedrez[i][j] == 8) {
                    casillas[i][j].setForeground(Color.lightGray);
                    casillas[i][j].setText("♗");
                }
                // Dama
                if (ajedrez[i][j] == 10) {
                    casillas[i][j].setForeground(Color.lightGray);
                    casillas[i][j].setText("♕");
                }
                // Rey
                if (ajedrez[i][j] == 12) {
                    casillas[i][j].setForeground(Color.lightGray);
                    casillas[i][j].setText("♔");
                }
                // Casilla vacía
                if (ajedrez[i][j] == 0) {
                    casillas[i][j].setText(" ");
                }

                // Jaques-Mates

                if (ajedrez[i][j] == 13) {
                    casillas[i][j].setForeground(Color.red);
                    casillas[i][j].setText("♔");
                    System.out.print(Main.verde + "  ♖" + Main.reset + Main.rojo + "♔  " + Main.reset);
                }
                if (ajedrez[i][j] == 14) {
                    casillas[i][j].setForeground(Color.red);
                    casillas[i][j].setText("♔");
                    System.out.print(Main.verde + "  ♙" + Main.reset + Main.rojo + "♔  " + Main.reset);
                }
                if (ajedrez[i][j] == 15) {
                    casillas[i][j].setForeground(Color.red);
                    casillas[i][j].setText("♔");
                    System.out.print(Main.verde + "  ♗" + Main.reset + Main.rojo + "♔  " + Main.reset);
                }
                if (ajedrez[i][j] == 16) {
                    casillas[i][j].setForeground(Color.red);
                    casillas[i][j].setText("♔");
                    System.out.print(Main.verde + "  ♘" + Main.reset + Main.rojo + "♔" + Main.reset);
                }
                if (ajedrez[i][j] == 17) {
                    casillas[i][j].setForeground(Color.red);
                    casillas[i][j].setText("♔");
                    System.out.print(Main.verde + "   ♔" + Main.reset + Main.rojo + "♔  " + Main.reset);
                }
                if (ajedrez[i][j] == 18) {
                    casillas[i][j].setForeground(Color.red);
                    casillas[i][j].setText("♔");
                    System.out.print(Main.verde + "   ♕" + Main.reset + Main.rojo + "♔  " + Main.reset);
                }

            }
        }
    }


    // Esta función, al final no la necesito, con actualizar el tablero, comprobando la posición nueva del Array de ajedrez y comparándolo con el Array de 'JLabels'
    public void figurasTablero(int[][] ajedrez, int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {

        // Piezas AZULES (Arriba)


        // Peón
        if (ajedrez[filaDestino][columnaDestino] == 1) {
            casillas[filaDestino][columnaDestino].setText("♙");
        }
        // Torre
        if (ajedrez[filaDestino][columnaDestino] == 3) {
            casillas[filaDestino][columnaDestino].setText("♖");
        }
        // Caballo
        if (ajedrez[filaDestino][columnaDestino] == 5) {
            casillas[filaDestino][columnaDestino].setText("♘");
        }
        // Alfil
        if (ajedrez[filaDestino][columnaDestino] == 7) {
            casillas[filaDestino][columnaDestino].setText("♗");
        }
        // Dama
        if (ajedrez[filaDestino][columnaDestino] == 9) {
            casillas[filaDestino][columnaDestino].setText("♕");
        }
        // Rey
        if (ajedrez[filaDestino][columnaDestino] == 11) {
            casillas[filaDestino][columnaDestino].setText("♔");
        }


        // Piezas BLANCAS (Abajo)

        // Peón
        if (ajedrez[filaDestino][columnaDestino] == 2) {
            casillas[filaDestino][columnaDestino].setText("♙");
        }
        // Torre
        if (ajedrez[filaDestino][columnaDestino] == 4) {
            casillas[filaDestino][columnaDestino].setText("♖");
        }
        // Caballo
        if (ajedrez[filaDestino][columnaDestino] == 6) {
            casillas[filaDestino][columnaDestino].setText("♘");
        }
        // Alfil
        if (ajedrez[filaDestino][columnaDestino] == 8) {
            casillas[filaDestino][columnaDestino].setText("♗");
        }
        // Dama
        if (ajedrez[filaDestino][columnaDestino] == 10) {
            casillas[filaDestino][columnaDestino].setText("♕");
        }
        // Rey
        if (ajedrez[filaDestino][columnaDestino] == 12) {
            casillas[filaDestino][columnaDestino].setText("♔");
        }

        // Casilla en origen, se queda vacía si el movimiento es correcto

        if (ajedrez[filaOrigen][columnaOrigen] == 0) {
            casillas[filaOrigen][columnaOrigen].setText(" ");
        }

        // Jaques-Mates

        if (ajedrez[filaDestino][columnaDestino] == 13) {
            casillas[filaDestino][columnaDestino].setText("  ♔  ");
            System.out.print(Main.verde + "  ♖" + Main.reset + Main.rojo + "♔  " + Main.reset);
        }
        if (ajedrez[filaDestino][columnaDestino] == 14) {
            casillas[filaDestino][columnaDestino].setText("  ♔  ");
            System.out.print(Main.verde + "  ♙" + Main.reset + Main.rojo + "♔  " + Main.reset);
        }
        if (ajedrez[filaDestino][columnaDestino] == 15) {
            casillas[filaDestino][columnaDestino].setText("  ♔  ");
            System.out.print(Main.verde + "  ♗" + Main.reset + Main.rojo + "♔  " + Main.reset);
        }
        if (ajedrez[filaDestino][columnaDestino] == 16) {
            casillas[filaDestino][columnaDestino].setText("  ♔  ");
            System.out.print(Main.verde + "  ♘" + Main.reset + Main.rojo + "♔" + Main.reset);
        }
        if (ajedrez[filaDestino][columnaDestino] == 17) {
            casillas[filaDestino][columnaDestino].setText("  ♔  ");
            System.out.print(Main.verde + "   ♔" + Main.reset + Main.rojo + "♔  " + Main.reset);
        }
        if (ajedrez[filaDestino][columnaDestino] == 18) {
            casillas[filaDestino][columnaDestino].setText("  ♔  ");
            System.out.print(Main.verde + "   ♕" + Main.reset + Main.rojo + "♔  " + Main.reset);
        }


    }


}
