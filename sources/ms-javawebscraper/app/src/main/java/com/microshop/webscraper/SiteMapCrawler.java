package com.microshop.webscraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SiteMapCrawler {
    private static final Logger log = LoggerFactory.getLogger(SiteMapCrawler.class);

    private static final class SiteMapHandler extends DefaultHandler {
        private SiteMapRoot root;
        private SMEntry head;
        private String lastQName;
        private SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        public SiteMapHandler() {
            this.root = new SiteMapRoot();
        }

        public SiteMapRoot getSiteMapRoot() {
            return root;
        }

        @Override
        public void startDocument() throws SAXException {
            log.debug("DOCUMENT START");
        }

        @Override
        public void endDocument() throws SAXException {
            log.debug("DOCUMENT END");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            log.debug("ELEMENT START: URI = {}, LOCALNAME = {}, QNAME = {}", uri, localName, qName);
            if (qName.equalsIgnoreCase("sitemapindex")) {
                // INGORE
            } else if (qName.equalsIgnoreCase("sitemap")) {
                head = new SMEntry("sitemap", null, null);
            } else if (qName.equalsIgnoreCase("loc")) {
                lastQName = qName;
            } else if (qName.equalsIgnoreCase("lastmod")) {
                lastQName = qName;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            log.debug("ELEMENT END: URI = {}, LOCALNAME = {}, QNAME = {}", uri, localName, qName);
            if (qName.equalsIgnoreCase("sitemap")) {
                root.addSiteMap(head);
                head = null;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String string = new String(ch, start, length);
            log.debug("CHARACTERS FOUND: {}", string);
            if (lastQName == "loc") {
                head.setLoc(string);
            } else if (lastQName == "lastmod") {
                Date lastmod;
                try {
                    lastmod = df.parse(string);
                } catch (ParseException pe) {
                    return;
                }
                head.setLastmod(lastmod);
            }
        }
    }

    private SAXParser smParser;
    private SiteMapHandler smHandler;
    private String siteMapURL;

    public SiteMapCrawler(String siteMapURL) throws ParserConfigurationException {
        this.siteMapURL = siteMapURL;
        this.smHandler = new SiteMapHandler();

        SAXParserFactory spf = SAXParserFactory.newDefaultInstance();
        try {
            this.smParser = spf.newSAXParser();
        } catch (SAXException | ParserConfigurationException e) {
            throw new ParserConfigurationException("Unable to get a SAX parser!");
        }
    }

    public List<SMEntry> crawl() throws SAXException, IOException, InterruptedException {
        InputStream siteMap;
        try {
            siteMap = SiteMapDownloader.downloadSiteMap(siteMapURL);
        } catch (IOException | InterruptedException ex) {
            log.debug("Failed to download website map!😵");
            throw ex;
        }

        smParser.parse(siteMap, smHandler);
        SiteMapRoot smRoot = smHandler.getSiteMapRoot();
        return smRoot.getSiteMaps();
    }
}
