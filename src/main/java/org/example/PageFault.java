package org.example;

public class PageFault extends Throwable {
    private final int page;

    public PageFault(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}
