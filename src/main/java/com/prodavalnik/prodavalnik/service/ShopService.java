package com.prodavalnik.prodavalnik.service;


import com.prodavalnik.prodavalnik.model.dto.ShopDetailsDTO;
import com.prodavalnik.prodavalnik.model.entity.Shop;
import com.prodavalnik.prodavalnik.model.enums.ShopEnum;

import java.util.Optional;

public interface ShopService {

    ShopDetailsDTO getShopDetails(ShopEnum city);

    Optional<Shop> findByCity(ShopEnum city);
}
