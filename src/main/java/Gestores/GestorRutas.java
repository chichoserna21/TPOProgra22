package gestores;

import dominio.Terminal;
import interfaces.Grafos;

public class GestorRutas {

    private Terminal[] terminales;
    private Grafos red;
    private int[][] usosRutas;

    public GestorRutas(Grafos implementacionGrafo) {
        this.terminales = new Terminal[10]; // Como pide el escenario inicial
        this.red = implementacionGrafo;
        this.usosRutas = new int[10][10];
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

    public void reportarConexiones() {
        int maxSalidas = -1;
        int maxLlegadas = -1;
        int maxConexiones = -1;
        Terminal termMasSalidas = null;
        Terminal termMasLlegadas = null;
        Terminal termMasConectada = null;

        for (int i = 0; i < terminales.length; i++) {
            if (terminales[i] == null) continue;
            int salidas = 0;
            int llegadas = 0;
            for (int j = 0; j < terminales.length; j++) {
                if (red.existsEdge(i, j)) salidas++;
                if (red.existsEdge(j, i)) llegadas++;
            }
            if (salidas > maxSalidas) {
                maxSalidas = salidas;
                termMasSalidas = terminales[i];
            }
            if (llegadas > maxLlegadas) {
                maxLlegadas = llegadas;
                termMasLlegadas = terminales[i];
            }
            if ((salidas + llegadas) > maxConexiones) {
                maxConexiones = (salidas + llegadas);
                termMasConectada = terminales[i];
            }
        }
        System.out.println("Terminal con mayor número de salidas (" + maxSalidas + "): " + (termMasSalidas != null ? termMasSalidas.toString() : "N/A"));
        System.out.println("Terminal con mayor número de llegadas (" + maxLlegadas + "): " + (termMasLlegadas != null ? termMasLlegadas.toString() : "N/A"));
        System.out.println("Terminal con más conexiones directas (" + maxConexiones + "): " + (termMasConectada != null ? termMasConectada.toString() : "N/A"));
    }

    public void registrarUsoRuta(int idOrigen, int idDestino) {
        if (idOrigen >= 0 && idOrigen < 10 && idDestino >= 0 && idDestino < 10) {
            usosRutas[idOrigen][idDestino]++;
        }
    }

    public void reportarRutasMasYMenosUtilizadas() {
        int max = -1;
        int min = Integer.MAX_VALUE;
        boolean hayRutas = false;
        
        for (int i = 0; i < terminales.length; i++) {
            for (int j = 0; j < terminales.length; j++) {
                if (red.existsEdge(i, j)) {
                    hayRutas = true;
                    int usos = usosRutas[i][j];
                    if (usos > max) max = usos;
                    if (usos < min) min = usos;
                }
            }
        }
        
        if (!hayRutas) {
            System.out.println("No hay rutas registradas.");
            return;
        }

        System.out.println("--- Rutas Más Utilizadas (" + max + " usos) ---");
        for (int i = 0; i < terminales.length; i++) {
            for (int j = 0; j < terminales.length; j++) {
                if (red.existsEdge(i, j) && usosRutas[i][j] == max) {
                    System.out.println(terminales[i].getCodigo() + " -> " + terminales[j].getCodigo());
                }
            }
        }
        
        System.out.println("--- Rutas Menos Utilizadas (" + min + " usos) ---");
        for (int i = 0; i < terminales.length; i++) {
            for (int j = 0; j < terminales.length; j++) {
                if (red.existsEdge(i, j) && usosRutas[i][j] == min) {
                    System.out.println(terminales[i].getCodigo() + " -> " + terminales[j].getCodigo());
                }
            }
        }
    }
}
