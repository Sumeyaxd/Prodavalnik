package com.example.demo.service.impl;

import com.example.demo.model.dto.AddOfferDTO;
import com.example.demo.model.dto.OffersViewDTO;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Offer;
import com.example.demo.model.entity.Shop;
import com.example.demo.repository.OfferRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.OfferService;
import com.example.demo.service.ShopService;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ShopService shopService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, ShopService shopService, CategoryService categoryService, UserService userService, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.shopService = shopService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean addOffer(AddOfferDTO addOfferDTO) {
        if (addOfferDTO == null) {
            return false;
        }

        Optional<Shop> optionalShop = this.shopService.findByCity(addOfferDTO.getCity());
        Optional<Category> optionalCategory = this.categoryService.findByName(addOfferDTO.getCategory());

        if (optionalCategory.isEmpty() || optionalShop.isEmpty()) {
            return false;
        }

        Category category = optionalCategory.get();
        Shop shop = optionalShop.get();
        Offer offer = this.modelMapper.map(addOfferDTO, Offer.class);
        offer.setShop(shop);
        offer.setCategory(category);

        this.offerRepository.saveAndFlush(offer);
        return true;
    }

    @Override
    public OffersViewDTO getAllOffers() {
        return null;
    }

    @Override
    public void deleteOffer(Long id) {

    }

    @Override
    public Optional<Offer> findOfferById(Long id) {
        return Optional.empty();
    }
}
