package com.microshop.webscraper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final SiteMapCrawler smc = new SiteMapCrawler();
    private static final String URL = "https://www.kabum.com.br/sitemap.xml";

    private static boolean isCategoryLink(URI path) {
        String[] pathArray = path.getPath().split("/", 1);
        if (pathArray[0].equalsIgnoreCase("produto")) return false;
        return true;
    }

    private static InputStream downloadSiteMap(String URL) {
        try {
            return SiteMapDownloader.downloadSiteMap(URL);
        } catch (IOException | InterruptedException e) {
            log.error("cannot download sitemap from url={}", URL);
            return null;
        }
    }

    public static void main(String[] args) {
        var smIS = downloadSiteMap(URL);

        // Crawl main sitemap.
        List<SMEntry> sitemaps = smc.crawl(smIS);
        log.info("{}", sitemaps);

        // For each sitemap inside the main sidemap:
        // map: crawl the inner sitemap and return all the urls inside.
        // reduce: flatten the list of list of SMEntry.
        // get: get the list of SMEntry.
        List<SMEntry> allSitemaps =
                sitemaps.stream()
                        .map(
                                sitemap -> {
                                    String loc = sitemap.getLoc();
                                    log.info("url = {}", loc);
                                    List<SMEntry> urls = smc.crawl(loc);
                                    log.info("urls={}", urls);
                                    return urls;
                                })
                        .reduce(
                                (x, y) -> {
                                    x.addAll(y);
                                    return x;
                                })
                        .get();

        List<URI> sitemapsStringList =
                allSitemaps.stream()
                        .map(SMEntry::getLoc)
                        .map(URI::create)
                        .filter(App::isCategoryLink)
                        .toList();
        log.info("{}", allSitemaps);
        Gson gson = new GsonBuilder().create();

        try (FileWriter fos = new FileWriter("./output.txt")) {
            JsonWriter jw = gson.newJsonWriter(fos);
            gson.toJson(sitemapsStringList, sitemapsStringList.getClass(), jw);

        } catch (IOException e) {
            log.error("ioexception", e);
        }
    }
}
