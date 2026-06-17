package Dominio;

public class Viaje {
    private int id; // ID interno usado por los TDAs
    private Terminal origen;
    private Terminal destino;
    private Micro micro;
    private Fecha fecha;
    private int prioridad;

    public Viaje(int id, Terminal origen, Terminal destino, Micro micro, Fecha fecha, int prioridad) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.micro = micro;
        this.fecha = fecha;
        this.prioridad = prioridad;
    }

    public int getId() {
        return id;
    }

    public Terminal getOrigen() {
        return origen;
    }

    public Terminal getDestino() {
        return destino;
    }

    public Micro getMicro() {
        return micro;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Viaje [" + id + "]: " + origen.getCodigo() + " -> " + destino.getCodigo() + 
               " | " + fecha.toString() + " | Prioridad: " + prioridad;
    }
}
