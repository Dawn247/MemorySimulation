package org.example;

import java.util.Arrays;

public class ThreadTask extends Thread {
    private final int[] accessSchedule;
    private final PageTable pageTable;

    /**
     * Constructs a representation of a user task by the use of a JVM thread.
     *
     * @param accessSchedule array of page IDs of operation targets, in the order they are to be sent.
     * @param memory physical memory the task’s pages are allocated to.
     */
    public ThreadTask(int[] accessSchedule, Memory memory) {
        this.accessSchedule = accessSchedule;
        this.pageTable = new PageTable(memory, this);
        this.setName("[" + this.threadId() + "]");
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
            pageTable.addTranslation(page);
            operation(page);
        }
    }

    public void clean(int frame) {
        EventHandler.clean(this.threadId(), pageTable.getFromFrame(frame).page());
        pageTable.removeTranslation(frame);
    }

    public void printState() {
        pageTable.printTable();
        System.out.print(", schedule " + Arrays.toString(accessSchedule));
        System.out.println();
    }
}
