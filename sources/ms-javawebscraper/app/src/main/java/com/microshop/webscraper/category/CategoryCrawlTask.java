package com.microshop.webscraper.category;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import com.microshop.webscraper.downloader.DownloadException;
import com.microshop.webscraper.downloader.PageDownloader;
import com.microshop.webscraper.models.category.CategoryPageData;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.Duration;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryCrawlTask implements Runnable {
    private static Logger log = LoggerFactory.getLogger(CategoryCrawlTask.class);
    private PageDownloader pageDownloader = new PageDownloader();
    private URI uri;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private ParallelCategoryCrawler pcc;

    public CategoryCrawlTask(URI uri, ParallelCategoryCrawler pcc) {
        this.uri = uri;
        this.pcc = pcc;
    }

    public static URI nextPageURI(URI pageURI) {
        String query = pageURI.getQuery();
        if (query == null || query.equalsIgnoreCase("")) {
            try {
                return new URI(
                        pageURI.getScheme(),
                        pageURI.getAuthority(),
                        pageURI.getPath(),
                        "page_number=2",
                        pageURI.getFragment());
            } catch (URISyntaxException uriSyntaxException) {
                // This will never happen
            }
        }
        String[] queryParams = query.split("&");

        // Checks if query has page_number query.
        int foundPageNumberIndex = -1;
        for (int i = 0; i < queryParams.length; i += 2) {
            if (queryParams[i].split("=")[0].equalsIgnoreCase("page_number")) foundPageNumberIndex = i;
        }

        // Checks if it doesn't have page_number query while having another query.
        if (foundPageNumberIndex == -1) {
            try {
                return new URI(
                        pageURI.getScheme(),
                        pageURI.getAuthority(),
                        pageURI.getPath(),
                        "page_number=2",
                        pageURI.getFragment());
            } catch (URISyntaxException uriSyntaxException) {
                // This will never happen
            }
            // If the query has page_number query, try to modify the number after the '='
        } else { // Dark magic. Why do Java doesn't have any URL/URI query parsing methods like JavaScript?
            String nextPageQuery = queryParams[foundPageNumberIndex];
            String pageNumber = nextPageQuery.split("=")[1];
            pageNumber = (Integer.parseInt(pageNumber) + 1) + "";
            queryParams[foundPageNumberIndex] = "page_number=%s".formatted(pageNumber);
            String newQuery = String.join("&", queryParams);
            try {
                return new URI(
                        pageURI.getScheme(),
                        pageURI.getAuthority(),
                        pageURI.getPath(),
                        newQuery,
                        pageURI.getFragment());
            } catch (URISyntaxException uriSyntaxException) {
                // This will never happen
            }
        }
        throw new IllegalArgumentException("Unable to find the next page for the given URL" + pageURI);
    }

    private void writeData(Object result, Type type, String crawlType, String catName, int pageNumber)
            throws IOException {
        Path basePath = Path.of(crawlType, catName);
        File baseDir = basePath.toFile();
        boolean foldersWasCreated = baseDir.mkdirs();
        // if(!foldersWasCreated){
        //    throw new IOException("Cannot create the folders");
        // }
        File fileToWrite = new File(baseDir, "%d.json".formatted(pageNumber));
        FileWriter fileWriter = new FileWriter(fileToWrite);
        JsonWriter jsonWriter = gson.newJsonWriter(fileWriter);
        gson.toJson(result, type, jsonWriter);
        fileWriter.close();
    }

    private Document downloadPage(URI uri) throws DownloadException {
        int downloadTries = 0;
        do {
            try {
                Document doc = pageDownloader.downloadPage(uri);
                return doc;
            } catch (DownloadException downloadException) {
                log.error("Error while downloading the page, Trying again in one second.");
                try {
                    Thread.sleep(Duration.ofSeconds(1));
                } catch (InterruptedException interruptedException) {
                    log.error("Interrupted thread.");
                    break;
                }
                downloadTries++;
                continue;
            }
        } while (downloadTries < 5);
        throw new DownloadException("Failed to download the page.");
    }

    @Override
    public void run() {
        boolean hasNextPage = false;
        log.info("Crawling uri={}", uri);
        Document doc;
        try {
            doc = downloadPage(uri);
        } catch (DownloadException downloadException) {
            log.error("Failed to download the page.");
            return;
        }

        CategoryPageData categoryPageData;

        try {
            categoryPageData = CategoryCrawler.getCategoryPageData(doc);
        } catch (NullPointerException nullPointerException) {
            log.error("Could not get the category data for url={%s}".formatted(uri.toString()));
            return;
        }

        // Make a catName to be the fullCatname of the category. Being 1TB not anymore ambiguous category.
        // Currently, catName will be or "Hardware>HDD>1TB" or "Hardware>SSD>1TB" instead of "1TB".
        String catName = categoryPageData.getBreadcrumb().stream()
                .map(b -> b.getName())
                .reduce((b1, b2) -> b1 + ">" + b2)
                .get();
        catName = catName.replace('/', '\u2044');
        int pageNumber = categoryPageData.getPagination().getCurrent();

        hasNextPage = pageNumber < categoryPageData.getPagination().getTotal();

        try {
            writeData(categoryPageData, CategoryPageData.class, "categories", catName, pageNumber);
        } catch (IOException e) {
            log.error("Error while saving the crawled data.", e);
            e.printStackTrace();
        }

        hasNextPage = pageNumber < categoryPageData.getPagination().getTotal();

        if (hasNextPage) pcc.crawlCategory(nextPageURI(uri));
        log.info(
                "Crawled uri={} . {}",
                uri,
                hasNextPage
                        ? "Going to crawl the page number %d.".formatted(pageNumber + 1)
                        : "No more pages to crawl in this category");
    }
}
