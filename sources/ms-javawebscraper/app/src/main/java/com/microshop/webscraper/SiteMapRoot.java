package com.microshop.webscraper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SiteMapRoot {
  private List<SMEntry> entries = new LinkedList<SMEntry>();

  public SiteMapRoot() {}

  public void addSiteMap(SMEntry sm) {
    entries.add(sm);
  }

  public void addSiteMap(String type, String loc, Date lastmod) {
    entries.add(new SMEntry(type, loc, lastmod));
  }

  public List<SMEntry> getSiteMaps() {
    return entries;
  }
}
