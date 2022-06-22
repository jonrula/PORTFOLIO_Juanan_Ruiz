from Cliente import *
from Oportunidad import *
from color import *

from colorama import init, Fore
init(autoreset=True)


class Actividad():
    def __init__(self, descripcion, fecha, hora, lugar, importancia):
        self.descripcion = descripcion
        self.fecha = fecha
        self.hora = hora
        self.lugar = lugar
        self.importancia = importancia

    # Función para mostrar los resultados de cada actividad por filas y tabulado
    def mostrarInfo(self):
        print(color.BOLD+"Descripción:"+color.END + Fore.GREEN+self.descripcion.title().rjust(24, " "))
        print(color.BOLD+"Fecha:"+color.END + self.fecha.rjust(30, " "))
        print(color.BOLD+"Hora:"+color.END + self.hora.rjust(31, " "))
        print(color.BOLD+"Lugar:"+color.END + self.lugar.title().rjust(30, " "))
        print(color.BOLD+"Importancia:"+color.END + self.importancia.rjust(24, " "))
        print()

    # Función para mostrar los resultados de cada actividad en cadena y tabulado

    def mostrarInfoCadena(self):
        cadena = "\tDescripción: " + str(self.descripcion.ljust(20, " ")) + "Fecha: "+ str(self.fecha.ljust(10, " ")) +", Hora: " + str(self.hora.ljust(5, " "))+ ", Lugar: "+ str(self.lugar.ljust(20, " ")) + "Importancia:" + str(self.importancia.ljust(9, " "))
        print(cadena)

    # Función para mostrar los resultados de cada actividad en cadena y tabulado, pero de color VERDE, para diferenciar cuando añado una actividad nueva

    def mostrarInfoUltimaActividad(self):
        cadena = "\tDescripción: " + str(self.descripcion.ljust(20, " ")) + "Fecha: "+ str(self.fecha.rjust(10, " ")) +", Hora: " + str(self.hora.ljust(5, " "))+ ", Lugar: "+ str(self.lugar.ljust(20, " ")) + "Importancia:" + str(self.importancia.ljust(9, " "))
        print(Fore.GREEN+cadena)

    def __repr__(self):
        return str(self.__dict__)


def validarFecha(pregunta):
    patron = re.compile(r"^(0?[1-9]|[12][0-9]|3[01])[/](0?[1-9]|1[012])[/](19|20)\d{2}$")

    fecha = input(color.BLUE+pregunta+color.END)
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, fecha):
        print(color.RED+"Error !!, introducir fecha correcto --> 'dd/mm/aaaa'"+color.END)
        fecha = input(color.BLUE+pregunta+color.END)

    return fecha


def validarHora(pregunta):
    patron = re.compile(r"^([01]?[0-9]|2[0-3]):[0-5][0-9]$")

    hora = input(color.BLUE+pregunta+color.END)
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, hora):
        print(color.RED+"Error !!, introducir formato correcto 24h --> '00:00' "+color.END)
        hora = input(color.BLUE+pregunta+color.END)

    return hora


def tipoActividad():
    resultado = ""

    print("Elige actividad:"
          "1 Reunion"
          "2 Comida"
          "3 Llamada telefono"
          "4 salir"
          )

    opcion = int(input("Opcion: "))

    if (opcion == 1):
        resultado = "Reunión"

    return resultado


def nivelImportancia():
    listaNivel = [1, 2, 3, 4, 5]

    print(color.BLUE+"Elige nivel de importancia:\n\n"+color.END+
    
          color.BLUE+"\t1"+color.END +" Muy baja\n"+
          color.BLUE+"\t2"+color.END +" Baja\n"+
          color.BLUE+"\t3"+color.END +" Media\n"+
          color.BLUE+"\t4"+color.END +" Alta\n"+
          color.BLUE+"\t5"+color.END +" Muy alta\n\n")

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        return "Muy baja"
    if opcion == 2:
        return "Baja"
    if opcion == 3:
        return "Media"
    if opcion == 4:
        return "Alta"
    if opcion == 5:
        return "Muy Alta"


def crearActividad(listaActividadesCliente):
    descripcion = validarString("Descripción actividad: ", 30)
    fecha = validarFecha("Fecha actividad: ")
    hora = validarHora("Hora actividad: ")
    lugar = validarString("Lugar actividad: ", 30)
    importancia = nivelImportancia()

    actividad = Actividad(descripcion, fecha, hora, lugar, importancia)

    print()
    print("Actividad añadida correctamente:".upper())
    actividad.mostrarInfo()
    print()

    listaActividadesCliente.append(actividad)

    print("Esta es la lista de actividades:".upper())

    for act in listaActividadesCliente:
        act.mostrarInfo()
        print()


    return listaActividadesCliente


def borrarActividad(listaOportunidades):
    if len(listaOportunidades) > 0:
        numeroOportunidad = 0

        print()

        print(color.BLUE+"Elige la oportunidad donde quieres borrar la actividad: "+color.END)

        print()

        for oportunidad in listaOportunidades:
            numeroOportunidad = numeroOportunidad + 1
            print(color.BLUE+str(numeroOportunidad)+color.END, end=" ")
            print(oportunidad.cliente.nombre, end="  Descripción: ")
            print(oportunidad.descripcion, end=" Fase: ")
            print(oportunidad.fase, end=" Ingreso estimado: ")
            print(str(oportunidad.ingresoEstimado) + " €")

        print()

        opcionOportunidad = validarNumero(listaOportunidades)

        print()

        numeroActividad = 0

        if len(listaOportunidades[opcionOportunidad - 1].listaActividadesCliente) > 0:

            for actividad in listaOportunidades[opcionOportunidad - 1].listaActividadesCliente:
                numeroActividad += 1
                print("ACTIVIDAD: "+color.BLUE+ str(numeroActividad)+color.END)
                actividad.mostrarInfo()
                #print(actividad.mostrarInfo2())


            opcionActividad = validarNumero(listaOportunidades[opcionOportunidad - 1].listaActividadesCliente)

            nombre = listaOportunidades[opcionOportunidad - 1].cliente.nombre.upper()
            apellidos = listaOportunidades[opcionOportunidad - 1].cliente.apellidos.upper()

            print()

            print(Fore.GREEN+"Ha sido borrada la Actividad: "+str(numeroActividad)+" '"+ str(listaOportunidades[opcionOportunidad - 1].listaActividadesCliente[opcionActividad - 1].descripcion) +"', del cliente: "+str(nombre)+ " "+str(apellidos))
            " del cliente "+listaOportunidades[opcionOportunidad - 1].cliente.nombre.upper()

            del listaOportunidades[opcionOportunidad - 1].listaActividadesCliente[opcionActividad - 1]

        else:
            print(color.RED+"Error !! la oportunidad del cliente " + listaOportunidades[opcionOportunidad - 1].cliente.nombre.upper() + " " + listaOportunidades[opcionOportunidad - 1].cliente.apellidos.upper() + " no tiene actividades !!"+color.END)


    else:
        print(color.RED+"Error, no hay oportunidades !!"+color.END)
