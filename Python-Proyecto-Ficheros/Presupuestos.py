import csv
from Compras import *

from reportlab.pdfgen import canvas
from Color import *
from Validaciones import validarOpcion
from colorama import init, Fore
init(autoreset=True)


def csvreader_listarTodosPresupuestos():
    contador =0
    with open("Archivos/pedidoCompras.csv") as csvfichero:
    # Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',', quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)
        print()

        print(color.BLUE+"\t" +"Opcion".ljust(8," ")+ "Presupuesto".ljust(15," ")+"Nombre".ljust(25," ")+"Fecha".ljust(17," ")+"Relación de productos: producto,descripción,Precio/unidad, unidades, subtotal: ".ljust(100," ")+"Precio total".ljust(14," ")+"Estado del pago".ljust(16," ")+color.END)
        print()

        if header != None:
            for fila in reader:

                lista2= fila[9][2:-2].split('], [')
                contador += 1
                print(color.BLUE + "\t" + str(contador).ljust(8," ") + color.END + fila[0].ljust(15," ") + fila[1].ljust(25," ") + fila[8].ljust(17," "),end="")
                if len(lista2) == 1:
                    print(str(lista2[0]).ljust(100," "),end="")
                if len(lista2) == 2:
                    print(str(lista2[0]).ljust(100," "))
                    print("                                                                     ",end="")
                    print(str(lista2[1]).ljust(100," "),end="")
                if len(lista2) == 3:
                    for i in range (0, len(lista2)):
                        #lista3 = lista2[0].replace("'","").split(", ")

                        if i ==0:
                            print(str(lista2[i]).ljust(100," "))
                        if i ==1:
                            print("                                                                     ",end="")
                            print(str(lista2[i]).ljust(100," "))
                        if i== len(lista2)-1:
                            print("                                                                     ",end="")
                            print(str(lista2[i]).ljust(100," "),end="")

                if len(lista2) > 3:
                    for i in range (0, len(lista2)):

                        if i ==0:
                            print(str(lista2[i]).ljust(100," "))
                        if i >0 and i <len(lista2)-1:
                            print("                                                                     ",end="")
                            print(str(lista2[i]).ljust(100," "))

                        if i== len(lista2)-1:
                            print("                                                                     ",end="")
                            print(str(lista2[i]).ljust(100," "),end="")

                if fila[11] == "Aprobado" :
                    print(fila[10].ljust(5," ")+" €".ljust(9," ") + Fore.GREEN+fila[11].ljust(13," ")+color.END)

                if fila[11] == "No aprobado" :
                    print(fila[10].ljust(5," ")+" €".ljust(9," ") +  color.RED+fila[11].ljust(13," ")+color.END)

        print()


        return validarOpcion(contador)



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
    c.drawString(20,248,"Esto no es una presupuesto real")
    c.drawString(20,253," solo es una prueba.")
    #c.drawImage("Imagenes/Firma.jpg",110,222,width=60,height=35)
    c.drawRightString(175,253,"Firmado: "+proveedor.upper())
    # End the Page and Start with new



    c.showPage()
    # Saving the PDF
    c.save()


def imprimirPresupuestoSeleccionado():

    respuesta=""
    numeroFactura=""

    while respuesta != "n":

        print()

        print(color.BLUE+"Elige la factura que quieres imprimir: "+color.END)

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



