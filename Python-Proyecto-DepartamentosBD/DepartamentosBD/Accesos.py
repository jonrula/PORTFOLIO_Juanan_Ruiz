import pymysql
from Color import *
from Compras import *
from FechaRegistros import registroBD
from Ventas import *
from Pagos import *
from Gastos import *
from RRHH import *
from colorama import init, Fore
from Presupuestos import *
init(autoreset=True)



def visualizarTodosLosRegistros(usuario):

    leerArchivo = open("Archivos/Registros/registros.txt", "r")
    contador =0

    # Hallamos los registros que coinciden con las búsquedas:
    for linea in leerArchivo.readlines():
            contador+=1

    print()

    print(color.BLUE+"Hay un total de "+str(contador)+" registros: \n")

    # Volvemos a leer el fichero y sacamos los registros por pantalla (ponemos el contador a 0, para que vaya numerando bien)

    contador =0
    leerArchivo = open("Archivos/Registros/registros.txt", "r")

    for linea in leerArchivo.readlines():
        contador+=1
        print(color.BLUE+str(contador)+color.END +" "+linea)

    leerArchivo.close()

    # Registro BD
    registroBD(usuario,"Control accesos a Pixels --> Visualización de todos los registros")

    # Volvemos al menú anterior:
    MenuAccesos(usuario)



def buscarRegistrosUsuario(usuario):

    buscarUsuario=validarString("Nombre del usuario a buscar: ",20)
    leerArchivo = open("Archivos/Registros/registros.txt", "r")
    contador =0

    # Hallamos los registros que coinciden con las búsquedas:
    for linea in leerArchivo.readlines():
        comprobarUsuario = linea.split(", ")
        if comprobarUsuario[0] == buscarUsuario:
            contador+=1


    print()

    print(color.BLUE+"Hemos encontrado "+str(contador)+" resultados en los registros de PIXELS: \n")

    # Volvemos a leer el fichero y sacamos los registros por pantalla (ponemos el contador a 0, para que vaya numerando bien)

    contador =0
    leerArchivo = open("Archivos/Registros/registros.txt", "r")

    for linea in leerArchivo.readlines():
        comprobarUsuario = linea.split(", ")

        if comprobarUsuario[0] == buscarUsuario:
            contador+=1
            print(color.BLUE+str(contador)+color.END +" "+linea)

    leerArchivo.close()

    # Registro BD
    registroBD(usuario,"Control accesos a Pixels --> Consultas del usuario: "+buscarUsuario.upper())

    # Volvemos al menú anterior:
    MenuAccesos(usuario)



def buscarRegistrosFecha(usuario):

    buscarFecha=validarFecha("Fecha a buscar: ")
    leerArchivo = open("Archivos/Registros/registros.txt", "r")
    contador =0

    # Hallamos los registros que coinciden con las búsquedas:
    for linea in leerArchivo.readlines():
        comprobarFecha = linea.split(", ")
        if comprobarFecha[1] == buscarFecha:
            contador+=1

    print()

    print(color.BLUE+"Hemos encontrado "+str(contador)+" resultados en los registros de PIXELS: \n")

    # Volvemos a leer el fichero y sacamos los registros por pantalla (ponemos el contador a 0, para que vaya numerando bien)

    contador =0
    leerArchivo = open("Archivos/Registros/registros.txt", "r")

    for linea in leerArchivo.readlines():
        comprobarFecha = linea.split(", ")
        if comprobarFecha[1] == buscarFecha:
            contador+=1
            print(color.BLUE+str(contador)+color.END +" "+linea)

    leerArchivo.close()

    # Registro BD
    registroBD(usuario,"Control accesos a Pixels --> Consultas Fecha: "+buscarFecha)

    # Volvemos al menú anterior:
    MenuAccesos(usuario)



def buscarRegistrosConsulta(usuario):

    buscarConsulta=validarString("Consulta a buscar (Palabras clave= INSERT, UPDATE, DELETE, Logueo, salida, pago, impreso, visualización, Control, 'NOMBRE_TABLA' ): ",20)
    leerArchivo = open("Archivos/Registros/registros.txt", "r")
    contador =0

    # Hallamos los registros que coinciden con las búsquedas:
    for linea in leerArchivo.readlines():
        comprobarConsulta = linea.split(", ")
        if buscarConsulta in comprobarConsulta[3]:
            contador+=1

    print()

    print(color.BLUE+"Hemos encontrado "+str(contador)+" resultados en los registros de PIXELS: \n")

    # Volvemos a leer el fichero y sacamos los registros por pantalla (ponemos el contador a 0, para que vaya numerando bien)

    contador =0
    leerArchivo = open("Archivos/Registros/registros.txt", "r")


    for linea in leerArchivo.readlines():
        comprobarConsulta = linea.split(", ")
        if buscarConsulta in comprobarConsulta[3]:
            contador+=1
            print(color.BLUE+str(contador)+color.END +" "+linea)

    leerArchivo.close()

    # Registro BD
    registroBD(usuario,"Control accesos a Pixels --> Consultas palabra clave: "+buscarConsulta)

    # Volvemos al menú anterior:
    MenuAccesos(usuario)




def MenuAccesos(usuario):
    opciones = [1, 2, 3, 4, 5]

    print(color.BLUE + "Elige una opción:\n\n" + color.END +

          color.BLUE + "\t1" + color.END + " Visualizar todos los registros\n" +
          color.BLUE + "\t2" + color.END + " Buscar registro por usuario\n" +
          color.BLUE + "\t3" + color.END + " Buscar registro por fecha\n" +
          color.BLUE + "\t4" + color.END + " Buscar registro por consulta\n" +
          color.BLUE + "\t5" + color.END + " Salir\n")

    opcion = validarNumeroLista(opciones)

    if opcion == 1:
        visualizarTodosLosRegistros(usuario)
    if opcion == 2:
        buscarRegistrosUsuario(usuario)
    if opcion == 3:
        buscarRegistrosFecha(usuario)
    if opcion == 4:
        buscarRegistrosConsulta(usuario)

