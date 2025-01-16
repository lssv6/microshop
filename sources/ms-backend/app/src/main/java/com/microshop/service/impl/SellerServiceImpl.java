package com.microshop.service.impl;

import com.microshop.dto.NewSellerDTO;
import com.microshop.dto.SellerDTO;
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
    public SellerDTO create(NewSellerDTO newSellerDTO) {
        Seller seller = modelMapper.map(newSellerDTO, Seller.class);
        seller = sellerRepository.save(seller);
        return modelMapper.map(seller, SellerDTO.class);
    }

    @Override
    public SellerDTO findById(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow();
        return modelMapper.map(seller, SellerDTO.class);
    }
}
