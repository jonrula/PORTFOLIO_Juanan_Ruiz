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

def csvreader_ComprobarExisteProveedor(dni):
    with open("Archivos/proveedores.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',
                            quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            if fila[2] == dni:
                return True



def csvwriter_crearProveedor():
    print(color.BLUE+"Introduce los datos de los nuevos clientes: "+color.END)
    respuesta =""


    while respuesta !="n":


        cif = validarCIF()

        while csvreader_ComprobarExisteProveedor(cif):
            print(color.RED+"Error !!... ya existe un proveedor con el DNI: "+ cif +color.END)
            cif = validarCIF()

        nombre = validarString("Nombre: ",20)
        apellidos = validarString("Apellidos: ",20)
        direccion = validarString("Dirección: ",20)
        telefono = validarTelefono()
        email= validarEmail()
        web = validarWeb()


        # Inicializo una variable de tipo String y le concateno todas las variables, a las que a cada una les quitos los posibles intros y saltos de línea --> 'rstrip("\r\n")' y quitar la separación den carácteres por coma --> 'split(',')'

        nuevosDatos = nombre.title().rstrip("\r\n").split(',') + apellidos.title().rstrip("\r\n").split(',')+cif.rstrip("\r\n").split(',')+direccion.capitalize().rstrip("\r\n").split(',')+telefono.rstrip("\r\n").split(',')+email.rstrip("\r\n").split(',')+web.rstrip("\r\n").split(',')

        mi_fichero = open("Archivos/proveedores.csv", 'a',newline='')

        with mi_fichero:
            writer = csv.writer(mi_fichero,quoting=csv.QUOTE_NONNUMERIC)
            writer.writerow(nuevosDatos)  # IMPORTANTE --> hay que poner solo 'writerow' para que escriba UNA SOLA FILA

        print(Fore.GREEN+"Se ha añadido correctamente al proveedor "+nombre.upper()+ " "+ apellidos.upper())


        respuesta = input(color.BLUE+"Quieres añadir más proveedores: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres añadir más proveedores: ? (s/n) "+color.END)


    #Me voy al menú anterior:
    mostrarMenuCompra()

def csvreader_listarProveedores():
    contador = 0
    with open("Archivos/proveedores.csv") as csvfichero:
        #Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)
        print()

        print(color.BLUE+"Elige un proveedor: \n"+color.END)

        if header!= None:

            for fila in reader:
                contador +=1
                print(color.BLUE+"\t"+str(contador)+color.END+ " " +fila[0])


        print()

        return  validarOpcion(contador)



def mostrarNombreApellidosDniDireccionTelefonoEmailWebProveedor():
    opcion= csvreader_listarProveedores()
    contador =0

    with open("Archivos/proveedores.csv") as csvfichero:
        #Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)

        if header!= None:

            for fila in reader:
                contador +=1
                if(contador == opcion):
                    return fila[0]+","+fila[1]+","+fila[2]+","+fila[3]+","+fila[4]+","+fila[5] +","+fila[6]

def cambiarNombreProveedor(opcion):
    contador =0
    with open("Archivos/proveedores.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[0]

def cambiarApellidosProveedor(opcion):
    contador =0
    with open("Archivos/proveedores.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[1]

def cambiarCIFProveedor(opcion):
    contador =0
    with open("Archivos/proveedores.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[2]

def cambiarDireccionProveedor(opcion):
    contador =0
    with open("Archivos/proveedores.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[3]

def cambiarTelefonoProveedor(opcion):
    contador =0
    with open("Archivos/proveedores.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[4]

def cambiarEmailProveedor(opcion):
    contador =0
    with open("Archivos/proveedores.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[5]

def cambiarWebProveedor(opcion):
    contador =0
    with open("Archivos/proveedores.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            contador +=1
            if(contador == opcion):
                return fila[6]

def csvreader_listarPedidoCompras():
    contador =0
    with open("Archivos/pedidoCompras.csv") as csvfichero:
    # Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',', quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)

        if header != None:


            print(color.BLUE+"\t" +"Opcion".ljust(8," ")+ "Presupuesto".ljust(15," ")+"Nombre".ljust(25," ")+"Fecha Presupuesto".ljust(20," ")+"Número de productos".ljust(25," ")+"Precio total".ljust(19," ")+"Estado del presupuesto".ljust(16," ")+color.END)
            print()

            for fila in reader:

                contador += 1

                if fila[11] == "Aprobado" :
                    print(color.BLUE + "\t" + str(contador).ljust(8," ") + color.END + fila[0].ljust(15," ") + fila[1].ljust(25," ") + fila[8].ljust(20," ") + str(len(fila[9][0:-1].rstrip("\r\n").split("]"))-1).ljust(25," ") +  fila[10].ljust(5," ")+" €".ljust(14," ") +  Fore.GREEN+fila[11].ljust(13," "))

                if fila[11] == "No aprobado" :
                    print(color.BLUE + "\t" + str(contador).ljust(8," ") + color.END + fila[0].ljust(15," ") + fila[1].ljust(25," ") + fila[8].ljust(20," ") + str(len(fila[9][0:-1].rstrip("\r\n").split("]"))-1).ljust(25," ") +  fila[10].ljust(5," ")+" €".ljust(14," ") +  color.RED+fila[11].ljust(13," ")+color.END)
        print()

        return validarOpcion(contador)

def csvreader_listarProductosCompras():
    contador = 0

    with open("Archivos/productosCompras.csv") as csvfichero:
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


def mostrarProductosCompras():
    opcion= csvreader_listarProductosCompras()
    contador =0

    with open("Archivos/productosCompras.csv") as csvfichero:
        #Devuelve una lista de listas,sin la cabecera
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



def csvwriter_crearPedidoCompra():
    print(color.BLUE+"Introduce los datos para el presupuesto de compra: "+color.END)
    respuesta =""
    respuesta2=""
    listaProductosVentas = []
    total =0


    while respuesta !="n":

        # Elegir proveedor:
        datosCliente= mostrarNombreApellidosDniDireccionTelefonoEmailWebProveedor()

        # Fecha pedido:
        fecha = validarFecha("Fecha presupuesto: ")

        # Inicializo una variable de tipo String y le concateno todas las variables, a las que a cada una les quitos los posibles intros y saltos de línea --> 'rstrip("\r\n")' y quitar la separación den carácteres por coma --> 'split(',')'
        # Tener en cuenta que a la hora de concatenar --> si concateno con una coma ',': me crea una lista, si pongo '+' ,me concatena como una lista de elementos dentro de la fila del archivo.csv

        respuesta2="s"
        while respuesta2 !="n":

            # Elijo productos
            productoCompras = mostrarProductosCompras()

            # Calculo los subtotales del producto
            horas= validarIntroducirNumero("Introducir horas o unidades: ")
            subTotal= int(horas) * int(productoCompras[2])

            # Añado los elementos hora y el subtotal a la lista 'productoVentas'  que al final es una nueva fila de pedido dentro de la lista 'listaProductosVentas.', que es la la fatura total. Es una lista (factura) llena de otras listas (lineas de pedidos, con sus subtotales)
            productoCompras.append(horas)
            productoCompras.append(subTotal)
            listaProductosVentas.append(productoCompras)

            # Voy calculando el valor total de la factura, por defecto, dentro del archivo.csv, 'productoVentas' ya es una lista y puedo acceder al elemento
            total += int(productoCompras[4])

            # Después de calcular el valor del total de la lista, le añado los símbolos '€' para sacarlos por pantalla, porque ya no va a calcular en ese elemento y ahora puedo hacerlo, antes no, porque no podría hacer el casting de 'int'
            productoCompras[2] = productoCompras[2] +" €"

            respuesta2 = input(color.BLUE+"Quieres añadir más productos: ? (s/n) "+color.END)
            while (respuesta2.lower() != "s" and respuesta2.lower() != "n"):
                print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                respuesta2 = input(color.BLUE+"Quieres añadir más productos ? (s/n) "+color.END)

        # Después de salir del bucle de hacer toda la cesta de productos, calculo el total de toda la factura:
        totalPedido = str(total)
        pago= "No aprobado"
        numeroPedido= buscarNumeroPedidoMasAlto("Archivos/pedidoCompras.csv") +1


        # Concateno en un string, los diversos elementos que corresponden alas columnas, teniendo en cuenta que le añado una lista de listas que es la factura: cada lista inerior es un producto y al final de la lista el total de la cesta:
        nuevoPedidoVentas = str(numeroPedido).rstrip("\r\n").split(',') + datosCliente.rstrip("\r\n").split(',') + fecha.rstrip("\r\n").split(',') + [listaProductosVentas] + totalPedido.rstrip("\r\n").split(',') + pago.rstrip("\r\n").split(',')


        # Escribo la nueva linea 'nuevoPedidoVentas' al archivo.csv:
        mi_fichero = open("Archivos/pedidoCompras.csv", 'a',newline='')
        with mi_fichero:
            writer = csv.writer(mi_fichero,quoting=csv.QUOTE_NONNUMERIC)
            writer.writerow(nuevoPedidoVentas)  # IMPORTANTE --> hay que poner solo 'writerow' para que escriba UNA SOLA FILA


        print(Fore.GREEN+"El fichero se ha escrito correctamente")


        respuesta = input(color.BLUE+"Quieres añadir más presupuestos: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres añadir más presupuestos ? (s/n) "+color.END)


    #Me voy al menú anterior:
    mostrarMenuCompra()



def RechazarAprobarPresupuestos():

    respuesta=""
    respuesta2=""
    listaProductosCompras=[]
    total =0

    while os.path.isfile("Archivos/pedidoCompras.csv") and respuesta !="n" and os.stat("Archivos/pedidoCompras.csv").st_size > 0:

        print()

        opcionPedidoCompras= csvreader_listarPedidoCompras() +1
        contador=0

        with open("Archivos/pedidoCompras.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/pedidoCompras.csv","w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)

            for fila in reader:
                contador+=1
                if contador == opcionPedidoCompras:

                    aprobado=eligeAprobarPresupuesto(fila[0],fila[11])

                    fila[11] = aprobado

                    writer.writerow(fila)

                else:
                    writer.writerow(fila)

        respuesta = input(color.BLUE+"Quieres editar más PRESUPUESTOS: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
                print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                respuesta = input(color.BLUE+"Quieres editar más PRESUPUESTOS ? (s/n) "+color.END)


    if os.stat("Archivos/pedidoCompras.csv").st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile("Archivos/pedidoCompras.csv"):
         print(color.RED+"Error !!... no existe el archivo "+color.END)


    #Me voy al menú anterior:
    mostrarMenuCompra()



def borrarLineaCSV(archivo):

    respuesta =""

    while os.path.isfile(archivo) and respuesta !="n" and os.stat(archivo).st_size > 0:

        opcion= csvreader_listarPedidoCompras() +1
        contador = 0

        with open(archivo,"r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open(archivo,"w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)
            for fila in reader:
                contador +=1
                if contador != opcion:
                    writer.writerow(fila)

            print(Fore.GREEN+"La fila "+str(opcion)+" ha sido borrada !!")

        respuesta = input(color.BLUE+"Quieres borrar más presupuestos: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres borrar más presupuestos ? (s/n) "+color.END)


    if os.stat(archivo).st_size == 0:
        print(color.RED+"Error !! ... no hay más presupuestos para borrar"+color.END)
    if not os.path.isfile(archivo):
         print(color.RED+"Error !!... no existe el archivo "+color.END)

    #Me voy al menú anterior:
    mostrarMenuCompra()

def editarLineaCSVpedidoCompras():

    respuesta=""
    respuesta2=""
    listaProductosCompras=[]
    total =0

    while os.path.isfile("Archivos/pedidoCompras.csv") and respuesta !="n" and os.stat("Archivos/pedidoCompras.csv").st_size > 0:

        print()


        opcionPedidoCompras= csvreader_listarPedidoCompras() +1
        contador=0

        with open("Archivos/pedidoCompras.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/pedidoCompras.csv","w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)

            for fila in reader:
                contador+=1
                if contador == opcionPedidoCompras:
                    datos = input(color.BLUE + "Quieres cambiar los DATOS DEL PROVEEDOR ? (S/N): " + color.END)
                    while (datos.lower()!= "s" and datos.lower() != "n"):
                        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                        datos = input(color.BLUE + "Quieres cambiar los DATOS DEL PROVEEDOR ? (S/N) ? : " + color.END)

                    if datos.lower() == "s":
                        opcionProveedor= csvreader_listarProveedores() +1

                        fila[1]=cambiarNombreProveedor(opcionProveedor)
                        fila[2]=cambiarApellidosProveedor(opcionProveedor)
                        fila[3]=cambiarCIFProveedor(opcionProveedor)
                        fila[4]=cambiarDireccionProveedor(opcionProveedor)
                        fila[5]=cambiarTelefonoProveedor(opcionProveedor)
                        fila[6]=cambiarEmailProveedor(opcionProveedor)
                        fila[7]=cambiarWebProveedor(opcionProveedor)

                    fecha = input(color.BLUE + "Quieres cambiar la FECHA ? (S/N): " + color.END)
                    while (fecha.lower()!= "s" and fecha.lower() != "n"):
                        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                        fecha = input(color.BLUE + "Quieres cambiar la FECHA ? (S/N) ? : " + color.END)

                    if fecha.lower() =="s":
                        nuevaFecha = validarFecha("Nueva Fecha: ")
                        fila[8] = nuevaFecha

                    listaProductos = input(color.BLUE + "Quieres cambiar la LISTA DE PRODUCTOS ? (S/N): " + color.END)
                    while (listaProductos.lower()!= "s" and listaProductos.lower() != "n"):
                        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                        listaProductos = input(color.BLUE + "Quieres cambiar la LISTA DE PRODUCTOS ? (S/N) ? : " + color.END)

                    if listaProductos.lower() == "s":

                        while respuesta2 !="n":

                            # Elijo productos
                            productoCompras = mostrarProductosCompras()

                            # Calculo los subtotales del producto
                            horas= validarIntroducirNumero("Introducir horas o unidades: ")
                            subTotal= int(horas) * int(productoCompras[2])

                            # Añado los elementos hora y el subtotal a la lista 'productoVentas'  que al final es una nueva fila de pedido dentro de la lista 'listaProductosVentas.', que es la la fatura total. Es una lista (factura) llena de otras listas (lineas de pedidos, con sus subtotales)
                            productoCompras.append(horas)
                            productoCompras.append(subTotal)
                            listaProductosCompras.append(productoCompras)

                            # Voy calculando el valor total de la factura, por defecto, dentro del archivo.csv, 'productoVentas' ya es una lista y puedo acceder al elemento
                            total += int(productoCompras[4])

                            # Después de calcular el valor del total de la lista, le añado los símbolos '€' para sacarlos por pantalla, porque ya no va a calcular en ese elemento y ahora puedo hacerlo, antes no, porque no podría hacer el casting de 'int'
                            productoCompras[2] = productoCompras[2] +" €"
                            #productoVentas[3] = str(productoVentas[3]) +" Unidades/horas"
                            #productoCompras[4] = str(productoCompras[4]) +" €"

                            respuesta2 = input(color.BLUE+"Quieres añadir más productos: ? (s/n) "+color.END)
                            while (respuesta2.lower() != "s" and respuesta2.lower() != "n"):
                                print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                                respuesta2 = input(color.BLUE+"Quieres añadir más productos ? (s/n) "+color.END)

                         # Después de salir del bucle de hacer toda la cesta de productos, calculo el total de toda la factura:
                        totalPedido = str(total)

                        fila[9] = listaProductosCompras
                        fila[10] = totalPedido

                    writer.writerow(fila)

                else:
                    writer.writerow(fila)

        respuesta = input(color.BLUE+"Quieres editar más PRESUPUESTOS: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
                print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                respuesta = input(color.BLUE+"Quieres editar más PRESUPUESTOS ? (s/n) "+color.END)


    if os.stat("Archivos/pedidoCompras.csv").st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile("Archivos/pedidoCompras.csv"):
         print(color.RED+"Error !!... no existe el archivo "+color.END)


    #Me voy al menú anterior:
    mostrarMenuCompra()

def comprobarExisteProducto(producto):

    with open("Archivos/productosCompras.csv") as csvfichero:
        #Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            if fila[0] == producto:
                return True


def crearProductoCompras():

    respuesta=""

    while os.path.isfile("Archivos/productosCompras.csv") and respuesta !="n" and os.stat("Archivos/productosCompras.csv").st_size > 0:

        producto= validarString("Producto: ", 50)
        while comprobarExisteProducto(producto):
            print(color.RED+"Error !!... ya existe el producto: "+ producto.upper() +color.END)
            producto= validarString("Producto: ", 50)

        descripcion= validarString("Descripción: ", 100)
        precio=validarIntroducirNumero("Precio unidad: ")

        nuevosDatos = producto.title().rstrip("\r\n").split(',') + descripcion.capitalize().rstrip("\r\n").split(',')+str(precio).rstrip("\r\n").split(',')

        mi_fichero = open("Archivos/productosCompras.csv", 'a',newline='')

        with mi_fichero:
            writer = csv.writer(mi_fichero,quoting=csv.QUOTE_NONNUMERIC)
            writer.writerow(nuevosDatos)  # IMPORTANTE --> hay que poner solo 'writerow' para que escriba UNA SOLA FILA

        print(Fore.GREEN+"El fichero se ha escrito correctamente")

        respuesta = input(color.BLUE+"Quieres editar más PRODUCTOS: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
                print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                respuesta = input(color.BLUE+"Quieres editar más PRODUCTOS ? (s/n) "+color.END)


    if os.stat("Archivos/productosVentas.csv").st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile("Archivos/productosVentas.csv"):
         print(color.RED+"Error !!... no existe el archivo "+color.END)


    #Me voy al menú anterior:
    mostrarMenuCompra()



def verPresupuesto(numeroPresupuesto,fecha,proveedor,cif,direccion,telefono,lista,total,aprobado):


    c = canvas.Canvas("Presupuestos/presupuesto"+numeroPresupuesto+".pdf",pagesize=(200,280),bottomup=0)
    # Logo Section
    # Setting th origin to (10,40)
    c.translate(10,40)
    # Inverting the scale for getting mirror Image of logo
    c.scale(1,-1)
    # Inserting Logo into the Canvas at required position
    #c.drawImage("Imagenes/logoFoto.png",0,0,width=50,height=30)
    # Title Section
    # Again Inverting Scale For strings insertion
    c.scale(1,-1)
    # Again Setting the origin back to (0,0) of top-left
    c.translate(-10,-40)
    # Setting the font for Name title of company
    c.setFont("Helvetica-Bold",10)
    # Inserting the name of the company
    c.drawCentredString(100,20,proveedor)
    # For under lining the title
    c.line(50,22,180,22)

    # Changing the font size for Specifying Address
    c.setFont("Helvetica-Bold",5)

    c.drawCentredString(100,30,proveedor)
    c.drawCentredString(100,35,direccion)
    # Changing the font size for Specifying GST Number of firm
    c.setFont("Helvetica-Bold",6)
    c.drawCentredString(100,42,cif)
    # Line Seprating the page header from the body
    c.line(5,45,195,45)
    # Document Information
    # Changing the font for Document title
    c.setFont("Courier-Bold",8)
    c.drawCentredString(100,55,"Presupuesto")
    # This Block Consist of Costumer Details
    c.roundRect(15,63,170,42,10,stroke=1,fill=0)

    c.setFont("Times-Bold",4)
    c.drawRightString(45,70,"Presupuesto: ")
    c.drawRightString(45,80,"Fecha: ")
    c.drawRightString(45,90,"Cliente : ")
    c.drawString(90,90,"NIF : ")
    c.drawRightString(45,100,"Dirección : ")
    c.drawString(90,100,"Teléfono : ")
    c.drawString(134,100,"Email : ")

    if aprobado == "Aprobado":
        c.drawImage("Imagenes/Aprobado.png",120,140,width=60,height=35)

    elif aprobado == "No aprobado":
        c.drawImage("Imagenes/Denegado.jpg",120,140,width=60,height=35)

    c.setFont("Courier",3)
    c.drawString(45,70,numeroPresupuesto)
    c.drawString(45,80,fecha)
    c.drawString(45,90,"Pixels")
    c.drawString(100,90,"16296028E")
    c.drawString(45,100,"Vitoria-Gasteiz (Araba)")
    c.drawString(110,100,"670839934")
    c.drawString(148,100,"info@pixels.com")

    # This Block Consist of Item Description
    c.roundRect(15,110,170,150,10,stroke=1,fill=0)
    c.line(15,120,185,120)

    c.setFont("Times-Bold",5)

    c.drawCentredString(30,118,"Producto")
    c.drawCentredString(85,118,"Descripción")
    c.drawCentredString(125,118,"Precio")
    c.drawCentredString(148,118,"Cantidad")
    c.drawCentredString(173,118,"TOTAL")



    c.setFont("Courier",2.5)
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
    c.drawString(20,248,"Esto no es un presupuesto real")
    c.drawString(20,253," solo es una simulación.")
    #c.drawImage("Imagenes/Firma.jpg",110,222,width=60,height=35)
    c.drawRightString(175,253,"Firmado: "+proveedor.upper())
    # End the Page and Start with new



    c.showPage()
    # Saving the PDF
    c.save()


def imprimirPresupuestoSeleccionado():

    respuesta=""
    numeroPresupuesto=""

    while respuesta != "n":

        print()

        print(color.BLUE+"Elige el presupuesto que quieres imprimir: "+color.END)

        opcionImprimir= csvreader_listarTodosPresupuestos() +1

        contador=0

        with open("Archivos/pedidoCompras.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

            for fila in reader:
                contador+=1
                if contador == opcionImprimir:
                    lista2= fila[9][2:-2].split('], [')
                    for i in range(0,len(lista2)):
                        #lista3=lista2[i].replace("'","").split(", ")
                        #for j in range(0,len(lista3)):

                            verPresupuesto(fila[0],fila[8],fila[1],fila[3],fila[4],fila[5],lista2,fila[10]+" €",fila[11])

                    numeroPresupuesto=str(fila[0])


        print(Fore.GREEN+"El fichero "+numeroPresupuesto+" se ha añadido al directorio de 'Presupuestos'\n")


        respuesta = input(color.BLUE+"Quieres imprimir más presupuestos: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres imprimir más presupuestos ? (s/n) "+color.END)


    mostrarMenuCompra()




def mostrarMenuCompra():

    opciones = [1,2,3,4,5,6,7,8]

    print(color.BLUE+"Elige una opción:\n"+color.END)
    print(color.BLUE+"\t1"+color.END+" Pedir un presupuesto")
    print(color.BLUE+"\t2"+color.END+" Aprobar / rechazar presupuesto")
    print(color.BLUE+"\t3"+color.END+" Borrar presupuesto")
    print(color.BLUE+"\t4"+color.END+" Crear proveedor")
    print(color.BLUE+"\t5"+color.END+" Crear producto")
    print(color.BLUE+"\t6"+color.END+" Imprimir presupuestos")
    print(color.BLUE+"\t7"+color.END+" Mostrar gráficos de presupuestos")
    print(color.BLUE+"\t8"+color.END+" Salir")

    print()

    opcion = validarNumero(opciones)

    if opcion == 1:
        csvwriter_crearPedidoCompra()
    if opcion == 2:
        RechazarAprobarPresupuestos()
    if opcion == 3:
        borrarLineaCSV("Archivos/pedidoCompras.csv")
    if opcion == 4:
        csvwriter_crearProveedor()
    if opcion == 5:
        crearProductoCompras()
    if opcion == 6:
        imprimirPresupuestoSeleccionado()
    if opcion == 7:
        elegirGraficoPresupuestos()


