package com.example.demo.repository;

import com.example.demo.model.entity.Shop;
import com.example.demo.model.enums.ShopEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByCity(ShopEnum city);
}
