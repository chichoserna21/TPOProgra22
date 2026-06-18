package implementaciones.Estaticas;

import interfaces.Set;
import java.util.Random;

public class ConjuntoEstatico implements Set {

    private int[] array;
    private int count;
    private Random random;

    public ConjuntoEstatico() {
        this.array = new int[100]; // Capacidad por defecto
        this.count = 0;
        this.random = new Random();
    }

    public ConjuntoEstatico(int capacity) {
        this.array = new int[capacity];
        this.count = 0;
        this.random = new Random();
    }

    @Override
    public boolean exist(int value) {
        for (int i = 0; i < count; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int choose() {
        if (isEmpty()) {
            throw new Excepcionees.ErrorVacia();
        }
        int index = random.nextInt(count);
        return array[index];
    }

    @Override
    public void add(int value) {
        if (count >= array.length) {
            throw new Excepcionees.ErrorLlena();
        }
        if (!exist(value)) {
            array[count] = value;
            count++;
        }
    }

    @Override
    public void remove(int element) {
        if (isEmpty()) {
            return;
        }
        for (int i = 0; i < count; i++) {
            if (array[i] == element) {
                array[i] = array[count - 1]; // Reemplazar por el último para evitar corrimientos
                count--;
                break;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }
}
