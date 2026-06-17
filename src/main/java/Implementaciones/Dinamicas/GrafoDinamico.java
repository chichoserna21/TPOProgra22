package Implementaciones.Dinamicas;

import Interfaces.Grafos;

public class GrafoDinamico implements Grafos {

    private class AristaNode {
        int vertxDestino;
        int weight;
        AristaNode next;

        AristaNode(int vertxDestino, int weight) {
            this.vertxDestino = vertxDestino;
            this.weight = weight;
            this.next = null;
        }
    }

    private class VertxNode {
        int vertex;
        AristaNode aristas;
        VertxNode next;

        VertxNode(int vertex) {
            this.vertex = vertex;
            this.aristas = null;
            this.next = null;
        }
    }

    private VertxNode head;

    public GrafoDinamico() {
        this.head = null;
    }

    private VertxNode findVertx(int vertex) {
        VertxNode current = head;
        while (current != null) {
            if (current.vertex == vertex) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public Grafos getVertxs() {
        Grafos copiaVertices = new GrafoDinamico();
        VertxNode current = head;
        while (current != null) {
            copiaVertices.addVertx(current.vertex);
            current = current.next;
        }
        return copiaVertices;
    }

    @Override
    public void addVertx(int vertex) {
        if (findVertx(vertex) == null) {
            VertxNode newNode = new VertxNode(vertex);
            newNode.next = head;
            head = newNode;
        }
    }

    @Override
    public void removeVertx(int vertex) {
        if (head == null) return;

        // Primero eliminar aristas que apunten a este vertice
        VertxNode current = head;
        while (current != null) {
            removeEdge(current.vertex, vertex);
            current = current.next;
        }

        // Ahora eliminar el vertice en si
        if (head.vertex == vertex) {
            head = head.next;
        } else {
            current = head;
            while (current.next != null) {
                if (current.next.vertex == vertex) {
                    current.next = current.next.next;
                    break;
                }
                current = current.next;
            }
        }
    }

    @Override
    public void addEdge(int vertxOne, int vertxTwo, int weight) {
        VertxNode origen = findVertx(vertxOne);
        VertxNode destino = findVertx(vertxTwo);

        if (origen != null && destino != null) {
            // Verificar si ya existe y pisar
            AristaNode currentA = origen.aristas;
            while (currentA != null) {
                if (currentA.vertxDestino == vertxTwo) {
                    currentA.weight = weight;
                    return;
                }
                currentA = currentA.next;
            }
            // Si no existe la arista, agregar al principio
            AristaNode newArista = new AristaNode(vertxTwo, weight);
            newArista.next = origen.aristas;
            origen.aristas = newArista;
        } else {
            throw new IllegalArgumentException("Uno o ambos vertices no existen");
        }
    }

    @Override
    public void removeEdge(int vertxOne, int vertxTwo) {
        VertxNode origen = findVertx(vertxOne);
        if (origen != null) {
            if (origen.aristas != null) {
                if (origen.aristas.vertxDestino == vertxTwo) {
                    origen.aristas = origen.aristas.next;
                } else {
                    AristaNode currentA = origen.aristas;
                    while (currentA.next != null) {
                        if (currentA.next.vertxDestino == vertxTwo) {
                            currentA.next = currentA.next.next;
                            break;
                        }
                        currentA = currentA.next;
                    }
                }
            }
        }
    }

    @Override
    public boolean existsEdge(int vertxOne, int vertxTwo) {
        VertxNode origen = findVertx(vertxOne);
        if (origen != null) {
            AristaNode currentA = origen.aristas;
            while (currentA != null) {
                if (currentA.vertxDestino == vertxTwo) {
                    return true;
                }
                currentA = currentA.next;
            }
        }
        return false;
    }

    @Override
    public int edgeWeight(int vertxOne, int vertxTwo) {
        VertxNode origen = findVertx(vertxOne);
        if (origen != null) {
            AristaNode currentA = origen.aristas;
            while (currentA != null) {
                if (currentA.vertxDestino == vertxTwo) {
                    return currentA.weight;
                }
                currentA = currentA.next;
            }
        }
        throw new IllegalArgumentException("La arista no existe o los vertices no existen");
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
