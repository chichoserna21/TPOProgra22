package org.example;

import Gestores.GestorFlota;
import Gestores.GestorRutas;
import Gestores.GestorViajes;
import Implementaciones.Dinamicas.ColaPrioridadDinamica;
import Implementaciones.Estaticas.DiccionarioEstatico;
import Implementaciones.Estaticas.GrafoEstatico;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE OPERACIONES PARA MICROS ===");

        // 1. Inicializar Gestores inyectando las implementaciones de los TDAs (Mezclando estáticas y dinámicas)
        GestorRutas rutas = new GestorRutas(new GrafoEstatico());
        GestorFlota flota = new GestorFlota(new DiccionarioEstatico());
        GestorViajes viajes = new GestorViajes(new ColaPrioridadDinamica());

        // 2. Cargar Escenario
        System.out.println("\nCargando datos iniciales...");
        CargadorDatos.cargarEscenarioInicial(rutas, flota, viajes);

        // 3. Generar Reportes y Análisis
        GeneradorReportes reportes = new GeneradorReportes(rutas, flota);
        //v
        reportes.reporteTerminales();
        rutas.identificarTerminalesDesconectadas(); // POS (Posadas) debería saltar aquí
        
        reportes.reporteConexionesYSaldasLlegadas();
        
        // Simulación: Búsqueda de rutas entre Buenos Aires (0) y Neuquén (7) con máx 2 paradas
        System.out.println("\n=== ANÁLISIS DE RUTAS ===");
        rutas.buscarRutasPosibles(0, 7, 2);

        // Simulación: Despachar 5 viajes
        System.out.println("\n=== DESPACHO DE VIAJES ===");
        for (int i = 0; i < 5; i++) {
            viajes.despacharProximo();
        }

        // Simulación: Modificar prioridad por lluvia
        System.out.println("\n=== REPROGRAMACIÓN POR CLIMA ===");
        viajes.modificarPrioridad(15, 1); // Subimos la prioridad del viaje 15 a máxima urgencia
        System.out.println("Despachando siguiente viaje (debería ser el 15 si es prioridad 1):");
        viajes.despacharProximo();

        // 4. Reporte final de Flota
        reportes.reporteUtilizacionMicros();
        
        System.out.println("\n=== FIN DE LA SIMULACIÓN ===");
    }
}
