package com.microshop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microshop.dto.ProductDTO;
import com.microshop.dto.request.NewProduct;
import com.microshop.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    @MockitoBean ProductService productService;
    @Autowired ObjectMapper objectMapper;
    @Autowired MockMvc mvc;

    private ProductDTO productDTO;
    private NewProduct newProduct;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(666L);
        productDTO.setName("Calculadora De Bolso 8 Dígitos Preta  Hl - 815l - Bk");
        productDTO.setFriendlyName("calculadora-de-bolso-8-digitos-preta-hl-815l-bk");
        productDTO.setDescription(
                "Detalhes <br />a linha de calculadoras casio facilitam o seu dia a dia em casa, no"
                    + " trabalho e na faculdade. Fáceis de usar e prática para levar em qualquer"
                    + " lugar. <br />especificações <br />cor: preto; <br />número de dígitos: 8"
                    + " dígitos; <br />tipo de produto: portátil; <br />visor de cristal líquido:"
                    + " visor grande, marcadores de vírgula a cada 3 dígitos; <br />alimentação de"
                    + " energia: pilha aa; <br />memória: memória independente; <br />funções de"
                    + " cálculo <br />cálculo básico; <br />percentual básico (%); <br />raiz"
                    + " quadrada (v). <br />peso: 65 g; <br />tamanho (l × p × a): 11,8 cm x 6,95"
                    + " cm x 1,8 cm.");
        productDTO.setTagDescription(
                "As melhores ofertas e condições de pagamento Descubra a melhor forma de comprar"
                        + " online");
        productDTO.setPrice(3981L); // R$ 39.81
        productDTO.setOldPrice(3384L); // R$ 33.84

        productDTO.setSellerId(2210L);
        productDTO.setCategoryId(20895L);
        productDTO.setManufacturerId(606L);

        newProduct = new NewProduct();
        newProduct.setName("Calculadora De Bolso 8 Dígitos Preta  Hl - 815l - Bk");
        newProduct.setDescription(
                "Detalhes <br />a linha de calculadoras casio facilitam o seu dia a dia em casa, no"
                    + " trabalho e na faculdade. Fáceis de usar e prática para levar em qualquer"
                    + " lugar. <br />especificações <br />cor: preto; <br />número de dígitos: 8"
                    + " dígitos; <br />tipo de produto: portátil; <br />visor de cristal líquido:"
                    + " visor grande, marcadores de vírgula a cada 3 dígitos; <br />alimentação de"
                    + " energia: pilha aa; <br />memória: memória independente; <br />funções de"
                    + " cálculo <br />cálculo básico; <br />percentual básico (%); <br />raiz"
                    + " quadrada (v). <br />peso: 65 g; <br />tamanho (l × p × a): 11,8 cm x 6,95"
                    + " cm x 1,8 cm.");
        newProduct.setTagDescription(
                "As melhores ofertas e condições de pagamento Descubra a melhor forma de comprar"
                        + " online");
        newProduct.setPrice(3981L); // R$ 39.81
        newProduct.setOldPrice(3384L); // R$ 33.84

        newProduct.setSellerId(2210L);
        newProduct.setCategoryId(20895L);
        newProduct.setManufacturerId(606L);
    }

    @Test
    void testSaveProd() throws Exception {
        // Given
        given(productService.save(any())).willReturn(productDTO);
        // When
        String body = objectMapper.writeValueAsString(newProduct);
        mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(".price").value(3981));
    }

    @Test
    void testFindById() throws Exception {
        given(productService.findById(666L)).willReturn(Optional.of(productDTO));

        mvc.perform(get("/products/{id}", 666L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(666))
                .andExpect(jsonPath("$.price").value(3981));
    }

    @Test
    void testFindProductByCategory() throws Exception {
        List<ProductDTO> productList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName("Product %d".formatted(i));
            productList.add(productDTO);
        }

        Page<ProductDTO> products = new PageImpl<>(productList);
        given(productService.findByCategoryId(eq(2L), any())).willReturn(products);

        mvc.perform(get("/products/by-category/2?page_number=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Product 0"));
    }
}
