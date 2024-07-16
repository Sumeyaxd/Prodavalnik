package com.example.demo.model.entity;

import com.example.demo.model.enums.CategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private CategoryEnum name;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(min = 3, max = 150)
    private String description;

    public Category() {
    }

    public CategoryEnum getName() {
        return name;
    }

    public void setName(CategoryEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
