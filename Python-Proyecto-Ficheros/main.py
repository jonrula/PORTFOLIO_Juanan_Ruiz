# coding=utf-8
from IPython.display import display
#import cv2
from PIL import Image
from ficheros import *
from Ventas import *


def menuLogueo():
    #cv2.imshow('Imagenes/logoFoto.png',i)
    #cv2.waitKey(0)
    #cv2.destroyAllWindows()

    # Se abre el logo de la empresa, luego la tienes que cerrar para que no te genere una nueva cada vez que arrancas la aplicación
    #i = Image.open('Imagenes/logoFoto.png','r')
    #i.show()
    #display(i)

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


        while not leer_todas_las_lineas2_IniciarSesion("Archivos/usuarios.txt",usuario,contrasenya):
            print(color.RED+"Error !!, no existe el usuario: "+usuario.upper()+color.END)
            usuario = input(color.BOLD+"Usuario: "+color.END)
            contrasenya = input(color.BOLD+"Contraseña: "+color.END)

        elegirOpcionDepartamento("Archivos/usuarios.txt", usuario, contrasenya)

        # Me voy al menú principal
        menuLogueo()


    if opcion.lower() == "b":

        # Al crear un nuevo usuario, creo un un nuevo trabajador con contraseña para la aplicación y con acceso a diferentes departamentos  (se 'conecta' al departamento RRHH)

        csvwriter_crearTrabajador()

        # Me voy al menú principal
        menuLogueo()

    if opcion.lower() == "c":
        print("\tAdios 👋 👋 👋")



menuLogueo()
