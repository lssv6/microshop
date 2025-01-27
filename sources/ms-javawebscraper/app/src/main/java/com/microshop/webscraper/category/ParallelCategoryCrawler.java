package com.microshop.webscraper.category;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParallelCategoryCrawler {
    private static final Logger log = LoggerFactory.getLogger(ParallelCategoryCrawler.class);
    private ThreadPoolExecutor tPoolExecutor;
    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(100_000);
    private ThreadFactory categoryWorkerFactory = new CategoryWorkerFactory();

    private AtomicLong lastCrawl;

    public ParallelCategoryCrawler() {
        tPoolExecutor = new ThreadPoolExecutor(32, 64, 5, TimeUnit.SECONDS, workQueue, categoryWorkerFactory);
        lastCrawl = new AtomicLong(Instant.now().getEpochSecond());
    }

    public void shutdown() {
        tPoolExecutor.shutdown();
        try {
            tPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);
            log.info("Shutted down the pool executor");
        } catch (InterruptedException a) {
        }
    }

    public AtomicLong getLastCrawl() {
        return lastCrawl;
    }

    public void crawlCategories(List<URI> uris) {
        for (URI uri : uris) {
            tPoolExecutor.execute(new CategoryCrawlTask(uri, this));
        }
    }

    public void crawlCategory(URI uri) {
        tPoolExecutor.execute(new CategoryCrawlTask(uri, this));
    }
}
