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
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.config.EncoderConfig.encoderConfig;
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
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        productRepository.deleteAll();
    }

    @Test
    void shouldPersistAndReturnAProduct(){

        //Product dummy = newDummyProduct("Product name", "CODE for product");
        //Product saved = productRepository.save(dummy);
        var code = "100-100000908WOF";
        var name = "Processador AMD Ryzen 9 7950X3D, 5.7GHz Max Turbo, Cache 128MB, AM5, 16 Núcleos, Vídeo Integrado";
        var description = "FAKE HTML DESCRIPTION";
        var technicalInfo = "FAKE HTML TECHNICALINFO";
 
        //Processador AMD Ryzen 9 7950X3D, 5.7GHz Max Turbo, Cache 128MB, AM5, 16 Núcleos, Vídeo Integrado - 100-100000908WOF
        var product = new NewProductDTO(code, name, description, technicalInfo, null);

        given().
            contentType("application/json").
            body(product).
        when().
            post("/products").
        then().
            body("code", equalTo(code)).
            body("name", equalTo(name)).
            body("description", equalTo(description)).
            body("technicalInfo", equalTo(technicalInfo));
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

