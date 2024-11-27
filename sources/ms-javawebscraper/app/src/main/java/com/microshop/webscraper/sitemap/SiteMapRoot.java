package com.microshop.webscraper.sitemap;

import java.util.LinkedList;
import java.util.List;

public class SiteMapRoot {
    private List<SMEntry> entries = new LinkedList<SMEntry>();

    public SiteMapRoot() {}

    public void addSiteMap(SMEntry sm) {
        entries.add(sm);
    }

    public List<SMEntry> getSiteMaps() {
        return entries;
    }
}
