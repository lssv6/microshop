package com.microshop.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.microshop.dto.NewSellerDTO;
import com.microshop.dto.SellerDTO;
import com.microshop.service.SellerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@AutoConfigureMockMvc
@SpringBootTest
public class SellerControllerTest {

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private SellerService sellerService;

    @Test
    void getASeller() {
        SellerDTO sellerDTO = new SellerDTO(1L, "My Seller");

        given(this.sellerService.create(new NewSellerDTO("My Seller"))).willReturn(sellerDTO);

        assertThat(this.mvc.get().uri("/sellers/1"))
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(SellerDTO.class)
                .isEqualTo(sellerDTO);
    }
}
