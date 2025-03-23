package com.microshop.service;

import com.microshop.dto.SellerDTO;

import java.util.Optional;

public interface SellerService {
    public Optional<SellerDTO> findById(Long id);
}
