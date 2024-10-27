package com.microshop.webscraper;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SiteMapCrawler {
    private static final class SiteMapHandler extends DefaultHandler {
        public SiteMapHandler() {}

        @Override
        public void startDocument() throws SAXException {
            System.out.println("DOCUMENT START");
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.println("DOCUMENT START");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            System.out.print("ELEMENT START");
            System.out.print(": ");
            System.out.printf("URI = %s, LOCALNAME = %s, QNAME = %s\n", uri, localName, qName);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            System.out.print("ELEMENT END");
            System.out.print(": ");
            System.out.printf("URI = %s, LOCALNAME = %s, QNAME = %s\n", uri, localName, qName);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            System.out.printf("CHARACTERS FOUND: %s\n", new String(ch, start, length));
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

    public void crawl() throws SAXException, IOException {
        InputStream siteMap;
        try {
            siteMap = SiteMapDownloader.downloadSiteMap(siteMapURL);
        } catch (IOException | InterruptedException ex) {
            System.out.println("Failed to download website map!😵");
            return;
        }

        smParser.parse(siteMap, smHandler);
    }
}
