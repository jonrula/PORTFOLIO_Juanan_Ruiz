package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;


public class VentanaCodigoProveedor {
    private JTextArea textAreaResultadoDatosCodigoProveedor;
    private JComboBox<String> comboBoxCodigosProveedores;
    private JTextField et_IntroduceCodigoProveedor;
    private JButton bt_BuscarProveedor;
    private JLabel lb_tituloCodigoBuscarProveedor;
    private JPanel PanelCodigoProveedor;


    public VentanaCodigoProveedor() {


        // Cargo todos los proveedores al cargar la ventana

        ArrayList<Proveedores> listaTodosProveedores = CargaDatos.cargarProveedores();
        cargandoProveedores(listaTodosProveedores);


        bt_BuscarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (et_IntroduceCodigoProveedor.getText().trim().equalsIgnoreCase("")) {

                    cargandoProveedores(listaTodosProveedores);

                }
                if (!et_IntroduceCodigoProveedor.getText().trim().equalsIgnoreCase("") && numeroCorrecto(et_IntroduceCodigoProveedor.getText().trim())) {

                    // Le paso el ArrayList de proveedores de la consulta por id

                    ArrayList<Proveedores> listaProveedoreSeleccionado = CargaDatos.consultaCodigoProveedor(et_IntroduceCodigoProveedor.getText());
                    cargandoProveedores(listaProveedoreSeleccionado);


                }


                if (!et_IntroduceCodigoProveedor.getText().trim().equalsIgnoreCase("") && !numeroCorrecto(et_IntroduceCodigoProveedor.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Código incorrecto !!", "Error", JOptionPane.WARNING_MESSAGE);
                    et_IntroduceCodigoProveedor.setText("");
                }
            }
        });

        comboBoxCodigosProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                SessionFactory sesion = HibernateUtil.getSessionFactory();
                Session session = sesion.openSession();

                if (comboBoxCodigosProveedores.getSelectedItem() != null) {

                    // Extraigo el ID del string del item seleccionado del combobox
                    String resultado = (String) comboBoxCodigosProveedores.getSelectedItem();
                    assert resultado != null;
                    String[] parts = resultado.split(" --> ");
                    int idParseoCombobox = Integer.parseInt(parts[0]); // Obtengo el ID

                    // Instancio un proveedor y lo cargo según Hibernate (le paso el código del proveedor parseado, es un int)
                    Proveedores prov = new Proveedores();
                    //prov = session.get(Proveedores.class, (Serializable) comboBoxCodigosProveedores.getSelectedItem());
                    prov = session.get(Proveedores.class, (Serializable) idParseoCombobox);

                    if (prov != null) {
                        String datosProveedor =
                                "\n\n\tCÓDIGO:                         " + prov.getProveedor() + "\n" +
                                        "\tPROVEEDOR:                   " + prov.getProveedor() + "\n" +
                                        "\tRESPONSABLE VENTAS:   " + prov.getResponsableVentas() + "\n" +
                                        "\tDIRECCION:                    " + prov.getDirCp();

                        textAreaResultadoDatosCodigoProveedor.setEditable(false);
                        textAreaResultadoDatosCodigoProveedor.setText(datosProveedor);


                    }

                }

            }
        });


    }

    private void cargandoProveedores(ArrayList<Proveedores> listaProveedores) {


        // ESTE MENSAJE LO SACO MEJOR AL CARGAR TODA LA VENTANA PRINCIPAL; QUE ES DONDE SE CARGAN TODOS LOS DATOS
 /*       if (primeraVez == 0) {
            JOptionPane.showMessageDialog(null, "Cargando todos los proveedores, paciencia ...");
            primeraVez++;
        }*/


        // ArrayList<Proveedores> listaTodosProveedores = CargaDatos.cargarProveedores();

        comboBoxCodigosProveedores.removeAllItems(); // Vacíar anteriormente el combo para que no se vayan sumando los items de las consultas

        if (listaProveedores.size() > 0) {
            for (Proveedores proveedor : listaProveedores) {
                comboBoxCodigosProveedores.addItem(proveedor.getIdProveedor() + " --> " + proveedor.getProveedor());
            }
        } else {
            System.out.println("Error");
            comboBoxCodigosProveedores.removeAllItems(); // Borrar para la siguiente consulta
            textAreaResultadoDatosCodigoProveedor.setText("No existe");
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

    public JPanel getPanelCodigoProveedor() {
        return PanelCodigoProveedor;
    }
}
