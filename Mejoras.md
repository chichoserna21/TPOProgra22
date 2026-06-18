# Revisión TP1 — Sistema de Operaciones para Micros

Revisión completa del proyecto contra las **consignas del TP1** y las **normas de la materia**.

---

## 1. Cumplimiento de Consignas (Requerimientos Funcionales)

### 1.1 Planificación de Rutas

| Consigna | Estado | Detalle |
|----------|--------|---------|
| Módulo para planificar rutas | ✅ | [GestorRutas.java](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Gestores/GestorRutas.java) |
| Estructura que represente terminales y conexiones (Grafo) | ✅ | Grafo con implementación estática (matriz de adyacencia) |
| Terminal con código y descripción | ✅ | [Terminal.java](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Dominio/Terminal.java) tiene `codigo` y `descripcion` |
| No permitir rutas repetidas (mismo origen-destino) | ⚠️ | No hay validación explícita. `addEdge` simplemente sobrescribe el peso si ya existe. Funciona "de facto" (no duplica), pero no hay lanzamiento de error ni mensaje informativo al usuario |
| Determinar todas las rutas posibles entre dos terminales con máximo de paradas | ✅ | [buscarRutasPosibles](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Gestores/GestorRutas.java#L78-L85) con DFS/backtracking |
| Identificar rutas no utilizadas (terminales desconectadas) | ✅ | [identificarTerminalesDesconectadas](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Gestores/GestorRutas.java#L43-L73) |
| Crear un viaje con micro y fecha asignados | ✅ | [Viaje.java](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Dominio/Viaje.java) tiene origen, destino, micro y fecha |

---

### 1.2 Gestión de Flota

| Consigna | Estado | Detalle |
|----------|--------|---------|
| Módulo para gestionar micros | ✅ | [GestorFlota.java](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Gestores/GestorFlota.java) |
| Micro con tipo (Ejecutivo, Semi-cama, Cama) | ✅ | [TipoMicro.java](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Dominio/TipoMicro.java) — enum con `EJECUTIVO`, `SEMI_CAMA`, `CAMA` |
| Micro con identificador único (patente o interno) | ✅ | [Micro.java](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Dominio/Micro.java) tiene `id` e `patente` |
| Asignar micro disponible a un viaje (un micro puede tener más de un viaje) | ❌ | **BUG**: Un micro solo puede tener 1 viaje activo. El Diccionario guarda `0=libre, 1=ocupado`, y `asignarViajeAMicro` rechaza micros con estado `1`. Cuando el segundo viaje llega para el mismo micro, imprime "ya está asignado" y **no incrementa asignaciones**. La consigna dice "Un micro puede tener asignado más de un viaje" |
| Estructura con IDs de micros para identificar asignaciones (Diccionario) | ✅ | Usa `Diccionario` con clave=ID micro |
| Consultar y actualizar disponibilidad | ⚠️ | Parcial. `estaDisponible()` y `liberarMicro()` existen, pero `liberarMicro` nunca se llama en el flujo del programa |
| Mostrar micros con mayor cantidad de asignaciones | ✅ | [reportarMicrosConMasAsignaciones](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Gestores/GestorFlota.java#L56-L69) |

> [!CAUTION]
> **Bug crítico en la asignación de micros**: La lógica actual impide que un micro tenga más de un viaje asignado. Solo el primer viaje asignado a cada micro se cuenta. Esto contradice directamente la consigna que dice "Un micro puede tener asignado más de un viaje, ya que se busca representar lo que ocurre a lo largo de un período X."

---

### 1.3 Prioridad en Viajes

| Consigna | Estado | Detalle |
|----------|--------|---------|
| Módulo para gestionar viajes con prioridad | ✅ | [GestorViajes.java](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Gestores/GestorViajes.java) usa `ColaConPrioridad` |
| Permitir modificar prioridad por cuestiones meteorológicas, etc. | ✅ | [modificarPrioridad](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Gestores/GestorViajes.java#L42-L78) desencola, actualiza y re-encola |

---

### 1.4 Análisis de Conexiones

| Consigna | Estado | Detalle |
|----------|--------|---------|
| Listar terminales operadas por la compañía | ✅ | [reporteTerminales](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/org/example/GeneradorReportes.java#L16-L22) |
| Terminales con mayor número de salidas y llegadas | ⚠️ | [obtenerTerminalMasConectada](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Gestores/GestorRutas.java#L117-L135) devuelve **una sola** terminal. No separa el reporte en "más salidas" y "más llegadas" como pide la consigna. Debería reportar ambas métricas por separado |
| Terminales con más conexiones directas con otras terminales | ⚠️ | Se mezcla con el reporte anterior en un solo método. No hay un reporte diferenciado de "conexiones directas" |

> [!WARNING]
> **Reporte 4 incompleto**: La consigna pide dos reportes separados: (1) terminales con mayor número de **salidas** y de **llegadas**, y (2) terminales con más **conexiones directas**. Actualmente se implementa un solo método que mezcla ambos conceptos.

---

### 1.5 Simulación y Reportes

| Consigna | Estado | Detalle |
|----------|--------|---------|
| Agregar nuevos viajes, rutas y micros | ⚠️ | La infraestructura existe (los métodos `agregarTerminal`, `agregarRuta`, `agregarMicro`, `registrarViaje`), pero **no hay simulación interactiva** que permita agregar nuevos elementos después de la carga inicial. El `Main` solo ejecuta un script fijo |
| Reprogramar viajes | ✅ | Se demuestra con `modificarPrioridad(15, 1)` en el Main |
| Utilización promedio de cada micro | ✅ | [reporteUtilizacionMicros](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/org/example/GeneradorReportes.java#L32-L45) calcula promedio de asignaciones |
| Rutas más utilizadas y menos recorridas | ❌ | **No implementado**. No hay tracking de cuántas veces se usa cada ruta, ni reporte que muestre las rutas más/menos frecuentadas |

> [!CAUTION]
> **Falta el reporte de rutas más/menos utilizadas**: La consigna pide "Rutas más utilizadas y menos recorridas" como parte de los reportes detallados. Esto no está implementado.

---

## 2. Cumplimiento del Escenario Inicial

| Requisito | Estado | Detalle |
|-----------|--------|---------|
| 10 terminales | ⚠️ | Se cargan 10 terminales en [CargadorDatos](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/org/example/CargadorDatos.java#L20-L35), pero la consigna lista **13 terminales** de ejemplo (BUE, COR, ROS, MDZ, SLA, TUC, SFE, NQN, BRC, POS, RGL, RES, SDE, TRE). Aunque dice "opera en 10 terminales", faltan 3 terminales de los ejemplos. Está bien cargar solo 10, pero faltarían RGL, RES, SDE y TRE del ejemplo |
| 15 micros | ✅ | Se cargan 15 micros con patentes generadas |
| 20 viajes planificados | ✅ | Se cargan 20 viajes en el loop del CargadorDatos |

---

## 3. Cumplimiento de Restricciones (Normas)

### 3.1 Restricciones Obligatorias

| Restricción | Estado | Detalle |
|-------------|--------|---------|
| **No usar TDAs propios de Java** (Stack, Set, Map, List, Tree) | ✅ | Todas las implementaciones son propias. Solo se usa `java.util.Random` en los conjuntos, lo cual es aceptable |
| **Proyecto con Maven** | ✅ | [pom.xml](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/pom.xml) presente |
| **No modificar interfaces de TDA** (no agregar métodos) | ✅ | Las interfaces tienen los métodos estándar de la cátedra |
| **No usar excepciones genéricas** (RuntimeException) | ❌ | [emptyADT.java](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Excepcionees/emptyADT.java) extiende **directamente de `RuntimeException`**, no de `ErrorTDA`. Además, [ErrorTDA.java](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Excepcionees/ErrorTDA.java) extiende `RuntimeException`. Y en [GestorFlota.java L35](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Gestores/GestorFlota.java#L35) se captura `Exception` genérica en vez de la excepción específica |
| **Seguir Java Code Conventions** | ⚠️ | Ver sección 3.2 |
| **No concentrar lógica en una sola clase** | ✅ | La lógica está bien distribuida en Gestores, Dominio, e Implementaciones |

> [!IMPORTANT]
> **`emptyADT` extiende `RuntimeException` directamente**. Para ser consistente, debería extender `ErrorTDA` como las demás excepciones custom. Además, el catch genérico `catch (Exception e)` en GestorFlota viola el espíritu de la restricción.

---

### 3.2 Problemas de Code Conventions

| Problema | Archivo | Detalle |
|----------|---------|---------|
| Clase con nombre en minúscula | [emptyADT.java](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/Excepcionees/emptyADT.java) | Debería ser `EmptyADT` (PascalCase) |
| Paquete con nombre incorrecto | `Excepcionees` | Tiene doble "e" (typo), y los paquetes en Java deben ser en minúsculas: `excepciones` |
| Paquetes con mayúscula inicial | `Dominio`, `Gestores`, `Interfaces`, `Implementaciones` | Java Code Conventions indica que los paquetes deben ser en **minúsculas**: `dominio`, `gestores`, `interfaces`, `implementaciones` |
| Typo en método | [GeneradorReportes.java L25](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/src/main/java/org/example/GeneradorReportes.java#L25) | `reporteConexionesYSaldasLlegadas` → debería ser `SalidasLlegadas` |

---

### 3.3 Forma de Entrega

| Requisito | Estado | Detalle |
|-----------|--------|---------|
| Repositorio en GitHub privado | ❓ | No se puede verificar desde aquí. El `.git` existe, pero no puedo confirmar si es privado |
| Archivo `.gitignore` con exclusiones de IDE y build | ✅ | [.gitignore](file:///c:/Users/bauti/IdeaProjects/TPOProgra2/.gitignore) excluye `target/`, `.idea/`, `.classpath`, `.project`, etc. |
| Archivo `README` con integrantes, consideraciones y TDAs usados | ❌ | **No existe archivo README** en el proyecto |

> [!CAUTION]
> **Falta el README**. La norma lo exige con: integrantes del grupo, consideraciones tenidas en cuenta, qué TDAs se usaron y el motivo de la decisión.

---

## 4. Resumen de Hallazgos

### ❌ Bloquantes (podrían causar desaprobación)

1. **Falta README** — Requerido explícitamente por las normas
2. **Bug en asignación de micros** — Un micro no puede tener más de un viaje, contradice la consigna directamente
3. **`emptyADT` extiende `RuntimeException`** — Viola la restricción de no usar excepciones genéricas
4. **Falta reporte de rutas más/menos utilizadas** — Consigna del punto 5 no implementada

### ⚠️ Mejoras Necesarias

5. **Reporte de salidas/llegadas/conexiones** — No separa los dos reportes que pide la consigna (punto 4)
6. **Simulación interactiva** — No hay forma de agregar viajes/rutas/micros post-carga; el punto 5 pide una simulación
7. **Catch genérico `Exception`** en GestorFlota — Debería capturar `ErrorClaveInexistente`
8. **Paquetes con mayúscula** — Viola Java Code Conventions
9. **`emptyADT` en minúscula** — Viola naming conventions
10. **Typo en `Excepcionees` y `SaldasLlegadas`** — Errores ortográficos
11. **No se llama nunca a `liberarMicro`** — La funcionalidad de "consultar y actualizar disponibilidad" queda incompleta

### ✅ Puntos Bien Implementados

- TDAs propios (Grafo, Diccionario, Cola con Prioridad, Conjunto) con implementaciones estáticas y dinámicas
- Inyección de implementaciones por interfaz en los constructores de los Gestores
- Proyecto Maven correctamente configurado
- Excepciones custom bien jerarquizadas (salvo `emptyADT`)
- DFS/Backtracking para búsqueda de rutas
- Modificación de prioridad sin violar la interfaz del TDA
- .gitignore completo
