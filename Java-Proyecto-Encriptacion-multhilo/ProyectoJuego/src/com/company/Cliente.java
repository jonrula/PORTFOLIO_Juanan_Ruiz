package com.company;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, ClassNotFoundException {
        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.setProperty("javax.net.ssl.trustStore", "UsuarioAlmacenSSL");
        System.setProperty("javax.net.ssl.trustStorePassword", "890123");

        // Me conecto a mi máquina local al puerto 4999
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket clienteSSL = (SSLSocket) factory.createSocket("localhost", 4999);


        // Declaro flujos de entrada y salida
        DataInputStream reciboDato = new DataInputStream(clienteSSL.getInputStream());
        DataOutputStream envioDato = new DataOutputStream(clienteSSL.getOutputStream());


        // Recibo menu desde el servidor, elijo opción del menú y envío al servidor
        System.out.print(reciboDato.readUTF());
        String envioMenu = br.readLine();
        envioDato.writeUTF(envioMenu);


        boolean menuCorrecto = reciboDato.readBoolean();

        while (!menuCorrecto) {
            System.out.println(reciboDato.readUTF());
            envioMenu = br.readLine();
            envioDato.writeUTF(envioMenu);
            menuCorrecto = reciboDato.readBoolean();

        }

        // Parseo el valor  String que recojo del usuario
        int opcionEnvio = Integer.parseInt(envioMenu);

        while (opcionEnvio != 3) {
            switch (opcionEnvio) {

                case 1:
                    // Comprobar Usuario
                    System.out.print(reciboDato.readUTF());
                    String nombre = br.readLine();

                    // Envío mensaje cifrado al servidor
                    cifradoMensajeCliente(clienteSSL, nombre);
                    //envioDato.writeUTF(nombre);

                    // Comprobar contrasena
                    System.out.print(reciboDato.readUTF());
                    String contrasenaPrivada = br.readLine();

                    // Contraseña encriptada
                    String contrasenaEncriptsha256 = DigestUtils.sha256Hex(contrasenaPrivada);
                    System.out.println("Contraseña encriptada sha256:" + contrasenaEncriptsha256);

                    envioDato.writeUTF(contrasenaEncriptsha256);

                    // Recibo la validación del servidor , para poder entrar a jugar
                    boolean validarUsuario = reciboDato.readBoolean();

                    // Si usuario y contraseña SI son correctos, entro a jugar
                    if (validarUsuario) {


                        /*
                        Recibo mensaje --> Si es correcto recibo el menú, sino NO validado servidor
                        System.out.println(reciboDato.readUTF());
                        // Si recibo el menú, elijo opción y envío al servidor
                        int opcion = Integer.parseInt(br.readLine());
                        envioDato.writeInt(opcion);
                        */


                        // Recibo menú desde el servidor, elijo opción del menú y envío al servidor
                        System.out.println(reciboDato.readUTF());
                        envioMenu = br.readLine();
                        envioDato.writeUTF(envioMenu);


                        menuCorrecto = reciboDato.readBoolean();

                        while (!menuCorrecto) {
                            System.out.println(reciboDato.readUTF());
                            envioMenu = br.readLine();
                            envioDato.writeUTF(envioMenu);
                            menuCorrecto = reciboDato.readBoolean();

                        }

                        // Parseo el valor  String que recojo del usuario
                        opcionEnvio = Integer.parseInt(envioMenu);


                        switch (opcionEnvio) {

                            case 1:
                                // Verificamos la firma digital que nos manda el servidor
                                if (firmaDigitalCliente(clienteSSL)) {

                                    String fin = "";

                                    // Contestamos al servidor Si la verificación de la firma es correcta le mandamos la respuesta valida para que empecemos a jugar
                                    envioDato.writeBoolean(true);

                                    System.out.println(reciboDato.readUTF());

                                    while (!fin.equalsIgnoreCase("fin")) {


                                        // Recibo preguntas del servidor
                                        System.out.println(reciboDato.readUTF());

                                        // Elijo una pregunta y mando mi elección al servidor para que me envíe las respuestas
                                        // Se lo mando como String (writeUTF para contemplar, si mete letras, intro, etc..)

                                        //envioDato.writeUTF(br.readLine());
                                        cifradoMensajeCliente(clienteSSL, br.readLine());


                                        // Recibo comprobación del servidor
                                        boolean numeroPregunta = reciboDato.readBoolean();

                                        // Compruebo los datos hasta que sean correctos
                                        while (!numeroPregunta) {
                                            System.out.println(reciboDato.readUTF());
                                            //envioDato.writeUTF(br.readLine());
                                            cifradoMensajeCliente(clienteSSL, br.readLine());
                                            numeroPregunta = reciboDato.readBoolean();

                                        }


                                        // Recibo las respuestas de la pregunta escogida
                                        System.out.println(reciboDato.readUTF());

                                        // Elijo una respuesta y la envio (NO CIFRADA)
                                        //opcion = Integer.parseInt((br.readLine()));
                                        //envioDato.writeUTF(br.readLine());

                                        // Elijo una respuesta y la envio (CIFRADA)

                                        cifradoMensajeCliente(clienteSSL, br.readLine());

                                        // Recibo comprobación del servidor
                                        boolean comprobarNumeroRespuesta = reciboDato.readBoolean();

                                        // Compruebo los datos hasta que sean correctos
                                        while (!comprobarNumeroRespuesta) {
                                            System.out.println(reciboDato.readUTF());
                                            //envioDato.writeUTF(br.readLine()); // SIN CIFRAR
                                            cifradoMensajeCliente(clienteSSL, br.readLine()); // CIFRADA
                                            comprobarNumeroRespuesta = reciboDato.readBoolean();

                                        }


                                        // Recibo el dato si es correcto o no  y actualizo 'fin' dentro del bucle...

                                        System.out.println(reciboDato.readUTF());
                                        fin = br.readLine();
                                        envioDato.writeUTF(fin);
                                    }


                                    // Recojo los puntos totales al finalizar el juego

                                    System.out.println(reciboDato.readUTF());

                                } else {
                                    envioDato.writeBoolean(false);
                                }

                                break;

                        }

                        // Si usuario y contraseña NO son correctos, no puedo jugar
                    } else {
                        System.out.println(reciboDato.readUTF());
                    }


                    break;

                case 2:

                    boolean jugadorRepetido;

                    do {
                        // Introducir Usuario nuevo
                        System.out.print(reciboDato.readUTF());
                        String nombreNuevo = br.readLine();
                        envioDato.writeUTF("CLIENTE: " + nombreNuevo);

                        // Vuelvo a meter el nombre si es incorrecto
                        boolean nombreCorrecto = reciboDato.readBoolean();

                        while (!nombreCorrecto) {
                            System.out.println(reciboDato.readUTF());
                            nombreNuevo = br.readLine();
                            envioDato.writeUTF(nombreNuevo);
                            nombreCorrecto = reciboDato.readBoolean();

                        }


                        // Introducir apellidos nuevos
                        System.out.print(reciboDato.readUTF());
                        String apellidoNuevo = br.readLine();
                        envioDato.writeUTF("CLIENTE: " + apellidoNuevo);

                        // Vuelvo a meter el apellido si es incorrecto
                        boolean apellidoCorrecto = reciboDato.readBoolean();

                        while (!apellidoCorrecto) {
                            System.out.println(reciboDato.readUTF());
                            apellidoNuevo = br.readLine();
                            envioDato.writeUTF(apellidoNuevo);
                            apellidoCorrecto = reciboDato.readBoolean();

                        }

                        jugadorRepetido = reciboDato.readBoolean();

                        if(!jugadorRepetido){
                            System.out.println(reciboDato.readUTF());
                        }

                    } while (!jugadorRepetido);


                    // Introducir edad
                    System.out.print(reciboDato.readUTF());
                    String edad = br.readLine();
                    envioDato.writeUTF("CLIENTE: " + edad);

                    // Vuelvo a meter la edad si es incorrecta
                    boolean edadCorrecta = reciboDato.readBoolean();

                    while (!edadCorrecta) {
                        System.out.println(reciboDato.readUTF());
                        edad = br.readLine();
                        envioDato.writeUTF(edad);
                        edadCorrecta = reciboDato.readBoolean();

                    }

                    // Introducir nick
                    System.out.print(reciboDato.readUTF());
                    String nick = br.readLine();
                    envioDato.writeUTF("CLIENTE: " + nick);

                    // Vuelvo a meter el nick si es incorrecta
                    boolean nickCorrecto = reciboDato.readBoolean();

                    while (!nickCorrecto) {
                        System.out.println(reciboDato.readUTF());
                        nick = br.readLine();
                        envioDato.writeUTF(nick);
                        nickCorrecto = reciboDato.readBoolean();

                    }


                    // Introducir contraseña
                    System.out.print(reciboDato.readUTF());
                    String contrasenaNuevaPrivada = br.readLine();

                    // Contraseña encriptada
                    String contrasenaNuevaEncriptsha256 = DigestUtils.sha256Hex(contrasenaNuevaPrivada);
                    System.out.println("Contraseña nueva encriptada sha256:" + contrasenaNuevaEncriptsha256);

                    envioDato.writeUTF("CLIENTE: " + contrasenaNuevaEncriptsha256);

                    // Vuelvo a meter la contraseña si es incorrecta
                    boolean contrasenaCorrecta = reciboDato.readBoolean();

                    while (!contrasenaCorrecta) {
                        System.out.println(reciboDato.readUTF());
                        contrasenaNuevaPrivada = br.readLine();
                        contrasenaNuevaEncriptsha256 = DigestUtils.sha256Hex(contrasenaNuevaPrivada);
                        System.out.println("Contraseña nueva encriptada sha256:" + contrasenaNuevaEncriptsha256);
                        envioDato.writeUTF(contrasenaNuevaEncriptsha256);
                        contrasenaCorrecta = reciboDato.readBoolean();

                    }

                    // Recibo la lista actualizada de jugadores
                    System.out.println(reciboDato.readUTF());



                    /*
                    // Después de introducir los datos del usuario nuevo, voy directamente al menú:
                    // Recibo mensaje --> Si es correcto recibo el menú, sino NO validado servidor
                    System.out.println(reciboDato.readUTF());
                    // Si recibo el menú, elijo opción y envío al servidor
                    int opcion = Integer.parseInt(br.readLine());
                    envioDato.writeInt(opcion);
                    */


                    // Recibo menú desde el servidor, elijo opción del menú y envío al servidor
                    System.out.println(reciboDato.readUTF());
                    envioMenu = br.readLine();
                    envioDato.writeUTF(envioMenu);


                    menuCorrecto = reciboDato.readBoolean();

                    while (!menuCorrecto) {
                        System.out.println(reciboDato.readUTF());
                        envioMenu = br.readLine();
                        envioDato.writeUTF(envioMenu);
                        menuCorrecto = reciboDato.readBoolean();

                    }

                    // Parseo el valor  String que recojo del usuario
                    opcionEnvio = Integer.parseInt(envioMenu);


                    switch (opcionEnvio) {

                        case 1:
                            // Verificamos la firma digital que nos manda el servidor
                            if (firmaDigitalCliente(clienteSSL)) {

                                String fin = "";

                                // Contestamos al servidor Si la verificación de la firma es correcta le mandamos la respuesta valida para que empecemos a jugar
                                envioDato.writeBoolean(true);

                                System.out.println(reciboDato.readUTF());

                                while (!fin.equalsIgnoreCase("fin")) {

                                    // Elijo una pregunta y mando mi elección al servidor para que me envíe las respuestas
                                    // Se lo mando como String (writeUTF para contemplar, si mete letras, intro, etc..)


                                    // Recibo preguntas del servidor
                                    System.out.println(reciboDato.readUTF());

                                    //envioDato.writeUTF(br.readLine());
                                    cifradoMensajeCliente(clienteSSL, br.readLine());


                                    // Recibo comprobación del servidor
                                    boolean numeroPregunta = reciboDato.readBoolean();

                                    // Compruebo los datos hasta que sean correctos
                                    while (!numeroPregunta) {
                                        System.out.println(reciboDato.readUTF());
                                        //envioDato.writeUTF(br.readLine());
                                        cifradoMensajeCliente(clienteSSL, br.readLine());
                                        numeroPregunta = reciboDato.readBoolean();

                                    }



                                    // Recibo las respuestas de la pregunta escogida
                                    System.out.println(reciboDato.readUTF());

                                    // Elijo una respuesta y la envio (NO CIFRADA)
                                    //opcion = Integer.parseInt((br.readLine()));
                                    //envioDato.writeUTF(br.readLine());

                                    // Elijo una respuesta y la envio (CIFRADA)

                                    cifradoMensajeCliente(clienteSSL, br.readLine());

                                    // Recibo comprobación del servidor
                                    boolean comprobarNumeroRespuesta = reciboDato.readBoolean();

                                    // Compruebo los datos hasta que sean correctos
                                    while (!comprobarNumeroRespuesta) {
                                        System.out.println(reciboDato.readUTF());
                                        //envioDato.writeUTF(br.readLine()); // SIN CIFRAR
                                        cifradoMensajeCliente(clienteSSL, br.readLine()); // CIFRADA
                                        comprobarNumeroRespuesta = reciboDato.readBoolean();

                                    }


                                    // Recibo el dato si es correcto o no  y actualizo 'fin' dentro del bucle...

                                    System.out.println(reciboDato.readUTF());
                                    fin = br.readLine();
                                    envioDato.writeUTF(fin);
                                }


                                // Recojo los puntos totales al finalizar el juego

                                System.out.println(reciboDato.readUTF());

                            } else {
                                envioDato.writeBoolean(false);
                            }

                            break;

                    }


                    break;


            }

/*            // Recibo de nuevo el menú, elijo opción y envío al servidor
            System.out.print(reciboDato.readUTF());
            opcionEnvio = Integer.parseInt(br.readLine());
            envioDato.writeInt(opcionEnvio);*/


            // Recibo menu desde el servidor, elijo opción del menú y envío al servidor
            System.out.print(reciboDato.readUTF());
            envioMenu = br.readLine();
            envioDato.writeUTF(envioMenu);


            menuCorrecto = reciboDato.readBoolean();

            while (!menuCorrecto) {
                System.out.println(reciboDato.readUTF());
                envioMenu = br.readLine();
                envioDato.writeUTF(envioMenu);
                menuCorrecto = reciboDato.readBoolean();

            }

            // Parseo el valor  String que recojo del usuario y se lo paso otra vez al buche 'while', para que compare si es diferente de 3
            opcionEnvio = Integer.parseInt(envioMenu);

        }

        // Recibo mensaje de despedida del servidor
        System.out.println(reciboDato.readUTF());

        // Recibo el Ranking de puntos del servidor
        System.out.println(reciboDato.readUTF());


        // Cierro el cliente
        clienteSSL.close();

        // Cerramos flujos de datos
        envioDato.close();
        reciboDato.close();




    }


    public static boolean firmaDigitalCliente(SSLSocket cliente) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, ClassNotFoundException {

        // Creo un objeto br de tipo BufferedReader para coger las respuestas por tecladp
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean firmaOK = false;

        // Creamos los flujos
        ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

        System.out.println("Leemos la clave:");

        // Obtenemos la clave publica
        PublicKey clave = (PublicKey) ois.readObject();
        System.out.println("La clave recibida es: " + clave.getAlgorithm());

        System.out.println();

        //Recibo las reglas del juego a firmar
        String mensaje = ois.readObject().toString();
        System.out.println(mensaje);
        // Le respondo por teclado
        String respuesta = br.readLine();
        // Mando la respuesta
        oos.writeObject(respuesta);

        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
            respuesta = ois.readObject().toString();
            System.out.println(respuesta);
            // Le respondo por teclado
            respuesta = br.readLine();
            // Mando la respuesta
            oos.writeObject(respuesta);
        }


        if (respuesta.equalsIgnoreCase("s")) {
            //System.out.println(mensaje);
            System.out.println("Este mensaje va a ser firmado...");
            System.out.println("Verifico firma");
            //mensaje = "ee"; // Prueba para que de error en la verificación

            // VERIFICACIÓN DE LA FIRMA

            // AL OBJETO signature sE LE suministra los datos a verificar
            Signature verificadsa = Signature.getInstance("SHA1WITHRSA");
            verificadsa.initVerify(clave);

            verificadsa.update(mensaje.getBytes());
            byte[] firma = (byte[]) ois.readObject();
            boolean check = verificadsa.verify(firma);

            // Compruebo la veracidad de la firma
            if (check) {
                System.out.println("El mensaje es auténtico\n");
                firmaOK = true;
            } else {
                System.out.println("Intento de falsificación\n");
                firmaOK = false;
            }
        } else {
            System.out.println(ois.readObject());

        }


        return firmaOK;

    }


    public static void cifradoMensajeCliente(SSLSocket cliente, String texto) {
        try {
            //Generamos el par de claves
            KeyPairGenerator keygen;

            keygen = KeyPairGenerator.getInstance("RSA");

            System.out.println("Generando par de claves");
            KeyPair par = keygen.generateKeyPair();
            PrivateKey privada = par.getPrivate();
            PublicKey publica = par.getPublic();

            //////////////////////////////////////////////////////////////


            //Creamos los flujos
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

            // 1 Mandamos la clave publica al servidor para que pueda desencriptar el mensaje

            oos.writeObject(publica);
            System.out.println("Enviamos la clave publica cuyo valor es: " + publica.getAlgorithm());


            // Enviamos informacion encriptada con la clave privada

            //String texto = "Mensaje desde el cliente";

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privada);
            //directamente cifrarlo en un array de bytes, y no hacer conversiones a string
            byte[] mensaje = cipher.doFinal(texto.getBytes());

            // 2 Mandamos el mensaje cifrado al servidor
            oos.writeObject(mensaje);


            //oos.close();
            //ois.close();
            //cliente.close();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }


    }


}
