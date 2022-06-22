import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import numpy as np


from colorama import init, Fore

init(autoreset=True)

from Cliente import *

'''
He planteado los gráficos,para que se vean las oportunidades, ya sean todas o por clientes
No he dado con el comando para que pueda aumentar el tamaño de la ventana, lo tienes que hacer a mano para que se vean bien todos los datos y no se monten. 
EL valor para 'cuantificar' en una gráfica es la 'fase' que va cambiando y a la que le asigno unos números para que se pueda representar en una gráfica.
He utilizado 2 gráficos que creo que son los que mejor se puede visualizar las oportunidades:
- Gráfico de doble barras, donde en ese grupo de 2 barras, asocio la fase de la oportunidad y sus actividades.
- Gráfico de quesitos, donde pongo el numero de oportunidades y la porción del 'quesito' es mayor o menor en función de la fase donde esté la oportunidad
'''


def elegirGrafico(listaClientes,listaOportunidades):
    if len(listaOportunidades)>0:

        listaNivel = [1, 2, 3,4,5]

        print(color.BLUE+"Elige un gráfico:\n\n"+color.END+

            "\tGráfico de barras: \n\n"+
              color.BLUE+"\t\t1"+color.END +" Oportunidades de un cliente\n"+
              color.BLUE+"\t\t2"+color.END +" Todas las oportunidades y sus actividades\n\n"+
            "\tGráfico de 'Quesitos': \n\n"+
              color.BLUE+"\t\t3"+color.END +" Oportunidades de un cliente\n"+
              color.BLUE+"\t\t4"+color.END +" Todas las oportunidades \n"+
              color.BLUE+"\t\t5"+color.END +" Salir\n")


        opcion = validarNumero(listaNivel)

        if opcion == 1:
            graficoBarrasOportunidadesCliente(listaClientes,listaOportunidades)
        if opcion == 2:
            graficoBarrasOportunidades(listaOportunidades)
        if opcion == 3:

            graficoQuesitosCliente(listaClientes,listaOportunidades)
        if opcion == 4:
            graficoQuesitos(listaOportunidades)


    else:
        print(color.RED+"No hay oportunidades !!"+color.END)


def graficoBarrasOportunidades(listaOportunidades):
        labels =[]
        actividades = []
        oportunidad =[]

        # Tengo que recorrer todas las oportunidades y por cada vuelta del objeto 'op' de tipo oportunidad añadir a las listas vacías anteriores:
        # - los valores de las fases --> oportunidad []
        # - La leyenda que aparecerá al lado de cada 'quesito' --> labels []
        # - Número de actividades --> oprtunidad []

        for op in listaOportunidades:
            cadena= op.cliente.nombre.upper()+"\n"+ op.descripcion+"\n" + op.comercial.title()+ "\n" +str(op.ingresoEstimado) + " €"
            labels.append(cadena.ljust(30," "))

            if op.fase == "Inicio":
                faseGrafico = 20
            elif op.fase == "En proceso":
                faseGrafico = 30
            elif op.fase == "Ganada":
                faseGrafico = 60
            else :
                faseGrafico = 10

            oportunidad.append(faseGrafico)
            actividades.append(len(op.listaActividadesCliente))


        x = np.arange(len(labels))  # the label locations
        width = 0.35  # the width of the bars+

        fig, ax = plt.subplots()
        rects1 = ax.bar(x - width/2, oportunidad, width, label="Oportunidad:\n -Ganada:60\n -En proceso:30\n -Inicio:20\n -Perdida:10")
        rects2 = ax.bar(x + width/2, actividades, width, label="Nº Actividades")

       # Add some text for labels, title and custom x-axis tick labels, etc.
        ax.set_ylabel('Fase')
        ax.set_title('Oportunidades')
        ax.set_xticks(x)
        ax.set_xticklabels(labels)
        ax.legend()


        def autolabel(rects):
              """Attach a text label above each bar in *rects*, displaying its height."""
              for rect in rects:
                     height = rect.get_height()
                     ax.annotate('{}'.format(height),
                           xy=(rect.get_x() + rect.get_width() / 2, height),
                           xytext=(0, 3),  # 3 points vertical offset
                           textcoords="offset points",
                           ha='center', va='bottom')


        autolabel(rects1)
        autolabel(rects2)


        fig.tight_layout()

        plt.show()

def graficoBarrasOportunidadesCliente(listaClientes,listaOportunidades):
        numero = 0
        print(color.BLUE+"Elige un cliente: \n"+color.END)

        for cliente in listaClientes:
            numero = numero + 1
            print(color.BLUE+str(numero)+color.END, end=" ")
            print(cliente.nombre)

        print()

        opcion = validarNumero(listaClientes)
        cliente = listaClientes[opcion - 1]




        labels =[]
        actividades = []
        oportunidad =[]


        for op in listaOportunidades:
            if op.cliente == cliente:
                cadena= op.cliente.nombre.upper()+"\n"+ op.descripcion+"\n" + op.comercial.title()+ "\n" +str(op.ingresoEstimado) + " €"
                labels.append(cadena.ljust(30," "))

                if op.fase == "Inicio":
                    faseGrafico = 20
                elif op.fase == "En proceso":
                    faseGrafico = 30
                elif op.fase == "Ganada":
                    faseGrafico = 60
                else :
                    faseGrafico = 10

                oportunidad.append(faseGrafico)
                actividades.append(len(op.listaActividadesCliente))

        x = np.arange(len(labels))  # the label locations
        width = 0.35  # the width of the bars+

        fig, ax = plt.subplots()
        rects1 = ax.bar(x - width/2, oportunidad, width, label="Oportunidad:\n-Perdida:10\n-Inicio:20\n-En proceso:30\n-Ganada:60\n-")
        rects2 = ax.bar(x + width/2, actividades, width, label="Nº Actividades")

       # Add some text for labels, title and custom x-axis tick labels, etc.
        ax.set_ylabel('Fase')
        ax.set_title('Oportunidades')
        ax.set_xticks(x)
        ax.set_xticklabels(labels)
        ax.legend()


        def autolabel(rects):
              """Attach a text label above each bar in *rects*, displaying its height."""
              for rect in rects:
                     height = rect.get_height()
                     ax.annotate('{}'.format(height),
                           xy=(rect.get_x() + rect.get_width() / 2, height),
                           xytext=(0, 3),  # 3 points vertical offset
                           textcoords="offset points",
                           ha='center', va='bottom')


        autolabel(rects1)
        autolabel(rects2)

        fig.tight_layout()

        plt.show()


def graficoQuesitos(listaOportunidades):

    labels =[]
    sizes = []
    faseGrafico = 0.0
    listaExplode = []
    colores =[]
    fase= []
    leyenda =[]

    colors = ['lightskyblue','gold', 'yellowgreen','lightcoral']
    leyenda =['INICIO','EN PROCESO', 'GANADA', 'PERDIDA']


    # Lo mismo que en el gráfico de barras, voy recorriendo el 'for' y por cada objeto oportunidad 'op' le voy añadiendo a las listas vacías del inicio
    # Al final voy a rellenar varias listas con el mismo número de elementos que son los necesarios para visualizar el gráfico, va cogiendo el primer elemento de cada lista , luego el segundo:

    # - El tamaño del 'quesito' en función de la fase --> sizes []
    # - Datos asociado a cada oportunidad --> labels []
    # - Lo que resalta el 'quesito' del conjunto --> listaExplode [], luego lo tengo que convertir en una tupla, porque el programa original me lo pide
    # - Asociar un color en función de la fase en que esté --> colores []
    # - Se lo paso a-->  plt.legend(handles=[inicio,enProceso, ganada,perdida],loc="upper right",prop={'size':12},title="Lista de oportunidades: ")


    for op in listaOportunidades:
        cadena= op.cliente.nombre.upper()+"\n"+ op.descripcion+"\n" + op.comercial.title()+ "\n" +str(op.ingresoEstimado) + " €"
        labels.append(cadena.ljust(30," "))


        if op.fase == "Inicio":
            faseGrafico = 15 * 100/len(listaOportunidades)
            listaExplode.append(0.02)
            colores.append('lightskyblue')
            #fase.append("INICIO")



        if op.fase == "En proceso":
            faseGrafico = 30 * 100/len(listaOportunidades)
            listaExplode.append(0.02)
            colores.append('gold')
            #fase.append("EN PROCESO")


        if op.fase == "Ganada":
            faseGrafico = 45 * 100/len(listaOportunidades)
            listaExplode.append(0.1)
            colores.append('yellowgreen')
            #fase.append("GANADA")



        if op.fase == "Perdida" :
            faseGrafico = 10 * 100/len(listaOportunidades)
            listaExplode.append(0.02)
            colores.append('lightcoral')
            #fase.append("PERDIDA")



        # Aquí la lista 'sizes' me va a definir el tamaño del 'quesito' en función de la fase en que esté, más grande o pequeña.
        sizes.append(faseGrafico)

    # Para convertir una lista en una tupla:
    explode = tuple(listaExplode)

    # print(labels)
    # print(listaExplode)
    # print(explode)
    # print(sizes)


    # Aquí le paso todos los valores de la lista para visualizar el gráfico.
    plt.pie(sizes,explode=explode,labels=labels,colors=colores,autopct='%1.1f%%',shadow=True, startangle=90)


    # Estos datos es para poner una leyenda fija en el gráfico que solo informe del color que corresponde a cada fase:
    inicio = mpatches.Patch(color='lightskyblue', label='INICIO')
    enProceso = mpatches.Patch(color='gold', label='EN PROCESO')
    ganada = mpatches.Patch(color='yellowgreen', label='GANADA')
    perdida = mpatches.Patch(color='lightcoral', label='PERDIDA')


    plt.legend(handles=[inicio,enProceso, ganada,perdida],loc="upper right",prop={'size':12},title="Lista de oportunidades: ")

    plt.axis('equal')
    plt.tight_layout()

    plt.show()


def graficoQuesitosCliente(listaClientes,listaOportunidades):

    numero = 0
    print(color.BLUE+"Elige un cliente: \n"+color.END)

    for cliente in listaClientes:
        numero = numero + 1
        print(color.BLUE+str(numero)+color.END, end=" ")
        print(cliente.nombre)

    print()

    opcion = validarNumero(listaClientes)
    cliente = listaClientes[opcion - 1]

    labels =[]
    sizes = []
    faseGrafico = 0.0
    listaExplode = []
    colores =[]
    fase= []
    leyenda =[]

    colors = ['lightskyblue','gold', 'yellowgreen','lightcoral']
    leyenda =['INICIO','EN PROCESO', 'GANADA', 'PERDIDA']



    for op in listaOportunidades:
        if op.cliente == cliente:
            cadena= op.cliente.nombre.upper()+"\n"+ op.descripcion+"\n" + op.comercial.title()+ "\n" +str(op.ingresoEstimado) + " €"
            labels.append(cadena.ljust(30," "))


            if op.fase == "Inicio":
                faseGrafico = 15 * 100/len(listaOportunidades)
                listaExplode.append(0.02)
                colores.append('lightskyblue')
                fase.append("INICIO")



            if op.fase == "En proceso":
                faseGrafico = 30 * 100/len(listaOportunidades)
                listaExplode.append(0.02)
                colores.append('gold')
                fase.append("EN PROCESO")


            if op.fase == "Ganada":
                faseGrafico = 45 * 100/len(listaOportunidades)
                listaExplode.append(0.1)
                colores.append('yellowgreen')
                fase.append("GANADA")



            if op.fase == "Perdida" :
                faseGrafico = 10 * 100/len(listaOportunidades)
                listaExplode.append(0.02)
                colores.append('lightcoral')
                fase.append("PERDIDA")



            sizes.append(faseGrafico)


    explode = tuple(listaExplode)


    # print(labels)
    # print(listaExplode)
    # print(explode)
    # print(sizes)


    # Aquí le paso todos los valores de la lista para visualizar el gráfico.
    plt.pie(sizes,explode=explode,labels=labels,colors=colores,autopct='%1.1f%%',shadow=True, startangle=90)


    # Estos datos es para poner una leyenda fija en el gráfico que solo informe del color que corresponde a cada fase:
    inicio = mpatches.Patch(color='lightskyblue', label='INICIO')
    enProceso = mpatches.Patch(color='gold', label='EN PROCESO')
    ganada = mpatches.Patch(color='yellowgreen', label='GANADA')
    perdida = mpatches.Patch(color='lightcoral', label='PERDIDA')

    # Aquí añado los valores anteriores al 'ptl.legend'
    plt.legend(handles=[inicio,enProceso, ganada,perdida],loc="upper right",prop={'size':12},title="Lista de oportunidades: ")

    plt.axis('equal')
    plt.tight_layout()


    plt.show()
