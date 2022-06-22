import csv
import os
#from Gastos import *
from Color import *
from ConsultasBD import insertar_datos_generica
from FechaRegistros import registroBD
from GraficoPresupuestos import *
from FechaRegistros import *
from ConsultasBD import *
from Logueo import *
from Presupuestos import *
from Validaciones import *
#from ficheros import *
from Ventas import *
from colorama import init, Fore
init(autoreset=True)


def MenuRRHH(usuario):
    opciones = [1, 2, 3, 4, 5, 6, 7, 8]

    print(color.BLUE + "Elige una opción:\n\n" + color.END +

          color.BLUE + "\t1" + color.END + " Nuevo trabajador/a\n" +
          color.BLUE + "\t2" + color.END + " Solicitudes de usuarios/as nuevos/as\n" +
          color.BLUE + "\t3" + color.END + " Cambio situación laboral\n" +
          color.BLUE + "\t4" + color.END + " Borrar trabajador/a\n" +
          color.BLUE + "\t5" + color.END + " Cursillos formación\n" +
          color.BLUE + "\t6" + color.END + " Imprimir nómina\n" +
          color.BLUE + "\t7" + color.END + " Iniciar nóminas nuevo mes\n" +
          color.BLUE + "\t8" + color.END + " Salir\n")

    opcion = validarNumeroLista(opciones)

    if opcion == 1:
        crearTrabajador(usuario)
    if opcion == 2:
        get_emails("smtp.gmail.com","departamentorrhh.pixels@gmail.com","PIXELS1234",True)
        crearTrabajador(usuario)
    if opcion == 3:
        bajaLaboral(usuario)
    if opcion == 4:
        borrarTrabajador(usuario)
    if opcion == 5:
        cursosFormacion(usuario)
    if opcion == 6:
        imprimirNomina(usuario)
    if opcion == 7:
       iniciarNominasNuevoMes(usuario)



def listarCursosTrabajador(trabajador):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    # CONSULTA DEVOLVER NOMBRE DEL TRABAJADOR SELECCIONADO
    cursor = db.cursor()
    consulta = 'SELECT B.ID_TRABAJADOR, B.NOMBRE, B.APELLIDOS, A.DESCRIPCION, A.HORAS, A.LUGAR, A.FECHA_INICIO, A.FECHA_FIN FROM CURSOS_FORMACION A, TRABAJADORES B WHERE A.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR AND B.NOMBRE='+"'"+trabajador+"'"
    #print(consulta)
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    if len(resultados) > 0 :

        print(color.BLUE+"Estos son los cursos del trabajador "+trabajador.upper()+ " en el mes activo: \n"+color.END)
        print(color.BLUE+"\t"+"Descripción".ljust(24," ")+"Duración del curso".ljust(24," ")+"Lugar".ljust(24," ")+"Fecha inicio".ljust(24," ")+"Fecha fin".ljust(24," ")+color.END)
        for registro in resultados:
            if registro[1].lower()== trabajador.lower():
                print("\t"+registro[3].ljust(24," ")+str(registro[4]).ljust(24," ")+registro[5].ljust(24," ")+registro[6].ljust(24," ")+registro[7].ljust(24," "))


    else:
        print(color.RED+ "El trabajador "+ trabajador.upper()+ " no está en ningún curso de formación actualmente"+color.END)



def listarTrabajadores():

        opciones=[]

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        # CONSULTA COMPROBAR SI EXISTE EL TRABAJADOR
        cursor = db.cursor()
        consulta = 'SELECT ID_TRABAJADOR,NOMBRE,APELLIDOS FROM TRABAJADORES'
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        if len(resultados) > 0:

            print()

            print(color.BLUE+"Elige un trabajador/a: \n")

            for registro in resultados:
                opciones.append(registro[0])
                print(color.BLUE+"\t"+str(registro[0])+color.END+" "+registro[1]+" "+registro[2])

            print()

        else:
            print(color.RED+ "No existen trabajadores actualmente"+color.END)


        return validarNumeroLista(opciones)




def nombreTrabajador(opcionClienteSeleccionado):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        # CONSULTA DEVOLVER NOMBRE DEL TRABAJADOR SELECCIONADO
        cursor = db.cursor()
        consulta = 'SELECT ID_TRABAJADOR,NOMBRE FROM TRABAJADORES'
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        if len(resultados) > 0:

            for registro in resultados:
                if registro[0]== opcionClienteSeleccionado:
                    return registro[1]
        else:
            print(color.RED+ "No existen trabajadores actualmente"+color.END)





def nombreTrabajadorFacturaSeleccionada(opcionFacturaSeleccionada):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        # CONSULTA DEVOLVER NOMBRE DEL TRABAJADOR SELECCIONADO
        cursor = db.cursor()
        consulta = 'SELECT A.NOMBRE FROM TRABAJADORES A, NOMINAS B WHERE B.ID_RELACION_TRABAJADOR=A.ID_TRABAJADOR AND B.ID_NOMINA='+ str(opcionFacturaSeleccionada)
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()


        for registro in resultados:
                return registro[0]


def situacionLaboral(opcionClienteSeleccionado):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        # CONSULTA DEVOLVER NOMBRE DEL TRABAJADOR SELECCIONADO
        cursor = db.cursor()
        consulta = 'SELECT ID_TRABAJADOR,TRABAJANDO FROM TRABAJADORES'
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        for registro in resultados:
            if registro[0]==opcionClienteSeleccionado:
                return registro[1]



def borrarTrabajador(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    respuesta =""

    while respuesta !="n":

        opcion= listarTrabajadores()

        print()

        # BORRAR TRABAJADOR
        cursor = db.cursor()
        consulta = 'DELETE FROM TRABAJADORES WHERE ID_TRABAJADOR='+str(opcion)
        #print(consulta)
        cursor.execute(consulta)
        db.commit()

        print(Fore.GREEN+"El usuario/a "+usuario.upper()+" ha sido borrado/a de PIXELS !!")

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(usuario,consulta)

        respuesta = input(color.BLUE+"Quieres borrar más registros: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres borrar más registros ? (s/n) "+color.END)


    #Me voy al menú anterior:
    MenuRRHH(usuario)


def bajaLaboral(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    opcionClienteSeleccionado = listarTrabajadores()
    trabajador= nombreTrabajador(opcionClienteSeleccionado)
    estadoTrabajando= eligeSituacionLaboral()
    situacionLaboralTrabajador = situacionLaboral(opcionClienteSeleccionado)

    print(Fore.GREEN + "El trabajador " + trabajador.upper() + " tiene actualmente la siguiente situación laboral: "+situacionLaboralTrabajador.upper() )
    pregunta = input(color.BLUE + "Quieres cambiar la situación laboral de "+trabajador+" ? (S/N): " + color.END)
    while (pregunta.lower() != "s" and pregunta.lower() != "n"):
        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
        pregunta = input(color.BLUE + "Quieres cambiar la situación laboral de "+trabajador.upper()+" ? (S/N) ? : " + color.END)

    if pregunta =="s":

        # ACTUALIZAR SITUACIÓN LABORAL DEL TRABAJADOR
        cursor = db.cursor()
        consulta = 'UPDATE TRABAJADORES SET TRABAJANDO='+"'"+estadoTrabajando+"'"+' WHERE ID_TRABAJADOR='+str(opcionClienteSeleccionado)
        #print(consulta)
        cursor.execute(consulta)
        db.commit()

        print(Fore.GREEN + "El trabajador " + trabajador.upper() + " se le ha cambiado correctamente la situación laboral a "+estadoTrabajando.upper() )

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(usuario,consulta)


    # Me voy al menú anterior:
    MenuRRHH(usuario)


def iniciarNominasNuevoMes(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    # Elijo el nuevo mes para las nóminas:
    nuevoPeriodoNominas= eligePeriodoNomina()

    # Calculo el año actual y los dias que corresponde al mes seleccionado para la nueva nómina:
    anio= str(datetime.now().year)
    diasMes =obtener_dias_del_mes(nuevoPeriodoNominas,anio)

    # Para inicializar un nuevo periodo de mes de nóminas:
    # - Borro todos los datos de la LISTA_GASTOS --> hago un 'TRUNCATE'
    # - Inicializo a todos los trabajadores/as, osea todas las filas, a 'cero' las posiciones que me interesan para hacer las nuevas nóminas:


    # CONSULTA INICIALIZAR DATOS TABLA NOMINAS
    cursor = db.cursor()
    #consulta = 'UPDATE NOMINAS A, LISTA_GASTOS B SET A.GASTO_TOTAL=0, B.PRECIO=0, A.KILOMETRAJE_MES=0, A.T0TAL_KILOMETROS=0, A.SUBTOTAL_LISTA_GASTOS=0, A.SALARIO_NETO=0,A.DEDUCCIONES=0,A.LIQUIDO_A_PERCIBIR=0  WHERE A.ID_NOMINA=B.ID_RELACION_NOMINAS'
    consulta='UPDATE NOMINAS A SET A.GASTO_TOTAL=0, A.MES_NOMINA='+"'"+nuevoPeriodoNominas+"', "'DIAS_TRABAJADOS='+str(diasMes)+", "'A.KILOMETRAJE_MES=0, A.T0TAL_KILOMETROS=0, A.SUBTOTAL_LISTA_GASTOS=0, A.SALARIO_NETO=0,A.DEDUCCIONES=0,A.LIQUIDO_A_PERCIBIR=0'
    #print(consulta)
    cursor.execute(consulta)


    # CONSULTA VACIAR DATOS TABLA LISTA_GASTOS
    cursor = db.cursor()
    consulta = 'TRUNCATE TABLE LISTA_GASTOS'
    #print(consulta)
    cursor.execute(consulta)

   # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
    registroBD(usuario,"Se ha inicializado todas las nóminas al nuevo mes de "+ nuevoPeriodoNominas.upper()+ " con  "+ str(diasMes)+ " días y se han borrado todas las listas de gastos")

    db.close()


    # Me voy al menú anterior:
    MenuRRHH(usuario)



def cursosFormacion(usuario):

    formacion=""
    sitio=""
    horas=0

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    opcionClienteSeleccionado = listarTrabajadores()
    contador = 0

    # Aquí listo los cursos pero pasándole el trabajador por parámetro de entrada, que a su vez le tenga que pasar la opción del trabajador elegido ('opcionClienteSeleccionado')
    # y a su vez se lo paso a la función 'nombreTrabajador(oción)' para que me devuelva el nombre del trabajador/a:

    listarCursosTrabajador(nombreTrabajador(opcionClienteSeleccionado))

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

        tablaCursos = 'CURSOS_FORMACION'
        columnasCursosFormacion = "ID_RELACION_TRABAJADOR,DESCRIPCION,HORAS,LUGAR,FECHA_INICIO,FECHA_FIN"
        cursoFormacion = str(opcionClienteSeleccionado)+",'"+formacion+"',"+str(horas)+",'"+sitio+"','"+fechaInicio+"','"+fechaFin+"'"
        insertar_datos_generica(db, tablaCursos, columnasCursosFormacion,cursoFormacion)

    # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
    registroBD(usuario,"Añadido el trabajador "+nombreTrabajador(opcionClienteSeleccionado).upper()+ " a un nuevo curso de formación de "+formacion+" en "+sitio+ " de "+str(horas) +" horas")

    # Me voy al menú anterior:
    MenuRRHH(usuario)


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
        #resultado = "Compras"
        print(Fore.GREEN+"Se ha creado correctamente el comercial: "+trabajador.upper()+ " "+apellidos.upper()+ " con acceso a los departamentos: COMPRAS ")

    ventas = input(color.BLUE + "Quieres añadir al departamento VENTAS ? (S/N): " + color.END)
    while (ventas.lower()!= "s" and ventas.lower() != "n"):
        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
        ventas = input(color.BLUE + "Quieres añadir al departamento VENTAS ? (S/N): " + color.END)

    if (ventas.lower() == "s"):
        #resultado += " , Ventas"
        print(Fore.GREEN+"Se ha creado correctamente el comercial: "+trabajador.upper()+ " "+apellidos.upper()+ " con acceso al departamento: VENTAS")

    rrhh = input(color.BLUE + "Quieres añadir al departamento RRHH ? (S/N): " + color.END)
    while (rrhh.lower()!= "s" and rrhh.lower() != "n"):
        print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
        rrhh = input(color.BLUE + "Quieres añadir al departamento RRHH ? (S/N): " + color.END)

    if (rrhh.lower() == "s"):
        #resultado += " , RRHH"
        print(Fore.GREEN+"Se ha creado correctamente el comercial: "+trabajador.upper()+ " "+apellidos.upper()+ " con acceso al departamento: RRHH")


    print(Fore.GREEN+"Se le incluye automáticamente al trabajador "+trabajador.upper()+ " "+apellidos.upper()+ " al departamento: GASTOS")

    if (compras == "n" and ventas=="n" and rrhh=="n"):
        resultado ="Gastos"
    if (compras == "s" and ventas=="n" and rrhh=="n"):
        resultado ="Compras , Gastos"
    if (compras == "n" and ventas=="s" and rrhh=="n"):
        resultado ="Ventas , Gastos"
    if (compras == "n" and ventas=="n" and rrhh=="s"):
        resultado ="RRHH , Gastos"
    if (compras == "s" and ventas=="s" and rrhh=="n"):
        resultado ="Compras , Ventas , Gastos"
    if (compras == "s" and ventas=="n" and rrhh=="s"):
        resultado ="Compras , RRHH , Gastos"
    if (compras == "s" and ventas=="s" and rrhh=="s"):
        resultado ="Compras , Ventas , RRHH , Gastos"
    if (compras == "n" and ventas=="s" and rrhh=="s"):
        resultado ="Ventas , RRHH , Gastos"


    return resultado


#eligirDepartamento("Pepe","Ruiz")

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

    print()

    print(color.BLUE + "Elige el mes para la nómina actual:\n\n" + color.END +

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

    opcion = validarNumeroLista(listaNivel)

    if opcion == 1:
        return "Enero"
    if opcion == 2:
        return "Febrero"
    if opcion == 3:
        return "Marzo"
    if opcion == 4:
        return "Abril"
    if opcion == 5:
        return "Mayo"
    if opcion == 6:
        return "Junio"
    if opcion == 7:
        return "Julio"
    if opcion == 8:
        return "Agosto"
    if opcion == 9:
        return "Septiembre"
    if opcion == 10:
        return "Octubre"
    if opcion == 11:
        return "Noviembre"
    if opcion == 12:
        return "Diciembre"



def es_bisiesto(anio):
    if (anio % 4 == 0) and (anio % 100 != 0 or anio % 400 == 0):
        return True


def obtener_dias_del_mes(mes,anio):
    # Abril, junio, septiembre y noviembre tienen 30
    if mes in ["Abril", "Junio", "Septiembre", "Noviembre"]:
        return 30
    # Febrero depende de si es o no bisiesto
    if mes == "Febrero":
        if es_bisiesto(anio):
            return 29
        else:
            return 28
    else:
        # En caso contrario, tiene 31 días
        return 31



def comprobarExisteTrabajador(dni):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        # CONSULTA COMPROBAR SI EXISTE EL TRABAJADOR
        cursor = db.cursor()
        consulta = 'SELECT DNI FROM TRABAJADORES'
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        if len(resultados) > 0 :

            for registro in resultados:
                if registro[0] == dni:
                    return True
        else:
            print(color.RED+"No existen trabajadores !!"+color.END)


def buscarIDtrabajadorNuevo(trabajador):

        # Establecemos conexión con la base de datos
        db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

        # CONSULTA COMPROBAR SI EXISTE EL TRABAJADOR
        cursor = db.cursor()
        consulta = 'SELECT ID_TRABAJADOR FROM TRABAJADORES WHERE NOMBRE='+ "'"+trabajador +"'"
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        if len(resultados) > 0 :

            for registro in resultados:
                    return registro[0]

        else:
            print(color.RED+"No existen trabajadores !!"+color.END)




def crearTrabajador(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")


    print(color.BLUE + "Introduce los datos del nuevo/a trabajador/a: " + color.END)
    respuesta = ""

    while respuesta != "n":

        dni = validarDNI()

        while not DNIesCorrecto(dni):
            print(color.RED + "DNI incorrecto" + color.END)
            dni = validarDNI()

        while comprobarExisteTrabajador(dni):
            print(color.RED + "Error !!... ya existe un trabajador con el DNI: " + dni + color.END)
            dni = validarDNI()

        nombre = validarString("Nombre: ", 20)
        apellidos = validarString("Apellidos: ", 20)
        direccion = validarString("Dirección: ", 20)
        telefono = validarTelefono()
        print(color.BLUE+"Introduce un email correcto y que esté activo, para que luego puedas comprobar el correo que se te envía con los datos de acceso a la aplicación..."+color.END)
        email = validarEmail()
        coche = eligeCoche().title()
        kilometrajeTotalcocheElegido = str(validarIntroducirNumero("Kilómetros actuales del coche elegido: "))
        kilometrajeMes = "0"


        fechaAltacontrato = validarFecha("Fecha alta contrato: ")
        trabajando = eligeSituacionLaboral()
        categoria = eligirDepartamento(nombre,apellidos)
        antiguedad = "0"
        periodoNomina = eligePeriodoNomina()

        # Extraer el año de la fecha de ALta y la convierto a dato 'int' para luego obtener los días de dicho mes:
            # - Convierto la fecha de alta de tipo string a tipo datetime con el formato que tengo en el regex de fecha --> datetime.strptime(fechaAltacontrato, "%d/%m/%Y")
            # - Ya convertido lo anterior, cojo el dato que me interesa, el año --> .strftime('%Y')

        anio= int(datetime.strptime(fechaAltacontrato, "%d/%m/%Y").strftime('%Y'))

        diasMes =obtener_dias_del_mes(periodoNomina,anio)


        # Ahora voy a grabar los datos del nuevo trabajador/a en tres ficheros:
        #     1 --> En el fichero de trabajadores, no se pueden repetir, son únicos
        #     2 --> En el fichero de nominas de los trabajadores, solo hay una nomina por cada trabajador, ya lo filtro anteriormente para que nos se repitan nominas de un trabajador
        #           Lo he planteado, que el archivo de nóminas es valido por cada mes, luego hay que hacer otro fichero de nómina por cada trabajador
        # Inicializo una variable de tipo String y le concateno todas las variables, a las que a cada una les quitos los posibles intros y saltos de línea --> 'rstrip("\r\n")' y quitar la separación den carácteres por coma --> 'split(',')'


        #"Nombre","Apellidos","DNI","Dirección","Teléfono","email","Coche","FechaAlta","Categoria","Antiguedad","Trabajando"

        # INSERT EN TABLA TRABAJADORES:

        # INSERCION DATOS TRABAJADORES
        tabla = 'TRABAJADORES'
        columnasTrabajadores = "NOMBRE,APELLIDOS,DNI,DIRECCION,TELEFONO,EMAIL,FECHA_ALTA,ANTIGUEDAD,DEPARTAMENTOS"
        trabajadorNuevo = "'"+nombre+"','"+apellidos+"','"+dni+"','"+direccion+"','"+telefono+"','"+email+"','"+fechaAltacontrato+"',"+str(antiguedad)+",'"+categoria+"'"
        insertar_datos_generica(db, tabla, columnasTrabajadores, trabajadorNuevo)

        # REGISTRO TRABAJADOR NUEVO
        registroBD(usuario,"INSERT INTO " + tabla + "("+ columnasTrabajadores +") VALUES (" + trabajadorNuevo + ")")


        # INSERCION DATOS NOMINAS
        idTrabajadorNuevo=buscarIDtrabajadorNuevo(nombre)
        tabla = 'NOMINAS'
        columnasNominas = "ID_RELACION_TRABAJADOR,MES_NOMINA,DIAS_TRABAJADOS,ANTIGUEDAD,KILOMETRAJE_MES"
        nominaTrabajadorNuevo= str(idTrabajadorNuevo)+",'"+periodoNomina+"',"+str(diasMes)+","+str(antiguedad)+","+str(kilometrajeMes)
        insertar_datos_generica(db,tabla,columnasNominas,nominaTrabajadorNuevo)

        # REGISTRO NOMINA TRABAJADOR NUEVO
        registroBD(usuario,"INSERT INTO " + tabla + "("+ columnasNominas +") VALUES (" + nominaTrabajadorNuevo + ")")


        # INSERCION DATOS COCHE
        idTrabajadorNuevo=buscarIDtrabajadorNuevo(nombre)
        tabla = 'COCHES'
        columnasCoches = "ID_RELACION_TRABAJADOR,COCHE,KILOMETROS_TOTALES"
        cocheTrabajadorNuevo= str(idTrabajadorNuevo)+",'"+coche+"',"+str(kilometrajeTotalcocheElegido)
        insertar_datos_generica(db,tabla,columnasCoches,cocheTrabajadorNuevo)

        # REGISTRO COCHE TRABAJADOR NUEVO
        registroBD(usuario,"INSERT INTO " + tabla + "("+ columnasCoches +") VALUES (" + cocheTrabajadorNuevo + ")")

        print()

        # Ahora lo meto dentro de 'usuarios.txt' para que tenfga acceso  a los diferentes departamentos
        print(color.BLUE+"Ahora generamos una contraseña para acceso a la aplicación: \n"+color.END)
        password = GenerarContrasenya()

        tabla = 'USUARIOS'
        columnasUsuarios = "ID_RELACION_TRABAJADOR,NOMBRE,CONTRASEÑA,DEPARTAMENTOS"
        usuarioNuevo = str(idTrabajadorNuevo)+",'"+nombre+"','"+password+"','"+categoria+"'"
        insertar_datos_generica(db,tabla,columnasUsuarios,usuarioNuevo)

        # REGISTRO COCHE TRABAJADOR NUEVO
        registroBD(usuario,"INSERT INTO " + tabla + "("+ columnasUsuarios +") VALUES (" + usuarioNuevo+ ")")


        #No activo 'enviaremail' porque tarda mucho y se bloquean los siguientes pasos, además de que no he desactivado el contrl de seguridad de app de terceros en gmail
        enviarEmailAccesoPixels(nombre,password,email)

        print(Fore.GREEN + "Se ha añadido correctamente al trabajador/a " + nombre.upper() + " " + apellidos.upper() + " al departamento de Gastos")
        print(Fore.GREEN + "Se ha añadido correctamente al trabajador/a " + nombre.upper() + " " + apellidos.upper() + " a la base de datos de trabajadores de PIXELS")
        print(Fore.GREEN + "Se ha añadido correctamente al trabajador/a " + nombre.upper() + " " + apellidos.upper() + " a la base de datos de coches de PIXELS")
        print(Fore.GREEN + "Se ha añadido correctamente al trabajador/a " + nombre.upper() + " " + apellidos.upper() + " a la base de datos de nominas de PIXELS")
        print(Fore.GREEN + "Se ha añadido correctamente al trabajador/a " + nombre.upper() + " " + apellidos.upper() + " a la base de datos de usuarios para tener acceso a PIXELS")

        respuesta = input(color.BLUE + "Quieres añadir más trabajadores/as: ? (s/n) " + color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE + "Quieres añadir más trabajadores/as: ? (s/n) " + color.END)

    # Me voy al menú anterior:
    MenuRRHH(usuario)


def listarTodasLasNominas():

    opciones = []

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    # CONSULTA DEVOLVERLISTADO NOMINAS
    cursor = db.cursor()
    consulta = 'SELECT A.ID_NOMINA, B.NOMBRE, B.APELLIDOS, B.DNI, B.FECHA_ALTA, B.TRABAJANDO, A.MES_NOMINA, A.DIAS_TRABAJADOS, A.ANTIGUEDAD, A.KILOMETRAJE_MES, A.GASTO_TOTAL, A.SALARIO_NETO, A.DEDUCCIONES, A.LIQUIDO_A_PERCIBIR FROM NOMINAS A, TRABAJADORES B WHERE A.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR'
    #print(consulta)
    cursor.execute(consulta)
    resultados = cursor.fetchall()

    if len(resultados) > 0:

        print()

        print(color.BLUE+"Elige la nómina que quieres imprimir\n: "+color.END)

        #"Nombre","Apellidos","DNI","Dirección","KilometrajeMesActivo","ListaGastos","FechaAlta","Categoria","Antiguedad","Trabajando"

        print(color.BLUE+"\t" +"Opcion".ljust(8," ")+ "Nombre".ljust(20," ")+"Apellidos".ljust(20," ")+"DNI".ljust(17," ")+"Fecha de alta".ljust(16," ")+"Estado laboral".ljust(25," ")+"Mes actual".ljust(20," ")+"Días trabajados".ljust(20," ")+"Antiguedad".ljust(15," ")+"Kilometraje mes actual".ljust(25," ")+"Gasto total".ljust(20," ")+"Salario neto".ljust(20," ")+"Deducciones".ljust(16," ")+"Líquido a percibir".ljust(16," ")+color.END)
        print()


        for registro in resultados:
            opciones.append(registro[0])
            print(color.BLUE + "\t" + str(registro[0]).ljust(8," ") + color.END + registro[1].ljust(20," ") + registro[2].ljust(20," ") + registro[3].ljust(17," ")+ registro[4].ljust(16," ")+ registro[5].ljust(25," ")+registro[6].ljust(20," ")+ str(registro[7]).ljust(20," ")+ str(registro[8]).ljust(15," ")+ str(str(registro[9])+" Kms.").ljust(25," ")+ str(str(registro[10])+" €").ljust(20," ")+ str(str(registro[11])+" €").ljust(20," ") + str(str(registro[12])+" €").ljust(16," ")+ str(str(registro[13])+" €").ljust(16," "))

        print()

    else:
        print(color.RED+"No existen nóminas !!"+color.END)




    return validarNumeroLista(opciones)



def imprimirNomina(usuario):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    respuesta=""
    nombre=""

    while respuesta != "n":

        #SELECCIONAR NOMINA:
        facturaSeleccionada= listarTodasLasNominas()
        nombre= nombreTrabajadorFacturaSeleccionada(facturaSeleccionada)

        c = canvas.Canvas("Archivos/Nominas/nomina"+nombre.upper()+".pdf",pagesize=(200,280),bottomup=0)

        # Logo Section
        # Setting th origin to (10,40)
        c.translate(10,40)
        # Inverting the scale for getting mirror Image of logo
        c.scale(1,-1)
        # Inserting Logo into the Canvas at required position
        c.drawImage("Archivos/Imagenes/logoFoto.png",0,0,width=50,height=30)
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
        c.drawString(92,70,"Mes nómina: ")
        c.drawRightString(45,80,"Apellidos: ")
        c.drawRightString(45,90,"Fecha Alta: ")
        c.drawString(92,90,"DNI : ")
        c.drawString(92,80,"Categoría: ")
        c.drawRightString(45,100,"Dirección : ")

        #CONSULTA DATOS TRABAJADOR SEGÚN LA FACTURA SELECCIONADA
        cursor = db.cursor()
        consulta= 'SELECT A.NOMBRE, A.APELLIDOS, A.DNI, A.DIRECCION, A.FECHA_ALTA, A.DEPARTAMENTOS, B.MES_NOMINA FROM TRABAJADORES A, NOMINAS B WHERE A.ID_TRABAJADOR=B.ID_RELACION_TRABAJADOR AND ID_NOMINA ='+str(facturaSeleccionada)
        #print(consulta)
        cursor.execute(consulta)
        resultados = cursor.fetchall()


        for registro in resultados:
            c.setFont("Courier",4)
            c.drawString(45,70,registro[0])
            c.drawString(116,70,registro[6]+" 2021")
            c.drawString(45,80,registro[1])
            c.drawString(45,90,registro[4])
            c.drawString(103,90,registro[2])
            c.drawString(112,80,registro[5])
            c.drawString(45,100,registro[3])


        # This Block Consist of Item Description
        c.roundRect(15,110,170,150,10,stroke=1,fill=0)
        c.line(15,120,185,120)

        c.setFont("Times-Bold",5)

        c.drawCentredString(30,118,"Gastos")
        c.drawCentredString(100,118,"Fecha")
        c.drawCentredString(125,118,"Precio")
        c.drawCentredString(115,217,"Líquido a percibir:")
        c.drawCentredString(30,173,"Kilómetros: ")
        c.drawCentredString(30,185,"Antiguedad: ")
        c.drawCentredString(31,198,"Salario Neto: ")
        c.drawCentredString(31,208,"Deducciones: ")
        c.drawCentredString(165,118,"Subtotales: ")



        c.setFont("Courier",4)
        lineaNueva =0
        totalGastos=0

        # CONSULTA DEVOLVER LISTADO NOMINAS
        cursor = db.cursor()
        consulta = 'SELECT C.DESCRIPCION, C.FECHA, C.PRECIO, A.MES_NOMINA, A.DIAS_TRABAJADOS, A.ANTIGUEDAD, A.KILOMETRAJE_MES, A.T0TAL_KILOMETROS, A.SUBTOTAL_LISTA_GASTOS, A.GASTO_TOTAL, A.SALARIO_NETO, A.DEDUCCIONES, A.LIQUIDO_A_PERCIBIR FROM NOMINAS A, TRABAJADORES B, LISTA_GASTOS C WHERE A.ID_RELACION_TRABAJADOR=B.ID_TRABAJADOR AND C.ID_RELACION_NOMINAS=A.ID_NOMINA AND A.ID_NOMINA='+str(facturaSeleccionada)
        cursor.execute(consulta)
        resultados = cursor.fetchall()

        print()

        for registro in resultados:

            totalGastos= int(registro[8])

            lineaNueva +=5

            c.drawString(17,122+lineaNueva,registro[0])
            c.drawString(87,122+lineaNueva,registro[1])
            c.drawString(120,122+lineaNueva,str(registro[2])+" €")


            totalKilometros= float(registro[6])*0.32
            c.drawString(62,173,str(registro[6])+ " Kms.")
            c.drawString(105,173,"0,32 €/km.")
            c.drawString(155,173,str(totalKilometros)+" €")

            totalAntiguedad= int(registro[5])*250
            c.drawString(62,185,str(registro[5])+" años")
            c.drawString(105,185,"250 €/año")
            c.drawString(155,185,str(totalAntiguedad)+" €")

            #totalDeducciones= 3500 * 0.15
            c.setFont("Courier",3)
            c.drawString(50,193,"Dias trabajados: "+str(registro[4])+" * 110 €/día = " +str(int(registro[4]) *110)+ " €")
            c.drawString(50,198,str(int(registro[4]) * 110) +" € + Antiguedad("+str(totalAntiguedad)+" €) + Gastos ("+str(totalGastos)+" + "+ str(registro[7])+" €)")
            c.drawString(50,208,"Salario Neto * 0.15")
            c.setFont("Courier",4)
            c.drawString(155,208,str(registro[11])+" €")
            c.drawString(155,198,str(registro[10])+" €")

            #totalLiquido= (totalGastos+totalAntiguedad+totalKilometros)-totalDeducciones
            c.drawString(155,217,str(registro[12])+" €")



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
        c.drawImage("Archivos/Imagenes/Firma.jpg",110,222,width=60,height=35)
        c.drawRightString(175,253,"Firma Autorizada")
        # End the Page and Start with new



        c.showPage()
        # Saving the PDF
        c.save()

        # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
        registroBD(usuario,"Se ha impreso la nómina de "+nombre.upper())

        respuesta = input(color.BLUE+"Quieres imprimir más nóminas: ? (s/n) "+color.END)
        while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres imprimir más nóminas ? (s/n) "+color.END)

    # Volver al menú anterior
    MenuRRHH(usuario)





