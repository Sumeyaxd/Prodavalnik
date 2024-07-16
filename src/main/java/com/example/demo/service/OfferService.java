package com.example.demo.service;

import com.example.demo.model.dto.AddOfferDTO;
import com.example.demo.model.dto.OffersViewDTO;
import com.example.demo.model.entity.Offer;

import java.util.Optional;

public interface OfferService {
    boolean addDish(AddOfferDTO addDishDTO);

    OffersViewDTO getAllDishes();

    void deleteDish(Long id);

    Optional<Offer> findOfferById(Long id);
}
