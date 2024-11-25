package com.microshop.webscraper.category;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.microshop.webscraper.models.Product;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.html.HTMLDocument;
import org.w3c.dom.html.HTMLScriptElement;

public class CategoryCrawler {

    private static final Gson gson = new GsonBuilder().create();

    private static JsonElement getProps(HTMLDocument dom) { // Gambiarra code, hardcoded.
        HTMLScriptElement scriptElement = (HTMLScriptElement) dom.getElementById("__NEXT_DATA__");
        String rawJson = scriptElement.getText();
        var root = JsonParser.parseString(rawJson).getAsJsonObject().asMap();
        return root.get("props");
    }

    private static String getCategoryRawData(HTMLDocument dom) {
        JsonElement props = getProps(dom);
        String data = props.getAsJsonObject()
                .get("pageProps")
                .getAsJsonObject()
                .get("data")
                .getAsString();
        return data;
    }

    public static List<Product> getProducts(HTMLDocument dom) {
        String rawData = getCategoryRawData(dom);
        String jsonString = StringEscapeUtils.escapeJson(rawData);
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        JsonArray products = jsonElement
                .getAsJsonObject()
                .get("catalogServer")
                .getAsJsonObject()
                .get("data")
                .getAsJsonArray();

        List<Product> result = new ArrayList<Product>();
        // Remember, products is a JsonArray.
        products.forEach(prod -> {
            // Serialize from JsonObject to Product. Product is a POJO.
            result.add(gson.fromJson(prod, Product.class));
        });
        return result;
    }
}
