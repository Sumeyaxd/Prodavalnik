package com.example.demo.service;


import com.example.demo.model.dto.ShopDetailsDTO;
import com.example.demo.model.entity.Shop;
import com.example.demo.model.enums.ShopEnum;

import java.util.Optional;

public interface ShopService {

    ShopDetailsDTO getShopDetails(ShopEnum city);

    Optional<Shop> findByCity(ShopEnum city);
}
