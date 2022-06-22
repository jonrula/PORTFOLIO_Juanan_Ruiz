import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal {

    // propiedades botones
    private JButton boton1;
    private JButton boton2;
    private JButton boton3;
    private JButton boton4;
    private JButton boton5;
    private JButton boton6;
    private JButton boton7;
    private JButton boton8;
    private JButton boton9;
    private JPanel PanelPrincipal;


    // Declaro un objeto de variable tipo 'MotorJuego'
    private final MotorJuego motorJuego = new MotorJuego();


    // Aquí es donde pongo el código
    public VentanaPrincipal() {

        /* La acción que realiza al clickar cada botón es:
           - Sacar la posición de la fila y la columna de cada botón que luego utilizo en el método 'tirada' en la clase 'MotorJuego'

             Con el método 'vaciarBotones()', consigo:

           - Comprobar ganador (0 ó 1)  y si hay empate
           - Vacíar todos los botones después de cada ganador o sy hay empate.

        */
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boton1.setText(motorJuego.tirada(0, 0));
                vaciarBotones();
            }
        });
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boton2.setText(motorJuego.tirada(0, 1));
                vaciarBotones();
            }
        });
        boton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boton3.setText(motorJuego.tirada(0, 2));
                vaciarBotones();
            }
        });
        boton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boton4.setText(motorJuego.tirada(1, 0));
                vaciarBotones();
            }
        });
        boton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boton5.setText(motorJuego.tirada(1, 1));
                vaciarBotones();
            }
        });
        boton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boton6.setText(motorJuego.tirada(1, 2));
                vaciarBotones();
            }
        });
        boton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boton7.setText(motorJuego.tirada(2, 0));
                vaciarBotones();
            }
        });
        boton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boton8.setText(motorJuego.tirada(2, 1));
                vaciarBotones();
            }
        });
        boton9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boton9.setText(motorJuego.tirada(2, 2));
                vaciarBotones();
            }
        });


    }


    // Con esta función pongo vacío todos los botones después de que gane alguien o haya un empate.
    private void vaciarBotones() {
        if (motorJuego.hayGanador() == 0) {
            JOptionPane.showMessageDialog(null, "Enhorabuena, Has ganado jugador 0", "Título", JOptionPane.INFORMATION_MESSAGE);
            motorJuego.reset();
            botonesVacios();
        }
        if (motorJuego.hayGanador() == 1) {
            JOptionPane.showMessageDialog(null, "Enhorabuena, Has ganado jugador 1", "Título", JOptionPane.INFORMATION_MESSAGE);
            motorJuego.reset();
            botonesVacios();
        }
        if (motorJuego.empate()) {
            JOptionPane.showMessageDialog(null, "Empate !!", "Título", JOptionPane.ERROR_MESSAGE);
            motorJuego.reset();
            botonesVacios();
        }
    }

    private void botonesVacios() {
        boton1.setText("");
        boton2.setText("");
        boton3.setText("");
        boton4.setText("");
        boton5.setText("");
        boton6.setText("");
        boton7.setText("");
        boton8.setText("");
        boton9.setText("");
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaPrincipal");
        frame.setContentPane(new VentanaPrincipal().PanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.setVisible(true);
    }
}
