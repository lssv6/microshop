package com.microshop.service;

import com.microshop.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest // Same as @ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @MockitoBean private ProductRepository productRepository;
    @Autowired private ProductService productService;

    // private Product product;
    //
    // @BeforeEach
    // void initializeProduct(){
    //    product = new Product();
    //    product.setId(2L);
    //    product.setName("CPU Ryzen 5 1600");
    //    product.setDescription("Description of the product");
    //    product.setTagDescription("description-of-the-product");
    //    product.setFriendlyName("cpu-ryzen");
    //    product.setPrice(9999);
    //    product.setOldPrice(109999);

    //    Seller seller = new Seller();
    //    seller.setId(5L);
    //    seller.setName("Kabums");
    //    seller.setVersion(1L);
    //
    //    Category category = new Category();
    //    category.setId(888L);
    //    category.setName("Processors");
    //    category.setFullName("Hardware/Processors");
    //    category.setPath("/processors");
    //    category.setFullPath("/hardware");
    // }

    // @Test
    // void testSaveProduct(){

    //    product.setSeller();

    //    // Given
    //    given(productRepository.findById(2L)).willReturn(Optional.of());
    // }
}
