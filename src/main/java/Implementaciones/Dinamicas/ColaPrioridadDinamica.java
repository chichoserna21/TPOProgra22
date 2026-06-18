package implementaciones.dinamicas;
import excepciones.ErrorVacia;

import interfaces.ColaConPrioridad;

public class ColaPrioridadDinamica implements ColaConPrioridad {

    private class Node {
        int value;
        int priority;
        Node next;

        Node(int value, int priority) {
            this.value = value;
            this.priority = priority;
            this.next = null;
        }
    }

    private Node head;

    public ColaPrioridadDinamica() {
        this.head = null;
    }

    @Override
    public int getElement() {
        if (isEmpty()) {
            throw new ErrorVacia();
        }
        return head.value;
    }
    @Override
    public int getPriority() {
        if (isEmpty()) {
            throw new ErrorVacia();
        }
        return head.priority;
    }

    @Override
    public void add(int value, int priority) {
        Node newNode = new Node(value, priority);
        // Asumiendo menor valor de prioridad = más urgente (e.g. 1 antes que 2)
        if (isEmpty() || head.priority > priority) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && current.next.priority <= priority) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new ErrorVacia();
        }
        head = head.next;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
