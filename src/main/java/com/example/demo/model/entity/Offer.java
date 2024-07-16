package com.example.demo.model.entity;

import com.example.demo.model.entity.BaseEntity;
import com.example.demo.model.entity.Category;
import com.example.demo.model.enums.CategoryEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    @NotEmpty
    private String description;

    @Positive
    private int price;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;


    public String getDescription() {
        return description;
    }

    public Offer setDescription(String description) {
        this.description = description;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public Offer setCategory(CategoryEnum engine) {
        this.category = engine;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Offer setPrice(int price) {
        this.price = price;
        return this;
    }
}