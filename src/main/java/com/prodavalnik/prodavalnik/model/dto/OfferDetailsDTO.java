package com.prodavalnik.prodavalnik.model.dto;

import com.prodavalnik.prodavalnik.model.enums.ShopEnum;

import java.math.BigDecimal;

public class OfferDetailsDTO {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String imageUrl;

    private ShopEnum city;

    public OfferDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ShopEnum getShop() {
        return city;
    }

    public void setCity(ShopEnum city) {
        this.city = city;
    }
}
