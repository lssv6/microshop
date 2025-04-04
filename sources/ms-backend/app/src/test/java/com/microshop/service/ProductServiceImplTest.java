package com.microshop.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.github.slugify.Slugify;
import com.microshop.dto.ProductDTO;
import com.microshop.dto.request.NewProduct;
import com.microshop.model.Category;
import com.microshop.model.Manufacturer;
import com.microshop.model.Product;
import com.microshop.model.Seller;
import com.microshop.repository.CategoryRepository;
import com.microshop.repository.ManufacturerRepository;
import com.microshop.repository.ProductRepository;
import com.microshop.repository.SellerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceImplTest {
    @MockitoBean private ProductRepository productRepository;
    @MockitoBean private CategoryRepository categoryRepository;
    @MockitoBean private SellerRepository sellerRepository;
    @MockitoBean private ManufacturerRepository manufacturerRepository;

    @Autowired private ProductService productService;
    @Autowired private Slugify slugify;

    private NewProduct product;

    @BeforeEach
    void initializeProduct() {

        product = new NewProduct();
        product.setName("Calculadora De Bolso 8 Dígitos Preta  Hl - 815l - Bk");
        product.setDescription(
                "Detalhes <br />a linha de calculadoras casio facilitam o seu dia a dia em casa, no"
                    + " trabalho e na faculdade. Fáceis de usar e prática para levar em qualquer"
                    + " lugar. <br />especificações <br />cor: preto; <br />número de dígitos: 8"
                    + " dígitos; <br />tipo de produto: portátil; <br />visor de cristal líquido:"
                    + " visor grande, marcadores de vírgula a cada 3 dígitos; <br />alimentação de"
                    + " energia: pilha aa; <br />memória: memória independente; <br />funções de"
                    + " cálculo <br />cálculo básico; <br />percentual básico (%); <br />raiz"
                    + " quadrada (v). <br />peso: 65 g; <br />tamanho (l × p × a): 11,8 cm x 6,95"
                    + " cm x 1,8 cm.");
        product.setTagDescription(
                "As melhores ofertas e condições de pagamento Descubra a melhor forma de comprar"
                        + " online");
        product.setPrice(3981L); // R$ 39.81
        product.setOldPrice(3384L); // R$ 33.84

        // Foreign entities references
        product.setSellerId(2210L);
        product.setCategoryId(20895L);
        product.setManufacturerId(606L);
    }

    @Test
    void testSaveProduct() { // Also tests generateFriendlyName method
        Seller seller = new Seller();
        seller.setId(2210L);
        seller.setName("Bits & Bytes");
        given(sellerRepository.findById(2210L)).willReturn(Optional.of(seller));

        Category category = new Category();
        category.setId(20895L);
        category.setName("Calculadoras");
        category.setPath("/calculadoras");
        category.setFullName("Automação/PDV/Calculadoras");
        category.setFullPath("/automacao/pdv/calculadoras");
        category.setParent(null); // No need to test depth
        given(categoryRepository.findById(20895L)).willReturn(Optional.of(category));

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(606L);
        manufacturer.setName("Casio");
        manufacturer.setImg("https://images4.kabum.com.br/produtos/fabricantes/logo-casio.jpg");
        given(manufacturerRepository.findById(606L)).willReturn(Optional.of(manufacturer));

        Product savedProduct = new Product();
        savedProduct.setName("Calculadora De Bolso 8 Dígitos Preta  Hl - 815l - Bk");
        savedProduct.setFriendlyName(
                slugify.slugify("Calculadora De Bolso 8 Dígitos Preta  Hl - 815l - Bk"));
        savedProduct.setDescription(
                "Detalhes <br />a linha de calculadoras casio facilitam o seu dia a dia em casa, no"
                    + " trabalho e na faculdade. Fáceis de usar e prática para levar em qualquer"
                    + " lugar. <br />especificações <br />cor: preto; <br />número de dígitos: 8"
                    + " dígitos; <br />tipo de produto: portátil; <br />visor de cristal líquido:"
                    + " visor grande, marcadores de vírgula a cada 3 dígitos; <br />alimentação de"
                    + " energia: pilha aa; <br />memória: memória independente; <br />funções de"
                    + " cálculo <br />cálculo básico; <br />percentual básico (%); <br />raiz"
                    + " quadrada (v). <br />peso: 65 g; <br />tamanho (l × p × a): 11,8 cm x 6,95"
                    + " cm x 1,8 cm.");
        savedProduct.setTagDescription(
                "As melhores ofertas e condições de pagamento Descubra a melhor forma de comprar"
                        + " online");
        savedProduct.setPrice(3981L); // R$ 39.81
        savedProduct.setOldPrice(3384L); // R$ 33.84

        savedProduct.setSeller(seller);
        savedProduct.setCategory(category);
        savedProduct.setManufacturer(manufacturer);

        given(productRepository.save(any())).willReturn(savedProduct);

        ProductDTO productDTO = productService.save(product);

        assertNotNull(productDTO);
        assertEquals("Calculadora De Bolso 8 Dígitos Preta  Hl - 815l - Bk", productDTO.getName());
        assertEquals(
                "calculadora-de-bolso-8-digitos-preta-hl-815l-bk", productDTO.getFriendlyName());
        assertEquals(2210L, productDTO.getSellerId());
        assertEquals(20895L, productDTO.getCategoryId());
        assertEquals(606L, productDTO.getManufacturerId());
    }

    @Test
    void testGetPageOfProducts() {
        List<Product> products = new ArrayList<Product>();
        for (int i = 0; i < 50; i++) {
            Product p = new Product();
            p.setName("Prod %d".formatted(i));
            products.add(p);
        }

        PageImpl<Product> page = new PageImpl<>(products);
        given(productRepository.findByCategoryId(99L, any(Pageable.class)))
                .willReturn((Page<Product>) page);

        Pageable pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("name")));
        Page<Product> page = productService.findByCategoryId(99L, pageRequest);

        List<Product> pageProducts = page.getContent();

        assertNotNull(pageProducts);
        assertEquals("Prod 0", pageProducts.get(0).getName());
        assertEquals("Prod 1", pageProducts.get(1).getName());
    }
}
