package org.example;

public class PageTable {
    private final Memory memory;
    private final int tid;
    public int size;
    private final PageTranslation[] table;

    /**
     * Constructs a page table, fitting up to a given amount of pages.
     *
     * @param maxSize maximum amount of pages.
     * @param memory physical memory representation.
     */
    public PageTable(int maxSize, Memory memory, int tid) {
        this.size = maxSize;
        this.table = new PageTranslation[size];
        this.memory = memory;
        this.tid = tid;
    }

    private int getFrame() { return memory.getFrame(); }
    
    public void addTranslation(int page) {
        table[page] = new PageTranslation(page, getFrame());
    }

    public void addTranslation(int page, boolean readPerm, boolean writePerm, boolean noExecute) {
        table[page] = new PageTranslation(true, page, getFrame(), readPerm, writePerm, noExecute);
    }
    
    public void operation(int page) throws PageFault {
        PageTranslation entry = table[page];
        if (entry == null || !entry.present()) throw new PageFault(page);
        memory.operation(entry.frame(), tid);
    }

    public void printTable() {
        System.out.print("[" + tid + "] Page table: ");
        for (PageTranslation entry : table)
            if (entry != null) System.out.print("("+entry.page()+","+entry.frame()+") ");
        System.out.println();
    }
}
