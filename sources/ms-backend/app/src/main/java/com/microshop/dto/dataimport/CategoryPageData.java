package com.microshop.dto.dataimport;

import com.microshop.dto.dataimport.category.Breadcrumb;
import com.microshop.dto.dataimport.category.PageInfo;
import com.microshop.dto.dataimport.category.PageLinks;
import com.microshop.dto.dataimport.category.Pagination;
import com.microshop.dto.dataimport.category.Seo;
import java.util.List;
import lombok.Data;

@Data
public class CategoryPageData {
    private List<Breadcrumb> breadcrumb;
    private Seo seo;
    private PageLinks pageLinks;
    private Pagination pagination;
    private PageInfo pageInfo;
    private List<ProductPageData> products;
}
