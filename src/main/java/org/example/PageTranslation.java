package org.example;

public record PageTranslation(
        boolean present,
        int page,
        int frame,
        boolean readPerm,
        boolean writePerm,
        boolean noExecute
) {
    public PageTranslation(int page, int frame) {
        this(true, page, frame, true, true, false);
    }
}
