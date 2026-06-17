package Excepcionees;

public class ErrorLlena extends RuntimeException {
    public ErrorLlena() {
        super("La estructura está llena (límite alcanzado).");
    }
}
