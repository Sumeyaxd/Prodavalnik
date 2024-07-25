package com.example.demo.service.impl;

import com.example.demo.model.dto.ShopDetailsDTO;
import com.example.demo.model.entity.Shop;
import com.example.demo.model.enums.ShopEnum;
import com.example.demo.repository.ShopRepository;
import com.example.demo.service.ShopService;
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

        Optional<Shop> optionalRestaurant = this.findByCity(city);

        if (optionalRestaurant.isEmpty()) {
            return null;
        }

        return this.modelMapper.map(optionalRestaurant.get(), ShopDetailsDTO.class);
    }

    @Override
    public Optional<Shop> findByCity(ShopEnum city) {
        return this.shopRepository.findByCity(city);
    }


}
