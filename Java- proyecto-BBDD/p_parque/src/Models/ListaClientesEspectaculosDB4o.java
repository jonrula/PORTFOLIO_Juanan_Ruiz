package Models;

import javax.swing.table.AbstractTableModel;

public class ListaClientesEspectaculosDB4o extends AbstractTableModel {


    private final String[] columnas = {"Nombre", "Fecha", "Hora", "Lugar"};
    private final ClienteDB4o cliente;
    //private List<Espectaculo> espectaculos;
    //private int idcliente;

    /*public CliEspTableModel(Cliente cliente) {
        this.cliente = cliente;
    }*/

    public ListaClientesEspectaculosDB4o(ClienteDB4o cliente) {
        this.cliente = cliente;
    }

    /*public CliEspTableModel(Cliente cliente) {
        this.cliente = cliente;
        espectaculos = cliente.getEspectaculos();
    }*/

    @Override
    public int getRowCount() {
        return cliente.getEspectaculos().size();
        //return espectaculos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        EspectaculoDB4o espectaculo = cliente.getEspectaculos().get(rowIndex);
        //Espectaculo espectaculo = espectaculos.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> espectaculo.getNombre();
            case 1 -> espectaculo.getFecha();
            case 2 -> espectaculo.getHorario();
            case 3 -> espectaculo.getLugar();
            default -> null;
        };

    }
}
