package com.ikasgela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class VPrincipalJuego {
    private JComboBox<Respuesta> resultadoRespuestas; // Hay que mter el tipo de Objeto que hay que añadir al comboBox, sino no coge el 'addItem'
    private JButton botonverificarRespuesta;
    private JPanel PanelJuego;
    private JButton botonVerRecords;
    private JLabel resultadoVerdaderoFalso;
    private JLabel nombreJugador;
    private JLabel etiquetaRecordJuego;
    private JLabel resultadoRecordJuego;
    private JLabel resultadoPreguntas;
    private JLabel etiquetaEstaJugando;

    //  Entre una tirada y la siguiente 'preguntaAleatoria' no cambia de valor, porque se enmascara.
    //  Necesito que sea una variable global a nivel de la clase y no volver a declararla

    private int preguntaAleatoria;

    public JPanel getPanelJuego() {
        return PanelJuego;
    }


    // Constructor

    public VPrincipalJuego(List<Jugador> jugadores, List<Pregunta> preguntas, List<Respuesta> respuestas, JTextField textUsuario) { // le paso también comop parámetro de entrada el 'textUsuario' del cajetín que va a necesitar para hacer la función --> '


        String nombreIntroducidoCajetin = textUsuario.getText().toUpperCase();

        int jugadorElegido = PreguntaBD.LeerJugadorID_SQLite(nombreIntroducidoCajetin);
        nombreJugador.setText(nombreIntroducidoCajetin + " \uD83D\uDC49️  " + jugadores.get(jugadorElegido).getRecord() + "  puntos");

        // Saco el valor máximo del record de todos los jugadores, llamando a la función --> 'PreguntaBD.LeerRecordMaximo()')
        resultadoRecordJuego.setText(PreguntaBD.LeerRecordMaximo() + "  puntos");

        // Cargo una pregunta aleatoria:
        Random r = new Random();
        preguntaAleatoria = r.nextInt(preguntas.size());

        // Saco por pantalla la pregunta aleatoria
        resultadoPreguntas.setText(preguntas.get(preguntaAleatoria).getPregunta());

        // Cargo las respuestas
        for (Respuesta respuesta : preguntas.get(preguntaAleatoria).getRespuesta()) {
            resultadoRespuestas.addItem(respuesta);
        }


        botonverificarRespuesta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (preguntas.size()>0) {
                    // Selecciono la respuesta
                    int respuestaSeleccionada = resultadoRespuestas.getSelectedIndex();
                    int respuestaElegida = preguntas.get(preguntaAleatoria).getRespuesta().get(respuestaSeleccionada).getCorrecta();


                    // Compruebo si es correcta
                    if (respuestaElegida == 1) {


                        // Mensaje de confirmación

                        resultadoVerdaderoFalso.setText("Verdadero ... sigue jugando");

                        // Actualizo el valor del record de del jugador al acertar la pregunta y saco de nuevo el valor del record actualizado por pantalla --> solo en Java dentro de la app
                        jugadores.get(jugadorElegido).setRecord(jugadores.get(jugadorElegido).getRecord() + 150); // Lo actualizo en la base de Java
                        nombreJugador.setText(nombreIntroducidoCajetin + " \uD83D\uDC49  " + (jugadores.get(jugadorElegido).getRecord()) + "  puntos");

                        if (PreguntaBD.LeerRecordMaximo() < jugadores.get(jugadorElegido).getRecord()) {
                            JOptionPane.showMessageDialog(null, "Record Máximo !! " + nombreIntroducidoCajetin + " \uD83D\uDC49 " + (jugadores.get(jugadorElegido).getRecord()) + "  puntos", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

                            // Busco el record máximo buscando dentro del ArrayList recorriendo todos los jugadores --> en JAVA
                            int recordMaxJava = 0;
                            for (Jugador jugador : jugadores) {
                                if (jugador.getRecord() > PreguntaBD.LeerRecordMaximo()) {
                                    recordMaxJava = jugador.getRecord();
                                }
                            }

                            // Actualizo el record Maximo, pero solo en el ArrayList de jugadores em JAVA

                            resultadoRecordJuego.setText(recordMaxJava + "  puntos");
                            /*

                             -Guardo el record máximo en la base de datos, pero lo recojo del ArrayList de jugadores en JAVA --> 'jugadores.get(jugadorElegido).getRecord()'
                                    int recordmáximo = jugadores.get(jugadorElegido).getRecord();
                             y luego lo actualizo en la base de Datos --> PreguntaBD.EscribirSQLite(int recordmaximo, String nombreIntroducidoCajetin)

                             -Si cojo el record desde la base de datos --> 'PreguntaBD.LeerRecordMaximo()', siempre me va a salir el mismo record, porque no he actualizado el valor que he conseguido en el ArrayList de jugadores
                                    int recordmáximo = PreguntaBD.LeerRecordMaximo();
                              PreguntaBD.EscribirSQLite(PreguntaBD.LeerRecordMaximo(), nombreIntroducidoCajetin);

                             */

                        }


                        // Borro las respuestas asociadas a la pregunta seleccionada y luego la pregunta seleccionada para que no se repitan, pero en JAVA, se siguen mantenidendo en la base de datos
                        // Al darle al botón, si no hay más preguntas --> 'preguntas.size()>0' ya sacaré un mensaje de error...

                        preguntas.get(preguntaAleatoria).getRespuesta().clear();
                        preguntas.remove(preguntaAleatoria);


                        // Nueva Pregunta aleatoria --> Aquí meto una pregunta nueva y que no se repita (he borrado la pregunta anterior)
                        preguntaAleatoria = r.nextInt(preguntas.size());

                        // Borro las anteriores respuestas, para que no se vayan sumando con las nuevas respuestas
                        resultadoRespuestas.removeAllItems();

                        // Cargo la nueva pregunta y las nuevas respuestas
                        resultadoPreguntas.setText(preguntas.get(preguntaAleatoria).getPregunta());

                        for (Respuesta respuesta : preguntas.get(preguntaAleatoria).getRespuesta()) {
                            resultadoRespuestas.addItem(respuesta);
                        }

                        // Dejo seleccionada la primera  respuesta, que es la posición 0 en el ArrayList de respuestas
                        resultadoRespuestas.setSelectedItem(0); // Deselecciono cualquier respuesta para que no me coja la anterior respuesta seleccionada

                        // Selecciono la nueva respuesta
                        respuestaSeleccionada = resultadoRespuestas.getSelectedIndex();
                        respuestaElegida = preguntas.get(preguntaAleatoria).getRespuesta().get(respuestaSeleccionada).getCorrecta();



                    } else {

                        // Saco el error por pantalla
                        resultadoVerdaderoFalso.setText("Falso ... cambio de usuario");


                        /*
                         Al fallar la respuesta, actualizo el valor del record en el ArrayList de jugadores en Java --> 'jugadores.get(jugadorElegido).getRecord()'
                         y ahora SI actualizo el record en la base de datos de SQLite --> 'PreguntaBD.EscribirSQLite(recordMaxJava, nombreIntroducidoCajetin)'
                         y saco el resultado por pantalla buscando el record Maximo en la base de datos --> 'PreguntaBD.LeerRecordMaximo()'

                         */


                        int recordMaxJava = jugadores.get(jugadorElegido).getRecord();

                        if (PreguntaBD.LeerRecordMaximo() < jugadores.get(jugadorElegido).getRecord()) {
                            PreguntaBD.EscribirSQLite(recordMaxJava, nombreIntroducidoCajetin);
                            resultadoRecordJuego.setText(PreguntaBD.LeerRecordMaximo() + "   puntos");

                        }

                        // Saco las ventana de mensaje y abro otra ventana de juego y cierro la ventana de juego del anterior jugador

                        JOptionPane.showMessageDialog(null, "Falso ... cambio de usuario", "Error", JOptionPane.ERROR_MESSAGE);

                        // Saco una ventana para que introduzca el nuevo usuario
                        JFrame frame = new JFrame("Cambio de Usuario");
                        frame.setContentPane(new VUsuario().getPanelUsuario());
                        frame.setLocationRelativeTo(null); // Saca la ventana al centro
                        frame.pack();
                        frame.setVisible(true);

                        // Cierro la ventana con la que está jugando el anterior jugador
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(getPanelJuego());
                        topFrame.dispose();



                        /*

                         // Otra forma de cambiar de usuario es coger el siguiente en la lista del ArrayList

                             Si el jugador que falla está en la última posición del ArrayList, le digo que empize por el de la posición cero de nuevo --> juanan
                             String nuevoJugador;

                        if (jugadores.get(jugadorElegido).getId() == jugadores.size() - 1) {

                            nuevoJugador = jugadores.get(0).getNombreJugador();
                            nombreJugador.setText(nuevoJugador + " --> " + jugadores.get(0).getRecord() + " puntos");
                            System.out.println(nuevoJugador);

                            // ... y sino es el último del ArrayList de jugadores le sumo 1 para ir a la siguiente posición del ArrayList de jugadores
                        } else {
                            int jugadorElegido = PreguntaBD.LeerJugadorID_SQLite(nombreIntroducidoCajetin); // siempre se va a quedar en el mismo valor
                            jugadorElegido++; // No me lo coje de arriba...

                            nuevoJugador = jugadores.get(jugadorElegido).getNombreJugador();
                            nombreJugador.setText(nuevoJugador + "   " + jugadores.get(0).getRecord() + " puntos");
                            System.out.println(nuevoJugador);
                        }

                         */

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ya no hay más preguntas !!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }


        });


        botonVerRecords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Records");
                frame.setContentPane(new VRecords(jugadores).getPanelRecords());
                frame.setLocationRelativeTo(null); // Saca la ventana al centro
                frame.pack();
                frame.setVisible(true);

            }
        });
    }

    // He echo estas funciones para que se quede parado el hilo durante un tiempo, mientras da la solucióbn a la pregunta...

    public static void esperar(int preguntaAleatoria, JComboBox<Respuesta> resultadoRespuestas, List<Pregunta> preguntas, JLabel resultadoVerdaderoFalso, int segundos) {
        int respuestaSeleccionada = resultadoRespuestas.getSelectedIndex();
        int respuestaElegida = preguntas.get(preguntaAleatoria).getRespuesta().get(respuestaSeleccionada).getCorrecta();


        try {
            resultadoVerdaderoFalso.setText("Pensando....");

            if (respuestaElegida == 1) {

                resultadoVerdaderoFalso.setText("Verdadero ... sigue jugando ...");
                Thread.sleep(segundos * 1000);

            } else {

                resultadoVerdaderoFalso.setText("Falso ... Cambio de Usuario ...");
                Thread.sleep(segundos * 1000);

            }


        } catch (Exception e) {
            System.out.println(e);
        }


    }


    public static void esperarmensaje(int segundos) {


        try {

            Thread.sleep(segundos * 1000);

        } catch (Exception e) {
            System.out.println(e);
        }


    }

}
