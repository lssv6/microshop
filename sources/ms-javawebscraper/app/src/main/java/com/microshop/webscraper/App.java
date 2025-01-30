package com.microshop.webscraper;

import com.microshop.webscraper.category.ParallelCategoryCrawler;
import com.microshop.webscraper.downloader.DownloadException;
import com.microshop.webscraper.sitemap.SMEntry;
import com.microshop.webscraper.sitemap.SiteMapCrawler;
import com.microshop.webscraper.sitemap.SiteMapDownloader;
import com.microshop.webscraper.sitemap.SitemapCrawlingException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final URI URL = URI.create("https://www.kabum.com.br/sitemap.xml");
    private static final SiteMapCrawler smc = new SiteMapCrawler();
    private static final SiteMapDownloader smDownloader = new SiteMapDownloader();

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
                "",
                "/login",
                "/carrinho",
                "/sobre",
                "/politicas",
                "/privacidade",
                "/portaldeprivacidade",
                "/faq",
                "/monte-seu-pc");
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
            return smDownloader.downloadSiteMap(URL);
        } catch (DownloadException e) {
            log.error("cannot download sitemap from url={}", URL);
            return null;
        }
    }

    private static List<SMEntry> crawlSitemap(InputStream sitemapIS) throws SitemapCrawlingException {
        return smc.crawl(sitemapIS);
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

    private static List<URI> crawlAllCategoryURIs() {
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
        return categoryURIs;
    }

    public static void main(String[] args) throws IOException, InterruptedException { // Mingau delicioso!!!
        List<URI> categoryURIs = crawlAllCategoryURIs();
        log.info("Found {} category pages", categoryURIs.size());
        // BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<CategoryCrawlerTask>();
        ParallelCategoryCrawler pcc = new ParallelCategoryCrawler();
        pcc.crawlCategories(categoryURIs);
        // List<URI> productURIs = sitemapsURIs.stream().filter(App::isProductLink).toList();
        // log.info("Started crawl...");
        // int count = 0;
        // int catCount = categoryURIs.size();
        // for (URI categoryURI : categoryURIs) {
        //    count++;
        //    try {
        //        log.info("Crawling category={}, {}/{}", categoryURI, count, catCount);
        //        Document categoryPageDocument = pageDownloader.downloadPage(categoryURI);
        //        List<Product> products = CategoryCrawler.getProducts(categoryPageDocument);
        //        CategoryPageData categoryPageData = CategoryCrawler.getCategoryPageData(categoryPageDocument);
        //        String catName = categoryPageData.getBreadcrumb().getLast().getName();

        //        int pageNumber = categoryPageData.getPagination().getCurrent();
        //        int totalNumberOfPages = categoryPageData.getPagination().getTotal();
        //        log.info("Crawling subcategories.");
        //        for(int i = 0; i <= totalNumberOfPages; i++){
        //            log.info("");
        //            Document nextCategoryPageDocument = pageDownloader.downloadPage(categoryURI);
        //            List<Product> nextProducts = CategoryCrawler.getProducts(categoryPageDocument)
        //            CategoryPageData nextCategoryPageData = CategoryCrawler.getCategoryPageData(categoryPageDocument);

        //        }
        //        writeResults(products, products.getClass(), "products/%s/%i.json".formatted(catName, pageNumber));
        //        writeResults(categoryPageData, categoryPageData.getClass(),
        // "categories/%s/%i".formatted(catName,pageNumber));
        //        log.info("Finished crawling category={}");
        //    } catch (DownloadException interruptedException) {
        //        log.error("Download was failed", interruptedException);
        //    }
        // }
        // log.info("Finished surface crawl!!!");

    }
}
