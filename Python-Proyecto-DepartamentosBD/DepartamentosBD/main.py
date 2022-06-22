# coding=utf-8

from Color import *
from ConsultasBD import *
from FechaRegistros import *

# Variable global de 'usuario' que necesito pasarle a la función 'menuLogueo()', para que pueda coger el nombre del usuario al salir de la aplicación
usuario= "Nadie"

def menuLogueo(usuario):



    print("""
    Elige una opcion:\n
        a. Iniciar sesión
        b. Crear usuario
        c. Salir
    """)

    opcion = input(color.BLUE+"Opción: "+color.END)
    while (opcion.lower() != "a" and opcion.lower() != "b" and opcion.lower() != "c"):
        print("""
        Elige una opcion:\n
            a. Iniciar sesión
            b. Crear usuario
            c. Salir
        """)

        opcion = input(color.BLUE+"Opción: "+color.END)

    if opcion.lower() == "a":


        usuario = input(color.BOLD+"Usuario: "+color.END)
        contrasenya = input(color.BOLD+"Contraseña: "+color.END)


        while not mostrar_departamentos_usuarios(usuario,contrasenya):
            print(color.RED+"Error !!, no existe el usuario: "+usuario.upper()+color.END)
            usuario = input(color.BOLD+"Usuario: "+color.END)
            contrasenya = input(color.BOLD+"Contraseña: "+color.END)

        #elegirOpcionDepartamento("Archivos/usuarios.txt", usuario, contrasenya)

        # REGISTRO LOGUEO EN LA APLICACION
        registroBD(usuario,"Entrada en la aplicación de PIXELS")

        # Me voy al menú principal
        menuLogueo(usuario)


    if opcion.lower() == "b":

        # Al crear un nuevo usuario/a, tiene que reelenar unos datos para mandarlo por correo al departamento de RRHH y ellos le enviarán por correo usuario/contraseña

        enviarEmailRRHH()

        # REGISTRO LOGUEO EN LA APLICACION
        usuario="Nuevo usuario"
        registroBD(usuario,"Envío de datos de nuevo Usuario/a al departamento de RRHH")


        # Me voy al menú principal
        menuLogueo(usuario)

    if opcion.lower() == "c":

        print("\tAdios 👋 👋 👋")

        # REGISTRO LOGUEO EN LA APLICACION
        if (usuario =="Nadie"):
            registroBD(usuario,"Salida de la aplicación PIXELS directamente")
        else:
            registroBD(usuario,"Salida de la aplicación de PIXELS")

#creacion_todas_tablas_con_datos()

print()
print(Fore.GREEN+"Ahora puedes comentar la función de la línea 78 del 'main'".upper() +" -->  'creacion_todas_tablas_con_datos()',"+" para que no te cree toda la base de datos cada que arrancas la aplicación, como tú veas...".upper())

menuLogueo(usuario)
