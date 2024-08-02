package com.prodavalnik.prodavalnik.service;

import com.prodavalnik.prodavalnik.model.entity.Category;
import com.prodavalnik.prodavalnik.model.enums.CategoryEnum;

import java.util.Optional;

public interface CategoryService {

    Optional<Category> findByName(CategoryEnum name);
}