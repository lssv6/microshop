package com.microshop.webscraper.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public class PageDownloader {
    // private static final Logger log = LoggerFactory.getLogger(PageDownloader.class);
    private HttpClient client = HttpClient.newBuilder().build();

    public PageDownloader() {}

    public Document downloadPage(URI uri) throws DownloadException {
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        Document document;
        try (InputStream pageInputStream =
                client.send(request, BodyHandlers.ofInputStream()).body(); ) {

            document = Jsoup.parse(pageInputStream, "UTF-8", uri.toString());
        } catch (IOException e) {
            throw new DownloadException("Couldn't parse. We have a problem while downloading.");

        } catch (InterruptedException interruptedException) {
            throw new DownloadException("Couldn't parse. Download has been interrupted.");
        }
        if (document == null) {
            throw new DownloadException("Couldnt download the page");
        }
        return document;
    }
}
