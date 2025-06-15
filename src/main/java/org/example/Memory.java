package org.example;

public class Memory {
    private final int memorySize;
    private final MemoryFrame[] frames;
    private int allocationHead;

    /**
     * Constructs a representation of the physical memory, consisting of a given, power-of-two-amount of pages.
     * Stores the set of allocated frames as a HashMap between the frame id and its allocation information.
     *
     * @param memorySize bits needed to identify a frame.
     */
    public Memory(int memorySize) {
        if (memorySize <= 0 || memorySize > 31) throw new IllegalArgumentException("Invalid memory size");
        this.memorySize = memorySize;
        this.allocationHead = 0;
        this.frames = new MemoryFrame[1<<memorySize];
    }

    /**
     * Allocates a frame for a thread, using the clock replacement algorithm on a global scope.
     *
     *  @param tid thread identification.
     */
    public synchronized int getFrame(int tid) {
        MemoryFrame f;
        int i;
        for (i = allocationHead; ; i = (i + 1) % (1 << memorySize)) {
            f = frames[i];
            if (frames[i] == null) break;
            if (f.CLOCK_SECOND_CHANCE()) frames[i] = new MemoryFrame(f.ALLOCATOR_TID(), false);
            else break;
        }
        if (f != null) {
            int tidOld = f.ALLOCATOR_TID();
            EventHandler.replacement(tidOld, tid, i);
            if (tidOld == tid) ThreadTask.currentThreadTask().clean(i);
        }
        frames[i] = new MemoryFrame(tid, true);
        allocationHead = i;
        return i;
    }

    /**
     * Simulates a read/write operation on a given frame, performed by a thread under a critical section.
     *
     * @param frame frame identification.
     * @param tid thread identification.
     */
    public synchronized void operation(int frame, int tid) throws OutdatedReference {
        if (frame < 0 || frame > 1<< memorySize) throw new IllegalArgumentException("Attempted operation on non-existent frame " + frame);
        if (frames[frame].ALLOCATOR_TID() != tid) throw new OutdatedReference();
        EventHandler.operationSuccess(tid, frame);
    }

    public void printFrameStatus() {
        for (MemoryFrame f : frames) if (f != null) System.out.print("1"); else System.out.print("0");
    }
}
