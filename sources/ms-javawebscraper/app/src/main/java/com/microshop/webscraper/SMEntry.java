package com.microshop.webscraper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

public class SMEntry {
    private String type;
    private String loc;
    private Date lastmod;

    private SMEntry(String type, String loc, Date lastmod) {
        this.type = type;
        this.loc = loc;
        this.lastmod = lastmod;
        try {
            loc = URLEncoder.encode(loc, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
        }
        setLoc(loc);
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

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setLastmod(Date lastmod) {
        this.lastmod = lastmod;
    }

    @Override
    public String toString() {
        return "SMEntry[type=%s, loc=%s, lastmod=%s]".formatted(type, loc, lastmod);
    }
}
