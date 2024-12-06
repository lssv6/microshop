package com.microshop.service.impl;

import com.microshop.model.Seller;
import com.microshop.service.SellerService;
import org.springframework.stereotype.Service;

@Service
public interface SellerServiceImpl extends SellerService {
    @Override
    public Seller create(Seller seller);
}
