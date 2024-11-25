package com.microshop.webscraper;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.ToString;

@ToString
public class SMEntry {
    private String type;
    private URI loc;
    private Date lastmod;

    public static class Builder {
        private SMEntry instance;
        private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        public Builder() {
            instance = new SMEntry(null, null, null);
        }

        public SMEntry build() {
            return instance;
        }

        public Builder setType(String type) {
            instance.type = type;
            return this;
        }

        public Builder setLoc(URI loc) {
            instance.loc = loc;
            return this;
        }

        public Builder setLoc(String loc) {
            instance.setLoc(loc);
            return this;
        }

        public Builder setLastmod(Date lastmod) {
            instance.setLastmod(lastmod);
            return this;
        }

        public Builder setLastmod(String lastmod) {
            try {
                setLastmod(df.parse(lastmod));
            } catch (ParseException pe) {
            }
            return this;
        }
    }

    protected SMEntry(String type, URI loc, Date lastmod) {
        this.type = type;
        this.lastmod = lastmod;
        this.loc = loc;
    }

    public URI getLoc() {
        return loc;
    }

    public Date getLastMod() {
        return lastmod;
    }

    public String getType() {
        return type;
    }

    public void setLoc(String loc) throws IllegalArgumentException {
        this.loc = URI.create(loc.replaceAll(" ", "%20"));
    }

    public void setLastmod(Date lastmod) {
        this.lastmod = lastmod;
    }
}
