import Models.Usuario;
import MySQL.Carga;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VAccesoSQLite {
    private JPanel VAccesoSQLite;
    private JLabel lb_usuario;
    private JLabel lb_password;
    private JPasswordField et_password;
    private JTextField et_usuario;
    private JButton bt_usuarioNuevoSQLite;
    private JButton bt_conectarSQLite;
    private JButton bt_salirSQLite;

    // Para comprobar si la ventana ya está abierta
    public static boolean ventanaAbierta = false;
    public static int intentos = 3;
    public static boolean estasConectado = false;
    public static String usuarioSQLite = "";
    public static String passwdBDSQLite = "";

    // Pongo como global el 'JFrame' para que solo se pueda abrir una vez.
    private final JFrame fSQLite = new JFrame("Parque SQLite");
    private final JFrame f2SQLite = new JFrame("Crear Usuario");

    public VAccesoSQLite() {
        ArrayList<Usuario> usuariosSQLite = new SQLite.Carga().listaUsuarios();

        System.out.println("Usuarios: " + usuariosSQLite);
        System.out.format("%-20s%-70s\n", "USUARIO", "PASSWORD");
        for (Usuario usu : usuariosSQLite) {
            System.out.format("%-20s%-70s\n", usu.getUsuario(), usu.getPassword());
        }


        bt_salirSQLite.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VAccesoSQLite);
            topFrame.dispose();
        });
        bt_usuarioNuevoSQLite.addActionListener(e -> {
            f2SQLite.setContentPane((new VCrearUsuarioSQLite().getVCrearUsuarioSQLite()));
            f2SQLite.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            f2SQLite.setMinimumSize(new Dimension(400, 200)); // Lo ajusto a un tamaño para que se vea bien
            f2SQLite.setLocationRelativeTo(null); // Saca la ventana al centro
            f2SQLite.pack();
            f2SQLite.setVisible(true);

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VAccesoSQLite);
            topFrame.dispose();
        });
        bt_conectarSQLite.addActionListener(e -> {
            usuarioSQLite = et_usuario.getText();
            passwdBDSQLite = String.valueOf(et_password.getPassword());
            String passwd = new SQLite.Carga().mirarPassword(usuarioSQLite);

            if (DigestUtils.sha256Hex(String.valueOf(et_password.getPassword())).equals(passwd)) {
                JOptionPane.showMessageDialog(null, "Bienvenido/a " + usuarioSQLite.toUpperCase());
                fSQLite.setContentPane((new VPrincipal_SQLite()).getVPanelPrincipal());
                fSQLite.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // No cierra la ventana
                fSQLite.setMinimumSize(new Dimension(1200, 600)); // Lo ajusto a un tamaño para que se vea bien
                fSQLite.setLocationRelativeTo(null); // Saca la ventana al centro
                fSQLite.pack();
                fSQLite.setVisible(true);

                ventanaAbierta = true;
                estasConectado = true;

                // Ahora tiene que cerrarse la ventana 'VAccesoMySql' --> FUNCIONA !!

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VAccesoSQLite);
                topFrame.dispose();

            } else {
                intentos--;
                JOptionPane.showMessageDialog(null, "Error !! .. usuario y/o contraseña desconocidos, te quedan " + intentos + " intentos");

                et_usuario.setText("");
                et_password.setText("");

                if (intentos == 0){
                    JOptionPane.showMessageDialog(null, "Has superado el número máximo de intentos !!");

                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VAccesoSQLite);
                    topFrame.dispose();
                    JOptionPane.showMessageDialog(null, "Adios, nos vemos en la próxima !!");

                    // Cierro el programa al superar los 3 intentos
                    System.exit(0);
                }
            }

        });
    }

    public JPanel getVAccesoSQLite() {
        return VAccesoSQLite;
    }
}
