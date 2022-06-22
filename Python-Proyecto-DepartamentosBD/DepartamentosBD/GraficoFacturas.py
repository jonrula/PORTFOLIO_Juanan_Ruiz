
import csv
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import numpy as np
import pymysql


from Color import *
from FechaRegistros import *
from FechaRegistros import registroBD
from Validaciones import *
from Ventas import *
from colorama import init, Fore

def elegirGraficoFacturas(usuario):


    listaNivel = [1, 2, 3, 4]

    print(color.BLUE+"Elige un gráfico:\n\n"+color.END+

        "\tGráfico de barras: \n\n"+
          color.BLUE+"\t\t1"+color.END +" Selecciona Factura\n"+
          color.BLUE+"\t\t2"+color.END +" Todos las facturas\n"+
          color.BLUE+"\t\t3"+color.END +" Todas las facturas de un cliente\n"+
          color.BLUE+"\t\t4"+color.END +" Volver\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        graficoBarrasSeleccionaFactura(usuario)
    if opcion == 2:
        graficoBarrasTodasLasFacturas(usuario)
    if opcion == 3:
        graficoBarrasSeleccionaFacturasCliente(usuario)


def mostrarClientes():
    opciones =[]

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    #CONSULTA
    cursor = db.cursor()
    consulta = 'SELECT * FROM CLIENTES'
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    print()

    for registro in resultados:
        opciones.append(registro[0])
        print(color.BLUE+"\t"+str(registro[0])+color.END+" " +registro[1])

    print()

    db.close()

    return validarNumeroLista(opciones)




def mostrarNombreCliente(idcliente):

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    #CONSULTA
    cursor = db.cursor()
    consulta = 'SELECT * FROM CLIENTES WHERE ID_CLIENTE= '+str(idcliente)
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    for registro in resultados:
        return registro[1]

    db.close()



def seleccionarFactura():

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

            print(color.BLUE+"Elige una factura: \n")
            print(color.BLUE+"\t"+"Nº Factura".ljust(15," ")+ "Cliente".ljust(30," ")+"Fecha".ljust(15," ")+"Total Pedidos ventas".ljust(30," ")+ "Pagado".ljust(20," ")+color.END)
            for registro in resultados:
                opciones.append(registro[0])
                if(registro[4] == "Pendiente"):
                    print(color.BLUE+"\t"+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.RED+registro[4].ljust(20," ")+color.END)
                if(registro[4] == "Pagado"):
                    print(color.BLUE+"\t"+str(registro[0]).ljust(15," ")+color.END+registro[1].ljust(30," ")+registro[2].ljust(15," ")+str(str(registro[3])+" €").ljust(30," ")+color.GREEN+registro[4].ljust(20," ")+color.END)

            print()

            return validarNumeroLista(opciones)

    except:
        print(color.RED+'Error en la consulta o en la conexión'+color.END)
        db.rollback() #Deshacer cambios



def graficoBarrasSeleccionaFactura(usuario):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        labels =[]
        lineaProductoSubtotal =[]
        listaAprobados=[]

        aprobado =""
        total =0
        cliente =""
        factura =0
        producto= ""
        descripcion= ""
        precio= 0.0
        unidades= 0

        opcion = seleccionarFactura()

        #CONSULTA DE TODAS LAS FACTURAS
        cursor = db.cursor()
        consulta= 'SELECT A.ID_PEDIDO_VENTA, B.NOMBRE, C.PRODUCTO, C.DESCRIPCION, C.PRECIO, C.UNIDADES, C.SUBTOTAL, A.TOTAL_PEDIDOS_VENTAS, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B, LISTA_VENTAS C WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE AND C.ID_RELACION_PEDIDO_VENTA = A.ID_PEDIDO_VENTA AND C.ID_RELACION_PEDIDO_VENTA= '+str(opcion)
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        if len(resultados) > 0:

            for registro in resultados:

                factura = registro[0]
                cliente = registro[1]+"\n"

                producto=registro[2]
                descripcion= registro[3]
                precio= registro[4]
                unidades= registro[5]

                # 'Labels' descriptivos de cada barra en el eje x
                labels.append("\n"+descripcion+"\n"+str(precio)+" € unidad"+"\n"+ str(unidades)+ " unidad(es)" )

                subtotal= registro[6]
                lineaProductoSubtotal.append(subtotal)
                aprobado = registro[8]
                listaAprobados.append(aprobado)
                total = registro[7]


            lineaProductoSubtotal.append(total)
            labels.append("\nTOTAL")

           # Add some text for labels, title and custom x-axis tick labels, etc.

            x = np.arange(len(labels))  # the label locations
            width = 0.35  # the width of the bars+

            fig, ax = plt.subplots()

            rects1 = ax.bar(x, lineaProductoSubtotal, width,color=['grey'])
            rects2 = ax.bar((0.65+width)*len(resultados),total, width,color=['yellowgreen' if (aprobado=="Pagado") else 'lightcoral'])

            ax.set_ylabel('\nPrecio en €\n', fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_title('\nCliente: '+cliente+'Factura: '+ str(factura)+"\n", fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_xticks(x)
            ax.set_xticklabels(labels,fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 10})

            ax.legend()
            plt.xlabel("\nProductos\n",fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})

            ax.set_ylim([0, float(max(lineaProductoSubtotal))*1.3])


            def autolabel(rects):
                  contador =-1
                  """Attach a text label above each bar in *rects*, displaying its height."""
                  for rect in rects:
                         contador +=1
                         height = rect.get_height()

                         ax.annotate('{}'.format(str(lineaProductoSubtotal[contador])+ " €"),
                               xy=(rect.get_x() + rect.get_width() / 2, height),
                               xytext=(0, 3),  # 3 points vertical offset
                               textcoords="offset points",
                               ha='center', va='bottom')

            def autolabel2(rects):
                  """Attach a text label above each bar in *rects*, displaying its height."""
                  for rect in rects:

                         height = rect.get_height()

                         ax.annotate('{}'.format(str(total)+ " €"),
                               xy=(rect.get_x() + rect.get_width() / 2, height),
                               xytext=(0, 3),  # 3 points vertical offset
                               textcoords="offset points",
                               ha='center', va='bottom')

            autolabel(rects1)
            autolabel2(rects2)


            # Estos datos es para poner una leyenda fija en el gráfico que solo informe del color que corresponde a cada subtotal de cada factura y el total de la factura:
            lineaPedido = mpatches.Patch(color='grey', label='SUBTOTALES FACTURAS')
            pagado = mpatches.Patch(color='yellowgreen', label='PAGADO')
            pendiente = mpatches.Patch(color='lightcoral', label='PENDIENTE')


            plt.legend(handles=[lineaPedido,pagado, pendiente],loc="upper left",prop={'size':8},title="Leyenda: \n")

            fig.tight_layout()
            plt.show()

           # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,"Visualización del gráfico de barras de la factura: "+ str(factura))


        else:
            print(color.RED+"Error !! ... no existen facturas !!"+color.END)

        elegirGraficoFacturas(usuario)


def graficoBarrasTodasLasFacturas(usuario):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        labels =[]
        lineaProductoSubtotal =[]
        listaAprobados=[]

        #CONSULTA DE TODOS LOS PRESUPUESTOS
        cursor = db.cursor()
        consulta= 'SELECT A.ID_PEDIDO_VENTA, B.NOMBRE, B.DIRECCION, B.CIF, A.TOTAL_PEDIDOS_VENTAS, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE'
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        if len(resultados) > 0:

            for registro in resultados:

                presupuesto = registro[0]
                proveedor = registro[1]+"\n"
                labels.append(proveedor+ str(presupuesto))
                total= registro[4]
                lineaProductoSubtotal.append(total)
                aprobado = registro[5]
                listaAprobados.append(aprobado)


           # Add some text for labels, title and custom x-axis tick labels, etc.

            x = np.arange(len(labels))  # the label locations
            width = 0.35  # the width of the bars+

            # Tamaño del gráfico
            fig, ax = plt.subplots(figsize=(len(labels)*1.2, len(labels)/2))


            rects1 = ax.bar(x, lineaProductoSubtotal,width,color=['yellowgreen' if (aprobado=="Pagado") else 'lightcoral' for aprobado in listaAprobados])
            plt.hist(len(labels), rwidth=0.1)

            ax.set_ylabel('\nTOTAL factura en €\n', fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_title("\nTodas las facturas\n", fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_xticks(x)
            ax.set_xticklabels(labels,fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 10})
            ax.legend()
            plt.xlabel("\nCliente y Nº Factura\n",fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})


            # Tamaño del eje y para que sea un poco más grande y se vean bien los datos
            #ax.set_ylim([0, float(max(lineaProductoSubtotal))*1.3])
            ax.set_ylim([0, float(max(lineaProductoSubtotal))*1.2])


            def autolabel(rects):
                  contador =-1
                  """Attach a text label above each bar in *rects*, displaying its height."""
                  for rect in rects:
                         contador +=1
                         height = rect.get_height()

                         ax.annotate('{}'.format(str(lineaProductoSubtotal[contador])+ " €"),
                               xy=(rect.get_x() + rect.get_width() / 2, height),
                               xytext=(0, 3),  # 3 points vertical offset
                               textcoords="offset points",
                               ha='center', va='bottom')



            autolabel(rects1)


            # Estos datos es para poner una leyenda fija en el gráfico que solo informe del color que corresponde a cada subtotal de cada factura y el total de la factura:
            aprobado = mpatches.Patch(color='yellowgreen', label='PAGADO')
            noAprobado = mpatches.Patch(color='lightcoral', label='PENDIENTE')


            plt.legend(handles=[aprobado, noAprobado],loc="upper left",prop={'size':8},title="Leyenda:")

            fig.tight_layout()

            plt.show()

           # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,"Visualización del gráfico de barras de todas las facturas")


        else:
            print(color.RED+"Error !! ... no existen facturas !!"+color.END)



        elegirGraficoFacturas(usuario)



def graficoBarrasSeleccionaFacturasCliente(usuario):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")


        labels =[]
        lineaProductoSubtotal =[]
        listaAprobados=[]

        # Id cliente elegido
        opcion= mostrarClientes()

        # Nombre del cliente elegido
        cliente = mostrarNombreCliente(opcion)

        contador=0

        #CONSULTA DE TODAS LAS FACTURAS DEL CLIENTE ELEGIDO
        cursor = db.cursor()
        consulta= 'SELECT A.ID_PEDIDO_VENTA, B.NOMBRE, B.DIRECCION, B.CIF, A.TOTAL_PEDIDOS_VENTAS, A.PAGO FROM PEDIDOS_VENTAS A, CLIENTES B WHERE A.ID_RELACION_CLIENTE = B.ID_CLIENTE AND A.ID_RELACION_CLIENTE='+str(opcion)
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        if len(resultados) > 0:

            for registro in resultados:

                factura = registro[0]
                cliente = registro[1]
                labels.append("Factura: "+str(factura))
                total= registro[4]
                lineaProductoSubtotal.append(total)
                aprobado = registro[5]
                listaAprobados.append(aprobado)

            x = np.arange(len(labels))  # the label locations
            width = 0.35  # the width of the bars+


            fig, ax = plt.subplots()



            rects1 = ax.bar(x, lineaProductoSubtotal, width,color=['yellowgreen' if (aprobado=="Pagado") else 'lightcoral' for aprobado in listaAprobados])


            ax.set_ylabel('\nPrecio en €\n', fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_title('Presupuestos de '+cliente+"\n", fontdict={'family': 'serif',
                        'color' : 'purple',
                        'weight': 'bold',
                        'size': 20})
            ax.set_xticks(x)
            ax.set_xticklabels(labels,fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 10})
            ax.legend()
            plt.xlabel("\nNº Factura\n",fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})

            ax.set_ylim([0, float(max(lineaProductoSubtotal))*1.2])


            def autolabel(rects):
                  contador =-1
                  """Attach a text label above each bar in *rects*, displaying its height."""
                  for rect in rects:
                         contador +=1
                         height = rect.get_height()

                         ax.annotate('{}'.format(str(lineaProductoSubtotal[contador])+ " €"),
                               xy=(rect.get_x() + rect.get_width() / 2, height),
                               xytext=(0, 3),  # 3 points vertical offset
                               textcoords="offset points",
                               ha='center', va='bottom')


            autolabel(rects1)


            # Estos datos es para poner una leyenda fija en el gráfico que solo informe del color que corresponde a cada subtotal de cada factura y el total de la factura:
            aprobado = mpatches.Patch(color='yellowgreen', label='PAGADO')
            noAprobado = mpatches.Patch(color='lightcoral', label='PENDIENTE')

            plt.legend(handles=[aprobado, noAprobado],loc="upper left",prop={'size':8},title="Leyenda:")

            fig.tight_layout()
            plt.show()

           # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,"Visualización del gráfico de barras de todas las facturas del cliente "+ cliente.upper())

        else:
            print(color.RED+"Error !! .. el cliente " +cliente.upper()+ " no tiene facturas !!"+color.END)

        elegirGraficoFacturas(usuario)








