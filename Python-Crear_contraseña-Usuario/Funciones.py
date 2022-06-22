# coding=utf-8
# El proyecto ha sido realizado en un PC, con la versión 3.9 de Python, y al pasarlo al Mac he tenido que elegir en el "Python interpreter la versión 3.8, para que sea compatible, porque hay problemas de codificación entre versiones.

import string
import random
import re
import smtplib


'''
Importo las clases:
 -"string" para acceder a las cadenas de carácteres de mayúsculas, minúsculas, símbolos y números
 -"random" para acceder aleatoriamente a los carácteres de la lista "ListaContrasenya = []" y escoger un carácter para crear la contraseña
 -"re" para trabajar con expresiones regulares en Python y acceder a sus métodos. 
'''


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
            numero = int(input("Longitud de la contraseña: "))

        except ValueError:
            print("Error !!, introduce un número !!")

        if numero < 4:
            print("Introduce un número mayor o igual que 4")
        elif numero > 20:
            print("Introduce un número menor  que 21")
        else:
            break

    return numero

def validarEmail():

    patron = re.compile(r"^\S{1,}@\S{2,}\.\S{2,}$")

    '''
    Esa expresión regular comprobará:
    - Que la dirección de correo es una secuencia de caracteres de longitud superior a 1 
    - Sin espacios separados por espacios, seguida de una "@"  
    - Seguida de dos secuencias de caracteres de espacios no espacios de 2 o más caracteres separados por a "." 
    '''

    email = input("Email: ")
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, email):
        print("Error !!")
        email = input("Email: ")

    print("Email correcto !!")
    print()

    return email


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

        minusculas = input("Quieres minúsculas ? (S/N): ")
        while (minusculas.lower() != "s" and minusculas.lower() != "n"):
            print("Error !!")
            minusculas = input("Quieres minúsculas ? (S/N): ")

        if (minusculas.lower() == "s"):
            ListaContrasenya.append(string.ascii_lowercase)
            opcionB = True


        mayusculas = input("Quieres mayúsculas ? (S/N): ")
        while (mayusculas.lower() != "s" and mayusculas.lower() != "n"):
            print("Error !!")
            mayusculas = input("Quieres mayúsculas ? (S/N): ")

        if (mayusculas.lower() == "s"):
            ListaContrasenya.append(string.ascii_uppercase)
            opcionC = True


        simbolos = input("Quieres símbolos ? (S/N): ")
        while (simbolos.lower() != "s" and simbolos.lower() != "n"):
            print("Error !!")
            simbolos = input("Quieres simbolos ? (S/N): ")

        if (simbolos.lower() == "s"):
            ListaContrasenya.append(string.punctuation)
            opcionD = True


        numeros = input("Quieres números ? (S/N): ")
        while (numeros.lower() != "s" and numeros.lower() != "n"):
            print("Error !!")
            numeros = input("Quieres números ? (S/N): ")

        if (numeros.lower() == "s"):
            ListaContrasenya.append(string.digits)
            opcionE = True


        if not opcionB and not opcionC and not opcionD and not opcionE:
            print("Error !... debes elegir como mínimo una opción !!")
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

    #Compruebo si el usuario escoge las  opciones:

    if opcionB and opcionC and opcionD and opcionE:
            if minusculas and mayusculas and simbolos and numeros:
                contrasenyaValida = True
                print(password + " --> Fortaleza Fuerte")
            else:
                print(password + " --> Contraseña no validada !!")

    #Compruebo si el usuario escoge 3 opciones:

    if opcionB and opcionC and opcionD and not opcionE:
            if minusculas and mayusculas and simbolos:
                contrasenyaValida = True
                print(password + " --> Fortaleza Fuerte")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionB and opcionC and opcionE and not opcionD:
            if minusculas and mayusculas and numeros:
                contrasenyaValida = True
                print(password + " --> Fortaleza Fuerte")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionB and opcionD and opcionE and not opcionC:
            if minusculas and simbolos and numeros:
                contrasenyaValida = True
                print(password + " --> Fortaleza Fuerte")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionC and opcionD and opcionE and not opcionB:
            if mayusculas and simbolos and numeros:
                contrasenyaValida = True
                print(password + " --> Fortaleza Fuerte")
            else:
                print(password + " --> Contraseña no validada !!")

    #Compruebo si el usuario escoge 2 opciones:

    if opcionB and opcionC and not opcionD and not opcionE:
            if minusculas and mayusculas:
                contrasenyaValida = True
                print(password + " --> Fortaleza Media")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionB and opcionD and not opcionC and not opcionE:
            if minusculas and simbolos:
                contrasenyaValida = True
                print(password + " --> Fortaleza Media")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionB and opcionE and not opcionC and not opcionD:
            if minusculas and numeros:
                contrasenyaValida = True
                print(password + " --> Fortaleza Media")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionC and opcionD and not opcionB and not opcionE:
            if mayusculas and simbolos:
                contrasenyaValida = True
                print(password + " --> Fortaleza Media")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionC and opcionE and not opcionB and not opcionD:
            if mayusculas and numeros:
                contrasenyaValida = True
                print(password + " --> Fortaleza Media")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionD and opcionE and not opcionB and not opcionC:
            if simbolos and numeros:
                contrasenyaValida = True
                print(password + " --> Fortaleza Media")
            else:
                print(password + " --> Contraseña no validada !!")

    #Compruebo si el usuario escoge 1 opcion:

    if opcionB and not opcionC and not opcionD and not opcionE:
            if minusculas:
                contrasenyaValida = True
                print(password + " --> Fortaleza Débil")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionC and not opcionB and not opcionD and not opcionE:
            if mayusculas:
                contrasenyaValida = True
                print(password + " --> Fortaleza Débil")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionD and not opcionB and not opcionC and not opcionE:
            if simbolos:
                contrasenyaValida = True
                print(password + " --> Fortaleza Débil")
            else:
                print(password + " --> Contraseña no validada !!")

    if opcionE and not opcionB and not opcionC and not opcionD:
            if numeros:
                contrasenyaValida = True
                print(password + " --> Fortaleza Débil")
            else:
                print(password + " --> Contraseña no validada !!")


    return contrasenyaValida

'''
La siguiente función es para enviar el email, que anteriormente ha sido comprobado si es correcto...
He estado mirando varios códigos por internet y el único que me funciona es el siguiente: 

https://code.tutsplus.com/es/tutorials/sending-emails-in-python-with-smtp--cms-29975  (Gracias Ekaitz)

Lo más importante es que si utilizo "gmail" como correo del remitente, hay que desactivar la seguridad para envíar mensajes desde aplicaciones de terceros, si no dá error...
He utilizado el correo de egibide que es de gmail, como remitente, para desactivar la seguridad de envío de aplicaciones de terceros.
He probado como remitente con una cuenta personal de gmail, pero como tengo contraseña de doble factor, no me deja desactivar la seguridad de envío de aplicaciones de terceros.
Puedes introducir el email que quieras como destinatario... a mi me va bien metiendo el email personal.
'''

def enviarEmail(nombre,apellido,password,email):

    # Importar librerías

    from email.mime.multipart import MIMEMultipart
    from email.mime.text import MIMEText
    import smtplib

    # Crear una instancia del objeto msg
    msg = MIMEMultipart()


    message = """Hola Gmail, soy Python !! 
    Estos son los datos del nuevo usuario %s: :
        • Nombre: %s 
        • Apellidos: %s
        • Contraseña: %s
        • Email: %s
        """ %(nombre.title(),nombre.title(),apellido.title(),password,email)

    # Variables del mensaje:
    password = "RJCkus96"
    msg['From'] = "juanantonio.ruiz@ikasle.egibide.org"
    msg['To'] = email
    msg['Subject'] = "Mensaje enviado desde Python"

    try:
        # Cuerpo del mensaje
        msg.attach(MIMEText(message, 'plain'))

        # Crear el servidor
        server = smtplib.SMTP('smtp.gmail.com: 587')

        server.starttls()

        # Credenciales
        server.login(msg['From'], password)

        # Envío del mensaje al servidor
        server.sendmail(msg['From'], msg['To'], msg.as_string())

        server.quit()

        print ("Email enviado correctamente de: '%s' a: '%s'" % ( msg['From'],msg['To']))

        print()

    except:
        print("ERROR !! ... el mensaje no pudo enviarse. Compruebe que sendmail se encuentra instalado en su sistema.")
        print("NOTA: Google no permitirá el inicio de sesión a través de 'smtplib' porque ha marcado este tipo de inicio de sesión como 'menos seguro'.")
        print("Para solucionar este problema, vaya a https://www.google.com/settings/security/lesssecureapps mientras está conectado a su cuenta de Google y a 'Permitir aplicaciones menos seguras'. ")
        print()





'''

LAS SIGUIENTES FUNCIONES NO LAS HE USADO, PERO LAS HE GUARDADO PARA UN FUTURO, POR SI ME VALEN...

    La función "GenerarContrasenya2()" está planteada para utilizar un menú, pero para este ejercicio no vale, 
    pues el usuario me puede elegir varias opciones y le tendría que obligar a que solo utilize cada opción como máximo una vez.
    Además la longitud de la contraseña tiene que ser obligatoria y no una opción.
'''


def GenerarContrasenya2():
    ListaContrasenya = []
    longitud = 0

    print("""
    Elige una opcion para generar la contraseña:\n
    a.Longitud (int)
    b.Minúsculas (si queremos que incluya o no)
    c.Mayúsculas (si queremos que incluya o no)
    d.Símbolos (si queremos que incluya o no)
    e.Números (si queremos que incluya o no)
    f.Salir
    """)

    opcion = input("Opción: ")

    opcionB = False
    opcionC = False
    opcionD = False
    opcionE = False

    while (opcion.lower() != "f"):

        if opcion == "a":
            longitud = validarLongitud()

        if opcion == "b":
            ListaContrasenya.append(string.ascii_lowercase)
            opcionB = True
        if opcion == "c":
            ListaContrasenya.append(string.ascii_uppercase)
            opcionC = True
        if opcion == "d":
            ListaContrasenya.append(string.punctuation)
            opcionD = True
        if opcion == "e":
            opcionE = True
            ListaContrasenya.append(string.digits)

        opcion = input("Más Opciónes: ")

    print()

    while True:
        password = ""

        for i in range(1, longitud + 1):
            password += random.choice(ListaContrasenya[random.randint(0, len(ListaContrasenya) - 1)])
        if validarContrasenya(password, opcionB, opcionC, opcionD, opcionE):
            return password


def enviarEmail1():
    print("Mandando correo... ")

    from_addr = 'jonrula@gmail.com'
    to = 'jonrula@gmail.com'
    message = 'Prueba de Email desde python'

    # Reemplaza estos valores con tus credenciales de Google Mail
    username = 'jonrula'
    password = 'jXXXXXCLA2B'

    server = smtplib.SMTP('smtp.gmail.com:587')
    server.starttls()
    server.login(username, password)
    server.sendmail(from_addr, to, message)

    server.quit()


def enviarEmail2():
    print("Mandando correo... ")


    remitente = "Desde Juanan <juanantonio.ruiz@ikasle.egibide.org>"
    destinatario = "Juanan <juanantonio.ruiz@ikasle.egibide.org>"
    asunto = "Email HTML enviado desde Python"
    mensaje = """Hola!<br/> <br/> 
    Este es un <b>e-mail</b> enviando desde <b>Python</b> 
    """

    email = """From: %s 
    To: %s 
    MIME-Version: 1.0 
    Content-type: text/html 
    Subject: %s 

    %s
    """ % (remitente, destinatario, asunto, mensaje)
    try:
        smtp = smtplib.SMTP('smtp.gmail.com')
        smtp.sendmail(remitente, destinatario, email)
        print ("Correo enviado")
    except:
        print ("""Error: el mensaje no pudo enviarse. 
        Compruebe que sendmail se encuentra instalado en su sistema""")



def valor(ListaContrasenya, opcion, respuesta, pregunta, diccionario):
    respuesta = input(pregunta)
    while (respuesta.lower() != "s" and respuesta.lower() != "n"):
        print("Error !!")
        respuesta = input(pregunta)

    if (respuesta.lower() == "s"):
        ListaContrasenya.append(diccionario)
        opcion = True

