package com.example.demo.model.entity;

import com.example.demo.model.entity.BaseEntity;
import com.example.demo.model.entity.Category;
import com.example.demo.model.enums.CategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    @NotEmpty
    private String description;

    @Positive
    private int price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    public String getDescription() {
        return description;
    }

    public Offer setDescription(String description) {
        this.description = description;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public Offer setPrice(int price) {
        this.price = price;
        return this;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}