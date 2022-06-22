import pymysql
import csv
from reportlab.pdfgen import canvas
from Color import *
from FechaRegistros import registroBD
from Validaciones import *
from ConsultasBD import *
from datetime import *
from FechaRegistros import *



def BuscarIDproveedor(usuario):

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    cursor = db.cursor()
    #CONSULTA
    consulta = 'SELECT ID_PROVEEDOR FROM PROVEEDORES WHERE NOMBRE='+"'"+usuario +"'"
    #print(consulta)
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    for registro in resultados:
        print (registro[0])
        return registro[0]

    db.close()


def mostrarProductosCompras(usuario):


    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    cursor = db.cursor()

    #CONSULTA
    consulta = 'SELECT A.ID_PRODUCTO_COMPRA, A.PRODUCTO, A.DESCRIPCION, A.PRECIO FROM PRODUCTOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR AND B.NOMBRE = '+"'"+usuario +"'"
    #print(consulta)
    cursor.execute(consulta)
    resultados = cursor.fetchall()
    db.commit()

    print()

    print(color.BLUE+"\t"+"ID Producto".ljust(15," ")+"Producto".ljust(50," ")+"Descripción".ljust(50," ")+"Precio/unidad".ljust(20," "))

    for registro in resultados:

        print(color.BLUE+"\t"+str(registro[0]).ljust(15," ")+color.END +registro[1].ljust(50," ")+registro[2].ljust(50," ")+str(str(registro[3])+" €").ljust(20," "))

    print()

    db.close()



def seleccionarProductoCompra(usuario):

    opciones =[]

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()

    #CONSULTA
    consulta = 'SELECT A.ID_PRODUCTO_COMPRA, A.PRODUCTO, A.DESCRIPCION, A.PRECIO FROM PRODUCTOS_COMPRAS A, PROVEEDORES B WHERE ID_RELACION_PROVEEDOR=ID_PROVEEDOR AND B.NOMBRE='+"'"+usuario+"'"
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    print()

    print(color.BLUE+"Estos son todos los productos que comercializael proveedor " +usuario.upper()+":\n"+color.END)
    print(color.BLUE+"\t"+"Opcion".ljust(10," ")+"Producto".ljust(50," ")+"Descripción".ljust(50," ")+"Precio/unidad".ljust(20," "))

    for registro in resultados:
        opciones.append(registro[0])
        print(color.BLUE+"\t"+str(registro[0]).ljust(10," ")+color.END +registro[1].ljust(50," ")+registro[2].ljust(50," ")+str(str(registro[3])+" €").ljust(20," "))

    print()

    db.close()

    return validarNumeroLista(opciones)




def nombreProductoCompraSeleccionado(IDproductoCompraSeleccionado):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    #CONSULTA
    cursor = db.cursor()
    consulta = 'SELECT PRODUCTO, DESCRIPCION, PRECIO FROM PRODUCTOS_COMPRAS WHERE ID_PRODUCTO_COMPRA='+str(IDproductoCompraSeleccionado)
    #print(consulta)
    cursor.execute(consulta)
    db.commit()
    resultados = cursor.fetchall()


    for registro in resultados:

        return registro[0]+ " de "+str(str(registro[2])+" € por unidad")

    db.close()




def nuevoProductoCompra(usuario):

    print()
    print(color.BLUE+"Estos son todos los productos del proveedor "+usuario.upper()+" que comercializa actualmente: "+color.END)

    mostrarProductosCompras(usuario)

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    respuesta =""


    while respuesta !="n":

        producto=validarString("Nombre del nuevo producto de compra: ",50)
        descripcion=validarString("Descripción del nuevo producto de compra: ",100)
        precio=validarIntroducirNumeroDecimal("Precio por unidad: ")

        # INSERCION DATOS TABLA PRODUCTOS_COMPRAS

        # Hallamos el ID del proveedor que tenemos que insertar
        idProveedor= BuscarIDproveedor(usuario)

        print("Id Proveedor: "+str(idProveedor))
        tabla = 'PRODUCTOS_COMPRAS'
        columnasProductosCompras= "ID_RELACION_PROVEEDOR,PRODUCTO,DESCRIPCION,PRECIO"
        productoCompra = str(idProveedor)+", '"+producto+"', '"+descripcion+"', "+str(precio)
        insertar_datos_generica(db, tabla, columnasProductosCompras, productoCompra)

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(usuario,"INSERT INTO " + tabla + "("+ columnasProductosCompras +") VALUES (" + productoCompra + ")")


        respuesta = input(color.BLUE+usuario.upper()+", quieres añadir más productos de compra: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+usuario.upper()+", quieres añadir más productos de compra: ? (s/n) "+color.END)


        print()
        print(color.BLUE+"Así queda la lista actualizada del proveedor "+usuario.upper()+ " con los nuevos productos añadidos:\n"+color.END)

        mostrarProductosCompras(usuario)


    #Me voy al menú anterior:
    mostrarMenuPresupuestos(usuario)

    db.close()




def borrarProductoCompra(usuario):


    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    respuesta =""
    respuestaBorrado=""


    while respuesta !="n":
        respuestaBorrado=""

        IDproductoCompraSeleccionado=seleccionarProductoCompra(usuario)
        nombreProductoSeleccionado= nombreProductoCompraSeleccionado(IDproductoCompraSeleccionado)

        respuestaBorrado = input(color.BLUE+"Estás seguro de borrar el producto '" + nombreProductoSeleccionado +"' ? (s/n) "+color.END)
        while (respuestaBorrado.lower() != "s" and respuestaBorrado.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuestaBorrado = input(color.BLUE+"Estás seguro de borrar el producto" + nombreProductoSeleccionado + " ? (s/n) "+color.END)

        if respuestaBorrado=="s":

            # BORRADO PRODUCTO VENTA
            cursor = db.cursor()
            consulta = 'DELETE FROM PRODUCTOS_COMPRAS WHERE ID_PRODUCTO_COMPRA = '+str(IDproductoCompraSeleccionado)
            #print(consulta)
            cursor.execute(consulta)
            db.commit()

            print(Fore.GREEN+'Producto borrado correctamente')

            # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,consulta)

        respuesta = input(color.BLUE+"Quieres borrar más productos de venta: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres borrar más productos de venta: ? (s/n) "+color.END)


        print()
        print(color.BLUE+"Así queda la lista actualizada del proveedor "+usuario.upper()+ ": "+color.END)

        mostrarProductosCompras(usuario)


    #Me voy al menú anterior:
    mostrarMenuPresupuestos(usuario)

    db.close()






def seleccionarPresupuesto(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    respuesta=""

    try:

        while respuesta !="n":
            opciones =[]

            # CONSULTA ELEGIR PRESUPUESTO (TABLA PEDIDOS_COMPRAS)
            cursor = db.cursor()
            consulta = 'SELECT A.ID_PEDIDO_COMPRA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR AND B.NOMBRE='+"'"+usuario+"'"
            #print(consulta)
            cursor.execute(consulta)
            resultados = cursor.fetchall()

            print(color.BLUE+"Estos son los presupuestos del proveedor "+usuario.upper()+color.END)
            print(color.BLUE+"Nº Pedido".ljust(15," ")+ "Proveedor".ljust(30," ")+"Fecha".ljust(15," ")+"Total Pedidos compras".ljust(30," ")+ "Aprobado".ljust(20," ")+color.END)
            for registro in resultados:
                opciones.append(registro[0])
                if(registro[4] == "No aprobado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
                if(registro[4] == "Aprobado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.GREEN+registro[4].ljust(20," ")+color.END)

            return validarNumeroLista(opciones)

    except:
        print(color.RED+'Error en la consulta o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


def imprimirPresupuesto(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    respuesta=""

    while respuesta != "n":

        #SELECCIONAR PRESUPUESTO:
        opcion= seleccionarPresupuesto(usuario)

        c = canvas.Canvas("Archivos/Presupuestos/presupuesto"+str(opcion)+".pdf",pagesize=(200,280),bottomup=0)

        # Logo Section
        # Setting th origin to (10,40)
        c.translate(10,40)
        # Inverting the scale for getting mirror Image of logo
        c.scale(1,-1)
        # Inserting Logo into the Canvas at required position
        #c.drawImage("Archivos/Imagenes/logoFoto.png",0,0,width=50,height=30)
        # Title Section
        # Again Inverting Scale For strings insertion
        c.scale(1,-1)
        # Again Setting the origin back to (0,0) of top-left
        c.translate(-10,-40)
        # Setting the font for Name title of company
        c.setFont("Helvetica-Bold",10)
        # Inserting the name of the company
        c.drawCentredString(100,20,usuario)
        # For under lining the title
        c.line(50,22,180,22)
        # Changing the font size for Specifying Address
        c.setFont("Helvetica-Bold",5)
        c.drawCentredString(100,30,usuario)


        #CONSULTA DATOS PROVEEDOR
        cursor = db.cursor()
        consulta= 'SELECT B.DIRECCION, B.CIF FROM PEDIDOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR AND A.ID_PEDIDO_COMPRA ='+str(opcion)
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        for registro in resultados:

            c.drawCentredString(100,35,registro[0])
            # Changing the font size for Specifying GST Number of firm
            c.setFont("Helvetica-Bold",6)
            c.drawCentredString(100,42,registro[1])

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
        c.drawString(90,90,"CIF : ")
        c.drawRightString(45,100,"Dirección : ")
        c.drawString(90,100,"Teléfono : ")
        c.drawString(134,100,"Email : ")
        c.drawString(134,90,"Web : ")


        c.setFont("Courier",3)

        #CONSULTA DATOS PROVEEDOR
        cursor = db.cursor()
        consulta= 'SELECT A.ID_PEDIDO_COMPRA, A.FECHA, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR AND A.ID_PEDIDO_COMPRA ='+str(opcion)
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()


        for registro in resultados:

            c.drawString(45,70,str(registro[0])) #numeroFactura
            c.drawString(45,80,registro[1]) # fecha
            if registro[2] == "Aprobado":
                c.drawImage("Archivos/Imagenes/Aprobado.png",120,140,width=60,height=35)

            elif registro[2] == "No aprobado":
                c.drawImage("Archivos/Imagenes/Denegado.jpg",120,140,width=60,height=35)


        c.drawString(45,90,"Pixels")
        c.drawString(100,90,"16296028E")
        c.drawString(45,100,"Vitoria-Gasteiz (Araba)")
        c.drawString(110,100,"670839934")
        c.drawString(148,100,"info@pixels.com")
        c.drawString(146,90,"www.pixels.com")

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
        consulta= 'SELECT C.PRODUCTO, C.DESCRIPCION, C.PRECIO, C.UNIDADES, C.SUBTOTAL, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, LISTA_COMPRAS C WHERE C.ID_RELACION_PEDIDO_COMPRA = A.ID_PEDIDO_COMPRA AND C.ID_RELACION_PEDIDO_COMPRA='+str(opcion)
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
        c.drawString(20,248,"Esto no es una presupuesto real")
        c.drawString(20,253," solo es una prueba.")
        #c.drawImage("Archivos/Imagenes/Firma.jpg",110,222,width=60,height=35)
        c.drawRightString(175,253,"Firmado: "+usuario.upper())
        #c.drawRightString(175,253,"Firma Autorizada")
        # End the Page and Start with new


        c.showPage()
        # Saving the PDF
        c.save()

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(usuario,"El proveedor "+usuario.upper()+" ha impreso la factura:'factura"+str(opcion)+'"')

        respuesta = input(color.BLUE+"Quieres imprimir más presupuestos: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres imprimir más presupuestos ? (s/n) "+color.END)

    # Volver al menú anterior
    mostrarMenuPresupuestos(usuario)


def mostrarMenuPresupuestos(usuario):

    opciones = [1,2,3,4]

    print(color.BLUE+"Elige una opción:\n"+color.END)
    print(color.BLUE+"\t1"+color.END+" Imprimir presupuesto para PIXELS")
    print(color.BLUE+"\t2"+color.END+" Añadir nuevos productos de compra")
    print(color.BLUE+"\t3"+color.END+" Borrar productos de compra")
    print(color.BLUE+"\t4"+color.END+" Salir")

    print()

    opcion = validarNumeroLista(opciones)

    if opcion == 1:
        imprimirPresupuesto(usuario)
    if opcion == 2:
        nuevoProductoCompra(usuario)
    if opcion == 3:
        borrarProductoCompra(usuario)

