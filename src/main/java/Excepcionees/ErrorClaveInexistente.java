package Excepcionees;

public class ErrorClaveInexistente extends ErrorTDA {
    public ErrorClaveInexistente() {
        super("La clave solicitada no existe en la estructura.");
    }
}
