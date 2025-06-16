package org.example;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("Main");
        ThreadSet threadSet = new ThreadSet();
        Memory memory = new Memory(4);
        //GUI gui = new GUI(memory, threadSet);

        threadSet.put(new int[]{1, 2, 3, 1}, memory);
        threadSet.put(new int[]{1, 2, 3, 1, 2}, memory);
        threadSet.put(new int[]{5, 6, 6, 6, 2}, memory);
        threadSet.put(new int[]{1, 1, 3, 4, 2, 1, 6}, memory);
        threadSet.put(new int[]{1, 1, 1, 1, 1}, memory);
        threadSet.put(new int[]{2, 3, 5, 6, 7}, memory);

        System.out.print("");

        threadSet.forEach((Long tid, ThreadTask t) -> t.start());
        for (Map.Entry<Long, ThreadTask> t : threadSet.entrySet()) {
            t.getValue().join();
        }

        threadSet.forEach((Long tid, ThreadTask t) -> t.printState());
        memory.printFrameStatus();
    }
}