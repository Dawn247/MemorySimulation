package org.example;

// Handles events not immediately relevant to this representation, such as disk I/O
public class EventHandler {
    public static void operationSuccess(int tid, int frame) {
        System.out.println("[" + tid + "] Operation on frame " + frame);
    }

    public static void operationFailure(int tid, int frame) {
        System.out.println("[" + tid + "] Failed Operation on frame " + frame);
    }

    public static void clean(int tid, int frame) {
        System.out.println("[" + tid + "] Cleaned frame " + frame);
    }

    public static void replacement(int tidOld, int tidNew, int frame) {
        System.out.println("[" + tidNew + "] Replaced frame " + frame + ", formerly allocated to thread " + tidOld);
    }
}
