package com.microshop.webscraper.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.html.HTMLDocument;
import org.xml.sax.SAXException;

public class PageDownloader {
    private static final Logger log = LoggerFactory.getLogger(PageDownloader.class);
    private static PageDownloader instance;
    private static DocumentBuilder documentBuilder;
    private HttpClient client;

    private PageDownloader() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("Couldn't build the document builder for PageDownloader", e);
        }
        client = HttpClient.newBuilder().build();
    }

    public PageDownloader getInstance() {
        if (instance == null) instance = new PageDownloader();
        return instance;
    }

    public HTMLDocument downloadPage(URI uri) throws DownloadException, SAXException {
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpResponse<InputStream> response;
        try {
            response = client.send(request, BodyHandlers.ofInputStream());
        } catch (IOException | InterruptedException e) {
            throw new DownloadException("Couldn't download the page. IO Error while downloading.", e);
        }

        InputStream respondeBody = response.body();
        try {
            Document document = documentBuilder.parse(respondeBody);
            if (document instanceof HTMLDocument) {
                return (HTMLDocument) document;
            }
            throw new SAXException("The document isn't an HTML file. Perhaps XML or something else.");
        } catch (SAXException se) {
            throw new SAXException();
        } catch (IOException e) {
            throw new DownloadException("Couldn't parse. We have a problem while reading the inputstream");
        }
    }
}
