# coding=utf-8
# El proyecto ha sido realizado en un PC, con la versión 3.9 de Python, y al pasarlo al Mac he tenido que elegir en el "Python interpreter la versión 3.8, para que sea compatible, porque hay problemas de codificación entre versiones.

from Funciones import *

'''
Planteo elejercicio usando un diccionario donde:
-La clave es el nombre del usuario, que no se puede repetir.
-El valor, se van almacenando en una lista, donde se guarda el nombre del usuario de nuevo, el apellido, la contraseña y el email)
-Declaro el diccionario y lo inicializo con una clave y valores, para comprobar que no se repiten usuarios.
'''

DiccionarioUsuarios = {"Paco": ["Paco", "Lopez", "U'H;i|{iv", "paco@paco.com"]}

nombre = input("Nuevo usuario: ")

while (nombre != "fin"):

    for clave in DiccionarioUsuarios:
        while clave.lower() == nombre.lower():
            print("Error, usuario existente !!")
            nombre = input("Nuevo usuario: ")

    'Si el usuario NO existe, le sigo pidiendo datos: apellido, generar contraseña y email'
    apellido = input("Apellido: ")
    password = GenerarContrasenya()

    print()

    'Compruebo que el email es correcto'
    email = validarEmail()

    'Añado todos los datos al diccionario:'
    DiccionarioUsuarios[nombre.title()] = [nombre.title(), apellido.title(), password, email]

    'Envío el email'
    enviarEmail(nombre, apellido, password, email)

    print("Estos son el número de usuarios que hay en la base de datos: " + str(len(DiccionarioUsuarios)))

    'Saco los datos de los usuarios por pantalla'

    print()
    print("Estos son los datos de los usuarios existentes en la base de datos:")
    print()

    for clave in DiccionarioUsuarios:
        print(clave + ": ")
        for datos in range(0, len(DiccionarioUsuarios.get(clave))):
            if datos == 0:
                print("  Nombre: " + DiccionarioUsuarios.get(clave)[datos])
            if datos == 1:
                print("  Apellido: " + DiccionarioUsuarios.get(clave)[datos])
            if datos == 2:
                print("  Contraseña: " + DiccionarioUsuarios.get(clave)[datos])
            if datos == 3:
                print("  Email: " + DiccionarioUsuarios.get(clave)[datos])

    print()

    nombre = input("Nuevo usuario o 'fin' para salir: ")

'''

ESTO SON PRUEBAS....

print(DiccionarioUsuarios)

ListaNumeros = ["1", "2", "3", "4"]
ListaSimbolos = ["!", "@", "#", "/"]
ListaLetras = ["a", "b", "c", "d"]
ListaMayusculas = ["A", "B", "C", "D"]

ListaContrasenya= [ListaNumeros,ListaSimbolos,ListaLetras....]


print(string.punctuation)
print(string.ascii_letters)
print(string.ascii_lowercase)
print(string.ascii_uppercase)
print(string.digits)
print(string.printable)

print(password)
print(DiccionarioUsuarios)
'''
