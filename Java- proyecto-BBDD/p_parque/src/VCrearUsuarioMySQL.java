import Models.Usuario;
import MySQL.Carga;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VCrearUsuarioMySQL {
    private JPanel VCrearUsuarioMySQL;
    private JLabel lb_nombre;
    private JTextField et_nombreUsuario;
    private JLabel lb_password;
    private JPasswordField et_passwordUsuario;
    private JButton bt_crear;
    private JButton bt_atras;
    private JButton bt_salir;

    // Pongo como global el 'JFrame' para que solo se pueda abrir una vez.
    private final JFrame frame = new JFrame("Crear Usuario");
    private final JFrame frame2 = new JFrame("Parque My Sql");

    public JPanel getVCrearUsuarioMySQL() {
        return VCrearUsuarioMySQL;
    }

    private final ArrayList<Usuario> usuarios;

    public VCrearUsuarioMySQL() {

        usuarios = new Carga().listaUsuarios();
        System.out.println("Usuarios: " + usuarios);
        System.out.format("%-20s%-70s\n", "USUARIO", "PASSWORD");
        for (Usuario usuario : usuarios) {
            System.out.format("%-20s%-70s\n", usuario.getUsuario(), usuario.getPassword());
        }


        bt_salir.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VCrearUsuarioMySQL);
            topFrame.dispose();
        });
        bt_atras.addActionListener(e -> {
            frame.setContentPane((new VAccesoMySQL()).getVAccesoMySQL());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra la ventana pero no el programa
            frame.setMinimumSize(new Dimension(400, 200)); // Lo ajusto a un tamaño para que se vea bien
            frame.setLocationRelativeTo(null); // Saca la ventana al centro
            frame.pack();
            frame.setVisible(true);

            // Ahora tiene que cerrarse la ventana 'VAccesoMySql' --> FUNCIONA !!

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VCrearUsuarioMySQL);
            topFrame.dispose();
        });
        bt_crear.addActionListener(e -> {
            String nombreUsuario = et_nombreUsuario.getText();

            if (!usuarios.contains(new Usuario(nombreUsuario)) && !et_nombreUsuario.getText().equalsIgnoreCase("") && !String.valueOf(et_passwordUsuario.getPassword()).equals("")) {
                // Recojo los datos de la ventana y encripto la contraseña
                String passwordUsuario = DigestUtils.sha256Hex(String.valueOf(et_passwordUsuario.getPassword()));
                //System.out.println("Contraseña: "+passwordUsuario);
                // Creo un usuario y lo inserto en la base de datos
                Usuario usuario = new Usuario(nombreUsuario, passwordUsuario);
                //System.out.println(usuario);
                new Carga().crearUsuarioNuevo(usuario);

                // Ahora tiene que cerrarse la ventana 'VCrearUsuarioMySql' --> FUNCIONA !!

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VCrearUsuarioMySQL);
                topFrame.dispose();


                // Abro la VPrincipal:

                if (!VAccesoMySQL.ventanaAbierta) {

                    JOptionPane.showMessageDialog(null, "Bienvenido/a " + et_nombreUsuario.getText().toUpperCase());

                    frame2.setContentPane((new VPrincipal_MySQL()).VPanelPrincipal);
                    frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // No cierra la ventana
                    frame2.setMinimumSize(new Dimension(1200, 600)); // Lo ajusto a un tamaño para que se vea bien
                    frame2.setLocationRelativeTo(null); // Saca la ventana al centro
                    frame2.pack();
                    frame2.setVisible(true);

                    VAccesoMySQL.ventanaAbierta = true;
                    VAccesoMySQL.estasConectado = true;

                } else {
                    JOptionPane.showMessageDialog(null, "Ya tienes abierta la ventana Principal !!");
                }


            } else if (et_nombreUsuario.getText().equalsIgnoreCase("") || String.valueOf(et_passwordUsuario.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Rellena los campos vacïos!!");
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe el usuario " + nombreUsuario.toUpperCase() + "!!");
                et_nombreUsuario.setText("");
                et_passwordUsuario.setText("");
            }


        });

    }
}

