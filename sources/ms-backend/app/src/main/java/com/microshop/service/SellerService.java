package com.microshop.service;

import java.util.Optional;

import com.microshop.dto.SellerDTO;

public interface SellerService{
    public Optional<SellerDTO> findById(Long id);
}
