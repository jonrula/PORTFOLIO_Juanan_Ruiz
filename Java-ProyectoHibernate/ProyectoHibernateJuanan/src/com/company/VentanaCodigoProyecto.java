package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class VentanaCodigoProyecto {
    private JLabel lb_tituloCodigoBuscarProyecto;
    private JTextField et_IntroduceCodigoProyecto;
    private JButton bt_BuscarProyecto;
    private JComboBox<String> comboBoxCodigosProyectos;
    private JTextArea textAreaResultadoDatosCodigoProyecto;
    private JPanel PanelCodigoProyecto;


    public VentanaCodigoProyecto() {

        // Cargo todos los proyectos al cargar la ventana

        ArrayList<Proyectos> listaTodosProyectos = CargaDatos.cargarProyectos();
        cargandoProyectos(listaTodosProyectos);


        bt_BuscarProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (et_IntroduceCodigoProyecto.getText().trim().equalsIgnoreCase("")) {

                    cargandoProyectos(listaTodosProyectos);

                }

                if (!et_IntroduceCodigoProyecto.getText().trim().equalsIgnoreCase("") && numeroCorrecto(et_IntroduceCodigoProyecto.getText().trim())) {

                    // Le paso el ArrayList de proyectos de la consulta por id

                    ArrayList<Proyectos> listaProyectos = CargaDatos.consultaCodigoProyecto(et_IntroduceCodigoProyecto.getText().trim());
                    cargandoProyectos(listaProyectos);


                }
                if (!et_IntroduceCodigoProyecto.getText().trim().equalsIgnoreCase("") && !numeroCorrecto(et_IntroduceCodigoProyecto.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Código incorrecto !!", "Error", JOptionPane.WARNING_MESSAGE);
                    et_IntroduceCodigoProyecto.setText("");
                }


            }
        });


        comboBoxCodigosProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SessionFactory sesion = HibernateUtil.getSessionFactory();
                Session session = sesion.openSession();

                if (comboBoxCodigosProyectos.getSelectedItem() != null) {

                    // Extraigo el ID del string del item seleccionado del combobox
                    String resultado = (String) comboBoxCodigosProyectos.getSelectedItem();
                    assert resultado != null;
                    String[] parts = resultado.split(" --> ");
                    int idParseoCombobox = Integer.parseInt(parts[0]); // Obtengo el ID

                    // Instancio un proyecto y lo cargo según Hibernate (le paso el código del proyecto parseado, es un int)
                    Proyectos proyecto = new Proyectos();
                    //proyecto = session.get(Proyectos.class, (Serializable) comboBoxCodigosProyectos.getSelectedItem());
                    proyecto = session.get(Proyectos.class, (Serializable) idParseoCombobox);

                    if (proyecto != null) {
                        String datosProyecto =
                                "\n\n\t\tCÓDIGO:       " + proyecto.getIdProyecto() + "\n" +
                                        "\t\tPROYECTO:   " + proyecto.getProyecto() + "\n" +
                                        "\t\tCIUDAD:       " + proyecto.getCiudad();

                        textAreaResultadoDatosCodigoProyecto.setEditable(false);
                        textAreaResultadoDatosCodigoProyecto.setText(datosProyecto);


                    }

                }

            }
        });


    }


    private void cargandoProyectos(ArrayList<Proyectos> listaProyectos) {


        comboBoxCodigosProyectos.removeAllItems(); // Vacíar anteriormente el combo para que no se vayan sumando los items de las consultas

        if (listaProyectos.size() > 0) {
            for (Proyectos proyecto : listaProyectos) {
                comboBoxCodigosProyectos.addItem(proyecto.getIdProyecto() + " --> " + proyecto.getProyecto());
            }
        } else {
            System.out.println("Error");
            comboBoxCodigosProyectos.removeAllItems(); // Borrar para la siguiente consulta
            textAreaResultadoDatosCodigoProyecto.setText("No existe");
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


    public JPanel getPanelCodigoProyecto() {
        return PanelCodigoProyecto;
    }
}
