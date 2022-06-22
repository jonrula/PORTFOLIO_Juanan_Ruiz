package Models;

import java.util.ArrayList;
import java.util.Objects;

public class Espectaculos_Cliente {

    // Atributos

    private int idEspecCli;
    private String cliente;
    private int espectaculo;

    // Constructores

    public Espectaculos_Cliente() {
    }

    public Espectaculos_Cliente(int idEspecCli, String cliente, int espectaculo) {
        this.idEspecCli = idEspecCli;
        this.cliente = cliente;
        this.espectaculo = espectaculo;
    }

    public Espectaculos_Cliente(String cliente, int espectaculo) {
        this.cliente = cliente;
        this.espectaculo = espectaculo;
    }

    // Getters y Setters


    public int getIdEspecCli() {
        return idEspecCli;
    }

    public void setIdEspecCli(int idEspecCli) {
        this.idEspecCli = idEspecCli;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(int espectaculo) {
        this.espectaculo = espectaculo;
    }

    // To String


    @Override
    public String toString() {
        return "Espectaculos_Cliente{" +
                "idEspecCli=" + idEspecCli +
                ", cliente='" + cliente + '\'' +
                ", espectaculo=" + espectaculo +
                '}';
    }

    public void mostrarEspectaculosCliente(ArrayList<Espectaculos_Cliente> espectaculosClientes){
        System.out.println("\nDatos del arrayList 'EspectaculosClientes':\n");
        System.out.format("%-5s%-15s%-20s\n","ID","ID_CLIENTE","ID_ESPECTACULO");

        for (Espectaculos_Cliente ec : espectaculosClientes) {
            System.out.format("%-5d%-15s%-20d\n", ec.getIdEspecCli(), ec.getCliente(), ec.getEspectaculo());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Espectaculos_Cliente)) return false;
        Espectaculos_Cliente that = (Espectaculos_Cliente) o;
        return getEspectaculo() == that.getEspectaculo() && getCliente().equals(that.getCliente());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCliente(), getEspectaculo());
    }
}
