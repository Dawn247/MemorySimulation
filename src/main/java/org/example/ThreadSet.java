package org.example;

import java.util.HashMap;

// Container for all threads accessing a simulated memory
public class ThreadSet extends HashMap<Long, ThreadTask> {
    public void put(int[] accessSchedule, Memory memory) {
        ThreadTask t = new ThreadTask(accessSchedule, memory);
        this.put(t.threadId(), t);
    }
}
