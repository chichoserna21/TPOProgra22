package Implementaciones.Dinamicas;

import Interfaces.Diccionario;

public class DiccionarioDinamico implements Diccionario {

    private class Node {
        int key;
        int value;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Node head;

    public DiccionarioDinamico() {
        this.head = null;
    }

    private Node findNode(int key) {
        Node current = head;
        while (current != null) {
            if (current.key == key) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public void add(int key, int value) {
        Node node = findNode(key);
        if (node != null) {
            node.value = value; // Pisa el valor
        } else {
            Node newNode = new Node(key, value);
            newNode.next = head;
            head = newNode;
        }
    }

    @Override
    public void remove(int key) {
        if (isEmpty()) {
            return;
        }
        if (head.key == key) {
            head = head.next;
            return;
        }
        Node current = head;
        while (current.next != null) {
            if (current.next.key == key) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    @Override
    public int get(int key) {
        Node node = findNode(key);
        if (node == null) {
            throw new Excepcionees.ErrorClaveInexistente();
        }
        return node.value;
    }

    @Override
    public Diccionario getKeys() {
        Diccionario dictKeys = new DiccionarioDinamico();
        Node current = head;
        while (current != null) {
            dictKeys.add(current.key, current.key);
            current = current.next;
        }
        return dictKeys;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
