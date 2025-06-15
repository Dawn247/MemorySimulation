package org.example;

import javax.swing.*;
import java.util.Map;

public class Main {
    /* Prompt:
Además, se pide implementar en su lenguaje favorito una problemática simplificada a la gestión de memo-
ria. Se pide simular un sistema de gestión de memoria virtual con páginas y tablas de páginas, donde múltiples
hilos (procesos) acceden a direcciones virtuales en una memoria física compartida. Su programa debe simular
la gestión de fallos de página, reemplazo de páginas (ej: FIFO), y sincronización para evitar condiciones de
carrera. Se pide:
1. Crear múltiples hilos que representen tareas concurrentes. Cada hilo tiene una lista de direcciones virtua-
les a acceder (por ejemplo, 10, 11, 12, 13, 14 para un proceso, 30, 31, 32, 32, 33 para otro).
2. Compartir una memoria física (vector de páginas) entre todos los hilos.
3. Implementar una tabla de páginas por hilo (mapa de direcciones virtuales a físicas).
4. Usar semáforos o mutexes para sincronizar el acceso a la memoria física.
5. Manejar fallos de página (si una dirección virtual no está en memoria física).
6. Implementar un algoritmo de reemplazo de páginas (ej: FIFO o LRU).
     */


    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("Main");
        ThreadSet threadSet = new ThreadSet();
        Memory memory = new Memory(2);
        threadSet.put(new int[]{1, 2, 3, 1}, memory, 0);
        threadSet.put(new int[]{1, 2, 3, 1, 2}, memory, 1);

        System.out.print("");

        threadSet.forEach((Integer tid, ThreadTask t) -> t.start());
        for (Map.Entry<Integer, ThreadTask> t : threadSet.entrySet()) {
            t.getValue().join();
        }

        threadSet.forEach((Integer tid, ThreadTask t) -> t.printTable());
        memory.printFrameStatus();
    }
}