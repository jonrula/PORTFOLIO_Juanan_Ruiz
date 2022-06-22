package Models;

import java.util.Objects;

public class EmpleadoEspectaculoDB4o {

    private int id;
    private EmpleadoDB4o empleado;
    private EspectaculoDB4o espectaculo;


    public EmpleadoEspectaculoDB4o(int id, EmpleadoDB4o empleado, EspectaculoDB4o espectaculo) {
        this.id = id;
        this.empleado = empleado;
        this.espectaculo = espectaculo;
    }

    public EmpleadoEspectaculoDB4o(EmpleadoDB4o empleado) {
        this.empleado = empleado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmpleadoDB4o getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDB4o empleado) {
        this.empleado = empleado;
    }

    public EspectaculoDB4o getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(EspectaculoDB4o espectaculo) {
        this.espectaculo = espectaculo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpleadoEspectaculoDB4o that = (EmpleadoEspectaculoDB4o) o;
        return id == that.id && empleado.equals(that.empleado) && espectaculo.equals(that.espectaculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, empleado, espectaculo);
    }

    @Override
    public String toString() {
        return "EmpleadoEspectaculo{" +
                "id=" + id +
                ", empleado=" + empleado +
                ", espectaculo=" + espectaculo +
                '}';
    }
}
