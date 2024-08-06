package com.prodavalnik.prodavalnik.service.impl;

import com.prodavalnik.prodavalnik.model.dto.AddOfferDTO;
import com.prodavalnik.prodavalnik.model.dto.OfferDetailsDTO;
import com.prodavalnik.prodavalnik.model.dto.OffersViewDTO;
import com.prodavalnik.prodavalnik.model.entity.Category;
import com.prodavalnik.prodavalnik.model.entity.Offer;
import com.prodavalnik.prodavalnik.model.entity.Shop;
import com.prodavalnik.prodavalnik.model.entity.User;
import com.prodavalnik.prodavalnik.model.enums.CategoryEnum;
import com.prodavalnik.prodavalnik.model.enums.RoleEnum;
import com.prodavalnik.prodavalnik.repository.OfferRepository;
import com.prodavalnik.prodavalnik.service.CategoryService;
import com.prodavalnik.prodavalnik.service.OfferService;
import com.prodavalnik.prodavalnik.service.ShopService;
import com.prodavalnik.prodavalnik.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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

        List<OfferDetailsDTO> clothes = this.mapOfferToDTO(CategoryEnum.CLOTHES);

        List<OfferDetailsDTO> electronics = this.mapOfferToDTO(CategoryEnum.ELECTRONICS);

        List<OfferDetailsDTO> furniture = this.mapOfferToDTO(CategoryEnum.FURNITURE);

        return new OffersViewDTO(clothes, electronics, furniture);
    }

    public List<OfferDetailsDTO> mapOfferToDTO(CategoryEnum categoryEnum) {

        return this.offerRepository.findAll().stream()
                .filter(offer -> offer.getCategory().getName().equals(categoryEnum))
                .map(offer -> {
                    OfferDetailsDTO dto = this.modelMapper.map(offer, OfferDetailsDTO.class);
                    dto.setCity(offer.getShop().getCity());
                    return dto;
                }).toList();
    }

    @Override
    public void deleteOffer(Long id) {
        String username = this.userService.getLoggedUsername();

        Optional<Offer> optionalOffer = this.offerRepository.findById(id);
        Optional<User> optionalUser = this.userService.findUserByUsername(username);

        if (optionalOffer.isPresent() && optionalUser.isPresent()) {

            User user = optionalUser.get();
            boolean isAdministrator = user.getRoles().stream()
                    .anyMatch(role -> role.getRole().equals(RoleEnum.ADMINISTRATOR));

            if (isAdministrator) {
                this.offerRepository.deleteById(id);
            }
        }
    }

    @Override
    public Optional<Offer> findOfferById(Long id) {
        return this.offerRepository.findById(id);
    }
}
