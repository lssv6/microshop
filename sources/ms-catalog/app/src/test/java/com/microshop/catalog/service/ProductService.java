package com.microshop.catalog;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.catalog.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.catalog.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.microshop.catalog.model.Product;
import com.microshop.catalog.service.ProductService;

@SpringBootTest
class ProductServiceTest{
    @Autowired
    private ProductService productService;

    @Test void persistsProduct(){
        var code = "123-456GGG";
        var name = "Ryzen 5 4600G";
        var description = "AMD Processor";
        var technicalInfo = "6 Core/ 12 Thread processor. 4.2 Ghz";

        Product p = new Product();
        p.setCode(code);
        p.setName(name);
        p.setDescription(description);
        p.setTechnicalInfo(technicalInfo);

        Product p2 = productService.save(p);
        var id = p2.getId();
        
        Product p3 = productService.findById(id);
        assertEquals(p3.getId(), id);
        assertEquals(p3.getCode(), code);
        assertEquals(p3.getName(), name);
        assertEquals(p3.getDescription(), description);
        assertEquals(p3.getTechnicalInfo(), technicalInfo);
    }

    @Test void productDescriptionCanBeVeryLarge(){
        Product p = new Product();
        var description = new StringBuilder("0123456789");
        Stream.iterate(0, x -> x < 100, x -> x+1 ).forEach((x) -> description.append("0123456789"));
        assertThrows(Exception.class,
            () -> {
                p.setDescription(description.toString());
                productService.save(p);
            }
        );
    }

}

