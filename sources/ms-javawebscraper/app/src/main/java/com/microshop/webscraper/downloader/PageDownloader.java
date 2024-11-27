package com.microshop.webscraper.downloader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public class PageDownloader {
    // private static final Logger log = LoggerFactory.getLogger(PageDownloader.class);
    private static PageDownloader instance;
    private HttpClient client = HttpClient.newBuilder().build();

    private PageDownloader() {}

    public static PageDownloader getInstance() {
        if (instance == null) instance = new PageDownloader();
        return instance;
    }

    public Document downloadPage(URI uri) throws DownloadException {
        try {
            int TIMEOUT = 10 * 1000; // 10 Seconds
            Document document = Jsoup.parse(uri.toURL(), TIMEOUT);
            return document;
        } catch (IOException e) {
            throw new DownloadException("Couldn't parse. We have a problem while downloading ");
        }
    }
}
