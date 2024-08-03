package com.prodavalnik.prodavalnik.model.dto;

import com.prodavalnik.prodavalnik.model.enums.CategoryEnum;
import com.prodavalnik.prodavalnik.model.enums.ShopEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AddOfferDTO {
    @NotNull
    @Size(min = 3, max = 20, message = "{add_offer_name_length}")
    private String name;

    @NotNull
    @Size(min = 3, max = 150, message = "{add_offer_description_length}")
    private String description;

    @NotNull(message = "{add_offer_price_not_null}")
    @Positive(message = "{add_offer_price_positive}")
    private BigDecimal price;

    @NotNull
    @Size(min = 3, max = 300, message = "{add_offer_image_url_length}")
    private String imageUrl;

    @NotNull(message = "{add_offer_category_not_null}")
    private CategoryEnum category;

    @NotNull(message = "{add_offer_shop_not_null}")
    private ShopEnum city;

    public AddOfferDTO() {
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

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public ShopEnum getCity() {
        return city;
    }

    public void setCity(ShopEnum city) {
        this.city = city;
    }

}
