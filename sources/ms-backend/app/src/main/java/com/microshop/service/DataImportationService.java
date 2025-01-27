package com.microshop.service;

import com.microshop.dto.dataimport.CategoryPageData;
import com.microshop.dto.dataimport.ProductPageData;

public interface DataImportationService {
    public void importCategoryData(CategoryPageData categoryPageData);

    public void importProductData(ProductPageData productPageData);
}
