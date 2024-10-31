package com.microshop.webscraper;

public class PageCrawler {
    private static PageCrawler instance;

    private PageCrawler() {}

    public PageCrawler getInstance() {
        if (instance == null) {
            instance = new PageCrawler();
        }
        return instance;
    }
}
