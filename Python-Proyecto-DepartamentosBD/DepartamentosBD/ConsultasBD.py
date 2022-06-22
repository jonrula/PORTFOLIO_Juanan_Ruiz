import pymysql
from Color import *
from Compras import *
from FechaRegistros import registroBD
from Ventas import *
from Pagos import *
from Gastos import *
from RRHH import *
from Accesos import *
from colorama import init, Fore
from Presupuestos import *
init(autoreset=True)

'''
Para ver todas las constraints:

select *
from information_schema.table_constraints
where constraint_schema = 'Pixels'

Para ver solo las Foreign key:

select *
from information_schema.table_constraints
where constraint_schema = 'Pixels' and constraint_type = 'FOREIGN KEY'

'''


def borrar_tablas_hijas():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:

        # Preparar el cursor
        cursor = db.cursor()
        # IMPORTANTE --> BORRAR EN ESTE ORDEN TODAS LAS TABLAS HIJAS (QUE TIENEN FK) Y LUEGO LAS PADRES PARA CREAR OTRA VEZ TODA LA BASE DE DATOS

        # TABLAS 'HIJAS'
        cursor.execute("DROP TABLE IF EXISTS USUARIOS")
        cursor.execute("DROP TABLE IF EXISTS COCHES")
        cursor.execute("DROP TABLE IF EXISTS LISTA_GASTOS")
        cursor.execute("DROP TABLE IF EXISTS NOMINAS")
        cursor.execute("DROP TABLE IF EXISTS CURSOS_FORMACION")
        cursor.execute("DROP TABLE IF EXISTS LISTA_COMPRAS")
        cursor.execute("DROP TABLE IF EXISTS LISTA_VENTAS")
        cursor.execute("DROP TABLE IF EXISTS PEDIDOS_COMPRAS")
        cursor.execute("DROP TABLE IF EXISTS PEDIDOS_VENTAS")

        # TRIGGERS
        cursor.execute("DROP TRIGGER IF EXISTS BORRADO_USUARIOS")
        cursor.execute("DROP TRIGGER IF EXISTS ACTUALIZACIONES_NOMINAS")
        cursor.execute("DROP TRIGGER IF EXISTS ACTUALIZACIONES_COMPRAS")
        cursor.execute("DROP TRIGGER IF EXISTS ACTUALIZACION_SUBTOTAL_LISTA_COMPRAS")
        cursor.execute("DROP TRIGGER IF EXISTS ACTUALIZACION_TOTAL_PEDIDOS_COMPRAS")
        cursor.execute("DROP TRIGGER IF EXISTS ACTUALIZACIONES_ACTUALIZACIONES_VENTAS")




        # TABLAS 'PADRE'
        cursor.execute("DROP TABLE IF EXISTS PRODUCTOS_COMPRAS")
        cursor.execute("DROP TABLE IF EXISTS PRODUCTOS_VENTAS")
        cursor.execute("DROP TABLE IF EXISTS TRABAJADORES")
        cursor.execute("DROP TABLE IF EXISTS PROVEEDORES")
        cursor.execute("DROP TABLE IF EXISTS CLIENTES")


        db.commit()

        # MENSAJES DE CONFIRMACION

        print(Fore.GREEN+'########################### INICIALIZANDO LA BASE DE DATOS PIXELS... #######################\n')

        print(Fore.GREEN+'Tabla USUARIOS borrada correctamente')
        print(Fore.GREEN+'Tabla COCHES borrada correctamente')
        print(Fore.GREEN+'Tabla LISTA_GASTOS borrada correctamente')
        print(Fore.GREEN+'Tabla NOMINAS borrada correctamente')
        print(Fore.GREEN+'Tabla CURSOS_FORMACION borrada correctamente')
        print(Fore.GREEN+'Tabla LISTA_COMPRAS borrada correctamente')
        print(Fore.GREEN+'Tabla LISTA_VENTAS borrada correctamente')
        print(Fore.GREEN+'Tabla PEDIDOS_COMPRAS borrada correctamente')
        print(Fore.GREEN+'Tabla PEDIDOS_VENTAS borrada correctamente')


        print(Fore.GREEN+'Trigger BORRADO_USUARIOS borrada correctamente')
        print(Fore.GREEN+'Trigger ACTUALIZACIONES_NOMINAS borrada correctamente')
        print(Fore.GREEN+'Trigger ACTUALIZACIONES_COMPRAS borrada correctamente')
        print(Fore.GREEN+'Trigger ACTUALIZACION_SUBTOTAL_LISTA_COMPRAS borrada correctamente')
        print(Fore.GREEN+'Trigger ACTUALIZACION_TOTAL_PEDIDOS_COMPRAS borrada correctamente')
        print(Fore.GREEN+'Trigger AACTUALIZACIONES_ACTUALIZACIONES_VENTAS borrada correctamente')


        print(Fore.GREEN+'Tabla PRODUCTOS_COMPRAS borrada correctamente')
        print(Fore.GREEN+'Tabla PRODUCTOS_VENTAS borrada correctamente')
        print(Fore.GREEN+'Tabla TRABAJADORES borrada correctamente')
        print(Fore.GREEN+'Tabla PROVEEDORES borrada correctamente')
        print(Fore.GREEN+'Tabla CLIENTES borrada correctamente')
        print()


        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en los borrado(s) o en la conexión'+color.END)
        db.rollback() #Deshacer cambios




################################################### TABLAS PADRES #####################################################

def crear_tabla_trabajadores():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS TRABAJADORES")
        # SQL para crear tabla
        sql = """CREATE TABLE TRABAJADORES (
            ID_TRABAJADOR INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            NOMBRE  VARCHAR(30) NOT NULL,
            APELLIDOS  VARCHAR(30) NOT NULL,
            DNI  VARCHAR(9) NOT NULL,
            DIRECCION  VARCHAR(30) NOT NULL,
            TELEFONO  VARCHAR(12) NOT NULL,
            EMAIL VARCHAR(50) NOT NULL,
            FECHA_ALTA  VARCHAR(10) NOT NULL,
            ANTIGUEDAD INT NOT NULL,
            TRABAJANDO VARCHAR(12) NOT NULL DEFAULT 'Trabajando',
            DEPARTAMENTOS VARCHAR(50) NOT NULL,
            CONSTRAINT CHK_TRABAJO CHECK (TRABAJANDO='Trabajando' OR TRABAJANDO='Baja laboral'))"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla TRABAJADORES creada correctamente')

        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla TRABAJADORES o en la conexión'+color.END)
        db.rollback() #Deshacer cambios




def crear_tabla_clientes():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS CLIENTES")
        # SQL para crear tabla
        sql = """CREATE TABLE CLIENTES (
            ID_CLIENTE INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            NOMBRE VARCHAR(30) NOT NULL,
            CIF VARCHAR(12) NOT NULL,
            DIRECCION VARCHAR(30) NOT NULL,
            TELEFONO VARCHAR(12) NOT NULL,
            EMAIL VARCHAR(50) NOT NULL,
            WEB VARCHAR(50) NOT NULL)"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla CLIENTES creada correctamente')
        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla CLIENTES o en la conexión'+color.END)
        db.rollback() #Deshacer cambios



def crear_tabla_proveedores():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:

        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS PROVEEDORES")
        # SQL para crear tabla
        sql = """CREATE TABLE PROVEEDORES (
            ID_PROVEEDOR INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            NOMBRE  VARCHAR(30) NOT NULL,
            CIF  VARCHAR(12) NOT NULL,
            DIRECCION  VARCHAR(30) NOT NULL,
            TELEFONO  VARCHAR(12) NOT NULL,
            EMAIL VARCHAR(50) NOT NULL,
            WEB VARCHAR(50) NOT NULL)"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla PROVEEDORES creada correctamente')
        # Desconectar del servidor
        db.close()
    except:
        print(color.RED+'Error en la creación de la tabla PROVEEDORES o en la conexión'+color.END)
        db.rollback() #Deshacer cambios









def crear_tabla_Productos_compras():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:

        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS PRODUCTOS_COMPRAS")
        # SQL para crear tabla
        sql = """CREATE TABLE PRODUCTOS_COMPRAS (
            ID_PRODUCTO_COMPRA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            ID_RELACION_PROVEEDOR INT NOT NULL, CONSTRAINT FK_PROD_POV FOREIGN KEY (ID_RELACION_PROVEEDOR) REFERENCES PROVEEDORES (ID_PROVEEDOR) ON DELETE CASCADE ON UPDATE CASCADE,
            PRODUCTO  VARCHAR(50) NOT NULL,
            DESCRIPCION VARCHAR(100) NOT NULL,
            PRECIO DEC(10,2) NOT NULL)"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla PRODUCTOS_COMPRAS creada correctamente')

        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla PRODUCTOS_COMPRAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


def crear_tabla_Productos_ventas():

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:

        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS PRODUCTOS_VENTAS")
        # SQL para crear tabla
        sql = """CREATE TABLE PRODUCTOS_VENTAS (
            ID_PRODUCTO_VENTA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            PRODUCTO  VARCHAR(50) NOT NULL,
            DESCRIPCION VARCHAR(100) NOT NULL,
            PRECIO  DEC(10,2) NOT NULL)"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla PRODUCTOS_VENTAS creada correctamente')

        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla PRODUCTO_VENTAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios








################################################### TABLAS HIJAS ######################################################

def crear_tabla_usuarios():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:

        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS USUARIOS")
        # SQL para crear tabla
        sql = """CREATE TABLE USUARIOS (
            ID_USUARIOS INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            ID_RELACION_TRABAJADOR INT NULL, CONSTRAINT FK_USU_TRAB FOREIGN KEY (ID_RELACION_TRABAJADOR) REFERENCES TRABAJADORES (ID_TRABAJADOR) ON DELETE CASCADE ON UPDATE CASCADE,
            ID_RELACION_CLIENTE INT NULL, CONSTRAINT FK_USU_CLI FOREIGN KEY (ID_RELACION_CLIENTE) REFERENCES CLIENTES (ID_CLIENTE) ON DELETE CASCADE ON UPDATE CASCADE,
            ID_RELACION_PROVEEDOR INT NULL, CONSTRAINT FK_USU_POV FOREIGN KEY (ID_RELACION_PROVEEDOR) REFERENCES PROVEEDORES (ID_PROVEEDOR) ON DELETE CASCADE ON UPDATE CASCADE,
            NOMBRE  VARCHAR(30),
            CONTRASEÑA VARCHAR(40),
            DEPARTAMENTOS VARCHAR(50))"""
        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla USUARIOS creada correctamente')
        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creacion de la tabla USUARIOS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios



def crear_tabla_Coches():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS COCHES")
        # SQL para crear tabla
        sql = """CREATE TABLE COCHES (
            ID_COCHE INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            ID_RELACION_TRABAJADOR INT NOT NULL, CONSTRAINT FK_COCHE_TRAB FOREIGN KEY (ID_RELACION_TRABAJADOR) REFERENCES TRABAJADORES (ID_TRABAJADOR) ON DELETE CASCADE ON UPDATE CASCADE,
            COCHE VARCHAR(30) NOT NULL,
            KILOMETROS_TOTALES INT NOT NULL,
            ESTADO VARCHAR(30) NOT NULL DEFAULT 'Bueno',
            CONSTRAINT CHK_COCHES CHECK (ESTADO='Bueno' OR ESTADO='Regular' OR ESTADO='Malo'))"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla CLIENTES creada correctamente')
        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla CLIENTES o en la conexión'+color.END)
        db.rollback() #Deshacer cambios





def crear_tabla_nominas():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS NOMINAS")
        # SQL para crear tabla
        sql = """CREATE TABLE NOMINAS (
            ID_NOMINA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            ID_RELACION_TRABAJADOR INT NOT NULL, CONSTRAINT FK_NOMINA_TRAB FOREIGN KEY (ID_RELACION_TRABAJADOR) REFERENCES TRABAJADORES (ID_TRABAJADOR) ON DELETE CASCADE ON UPDATE CASCADE,
            MES_NOMINA VARCHAR(30) NOT NULL DEFAULT 'Mayo',
            DIAS_TRABAJADOS INT NOT NULL DEFAULT 31,
            ANTIGUEDAD INT NOT NULL DEFAULT 3,
            KILOMETRAJE_MES INT NOT NULL DEFAULT 0,
            T0TAL_KILOMETROS DEC(10,2) NOT NULL DEFAULT 0,
            SUBTOTAL_LISTA_GASTOS DEC(10,2) NOT NULL DEFAULT 0,
            GASTO_TOTAL DEC(10,2) NOT NULL DEFAULT 0,
            SALARIO_NETO DEC (10,2) NOT NULL DEFAULT 0,
            DEDUCCIONES DEC (10,2) NOT NULL DEFAULT 0,
            LIQUIDO_A_PERCIBIR DEC(10,2) NOT NULL DEFAULT 0,
            CONSTRAINT CHK_NOMINAS CHECK (MES_NOMINA IN ('Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre')))"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla NOMINAS creada correctamente')
        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla NOMINAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios



def crear_tabla_Lista_gastos():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS LISTA_GASTOS")
        # SQL para crear tabla
        sql = """CREATE TABLE LISTA_GASTOS (
            ID_LISTA_GASTOS INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            ID_RELACION_NOMINAS INT NOT NULL, CONSTRAINT FK_GASTOS_NOMINA FOREIGN KEY (ID_RELACION_NOMINAS) REFERENCES NOMINAS (ID_NOMINA) ON DELETE CASCADE ON UPDATE CASCADE,
            LUGAR VARCHAR(30) NOT NULL,
            FECHA VARCHAR(10) NOT NULL,
            DESCRIPCION VARCHAR(100) NOT NULL,
            PRECIO DEC(10,2) NOT NULL)"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla LISTA_GASTOS creada correctamente')
        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla LISTA_GASTOS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


def crear_tabla_Cursos_formacion():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS CURSOS_FORMACION")
        # SQL para crear tabla
        sql = """CREATE TABLE CURSOS_FORMACION (
            ID_CURSOS_FORMACION INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            ID_RELACION_TRABAJADOR INT NOT NULL, CONSTRAINT FK_CURSOS_TRABAJADOR FOREIGN KEY (ID_RELACION_TRABAJADOR) REFERENCES TRABAJADORES (ID_TRABAJADOR) ON DELETE CASCADE ON UPDATE CASCADE,
            DESCRIPCION VARCHAR(100) NOT NULL,
            HORAS INT NOT NULL,
            LUGAR VARCHAR(30) NOT NULL,
            FECHA_INICIO VARCHAR(10) NOT NULL,
            FECHA_FIN VARCHAR(10) NOT NULL)"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla CURSOS_FORMACION creada correctamente')
        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla CURSOS_FORMACION o en la conexión'+color.END)
        db.rollback() #Deshacer cambios



def crear_tabla_Pedidos_compras():

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:

        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS PEDIDOS_COMPRAS")
        # SQL para crear tabla
        sql = """CREATE TABLE PEDIDOS_COMPRAS (
            ID_PEDIDO_COMPRA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            ID_RELACION_PROVEEDOR INT NOT NULL, CONSTRAINT FK_PEDIDO_PROV FOREIGN KEY (ID_RELACION_PROVEEDOR) REFERENCES PROVEEDORES (ID_PROVEEDOR) ON DELETE CASCADE ON UPDATE CASCADE,
            FECHA VARCHAR(10) NOT NULL,
            TOTAL_PEDIDOS_COMPRAS DEC(10,2) NULL,
            APROBADO VARCHAR(20) NOT NULL DEFAULT 'No aprobado',
            CONSTRAINT CHK_APROBADO CHECK (APROBADO IN ('Aprobado','No aprobado')))"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla PEDIDOS_COMPRAS creada correctamente')

        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla PEDIDOS_COMPRAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


def crear_tabla_Lista_compras():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS LISTA_COMPRAS")
        # SQL para crear tabla
        sql = """CREATE TABLE LISTA_COMPRAS (
            ID_LISTA_COMPRA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            ID_RELACION_PEDIDO_COMPRA INT NOT NULL, CONSTRAINT FK_LISTACOMPRA_PEDCOMPRA FOREIGN KEY (ID_RELACION_PEDIDO_COMPRA) REFERENCES PEDIDOS_COMPRAS (ID_PEDIDO_COMPRA) ON DELETE CASCADE ON UPDATE CASCADE,
            PRODUCTO VARCHAR(30) NOT NULL,
            DESCRIPCION VARCHAR(50) NOT NULL,
            PRECIO DEC(10,2) NOT NULL,
            UNIDADES INT NOT NULL,
            SUBTOTAL DEC(10,2) NULL)"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla LISTA_COMPRAS creada correctamente')
        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla LISTA_COMPRAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios

def crear_tabla_Pedidos_ventas():

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS PEDIDOS_VENTAS")
        # SQL para crear tabla
        sql = """CREATE TABLE PEDIDOS_VENTAS (
            ID_PEDIDO_VENTA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            ID_RELACION_CLIENTE INT NULL, CONSTRAINT FK_PEDIDO_CLI FOREIGN KEY (ID_RELACION_CLIENTE) REFERENCES CLIENTES (ID_CLIENTE) ON DELETE CASCADE ON UPDATE CASCADE,
            FECHA VARCHAR(10) NOT NULL,
            TOTAL_PEDIDOS_VENTAS DEC(10,2) NULL,
            PAGO VARCHAR(20) NOT NULL DEFAULT 'Pendiente',
            CONSTRAINT CHK_PAGO CHECK (PAGO IN ('Pendiente','Pagado')))"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla PEDIDOS_VENTAS creada correctamente')

        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla PEDIDOS_VENTAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


def crear_tabla_Lista_ventas():
    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()
        # Eliminar tabla (drop) si ya existe
        cursor.execute("DROP TABLE IF EXISTS LISTA_VENTAS")
        # SQL para crear tabla
        sql = """CREATE TABLE LISTA_VENTAS (
            ID_LISTA_VENTA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
            ID_RELACION_PEDIDO_VENTA INT NOT NULL, CONSTRAINT FK_LISTAVENTA_PEDVENTA FOREIGN KEY (ID_RELACION_PEDIDO_VENTA) REFERENCES PEDIDOS_VENTAS (ID_PEDIDO_VENTA) ON DELETE CASCADE ON UPDATE CASCADE,
            PRODUCTO VARCHAR(30) NOT NULL,
            DESCRIPCION VARCHAR(50) NOT NULL,
            PRECIO DEC(10,2) NOT NULL,
            UNIDADES INT NOT NULL,
            SUBTOTAL DEC(10,2) AS (PRECIO * UNIDADES))"""

        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Tabla LISTA_VENTAS creada correctamente')
        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación de la tabla LISTA_VENTAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios




##################################################### TRIGGERS ########################################################


'''
Mostrar todos los triggers:
show TRIGGERS
'''


def evitar_borrado():

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()

        sql="""CREATE OR REPLACE TRIGGER BORRADO_USUARIOS
            BEFORE DELETE ON USUARIOS
        FOR EACH ROW
        BEGIN
          IF OLD.ID_USUARIOS IS NOT NULL THEN -- Parar borrado si este campo NO es null
            CALL No_puedes_borrar_esta_tabla; -- Error a lanzar para impedir el borrado
          END IF;
        END"""

        print(sql)
        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Trigger BORRADO_USUARIOS creado correctamente')
        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en el borrado del trigger BORRADO_USUARIOS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios

def actualizaciones_lista_gastos_Nominas():

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()

        sql="""CREATE OR REPLACE TRIGGER ACTUALIZACIONES_NOMINAS
            AFTER INSERT ON LISTA_GASTOS
        FOR EACH ROW
        BEGIN
            UPDATE NOMINAS -- Subtotal lista gastos
            SET SUBTOTAL_LISTA_GASTOS = (SELECT SUM(PRECIO) FROM LISTA_GASTOS WHERE ID_RELACION_NOMINAS = NEW.ID_RELACION_NOMINAS)
            WHERE ID_NOMINA = NEW.ID_RELACION_NOMINAS;
            
            UPDATE NOMINAS -- Gasto kilometraje mes
            SET T0TAL_KILOMETROS = KILOMETRAJE_MES * 0.32
            WHERE ID_NOMINA = NEW.ID_RELACION_NOMINAS;
            
            UPDATE NOMINAS -- Gastos totales
            SET GASTO_TOTAL = T0TAL_KILOMETROS + SUBTOTAL_LISTA_GASTOS
            WHERE ID_NOMINA = NEW.ID_RELACION_NOMINAS;
            
            UPDATE NOMINAS -- salario neto
            SET SALARIO_NETO = (DIAS_TRABAJADOS * 110) + (ANTIGUEDAD * 250) + GASTO_TOTAL
            WHERE ID_NOMINA = NEW.ID_RELACION_NOMINAS;
            
            UPDATE NOMINAS -- Deducciones
            SET DEDUCCIONES = SALARIO_NETO * 0.15
            WHERE ID_NOMINA = NEW.ID_RELACION_NOMINAS;
            
            UPDATE NOMINAS -- Líquido a percibir
            SET LIQUIDO_A_PERCIBIR = SALARIO_NETO - DEDUCCIONES
            WHERE ID_NOMINA = NEW.ID_RELACION_NOMINAS; 
        END"""
        print(sql)
        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Trigger ACTUALIZACIONES_NOMINAS creado correctamente')

        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación del trigger ACTUALIZACIONES_NOMINAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


# ESTE TRIGGER AL FINAL NO LO UTILIZADO, PORQUE NO VA BIEN CON EL 'BEFORE' (NO ME HACE BIEN EL SUMATORIO, AL TENER VALORES EN EL AIRE CON EL 'BEFORE', --> LO HE DESGLOSADO EN DOS TRIGGERS. UNO 'BEFORE' Y OTRO 'AFTER'
def actualizaciones_compras():

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()

        sql="""CREATE OR REPLACE TRIGGER ACTUALIZACIONES_COMPRAS
            BEFORE INSERT ON LISTA_COMPRAS
        FOR EACH ROW
        BEGIN
            SET NEW.SUBTOTAL = NEW.PRECIO * NEW.UNIDADES;
            
            UPDATE PEDIDOS_COMPRAS -- total lista pedido compra
            SET TOTAL_PEDIDOS_COMPRAS = (SELECT SUM(SUBTOTAL) FROM LISTA_COMPRAS WHERE ID_RELACION_PEDIDO_COMPRA = NEW.ID_RELACION_PEDIDO_COMPRA)
            WHERE ID_PEDIDO_COMPRA = ©;
              
        END"""

        print(sql)
        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Trigger ACTUALIZACIONES_COMPRAS creado correctamente')

        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación del trigger ACTUALIZACIONES_COMPRAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


def actualizaciones_Subtotal_lista_compras():

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()

        sql="""CREATE OR REPLACE TRIGGER ACTUALIZACION_SUBTOTAL_LISTA_COMPRAS
            BEFORE INSERT ON LISTA_COMPRAS
        FOR EACH ROW
        BEGIN
            SET NEW.SUBTOTAL = NEW.PRECIO * NEW.UNIDADES;    
        END"""

        print(sql)
        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Trigger ACTUALIZACION_SUBTOTAL_LISTA_COMPRAS creado correctamente')

        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación del trigger ACTUALIZACIONES_COMPRAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios

def actualizaciones_Total_Pedido_compras():

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()

        sql="""CREATE OR REPLACE TRIGGER ACTUALIZACION_TOTAL_PEDIDOS_COMPRAS
            AFTER INSERT ON LISTA_COMPRAS
        FOR EACH ROW
        BEGIN
    
            UPDATE PEDIDOS_COMPRAS -- total lista pedido compra
            SET TOTAL_PEDIDOS_COMPRAS = (SELECT SUM(SUBTOTAL) FROM LISTA_COMPRAS WHERE ID_RELACION_PEDIDO_COMPRA = NEW.ID_RELACION_PEDIDO_COMPRA)
            WHERE ID_PEDIDO_COMPRA = NEW.ID_RELACION_PEDIDO_COMPRA;
        
        END"""

        print(sql)
        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Trigger ACTUALIZACION_TOTAL_PEDIDOS_COMPRAS creado correctamente')

        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación del trigger ACTUALIZACIONES_COMPRAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios


def actualizaciones_ventas():

    # Conexión a la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    try:
        # Preparar el cursor
        cursor = db.cursor()

        sql="""CREATE OR REPLACE TRIGGER ACTUALIZACIONES_VENTAS
            AFTER INSERT ON LISTA_VENTAS
        FOR EACH ROW
        BEGIN
    
            UPDATE PEDIDOS_VENTAS -- total lista pedido compra
            SET TOTAL_PEDIDOS_VENTAS = (SELECT SUM(SUBTOTAL) FROM LISTA_VENTAS WHERE ID_RELACION_PEDIDO_VENTA = NEW.ID_RELACION_PEDIDO_VENTA)
            WHERE ID_PEDIDO_VENTA = NEW.ID_RELACION_PEDIDO_VENTA;
        
        END"""

        print(sql)
        cursor.execute(sql)
        db.commit()
        print()
        print(Fore.GREEN+'Trigger ACTUALIZACIONES_VENTAS creado correctamente')

        # Desconectar del servidor
        db.close()

    except:
        print(color.RED+'Error en la creación del trigger ACTUALIZACIONES_COMPRAS o en la conexión'+color.END)
        db.rollback() #Deshacer cambios



################################################# INSERCION DE DATOS ##################################################


def insertar_datos_generica(db,tabla,columnas,valores):

    try:
        cursor = db.cursor()

        consulta = "INSERT INTO " + tabla + "("+ columnas +") VALUES (" + valores + ")"
        print(consulta)

        # Ejecutar SQL --> es un string
        cursor.execute(consulta)
        db.commit()
        print(Fore.GREEN+'Registro insertado correctamente')
    except:
        print(color.RED+'Error en la consulta o en la conexión'+color.END)
        db.rollback() #Deshacer cambios



def creacion_todas_tablas_con_datos():

    ######################################### PRIMERO --> BORRADO DE TABLAS HIJAS #####################################
    # Borrado de tablas hijas, que son las que tienen dependencias, antes de volver a crear las tablas, porque sino no nos deja

    borrar_tablas_hijas()

    print(Fore.GREEN+"################# CREANDO TODAS LAS TABLAS Y LA INSERCIÓN DE DATOS... #######################\n")


    ######################################### SEGUNDO --> CREACION DE TABLAS PADRES ###################################

    # CREACION TABLA TRABAJADORES
    crear_tabla_trabajadores()

    # CONEXION GENERAL
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")

    # INSERCION DATOS TRABAJADORES
    tabla1 = 'TRABAJADORES'
    columnasTrabajadores = "NOMBRE,APELLIDOS,DNI,DIRECCION,TELEFONO,EMAIL,FECHA_ALTA,ANTIGUEDAD,DEPARTAMENTOS"

    trabajador0 = "'Admin','','','','','','',3,'Todo'"
    trabajador1 = "'Juanan','Ruiz','16296028E','Clara Campoamor 8 Vitoria','670839934','juanantonio.ruiz@ikasle.egibide.org','10/09/2018',3,'Fotografía'"
    trabajador2 = "'Elena','Zamora','42132042K','Su casa 2 Vitoria','789345458','elena.zamora@ikasle.egibide.org','10/09/2018',3,'RRHH'"
    trabajador3 = "'Txus','Gonzalez','45657230E','Su casa 3 Vitoria','666568932','jesusmaria.gonzalez@ikasle.egibide.org','10/09/2018',3,'Compras y Ventas'"
    trabajador4 = "'Igor','Anton Muguerza','79157677A','Su casa 4 Vitoria','670456723','igor.anton@ikasle.egibide.org','10/09/2018',3,'Fotografía'"
    trabajador5 = "'Raul','García','53346710G','Su casa 5 Vitoria','690341212','raul.garcia@ikasle.egibide.org','10/09/2018',3,'Fotografía'"
    trabajador6 = "'Gustavo','Adolfo','50095498A','Su casa 6 Vitoria','670212121','gustavoadolfo.quintero@ikasle.egibide.org','10/09/2018',3,'Fotografía'"
    trabajador7 = "'Guillermo','Arias','24038669N','Su casa 7 Vitoria','670171819','guillermo.arias@ikasle.egibide.org','10/09/2018',3,'Fotografía'"


    # INSERCION DATOS TABLA TRABAJADORES


    insertar_datos_generica(db, tabla1, columnasTrabajadores, trabajador0)
    insertar_datos_generica(db, tabla1, columnasTrabajadores, trabajador1)
    insertar_datos_generica(db, tabla1, columnasTrabajadores, trabajador2)
    insertar_datos_generica(db, tabla1, columnasTrabajadores, trabajador3)
    insertar_datos_generica(db, tabla1, columnasTrabajadores, trabajador4)
    insertar_datos_generica(db, tabla1, columnasTrabajadores, trabajador5)
    insertar_datos_generica(db, tabla1, columnasTrabajadores, trabajador6)
    insertar_datos_generica(db, tabla1, columnasTrabajadores, trabajador7)


    # CREACION TABLA CLIENTES
    crear_tabla_clientes()

    tabla2 = 'CLIENTES'
    columnasClientes= "NOMBRE,CIF,DIRECCION,TELEFONO,EMAIL,WEB"

    cliente1="'Abecedario','A26636654','La letrilla 4 Madrid','91235682','abcdario@abcdario.com','www.abcdario.es'"
    cliente2="'La sinrazon','V81613192','Desquicio 2 bajo Madrid','945156472','info@lasinrazon.com','www.lasinrazon.es'"
    cliente3="'Alava News','C99370983','Capelamendi 2 Araba','945156472','info@alavanews.com','www.alavanews.eus'"
    cliente4="'LiverPress','J16864688','Living la vida 42 Madrid','915642890','info@liverpress.com','www.liverpress.es'"
    cliente5="'PinkPress','D39206818','Cupido 19 bajo Alcorcón','915896574','info@pinkpress.com','www.pinkpress.es'"
    cliente6="'Somos','E30934038','Ser o no ser 7 bajo Donosti','943356851','info@somos.com','www.somos.eus'"

    # INSERCION DATOS TABLA CLIENTES

    insertar_datos_generica(db, tabla2, columnasClientes, cliente1)
    insertar_datos_generica(db, tabla2, columnasClientes, cliente2)
    insertar_datos_generica(db, tabla2, columnasClientes, cliente3)
    insertar_datos_generica(db, tabla2, columnasClientes, cliente4)
    insertar_datos_generica(db, tabla2, columnasClientes, cliente5)
    insertar_datos_generica(db, tabla2, columnasClientes, cliente6)


    # CREACION TABLA PROVEEDORES
    crear_tabla_proveedores()

    tabla3 = 'PROVEEDORES'
    columnasProveedores = "NOMBRE,CIF,DIRECCION,TELEFONO,EMAIL,WEB"

    proveedor1="'Fuji','F78060761','Aragó 180 Barcelona','934511515','info@fujifilm.es','www.fujifilm.eu'"
    proveedor2="'Ofirent','H06137673','Sin casa 18 bajo Vitoria','945623789','info@ofirent.com','www.ofirent.es'"
    proveedor3="'Limon Pear Netbooks','C63887020','Pera limonera 5 Vitoria','945896574','info@limonpears.com','www.limonpearsnetbooks.es'"
    proveedor4="'MobiNet','J76458199','Aligobeo 34 Vitoria','945892345','info@mobinet.com','www.mobinet.com'"
    proveedor5="'Segur','S95556312','Segura 56 Vitoria','945878985','info@segur.com','www.segur.com'"
    proveedor6="'Super Limpio','L82716200','Limpisimos 88 Vitoria','945896312','info@superlimpio.com','www.superlimpio.es'"
    proveedor7="'Tesla Leasing Cars','A07546203','Rosello 257 Vitoria','945897515','info@teslaleasingcars.com','www.tesla.com'"
    proveedor8="'Ticket Restaurant','P51835734','Tragaldabas 65 Vitoria','945896574','info@ticketrestaurant.com','www.ticketrestaurant.es'"
    proveedor9="'Amazon Spain','B84599936','Ramirez Prado 5 Madrid','910484500','clientes@amazon.es','www.amazon.es'"
    proveedor10="'Calefa','K98242837','Calentito 8 Vitoria','945236523','info@calefa.com','www.calefa.com'"
    proveedor11="'Electric SA','E06538748','Chisposo 13 Vitoria','945236582','info@electricsa.com','www.electricsa.es'"
    proveedor12="'Fotoreparex','F91443663','Estropeados 5 Vitoria','945238961','info@fotoreparex.com','www.fotoreparex.com'"

    # INSERCION DATOS TABLA PROVEEDORES

    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor1)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor2)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor3)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor4)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor5)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor6)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor7)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor8)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor9)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor10)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor11)
    insertar_datos_generica(db, tabla3, columnasProveedores, proveedor12)


    # CREACION TABLA PRODUCTOS_COMPRAS

    crear_tabla_Productos_compras()

    tabla4 = 'PRODUCTOS_COMPRAS'
    columnasProductosCompras= "ID_RELACION_PROVEEDOR,PRODUCTO,DESCRIPCION,PRECIO"

    productoCompra1 = "1,'Fuji XT-4','Cámara mirrorless',1500"
    productoCompra2 = "1,'Fujinon 100-400 F4-5.6','Zoom luminoso',1800"
    productoCompra3 = "1,'Fujinon 16-80 F4','Angular luminoso',800"
    productoCompra4 = "1,'Flash Godox','Cabezal Flash orientable',330"
    productoCompra5 = "1,'Objetivo 400 F2,8','Super tele F2,8',12000"
    productoCompra6 = "1,'Cable sincro Flash','Cables',25"
    productoCompra7 = "2,'Alquiler oficina','Cuota mensual',1250"
    productoCompra8 = "2,'Alquiler trastero','Cuota mensual',150"
    productoCompra9 = "2,'Alquiler parking','Cuota mensual',150"
    productoCompra10 = "3,'Macbook Pro 16','Portatil edición fotos',2400"
    productoCompra11 = "3,'Arreglo equipos informático','Precio por 1 hora',100"
    productoCompra12 = "3,'iMac 27 5K','Ordenador de mesa',2500"
    productoCompra13 = "3,'iPad 12,9','Tablet',1200"
    productoCompra14 = "3,'iWatch series 6','smartwatch',600"
    productoCompra15 = "3,'Sustitución equipo informático','Sustitución equipo informático en 1 hora',250"
    productoCompra16 = "3,'Rack servidor datos','Backup de datos',1200"
    productoCompra17 = "3,'Datos en la nube','Acceso datos a la nube cuota mensual',120"
    productoCompra18 = "4,'iPhone 12 Pro','Móvil',1300"
    productoCompra19 = "4,'Mifi Netgear','Wifi Portatil 4G',250"
    productoCompra20 = "4,'Internet y telefonía','Tarifa plana mensual telefonía',850"
    productoCompra21 = "4,'Acceso datos en la nube','Tarifa plana mensual acceso datos a la nube',250"
    productoCompra22 = "5,'Seguridad oficina','Vigilancia oficina 24h mensualmente',350"
    productoCompra23 = "5,'Seguridad parking','Vigilancia Parking, 24h mensualmente ',150"
    productoCompra24 = "5,'Seguridad personal','Vigilancia personal, 24h diario ',500"
    productoCompra25 = "6,'Limpieza oficina','Limpiar diariamente oficina 1 mes',750"
    productoCompra26 = "6,'Limpieza coche','Limpiar coche',50"
    productoCompra27 = "6,'Limpieza casa','Limpiar casa diariammente cuota mensual',500"
    productoCompra28 = "6,'Limpieza especiales','Consultar precio hora',50"
    productoCompra29 = "7,'Alquiler coches','Alquiler mensual',500"
    productoCompra30 = "7,'Cuota recarga','Cuota mensual coche',500"
    productoCompra31 = "7,'Cambio neumáticos','Cambio 4 ruedas',600"
    productoCompra32 = "7,'Pintar coche','Pintar de nuevo coche',2400"
    productoCompra33 = "7,'Cambio neumáticos','Cambio 4 ruedas',600"
    productoCompra34 = "7,'Sustitución coche','Sustituir coche en 1 hora',250"
    productoCompra35 = "7,'Arreglo avería express','Arreglo por hora',75"
    productoCompra36 = "7,'Recogida coche averiado','Recoger persona(s) por avería',600"
    productoCompra37 = "8,'Menú Comida','Comer en horas de trabajo',12"
    productoCompra38 = "8,'Menú Comida Fin de semana','Comer en horas de trabajo',25"
    productoCompra39 = "8,'Menú Cena','Cenar en horas de trabajo',20"
    productoCompra40 = "8,'Menú Cena Fin de semana','Cenar en horas de trabajo',35"
    productoCompra41 = "8,'Café','Un café en horas de trabajo',1"
    productoCompra42 = "9,'Bolsa tenba','Bolsa material fotográfico',150"
    productoCompra43 = "9,'Lector de tarjetas','Lector tarjetas CF',13"
    productoCompra44 = "9,'Pilas recargables Eneloop','Pack 4 pilas',18"
    productoCompra45 = "9,'Tapas objetivos','Tapa 77 mm.',5"
    productoCompra46 = "9,'SD Sandisk 512 Gb','Tarjeta SD',30"
    productoCompra47 = "9,'Disco duro externo 2TB','Disco duro',450"
    productoCompra48 = "10,'Calefacción oficina','Gasto calefacción mensual',200"
    productoCompra49 = "10,'Calefacción casa','Gasto calefacción mensual',300"
    productoCompra50 = "10,'Mantenimiento calefacción','Mantenimiento mensual',250"
    productoCompra51 = "11,'Mantenimiento electricidad','Cuota mensual',150"
    productoCompra52 = "11,'Protección SAI para ordenadores','Cuota mensual',350"
    productoCompra53 = "11,'Obras electricidad','Precio por hora',150"
    productoCompra54 = "12,'Arreglo especiales','Depende del material precio/hora',100"
    productoCompra55 = "12,'Mantenimiento arreglo cámaras','Cuota Mensual',1000"
    productoCompra56 = "12,'Arreglo zoom','Ajuste zoom',150"
    productoCompra57 = "12,'Cambio lentes','Ajuste lentes',350"
    productoCompra58 = "12,'Arreglo zapata flash','Cambio zapata',35"
    productoCompra59 = "12,'Arreglo enfoque','Ajustar el enfoque',285"
    productoCompra60 = "12,'Actualización software','Actualizar software cámaras',1000"
    productoCompra61 = "12,'Limpieza sensor CCD','Limpiar sensor',150"



    # INSERCION DATOS TABLA PRODUCTOS_COMPRAS

    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra1)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra2)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra3)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra4)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra5)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra6)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra7)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra8)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra9)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra10)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra11)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra12)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra13)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra14)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra15)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra16)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra17)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra18)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra19)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra20)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra21)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra22)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra23)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra24)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra25)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra26)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra27)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra28)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra29)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra30)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra31)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra32)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra33)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra34)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra35)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra36)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra37)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra38)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra39)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra40)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra41)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra42)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra43)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra44)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra45)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra46)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra47)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra48)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra49)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra50)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra51)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra52)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra53)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra54)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra55)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra56)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra57)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra58)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra59)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra60)
    insertar_datos_generica(db, tabla4, columnasProductosCompras, productoCompra61)


    # CREACION TABLA VENTAS
    crear_tabla_Productos_ventas()

    tabla5 = 'PRODUCTOS_VENTAS'
    columnasProductosVentas= "PRODUCTO,DESCRIPCION,PRECIO"

    productoVenta1 = "'Eventos BBC','Bodas bautizos y comuniones',1500"
    productoVenta2 = "'Restauración Fotos','En papel o digital 1 hora',250"
    productoVenta3 = "'Catálogo de fotos','Digitalizar fotos 1 hora',100"
    productoVenta4 = "'Rueda de prensa','Rueda de prensa 1 hora',150"
    productoVenta5 = "'Eventos deportivos','Evento deportivo 3 horas',500"
    productoVenta6 = "'Reportaje fotográfico','Reportaje fotográfico 4 horas',600"
    productoVenta7 = "'Reportaje especial','Reportaje fotográfico 8 horas',1000"
    productoVenta8 = "'Banco imágenes','Comprar fotos web 1 foto',100"
    productoVenta9 = "'Elecciones seguimiento','Seguimiento candidato/a 1 día',1000"
    productoVenta10 = "'Fuji XT-4 ocasión','En buen estado 1 unidad',1000"
    productoVenta11 = "'iMac 27 5K ocasión','En buen estado 1 unidad',1000"
    productoVenta12 = "'Fujinon 16-80 F4 ocasión','En buen estado 1 unidad',500"
    productoVenta13 = "'Fujinon 100-400 F4-F5.6 ocasión','En buen estado 1 unidad',500"
    productoVenta14 = "'Macbook Pro 16 ocasión','En buen estado 1 unidad',1000"

    # INSERCION DATOS TABLA PRODUCTOS_VENTAS

    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta1)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta2)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta3)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta4)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta5)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta6)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta7)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta8)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta9)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta10)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta11)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta12)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta13)
    insertar_datos_generica(db, tabla5, columnasProductosVentas, productoVenta14)



    ######################################### TERCERO --> CREACION DE TABLAS HIJAS ###################################

    # CREACION TABLA USUARIOS
    crear_tabla_usuarios()

    tabla = 'USUARIOS'
    columnasUsuarios = "ID_RELACION_TRABAJADOR,NOMBRE,CONTRASEÑA,DEPARTAMENTOS"

    usuario0 = "1,'Admin','0000','Compras , Ventas , Pagos , Gastos , RRHH , Accesos'"
    usuario1 = "2,'Juanan','1234','Compras , Gastos'"
    usuario2 = "3,'Elena','1234','RRHH , Gastos'"
    usuario3 = "4,'Txus','1234','Compras , Ventas , Gastos'"
    usuario4 = "5,'Igor','1234','Gastos'"
    usuario5 = "6,'Gustavo','1234','Gastos'"
    usuario6 = "7,'Raul','1234','Gastos'"
    usuario7 = "8,'Guillermo','1234','Gastos'"

    # Añado también creo como 'trabajadores' a los clientes, para que también tengan acceso a la aplicación, y puedan realizar los pagos pendientes:

    columnasUsuarios2 = "ID_RELACION_CLIENTE,NOMBRE,CONTRASEÑA,DEPARTAMENTOS"

    usuario8 = "1,'Abecedario','A26636654','Pagos'"
    usuario9 = "2,'La sinrazon','V81613192','Pagos'"
    usuario10 = "3,'Alava News','C99370983','Pagos'"
    usuario11 = "4,'LiverPress','J16864688','Pagos'"
    usuario12 = "5,'PinkPress','D39206818','Pagos'"
    usuario13 = "6,'Somos','E30934038','Pagos'"

    # Añado también creo como 'trabajadores' a los proveedores, para que también tengan acceso a la aplicación, y puedan realizar los presupuestos:

    columnasUsuarios3 = "ID_RELACION_PROVEEDOR,NOMBRE,CONTRASEÑA,DEPARTAMENTOS"

    usuario14 = "1,'Fuji','F78060761','Presupuestos'"
    usuario15 = "2,'Ofirent','H06137673','Presupuestos'"
    usuario16 = "3,'Limon Pear Netbooks','C63887020','Presupuestos'"
    usuario17 = "4,'MobiNet','J76458199','Presupuestos'"
    usuario18 = "5,'Segur','S95556312','Presupuestos'"
    usuario19 = "6,'Super Limpio','L82716200','Presupuestos'"
    usuario20 = "7,'Tesla Leasing Cars','A07546203','Presupuestos'"
    usuario21 = "8,'Ticket Restaurant','P51835734','Presupuestos'"
    usuario22 = "9,'Amazon Spain','B84599936','Presupuestos'"
    usuario23 = "10,'Calefa','K98242837','Presupuestos'"
    usuario24 = "11,'Electric SA','E06538748','Presupuestos'"
    usuario25 = "12,'Fotoreparex','F91443663','Presupuestos'"


    # INSERCION DATOS TABLA USUARIOS

    insertar_datos_generica(db, tabla, columnasUsuarios, usuario0)
    insertar_datos_generica(db, tabla, columnasUsuarios, usuario1)
    insertar_datos_generica(db, tabla, columnasUsuarios, usuario2)
    insertar_datos_generica(db, tabla, columnasUsuarios, usuario3)
    insertar_datos_generica(db, tabla, columnasUsuarios, usuario4)
    insertar_datos_generica(db, tabla, columnasUsuarios, usuario5)
    insertar_datos_generica(db, tabla, columnasUsuarios, usuario6)
    insertar_datos_generica(db, tabla, columnasUsuarios, usuario7)
    insertar_datos_generica(db, tabla, columnasUsuarios2, usuario8)
    insertar_datos_generica(db, tabla, columnasUsuarios2, usuario9)
    insertar_datos_generica(db, tabla, columnasUsuarios2, usuario10)
    insertar_datos_generica(db, tabla, columnasUsuarios2, usuario11)
    insertar_datos_generica(db, tabla, columnasUsuarios2, usuario12)
    insertar_datos_generica(db, tabla, columnasUsuarios2, usuario13)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario14)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario15)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario16)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario17)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario18)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario19)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario20)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario21)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario22)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario23)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario24)
    insertar_datos_generica(db, tabla, columnasUsuarios3, usuario25)

    # CREACION TABLA USUARIOS
    crear_tabla_Coches()

    tabla6 = 'COCHES'
    columnasCoches = "ID_RELACION_TRABAJADOR,COCHE,KILOMETROS_TOTALES"

    coche1 = "2,'Tesla Model 3',31430"
    coche2 = "3,'Tesla Model S',4440"
    coche3 = "4,'Megane Coupé',7350"
    coche4 = "5,'Tesla Model Y',1250"
    coche5 = "6,'Tesla Model 3',250"
    coche6 = "7,'Tesla Model Y',2380"
    coche7 = "8,'Peugeot 205',7960"

    # INSERCION DATOS TABLA USUARIOS

    insertar_datos_generica(db, tabla6, columnasCoches, coche1)
    insertar_datos_generica(db, tabla6, columnasCoches, coche2)
    insertar_datos_generica(db, tabla6, columnasCoches, coche3)
    insertar_datos_generica(db, tabla6, columnasCoches, coche4)
    insertar_datos_generica(db, tabla6, columnasCoches, coche5)
    insertar_datos_generica(db, tabla6, columnasCoches, coche6)
    insertar_datos_generica(db, tabla6, columnasCoches, coche7)

    # EVITAR BORRADO TABLA USUARIOS
    evitar_borrado()

    # CREACION TABLA NOMINAS

    crear_tabla_nominas()

    tabla7 = 'NOMINAS'
    columnasNominas = "ID_RELACION_TRABAJADOR,KILOMETRAJE_MES"

    nomina1= "2,1200"
    nomina2= "3,200"
    nomina3= "4,980"
    nomina4= "5,750"
    nomina5= "6,2250"
    nomina6= "7,1880"
    nomina7= "8,0"

    insertar_datos_generica(db, tabla7, columnasNominas,nomina1)
    insertar_datos_generica(db, tabla7, columnasNominas,nomina2)
    insertar_datos_generica(db, tabla7, columnasNominas,nomina3)
    insertar_datos_generica(db, tabla7, columnasNominas,nomina4)
    insertar_datos_generica(db, tabla7, columnasNominas,nomina5)
    insertar_datos_generica(db, tabla7, columnasNominas,nomina6)
    insertar_datos_generica(db, tabla7, columnasNominas,nomina7)


    # CREACION TABLA LISTA_GASTOS Y CREAR TRIGGER PARA ACTUALIZAR DATOS DE LA LISTA_GASTOS Y NOMINAS
    crear_tabla_Lista_gastos()
    actualizaciones_lista_gastos_Nominas()



    tabla8 = 'LISTA_GASTOS'
    columnasListaGastos = "ID_RELACION_NOMINAS,LUGAR,FECHA,DESCRIPCION,PRECIO"

    listaGastos1 ="1,'Zaldiaran','01/05/2021','Menú comida por trabajo',35"
    listaGastos2 ="1,'Café Victoria','02/05/2021','Café reunión de trabajo',1"
    listaGastos3 ="1,'Hotel Canciller Ayala','06/05/2021','Día entero de trabajo',65"
    listaGastos4 ="2,'Pintabocas','12/05/2021','Menú comida por trabajo',15"
    listaGastos5 ="2,'Café Plaza','02/05/2021','Café reunión de trabajo',2"
    listaGastos6 ="2,'Hotel Ciudad Vitoria','15/05/2021','Día entero de trabajo',45"
    listaGastos7 ="3,'Hotel Ciudad Vitoria','15/05/2021','Día entero de trabajo',489"



    insertar_datos_generica(db, tabla8, columnasListaGastos,listaGastos1)
    insertar_datos_generica(db, tabla8, columnasListaGastos,listaGastos2)
    insertar_datos_generica(db, tabla8, columnasListaGastos,listaGastos3)
    insertar_datos_generica(db, tabla8, columnasListaGastos,listaGastos4)
    insertar_datos_generica(db, tabla8, columnasListaGastos,listaGastos5)
    insertar_datos_generica(db, tabla8, columnasListaGastos,listaGastos6)
    insertar_datos_generica(db, tabla8, columnasListaGastos,listaGastos7)


    # CREAR TABLA CURSOS_FORMACION
    crear_tabla_Cursos_formacion()
    tablaCursos = 'CURSOS_FORMACION'
    columnasCursosFormacion = "ID_RELACION_TRABAJADOR,DESCRIPCION,HORAS,LUGAR,FECHA_INICIO,FECHA_FIN"

    cursoFormacion1 = "2,'Curso Final Cut Pro',25,'Egibide','02/06/2021','10/06/2021'"
    cursoFormacion2 = "2,'Curso Python',40,'CETIC','10/06/2021','20/06/2021'"
    cursoFormacion3 = "2,'Curso Fotografía Moda',100,'Escuela Fotografía','20/06/2021','20/07/2021'"
    insertar_datos_generica(db, tablaCursos, columnasCursosFormacion,cursoFormacion1)
    insertar_datos_generica(db, tablaCursos, columnasCursosFormacion,cursoFormacion2)
    insertar_datos_generica(db, tablaCursos, columnasCursosFormacion,cursoFormacion3)



    # CREAR TABLA PEDIDOS_COMPRAS (PADRE DE LISTA_COMPRAS) y TABLA LISTA_COMPRAS (HIJA DE PEDIDOS_COMPRAS) y TRIGGERS PARA SUBTOTAL LISTA_COMPRAS Y TOTAL DE PEDIDOS_COMPRAS
    crear_tabla_Pedidos_compras()
    crear_tabla_Lista_compras()
    actualizaciones_Subtotal_lista_compras()
    actualizaciones_Total_Pedido_compras()

    # INTRODUCCION DATOS TABLA 'PEDIDOS_COMPRAS'

    tabla9 = 'PEDIDOS_COMPRAS'
    columnasPedidoCompras = "ID_RELACION_PROVEEDOR,FECHA"

    pedidoCompra1 = "1,'02/05/2021'"
    pedidoCompra2 = "2,'02/05/2021'"
    pedidoCompra3 = "3,'02/05/2021'"
    insertar_datos_generica(db, tabla9, columnasPedidoCompras,pedidoCompra1)
    insertar_datos_generica(db, tabla9, columnasPedidoCompras,pedidoCompra2)
    insertar_datos_generica(db, tabla9, columnasPedidoCompras,pedidoCompra3)


   # INTRODUCCION DATOS TABLA 'LISTA_COMPRAS'

    tabla10 = 'LISTA_COMPRAS'
    columnasListaCompras = "ID_RELACION_PEDIDO_COMPRA,PRODUCTO,DESCRIPCION,PRECIO,UNIDADES"

    pedidoListaCompra1 = "1,'Fuji','Cámara mirrorless',1500,4"
    pedidoListaCompra2 = "1,'Objetivo Fuji','Objetivo mirrorless1',1800,1"
    pedidoListaCompra3 = "2,'Objetivo Fuji','Objetivo mirrorless2',1800,2"
    pedidoListaCompra4 = "2,'Objetivo Fuji','Objetivo mirrorless2',1800,2"
    pedidoListaCompra5 = "3,'Objetivo Fuji','Objetivo mirrorless',1800,7"
    insertar_datos_generica(db, tabla10, columnasListaCompras,pedidoListaCompra1)
    insertar_datos_generica(db, tabla10, columnasListaCompras,pedidoListaCompra2)
    insertar_datos_generica(db, tabla10, columnasListaCompras,pedidoListaCompra3)
    insertar_datos_generica(db, tabla10, columnasListaCompras,pedidoListaCompra4)
    insertar_datos_generica(db, tabla10, columnasListaCompras,pedidoListaCompra5)

    # CREAR TABLA PADRE 'PEDIDOS_VENTAS' TABLA HIJA 'LISTA_VENTAS' Y EL TRIGGER QUE HACE REFERENCIA A AMBAS TABLAS 'ACTUALIZACIONES_VENTAS'
    crear_tabla_Pedidos_ventas()
    crear_tabla_Lista_ventas()
    actualizaciones_ventas()


    # INTRODUCCION DATOS TABLA 'PEDIDOS_VENTAS'

    tabla11 = 'PEDIDOS_VENTAS'
    columnasPedidoVentas = "ID_RELACION_CLIENTE,FECHA"

    pedidoVenta1 = "1,'02/05/2021'"
    pedidoVenta2 = "2,'02/02/2021'"
    pedidoVenta3 = "3,'03/04/2021'"
    pedidoVenta4 = "4,'14/08/2021'"
    pedidoVenta5 = "3,'12/05/2021'"
    pedidoVenta6 = "3,'23/08/2021'"
    pedidoVenta7 = "5,'11/01/2021'"
    pedidoVenta8 = "6,'12/02/2021'"
    pedidoVenta9 = "6,'30/03/2021'"
    pedidoVenta10 = "2,'08/05/2021'"
    insertar_datos_generica(db, tabla11, columnasPedidoVentas,pedidoVenta1)
    insertar_datos_generica(db, tabla11, columnasPedidoVentas,pedidoVenta2)
    insertar_datos_generica(db, tabla11, columnasPedidoVentas,pedidoVenta3)
    insertar_datos_generica(db, tabla11, columnasPedidoVentas,pedidoVenta4)
    insertar_datos_generica(db, tabla11, columnasPedidoVentas,pedidoVenta5)
    insertar_datos_generica(db, tabla11, columnasPedidoVentas,pedidoVenta6)
    insertar_datos_generica(db, tabla11, columnasPedidoVentas,pedidoVenta7)
    insertar_datos_generica(db, tabla11, columnasPedidoVentas,pedidoVenta8)
    insertar_datos_generica(db, tabla11, columnasPedidoVentas,pedidoVenta9)
    insertar_datos_generica(db, tabla11, columnasPedidoVentas,pedidoVenta10)

    # INTRODUCCION DATOS TABLA 'LISTA_VENTAS'

    tabla12 = 'LISTA_VENTAS'
    columnasListaVentas = "ID_RELACION_PEDIDO_VENTA,PRODUCTO,DESCRIPCION,PRECIO,UNIDADES"

    productoVenta1 = "'Eventos BBC','Bodas bautizos y comuniones',1500"
    productoVenta2 = "'Restauración Fotos','En papel o digital 1 hora',250"
    productoVenta3 = "'Catálogo de fotos','Digitalizar fotos 1 hora',100"
    productoVenta4 = "'Rueda de prensa','Rueda de prensa 1 hora',150"
    productoVenta5 = "'Eventos deportivos','Evento deportivo 3 horas',500"
    productoVenta6 = "'Reportaje fotográfico','Reportaje fotográfico 4 horas',600"
    productoVenta7 = "'Reportaje especial','Reportaje fotográfico 8 horas',1000"
    productoVenta8 = "'Banco imágenes','Comprar fotos web 1 foto',100"
    productoVenta9 = "'Elecciones seguimiento','Seguimiento candidato/a 1 día',1000"
    productoVenta10 = "'Fuji XT-4 ocasión','En buen estado 1 unidad',1000"
    productoVenta11 = "'iMac 27 5K ocasión','En buen estado 1 unidad',1000"
    productoVenta12 = "'Fujinon 16-80 F4 ocasión','En buen estado 1 unidad',500"
    productoVenta13 = "'Fujinon 100-400 F4-F5.6 ocasión','En buen estado 1 unidad',500"
    productoVenta14 = "'Macbook Pro 16 ocasión','En buen estado 1 unidad',1000"

    # Pedido 1 Cliente 1
    pedidoListaVenta1 = "1,'Reportaje fotográfico','Reportaje fotográfico 4 horas',600,2"
    pedidoListaVenta2 = "1,'Rueda de prensa','Rueda de prensa 1 hora',150,1"

    # Pedido 2 Cliente 2
    pedidoListaVenta3 = "2,'Rueda de prensa','Rueda de prensa 1 hora',150,3"
    pedidoListaVenta4 = "2,'Reportaje fotográfico','Reportaje fotográfico 4 horas',600,2"
    pedidoListaVenta5 = "2,'Restauración Fotos','En papel o digital 1 hora',250,3"
    pedidoListaVenta6 = "2,'Banco imágenes','Comprar fotos web 1 foto',100,12"

    # Pedido 3 Cliente 3
    pedidoListaVenta7 = "3,'Rueda de prensa','Rueda de prensa 1 hora',150,2"
    pedidoListaVenta8 = "3,'Reportaje fotográfico','Reportaje fotográfico 4 horas',600,3"
    pedidoListaVenta9 = "3,'Elecciones seguimiento','Seguimiento candidato/a 1 día',1000,7"

    # Pedido 4 Cliente 4
    pedidoListaVenta10 = "4,'Eventos deportivos','Evento deportivo 3 horas',500,2"
    pedidoListaVenta11 = "4,'Reportaje fotográfico','Reportaje fotográfico 4 horas',600,3"

    # Pedido 5 Cliente 3
    pedidoListaVenta12 = "5,'Banco imágenes','Comprar fotos web 1 foto',100,23"

    # Pedido 6 Cliente 3
    pedidoListaVenta13 = "6,'Rueda de prensa','Rueda de prensa 1 hora',150,2"
    pedidoListaVenta14 = "6,'Reportaje fotográfico','Reportaje fotográfico 4 horas',600,4"
    pedidoListaVenta15 = "6,'Restauración Fotos','En papel o digital 1 hora',250,10"
    pedidoListaVenta16 = "6,'Elecciones seguimiento','Seguimiento candidato/a 1 día',1000,15"
    pedidoListaVenta17 = "6,'Banco imágenes','Comprar fotos web 1 foto',100,85"

    # Pedido 7 Cliente 5
    pedidoListaVenta18 = "7,'Reportaje especial','Reportaje fotográfico 8 horas',1000,3"
    pedidoListaVenta19 = "7,'Eventos deportivos','Evento deportivo 3 horas',500,2"
    pedidoListaVenta20 = "7,'Restauración Fotos','En papel o digital 1 hora',250,10"

    # Pedido 8 Cliente 6
    pedidoListaVenta21 = "8,'Restauración Fotos','En papel o digital 1 hora',250,35"

    # Pedido 9 Cliente 6
    pedidoListaVenta22 = "9,'Restauración Fotos','En papel o digital 1 hora',250,23"
    pedidoListaVenta23 = "9,'Eventos deportivos','Evento deportivo 3 horas',500,3"
    pedidoListaVenta24 = "9,'Reportaje fotográfico','Reportaje fotográfico 4 horas',600,10"
    pedidoListaVenta25 = "9,'Elecciones seguimiento','Seguimiento candidato/a 1 día',1000,15"


    # Pedido 10 Cliente 2
    pedidoListaVenta26 = "10,'Elecciones seguimiento','Seguimiento candidato/a 1 día',1000,5"
    pedidoListaVenta27 = "10,'Eventos deportivos','Evento deportivo 3 horas',500,3"
    pedidoListaVenta28 = "10,'Reportaje especial','Reportaje fotográfico 8 horas',1000,2"
    pedidoListaVenta29 = "10,'Reportaje fotográfico','Reportaje fotográfico 4 horas',600,8"
    pedidoListaVenta30 = "10,'Restauración Fotos','En papel o digital 1 hora',250,20"
    pedidoListaVenta31 = "10,'Rueda de prensa','Rueda de prensa 1 hora',150,14"
    pedidoListaVenta32 = "10,'Banco imágenes','Comprar fotos web 1 foto',100,85"



    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta1)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta2)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta3)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta4)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta5)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta6)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta7)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta8)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta9)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta10)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta11)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta12)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta13)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta14)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta15)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta16)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta17)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta18)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta19)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta20)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta21)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta22)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta23)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta24)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta25)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta26)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta27)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta28)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta29)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta30)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta31)
    insertar_datos_generica(db, tabla12, columnasListaVentas,pedidoListaVenta32)


#creacion_todas_tablas_con_datos()


# CONSULTA DIRECTA CONTRA UNA TABLA (USUARIOS)
def mostrar_departamentos_usuarios(usuario, password):

    # Establecemos conexión con la base de datos
    db = pymysql.connect(host="127.0.0.1", user="Pixels", db="Pixels", port=3306, password="12345Abcde")
    # Preparar el cursor
    cursor = db.cursor()
    #CONSULTA
    consulta = 'SELECT * FROM USUARIOS'
    # Ejecutar SQL --> es un string
    cursor.execute(consulta)

    # Recoger más de un dato con fetchall()
    # Resultados es una tupla de tuplas --> Guarda una tupla por cada registro de la tabla
    resultados = cursor.fetchall()
    #print(resultados)

    correcto =False

    #En este caso, sabemos qué columnas y datos tiene la tabla en concreto
    for registro in resultados:
        if registro[4] == usuario and registro[5] == password:
            correcto = True
            departamentos = registro[6].split(" , ")
            contador = 0
            print(color.BLUE+"Hola "+usuario.upper()+ ", bienvenido a Pixels, elige un departamento: \n"+color.END)
            for dep in departamentos:
                contador +=1
                print(color.BLUE+"\t"+str(contador)+ " " +color.END+dep)

            print()

            respuesta = input(color.BLUE+"Escribe el nombre del Departamento: "+color.END)

            print()

            while respuesta.lower() != "ventas" and  respuesta.lower() != "compras" and  respuesta.lower() != "pagos" and  respuesta.lower() != "gastos" and  respuesta.lower() != "rrhh" and  respuesta.lower() != "presupuestos" and  respuesta.lower() != "accesos":
                print(color.RED+"Error !!... introduce el nombre del departamento !!"+ color.END)
                respuesta = input(color.BLUE+"Escribe el nombre del Departamento: "+color.END)

            if respuesta.lower() == "ventas":
                print(color.BLUE+"Bienvenido al departamento de Ventas"+color.END)
                mostrarMenuVentas(usuario)

            if respuesta.lower() == "compras":
                print(color.BLUE+"Bienvenido al departamento de Compras"+color.END)
                mostrarMenuCompras(usuario)
            if respuesta.lower()== "presupuestos":
                print(color.BLUE+"Bienvenido al departamento de Presupuestos: "+color.END)
                mostrarMenuPresupuestos(usuario)

            if respuesta.lower()== "pagos":
                print(color.BLUE+"Bienvenido al departamento de Pagos: "+color.END)
                pagarFacturas(usuario)

            if respuesta.lower()== "gastos":
                print(color.BLUE+"Bienvenido al departamento de Gastos: "+color.END)
                eligeGastos(usuario)

            if respuesta.lower()== "rrhh":
                print(color.BLUE+"Bienvenido al departamento de RRHH: "+color.END)
                MenuRRHH(usuario)

            if respuesta.lower()== "accesos":
                print(color.BLUE+"Bienvenido al departamento de Accesos: "+color.END)
                MenuAccesos(usuario)




    if not correcto:
            print (color.RED+"No existe el usuario/a "+usuario.upper()+ " y/o la contraseña es incorrecta"+color.END)

            # REGISTRO DE LA CONSULTA DE LA BASE DE DATOS
            registroBD(usuario,"Error de logueo 'usuario' y/o 'contraseña' en la conexión a la aplicación PIXELS")


    #CERRAMOS CONEXIÓN
    db.close()

    return correcto


