from Email import *
from colorama import init, Fore
init(autoreset=True)

class Cliente():

    def __init__(self,nombre, apellidos,dni,email,telefono, industria):
            self.nombre = nombre
            self.apellidos = apellidos
            self.dni = dni
            self.email = email
            self.telefono= telefono
            self.pais = "España"
            self.provincia = "Araba"
            self.industria = industria

    # def __init2__(self,nombre, apellidos):
    #         self.nombre = nombre
    #         self.apellidos = apellidos

    def mostrarInfo(self):

        print(color.BOLD+"Nombre:"+color.END +Fore.GREEN+ self.nombre.title().rjust(23," "))
        print(color.BOLD+"Apellidos:"+color.END + Fore.GREEN+ self.apellidos.title().rjust(20," "))
        print(color.BOLD+"DNI:"+color.END + self.dni.rjust(26," "))
        print(color.BOLD+"Email:"+color.END +self.email.rjust(24," "))
        print(color.BOLD+"Teléfono:"+color.END + self.telefono.rjust(21," "))
        print(color.BOLD+"País:"+color.END + self.pais.rjust(25," "))
        print(color.BOLD+"Provincia:"+color.END+ self.provincia.rjust(20," "))
        print(color.BOLD+"Industria:"+color.END + self.industria.rjust(20," "))


    def __repr__(self):
        return str(self.__dict__)

def validarString(pregunta, longitud):
    dato = input(color.BLUE+pregunta+color.END)

    while len(dato) == 0 or dato.isspace() or len(dato) > longitud:
        if len(dato) == 0 or dato.isspace():
            print(color.RED+"Error, introduce un nombre !!"+color.END)
        if len(dato) > longitud:
            print(color.RED+"Error !!, el nommbre no puede tener más de " +str(longitud) + " carácteres !!"+color.END)
        dato = input(pregunta)

    return dato.strip()

def crearCliente(listaClientes):



    # for cliente in listaClientes:
    #     #while cliente.nombre == nombre and cliente.apellidos == apellidos and cliente.dni:
    #     while cliente.nombre == nombre and cliente.apellidos == apellidos and cliente.dni:
    #         print("Error !! ya existe el cliente "+ nombre+ " "+ apellidos)
    #         crearCliente(listaClientes)

    dni = validarDNI()

    while not DNIesCorrecto(dni):
        if not DNIesCorrecto(dni):
            print(color.RED+"DNI incorrecto"+color.END)
            dni = validarDNI()

        print("DNI correcto !!")

    for cliente in listaClientes:
        #while cliente.nombre == nombre and cliente.apellidos == apellidos and cliente.dni:
        while cliente.dni == dni:
            print(color.RED+"Error !! ya existe un cliente con el mismo DNI: "+ dni + " --> "+cliente.nombre.title()+" "+ cliente.apellidos.title()+color.END)
            dni = validarDNI()
            while not DNIesCorrecto(dni):
                if not DNIesCorrecto(dni):
                    print(color.RED+"DNI incorrecto"+color.END)
                    dni = validarDNI()
                print("DNI correcto !!")

    nombre = validarString("Cliente: ",10)
    apellidos = validarString("Apellidos: ",10)


    email= validarEmail()
    industria = elegirIndustria(listaClientes)
    telefono = validarTelefono()

    clienteNuevo = Cliente(nombre,apellidos,dni,email,telefono,industria)
    print(Fore.GREEN+"El/la  cliente "+nombre.upper()+" "+apellidos.upper()+" ha sido creado/a correctamente.\n")

    print("Estos son todos los datos del nuevo cliente:\n".upper())
    clienteNuevo.mostrarInfo()

    listaClientes.append(clienteNuevo)

    print()
    print("Estos son todos los clientes que hay:\n".upper())

    for cliente in listaClientes:
        cliente.mostrarInfo()
        print()

def validarDNI():

    patron = re.compile(r"^\d{8}[ -]?[a-zA-Z]$")
    #(([X-Z]{1})([-]?)(\d{7})([-]?)([A-Z]{1}))|((\d{8})([-]?)([A-Z]{1}))
    #patron = re.compile(r"^\d{8}[ -]?[a-zA-Z]$")

    dni = input(color.BLUE+"DNI: "+color.END)
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, dni):
        print(color.RED+"Error !! introduce 8 dígitos más una letra sin separaciones !!"+color.END)
        dni = input(color.BLUE+"DNI: "+color.END)

    return dni

def DNIesCorrecto(dni):
    tabla = "TRWAGMYFPDXBNJZSQVHLCKE"
    dig_ext = "XYZ"
    reemp_dig_ext = {'X':'0', 'Y':'1', 'Z':'2'}
    numeros = "1234567890"

    dni = dni.upper()
    if len(dni) == 9:
        dig_control = dni[8]
        dni = dni[:8]
        if dni[0] in dig_ext:
            dni = dni.replace(dni[0], reemp_dig_ext[dni[0]])

        return len(dni) == len([n for n in dni if n in numeros]) \
            and tabla[int(dni)%23] == dig_control

    else:

        return False



def elegirIndustria(listaClientes):
    numero= 0
    listaIndustrias = ["Electronica","Automoción", "Informática","Educación","Alimentación","Comercio","Construcción","Transporte","Textil","Hostelería","Salud","Otros"]

    print(color.BLUE+"Elige una industria: \n"+color.END)

    for industria in listaIndustrias:
        numero = numero +1
        print("\t "+color.BLUE+str(numero)+color.END,end=" ")
        print(industria)

    print()

    opcion = validarNumero(listaIndustrias)


    if opcion == 1:
        return "Electrónica"
    if opcion == 2:
        return "Automocion"
    if opcion == 3:
        return "Informática"
    if opcion == 4:
        return "Educación"
    if opcion == 5:
        return "Alimentación"
    if opcion == 6:
        return "Comercio"
    if opcion == 7:
        return "Construcción"
    if opcion == 8:
        return "Transporte"
    if opcion == 9:
        return "Textil"
    if opcion == 10:
        return "Hostelería"
    if opcion == 11:
        return "Salud"
    if opcion == 12:
        otros = input(color.BLUE+"Introduce nuevo sector de Industria: "+color.END)
        listaIndustrias.append(otros)
        print(listaIndustrias)
        return otros

    while not listaIndustrias[opcion-1]:
        crearCliente(listaClientes)

def validarTelefono():

    patron = re.compile(r"^\d{3}-?\d{3}-?\d{3}$")

    '''
    Esa expresión regular comprobará:
    - Que la dirección de correo es una secuencia de caracteres de longitud superior a 1 
    - Sin espacios separados por espacios, seguida de una "@"  
    - Seguida de dos secuencias de caracteres de espacios no espacios de 2 o más caracteres separados por a "." 
    '''

    telefono = input(color.BLUE+"Teléfono: "+color.END)
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, telefono):
        print(color.RED+"Error !!, introduce un teléfono correcto --> '000-000-000' ó '00000000' ó '000-000000' "+color.END )
        telefono = input(color.BLUE+"Teléfono: "+color.END)

    print()

    return telefono

def validarNumero(lista):
    correcto = True
    numero = 0

    '''
    Valido el número que recojo del usuario, que no puede ser cualquier cáracter, tiene que ser un número
    y ese número tiene que ser mayor o igual que 4,  y menor de 21, porque si el usuario escoge un número muy grande
    La decisión de elegir un a longitud mínima de 4 es por si elige el usuario las 4 opciones:
    mayúsculas, minúsculas, símbolos y números --> necesita 4 carácteres para formar la contraseña
    '''
    while (correcto):
        try:
            numero = int(input(color.BLUE+"Opción: "+color.END))

        except ValueError:
            print(color.RED+"Error !!, introduce un número entre 1 y " +str(len(lista))+ "!!"+color.END)

        if numero <1 or numero > len(lista):
            print(color.RED+"Error !!"+color.END)
        if numero >0 and numero <= len(lista):
            break

    return numero

