
import csv
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import numpy as np
import os
import pymysql
import csv

from Color import *
from FechaRegistros import *
from FechaRegistros import registroBD
from Validaciones import *
from Compras import *
from colorama import init, Fore

def elegirGraficoPresupuestos(usuario):


    listaNivel = [1, 2, 3, 4]

    print(color.BLUE+"Elige un gráfico:\n\n"+color.END+

        "\tGráfico de barras: \n\n"+
          color.BLUE+"\t\t1"+color.END +" Selecciona Presupuesto\n"+
          color.BLUE+"\t\t2"+color.END +" Todos las presupuestos\n"+
          color.BLUE+"\t\t3"+color.END +" Presupuestos de un proveedor\n"+
          color.BLUE+"\t\t4"+color.END +" Volver\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        graficoBarrasSeleccionaPresupuesto(usuario)
    if opcion == 2:
        graficoBarrasTodasLosPresupuestos(usuario)
    if opcion == 3:
        graficoBarrasSeleccionaPresupuestosProveedor(usuario)


def mostrarProveedores():
    opciones =[]

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    #CONSULTA
    cursor = db.cursor()
    consulta = 'SELECT * FROM PROVEEDORES'
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    print()

    for registro in resultados:
        opciones.append(registro[0])
        print(color.BLUE+"\t"+str(registro[0])+color.END+" " +registro[1])

    print()

    db.close()

    return validarNumeroLista(opciones)



def seleccionarPresupuesto():

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    respuesta=""

    try:

        while respuesta !="n":
            opciones =[]

            # CONSULTA ELEGIR PRESUPUESTO (TABLA PEDIDOS_COMPRAS)
            cursor = db.cursor()
            consulta = 'SELECT A.ID_PEDIDO_COMPRA, B.NOMBRE, A.FECHA, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR'
            #print(consulta)
            cursor.execute(consulta)
            resultados = cursor.fetchall()

            print(color.BLUE+"Elige un presupuesto: \n")
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



def graficoBarrasSeleccionaPresupuesto(usuario):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        labels =[]
        lineaProductoSubtotal =[]
        listaAprobados=[]

        aprobado =""
        total =0
        proveedor =""
        presupuesto =0
        producto= ""
        descripcion= ""
        precio= 0.0
        unidades= 0

        opcion = seleccionarPresupuesto()



        #CONSULTA DE TODOS LOS PRESUPUESTOS
        cursor = db.cursor()
        consulta= 'SELECT A.ID_PEDIDO_COMPRA, B.NOMBRE, C.PRODUCTO, C.DESCRIPCION, C.PRECIO, C.UNIDADES, C.SUBTOTAL, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B, LISTA_COMPRAS C WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR AND C.ID_RELACION_PEDIDO_COMPRA = A.ID_PEDIDO_COMPRA AND C.ID_RELACION_PEDIDO_COMPRA= '+str(opcion)
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        if len(resultados) > 0:

            for registro in resultados:

                presupuesto = registro[0]
                proveedor = registro[1]+"\n"

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
            rects2 = ax.bar((0.65+width)*len(resultados),total, width,color=['yellowgreen' if (aprobado=="Aprobado") else 'lightcoral'])

            ax.set_ylabel('\nPrecio en €\n', fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_title('\nProveedor: '+proveedor+'Presupuesto: '+ str(presupuesto)+"\n", fontdict={'family': 'serif',
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
            lineaPedido = mpatches.Patch(color='grey', label='SUBTOTALES PRESUPUESTOS')
            pagado = mpatches.Patch(color='yellowgreen', label='APROBADO')
            pendiente = mpatches.Patch(color='lightcoral', label='NO APROBADO')


            plt.legend(handles=[lineaPedido,pagado, pendiente],loc="upper left",prop={'size':8},title="Leyenda: \n")

            fig.tight_layout()
            plt.show()

           # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,"Visualización del gráfico de barras del presupuesto: "+ str(presupuesto))


        else:
            print(color.RED+"Error !! ... no existen presupuestos !!"+color.END)

        elegirGraficoPresupuestos(usuario)


def graficoBarrasTodasLosPresupuestos(usuario):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        labels =[]
        lineaProductoSubtotal =[]
        listaAprobados=[]



        #CONSULTA DE TODOS LOS PRESUPUESTOS
        cursor = db.cursor()
        consulta= 'SELECT A.ID_PEDIDO_COMPRA, B.NOMBRE, B.DIRECCION, B.CIF, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR'
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



            rects1 = ax.bar(x, lineaProductoSubtotal,width,color=['yellowgreen' if (aprobado=="Aprobado") else 'lightcoral' for aprobado in listaAprobados])
            plt.hist(len(labels), rwidth=0.1)

            ax.set_ylabel('\nTOTAL presupuesto en €\n', fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_title("\nTodos los presupuestos\n", fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_xticks(x)
            ax.set_xticklabels(labels,fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 10})
            ax.legend()
            plt.xlabel("\nProveedor y Nº Presupuesto\n",fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})


            # Tamaño del eje y para que sea un poco más grande y se vean bien los datos
            #ax.set_ylim([0, float(max(lineaProductoSubtotal))* 1.2])


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
            aprobado = mpatches.Patch(color='yellowgreen', label='APROBADO')
            noAprobado = mpatches.Patch(color='lightcoral', label='NO APROBADO')


            plt.legend(handles=[aprobado, noAprobado],loc="upper left",prop={'size':8},title="Leyenda:")

            fig.tight_layout()

            plt.show()

           # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,"Visualización del gráfico de barras de todos los presupuestos")


        else:
            print(color.RED+"Error !! ... no existen presupuestos !!"+color.END)

        elegirGraficoPresupuestos(usuario)

def graficoBarrasSeleccionaPresupuestosProveedor(usuario):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")


        labels =[]
        lineaProductoSubtotal =[]
        listaAprobados=[]
        proveedor = ""

        opcion= mostrarProveedores()

        contador=0

        #CONSULTA DE TODOS LOS PRESUPUESTOS DEL PROVEEDOR ELEGIDO
        cursor = db.cursor()
        consulta= 'SELECT A.ID_PEDIDO_COMPRA, B.NOMBRE, B.DIRECCION, B.CIF, A.TOTAL_PEDIDOS_COMPRAS, A.APROBADO FROM PEDIDOS_COMPRAS A, PROVEEDORES B WHERE A.ID_RELACION_PROVEEDOR = B.ID_PROVEEDOR AND A.ID_RELACION_PROVEEDOR='+str(opcion)
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        if len(resultados) > 0:

            for registro in resultados:

                presupuesto = registro[0]
                proveedor = registro[1]
                labels.append("Presupuesto: "+str(presupuesto))
                total= registro[4]
                lineaProductoSubtotal.append(total)
                aprobado = registro[5]
                listaAprobados.append(aprobado)

            x = np.arange(len(labels))  # the label locations
            width = 0.35  # the width of the bars+


            fig, ax = plt.subplots()

            rects1 = ax.bar(x, lineaProductoSubtotal, width,color=['yellowgreen' if (aprobado=="Aprobado") else 'lightcoral' for aprobado in listaAprobados])


            ax.set_ylabel('\nPrecio en €\n', fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_title('Presupuestos de '+proveedor+"\n", fontdict={'family': 'serif',
                        'color' : 'purple',
                        'weight': 'bold',
                        'size': 20})
            ax.set_xticks(x)
            ax.set_xticklabels(labels,fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 10})
            ax.legend()
            plt.xlabel("\nNº Presupuesto\n",fontdict={'family': 'serif',
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
            aprobado = mpatches.Patch(color='yellowgreen', label='APROBADO')
            noAprobado = mpatches.Patch(color='lightcoral', label='NO APROBADO')

            plt.legend(handles=[aprobado, noAprobado],loc="upper left",prop={'size':8},title="Leyenda:")

            fig.tight_layout()
            plt.show()

           # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,"Visualización del gráfico de barras del proveedor "+ proveedor.upper())

        else:
            print(color.RED+"Error !! .. el proveedor " +proveedor.upper()+ " no tiene presupuestos !!"+color.END)

        elegirGraficoPresupuestos(usuario)








