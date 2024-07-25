package com.example.demo.model.dto;

import java.util.ArrayList;
import java.util.List;

public class OffersViewDTO {
    private List<OfferDetailsDTO> clothes;
    private List<OfferDetailsDTO> electronics;
    private List<OfferDetailsDTO> homeAppliances;

    private int clothesCount;
    private int electronicsCount;
    private int homeAppliancesCount;

    public OffersViewDTO() {
        this.clothes = new ArrayList<>();
        this.electronics = new ArrayList<>();
        this.homeAppliances = new ArrayList<>();
    }

    public OffersViewDTO(List<OfferDetailsDTO> clothes, List<OfferDetailsDTO> electronics, List<OfferDetailsDTO> homeAppliances, int clothesCount, int electronicsCount, int homeAppliancesCount) {
        this.clothes = clothes;
        this.electronics = electronics;
        this.homeAppliances = homeAppliances;
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

    public List<OfferDetailsDTO> getHomeAppliances() {
        return homeAppliances;
    }

    public void setHomeAppliances(List<OfferDetailsDTO> homeAppliances) {
        this.homeAppliances = homeAppliances;
    }

    public int getClothesCount() {
        return clothesCount;
    }

    public void setClothesCount(int clothesCount) {
        this.clothesCount = clothesCount;
    }

    public int getElectronicsCount() {
        return electronicsCount;
    }

    public void setElectronicsCount(int electronicsCount) {
        this.electronicsCount = electronicsCount;
    }

    public int getHomeAppliancesCount() {
        return homeAppliancesCount;
    }

    public void setHomeAppliancesCount(int homeAppliancesCount) {
        this.homeAppliancesCount = homeAppliancesCount;
    }
}
