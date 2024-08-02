package com.prodavalnik.prodavalnik.service.impl;

import com.prodavalnik.prodavalnik.model.entity.Category;
import com.prodavalnik.prodavalnik.model.enums.CategoryEnum;
import com.prodavalnik.prodavalnik.repository.CategoryRepository;
import com.prodavalnik.prodavalnik.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findByName(CategoryEnum name) {
        return this.categoryRepository.findByName(name);
    }
}
