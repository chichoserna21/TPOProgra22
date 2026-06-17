package Gestores;

import Dominio.Micro;
import Interfaces.Diccionario;

public class GestorFlota {

    private Micro[] arrayMicros;
    // Diccionario mapea: ID de Micro (int) -> Estado de ocupación (0=libre, 1=ocupado)
    // De esta forma cumplimos estrictamente con usar el TDA para registrar/buscar asignaciones
    private Diccionario asignacionesFlota;

    public GestorFlota(Diccionario implementacionDiccionario) {
        this.arrayMicros = new Micro[15]; // Escenario base de 15 micros
        this.asignacionesFlota = implementacionDiccionario;
    }

    public void agregarMicro(Micro micro) {
        arrayMicros[micro.getId()] = micro;
        // Se inicializa libre
        asignacionesFlota.add(micro.getId(), 0);
    }

    public Micro obtenerMicroPorId(int id) {
        if (id >= 0 && id < arrayMicros.length) {
            return arrayMicros[id];
        }
        return null;
    }

    public boolean estaDisponible(int microId) {
        try {
            int estado = asignacionesFlota.get(microId);
            return estado == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void asignarViajeAMicro(int microId) {
        if (estaDisponible(microId)) {
            // Actualizamos en Diccionario a Ocupado (1)
            asignacionesFlota.add(microId, 1);
            // Incrementamos la estadística en el objeto
            arrayMicros[microId].incrementarAsignaciones();
        } else {
            System.out.println("El micro " + arrayMicros[microId].getPatente() + " ya está asignado/ocupado.");
        }
    }

    public void liberarMicro(int microId) {
        asignacionesFlota.add(microId, 0); // Vuelve a estar libre
    }

    // Funcionalidad pedida: Mostrar micros con mayor cantidad de asignaciones
    public void reportarMicrosConMasAsignaciones() {
        int maxAsignaciones = -1;
        for (Micro m : arrayMicros) {
            if (m != null && m.getCantidadAsignaciones() > maxAsignaciones) {
                maxAsignaciones = m.getCantidadAsignaciones();
            }
        }
        
        System.out.println("--- Micros con Mayor Cantidad de Asignaciones (" + maxAsignaciones + " viajes) ---");
        for (Micro m : arrayMicros) {
            if (m != null && m.getCantidadAsignaciones() == maxAsignaciones && maxAsignaciones > 0) {
                System.out.println(m.toString());
            }
        }
    }
}
