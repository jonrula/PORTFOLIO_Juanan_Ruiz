package com.ikasgela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VUsuario {
    private JTextField textUsuario;
    private JPanel PanelUsuario;
    private JButton botonOK;
    private JLabel comprobacionUsuario;

    public JTextField getTextUsuario() {
        return textUsuario;
    }

    public JPanel getPanelUsuario() {
        return PanelUsuario;
    }

// Constructor


    public VUsuario() {

        // Inicializo el arrayList de jugadores con los datos de la base SQLite

        List<Jugador> jugadores = PreguntaBD.LeerJugadorSQLite();
        List<Pregunta> preguntas = PreguntaBD.LeerPreguntaSQLite();
        List<Respuesta> respuestas = PreguntaBD.LeerRespuestaSQLite();


        for (Pregunta pregunta : preguntas) {
            for (Respuesta respuesta : respuestas) {
                if (pregunta.getId()==respuesta.getPregunta_id()){
                    pregunta.getRespuesta().add(respuesta);
                    respuesta.setPregunta(pregunta);
                }
            }

        }


        botonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 1º opción --> compruebo los datos mirando si esta directamente en la base de datos, conectándose a dicha base

                // Meto en una variable 'int' el resultado que me retorna la función 'PreguntaBD.comprobarLeerJugador(String nombreIntroducidoCajetin)' para acceder a la posición del ArrayList --> muy importante recoger el dato en  en Mayúsculas !!

                String nombreIntroducidoCajetin = textUsuario.getText().toUpperCase();
                int jugadorElegido = PreguntaBD.LeerJugadorID_SQLite(nombreIntroducidoCajetin) ; // Hay que restar 1, para acceder a la posición correcta del Array

                if (PreguntaBD.comprobarLeerJugador(nombreIntroducidoCajetin)) { // Compruebo si existe el usuario en la base de datos, con la variable booleana (true) que me retorna la función --> 'PreguntaBD.comprobarLeerJugador( String nombreIntroducidoCajetin)'
                    comprobacionUsuario.setText(nombreIntroducidoCajetin + " tiene " + jugadores.get(jugadorElegido).getRecord() + " puntos");


                    JFrame frame = new JFrame("Trivia");
                    frame.setContentPane(new VPrincipalJuego(jugadores, preguntas, respuestas,textUsuario).getPanelJuego());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocationRelativeTo(null); // Saca la ventana al centro
                    frame.pack();
                    frame.setVisible(true);

                    // Ahora tiene que cerrarse la ventana 'VUsuario'

                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelUsuario);
                    topFrame.dispose();



                } else {
                    JOptionPane.showMessageDialog(null, "No existe el usuario " + textUsuario.getText(), "Título", JOptionPane.ERROR_MESSAGE);
                }

/*
                // 2º opción --> está hecho para comprobar el resultado si está en el ArrayList en Java

                if (jugadores.contains(new Jugador(textUsuario.getText()))) {
                    for (Jugador jugador : jugadores) {
                        if (jugador.getNombreJugador().equalsIgnoreCase(textUsuario.getText())) {
                            comprobacionUsuario.setText(textUsuario.getText().toUpperCase() + " tiene " + jugador.getRecord() + " puntos");

                        }
                    }

                    JFrame frame = new JFrame("Trivia");
                    frame.setContentPane(new VPrincipalJuego(jugadores, preguntas, respuestas).getPanelJuego());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocationRelativeTo(null); // Saca la ventana al centro
                    frame.pack();
                    frame.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "No existe el usuario " + textUsuario.getText(), "Título", JOptionPane.ERROR_MESSAGE);
                }

 */


            }

        });

    }

    public static void main(String[] args) {


        JFrame frame = new JFrame("Usuario");
        frame.setContentPane(new VUsuario().PanelUsuario);
        frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);
    }
}
