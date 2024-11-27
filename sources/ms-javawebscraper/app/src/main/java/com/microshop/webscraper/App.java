package com.microshop.webscraper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import com.microshop.webscraper.category.CategoryCrawler;
import com.microshop.webscraper.downloader.DownloadException;
import com.microshop.webscraper.downloader.PageDownloader;
import com.microshop.webscraper.models.Product;
import com.microshop.webscraper.sitemap.SMEntry;
import com.microshop.webscraper.sitemap.SiteMapCrawler;
import com.microshop.webscraper.sitemap.SiteMapDownloader;
import com.microshop.webscraper.sitemap.SitemapCrawlingException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final URI URL = URI.create("https://www.kabum.com.br/sitemap.xml");
    private static final SiteMapCrawler smc = new SiteMapCrawler();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final PageDownloader pageDownloader = PageDownloader.getInstance();

    private static boolean isSale(URI uri) {
        String path = uri.getPath();
        return path.contains("/promocao/");
    }

    private static boolean isShops(URI uri) {
        String path = uri.getPath();
        return path.contains("/lojas/");
    }

    private static boolean isSearch(URI uri) {
        String path = uri.getPath();
        return path.contains("/busca/");
    }

    private static boolean isHotsiteLink(URI uri) {
        String path = uri.getPath();
        return path.contains("/hotsite/");
    }

    private static boolean isBrandLink(URI uri) {
        String path = uri.getPath();
        return path.contains("/marcas/");
    }

    private static boolean isCategoryLink(URI uri) {
        List<String> pathBlacklist = List.of(
                "", "/login", "/carrinho", "/sobre", "/politicas", "/privacidade", "/portaldeprivacidade", "/faq");
        if (pathBlacklist.contains(uri.getPath())) return false;
        if (isHotsiteLink(uri)) return false;
        if (isBrandLink(uri)) return false;
        if (isSearch(uri)) return false;
        if (isShops(uri)) return false;
        if (isSale(uri)) return false;
        return !isProductLink(uri);
    }

    private static boolean isProductLink(URI uri) {
        String path = uri.getPath();
        return path.contains("/produto/");
    }

    private static InputStream downloadSiteMap(URI URL) {
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
            URI location = sme.getLoc();
            try {
                InputStream sitemapIS = downloadSiteMap(location);
                List<SMEntry> innerSitemaps = crawlSitemap(sitemapIS);
                result.addAll(innerSitemaps);
            } catch (SitemapCrawlingException e) {
                log.error("", e);
            }
        }
        return result;
    }

    private static void writeResults(Object result, Type type, String path) throws IOException {
        log.info("Starting to write file in path={}", path);
        FileWriter fileWriter = new FileWriter(path);
        JsonWriter jsonWriter = gson.newJsonWriter(fileWriter);
        gson.toJson(result, type, jsonWriter);
        fileWriter.close();
        log.info("Finished writing file in path={}", path);
    }

    public static void main(String[] args) throws IOException {
        InputStream sitemapIS = downloadSiteMap(URL);
        List<SMEntry> sitemaps = List.of();
        try {
            sitemaps = crawlSitemap(sitemapIS);
        } catch (SitemapCrawlingException sce) {
            log.error("Unable to crawl the sitemap", sce);
        }

        sitemaps = crawlInnerSitemaps(sitemaps);

        List<URI> sitemapsURIs = sitemaps.stream().map(SMEntry::getLoc).toList();
        List<URI> categoryURIs =
                sitemapsURIs.stream().filter(App::isCategoryLink).toList();
        List<URI> productURIs = sitemapsURIs.stream().filter(App::isProductLink).toList();
        int counter = 0;
        for (URI categoryURI : categoryURIs) {
            try {
                log.info("Crawling category={}", categoryURI);
                Document categoryPageDocument = pageDownloader.downloadPage(categoryURI);
                List<Product> products = CategoryCrawler.getProducts(categoryPageDocument);
                writeResults(products, products.getClass(), "%d.json".formatted(counter));
            } catch (DownloadException interruptedException) {
                log.error("Download was failed", interruptedException);
            }
            counter++;
        }
    }
}
