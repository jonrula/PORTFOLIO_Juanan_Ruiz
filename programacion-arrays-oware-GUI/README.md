Crea un programa que permita jugar a [Oware](https://es.wikipedia.org/wiki/Oware).

## Sugerencias

- Antes de codificar nada, lee detenidamente cómo se juega y define las reglas de juego que va a seguir tu implementación.
- El tablero, en su posición inicial, podría tener este aspecto:

	```
	     ┌─────────────────────────────┐
	     │  f │  e │  d │  c │  b │  a │
	┌────┼─────────────────────────────┼────┐
	│    │  4 │  4 │  4 │  4 │  4 │  4 │    │
	│  0 ├─────────────────────────────┤  0 │
	│    │  4 │  4 │  4 │  4 │  4 │  4 │    │
	└────┼─────────────────────────────┼────┘
	     │  A │  B │  C │  D │  E │  F │
	     └─────────────────────────────┘
	```

	Está dibujado con [estos caracteres](https://en.wikipedia.org/wiki/Box-drawing_character) Unicode.

## Restricciones

Aplica los principios de la programación modular y subdivide el programa en las funciones que consideres necesarias.

##¿Cómo se juega al oware?

El oware es un juego de estrategia para dos personas. Pertenece a la familia de los juegos de mancala, denominados de cuenta-y-captura por la peculiaridad del desarrollo de una partida, consistente en la distribución de las fichas del juego por el tablero y la posterior supresión de las mismas del tablero cuando se cumplen ciertas condiciones.

##Equipamiento y disposición inicial

Para jugar al oware abapa se requiere un tablero y cuarenta y ocho fichas, llamadas semillas. Habitualmente el tablero estará compuesto por dos hileras de seis hoyos, situadas una enfrente de la otra. Dos hoyos más grandes a ambos lados tablero serviran para guardar las semillas que los jugadores capturen en el desarrollo de la partida. Se dice que la hilera inferior pertenece al jugador que mueve primero, llamado sur, y la hilera superior al segundo jugador o norte.


Tablero de oware en su posición inicial:

	```
	     ┌─────────────────────────────┐
	     │  f │  e │  d │  c │  b │  a │
	┌────┼─────────────────────────────┼────┐
	│    │  4 │  4 │  4 │  4 │  4 │  4 │    │
	│  0 ├─────────────────────────────┤  0 │
	│    │  4 │  4 │  4 │  4 │  4 │  4 │    │
	└────┼─────────────────────────────┼────┘
	     │  A │  B │  C │  D │  E │  F │
	     └─────────────────────────────┘
	```

Cada uno de los hoyos de juego contiene exactamente cuatro semillas.

En la posición inicial, cada hoyo excepto los dos más grandes (hoyos de captura), contiene exactamente cuatro semillas. En esta posición el jugador sur hará su primer movimiento, seguido de un movimiento del jugador norte y así alternativamente hasta que la partida termine.

##Objetivo del juego

La finalidad del juego consiste en capturar el máximo de semillas posibles. Para hacerlo los jugadores realizan movimientos en turnos alternativos hasta que alguno de ellos ha capturado más de 24 semillas. Gana la partida aquel jugador que al finalizar el juego ha capturado más semillas que el adversario. También puede darse el caso que ambos jugadores hayan capturado el mismo número de semillas al finalizar. En este supuesto, ninguno de los jugadores gana la partida y se dice que ha terminado en empate.

##Desarrollo de una partida

Cada movimiento del juego se hace en tres fases: la recolección, la siembra y la captura. Durante la siembra el jugador distribuye las semillas recogidas a lo largo del tablero y en la captura el jugador toma, cuando le es posible, las semillas que se encuentran en los hoyos del adversario.

###Recolección de semillas

En la primera fase de un movimiento el jugador al cual le toca mover escoge uno de los hoyos que le pertenecen y recoge todas las semillas que contiene, dejando aquel hoyo vacío. Posteriormente, estas semillas se repartirán por el tablero en la fase de siembra.

El jugador puede recoger las semillas de cualquiera de los hoyos que le pertenecen cuando este contenga una o más semillas, sólo con la excepción que después de realizar el movimiento su adversario debe poder jugar. No serán legales pues aquellos movimientos que podrian dejar todos los hoyos del oponente sin semillas.

###Siembra de semillas

Durante la siembra, el jugador reparte por el tablero, en sentido antihorario, las semillas recogidas en la primera fase; dejando una semilla en cada hoyo propio y del jugador contrario hasta que las haya distribuido todas. El jugador nunca sembrará semillas en los hoyos de captura.

Animación del proceso de siembra en el oware
Proceso de siembra en el oware
El jugador sur siembra 4 semillas, repartiéndolas por el tablero una a una y en sentido antihorario.

Al finalizar la siembra el hoyo del cual el jugador ha recogido las semillas estará vacío. Se puede dar el caso que el jugador siembre doce o más semillas. Cuando esto suceda el jugador las sembrará dando una o más vueltas al tablero, dejando una semilla en cada uno de los hoyos en cada vuelta, pero exceptuando siempre el hoyo del cual se han recogido.

###Captura de semillas

Si al finalizar la siembra la última semilla se ha dejado en uno de los hoyos que pertenecen al adversario y después de dejar la semilla este hoyo contiene exactamente dos o tres semillas, el jugador las capturará. Tomando todas las semillas y depositándolas en su hoyo de capturas.

Si después de capturar las semillas de un hoyo resulta que el hoyo inmediatamente a su derecha también contiene dos o tres semillas, el jugador también capturará las semillas de aquel hoyo. La captura continuará sucesivamente hasta que el jugador ya no pueda capturar más semillas, teniendo en cuenta que los jugadores sólo pueden capturar semillas que se encuentren en hoyos de su adversario, nunca de sus propios hoyos.

Animación del proceso de captura en el oware
Proceso de captura en el oware
Luego de sembrar tres semillas, recogidas del hoyo F, el jugador sur capturará 5 semillas en los hoyos b y c.

Hay que destacar que un jugador no puede capturar nunca todas las semillas del adversario. Si el jugador hace un movimiento que después de las capturas dejaría todos los hoyos del adversario vacíos, aquel jugador hará la siembra normalmente pero no capturará ninguna semilla.

##Finalización de la partida

Lo normal es que el juego se termine cuando uno de los dos jugadores ha capturado más de 24 semillas o cuando los dos han capturado 24. También puede suceder que en el turno de un jugador este no pueda hacer ningún movimiento legal, en este supuesto, cada jugador capturará las semillas que se encuentren en los hoyos de su lado del tablero y el juego finalizará.

Una situación especial es cuando el juego entra en un ciclo, de manera que las mismas posiciones y movimientos se irían repitiendo indefinidamente. Si esto sucede, los jugadores pueden acordar finalizar la partida y cada jugador capturará las semillas que se encuentren en su lado del tablero.