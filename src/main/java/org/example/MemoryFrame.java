package org.example;

public record MemoryFrame(
        long allocatorTid,
        boolean clockSecondChance
) {
}
