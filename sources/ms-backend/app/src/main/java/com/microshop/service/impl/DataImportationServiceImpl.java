package com.microshop.service.impl;

import com.microshop.dto.dataimport.CategoryPageData;
import com.microshop.dto.dataimport.ProductPageData;
import com.microshop.dto.dataimport.category.Breadcrumb;
import com.microshop.model.Category;
import com.microshop.model.CategorySEO;
import com.microshop.model.Product;
import com.microshop.model.Seller;
import com.microshop.repository.CategoryRepository;
import com.microshop.repository.CategorySEORepository;
import com.microshop.repository.ProductRepository;
import com.microshop.repository.SellerRepository;
import com.microshop.service.DataImportationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataImportationServiceImpl implements DataImportationService {
    private static Logger log = LoggerFactory.getLogger(DataImportationServiceImpl.class);

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategorySEORepository categorySEORepository;

    @Autowired
    private ProductRepository productRepository;

    /*
     * With the categoryPageDatas, this method saves only the category related data
     * in order to import
     * */
    @Override
    public void importCategoryData(CategoryPageData categoryPageData) {
        // Saves all categories of the breadcrumb.
        for (Breadcrumb breadcrumb : categoryPageData.getBreadcrumb()) {
            long breadId = breadcrumb.getId();
            String breadName = breadcrumb.getName();
            String breadPath = breadcrumb.getPath();

            Optional<Category> dbCategory = categoryRepository.findById(breadId);
            if (dbCategory.isEmpty()) {
                Category savedCategory = new Category();
                savedCategory.setId(breadId);
                savedCategory.setName(breadName);
                savedCategory.setPath(breadPath);
                categoryRepository.save(savedCategory);
                log.info(
                        "Imported category with name -> {}",
                        savedCategory
                                .getName()
                                .substring(
                                        0, Math.min(30, savedCategory.getName().length())));
            }
        }

        String beforePath = "";

        for (Breadcrumb breadcrumb : categoryPageData.getBreadcrumb()) {
            long breadId = breadcrumb.getId();
            Optional<Category> dbCategory = categoryRepository.findById(breadId);
            if (dbCategory.isPresent()) {
                Category category = dbCategory.get();
                String path = category.getPath();
                category.setFullPath(beforePath + path);
                categoryRepository.save(category);
                // log.info("Saved fullPath={}", category.getFullPath());
                beforePath += path;
            } else {
                log.warn("Unable to find category with the given id -> {}.", breadId);
            }
        }

        String prettyPath = "";
        for (Breadcrumb breadcrumb : categoryPageData.getBreadcrumb()) {
            long breadId = breadcrumb.getId();
            Optional<Category> dbCategory = categoryRepository.findById(breadId);
            if (dbCategory.isPresent()) {
                Category category = dbCategory.get();
                String path = category.getName();
                prettyPath += path + "/";
                String categoryPrettyPath = prettyPath.substring(0, prettyPath.length() - 1);
                category.setPrettyPath(categoryPrettyPath);
                categoryRepository.save(category);
                // log.info("Saved prettyPath={}", category.getPrettyPath());
            } else {
                log.warn("Unable to find category with the given id -> {}.", breadId);
            }
        }

        categoryPageData.getBreadcrumb().stream().reduce((b1, b2) -> {
            Category cat1 = categoryRepository.findById(b1.getId()).get();
            Category cat2 = categoryRepository.findById(b2.getId()).get();
            cat2.setParentCategory(cat1);
            categoryRepository.save(cat2);
            return b2;
        });

        // categoryPageData.getBreadcrumb().stream().forEach(breadcrumb -> {
        //    long breadId = breadcrumb.getId();
        //    String breadName = breadcrumb.getName();
        //    String breadPath = breadcrumb.getPath();
        //
        //    Optional<Category> dbCategory = categoryRepository.findById(breadId);
        //    log.info("Is dbCategory empty? {}!", dbCategory.isEmpty());
        //    if(dbCategory.isEmpty()){
        //        Category savedCategory = new Category();
        //        savedCategory.setId(breadId);
        //        savedCategory.setName(breadName);
        //        savedCategory.setPath(breadPath);
        //        categoryRepository.save(savedCategory);
        //        log.info("Imported category with name -> {}", savedCategory.getName().substring(0, Math.min(30,
        // savedCategory.getName().length())));
        //    }
        // });

        // For all the categories, set the fullPath variable.
        // This field must reflect its hierarchy.
        // Looks black magic, but isn't.
        // categoryPageData.getBreadcrumb().stream().reduce("",
        //    (beforePath, breadcrumb)->{
        //        long breadId = breadcrumb.getId();
        //        Optional<Category> dbCategory = categoryRepository.findById(breadId);
        //        if(dbCategory.isEmpty()){
        //            return "";
        //        }
        //        Category category = dbCategory.get();
        //        String path = category.getPath();
        //        category.setFullPath(beforePath + path);
        //        categoryRepository.save(category);
        //        return path;
        //    },
        // (path1, path2)->{return path1.concat(path2);});

        // For all the categories, set the prettyPath variable.
        // This field must reflect its hierarchy.
        // prettyPath is the pretty name of the category.
        // Looks black magic, but isn't.
        // categoryPageData.getBreadcrumb().stream().reduce("",
        //    (beforePath, breadcrumb)->{
        //        long breadId = breadcrumb.getId();
        //        Optional<Category> dbCategory = categoryRepository.findById(breadId);
        //        if(dbCategory.isEmpty()){
        //            return "";
        //        }
        //        Category category = dbCategory.get();
        //        String path = category.getName();
        //        category.setFullPath(beforePath +"/"+ path);
        //        categoryRepository.save(category);
        //        return path;
        //    },
        // (path1, path2)->{return path1.concat(path2);});

        // Fills the parent category field.
        // categoryPageData.getBreadcrumb().stream().reduce((b1, b2) -> {
        //    Category cat1 = categoryRepository.findById(b1.getId()).get();
        //    Category cat2 = categoryRepository.findById(b2.getId()).get();
        //    cat2.setParentCategory(cat1);
        //    categoryRepository.save(cat2);
        //    return b2;
        // });

        // Save category's SEO into the database.
        Breadcrumb lastBreadcrumb = categoryPageData.getBreadcrumb().getLast();
        Category category = categoryRepository.findById(lastBreadcrumb.getId()).get();

        Optional<CategorySEO> dbCategorySEO = categorySEORepository.findByCategoryId(lastBreadcrumb.getId());
        if (dbCategorySEO.isEmpty()) {
            CategorySEO categorySEO = new CategorySEO();
            String seoTitle = categoryPageData.getSeo().getTitle();
            String seoDescription = categoryPageData.getSeo().getTitle();
            String seoTitleHeading = categoryPageData.getSeo().getDescription();
            categorySEO.setCategory(category);
            categorySEO.setTitle(seoTitle);
            categorySEO.setDescription(seoDescription);
            categorySEO.setTitleHeading(seoTitleHeading);
            categorySEORepository.save(categorySEO);
        }
    }

    @Override
    public void importProductData(ProductPageData productPageData) {
        if (productRepository.findByCode(productPageData.getCode()).isPresent()) {
            return;
        }
        Product product = new Product();
        product.setName(productPageData.getName());
        product.setFriendlyName(productPageData.getFriendlyName());
        product.setDescription(productPageData.getDescription());
        product.setTagDescription(productPageData.getTagDescription());

        Seller seller;
        Optional<Seller> dbSeller = sellerRepository.findById(productPageData.getSellerId());

        if (dbSeller.isPresent()) {
            seller = dbSeller.get();
        } else {
            seller = new Seller();
            seller.setId(productPageData.getSellerId());
            seller.setName(productPageData.getSellerName());
            sellerRepository.save(seller);
        }

        product.setSeller(seller);

        Category category;
        Optional<Category> dbCategory = categoryRepository.findByPrettyPath(productPageData.getCategory());
        if (dbCategory.isPresent()) {
            category = dbCategory.get();
            log.info("Category with prettyPath={} does exists.", productPageData.getCategory());

        } else {
            // ??? Just don't import the product.
            log.info("Category with prettyPath={} does not exists.", productPageData.getCategory());
            return;
        }
        product.setCategory(category);

        product.setPrice(productPageData.getPrice());
        product.setOldPrice(productPageData.getOldPrice());
        product.setWarranty(productPageData.getWarranty());
        productRepository.save(product);
        String productName = productPageData.getName();
        log.info("Imported product with name -> {}", productName);
    }
}
