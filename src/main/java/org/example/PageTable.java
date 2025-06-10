package org.example;

public class PageTable {
    int size;
    PageTranslation[] table;

    public PageTable(int maxSize) {
        this.size = maxSize;
        this.table = new PageTranslation[size];
    }

    public void addTranslation(int page, int frame) {
        table[page] = new PageTranslation(page, frame);
    };
}
