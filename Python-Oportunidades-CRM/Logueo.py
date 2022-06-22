import string
import random
import re
import smtplib

from Cliente import *
from Oportunidad import *


class Logueo():
    def __init__(self, usuario, password):
        self.usuario = usuario
        self.password = password

    def __repr__(self):
        return str(self.__dict__)


def validarLongitud():
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
            numero = int(input(color.BLUE + "Longitud de la contraseña: " + color.END))

        except ValueError:
            print(color.RED + "Error !!, introduce un número !!" + color.END)

        if numero < 4:
            print(color.RED + "Introduce un número mayor o igual que 4" + color.END)
        elif numero > 20:
            print(color.RED + "Introduce un número menor  que 21" + color.END)
        else:
            break


    return numero


def GenerarContrasenya():
    ListaContrasenya = []

    '''
    Creo una lista vacía "ListaContrasenya = []" y le voy añadiendo cadenas de strings:
    - Strings de minúsculas y/o mayúsculas y/o símbolos y/o números, en función de lo que elija el usuario
    - Así voy conformando la "ListaContrasenya = [mayusculas, minusculas, simbolos, numeros]
    - Que quedaría asi si se escogen todas las opciones "ListaContrasenya =['abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', '!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~', '0123456789']
    '''

    print("""
    Estas son los pasos para generar la contraseña:\n
     •Longitud
     •Minúsculas (si queremos que incluya o no)
     •Mayúsculas (si queremos que incluya o no)
     •Símbolos (si queremos que incluya o no)
     •Números (si queremos que incluya o no)
     
    """)

    ''' La "opcionA" no hace falta declararla porque obligatoriamente la vamos a utilizar !!'''

    opcionB = False
    opcionC = False
    opcionD = False
    opcionE = False

    longitud = validarLongitud()

    ' Pongo en un bucle para que me elija el usuario como mínimo una opción:'

    while not opcionB and not opcionC and not opcionD and not opcionE:

        minusculas = input(color.BLUE + "Quieres minúsculas ? (S/N): " + color.END)
        while (minusculas.lower() != "s" and minusculas.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            minusculas = input(color.BLUE + "Quieres minúsculas ? (S/N): " + color.END)

        if (minusculas.lower() == "s"):
            ListaContrasenya.append(string.ascii_lowercase)
            opcionB = True

        mayusculas = input(color.BLUE + "Quieres mayúsculas ? (S/N): " + color.END)
        while (mayusculas.lower() != "s" and mayusculas.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            mayusculas = input(color.BLUE + "Quieres mayúsculas ? (S/N): " + color.END)

        if (mayusculas.lower() == "s"):
            ListaContrasenya.append(string.ascii_uppercase)
            opcionC = True

        simbolos = input(color.BLUE + "Quieres símbolos ? (S/N): " + color.END)
        while (simbolos.lower() != "s" and simbolos.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            simbolos = input(color.BLUE + "Quieres simbolos ? (S/N): " + color.END)

        if (simbolos.lower() == "s"):
            ListaContrasenya.append(string.punctuation)
            opcionD = True

        numeros = input(color.BLUE + "Quieres números ? (S/N): " + color.END)
        while (numeros.lower() != "s" and numeros.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            numeros = input(color.BLUE + "Quieres números ? (S/N): " + color.END)

        if (numeros.lower() == "s"):
            ListaContrasenya.append(string.digits)
            opcionE = True

        if not opcionB and not opcionC and not opcionD and not opcionE:
            print(color.RED + "Error !... debes elegir como mínimo una opción !!" + color.END)
            print()

    print()

    '''
        En el siguiente bucle "while", voy concatenando los carácteres hasta el límite de la longitud, que le paso en el "for".
        Después verifico que la contraseña es correcta, llamando a la función "validarContrasenya" y pasándole los parámetros de entrada que son las opciones que ha elegido el usuario´.
        Si es correcto, me retorna la contraseña, compruebo la fortaleza y luego se la añado al diccionario en el "main"
        Si NO es correcta, se vacía la contrasenya de nuevo(password = "") y vuelvo a generar otra contraseña hasta que sea correcta y pueda salir del "while"
    '''

    while True:
        password = ""

        for i in range(1, longitud + 1):
            password += random.choice(ListaContrasenya[random.randint(0, len(ListaContrasenya) - 1)])
        if validarContrasenya(password, opcionB, opcionC, opcionD, opcionE):
            return password


'''
    En siguiente función compruebo que la contraseña es correcta, recorriendo cada caracter de la contraseña con "for":
    - Tengo en cuenta que opciones ha escogido el usuario, que ya he recogido de la función anterior "generarContrasenya"
    - Compruebo que se cumplen las condiciones del usuario que sean correctas, utilizando los métodos: "islower()", "isupper", "isdigit"
    - Para comprobar si tiene simbolos, comparo con dos "for la cadena de la contraseña y la cadena de símbolos.
    - Si se cumple las condiciones le aplico una variable booleana que sea correcta --> True
    - Aprovecho y compruebo el tipo de fortaleza que tiene la contraseña, en función de las opciones elegidas,sacando mensaje por pantalla.
    -
 
'''


def validarContrasenya(password, opcionB, opcionC, opcionD, opcionE):
    contrasenyaValida = False
    mayusculas = False
    minusculas = False
    simbolos = False
    numeros = False

    '''
    Aquí recorro la contraseña para validar si cumple las condiciones elegidas por el usuario
    '''

    for c in password:

        if opcionB and c.islower():
            minusculas = True
        if opcionC and c.isupper():
            mayusculas = True
        if opcionD:
            for b in string.punctuation:
                if c == b:
                    simbolos = True
        if opcionE and c.isdigit():
            numeros = True

    '''
    - Saco todas las posibilidades con "if" y las opciones que ha elegido el usuario y las valido asignándole una variable True
    - Así pueda salir del bucle cuando se llame a esta función en "generarContrasenya"
    - Si no saca un mensaje de error, que la contraseña no es correcta
    - Es muy importante meter todos los "if" que validan correctamente, porque sino se va al "else" y no sale del "while" de "generarContrasenya"
    
    - La mejor forma de comprobar que una contraseña es correcta es en el caso más crítico:
        La longitud de la contraseña es 4
        Eligiendo todas las opciones: mayúsculas, minúsculas, símbolos y números
        De esta forma cada caracter tiene que cumplir una condición, no se pueden repetir, esmás dificil generar aleatoriamente todas las condiciones con tan poco margen
        En cuanto va creciendo la longitud de la contraseña, aunque se elijan todas las opciones, es más fácil generar la contraseña.
        
    - A la hora de comprobar la validez de la contraseña, le tengo que obligar que entre en los  "if" correctos
      obligándole a que verifique solo entre las opciones que ha elegido el usuario y las que no,
      si solo pongo las que ha elegido, después de entrar al primer "if" y si no valida la contraseña salta al siguiente "if":
      Por ejemplo:
       - Si en el primer "If", pongo que ha elegido 4 opciones (minúsculas, mayúsculas, símbolos y números)
        y en el siguiente "if" pongo que ha elegido 2 opciones (minúsculas y mayúsculas)
        Primero comprueba el primer "if" y si no valida la contraseña, salta al segundo "if", porque cumple también la condición de que son minusculas y mayusculas
        En el segundo "if" podría validarme una contraseña correcta y no tendría sentido si se han elegido las 4 opciones.
      - Para solucionarlo en el segundo "if" hay que decirle que "simbolos" y numeros" son falsos y le obligo a que no entre..
        
             if (opcionB and opcionC):  --> NO
             if (opcionB and opcionC and not opcionD and not opcion): --> SI
             
     - Aprovecho y si la contraseña es validada, compruebo su nivel de Fortaleza:
        - Si el usuario elige 4 ó 3 opciones --> Fortaleza Fuerte
        - Si el usuario elige 2 opciones  --> Fortaleza Media
        - Si el usuario elige 1 opción --> Fortaleza débil
       
    '''

    # Compruebo si el usuario escoge las  opciones:

    if opcionB and opcionC and opcionD and opcionE:
        if minusculas and mayusculas and simbolos and numeros:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Fuerte".upper() + color.END)
        else:
            print(password + color.RED + " --> Contraseña no validada !!" + color.END)

    # Compruebo si el usuario escoge 3 opciones:

    if opcionB and opcionC and opcionD and not opcionE:
        if minusculas and mayusculas and simbolos:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Fuerte".upper() + color.END)
        else:
            print(password + color.RED + " --> Contraseña no validada !!" + color.END)

    if opcionB and opcionC and opcionE and not opcionD:
        if minusculas and mayusculas and numeros:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Fuerte".upper() + color.END)
        else:
            print(password + color.RED + " --> Contraseña no validada !!" + color.END)

    if opcionB and opcionD and opcionE and not opcionC:
        if minusculas and simbolos and numeros:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Fuerte".upper() + color.END)
        else:
            print(password + color.RED + " --> Contraseña no validada !!" + color.END)

    if opcionC and opcionD and opcionE and not opcionB:
        if mayusculas and simbolos and numeros:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Fuerte".upper() + color.END)
        else:
            print(password + color.RED + " --> Contraseña no validada !!" + color.END)

    # Compruebo si el usuario escoge 2 opciones:

    if opcionB and opcionC and not opcionD and not opcionE:
        if minusculas and mayusculas:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Media".upper() + color.END)
        else:
            print(password + color.RED + " --> Contraseña no validada !!" + color.END)

    if opcionB and opcionD and not opcionC and not opcionE:
        if minusculas and simbolos:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Media".upper() + color.END)
        else:
            print(password + color.RED + " --> Contraseña no validada !!" + color.END)

    if opcionB and opcionE and not opcionC and not opcionD:
        if minusculas and numeros:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Media".upper() + color.END)
        else:
            print(password + color.RED+ " --> Contraseña no validada !!"+color.END)

    if opcionC and opcionD and not opcionB and not opcionE:
        if mayusculas and simbolos:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Media".upper() + color.END)
        else:
            print(password + color.RED+ " --> Contraseña no validada !!"+color.END)

    if opcionC and opcionE and not opcionB and not opcionD:
        if mayusculas and numeros:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Media".upper() + color.END)
        else:
            print(password +color.RED+ " --> Contraseña no validada !!"+color.END)

    if opcionD and opcionE and not opcionB and not opcionC:
        if simbolos and numeros:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Media".upper() + color.END)
        else:
            print(password + color.RED+ " --> Contraseña no validada !!"+color.END)

    # Compruebo si el usuario escoge 1 opcion:

    if opcionB and not opcionC and not opcionD and not opcionE:
        if minusculas:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Débil".upper() + color.END)
        else:
            print(password + color.RED+ " --> Contraseña no validada !!"+color.END)

    if opcionC and not opcionB and not opcionD and not opcionE:
        if mayusculas:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Débil".upper() + color.END)
        else:
            print(password + color.RED+ " --> Contraseña no validada !!"+color.END)

    if opcionD and not opcionB and not opcionC and not opcionE:
        if simbolos:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Débil".upper() + color.END)
        else:
            print(password + color.RED+ " --> Contraseña no validada !!"+color.END)

    if opcionE and not opcionB and not opcionC and not opcionD:
        if numeros:
            contrasenyaValida = True
            print(password + color.BLUE + " --> Fortaleza Débil".upper() + color.END)
        else:
            print(password + color.RED+ " --> Contraseña no validada !!"+color.END)

    return contrasenyaValida
