package com.company;

import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;


public class Carga {


    // *******************************************************************************   LOGGERS REGISTROS  *******************************************************************************

    public static void escribirLog(String mensaje) {
        Logger logger = Logger.getLogger("Juanan");
        FileHandler fh;

        try {
            fh = new FileHandler("Registros.log", true);

            logger.addHandler(fh);
            logger.setUseParentHandlers(false);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.log(Level.INFO, mensaje);

            fh.close();

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }


    // ***************************************************************  VARIABLES GLOBALES ***************************************************************

    static Scanner teclado = new Scanner(System.in);
    static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
    static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/ColeccionDiscos"; //URI colección
    static String usu = "admin"; //Usuario
    static String usuPwd = ""; //Clave
    static Collection col = null;


    // ***************************************************************   MENÚ   ***************************************************************************

    public static void Menu(String persona, String password) throws IOException, ClassNotFoundException, XMLDBException {

        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int respuesta = 0;
        boolean correcto;

        do {

            do {
                correcto = true;
                try {
                    System.out.println("\nElige una opción: \n\n" +
                            "\t1 Crear usuario/a nuevo/a (admin)\n" +
                            "\t2 Borrar usuario/a y todas sus valoraciones (admin)\n" +
                            "\t3 Crear Disco nuevo\n" +
                            "\t4 Borrar Disco y todas sus valoraciones (admin)\n" +
                            "\t5 Valorar un disco\n" +
                            "\t6 Ver valoraciones de un disco\n" +
                            "\t7 Ver valoraciones del usuario actual\n" +
                            "\t8 Modificar valoraciones del usuario actual\n" +
                            "\t9 Borrar valoraciones del usuario actual\n" +
                            "\t10 Ver valoraciones de cualquier usuario (admin)\n" +
                            "\t11 Ranking discos más escuchados\n" +
                            "\t12 Salir\n"
                    );

                    System.out.print("Opción: ");

                    respuesta = Integer.parseInt(br.readLine());

                } catch (NumberFormatException e) {
                    System.out.println("Error !! ... elige una opción correcta [1-12] ");
                    correcto = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (!correcto);


            switch (respuesta) {
                case 1:
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 1 del menú para añadir personas");
                    anadirPersona(persona, password);
                    break;
                case 2:
                    borrarPersonayValoraciones(persona, password);
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 2 del menú para borrar personas y sus valoraciones");
                    break;
                case 3:
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 3 del menú para añadir discos");
                    anadirDisco();
                    break;
                case 4:
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 4 del menú para borrar disco y sus valoraciones");
                    borrarDiscoyValoraciones(persona, password);
                    break;
                case 5:
                    // Le paso como parámetro de entrada la persona que entra como usuario/a para añadirla a la valoración del disco
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 5 del menú para añadir valoraciones");
                    anadirValoraciones(persona);
                    break;
                case 6:
                    // Visualizar Valoraciones de un disco con las Personas y su puntuacion
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 6 del menú para visualizar Valoraciones de un disco con las Personas y su puntuacion");
                    verValoracionesDisco();
                    break;
                case 7:
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 7 del menú para ver valoraciones de una persona");
                    verValoracionesPersona(persona);
                    break;
                case 8:
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 8 del menú para modificar valoración");
                    modificarValoracionesPersona(persona);
                    break;
                case 9:
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 9 del menú para borrar valoración");
                    borrarValoracionPersona(persona);
                    break;
                case 10:
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 10 del menú para ver valoraciones de cualquier persona");
                    verValoracionesCualquierPersona(persona, password);
                    break;
                case 11:
                    escribirLog("Usuario: "+persona+", Entrando  en la opcion 11 del menú para consultar el ranking");
                    ejecutarconsultaficheroRanking("Consultas/Ranking.xq");
                    crearRankingXML();
                    break;
                case 12:
                    escribirLog("Usuario: "+persona+", saliendo del menú");
                    System.out.println("Adios, nos vemos en la próxima... que tengas buen día !!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error !! ... elige una opción correcta [1-7] ");

            }


        } while (true);


    }

    // *******************************************************************************  CONEXIÓN ***************************************************************************


    public static Collection conectar() {

        try {

            Class cl = Class.forName(driver); //Cargar del driver
            Database database = (Database) cl.getDeclaredConstructor().newInstance(); //Instancia de la BD
            DatabaseManager.registerDatabase(database); //Registro del driver
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            return col;
        } catch (XMLDBException e) {
            System.out.println("Error al inicializar la BD eXist.");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error en el driver.");
            //e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Error al instanciar la BD.");
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Error al instanciar la BD.");
            //e.printStackTrace();
        } catch (InvocationTargetException e) {
            //e.printStackTrace();
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
        }
        return null;
    }

    // ***************************************************************************  CREAR COLECCIÓN Y SUBIR ARCHIVOS XML *****************************************************************************

    // AL CREAR LA COLECCIÓN Y LOS ARCHIVOS XML, LUEGO HAY QUE DAR AL BOTON DE GUARDAR A CADA ARCHIVO XML, DESDE LA BASE EXIST, PARA QUE SALGAN BIEN TABULADOS LOS ARCHIVOS XML POR CONSOLA


    public static void crearcoleccysubirarchivo(String colecc) throws XMLDBException {


        // Estos datos hay que ponerlos aquí la primera vez que se crea la colección, porque sino da error  luego se tiran de las variables globales, que están declaradas arriba.

        String driver = "org.exist.xmldb.DatabaseImpl";
        Collection col = null;
        String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db";
        String usu = "admin";
        String usuPwd = "";

        try {
            Class cl = Class.forName(driver);
            Database database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            if (col != null) {
                // CREAR COLECCION dentro de col,
                CollectionManagementService mgtService = (CollectionManagementService) col
                        .getService("CollectionManagementService", "1.0");
                mgtService.createCollection(colecc);
                System.out.println("\n *** COLECCION CREADA: '" + colecc + "'");
            }
            // Nos posicionamos en la nueva colección y añadimos el archivo
            // Si es un fichero binario ponemos BinaryResource
            URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/" + colecc;
            col = DatabaseManager.getCollection(URI, usu, usuPwd);

            File personasXML = new File("ColeccionDiscosXMLvacios/Personas.xml");
            File discosXML = new File("ColeccionDiscosXMLvacios/Discos.xml");
            File valoracionesXML = new File("ColeccionDiscosXMLvacios/Valoraciones.xml");
            //File rankingXML = new File("ColeccionDiscosXMLvacios/Ranking.xml");

            // Creo los archivos con una etiqueta raiz
            crearFicheroXML(personasXML, "Personas");
            crearFicheroXML(discosXML, "Discos");
            crearFicheroXML(valoracionesXML, "Valoraciones");
            //crearFicheroXML(rankingXML, "Ranking");

            // Creo un ArrayList de ficheros File y añado los XML
            ArrayList<File> archivosXML = new ArrayList<>();
            archivosXML.add(personasXML);
            archivosXML.add(discosXML);
            archivosXML.add(valoracionesXML);
            //archivosXML.add(rankingXML);


            System.out.println("\n *** CREACIÓN DE FICHEROS XML: ***");

            // Añado los archivos del ArrayList a la colección
            for (File archivo : archivosXML) {
                if (!archivo.canRead()) {
                    System.out.println("ERROR AL LEER EL FICHERO");
                } else {
                    //System.out.println("ARCHIVO: " + archivo);

                    Resource nuevoRecurso = col.createResource(archivo.getName(), "XMLResource");
                    nuevoRecurso.setContent(archivo);
                    col.storeResource(nuevoRecurso);
                    System.out.println("\tFICHERO '" + archivo + "' AÑADIDO");
                }

            }


        } catch (Exception e) {
            System.out.println("Error al inicializar la BD eXist");
            escribirLog("Error al inicializar la BD eXist");
        }
    }

    public static void crearcoleccysubirarchivoConDatos(String colecc) throws XMLDBException {

        // Estos datos hay que ponerlos aquí la primera vez que se crea la colección, porque sino da error  luego se tiran de las variables globales, que están declaradas arriba.

        String driver = "org.exist.xmldb.DatabaseImpl";
        Collection col = null;
        String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db";
        String usu = "admin";
        String usuPwd = "";

        try {
            Class cl = Class.forName(driver);
            Database database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            if (col != null) {
                // CREAR COLECCION dentro de col,
                CollectionManagementService mgtService = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
                mgtService.createCollection(colecc);
                System.out.println("\n *** COLECCION CREADA: '" + colecc + "'");
            }
            // Nos posicionamos en la nueva colección y añadimos el archivo
            // Si es un fichero binario ponemos BinaryResource
            URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/" + colecc;
            col = DatabaseManager.getCollection(URI, usu, usuPwd);

            File personasXML = new File("ColeccionDiscosXMLconDatos/Personas.xml");
            File discosXML = new File("ColeccionDiscosXMLconDatos/Discos.xml");
            File valoracionesXML = new File("ColeccionDiscosXMLconDatos/Valoraciones.xml");
            File rankingXML = new File("ColeccionDiscosXMLconDatos/Ranking.xml");


            // Creo un ArrayList de ficheros File y añado los XML
            ArrayList<File> archivosXML = new ArrayList<>();
            archivosXML.add(personasXML);
            archivosXML.add(discosXML);
            archivosXML.add(valoracionesXML);
            //archivosXML.add(rankingXML); // No hace falta que lo cargue lo creo en la misma aplicacion al sacar el ranking


            System.out.println("\n *** CREACIÓN DE FICHEROS XML: ***");

            // Añado los archivos del ArrayList a la colección
            for (File archivo : archivosXML) {
                if (!archivo.canRead()) {
                    System.out.println("ERROR AL LEER EL FICHERO");
                } else {
                    //System.out.println("ARCHIVO: " + archivo);

                    Resource nuevoRecurso = col.createResource(archivo.getName(), "XMLResource");
                    nuevoRecurso.setContent(archivo);
                    col.storeResource(nuevoRecurso);
                    System.out.println("\tFICHERO '" + archivo + "' AÑADIDO");
                }

            }

            escribirLog("Colección creada y archivos XML subidos");


        } catch (Exception e) {
            System.out.println("Error al inicializar la BD eXist" + e.getMessage());
            escribirLog("Error al inicializar la BD eXist" + e.getMessage());
        }
    }

    public static void crearFicheroXML(File archivo, String etiquetas) throws IOException {

        if (!archivo.exists()) {
            archivo.createNewFile(); // Creo el fichero nuevo
            FileWriter escribirFichero = new FileWriter(archivo);
            BufferedWriter ficheroLineas = new BufferedWriter(escribirFichero);
            ficheroLineas.write("<?xml version='1.0' encoding='UTF-8'?>" + "\n");
            ficheroLineas.write("<" + etiquetas + ">"); // Escribe una línea
            ficheroLineas.newLine(); // Salto de línea
            ficheroLineas.write("</" + etiquetas + ">"); // Escribe una línea
            ficheroLineas.close();
            escribirFichero.close();
            escribirLog("Creado fichero "+archivo);
        }

    }

    public static void entrarUsuario() throws IOException, ClassNotFoundException, XMLDBException {


        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // ListaPersonas listaper = new ListaPersonas();
        String nombre = "";
        String password = "";

        System.out.print("Nombre: ");
        nombre = br.readLine();
        System.out.print("Password: ");
        password = br.readLine();

        String passwordCifradoMenuInicio = DigestUtils.sha256Hex(password);
        //System.out.println("PasswordCifrado: "+passwordCifradoMenuInicio);

        String passwordBD = mirarContrasena(nombre);
        //System.out.println("PasswordBD: " + passwordBD);


        if (passwordCifradoMenuInicio.equals(passwordBD)) {
            System.out.println("\n************************** DISCOS.NET *************************");
            System.out.println("Bienvenido/a " + nombre.toUpperCase() + " a 'Discos.net'");

            // Poner el log antes de que salte al menú, sino no lo coge
            escribirLog("Bienvenido/a " + nombre.toUpperCase() + " a 'Discos.net'");

            Menu(nombre, password);


        } else {
            System.out.println("No existe el usuario/a " + nombre + "!!\n");
            escribirLog("No existe el usuario/a " + nombre + "!!\n");
        }


    }


    // ************************************************************************************  CRUD PERSONAS.XML **************************************************************************************

    public static void cargarPersonas() {

        System.out.println("\n *** INSERCION DE PERSONAS: *** ");

        insertarPersona(1, "Ana", "Ruiz", "1234", 55, 2);
        insertarPersona(2, "Luis Miguel", "Romero", "1234", 46, 3);
        insertarPersona(3, "Juanan", "Ruiz", "1234", 50, 2);
        insertarPersona(4, "Paco", "López", "1234", 49, 2);
        insertarPersona(5, "Rosa", "Martinez", "1234", 26, 2);
        insertarPersona(6, "Antonio", "Ruiz", "1234", 86, 7);
        insertarPersona(7, "Sergio", "Pérez", "1234", 50, 23);
        insertarPersona(8, "Paki", "Lopez", "1234", 23, 2);
        insertarPersona(9, "Juanjo", "Ramirez", "1234", 45, 5);
        insertarPersona(10, "Patxi", "Ruiz", "1234", 56, 12);


    }

    public static void insertarPersona(int idpersona, String nombre, String apellidos, String password, int edad, int discosEscuchados) {

        String nuevaPersona =
                "<DatosPersona>" +
                        "<IDpersona>" + idpersona + "</IDpersona>" +
                        "<nombrePersona>" + nombre + "</nombrePersona>" +
                        "<ApellidosPersona>" + apellidos + "</ApellidosPersona>" +
                        "<password>" + password + "</password>" +
                        "<edad>" + edad + "</edad>" +
                        "<discosEscuchados>" + discosEscuchados + "</discosEscuchados>" +
                        "</DatosPersona>";

        if (conectar() != null) { // Comprobamos la conexión
            if (!comprobarPersona(idpersona)) { // Comprobamos duplicados
                try {
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    System.out.printf("\tInserto: %s \n", nuevaPersona);
                    //Consulta para insertar --> update insert ... into
                    ResourceSet result = servicio.query("update insert " + nuevaPersona + " into /Personas");
                    col.close(); //borramos
                    System.out.println("\tPersona '" + nombre.toUpperCase() + " " + apellidos.toUpperCase() + "' insertada.");
                    escribirLog("Persona '" + nombre.toUpperCase() + " " + apellidos.toUpperCase() + "' insertada.");

                } catch (Exception e) {
                    System.out.println("Error al insertar persona.");
                    escribirLog("Error al insertar persona.");
                    //e.printStackTrace();
                }

            } else {
                System.out.println("La persona '" + nombre.toUpperCase() + " " + apellidos.toUpperCase() + "' YA EXISTE.");
                escribirLog("La persona '" + nombre.toUpperCase() + " " + apellidos.toUpperCase() + "' YA EXISTE.");
            }

        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
            escribirLog("Error en la conexión. Comprueba datos.");
        }
    }

    public static void anadirPersona(String usuario, String password) throws IOException {

        if (usuario.equalsIgnoreCase("admin") && password.equalsIgnoreCase("0000")) {

            int idpersona = 0;
            String nombre = "";
            String apellidos = "";
            String passwordBD = "";
            int edad = 0;
            int discosEscuchados = 0;
            String respuesta = "";

            do {

                // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                idpersona = idMAXPersona() + 1;

                //System.out.print("Nombre: ");
                nombre = comprobarString("Nombre: ");
                while (comprobarNombrePersona(nombre)) {
                    System.out.println("La persona '" + nombre.toUpperCase() + "' YA EXISTE.");
                    escribirLog("La persona '" + nombre.toUpperCase() + "' YA EXISTE.");
                    nombre = comprobarString("Nombre: ");
                }


                apellidos = comprobarString("Apellidos: ");


                passwordBD = comprobarString("Password: ");
                password = DigestUtils.sha256Hex(passwordBD);

                edad = numeroCorrecto("Edad: ", edad);

                String nuevaPersona =
                        "<DatosPersona>" +
                                "<IDpersona>" + idpersona + "</IDpersona>" +
                                "<nombrePersona>" + nombre + "</nombrePersona>" +
                                "<ApellidosPersona>" + apellidos + "</ApellidosPersona>" +
                                "<password>" + password + "</password>" +
                                "<edad>" + edad + "</edad>" +
                                "<discosEscuchados>" + discosEscuchados + "</discosEscuchados>" +
                                "</DatosPersona>";


                if (conectar() != null) { // Comprobamos la conexión

                    try {

                        XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                        System.out.printf("\tInserto: %s \n", nuevaPersona);
                        //Consulta para insertar --> update insert ... into
                        ResourceSet result = servicio.query("update insert " + nuevaPersona + " into /Personas");
                        col.close(); //borramos
                        System.out.println("\tPersona '" + nombre.toUpperCase() + " " + apellidos.toUpperCase() + "' insertada.");

                        listarPersonas();

                        escribirLog("Usuario: "+usuario+" --> Persona '" + nombre.toUpperCase() + " " + apellidos.toUpperCase() + "' insertada.");

                    } catch (Exception e) {
                        System.out.println("Error al insertar persona.");
                        escribirLog("Usuario: "+usuario+ "--> 'Error al insertar persona '" + nombre.toUpperCase() + " " + apellidos.toUpperCase()+"'");
                        //e.printStackTrace();
                    }


                } else {
                    System.out.println("Error en la conexión. Comprueba datos.");
                    escribirLog("Error en la conexión. Comprueba datos.");
                }

                do {

                    System.out.print("\n¿Quieres añadir más personas (s/n) ? ");
                    respuesta = br.readLine();
                    if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                        System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
                    }
                } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));

            } while (respuesta.equalsIgnoreCase("s"));


        } else {
            System.out.println("\nAcceso denegado !!, solo puede añadir nuevos usuarios el administrador... ");
            escribirLog("Acceso denegado !!, solo puede añadir nuevos usuarios el administrador... ");

        }
    }

    public static void modificarPersona(int idPersona, String nombre) {

        if (comprobarPersona(idPersona)) {

            if (conectar() != null) {
                try {
                    System.out.printf("Actualizo la persona: %s\n", idPersona);
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    //Consulta para modificar/actualizar un valor --> update value
                    ResourceSet result = servicio.query(
                            "update value /Personas/DatosPersona[IDpersona=" + idPersona + "]/nombrePersona with data('" + nombre + "') ");

                    col.close();
                    System.out.println("Persona actualizada.");
                    escribirLog("Persona actualizada.");

                } catch (Exception e) {
                    System.out.println("Error al actualizar.");
                    escribirLog("Error al actualizar.");
                    //e.printStackTrace();
                }
            } else {
                System.out.println("Error en la conexión. Comprueba datos.");
                escribirLog("Error en la conexión. Comprueba datos.");
            }
        } else {
            System.out.println("La persona '" + nombre.toUpperCase() + "' NO EXISTE.");
            escribirLog("La persona '" + nombre.toUpperCase() + "' NO EXISTE.");
        }
    }

    public static void borrarPersona(int idPersona) {

        if (comprobarPersona(idPersona)) {
            if (conectar() != null) {
                try {
                    String nombrePersonaBorrada = personaSeleccionada(idPersona);
                    System.out.println("Borro la persona: " + nombrePersonaBorrada);
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");

                    //Consulta para borrar una persona --> update delete
                    //ResourceSet result = servicio.query("update delete /Personas/DatosPersona[IDpersona=" + idPersona + "]");

                    //Consulta para reemplazar un texto  por un nodo seleccionado, es como borrarlo una persona --> update replace
                    ResourceSet result = servicio.query("update replace /Personas/DatosPersona[IDpersona=" + idPersona + "]  with text {'Persona borrada: " + nombrePersonaBorrada + "'}");

                    col.close();
                    System.out.println("Persona con ID: "+idPersona+" borrada.");
                    escribirLog("Persona con ID: "+idPersona+" borrada.");


                } catch (Exception e) {
                    System.out.println("Error al borrar.");
                    escribirLog("Error al borrar la persona con el ID: "+idPersona);
                    //e.printStackTrace();
                }
            } else {
                System.out.println("Error en la conexión. Comprueba datos.");
                escribirLog("Error en la conexión. Comprueba datos.");
            }
        } else {
            System.out.println("La persona con el ID: " + idPersona + " NO EXISTE.");
            escribirLog("La persona con el ID: " + idPersona + " NO EXISTE.");
        }


    }

    public static void borrarValoracionesPersona(int idPersona) {


        try {
            Class cl = Class.forName(driver); // Cargar del driver
            Database database = (Database) cl.newInstance(); // Instancia de la
            // BD
            DatabaseManager.registerDatabase(database); // Registro del driver
            Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);
            System.out.println("\nConvirtiendo el fichero a cadena...");

            String consulta = "for $ValoracionesPersona in (/Valoraciones/DatosValoracion) where $ValoracionesPersona/persona/DatosPersona[IDpersona=" + idPersona + "]\n" +
                    "let $personaID:= $ValoracionesPersona/IDEscucha\n" +
                    "let $nombre:= $ValoracionesPersona/persona/DatosPersona/nombrePersona/text()\n" +
                    "return update replace $ValoracionesPersona with text {'Borrada valoración ID: ',$personaID,'de ',$nombre}";

            System.out.println("Consulta: " + consulta);
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = servicio.query(consulta);
            ResourceIterator i; // se utiliza para recorrer un set de recursos
            i = result.getIterator();

/*
            if (!i.hasMoreResources()) {
                //System.out.println("\nEl usuario/a " +  personaSeleccionadas + " ha sido borrado/a y todas sus valoraciones ...");
            }


*/

        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR EN EL DRIVER.");
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("ERROR AL CREAR LA INSTANCIA.");
        } catch (XMLDBException ex) {
            System.out.println("ERROR AL OPERAR CON EXIST.");
        }

    }

    public static void borrarValoracionPersona(String persona) throws IOException {

        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int idvaloracion = 0;
        String respuesta = "";

        do {

            // Si tiene como minimo una valoración, puede borrar sino vuelve al menú principal
            if (contarValoracionesPersona(persona) > 0) {

                // Sacamos la lista de valoraciones del usuario actual
                verValoracionesPersona(persona);

                idvaloracion = numeroCorrecto("Elige valoración: ", idvaloracion);


                // comprobamos que existe la persona seleccionada
                if (comprobarValoracionyPersona(idvaloracion, persona)) {
                    if (conectar() != null) {
                        try {
                            System.out.printf("Borro la valoracion: %s\n", idvaloracion);
                            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                            //Consulta para borrar un departamento --> update delete
                            ResourceSet result = servicio.query(
                                    "update delete /Valoraciones/DatosValoracion[IDEscucha=" + idvaloracion + "]");
                            col.close();
                            System.out.println("Valoración borrada.");
                            escribirLog("valoración ID: "+idvaloracion+" borrada");

                        } catch (Exception e) {
                            System.out.println("Error al borrar la valoración ID: "+idvaloracion);
                            escribirLog("Error al borrar la valoración ID: "+idvaloracion);
                            //e.printStackTrace();
                        }
                    } else {
                        System.out.println("Error en la conexión. Comprueba datos.");
                        escribirLog("Error en la conexión. Comprueba datos.");
                    }
                } else {
                    System.out.println("La valoración " + idvaloracion + " del usuario " + persona.toUpperCase() + "  NO EXISTE.");
                    escribirLog("La valoración " + idvaloracion + " del usuario " + persona.toUpperCase() + "  NO EXISTE.");
                }

                do {

                    System.out.print("\n¿Quieres borrar más valoraciones (s/n) ? ");
                    respuesta = br.readLine();
                    if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                        System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
                    }
                } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
            } else {

                System.out.println("\nEl usuario actual '" + persona.toUpperCase() + "' no tiene valoraciones ...");
                escribirLog("El usuario actual '" + persona.toUpperCase() + "' no tiene valoraciones ...");
                break; // Fuerzo a salir, porque sino se queda en un bucle.
            }

        } while (respuesta.equalsIgnoreCase("s"));


    }

    public static void modificarValoracionesPersona(String persona) throws IOException {

        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Si tiene como minimo una valoración, puede borrar sino vuelve al menú principal
        if (contarValoracionesPersona(persona) > 0) {

            String respuesta = "";

            do {

                // Sacamos la lista de valoraciones del usuario actual
                verValoracionesPersona(persona);
                int idvaloracion = 0;
                int puntuacion = 0;


                idvaloracion = numeroCorrecto("Elige valoración: ", idvaloracion);


                while (!comprobarValoracionyPersona(idvaloracion, persona)) {
                    System.out.println("El usuario actual NO tiene la valoración " + idvaloracion);
                    idvaloracion = numeroCorrecto("Elige valoración: ", idvaloracion);

                }


                puntuacion = numeroCorrectoEntreLimites("Nueva valoración: ", puntuacion, 1, 5);


                if (conectar() != null) {

                    try {
                        System.out.println("Actualizo la valoración: " + idvaloracion + " del usuario '" + persona.toUpperCase() + "': Anterior valor: " + comprobarValorValoracion(idvaloracion) + ", Nuevo valor: " + puntuacion);
                        XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                        //Consulta para modificar/actualizar un valor --> update value
                        ResourceSet result = servicio.query(
                                "update value /Valoraciones/DatosValoracion[IDEscucha=" + idvaloracion + "]/puntuacionDisco with data(" + puntuacion + ") ");

                        col.close();
                        System.out.println("Valoración ID: "+idvaloracion+" actualizada");
                        escribirLog("Valoración ID: "+idvaloracion+" actualizada");
                    } catch (Exception e) {
                        System.out.println("Error al actualizar valoración ID: "+idvaloracion);
                        escribirLog("Error al actualizar valoración ID: "+idvaloracion);
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("Error en la conexión. Comprueba datos");
                }


                do {

                    System.out.print("\n¿Quieres realizar más modificaciones (s/n) ? ");
                    respuesta = br.readLine();
                    if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                        System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
                    }
                } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));


            } while (respuesta.equalsIgnoreCase("s"));


        } else {
            System.out.println("\nEl usuario actual '" + persona.toUpperCase() + "' no tiene valoraciones ...");
            escribirLog("El usuario actual '" + persona.toUpperCase() + "' no tiene valoraciones ...");
        }


    }


    public static void borrarPersonayValoraciones(String usuario, String password) throws IOException {

        if (usuario.equalsIgnoreCase("admin") && password.equalsIgnoreCase("0000") && contarPersonas() > 0) {

            // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int opcion = 0;
            String respuesta = "";

            do {
                // Elegimos persona
                listarPersonas();

                // Elijo la persona, si la opción que elijo, osea el ID, no corresponde a ninguna persona, salta un error ...
                opcion = numeroCorrecto("Elige una persona: ", opcion);

                // Consigo el nombre de la persona seleccionada, ANTES DE BORRARLA, porque sino no tengo acceso a su ID
                String nombrePersonaSeleccionada = personaSeleccionada(opcion);

                // COMPROBAR QUE NO SE BORRA EL ADMINISTRADOR --> el ID 7 corresponde con el administrador
                if (opcion != 7) {

                    // Borramos Persona
                    borrarPersona(opcion);

                    // Borramos las valoraciones de la persona que hemos borrado
                    borrarValoracionesPersona(opcion);

                    System.out.println("\nEl usuario/a '" + nombrePersonaSeleccionada + "' ha sido borrado/a y todas sus valoraciones ...");
                    escribirLog("El usuario/a '" + nombrePersonaSeleccionada + "' ha sido borrado/a y todas sus valoraciones ...");


                } else {
                    System.out.println("\nNO PUEDES BORRAR AL ADMINISTRADOR !!!");
                    escribirLog("NO PUEDES BORRAR AL ADMINISTRADOR !!!");
                }


                do {

                    System.out.print("\n¿Quieres borrar más usuarios/as (s/n) ? ");
                    respuesta = br.readLine();
                    if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                        System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
                    }
                } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));

            } while (respuesta.equalsIgnoreCase("s") && contarPersonas() > 0);


            // Lista actualizada de personas
            listarPersonas();


        } else if (contarPersonas() == 0) {
            System.out.println("\nNO EXISTEN USUARIOS !!");
            escribirLog("NO EXISTEN USUARIOS !!");
        } else {
            System.out.println("\nAcceso denegado !!, solo puede borrar usuarios/as el administrador... ");
            escribirLog("Acceso denegado !!, solo puede borrar usuarios/as el administrador... ");
        }

    }

    public static void borrarLineasBlanco() {

        // Esto solo funciona para archivos XML que conocemos la ruta , a eXist no poedmos entrar
        try {


            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            File xmlFile = new File("fichero.xml");
            Document doc = builder.parse(xmlFile);

            // Remove Code
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); ++i) {
                Node node = nl.item(i);
                node.getParentNode().removeChild(node);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(xmlFile));
        } catch (ParserConfigurationException e) {
            //e.printStackTrace();
        } catch (SAXException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (XPathExpressionException e) {
            //e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            //e.printStackTrace();
        } catch (TransformerException e) {
            //e.printStackTrace();
        }
    }

    public static boolean comprobarPersona(int idPersona) {
        //Devuelve true si la persona existe
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/Personas/DatosPersona[IDpersona=" + idPersona + "]");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;

    }

    public static boolean comprobarNombrePersona(String nombrePersona) {

        String persona = "";

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/Personas/DatosPersona[nombrePersona='" + nombrePersona + "']/nombrePersona/text()");
                ResourceIterator i;
                i = result.getIterator();
                col.close();

                if (!i.hasMoreResources()) {


                    return false;

                } else {
                    // Si existe una persona con el mismo nombre, salta a quí
                    Resource r2 = i.nextResource();
                    persona = (String) r2.getContent();

                    return true;

                }

            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;


    }

    public static String personaValoracion(String nombrePersona) {

        String persona = "";

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/Personas/DatosPersona[nombrePersona='" + nombrePersona + "']");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {

                } else {
                    Resource r = i.nextResource();
                    persona = (String) r.getContent();

                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        System.out.println(persona);

        return persona;

    }

    public static String personaSeleccionada(int idPersona) {

        String resultado = "";

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/Personas/DatosPersona[IDpersona=" + idPersona + "]/nombrePersona/text()");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    System.out.println("LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");

                } else {
                    //System.out.println("\n----------------------------------------------------------------------------------------");
                    //System.out.println("Persona seleccionada (ID): "+idPersona);
                    Resource r = i.nextResource();
                    resultado = (String) r.getContent();
                    //System.out.println((String) r.getContent());
                    //System.out.println("----------------------------------------------------------------------------------------\n");
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return resultado;

    }

    public static void listarPersonas() {

        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("for $persona in /Personas/DatosPersona return concat($persona/IDpersona,'  ',$persona/nombrePersona)");
                // Recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();

                if (!i.hasMoreResources()) {
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }
                System.out.println("\n=============================================================================================================================\n");
                System.out.format("%75s\n", "LISTA ACTUALIZADA DE PERSONAS.XML\n");
                System.out.format("%20s%30s     %-20s\n", " ", "ID", "NOMBRE");
                while (i.hasMoreResources()) {

                    Resource r = i.nextResource();

                    String resultado = (String) r.getContent();
                    String[] parts = resultado.split("  ");
                    String idpersona = parts[0]; // Obtengo el ID
                    String nombre = parts[1]; // Obtengo persona

                    System.out.format("%20s%30s     %-20s\n", " ", idpersona, nombre);
                }
                System.out.println("\n=============================================================================================================================\n");
                col.close();
            } catch (XMLDBException e) {
                System.out.println("ERROR AL CONSULTAR DOCUMENTO.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

    }

    public static int idMAXPersona() {

        int idMax = 0;
        int idMaxporVuelta = 0;
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/Personas/DatosPersona/max(IDpersona)");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    //return false;

                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();

                    //System.out.println("Elemento: " + (String) r.getContent());

                    idMaxporVuelta = Integer.parseInt((String) r.getContent());

                    if (idMaxporVuelta > idMax) {
                        idMax = idMaxporVuelta;
                    }

                }
            } catch (Exception e) {
                System.out.println("Error al consultar." + e);
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        //System.out.println("ID Persona máximo: " + idMax);

        return idMax;

    }

    public static int contarPersonas() {

        int numeroPersonas = 0;

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("count(/Personas/DatosPersona)");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    //return false;

                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();

                    //System.out.println("Elemento: " + (String) r.getContent());

                    numeroPersonas = Integer.parseInt((String) r.getContent());

                }
            } catch (Exception e) {
                System.out.println("Error al consultar." + e);
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        //System.out.println("Número personas: " + numeroPersonas);

        return numeroPersonas;

    }

    public static String mirarContrasena(String persona) {

        String password = "";

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/Personas/DatosPersona[nombrePersona='" + persona + "']/password/text()");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    //return false;

                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();

                    //System.out.println("Elemento: " + (String) r.getContent());

                    password = (String) r.getContent();

                }
            } catch (Exception e) {
                System.out.println("Error al consultar." + e);
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        //System.out.println("Password: " + password);

        return password;

    }


    // *******************************************************************************  CONSULTAS DISCOS Y PERSONAS **********************************************************************


    public static void verValoracionesDisco() throws IOException {

        if (contarDiscos() > 0) {

            int opcion = 0;
            String disco = "";
            String ruta = "";
            String respuesta = "";

            do {

                // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                // Elegir Disco ( Sacar discos por pantalla y elegir uno)
                listarDiscos();
                opcion = numeroCorrectoEntreLimites("Elige un disco: ", opcion, 1, contarDiscos());
                disco = nombreDiscoElegidoValoracion(opcion); // Para sacar solo el nombre del disco

                if (comprobarDiscoValoracion(disco)) {

                    // CONSULTA DIRECTA SIN RECURRIR A UN :XQ
                    //ejecutarconsultaDiscoPersonas(disco);


                    // CONSULTA CREANDO UN FICHERO:XQ Y LUEGO HACEMOS LA CONSULTA

                    String consulta = "for $disco in (/Valoraciones/DatosValoracion) where $disco/disco/DatosDisco[nombreDisco='" + disco + "']\n" +
                            "return concat ('Personas que han escuchado \"',$disco/disco/DatosDisco/nombreDisco/text(),'\": ', $disco/persona/DatosPersona/nombrePersona/text(),', Puntuación: ', $disco/puntuacionDisco)";

                    // Creamos el archivo de consulta y le escribimos la consulta
                    ruta = "Consultas/consulta" + disco + ".xq";
                    ruta = ruta.replace(" ", ""); // Quitamos los espacios si los hubiera
                    File archivo = new File(ruta);

                    archivo.createNewFile(); // Creo el fichero nuevo
                    FileWriter escribirFichero = new FileWriter(archivo);
                    BufferedWriter ficheroLineas = new BufferedWriter(escribirFichero);
                    ficheroLineas.write(consulta); // Escribe una línea
                    ficheroLineas.close();
                    escribirFichero.close();

                    // Ejecutamos la consulta
                    ejecutarconsultafichero(ruta);
                } else {
                    System.out.println("\nEl disco '" + disco.toUpperCase() + "' no tiene valoraciones ...\n");
                    escribirLog("El disco '" + disco.toUpperCase() + "' no tiene valoraciones ...");
                }

                do {

                    System.out.print("¿Quieres ver más valoraciones de discos (s/n) ? ");
                    respuesta = br.readLine();
                    if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                        System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
                    }
                } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));


            } while (respuesta.equalsIgnoreCase("s"));


        } else {
            System.out.println("\nNO EXISTEN DISCOS !!");
            escribirLog("NO EXISTEN DISCOS !!");
        }


    }

    public static void verValoracionesPersona(String persona) throws IOException {

        if (comprobarnombreValoracion(persona)) {
            String ruta = "";

            // CONSULTA DIRECTA SIN RECURRIR A UN :XQ
            //ejecutarconsultaDiscoPersonas(disco);


            // CONSULTA CREANDO UN FICHERO:XQ Y LUEGO HACEMOS LA CONSULTA


            String consulta = "for $disco in (/Valoraciones/DatosValoracion) where $disco/persona/DatosPersona[nombrePersona='" + persona + "']\n" +
                    "return concat ('ID: ', $disco/IDEscucha, ',  Usuario: ',$disco/persona/DatosPersona/nombrePersona/text(),', Disco: ','\"',$disco/disco/DatosDisco/nombreDisco/text(),'\", Puntuación: ', $disco/puntuacionDisco)";

            // Creamos el archivo de consulta y le escribimos la consulta
            ruta = "Consultas/consulta" + persona + ".xq";
            ruta = ruta.replace(" ", ""); // Quitamos los espacios si los hubiera
            File archivo = new File(ruta);

            archivo.createNewFile(); // Creo el fichero nuevo
            FileWriter escribirFichero = new FileWriter(archivo);
            BufferedWriter ficheroLineas = new BufferedWriter(escribirFichero);
            ficheroLineas.write(consulta); // Escribe una línea
            ficheroLineas.close();
            escribirFichero.close();

            // Ejecutamos la consulta

            ejecutarconsultafichero(ruta);
        } else {
            System.out.println("\nEl usuario actual '" + persona.toUpperCase() + "' no tiene valoraciones ...");
            escribirLog("El usuario actual '" + persona.toUpperCase() + "' no tiene valoraciones ...");
        }


    }

    public static void verValoracionesCualquierPersona(String persona, String password) throws IOException {

        if (persona.equalsIgnoreCase("admin") && password.equalsIgnoreCase("0000")) {

            // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String respuesta = "";

            do {
                String ruta = "";
                int opcion = 0;
                String personaSeleccionada = "";

                // CONSULTA DIRECTA SIN RECURRIR A UN :XQ
                //ejecutarconsultaDiscoPersonas(disco);

                // CONSULTA CREANDO UN FICHERO:XQ Y LUEGO HACEMOS LA CONSULTA

                listarPersonas();
                opcion = numeroCorrecto("Elige una persona: ", opcion);
                personaSeleccionada = personaSeleccionada(opcion); // Para sacar solo el nombre de la persona
                System.out.println("Persona seleccionada: " + personaSeleccionada);

                if (comprobarnombreValoracion(personaSeleccionada)) {
                    String consulta = "for $disco in (/Valoraciones/DatosValoracion) where $disco/persona/DatosPersona[nombrePersona='" + personaSeleccionada + "']\n" +
                            "return concat ('Discos escuchados por ',$disco/persona/DatosPersona/nombrePersona/text(),': \"',$disco/disco/DatosDisco/nombreDisco/text(),'\", Puntuación: ', $disco/puntuacionDisco)";

                    // Creamos el archivo de consulta y le escribimos la consulta
                    ruta = "Consultas/consulta" + persona + ".xq";
                    ruta = ruta.replace(" ", ""); // Quitamos los espacios si los hubiera
                    File archivo = new File(ruta);

                    archivo.createNewFile(); // Creo el fichero nuevo
                    FileWriter escribirFichero = new FileWriter(archivo);
                    BufferedWriter ficheroLineas = new BufferedWriter(escribirFichero);
                    ficheroLineas.write(consulta); // Escribe una línea
                    ficheroLineas.close();
                    escribirFichero.close();

                    // Ejecutamos la consulta

                    ejecutarconsultafichero(ruta);


                } else {
                    System.out.println("\nEl usuario actual '" + personaSeleccionada.toUpperCase() + "' no tiene valoraciones ...\n");
                    escribirLog("El usuario actual '" + personaSeleccionada.toUpperCase() + "' no tiene valoraciones ...");
                }

                do {

                    System.out.print("¿Quieres ver más valoraciones de usuarios (s/n) ? ");
                    respuesta = br.readLine();
                    if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                        System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
                    }
                } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));


            } while (respuesta.equalsIgnoreCase("s"));


        } else {
            System.out.println("Acceso denegado !! ... solo puede acceder el aministrador a las valoraciones de otros usuarios ...");
            escribirLog("Acceso denegado !! ... solo puede acceder el aministrador a las valoraciones de otros usuarios ...");
        }


    }

    // *******************************************************************************  CRUD DISCOS.XML **********************************************************************


    public static void cargarDiscos() {

        System.out.println("\n *** INSERCION DE DISCOS: *** ");

        insertarDisco(1, "Thriller", "Michael Jackson", "42:19", 1982, "Pop",
                new String[]{" 1 Wanna Be Startin Somethin", " 2 Baby Be Mine", " 3 The Girl Is Mine (With Paul McCartney)", " 4 Thriller", " 5 Beat It", " 6 Billie Jean", " 7 Human Nature", " 8 P.Y.T. (Pretty Young Thing)", " 9 The Lady In My Life"},
                1);
        insertarDisco(2, "Unplugged", "Eric Clapton", "61:47", 1992, "Rock",
                new String[]{" 1 Signe", " 2 Before you acusse me", " 3 Hey hey", " 4 Tears in heaven", " 5 Lonely Stranger", " 6 Nobody knows you when you are down and out", " 7 Layla", " 8 Running on faith", " 9 Walkin` blues", "10 Alberta", "11 San Francisco Bay Blues", "12 Malted Milk", "13 Old love"},
                1);
        insertarDisco(3, "The Piano", "Michael Nyman", "61:47", 1993, "Clásica",
                new String[]{" 1 To The Edge Of The Earth", " 2 Big My Secret", " 3 A Wild And Distant Shore", " 4 The Heart Asks Pleasure First", " 5 Here To There", " 6 The Promise", " 7 A Bed Of Ferns", " 8 The Fling", " 9 The Scent Of Love blues", "10 Deep Into The Forest", "11 The Mood That Passes Through You", "12 Lost And Found", "13 The Embrace", "14 Little Impulse", "15 The Sacrifice", "16 I Clipped Your Wing", "17 The Wounded", "18 All Imperfect Things", "19 Dreams Of A Journey"},
                1);
        insertarDisco(4, "Master Of Puppets", "Metallica", "54:44", 1986, "Trash",
                new String[]{" 1 Battery", " 2 Master Of Puppets", " 3 The Thing That Should Not Be", " 4 Welcome Home (Sanitarium)", " 5 Disposable Heroes", " 6 Leper Messiah", " 7 Orion", " 8 The Fling", " 9 Damage, Inc."},
                1);
        insertarDisco(5, "Kortatu (Albúm)", "Kortatu", "33:21", 1985, "Punk,Ska",
                new String[]{" 1 Don Vito Y La Revuelta En El Frenopatico", " 2 Jimmy Jazz", " 3 La Cultura", " 4 Nicaragua Sandinista", " 5 Zu Atrapatu Arte", " 6 Tolosa Inauteriak", " 7 Hernani 15-6-84", " 8 Sospechosos", " 9 Sarri Sarri", "10 La Familia Iscariote", "11 Tatuado", "12 Mr. Snoid Entre Sus Amigos Los Humanos", "13 Desmond Tutu"},
                1);
        insertarDisco(6, "Appetite For Destruction", "Guns N' Roses", "53:52", 1987, "Hard Rock",
                new String[]{" 1 Welcome To The Jungle", " 2 It's So Easy", " 3 Nightrain", " 4 Out Ta Get Me", " 5 Mr. Brownstone", " 6 Paradise City", " 7 My Michelle", " 8 Think About You", " 9 Sweet Child O' Mine", "10 You're Crazy", "11 Anything Goes", "12 Rocket Queen"},
                1);
        insertarDisco(7, "A Night at the Opera", "Queen", "44:11", 1993, "Rock",
                new String[]{" 1 Death On Two Legs", " 2 Lazing On A Sunday Afternoon", " 3 I'm In Love With My Car", " 4 You're My Best Friend", " 5 '39", " 6 Sweet Lady", " 7 Seaside Rendezvous", " 8 The Prophet's Song", " 9 Love Of My Life", "10 Good Company", "11 Bohemian Rhapsody", "12 RGod Save The Queen"},
                1);
        insertarDisco(8, "The Dark Side of the Moon", "Pink Floyd", "42:59", 1973, "Rock",
                new String[]{" 1 Speak To Me", " 2 Breathe", " 3 On The Run", " 4 Time", " 5 The Great Gig In The Sky", " 6 Money", " 7 Us And Them", " 8 Any Colour You Like", " 9 Brain Damage", "10 Eclipse"},
                1);
        insertarDisco(9, "Abbey Road", "The Beatles", "47:03", 1969, "Rock",
                new String[]{" 1 Come Together", " 2 Something", " 3 Maxwell's Silver Hammer", " 4 Oh! Darling", " 5 Octopus's Garden", " 6 I Want You (She's So Heavy)", " 7 Here Comes The Sun", " 8 Because", " 9 You Never Give Me Your Money", "10 Sun King", "11 Mean Mr. Mustard", "12 Polythene Pam", "13 She Came In Through The Bathroom Window", "14 Golden Slumbers", "15 Carry That Weight", "16 The End", "17 Her Majesty"},
                1);
        insertarDisco(10, "Nevermind", "Nirvana", "42:38", 1991, "Grunge",
                new String[]{" 1 Smells Like Teen Spirit", " 2 In Bloom", " 3 Come As You Are", " 4 Breed", " 5 Lithium", " 6 Polly", " 7 Territorial Pissings", " 8 Drain You", " 9 Lounge Act", "10 Stay Away", "11 On A Plain", "12 Something In The Way"},
                1);
        insertarDisco(11, "Back in Black", "AC/DC", "42:11", 1980, "Hard Rock",
                new String[]{" 1 Hell's Bells", " 2 Shoot To Thrill", " 3 What Do You Do For Money Honey", " 4 Given The Dog A Bone", " 5 Let Me Put My Love Into You", " 6 Back In Black", " 7 You Shook Me All Night Long", " 8 Have A Drink On Me", " 9 Shake A Leg", "10 Rock And Roll Ain't Noise Pollution"},
                1);
        insertarDisco(12, "Led Zeppelin IV", "Led Zeppelin", "42:34", 1978, "Rock Clásico",
                new String[]{" 1 Black Dog", " 2 Rock And Roll", " 3 The Battle Of Evermore", " 4 Stairway To Heaven", " 5 Misty Mountain Hop", " 6 Four Sticks", " 7 Going To California", " 8 When The Levee Breaks"},
                1);
        insertarDisco(13, "The Doors (Album)", "The Doors", "43:05", 1967, "Rock",
                new String[]{" 1 Break On Through (To The Other Side)", " 2 Soul Kitchen", " 3 The Crystal Ship", " 4 Twentieth Century Fox", " 5 Alabama Song (Whisky Bar)", " 6 Light My Fire", " 7 Back Door Man", " 8 I Looked At You", " 9 End Of The Night", "10 Take It As It Comes", "11 The End"},
                1);
        insertarDisco(14, "Sticky Fingers", "The Rolling Stones", "46:25", 1971, "Rock Clásico",
                new String[]{" 1 Brown Sugar", " 2 Sway", " 3 Wild Horses", " 4 Can’t You Hear Me Knocking", " 5 You Gotta Move", " 6 Bitch", " 7 I Got The Blues", " 8 Sister Morphine", " 9 Dead Flowers", "10 Moonlight Mile"},
                1);
        insertarDisco(15, "Exodus", "Bob Marley and The Wailers", "37:24", 1977, "Reggae",
                new String[]{" 1 Natural Mystic", " 2 So Much Things To Say", " 3 Guiltiness", " 4 The Heathen", " 5 Exodus", " 6 Jamming", " 7 Waiting In Vain", " 8 Turn Your Lights Down Low", " 9 Three Little Birds", "10 One Love / People Get Ready"},
                1);
        insertarDisco(16, "The Joshua Tree", "U2", "50:11", 1987, "Rock",
                new String[]{" 1 Where The Streets Have No Name", " 2 I Still Haven't Found What I'm Looking For", " 3 With Or Without You", " 4 Bullet The Blue Sky", " 5 Running To Stand Still", " 6 Red Hill Mining Town", " 7 In God's Country", " 8 Trip Through Your Wires", " 9 One Tree Hill", "10 Exit", "11 Mothers Of The Disappeared"},
                1);
        insertarDisco(17, "Like a Prayer", "Madonna", "51:16", 1989, "Pop",
                new String[]{" 1 Like A Prayer", " 2 I Like A Prayer", " 3 Love Song", " 4 Till Death Do Us Part", " 5 Promise To Try", " 6 Cherish", " 7 Dear Jessie", " 8 Oh Father", " 9 Keep It Together", "10 Spanish Eyes", "11 Act Of Contrition"},
                1);
        insertarDisco(18, "Ten", "Pearl Jam", "53:20", 1991, "Grunge",
                new String[]{" 1 Once", " 2 Even Flow", " 3 Alive", " 4 Why Go", " 5 Black", " 6 Jeremy", " 7 Oceans", " 8 Porch", " 9 Garden", "10 Deep", "11 Release", "12 (silence)", "13 Master / Slave"},
                1);
        insertarDisco(19, "Vice Versa", "Rauw Alejandro", "44:12", 2021, "Reggae",
                new String[]{" 1 Todo De Ti", " 2 Sexo Virtual", " 3 Nubes", " 4 Track 4", " 5 2/Catorce", " 6 Aquel Nap ZzZz", " 7 Cúrame", " 8 Cosa Guapa", " 9 Desenfocao", "10 ¿Cuándo Fue?", "11 La Old Skul", "11 ¿Y Eso?", "12 Tengo un Pal", "13 Brazilera"},
                1);
        insertarDisco(20, "La Leyenda Del Tiempo", "Camarón", "37:02", 1979, "Flamenco",
                new String[]{" 1 La Leyenda Del Tiempo", " 2 Romance Del Amargo", " 3 Homenaje A Federico", " 4 Mi Niña Se Fue A La Mar", " 5 La Tarara", " 6 Volando Voy", " 7 Volando Voy", " 8 Viejo Mundo", " 9 Tangos De La Sultana", "10 Nana Del Caballo Grande"},
                1);
    }

    public static void insertarDisco(int idDisco, String nombreDisco, String cantante, String duracion, int anyo, String estilo, String[] canciones, int vecesEscuchado) {

        String cancion = "";
        for (int i = 0; i < canciones.length; i++) {
            cancion += "<string>" + canciones[i] + "</string>";

        }
        String nuevoDisco = "<DatosDisco>" +
                "<IDdisco>" + idDisco + "</IDdisco>" +
                "<nombreDisco>" + nombreDisco + "</nombreDisco>" +
                "<cantante>" + cantante + "</cantante>" +
                "<duracion>" + duracion + "</duracion>" +
                "<anyoDisco>" + anyo + "</anyoDisco>" +
                "<estilo>" + estilo + "</estilo>" +
                "<canciones>" + cancion + "</canciones>" +
                "<vecesDiscoEscuchado>" + vecesEscuchado + "</vecesDiscoEscuchado>" +
                "</DatosDisco>";

        if (conectar() != null) { // Comprobamos la conexión
            if (!comprobarDisco(idDisco)) { // Comprobamos duplicados
                try {
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    System.out.printf("\tInserto: %s \n", nuevoDisco);
                    //Consulta para insertar --> update insert ... into
                    ResourceSet result = servicio.query("update insert " + nuevoDisco + " into /Discos");
                    col.close(); //borramos
                    System.out.println("\tDisco '" + nombreDisco.toUpperCase() + " 'insertado.");
                    escribirLog("Disco '" + nombreDisco.toUpperCase() + " 'insertado.");
                } catch (Exception e) {
                    System.out.println("Error al insertar disco.");
                    //e.printStackTrace();
                }

            } else {
                System.out.println("El disco '" + nombreDisco.toUpperCase() + "' YA EXISTE.");
                escribirLog("El disco '" + nombreDisco.toUpperCase() + "' YA EXISTE.");
            }

        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

    }

    public static void anadirDisco() throws IOException {

        String respuesta = "";

        do {

            // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            int idDisco = idMAXDisco() + 1;

            String nombreDisco = comprobarString("Nombre del albúm: ");

            while (comprobarNombreDisco(nombreDisco)) {
                System.out.println("Error ... YA EXISTE el disco '" + nombreDisco.toUpperCase() + "'");
                escribirLog("Error ... YA EXISTE el disco '" + nombreDisco.toUpperCase() + "'");
                nombreDisco = comprobarString("Nombre del albúm: ");
            }

            String cantante = comprobarString("Cantante: ");

            String duracion = comprobarString("Duración: ");
            while (!formatoMinutosSegundos(duracion)) {
                duracion = comprobarString("Duración: ");
            }

            int anyo = 0;
            anyo = numeroCorrectoEntreLimites("Año: ", anyo, 1902, 2021);

            String estilo = elegirEstilo();

            int numCanciones = 0;
            numCanciones = numeroCorrecto("Número de canciones: ", numCanciones);

            ArrayList<String> canciones = new ArrayList<String>();
            String cancion = "";

            for (int i = 0; i < numCanciones; i++) {
                cancion += "<string> " + (i + 1) + " " + comprobarString("\tCancion(" + (i + 1) + "): ") + "</string>";
                canciones.add(cancion);

            }
            // Convertimos el ArrayList en una lista
            String[] listaCanciones = canciones.toArray(new String[0]);

            int vecesEscuchado = 1;


            String nuevoDisco = "<DatosDisco>" +
                    "<IDdisco>" + idDisco + "</IDdisco>" +
                    "<nombreDisco>" + nombreDisco + "</nombreDisco>" +
                    "<cantante>" + cantante + "</cantante>" +
                    "<duracion>" + duracion + "</duracion>" +
                    "<anyoDisco>" + anyo + "</anyoDisco>" +
                    "<estilo>" + estilo + "</estilo>" +
                    "<canciones>" + cancion + "</canciones>" +
                    "<vecesDiscoEscuchado>" + vecesEscuchado + "</vecesDiscoEscuchado>" +
                    "</DatosDisco>";

            if (conectar() != null) { // Comprobamos la conexión
                if (!comprobarDisco(idDisco)) { // Comprobamos duplicados
                    try {
                        XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                        System.out.printf("\tInserto: %s \n", nuevoDisco);
                        //Consulta para insertar --> update insert ... into
                        ResourceSet result = servicio.query("update insert " + nuevoDisco + " into /Discos");
                        col.close(); //borramos
                        System.out.println("\tDisco '" + nombreDisco.toUpperCase() + " 'insertado.");
                        escribirLog("Disco '" + nombreDisco.toUpperCase() + " 'insertado.");
                    } catch (Exception e) {
                        System.out.println("Error al insertar disco.");
                        escribirLog("Error al insertar disco.");
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("El disco '" + nombreDisco.toUpperCase() + "' YA EXISTE.");
                    escribirLog("El disco '" + nombreDisco.toUpperCase() + "' YA EXISTE.");
                }

            } else {
                System.out.println("Error en la conexión. Comprueba datos.");
            }


            do {

                System.out.print("\n¿Quieres añadir más discos (s/n) ? ");
                respuesta = br.readLine();
                if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                    System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
                }
            } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));

        } while (respuesta.equalsIgnoreCase("s"));


    }

    public static void modificarDisco(int idDisco, String nombreDisco) {

        if (comprobarDisco(idDisco)) {

            if (conectar() != null) {
                try {
                    System.out.printf("Actualizo la persona: %s\n", idDisco);
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    //Consulta para modificar/actualizar un valor --> update value
                    ResourceSet result = servicio.query(
                            "update value /Discos/DatosDisco[IDdisco=" + idDisco + "]/nombreDisco with data('" + nombreDisco + "')");

                    col.close();
                    System.out.println("Disco actualizado.");
                } catch (Exception e) {
                    System.out.println("Error al actualizar.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error en la conexión. Comprueba datos.");
            }
        } else {
            System.out.println("La persona '" + nombreDisco.toUpperCase() + "' NO EXISTE.");
        }
    }

    public static void borrarDisco(int idDisco) {
        if (comprobarDisco(idDisco)) {
            if (conectar() != null) {
                try {
                    String nombreDisco = nombreDiscoElegidoValoracion(idDisco);

                    System.out.println("Borro el disco: '" + nombreDisco + "'");
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    //Consulta para borrar un disco --> update delete
                    //ResourceSet result = servicio.query("update delete /Discos/DatosDisco[IDdisco=" + idDisco + "]");

                    // Consulta para reemplazar texto, sustituye el nodo seleccionado por texto, es como un borrado
                    ResourceSet result = servicio.query("update replace /Discos/DatosDisco[IDdisco=" + idDisco + "]  with text {'Disco borrado: \"" + nombreDisco + "\"'}");
                    col.close();
                    System.out.println("Disco ID: "+idDisco+" borrado.");
                    escribirLog("Disco ID: "+idDisco+" borrado.");
                } catch (Exception e) {
                    System.out.println("Error al borrar.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error en la conexión. Comprueba datos.");
            }
        } else {
            System.out.println("El disco " + idDisco + " NO EXISTE.");
            escribirLog("El disco " + idDisco + " NO EXISTE.");
        }

    }


    public static void borrarDiscoyValoraciones(String persona, String password) throws IOException {

        if (persona.equalsIgnoreCase("admin") && password.equalsIgnoreCase("0000") && contarDiscos() > 0) {

            // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String respuesta = "";

            do {
                int idDisco = 0;

                listarDiscos();
                idDisco = numeroCorrecto("Elige un disco: ", idDisco);


                // Elijo el nombre del disco antes de borrarlo

                String nombreDisco = nombreDiscoElegidoValoracion(idDisco);


                if (!nombreDisco.equalsIgnoreCase("")) {

                    // Borro disco
                    borrarDisco(idDisco);

                    // Borro valoraciones del disco

                    borrarValoracionesDisco(idDisco);

                    // Mensaje de confirmación

                    System.out.println("\nEl disco '" + nombreDisco + "' ha sido borrado y todas sus valoraciones ...");
                    escribirLog("El disco '" + nombreDisco + "' ha sido borrado y todas sus valoraciones ...");

                }


                do {

                    System.out.print("\n¿Quieres borrar más discos (s/n) ? ");
                    respuesta = br.readLine();
                    if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                        System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
                    }
                } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));

            } while (respuesta.equalsIgnoreCase("s") && contarDiscos() > 0);


            if (contarDiscos() > 0) {
                // Lista actualizada de discos
                listarDiscos();
            } else {
                System.out.println("\nNO HAY DISCOS !!!");
                escribirLog("NO HAY DISCOS !!!");
            }


        } else if (contarDiscos() == 0) {
            System.out.println("\nNO EXISTEN DISCOS !!");
            escribirLog("NO EXISTEN DISCOS !!");
        } else {
            System.out.println("\nAcceso denegado !!, solo puede borrar usuarios/as el administrador... ");
            escribirLog("Acceso denegado !!, solo puede borrar usuarios/as el administrador... ");
        }


    }


    public static void borrarValoracionesDisco(int idDisco) {

        try {
            Class cl = Class.forName(driver); // Cargar del driver
            Database database = (Database) cl.newInstance(); // Instancia de la
            // BD
            DatabaseManager.registerDatabase(database); // Registro del driver
            Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);
            System.out.println("\nConvirtiendo el fichero a cadena...");

            String consulta = "for $Valoraciones in (/Valoraciones/DatosValoracion) where $Valoraciones/disco/DatosDisco[IDdisco=" + idDisco + "]\n" +
                    "let $valoracionID:= $Valoraciones/IDEscucha\n" +
                    "let $nombreDisco:= $Valoraciones/disco/DatosDisco/nombreDisco/text()\n" +
                    "return update replace $Valoraciones with text {'Borrada valoración ID:',$valoracionID,'del disco: \"',$nombreDisco,'\"'}";

            System.out.println("Consulta: " + consulta);
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = servicio.query(consulta);
            ResourceIterator i; // se utiliza para recorrer un set de recursos
            i = result.getIterator();

/*
            if (!i.hasMoreResources()) {
                //System.out.println("\nEl usuario/a " +  personaSeleccionadas + " ha sido borrado/a y todas sus valoraciones ...");
            }
*/

        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR EN EL DRIVER.");
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("ERROR AL CREAR LA INSTANCIA.");
        } catch (XMLDBException ex) {
            System.out.println("ERROR AL OPERAR CON EXIST.");
        }


    }

    public static boolean comprobarDisco(int idDisco) {

        //Devuelve true si el disco existe
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/Discos/DatosDisco[IDdisco=" + idDisco + "]");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;

    }

    public static boolean comprobarNombreDisco(String nombreDisco) {

        //Devuelve true si el disco existe
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/Discos/DatosDisco[nombreDisco='" + nombreDisco + "']");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;

    }

    public static String discoValoracion(int idDisco) {

        String disco = "";

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar el nodo deuna ID de valoración
                ResourceSet result = servicio.query("/Discos/DatosDisco[IDdisco=" + idDisco + "]");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {

                } else {

                    Resource r = i.nextResource();
                    disco = (String) r.getContent();

                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return disco;

    }

    public static String nombreDiscoElegidoValoracion(int idDisco) {

        String disco = "";

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar el nombre del disco
                ResourceSet result = servicio.query("/Discos/DatosDisco[IDdisco=" + idDisco + "]/nombreDisco/text()");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {

                } else {

                    Resource r = i.nextResource();
                    disco = (String) r.getContent();

                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return disco;

    }

    public static String discoSeleccionado(int idDisco) {

        String resultado = "";

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/Discos/DatosDisco[IDdisco=" + idDisco + "]");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");

                } else {

                    //System.out.println("\n----------------------------------------------------------------------------------------");
                    //System.out.println("Disco seleccionado (ID): "+idDisco);
                    Resource r = i.nextResource();
                    resultado = (String) r.getContent();
                    //System.out.println((String) r.getContent());
                    //System.out.println("----------------------------------------------------------------------------------------\n");

                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return resultado;

    }

    public static void listarDiscosCanciones() {

        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("for $disco in /Discos/DatosDisco return concat($disco/IDdisco,'   ',$disco/nombreDisco,'   ',$disco/cantante,'   ',$disco/duracion,'   ',$disco/anyoDisco,'   ',$disco/estilo,'--',$disco/canciones)");
                // Recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();

                if (!i.hasMoreResources()) {
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }
                System.out.println("\n==========================================================================================================================================================================================\n");
                System.out.format("%60s%s\n", " ", "LISTA ACTUALIZADA DE DISCOS.XML\n");
                System.out.format("%10s%-5s%-30s%-30s%-20s%-10s%-20s %-100s\n", " ", "ID", "DISCO", "CANTANTE", "DURACIÓN", "AÑO", "ESTILO", "CANCIONES");
                while (i.hasMoreResources()) {

                    Resource r = i.nextResource();

                    String resultado = (String) r.getContent(); // Obtengo una fila pos cada nodo consultado de $disco
                    String[] parts = resultado.split("   "); // lo divido en una lista por los espacios entre palabras
                    String iddisco = parts[0]; // Obtengo el ID
                    String disco = parts[1]; // Obtengo disco
                    String cantante = parts[2]; // Obtengo cantante
                    String duracion = parts[3]; // Obtengo duración
                    String anyo = parts[4]; // Obtengo anyo
                    String estilo = parts[5]; // Obtengo estilo

                    String[] canciones = resultado.split("--\n"); // Obtengo 2 partes de la fila  separado por "--" --> ( iddisco, disco, cantante, duracion, anyo, estilo) +"--"+ (canciones)

                    // SI SE CODIFICA COMO '<?xml version='1.0' encoding='UTF-8'?>' en la cabecera del documento xml, se codifica bien
                    String[] lista = canciones[1].replace("  ", "").split("\n"); // Me quedo con la segunda parte que son las canciones y que quite los espacios antes de las canciones y el primer salto de línea

                    // SI SE CODIFICA COMO '<?xml version='1.0' encoding='ISO-8859-1'?>' en la cabecera del documento xml, hay que hacer replace, porque no lo codifica bien
                    //String[] lista = canciones[1].replace("  ", "").replace("Ãº","ú").replace("Ã±","ñ").replace("Ã¡","á").replace("Â","").split("\n"); // Me quedo con la segunda parte que son las canciones y que quite los espacios antes de las canciones y el primer salto de línea


/*                    // VISUALIZADO OPCION A --> SE  REPITEN EL NOMBRE DEL DISCO; CANTANTE....

                    for (int i1 = 0; i1 < lista.length; i1++) {
                        System.out.format("%10s%-5s%-30s%-30s%-20s%-10s%-20s%-100s\n", " ", iddisco, disco, cantante, duracion, anyo, estilo.replace("--\n", ""), lista[i1]);
                    }
                    System.out.println();

*/


                    // VISUALIZADO OPCION B --> SE LEEN LAS CANCIONES Y NO REPITEN EL NOMBRE DEL DISCO; CANTANTE....
                    String listaCan = "";
                    for (int i1 = 0; i1 < lista.length; i1++) {
                        if (i1 == 0) {
                            listaCan += lista[i1] + "\n";
                        } else {
                            listaCan += "                                                                                                                             " + lista[i1] + "\n";
                        }

                    }
                    System.out.format("%10s%-5s%-30s%-30s%-20s%-10s%-20s%-100s\n", " ", iddisco, disco, cantante, duracion, anyo, estilo.replace("--\n", ""), listaCan);


                }

                System.out.println("\n==========================================================================================================================================================================================\n");

                col.close();
            } catch (XMLDBException e) {
                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
                //e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

    }


    public static void listarDiscosRepetirDisco() {

        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("for $disco in /Discos/DatosDisco return concat($disco/IDdisco,'   ',$disco/nombreDisco,'   ',$disco/cantante,'   ',$disco/duracion,'   ',$disco/anyoDisco,'   ',$disco/estilo,'--',$disco/canciones)");
                // Recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();

                if (!i.hasMoreResources()) {
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }
                System.out.println("\n==========================================================================================================================================================================================\n");
                System.out.format("%60s%s\n", " ", "LISTA ACTUALIZADA DE DISCOS.XML\n");
                System.out.format("%10s%-5s%-30s%-30s%-20s%-10s%-20s %-100s\n", " ", "ID", "DISCO", "CANTANTE", "DURACIÓN", "AÑO", "ESTILO", "CANCIONES");
                while (i.hasMoreResources()) {

                    Resource r = i.nextResource();

                    String resultado = (String) r.getContent(); // Obtengo una fila pos cada nodo consultado de $disco
                    String[] parts = resultado.split("   "); // lo divido en una lista por los espacios entre palabras
                    String iddisco = parts[0]; // Obtengo el ID
                    String disco = parts[1]; // Obtengo disco
                    String cantante = parts[2]; // Obtengo cantante
                    String duracion = parts[3]; // Obtengo duración
                    String anyo = parts[4]; // Obtengo anyo
                    String estilo = parts[5]; // Obtengo estilo

                    String[] canciones = resultado.split("--\n"); // Obtengo 2 partes de la fila  separado por "--" --> ( iddisco, disco, cantante, duracion, anyo, estilo) +"--"+ (canciones)

                    // SI SE CODIFICA COMO '<?xml version='1.0' encoding='UTF-8'?>' en la cabecera del documento xml, se codifica bien
                    String[] lista = canciones[1].replace("  ", "").split("\n"); // Me quedo con la segunda parte que son las canciones y que quite los espacios antes de las canciones y el primer salto de línea

                    // SI SE CODIFICA COMO '<?xml version='1.0' encoding='ISO-8859-1'?>' en la cabecera del documento xml, hay que hacer replace, porque no lo codifica bien
                    //String[] lista = canciones[1].replace("  ", "").replace("Ãº","ú").replace("Ã±","ñ").replace("Ã¡","á").replace("Â","").split("\n"); // Me quedo con la segunda parte que son las canciones y que quite los espacios antes de las canciones y el primer salto de línea


                    // VISUALIZADO OPCION A --> SE  REPITEN EL NOMBRE DEL DISCO; CANTANTE....

                    for (int i1 = 0; i1 < lista.length; i1++) {
                        System.out.format("%10s%-5s%-30s%-30s%-20s%-10s%-20s%-100s\n", " ", iddisco, disco, cantante, duracion, anyo, estilo.replace("--\n", ""), lista[i1]);
                    }
                    System.out.println();


                }

                System.out.println("\n==========================================================================================================================================================================================\n");

                col.close();
            } catch (XMLDBException e) {
                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
                //e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

    }


    public static void listarDiscos() {

        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("for $disco in /Discos/DatosDisco return concat($disco/IDdisco,'   ',$disco/nombreDisco,'   ',$disco/cantante,'   ',$disco/duracion,'   ',$disco/anyoDisco,'   ',$disco/estilo,'--',$disco/canciones)");
                // Recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();

                if (!i.hasMoreResources()) {
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }
                System.out.println("\n==============================================================================================================================================\n");
                System.out.format("%60s%s\n", " ", "LISTA ACTUALIZADA DE DISCOS.XML\n");
                System.out.format("%10s%-5s%-30s%-30s%-20s%-10s%-20s\n", " ", "ID", "DISCO", "CANTANTE", "DURACIÓN", "AÑO", "ESTILO");
                while (i.hasMoreResources()) {

                    Resource r = i.nextResource();

                    String resultado = (String) r.getContent(); // Obtengo una fila pos cada nodo consultado de $disco
                    String[] parts = resultado.split("   "); // lo divido en una lista por los espacios entre palabras
                    String iddisco = parts[0]; // Obtengo el ID
                    String disco = parts[1]; // Obtengo disco
                    String cantante = parts[2]; // Obtengo cantante
                    String duracion = parts[3]; // Obtengo duración
                    String anyo = parts[4]; // Obtengo anyo
                    String estilo = parts[5]; // Obtengo estilo


                    // VISUALIZADO OPCION B --> SE LEEN LAS CANCIONES Y NO REPITEN EL NOMBRE DEL DISCO; CANTANTE....

                    System.out.format("%10s%-5s%-30s%-30s%-20s%-10s%-20s\n", " ", iddisco, disco, cantante, duracion, anyo, estilo.replaceAll("--", ""));


                }

                System.out.println("\n==============================================================================================================================================\n");

                col.close();
            } catch (XMLDBException e) {
                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
                //e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

    }

    public static int idMAXDisco() {

        int idMax = 0;
        int idMaxporVuelta = 0;
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar el ID máximo de la lista de discos
                ResourceSet result = servicio.query("/Discos/DatosDisco/max(IDdisco)");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    //return false;

                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();

                    //System.out.println("Elemento: " + (String) r.getContent());

                    idMaxporVuelta = Integer.parseInt((String) r.getContent());

                    if (idMaxporVuelta > idMax) {
                        idMax = idMaxporVuelta;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error al consultar." + e);
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        //System.out.println("ID máximo Disco: " + idMax);

        return idMax;

    }

    public static int contarDiscos() {

        int numeroDiscos = 0;

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("count(/Discos/DatosDisco)");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    //return false;

                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();

                    //System.out.println("Elemento: " + (String) r.getContent());

                    numeroDiscos = Integer.parseInt((String) r.getContent());

                }
            } catch (Exception e) {
                System.out.println("Error al consultar." + e);
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        //System.out.println("Número discos: " + numeroDiscos);

        return numeroDiscos;

    }


    // *******************************************************************************  CRUD VALORACIONES.XML **********************************************************************


    public static void cargarValoraciones() {

        System.out.println("\n *** INSERCION DE VALORACIONES: *** ");

        insertarValoracion(1, 1, 1, 5);
        insertarValoracion(2, 3, 1, 3);
        insertarValoracion(3, 4, 12, 4);
        insertarValoracion(4, 4, 20, 5);
        insertarValoracion(5, 5, 5, 3);
        insertarValoracion(6, 5, 4, 4);
        insertarValoracion(7, 5, 15, 5);
        insertarValoracion(8, 3, 10, 3);
        insertarValoracion(9, 3, 3, 5);
        insertarValoracion(10, 3, 17, 5);
        insertarValoracion(11, 1, 17, 5);
        insertarValoracion(12, 1, 19, 3);
        insertarValoracion(13, 4, 1, 5);
        insertarValoracion(14, 5, 19, 3);
        insertarValoracion(15, 5, 1, 4);
        insertarValoracion(16, 4, 10, 5);
        insertarValoracion(17, 1, 20, 5);
        insertarValoracion(18, 3, 20, 3);
        insertarValoracion(19, 3, 11, 3);
        insertarValoracion(20, 3, 16, 5);

    }

    public static void insertarValoracion(int idValoracion, int persona, int disco, int valoracion) {

        // Buscar el ID de la persona --> función que retorne los nodos de la persona pasándole como parámetro de entrada el ID

        String personaSeleccionada = personaSeleccionada(persona);

        // Buscar el ID del disco --> función que retorne los nodos del disco pasándole como parámetro de entrada el ID

        String discoSeleccionado = discoSeleccionado(disco);

        // Le pasamos todos los datos al nodo del XML <DatosValoracion>
        String nuevaValoracion = "<DatosValoracion>" +
                "<IDEscucha>" + idValoracion + "</IDEscucha>" +
                "<persona>" + personaSeleccionada + "</persona>" +
                "<disco>" + discoSeleccionado + " </disco>" +
                "<puntuacionDisco>" + valoracion + "</puntuacionDisco>" +
                "</DatosValoracion>";

        if (conectar() != null) { // Comprobamos la conexión
            if (!comprobarValoracion(idValoracion)) { // Comprobamos duplicados
                try {
                    XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                    System.out.printf("\tInserto: %s \n", nuevaValoracion);
                    //Consulta para insertar --> update insert ... into
                    ResourceSet result = servicio.query("update insert " + nuevaValoracion + " into /Valoraciones");
                    col.close(); //borramos
                    System.out.println("\tValoración '" + idValoracion + " 'insertado.");
                    escribirLog("Valoración '" + idValoracion + " 'insertado.");
                } catch (Exception e) {
                    System.out.println("Error al insertar Valoración ID: "+idValoracion);
                    escribirLog("Error al insertar Valoración ID: "+idValoracion);
                    e.printStackTrace();
                }

            } else {
                System.out.println("La valoración '" + idValoracion + "' YA EXISTE.");
                escribirLog("La valoración '" + idValoracion + "' YA EXISTE.");
            }

        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

    }

    public static void anadirValoraciones(String persona) throws IOException, EOFException, ClassNotFoundException {

        if (contarDiscos() > 0) {
            // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String respuesta;
            String personaValoracion = "";
            String discoValoracion = "";
            String disco = "";
            int opcion = 0;
            int idValoracion = 0;


            do {

                // EL ID es automático, por función:

                idValoracion = idMAXValoracion() + 1;

                // Persona que valora --> es la que ha entrado a la aplicación, lo paso por parámetro de entrada
                personaValoracion = personaValoracion(persona);

                // Elegir Disco ( Sacar discos por pantalla y elegir uno)
                listarDiscos();
                opcion = numeroCorrectoEntreLimites("Elige un disco: ", opcion, 1, contarDiscos());
                disco = nombreDiscoElegidoValoracion(opcion); // Para sacar solo el nombre del disco


                // Comprobamos duplicados
                while (comprobarDuplicadosValoracion(persona, disco)) {
                    System.out.println("Error !! ...NO PUEDES DUPLICAR VALORACIONES, ya tienes una valoración del disco '" + disco.toUpperCase() + "'");
                    escribirLog("Error !! ...NO PUEDES DUPLICAR VALORACIONES, ya tienes una valoración del disco '" + disco.toUpperCase() + "'");
                    opcion = numeroCorrectoEntreLimites("Opción: ", opcion, 1, contarDiscos());
                    disco = nombreDiscoElegidoValoracion(opcion); // Para sacr solo el nombre del disco

                }

                // Si es correcto, ahora si le paso el String del nodo del disco seleccionado
                discoValoracion = discoValoracion(opcion);

                // Añadimos valoración
                int valoracion = 0;
                valoracion = numeroCorrectoEntreLimites("Valoración: ", valoracion, 1, 5);


                // Insertamos valoración a la BD

                // Le pasamos todos los datos al nodo del XML <DatosValoracion>
                String nuevaValoracion = "<DatosValoracion>" +
                        "<IDEscucha>" + idValoracion + "</IDEscucha>" +
                        "<persona>" + personaValoracion + "</persona>" +
                        "<disco>" + discoValoracion + " </disco>" +
                        "<puntuacionDisco>" + valoracion + "</puntuacionDisco>" +
                        "</DatosValoracion>";

                if (conectar() != null) { // Comprobamos la conexión
                    if (!comprobarValoracion(idValoracion)) { // Comprobamos duplicados
                        try {
                            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                            System.out.println("\n-----------------------------------------------------------------------------------------------");
                            System.out.printf("Inserto: Valoracion %s\n\n\t%s\n\n", idValoracion, nuevaValoracion);

                            //Consulta para insertar --> update insert ... into
                            ResourceSet result = servicio.query("update insert " + nuevaValoracion + " into /Valoraciones");
                            col.close(); //borramos
                            System.out.println("-----------------------------------------------------------------------------------------------\n");
                            System.out.println("\nValoración '" + idValoracion + "' insertado.");
                            escribirLog("Valoración '" + idValoracion + "' insertado.");
                        } catch (Exception e) {
                            System.out.println("Error al insertar Valoración ID: "+idValoracion);
                            escribirLog("Error al insertar Valoración ID: "+idValoracion);
                            e.printStackTrace();
                        }

                    } else {
                        System.out.println("La valoración '" + idValoracion + "' YA EXISTE.");
                        escribirLog("La valoración '" + idValoracion + "' YA EXISTE.");
                    }

                } else {
                    System.out.println("Error en la conexión. Comprueba datos.");
                }


                do {

                    System.out.print("\n¿Quieres añadir más valoraciones (s/n) ? ");
                    respuesta = br.readLine();
                    if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                        System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
                    }
                } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));

            } while (respuesta.equalsIgnoreCase("s"));


            // Saco el listado de las valoraciones
            listarValoraciones();


        } else {
            System.out.println("\nNO EXISTEN DISCOS PARA VALORAR !!!");
            escribirLog("NO EXISTEN DISCOS PARA VALORAR !!!");
        }


    }

    public static boolean comprobarValoracion(int idValoracion) {

        //Devuelve true si la valoración existe
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");

                ResourceSet result = servicio.query("/Valoraciones/DatosValoracion[IDEscucha=" + idValoracion + "]");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;

    }

    public static int comprobarValorValoracion(int idValoracion) {

        int valoracion = 0;

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");

                ResourceSet result = servicio.query("/Valoraciones/DatosValoracion[IDEscucha=" + idValoracion + "]/puntuacionDisco/text()");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    //
                } else {

                    Resource r = i.nextResource();
                    valoracion = Integer.parseInt((String) r.getContent());

                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        //System.out.println("Valoracion antigua: "+valoracion);

        return valoracion;

    }

    public static boolean comprobarValoracionyPersona(int idValoracion, String persona) {

        //Devuelve true si la valoración existe
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");

                ResourceSet result = servicio.query("/Valoraciones/DatosValoracion[IDEscucha=" + idValoracion + " and persona/DatosPersona/nombrePersona='" + persona + "']");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;

    }


    public static boolean comprobarnombreValoracion(String persona) {

        //Devuelve true si el nombre de la persona existe
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");

                ResourceSet result = servicio.query("/Valoraciones/DatosValoracion/persona/DatosPersona[nombrePersona='" + persona + "']");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;

    }

    public static boolean comprobarDiscoValoracion(String disco) {

        //Devuelve true si el nombre de la persona existe
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");

                ResourceSet result = servicio.query("/Valoraciones/DatosValoracion/disco/DatosDisco[nombreDisco='" + disco + "']");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;

    }


    public static int contarValoracionesPersona(String persona) {

        int numeroValoracionesPersona = 0;

        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("count(/Valoraciones/DatosValoracion/persona/DatosPersona[nombrePersona='" + persona + "'])");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    //return false;

                }
                while (i.hasMoreResources()) {

                    Resource r = i.nextResource();
                    numeroValoracionesPersona = Integer.parseInt((String) r.getContent());

                }
            } catch (Exception e) {
                System.out.println("Error al consultar." + e);
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        //System.out.println("Número valoraciones personas: " + numeroValoracionesPersona);

        return numeroValoracionesPersona;

    }

    public static boolean comprobarDuplicadosValoracion(String persona, String disco) {

        //Devuelve true si la valoración existe
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de una valoración por persona y nombre disco
                ResourceSet result = servicio.query("/Valoraciones/DatosValoracion[persona/DatosPersona/nombrePersona='" + persona + "' and disco/DatosDisco/nombreDisco='" + disco + "']");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al consultar.");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return false;

    }

    public static void listarValoraciones() {
        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("for $valoracion in /Valoraciones/DatosValoracion return concat($valoracion/IDEscucha,'   ',$valoracion/persona/DatosPersona/nombrePersona, '   ',$valoracion/disco/DatosDisco/nombreDisco,'   ',$valoracion/puntuacionDisco)");
                // Recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();

                if (!i.hasMoreResources()) {
                    System.out.println("LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }
                System.out.println("\n=============================================================================================================================\n");
                System.out.format("%75s\n", "LISTA ACTUALIZADA DE VALORACIONES.XML\n");
                System.out.format("%20s%-5s%-20s%-40s%-10s\n", " ", "ID", "PERSONA", "DISCO", "PUNTUACION");
                while (i.hasMoreResources()) {

                    Resource r = i.nextResource();
                    String resultado = (String) r.getContent();
                    String[] parts = resultado.split("   ");
                    String idValoracion = parts[0]; // Obtengo el ID
                    String persona = parts[1]; // Obtengo persona
                    String disco = parts[2]; // Obtengo disco
                    String puntuacion = parts[3]; // Obtengo puntuacion

                    System.out.format("%20s%-5s%-20s%-40s    %-5s\n", " ", idValoracion, persona, disco, puntuacion);

                    //System.out.println((String) r.getContent());


                }
                System.out.println("\n=============================================================================================================================\n");
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
                //e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

    }

    public static String Valoraciones() {

        String resultado = "";
        if (conectar() != null) {
            try {
                XPathQueryService servicio;
                servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Preparamos la consulta
                ResourceSet result = servicio.query("for $valoracion in /Valoraciones/DatosValoracion return concat($valoracion/IDEscucha,'   ',$valoracion/persona/DatosPersona/nombrePersona, '   ',$valoracion/disco/DatosDisco/nombreDisco,'   ',$valoracion/puntuacionDisco)");
                // Recorrer los datos del recurso.
                ResourceIterator i;
                i = result.getIterator();

                if (!i.hasMoreResources()) {
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA O ESTÁ MAL ESCRITA");
                }
                System.out.println("\n============================================");
                System.out.println("LISTA ACTUALIZADA DE VALORACIONES.XML");
                System.out.println("ID  PERSONA   DISCO    PUNTUACION");
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    System.out.println((String) r.getContent());
                }
                System.out.println("============================================");
                col.close();
            } catch (XMLDBException e) {
                System.out.println(" ERROR AL CONSULTAR DOCUMENTO.");
               // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        return resultado;

    }

    public static int idMAXValoracion() {

        int idMax = 0;
        int idMaxporVuelta = 0;
        if (conectar() != null) {
            try {
                XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
                //Consulta para consultar la información de un departamento
                ResourceSet result = servicio.query("/Valoraciones/DatosValoracion/max(IDEscucha)");
                ResourceIterator i;
                i = result.getIterator();
                col.close();
                if (!i.hasMoreResources()) {
                    //return false;

                }
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();

                    //System.out.println("Elemento: " + (String) r.getContent());

                    idMaxporVuelta = Integer.parseInt((String) r.getContent());

                    if (idMaxporVuelta > idMax) {
                        idMax = idMaxporVuelta;
                    }

                }
            } catch (Exception e) {
                System.out.println("Error al consultar." + e);
                // e.printStackTrace();
            }
        } else {
            System.out.println("Error en la conexión. Comprueba datos.");
        }

        System.out.println("ID Persona máximo: " + idMax);

        return idMax;

    }


    // ************************************************************************************  RANKING **************************************************************************************

    public static void crearRankingXML() throws XMLDBException, IOException {

        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String respuesta = "";

        do {

            System.out.print("\n¿Quieres crear un archivo XML del Ranking (s/n) ? ");
            respuesta = br.readLine();
            if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
            }
        } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));


        if (respuesta.equalsIgnoreCase("s")) {
            try {
                Class cl = Class.forName(driver); //Cargar del driver
                Database database = (Database) cl.newInstance(); //Instancia de la BD
                // DatabaseManager.registerDatabase(database); //Registro del driver
            } catch (Exception e) {
                System.out.println("Error al inicializar la BD eXist");
                //e.printStackTrace();
            }
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            if (col == null) System.out.println(" *** LA COLECCION DB NO EXISTE. ***");
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = servicio.query("for $disco in /Valoraciones\n" +
                    "return \n" +
                    "  <discos>{\n" +
                    "    for $payment in $disco/DatosValoracion\n" +
                    "    let $nombre:= $payment/disco/DatosDisco/nombreDisco\n" +
                    "    group by $nombre\n" +
                    "    \n" +
                    "    order by sum($payment/puntuacionDisco) descending\n" +
                    "    \n" +
                    "    return <Puesto>\n" +
                    "         {$nombre}\n" +
                    "      <puntuacionDisco>{sum($payment/puntuacionDisco)}</puntuacionDisco>\n" +
                    "    </Puesto>\n" +
                    "  \n" +
                    "  }</discos>");

            // recorrer los datos de la consulta y los grabamos en un fichero externo

            String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());

            System.out.println("Archivo xml creado a las: " + timeStamp);

            String archivo = "Ranking/RankingValoracionesDiscos_" + LocalDateTime.now().getDayOfWeek() + LocalDateTime.now().getHour() + "_" + LocalDateTime.now().getMinute() + "_" + LocalDateTime.now().getSecond() + ".xml";
            File fichero = new File(archivo);
            if (fichero.exists()) {

                //borramos el archivo si existe y se crea de nuevo
                if (fichero.delete()) System.out.println("Archivo borrado. Creo de nuevo");
                else System.out.println("Error al borrar el archivo");

            }

            BufferedWriter bw;
            System.out.println("-------------------------------------------------");
            System.out.println("CREACIÓN DEL DOCUMENTO 'RankingValoracionesDiscos.xml' ");
            System.out.println("-------------------------------------------------");
            try {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write("<?xml version='1.0' encoding='UTF-8'?>" + "\n");
                bw.write("<Ranking>" + "\n");
                ResourceIterator i;
                i = result.getIterator();
                if (!i.hasMoreResources())
                    System.out.println(" LA CONSULTA NO DEVUELVE NADA.");
                while (i.hasMoreResources()) {
                    Resource r = i.nextResource();
                    bw.write((String) r.getContent() + "\n"); // grabamos en el fichero
                    System.out.println();
                }
                bw.write("</Ranking>" + "\n");
                bw.close(); // Cerramos el fichero el fichero
            } catch (IOException e) {
                // problemas con el fichero
                //e.printStackTrace();
            }

            respuesta = "";


            do {

                System.out.print("\n¿Quieres subir el archivo XML del Ranking a la colección (s/n) ? ");
                respuesta = br.readLine();
                if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
                    System.out.println("Error !! ... tienes que introducir 's' o 'n' ");
                }
            } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));


            if (respuesta.equalsIgnoreCase("s")) {

                try {

                    // Nos posicionamos en la nueva colección y a?adimos el archivo
                    // Si es un ficheo binario ponemos BinaryResource
                    URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/ColeccionDiscos";
                    col = DatabaseManager.getCollection(URI, usu, usuPwd);
                    System.out.println(col);
                    System.out.println(archivo);
                    //File archivo = new File("zonas.xml");
                    if (!fichero.canRead()) {
                        System.out.println("ERROR AL LEER EL FICHERO: "+fichero);
                        escribirLog("ERROR AL LEER EL FICHERO: "+fichero);
                    } else {
                        Resource nuevoRecurso = col.createResource(fichero.getName(), "XMLResource");
                        nuevoRecurso.setContent(fichero);
                        col.storeResource(nuevoRecurso);
                        System.out.println("FICHERO: '"+fichero+"' AÑADIDO");
                        escribirLog("FICHERO: '"+fichero+"' AÑADIDO");
                    }
                } catch (Exception e) {
                   // e.printStackTrace();
                }

            }

        }

    }

    // ************************************************************************************  PRUEBAS **************************************************************************************


    public static void ejecutarconsultafichero(String fichero) {

        try {
            Class cl = Class.forName(driver); // Cargar del driver
            Database database = (Database) cl.newInstance(); // Instancia de la
            // BD
            DatabaseManager.registerDatabase(database); // Registro del driver
            Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);
            System.out.println("\nConvirtiendo el fichero a cadena...");
            BufferedReader entrada = new BufferedReader(new FileReader(fichero));
            String linea = null;
            StringBuilder stringBuilder = new StringBuilder();
            String salto = System.getProperty("line.separator"); // es el salto de línea


            while ((linea = entrada.readLine()) != null) {
                stringBuilder.append(linea);
                stringBuilder.append(salto);
            }
            String consulta = stringBuilder.toString();
            // Ejecutar consulta
            System.out.println("Consulta: " + consulta);
            escribirLog("realizando la consulta: "+consulta);
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = servicio.query(consulta);
            ResourceIterator i; // se utiliza para recorrer un set de recursos
            i = result.getIterator();
            if (!i.hasMoreResources()) {
                System.out.println("LA CONSULTA NO DEVUELVE NADA.");
            }

            System.out.println("\n----------------------------------------------------------------------------------------");
            while (i.hasMoreResources()) {

                Resource r = i.nextResource();
                System.out.println((String) r.getContent());
            }
            System.out.println("----------------------------------------------------------------------------------------\n");

        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR EN EL DRIVER.");
        } catch (InstantiationException ex) {
            System.out.println("ERROR AL CREAR LA INSTANCIA.");
        } catch (IllegalAccessException ex) {
            System.out.println("ERROR AL CREAR LA INSTANCIA.");
        } catch (XMLDBException ex) {
            System.out.println("ERROR AL OPERAR CON EXIST.");
        } catch (FileNotFoundException ex) {
            System.out.println("El fichero no se localiza: " + fichero);
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }

    public static void ejecutarconsultaficheroRanking(String fichero) {

        try {
            Class cl = Class.forName(driver); // Cargar del driver
            Database database = (Database) cl.newInstance(); // Instancia de la
            // BD
            DatabaseManager.registerDatabase(database); // Registro del driver
            Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);
            System.out.println("\nConvirtiendo el fichero a cadena...");
            BufferedReader entrada = new BufferedReader(new FileReader(fichero));
            String linea = null;
            StringBuilder stringBuilder = new StringBuilder();
            String salto = System.getProperty("line.separator"); // es el salto
            // de línea
            // \n

            while ((linea = entrada.readLine()) != null) {
                stringBuilder.append(linea);
                stringBuilder.append(salto);
            }
            String consulta = stringBuilder.toString();
            // Ejecutar consulta
            System.out.println("Consulta: " + consulta);
            escribirLog("Realizando la consulta: "+consulta);
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = servicio.query(consulta);
            ResourceIterator i; // se utiliza para recorrer un set de recursos
            i = result.getIterator();
            if (!i.hasMoreResources()) {
                System.out.println(" LA CONSULTA NO DEVUELVE NADA.");
            }


            // Variable para controlar el puesto
            int contador = 1;

            System.out.println("\n********************************** RANKING DISCOS MAS VALORADOS **********************************\n");
            System.out.format("%20s             %-40s%-30s\n", "PUESTO", "DISCO", "PUNTUACIÓN");
            while (i.hasMoreResources()) {

                Resource r = i.nextResource();

                String fila = (String) r.getContent(); // Obtengo una fila --> (disco + puntuación)
                String puntuacion = extraerNumeros((String) r.getContent()); // Obtengo el número
                String disco = fila.replace(puntuacion, ""); // Obtengo el nombre del disco
                //System.out.println("Disco: "+disco);
                //System.out.println("Puntuacion: "+puntuacion);

                //System.out.println("\t\t\t\t\t\t\t\t" + contador + "     " + (String) r.getContent());

                System.out.format("%17d                %-40s    %-5s\n", contador, disco, puntuacion);

                contador++;
            }
            System.out.println("\n**************************************************************************************************\n");

        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR EN EL DRIVER.");
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("ERROR AL CREAR LA INSTANCIA.");
        } catch (XMLDBException ex) {
            System.out.println("ERROR AL OPERAR CON EXIST.");
        } catch (FileNotFoundException ex) {
            System.out.println("El fichero no se localiza: " + fichero);
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }

    public static void ejecutarconsultaDiscoPersonas(String disco) {

        try {
            Class cl = Class.forName(driver); // Cargar del driver
            Database database = (Database) cl.newInstance(); // Instancia de la BD
            Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);
            System.out.println("\nConvirtiendo el fichero a cadena...");

            String consulta = "for $disco in (/Valoraciones/DatosValoracion) where $disco/disco/DatosDisco[nombreDisco='" + disco + "']\n" +
                    "return concat ('Personas que han escuchado \"',$disco/disco/DatosDisco/nombreDisco/text(),'\": ', $disco/persona/DatosPersona/nombrePersona/text(),', Puntuación: ', $disco/puntuacionDisco)";

            System.out.println("Consulta: " + consulta);
            escribirLog("Realizando la consulta: "+consulta);
            XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            ResourceSet result = servicio.query(consulta);
            ResourceIterator i; // se utiliza para recorrer un set de recursos
            i = result.getIterator();
            if (!i.hasMoreResources()) {
                System.out.println(" LA CONSULTA NO DEVUELVE NADA.");
            }

            System.out.println("\n----------------------------------------------------------------------------------------");
            while (i.hasMoreResources()) {

                Resource r = i.nextResource();
                System.out.println("Elemento: " + (String) r.getContent());
            }
            System.out.println("----------------------------------------------------------------------------------------\n");

        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR EN EL DRIVER.");
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("ERROR AL CREAR LA INSTANCIA.");
        } catch (XMLDBException ex) {
            System.out.println("ERROR AL OPERAR CON EXIST.");
        }
    }

    // ************************************************************************** FUNCIONES PARA VALIDAR DATOS ****************************************************************************


    public static String comprobarString(String pregunta) throws IOException {

        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String respuesta;

        System.out.print(pregunta);
        respuesta = br.readLine();
        while (respuesta.trim().length() < 4) {
            System.out.println("Error !! ... tienes que introducir como mínimo 4 carácteres !!");

            System.out.print(pregunta);
            respuesta = br.readLine();

        }

        return respuesta.trim();

    }

    public static boolean formatoMinutosSegundos(String duracion) {

        // String regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"; // formato 'HH:mm' ó 'H:mm'
        // String regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$"; // formato 'HH:mm:ss' ó 'H:mm:ss'

        boolean correcto = false;
        String regexp = "^(0?[0-9]?[0-9]?[0-9]):[0-5][0-9]$"; // formato 'MMM:ss' ó 'MM:ss' ó 'M:ss'

        if (Pattern.matches(regexp, duracion)) {
            //System.out.println(Pattern.matches(regexp,duracion));
            correcto = true;
        } else {
            System.out.println("Introduce un formato correcto --> 'HHH:mm' ó 'HH:mm' ó 'H:mm' ");
        }


        return correcto;
    }


    public static int numeroCorrectoEntreLimites(String pregunta, int respuesta, int numAbajo, int numArriba) {

        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean correcto;
        do {
            correcto = true;
            try {
                System.out.print(pregunta);
                respuesta = Integer.parseInt(br.readLine());
                if (respuesta < numAbajo || respuesta > numArriba) {
                    System.out.println("Error !! ... introduce un número entre 1 y " + numArriba);
                    correcto = false;
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Error !! ... introduce un número");
                correcto = false;
            }

        } while (!correcto);

        return respuesta;
    }

    public static String elegirEstilo() throws IOException {

        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int respuesta = 0;
        boolean correcto;


        do {

            do {
                correcto = true;
                try {
                    System.out.println("Elige un estilo: \n\n" +
                            " \t1 Pop\n" +
                            " \t2 Rock\n" +
                            " \t3 Clásica\n" +
                            " \t4 Flamenco\n" +
                            " \t5 Folk\n" +
                            " \t6 Disco\n" +
                            " \t7 Hard Rock\n" +
                            " \t8 Funky\n" +
                            " \t9 Jazz\n" +
                            "\t10 Blues\n" +
                            "\t11 Soul\n" +
                            "\t12 Punk\n" +
                            "\t13 Techno\n" +
                            "\t14 Ska\n" +
                            "\t15 Reaggae\n" +
                            "\t16 Hip hop\n" +
                            "\t17 Reggaeton\n" +
                            "\t18 Salsa\n" +
                            "\t19 Country\n" +
                            "\t20 Grunge\n" +
                            "\t21 Otros\n");

                    System.out.print("Opción: ");

                    respuesta = Integer.parseInt(br.readLine());

                } catch (NumberFormatException e) {
                    System.out.println("Error !! introduce un número correcto !!");
                    correcto = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (!correcto);


            switch (respuesta) {
                case 1:
                    return "Pop";
                case 2:
                    return "Rock";
                case 3:
                    return "Clásica";
                case 4:
                    return "Flamenco";
                case 5:
                    return "Folk";
                case 6:
                    return "Disco";
                case 7:
                    return "Hard Rock";
                case 8:
                    return "Funky";
                case 9:
                    return "Jazz";
                case 10:
                    return "Blues";
                case 11:
                    return "Soul";
                case 12:
                    return "Punk";
                case 13:
                    return "Techno";
                case 14:
                    return "Ska";
                case 15:
                    return "Reggae";
                case 16:
                    return "Hip hop";
                case 17:
                    return "Reggaeton";
                case 18:
                    return "Salsa";
                case 19:
                    return "Country";
                case 20:
                    return "Grunge";
                case 21:
                    System.out.print("Introduce estilo:");
                    return br.readLine();
                default:


            }

        } while (true);

    }

    public static int numeroCorrecto(String pregunta, int respuesta) {
        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean correcto;
        do {
            correcto = true;
            try {
                System.out.print(pregunta);
                respuesta = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Error !! ... introduce un número");
                correcto = false;
            }

        } while (!correcto);

        return respuesta;
    }

    public static String extraerNumeros(String cadena) {
        String resultado = "";
        char[] cadenaChar = cadena.toCharArray();

        for (int i = 0; i < cadenaChar.length; i++) {
            if (Character.isDigit(cadenaChar[i])) {
                resultado += cadenaChar[i];
            }

        }

        return resultado;


    }


}
