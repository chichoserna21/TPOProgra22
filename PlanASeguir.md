# Plan de Implementación: Sistema de Operaciones para Micros (Versión Definitiva)

Este documento detalla el plan de implementación para el Trabajo Práctico de Programación II, estructurado por módulos y especificando las estructuras de datos (TDAs) a utilizar. El plan cumple rigurosamente con las restricciones de la cátedra, asumiendo una política de "cero modificaciones a las firmas de las interfaces" impuestas por la cátedra.

## Resoluciones Arquitectónicas Clave (Revisión Final)

1. **Interfaces Mínimas:** Se usarán **Grafos**, **Diccionario** y **ColaConPrioridad**. 
2. **Uso estricto de `int` en TDAs:** Todos los TDAs operarán internamente con números enteros (`int`). Las interfaces (`Grafos.java`, `Diccionario.java`, `ColaConPrioridad.java`) exigen explícitamente parámetros de tipo `int`. No se alterarán estas firmas.
3. **Mapeo por Arrays Auxiliares (Resolución O(1)):** 
   - A cada `Terminal`, `Micro` y `Viaje` se le asignará un **ID entero secuencial** que operará como clave universal en todo el sistema.
   - En lugar de forzar a los TDAs a manejar objetos o strings, los gestores tendrán arreglos estáticos internos (e.g., `Terminal[]`, `Micro[]`, `Viaje[]`). El `id` entero será literalmente el índice del arreglo, logrando una extracción de datos de costo O(1).
4. **Búsqueda Inversa de Strings a IDs:** Dado que el `Diccionario` de la cátedra no admite `String -> int`, cuando el usuario o la consola ingrese el código `"BUE"`, la búsqueda de su `ID` se hará iterando secuencialmente el arreglo `Terminal[]`. Al ser solo 10 terminales, el costo computacional es virtualmente cero y mantenemos la pureza de las interfaces.
5. **ColaConPrioridad y Viajes:** La cola guardará el `ID del Viaje` como valor y su nivel de urgencia como prioridad. Cuando el TDA despache un viaje, usaremos ese ID entero retornado para buscar el objeto en el arreglo `Viaje[]`.
6. **Grafo:** Grafo dirigido con aristas de peso fijo = 1.

---

## Módulos y Diseño Propuesto

### 1. Entidades del Dominio (Modelos)
Clases puramente orientadas a objetos, todas equipadas con un ID indexable.
- `Terminal`: `int id` (0-9), `String codigo` (ej. "BUE"), `String descripcion`.
- `Micro`: `int id` (0-14), `String patente`, Tipo (Ejecutivo, Semi-cama, Cama), `int cantidadAsignaciones`.
- `Viaje`: **`int id` (0-19)**, `Terminal origen`, `Terminal destino`, `Micro micro`, `Fecha fecha`, `int prioridad`.
- `Fecha`: Atributos de día, mes, año y métodos para validación/comparación.

### 2. Módulo de Planificación de Rutas y Análisis (GestorRutas)
- **Responsabilidad:** Conocer la red logística.
- **Estructura Interna:** Un arreglo `Terminal[10]` para mapear `id -> Objeto` y permitir la iteración lineal que convierta `Código("BUE") -> ID(0)`.
- **TDAs a usar:**
  - `Grafos`: La red topológica de IDs de terminales.
- **Funcionalidad:** Implementar la búsqueda (DFS acotado por paradas) y reportes de grados de entrada/salida directamente sobre el Grafo.

### 3. Módulo de Gestión de Flota (GestorFlota)
- **Responsabilidad:** Seguir el estado de los micros.
- **Estructura Interna:** Un arreglo `Micro[15]` para O(1).
- **TDAs a usar:**
  - `Diccionario`: Cumpliendo el pedido del profesor de usar esta estructura, se mapeará `ID_Micro -> Cantidad de Viajes` o un código de estado (ej. `0` libre, `1` ocupado).

### 4. Módulo de Gestión de Viajes y Prioridades (GestorViajes)
- **Responsabilidad:** Despachar viajes y manejar contingencias climáticas.
- **Estructura Interna:** Un arreglo `Viaje[20]`.
- **TDAs a usar:**
  - `ColaConPrioridad`: Encola `(id_viaje, prioridad)`. Ante un cambio de clima, la cola se vacía temporalmente hacia una Pila, se re-asigna la nueva prioridad, y se vuelve a llenar.

### 5. Controladores del Sistema (Cargador y Reportes)
- `CargadorDatos`: Genera las instancias de los objetos, los inyecta en los Gestores y crea las aristas iniciales en el Grafo.
- `GeneradorReportes`: Interpreta los IDs enteros salientes de los TDAs traduciéndolos a texto amigable consultando los Gestores, e imprime por pantalla.
- `Main`: Punto de entrada de ejecución del escenario. No contiene bucles complejos ni algoritmos de búsqueda.

---

## Plan de Acción (Fase de Código)

1. **TDAs (Estáticos y Dinámicos):** 
   - `GrafoEstatico` (Matriz Adyacencia `int[][]`) y `GrafoDinamico` (Lista de Adyacencia con Nodos).
   - `DiccionarioEstatico` (Dos arreglos paralelos) y `DiccionarioDinamico`.
   - `ColaPrioridadEstatica` (Arreglo con corrimiento) y `ColaPrioridadDinamica`.
   - `ConjuntoEstatico` / `ConjuntoDinamico`.
2. **Clases del Dominio:** `Terminal`, `Micro`, `Viaje`, `Fecha`.
3. **Controladores y Gestores:** Escribir los Gestores asegurando la separación de responsabilidades y el mapeo mediante IDs.
4. **Verificación Global:** Ejecutar el flujo principal para confirmar el funcionamiento de los reportes y asignaciones.
