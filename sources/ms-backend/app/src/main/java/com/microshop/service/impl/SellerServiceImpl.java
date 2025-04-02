package com.microshop.service.impl;

import com.microshop.dto.SellerDTO;
import com.microshop.dto.request.NewSeller;
import com.microshop.mapper.SellerMapper;
import com.microshop.model.Seller;
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
        Optional<SellerDTO> seller = sellerRepository.findById(id).map(s -> mapper.toDTO(s));
        return seller;
    }

    @Override
    public Optional<SellerDTO> findByName(String name) {
        Optional<SellerDTO> seller = sellerRepository.findByName(name).map(s -> mapper.toDTO(s));
        return seller;
    }

    @Override
    public SellerDTO save(NewSeller newSeller) {
        Seller s = new Seller();
        s.setName(newSeller.getName());
        return mapper.toDTO(sellerRepository.save(s));
    }
}
