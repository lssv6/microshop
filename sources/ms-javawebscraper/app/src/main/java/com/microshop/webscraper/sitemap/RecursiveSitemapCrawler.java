package com.microshop.webscraper.sitemap;

import com.microshop.webscraper.downloader.DownloadException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RecursiveSitemapCrawler { // Not recursive really. As this code is specific and
    private URI root;
    private SiteMapCrawler smCrawler;
    private SiteMapDownloader smDownloader;

    public RecursiveSitemapCrawler(URI root) {
        this.root = root;
        this.smCrawler = new SiteMapCrawler();
        this.smDownloader = new SiteMapDownloader();
    }

    public List<SMEntry> getRootSitemaps() throws SitemapCrawlingException {
        try (InputStream sitemapStream = smDownloader.downloadSiteMap(root)) {
            return smCrawler.crawl(sitemapStream);
        } catch (DownloadException de) {
            throw new SitemapCrawlingException("Unable to crawl, Error while downloading.", de);
        } catch (IOException ioException) {
            throw new SitemapCrawlingException("Unable to crawl, IO error.", ioException);
        }
    }

    public List<SMEntry> getAllSitemaps() throws SitemapCrawlingException {
        List<SMEntry> rootSitemaps = getRootSitemaps();
        List<SMEntry> result = new ArrayList<SMEntry>();
        for (SMEntry sme : rootSitemaps) {
            try (InputStream sitemapStream = smDownloader.downloadSiteMap(sme.getLoc())) {
                result.addAll(smCrawler.crawl(sitemapStream));
            } catch (DownloadException de) {
                throw new SitemapCrawlingException("Unable to crawl, error while downloading", de);
            } catch (IOException ioe) {
                throw new SitemapCrawlingException("Unable to crawl, IO error.", ioe);
            }
        }
        return result;
    }

    public List<URI> getAllInnerLinks() throws SitemapCrawlingException {
        List<URI> result = new ArrayList<URI>();
        List<SMEntry> sitemaps = getAllSitemaps();

        for (SMEntry sitemap : sitemaps) {
            try (InputStream sitemapStream = smDownloader.downloadSiteMap(sitemap.getLoc())) {
                List<SMEntry> entries = smCrawler.crawl(sitemapStream);
                List<URI> uris = entries.stream().map(SMEntry::getLoc).toList();
                result.addAll(uris);
            } catch (DownloadException de) {
                throw new SitemapCrawlingException("Unable to crawl, error while downloading", de);
            } catch (IOException ioe) {
                throw new SitemapCrawlingException("Unable to crawl, IO error.", ioe);
            }
        }
        return result;
    }
}
