package com.microshop.controller;

import com.microshop.dto.SellerDTO;
import com.microshop.service.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired private SellerService sellerService;

    @GetMapping("/{id}")
    public ResponseEntity<SellerDTO> findById(@PathVariable Long id) {
        Optional<SellerDTO> seller = sellerService.findById(id);
        return ResponseEntity.of(seller);
    }
}
