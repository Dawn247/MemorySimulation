package org.example;

public record MemoryFrame(
        int ALLOCATOR_TID,
        boolean CLOCK_SECOND_CHANCE
) {
}
