package Excepcionees;

public class ErrorGrafoElementoInexistente extends ErrorTDA {
    public ErrorGrafoElementoInexistente() {
        super("El vértice o la arista solicitada no existe en el grafo.");
    }
}
