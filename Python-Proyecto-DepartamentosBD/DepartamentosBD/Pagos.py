
import pymysql
from Color import *
from FechaRegistros import registroBD
from Validaciones import *
from colorama import init, Fore
init(autoreset=True)
from FechaRegistros import *
from ConsultasBD import *
from GraficoPresupuestos import *
from datetime import *




def aprobarRechazarPedidosVenta(opcion):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:

        #CONSULTA ACTUALIZACIÓN ESTADO FACTURAS
        cursor = db.cursor()
        consulta = 'UPDATE PEDIDOS_VENTAS SET PAGO='+"'Pagado'"+' WHERE ID_PEDIDO_VENTA ='+str(opcion)
        #print(consulta)
        cursor.execute(consulta)
        db.commit()
        print(Fore.GREEN+'Registro actualizado correctamente')

    except:
        print(color.RED+'Error en la consulta o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


    # Cerrar la conexión
    db.close()



# Aquí llegan los clientes que tienen pendientes pagos de facturas, les pongo acceso a la aplicación a la hora de crear el cliente también le doy acceso con el nombre de cliente y el pasword es el CIF de la empresa:

def pagarFacturas(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    respuesta=""

    while respuesta != "n" :

        opciones = []

        # CONSULTA ELEGIR PRESUPUESTO (TABLA PEDIDOS_COMPRAS)
        cursor = db.cursor()
        consulta = 'SELECT A.ID_PEDIDO_VENTA, B.NOMBRE, A.FECHA, COUNT(C.ID_RELACION_PEDIDO_VENTA), A.TOTAL_PEDIDOS_VENTAS, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B, LISTA_VENTAS C WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE AND C.ID_RELACION_PEDIDO_VENTA= A.ID_PEDIDO_VENTA AND A.PAGO ='+"'Pendiente'"+ ' AND B.NOMBRE='+"'"+usuario+"'"+' GROUP BY C.ID_RELACION_PEDIDO_VENTA'
        #print(consulta)
        cursor.execute(consulta)
        db.commit()
        resultados = cursor.fetchall()

        if len(resultados) > 0:

            print(color.BLUE+usuario.upper()+" ,estos son todos los pagos que tienes pendiente: \n")
            print(color.BLUE+"\t" +"Número Factura".ljust(20," ")+"Cliente".ljust(15," ")+"Fecha".ljust(15," ")+"Número de productos".ljust(30," ")+"Precio total".ljust(20," ")+"Estado del pago".ljust(20," ")+color.END)

            for registro in resultados:
                opciones.append(registro[0])
                print(color.BLUE + "\t" + str(registro[0]).ljust(20," ") + color.END + registro[1].ljust(15," ") + registro[2].ljust(15," ") + str(registro[3]).ljust(30," ") + str(str(registro[4])+" €").ljust(20," ") +  color.RED+registro[5].ljust(20," ")+color.END)


            print()

            opcion = validarNumeroLista(opciones)
            tarjeta = validarTarjetaCredito()
            importe = validarIntroducirNumero("Introduce importe a pagar: ")

            for registro in resultados:

                if registro[0]== opcion and registro[5] == "Pendiente":
                    while importe != registro[4]:
                        print(color.RED+"Importe incorrecto !! tienes que introducir: "+str(registro[4])+" €"+color.END)
                        importe = validarIntroducirNumero("Introduce el importe correcto a pagar: ")

                    if  registro[4] == importe:

                        aprobarRechazarPedidosVenta(opcion)

                        print(Fore.GREEN+"La Factura Nº "+str(registro[0])+ " ha sido abonada correctamente")

                        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
                        registroBD(usuario,"Pago factura: "+str(opcion) +"  --> "+ consulta)


            print()

            # CONSULTA ELEGIR PRESUPUESTO (TABLA PEDIDOS_COMPRAS)
            cursor = db.cursor()
            consulta = 'SELECT A.ID_PEDIDO_VENTA, B.NOMBRE, A.FECHA, COUNT(C.ID_RELACION_PEDIDO_VENTA), A.TOTAL_PEDIDOS_VENTAS, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B, LISTA_VENTAS C WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE AND C.ID_RELACION_PEDIDO_VENTA= A.ID_PEDIDO_VENTA AND B.NOMBRE='+"'"+usuario+"'"+' GROUP BY C.ID_RELACION_PEDIDO_VENTA'
            #print(consulta)
            cursor.execute(consulta)
            db.commit()
            resultados = cursor.fetchall()

            print(color.BLUE+usuario.upper()+", así quedan todos tus pagos: \n")

            print(color.BLUE+"\t" +"Número Factura".ljust(20," ")+"Cliente".ljust(15," ")+"Fecha".ljust(15," ")+"Número de productos".ljust(30," ")+"Precio total".ljust(20," ")+"Estado del pago".ljust(20," ")+color.END)

            for registro in resultados:
                if registro[5] == "Pendiente" :
                     print(color.BLUE + "\t" + str(registro[0]).ljust(20," ") + color.END + registro[1].ljust(15," ") + registro[2].ljust(15," ") + str(registro[3]).ljust(30," ") + str(str(registro[4])+" €").ljust(20," ") +  color.RED+registro[5].ljust(20," ")+color.END)

                if registro[5] == "Pagado" :
                     print(color.BLUE + "\t" + str(registro[0]).ljust(20," ") + color.END + registro[1].ljust(15," ") + registro[2].ljust(15," ") + str(registro[3]).ljust(30," ") + str(str(registro[4])+" €").ljust(20," ") +  Fore.GREEN+registro[5].ljust(20," ")+color.END)

            print()


        else:
            print("El cliente "+color.RED+usuario.upper()+", no tienes pagos pendientes !!"+color.END)

        respuesta = input(color.BLUE+"Quieres hacer más pagos ? (s/n): "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
                print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                respuesta = input(color.BLUE+"Quieres hacer más pagos ? (s/n) "+color.END)
