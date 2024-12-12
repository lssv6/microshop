package com.microshop.service.impl;

import com.microshop.dto.NewSellerDTO;
import com.microshop.model.Seller;
import com.microshop.repository.SellerRepository;
import com.microshop.service.SellerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void create(NewSellerDTO newSellerDTO) {
        sellerRepository.save(modelMapper.map(newSellerDTO, Seller.class));
    }
}
