import csv
from Color import *
from Validaciones import *
from colorama import init, Fore
init(autoreset=True)
import os

# Aquí llegan los clientes que tienen pendientes pagos de facturas, les pongo acceso a la plaicacion a la hora de crear el cliente también le doy acceso con el nombre de cliente y el pasword es el DNI:

def pagarFacturas(usuario):

    respuesta=""

    with open("Archivos/pedidoVentas.csv") as csvfichero:
        # Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',', quoting=csv.QUOTE_NONNUMERIC, strict=True)

        while respuesta != "n" :
            contador =0

            for fila in reader:
                if fila[1].lower() == usuario.lower() and fila[10] == "Pendiente" :
                    contador += 1
                    if contador == 1:
                        print()
                        print(color.BLUE+"\t" +"Opcion".ljust(8," ")+ "Número Pedido".ljust(15," ")+"Nombre".ljust(10," ")+"Fecha".ljust(12," ")+"Número de productos".ljust(20," ")+"Precio total".ljust(14," ")+"Estado del pago".ljust(16," ")+color.END)
                    print(color.BLUE + "\t" + str(contador).ljust(8," ") + color.END + fila[0].ljust(15," ") + fila[1].ljust(10," ") + fila[7].ljust(12," ") + str(len(fila[8][0:-1].rstrip("\r\n").split("]"))-1).ljust(20," ") +  fila[9].ljust(5," ")+" €".ljust(9," ") +  color.RED+fila[10].ljust(13," ")+color.END)

            if contador == 0:
                print(color.RED+usuario.upper()+", no tienes pagos pendientes !!"+color.END)
                break
            else:

                cont=0

                print()

                opcion = validarOpcion(contador)
                tarjeta = validarTarjetaCredito()
                importe = validarIntroducirNumero("Introduce importe a pagar: ")


                with open("Archivos/pedidoVentas.csv", "r") as csvfichero:
                    reader = list(csv.reader(csvfichero))

                with open("Archivos/pedidoVentas.csv", "w") as csvfichero:
                    writer = csv.writer(csvfichero, quoting=csv.QUOTE_NONNUMERIC)
                    for fila in reader:
                        if fila[1].lower() == usuario.lower() and fila[10] == "Pendiente":
                            cont+=1
                            if cont == opcion and int(importe) != int(fila[9]):

                                while importe != int(fila[9]):

                                    print(color.RED+"Importe incorrecto !! tienes que introducir: "+str(fila[9])+" €"+color.END)
                                    importe = validarIntroducirNumero("Introduce importe a pagar: ")

                            if  cont == opcion and int(importe) == int(fila[9]):
                                fila[10] = "Pagado"
                                print(Fore.GREEN+"La Factura Nº "+fila[0]+ " ha sido abonada correctamente")

                                writer.writerow(fila)
                            else:
                                writer.writerow(fila)

                        else:
                            writer.writerow(fila)


                print()

                print(color.BLUE+usuario.upper()+", así quedan tus pagos: \n")

                print(color.BLUE+"\tNúmero Pedido".ljust(15," ")+"Nombre".ljust(10," ")+"Fecha".ljust(12," ")+"Número de productos".ljust(20," ")+"Precio total".ljust(14," ")+"Estado del pago".ljust(16," ")+color.END)

                for fila in reader:
                    if fila[1].lower() == usuario.lower() and fila[10] == "Pendiente" :
                        contador += 1
                        print("\t" +  color.END + fila[0].ljust(15," ") + fila[1].ljust(10," ") + fila[7].ljust(12," ") + str(len(fila[8][0:-1].rstrip("\r\n").split("]"))-1).ljust(20," ") +  fila[9].ljust(5," ")+" €".ljust(9," ") +  color.RED+fila[10].ljust(13," ")+color.END)

                    if fila[1].lower() == usuario.lower() and fila[10] == "Pagado" :
                        contador += 1
                        print("\t" + color.END + fila[0].ljust(15," ") + fila[1].ljust(10," ") + fila[7].ljust(12," ") + str(len(fila[8][0:-1].rstrip("\r\n").split("]"))-1).ljust(20," ") +  fila[9].ljust(5," ")+" €".ljust(9," ") +  Fore.GREEN+fila[10].ljust(13," "))

                print()


            respuesta = input(color.BLUE+"Quieres hacer más pagos ? (s/n): "+color.END)
            while (respuesta.lower() != "s" and respuesta.lower() != "n"):
                    print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                    respuesta = input(color.BLUE+"Quieres hacer más pagos ? (s/n) "+color.END)
