package com.microshop.service;

import com.microshop.dto.SellerDTO;
import com.microshop.dto.request.NewSeller;

import java.util.Optional;

public interface SellerService {
    public Optional<SellerDTO> findById(Long id);

    public Optional<SellerDTO> findByName(String name);

    public SellerDTO save(NewSeller seller);
}
