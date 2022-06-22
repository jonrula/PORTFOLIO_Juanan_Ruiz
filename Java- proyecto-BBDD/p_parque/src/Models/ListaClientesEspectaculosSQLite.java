package Models;

import javax.swing.table.AbstractTableModel;

public class ListaClientesEspectaculosSQLite extends AbstractTableModel {

    private final String[] espectaculos = {"ID", "Nombre", "Aforo", "Descripción", "Lugar", "Fecha", "Horario", "Precio"};

    //Propiedades
    private final Cliente cliente;
    public ListaClientesEspectaculosSQLite(Cliente cliente){
        this.cliente = cliente;
    }


    @Override
    public int getRowCount() {
        return cliente.getEspectaculosSQLite().size();
    }

    @Override
    public int getColumnCount() {
        return espectaculos.length;
    }

    //Nombre de las columnas
    @Override
    public String getColumnName(int column){
        return espectaculos[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EspectaculoSQLite e = cliente.getEspectaculosSQLite().get(rowIndex);

        return switch (columnIndex){
            case 0 -> e.getNo_Espect();
            case 1 -> e.getNombreEspec();
            case 2 -> e.getAforo();
            case 3 -> e.getDescripcion();
            case 4 -> e.getLugar();
            case 5 -> e.getFecha_Espec();
            case 6 -> e.getHorario_espec();
            case 7 -> e.getPrecio();
            default -> null;
        };
    }
}
