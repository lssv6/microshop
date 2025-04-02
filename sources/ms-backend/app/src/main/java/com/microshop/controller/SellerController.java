package com.microshop.controller;

import com.microshop.dto.SellerDTO;
import com.microshop.dto.request.NewSeller;
import com.microshop.service.SellerService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public ResponseEntity<SellerDTO> findByName(
            @RequestParam(name = "name", required = true) String name) {
        Optional<SellerDTO> seller = sellerService.findByName(name);
        return ResponseEntity.of(seller);
    }

    @PostMapping
    public ResponseEntity<SellerDTO> save(@Valid @RequestBody NewSeller seller) {
        SellerDTO sellerDTO = sellerService.save(seller);
        return ResponseEntity.ofNullable(sellerDTO);
    }
}
