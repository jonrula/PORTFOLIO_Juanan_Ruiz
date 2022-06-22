import Models.Usuario;
import MySQL.Carga;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VAccesoMySQL {
    private JButton bt_usuarioNuevo;
    private JButton bt_conectar;
    private JButton bt_salir;
    private JLabel lb_usuario;
    private JLabel lb_password;
    private JPasswordField et_password;
    private JTextField et_usuario;
    private JPanel VAccesoMySQL;

    // Para comprobar si la ventana ya está abierta
    public static boolean ventanaAbierta = false;
    public static int intentos = 3;
    public static boolean estasConectado = false;
    public static String passwordBDMySQL = "";
    public static String usuarioMySQL = "";


    // Pongo como global el 'JFrame' para que solo se pueda abrir una vez.
    private final JFrame frame = new JFrame("Parque My Sql");
    private final JFrame frame2 = new JFrame("Crear Usuario");


    public VAccesoMySQL() {
        ArrayList<Usuario> usuarios= new Carga().listaUsuarios();

        System.out.println("Usuarios: "+usuarios);
        System.out.format("%-20s%-70s\n","USUARIO","PASSWORD");
        for (Usuario usuario : usuarios) {
            System.out.format("%-20s%-70s\n",usuario.getUsuario(),usuario.getPassword());
        }


        bt_salir.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VAccesoMySQL);
            topFrame.dispose();
        });
        bt_usuarioNuevo.addActionListener(e -> {
            frame2.setContentPane((new VCrearUsuarioMySQL()).getVCrearUsuarioMySQL());
            frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // No cierra la ventana
            frame2.setMinimumSize(new Dimension(400, 200)); // Lo ajusto a un tamaño para que se vea bien
            frame2.setLocationRelativeTo(null); // Saca la ventana al centro
            frame2.pack();
            frame2.setVisible(true);

            // Ahora tiene que cerrarse la ventana 'VAccesoMySql' --> FUNCIONA !!

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VAccesoMySQL);
            topFrame.dispose();
        });
        bt_conectar.addActionListener(e -> {
            usuarioMySQL = et_usuario.getText();
            passwordBDMySQL = String.valueOf(et_password.getPassword());
            String passwd = new Carga().mirarPassword(usuarioMySQL);
            if (DigestUtils.sha256Hex(String.valueOf(et_password.getPassword())).equals(passwd)){
                JOptionPane.showMessageDialog(null, "Bienvenido/a " + usuarioMySQL.toUpperCase());

                frame.setContentPane((new VPrincipal_MySQL()).VPanelPrincipal);
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // No cierra la ventana
                frame.setMinimumSize(new Dimension(1200, 600)); // Lo ajusto a un tamaño para que se vea bien
                frame.setLocationRelativeTo(null); // Saca la ventana al centro
                frame.pack();
                frame.setVisible(true);

                ventanaAbierta = true;
                estasConectado = true;

                // Ahora tiene que cerrarse la ventana 'VAccesoMySql' --> FUNCIONA !!

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VAccesoMySQL);
                topFrame.dispose();
            } else {
                intentos--;
                JOptionPane.showMessageDialog(null, "Error !! .. usuario y/o contraseña desconocidos, te quedan " + intentos + " intentos");

                et_usuario.setText("");
                et_password.setText("");

                if (intentos == 0) {

                    // Ahora tiene que cerrarse la ventana 'VAccesoMySql' si ha fallado 3 veintentos

                    JOptionPane.showMessageDialog(null, "Has superado el número máximo de intentos !!");

                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VAccesoMySQL);
                    topFrame.dispose();
                    JOptionPane.showMessageDialog(null, "Adios, nos vemos en la próxima !!");

                    // Cierro el programa al superar los 3 intentos
                    System.exit(0);

                }

            }
        });
    }

    public JPanel getVAccesoMySQL() {
        return VAccesoMySQL;
    }
}
