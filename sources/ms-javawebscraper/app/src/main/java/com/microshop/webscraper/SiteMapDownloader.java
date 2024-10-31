package com.microshop.webscraper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteMapDownloader {
  private static Logger log = LoggerFactory.getLogger(SiteMapDownloader.class);
  private static HttpClient client = HttpClient.newHttpClient();

  public static InputStream downloadSiteMap(String siteMapUrl)
      throws InterruptedException, IOException {
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(siteMapUrl)).build();
    HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
    log.info("Requested url={} with status={}", siteMapUrl, response.statusCode());
    return response.body();
  }
}
