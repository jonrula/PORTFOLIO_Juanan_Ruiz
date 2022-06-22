import pymysql
import csv
from reportlab.pdfgen import canvas
from Color import *
from ConsultasBD import insertar_datos_generica
from FechaRegistros import registroBD
from Validaciones import *
from ConsultasBD import *
from datetime import *
from FechaRegistros import *
from GraficoFacturas import *



def comprobarSiExisteCliente(cif):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()
    #CONSULTA
    consulta = 'SELECT * FROM CLIENTES'
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    resultados = cursor.fetchall()

    #correcto =False

    #En este caso, sabemos qué columnas y datos tiene la tabla en concreto
    for registro in resultados:
        if registro[2] == cif:
            return True



def mostrarClientes():

    opciones =[]
     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()
    #CONSULTA
    consulta = 'SELECT * FROM CLIENTES'
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()

    print()
    print(color.BLUE+"Elige un cliente: \n"+color.END)

    for registro in resultados:
        opciones.append(registro[0])
        print(color.BLUE+"\t"+str(registro[0])+color.END+" " +registro[1])

    print()

    db.close()

    return validarNumeroLista(opciones)




def comprobarFacturaPagada(opcion):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()
    #CONSULTA
    consulta = 'SELECT PAGO FROM PEDIDOS_VENTAS WHERE ID_PEDIDO_VENTA='+str(opcion)
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    resultados = cursor.fetchall()

    #correcto =False

    #En este caso, sabemos qué columnas y datos tiene la tabla en concreto
    for registro in resultados:
        if registro[0] == "Pagado":
            return True

def BuscarIDmaximo(id,tabla):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()
    #CONSULTA
    #consulta = 'SELECT MAX(ID_PEDIDO_COMPRA) FROM PEDIDOS_COMPRAS'
    consulta = 'SELECT MAX('+id+') FROM '+tabla
    #print(consulta)
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()

    for registro in resultados:
        print (registro[0])
        return registro[0]

    db.close()



def BuscarIDcliente(cif):

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()
    #CONSULTA
    #consulta = 'SELECT MAX(ID_PEDIDO_COMPRA) FROM PEDIDOS_COMPRAS'
    consulta = 'SELECT ID_CLIENTE FROM CLIENTES WHERE CIF='+"'" +cif +"'"
    #print(consulta)
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()

    for registro in resultados:
        print (registro[0])
        return registro[0]

    db.close()





def crearCliente(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    print(color.BLUE+"Introduce los datos de los nuevo(s) cliente(s): "+color.END)
    respuesta =""

    while respuesta !="n":

        cliente = validarString("Nombre: ",20)
        cif = validarCIF()

        while comprobarSiExisteCliente(cif):
            print(color.RED+"Error !!... ya existe un Cliente con el CIF: "+ cif +color.END)
            cif = validarCIF()

        direccion = validarString("Dirección: ",20)
        telefono = validarTelefono()
        email= validarEmail()
        web = validarWeb()

        # INSERCION DATOS TABLA CLIENTES

        tabla = 'CLIENTES'
        columnasClientes = "NOMBRE,CIF,DIRECCION,TELEFONO,EMAIL,WEB"
        nuevoCliente="'"+cliente.title()+"','"+cif+"','"+direccion.capitalize()+"','"+telefono+"','"+email+"','"+web+"'"
        consulta = insertar_datos_generica(db, tabla, columnasClientes, nuevoCliente)

        # REGISTRO DE LA CONSULTA
        registroBD(usuario,consulta)

        # INSERCION DATOS TABLA USUARIOS, PARA TENER ACCESO A LA APLICACION Y HACER PAGOS
        tabla = 'USUARIOS'
        columnaNuevoUsuario = "ID_RELACION_CLIENTE,NOMBRE,CONTRASEÑA,DEPARTAMENTOS"

        # Ahora busco la ID del proveedor para relacionarla con la tabla usuarios:
        #idNuevoProveedor= BuscarIDmaximo('ID_CLIENTE','CLIENTES')
        idNuevoProveedor= BuscarIDcliente(cif)

        nuevoUsuario=str(idNuevoProveedor)+",'"+cliente.title()+"','"+cif+"'"+",'Pagos'"
        consulta= insertar_datos_generica(db, tabla, columnaNuevoUsuario, nuevoUsuario)

        # REGISTRO DE LA CONSULTA
        registroBD(usuario,consulta)


        respuesta = input(color.BLUE+"Quieres añadir más clientes: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres añadir más clientes: ? (s/n) "+color.END)


    # Volver al menú anterior:
    mostrarMenuVentas(usuario)



def compobarPagosCliente(clienteSeleccionado):

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    #CONSULTA PAGOS CLIENTE SELECCIONADO
    cursor = db.cursor()
    consulta = 'SELECT PAGO FROM PEDIDOS_VENTAS A WHERE ID_RELACION_CLIENTE =' +str(clienteSeleccionado)
    cursor.execute(consulta)
    db.commit()
    resultados = cursor.fetchall()

    if len(resultados) > 0:

        for registro in resultados:
            if registro[0]=="Pendiente":
                return True

    else:
        print(Fore.GREEN+"El cliente seleccionado no tiene ningún pago pendiente "+color.END)


    db.close()


def mostrarNombreCliente(clienteSeleccionado):

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    cursor = db.cursor()
    #CONSULTA NOMBRE CLIENTE
    consulta = 'SELECT * FROM CLIENTES WHERE ID_CLIENTE= '+str(clienteSeleccionado)
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    for registro in resultados:

        #print (registro[1])
        return registro[1]


    db.close()

def mostrarPedidosVentaCliente(clienteSeleccionado):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    # NOMBRE CLIENTE SELECCIONADO:
    nombreClienteSeleccionado= mostrarNombreCliente(clienteSeleccionado)

    #CONSULTA PEDIDOS VENTAS CLIENTE SELECCIONADO
    cursor = db.cursor()
    consulta = 'SELECT ID_PEDIDO_VENTA, FECHA, TOTAL_PEDIDOS_VENTAS, PAGO FROM PEDIDOS_VENTAS A WHERE A.ID_RELACION_CLIENTE =' +str(clienteSeleccionado)
    cursor.execute(consulta)
    db.commit()
    resultados = cursor.fetchall()

    if len(resultados) > 0:

        print()
        print(color.BLUE+"Estos son los pedidos que tiene el cliente "+nombreClienteSeleccionado.upper()+":\n")
        print(color.BLUE+"\tNº Factura".ljust(15," ")+"Fecha".ljust(15," ")+"Total Pedidos ventas".ljust(30," ")+ "Pago".ljust(20," "))

        for registro in resultados:

            if(registro[3] == "Pendiente"):
                print(color.BLUE+"\t"+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(15," ")+str(str(registro[2])+" €").ljust(30," ")+color.RED+registro[3].ljust(20," ")+color.END)
            if(registro[3] == "Pagado"):
                print(color.BLUE+"\t"+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(15," ")+str(str(registro[2])+" €").ljust(30," ")+color.GREEN+registro[3].ljust(20," ")+color.END)

        print()

    else:
        print(Fore.GREEN+"El cliente seleccionado no tiene ningún pago pendiente "+color.END)


    db.close()





def borrarCliente(usuario):


    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    respuesta =""
    respuestaBorrado=""


    while respuesta !="n":

        respuestaBorrado=""

        # SELECCIONAMOS EL CLIENTE QUE QUEREMOS BORRAR
        clienteSeleccionado=mostrarClientes()

        # NOMBRE DEL CLIENTE SELECCIONADO:
        nombreClienteSeleccionado=mostrarNombreCliente(clienteSeleccionado)

        # MOSTAR LAS FACTURAS DEL CLIENTE SELECCIONADO:
        mostrarPedidosVentaCliente(clienteSeleccionado)

        if not compobarPagosCliente(clienteSeleccionado):

            respuestaBorrado = input(color.BLUE+"Estás seguro de borrar al cliente " +nombreClienteSeleccionado.upper()+ " ? (s/n) "+color.END)
            while (respuestaBorrado.lower() != "s" and respuestaBorrado.lower() != "n"):
                print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                respuestaBorrado = input(color.BLUE+"Estás seguro de borrar al cliente" +nombreClienteSeleccionado.upper()+ " ? (s/n) "+color.END)

            if respuestaBorrado=="s":

                #CONSULTA BORRADO CLIENTE SELECCIONADO
                cursor = db.cursor()
                consulta = 'DELETE FROM CLIENTES WHERE ID_CLIENTE ='+str(clienteSeleccionado)
                #print(consulta)
                cursor.execute(consulta)
                db.commit()

                # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
                registroBD(usuario,consulta)

                print(Fore.GREEN+"El cliente " +nombreClienteSeleccionado.upper()+ " ha sido borrado correctamente")

                print()

                #CONSULTA DE LOS CLIENTES EXISTENTES:
                consulta = 'SELECT * FROM CLIENTES'
                cursor.execute(consulta)
                resultados = cursor.fetchall()

                print()

                print(color.BLUE+"Estos son los clientes con los que trabajamos actualmente en PIXELS: \n")

                for registro in resultados:
                    print(color.BLUE+"\t"+str(registro[0])+color.END+" " +registro[1])

                print()

        else:
            print(color.RED+"No puedes borrar clientes que tienen pagos pendientes !!"+color.END)
            print()


        respuesta = input(color.BLUE+"Quieres borrar más clientes: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres borrar más clientes: ? (s/n) "+color.END)


    db.close()

    # Volver al menú anterior:
    mostrarMenuVentas(usuario)



def borrarFactura(usuario):
    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    respuesta=""
    opciones =[]

    try:

        while respuesta !="n":
            opciones =[]

            #CONSULTA LISTADO DE TODOS LAS FACTURAS
            cursor = db.cursor()
            consulta = 'SELECT A.ID_PEDIDO_VENTA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_VENTAS, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE'
            cursor.execute(consulta)
            resultados = cursor.fetchall()

            if len(resultados) >0 :

                print(color.BLUE+"Estas son todas los facturas: \n"+color.END)
                print(color.BLUE+"Nº Factura".ljust(15," ")+ "Cliente".ljust(30," ")+"Fecha".ljust(15," ")+"Total Pedidos ventas".ljust(30," ")+ "Pago".ljust(20," "))

                for registro in resultados:
                    opciones.append(registro[0])
                    if(registro[4] == "Pendiente"):
                        print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
                    if(registro[4] == "Pagado"):
                        print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.GREEN+registro[4].ljust(20," ")+color.END)

                print()

                opcion = validarNumeroLista(opciones)

                if comprobarFacturaPagada(opcion):

                    #CONSULTA BORRADO PRESUPUESTO ELEGIDO
                    cursor = db.cursor()
                    consulta = 'DELETE FROM PEDIDOS_VENTAS WHERE ID_PEDIDO_VENTA ='+str(opcion)
                    #print(consulta)
                    cursor.execute(consulta)
                    db.commit()

                    print(Fore.GREEN+'Factura borrada correctamente')

                    # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
                    registroBD(usuario,consulta)

                    print()

                    #CONSULTA CON LA LISTA DE TODOS LAS FACTURAS PARA VER LOS ESTADOS DE LOS PAGOS
                    cursor = db.cursor()
                    consultaEstados = 'SELECT A.ID_PEDIDO_VENTA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_VENTAS, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE'
                    cursor.execute(consultaEstados)
                    db.commit()

                    resultados = cursor.fetchall()

                    print(color.BLUE+"Así quedan las facturas: \n"+color.END)
                    print(color.BLUE+"Nº Factura".ljust(15," ")+ "Cliente".ljust(30," ")+"Fecha".ljust(15," ")+"Total Pedidos ventas".ljust(30," ")+ "Pago".ljust(20," "))

                    for registro in resultados:
                        opciones.append(registro[0])
                        if(registro[4] == "Pendiente"):
                            print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
                        if(registro[4] == "Pagado"):
                            print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.GREEN+registro[4].ljust(20," ")+color.END)


                    print()

                else:
                     print(color.RED+"Error !!...no puedes borrar una factura que no está pendiente de pago!!"+color.END)


            else:
                print(color.RED+"No hay facturas !!"+color.END)



            respuesta = input(color.BLUE+"Quieres borrar más Facturas: ? (s/n) "+color.END)
            while (respuesta.lower() != "s" and respuesta.lower() != "n"):
                    print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                    respuesta = input(color.BLUE+"Quieres borrar más Facturas ? (s/n) "+color.END)


    except:
        print(color.RED+'Error en la consulta o en la conexión'+color.END)
        db.rollback() #Deshacer cambios

    # Cerrar la conexión
    db.close()

    #Me voy al menú anterior:
    mostrarMenuVentas(usuario)


def mostrarTodosProductosVentasPixels():

    opciones =[]

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    #CONSULTA
    cursor = db.cursor()
    consulta = 'SELECT ID_PRODUCTO_VENTA, PRODUCTO, DESCRIPCION, PRECIO FROM PRODUCTOS_VENTAS'
    cursor.execute(consulta)
    db.commit()
    resultados = cursor.fetchall()

    if len(resultados) > 0:

        print()

        print(color.BLUE+"\t"+"Número".ljust(10," ")+"Producto".ljust(50," ")+"Descripción".ljust(50," ")+"Precio/unidad".ljust(20," "))

        for registro in resultados:
            opciones.append(registro[0])
            print(color.BLUE+"\t"+str(registro[0]).ljust(10," ")+color.END +registro[1].ljust(50," ")+registro[2].ljust(50," ")+str(str(registro[3])+" €").ljust(20," "))

        print()

    else:
        print(color.RED+"No hay productos !!"+color.END)


    db.close()





def nuevoProductoVenta(usuario):

    print()
    print(color.BLUE+"Estos son todos los productos que comercializa PIXELS:\n"+color.END)
    mostrarTodosProductosVentasPixels()

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    respuesta =""


    while respuesta !="n":

        producto=validarString("Nombre del nuevo producto de venta: ",50)
        descripcion=validarString("Descripción del nuevo producto de venta: ",100)
        precio=validarIntroducirNumeroDecimal("Precio por unidad: ")

        # INSERCION DATOS TABLA PRODUCTOS_VENTAS

        tabla = 'PRODUCTOS_VENTAS'
        columnasProductosVentas= "PRODUCTO,DESCRIPCION,PRECIO"
        productoVenta = "'"+producto+"', '"+descripcion+"', "+str(precio)
        insertar_datos_generica(db, tabla, columnasProductosVentas, productoVenta)

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(usuario,"INSERT INTO " + tabla + "("+ columnasProductosVentas +") VALUES (" + productoVenta + ")")

        print()
        print(color.BLUE+"Así queda actualizada la lista de productos que comercializa PIXELS:\n"+color.END)
        mostrarTodosProductosVentasPixels()

        respuesta = input(color.BLUE+"Quieres añadir más productos de venta: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres añadir más productos de venta: ? (s/n) "+color.END)


    #Me voy al menú anterior:
    mostrarMenuVentas(usuario)

    db.close()





def seleccionarProductoVenta():

    opciones =[]

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()

    #CONSULTA
    consulta = 'SELECT ID_PRODUCTO_VENTA, PRODUCTO, DESCRIPCION, PRECIO FROM PRODUCTOS_VENTAS'
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    print()

    print(color.BLUE+"Estos son todos los productos que comercializa PIXELS:\n"+color.END)
    print(color.BLUE+"\t"+"Opcion".ljust(10," ")+"Producto".ljust(50," ")+"Descripción".ljust(50," ")+"Precio/unidad".ljust(20," "))

    for registro in resultados:
        opciones.append(registro[0])
        print(color.BLUE+"\t"+str(registro[0]).ljust(10," ")+color.END +registro[1].ljust(50," ")+registro[2].ljust(50," ")+str(str(registro[3])+" €").ljust(20," "))

    print()

    db.close()

    return validarNumeroLista(opciones)



def nombreProductoVentaSeleccionado(IDproductoVentaSeleccionado):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    #CONSULTA
    cursor = db.cursor()
    consulta = 'SELECT PRODUCTO, DESCRIPCION, PRECIO FROM PRODUCTOS_VENTAS WHERE ID_PRODUCTO_VENTA='+str(IDproductoVentaSeleccionado)
    #print(consulta)
    cursor.execute(consulta)
    db.commit()
    resultados = cursor.fetchall()


    for registro in resultados:

        return registro[0]+ " de "+str(str(registro[2])+" € por unidad")

    db.close()




def borrarProductoVenta(usuario):


    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    respuesta =""
    respuestaBorrado=""


    while respuesta !="n":
        respuestaBorrado=""

        IDproductoVentaSeleccionado = seleccionarProductoVenta()
        nombreProductoVenta=nombreProductoVentaSeleccionado(IDproductoVentaSeleccionado)


        respuestaBorrado = input(color.BLUE+"Estás seguro de borrar el producto '" +nombreProductoVenta +"' ? (s/n) "+color.END)
        while (respuestaBorrado.lower() != "s" and respuestaBorrado.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuestaBorrado = input(color.BLUE+"Estás seguro de borrar el producto" +nombreProductoVenta+ " ? (s/n) "+color.END)

        if respuestaBorrado=="s":

            # BORRADO PRODUCTO VENTA
            cursor = db.cursor()
            consulta = 'DELETE FROM PRODUCTOS_VENTAS WHERE ID_PRODUCTO_VENTA ='+str(IDproductoVentaSeleccionado)
            #print(consulta)
            cursor.execute(consulta)
            db.commit()

            print(Fore.GREEN+'Producto borrado correctamente')

            # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,consulta)

            print()
            print(color.BLUE+"Así queda actualizada la lista de productos que comercializa PIXELS:\n"+color.END)
            mostrarTodosProductosVentasPixels()

        respuesta = input(color.BLUE+"Quieres borrar más productos de venta: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres borrar más productos de venta: ? (s/n) "+color.END)


    #Me voy al menú anterior:
    mostrarMenuVentas(usuario)

    db.close()






def mostrarProductosVentas():

    opciones =[]

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()

    #CONSULTA
    consulta = 'SELECT ID_PRODUCTO_VENTA, PRODUCTO, DESCRIPCION, PRECIO FROM PRODUCTOS_VENTAS'
    #print(consulta)
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    print()

    print(color.BLUE+"Estos son todos los productos que comercializa PIXELS:\n"+color.END)
    print(color.BLUE+"\t"+"Opcion".ljust(10," ")+"Producto".ljust(50," ")+"Descripción".ljust(50," ")+"Precio/unidad".ljust(20," "))

    for registro in resultados:
        opciones.append(registro[0])
        print(color.BLUE+"\t"+str(registro[0]).ljust(10," ")+color.END +registro[1].ljust(50," ")+registro[2].ljust(50," ")+str(str(registro[3])+" €").ljust(20," "))

    print()


    opcion = validarNumeroLista(opciones)

    for registro in resultados:
        if(registro[0] == opcion):
            print (registro[1] +"," +registro[2] +"," + str(registro[3]))
            return "'"+registro[1] +"','" +registro[2] +"'," + str(registro[3])

    db.close()



def mostrarTotalVentaCliente(relacionnuevoIDPedidoVenta):


    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()

    #CONSULTA
    consulta = 'SELECT A.ID_PEDIDO_VENTA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_VENTAS, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B  WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE AND A.ID_PEDIDO_VENTA= '+ str(relacionnuevoIDPedidoVenta)
    #print(consulta)
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)
    db.commit()

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()

    print()

    print(color.BLUE+"Esta es el TOTAL de la lista de los productos de venta de PIXELS elegido por el cliente:\n"+color.END)
    print(color.BLUE+"\t"+"Pedido Venta".ljust(20," ")+"Cliente".ljust(20," ")+"Fecha".ljust(20," ")+"Total pedido venta".ljust(30," ")+"Pago".ljust(20," "))

    for registro in resultados:
        if(registro[4]=="Pendiente"):
         print(color.BLUE+"\t"+str(registro[0]).ljust(20," ")+color.END+registro[1].ljust(20," ")+registro[2].ljust(20," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
        if(registro[4]=="Pagado"):
         print(color.BLUE+"\t"+str(registro[0]).ljust(20," ")+color.END+registro[1].ljust(20," ")+registro[2].ljust(20," ")+str(str(registro[3])+" €").ljust(30," ")+Fore.GREEN+registro[4].ljust(20," ")+color.END)


    print()




def mostrarUltimaListaVentaCliente(relacionnuevoIDPedidoVenta):
    contador = 0


    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()

    #CONSULTA
    consulta = 'SELECT ID_RELACION_PEDIDO_VENTA, PRODUCTO, DESCRIPCION, PRECIO, UNIDADES, SUBTOTAL FROM LISTA_VENTAS WHERE ID_RELACION_PEDIDO_VENTA= '+ str(relacionnuevoIDPedidoVenta)
    #print(consulta)
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)
    db.commit()

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()

    print()

    print(color.BLUE+"Esta es la lista de productos que comercializa PIXELS que has elegido:\n"+color.END)
    print(color.BLUE+"\t"+"Numero".ljust(20," ")+"Producto".ljust(50," ")+"Descripción".ljust(50," ")+"Precio".ljust(20," ")+"Unidades".ljust(20," ")+"Subtotal".ljust(20," "))


    for registro in resultados:
        contador +=1
        print("\t"+str(contador).ljust(20," ")+registro[1].ljust(50," ")+registro[2].ljust(50," ")+str(str(registro[3])+" €").ljust(20," ")+ str(registro[4]).ljust(20," ")+str(str(registro[5])+" €").ljust(20," "))

    print()







def crearNuevoPedidoVenta(usuario):

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    respuesta =""
    respuesta2=""
    relacionNuevoIDPedidoVenta=0

    print(color.BLUE+"Introduce los datos para el factura: "+color.END)

    while respuesta !="n":

        #Elegir Cliente
        iDcliente= mostrarClientes()

       # Fecha pedido:
        fecha = validarFecha("Fecha presupuesto: ")

        # Creamos primero el nuevo pedido de venta, para sacar la nueva ID y luego poder relacionarla con la lista de productos de ventas de PIXELS para asociarla al nuevo pedido

        tabla = 'PEDIDOS_VENTAS'
        columnasPedidoVentas = "ID_RELACION_CLIENTE,FECHA"
        pedidoVenta = str(iDcliente)+",'"+fecha+"'"
        insertar_datos_generica(db, tabla, columnasPedidoVentas, pedidoVenta)

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(usuario,"INSERT INTO " + tabla + "("+ columnasPedidoVentas +") VALUES (" + pedidoVenta + ")")

        # Elijo productos de la lista de ventas
        respuesta2="s"
        while respuesta2 !="n":

                # Elijo productos de la lista de ventas
                productoVentas = mostrarProductosVentas()
                print(productoVentas)

               # Calculo los subtotales del producto que he elegido
                unidades= validarIntroducirNumero("Introducir unidades: ")

                # Añado los productos de la lista de ventas

                tabla = 'LISTA_VENTAS'
                columnasListaVentas = "ID_RELACION_PEDIDO_VENTA,PRODUCTO,DESCRIPCION,PRECIO,UNIDADES"
                relacionNuevoIDPedidoVenta = BuscarIDmaximo('ID_PEDIDO_VENTA','PEDIDOS_VENTAS')
                listaVenta = str(relacionNuevoIDPedidoVenta)+","+productoVentas+","+str(unidades)
                #print(listaVenta)
                insertar_datos_generica(db, tabla, columnasListaVentas,listaVenta)

                # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
                registroBD(usuario,"INSERT INTO " + tabla + "("+ columnasPedidoVentas +") VALUES (" + listaVenta + ")")

                respuesta2 = input(color.BLUE+"Quieres añadir más productos: ? (s/n) "+color.END)
                while (respuesta2.lower() != "s" and respuesta2.lower() != "n"):
                    print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                    respuesta2 = input(color.BLUE+"Quieres añadir más productos ? (s/n) "+color.END)


        # MUESTRO TODA LA LISTA DE LA FACTURA DEL CLIENTE SELECCIONADO
        mostrarUltimaListaVentaCliente(relacionNuevoIDPedidoVenta)


        respuesta = input(color.BLUE+"Quieres añadir más facturas: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres añadir más facturas ? (s/n) "+color.END)


    # MUESTRO EL TOTAL DEL PEDIDO_VENTA (SUMA DE TODA LA LISTA DE LA VENTA DEL CLIENTES ELECCIONADO, QUE ES LA ULTIMO PEDIDO_VENTA
    mostrarTotalVentaCliente(relacionNuevoIDPedidoVenta)


    #Me voy al menú anterior:
    mostrarMenuVentas(usuario)

    db.close()


def seleccionarFactura(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    respuesta=""

    try:

        while respuesta !="n":
            opciones =[]

            # CONSULTA ELEGIR PRESUPUESTO (TABLA PEDIDOS_COMPRAS)
            cursor = db.cursor()
            consulta = 'SELECT A.ID_PEDIDO_VENTA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_VENTAS, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE'
            #print(consulta)
            cursor.execute(consulta)
            resultados = cursor.fetchall()

            print(color.BLUE+"Estos son todas las facturas que existen:\n")
            print(color.BLUE+"Nº Factura".ljust(15," ")+ "Cliente".ljust(30," ")+"Fecha".ljust(15," ")+"Total Pedidos ventas".ljust(30," ")+ "Estado Pago".ljust(20," ")+color.END)
            for registro in resultados:
                opciones.append(registro[0])
                if(registro[4] == "Pendiente"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
                if(registro[4] == "Pagado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.GREEN+registro[4].ljust(20," ")+color.END)

            return validarNumeroLista(opciones)

    except:
        print(color.RED+'Error en la consulta o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


def imprimirFactura(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    respuesta=""

    while respuesta != "n":
        nombreCliente = ""

        #SELECCIONAR PRESUPUESTO:
        opcion= seleccionarFactura(usuario)

        c = canvas.Canvas("Archivos/Facturas/Factura"+str(opcion)+".pdf",pagesize=(200,280),bottomup=0)
        # Logo Section
        # Setting th origin to (10,40)
        c.translate(10,40)
        # Inverting the scale for getting mirror Image of logo
        c.scale(1,-1)
        # Inserting Logo into the Canvas at required position
        c.drawImage("Archivos/Imagenes/logoFoto.png",0,0,width=50,height=30)
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
        c.drawString(92,90,"CIF : ")
        c.drawRightString(45,100,"Dirección : ")
        c.drawString(92,100,"Teléfono : ")
        c.drawString(128,100,"Email : ")
        c.drawString(128,90,"Web : ")


        c.setFont("Courier",3)

        #CONSULTA DATOS PROVEEDOR
        cursor = db.cursor()
        consulta= 'SELECT A.ID_PEDIDO_VENTA, A.FECHA, B.NOMBRE, B.CIF, B.DIRECCION, B.TELEFONO, B.EMAIL, B.WEB, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE AND A.ID_PEDIDO_VENTA ='+str(opcion)
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        for registro in resultados:

            nombreCliente = registro[2]

            c.drawString(45,70,str(registro[0])) #numeroFactura
            c.drawString(45,80,registro[1]) # fecha
            c.drawString(45,90,registro[2].upper()) #Proveedor
            c.drawString(103,90,registro[3]) #CIF
            c.drawString(45,100,registro[4]) # direccion
            c.drawString(110,100,registro[5]) #Telefono
            c.drawString(143,100,registro[6]) # email
            c.drawString(141,90,registro[7]) # Web

            if registro[8] == "Pendiente":
                c.drawImage("Archivos/Imagenes/Pendiente.jpg",120,140,width=60,height=35)

            elif registro[8] == "Pagado":
                c.drawImage("Archivos/Imagenes/Pagado.jpg",120,140,width=60,height=35)

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


        #CONSULTA PRESUPUESTO SELECCIONADO
        cursor = db.cursor()
        #consulta= 'SELECT A.ID_PEDIDO_VENTA, B.NOMBRE, B.CIF, B.DIRECCION, B.TELEFONO, B.EMAIL, A.FECHA, C.PRODUCTO, C.DESCRIPCION, C.PRECIO, C.UNIDADES, C.SUBTOTAL, A.TOTAL_PEDIDOS_VENTAS, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B, LISTA_VENTAS C WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE AND C.ID_RELACION_PEDIDO_VENTA = A.ID_PEDIDO_VENTA AND C.ID_RELACION_PEDIDO_VENTA='+str(opcion)
        consulta= 'SELECT C.PRODUCTO, C.DESCRIPCION, C.PRECIO, C.UNIDADES, C.SUBTOTAL, A.TOTAL_PEDIDOS_VENTAS FROM PEDIDOS_VENTAS A, LISTA_VENTAS C WHERE C.ID_RELACION_PEDIDO_VENTA = A.ID_PEDIDO_VENTA AND C.ID_RELACION_PEDIDO_VENTA='+str(opcion)
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        for registro in resultados:

            lineaNueva +=5

            c.drawString(17,122+lineaNueva,registro[0]) #Producto
            c.drawString(62,122+lineaNueva,registro[1]) #Descripcion
            c.drawString(120,122+lineaNueva,str(registro[2])) #Precio
            c.drawString(145,122+lineaNueva,str(registro[3])) #Unidades
            c.drawString(163,122+lineaNueva,str(registro[4])+" €") #Subtotal
            c.drawString(163,217,str(registro[5])+" €") #Total


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
        c.drawImage("Archivos/Imagenes/Firma.jpg",110,222,width=60,height=35)
        c.drawRightString(175,253,"Firma Autorizada")
        # End the Page and Start with new


        c.showPage()
        # Saving the PDF
        c.save()

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(usuario,usuario+"Impresión de la factura:'Factura"+str(opcion)+"' del cliente "+nombreCliente.upper())

        respuesta = input(color.BLUE+"Quieres imprimir más facturas: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres imprimir más facturas ? (s/n) "+color.END)


    # Volver al menú anterior
    mostrarMenuVentas(usuario)

def mostrarMenuVentas(usuario):

    opciones = [1,2,3,4,5,6,7,8,9]

    print(color.BLUE+"Elige una opción:\n"+color.END)
    print(color.BLUE+"\t1"+color.END+" Imprimir facturas")
    print(color.BLUE+"\t2"+color.END+" Borrar factura")
    print(color.BLUE+"\t3"+color.END+" Añadir cliente")
    print(color.BLUE+"\t4"+color.END+" Borrar cliente")
    print(color.BLUE+"\t5"+color.END+" Crear factura")
    print(color.BLUE+"\t6"+color.END+" Crear nuevo producto de venta")
    print(color.BLUE+"\t7"+color.END+" Borrar producto de venta")
    print(color.BLUE+"\t8"+color.END+" Mostrar gráficos de facturas")
    print(color.BLUE+"\t9"+color.END+" Salir")

    print()

    opcion = validarNumeroLista(opciones)

    if opcion == 1:
        imprimirFactura(usuario)
    if opcion == 2:
        borrarFactura(usuario)
    if opcion == 3:
        crearCliente(usuario)
    if opcion == 4:
        borrarCliente(usuario)
    if opcion == 5:
        crearNuevoPedidoVenta(usuario)
    if opcion == 6:
        nuevoProductoVenta(usuario)
    if opcion == 7:
        borrarProductoVenta(usuario)
    if opcion == 8:
        elegirGraficoFacturas(usuario)

