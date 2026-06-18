package dominio;

public class Micro {
    private int id; // ID interno usado por los TDAs
    private String patente;
    private TipoMicro tipo;
    private int cantidadAsignaciones;

    public Micro(int id, String patente, TipoMicro tipo) {
        this.id = id;
        this.patente = patente;
        this.tipo = tipo;
        this.cantidadAsignaciones = 0;
    }

    public int getId() {
        return id;
    }

    public String getPatente() {
        return patente;
    }

    public TipoMicro getTipo() {
        return tipo;
    }

    public int getCantidadAsignaciones() {
        return cantidadAsignaciones;
    }

    public void incrementarAsignaciones() {
        this.cantidadAsignaciones++;
    }

    @Override
    public String toString() {
        return "Micro " + patente + " (" + tipo + ")";
    }
}
