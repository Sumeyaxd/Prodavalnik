package com.prodavalnik.prodavalnik.model.dto;

import java.util.ArrayList;
import java.util.List;

public class OffersViewDTO {
    private List<OfferDetailsDTO> clothes;
    private List<OfferDetailsDTO> electronics;
    private List<OfferDetailsDTO> furniture;

    private int clothesCount;
    private int electronicsCount;
    private int furnitureCount;

    public OffersViewDTO() {
        this.clothes = new ArrayList<>();
        this.electronics = new ArrayList<>();
        this.furniture = new ArrayList<>();
    }

    public OffersViewDTO(List<OfferDetailsDTO> clothes, List<OfferDetailsDTO> electronics, List<OfferDetailsDTO> furniture) {
        this.clothes = clothes;
        this.electronics = electronics;
        this.furniture = furniture;
    }

    public List<OfferDetailsDTO> getClothes() {
        return clothes;
    }

    public void setClothes(List<OfferDetailsDTO> clothes) {
        this.clothes = clothes;
    }

    public List<OfferDetailsDTO> getElectronics() {
        return electronics;
    }

    public void setElectronics(List<OfferDetailsDTO> electronics) {
        this.electronics = electronics;
    }

    public List<OfferDetailsDTO> getFurniture() {
        return furniture;
    }

    public void setFurniture(List<OfferDetailsDTO> furniture) {
        this.furniture = furniture;
    }

    public int getFurnitureCount() {
        return this.furniture.size();
    }

    public void setFurnitureCount(int furnitureCount) {
        this.furnitureCount = furnitureCount;
    }

    public int getClothesCount() {
        return this.clothes.size();
    }


    public void setClothesCount(int clothesCount) {
        this.clothesCount = clothesCount;
    }

    public int getElectronicsCount() {
        return this.electronics.size();
    }

    public void setElectronicsCount(int electronicsCount) {
        this.electronicsCount = electronicsCount;
    }


}
