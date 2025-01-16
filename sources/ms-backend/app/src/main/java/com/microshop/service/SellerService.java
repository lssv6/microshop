package com.microshop.service;

import com.microshop.dto.NewSellerDTO;
import com.microshop.dto.SellerDTO;

public interface SellerService {
    public SellerDTO create(NewSellerDTO newSellerDTO);

    public SellerDTO findById(Long id);
}
