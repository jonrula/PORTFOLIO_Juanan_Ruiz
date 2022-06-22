# coding=utf-8



from getpass import getpass
from bullet import Password


from Logueo import *
from Oportunidad import *
from Actividad import *
from Grafico import *

# Estas librerías son para poner colores en la consola
from color import *
from colorama import init, Fore

init(autoreset=True) # Este sirve para 'reiniciar' al color por defecto de la consola y que no se quede en el anterior

'''

print("EJEMPLOS DE COLORES: ")

print("Colores de ejemplo: ")
print(color.BLUE+"azul"+color.END)
print(color.RED+"rojo"+color.END)
print(color.GREEN+"verde"+color.END)
print(color.BOLD+"bold"+color.END)
print(color.CYAN+"Cyan"+color.END)
print(color.DARKCYAN+"Darkcyan"+color.END)
print(color.PURPLE+"Morado"+color.END)
print(color.UNDERLINE+"Underline"+color.END)
print(color.YELLOW+"amarillo"+color.END)

print("Texto color original")

print(Fore.YELLOW + "Texto color amarillo")
print(Fore.GREEN + "Texto color verde")
print(Fore.RED + "Texto color rojo")
print("Texto color original de nuevo")

print(Fore.YELLOW + "P" + Fore.GREEN + "a" + Fore.BLUE + "r" + Fore.RED + "z" + Fore.CYAN + "i" + Fore.MAGENTA + "b" + Fore.WHITE + "y" + Fore.YELLOW + "t" + Fore.RED + "e")

# Otra forma:
print(Fore.YELLOW + "P", end="")
print(Fore.GREEN + "a", end="")
print(Fore.BLUE + "r", end="")
print(Fore.RED + "z", end="")
print(Fore.CYAN + "i", end="")
print(Fore.MAGENTA + "b", end="")
print(Fore.WHITE + "y", end="")
print(Fore.YELLOW + "t", end="")
print(Fore.RED + "e")

'''



'''
                                                                    PLANTEAMIENTO:

PRIMERO --> Puedes arrancar la aplicación desde la terminal (para que no se vean las contraseñas) o en la consola (se verán las contraseñas)
Para acceder a la aplicación primero hay que loguearse, los usarios que tienen acceso a la aplicación son los 'comerciales' que al abrir una sesión al loguearse en la app, les añado por defecto  su nombre de comercial al al objeto 'oportunidad'.
Estos comerciales se guardan en un diccionario --> 'diccionarioAccesoComerciales = {"juanan": "1234", "Paco": "abcd", "admin": "0000"}'
Puedes entrar con cualquiera de los anteriores comerciales con sus respectivas contraseñas o crearte un nuevo usuario.
He aprovechado el proyecto anterior, para que cuando se crea un nuevo usuario, eliga un tipo de contraseña, se la manda un correo validado y finalmente lo añado al 'diccionarioAccesoComerciales'
He tenido en cuenta los campos vacíos, intros sin texto, poner expresiones regulares para los campos hora, fecha, correo, correo electrónico, DNI correcto, elegir opciones correctas, a la hora de elegir oportunidades, clientes etc...
Las siguientes librerías son para encriptar la contraseña y que no se vean:
    'from getpass import getpass' --> Aparecen espacios en blanco
    'from bullet import Password' --> Aparecen asteriscos

    
Tengo una Clase PRINCIPAL --> clase Oportunidad:
esta se compone -->  Oportunidad (CLIENTE, Descripción, Fecha, ingreso de dinero, comercial al cargo de la oportunidad, Fase oportunidad, lista de Actividades)
De esta clase, los importantes son:
    CLIENTE --> Objeto de tipo Cliente
    Fase oportunidad (Inicio, En proceso, ganada, perdida) --> Aquí puedo ir CAMBIANDO DE FASE y luego puedo 'cuantificar' estas fases para los gráficos
    LISTA ACTIVIDADES (Aquí voy añadiendo las actividades de cada oportunidad, puede estar vacía o tener muchas actividades)--> IMPORTANTE, tiene que estar entre corchetes, porque sino no puede iterar
    Comercial --> Es el que se loguea en la aplicación y por defecto lo añado al 'comercial'del objeto 'Oportunidad' que haya creado.
    Los demás son atributos normales
    
Las relaciones entre las clases y sus características:
    1 Cliente puede tener muchas oportunidades diferentes.
    1 Oportunidad solo pertenece a un Cliente.
    1 Oportunidad puede tener 0 actividades o muchas actividades
    1 Oportunidad puede ir cambiando entre fases
    1 Oportunidad se puede borrar junto a todas sus actividades (No afecta a los clientes)
    NO puedo borrar Clientes si tienen oportunidades, de hecho tengo que tener clientes sino NO puedo crear una oportunidad, luego puede tener o no tener actividades o añadirselas más tarde


Los siguientes datos son para tener una pequeña base de datos de lista de clientes, Usuarios con acceso a la aplicación, lista de oportunidades y lista de oportunidades
Como he dicho anteriormente, es muy IMPORTANTE el poner posiciones de la lista de actividades, entre corchetes, sino no entiende que es una lista y no puedo iterar, en cambio en el objeto Cliente, solo se añade uno, osea que no es necesario iterar:
    Oportunidad(CLIENTE,....... ,[listaActividades[0]]))
    Oportunidad(CLIENTE,....... ,listaActividades))  --> Aquí no hace falta, ya entiende que es una lista
    Oportunidad(CLIENTE,....... ,[listaActividades[1:2]]))
    Oportunidad(CLIENTE,....... ,[])) --> Aquí le tengo que decir que es una lista vacía
    
'''

'''
IMPORTANTE --> A partir del MAIN, pongo todo el FOCO de la aplicación en estas listas que pongo en el MAIN y apartir de aquí les voy a ir pasando como argumentos en todas las funciones desde el menú principal. a todas los submenús y funciones que los incluye.

                                        
                                        
Creo:
    lista de clientes --> 'listaClientes = []'
    lista de Actividades --> 'listaActividades = []'
    lista de oportunidades --> 'listaOportunidades = []'
    diccionarioAccesoComerciales = {}
    
                                                     DECLARACIÓN E INICIALIZACIÓN DE LISTAS Y DICCIONARIOS DE ACCESO:
                                        
'''


listaClientes = []
listaClientes.append(Cliente("Egibide", "Ruiz", "16296028E", "egibide@egibide.com", "945-123456", "Educación"))
listaClientes.append(Cliente("Paco", "López", "16978023B", "pacolopez@pl.com", "945-896789", "Transporte"))
listaClientes.append(Cliente("Ekaitz", "Martinez", "16234578N", "ekaitz@emc.com", "945-010101", "Informática"))

diccionarioAccesoComerciales = {"juanan": "1234", "Paco": "abcd", "admin": "0000"}

listaActividades = []
listaActividades.append(Actividad("Reunión", "23/05/2021", "20:00", "Clara Campoamor", "Alta"))
listaActividades.append(Actividad("Comida", "12/06/2021", "15:00", "Zaldiaran", "Media"))
listaActividades.append(Actividad("Visita Fábrica", "12/10/2021", "12:00", "Betoño", "Baja"))
listaActividades.append(Actividad("Cena", "23/07/2021", "21:00", "Arkupe", "Alta"))
listaActividades.append(Actividad("Negociación", "01/04/2021", "15:00", "Colegio", "Muy Alta"))

listaOportunidades = []
listaOportunidades.append(Oportunidad(listaClientes[0], "Proyecto venta ordenadores", "14/02/2021", 12000.789, "JUANAN", "Inicio",[listaActividades[0]]))
listaOportunidades.append(Oportunidad(listaClientes[1], "Proyecto venta casas", "01/04/2021", 786.98, "PACO", "Inicio",listaActividades))
listaOportunidades.append(Oportunidad(listaClientes[1], "Proyecto viajes", "12/01/2021", 234578.98, "PACO", "En proceso",[listaActividades[3]]))
listaOportunidades.append(Oportunidad(listaClientes[1], "Proyecto carreteras", "23/10/2021", 23.98, "PACO", "Ganada",listaActividades[1:2]))
listaOportunidades.append(Oportunidad(listaClientes[2], "Proyecto Python", "14/02/2021", 14567.98, "JUANAN", "En proceso",[listaActividades[0],listaActividades[2]]))
listaOportunidades.append(Oportunidad(listaClientes[2], "Proyecto iCar", "23/03/2021", 10000000.0, "JUANAN", "Ganada",listaActividades[1:4]))
listaOportunidades.append(Oportunidad(listaClientes[2], "Proyecto Viajes by the world", "24/06/2021", 500000.0, "JUANAN", "Perdida",[]))
listaOportunidades.append(Oportunidad(listaClientes[2], "Proyecto Colegio", "12/10/2021", 10000.0, "JUANAN", "Inicio",listaActividades[1:3]))





def menuLogueo(diccionarioAccesoComerciales, listaClientes, listaActividades, listaOportunidades):
    nombre = ""
    password = ""

    print("""
    Elige una opcion:\n
        a. Iniciar sesión
        b. Crear usuario
        c. Salir
    """)

    opcion = input(color.BLUE+"Opción: "+color.END)

    if opcion.lower() == "a":


        # Si quieres acceder desde la CONSOLA sin que dé errores:
        comercial = input(color.BOLD+"Usuario: "+color.END)
        password = input(color.BOLD+"Contraseña: "+color.END)

        # Si quieres acceder desde la TERMINAL sin que se vean la contraseña (*) con al librería --> 'from bullet import Password', tienes que comentar la línea 162 del password y descomentar las líneas 166 y 167

        #pregunta = Password(color.BOLD+"Contraseña: "+color.END)
        #password= pregunta.launch()

        # Si quieres acceder desde la TERMINAL sin que se vean la contraseña (espacios en blanco) con la libreria 'from getpass import getpass', tienes que comentar la línea 162 del password y descomentar la línea 170
        #password = getpass(color.BOLD+"Contraseña: "+color.END)


        # Una forma que he mirado para comprobar que coinciden usuario y contraseña recorriendo el diccionario
        # for clave in diccionarioAccesoComerciales:
        #     if clave.lower() == nombre.lower() and diccionarioAccesoComerciales[clave] == password:
        #         menuPrincipal(listaClientes,listaOportunidades)
        #     else:
        #
        #
        #         print("Error !!")
        #         menuLogueo(diccionarioAccesoComerciales, listaClientes,listaActividades,listaOportunidades)


        # Otra forma comprobando con el método 'get()' y pasándole la clave, que es el nombre del comercial

        if diccionarioAccesoComerciales.get(comercial) == password:
            menuPrincipal(comercial, listaClientes, listaOportunidades)

        else:
            print(color.RED+"Error !!, no existe el usuario "+comercial.upper()+color.END)
            menuLogueo(diccionarioAccesoComerciales, listaClientes, listaActividades, listaOportunidades)

    if opcion.lower() == "b":

        nombre = validarString("Nuevo usuario: ", 20)

        for claveNombre in diccionarioAccesoComerciales:
            while claveNombre.lower() == nombre.lower():

                if claveNombre.lower() == nombre.lower():
                    print(color.RED+"Error, el usuario " + nombre.title() + " ya  existe !!"+color.END)

                    nombre = validarString("Nuevo usuario: ", 10)

        apellido = validarString("Apellidos: ", 20)
        password = GenerarContrasenya()

        diccionarioAccesoComerciales[nombre] = password
        print()

        email = validarEmail()

        enviarEmail(nombre,apellido,password,email)

        print(Fore.GREEN+"Se ha creado correctamente a el comercial: "+nombre.upper()+ " "+apellido.upper()+ " con acceso a la aplicación ")

        # for comerciales, contrasenya in diccionarioAccesoComerciales.items():
        #     print(comerciales, contrasenya)

        menuLogueo(diccionarioAccesoComerciales, listaClientes, listaActividades, listaOportunidades)

    if opcion.lower() == "c":
        print("\tAdios 👋 👋 👋")

    while (opcion.lower() != "a" and opcion.lower() != "b" and opcion.lower() != "c" and diccionarioAccesoComerciales.get(nombre) != password):
        menuLogueo(diccionarioAccesoComerciales, listaClientes, listaActividades, listaOportunidades)


' ESTE ES EL MENÚ PRINCIPAL'

def menuPrincipal(comercial, listaClientes, listaOportunidades):

    opciones = ["a", "b", "c", "d", "e", "f", "g", "h"]

    print("""
    Elige una opcion:\n
    a. Crear clientes
    b. Crear oportunidad y añadirle actividad/es
    c. Crear actividad y añadirla a oportunidad creada
    d. Cambio fase oportunidad
    e. Eliminar oportunidad
    f. Eliminar actividad
    g. Visualizaciones (gráficos, listas de clientes, oportunidades, actividades)
    h. Salir
    """)

    opcion = input(color.BLUE+"Opción: "+color.END)


    if opcion.lower() == "a":
        crearCliente(listaClientes)
        menuPrincipal(comercial, listaClientes, listaOportunidades)

    if opcion.lower() == "b":
        crearOportunidad(comercial, listaOportunidades, listaClientes)
        menuPrincipal(comercial, listaClientes, listaOportunidades)

    if opcion.lower() == "c":
        anyadirActividadOportunidad(listaOportunidades)
        menuPrincipal(comercial, listaClientes, listaOportunidades)

    if opcion.lower() == "d":
        cambioFase(listaClientes, listaOportunidades)
        menuPrincipal(comercial, listaClientes, listaOportunidades)

    if opcion.lower() == "e":
        borrarOportunidad(listaOportunidades)
        menuPrincipal(comercial, listaClientes, listaOportunidades)
    if opcion.lower() == "f":
        borrarActividad(listaOportunidades)
        menuPrincipal(comercial, listaClientes, listaOportunidades)
    if opcion.lower() == "g":
        elegirGrafico(listaClientes,listaOportunidades)
        menuPrincipal(comercial, listaClientes, listaOportunidades)


    if opcion.lower() == "h":
        print("Adios 👋 👋 👋")


    while not opcion.lower() in opciones:
        menuPrincipal(comercial, listaClientes, listaOportunidades)


def validarString(pregunta, longitud):
    dato = input(color.BLUE+pregunta+color.END)

    while len(dato) == 0 or dato.isspace() or len(dato) > longitud:
        if len(dato) == 0 or dato.isspace():
            print(color.RED+"Error, introduce un nombre !!"+color.END)
        if len(dato) > longitud:
            print(color.RED+"Error !!, el nombre no puede tener más de " + str(longitud) + " carácteres !!"+color.END)
        dato = input(color.BLUE+pregunta+color.END)

    return dato.strip()


'''
ARRANCA AQUÍ LA APLICACIÓN LOGUEANDOSE O CREANDO UN COMERCIAL NUEVO CON ACCESO Y LES PASO TODOS LOS ARGUMENTOS (LISTAS) QUE VOY A UTILIZAR EN TODA LA APLICACIÓN Y EN TODOS LOS SUBMENúS QUE los CONTIENE :
'''

menuLogueo(diccionarioAccesoComerciales, listaClientes, listaActividades, listaOportunidades)
