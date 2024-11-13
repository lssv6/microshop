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

    private HTMLDocument dom;

    private Gson gson = new GsonBuilder().create();

    public CategoryCrawler(HTMLDocument dom) {
        this.dom = dom;
    }

    private JsonElement getProps() { // Gambiarra code, hardcoded.
        HTMLScriptElement scriptElement = (HTMLScriptElement) dom.getElementById("__NEXT_DATA__");
        String rawJson = scriptElement.getText();
        var root = JsonParser.parseString(rawJson).getAsJsonObject().asMap();
        return root.get("props");
    }

    private String getCategoryRawData() {
        JsonElement props = getProps();
        String data = props.getAsJsonObject()
                .get("pageProps")
                .getAsJsonObject()
                .get("data")
                .getAsString();
        return data;
    }

    public List<Product> getProducts() {
        String rawData = getCategoryRawData();
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
