package com.ikasgela;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class VRecords {
    private JList <Jugador> listaRecords;
    private JPanel panelRecords;

    // Constructor

    public VRecords(List<Jugador> jugadores) {

        DefaultListModel<Jugador> modelo= new DefaultListModel<>();

        for (Jugador jugador : jugadores) {
            modelo.addElement(jugador);
        }

        listaRecords.setModel(modelo);

    }

    public JPanel getPanelRecords() {
        return panelRecords;
    }
}
