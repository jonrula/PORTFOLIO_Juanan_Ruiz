import pymysql
from Color import *
from FechaRegistros import registroBD
from Validaciones import *
from ConsultasBD import *
from GraficoPresupuestos import *
from datetime import *



def comprobarSiExisteProveedor(cif):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()
    #CONSULTA
    consulta = 'SELECT * FROM PROVEEDORES'
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    resultados = cursor.fetchall()

    #correcto =False

    #En este caso, sabemos qué columnas y datos tiene la tabla en concreto
    for registro in resultados:
        if registro[2] == cif:
            return True



def crearProveedor(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    print(color.BLUE+"Introduce los datos de los nuevo(s) proveedore(s): "+color.END)
    respuesta =""

    while respuesta !="n":

        proveedor = validarString("Nombre: ",20)
        cif = validarCIF()

        while comprobarSiExisteProveedor(cif):
            print(color.RED+"Error !!... ya existe un proveedor con el CIF: "+ cif +color.END)
            cif = validarCIF()

        direccion = validarString("Dirección: ",20)
        telefono = validarTelefono()
        email= validarEmail()
        web = validarWeb()

        # INSERCION DATOS TABLA PROVEEDORES

        tabla = 'PROVEEDORES'
        columnasProveedores = "NOMBRE,CIF,DIRECCION,TELEFONO,EMAIL,WEB"
        nuevoProveedor="'"+proveedor.title()+"','"+cif+"','"+direccion.capitalize()+"','"+telefono+"','"+email+"','"+web+"'"
        consulta = insertar_datos_generica(db, tabla, columnasProveedores, nuevoProveedor)
        registroBD(usuario,consulta)

        # INSERCION DATOS TABLA USUARIOS, PARA TENER ACCESO A LA APLICACION Y HACER PRESUPUESTOS

        tabla = 'USUARIOS'
        columnaNuevoUsuario = "ID_RELACION_PROVEEDOR,NOMBRE,CONTRASEÑA,DEPARTAMENTOS"

        # Ahora busco la ID del proveedor para relacionarla con la tabla usuarios:
        idNuevoProveedor= BuscarIDmaximo('ID_PROVEEDOR','PROVEEDORES')

        nuevoUsuario=str(idNuevoProveedor)+",'"+proveedor.title()+"','"+cif+"'"+",'Presupuestos'"

        consulta= insertar_datos_generica(db, tabla, columnaNuevoUsuario, nuevoUsuario)
        registroBD(usuario,consulta)


        respuesta = input(color.BLUE+"Quieres añadir más proveedores: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres añadir más proveedores: ? (s/n) "+color.END)


    # Volver al menú anterior:
    mostrarMenuCompras(usuario)


def borrarPresupuesto(usuario):
    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    respuesta=""
    opciones =[]

    try:

        while respuesta !="n":
            opciones =[]

            #CONSULTA LISTADO DE TODOS LOS PEDIDOS
            cursor = db.cursor()
            consulta = 'SELECT A.ID_PEDIDO_COMPRA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR'
            cursor.execute(consulta)
            resultados = cursor.fetchall()



            print(color.BLUE+"Estos son todos los presupuestos: \n"+color.END)
            print(color.BLUE+"Nº Pedido".ljust(15," ")+ "Proveedor".ljust(30," ")+"Fecha".ljust(15," ")+"Total Pedidos compras".ljust(30," ")+ "Aprobado".ljust(20," "))

            for registro in resultados:
                opciones.append(registro[0])
                if(registro[4] == "No aprobado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
                if(registro[4] == "Aprobado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.GREEN+registro[4].ljust(20," ")+color.END)

            print()

            opcion = validarNumeroLista(opciones)


            #CONSULTA BORRADO PRESUPUESTO ELEGIDO
            cursor = db.cursor()
            #consulta = 'UPDATE PEDIDOS_COMPRAS SET APROBADO='+"'Aprobado'"+' WHERE ID_PEDIDO_COMPRA ='+str(opcion)
            consulta = 'DELETE FROM PEDIDOS_COMPRAS WHERE ID_PEDIDO_COMPRA ='+str(opcion)
            #print(consulta)
            cursor.execute(consulta)
            db.commit()

            print(Fore.GREEN+'Presupuesto borrado correctamente')

            print()

            #CONSULTA CON LA LISTA DE TODOS LOS PRESUPUESTOS PARA VER LOS ESTADOS
            cursor = db.cursor()
            consultaEstados = 'SELECT A.ID_PEDIDO_COMPRA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR'
            cursor.execute(consultaEstados)
            db.commit()


            resultados = cursor.fetchall()

            print(color.BLUE+"Así quedan los presupuestos: \n"+color.END)
            print(color.BLUE+"Nº Pedido".ljust(15," ")+ "Proveedor".ljust(30," ")+"Fecha".ljust(15," ")+"Total Pedidos compras".ljust(30," ")+ "Aprobado".ljust(20," "))

            for registro in resultados:
                opciones.append(registro[0])
                if(registro[4] == "No aprobado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
                if(registro[4] == "Aprobado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.GREEN+registro[4].ljust(20," ")+color.END)

            print()

            # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,consulta)


            respuesta = input(color.BLUE+"Quieres borrar más PRESUPUESTOS: ? (s/n) "+color.END)
            while (respuesta.lower() != "s" and respuesta.lower() != "n"):
                    print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                    respuesta = input(color.BLUE+"Quieres borrar más PRESUPUESTOS ? (s/n) "+color.END)


    except:
        print(color.RED+'Error en la consulta o en la conexión'+color.END)
        db.rollback() #Deshacer cambios

    # Cerrar la conexión
    db.close()

    #Me voy al menú anterior:
    mostrarMenuCompras(usuario)


def aprobarRechazarPedidosCompra(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        respuesta=""
        opciones =[]

        while respuesta !="n":


            #CONSULTA LISTADO DE TODOS LOS PEDIDOS
            cursor = db.cursor()
            consulta = 'SELECT A.ID_PEDIDO_COMPRA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR'
            cursor.execute(consulta)
            resultados = cursor.fetchall()

            print(color.BLUE+"Estos son todos los pedidos que hay con sus respectivos estados: \n"+color.END)
            print(color.BLUE+"Nº Pedido".ljust(15," ")+ "Proveedor".ljust(30," ")+"Fecha".ljust(15," ")+"Total Pedidos compras".ljust(30," ")+ "Aprobado".ljust(20," "))

            for registro in resultados:
                opciones.append(registro[0])
                if(registro[4] == "No aprobado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
                if(registro[4] == "Aprobado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.GREEN+registro[4].ljust(20," ")+color.END)

            print()

            opcion = validarNumeroLista(opciones)

            for registro in resultados:
                if(registro[0] == opcion):
                    if(registro[4] == "No aprobado"):
                        print (color.BLUE+"El pedido "+str(registro[0]) +" del proveedor " +registro[1].upper() +" está: " + color.RED+registro[4].upper()+color.END)
                    if(registro[4] == "Aprobado"):
                        print (color.BLUE+"El pedido "+str(registro[0]) +" del proveedor " +registro[1].upper() +" está: " + color.GREEN+registro[4].upper()+color.END)

            print()

            #Elegimos estado presupuesto: Aprobar/Rechazar
            aprobado= eligeEstadoAprobado()

            #CONSULTA ACTUALIZACIÓN ESTADO PRESUPUESTO
            cursor = db.cursor()
            #consulta = 'UPDATE PEDIDOS_COMPRAS SET APROBADO='+"'Aprobado'"+' WHERE ID_PEDIDO_COMPRA ='+str(opcion)
            consulta = 'UPDATE PEDIDOS_COMPRAS SET APROBADO='+aprobado+' WHERE ID_PEDIDO_COMPRA ='+str(opcion)
            #print(consulta)
            cursor.execute(consulta)
            db.commit()
            print(Fore.GREEN+'Registro actualizado correctamente')

            # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,consulta)

            print()

            #CONSULTA CON LA LISTA DE TODOS LOS PRESUPUESTOS PARA VER LOS ESTADOS
            cursor = db.cursor()
            consultaEstado = 'SELECT A.ID_PEDIDO_COMPRA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR'
            cursor.execute(consultaEstado)
            db.commit()


            resultados = cursor.fetchall()

            print(color.BLUE+"Así quedan los pedidos: \n"+color.END)
            print(color.BLUE+"Nº Pedido".ljust(15," ")+ "Proveedor".ljust(30," ")+"Fecha".ljust(15," ")+"Total Pedidos compras".ljust(30," ")+ "Aprobado".ljust(20," "))

            for registro in resultados:
                opciones.append(registro[0])
                if(registro[4] == "No aprobado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
                if(registro[4] == "Aprobado"):
                    print(color.BLUE+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.GREEN+registro[4].ljust(20," ")+color.END)

            print()

            # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,consulta)


            respuesta = input(color.BLUE+"Quieres aprobar/rechazar más PRESUPUESTOS: ? (s/n) "+color.END)
            while (respuesta.lower() != "s" and respuesta.lower() != "n"):
                    print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                    respuesta = input(color.BLUE+"Quieres aprobar/rechazar más PRESUPUESTOS ? (s/n) "+color.END)

    except:
        print(color.RED+'Error en la consulta o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


    # Cerrar la conexión
    db.close()

    #Me voy al menú anterior:
    mostrarMenuCompras(usuario)


def insertar_datos_generica(db,tabla,columnas,valores):

    try:
        cursor = db.cursor()

        consulta = "INSERT INTO " + tabla + "("+ columnas +") VALUES (" + valores + ")"
        #print(consulta)

        # Ejecutar SQL --> es un string
        cursor.execute(consulta)
        db.commit()
        print(Fore.GREEN+'Registro insertado correctamente')

        # Necesito el dato de la consulta realizada para pasársela luego al registroBD
        return consulta
    except:
        print(color.RED+'Error en la consulta o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


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
        #print (registro[0])
        return registro[0]

    db.close()


def mostrarUltimaListaCompraProveedor(relacionnuevoIDPedidoCompra):
    contador = 0


    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()

    #CONSULTA
    consulta = 'SELECT ID_RELACION_PEDIDO_COMPRA, PRODUCTO,DESCRIPCION,PRECIO,UNIDADES,SUBTOTAL FROM LISTA_COMPRAS WHERE ID_RELACION_PEDIDO_COMPRA= '+ str(relacionnuevoIDPedidoCompra)
    #print(consulta)
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()

    print()

    print(color.BLUE+"Esta es la lista de la compra con los productos que has elegido:\n"+color.END)
    print(color.BLUE+"\t"+"Numero".ljust(20," ")+"Producto".ljust(50," ")+"Descripción".ljust(50," ")+"Precio".ljust(20," ")+"Unidades".ljust(20," ")+"Subtotal".ljust(20," "))


    for registro in resultados:
        contador +=1
        print("\t"+str(contador).ljust(20," ")+registro[1].ljust(50," ")+registro[2].ljust(50," ")+str(str(registro[3])+" €").ljust(20," ")+ str(registro[4]).ljust(20," ")+str(str(registro[5])+" €").ljust(20," "))

    print()



def mostrarTotalCompraProveedor(relacionnuevoIDPedidoCompra):


    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()

    #CONSULTA
    consulta = 'SELECT A.ID_PEDIDO_COMPRA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B  WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR AND A.ID_PEDIDO_COMPRA= '+ str(relacionnuevoIDPedidoCompra)
    #print(consulta)
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()

    print()

    print(color.BLUE+"Esta es el TOTAL de la lista de la compra de todos los productos que has elegido:\n"+color.END)
    print(color.BLUE+"\t"+"Pedido Compra".ljust(20," ")+"Proveedor".ljust(20," ")+"Fecha".ljust(20," ")+"Total pedido compra".ljust(30," ")+"Estado".ljust(20," "))

    for registro in resultados:
        if(registro[4]=="No aprobado"):
         print(color.BLUE+"\t"+str(registro[0]).ljust(20," ")+color.END+registro[1].ljust(20," ")+registro[2].ljust(20," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
        if(registro[4]=="Aprobado"):
         print(color.BLUE+"\t"+str(registro[0]).ljust(20," ")+color.END+registro[1].ljust(20," ")+registro[2].ljust(20," ")+str(str(registro[3])+" €").ljust(30," ")+Fore.GREEN+registro[4].ljust(20," ")+color.END)


    print()



def mostrarNombreProveedor(idproveedor):

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()
    #CONSULTA
    consulta = 'SELECT * FROM PROVEEDORES WHERE ID_PROVEEDOR= '+str(idproveedor)
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()

    for registro in resultados:

        print (registro[1])
        return registro[1]


    db.close()


def mostrarProductosCompras(idproveedor):

    opciones =[]

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()

    #CONSULTA
    consulta = 'SELECT A.ID_PRODUCTO_COMPRA, A.PRODUCTO, A.DESCRIPCION, A.PRECIO FROM PRODUCTOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR AND B.ID_PROVEEDOR = '+str(idproveedor)
    #print(consulta)
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()

    print()

    nombreProveedor= mostrarNombreProveedor(idproveedor)

    print(color.BLUE+"Estos son todos los productos del proveedor "+nombreProveedor.upper()+":\n"+color.END)
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





def mostrarProveedores():
    opciones =[]

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()
    #CONSULTA
    consulta = 'SELECT * FROM PROVEEDORES'
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()

    print()

    for registro in resultados:
        opciones.append(registro[0])
        print(color.BLUE+"\t"+str(registro[0])+color.END+" " +registro[1])

    print()

    opcion = validarNumeroLista(opciones)

    for registro in resultados:
        if(registro[0] == opcion):
            #print (registro[1])
            return registro[0]


    db.close()




def borrarProveedor(usuario):


    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    respuesta =""
    respuestaBorrado=""


    while respuesta !="n":

        respuestaBorrado=""

        # SELECCIONAMOS EL PROVEEDOR QUE QUEREMOS BORRAR
        IDproveedorSeleccionado=mostrarProveedores()

        # NOMBRE DEL PROVEEDOR SELECCIONADO:
        nombreProveedorSeleccionado=mostrarNombreProveedor(IDproveedorSeleccionado)

        respuestaBorrado = input(color.BLUE+"Estás seguro de borrar al cliente " +nombreProveedorSeleccionado.upper()+ " ? (s/n) "+color.END)
        while (respuestaBorrado.lower() != "s" and respuestaBorrado.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuestaBorrado = input(color.BLUE+"Estás seguro de borrar al cliente" +nombreProveedorSeleccionado.upper()+ " ? (s/n) "+color.END)

        if respuestaBorrado=="s":

            #CONSULTA BORRADO PROVEEDOR SELECCIONADO
            cursor = db.cursor()
            consulta = 'DELETE FROM PROVEEDORES WHERE ID_PROVEEDOR ='+str(IDproveedorSeleccionado)
            #print(consulta)
            cursor.execute(consulta)
            db.commit()

            # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,consulta)

            print(Fore.GREEN+"El proveedor " +nombreProveedorSeleccionado.upper()+ " ha sido borrado correctamente")

            print()

            #CONSULTA DE LOS PROVEEDORES EXISTENTES:
            consulta = 'SELECT * FROM PROVEEDORES'
            cursor.execute(consulta)
            resultados = cursor.fetchall()

            print()

            print(color.BLUE+"Estos son los proveedores con los que trabajamos actualmente en PIXELS: \n")

            for registro in resultados:
                print(color.BLUE+"\t"+str(registro[0])+color.END+" " +registro[1])

            print()


        respuesta = input(color.BLUE+"Quieres borrar más proveedores: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres borrar más proveedores: ? (s/n) "+color.END)


    db.close()

    # Volver al menú anterior:
    mostrarMenuCompras(usuario)



def crearNuevoPedidoCompra(usuario):

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    respuesta =""
    respuesta2=""
    relacionnuevoIDPedidoCompra=0

    print(color.BLUE+"Introduce los datos para el presupuesto de compra: "+color.END)

    while respuesta !="n":

        #Elegir Proveedor
        iDproveedor= mostrarProveedores()

       # Fecha pedido:
        fecha = validarFecha("Fecha presupuesto: ")

        # Creamos el nuevo pedido de compra, para sacar la nueva ID y luego poder relacionarla con la lista de compra asociada al nuevo pedido

        tabla = 'PEDIDOS_COMPRAS'
        columnasPedidoCompras = "ID_RELACION_PROVEEDOR,FECHA"
        pedidoCompra = str(iDproveedor)+",'"+fecha+"'"
        consulta= insertar_datos_generica(db, tabla, columnasPedidoCompras,pedidoCompra)

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(usuario,consulta)

        # Elijo productos de la lista de compras
        respuesta2="s"
        while respuesta2 !="n":
                # Elijo productos de la lista de compras
                productoCompras = mostrarProductosCompras(iDproveedor)
                #print(productoCompras)

               # Calculo los subtotales del producto
                unidades= validarIntroducirNumero("Introducir unidades: ")

                # Añado los productos de la lista de compras

                tabla = 'LISTA_COMPRAS'
                columnasListaCompras = "ID_RELACION_PEDIDO_COMPRA,PRODUCTO,DESCRIPCION,PRECIO,UNIDADES"

                #consulta = 'SELECT MAX(ID_PEDIDO_COMPRA) FROM PEDIDOS_COMPRAS'
                relacionnuevoIDPedidoCompra = BuscarIDmaximo('ID_PEDIDO_COMPRA','PEDIDOS_COMPRAS')

                listaCompra = str(relacionnuevoIDPedidoCompra)+","+productoCompras+","+str(unidades)
                #print(listaCompra)

                consulta =insertar_datos_generica(db, tabla, columnasListaCompras,listaCompra)

                # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
                registroBD(usuario,consulta)

                respuesta2 = input(color.BLUE+"Quieres añadir más productos: ? (s/n) "+color.END)
                while (respuesta2.lower() != "s" and respuesta2.lower() != "n"):
                    print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                    respuesta2 = input(color.BLUE+"Quieres añadir más productos ? (s/n) "+color.END)

        # MUESTRO TODA LA LISTA DE LA COMPRA DEL PROVEEDOR SELECCIONADO
        mostrarUltimaListaCompraProveedor(relacionnuevoIDPedidoCompra)


        respuesta = input(color.BLUE+"Quieres añadir más presupuestos: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres añadir más presupuestos ? (s/n) "+color.END)


    # MUESTRO EL TOTAL DEL PEDIDO_COMPRA (SUMA DE TODA LA LISTA DE LA COMPRA DEL PROVEEDOR SELECCIONADO), QUE ES EL LA ULTIMO PEDIDO_COMPRA
    mostrarTotalCompraProveedor(relacionnuevoIDPedidoCompra)


    #Me voy al menú anterior:
    mostrarMenuCompras(usuario)


def mostrarMenuCompras(usuario):

    opciones = [1,2,3,4,5,6,7]

    print(color.BLUE+"Elige una opción:\n"+color.END)
    print(color.BLUE+"\t1"+color.END+" Petición de presupuesto")
    print(color.BLUE+"\t2"+color.END+" Aprobar / rechazar presupuesto")
    print(color.BLUE+"\t3"+color.END+" Borrar presupuesto")
    print(color.BLUE+"\t4"+color.END+" Añadir proveedor")
    print(color.BLUE+"\t5"+color.END+" Borrar proveedor")
    print(color.BLUE+"\t6"+color.END+" Mostrar gráficos de presupuestos")
    print(color.BLUE+"\t7"+color.END+" Salir")

    print()

    opcion = validarNumeroLista(opciones)

    if opcion == 1:
        crearNuevoPedidoCompra(usuario)
    if opcion == 2:
        aprobarRechazarPedidosCompra(usuario)
    if opcion == 3:
        borrarPresupuesto(usuario)
    if opcion == 4:
        crearProveedor(usuario)
    if opcion == 5:
        borrarProveedor(usuario)
    if opcion == 6:
       elegirGraficoPresupuestos(usuario)




