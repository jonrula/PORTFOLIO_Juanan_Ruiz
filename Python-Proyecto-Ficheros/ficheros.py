'''
Ficheros en Python
'''
from Compras import mostrarMenuCompra
from RRHH import *
from Ventas import *
from Compras import *
from Pagos import *
from Gastos import *
from colorama import init, Fore
init(autoreset=True)

from Color import *



def leer_todas_las_lineas2_ComprobarExisteUsuario(archivo, usuario):
    leerArchivo = open(archivo, "r")

    for linea in leerArchivo.readlines():
        comprobarUsuario = linea.split(" , ")
        if comprobarUsuario[0].lower() == usuario.lower():
            print(color.RED+"Error !!... el usuario "+ usuario.upper()+ " ya existe !!"+color.END)
            return True


    leerArchivo.close()


def leer_todas_las_lineas2_IniciarSesion(archivo, usuario, contrasenya):
    leerArchivo = open(archivo, "r")

    for linea in leerArchivo.readlines():
        comprobarUsuario = linea.split(" , ")
        if comprobarUsuario[0].lower() == usuario.lower() and comprobarUsuario[1] == contrasenya:
            return True
    leerArchivo.close()



def elegirOpcionDepartamento(archivo, usuario, contrasenya):
    leerArchivo = open(archivo, "r")
    contador =0

    for linea in leerArchivo.readlines():
        comprobarUsuario = linea.split(" , ")

        if comprobarUsuario[0].lower() == usuario.lower() and comprobarUsuario[1] == contrasenya:

            print()

            print(color.BLUE+"Buenos días "+ usuario.upper()+ ", elige un departamento: \n"+color.END)
            for i in range (2, len(comprobarUsuario)):
                contador +=1
                print("\t"+color.BLUE+ str(contador)+color.END+" "+str(comprobarUsuario[i]))


            respuesta = input(color.BLUE+"Escribe el nombre del Departamento: "+color.END)

            print()

            while respuesta.lower() != "ventas" and  respuesta != "compras" and  respuesta != "pagos" and  respuesta != "gastos" and  respuesta != "rrhh":
                print(color.RED+"Error !!... introduce el nombre del departamento !!"+ color.END)
                respuesta = input(color.BLUE+"Escribe el nombre del Departamento: "+color.END)

            if respuesta.lower() == "ventas":
                print(color.BLUE+"Bienvenido al departamento de Ventas"+color.END)
                mostrarMenuVenta()

            if respuesta.lower() == "compras":
                print(color.BLUE+"Bienvenido al departamento de Compras"+color.END)
                mostrarMenuCompra()

            if respuesta.lower()== "pagos":
                print(color.BLUE+"Bienvenido al departamento de Pagos: "+color.END)
                pagarFacturas(usuario)

            if respuesta.lower()== "gastos":
                print(color.BLUE+"Bienvenido al departamento de Gastos: "+color.END)
                eligeGastos(usuario)

            if respuesta.lower()== "rrhh":
                print(color.BLUE+"Bienvenido al departamento de RRHH: "+color.END)
                MenuRRHH()



    leerArchivo.close()


def escribir_varias_lineas(archivo,nombre,contrasenya, departamento):

    archivo = open(archivo, "a+")

    lista = [nombre," , " ,contrasenya," , " ,departamento,"\n"]

    archivo.writelines(lista)


def tipoActividad():
    resultado = ""

    print("Elige Departamento:\n"
          "\t1 Compras\n"
          "\t2 Ventas\n"
          "\t3 Salir"
          )

    opcion = int(input("Opcion: "))
    while opcion != 3:
        if (opcion == 1):
            resultado = "Compras"
            print("Has añadido el departamento COMPRAS")
        if (opcion == 2):
            resultado = "Ventas"
            print("Has añadido el departamento VENTAS")

        opcion = int(input("Quieres añadir mas departamentos? Opcion: "))

    return resultado


# Estas funciones al final no las he utilizado, para crear un usuario le mando directamente a la funcion de 'crear un trabajador' en el departamento de RRHH
def crearUsuarioConDepartamentos(archivo,nombre,apellido,contrasenya):

    archivo = open(archivo, "a+")
    lista = [nombre," , ",contrasenya,]


    print()

    print("Estos son los departamentos que existen:\n\n"+
          color.BLUE+"\tCompras\n"
          "\tVentas\n"+color.END)

    opcion1 = False
    opcion2 = False


    while not opcion1 and not opcion2:

        compras = input(color.BLUE + "Quieres añadir al departamento COMPRAS ? (S/N): " + color.END)
        while (compras!= "s" and compras != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            compras = input(color.BLUE + "Quieres añadir al departamento COMPRAS ? (S/N): " + color.END)


        ventas = input(color.BLUE + "Quieres añadir al departamento VENTAS ? (S/N): " + color.END)
        while (ventas!= "s" and ventas != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            compras = input(color.BLUE + "Quieres añadir al departamento VENTAS ? (S/N): " + color.END)

        print()

        if (compras.lower() =="s" and ventas.lower() == "s"):
            resultado = " , Compras , Ventas\n"
            lista.append(resultado)
            print(Fore.GREEN+"Se ha creado correctamente el comercial: "+nombre.upper()+ " "+apellido.upper()+ " con acceso a los departamentos: COMPRAS y VENTAS ")
            opcion1 = True
            opcion2 = True


        if (compras.lower() =="s" and ventas.lower() == "n"):
            resultado = " , Compras\n"
            lista.append(resultado)
            print(Fore.GREEN+"Se ha creado correctamente el comercial: "+nombre.upper()+ " "+apellido.upper()+ " con acceso al departamento: COMPRAS")
            opcion1 = True
            opcion2 = False


        if (compras.lower() =="n" and ventas.lower() == "s"):
            resultado = " , Ventas\n"
            lista.append(resultado)
            print(Fore.GREEN+"Se ha creado correctamente el comercial: "+nombre.upper()+ " "+apellido.upper()+ " con acceso al departamento: VENTAS")
            opcion1 = False
            opcion2 = True

        if not opcion1 and not opcion2:
            print(color.RED + "Error !... debes elegir como mínimo una opción !!" + color.END)
            print()



    archivo.writelines(lista)


