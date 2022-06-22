package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class VentanaCodigoPieza {
    private JPanel PanelCodigoPieza;
    private JLabel lb_tituloCodigoBuscarPieza;
    private JTextField et_IntroduceCodigoPieza;
    private JButton bt_BuscarPieza;
    private JComboBox<String> comboBoxCodigosPiezas;
    private JTextArea textAreaResultadoDatosCodigoPieza;


    public VentanaCodigoPieza() {


        // Cargo todos las piezas al cargar la ventana

        ArrayList<Piezas> listaTodasPiezas = CargaDatos.cargarPiezas();
        cargandoPiezas(listaTodasPiezas);


        bt_BuscarPieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (et_IntroduceCodigoPieza.getText().trim().equalsIgnoreCase("")) {

                    cargandoPiezas(listaTodasPiezas);

                }

                if (!et_IntroduceCodigoPieza.getText().trim().equalsIgnoreCase("") && numeroCorrecto(et_IntroduceCodigoPieza.getText().trim())) {

                    // Le paso el ArrayList de piezas de la consulta por id

                    ArrayList<Piezas> listaPiezas = CargaDatos.consultaCodigoPieza(et_IntroduceCodigoPieza.getText());
                    cargandoPiezas(listaPiezas);


                }
                if (!et_IntroduceCodigoPieza.getText().trim().equalsIgnoreCase("") && !numeroCorrecto(et_IntroduceCodigoPieza.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Código incorrecto !!", "Error", JOptionPane.WARNING_MESSAGE);
                    et_IntroduceCodigoPieza.setText("");
                }

            }
        });


        comboBoxCodigosPiezas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SessionFactory sesion = HibernateUtil.getSessionFactory();
                Session session = sesion.openSession();

                if (comboBoxCodigosPiezas.getSelectedItem() != null) {

                    // Extraigo el ID del string del item seleccionado del combobox
                    String resultado = (String) comboBoxCodigosPiezas.getSelectedItem();
                    assert resultado != null;
                    String[] parts = resultado.split(" --> ");
                    int idParseoCombobox = Integer.parseInt(parts[0]); // Obtengo el ID


                    // Instancio una pieza y lo cargo según Hibernate (le paso el código de la pieza parseado, es un int)
                    Piezas pieza = new Piezas();
                    //pieza = session.get(Piezas.class, (Serializable) comboBoxCodigosPiezas.getSelectedItem());
                    pieza = session.get(Piezas.class, (Serializable) idParseoCombobox);

                    if (pieza != null) {
                        String datosPieza =
                                "\n\n\tCÓDIGO:            " + pieza.getIdPieza() + "\n" +
                                        "\tNOMBRE:           " + pieza.getPieza() + "\n" +
                                        "\tDESCRIPCIÓN:   " + pieza.getDescripcion() + "\n" +
                                        "\tPRECIO:             " + pieza.getPrecio() + " €";

                        textAreaResultadoDatosCodigoPieza.setEditable(false);
                        textAreaResultadoDatosCodigoPieza.setText(datosPieza);


                    }

                }

            }
        });


    }


    private void cargandoPiezas(ArrayList<Piezas> listaPiezas) {


        comboBoxCodigosPiezas.removeAllItems(); // Vacíar anteriormente el combo para que no se vayan sumando los items de las consultas

        if (listaPiezas.size() > 0) {
            for (Piezas pieza : listaPiezas) {
                comboBoxCodigosPiezas.addItem(pieza.getIdPieza() + " --> " + pieza.getPieza());
            }
        } else {
            System.out.println("Error");
            comboBoxCodigosPiezas.removeAllItems(); // Borrar para la siguiente consulta
            textAreaResultadoDatosCodigoPieza.setText("No existe");
        }
    }


    public boolean numeroCorrecto(String numero) {

        boolean correcto = true;

        try {
            int comprobaNumero = Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            System.out.println("Error !!, número incorrecto");
            correcto = false;
        }

        return correcto;
    }


    public JPanel getPanelCodigoPieza() {
        return PanelCodigoPieza;
    }
}
