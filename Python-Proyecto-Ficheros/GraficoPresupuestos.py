
import csv

import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import numpy as np
import os

from Color import *
from Validaciones import *
from Compras import *
from colorama import init, Fore


def elegirGraficoPresupuestos():


    listaNivel = [1, 2, 3, 4]

    print(color.BLUE+"Elige un gráfico:\n\n"+color.END+

        "\tGráfico de barras: \n\n"+
          color.BLUE+"\t\t1"+color.END +" Selecciona Presupuesto\n"+
          color.BLUE+"\t\t2"+color.END +" Todos las presupuestos\n"+
          color.BLUE+"\t\t3"+color.END +" Presupuestos de un proveedor\n"+
          color.BLUE+"\t\t4"+color.END +" Volver\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        graficoBarrasSeleccionaPresupuesto()
    if opcion == 2:
        graficoBarrasTodasLosPresupuestos()
    if opcion == 3:
        graficoBarrasSeleccionaPresupuestosProveedor()


' FUNCIONES DE LAS FACTURAS DE LOS PROVEEDORES: '

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



def mostrarNombreProveedor():
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
                    return fila[0]



def csvreader_listarTodosPresupuestos():
    contador =0
    with open("Archivos/pedidoCompras.csv") as csvfichero:
    # Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',', quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)
        print()

        print(color.BLUE+"\t" +"Opcion".ljust(8," ")+ "Presupuesto".ljust(15," ")+"Nombre".ljust(20," ")+"Fecha".ljust(17," ")+"Relación de productos: producto,descripción,Precio/unidad, unidades, subtotal: ".ljust(100," ")+"Precio total".ljust(14," ")+"Estado del pago".ljust(16," ")+color.END)
        print()

        if header != None:
            for fila in reader:

                lista2= fila[9][2:-2].split('], [')
                contador += 1
                print(color.BLUE + "\t" + str(contador).ljust(8," ") + color.END + fila[0].ljust(15," ") + fila[1].ljust(20," ") + fila[8].ljust(17," "),end="")
                if len(lista2) == 1:
                    print(str(lista2[0]).ljust(100," "),end="")
                if len(lista2) == 2:
                    print(str(lista2[0]).ljust(100," "))
                    print("                                                                ",end="")
                    print(str(lista2[1]).ljust(100," "),end="")
                if len(lista2) == 3:
                    for i in range (0, len(lista2)):

                        if i ==0:
                            print(str(lista2[i]).ljust(100," "))
                        if i ==1:
                            print("                                                                ",end="")
                            print(str(lista2[i]).ljust(100," "))
                        if i== len(lista2)-1:
                            print("                                                                ",end="")
                            print(str(lista2[i]).ljust(100," "),end="")

                if len(lista2) > 3:
                    for i in range (0, len(lista2)):

                        if i ==0:
                            print(str(lista2[i]).ljust(100," "))
                        if i >0 and i <len(lista2)-1:
                            print("                                                                ",end="")
                            print(str(lista2[i]).ljust(100," "))

                        if i== len(lista2)-1:
                            print("                                                                ",end="")
                            print(str(lista2[i]).ljust(100," "),end="")

                if fila[11] == "Aprobado" :
                    print(fila[10].ljust(5," ")+" €".ljust(9," ") + Fore.GREEN+fila[11].ljust(13," ")+color.END)

                if fila[11] == "No aprobado" :
                    print(fila[10].ljust(5," ")+" €".ljust(9," ") +  color.RED+fila[11].ljust(13," ")+color.END)

        print()


        return validarOpcion(contador)




def graficoBarrasSeleccionaPresupuesto():

        if os.stat("Archivos/pedidoCompras.csv").st_size > 0:
            labels =[]
            lineaProductoSubtotal =[]

            opcionImprimir= csvreader_listarTodosPresupuestos() +1

            contador=0

            with open("Archivos/pedidoCompras.csv","r") as csvfichero:
                reader = list(csv.reader(csvfichero))

                for fila in reader:
                    contador+=1
                    if contador == opcionImprimir:

                        lista2= fila[9][2:-2].split('], [')

                        for i in range(0,len(lista2)):

                            lista3 = lista2[i].replace("'","").split(", ")

                            cadena= lista3[0]
                            labels.append(cadena)

                            lineaProductoSubtotal.append(int(lista3[4]))


                        presupuesto = fila[0]
                        proveedor = fila[1]+ " "+fila[2]
                        total= int(fila[10])
                        aprobado = fila[11]

                lineaProductoSubtotal.append(total)
                labels.append("TOTAL")


           # Add some text for labels, title and custom x-axis tick labels, etc.

            x = np.arange(len(labels))  # the label locations
            width = 0.35  # the width of the bars+


            fig, ax = plt.subplots()

            rects1 = ax.bar(x, lineaProductoSubtotal, width,color=['grey'])
            rects2 = ax.bar((0.65+width)*len(lista2),total, width,color=['yellowgreen' if (aprobado=="Aprobado") else 'lightcoral'])



            ax.set_ylabel('\nPrecio en €\n', fontdict={'family': 'serif',
                        'color' : 'darkblue',
                        'weight': 'bold',
                        'size': 16})
            ax.set_title('\nProveedor: '+proveedor+',  Presupuesto: '+ str(presupuesto)+"\n", fontdict={'family': 'serif',
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
            lineaPedido = mpatches.Patch(color='grey', label='SUBTOTALES PRESUPUESTOS')
            pagado = mpatches.Patch(color='yellowgreen', label='APROBADO')
            pendiente = mpatches.Patch(color='lightcoral', label='NO APROBADO')


            plt.legend(handles=[lineaPedido,pagado, pendiente],loc="upper left",prop={'size':8},title="Leyenda: \n")

            fig.tight_layout()
            plt.show()

        else:
            print(color.RED+"Error !! ... no existen presupuestos !!"+color.END)



def graficoBarrasTodasLosPresupuestos():

        labels =[]
        lineaProductoSubtotal =[]
        listaAprobados=[]

        if os.stat("Archivos/pedidoCompras.csv").st_size > 0:

            with open("Archivos/pedidoCompras.csv","r") as csvfichero:
                reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
                header =next(reader)

                if header != None:

                    for fila in reader:

                        presupuesto = fila[0]
                        proveedor = fila[1]+ " "+fila[2]+"\n"
                        labels.append(proveedor+presupuesto)
                        total= int(fila[10])
                        lineaProductoSubtotal.append(total)
                        aprobado = fila[11]
                        listaAprobados.append(aprobado)


           # Add some text for labels, title and custom x-axis tick labels, etc.

            x = np.arange(len(labels))  # the label locations
            width = 0.35  # the width of the bars+


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
            aprobado = mpatches.Patch(color='yellowgreen', label='APROBADO')
            noAprobado = mpatches.Patch(color='lightcoral', label='NO APROBADO')


            plt.legend(handles=[aprobado, noAprobado],loc="upper left",prop={'size':8},title="Leyenda:")

            fig.tight_layout()

            plt.show()


        else:
            print(color.RED+"Error !!... no existen presupuestos !!"+color.END)



def graficoBarrasSeleccionaPresupuestosProveedor():
        existeFactura=False

        labels =[]
        lineaProductoSubtotal =[]
        listaAprobados=[]

        opcionImprimir= mostrarNombreProveedor()
        print(opcionImprimir)

        contador=0

        with open("Archivos/pedidoCompras.csv","r") as csvfichero:
            reader = csv.reader(csvfichero, delimiter=',',quoting=csv.QUOTE_NONNUMERIC, strict=True)
            header =next(reader)

            if header!=None:

                for fila in reader:

                    if fila[1] == opcionImprimir:

                        existeFactura= True


       # Add some text for labels, title and custom x-axis tick labels, etc.

        if existeFactura:

            presupuesto = fila[0]
            labels.append(presupuesto)
            proveedor = fila[1]+ " "+fila[2]
            total= int(fila[10])
            lineaProductoSubtotal.append(total)
            aprobado = fila[11]
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
            aprobado = mpatches.Patch(color='yellowgreen', label='APROBADO')
            noAprobado = mpatches.Patch(color='lightcoral', label='NO APROBADO')


            plt.legend(handles=[aprobado, noAprobado],loc="upper left",prop={'size':8},title="Leyenda:")

            fig.tight_layout()
            plt.show()

        else:
            print(color.RED+"Error !! .. el proveedor  no tiene presupuestos !!"+color.END)
            elegirGraficoPresupuestos()
