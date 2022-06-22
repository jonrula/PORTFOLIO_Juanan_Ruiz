import javax.swing.*;
import java.awt.*;


public class VInicio {

    private JPanel VPanelInicio;
    private JButton MySqlButton;
    private JButton DB4oButton;
    private JButton SQLiteButton;

    //Se pone de manera global las declaraciones de los frame para que solo se pueda abrir una ventana
    private final JFrame frameMySQL = new JFrame("Parque MySql");
    private final JFrame frameSQLite = new JFrame("Parque SQLite");
    private final JFrame frameDB4o = new JFrame("Parque DB4o");



    public VInicio() {

        //Botón de acceso a la base de datos de MySQL
        MySqlButton.addActionListener(e -> {

           if (!VAccesoMySQL.estasConectado) {

               frameMySQL.setContentPane((new VAccesoMySQL()).getVAccesoMySQL());
               frameMySQL.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra la ventana pero no el programa
               frameMySQL.setMinimumSize(new Dimension(400, 200)); // Lo ajusto a un tamaño para que se vea bien
               frameMySQL.setLocationRelativeTo(null); // Saca la ventana al centro
               frameMySQL.pack();
               frameMySQL.setVisible(true);


            } else {
                JOptionPane.showMessageDialog(null, "Ya estás conectado !!");
            }

        });
        //Botón de acceso a la base de datos de DB4o
        DB4oButton.addActionListener(e -> {

            //JFrame frame = new JFrame("Parque DB4o");
            frameDB4o.setContentPane((new VPrincipal_DB4O()).getVPanelPrincipal());
            frameMySQL.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frameDB4o.setMinimumSize(new Dimension(1200, 600)); // Lo ajusto a un tamaño para que se vea bien
            frameDB4o.setLocationRelativeTo(null); // Saca la ventana al centro
            frameDB4o.pack();
            frameDB4o.setVisible(true);


        });
        //Botón de acceso a la base de datos de SQLite
        SQLiteButton.addActionListener(e -> {

            if (!VAccesoSQLite.estasConectado) {

                frameSQLite.setContentPane((new VAccesoSQLite()).getVAccesoSQLite());
                frameSQLite.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra la ventana pero no el programa
                frameSQLite.setMinimumSize(new Dimension(400, 200)); // Lo ajusto a un tamaño para que se vea bien
                frameSQLite.setLocationRelativeTo(null); // Saca la ventana al centro
                frameSQLite.pack();
                frameSQLite.setVisible(true);


            } else {
                JOptionPane.showMessageDialog(null, "Ya estás conectado !!");
            }

        });
    }

    // Le paso la clase main para que se EJECUTE AL ARRANCAR LA APLICACION
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menú Inicio");
        frame.setContentPane(new VInicio().VPanelInicio);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setLocation(50, 50); // Posiciono la ventana es la esquina superior izquierda, para que no estorbe a otras ventanas
        frame.pack();
        frame.setVisible(true);
    }
}
