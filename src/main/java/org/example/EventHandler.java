package org.example;

// Handles events not immediately relevant to this representation, such as disk I/O
public class EventHandler {
    public static void operationSuccess(long tid, int frame) {
        System.out.println("[" + tid + "] Operation on frame " + frame);
    }

    public static void operationFailure(long tid, int frame) {
        System.out.println("[" + tid + "] Failed Operation on frame " + frame);
    }

    public static void clean(long tid, int page) {
        System.out.println("[" + tid + "] Cleaned page " + page);
    }

    public static void replacement(long tidOld, long tidNew, int frame) {
        System.out.println("[" + tidNew + "] Replaced frame " + frame + ", formerly allocated to thread " + tidOld);
    }
}
