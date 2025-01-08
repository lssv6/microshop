package com.microshop.controller;

import com.microshop.dto.NewSellerDTO;
import com.microshop.dto.SellerDTO;
import com.microshop.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sellers")
@RestController
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @GetMapping("/{id}")
    public ResponseEntity<SellerDTO> getSeller(@PathVariable Long id) {
        SellerDTO seller = sellerService.findById(id);
        return ResponseEntity.ok(seller);
    }

    @PostMapping
    public ResponseEntity<SellerDTO> createSeller(@RequestBody NewSellerDTO nSellerDTO){
        SellerDTO seller = sellerService.create(nSellerDTO);
        return ResponseEntity.ok(seller);
    }
}
