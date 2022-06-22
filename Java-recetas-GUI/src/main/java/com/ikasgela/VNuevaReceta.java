package com.ikasgela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class VNuevaReceta {
    private JPanel PanelRecetaNueva;
    private JTextField textRecetaNueva;
    private JTextArea textInstrucciones;
    private JButton botonAnadirReceta;
    private JLabel etiquetaIngredientes;
    private JLabel etiquetaCantidad;
    private JButton botonAnadirIngredientesYCantidad;
    private JTextField textIngrediente;
    private JTextField textCantidad;
    private JTextArea textIngredientesEscogidos;
    private JButton botonRecetaNueva;
    private JComboBox<Ingrediente> comboBoxListaIngredientes;

    // Variables globales de recetas e ingredientes que no cambian a 'true' hasta que añado una nueva receta

    private boolean hayReceta = false;
    private boolean hayIngredientes = false;

    // Pongo como global el 'JFrame' para que solo se pueda abrir una vez.

    private JFrame frame = new JFrame("Recetas");


    // String mensaje de lista de ingredientes escogidos, que se van sumando todos por cada receta y luego lo inicializo al poner una nueva receta:
    private String ingredientesEscogidos = "INGREDIENTES   |   CANTIDAD\n_________________________\n";

    private int id_recetaNueva = 0;
    private int id_ingrediente_receta = 0;
    private Receta recetaNueva;


    // Getter  de la Ventana de receta nueva

    public JPanel getPanelRecetaNueva() {
        return PanelRecetaNueva;
    }


    // Constructor (Meto como parámetros de entrada Los ArrayList que se cargan en la ventana principal, junto al 'combo'  'textArea' que voy a actualizar después, al meter nuevas recetas

    public VNuevaReceta(JTextArea textAreaInstrucciones, JList<Ingrediente> listaInstrucciones, JComboBox<Receta> comboBoxRecetas, List<Receta> recetas, List<Ingrediente> ingredientes, List<Ingrediente_receta> ingrediente_recetas) {

        // Cargo en el combo los ingredientes actuales


        for (Ingrediente ingrediente : ingredientes) {
            comboBoxListaIngredientes.setForeground(Color.green);
            comboBoxListaIngredientes.addItem(ingrediente);
        }
  

        botonRecetaNueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Hayo  la ID máxima de la recetas y le sumo 1 y recojo el nombre de la receta
                id_recetaNueva = PreguntasBD.LeerRecetaIDMaximo() + 1;
                String titulo = textRecetaNueva.getText();

                // Instancio una receta
                recetaNueva = new Receta(id_recetaNueva, titulo);


                if (!textRecetaNueva.getText().isEmpty()) {
                    if (!recetas.contains(recetaNueva)) { // --> para comprobar si existe ya la receta

                        // Añado a la base de datos la nueva receta con su 'id' y 'titulo'
                        PreguntasBD.introducirSoloIDyRecetaNuevaOracleSQL(id_recetaNueva, titulo);

                        // Añado la receta nueva al ArrayLIst de recetas, para luego poder sacar la relación de recetas y la lista de ingredientes actualizados en la Vprincipal
                        recetas.add(recetaNueva);

                        // Inicializo el String cada vez que hago una receta nueva, lo tengo puesto como variable global
                        ingredientesEscogidos = "INGREDIENTES   |   CANTIDAD\n_________________________\n";

                        // Actualizo que 'hayReceta' a 'true', para evitar errores si no le das al botón de 'añadir recetas' en los siguientes pasos
                        hayReceta = true;


                    } else {
                        JOptionPane.showMessageDialog(null, "Ya existe la receta " + titulo.toUpperCase() + " !! ", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No has introducido ninguna receta !!", "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });


        botonAnadirIngredientesYCantidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Solo entra si ha dado al botón anterior 'ok' de receta nueva y ha rellenado el texto de cantidad y existe un array de ingredientes y que escoja siempre el ingrediente 1 del ArrayList, porque el '0' lo tengo solo para introducir ingredientes nuevos

                if (hayReceta && comboBoxListaIngredientes.getSelectedIndex() > 0 && ingredientes.size() > 0 && !textCantidad.getText().isEmpty()) {

                    // Actualizo el valor de 'hayIngredientes' a 'true' para evitar errores y obligar dar primero  a los botones: 'ok' y luego al botón de 'añadir ingredientes'
                    hayIngredientes = true;

                    // Selecciono el texto de 'cantidad'
                    String cantidad = textCantidad.getText();

                    // Selecciono el item del combo y lo meto en una variable de tipo 'Ingrediente', que posteriormente voy a utilizar en una función que llama a la base de datos, para hallar el 'id' al que corresponde ese item
                    Ingrediente ingredienteLista = (Ingrediente) comboBoxListaIngredientes.getSelectedItem();

                    // Hallo la nueva 'Id' máxima de 'ingrediente_receta' de la nueva receta, hallando su máximo en la base de datos y sumándole 1
                    id_ingrediente_receta = PreguntasBD.LeerIngrediente_RecetaIDMaximo() + 1;

                    // Inserto la nueva receta, pues ya tengo todas sus propiedades, que he hallado anteriormente
                    PreguntasBD.introducirIngrediente_RecetaNuevoOracleSQL(id_ingrediente_receta, cantidad, ingredienteLista, id_recetaNueva);

                    // Instancio un objeto de tipo 'Ingrediente_receta' y los relaciono con sus respectivos Arrays
                    Ingrediente_receta ingrediente_recetaNuevo = new Ingrediente_receta(id_ingrediente_receta, cantidad, ingredienteLista.getId(), id_recetaNueva);

                    // Añado el ingrediente que he escogido a la lista de ingredientes de la receta nueva, según el item de ingrediente que he escogido en el combo y luego lo relaciono con el ArrayList de 'recetas¡
                    recetaNueva.getIngredientes().add(ingredienteLista);
                    ingredienteLista.setRecetas(recetas);

                    // Hago lo mismo con 'receta_ingrediente' que relaciona receta e ingrediente
                    ingrediente_recetas.add(ingrediente_recetaNuevo);
                    recetaNueva.setReceta_ingrediente(ingrediente_recetaNuevo);
                    ingrediente_recetaNuevo.getRecetas().add(recetaNueva);

                    // Saco el listado de los ingredientes escogidos que se van sumando en una variable String y que solo inicializo al hacer una nueva receta (la tengo como global)
                    ingredientesEscogidos = ingredientesEscogidos + ingredienteLista.getNombre() + "   \uD83D\uDC49    " + cantidad + "\n";
                    textIngredientesEscogidos.setText(ingredientesEscogidos);

                    // Vacío el cajetín de cantidad para meter nuevos datos
                    textCantidad.setText("");


                } else if (!hayReceta) {
                    JOptionPane.showMessageDialog(null, "No has introducido ninguna receta, vete al punto anterior !!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (comboBoxListaIngredientes.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "La opción 'Introducir un nuevo ingrediente' no vale, solo sirve para introducir nuevos ingredientes que NO están en la lista  ", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No has introducido ninguna cantidad !!", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }
        });


        botonAnadirReceta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // Recojo los datos de las casillas
                String titulo = textRecetaNueva.getText();
                String instrucciones = textInstrucciones.getText();

                if (hayReceta && hayIngredientes && !textInstrucciones.getText().isEmpty()) {

                    // Actualizo el dato de instrucciones en la base de datos y en el ArrayList de la recetaNueva
                    PreguntasBD.introducirInstruccionesRecetaNuevaOracleSQL(instrucciones, id_recetaNueva);
                    recetaNueva.setInstrucciones(instrucciones);

                    // Actualizo el combo de recetas de la VPrincipal, para eso le paso al constructor el combo --> 'JComboBox<Receta> comboBoxRecetas'
                    comboBoxRecetas.addItem(recetaNueva);


                    // Cargo el combobox con los datos actualizados de los ingredientes relacionados con la nueva receta y sus instrucciones
                    DefaultListModel<Ingrediente> modelo = new DefaultListModel<>();
                    for (Ingrediente ingrediente : recetaNueva.getIngredientes()) {
                        modelo.addElement(ingrediente);
                    }

                    // Actualizo la 'listaInstrucciones' y ' textAreaInstrucciones' de la VPrincipal, para eso le paso al constructor  --> 'JTextArea textAreaInstrucciones' y 'JList<Ingrediente> listaInstrucciones'
                    listaInstrucciones.setModel(modelo);
                    textAreaInstrucciones.setText(recetaNueva.getInstrucciones());

                    // Saco mensaje de confirmación
                    JOptionPane.showMessageDialog(null, "Has añadido la receta " + titulo.toUpperCase() + "  ", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

                    // Actualizo variables de receta e ingredientes para la siguiente receta que se quiera meter
                    hayReceta = false;
                    hayIngredientes = false;

                    // Pongo las casillas de los cajetines vacías para los nuevos datos
                    textRecetaNueva.setText("");
                    textIngredientesEscogidos.setText("");
                    textInstrucciones.setText("");


                    // Ahora tiene que cerrarse la ventana 'VUsuario'

                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelRecetaNueva);
                    topFrame.dispose();


                } else if (!hayReceta) {
                    JOptionPane.showMessageDialog(null, "No has añadido ninguna receta !!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!hayIngredientes) {
                    JOptionPane.showMessageDialog(null, "No has añadido ningún ingrediente !!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No has puesto las instrucciones !!", "Error", JOptionPane.ERROR_MESSAGE);
                }


            }


        });


        // Este 'ActionEvent' lo pongo para que salte una nueva ventana, al elegir del combo 'Introducir nuevo ingrediente'
        comboBoxListaIngredientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Objects.equals(comboBoxListaIngredientes.getSelectedItem(), ingredientes.get(0))) {

                    //JFrame frame = new JFrame("Recetas");
                    frame.setContentPane(new VIngredienteNuevo(ingredientes, comboBoxListaIngredientes).getPanelIngredienteNuevo());
                    frame.setLocationRelativeTo(null); // Saca la ventana al centro
                    frame.pack();
                    frame.setVisible(true);


                }
            }
        });

    }


}
