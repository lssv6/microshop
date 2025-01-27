package com.microshop.webscraper.models.category;

import com.microshop.webscraper.models.Product;
import java.util.List;
import lombok.Data;

@Data
public class CategoryPageData {
    private List<Breadcrumb> breadcrumb;
    private Seo seo;
    private PageLinks pageLinks;
    private Pagination pagination;
    private PageInfo pageInfo;
    private List<Product> products;
}
