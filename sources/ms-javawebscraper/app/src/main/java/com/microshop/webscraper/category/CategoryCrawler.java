package com.microshop.webscraper.category;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.Strictness;
import com.microshop.webscraper.models.Product;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CategoryCrawler {

    private static final Gson gson =
            new GsonBuilder().setStrictness(Strictness.LENIENT).create();

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

    public static List<Product> getProducts(Document dom) {
        String rawData = getCategoryRawData(dom);
        // The line bellow unescapes jsonStrings that haves scaped chars like \" and \\
        // String jsonString = StringEscapeUtils.unescapeJson(rawData);
        // But in the case there is " in the middle of the string, the unescaper fails, and we need to
        // Unescape the right way.
        // jsonString = jsonString.replaceAll("([^.])(\"\")", "$1\\\"");
        String jsonString = rawData.replaceAll("\\\"", "\"");
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        JsonArray products = jsonElement
                .getAsJsonObject()
                .get("catalogServer")
                .getAsJsonObject()
                .get("data")
                .getAsJsonArray();

        List<Product> result = new ArrayList<Product>();
        products.forEach(prod -> {
            // Serialize from JsonObject to Product. Product is a POJO.
            result.add(gson.fromJson(prod, Product.class));
        });
        return result;
    }
}
