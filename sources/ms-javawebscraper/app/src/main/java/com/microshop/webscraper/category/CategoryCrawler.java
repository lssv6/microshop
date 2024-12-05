package com.microshop.webscraper.category;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Strictness;
import com.microshop.webscraper.models.Product;
import com.microshop.webscraper.models.category.Breadcrumb;
import com.microshop.webscraper.models.category.CategoryPageData;
import com.microshop.webscraper.models.category.PageInfo;
import com.microshop.webscraper.models.category.PageLinks;
import com.microshop.webscraper.models.category.Pagination;
import com.microshop.webscraper.models.category.Seo;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryCrawler {

    private static final Gson gson =
            new GsonBuilder().setStrictness(Strictness.LENIENT).create();
    private static final Logger log = LoggerFactory.getLogger(CategoryCrawler.class);

    private static JsonElement getProps(Document dom) { // Gambiarra code, hardcoded.
        Element scriptElement = dom.getElementById("__NEXT_DATA__");
        String rawJson = scriptElement.data();
        var root = JsonParser.parseString(rawJson).getAsJsonObject().asMap();
        return root.get("props");
    }

    private static String getCategoryRawData(Document dom) {
        JsonElement props = getProps(dom);
        String data = props.getAsJsonObject()
                .get("pageProps")
                .getAsJsonObject()
                .get("data")
                .getAsString();
        return data;
    }

    private static JsonObject getCategoryParsedLinks(Document dom) {
        String rawData = getCategoryRawData(dom);
        String jsonString = rawData.replaceAll("\\\"", "\"");
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        JsonObject links = jsonElement
                .getAsJsonObject()
                .get("catalogServer")
                .getAsJsonObject()
                .get("links")
                .getAsJsonObject();
        return links;
    }

    private static JsonObject getCategoryParsedMeta(Document dom) {
        String rawData = getCategoryRawData(dom);
        String jsonString = rawData.replaceAll("\\\"", "\"");
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        JsonObject meta = jsonElement
                .getAsJsonObject()
                .get("catalogServer")
                .getAsJsonObject()
                .get("meta")
                .getAsJsonObject();
        return meta;
    }

    private static JsonArray getCategoryParsedData(Document dom) {
        String rawData = getCategoryRawData(dom);
        String jsonString = rawData.replaceAll("\\\"", "\"");
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        JsonArray products = jsonElement
                .getAsJsonObject()
                .get("catalogServer")
                .getAsJsonObject()
                .get("data")
                .getAsJsonArray();
        return products;
    }

    private static JsonObject getCategoryParsedPagination(Document dom) {
        String rawData = getCategoryRawData(dom);
        String jsonString = rawData.replaceAll("\\\"", "\"");
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        JsonObject pagination =
                jsonElement.getAsJsonObject().getAsJsonObject("catalogServer").getAsJsonObject("pagination");
        // String jsonString = rawData.replaceAll("\\\"", "\"");
        // JsonObject jsonElement = JsonParser.parseString(jsonString).getAsJsonObject();
        // JsonObject pagination = jsonElement.getAsJsonObject("pagination");
        return pagination;
    }

    private static PageInfo getPageInfo(Document dom) {
        JsonObject meta = getCategoryParsedMeta(dom);
        JsonObject pageInfo = meta.getAsJsonObject("page");
        return gson.fromJson(pageInfo, PageInfo.class);
    }

    private static Seo getSeo(Document dom) {
        JsonObject meta = getCategoryParsedMeta(dom);
        JsonObject seo = meta.getAsJsonObject("seo");
        return gson.fromJson(seo, Seo.class);
    }

    private static Pagination getPagination(Document dom) {
        JsonObject pagination = getCategoryParsedPagination(dom);
        return gson.fromJson(pagination, Pagination.class);
    }

    private static PageLinks getPageLinks(Document dom) {
        JsonObject linksJsonObject = getCategoryParsedLinks(dom);
        return gson.fromJson(linksJsonObject, PageLinks.class);
    }

    private static List<Breadcrumb> getBreadcrumbs(Document dom) {
        JsonObject meta = getCategoryParsedMeta(dom);
        JsonArray breadcrumbs = meta.getAsJsonArray("breadcrumb");
        List<Breadcrumb> breadcrumbsList = new ArrayList<Breadcrumb>();
        breadcrumbs.forEach(b -> breadcrumbsList.add(gson.fromJson(b, Breadcrumb.class)));
        return breadcrumbsList;
    }

    public static CategoryPageData getCategoryPageData(Document dom) {
        CategoryPageData result = new CategoryPageData();
        result.setBreadcrumb(getBreadcrumbs(dom));
        result.setSeo(getSeo(dom));
        result.setPagination(getPagination(dom));
        result.setPageLinks(getPageLinks(dom));
        result.setPageInfo(getPageInfo(dom));
        return result;
    }

    // The line bellow unescapes jsonStrings that haves scaped chars like \" and \\
    // String jsonString = StringEscapeUtils.unescapeJson(rawData);
    // But in the case there is " in the middle of the string, the unescaper fails, and we need to
    // Unescape the right way.
    // jsonString = jsonString.replaceAll("([^.])(\"\")", "$1\\\""); // Seems that is broken.

    public static List<Product> getProducts(Document dom) {
        if (dom == null) throw new NullPointerException("dom is null");
        try {
            JsonArray products = getCategoryParsedData(dom);
            List<Product> result = new ArrayList<Product>();
            products.forEach(prod -> {
                // Serialize from JsonObject to Product. Product is a POJO.
                result.add(gson.fromJson(prod, Product.class));
            });
            return result;
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }
}
