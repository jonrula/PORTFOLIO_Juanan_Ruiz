import csv

import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import numpy as np
import os

from Color import *
from Validaciones import *
from Ventas import *
from colorama import init, Fore
init(autoreset=True)


def elegirGraficoFacturas():


    listaNivel = [1, 2, 3, 4]

    print(color.BLUE+"Elige un gráfico:\n\n"+color.END+

        "\tGráfico de barras: \n\n"+
          color.BLUE+"\t\t1"+color.END +" Selecciona Factura\n"+
          color.BLUE+"\t\t2"+color.END +" Todas las facturas\n"+
          color.BLUE+"\t\t3"+color.END +" Facturas de un cliente\n"+
          color.BLUE+"\t\t4"+color.END +" Volver\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        graficoBarrasSeleccionaFactura()
    if opcion == 2:
        graficoBarrasTodasLasFacturas()
    if opcion == 3:
        graficoBarrasSeleccionaFacturasCliente()




' FUNCIONES DE LAS FACTURAS DE LOS CLIENTES: '

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



def mostrarNombre():
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
                    return fila[0]



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




def graficoBarrasSeleccionaFactura():

        if os.stat("Archivos/pedidoVentas.csv").st_size > 0:
            labels =[]
            lineaProductoSubtotal =[]

            opcionImprimir= csvreader_listarTodosPedidoVentas() +1

            contador=0

            with open("Archivos/pedidoVentas.csv","r") as csvfichero:
                reader = list(csv.reader(csvfichero))

                for fila in reader:
                    contador+=1
                    if contador == opcionImprimir:

                        lista2= fila[8][2:-2].split('], [')

                        for i in range(0,len(lista2)):

                            lista3 = lista2[i].replace("'","").split(", ")

                            cadena= lista3[0]
                            labels.append(cadena)

                            lineaProductoSubtotal.append(int(lista3[4]))


                        pedido = fila[0]
                        cliente = fila[1]+ " "+fila[2]
                        total= int(fila[9])
                        pago = fila[10]

                lineaProductoSubtotal.append(total)
                labels.append("TOTAL")


           # Add some text for labels, title and custom x-axis tick labels, etc.

            x = np.arange(len(labels))  # the label locations
            width = 0.35  # the width of the bars+


            fig, ax = plt.subplots()

            rects1 = ax.bar(x, lineaProductoSubtotal, width,color=['grey'])
            rects2 = ax.bar((0.65+width)*len(lista2),total, width,color=['yellowgreen' if (pago=="Pagado") else 'lightcoral'])



            ax.set_ylabel('\nPrecio en €\n', fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_title('\nCliente: '+cliente+',  Factura: '+ str(pedido)+"\n", fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_xticks(x)
            ax.set_xticklabels(labels,rotation=90,fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 10})

            ax.legend()
            plt.xlabel("\nProductos\n",fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})

            ax.set_ylim([0, max(lineaProductoSubtotal)*1.3])


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
            lineaPedido = mpatches.Patch(color='grey', label='SUBTOTALES PEDIDO')
            pagado = mpatches.Patch(color='yellowgreen', label='PAGADO')
            pendiente = mpatches.Patch(color='lightcoral', label='PENDIENTE')


            plt.legend(handles=[lineaPedido,pagado, pendiente],loc="upper left",prop={'size':8},title="Leyenda: \n")

            fig.tight_layout()
            plt.show()

        else:
            print(color.RED+"Error !! ... no existen facturas !!"+color.END)



def graficoBarrasTodasLasFacturas():

        labels =[]
        lineaProductoSubtotal =[]
        pagos=[]

        if os.stat("Archivos/pedidoVentas.csv").st_size > 0:

            with open("Archivos/pedidoVentas.csv","r") as csvfichero:
                reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
                header =next(reader)

                if header != None:

                    for fila in reader:

                        pedido = fila[0]
                        cliente = fila[1]+ " "+fila[2]+"\n"
                        labels.append(cliente+pedido)
                        total= int(fila[9])
                        lineaProductoSubtotal.append(total)
                        pago = fila[10]
                        pagos.append(pago)


           # Add some text for labels, title and custom x-axis tick labels, etc.

            x = np.arange(len(labels))  # the label locations
            width = 0.35  # the width of the bars+


            fig, ax = plt.subplots(figsize=(len(labels)*1.2, len(labels)/2))



            rects1 = ax.bar(x, lineaProductoSubtotal,width,color=['yellowgreen' if (pago=="Pagado") else 'lightcoral' for pago in pagos])
            plt.hist(len(labels), rwidth=0.1)

            ax.set_ylabel('\nTOTAL de cada factura en €\n', fontdict={'family': 'serif',
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

            ax.set_ylim([0, max(lineaProductoSubtotal)*1.2])





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
            pagado = mpatches.Patch(color='yellowgreen', label='PAGADO')
            pendiente = mpatches.Patch(color='lightcoral', label='PENDIENTE')


            plt.legend(handles=[pagado, pendiente],loc="upper left",prop={'size':8},title="Leyenda:")

            fig.tight_layout()

            plt.show()


        else:
            print(color.RED+"Error !!... no existen facturas !!"+color.END)





def graficoBarrasSeleccionaFacturasCliente():
        existeFactura=False

        labels =[]
        lineaProductoSubtotal =[]
        pagos=[]

        opcionImprimir= mostrarNombre()
        print(opcionImprimir)

        contador=0

        with open("Archivos/pedidoVentas.csv","r") as csvfichero:
            reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
            header =next(reader)

            if header!=None:

                for fila in reader:

                    if fila[1] == opcionImprimir:

                        existeFactura= True



       # Add some text for labels, title and custom x-axis tick labels, etc.

        if existeFactura:

            pedido = fila[0]
            labels.append(pedido)
            cliente = fila[1]+ " "+fila[2]
            total= int(fila[9])
            lineaProductoSubtotal.append(total)
            pago = fila[10]
            pagos.append(pago)

            x = np.arange(len(labels))  # the label locations
            width = 0.35  # the width of the bars+


            fig, ax = plt.subplots()

            rects1 = ax.bar(x, lineaProductoSubtotal, width,color=['yellowgreen' if (pago=="Pagado") else 'lightcoral' for pago in pagos])


            ax.set_ylabel('\nPrecio en €\n', fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_title('Facturas de '+cliente+"\n", fontdict={'family': 'serif',
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

            ax.set_ylim([0, max(lineaProductoSubtotal)*1.2])


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
            pagado = mpatches.Patch(color='yellowgreen', label='PAGADO')
            pendiente = mpatches.Patch(color='lightcoral', label='PENDIENTE')


            plt.legend(handles=[pagado, pendiente],loc="upper left",prop={'size':8},title="Leyenda:")

            fig.tight_layout()
            plt.show()

        else:
            print(color.RED+"Error !! ... el cliente  no tiene facturas !!"+color.END)









