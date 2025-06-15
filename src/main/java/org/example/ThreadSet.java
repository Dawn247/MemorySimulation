package org.example;

import java.util.HashMap;

// Container for all threads accessing a simulated memory
public class ThreadSet extends HashMap<Integer, ThreadTask> {
    public void put(int[] accessSchedule, Memory memory, int tid) {
        this.put(tid, new ThreadTask(accessSchedule, memory, tid));
    }
}
