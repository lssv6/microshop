package com.microshop.webscraper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import com.microshop.webscraper.downloader.DownloadException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final String URL = "https://www.kabum.com.br/sitemap.xml";
    private static final SiteMapCrawler smc = new SiteMapCrawler();
    private static final Gson gson = new GsonBuilder().create();

    private static boolean isCategoryLink(String path) {
        try {
            path = URLEncoder.encode(path, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            log.error("UTF-8 isn't supported");
            // This error will never be thrown
        }
        try {
            return isCategoryLink(new URI(path));
        } catch (URISyntaxException use) {
            log.error("Malformed URI", use);
        }
        return false;
    }

    private static boolean isCategoryLink(URI link) {
        String path = link.getPath();
        log.info("link={}", link);
        log.info("path={}", path);

        String[] pathArray = path.split("/");
        if (pathArray[0].equalsIgnoreCase("produto")) return false;
        return true;
    }

    private static InputStream downloadSiteMap(String URL) {
        try {
            log.info("trying to download url={}", URL);
            return SiteMapDownloader.downloadSiteMap(URL);
        } catch (DownloadException e) {
            log.error("cannot download sitemap from url={}", URL);
            return null;
        }
    }

    private static List<SMEntry> crawlSitemap(InputStream sitemapIS) throws SitemapCrawlingException {
        if (sitemapIS == null) {
            return List.of();
        }
        try {
            return smc.crawl(sitemapIS);
        } catch (IOException e) {
            return List.of();
        }
    }

    private static List<SMEntry> crawlInnerSitemaps(List<SMEntry> sitemaps) {
        List<SMEntry> result = new ArrayList<SMEntry>();
        for (SMEntry sme : sitemaps) {
            String location = sme.getLoc();
            try {
                try {
                    location = URLEncoder.encode(location, "UTF-8");
                } catch (UnsupportedEncodingException uee) {
                    log.error("I dont believe that this happened. Cannot encode url to UTF-8", uee);
                }
                InputStream sitemapIS = downloadSiteMap(location);
                List<SMEntry> innerSitemaps = crawlSitemap(sitemapIS);
                result.addAll(innerSitemaps);
            } catch (SitemapCrawlingException e) {
                log.error("", e);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        InputStream sitemapIS = downloadSiteMap(URL);
        List<SMEntry> sitemaps = List.of();
        try {
            sitemaps = crawlSitemap(sitemapIS);
        } catch (SitemapCrawlingException sce) {
            log.error("Unable to crawl the sitemap", sce);
        }

        sitemaps = crawlInnerSitemaps(sitemaps);

        List<String> sitemapsStringList = sitemaps.stream().map(SMEntry::getLoc).toList();
        List<String> categoryURLList =
                sitemapsStringList.stream().filter(App::isCategoryLink).toList();
        log.info(
                "number of urls obtained = {}; categories = {}; products = {}",
                sitemapsStringList.size(),
                categoryURLList.size(),
                sitemapsStringList.size() - categoryURLList.size());

        try (FileWriter fos = new FileWriter("./output.json")) {
            JsonWriter jw = gson.newJsonWriter(fos);
            gson.toJson(sitemapsStringList, sitemapsStringList.getClass(), jw);

        } catch (IOException e) {
            log.error("ioexception ", e);
        }
    }
}
