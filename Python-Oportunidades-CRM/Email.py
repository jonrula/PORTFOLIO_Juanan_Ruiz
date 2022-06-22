import re
from color import  *

class Email ():
    def __init__(self,email ):
        self.email =email


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
        email = input("Email: ")


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
