package com.microshop.webscraper;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class App {
    public static void main(String[] args) {
        String URL = "https://www.kabum.com.br/sitemap.xml";
        SiteMapCrawler smc;
        try {
            smc = new SiteMapCrawler(URL);
            try {
                smc.crawl();
            } catch (SAXException | IOException | InterruptedException e) {
                System.out.println("Unable to crawl the required url");
                System.exit(1);
            }
        } catch (ParserConfigurationException e) {
            System.out.println("Unable to start site map crawler");
            System.exit(1);
        }
    }
}
