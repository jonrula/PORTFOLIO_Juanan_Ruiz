package Models;

import javax.swing.table.AbstractTableModel;

public class ListaEmpleadosEspectaculosDB4o extends AbstractTableModel {

    private final String[] columnas = {"Nombre", "Fecha", "Hora", "Lugar"};
    private final EmpleadoDB4o empleado;

    public ListaEmpleadosEspectaculosDB4o(EmpleadoDB4o e) {
        this.empleado = e;
    }

    @Override
    public int getRowCount() {
        return empleado.getEspectaculos().size();
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

        EspectaculoDB4o espectaculo = empleado.getEspectaculos().get(rowIndex);

        return switch (columnIndex) {
            case 0 -> espectaculo.getNombre();
            case 1 -> espectaculo.getFecha();
            case 2 -> espectaculo.getHorario();
            case 3 -> espectaculo.getLugar();
            default -> null;
        };
    }

}
