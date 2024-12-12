package com.microshop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;

    // @Autowired
    // private ProductDescriptionRepository productDescriptionRepository;

    // @Test
    // void persistsProduct() {

    //    ProductDescription productDescription = new ProductDescription();
    //    productDescription.setDescription("Uma caneta que escreve");
    //    productDescription.setTagDescription(
    //            "Compre agora esta caneta que escreve e escreva nem que seja uma carta num pedaço de papel.");
    //    productDescriptionRepository.save(productDescription);

    //    Seller seller = new Seller();
    //    seller.setName("Manoel Gomes Inc.");
    //    sellerRepository.save(seller);

    //    Category category = new Category();
    //    category.setName("Canetas");
    //    category.setPath("/canetas");
    //    categoryRepository.save(category);

    //    Product product = new Product();
    //    product.setName("Caneta Azul");
    //    product.setPrice(1.99d);
    //    product.setOldPrice(3.49d);
    //    product.setWarranty("Comprou porque quis! Sem garantia!");
    //    product.setCategory(category);
    //    product.setSeller(seller);
    //    product.setDescription(productDescription);

    //    productRepository.save(product);

    //    Product product2 = productRepository.findByName("Caneta Azul");
    //    assertEquals(product2, product);
    // }
}
