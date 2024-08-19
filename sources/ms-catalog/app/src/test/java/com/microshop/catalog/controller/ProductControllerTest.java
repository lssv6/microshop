package com.microshop.catalog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.microshop.catalog.AbstractIntegrationTest;
import com.microshop.catalog.dto.NewProductDTO;
import com.microshop.catalog.model.Product;
import com.microshop.catalog.repository.ProductRepository;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

class ProductControllerTest extends AbstractIntegrationTest{

    @Autowired
    ProductRepository productRepository;
    
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp(){
        System.out.println("LocalServerPort=%d".formatted(port));
        RestAssured.baseURI = "http://localhost:" + port;
        productRepository.deleteAll();
    }

    @Test
    void shouldPersistAndReturnAProduct(){
        System.out.println("8=D-");

        Product dummy = newDummyProduct("Product name", "CODE for product");
        Product saved = productRepository.save(dummy);
        var code = "100-100000908WOF";
        var name = "Processador AMD Ryzen 9 7950X3D, 5.7GHz Max Turbo, Cache 128MB, AM5, 16 Núcleos, Vídeo Integrado";
        var description = "FAKE HTML DESCRIPTION";
        var technicalInfo = ""
 
Processador AMD Ryzen 9 7950X3D, 5.7GHz Max Turbo, Cache 128MB, AM5, 16 Núcleos, Vídeo Integrado - 100-100000908WOF
    <BS>
        var product = new NewProductDTO("KB_2378AMD", "Ryzen 5 1600 AMD CPU");

        given()
            .body(new NewProductDTO("Product name")).
        when()
            .post("/products");
    }
    
    
    private Product newDummyProduct(String name, String code){
        Product p = new Product();
        p.setName(name);
        p.setCode(code);
        p.setDescription("DUMMY DESCRIPTION");
        p.setTechnicalInfo("DUMMY TECHNICAL_INFO");
        return p;
    }
}

