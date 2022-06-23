# Trivia

Crea un juego de preguntas y respuestas tipo [_pub quiz_](https://en.wikipedia.org/wiki/Pub_quiz) donde el usuario responda a preguntas elegidas al azar desde una base de datos.

Cada pregunta tendrá un máximo de 4 posibles opciones y el programa registrará la racha más larga de preguntas correctas que consiga el jugador.

El funcionamiento de la aplicación será así:

1. Al arrancar, el programa preguntará su nombre al usuario y, si existe en la base de datos, recuperará su record anterior y lo mostrará. 
2. El juego mostrará preguntas al azar hasta que el usuario falle.
3. Cuando se produzca el fallo, si hay un nuevo record, se guardará en la base de datos y comenzará una nueva ronda de preguntas.

## Restricciones

- La aplicación tiene seguir la estructura de este diagrama de clases:

    ```plantuml
    @startuml
    skinparam classAttributeIconSize 0
    skinparam nodesep 100
    skinparam ranksep 80
	
    class Jugador {
    - nombre: String
    - record: int
    }
	
    class Pregunta {
    - texto: String
    }
	
    class Respuesta {
    - texto: String
    - correcta: boolean = false
    }
	
    Pregunta - "2..4" Respuesta
	
    @enduml
    ```

- La base de datos tiene que ser SQLite. Diséñala usando [DB Browser for SQLite](https://sqlitebrowser.org) y añádela al proyecto.
- La aplicación tiene que tener un GUI basado en Swing como interfaz de usuario. El diseño es libre.
- No es obligatorio que la aplicación permita crear, editar o borrar preguntas; crea manualmente unas pocas en la base de datos para hacer las pruebas.

##LISTA DE JUGADORES:
###Juanan, Luis, Txus, Ana


