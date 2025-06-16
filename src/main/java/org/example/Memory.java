package org.example;

public class Memory {
    private final int memorySize;
    private final MemoryFrame[] frames;
    private int allocationHand;

    /**
     * Constructs a representation of the physical memory, consisting of a given, power-of-two-amount of pages.
     * Stores the set of allocated frames as an array of their allocation information.
     *
     * @param memorySize bits needed to identify a frame.
     */
    public Memory(int memorySize) {
        if (memorySize <= 0 || memorySize > 31) throw new IllegalArgumentException("Invalid memory size");
        this.memorySize = memorySize;
        this.allocationHand = 0;
        this.frames = new MemoryFrame[1<<memorySize];
    }

    // Prevents race conditions on concurrent operation() and getFrame()
    private synchronized void updateFrame(int frame, MemoryFrame value) {
        frames[frame] = value;
    }

    /**
     * Allocates a frame for a thread, using the clock replacement algorithm on a global scope.
     *
     * @param t allocating thread
     */
    public synchronized int getFrame(ThreadTask t) {
        MemoryFrame f;
        int i;
        for (i = allocationHand; ; i = (i + 1) % (1 << memorySize)) {
            f = frames[i];
            if (frames[i] == null) break;
            if (f.clockSecondChance()) updateFrame(i, new MemoryFrame(f.allocatorTid(), false));
            else break;
        }
        if (f != null) {
            long tidOld = f.allocatorTid();
            EventHandler.replacement(tidOld, t.threadId(), i);
            if (tidOld == t.threadId()) t.clean(i);
        }
        updateFrame(i, new MemoryFrame(t.threadId(), true));

        allocationHand = i;
        return i;
    }

    /**
     * Simulates a read/write operation on a given frame, performed by a thread under a critical section.
     *
     * @param frame frame identification.
     * @param tid thread identification.
     */
    public synchronized void operation(int frame, long tid) throws OutdatedReference {
        if (frame < 0 || frame > 1<< memorySize) throw new IllegalArgumentException("Attempted operation on non-existent frame " + frame);
        MemoryFrame f = frames[frame];
        if (f.allocatorTid() != tid) throw new OutdatedReference();
        if (!f.clockSecondChance()) updateFrame(frame, new MemoryFrame(f.allocatorTid(), true));
        EventHandler.operationSuccess(tid, frame);
    }

    public void printFrameStatus() {
        for (MemoryFrame f : frames) if (f != null) System.out.print("1"); else System.out.print("0");
        System.out.println();
    }
}
