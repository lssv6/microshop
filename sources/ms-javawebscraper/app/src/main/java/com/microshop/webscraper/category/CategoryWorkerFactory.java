package com.microshop.webscraper.category;

import java.util.concurrent.ThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryWorkerFactory implements ThreadFactory {
    private static Logger log = LoggerFactory.getLogger(CategoryWorkerFactory.class);

    public CategoryWorkerFactory() {}

    @Override
    public Thread newThread(Runnable r) {
        log.info("Started new CategoryWorker thread.");
        return new CategoryWorker(r);
    }
}
