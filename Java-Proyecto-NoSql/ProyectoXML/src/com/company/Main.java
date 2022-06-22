package com.company;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.xpath.XPathExpressionException;
import java.util.logging.*;

import java.io.*;

public class Main {

    public static void main(String[] args) throws XMLDBException, IOException, ClassNotFoundException, XPathExpressionException {

        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Ejemplos de encriptacion

/*        String texto="Saludos desde Apuntesdejava.com";
        String encriptMD5= DigestUtils.md5Hex(texto);
        System.out.println("md5:"+encriptMD5);

        String texto2="Saludos desde Apuntesdejava.com";
        String encriptshaHex= DigestUtils.shaHex(texto2);
        System.out.println("shaHex:"+encriptshaHex);

        String texto3="1234";
        String encriptsha256= DigestUtils.sha256Hex(texto3);
        System.out.println("sha256:"+encriptsha256);

        String texto4="1234";
        String encriptsha3_512Hex= DigestUtils.sha3_512Hex(texto4);
        System.out.println("sha512:"+encriptsha3_512Hex);

        String texto5="0000";
        String adminEncriptsha256= DigestUtils.sha256Hex(texto5);
        System.out.println("sha256:"+adminEncriptsha256);*/


        // *******************************************************************************   LOGGERS REGISTROS  *******************************************************************************


        /*
        Logger logger=Logger.getLogger("Juanan");
        FileHandler fh= new FileHandler("RegistrosXML.log",true);
        logger.setUseParentHandlers(false);
        logger.addHandler(fh);
        // Guardar en formato txt
        //SimpleFormatter formatter = new SimpleFormatter();

        // Guardar en formato XML
        XMLFormatter formatter = new XMLFormatter ();
        fh. setFormatter(formatter);
        fh.setLevel(Level.ALL);
        logger.setLevel(Level.INFO);
        logger. setLevel(Level.ALL);
        logger.log(Level.INFO,"Mi primer log");

         */


        // *******************************************************************************   CREAR COLECCIÓN Y CARGAR ARCHIVOS XML *******************************************************************************


        // CREAR COLECCION Y CARGAR ARCHIVOS XML
        // LUEGO HAY QUE ACORDARSE QUE EN LAS SIGUENTES VECES QUE ARRANQUE EL PROGRAMA DE QUE NO SE EJECUTE, PORQUE SINO MACHACA DE NUEVO LOS DATOS QUE HALLAMOS AÑADIDO
        Carga.crearcoleccysubirarchivoConDatos("ColeccionDiscos"); // ESTA ES LA BUENA

        // CREA COLECCION Y CARGA LOS ARCHIVOS XML SIN DATOS Y LOS LLENA POSTERIORMENTE CON LA FUNCIONES SIGUIENTES
        //Carga.crearcoleccysubirarchivo("ColeccionDiscos");
        //Carga.cargarPersonas();
        //Carga.cargarDiscos();
        //Carga.cargarValoraciones();
        //Carga.cargaRanking();



        // LISTAR FICHEROS XML
        Carga.listarPersonas();
        Carga.listarDiscos();
        Carga.listarValoraciones();


        // PRUEBAS
        //Carga.ejecutarconsultafichero("Consultas/consultaPersonasDisco.xq");
        //Carga.ejecutarconsultafichero("Consultas/consultaDiscosPersona.xq");
        //Carga.ejecutarconsultafichero("Consultas/consultaPersonas.xq");
        //Carga.ejecutarconsultafichero("Consultas/consultaDiscos.xq");
        //Carga.ejecutarconsultaficheroRanking("Consultas/Ranking.xq");
        //Carga.insertarPersona(7,"Elena","Zamora","1234",23,2);
        //Carga.insertarValoracion(1, 1, 1, 5);
        //Carga.discoSeleccionado(1);
        //Carga.personaSeleccionada(1);

        //Carga.borrarPersona(7);
        //Carga.modificarPersona(7);
        //Carga.modificarDisco(19,"Elenilla");
        //Carga.idMAXPersona();
        //Carga.idMAXDisco();
        //Carga.contarPersonas();
        //Carga.borrarDisco(20);
        //arga.listarDiscos();
        //Carga.contarDiscos();
        //Carga.crearRankingXML();


        // *******************************************************************************   MENÚ PRINCIPAL  *******************************************************************************


        boolean correcto = true;
        int respuesta = 0;
        do {

            do {
                correcto = true;
                try {
                    System.out.println("\nElige una opción: \n\n" +
                            "\t1 Entrar\n" +
                            "\t2 Crear Usuario nuevo\n" +
                            "\t3 Salir\n");

                    System.out.print("Opción: ");

                    respuesta = Integer.parseInt(br.readLine());

                } catch (NumberFormatException e) {
                    System.out.println("Error !! ... elige una opción correcta [1-3] ");
                    correcto = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (!correcto);


            switch (respuesta) {
                case 1:
                    //Carga.Menu("Juanan", "1234");
                    Carga.entrarUsuario();
                    escribirLog("Entrando a loguearse ...");
                    break;
                case 2:
                    //Carga.anadirPersonaInicio();
                    System.out.println("Ponte en contacto con el administrador --> 'juanantonio.ruiz@ikasle.egibide.org'");
                    escribirLog("Entrando en la opción de crear nuevo usuario  --> 'juanantonio.ruiz@ikasle.egibide.org'");
                    break;
                case 3:
                    System.out.println("Adios, nos vemos en la próxima... que tengas buen día !!");
                    escribirLog("Saliendo de la aplicación");
                    break;
                default:
                    System.out.println("Error !! ... elige una opción correcta [1-3] ");


            }


        } while (respuesta != 3);

    }


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


}
