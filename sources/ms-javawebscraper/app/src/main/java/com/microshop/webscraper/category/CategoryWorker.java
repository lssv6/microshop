package com.microshop.webscraper.category;

public class CategoryWorker extends Thread {
    private static int increment = 0;

    public CategoryWorker(Runnable cct) {
        super(cct, "CategoryWorker-%d".formatted(increment));
        increment++;
    }
}
