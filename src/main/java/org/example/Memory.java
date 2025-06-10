package org.example;

public class Memory {
    final int size;
    int[] frames;

    Memory(int size) {
        this.size = size;
        this.frames = new int[size];
    }
}
