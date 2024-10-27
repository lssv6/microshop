package com.microshop.webscraper;

import java.util.Date;

public class SMEntry {
    private String type;
    private String loc;
    private Date lastmod;

    public SMEntry(String type, String loc, Date lastmod) {
        this.type = type;
        this.loc = loc;
        this.lastmod = lastmod;
    }

    public String getLoc() {
        return loc;
    }

    public Date getLastMod() {
        return lastmod;
    }

    public String getType() {
        return type;
    }
}
