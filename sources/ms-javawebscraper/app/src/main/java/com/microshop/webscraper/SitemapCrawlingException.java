package com.microshop.webscraper;

public class SitemapCrawlingException extends Exception {

    public SitemapCrawlingException(String message) {
        super(message);
    }

    public SitemapCrawlingException(String message, Throwable cause) {
        super(message, cause);
    }
}
