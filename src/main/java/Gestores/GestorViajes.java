package gestores;

import dominio.Viaje;
import interfaces.ColaConPrioridad;

public class GestorViajes {

    private Viaje[] viajesTotales;
    private ColaConPrioridad colaDespacho;

    public GestorViajes(ColaConPrioridad implementacionCola) {
        this.viajesTotales = new Viaje[20]; // 20 viajes segun escenario inicial
        this.colaDespacho = implementacionCola;
    }

    public void registrarViaje(Viaje viaje) {
        viajesTotales[viaje.getId()] = viaje;
        colaDespacho.add(viaje.getId(), viaje.getPrioridad());
        System.out.println("Viaje " + viaje.getId() + " registrado y encolado. Prioridad: " + viaje.getPrioridad());
    }

    public Viaje obtenerProximoADespachar() {
        if (!colaDespacho.isEmpty()) {
            int viajeId = colaDespacho.getElement();
            return viajesTotales[viajeId];
        }
        return null;
    }

    public Viaje despacharProximo() {
        if (!colaDespacho.isEmpty()) {
            int viajeId = colaDespacho.getElement();
            colaDespacho.remove();
            Viaje v = viajesTotales[viajeId];
            System.out.println(">>> Despachando " + v.toString());
            return v;
        } else {
            System.out.println("No hay viajes en cola para despachar.");
            return null;
        }
    }

    // Funcionalidad pedida: Reprogramar prioridad de un viaje
    public void modificarPrioridad(int viajeId, int nuevaPrioridad) {
        System.out.println("Modificando prioridad del Viaje " + viajeId + " a " + nuevaPrioridad);
        // Actualizamos el objeto
        if (viajeId >= 0 && viajeId < viajesTotales.length && viajesTotales[viajeId] != null) {
            viajesTotales[viajeId].setPrioridad(nuevaPrioridad);
        } else {
            System.out.println("Viaje no encontrado.");
            return;
        }

        // Táctica anti-violación-TDA: Desencolar todo a un arreglo auxiliar
        // buscar el id a modificar, y volver a encolar todos con sus prioridades actuales
        int[] tempIds = new int[20];
        int[] tempPrioridades = new int[20];
        int count = 0;

        while (!colaDespacho.isEmpty()) {
            int currentId = colaDespacho.getElement();
            int currentPri = colaDespacho.getPriority();
            colaDespacho.remove();
            
            tempIds[count] = currentId;
            // Si es el viaje a modificar, actualizamos la prioridad a encolar
            if (currentId == viajeId) {
                tempPrioridades[count] = nuevaPrioridad;
            } else {
                tempPrioridades[count] = currentPri;
            }
            count++;
        }

        // Volver a encolar todos
        for (int i = 0; i < count; i++) {
            colaDespacho.add(tempIds[i], tempPrioridades[i]);
        }
        System.out.println("Cola re-ordenada exitosamente tras cambio climático.");
    }
}
