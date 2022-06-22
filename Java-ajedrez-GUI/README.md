Crea un programa que permita jugar por turnos al [ajedrez](https://es.wikipedia.org/wiki/Ajedrez):

- Modela el [tablero](https://es.wikipedia.org/wiki/Ajedrez#El_tablero_de_ajedrez) usando un array de 8x8 elementos y visualízalo con la ayuda de los [caracteres Unicode](http://www.unicode.org/charts/PDF/U2600.pdf) correspondientes (están situados en el rango 0x2654-0x265F).
- Coloca en él todas las piezas en su posición inicial:

	![](http://www.jinchess.com/chessboard/?p=rnbqkbnrpppppppp--------------------------------PPPPPPPPRNBQKBNR&ps=alpha-flat&cm=o)

- Sortea a quién le toca empezar (blancas o negras).
- Pide a los jugadores, por turnos, la casilla de origen y de final del movimiento usando la [notación algebraica](https://es.wikipedia.org/wiki/Notaci%C3%B3n_algebraica) (por ejemplo, `c2 → d2`). 
- El programa debe comprobar la validez del movimiento y si se captura alguna pieza.

Aplica los principios de la programación modular y subdivide el programa en las funciones que consideres necesarias.
