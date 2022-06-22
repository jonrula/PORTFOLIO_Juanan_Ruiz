package com.company;

import javax.swing.*;

public class VentanaAyuda {
    private JTextArea textAreaInformación;
    private JPanel panelInformacion;

    public JPanel getPanelInformacion() {
        return panelInformacion;
    }

    public VentanaAyuda(){

        String Informacion= """
                        
                        
                        \t\t\tAUTOR: Juanan Ruiz.
                        \t\t\tCURSO: 3º DAM.
                        \t\t\tASIGNATURA: Acceso de datos.
                        \t\t\tPROYECTO: Acceso a datos con hibernate en java
                        """;

        textAreaInformación.setText(Informacion);
        textAreaInformación.setEditable(false);




    }
}
