
from Color import *
from FechaRegistros import registroBD
from GraficoPresupuestos import *
from Presupuestos import *
from Validaciones import *
from colorama import init, Fore
init(autoreset=True)


def listarNominaTrabajador(trabajador):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    # CONSULTA DEVOLVERLISTADO NOMINAS
    cursor = db.cursor()
    consulta = 'SELECT B.NOMBRE, B.APELLIDOS, B.DNI, B.FECHA_ALTA, B.TRABAJANDO, A.MES_NOMINA, A.DIAS_TRABAJADOS, A.ANTIGUEDAD, A.KILOMETRAJE_MES, A.T0TAL_KILOMETROS, A.SUBTOTAL_LISTA_GASTOS, A.GASTO_TOTAL, A.SALARIO_NETO, A.DEDUCCIONES, A.LIQUIDO_A_PERCIBIR FROM NOMINAS A, TRABAJADORES B WHERE A.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR AND B.NOMBRE='+"'"+trabajador+"'"
    #print(consulta)
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    if len(resultados) > 0:

        #print()

        print(color.BLUE+"Asi queda la nómina del trabajador "+trabajador.upper()+":\n"+color.END)

        #print(color.BLUE+"\t"+"Nombre".ljust(15," ")+"Apellidos".ljust(15," ")+"DNI".ljust(15," ")+"Fecha de alta".ljust(20," ")+"Estado laboral".ljust(20," ")+"Mes actual".ljust(15," ")+"Días trabajados".ljust(20," ")+"Antiguedad".ljust(15," ")+"Kilometraje mes actual".ljust(25," ")+"Total Kilometraje mes actual".ljust(30," ")+"Subtotal lista gastos".ljust(30," ")+"Gasto total".ljust(20," ")+"Salario neto".ljust(20," ")+"Deducciones".ljust(16," ")+"Líquido a percibir".ljust(16," ")+color.END)
        #print()

        for registro in resultados:

            #print(color.BLUE + "\t" + registro[0].ljust(15," ") + color.END + registro[1].ljust(15," ") + registro[2].ljust(15," ") + registro[3].ljust(20," ")+ registro[4].ljust(20," ")+ registro[5].ljust(15," ")+str(registro[6]).ljust(20," ")+ str(registro[7]).ljust(15," ")+ str(str(registro[8])+ " Kms.").ljust(25," ")+ str(str(registro[9])+" €").ljust(30," ")+ str(str(registro[10])+" €").ljust(30," ")+ str(str(registro[11])+" €").ljust(20," ") + str(str(registro[12])+" €").ljust(20," ")+ str(str(registro[13])+" €").ljust(16," ")+ str(str(registro[14])+" €").ljust(16," "))
            #print()

            print(color.BLUE+"\tNombre:".ljust(35," ")+color.END+Fore.GREEN+registro[0].rjust(15," "))
            print(color.BLUE+"\tApellidos:".ljust(35," ")+ color.END+Fore.GREEN+registro[1].rjust(15," "))
            print(color.BLUE+"\tDNI:".ljust(35," ")+ color.END+Fore.GREEN+registro[2].rjust(15," "))
            print(color.BLUE+"\tFecha de alta:".ljust(35," ")+color.END+ Fore.GREEN+registro[3].rjust(15," "))
            print(color.BLUE+"\tEstado laboral:".ljust(35," ")+ color.END+Fore.GREEN+registro[4].rjust(15," "))
            print(color.BLUE+"\tMes actual:".ljust(35," ")+ color.END+Fore.GREEN+registro[5].rjust(15," "))
            print(color.BLUE+"\tDías trabajados".ljust(35," ")+color.END+ Fore.GREEN+str(str(registro[6])+" días").rjust(15," "))
            print(color.BLUE+"\tAntiguedad:".ljust(35," ")+ color.END+Fore.GREEN+str(str(registro[7])+" años").rjust(15," "))
            print(color.BLUE+"\tKilometraje mes actual:".ljust(35," ")+ color.END+Fore.GREEN+str(str(registro[8])+" Kms").rjust(15," "))
            print(color.BLUE+"\tTotal Kilometraje mes actual:".ljust(35," ")+color.END+ Fore.GREEN+str(str(registro[9])+" €").rjust(15," "))
            print(color.BLUE+"\tSubtotal lista gastos:".ljust(35," ")+ color.END+Fore.GREEN+str(str(registro[10])+" €").rjust(15," "))
            print(color.BLUE+"\tGasto total:".ljust(35," ")+ color.END+Fore.GREEN+str(str(registro[11])+" €").rjust(15," "))
            print(color.BLUE+"\tSalario neto:".ljust(35," ")+ color.END+Fore.GREEN+str(str(registro[12])+" €").rjust(15," "))
            print(color.BLUE+"\tDeducciones:".ljust(35," ")+ color.END+Fore.GREEN+str(str(registro[13])+" €").rjust(15," "))
            print(color.BLUE+"\tLíquido a percibir:".ljust(35," ")+ color.END+Fore.GREEN+str(str(registro[14])+" €").rjust(15," "))

        print()

    else:
        print(color.RED+"No existen nóminas !!"+color.END)




def buscarIDNomina(trabajador):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        # CONSULTA COMPROBAR SI EXISTE EL TRABAJADOR
        cursor = db.cursor()
        consulta = 'SELECT A.ID_NOMINA FROM NOMINAS A, TRABAJADORES B WHERE A.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR AND B.NOMBRE='+ "'"+trabajador +"'"
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        if len(resultados) > 0 :

            for registro in resultados:
                    return registro[0]

        else:
            print(color.RED+"No existen trabajadores y/o trabajadores !!"+color.END)


def listarKilometrajeGastosTrabajador(trabajador):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        # CONSULTA LISTA DE GASTOS DEL TRABAJADOR
        cursor = db.cursor()
        consulta = 'SELECT B.APELLIDOS, A.LUGAR, A.FECHA, A.DESCRIPCION, A.PRECIO FROM LISTA_GASTOS A, TRABAJADORES B, NOMINAS C WHERE C.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR AND A.ID_RELACION_NOMINAs=C.ID_NOMINA AND B.NOMBRE='+ "'"+trabajador +"'"
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        total=0

        if len(resultados) > 0:

            print()

            print(color.BLUE+"Estos son los gastos del trabajador "+trabajador.upper()+" en el mes activo: \n"+color.END)
            print(color.BLUE+"\t"+"Descripción".ljust(50," ")+"Fecha".ljust(24," ")+"Lugar".ljust(30," ")+"Precio".ljust(24," ")+color.END)

            for registro in resultados:

                print("\t"+registro[3].ljust(50," ")+registro[2].ljust(24," ")+registro[1].ljust(30," ")+str(str(registro[4])+" €").ljust(24," "))

                total=total + int(registro[4])


            print()
            print(color.BLUE+"\tTOTAL: "+color.END+str(total)+" €")
            print()


        else:
            print(color.RED+"El trabajador "+trabajador.upper()+" no tiene ningún gasto (nos sale más barato)"+color.END)




def listarGastosTrabajador(trabajador):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        # CONSULTA LISTA DE GASTOS DEL TRABAJADOR
        cursor = db.cursor()
        consulta = 'SELECT B.APELLIDOS, A.LUGAR, A.FECHA, A.DESCRIPCION, A.PRECIO FROM LISTA_GASTOS A, TRABAJADORES B, NOMINAS C WHERE C.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR AND A.ID_RELACION_NOMINAs=C.ID_NOMINA AND B.NOMBRE='+ "'"+trabajador +"'"
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        total=0

        if len(resultados) > 0:

            print()

            print(color.BLUE+"\t"+"Descripción".ljust(50," ")+"Fecha".ljust(24," ")+"Lugar".ljust(30," ")+"Precio".ljust(24," ")+color.END)

            for registro in resultados:

                print("\t"+registro[3].ljust(50," ")+registro[2].ljust(24," ")+registro[1].ljust(30," ")+str(str(registro[4])+" €").ljust(24," "))

                total=total + int(registro[4])


            print()
            print(color.BLUE+"\tTOTAL: "+color.END+str(total)+" €")
            print()


        else:
            print(color.RED+"El trabajador "+trabajador.upper()+" no tiene ningún gasto (nos sale más barato)"+color.END)




def actualizarKilometraje(trabajador):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    # CONSULTA DEVOLVER NOMBRE DEL TRABAJADOR SELECCIONADO
    cursor = db.cursor()
    consulta = 'SELECT A.KILOMETRAJE_MES, C.COCHE, C.KILOMETROS_TOTALES, C.ESTADO FROM NOMINAS A, TRABAJADORES B, COCHES C WHERE A.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR AND C.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR AND B.NOMBRE='+"'"+trabajador+"'"
    #print(consulta)
    cursor.execute(consulta)
    resultados = cursor.fetchall()


    for registro in resultados:

        print()

        print(Fore.GREEN+"El trabajador "+ trabajador.upper()+" tiene actualmente un "+ registro[1]+" con "+str(registro[2])+ " kilómetros totales. con un estado: "+registro[3].upper())

        print()

        pregunta = input(color.BLUE + "Quieres actualizar el kilometraje ? (S/N): " + color.END)
        while (pregunta.lower()!= "s" and pregunta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            pregunta = input(color.BLUE + "Quieres actualizar el kilometraje ? (S/N) ? : " + color.END)

        if pregunta.lower() == "s":
            nuevoKilometraje= validarIntroducirNumero("Kilometros realizados hasta el último día del mes en activo: ")

            # ACTUALIZO LOS KILOMETROS DEL MES DE LA TABLA NOMINAS Y LOS KILOMETROS TOTALES DE LA TABLA COCHES
            cursor = db.cursor()
            consulta = 'UPDATE NOMINAS A, TRABAJADORES B, COCHES C SET A.KILOMETRAJE_MES='+str(nuevoKilometraje)+", C.KILOMETROS_TOTALES=C.KILOMETROS_TOTALES+"+str(nuevoKilometraje)+" WHERE B.ID_TRABAJADOR=A.ID_RELACION_TRABAJADOR AND B.NOMBRE="+"'"+trabajador+"'"
            #print(consulta)
            cursor.execute(consulta)
            db.commit()

            # ACTUALIZO LOS KILOMETROS DEL MES DE LA TABLA NOMINAS Y LOS KILOMETROS TOTALES DE LA TABLA COCHES
            #UPDATE NOMINAS A, TRABAJADORES B SET A.T0TAL_KILOMETROS=A.KILOMETRAJE_MES * 0.50 WHERE A.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR AND B.NOMBRE='Juanan'
            cursor = db.cursor()
            consulta = 'UPDATE NOMINAS A, TRABAJADORES B SET A.T0TAL_KILOMETROS=A.KILOMETRAJE_MES * 0.32, A.GASTO_TOTAL=(A.KILOMETRAJE_MES * 0.32) + A.SUBTOTAL_LISTA_GASTOS, A.SALARIO_NETO=(A.DIAS_TRABAJADOS * 110) + (A.ANTIGUEDAD *250) + (A.KILOMETRAJE_MES * 0.32) + A.SUBTOTAL_LISTA_GASTOS, A.DEDUCCIONES=((A.DIAS_TRABAJADOS * 110) + (A.ANTIGUEDAD *250) + (A.KILOMETRAJE_MES * 0.32)) * 0.15, A.LIQUIDO_A_PERCIBIR=((A.DIAS_TRABAJADOS * 110) + (A.ANTIGUEDAD *250) + (A.KILOMETRAJE_MES * 0.32) + A.SUBTOTAL_LISTA_GASTOS) - ((A.DIAS_TRABAJADOS * 110) + (A.ANTIGUEDAD *250) + (A.KILOMETRAJE_MES * 0.32)) * 0.15 WHERE A.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR AND B.NOMBRE='+"'"+trabajador+"'"
            #print(consulta)
            cursor.execute(consulta)
            db.commit()

            # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(trabajador,consulta)

            print(Fore.GREEN+"Se ha actualizado correctamente los kilómetros del mes actual a la tabla nóminas")

            listarNominaTrabajador(trabajador)


    #Me voy al menú anterior:
    eligeGastos(trabajador)



def gastosComida(trabajador):

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    print(color.BLUE+"Estos son los gastos del trabajador "+trabajador.upper()+" en el mes activo: \n"+color.END)
    listarGastosTrabajador(trabajador)

    pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N): " + color.END)
    while (pregunta.lower()!= "s" and pregunta.lower() != "n"):
        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
        pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N) ? : " + color.END)


    if pregunta.lower() == "s":
        fecha = validarFecha("Introduce fecha de la comida: ")
        sitio= validarString("Introducir lugar de la comida: ",20)
        descripcion= validarString("Introducir descripcion: ",50)
        precio= validarIntroducirNumero("Precio de la comida: ")
        idNominaTrabajador = buscarIDNomina(trabajador)

        tabla = 'LISTA_GASTOS'
        columnasListaGastos = "ID_RELACION_NOMINAS,LUGAR,FECHA,DESCRIPCION,PRECIO"
        listaGastos = str(idNominaTrabajador) +", '"+sitio+"', '"+fecha+"', '"+descripcion+"', "+str(precio)
        insertar_datos_generica(db, tabla, columnasListaGastos,listaGastos)

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(trabajador,"INSERT INTO " + tabla + "("+ columnasListaGastos +") VALUES (" + listaGastos + ")")

        print(Fore.GREEN+"Se ha añadido añadido el gasto de comida y se han actualizado los valores de la tabla nóminas")

        print(color.BLUE+"Asi quedan los gastos del trabajador "+trabajador.upper())
        listarGastosTrabajador(trabajador)


    #Me voy al menú anterior:
    eligeGastos(trabajador)



def gastosParking(trabajador):

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    print(color.BLUE+"Estos son los gastos del trabajador "+trabajador.upper()+" en el mes activo: \n"+color.END)
    listarGastosTrabajador(trabajador)

    pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N): " + color.END)
    while (pregunta.lower()!= "s" and pregunta.lower() != "n"):
        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
        pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N) ? : " + color.END)

    if pregunta.lower() == "s":

        sitio= validarString("Introducir nombre del Parking: ",20)
        fecha = validarFecha("Introduce fecha del parking: ")
        descripcion = validarString("Descripción uso del parking: ",50)
        precio= validarIntroducirNumero("Precio del parking: ")

        idNominaTrabajador = buscarIDNomina(trabajador)

        tabla = 'LISTA_GASTOS'
        columnasListaGastos = "ID_RELACION_NOMINAS,LUGAR,FECHA,DESCRIPCION,PRECIO"
        listaGastos = str(idNominaTrabajador) +", '"+sitio+"', '"+fecha+"', '"+descripcion+"', "+str(precio)
        insertar_datos_generica(db, tabla, columnasListaGastos,listaGastos)

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(trabajador,"INSERT INTO " + tabla + "("+ columnasListaGastos +") VALUES (" + listaGastos + ")")

        print(Fore.GREEN+"Se ha añadido añadido el gasto del parking y se han actualizado los valores de la tabla nóminas")

        print(color.BLUE+"Asi quedan los gastos del trabajador "+trabajador.upper())
        listarGastosTrabajador(trabajador)


    #Me voy al menú anterior:
    eligeGastos(trabajador)



def gastosPernocta(trabajador):

     # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    print(color.BLUE+"Estos son los gastos del trabajador "+trabajador.upper()+" en el mes activo: \n"+color.END)
    listarGastosTrabajador(trabajador)

    pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N): " + color.END)
    while (pregunta.lower()!= "s" and pregunta.lower() != "n"):
        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
        pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N) ? : " + color.END)

    if pregunta.lower() == "s":

        sitio= validarString("Introducir nombre del Hotel: ",20)
        fecha = validarFecha("Introduce fecha de la pernocta: ")
        descripcion = validarString("Descripción motivo pernocta: ",50)
        precio= validarIntroducirNumero("Precio del Hotel: ")

        idNominaTrabajador = buscarIDNomina(trabajador)

        tabla = 'LISTA_GASTOS'
        columnasListaGastos = "ID_RELACION_NOMINAS,LUGAR,FECHA,DESCRIPCION,PRECIO"
        listaGastos = str(idNominaTrabajador) +", '"+sitio+"', '"+fecha+"', '"+descripcion+"', "+str(precio)
        insertar_datos_generica(db, tabla, columnasListaGastos,listaGastos)

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(trabajador,"INSERT INTO " + tabla + "("+ columnasListaGastos +") VALUES (" + listaGastos + ")")

        print(Fore.GREEN+"Se ha añadido añadido el gasto de la pernocta y se han actualizado los valores de la tabla nóminas")

        print(color.BLUE+"Asi quedan los gastos del trabajador "+trabajador.upper())
        listarGastosTrabajador(trabajador)


    #Me voy al menú anterior:
    eligeGastos(trabajador)



def gastosOtros(trabajador):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    print(color.BLUE+"Estos son los gastos del trabajador "+trabajador.upper()+" en el mes activo: \n"+color.END)
    listarGastosTrabajador(trabajador)


    pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N): " + color.END)
    while (pregunta.lower()!= "s" and pregunta.lower() != "n"):
        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
        pregunta = input(color.BLUE + "Quieres añadir más gastos ? (S/N) ? : " + color.END)

    if pregunta.lower() == "s":

        sitio= validarString("Introducir nombre del gasto: ",20)
        fecha = validarFecha("Introduce fecha del gasto: ")
        descripcion = validarString("Descripción motivo del gasto: ",50)
        precio= validarIntroducirNumero("Precio del gasto: ")

        idNominaTrabajador = buscarIDNomina(trabajador)

        tabla = 'LISTA_GASTOS'
        columnasListaGastos = "ID_RELACION_NOMINAS,LUGAR,FECHA,DESCRIPCION,PRECIO"
        listaGastos = str(idNominaTrabajador) +", '"+sitio+"', '"+fecha+"', '"+descripcion+"', "+str(precio)
        insertar_datos_generica(db, tabla, columnasListaGastos,listaGastos)

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(trabajador,"INSERT INTO " + tabla + "("+ columnasListaGastos +") VALUES (" + listaGastos + ")")

        print(Fore.GREEN+"Se ha añadido añadido el gasto "+descripcion+ " y se han actualizado los valores de la tabla nóminas")

        print(color.BLUE+"Asi quedan los gastos del trabajador "+trabajador.upper())
        listarGastosTrabajador(trabajador)


    #Me voy al menú anterior:
    eligeGastos(trabajador)




def eligeGastos(trabajador):

    listaNivel = [1,2,3,4,5,6]

    print(color.BLUE+"Elige los gastos a pasar:\n\n"+color.END+

        color.BLUE+"\t1"+color.END +" Kilometraje\n"+
        color.BLUE+"\t2"+color.END +" Comidas\n"+
        color.BLUE+"\t3"+color.END +" Parking\n"+
        color.BLUE+"\t4"+color.END +" Pernoctar\n"+
        color.BLUE+"\t5"+color.END +" Otros\n"+
        color.BLUE+"\t6"+color.END +" Salir\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        #kilometraje= validarIntroducirNumero("Añade kilómetros recorridos este mes: ")
        actualizarKilometraje(trabajador)

    if opcion == 2:
        gastosComida(trabajador)

    if opcion == 3:
        gastosParking(trabajador)

    if opcion == 4:
        gastosPernocta(trabajador)

    if opcion == 5:
        gastosOtros(trabajador)



