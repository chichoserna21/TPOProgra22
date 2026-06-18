package org.example;

import gestores.GestorFlota;
import gestores.GestorRutas;
import gestores.GestorViajes;
import implementaciones.Dinamicas.ColaPrioridadDinamica;
import implementaciones.Estaticas.DiccionarioEstatico;
import implementaciones.Estaticas.GrafoEstatico;

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
        
        reportes.reporteConexionesYSalidasLlegadas();
        
        // Simulación: Búsqueda de rutas entre Buenos Aires (0) y Neuquén (7) con máx 2 paradas
        System.out.println("\n=== ANÁLISIS DE RUTAS ===");
        rutas.buscarRutasPosibles(0, 7, 2);

        // Simulación: Despachar 5 viajes
        System.out.println("\n=== DESPACHO DE VIAJES ===");
        for (int i = 0; i < 5; i++) {
            dominio.Viaje v = viajes.despacharProximo();
            if (v != null) {
                flota.liberarMicro(v.getMicro().getId());
            }
        }

        // Simulación: Modificar prioridad por lluvia
        System.out.println("\n=== REPROGRAMACIÓN POR CLIMA ===");
        viajes.modificarPrioridad(15, 1); // Subimos la prioridad del viaje 15 a máxima urgencia
        System.out.println("Despachando siguiente viaje (debería ser el 15 si es prioridad 1):");
        dominio.Viaje vPrioridad = viajes.despacharProximo();
        if (vPrioridad != null) {
            flota.liberarMicro(vPrioridad.getMicro().getId());
        }

        // 4. Reporte final de Flota y Rutas
        reportes.reporteUtilizacionMicros();
        reportes.reporteRutasMasYMenosUtilizadas();
        
        System.out.println("\n=== SIMULACIÓN INTERACTIVA ===");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("\nOpciones de Simulación:");
            System.out.println("1. Despachar próximo viaje");
            System.out.println("2. Ver reporte de utilización de flota actual");
            System.out.println("3. Salir");
            System.out.print("Elija una opción: ");
            
            if (!scanner.hasNextInt()) {
                scanner.next(); // clear invalid input
                continue;
            }
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    dominio.Viaje vNuevo = viajes.despacharProximo();
                    if (vNuevo != null) {
                        flota.liberarMicro(vNuevo.getMicro().getId());
                    }
                    break;
                case 2:
                    reportes.reporteUtilizacionMicros();
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();

        System.out.println("\n=== FIN DE LA SIMULACIÓN ===");
    }
}
