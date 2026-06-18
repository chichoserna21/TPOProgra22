package implementaciones.Estaticas;

import interfaces.Grafos;

public class GrafoEstatico implements Grafos {

    private int[][] matrizAdyacencia;
    private int[] vertices;
    private int cantidadVertices;
    private static final int MAX_VERTICES = 100;

    public GrafoEstatico() {
        this.matrizAdyacencia = new int[MAX_VERTICES][MAX_VERTICES];
        this.vertices = new int[MAX_VERTICES];
        this.cantidadVertices = 0;
    }

    private int indexOf(int vertex) {
        for (int i = 0; i < cantidadVertices; i++) {
            if (vertices[i] == vertex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Grafos getVertxs() {
        // En vez de retornar una interfaz, retornaremos un sub-grafo que solo contenga los vertices
        // O más bien un grafo nuevo sin aristas que tenga todos los vertices.
        // Segun implementaciones clasicas de la UADE:
        Grafos copiaVertices = new GrafoEstatico();
        for (int i = 0; i < cantidadVertices; i++) {
            copiaVertices.addVertx(vertices[i]);
        }
        return copiaVertices;
    }

    @Override
    public void addVertx(int vertex) {
        if (cantidadVertices >= MAX_VERTICES) {
            throw new Excepcionees.ErrorLlena();
        }
        if (indexOf(vertex) == -1) {
            vertices[cantidadVertices] = vertex;
            for (int i = 0; i <= cantidadVertices; i++) {
                matrizAdyacencia[cantidadVertices][i] = 0;
                matrizAdyacencia[i][cantidadVertices] = 0;
            }
            cantidadVertices++;
        }
    }

    @Override
    public void removeVertx(int vertex) {
        int index = indexOf(vertex);
        if (index != -1) {
            vertices[index] = vertices[cantidadVertices - 1];
            for (int i = 0; i < cantidadVertices; i++) {
                matrizAdyacencia[index][i] = matrizAdyacencia[cantidadVertices - 1][i];
                matrizAdyacencia[i][index] = matrizAdyacencia[i][cantidadVertices - 1];
            }
            matrizAdyacencia[index][index] = matrizAdyacencia[cantidadVertices - 1][cantidadVertices - 1];
            cantidadVertices--;
        }
    }

    @Override
    public void addEdge(int vertxOne, int vertxTwo, int weight) {
        int indexOne = indexOf(vertxOne);
        int indexTwo = indexOf(vertxTwo);
        if (indexOne != -1 && indexTwo != -1) {
            matrizAdyacencia[indexOne][indexTwo] = weight;
        } else {
            throw new Excepcionees.ErrorGrafoElementoInexistente();
        }
    }

    @Override
    public void removeEdge(int vertxOne, int vertxTwo) {
        int indexOne = indexOf(vertxOne);
        int indexTwo = indexOf(vertxTwo);
        if (indexOne != -1 && indexTwo != -1) {
            matrizAdyacencia[indexOne][indexTwo] = 0;
        }
    }

    @Override
    public boolean existsEdge(int vertxOne, int vertxTwo) {
        int indexOne = indexOf(vertxOne);
        int indexTwo = indexOf(vertxTwo);
        if (indexOne != -1 && indexTwo != -1) {
            return matrizAdyacencia[indexOne][indexTwo] != 0;
        }
        return false;
    }

    @Override
    public int edgeWeight(int vertxOne, int vertxTwo) {
        int indexOne = indexOf(vertxOne);
        int indexTwo = indexOf(vertxTwo);
        if (indexOne != -1 && indexTwo != -1) {
            return matrizAdyacencia[indexOne][indexTwo];
        }
        throw new Excepcionees.ErrorGrafoElementoInexistente();
    }

    @Override
    public boolean isEmpty() {
        return cantidadVertices == 0;
    }
}
