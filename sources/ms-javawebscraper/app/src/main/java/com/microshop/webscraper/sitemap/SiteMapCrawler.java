package com.microshop.webscraper.sitemap;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SiteMapCrawler {
    private static final Logger log = LoggerFactory.getLogger(SiteMapCrawler.class);

    private static final class SiteMapHandler extends DefaultHandler {
        private SiteMapRoot root;
        private SMEntry.Builder builder;
        private String lastQName;
        private StringBuilder stringBuilder = new StringBuilder();

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
                builder = new SMEntry.Builder().setType("sitemap");
            } else if (qName.equalsIgnoreCase("url")) {
                builder = new SMEntry.Builder().setType("url");
            } else if (qName.equalsIgnoreCase("loc")) {
                lastQName = qName;
            } else if (qName.equalsIgnoreCase("lastmod")) {
                lastQName = qName;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            log.debug("ELEMENT END: URI = {}, LOCALNAME = {}, QNAME = {}", uri, localName, qName);
            if (qName.equalsIgnoreCase("sitemap") || qName.equalsIgnoreCase("url")) {
                root.addSiteMap(builder.build());
            }

            if (qName.equalsIgnoreCase("loc") || qName.equalsIgnoreCase("lastmod")) {
                String string = stringBuilder.toString();
                if (qName.equalsIgnoreCase("loc")) {
                    builder.setLoc(string);
                } else if (qName.equalsIgnoreCase("lastmod")) {
                    builder.setLastmod(string);
                }
                stringBuilder = new StringBuilder();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String string = new String(ch, start, length);
            log.debug("CHARACTERS FOUND: {}, START={}, LENGTH={}", string, start, length);
            stringBuilder.append(ch, start, length);
        }
    }

    private SAXParser smParser;
    private SiteMapHandler smHandler;

    public SiteMapCrawler() {
        SAXParserFactory spf = SAXParserFactory.newDefaultInstance();
        try {
            this.smParser = spf.newSAXParser();
        } catch (SAXException | ParserConfigurationException e) {
            // Almost impossible to happen.
        }
    }

    public List<SMEntry> crawl(InputStream siteMap) throws SitemapCrawlingException {
        smHandler = new SiteMapHandler();
        try {
            smParser.parse(siteMap, smHandler);
        } catch (SAXException e) {
            throw new SitemapCrawlingException("Unable to crawl, SAX parsing error!");
        } catch (IOException e) {
            throw new SitemapCrawlingException("Unable to crawl, IO error!");
        }
        SiteMapRoot smRoot = smHandler.getSiteMapRoot();
        List<SMEntry> sitemaps = smRoot.getSiteMaps();
        return sitemaps;
    }
}
