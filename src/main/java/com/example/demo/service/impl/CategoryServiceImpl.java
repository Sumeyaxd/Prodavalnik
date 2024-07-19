package com.example.demo.service.impl;

import com.example.demo.model.entity.Category;
import com.example.demo.model.enums.CategoryEnum;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
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
