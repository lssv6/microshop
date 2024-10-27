package com.microshop.webscraper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

public class SiteMapDownloader {
    static {
        client = HttpClient.newHttpClient();
    }

    private static HttpClient client;

    public static InputStream downloadSiteMap(String siteMapUrl)
            throws InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(siteMapUrl)).build();
        return client.send(request, BodyHandlers.ofInputStream()).body();
    }
}
