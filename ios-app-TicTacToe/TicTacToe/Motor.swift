//
//  Motor.swift
//  TicTacToe
//
//  Created by  on 30/10/2020.
//

import Foundation

// Variables globales

var tablero = [[Int]] ()

var jugando:Int = 0
var tiradas:Int = 0
var turno = true
var fila: Int = -1
var columna: Int = -1


// Para saber si hay ganador miro lo que me retorna la función (0,1)
func hayGanador() -> Int {
    
    var txapeldun: Int = -1
   
    //  8  Jugadas ganadoras del jugador 0
    

    if (tablero[0][0] == 0 && tablero[0][1] == 0 && tablero[0][2] == 0) {
           txapeldun = 0;
       }
    
    if (tablero[1][0] == 0 && tablero[1][1] == 0 && tablero[1][2] == 0) {
         txapeldun = 0
     }

     if (tablero[2][0] == 0 && tablero[2][1] == 0 && tablero[2][2] == 0) {
         txapeldun = 0
     }
     if (tablero[0][0] == 0 && tablero[1][0] == 0 && tablero[2][0] == 0) {
         txapeldun = 0
     }

     if (tablero[0][1] == 0 && tablero[1][1] == 0 && tablero[2][1] == 0) {
         txapeldun = 0
     }

     if (tablero[0][2] == 0 && tablero[1][2] == 0 && tablero[2][2] == 0) {
         txapeldun = 0
     }

     if (tablero[0][0] == 0 && tablero[1][1] == 0 && tablero[2][2] == 0) {
         txapeldun = 0
     }

     if (tablero[0][2] == 0 && tablero[1][1] == 0 && tablero[2][0] == 0) {
         txapeldun = 0
     }

     // 8 Jugadas ganadoras del jugador 1

     if (tablero[0][0] == 1 && tablero[0][1] == 1 && tablero[0][2] == 1) {
         txapeldun = 1
     }

     if (tablero[1][0] == 1 && tablero[1][1] == 1 && tablero[1][2] == 1) {
         txapeldun = 1
     }

     if (tablero[2][0] == 1 && tablero[2][1] == 1 && tablero[2][2] == 1) {
         txapeldun = 1
     }
     if (tablero[0][0] == 1 && tablero[1][0] == 1 && tablero[2][0] == 1) {
         txapeldun = 1
     }

     if (tablero[0][1] == 1 && tablero[1][1] == 1 && tablero[2][1] == 1) {
         txapeldun = 1
     }

     if (tablero[0][2] == 1 && tablero[1][2] == 1 && tablero[2][2] == 1) {
         txapeldun = 1
     }

     if (tablero[0][0] == 1 && tablero[1][1] == 1 && tablero[2][2] == 1) {
         txapeldun = 1
     }

     if (tablero[0][2] == 1 && tablero[1][1] == 1 && tablero[2][0] == 1) {
         txapeldun = 1
     }
    
    return txapeldun
    
}

func  empate() -> Bool  {
    var empate:Bool = false;

    // Si empieza el jugador '0' en la novena jugada le toca al jugador '0', sino realiza tres en raya hay empate
    if (tiradas == 9 && hayGanador () == -1) {
        empate = true;
    }

    return empate;
}


func  reset() {
    // Inicializo el tablero, pongo a cero el tablero

    tablero = Array(repeating: Array(repeating: -1, count:3 ), count: 3)

    // Reinicio variables:

    tiradas = 0;
    jugando = 0;

}
func tiradaRandom() {
    
  
    
    var ficha:String = ""
    
    
  
    if (tablero[fila][columna] == -1) {
     
        tablero[fila][columna] = jugando

        // Cambio de jugador

        if (jugando == 0) {
            jugando = 1
        }
        
        // Ordenador
        
        if (jugando == 1){
            // Mirar que mientras la posición del tablero que NO está vacía
            var filaAleatorio:Int
            var columnaAleatorio:Int
            
            repeat {
                filaAleatorio = Int.random(in: 0..<3)
                columnaAleatorio = Int.random(in: 0..<3)
              
                if (tablero[filaAleatorio][columnaAleatorio] == 0) {
                    ficha = "O";
                } else if (tablero[filaAleatorio][columnaAleatorio] == 1) {
                    ficha = "X";
                } else {
                    ficha = "";
                }
            }while (tablero[filaAleatorio][columnaAleatorio] != -1 )
          
            
            // Cambio de jugador
            jugando = 0
        }
        else {
            jugando = 0;
        }
        // Aumento una tirada:

        tiradas = tiradas + 1

    } else {
        //alerta (titulo: "Mensaje", mensaje: "Error")
    }

    if (tablero[fila][columna] == 0) {
        ficha = "O";
    } else if (tablero[fila][columna] == 1) {
        ficha = "X";
    } else {
        ficha = "";
    }
 
}


func tirada(tag:Int) -> String {
    // En el parámetro de entrada meto el Boton que pulsa el jugador (tag), sacamos la fila y la columna:
    //tag = (fila * 3) + columna
    
    fila = Int (tag/3)
    columna = tag - (fila * 3)
    
    var ficha:String = ""
    
    // Si la casilla escogida del tablero está vacía --> '-1' procedemos a jugar
    if (tablero[fila][columna] == -1) {
        // En el boton equivale al valor numérico que adopta la posición '0' o '1'
        // Empieza siempre el jugador '0'

        tablero[fila][columna] = jugando

        // Cambio de jugador

        if (jugando == 0) {
            jugando = 1;
        } else {
            jugando = 0;
        }
        // Aumento una tirada:

        tiradas = tiradas + 1;

    } else {
        //alerta (titulo: "Mensaje", mensaje: "Error")
    }

    // Ahora 'fijo' el String de cada casilla, para que no cambie su valor, mientras se está jugando... hasta reiniciar el tablero
    
    if (tablero[fila][columna] == 0) {
        ficha = "O";
    } else if (tablero[fila][columna] == 1) {
        ficha = "X";
    } else {
        ficha = "";
    }
    return ficha
}


