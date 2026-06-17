package Excepcionees;

public class emptyADT extends RuntimeException {
    public emptyADT() {
        super("The adt is empty.");
    }
}