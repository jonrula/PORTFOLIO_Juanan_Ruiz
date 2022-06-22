from GraficoFacturas import *
from Validaciones import *
from ficheros import *
from Facturas import *
from Logueo import *
from colorama import init, Fore
init(autoreset=True)
import csv
import os

def csvreader_ComprobarExisteCliente(dni):
    with open("Archivos/clientes.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',
                            quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            if fila[2] == dni:
                #print(fila[2])
                return True



def csvwriter_crearCliente():
    print(color.BLUE+"Introduce los datos de los nuevos clientes: "+color.END)
    respuesta =""

    while respuesta !="n":

        dni = validarDNI()

        while not DNIesCorrecto(dni):
            print(color.RED+"DNI incorrecto"+color.END)
            dni = validarDNI()

        while csvreader_ComprobarExisteCliente(dni):
            print(color.RED+"Error !!... ya existe un cliente con el DNI: "+ dni +color.END)
            dni = validarDNI()

        nombre = validarString("Nombre: ",20)
        apellidos = validarString("Apellidos: ",20)
        direccion = validarString("Dirección: ",20)
        telefono = validarTelefono()
        email= validarEmail()

        # Inicializo una variable de tipo String y le concateno todas las variables, a las que a cada una les quitos los posibles intros y saltos de línea --> 'rstrip("\r\n")' y quitar la separación den carácteres por coma --> 'split(',')'

        nuevosDatos = nombre.title().rstrip("\r\n").split(',') + apellidos.title().rstrip("\r\n").split(',')+dni.rstrip("\r\n").split(',')+direccion.capitalize().rstrip("\r\n").split(',')+telefono.rstrip("\r\n").split(',')+email.rstrip("\r\n").split(',')

        mi_fichero = open("Archivos/clientes.csv", 'a',newline='')

        with mi_fichero:
            writer = csv.writer(mi_fichero,quoting=csv.QUOTE_NONNUMERIC)
            writer.writerow(nuevosDatos)  # IMPORTANTE --> hay que poner solo 'writerow' para que escriba UNA SOLA FILA


        password =dni
        archivo = open("Archivos/usuarios.txt", "a+")
        lista = [nombre," , ",password," , Pagos\r"]
        archivo.writelines(lista)


        print(Fore.GREEN+"Se ha añadido correctamente al cliente "+nombre.upper()+ " "+ apellidos.upper()+" al departamento de pagos")

        respuesta = input(color.BLUE+"Quieres añadir más clientes: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres añadir más clientes: ? (s/n) "+color.END)


    #Me voy al menú anterior:
    mostrarMenuVenta()

def csvreader_listarClientes():
    contador = 0
    with open("Archivos/clientes.csv") as csvfichero:
        #Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)
        print()

        print(color.BLUE+"Elige un cliente: \n"+color.END)

        if header!= None:

            for fila in reader:
                contador +=1
                print(color.BLUE+"\t"+str(contador)+color.END+ " " +fila[0])


        print()

        return  validarOpcion(contador)



def mostrarNombreApellidosDniDireccionTelefonoEmailCliente():
    opcion= csvreader_listarClientes()
    contador =0

    with open("Archivos/clientes.csv") as csvfichero:
        #Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)

        if header!= None:

            for fila in reader:
                contador +=1
                if(contador == opcion):
                    return fila[0]+","+fila[1]+","+fila[2]+","+fila[3]+","+fila[4]+","+fila[5]

def cambiarNombreCliente(opcion):
    contador =0
    with open("Archivos/clientes.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[0]

def cambiarApellidosCliente(opcion):
    contador =0
    with open("Archivos/clientes.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[1]

def cambiarDniCliente(opcion):
    contador =0
    with open("Archivos/clientes.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[2]

def cambiarDireccionCliente(opcion):
    contador =0
    with open("Archivos/clientes.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[3]

def cambiarTelefonoCliente(opcion):
    contador =0
    with open("Archivos/clientes.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[4]

def cambiarEmailCliente(opcion):
    contador =0
    with open("Archivos/clientes.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[5]




def csvreader_listarPedidoVentas():
    contador =0
    with open("Archivos/pedidoVentas.csv") as csvfichero:
    # Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',', quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)

        if header != None:


            print(color.BLUE+"\t" +"Opcion".ljust(8," ")+ "Número Pedido".ljust(15," ")+"Nombre".ljust(20," ")+"Fecha".ljust(15," ")+"Número de productos".ljust(25," ")+"Precio total".ljust(19," ")+"Estado del pago".ljust(16," ")+color.END)
            print()

            for fila in reader:

                contador += 1

                if fila[10] == "Pendiente" :
                    print(color.BLUE + "\t" + str(contador).ljust(8," ") + color.END + fila[0].ljust(15," ") + fila[1].ljust(20," ") + fila[7].ljust(15," ") + str(len(fila[8][0:-1].rstrip("\r\n").split("]"))-1).ljust(25," ") +  fila[9].ljust(5," ")+" €".ljust(14," ") +  color.RED+fila[10].ljust(13," ")+color.END)

                if fila[10] == "Pagado" :
                    print(color.BLUE + "\t" + str(contador).ljust(8," ") + color.END + fila[0].ljust(15," ") + fila[1].ljust(20," ") + fila[7].ljust(15," ") + str(len(fila[8][0:-1].rstrip("\r\n").split("]"))-1).ljust(25," ") +  fila[9].ljust(5," ")+" €".ljust(14," ") +  Fore.GREEN+fila[10].ljust(13," ")+color.END)
        print()

        return validarOpcion(contador)


def csvreader_listarProductosVentas():
    contador = 0

    with open("Archivos/productosVentas.csv") as csvfichero:
        #Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header = next(reader)

        print()
        print(color.BLUE+"Elige un producto: \n"+color.END)
        print(color.BLUE+"\t" +"Opcion".ljust(8," ")+ "Producto".ljust(40," ")+"Descripción".ljust(70," ")+"Precio unitario".ljust(12," "))

        if header != None:

                for fila in reader:
                    contador +=1
                    print(color.BLUE+"\t"+str(contador).ljust(8," ")+color.END+fila[0].ljust(40," ")+ fila[1].ljust(70," ")+fila[2]+" €".ljust(12," "))

        print()

        return  validarOpcion(contador)




def csvreader_listarTodosPedidoVentas():
    contador =0
    with open("Archivos/pedidoVentas.csv") as csvfichero:
    # Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',', quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)
        print()

        print(color.BLUE+"\t" +"Opcion".ljust(8," ")+ "Número Pedido".ljust(15," ")+"Nombre".ljust(20," ")+"Fecha".ljust(17," ")+"Número de productos".ljust(110," ")+"Precio total".ljust(14," ")+"Estado del pago".ljust(16," ")+color.END)
        print()

        if header != None:
            for fila in reader:

                lista2= fila[8][2:-2].split('], [')
                contador += 1
                print(color.BLUE + "\t" + str(contador).ljust(8," ") + color.END + fila[0].ljust(15," ") + fila[1].ljust(20," ") + fila[7].ljust(17," "),end="")
                if len(lista2) == 1:
                    print(str(lista2[0]).ljust(110," "),end="")
                if len(lista2) == 2:
                    print(str(lista2[0]).ljust(110," "))
                    print("                                                                ",end="")
                    print(str(lista2[1]).ljust(110," "),end="")
                if len(lista2) == 3:
                    for i in range (0, len(lista2)):

                        if i ==0:
                            print(str(lista2[i]).ljust(110," "))
                        if i ==1:
                            print("                                                                ",end="")
                            print(str(lista2[i]).ljust(110," "))
                        if i== len(lista2)-1:
                            print("                                                                ",end="")
                            print(str(lista2[i]).ljust(110," "),end="")

                if len(lista2) > 3:
                    for i in range (0, len(lista2)):

                        if i ==0:
                            print(str(lista2[i]).ljust(110," "))
                        if i >0 and i <len(lista2)-1:
                            print("                                                                ",end="")
                            print(str(lista2[i]).ljust(110," "))

                        if i== len(lista2)-1:
                            print("                                                                ",end="")
                            print(str(lista2[i]).ljust(110," "),end="")

                if fila[10] == "Pendiente" :
                    print(fila[9].ljust(5," ")+" €".ljust(9," ") + color.RED+fila[10].ljust(13," ")+color.END)

                if fila[10] == "Pagado" :
                    print(fila[9].ljust(5," ")+" €".ljust(9," ") +  Fore.GREEN+fila[10].ljust(13," ")+color.END)

        print()


        return validarOpcion(contador)




def csvreader_listarPedidoVentasPendientes():
    contador =0
    with open("Archivos/pedidoVentas.csv") as csvfichero:
    # Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',', quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)
        print()

        print(color.BLUE+"\t" +"Opcion".ljust(8," ")+ "Número Pedido".ljust(15," ")+"Nombre".ljust(20," ")+"Fecha".ljust(17," ")+"Número de productos".ljust(110," ")+"Precio total".ljust(14," ")+"Estado del pago".ljust(16," ")+color.END)
        print()

        if header != None:
            for fila in reader:

                if fila[10]=="Pendiente":

                    lista2= fila[8][2:-2].split('], [')
                    contador += 1
                    print(color.BLUE + "\t" + str(contador).ljust(8," ") + color.END + fila[0].ljust(15," ") + fila[1].ljust(20," ") + fila[7].ljust(17," "),end="")
                    if len(lista2) == 1:
                        print(str(lista2[0]).ljust(110," "),end="")
                    if len(lista2) == 2:
                        print(str(lista2[0]).ljust(110," "))
                        print("                                                                ",end="")
                        print(str(lista2[1]).ljust(110," "),end="")
                    if len(lista2) == 3:
                        for i in range (0, len(lista2)):

                            if i ==0:
                                print(str(lista2[i]).ljust(110," "))
                            if i ==1:
                                print("                                                                ",end="")
                                print(str(lista2[i]).ljust(110," "))
                            if i== len(lista2)-1:
                                print("                                                                ",end="")
                                print(str(lista2[i]).ljust(110," "),end="")

                    if len(lista2) > 3:
                        for i in range (0, len(lista2)):

                            if i ==0:
                                print(str(lista2[i]).ljust(110," "))
                            if i >0 and i <len(lista2)-1:
                                print("                                                                ",end="")
                                print(str(lista2[i]).ljust(110," "))

                            if i== len(lista2)-1:
                                print("                                                                ",end="")
                                print(str(lista2[i]).ljust(110," "),end="")

                    if fila[10] == "Pendiente" :
                        print(fila[9].ljust(5," ")+" €".ljust(9," ") + color.RED+fila[10].ljust(13," ")+color.END)

                    if fila[10] == "Pagado" :
                        print(fila[9].ljust(5," ")+" €".ljust(9," ") +  Fore.GREEN+fila[10].ljust(13," ")+color.END)

        print()


        return validarOpcion(contador)






def mostrarProductosVentas():
    opcion= csvreader_listarProductosVentas()
    contador =0

    with open("Archivos/productosVentas.csv") as csvfichero:
        #Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header = next(reader)

        if header != None:

            for fila in reader:
                contador +=1
                if(contador == opcion):
                    return str(fila[0] + ","+fila[1] + ","+ fila[2]).rstrip("\r\n").split(',')

def buscarNumeroPedidoMasAlto(archivo):
    pedidoMasAlto =0

    with open(archivo) as csvfichero:
        #Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header = next(reader)

        if header != None:

            for fila in reader:
                buscarNumero = int(fila[0])
                if(buscarNumero > pedidoMasAlto):
                    pedidoMasAlto = buscarNumero

        return pedidoMasAlto



def csvwriter_crearPedidoVenta():
    print(color.BLUE+"Introduce los datos para el pedido de venta: "+color.END)
    respuesta =""
    respuesta2=""
    listaProductosVentas = []
    total =0


    while respuesta !="n":

        # Elegir cliente:
        datosCliente= mostrarNombreApellidosDniDireccionTelefonoEmailCliente()

        # Fecha pedido:
        fecha = validarFecha("Fecha pedido: ")

        # Inicializo una variable de tipo String y le concateno todas las variables, a las que a cada una les quitos los posibles intros y saltos de línea --> 'rstrip("\r\n")' y quitar la separación den carácteres por coma --> 'split(',')'
        # Tener en cuenta que a la hora de concatenar --> si concateno con una coma ',': me crea una lista, si pongo '+' ,me concatena como una lista de elementos dentro de la fila del archivo.csv
        respuesta2="s"
        while respuesta2 !="n":

            # Elijo productos
            productoVentas = mostrarProductosVentas()

            # Calculo los subtotales del producto
            horas= validarIntroducirNumero("Introducir horas o unidades: ")
            subTotal= int(horas) * int(productoVentas[2])

            # Añado los elementos hora y el subtotal a la lista 'productoVentas'  que al final es una nueva fila de pedido dentro de la lista 'listaProductosVentas.', que es la la fatura total. Es una lista (factura) llena de otras listas (lineas de pedidos, con sus subtotales)
            productoVentas.append(horas)
            productoVentas.append(subTotal)
            listaProductosVentas.append(productoVentas)

            # Voy calculando el valor total de la factura, por defecto, dentro del archivo.csv, 'productoVentas' ya es una lista y puedo acceder al elemento
            total += int(productoVentas[4])

            # Después de calcular el valor del total de la lista, le añado los símbolos '€' para sacarlos por pantalla, porque ya no va a calcular en ese elemento y ahora puedo hacerlo, antes no, porque no podría hacer el casting de 'int'
            productoVentas[2] = productoVentas[2] +" €"
            #productoVentas[3] = str(productoVentas[3]) +" Unidades/horas"
            #productoVentas[4] = str(productoVentas[4]) +" €"

            respuesta2 = input(color.BLUE+"Quieres añadir más productos: ? (s/n) "+color.END)
            while (respuesta2.lower() != "s" and respuesta2.lower() != "n"):
                print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                respuesta2 = input(color.BLUE+"Quieres añadir más productos ? (s/n) "+color.END)

        # Después de salir del bucle de hacer toda la cesta de productos, calculo el total de toda la factura:
        totalPedido = str(total)
        pago= "Pendiente"
        numeroPedido= buscarNumeroPedidoMasAlto("Archivos/pedidoVentas.csv") +1


        # Concateno en un string, los diversos elementos que corresponden alas columnas, teniendo en cuenta que le añado una lista de listas que es la factura: cada lista inerior es un producto y al final de la lista el total de la cesta:
        nuevoPedidoVentas = str(numeroPedido).rstrip("\r\n").split(',') + datosCliente.rstrip("\r\n").split(',') + fecha.rstrip("\r\n").split(',') + [listaProductosVentas] + totalPedido.rstrip("\r\n").split(',') + pago.rstrip("\r\n").split(',')


        # Escribo la nueva linea 'nuevoPedidoVentas' al archivo.csv:
        mi_fichero = open("Archivos/pedidoVentas.csv", 'a',newline='')
        with mi_fichero:
            writer = csv.writer(mi_fichero,quoting=csv.QUOTE_NONNUMERIC)
            writer.writerow(nuevoPedidoVentas)  # IMPORTANTE --> hay que poner solo 'writerow' para que escriba UNA SOLA FILA


        print(Fore.GREEN+"El fichero se ha escrito correctamente")


        respuesta = input(color.BLUE+"Quieres añadir más pedidos de ventas: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres añadir más pedidos de ventas ? (s/n) "+color.END)


    #Me voy al menú anterior:
    mostrarMenuVenta()

def borrarLineaCSV(archivo):

    respuesta =""
    pedido=""

    while os.path.isfile(archivo) and respuesta !="n" and os.stat(archivo).st_size > 0:

        opcion= csvreader_listarPedidoVentas() +1
        contador = 0

        with open(archivo,"r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open(archivo,"w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)
            for fila in reader:
                contador +=1
                if contador != opcion:
                    writer.writerow(fila)
                if contador == opcion:
                    pedido = fila[0]

            print(Fore.GREEN+"El pedido "+pedido+" ha sido borrado !!")

        respuesta = input(color.BLUE+"Quieres borrar más registros: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres borrar más registros ? (s/n) "+color.END)


    if os.stat(archivo).st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile(archivo):
         print(color.RED+"Error !!... no existe el archivo "+color.END)

    #Me voy al menú anterior:
    mostrarMenuVenta()

def editarLineaCSV():

    respuesta=""
    respuesta2=""
    listaProductosVentas=[]
    total =0

    while os.path.isfile("Archivos/productosVentas.csv") and respuesta !="n" and os.stat("Archivos/productosVentas.csv").st_size > 0:

        print()

        # Solo edito la lista de los pagos que estan pendientes, los pagados NO se tocan.

        #opcionPedidoVentas= csvreader_listarTodosPedidoVentas() +1


        opcionPedidoVentas=csvreader_listarPedidoVentasPendientes()
        contador=0

        with open("Archivos/pedidoVentas.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/pedidoVentas.csv","w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)


            for fila in reader:
                if fila[10]=="Pendiente":
                    contador+=1
                    if contador == opcionPedidoVentas:
                        datos = input(color.BLUE + "Quieres cambiar los DATOS DEL CLIENTE ? (S/N): " + color.END)
                        while (datos.lower()!= "s" and datos.lower() != "n"):
                            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                            datos = input(color.BLUE + "Quieres cambiar los DATOS DEL CLIENTE ? (S/N) ? : " + color.END)

                        if datos.lower() == "s":
                            opcionCliente= csvreader_listarClientes() +1

                            fila[1]=cambiarNombreCliente(opcionCliente)
                            fila[2]=cambiarApellidosCliente(opcionCliente)
                            fila[3]=cambiarDniCliente(opcionCliente)
                            fila[4]=cambiarDireccionCliente(opcionCliente)
                            fila[5]=cambiarTelefonoCliente(opcionCliente)
                            fila[6]=cambiarEmailCliente(opcionCliente)

                        fecha = input(color.BLUE + "Quieres cambiar la FECHA ? (S/N): " + color.END)
                        while (fecha.lower()!= "s" and fecha.lower() != "n"):
                            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                            fecha = input(color.BLUE + "Quieres cambiar la FECHA ? (S/N) ? : " + color.END)

                        if fecha.lower() =="s":
                            nuevaFecha = validarFecha("Nueva Fecha: ")
                            fila[7] = nuevaFecha

                        listaProductos = input(color.BLUE + "Quieres cambiar la LISTA DE PRODUCTOS ? (S/N): " + color.END)
                        while (listaProductos.lower()!= "s" and listaProductos.lower() != "n"):
                            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                            listaProductos = input(color.BLUE + "Quieres cambiar la LISTA DE PRODUCTOS ? (S/N) ? : " + color.END)

                        if listaProductos.lower() == "s":

                            while respuesta2 !="n":

                                # Elijo productos
                                productoVentas = mostrarProductosVentas()

                                # Calculo los subtotales del producto
                                horas= validarIntroducirNumero("Introducir horas o unidades: ")
                                subTotal= int(horas) * int(productoVentas[2])

                                # Añado los elementos hora y el subtotal a la lista 'productoVentas'  que al final es una nueva fila de pedido dentro de la lista 'listaProductosVentas.', que es la la fatura total. Es una lista (factura) llena de otras listas (lineas de pedidos, con sus subtotales)
                                productoVentas.append(horas)
                                productoVentas.append(subTotal)
                                listaProductosVentas.append(productoVentas)

                                # Voy calculando el valor total de la factura, por defecto, dentro del archivo.csv, 'productoVentas' ya es una lista y puedo acceder al elemento
                                total += int(productoVentas[4])

                                # Después de calcular el valor del total de la lista, le añado los símbolos '€' para sacarlos por pantalla, porque ya no va a calcular en ese elemento y ahora puedo hacerlo, antes no, porque no podría hacer el casting de 'int'
                                productoVentas[2] = productoVentas[2] +" €"
                                #productoVentas[3] = str(productoVentas[3]) +" Unidades/horas"
                                productoVentas[4] = str(productoVentas[4]) +" €"

                                respuesta2 = input(color.BLUE+"Quieres añadir más productos: ? (s/n) "+color.END)
                                while (respuesta2.lower() != "s" and respuesta2.lower() != "n"):
                                    print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                                    respuesta2 = input(color.BLUE+"Quieres añadir más productos ? (s/n) "+color.END)

                             # Después de salir del bucle de hacer toda la cesta de productos, calculo el total de toda la factura:
                            totalPedido = str(total)

                            fila[8] = listaProductosVentas
                            fila[9] = totalPedido

                        writer.writerow(fila)

                    else:
                        writer.writerow(fila)
                else:
                     writer.writerow(fila)



        respuesta = input(color.BLUE+"Quieres editar más registros: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
                print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                respuesta = input(color.BLUE+"Quieres editar más registros ? (s/n) "+color.END)


    if os.stat("Archivos/productosVentas.csv").st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile("Archivos/productosVentas.csv"):
         print(color.RED+"Error !!... no existe el archivo "+color.END)


    #Me voy al menú anterior:
    mostrarMenuVenta()

def comprobarExisteProducto(producto):

    with open("Archivos/productosVentas.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            if fila[0] == producto:
                return True


def crearProductoVentas():

    respuesta=""

    while os.path.isfile("Archivos/productosVentas.csv") and respuesta !="n" and os.stat("Archivos/productosVentas.csv").st_size > 0:

        producto= validarString("Producto: ", 50)
        while comprobarExisteProducto(producto):
            print(color.RED+"Error !!... ya existe el producto: "+ producto.upper() +color.END)
            producto= validarString("Producto: ", 50)

        descripcion= validarString("Descripción: ", 100)
        precio=validarIntroducirNumero("Precio unidad: ")

        nuevosDatos = producto.title().rstrip("\r\n").split(',') + descripcion.capitalize().rstrip("\r\n").split(',')+str(precio).rstrip("\r\n").split(',')

        mi_fichero = open("Archivos/productosVentas.csv", 'a',newline='')

        with mi_fichero:
            writer = csv.writer(mi_fichero,quoting=csv.QUOTE_NONNUMERIC)
            writer.writerow(nuevosDatos)  # IMPORTANTE --> hay que poner solo 'writerow' para que escriba UNA SOLA FILA

        print(Fore.GREEN+"El fichero se ha escrito correctamente")

        respuesta = input(color.BLUE+"Quieres editar más registros: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
                print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                respuesta = input(color.BLUE+"Quieres editar más registros ? (s/n) "+color.END)


    if os.stat("Archivos/productosVentas.csv").st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile("Archivos/productosVentas.csv"):
         print(color.RED+"Error !!... no existe el archivo "+color.END)


    #Me voy al menú anterior:
    mostrarMenuVenta()


def verFactura(numeroFactura,fecha,cliente,apellido,dni,direccion,telefono,email,lista,total,pago):


    c = canvas.Canvas("Facturas/factura"+numeroFactura+".pdf",pagesize=(200,280),bottomup=0)
    # Logo Section
    # Setting th origin to (10,40)
    c.translate(10,40)
    # Inverting the scale for getting mirror Image of logo
    c.scale(1,-1)
    # Inserting Logo into the Canvas at required position
    c.drawImage("Imagenes/logoFoto.png",0,0,width=50,height=30)
    # Title Section
    # Again Inverting Scale For strings insertion
    c.scale(1,-1)
    # Again Setting the origin back to (0,0) of top-left
    c.translate(-10,-40)
    # Setting the font for Name title of company
    c.setFont("Helvetica-Bold",10)
    # Inserting the name of the company
    c.drawCentredString(125,20,"Pixels")
    # For under lining the title
    c.line(70,22,180,22)

    # Changing the font size for Specifying Address
    c.setFont("Helvetica-Bold",5)

    c.drawCentredString(125,30,"PIXELS")
    c.drawCentredString(125,35,"Vitoria-Gasteiz ARABA")
    # Changing the font size for Specifying GST Number of firm
    c.setFont("Helvetica-Bold",6)
    c.drawCentredString(125,42,"NIF: 16296028E")
    # Line Seprating the page header from the body
    c.line(5,45,195,45)
    # Document Information
    # Changing the font for Document title
    c.setFont("Courier-Bold",8)
    c.drawCentredString(100,55,"Factura")
    # This Block Consist of Costumer Details
    c.roundRect(15,63,170,42,10,stroke=1,fill=0)

    c.setFont("Times-Bold",4)
    c.drawRightString(45,70,"Factura: ")
    c.drawRightString(45,80,"Fecha: ")
    c.drawRightString(45,90,"Cliente : ")
    c.drawString(87,90,"DNI : ")
    c.drawRightString(45,100,"Dirección : ")
    c.drawString(87,100,"Teléfono : ")
    c.drawString(128,100,"Email : ")

    if pago == "Pendiente":
        c.drawImage("Imagenes/Pendiente.jpg",120,140,width=60,height=35)

    elif pago == "Pagado":
        c.drawImage("Imagenes/Pagado.jpg",120,140,width=60,height=35)

    c.setFont("Courier",3)
    c.drawString(45,70,numeroFactura)
    c.drawString(45,80,fecha)
    c.drawString(45,90,cliente+ " "+apellido)
    c.drawString(97,90,dni)
    c.drawString(45,100,direccion)
    c.drawString(105,100,telefono)
    c.drawString(143,100,email)

    # This Block Consist of Item Description
    c.roundRect(15,110,170,150,10,stroke=1,fill=0)
    c.line(15,120,185,120)

    c.setFont("Times-Bold",5)

    c.drawCentredString(30,118,"Producto")
    c.drawCentredString(85,118,"Descripción")
    c.drawCentredString(125,118,"Precio")
    c.drawCentredString(148,118,"Cantidad")
    c.drawCentredString(173,118,"TOTAL")



    c.setFont("Courier",3)
    lineaNueva =0

    if len(lista) >1 :



        for i in range(0,len(lista)):
            lista3=lista[i].replace("'","").split(", ")

            lineaNueva +=5

            c.drawString(17,122+lineaNueva,lista3[0])
            c.drawString(62,122+lineaNueva,lista3[1])
            c.drawString(120,122+lineaNueva,lista3[2])
            c.drawString(145,122+lineaNueva,lista3[3])
            c.drawString(163,122+lineaNueva,lista3[4]+" €")
            c.drawString(163,217,total)

    else:

        lista3=lista[0].replace("'","").split(", ")



        c.drawString(17,127,lista3[0])
        c.drawString(65,127,lista3[1])
        c.drawString(120,127,lista3[2])
        c.drawString(145,127,lista3[3])
        c.drawString(168,127,lista3[4]+" €")
        c.drawString(168,217,total)


    # Drawing table for Item Description
    c.line(15,210,185,210)
    c.line(60,110,60,220)
    c.line(115,110,115,220)
    c.line(135,110,135,220)
    c.line(160,110,160,220)
    # Declaration and Signature
    c.line(15,220,185,220)
    c.line(100,220,100,260)

    c.setFont("Times-Bold",5)
    c.drawString(20,248,"Esto no es una factura real")
    c.drawString(20,253," solo es una prueba.")
    c.drawImage("Imagenes/Firma.jpg",110,222,width=60,height=35)
    c.drawRightString(175,253,"Firma Autorizada")
    # End the Page and Start with new



    c.showPage()
    # Saving the PDF
    c.save()


def imprimirFacturaSeleccionada():

    respuesta=""
    numeroFactura=""

    while respuesta != "n":

        print()

        print(color.BLUE+"Elige la factura que quieres imprimir: "+color.END)

        opcionImprimir= csvreader_listarTodosPedidoVentas() +1

        contador=0

        with open("Archivos/pedidoVentas.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

            for fila in reader:
                contador+=1
                if contador == opcionImprimir:
                    lista2= fila[8][2:-2].split('], [')
                    for i in range(0,len(lista2)):
                        #lista3=lista2[i].replace("'","").split(", ")
                        #for j in range(0,len(lista3)):

                            verFactura(fila[0],fila[7],fila[1],fila[2],fila[3],fila[4],fila[5],fila[6],lista2,fila[9]+" €",fila[10])
                            #verFactura(fila[0],fila[7],fila[1],fila[5],lista3[0],lista3[1],lista3[2],lista3[3],lista3[4],fila[9]+" €")

                    numeroFactura=str(fila[0])


        print(Fore.GREEN+"El fichero "+numeroFactura+" se ha añadido al directorio de 'Facturas'\n")


        respuesta = input(color.BLUE+"Quieres imprimir más facturas: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres imprimir más facturas ? (s/n) "+color.END)

    # Vuelvo al menú anterior
    mostrarMenuVenta()

def mostrarMenuVenta():

    opciones = [1,2,3,4,5,6,7,8]

    print(color.BLUE+"Elige una opción:\n"+color.END)
    print(color.BLUE+"\t1"+color.END+" Crear orden de venta")
    print(color.BLUE+"\t2"+color.END+" Editar orden de venta")
    print(color.BLUE+"\t3"+color.END+" Anular orden de venta")
    print(color.BLUE+"\t4"+color.END+" Crear Cliente")
    print(color.BLUE+"\t5"+color.END+" Crear producto")
    print(color.BLUE+"\t6"+color.END+" Imprimir facturas de Ventas")
    print(color.BLUE+"\t7"+color.END+" Mostrar gráficos de facturas")
    print(color.BLUE+"\t8"+color.END+" Salir")
    print()

    opcion = validarNumero(opciones)

    if opcion == 1:
        csvwriter_crearPedidoVenta()
    if opcion == 2:
        editarLineaCSV()
    if opcion == 3:
        borrarLineaCSV("Archivos/pedidoVentas.csv")
    if opcion == 4:
        csvwriter_crearCliente()
    if opcion == 5:
        crearProductoVentas()
    if opcion == 6:
        imprimirFacturaSeleccionada()
    if opcion == 7:
        elegirGraficoFacturas()










