package org.example;

import java.util.HashMap;

public class PageTable extends HashMap<Integer, PageTranslation> {
    private final Memory memory;
    private final int tid;

    /**
     * Constructs a page table, fitting up to a given amount of pages.
     *
     * @param maxSize maximum amount of pages.
     * @param memory physical memory representation.
     */
    public PageTable(Memory memory, int tid) {
        this.memory = memory;
        this.tid = tid;
    }

    private int getFrame() { return memory.getFrame(tid); }
    
    public void addTranslation(int page) {
        this.put(page, new PageTranslation(page, getFrame()));
    }

    public void removeTranslation(int frame) {
        this.forEach((Integer p, PageTranslation e) -> {
            if (e.frame() == frame) this.remove(p);
        });
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
        if (entry == null || !entry.present()) throw new PageFault(page);
        memory.operation(entry.frame(), tid);
    }

    public void printTable() {
        System.out.print("[" + tid + "] Page table: ");
        this.forEach((Integer p, PageTranslation e) -> {
            if (e != null) System.out.print("(" + e.page() + "," + e.frame() + ") ");
        });
        System.out.println();
    }
}
