package com.prodavalnik.prodavalnik.service;

import com.prodavalnik.prodavalnik.model.dto.AddOfferDTO;
import com.prodavalnik.prodavalnik.model.dto.OffersViewDTO;
import com.prodavalnik.prodavalnik.model.entity.Offer;

import java.util.Optional;

public interface OfferService {
    boolean addOffer(AddOfferDTO addOfferDTO);

    OffersViewDTO getAllOffers();

    void deleteOffer(Long id);

    Optional<Offer> findOfferById(Long id);
}
