package implementaciones.Estaticas;

import interfaces.ColaConPrioridad;

public class ColaPrioridadEstatica implements ColaConPrioridad {

    private int[] elements;
    private int[] priorities;
    private int count;

    public ColaPrioridadEstatica() {
        this.elements = new int[100];
        this.priorities = new int[100];
        this.count = 0;
    }

    public ColaPrioridadEstatica(int capacity) {
        this.elements = new int[capacity];
        this.priorities = new int[capacity];
        this.count = 0;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new Excepcionees.ErrorVacia();
        }
        return elements[0];
    }

    @Override
    public int getPriority() {
        if (isEmpty()) {
            throw new Excepcionees.ErrorVacia();
        }
        return priorities[0];
    }

    @Override
    public void add(int value, int priority) {
        if (count >= elements.length) {
            throw new Excepcionees.ErrorLlena();
        }
        
        // Buscar la posición donde insertar (mayor prioridad primero, o menor numero = mayor prioridad)
        // Asumo que mayor valor de prioridad significa que va primero. 
        // Si prioridad 1 es más urgente que 10, hay que invertir el signo. 
        // Aquí asumiré que mayor 'priority' = mayor urgencia = va más adelante.
        // O lo haré al revés: Menor valor de prioridad significa más urgente (prioridad 1 es mejor que 2).
        // Vamos a asumir: Prioridad 1 es la más alta. 2 es menos.
        
        int i = count - 1;
        while (i >= 0 && priorities[i] > priority) { // Si el elemento en i tiene menor prioridad (número más alto)
            elements[i + 1] = elements[i];
            priorities[i + 1] = priorities[i];
            i--;
        }
        elements[i + 1] = value;
        priorities[i + 1] = priority;
        count++;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new Excepcionees.ErrorVacia();
        }
        for (int i = 0; i < count - 1; i++) {
            elements[i] = elements[i + 1];
            priorities[i] = priorities[i + 1];
        }
        count--;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }
}
