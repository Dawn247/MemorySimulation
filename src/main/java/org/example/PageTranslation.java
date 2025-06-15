package org.example;

public record PageTranslation(
        boolean present,
        int page,
        int frame,

        // Other flags; ignored in this implementation.
        boolean readPerm,
        boolean writePerm,
        boolean noExecute
) {
    public PageTranslation(int page, int frame) {
        this(true, page, frame, true, true, false);
    }
}
