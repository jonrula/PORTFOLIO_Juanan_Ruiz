package DB4o;

import com.db4o.*;
import com.db4o.ext.Db4oException;

import javax.swing.*;

public class Conn {
    public Conn() {

    }
    public ObjectContainer conexion() {
        ObjectContainer db = null;
        //ObjectContainer db1 = null;
        String parqueBD = "db4oPark.yap";
        try {
            //db1 = Db4o.openFile(Db4o.newConfiguration(), parqueBD);
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), parqueBD);
        } catch (Db4oException e) {
            //sacamos ventanita de mensaje, por ejemplo:
            JButton ok = new JButton("OK");
            ok.setFocusPainted(false);
            Object[] ops = {ok};
            final JOptionPane panel = new JOptionPane("Error en la conexión con Db4o", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION, null, ops);
            JDialog jDialog = panel.createDialog("Error BBDD");
            ok.addActionListener(x -> jDialog.dispose()); //quitamos el panel de mensaje
            jDialog.setVisible(true);
        }
        return db;
    }
}
