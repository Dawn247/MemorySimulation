package org.example;

public record PageTranslation(
        int page,
        int frame,

        // Other flags; ignored in this implementation.
        boolean readPerm,
        boolean writePerm,
        boolean noExecute
) {
    public PageTranslation(int page, int frame) {
        this(page, frame, true, true, false);
    }
}
