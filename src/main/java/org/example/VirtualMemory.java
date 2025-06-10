package org.example;

public class VirtualMemory {
    public final int size;
    byte[] pages;

    VirtualMemory(int size) {
        this.size = size;
        this.pages = new byte[size];
    }

}
