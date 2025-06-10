package org.example;

public class Memory {
    final int size;

    /**
     * Array of frames’ occupation statuses
     */
    private final boolean[] frames;

    /**
     * Constructs a representation of the physical memory, consisting of a given, power-of-two-amount of pages.
     *
     * @param size bits needed to identify a frame.
     */
    public Memory(int size) {
        if (size <= 0 || size > 31) throw new IllegalArgumentException("Invalid memory size");
        this.size = size;
        this.frames = new boolean[1<<size];
    }

    public synchronized int getFrame() {
        int i = 0;
        while (frames[i]) i++;
        frames[i] = true;
        return i;
    }

    /**
     * Simulates a synchronisation-requiring read/write operation on a given frame.
     *
     * @param frame frame identification.
     */
    public synchronized void operation(int frame, int tid) {
        if (frame < 0 || frame > 1<<size) throw new IllegalArgumentException("Attempted operation on non-existent frame " + frame);
        System.out.println("[" + tid + "] Operation on frame " + frame);
    }

    public void printFrameStatus() {
        for (boolean f : frames) if (f) System.out.print("1"); else System.out.print("0");
    }
}
