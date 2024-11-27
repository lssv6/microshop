package com.microshop.webscraper.sitemap;

import com.microshop.webscraper.downloader.DownloadException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteMapDownloader {
    private static Logger log = LoggerFactory.getLogger(SiteMapDownloader.class);
    private static HttpClient client = HttpClient.newHttpClient();

    public static InputStream downloadSiteMap(URI uri) throws DownloadException {
        log.info("Trying to download sitemap from uri={}", uri.toString());
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        try {
            HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
            log.info("Requested url={} with status={}", uri.toString(), response.statusCode());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new DownloadException("Download failed. Because of ", e);
        }
    }

    public static InputStream downloadSiteMap(String uri) throws DownloadException {
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
            log.info("{}", uri.toString());
        } catch (UnsupportedEncodingException uee) {
            log.error("Cannot encode uri to UTF-8", uee);
            throw new DownloadException("Couldn't encode uri to UTF-8", uee);
        }
        return downloadSiteMap(URI.create(uri));
    }
}
