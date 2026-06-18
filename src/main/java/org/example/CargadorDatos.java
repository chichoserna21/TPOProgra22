package org.example;

import dominio.Fecha;
import dominio.Micro;
import dominio.Terminal;
import dominio.TipoMicro;
import dominio.Viaje;
import gestores.GestorFlota;
import gestores.GestorRutas;
import gestores.GestorViajes;
import implementaciones.estaticas.ColaPrioridadEstatica;
import implementaciones.estaticas.DiccionarioEstatico;
import implementaciones.estaticas.GrafoEstatico;

public class CargadorDatos {

    public static void cargarEscenarioInicial(GestorRutas rutas, GestorFlota flota, GestorViajes viajes) {
        
        // 1. Cargar 10 Terminales
        String[][] datosTerminales = {
            {"BUE", "Terminal de Ómnibus de Retiro (Buenos Aires)"},
            {"COR", "Terminal de Ómnibus de Córdoba Capital (Córdoba)"},
            {"ROS", "Terminal Mariano Moreno (Rosario, Santa Fe)"},
            {"MDZ", "Terminal del Sol (Mendoza Capital)"},
            {"SLA", "Terminal de Ómnibus de Salta Capital"},
            {"TUC", "Terminal de Ómnibus de San Miguel de Tucumán"},
            {"SFE", "Terminal de Ómnibus de Santa Fe Capital"},
            {"NQN", "Terminal de Ómnibus de Neuquén"},
            {"BRC", "Terminal de Ómnibus de San Carlos de Bariloche"},
            {"POS", "Terminal de Ómnibus de Posadas (Misiones)"}
        };

        for (int i = 0; i < 10; i++) {
            rutas.agregarTerminal(new Terminal(i, datosTerminales[i][0], datosTerminales[i][1]));
        }

        // 2. Cargar 15 Micros
        TipoMicro[] tipos = {TipoMicro.EJECUTIVO, TipoMicro.SEMI_CAMA, TipoMicro.CAMA};
        for (int i = 0; i < 15; i++) {
            String patente = "AB" + String.format("%03d", i) + "CD";
            TipoMicro tipo = tipos[i % 3];
            flota.agregarMicro(new Micro(i, patente, tipo));
        }

        // 3. Crear Rutas (conexiones)
        // BUE (0) -> COR (1), ROS (2), MDZ (3), SFE (6)
        rutas.agregarRuta(0, 1);
        rutas.agregarRuta(1, 0);
        rutas.agregarRuta(0, 2);
        rutas.agregarRuta(2, 0);
        rutas.agregarRuta(0, 3);
        rutas.agregarRuta(3, 0);
        rutas.agregarRuta(0, 6);
        rutas.agregarRuta(6, 0);
        
        // COR (1) -> SLA (4), TUC (5)
        rutas.agregarRuta(1, 4);
        rutas.agregarRuta(4, 1);
        rutas.agregarRuta(1, 5);
        rutas.agregarRuta(5, 1);

        // NQN (7) -> BRC (8)
        rutas.agregarRuta(7, 8);
        rutas.agregarRuta(8, 7);
        // BUE (0) -> NQN (7)
        rutas.agregarRuta(0, 7);
        rutas.agregarRuta(7, 0);

        // POS (9) queda desconectada para probar el reporte de terminales desconectadas
        
        // 4. Cargar 20 Viajes
        int[] origenesViaje =  {0, 1, 0, 2, 0, 3, 0, 6, 1, 4, 1, 5, 7, 8, 0, 7, 1, 0, 2, 0};
        int[] destinosViaje =  {1, 0, 2, 0, 3, 0, 6, 0, 4, 1, 5, 1, 8, 7, 7, 0, 0, 1, 0, 2};
        
        for (int i = 0; i < 20; i++) {
            Terminal orig = rutas.obtenerTerminalPorId(origenesViaje[i]);
            Terminal dest = rutas.obtenerTerminalPorId(destinosViaje[i]);
            Micro mic = flota.obtenerMicroPorId(i % 15);
            Fecha fec = new Fecha(1 + (i % 28), 10, 2026);
            int prioridad = (i % 3) + 1; // Prioridad 1, 2, o 3
            
            Viaje v = new Viaje(i, orig, dest, mic, fec, prioridad);
            viajes.registrarViaje(v);
            flota.asignarViajeAMicro(mic.getId());
            rutas.registrarUsoRuta(orig.getId(), dest.getId());
        }
    }
}
