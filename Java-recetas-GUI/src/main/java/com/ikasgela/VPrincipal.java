package com.ikasgela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VPrincipal {
    private JComboBox<Receta> comboBoxRecetas; // Hay que decirle al combo que tipo de dato es el objeto que queremos añadir
    private JList<Ingrediente> listaInstrucciones;
    private JButton botonOK;
    private JPanel PanelPrincipal;
    private JLabel etiquetaNombreRecetas;
    private JPanel resultadoInstrucciones;
    private JTextArea textAreaInstrucciones;
    private JButton añadirNuevaRecetaButton;
    private JButton botonBorrarReceta;

    private boolean hayNuevaVentana=false;
    private static JFrame frame = new JFrame("Recetas");


// Constructor

    public VPrincipal() {
        // Conectar a la base de datos
        System.out.println("--- Conexión a Oracle --------------------------");
        PreguntasBD.conectar();
        System.out.println("------------------------------------------------\n");


        // Cargo las recetas en el comboBox

        List<Receta> recetas = PreguntasBD.RecetaOracleSQL();
        List<Ingrediente> ingredientes = PreguntasBD.ListaIngredienteOracleSQL();
        List<Ingrediente_receta> ingrediente_recetas = PreguntasBD.ListaIngrediente_recetaOracleSQL();


        for (Receta receta : recetas) {
            comboBoxRecetas.addItem(receta);
        }
        mostrarDetalle();


        comboBoxRecetas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mostrarDetalle();

            }
        });



        añadirNuevaRecetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                    if(añadirNuevaRecetaButton.isSelected()){
                        JOptionPane.showMessageDialog(null, "Solo puedes tener abierta una ventana de nueva receta !!", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        //JFrame frame = new JFrame("Recetas");
                        frame.setContentPane(new VNuevaReceta(textAreaInstrucciones, listaInstrucciones, comboBoxRecetas, recetas, ingredientes, ingrediente_recetas).getPanelRecetaNueva());
                        frame.setLocationRelativeTo(null); // Saca la ventana al centro
                        frame.pack();
                        frame.setVisible(true);


                    }





            }
        });
        botonBorrarReceta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //int recetaSeleccionada = comboBoxRecetas.getSelectedIndex();
                if (recetas.size() > 0) {
                    Receta recetaSeleccionada = comboBoxRecetas.getItemAt(comboBoxRecetas.getSelectedIndex());


                    PreguntasBD.BorrarRecetaOracleSQL(recetaSeleccionada);
                    JOptionPane.showMessageDialog(null, "Has borrado la receta " + recetaSeleccionada.getTitulo().toUpperCase() + " ", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    //JOptionPane.showMessageDialog(null, "Has borrado la receta " + recetas.get(recetaSeleccionada).getTitulo().toUpperCase() + " ", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    // Quito la receta seleccionada y de sus ingredientes del ArrayList de recetas en Java
                    recetaSeleccionada.getIngredientes().removeAll(recetaSeleccionada.getIngredientes());
                    // recetas.get(recetaSeleccionada).getIngredientes().clear();
                    recetas.remove(recetaSeleccionada);

                    // Actualizo el combo
                    comboBoxRecetas.removeItem(recetaSeleccionada);
                    textAreaInstrucciones.setText("");

                    mostrarDetalle();

                } else {
                    JOptionPane.showMessageDialog(null, "No hay más recetas !! ", "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }

    public void mostrarDetalle() {


        DefaultListModel<Ingrediente> modelo = new DefaultListModel<>();

        Receta recetaSeleccionada = (Receta) comboBoxRecetas.getSelectedItem();

        if (recetaSeleccionada != null) {
            for (Ingrediente ingrediente : recetaSeleccionada.getIngredientes()) {
                modelo.addElement(ingrediente);

            }

            listaInstrucciones.setModel(modelo);
            textAreaInstrucciones.setText(recetaSeleccionada.getInstrucciones());

        } else {
            modelo.removeAllElements();
            listaInstrucciones.setModel(modelo);
            textAreaInstrucciones.setText("");
        }

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Recetas");
        frame.setContentPane(new VPrincipal().PanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Saca la ventana al centro
        frame.pack();
        frame.setVisible(true);
    }
}
