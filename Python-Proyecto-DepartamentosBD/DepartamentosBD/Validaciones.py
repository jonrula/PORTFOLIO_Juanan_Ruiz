import poplib
from email.parser import Parser

from Color import *
import re
from colorama import init, Fore
init(autoreset=True)

class Validador(object):
  TABLA_NIF='TRWAGMYFPDXBNJZSQVHLCKE'       # Valores para validar el NIF

  CLAVES_CIF='PQS' + 'ABEH' + 'CDFGJRUVNW'
  CLAVES_NIF1 = 'LKM'                       # Son especiales, se validan
                                            # como CIFs
  CLAVES_NIF2 = 'XYZ'
  CLAVES_NIF = CLAVES_NIF1 + CLAVES_NIF2


  CONTROL_CIF_LETRA = 'KPQS'
  CONTROL_CIF_NUMERO = 'ABEH'

  EQUIVALENCIAS_CIF = {1:'A', 2:'B', 3:'C', 4:'D', 5:'E', 6:'F', 7:'G',
8:'H', 9:'I', 10:'J', 0:'J'}


def validarString(pregunta, longitud):
    dato = input(color.BLUE+pregunta+color.END)

    while len(dato) == 0 or dato.isspace() or len(dato) > longitud:
        if len(dato) == 0 or dato.isspace():
            print(color.RED+"Error, introduce un nombre !!"+color.END)
        if len(dato) > longitud:
            print(color.RED+"Error !!, el nombre no puede tener más de " + str(longitud) + " carácteres !!"+color.END)
        dato = input(color.BLUE+pregunta+color.END)

    return dato.strip()

def validarIntroducirNumero(pregunta):
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
            numero = int(input(color.BLUE + pregunta + color.END))

        except ValueError:
            print(color.RED + "Error !!, introduce un número !!" + color.END)
            numero = int(input(color.BLUE + pregunta + color.END))

        if numero < 0:
            print(color.RED + "Introduce un número mayor que cero !!" + color.END)
        else:
            break


    return numero


def validarIntroducirNumeroDecimal(pregunta):
    correcto = True
    numero = 0.00

    '''
    Valido el número que recojo del usuario, que no puede ser cualquier cáracter, tiene que ser un número
    y ese número tiene que ser mayor o igual que 4,  y menor de 21, porque si el usuario escoge un número muy grande
    La decisión de elegir un a longitud mínima de 4 es por si elige el usuario las 4 opciones:
    mayúsculas, minúsculas, símbolos y números --> necesita 4 carácteres para formar la contraseña
    '''
    while (correcto):
        try:
            numero = float(input(color.BLUE + pregunta + color.END))

        except ValueError:
            print(color.RED + "Error !!, introduce un número !!" + color.END)
            numero = float(input(color.BLUE + pregunta + color.END))

        if numero < 0.00:
            print(color.RED + "Introduce un número mayor que cero !!" + color.END)
        else:
            break


    return numero





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

def validarNumeroLista(lista):
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

        if numero not in lista:
            print(color.RED+"Error !!"+color.END)
        else:
            break

    return numero

def validarOpcion(contador):
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
            print(color.RED+"Error !!, introduce un número entre 1 y " +str(contador)+ "!!"+color.END)

        if numero <1 or numero > contador:
            print(color.RED+"Error !!"+color.END)
        if numero >0 and numero <= contador:
            break

    return numero


def validarDNI():

    patron = re.compile(r"^\d{8}[ -]?[a-zA-Z]$")

    dni = input(color.BLUE+"DNI: "+color.END)
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, dni):
        print(color.RED+"Error !! introduce 8 dígitos más una letra sin separaciones !!"+color.END)
        dni = input(color.BLUE+"DNI: "+color.END)

    return dni

def validarCIF():

    patron = re.compile(r"^[a-zA-Z]{1}\d{7}[a-zA-Z0-9]{1}$")

    cif = input(color.BLUE+"CIF: "+color.END)
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, cif):
        print(color.RED+"""Error !!... introduce un CIF correcto:
        Consta de una letra, seguida de cinco números y finalmente un código de control (puede ser numérico o una letra)
        Solo compruebo la expresión regular, no que sea válido el CIF...
        https://www.generador-de-dni.com/generador-de-dni
        Ejemplos:
        
        \tN1858833E
        \tR7002625G
        \tB36912897
        """
        +color.END)
        cif = input(color.BLUE+"CIF: "+color.END)

    return cif

def CIFesCorrecto(self,valor):
    """
    Nos indica si un CIF es valido.
    El valor debe estar normalizado
    @note:
      - ante cualquier problema se valida como False
    """
    bRet = False

    if len(valor) == 9:
      v0 = valor[0]
      if v0 in self.Validador.CLAVES_NIF1 or v0 in self.Validador.CLAVES_CIF:
        try:
          sumPar = 0
          sumImpar = 0
          for i in range(1,8):
            if i % 2:
              v = int(valor[i]) * 2
              if v > 9: v = 1 + (v - 10)
              sumImpar += v
            else:
              v = int(valor[i])
              sumPar += v
          suma = sumPar + sumImpar
          e = suma % 10
          d = 10 - e
          letraCif = self.Validador.EQUIVALENCIAS_CIF[d]
          if valor[0] in self.Validador.CONTROL_CIF_LETRA:
            if valor[-1] == letraCif: bRet = True
          elif valor[0] in self.Validador.CONTROL_CIF_NUMERO:
            if d == 10: d = 0
            if valor[-1] == str(d): bRet = True
          else:
            if d == 10: d = 0
            if valor[-1] == str(d) or valor[-1] == letraCif: bRet = True
        except:
          pass

    return bRet



def validarWeb():
    patron = re.compile(r"^(https?:\/\/)?(www\.)([a-zA-Z0-9]+(-?[a-zA-Z0-9])*\.)+[\w]{2,}(\/\S*)?$")


    web = input(color.BLUE+"Página web: "+color.END)
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, web):
        print(color.RED+"""Error !! introduce el nombre de la página web correcta: !!"
        https://www.google.com
        http://www.google.com
        www.google.com"""
        +color.END)
        web = input(color.BLUE+"Página web: "+color.END)

    return web



def validarTarjetaCredito():
    patron = re.compile(r"^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|3[47][0-9]{13}|6(?:011|5[0-9][0-9])[0-9]{12})$")

    # Otras expresiones regulares, pero que no son 'Universales' o dan problemas con espacios, guiones...
    #(([X-Z]{1})([-]?)(\d{7})([-]?)([A-Z]{1}))|((\d{8})([-]?)([A-Z]{1}))
    #^(?:4\d([\- ])?\d{6}\1\d{5}|(?:4\d{3}|5[1-5]\d{2}|6011)([\- ])?\d{4}\2\d{4}\2\d{4})$
    #/^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|3[47][0-9]{13}|6(?:011|5[0-9][0-9])[0-9]{12})$/;
    #patron = re.compile(r"^\d{8}[ -]?[a-zA-Z]$")

    tarjeta = input(color.BLUE+"Tarjeta crédito: "+color.END)
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, tarjeta):
        print(color.RED+"""
        Error !! Tarjeta de crédito no válida !!
        Tienes que introducir una de las siguientes tarjetas válidas:
        
        Visa (16 dígitos empieza por 4, sin espacios ni guiones)
            Ejemplos:
            4012888888881881
            4700771388202047
            4111111111111111
            4142825735295629
            4012-8888-8888-1881 --> NO !
            
        MasterCard (16 dígitos empieza por (51 - 55), sin espacios ni guiones)
            Ejemplos:
            5105105105105100
            5354934050712125
            5491466415854114
            5436426206669657
            5105-1051-0510-5100 --> NO !
                   
        Discover (16 dígitos empieza por 6011, sin espacios ni guiones)
            Ejemplos:
            6011000990139424
            6011549905050803
            6011970471067254
            6011111111111117
            6011-0009-9013-9424  --> NO !
            
        """
              +color.END)
        tarjeta = input(color.BLUE+"Tarjeta crédito: "+color.END)

    return tarjeta


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

def validarEmail():

    patron = re.compile(r"^\S{1,}@\S{1,}\.\S{2,}$")

    '''
    Esa expresión regular comprobará:
    - Que la dirección de correo es una secuencia de caracteres de longitud superior a 1 
    - Sin espacios separados por espacios, seguida de una "@"  
    - Seguida de dos secuencias de caracteres de espacios no espacios de 1 o más caracteres separados por un "." 
    '''

    email = input(color.BLUE+"Email: "+color.END)
    '''
     --> Aquí compruebo con el método "match" de "re" al que le paso los siguientes prámetros: el patrón y el email y si NO es válido devuelve None
    '''
    while not re.match(patron, email):
        print(color.RED+"Error, email incorrecto!!"+color.END)
        email = input(color.BLUE+"Email: "+color.END)


    return email


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
        print(color.RED+"ERROR !! ... el mensaje no pudo enviarse. Compruebe que sendmail se encuentra instalado en su sistema."+color.END)
        print("NOTA: Google no permitirá el inicio de sesión a través de 'smtplib' porque ha marcado este tipo de inicio de sesión como 'menos seguro'.")
        print("Para solucionar este problema, vaya a https://www.google.com/settings/security/lesssecureapps mientras está conectado a su cuenta de Google y a 'Permitir aplicaciones menos seguras'. ")
        print()


def enviarEmailAccesoPixels(nombre,password,email):

    # Importar librerías

    from email.mime.multipart import MIMEMultipart
    from email.mime.text import MIMEText
    import smtplib

    # Crear una instancia del objeto msg
    msg = MIMEMultipart()

    print(color.BLUE+"Enviando datos por email a "+nombre.upper()+" ... ")

    message = """Hola %s, bienvenido/a a PIXELS. 
    Estos son tus datos de acceso:
        • Usuario/a: %s 
        • Contraseña: %s
        """ %(nombre.title(),nombre.title(),password)

    # Variables del mensaje:
    passwordPixels = "PIXELS1234"
    msg['From'] = "departamentorrhh.pixels@gmail.com"
    msg['To'] = email
    msg['Subject'] = "Datos de acceso a PIXELS"

    try:
        # Cuerpo del mensaje
        msg.attach(MIMEText(message, 'plain'))

        # Crear el servidor
        server = smtplib.SMTP('smtp.gmail.com: 587')

        server.starttls()

        # Credenciales
        server.login(msg['From'], passwordPixels)

        # Envío del mensaje al servidor
        server.sendmail(msg['From'], msg['To'], msg.as_string())

        server.quit()

        print (Fore.GREEN+"Email enviado correctamente de: '%s' a: '%s'" % ( msg['From'],msg['To']))

        print()

    except:
        print(color.RED+"ERROR !! ... el mensaje no pudo enviarse. Compruebe que sendmail se encuentra instalado en su sistema."+color.END)
        print("NOTA: Google no permitirá el inicio de sesión a través de 'smtplib' porque ha marcado este tipo de inicio de sesión como 'menos seguro'.")
        print("Para solucionar este problema, vaya a https://www.google.com/settings/security/lesssecureapps mientras está conectado a su cuenta de Google y a 'Permitir aplicaciones menos seguras'. ")
        print()





def enviarEmailRRHH():

    # Importar librerías

    from email.mime.multipart import MIMEMultipart
    from email.mime.text import MIMEText
    import smtplib

    # Crear una instancia del objeto msg
    msg = MIMEMultipart()

    print()
    print(color.BLUE+"Hola bienvenido a PIXELS, para registarse como usuario/a nuevo/a, le pedimos unos datos que serán gestionados por el departamento de RRHH, y se le enviará por correo el usuario y contraseña")
    print(color.BLUE+"Muchas gracias"+color.END)
    print()



    nombre=validarString("Nombre: ",20)
    apellidos=validarString("Apellidos: ",20)
    dni=validarDNI()
    direccion = validarString("Dirección: ", 20)
    telefono = validarTelefono()
    email=validarEmail()

    # Tengo que hacer 2 emails:
    # -remitente le obligo a usar el mío
    # -destinatario --> el departamento de RRHH

    # Variables del mensaje:
    password = "PIXELS1234"
    msg['From'] = "usuarionuevo.pixels@gmail.com"
    msg['To'] = "departamentorrhh.pixels@gmail.com"
    msg['Subject'] = "Usuario nuevo"

    message = """Hola departamento de RRHH de Pixels. 
    Estos son mis datos:
        • Nombre: %s 
        • Apellidos: %s
        • DNI: %s
        • Direccion: %s
        • Telefono: %s
        • Email: %s
        """ %(nombre.title(),apellidos.title(),dni,direccion,telefono,email)

    try:

        print(color.BLUE+"Mandando petición de acceso a PIXELS ... "+color.END)

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

        print()

        print (Fore.GREEN+"Email enviado correctamente de: '%s' a: '%s'" % ( msg['From'],msg['To']))

        print()

    except:
        print()
        print(color.RED+"ERROR !! ... el mensaje no pudo enviarse. Compruebe que sendmail se encuentra instalado en su sistema."+color.END)
        print("NOTA: Google no permitirá el inicio de sesión a través de 'smtplib' porque ha marcado este tipo de inicio de sesión como 'menos seguro'.")
        print("Para solucionar este problema, vaya a https://www.google.com/settings/security/lesssecureapps mientras está conectado a su cuenta de Google y a 'Permitir aplicaciones menos seguras'. ")
        print()




def peticionUsuarioNuevo(usuario):

    # Importar librerías

    from email.mime.multipart import MIMEMultipart
    from email.mime.text import MIMEText
    import smtplib


    # Crear una instancia del objeto msg
    msg = MIMEMultipart()

    print()
    print(color.BLUE+"Hola estos son los mensajes que hay en la bandeja de entrada del departamento de RRHH e PIXELS"+color.END)
    print()


    # Se establece conexion con el servidor pop de gmail
    m = poplib.POP3_SSL('pop.gmail.com',995)
    m.user('departamentorrhh.pixels@gmail.com')
    m.pass_('PIXELS1234')

    numero = len(m.list()[1])

    for i in range (numero):
        print("Mensaje numero"+str(i+1))
        print("--------------------")

        # Se lee el mensaje
        response, headerLines, bytes = m.retr(i+1)

        print(headerLines)

        # Se mete el contenido del mensaje en un único string
        mensaje="\n".join(headerLines)

        # Se parsea el mensaje
        p = Parser()
        email = p.parsestr(mensaje)

        # Se sacan por pantalla los campos from, to y subject
        print("From: "+email["From"])
        print("To: "+email["To"])
        print("Subject: "+email["Subject"])
        print("ID: "+email['message-id'])

        # Sino es de tipo Multipart --> Podemos mirar el tipo de contenido y si es de texto, extraerlo

        tipo = email.get_content_type()
        if ("text/plain" == tipo):
            # Si es texto plano, se escribe en pantalla
            print(email.get_payload(decode=True))


    # Cierre de la conexion
    m.quit()



# RECEPCION DE CORREOS EN LA BANDEJA DE ENTRADA DE 'departamentosrrhh.pixels@gmail.com'

import imaplib
import email

def get_body(tmsg):
    body = ""
    if tmsg.is_multipart():
        for part in tmsg.walk():
            ctype = part.get_content_type()
            cdispo = str(part.get('Content-Disposition'))
            # skip any text/plain (txt) attachments
            if ctype == 'text/plain' and 'attachment' not in cdispo:
                body = str(part.get_payload(decode=True))  # decode
                #body=body.replace("\\r\\n","\n")
                body=body.replace("\\xe2\\x80\\xa2","").replace("\\n", "\n").replace("b'","\t").replace("'","")
                break
    # not multipart - i.e. plain text, no attachments, keeping fingers crossed
    else:
        body = str(tmsg.get_payload(decode=True))
    return body

def get_emailinfo(id, data, bshowbody=False):
    for contenido in data:
        # comprueba que 'contenido' sea una tupla, si es así continua
        if isinstance(contenido, tuple):
            # recuperamos información del email:
            msg = email.message_from_string(contenido[1].decode())
            # mostramos resultados:
            #print ("%d - *** %s ***" % (id, msg['subject'].upper()))

            if(bshowbody):
                print ("---------------------------------------------------")
                print(color.BLUE+"EMAIL: "+str(id)+color.END)
                print()
                print ("Enviado por: "+Fore.LIGHTBLUE_EX+"%s" % msg['from'])
                print ("Para: "+Fore.LIGHTBLUE_EX+"%s" % msg['to'])
                print()
                print(Fore.GREEN+get_body(msg))

            return True
    # si no hay info
    return False

def get_emails(gmailsmtpsvr, gmailusr, gmailpwd, bshowbody):
    try:
        # Conectamos a nuestro servidor, gmail necesita un ssl socket (encriptado) por lo que utilizamos
        # la subclase IMAP4_SSL
        # Parámetros: host='', port=IMAP4_SSL_PORT, keyfile=None, certfile=None, ssl_context=None
        # El puerto por defecto IMAP4_SSL_PORT es el 993, en el caso de gmail lo dejamos como está, el resto de
        # parámetros tampoco son necesarios en este caso.
        # Únicamente es necesario pasar como parámetro el servidor stmp de gmail

        print()
        print(color.BLUE+"Accediendo al correo de 'departamentosrrhh.pixels@gamil.com' ... "+color.END)
        mail = imaplib.IMAP4_SSL(gmailsmtpsvr)
        # logamos:
        mail.login(gmailusr, gmailpwd)
        # seleccionamos bandeja de entrada 'inbox'
        mail.select("inbox")

        # recuperamos lista de emails, es posible filtrar la consulta
        # ALL devuelve todos los emails
        # Ejemplo de filtro: '(FROM "altaruru" SUBJECT "ejemplo python")'

        result, data = mail.search(None, '(FROM "usuarionuevo.pixels" SUBJECT "Usuario nuevo")')
        strids = data[0] # coge lista de ids encontrados
        lstids = strids.split()
        # recuperamos valores para bucle
        firstid = int(lstids[0])
        lastid = int(lstids[-1])
        countid = 0
        # mostramos datos de los ids encontrados
        print()
        #print("Primer email %d\nÚltimo email: %d\n" % (firstid, lastid))
        print(color.BLUE+"*************  BIENVENIDOS AL CORREO DE RRHH DE PIXELS  ************")
        print(color.BLUE+"Recibiendo mensajes de 'departamentosrrh.pixels@gmail.com' ... ")
        print(color.BLUE+"Se han encontrado "+ str((int(lastid) - int(firstid))+1) +" correos con peticiones de usuarios/as nuevos/as: \n"+color.END)

        # recorremos lista de mayor a menor (mas recientes primero)
        for id in range(lastid, firstid-1, -1):
            typ, data = mail.fetch(str(id), '(RFC822)' ) # el parámetro id esperado es tipo cadena
            if (get_emailinfo(id, data, bshowbody)):
                countid+=1
        # fin, si llegamos aqui está bien toda la función

        print ("---------------------------------------------------")
        print()
        print(color.BLUE+"Emails listados:"+color.END+" %d" % countid)
        print()
    except Exception as e:
        print("Error: %s" % (e))
        return ""
    except:
        print("Error desconocido")
        return ""










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




def eligeEstadoAprobado():
    listaNivel = [1,2]

    print(color.BLUE+"Elige nuevo estado del presupuesto:\n\n"+color.END+

          color.BLUE+"\t1"+color.END +color.RED+" No aprobado\n"+color.END+
          color.BLUE+"\t2"+color.END +color.GREEN+" Aprobado\n"+color.END)

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        return "'No aprobado'"
    if opcion == 2:
        return "'Aprobado'"

def eligeEstadoPago():
    listaNivel = [1,2]

    print(color.BLUE+"Elige nuevo estado del pago:\n\n"+color.END+

          color.BLUE+"\t1"+color.END +color.RED+" Pendiente\n"+color.END+
          color.BLUE+"\t2"+color.END +color.GREEN+" pagado\n"+color.END)

    opcion = validarNumero(listaNivel)

    if opcion == 1:
        return "'Pendiente'"
    if opcion == 2:
        return "'Pagado'"



def eligeAprobarPresupuesto(numeroPresupuesto,estadoPresupuesto):
    respuesta=""

    listaNivel = [1,2]
    if estadoPresupuesto=="Aprobado":
        print(color.BLUE+"El estado actual del presupuesto "+numeroPresupuesto+" es: "+color.END+Fore.GREEN+estadoPresupuesto.upper())
    elif estadoPresupuesto=="No aprobado":
        print(color.BLUE+"El estado actual del presupuesto "+numeroPresupuesto+" es: "+color.RED+estadoPresupuesto.upper()+color.END)

    respuesta = input(color.BLUE+"Quieres cambiar el estado del PRESUPUESTO: ? (s/n) "+color.END)
    while (respuesta.lower() != "s" and respuesta.lower() != "n"):
            print(color.RED + "Error !!, introduce 's' o 'n'" + color.END)
            respuesta = input(color.BLUE+"Quieres cambiar el estado del PRESUPUESTO ? (s/n) "+color.END)

    if respuesta == "s":

        print()

        print(color.BLUE+"Elige una opción:\n\n"+color.END+

              color.BLUE+"\t1"+color.END +Fore.GREEN+" Aprobado\n"+
              color.BLUE+"\t2"+color.END +color.RED+" No aprobado\n"+color.END)

        opcion = validarNumero(listaNivel)

        if opcion == 1:
            print(color.BLUE+"El estado actual del presupuesto "+numeroPresupuesto+" es: "+color.END+Fore.GREEN+"Aprobado")
            return "Aprobado"
        if opcion == 2:
            print(color.BLUE+"El estado actual del presupuesto "+numeroPresupuesto+" es: "+color.RED+"No aprobado"+color.END)
            return "No aprobado"
