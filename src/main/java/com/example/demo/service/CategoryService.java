package com.example.demo.service;
import com.example.demo.model.entity.Category;
import com.example.demo.model.enums.CategoryEnum;


import java.util.Optional;

public interface CategoryService {

    Optional<Category> findByName(CategoryEnum name);
}