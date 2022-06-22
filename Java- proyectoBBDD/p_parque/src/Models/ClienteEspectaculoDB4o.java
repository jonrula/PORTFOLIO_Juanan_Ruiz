package Models;

import java.util.Objects;

public class ClienteEspectaculoDB4o {
    private int id;
    ClienteDB4o cliente;
    EspectaculoDB4o espectaculo;

    private int espectaculoInt;
    private String clienteString;

    public ClienteEspectaculoDB4o(ClienteDB4o cliente, EspectaculoDB4o espectaculo) {
        this.cliente = cliente;
        this.espectaculo = espectaculo;
    }


    public int getEspectaculoInt() {
        return espectaculoInt;
    }

    public void setEspectaculoInt(int espectaculoInt) {
        this.espectaculoInt = espectaculoInt;
    }

    public String getClienteString() {
        return clienteString;
    }

    public void setClienteString(String clienteString) {
        this.clienteString = clienteString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClienteEspectaculoDB4o(int id, ClienteDB4o cliente, EspectaculoDB4o espectaculo) {
        this.id = id;
        this.cliente = cliente;
        this.espectaculo = espectaculo;
    }

    public ClienteEspectaculoDB4o() {

    }

    public ClienteEspectaculoDB4o(ClienteDB4o cliente) {
        this.cliente = cliente;
    }

    public ClienteEspectaculoDB4o(EspectaculoDB4o espectaculo) {
        this.espectaculo = espectaculo;
    }

    //equals para que no se repitan eventos iguales del mismo cliente con el mismo espectáculo:


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEspectaculoDB4o that = (ClienteEspectaculoDB4o) o;
        return cliente.equals(that.cliente) && espectaculo.equals(that.espectaculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, espectaculo);
    }

    public ClienteDB4o getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDB4o cliente) {
        this.cliente = cliente;
    }

    public EspectaculoDB4o getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(EspectaculoDB4o espectaculo) {
        this.espectaculo = espectaculo;
    }

    @Override
    public String toString() {
        return "ClienteEspectaculo{" +
                "cliente=" + cliente +
                ", espectaculo=" + espectaculo +
                '}';
    }
}
