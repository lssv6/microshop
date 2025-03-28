package com.microshop.service.impl;

import com.microshop.dto.SellerDTO;
import com.microshop.mapper.SellerMapper;
import com.microshop.repository.SellerRepository;
import com.microshop.service.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired private SellerMapper mapper;

    @Autowired private SellerRepository sellerRepository;

    @Override
    public Optional<SellerDTO> findById(Long id) {
        Optional<SellerDTO> seller = sellerRepository.findById(id).map(s -> mapper.stoDTO(s));
        return seller;
    }
}
