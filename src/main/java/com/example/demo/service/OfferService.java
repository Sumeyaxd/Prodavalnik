package com.example.demo.service;

import com.example.demo.model.dto.AddOfferDTO;
import com.example.demo.model.dto.OffersViewDTO;
import com.example.demo.model.entity.Offer;

import java.util.Optional;

public interface OfferService {
    boolean addOffer(AddOfferDTO addOfferDTO);

    OffersViewDTO getAllOffers();

    void deleteOffer(Long id);

    Optional<Offer> findOfferById(Long id);
}
