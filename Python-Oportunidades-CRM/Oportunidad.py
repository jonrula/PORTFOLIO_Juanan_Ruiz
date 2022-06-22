
from Actividad import *
from Cliente import *
from color import *

from colorama import init, Fore
init(autoreset=True)

'''
 Tengo que partir siempre de una oportunidad que tenga una "listaActividaesCliente" VACIA definida en el constructor --> "self.listaActividadesCliente = []"  porque sino no puedo acceder a ningún elemento de la lista para borrar o añadir!!
 Aunque inicialize una oportunidad con una actividad ya asignada siempre me va a poner la listaActividades vacía, porque se lo he dicho en el constructor, solo funciona bien en TIEMPO de EJECUCION, añadiendo quitando actividades, pero las tengo que crear desde 0,--lista de actividades vacía !!
'''

class Oportunidad():


    def __init__(self, cliente, descripcion, fechaOportunidad, ingresoEstimado, comercial, fase, listaActividadesCliente):
        self.cliente = cliente
        self.descripcion = descripcion
        self.fechaOportunidad = fechaOportunidad
        self.ingresoEstimado = ingresoEstimado
        self.comercial = comercial
        self.fase = fase
        self.listaActividadesCliente = listaActividadesCliente  # Hay que decirle que es una lista, porque sino no lo reconoce, NO puede hacer el "append" en añadir una actividad a la lista de actividades a un oportunidad ya creada
                                        # Pero no me coge el valor con que he inicializado una oportunidad en el main:

    #Esta función para sacar los datos de la oportunidad por filas bien tabulado y las actividades que tenga asociado'

    def mostrarInfo(self):

        print(color.BOLD+"Cliente: "+color.END + (Fore.GREEN+self.cliente.nombre.title() +" "+self.cliente.apellidos.title()).rjust(36, " "))
        print(color.BOLD+"Descripcion: "+color.END+ self.descripcion.rjust(27, " "))
        print(color.BOLD+"Fecha Oportunidad: "+color.END+ self.fechaOportunidad.rjust(21, " "))
        print(color.BOLD+"Ingreso estimado: "+color.END + str(self.ingresoEstimado).rjust(20, " ")+" €")
        print(color.BOLD+"Comercial: "+color.END+ self.comercial.upper().rjust(29," "))
        print(color.BOLD+"Fase: "+color.END+ self.fase.upper().rjust(34, " "))
        print(color.BOLD+"Actividades: "+color.END)
        for actividad in self.listaActividadesCliente:
            actividad.mostrarInfoCadena()

    # Esta función es solo para diferenciar una ultima actividad añadida cambiándola el color a verde (llama a mostrarInfoUltimaActividad()

    def mostrarInfoAnyadirActividad(self):

        print(color.BOLD+"Cliente: "+color.END + (Fore.GREEN+self.cliente.nombre.title() +" "+self.cliente.apellidos.title()).rjust(36, " "))
        print(color.BOLD+"Descripcion: "+color.END+ self.descripcion.rjust(27, " "))
        print(color.BOLD+"Fecha Oportunidad: "+color.END+ self.fechaOportunidad.rjust(21, " "))
        print(color.BOLD+"Ingreso estimado: "+color.END + str(self.ingresoEstimado).rjust(20, " ")+" €")
        print(color.BOLD+"Comercial: "+color.END+ self.comercial.upper().rjust(29," "))
        print(color.BOLD+"Fase: "+color.END+ self.fase.upper().rjust(34, " "))
        print(color.BOLD+"Actividades: "+color.END)
        for actividad in range(0,len(self.listaActividadesCliente)-1):
            self.listaActividadesCliente[actividad].mostrarInfoCadena()

        for actividad in range(len(self.listaActividadesCliente)-1,len(self.listaActividadesCliente)):
             self.listaActividadesCliente[actividad].mostrarInfoUltimaActividad()

    # Función solo para diferenciar una fase 'Ganada' que la cambia de color a verde

    def mostrarInfoFaseCambiada(self):

        print(color.BOLD+"Cliente: "+color.END + (Fore.GREEN+self.cliente.nombre.title() +" "+self.cliente.apellidos.title()).rjust(36, " "))
        print(color.BOLD+"Descripcion: "+color.END+ self.descripcion.rjust(27, " "))
        print(color.BOLD+"Fecha Oportunidad: "+color.END+ self.fechaOportunidad.rjust(21, " "))
        print(color.BOLD+"Ingreso estimado: "+color.END + str(self.ingresoEstimado).rjust(20, " ")+" €")
        print(color.BOLD+"Comercial: "+color.END+ self.comercial.upper().rjust(29," "))
        print(color.BOLD+"Fase: "+color.END+ Fore.GREEN+self.fase.upper().rjust(34, " "))
        print(color.BOLD+"Actividades: "+color.END)
        for actividad in self.listaActividadesCliente:
            actividad.mostrarInfoCadena()

    # Función solo para diferenciar una fase 'Perdoda' que la cambia de color a rojo

    def mostrarInfoFaseCambiadaPerdida(self):

        print(color.BOLD+"Cliente: "+color.END + (Fore.GREEN+self.cliente.nombre.title() +" "+self.cliente.apellidos.title()).rjust(36, " "))
        print(color.BOLD+"Descripcion: "+color.END+ self.descripcion.rjust(27, " "))
        print(color.BOLD+"Fecha Oportunidad: "+color.END+ self.fechaOportunidad.rjust(21, " "))
        print(color.BOLD+"Ingreso estimado: "+color.END + str(self.ingresoEstimado).rjust(20, " ")+" €")
        print(color.BOLD+"Comercial: "+color.END+ self.comercial.upper().rjust(29," "))
        print(color.BOLD+"Fase: "+color.END+ color.RED+self.fase.upper().rjust(34, " ")+color.END)
        print(color.BOLD+"Actividades: "+color.END)
        for actividad in self.listaActividadesCliente:
            actividad.mostrarInfoCadena()

    def __repr__(self):
        return str(self.__dict__)


def elegirFase():
    opciones = [1, 2, 3, 4]

    print(color.BLUE+"Elige la fase de la oportunidad:\n\n"+color.END+
    
                  color.BLUE+"\t1"+color.END+" Inicio\n"+
                  color.BLUE+"\t2"+color.END+" En proceso\n"+
                  color.BLUE+"\t3"+color.END+" Ganada\n"+
                  color.BLUE+"\t4"+color.END+" Perdida\n")
                  


    opcion = validarNumero(opciones)

    if opcion == 1:
        return "Inicio"
    if opcion == 2:
        return "En proceso"
    if opcion == 3:
        return "Ganada"
    if opcion == 4:
        return "Perdida"


def validarFecha(pregunta):
    patron = re.compile(r"^(0?[1-9]|[12][0-9]|3[01])[/](0?[1-9]|1[012])[/](19|20)\d{2}$")

    fecha = input(color.BLUE+pregunta+color.END)
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, fecha):
        print(color.RED+"Error !!, introdude fecha correcta --> 'dd/mm/aaaa' "+color.END)
        fecha = input(color.BLUE+pregunta+color.END)

    return fecha


def crearOportunidad(comercial,listaOportunidades, listaClientes):
    if len(listaClientes) > 0:

        numero = 0

        print(color.BLUE+"Elige un cliente: \n"+color.END)

        for cliente in listaClientes:
            numero = numero + 1
            print(color.BLUE+str(numero)+color.END, end=" ")
            print(cliente.nombre)

        print()

        opcion = validarNumero(listaClientes)
        cliente = listaClientes[opcion - 1]

        descripcion = validarString("Descripción Oportunidad: ",30)
        fechaOportunidad = validarFecha("Fecha oportunidad: ")
        ingresoEstimado = validarIngreso()
        fase = elegirFase()

        listaActividadesCliente = []

        'Creo el objeto "op" y le añado la lista de actividades vacía, si quiere añadir más actividades le pregunto le pregunto y se le añado a la listaActividades.cliente'
        'Luego puedo añadirle más actividades en la opción "c"'

        op = Oportunidad(cliente, descripcion, fechaOportunidad, ingresoEstimado,comercial,fase,listaActividadesCliente)


        'Ahora le pregunto si quiere añadir actividades y se las sumo a la lista de actividades que está vacía'
        respuesta = input(color.BLUE+"Quieres añadir actividades ahora a la oportunidad del cliente " + cliente.nombre + " " + cliente.apellidos + " ? (s/n): "+color.END)

        while respuesta != "n":
            if respuesta != "s" and respuesta != "n":
                print(color.RED+"Error !!, tienes que elegir 's' o 'n' "+color.END)

            if respuesta.lower() == "s":
                'Creo un nuevo objeto de tipo actividad y lo voy añadiendo a la lista "listaActividadesCliente", dentro de la función "crearActividad(listaActividadesCliente)" hago el "append"'

                actividadNueva = crearActividad(listaActividadesCliente)
                op = Oportunidad(cliente, descripcion, fechaOportunidad, ingresoEstimado, comercial,fase, listaActividadesCliente)

                print(Fore.GREEN+"Se ha añadido correctamente la oportunidad al/la cliente "  + cliente.nombre + "  " + cliente.apellidos)
                print()

            respuesta = input(color.BLUE+"Quieres añadir más actividades ? (s/n): "+color.END)

        print()

        'Cuando salga del "while" (que ya no quiero añadir más actividades) añado la nueva oportunidad a la listaOportunidades, para que luego pueda listarlas y añadir más actividades por si me interesa'

        listaOportunidades.append(op)
        print(Fore.GREEN+"Esta es la oportunidad creada con todas las actividades añadidas:\n".upper())
        op.mostrarInfo()

        print()

    else:
        print(color.RED+"No hay clientes !!, vete al punto 'a'"+color.END)

def anyadirActividadOportunidad(listaOportunidades):
    if len(listaOportunidades) > 0:

        respuesta = ""
        while respuesta != "fin":

            descripcion = validarString("Descripción actividad: ",30)
            fecha = validarFecha("Fecha actividad: ")
            hora = validarHora("Hora actividad: ")
            lugar = validarString("Lugar actividad: ",30)
            importancia = nivelImportancia()

            actividad = Actividad(descripcion, fecha, hora, lugar, importancia)

            print()

            print(Fore.GREEN+"nueva actividad añadida:\n".upper())
            actividad.mostrarInfo()

            numero = 0
            print(color.BLUE+"Elige una oportunidad: ")

            for oportunidad in listaOportunidades:
                numero = numero + 1
                print(color.BLUE+str(numero)+color.END, end=" ")
                print(oportunidad.cliente.nombre, end="  Descripción: ")
                print(oportunidad.descripcion, end=" Fase: ")
                print(oportunidad.fase, end=" Ingreso estimado: ")
                print(str(oportunidad.ingresoEstimado) + " €")

            print()

            opcion = validarNumero(listaOportunidades)
            oportunidad = listaOportunidades[opcion - 1]


            print()

            oportunidad.listaActividadesCliente.append(actividad)

            print(Fore.GREEN+"Asi es como queda la oportunidad con la nueva actividad añadida:\n".upper())
            oportunidad.mostrarInfoAnyadirActividad()

            print()

            respuesta = input(color.BLUE+"Quieres añadir más actividades: ? (<intro> o 'fin') "+color.END)


    else:

        print(color.RED+"No hay ninguna oportunidad donde elegir, vete al punto 'd'"+color.END)

def borrarOportunidad(listaOportunidades):
    if len(listaOportunidades)>0:

        numero = 0

        for oportunidad in listaOportunidades:
            numero = numero + 1
            print(color.BLUE+str(numero)+color.END, end=" ")
            print(oportunidad.cliente.nombre, end="  Descripción: ")
            print(oportunidad.descripcion, end=" Fase: ")
            print(oportunidad.fase, end=" Ingreso estimado: ")
            print(str(oportunidad.ingresoEstimado) + " €")

        print()

        opcion = validarNumero(listaOportunidades)


        respuesta= input(color.BLUE+"Estas seguro que quieres borrar la oportunidad del cliente:? (s/n): "+color.END)
        while respuesta != "s" and respuesta!= "n":
            if respuesta != "s" and respuesta!= "n":
                print(color.RED+"Error !! elige 's' o 'n' "+color.END)
                respuesta= input(color.BLUE+"Estas seguro que quieres borrar la oportunidad del cliente: ? (s/n): "+color.END)

        print()

        print(Fore.GREEN+"La oportunidad perteneciente a el/la cliente: "+str(listaOportunidades[opcion - 1].cliente.nombre.upper())+ " "+str(listaOportunidades[opcion - 1].cliente.apellidos.upper())+ " con un ingreso estimado de: "+str(listaOportunidades[opcion - 1].ingresoEstimado)+ " € HA SIDO BORRADA JUNTO AT ODAS SUS ACTIVIDADES !!")

        del listaOportunidades[opcion - 1]



    else:
        print(color.RED+"Error !!, no hay oportunidades !! , vete al punto 'b' "+color.END)


def cambioFase(listaClientes, listaOportunidades):
    if len(listaOportunidades) > 0:
        numero = 0
        print(color.BLUE+"Elige un Cliente: "+color.END)

        for oportunidad in listaOportunidades:
            numero = numero + 1
            print("\t" + color.BLUE+str(numero)+color.END+ "  " + str(oportunidad.cliente.nombre) + "  Descripción: " + str(
                oportunidad.descripcion).title() + "  Ingreso: " + str(oportunidad.ingresoEstimado) + "  Fase: " + str(oportunidad.fase).upper())

        print()

        opcion = validarNumero(listaOportunidades)
        numero = int(opcion - 1)
        listaOportunidades[numero].fase = elegirFase()
        print()


        if (listaOportunidades[numero].fase == "Perdida"):

            listaOportunidades[numero].mostrarInfoFaseCambiadaPerdida()

        else:
            listaOportunidades[numero].mostrarInfoFaseCambiada()



    else:
        print(color.RED+"No hay oportunidades !!"+color.END)


def validarIngreso():
    correcto = True
    numero = 0

    while (correcto):
        try:
            numero = float(input(color.BLUE+"Ingreso estimado: "+color.END).strip())

        except ValueError:
            print(color.RED+"Error !!, introduce un número !!"+color.END)

        else:
            break

    return numero
