package org.example;

public class ThreadTask extends Thread {
    private final int[] accessSchedule;
    private final PageTable pageTable;
    private final int tid;

    /**
     * Constructs a representation of a user task by the use of a JVM thread.
     *
     * @param accessSchedule array of page IDs of operation targets, in the order they are to be sent.
     * @param memory physical memory the task’s pages are allocated to.
     * @param tid thread identifier.
     */
    public ThreadTask(int[] accessSchedule, Memory memory, int tid) {
        this.accessSchedule = accessSchedule;
        this.pageTable = new PageTable(memory, tid);
        this.tid = tid;
        this.setName("[" + tid + "]");
    }

    @Override
    public void run() {
        super.run();
        for (int a : accessSchedule) {
            operation(a);
        }
    }

    // Represents a read or write operation, ignoring the privilege flags from PageTranslation. Cleans on demand
    private void operation(int page) {
        try {
            pageTable.operation(page);
        } catch (PageFault f) {
            pageTable.addTranslation(page);
            operation(page);
        } catch (OutdatedReference e) {
            clean(pageTable.get(page).frame());
        }
    }

    public void clean(int frame) {
        int page = pageTable.get(frame).page();
        EventHandler.clean(tid, page);
        pageTable.removeTranslation(frame);
        pageTable.addTranslation(page);
    }

    public static ThreadTask currentThreadTask() {
        return (ThreadTask) Thread.currentThread();
    }

    public void printTable() { pageTable.printTable(); }
}
