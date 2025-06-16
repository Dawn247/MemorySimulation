package org.example;

import java.util.HashMap;

public class PageTable extends HashMap<Integer, PageTranslation> {
    private final Memory memory;
    private final ThreadTask thread;

    /**
     * Constructs a page table, fitting up to a given amount of pages.
     *
     * @param memory physical memory representation.
     * @param thread corresponding thread.
     */
    public PageTable(Memory memory, ThreadTask thread) {
        this.memory = memory;
        this.thread = thread;
    }

    private int getFrame() { return memory.getFrame(this.thread); }
    
    public void addTranslation(int page) {
        int frame;
        synchronized (this) {frame = getFrame();}
        this.put(page, new PageTranslation(page, frame));
    }

    public void removeTranslation(int frame) {
        this.values().removeIf(pageTranslation -> pageTranslation.frame() == frame);
    }

    public PageTranslation getFromFrame(int frame) {
        for (Entry<Integer, PageTranslation> entry : this.entrySet()) {
            PageTranslation e = entry.getValue();
            if (e.frame() == frame) return e;
        }
        throw new IllegalStateException();
    }

    public void operation(int page) throws PageFault, OutdatedReference {
        PageTranslation entry = this.get(page);
        if (entry == null) throw new PageFault(page);
        memory.operation(entry.frame(), thread.threadId());
    }

    public void printTable() {
        System.out.print("[" + thread.threadId() + "] Page table: ");
        this.forEach((Integer p, PageTranslation e) -> {
            if (e != null) System.out.print("(" + e.page() + "," + e.frame() + ") ");
        });
    }
}
