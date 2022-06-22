import csv
import os

from Color import *
from GraficoPresupuestos import *
from Presupuestos import *
from Validaciones import *
from ficheros import *
from Facturas import *
from colorama import init, Fore
init(autoreset=True)



def listarKilometrajeGastosTrabajador(trabajador):

        total=0

        with open("Archivos/nominasTrabajadores.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))


            for fila in reader:

                if trabajador.lower() == fila[0].lower():

                    print()
                    print(color.BLUE+"Estos son los gastos del trabajador "+trabajador.upper()+" "+fila[1].upper()+" en el mes activo: \n\n"+color.END)
                    print(color.BLUE+"Nombre".ljust(15," ")+"Apellidos".ljust(15," ")+"DNI".ljust(17," ")+"Dirección".ljust(30," ")+"Kilometraje mes actual".ljust(25," ")+"Lista de gastos".ljust(60," ")+color.END)
                    print(fila[0].ljust(15," ") + fila[1].ljust(15," ") + fila[2].ljust(17," ")+ fila[3].ljust(30," ")+ fila[4].ljust(25," "),end="")


                    lista2= fila[5][2:-2].replace("'","").split('], [')

                    #lista3=lista2.split(", ")
                    for i in range(0,len(lista2)):
                        lista3=lista2[i].replace("'","").split(", ")

                        for j in range(0,len(lista3)):

                            if j == len(lista3)-1:
                                print(lista3[j]+" €".ljust(20," "),end="")
                                total+=int(lista3[j])
                            else:
                                print(lista3[j].ljust(20," "),end="")

                        print()
                        print("".ljust(102," "),end="")


                    print(color.BLUE+"TOTAL: "+color.END+str(total)+" €".ljust(100," "))
                    print()




def listarGastosTrabajador(trabajador):
        with open("Archivos/nominasTrabajadores.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))


            for fila in reader:
                if trabajador.lower() == fila[0].lower():

                    print()
                    print(color.BLUE+"Estos son los gastos del trabajador "+trabajador.upper()+" "+fila[1].upper()+" en el mes activo: \n"+color.END)
                    print(color.BLUE+"\t"+"Descripción".ljust(24," ")+"Fecha".ljust(24," ")+"Precio".ljust(24," ")+color.END)

                    lista2= fila[5][2:-2].replace("'","").split('], [')
                    total=0
                    #lista3=lista2.split(", ")
                    for i in range(0,len(lista2)):
                        lista3=lista2[i].replace("'","").split(", ")
                        for j in range(0,len(lista3)):
                            if j == len(lista3)-1:
                                print("\t"+lista3[j]+" €".ljust(20," "),end="")
                                total+=int(lista3[j])
                            else:
                                print("\t"+lista3[j].ljust(20," "),end="")

                        print()

                    print(color.BLUE+"\tTOTAL: "+color.END+str(total)+" €")
                    print()



def csvreader_listarTrabajadores():
    contador = 0

    with open("Archivos/trabajadores.csv") as csvfichero:
        #Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header = next(reader)

        print()
        print(color.BLUE+"Elige un trabajador/a: \n"+color.END)

        if header != None:

                for fila in reader:
                    contador +=1
                    print(color.BLUE+"\t"+str(contador).ljust(8," ")+color.END+fila[0].ljust(40," "))

        print()

        return  validarOpcion(contador)






def nombreTrabajador(opcion):
    contador =0
    with open("Archivos/trabajadores.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[0]



def actualizarKilometraje(trabajador):


    if os.path.isfile("Archivos/Coches.csv") and os.stat("Archivos/Coches.csv").st_size and os.path.isfile("Archivos/nominasTrabajadores.csv") and os.stat("Archivos/nominasTrabajadores.csv").st_size > 0:
        nuevoKilometraje=0

        with open("Archivos/Coches.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/Coches.csv","w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)

            for fila in reader:
                if trabajador.lower() == fila[0].lower():
                    print(Fore.GREEN+"El trabajador "+ fila[0].upper()+" tiene actualmente un "+fila[1]+" con "+fila[2]+ " kilómetros totales")
                    pregunta = input(color.BLUE + "Quieres actualizar el kilometraje ? (S/N): " + color.END)
                    while (pregunta.lower()!= "s" and pregunta.lower() != "n"):
                        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                        pregunta = input(color.BLUE + "Quieres actualizar el kilometraje ? (S/N) ? : " + color.END)

                    if pregunta.lower() == "s":
                        nuevoKilometraje= validarIntroducirNumero("Kilometros realizados hasta el último día del mes en activo: ")
                        kilometrajeTotal=nuevoKilometraje+ int(fila[2])
                        fila[2] = str(kilometrajeTotal)

                    writer.writerow(fila)

                else:
                    writer.writerow(fila)


        with open("Archivos/nominasTrabajadores.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/nominasTrabajadores.csv","w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)
            for fila2 in reader:
                if trabajador.lower() == fila2[0].lower():
                    fila2[4]=str(nuevoKilometraje)

                    writer.writerow(fila2)
                else:
                    writer.writerow(fila2)



    if os.stat("Archivos/trabajadores.csv").st_size == 0 or os.stat("Archivos/nominasTrabajadores.csv").st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile("Archivos/trabajadores.csv") or not os.path.isfile("Archivos/nominasTrabajadores.csv"):
         print(color.RED+"Error !!... no existe el archivo "+color.END)


    #Me voy al menú anterior:
    eligeGastos(trabajador)



def gastosComida(trabajador):


    if os.path.isfile("Archivos/nominasTrabajadores.csv") and os.stat("Archivos/nominasTrabajadores.csv").st_size > 0:
        listarGastosTrabajador(trabajador)

        with open("Archivos/nominasTrabajadores.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/nominasTrabajadores.csv","w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)

            for fila in reader:
                if trabajador.lower() == fila[0].lower():

                    pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N): " + color.END)
                    while (pregunta.lower()!= "s" and pregunta.lower() != "n"):
                        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                        pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N) ? : " + color.END)

                    if pregunta.lower() == "s":
                        fecha = validarFecha("Introduce fecha de la comida: ")
                        sitio= validarString("Introducir lugar de la comida: ",20)
                        precio= validarIntroducirNumero("Precio de la comida: ")
                        lista3=fila[5][1:-1].replace('"','')

                        fila[5]="["+lista3+ ", "+ str([sitio.title(),fecha,precio])+"]"
                        fila[5]= fila[5].replace('[Ninguno], ','')


                    writer.writerow(fila)

                else:
                    writer.writerow(fila)



    if os.stat("Archivos/nominasTrabajadores.csv").st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile("Archivos/nominasTrabajadores.csv"):
         print(color.RED+"Error !!... no existe el archivo "+color.END)


    #Me voy al menú anterior:
    eligeGastos(trabajador)



def gastosParking(trabajador):

    if os.path.isfile("Archivos/nominasTrabajadores.csv") and os.stat("Archivos/nominasTrabajadores.csv").st_size > 0:
        listarGastosTrabajador(trabajador)

        with open("Archivos/nominasTrabajadores.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/nominasTrabajadores.csv","w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)


            for fila in reader:
                if trabajador.lower() == fila[0].lower():

                    pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N): " + color.END)
                    while (pregunta.lower()!= "s" and pregunta.lower() != "n"):
                        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                        pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N) ? : " + color.END)

                    if pregunta.lower() == "s":
                        fecha = validarFecha("Introduce fecha del parking: ")
                        sitio= validarString("Introducir nombre del Parking: ",20)
                        precio= validarIntroducirNumero("Precio del parking: ")
                        lista3=fila[5][1:-1].replace('"','')

                        fila[5]="["+lista3+ ", "+ str([sitio.title(),fecha,precio])+"]"
                        fila[5]= fila[5].replace('[Ninguno], ','')


                    writer.writerow(fila)

                else:
                    writer.writerow(fila)



    if os.stat("Archivos/nominasTrabajadores.csv").st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile("Archivos/nominasTrabajadores.csv"):
         print(color.RED+"Error !!... no existe el archivo "+color.END)


    #Me voy al menú anterior:
    eligeGastos(trabajador)

def gastosPernocta(trabajador):


    if os.path.isfile("Archivos/nominasTrabajadores.csv") and os.stat("Archivos/nominasTrabajadores.csv").st_size > 0:
        listarGastosTrabajador(trabajador)

        with open("Archivos/nominasTrabajadores.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/nominasTrabajadores.csv","w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)

            for fila in reader:
                if trabajador.lower() == fila[0].lower():

                    pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N): " + color.END)
                    while (pregunta.lower()!= "s" and pregunta.lower() != "n"):
                        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                        pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N) ? : " + color.END)

                    if pregunta.lower() == "s":
                        fecha = validarFecha("Introduce fecha de la pernocta: ")
                        sitio= validarString("Introducir nombre del hotel: ",20)
                        precio= validarIntroducirNumero("Precio del Hotel: ")
                        lista3=fila[5][1:-1].replace('"','')

                        fila[5]="["+lista3+ ", "+ str([sitio.title(),fecha,precio])+"]"
                        fila[5]= fila[5].replace('[Ninguno], ','')

                    writer.writerow(fila)

                else:
                    writer.writerow(fila)



    if os.stat("Archivos/nominasTrabajadores.csv").st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile("Archivos/nominasTrabajadores.csv"):
         print(color.RED+"Error !!... no existe el archivo "+color.END)


    #Me voy al menú anterior:
    eligeGastos(trabajador)

def gastosOtros(trabajador):

    if os.path.isfile("Archivos/nominasTrabajadores.csv") and os.stat("Archivos/nominasTrabajadores.csv").st_size > 0:
        listarGastosTrabajador(trabajador)


        with open("Archivos/nominasTrabajadores.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/nominasTrabajadores.csv","w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)

            for fila in reader:
                if trabajador.lower() == fila[0].lower():

                    pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N): " + color.END)
                    while (pregunta.lower()!= "s" and pregunta.lower() != "n"):
                        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                        pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N) ? : " + color.END)

                    if pregunta.lower() == "s":
                        fecha = validarFecha("Introduce fecha del gasto: ")
                        sitio= validarString("Introducir motivo: ",50)
                        precio= validarIntroducirNumero("Precio del gasto: ")
                        lista3=fila[5][1:-1].replace('"','')

                        fila[5]="["+lista3+ ", "+ str([sitio.title(),fecha,precio])+"]"
                        fila[5]= fila[5].replace('[Ninguno], ','')


                    writer.writerow(fila)

                else:
                    writer.writerow(fila)


    if os.stat("Archivos/trabajadores.csv").st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile("Archivos/trabajadores.csv"):
         print(color.RED+"Error !!... no existe el archivo "+color.END)


    #Me voy al menú anterior:
    eligeGastos(trabajador)


def eligeGastos(trabajador):

    listaNivel = [1,2,3,4,5,6]

    print(color.BLUE+"Elige los gastos a pasar:\n\n"+color.END+

        color.BLUE+"\t1"+color.END +" Kilometraje\n"+
        color.BLUE+"\t2"+color.END +" Comidas\n"+
        color.BLUE+"\t3"+color.END +" Parking\n"+
        color.BLUE+"\t4"+color.END +" Pernoctar\n"+
        color.BLUE+"\t5"+color.END +" Otros\n"+
        color.BLUE+"\t6"+color.END +" Salir\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        #kilometraje= validarIntroducirNumero("Añade kilómetros recorridos este mes: ")
        actualizarKilometraje(trabajador)

    if opcion == 2:
        gastosComida(trabajador)

    if opcion == 3:
        gastosParking(trabajador)

    if opcion == 4:
        gastosPernocta(trabajador)

    if opcion == 5:
        gastosOtros(trabajador)


def eligeGastosNuevoTrabajador():

    listaNivel = [1,2,3,4,5,6]
    respuesta=""
    listaGastosGeneral=[]


    while respuesta!="n":

        print(color.BLUE+"Elige los gastos a pasar:\n\n"+color.END+

            color.BLUE+"\t1"+color.END +" Kilometraje\n"+
            color.BLUE+"\t2"+color.END +" Comidas\n"+
            color.BLUE+"\t3"+color.END +" Parking\n"+
            color.BLUE+"\t4"+color.END +" Pernoctar\n"+
            color.BLUE+"\t5"+color.END +" Otros\n"+
            color.BLUE+"\t6"+color.END +" Salir\n")

        opcion = validarNumero(listaNivel)

        if opcion == 1:
            kilometraje= validarIntroducirNumero("Añade kilómetros recorridos este mes: ")


        if opcion == 2:
            fecha = validarFecha("Introduce fecha de la comida: ")
            sitio= validarString("Introducir lugar de la comida: ",20)
            precio= validarIntroducirNumero("Precio de la comida: ")
            listaGastosGeneral.append([fecha,sitio,precio])


        if opcion == 3:
            fecha = validarFecha("Introduce fecha del parking: ")
            sitio= validarString("Introducir nombre del Parking: ",20)
            precio= validarIntroducirNumero("Precio del parking: ")
            listaGastosGeneral.append([fecha,sitio,precio])

        if opcion == 4:
            fecha = validarFecha("Introduce fecha de la pernocta: ")
            sitio= validarString("Introducir nombre del hotel: ",20)
            precio= validarIntroducirNumero("Precio del Hotel: ")
            listaGastosGeneral.append([fecha,sitio,precio])

        if opcion == 5:
            fecha = validarFecha("Introduce fecha del gasto: ")
            sitio= validarString("Introducir motivo: ",50)
            precio= validarIntroducirNumero("Precio del gasto: ")
            listaGastosGeneral.append([fecha,sitio,precio])


        respuesta = input(color.BLUE+"Quieres añadir más gastos: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres añadir más gastos ? (s/n) "+color.END)


    return listaGastosGeneral
