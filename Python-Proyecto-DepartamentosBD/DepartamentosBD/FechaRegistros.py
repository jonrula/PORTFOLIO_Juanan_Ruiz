from datetime import date, time, datetime
import pymysql
from Color import *
import csv
from Compras import *
from colorama import init, Fore
init(autoreset=True)

def fechaActual():

        return str(datetime.now().day)+"/"+str(datetime.now().month)+"/"+str(datetime.now().year)


def horaActual():

        return str(datetime.now().hour)+":"+str(datetime.now().minute)+":"+str(datetime.now().second)


def registroBD(usuario,consulta):

        # Registro de cada consulta a la base de datos
        fecha = fechaActual()
        hora = horaActual()

        archivo = open("Archivos/Registros/registros.txt", "a+")
        lista=usuario+ ", "+fechaActual()+", "+horaActual()+", "+consulta +"\n"
        archivo.writelines(lista)

#today="12/12/2014"
#print(datetime.strptime(today, "%d/%m/%Y").strftime('%Y'))

#d=datetime.now()
#print(today.strftime('%Y'))




