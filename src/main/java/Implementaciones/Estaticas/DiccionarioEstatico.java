package implementaciones.estaticas;
import excepciones.ErrorClaveInexistente;
import excepciones.ErrorLlena;

import interfaces.Diccionario;

public class DiccionarioEstatico implements Diccionario {

    private int[] keys;
    private int[] values;
    private int count;

    public DiccionarioEstatico() {
        this.keys = new int[100];
        this.values = new int[100];
        this.count = 0;
    }

    public DiccionarioEstatico(int capacity) {
        this.keys = new int[capacity];
        this.values = new int[capacity];
        this.count = 0;
    }

    private int indexOfKey(int key) {
        for (int i = 0; i < count; i++) {
            if (keys[i] == key) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void add(int key, int value) {
        int index = indexOfKey(key);
        if (index != -1) {
            values[index] = value; // Pisa el contenido
        } else {
            if (count >= keys.length) {
                throw new ErrorLlena();
            }
            keys[count] = key;
            values[count] = value;
            count++;
        }
    }

    @Override
    public void remove(int key) {
        if (isEmpty()) {
            return;
        }
        int index = indexOfKey(key);
        if (index != -1) {
            keys[index] = keys[count - 1];
            values[index] = values[count - 1];
            count--;
        }
    }

    @Override
    public int get(int key) {
        int index = indexOfKey(key);
        if (index == -1) {
            throw new ErrorClaveInexistente();
        }
        return values[index];
    }

    @Override
    public Diccionario getKeys() {
        Diccionario dictKeys = new DiccionarioEstatico(this.keys.length);
        for (int i = 0; i < count; i++) {
            dictKeys.add(keys[i], keys[i]);
        }
        return dictKeys;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }
}
