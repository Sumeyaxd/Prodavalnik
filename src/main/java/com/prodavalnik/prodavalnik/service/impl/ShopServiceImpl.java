package com.prodavalnik.prodavalnik.service.impl;

import com.prodavalnik.prodavalnik.model.dto.ShopDetailsDTO;
import com.prodavalnik.prodavalnik.model.entity.Shop;
import com.prodavalnik.prodavalnik.model.enums.ShopEnum;
import com.prodavalnik.prodavalnik.repository.ShopRepository;
import com.prodavalnik.prodavalnik.service.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    public ShopServiceImpl(ShopRepository shopRepository, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ShopDetailsDTO getShopDetails(ShopEnum city) {

        Optional<Shop> optionalShop = this.findByCity(city);

        if (optionalShop.isEmpty()) {
            return null;
        }

        return this.modelMapper.map(optionalShop.get(), ShopDetailsDTO.class);
    }

    @Override
    public Optional<Shop> findByCity(ShopEnum city) {
        return this.shopRepository.findByCity(city);
    }


}
