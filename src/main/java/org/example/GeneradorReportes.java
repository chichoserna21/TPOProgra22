package org.example;

import gestores.GestorFlota;
import gestores.GestorRutas;

public class GeneradorReportes {

    private GestorRutas rutas;
    private GestorFlota flota;

    public GeneradorReportes(GestorRutas rutas, GestorFlota flota) {
        this.rutas = rutas;
        this.flota = flota;
    }

    public void reporteTerminales() {
        System.out.println("\n=== REPORTE DE TERMINALES OPERADAS ===");
        for (int i = 0; i < 10; i++) {
            if (rutas.obtenerTerminalPorId(i) != null) {
                System.out.println(rutas.obtenerTerminalPorId(i).toString());
            }
        }
    }

    public void reporteConexionesYSalidasLlegadas() {
        System.out.println("\n=== REPORTE DE SALIDAS Y LLEGADAS ===");
        rutas.reportarConexiones();
    }

    public void reporteUtilizacionMicros() {
        System.out.println("\n=== REPORTE DE FLOTA ===");
        flota.reportarMicrosConMasAsignaciones();
        
        // Promedio
        int totalAsignaciones = 0;
        int cantMicros = 15;
        for (int i = 0; i < cantMicros; i++) {
            if (flota.obtenerMicroPorId(i) != null) {
                totalAsignaciones += flota.obtenerMicroPorId(i).getCantidadAsignaciones();
            }
        }
        System.out.println("Utilización promedio de la flota: " + (double)totalAsignaciones / cantMicros + " viajes por micro.");
    }

    public void reporteRutasMasYMenosUtilizadas() {
        System.out.println("\n=== REPORTE DE RUTAS MÁS Y MENOS UTILIZADAS ===");
        rutas.reportarRutasMasYMenosUtilizadas();
    }
}
