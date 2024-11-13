package com.microshop.webscraper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import com.microshop.webscraper.downloader.DownloadException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final SiteMapCrawler smc = new SiteMapCrawler();
    private static final String URL = "https://www.kabum.com.br/sitemap.xml";

    private static final Gson gson = new GsonBuilder().create();

    private static boolean isCategoryLink(URI path) {
        String[] pathArray = path.getPath().split("/", 1);
        if (pathArray[0].equalsIgnoreCase("produto")) return false;
        return true;
    }

    private static InputStream downloadSiteMap(String URL) {
        try {
            return SiteMapDownloader.downloadSiteMap(URL);
        } catch (DownloadException e) {
            log.error("cannot download sitemap from url={}", URL);
            return null;
        }
    }

    public static void main(String[] args) {
        var smIS = downloadSiteMap(URL);

        // Crawl main sitemap.
        List<SMEntry> sitemaps = smc.crawl(smIS);
        log.info("{}", sitemaps);

        // For each sitemap inside the main sitemap:
        // map: get the location of the sitemaps.
        // map: for each sitemap, crawl and return the result
        // reduce: flatten the list of list of SMEntry.
        // get: get the list of SMEntry.
        List<SMEntry> allSitemaps = sitemaps.stream()
                .map(SMEntry::getLoc)
                .map(loc -> {
                    List<SMEntry> result;
                    try {
                        result = smc.crawl(loc);
                        return result;
                    } catch (DownloadException e) {
                        log.error("Failed to download loc={}", loc);
                        return new ArrayList<SMEntry>();
                    }
                })
                .flatMap(sm -> sm.stream())
                .collect(Collectors.toList());

        List<URI> sitemapsStringList = allSitemaps.stream()
                .map(SMEntry::getLoc)
                .map(URI::create)
                .filter(App::isCategoryLink)
                .toList();

        log.info("{}", allSitemaps);

        try (FileWriter fos = new FileWriter("./output.json")) {
            JsonWriter jw = gson.newJsonWriter(fos);
            gson.toJson(sitemapsStringList, sitemapsStringList.getClass(), jw);
            throw new IOException("bad java");

        } catch (IOException e) {
            log.error("ioexception ", e);
        }
    }
}
