package com.microshop.service.impl;

import com.github.slugify.Slugify;
import com.microshop.dto.ProductDTO;
import com.microshop.dto.request.NewProduct;
import com.microshop.mapper.ProductMapper;
import com.microshop.model.Category;
import com.microshop.model.Manufacturer;
import com.microshop.model.Product;
import com.microshop.model.Seller;
import com.microshop.repository.CategoryRepository;
import com.microshop.repository.ManufacturerRepository;
import com.microshop.repository.ProductRepository;
import com.microshop.repository.SellerRepository;
import com.microshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired private ProductRepository productRepository;
    @Autowired private SellerRepository sellerRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ManufacturerRepository manufacturerRepository;

    @Autowired private ProductMapper mapper;
    @Autowired private Slugify slugify;

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id).map(p -> mapper.toDTO(p));
    }

    @Override
    public ProductDTO save(NewProduct newProduct) {
        Long sellerId = newProduct.getSellerId();
        Long categoryId = newProduct.getCategoryId();
        Long manufacturerId = newProduct.getManufacturerId();

        Seller seller = sellerRepository.findById(sellerId).orElseThrow();
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId).orElseThrow();

        Product product = new Product();
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setTagDescription(newProduct.getTagDescription());
        product.setFriendlyName(slugify.slugify(newProduct.getName()));
        product.setPrice(newProduct.getPrice());
        product.setOldPrice(newProduct.getOldPrice());

        product.setSeller(seller);
        product.setCategory(category);
        product.setManufacturer(manufacturer);

        Product saved = productRepository.save(product);
        return mapper.toDTO(saved);
    }
}
