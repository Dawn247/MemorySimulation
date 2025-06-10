package org.example;

public class ThreadTask extends Thread {
    static int VIRTUAL_MEMORY_SIZE = 100;
    int[] accessSchedule;
    PageTable pageTable;
    final int tid;

    public ThreadTask(int[] accessSchedule, Memory memory, int tid) {
        this.accessSchedule = accessSchedule;
        this.pageTable = new PageTable(VIRTUAL_MEMORY_SIZE, memory, tid);
        this.tid = tid;
    }

    @Override
    public void run() {
        super.run();
        for (int a : accessSchedule) {

            operation(a);
        }
    }

    private void operation(int page) {
        try {
            pageTable.operation(page);
        } catch (PageFault f) {
            pageTable.addTranslation(page);
            operation(page);
        }
    }

    public void printTable() { pageTable.printTable(); }
}
