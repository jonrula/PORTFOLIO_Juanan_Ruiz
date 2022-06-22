import csv
import os
from Gastos import *
from Color import *
from GraficoPresupuestos import *
from Presupuestos import *
from Validaciones import *
from ficheros import *
from Facturas import *
from colorama import init, Fore

init(autoreset=True)


def MenuRRHH():
    listaNivel = [1, 2, 3, 4, 5, 6, 7]

    print(color.BLUE + "Elige una opción:\n\n" + color.END +

          color.BLUE + "\t1" + color.END + " Nuevo trabajador/a\n" +
          color.BLUE + "\t2" + color.END + " Baja laboral\n" +
          color.BLUE + "\t3" + color.END + " Borrar trabajador/a\n" +
          color.BLUE + "\t4" + color.END + " Cursillos formación\n" +
          color.BLUE + "\t5" + color.END + " Imprimir nómina\n" +
          color.BLUE + "\t6" + color.END + " Iniciar nóminas nuevo mes\n" +
          color.BLUE + "\t7" + color.END + " Salir\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        csvwriter_crearTrabajador()
    if opcion == 2:
        bajaLaboral()
    if opcion == 3:
        borrarTrabajador("Archivos/trabajadores.csv")
    if opcion == 4:
        cursosFormacion()
    if opcion == 5:
        imprimirNominaSeleccionada()
    if opcion == 6:
        inicarNominasNuevoMes()



def listarCursosTrabajador(trabajador):
        with open("Archivos/trabajadores.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

            for fila in reader:
                if trabajador.lower() == fila[0].lower():

                    print()
                    print(color.BLUE+"Estos son los cursos del trabajador "+trabajador.upper()+" "+fila[1].upper()+" en el mes activo: \n"+color.END)
                    print(color.BLUE+"\t"+"Descripción".ljust(24," ")+"Duración del curso".ljust(24," ")+"Lugar".ljust(24," ")+"Fecha inicio".ljust(24," ")+"Fecha fin".ljust(24," ")+color.END)

                    lista2= fila[11][2:-2].replace("'","").split('], [')
                    for i in range(0,len(lista2)):
                        lista3=lista2[i].replace("'","").split(", ")
                        for j in range(0,len(lista3)):
                            if j == len(lista3)-1:
                                print("\t"+lista3[j]+" €".ljust(20," "),end="")

                            else:
                                print("\t"+lista3[j].ljust(20," "),end="")

                        print()

                    print()



def borrarTrabajador(archivo):

    respuesta =""

    while os.path.isfile(archivo) and respuesta !="n" and os.stat(archivo).st_size > 0:

        opcion= csvreader_listarTrabajadores() +1
        contador = 0

        with open(archivo,"r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open(archivo,"w") as csvfichero:
            writer = csv.writer(csvfichero,quoting=csv.QUOTE_NONNUMERIC)
            for fila in reader:
                contador +=1
                if contador != opcion:
                    writer.writerow(fila)

            print(Fore.GREEN+"La fila "+str(opcion)+" ha sido borrada !!")

        respuesta = input(color.BLUE+"Quieres borrar más registros: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres borrar más registros ? (s/n) "+color.END)


    if os.stat(archivo).st_size == 0:
        print(color.RED+"Error !! ... no hay más registros para borrar"+color.END)
    if not os.path.isfile(archivo):
         print(color.RED+"Error !!... no existe el archivo "+color.END)

    #Me voy al menú anterior:
    MenuRRHH()


def bajaLaboral():
    if os.path.isfile("Archivos/trabajadores.csv") and os.stat("Archivos/trabajadores.csv").st_size > 0:

        opcionClienteSeleccionado = csvreader_listarTrabajadores() + 1
        trabajador= nombreTrabajador(opcionClienteSeleccionado)
        contador = 0

        with open("Archivos/trabajadores.csv", "r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/trabajadores.csv", "w") as csvfichero:
            writer = csv.writer(csvfichero, quoting=csv.QUOTE_NONNUMERIC)

            for fila in reader:
                contador += 1
                if contador == opcionClienteSeleccionado:

                    print(Fore.GREEN + "El trabajador " + trabajador + " tiene actualmente la siguiente situación laboral:" + fila[10])
                    pregunta = input(color.BLUE + "Quieres cambiar la situación laboral de "+trabajador+" ? (S/N): " + color.END)
                    while (pregunta.lower() != "s" and pregunta.lower() != "n"):
                        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                        pregunta = input(color.BLUE + "Quieres cambiar la situación laboral de "+trabajador+" ? (S/N) ? : " + color.END)

                    if pregunta.lower() == "s":
                        situacionLaboral= eligeSituacionLaboral()
                        fila[10]=situacionLaboral

                    writer.writerow(fila)
                else:
                    writer.writerow(fila)

    if os.stat("Archivos/trabajadores.csv").st_size == 0:
        print(color.RED + "Error !! ... no hay más registros para borrar" + color.END)
    if not os.path.isfile("Archivos/trabajadores.csv"):
        print(color.RED + "Error !!... no existe el archivo " + color.END)

    # Me voy al menú anterior:
    MenuRRHH()


def inicarNominasNuevoMes():
    if os.path.isfile("Archivos/nominasTrabajadores.csv") and os.stat("Archivos/nominasTrabajadores.csv").st_size > 0:

        # Elijo el nuevo mes para las nóminas:
        nuevoPeriodoNominas= eligePeriodoNomina()

        with open("Archivos/nominasTrabajadores.csv", "r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/nominasTrabajadores.csv", "w") as csvfichero:
            writer = csv.writer(csvfichero, quoting=csv.QUOTE_NONNUMERIC)

            # Inicializo a todos los trabajadores/as, osea todas las filas, a 'cero' las posiciones que me interesan para hacer las nuevas nóminas:
            # kilometraje --> fila[4]= "0"
            # listaGastos -->  fila[5] = "[[Ninguno]]"
            # periodoNominas  --> fila[10]=nuevoPeriodoNominas  (me devuelve el mes seleccionado)

            for fila in reader:
                fila[4]= "0"
                fila[5] = "[[Ninguno]]"
                fila[10]=nuevoPeriodoNominas
                writer.writerow(fila)

    if os.stat("Archivos/nominasTrabajadores.csv").st_size == 0:
        print(color.RED + "Error !! ... no hay más registros para borrar" + color.END)
    if not os.path.isfile("Archivos/nominasTrabajadores.csv"):
        print(color.RED + "Error !!... no existe el archivo " + color.END)

    # Me voy al menú anterior:
    MenuRRHH()



def cursosFormacion():
    if os.path.isfile("Archivos/trabajadores.csv") and os.stat("Archivos/trabajadores.csv").st_size > 0:

        opcionClienteSeleccionado = csvreader_listarTrabajadores() + 1
        contador = 0

        # Aquí listo los cursos pero pasandole el trabajador por parámetro de entrada, que a suvez le tenga que pasar la oción del trabajador elegido ('opcionClienteSeleccionado')
        # y a su vez se lo paso a la función 'nombreTrabajador(oción)' para que me devuelva el nombre del trabajador/a:

        listarCursosTrabajador(nombreTrabajador(opcionClienteSeleccionado))

        with open("Archivos/trabajadores.csv", "r") as csvfichero:
            reader = list(csv.reader(csvfichero))

        with open("Archivos/trabajadores.csv", "w") as csvfichero:
            writer = csv.writer(csvfichero, quoting=csv.QUOTE_NONNUMERIC)

            for fila in reader:
                contador += 1
                if contador == opcionClienteSeleccionado:

                    print(Fore.GREEN + "El trabajador " + fila[0].upper() + " tiene actualmente la siguiente lista de cursos:\n " + fila[11])
                    pregunta = input(color.BLUE + "Quieres añadir un curso de formación ? (S/N): " + color.END)
                    while (pregunta.lower() != "s" and pregunta.lower() != "n"):
                        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
                        pregunta = input(color.BLUE + "Quieres añadir un curso de formación ? (S/N) ? : " + color.END)

                    if pregunta.lower() == "s":
                        formacion = validarString("Introduce nombre del curso: ", 20)
                        horas = validarIntroducirNumero("Horas del curso: ")
                        sitio = validarString("Lugar donde se imparte el cursillo: ", 20)
                        fechaInicio = validarFecha("Fecha inicio: ")
                        fechaFin = validarFecha("Fecha fin: ")

                        lista3 = fila[11][1:-1].replace('"', '')

                        fila[11] = "[" + lista3 + ", " + str([formacion.capitalize(), horas, sitio.title(), fechaInicio, fechaFin]) + "]"
                        fila[11] = fila[11].replace('[Ninguno], ', '')


                    writer.writerow(fila)
                else:
                    writer.writerow(fila)

    if os.stat("Archivos/trabajadores.csv").st_size == 0:
        print(color.RED + "Error !! ... no hay más registros para borrar" + color.END)
    if not os.path.isfile("Archivos/trabajadores.csv"):
        print(color.RED + "Error !!... no existe el archivo " + color.END)

    # Me voy al menú anterior:
    MenuRRHH()


def eligeSituacionLaboral():
    listaNivel = [1, 2]

    print(color.BLUE + "Elige situacion laboral:\n\n" + color.END +

          color.BLUE + "\t1" + color.END + Fore.GREEN + " Trabajando\n" +
          color.BLUE + "\t2" + color.END + color.RED + " Baja laboral\n" + color.END)

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        return "Alta"
    if opcion == 2:
        return "Baja laboral"


def eligeCategorial():
    listaNivel = [1, 2, 3, 4]

    print(color.BLUE + "Elige Departamento:\n\n" + color.END +

          color.BLUE + "\t1" + color.END +" Fotografia\n" +
          color.BLUE + "\t2" + color.END +" Compras\n" +
          color.BLUE + "\t3" + color.END +" Ventas\n" +
          color.BLUE + "\t4" + color.END +" RRHH\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        return "Fotografía"
    if opcion == 2:
        return "Compras"
    if opcion == 3:
        return "Ventas"
    if opcion == 4:
        return "RRHH"



def eligirDepartamento(trabajador,apellidos):
    print()
    lista=[]
    resultado=""

    print(color.BLUE+"Estos son los departamentos que existen:\n\n"+color.END+
          color.BLUE + "\t1" + color.END +" Gastos\n" +
          color.BLUE + "\t2" + color.END +" Compras\n" +
          color.BLUE + "\t3" + color.END +" Ventas\n" +
          color.BLUE + "\t4" + color.END +" RRHH\n")


    compras = input(color.BLUE + "Quieres añadir al departamento COMPRAS ? (S/N): " + color.END)
    while (compras.lower()!= "s" and compras.lower() != "n"):
        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
        compras = input(color.BLUE + "Quieres añadir al departamento COMPRAS ? (S/N): " + color.END)

    if (compras.lower() =="s"):
        resultado = " , Compras"
        print(Fore.GREEN+"Se ha creado correctamente el comercial: "+trabajador.upper()+ " "+apellidos.upper()+ " con acceso a los departamentos: COMPRAS ")

    ventas = input(color.BLUE + "Quieres añadir al departamento VENTAS ? (S/N): " + color.END)
    while (ventas.lower()!= "s" and ventas.lower() != "n"):
        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
        ventas = input(color.BLUE + "Quieres añadir al departamento VENTAS ? (S/N): " + color.END)

    if (ventas.lower() == "s"):
        resultado += " , Ventas"
        print(Fore.GREEN+"Se ha creado correctamente el comercial: "+trabajador.upper()+ " "+apellidos.upper()+ " con acceso al departamento: VENTAS")

    rrhh = input(color.BLUE + "Quieres añadir al departamento RRHH ? (S/N): " + color.END)
    while (rrhh.lower()!= "s" and rrhh.lower() != "n"):
        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
        rrhh = input(color.BLUE + "Quieres añadir al departamento RRHH ? (S/N): " + color.END)

    if (rrhh.lower() == "s"):
        resultado += " , RRHH"
        print(Fore.GREEN+"Se ha creado correctamente el comercial: "+trabajador.upper()+ " "+apellidos.upper()+ " con acceso al departamento: RRHH")


    print(Fore.GREEN+"Se le incluye automáticamente al trabajador "+trabajador.upper()+ " "+apellidos.upper()+ " al departamento: GASTOS")
    resultado +=" , Gastos"

    return resultado



def eligeCoche():
    listaNivel = [1, 2, 3, 4, 5, 6]

    print(color.BLUE + "Elige el coche que quieres:\n\n" + color.END +

          color.BLUE + "\t1" + color.END + " Tesla Model S\n" +
          color.BLUE + "\t2" + color.END + " Tesla Model 3\n" +
          color.BLUE + "\t3" + color.END + " Tesla Model X\n" +
          color.BLUE + "\t4" + color.END + " Tesla Model Y\n" +
          color.BLUE + "\t5" + color.END + " Otro coche\n" +
          color.BLUE + "\t6" + color.END + " Salir\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        return "Tesla Model S"
    if opcion == 2:
        return "Tesla Model 3"
    if opcion == 3:
        return "Tesla Model X"
    if opcion == 4:
        return "Tesla Model Y"
    if opcion == 5:
        return validarString("Introduce marca y modelo de coche: ", 30)

def eligePeriodoNomina():
    listaNivel = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]

    print(color.BLUE + "Elige el coche que quieres:\n\n" + color.END +

          color.BLUE + "\t1" + color.END + " Enero\n" +
          color.BLUE + "\t2" + color.END + " Febrero\n" +
          color.BLUE + "\t3" + color.END + " Marzo\n" +
          color.BLUE + "\t4" + color.END + " Abril\n" +
          color.BLUE + "\t5" + color.END + " Mayo\n" +
          color.BLUE + "\t6" + color.END + " Junio\n" +
          color.BLUE + "\t7" + color.END + " Julio\n" +
          color.BLUE + "\t8" + color.END + " Agosto\n" +
          color.BLUE + "\t9" + color.END + " Septiembre\n" +
          color.BLUE + "\t10" + color.END + " Octubre\n" +
          color.BLUE + "\t11" + color.END + " Noviembre\n" +
          color.BLUE + "\t12" + color.END + " Diciembre\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        return "Del 1 al 31 de Enero"
    if opcion == 2:
        return "Del 1 al 28 de Febrero"
    if opcion == 3:
        return "Del 1 al 31 de Marzo"
    if opcion == 4:
        return "Del 1 al 30 de Abril"
    if opcion == 5:
        return "Del 1 al 31 de Mayo"
    if opcion == 6:
        return "Del 1 al 30 de Junio"
    if opcion == 7:
        return "Del 1 al 31 de Julio"
    if opcion == 8:
        return "Del 1 al 31 de Agosto"
    if opcion == 9:
        return "Del 1 al 30 de Septiembre"
    if opcion == 10:
        return "Del 1 al 31 de Octubre"
    if opcion == 11:
        return "Del 1 al 30 de Noviembre"
    if opcion == 12:
        return "Del 1 al 31 de Diciembre"



def csvreader_ComprobarExisteTrabajador(dni):
    with open("Archivos/trabajadores.csv") as csvfichero:
        # Devuelve una lista de listas, incluida la cabecera
        reader = csv.reader(csvfichero, delimiter=',',
                            quoting=csv.QUOTE_NONNUMERIC, strict=True)

        for fila in reader:
            if fila[2] == dni:
                return True


def csvwriter_crearTrabajador():
    print(color.BLUE + "Introduce los datos del nuevo trabajador/a: " + color.END)
    respuesta = ""

    while respuesta != "n":

        dni = validarDNI()

        while not DNIesCorrecto(dni):
            print(color.RED + "DNI incorrecto" + color.END)
            dni = validarDNI()

        while csvreader_ComprobarExisteTrabajador(dni):
            print(color.RED + "Error !!... ya existe un trabajador con el DNI: " + dni + color.END)
            dni = validarDNI()

        nombre = validarString("Nombre: ", 20)
        apellidos = validarString("Apellidos: ", 20)
        direccion = validarString("Dirección: ", 20)
        telefono = validarTelefono()
        email = validarEmail()
        coche = eligeCoche().title()
        kilometraje = str(validarIntroducirNumero("Kilómetros actuales del coche elegido: "))

        listaGastos = "[[Ninguno]]"

        fechaAltacontrato = validarFecha("Fecha alta contrato: ")
        trabajando = eligeSituacionLaboral()
        cursos = "[[Ninguno]]"
        categoria = eligirDepartamento(nombre,apellidos)
        copiaCategoria = categoria.replace(', ','')
        copiaCategoria = copiaCategoria.replace(' Gastos','Fotografía')
        antiguedad = "0"
        periodoNomina = eligePeriodoNomina()


        # Ahora voy a grabar los datos del nuevo trabajador/a en tres ficheros:
        #     1 --> En el fichero de trabajadores, no se pueden repetir, son únicos
        #     2 --> En el fichero de nominas de los trabajadores, solo hay una nomina por cada trabajador, ya lo filtro anteriormente para que nos se repitan nominas de un trabajador
        #           Lo he planteado, que el archivo de nóminas es valido por cada mes, luego hay que hacer otro fichero de nómina por cada trabajador
        # Inicializo una variable de tipo String y le concateno todas las variables, a las que a cada una les quitos los posibles intros y saltos de línea --> 'rstrip("\r\n")' y quitar la separación den carácteres por coma --> 'split(',')'


        #"Nombre","Apellidos","DNI","Dirección","Teléfono","email","Coche","FechaAlta","Categoria","Antiguedad","Trabajando"
        nuevoTrabajador = nombre.title().rstrip("\r\n").split(',') + apellidos.title().rstrip("\r\n").split(
            ',') + dni.rstrip("\r\n").split(',') + direccion.capitalize().rstrip("\r\n").split(',') + telefono.rstrip(
            "\r\n").split(',') + email.rstrip("\r\n").split(',') + coche.rstrip("\r\n").split(',')+ fechaAltacontrato.rstrip(
            "\r\n").split(',') + copiaCategoria.rstrip("\r\n").split(',') + antiguedad.rstrip("\r\n").split(',') + trabajando.rstrip("\r\n").split(',')+ [cursos]


        #"Nombre","Apellidos","DNI","Dirección","KilometrajeMesActivo","ListaGastos","FechaAlta","Categoria","Antiguedad","Trabajando","Cursos","periodoNomina"
        nominaTrabajador = nombre.title().rstrip("\r\n").split(',') + apellidos.title().rstrip("\r\n").split(
        ',') + dni.rstrip("\r\n").split(',') + direccion.capitalize().rstrip("\r\n").split(',')  + kilometraje.rstrip(
        "\r\n").split(',') + [listaGastos] + fechaAltacontrato.rstrip("\r\n").split(',') + copiaCategoria.rstrip(
        "\r\n").split(',') + antiguedad.rstrip("\r\n").split(',') + trabajando.rstrip("\r\n").split(',')+ periodoNomina.rstrip("\r\n").split(',')

        #"Dueño","Coche","KilometrosTotales","Estado"
        cocheTrabajador= nombre.title().rstrip("\r\n").split(',')+ coche.rstrip("\r\n").split(',')+ kilometraje.rstrip(
        "\r\n").split(',')+"Bueno".rstrip("\r\n").split(',')


        mi_fichero = open("Archivos/trabajadores.csv", 'a', newline='')
        with mi_fichero:
            writer = csv.writer(mi_fichero, quoting=csv.QUOTE_NONNUMERIC)
            writer.writerow(nuevoTrabajador)  # IMPORTANTE --> hay que poner solo 'writerow' para que escriba UNA SOLA FILA

        mi_fichero = open("Archivos/nominasTrabajadores.csv", 'a', newline='')
        with mi_fichero:
            writer = csv.writer(mi_fichero, quoting=csv.QUOTE_NONNUMERIC)
            writer.writerow(nominaTrabajador)  # IMPORTANTE --> hay que poner solo 'writerow' para que escriba UNA SOLA FILA

        mi_fichero = open("Archivos/Coches.csv", 'a', newline='')
        with mi_fichero:
            writer = csv.writer(mi_fichero, quoting=csv.QUOTE_NONNUMERIC)
            writer.writerow(cocheTrabajador)  # IMPORTANTE --> hay que poner solo 'writerow' para que escriba UNA SOLA FILA

        print()
        # Ahora lo meto dentro de 'usuarios.txt' para que tenfga acceso  a los diferentes departamentos
        print(color.BLUE+"Ahora generamos ua contraseña para acceso a la aplicación: \n"+color.END)
        password = GenerarContrasenya()
        archivo = open("Archivos/usuarios.txt", "a+")

        lista=[nombre.title(), " , ", password, ]
        lista.append(categoria)
        archivo.writelines(lista)

        #No activo 'enviaremail' porque tarda mucho y se bloquean los siguientes pasos, además de que no he desactivado el contrl de seguridad de app de terceros en gmail
        #enviarEmail(nombre,apellidos,password,email)

        print(Fore.GREEN + "Se ha añadido correctamente al trabajador/a " + nombre.upper() + " " + apellidos.upper() + " al departamento de Gastos")
        print(Fore.GREEN + "Se ha añadido correctamente al trabajador/a " + nombre.upper() + " " + apellidos.upper() + " a la base de datos de trabajadores de PIXELS")

        respuesta = input(color.BLUE + "Quieres añadir más trabajadores/as: ? (s/n) " + color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE + "Quieres añadir más trabajadores/as: ? (s/n) " + color.END)

    # Me voy al menú anterior:
    MenuRRHH()


def csvreader_listarTodasLasNominas():
    contador =0
    with open("Archivos/nominasTrabajadores.csv") as csvfichero:
    # Devuelve una lista de listas, sin la cabecera
        reader = csv.reader(csvfichero, delimiter=',', quoting=csv.QUOTE_NONNUMERIC, strict=True)
        header =next(reader)
        print()

    #"Nombre","Apellidos","DNI","Dirección","KilometrajeMesActivo","ListaGastos","FechaAlta","Categoria","Antiguedad","Trabajando"

        print(color.BLUE+"\t" +"Opcion".ljust(8," ")+ "Nombre".ljust(20," ")+"Apellidos".ljust(20," ")+"DNI".ljust(17," ")+"Dirección".ljust(30," ")+"Kilometraje mes actual".ljust(25," ")+"Lista de gastos".ljust(40," ")+"Fecha de alta".ljust(16," ")+"Categoría".ljust(30," ")+"Antiguedad".ljust(15," ")+"Situación laboral".ljust(20," ")+color.END)
        print()

        if header != None:
            for fila in reader:

                lista2= fila[5][2:-2].replace("'","").split('], [')

                contador += 1
                print(color.BLUE + "\t" + str(contador).ljust(8," ") + color.END + fila[0].ljust(20," ") + fila[1].ljust(20," ") + fila[2].ljust(17," ")+ fila[3].ljust(30," ")+ fila[4].ljust(25," "),end="")
                if len(lista2) == 1:
                    lista3=lista2[0].split(", ")
                    print(str(lista2[0]).ljust(40," "),end="")
                if len(lista2) == 2:
                    print(str(lista2[0]).ljust(40," "))
                    print("                                                                                                                            ",end="")
                    print(str(lista2[1]).ljust(40," "),end="")
                if len(lista2) == 3:
                    for i in range (0, len(lista2)):

                        if i ==0:
                            print(str(lista2[i]).ljust(40," "))
                        if i ==1:
                            print("                                                                                                                            ",end="")
                            print(str(lista2[i]).ljust(40," "))
                        if i== len(lista2)-1:
                            print("                                                                                                                            ",end="")
                            print(str(lista2[i]).ljust(40," "),end="")

                if len(lista2) > 3:
                    for i in range (0, len(lista2)):

                        if i ==0:
                            print(str(lista2[i]).ljust(40," "))
                        if i >0 and i <len(lista2)-1:
                            print("                                                                                                                            ",end="")
                            print(str(lista2[i]).ljust(40," "))

                        if i== len(lista2)-1:
                            print("                                                                                                                            ",end="")
                            print(str(lista2[i]).ljust(40," "),end="")



                if fila[9] == "Alta" :
                    print(fila[6].ljust(16," ") + fila[7].ljust(30," ") + fila[8].ljust(15," ")+Fore.GREEN+fila[9].ljust(20," "))

                if fila[9] == "Baja laboral" :
                    print(fila[6].ljust(16," ") + fila[7].ljust(30," ") + fila[8].ljust(15," ")+color.RED+fila[9].ljust(20," ")+color.END)

        print()


        return validarOpcion(contador)



def verFactura(nombre,apellidos,dni,direccion,kilometros,lista,fechaAlta,categoria,antiguedad,estado):


    c = canvas.Canvas("Nominas/Factura"+nombre.upper()+".pdf",pagesize=(200,280),bottomup=0)
    # Logo Section
    # Setting th origin to (10,40)
    c.translate(10,40)
    # Inverting the scale for getting mirror Image of logo
    c.scale(1,-1)
    # Inserting Logo into the Canvas at required position
    c.drawImage("Imagenes/logoFoto.png",0,0,width=50,height=30)
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
    c.drawCentredString(100,55,"Nómina")
    # This Block Consist of Costumer Details
    c.roundRect(15,63,170,42,10,stroke=1,fill=0)

    c.setFont("Times-Bold",4)
    c.drawRightString(45,70,"Nombre: ")
    c.drawRightString(45,80,"Apellidos: ")
    c.drawRightString(45,90,"Fecha : ")
    c.drawString(92,90,"DNI : ")
    c.drawString(92,80,"Categoría: ")
    c.drawRightString(45,100,"Dirección : ")


    c.setFont("Courier",4)
    c.drawString(45,70,nombre)
    c.drawString(45,80,apellidos)
    c.drawString(45,90,fechaAlta)
    c.drawString(103,90,dni)
    c.drawString(112,80,categoria)
    c.drawString(45,100,direccion)


    # This Block Consist of Item Description
    c.roundRect(15,110,170,150,10,stroke=1,fill=0)
    c.line(15,120,185,120)

    c.setFont("Times-Bold",5)

    c.drawCentredString(30,118,"Gastos")
    c.drawCentredString(75,118,"Fecha")
    c.drawCentredString(105,118,"Precio")
    c.drawCentredString(115,217,"Líquido a percibir:")
    c.drawCentredString(30,173,"Kilómetros: ")
    c.drawCentredString(30,185,"Antiguedad: ")
    c.drawCentredString(31,198,"Deducciones: ")
    c.drawCentredString(165,118,"Subtotales: ")



    c.setFont("Courier",4)
    lineaNueva =0
    totalGastos=0

    if len(lista) >1 :



        for i in range(0,len(lista)):
            lista3=lista[i].replace("'","").split(", ")
            totalGastos+= int(lista3[2])


            lineaNueva +=5

            c.drawString(17,122+lineaNueva,lista3[0])
            c.drawString(62,122+lineaNueva,lista3[1])
            c.drawString(105,122+lineaNueva,lista3[2]+" €")


    else:

        lista3=lista[0].replace("'","").split(", ")

        c.drawString(17,127,lista3[0])
        c.drawString(65,127,lista3[1])
        c.drawString(105,127,lista3[2]+" €")




    totalKilometros= float(kilometros)*0.32
    c.drawString(62,173,kilometros+ " Kms.")
    c.drawString(105,173,"0,32 €/km.")
    c.drawString(155,173,str(totalKilometros)+" €")

    totalAntiguedad= int(antiguedad)*250
    c.drawString(62,185,antiguedad+" años")
    c.drawString(105,185,"250 €/año")
    c.drawString(155,185,str(totalAntiguedad)+" €")

    totalDeducciones= 3500 * 0.15
    c.drawString(62,198,"Salario bruto: 3500 € x 15% IRPF")
    c.drawString(155,198,str(totalDeducciones)+" €")

    totalLiquido= (totalGastos+totalAntiguedad+totalKilometros)-totalDeducciones
    c.drawString(155,217,str(totalLiquido)+" €")



    # Drawing table for Item Description
    c.line(15,210,185,210)
    c.line(15,162,185,162)

    c.drawString(155,160,str(totalGastos)+" €")


    c.line(15,175,185,175)
    c.line(15,200,185,200)
    c.line(15,187,185,187)
    c.line(145,110,145,220)

    # Declaration and Signature
    c.line(15,220,185,220)
    c.line(100,220,100,260)

    c.setFont("Times-Bold",5)
    c.drawString(20,248,"Esto no es una nómina real")
    c.drawString(20,253," solo es una simulación.")
    c.drawImage("Imagenes/Firma.jpg",110,222,width=60,height=35)
    c.drawRightString(175,253,"Firma Autorizada")
    # End the Page and Start with new



    c.showPage()
    # Saving the PDF
    c.save()


def imprimirNominaSeleccionada():

    respuesta=""
    trabajador=""

    while respuesta != "n":

        print()

        print(color.BLUE+"Elige la nómina que quieres imprimir: "+color.END)

        opcionImprimir= csvreader_listarTodasLasNominas() +1

        contador=0

        with open("Archivos/nominasTrabajadores.csv","r") as csvfichero:
            reader = list(csv.reader(csvfichero))

            for fila in reader:
                contador+=1
                if contador == opcionImprimir:
                    #print("Fila8: "+fila[8])
                    lista2= fila[5][2:-2].split('], [')
                    #print("Lista2: "+str(lista2))
                    #print()

                    for i in range(0,len(lista2)):
                        #lista3=lista2[i].replace("'","").split(", ")
                        #for j in range(0,len(lista3)):

                            #def verFactura(nombre,apellidos,dni,direccion,kilometros,lista,fechaAlta,categoria,antiguedad,estado)
                            #"Nombre","Apellidos","DNI","Dirección","KilometrajeMesActivo","ListaGastos","FechaAlta","Categoria","Antiguedad","Trabajando"

                            verFactura(fila[0],fila[1],fila[2],fila[3],fila[4],lista2,fila[6],fila[7],fila[8],fila[9])


                    numeroFactura=str(fila[0])


        print(Fore.GREEN+"El fichero "+numeroFactura+" se ha añadido al directorio de 'Nóminas'\n")


        respuesta = input(color.BLUE+"Quieres imprimir más nóminass: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres imprimir más nóminas ? (s/n) "+color.END)
