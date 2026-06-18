package implementaciones.dinamicas;
import excepciones.ErrorVacia;

import interfaces.Set;
import java.util.Random;

public class ConjuntoDinamico implements Set {

    private class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private int count;
    private Random random;

    public ConjuntoDinamico() {
        this.head = null;
        this.count = 0;
        this.random = new Random();
    }

    @Override
    public boolean exist(int value) {
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int choose() {
        if (isEmpty()) {
            throw new ErrorVacia();
        }
        int index = random.nextInt(count);
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public void add(int value) {
        if (!exist(value)) {
            Node newNode = new Node(value);
            newNode.next = head;
            head = newNode;
            count++;
        }
    }

    @Override
    public void remove(int element) {
        if (isEmpty()) {
            return;
        }
        if (head.value == element) {
            head = head.next;
            count--;
            return;
        }
        Node current = head;
        while (current.next != null) {
            if (current.next.value == element) {
                current.next = current.next.next;
                count--;
                return;
            }
            current = current.next;
        }
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }
}
