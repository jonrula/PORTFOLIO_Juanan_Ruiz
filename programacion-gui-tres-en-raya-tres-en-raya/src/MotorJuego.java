import javax.swing.*;

public class MotorJuego {

    private int[][] tablero = {
            {-1, -1, -1},
            {-1, -1, -1},
            {-1, -1, -1},
    };

    private int jugando = 0;
    private int tiradas = 0;
    private boolean turno = true;


    // Métodos

    // Poner a cero el tablero
    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = -1;
            }
        }

        // Reinicio variables:

        tiradas = 0;
        jugando = 0;

    }


    public boolean empate() {
        boolean empate = false;

        // Si empieza el jugador '0' en la novena jugada le toca al jugador '0', sino realiza tres en raya hay empate
        if (tiradas == 9 && hayGanador() == -1) {
            empate = true;
        }

        return empate;
    }

    public int hayGanador() {
        int txapeldun = -1;

        //  8  Jugadas ganadoras del jugador 0

        if (tablero[0][0] == 0 && tablero[0][1] == 0 && tablero[0][2] == 0) {
            txapeldun = 0;
        }

        if (tablero[1][0] == 0 && tablero[1][1] == 0 && tablero[1][2] == 0) {
            txapeldun = 0;
        }

        if (tablero[2][0] == 0 && tablero[2][1] == 0 && tablero[2][2] == 0) {
            txapeldun = 0;
        }
        if (tablero[0][0] == 0 && tablero[1][0] == 0 && tablero[2][0] == 0) {
            txapeldun = 0;
        }

        if (tablero[0][1] == 0 && tablero[1][1] == 0 && tablero[2][1] == 0) {
            txapeldun = 0;
        }

        if (tablero[0][2] == 0 && tablero[1][2] == 0 && tablero[2][2] == 0) {
            txapeldun = 0;
        }

        if (tablero[0][0] == 0 && tablero[1][1] == 0 && tablero[2][2] == 0) {
            txapeldun = 0;
        }

        if (tablero[0][2] == 0 && tablero[1][1] == 0 && tablero[2][0] == 0) {
            txapeldun = 0;
        }

        // 8 Jugadas ganadoras del jugador 1

        if (tablero[0][0] == 1 && tablero[0][1] == 1 && tablero[0][2] == 1) {
            txapeldun = 1;
        }

        if (tablero[1][0] == 1 && tablero[1][1] == 1 && tablero[1][2] == 1) {
            txapeldun = 1;
        }

        if (tablero[2][0] == 1 && tablero[2][1] == 1 && tablero[2][2] == 1) {
            txapeldun = 1;
        }
        if (tablero[0][0] == 1 && tablero[1][0] == 1 && tablero[2][0] == 1) {
            txapeldun = 1;
        }

        if (tablero[0][1] == 1 && tablero[1][1] == 1 && tablero[2][1] == 1) {
            txapeldun = 1;
        }

        if (tablero[0][2] == 1 && tablero[1][2] == 1 && tablero[2][2] == 1) {
            txapeldun = 1;
        }

        if (tablero[0][0] == 1 && tablero[1][1] == 1 && tablero[2][2] == 1) {
            txapeldun = 1;
        }

        if (tablero[0][2] == 1 && tablero[1][1] == 1 && tablero[2][0] == 1) {
            txapeldun = 1;
        }

        return txapeldun;
    }


    public String tirada(int fila, int columna) {

        String ficha = "";

        // Si la casilla escogida del tablero está vacía --> '-1' procedemos a jugar
        if (tablero[fila][columna] == -1) {
            tablero[fila][columna] = jugando; // En el boton equivale al valor numérico que adopta la posición '0' o '1'  Empieza siempre el jugador '0'

            // Meto en las coordenada --> fila, columna que he elegido al apretar el botón correspondiente, la 'X' o la 'O' el función del jugador


            // Cambio de jugador

            if (jugando == 0) {
                jugando = 1;
            } else {
                jugando = 0;
            }
            // Aumento una tirada:

            tiradas = tiradas + 1;

        } else {
            JOptionPane.showMessageDialog(null, "Casilla ocupada !!", "Título", JOptionPane.ERROR_MESSAGE);
        }

        // Ahora 'fijo' el String de cada casilla, para que no cambie su valor, mientras se está jugando... hasta reiniciar el tablero
        if (tablero[fila][columna] == 0) {
            ficha = "O";
        } else if (tablero[fila][columna] == 1) {
            ficha = "X";
        } else {
            ficha = "";
        }


        return ficha;


    }


}
