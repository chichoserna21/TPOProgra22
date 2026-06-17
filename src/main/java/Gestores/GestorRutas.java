package Gestores;

import Dominio.Terminal;
import Interfaces.Grafos;
import Implementaciones.Estaticas.GrafoEstatico;

public class GestorRutas {

    private Terminal[] terminales;
    private Grafos red;

    public GestorRutas(Grafos implementacionGrafo) {
        this.terminales = new Terminal[10]; // Como pide el escenario inicial
        this.red = implementacionGrafo;
    }

    public void agregarTerminal(Terminal terminal) {
        terminales[terminal.getId()] = terminal;
        red.addVertx(terminal.getId());
    }

    public void agregarRuta(int idOrigen, int idDestino) {
        // Peso fijo en 1 para contar paradas/tramos
        red.addEdge(idOrigen, idDestino, 1);
    }

    public Terminal obtenerTerminalPorCodigo(String codigo) {
        for (Terminal t : terminales) {
            if (t != null && t.getCodigo().equalsIgnoreCase(codigo)) {
                return t;
            }
        }
        return null;
    }

    public Terminal obtenerTerminalPorId(int id) {
        if (id >= 0 && id < terminales.length) {
            return terminales[id];
        }
        return null;
    }

    // Funcionalidad pedida 1: Identificar terminales desconectadas (rutas no utilizadas)
    public void identificarTerminalesDesconectadas() {
        System.out.println("--- Terminales Desconectadas ---");
        boolean hayDesconectadas = false;
        
        // Obtenemos los vértices del grafo
        Grafos vertices = red.getVertxs();
        
        // Este algoritmo O(N^2) es rudimentario porque la interfaz Grafos 
        // no da forma de iterar fácilmente sin conocer los IDs. 
        // Como sabemos que los IDs van de 0 a 9, iteramos:
        for (int i = 0; i < terminales.length; i++) {
            if (terminales[i] == null) continue;
            
            boolean tieneConexion = false;
            // Verificar si tiene alguna salida
            for (int j = 0; j < terminales.length; j++) {
                if (red.existsEdge(i, j) || red.existsEdge(j, i)) {
                    tieneConexion = true;
                    break;
                }
            }
            if (!tieneConexion) {
                System.out.println("- " + terminales[i].getDescripcion() + " no tiene rutas activas.");
                hayDesconectadas = true;
            }
        }
        
        if (!hayDesconectadas) {
            System.out.println("Todas las terminales tienen al menos una ruta conectada.");
        }
    }

    // Funcionalidad pedida 2: Determinar rutas posibles entre dos terminales (max paradas)
    // Se utilizará Backtracking / DFS en la versión completa del Main.
    // Aquí dejamos preparada la firma.
    public void buscarRutasPosibles(int origenId, int destinoId, int maxParadas) {
        boolean[] visitados = new boolean[terminales.length];
        int[] caminoActual = new int[terminales.length];
        System.out.println("Buscando rutas desde " + terminales[origenId].getCodigo() + 
                           " hasta " + terminales[destinoId].getCodigo() + 
                           " con máximo " + maxParadas + " paradas:");
        dfsRutas(origenId, destinoId, maxParadas, visitados, caminoActual, 0);
    }

    private void dfsRutas(int actual, int destino, int maxParadas, boolean[] visitados, int[] camino, int profundidad) {
        camino[profundidad] = actual;
        
        if (actual == destino) {
            imprimirCamino(camino, profundidad);
            return;
        }
        
        if (profundidad >= maxParadas) {
            return; // Límite de paradas alcanzado
        }

        visitados[actual] = true;
        for (int i = 0; i < terminales.length; i++) {
            if (terminales[i] != null && !visitados[i] && red.existsEdge(actual, i)) {
                dfsRutas(i, destino, maxParadas, visitados, camino, profundidad + 1);
            }
        }
        visitados[actual] = false;
    }

    private void imprimirCamino(int[] camino, int longitud) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= longitud; i++) {
            sb.append(terminales[camino[i]].getCodigo());
            if (i < longitud) sb.append(" -> ");
        }
        System.out.println("Ruta encontrada: " + sb.toString() + " (" + longitud + " paradas)");
    }
}
