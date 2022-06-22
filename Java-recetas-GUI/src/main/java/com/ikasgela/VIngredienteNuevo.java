package com.ikasgela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VIngredienteNuevo {
    private JPanel panelIngredienteNuevo;
    private JTextField textIngredienteNuevo;
    private JButton botonAnadirIngredienteNuevo;

    private int id_ingredienteNuevo = 0;

    // Getter del panel para llamarlo desde VNuevaReceta

    public JPanel getPanelIngredienteNuevo() {
        return panelIngredienteNuevo;
    }

    // Constructor (le paso el arrayList de ingredientes y los combo, para que luego pueda actualizarlos)

    public VIngredienteNuevo(List<Ingrediente> ingredientes, JComboBox<Ingrediente> comboBoxListaIngredientes) {
        botonAnadirIngredienteNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!textIngredienteNuevo.getText().isEmpty()) {
                    // Compruebo si ya existe el ingrediente
                    if (ingredientes.contains(new Ingrediente(textIngredienteNuevo.getText()))) {

                        JOptionPane.showMessageDialog(null, "El ingrediente " + textIngredienteNuevo.getText().toUpperCase() + " ya existe !!", "Mensaje", JOptionPane.ERROR_MESSAGE);

                    } else {

                        // Sino existe, hallo un nuevo 'id' para el nuevo ingrediente y recojo su nombre del 'cajetín'
                        String nombreIngrediente = textIngredienteNuevo.getText();
                        id_ingredienteNuevo = PreguntasBD.LeerIngredienteIDMaximo() + 1;

                        // Inserto el nuevo ingrediente en la base de datos
                        PreguntasBD.introducirIngredienteNuevoOracleSQL(id_ingredienteNuevo, nombreIngrediente);

                        // Instancio un nuevo objeto de tipo 'Ingrediente' y lo aññado al ArrayList de ingredientes y actualizo el combo de la 'VPrincipal'
                        Ingrediente ingredienteNuevo = new Ingrediente(id_ingredienteNuevo, nombreIngrediente);

                        comboBoxListaIngredientes.addItem(ingredienteNuevo);
                        ingredientes.add(ingredienteNuevo);

                        // Ahora tiene que cerrarse la ventana 'VIngredienteNuevo'

                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelIngredienteNuevo);
                        topFrame.dispose();

                        // Saco mensaje de confirmación

                        JOptionPane.showMessageDialog(null, "Has añadido el ingrediente " + nombreIngrediente.toUpperCase(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No has introducido ningún ingrediente !!", "Título", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
}
