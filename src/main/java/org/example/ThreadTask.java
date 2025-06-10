package org.example;

public class ThreadTask extends Thread {
    int[] accessSchedule;

    public ThreadTask(int[] accessSchedule) {
        this.accessSchedule = accessSchedule;
    }

    @Override
    public void run() {
        super.run();
        for (int a : accessSchedule){

        }
    }
}
