import Models.Usuario;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VCrearUsuarioSQLite {
    private JPanel VCrearUsuarioSQLite;
    private JLabel lb_nombre;
    private JTextField et_nombreUsuario;
    private JLabel lb_password;
    private JPasswordField et_passwordUsuario;
    private JButton bt_crearSQLite;
    private JButton bt_atrasSQLite;
    private JButton bt_salirSQLite;

    // Pongo como global el 'JFrame' para que solo se pueda abrir una vez.
    private final JFrame fSQLite = new JFrame("Crear Usuario");
    private final JFrame f2SQLite = new JFrame("Parque SQLite");

    public JPanel getVCrearUsuarioSQLite() {
        return VCrearUsuarioSQLite;
    }

    private final ArrayList<Usuario> usuariosSQLite;

    public VCrearUsuarioSQLite(){
        usuariosSQLite = new SQLite.Carga().listaUsuarios();
        System.out.println("Usuarios: " + usuariosSQLite);
        for (Usuario usuario : usuariosSQLite) {
            System.out.format("%-20s%-70s\n", usuario.getUsuario(), usuario.getPassword());
        }


        bt_salirSQLite.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VCrearUsuarioSQLite);
            topFrame.dispose();
        });
        bt_atrasSQLite.addActionListener(e -> {
            fSQLite.setContentPane((new VAccesoMySQL()).getVAccesoMySQL());
            fSQLite.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Cierra la ventana pero no el programa
            fSQLite.setMinimumSize(new Dimension(400, 200)); // Lo ajusto a un tamaño para que se vea bien
            fSQLite.setLocationRelativeTo(null); // Saca la ventana al centro
            fSQLite.pack();
            fSQLite.setVisible(true);

            // Ahora tiene que cerrarse la ventana 'VAccesoMySql' --> FUNCIONA !!

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VCrearUsuarioSQLite);
            topFrame.dispose();
        });
        bt_crearSQLite.addActionListener(e -> {
            String nombreUsuario = et_nombreUsuario.getText();

            if (!usuariosSQLite.contains(new Usuario(nombreUsuario)) && !et_nombreUsuario.getText().equalsIgnoreCase("") && !String.valueOf(et_passwordUsuario.getPassword()).equals("")) {
                // Recojo los datos de la ventana y encripto la contraseña
                String passwordUsuario = DigestUtils.sha256Hex(String.valueOf(et_passwordUsuario.getPassword()));
                //System.out.println("Contraseña: "+passwordUsuario);
                // Creo un usuario y lo inserto en la base de datos
                Usuario usuario = new Usuario(nombreUsuario, passwordUsuario);
                //System.out.println(usuario);
                new SQLite.Carga().crearUsuarioNuevo(usuario);

                // Ahora tiene que cerrarse la ventana 'VCrearUsuarioMySql' --> FUNCIONA !!

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(VCrearUsuarioSQLite);
                topFrame.dispose();


                // Abro la VPrincipal:

                if (!VAccesoSQLite.ventanaAbierta) {

                    JOptionPane.showMessageDialog(null, "Bienvenido/a " + et_nombreUsuario.getText().toUpperCase());

                    f2SQLite.setContentPane((new VPrincipal_SQLite()).getVPanelPrincipal());
                    f2SQLite.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // No cierra la ventana
                    f2SQLite.setMinimumSize(new Dimension(1200, 600)); // Lo ajusto a un tamaño para que se vea bien
                    f2SQLite.setLocationRelativeTo(null); // Saca la ventana al centro
                    f2SQLite.pack();
                    f2SQLite.setVisible(true);

                    VAccesoSQLite.ventanaAbierta = true;
                    VAccesoSQLite.estasConectado = true;

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
