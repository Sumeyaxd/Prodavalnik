package com.example.demo.service;

import com.example.demo.model.entity.Category;
import com.example.demo.model.enums.CategoryEnum;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.impl.CategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository mockCategoryRepository;

    private CategoryServiceImpl categoryServiceToTest;

    @BeforeEach
    public void setUp() {
        this.categoryServiceToTest = new CategoryServiceImpl(mockCategoryRepository);
    }

    @Test
    public void testFindByName() {
        CategoryEnum categoryEnum = CategoryEnum.CLOTHES;

        Category category = new Category();
        category.setName(categoryEnum);

        when(mockCategoryRepository.findByName(categoryEnum)).thenReturn(Optional.of(category));

        Optional<Category> foundCategory = categoryServiceToTest.findByName(categoryEnum);

        assertEquals(categoryEnum, foundCategory.get().getName());
    }

    @Test
    public void testFindByNameNotFound() {

        Category category = new Category();

        when(mockCategoryRepository.findByName(category.getName())).thenReturn(Optional.empty());

        Optional<Category> foundCategory = categoryServiceToTest.findByName(category.getName());

        assertFalse(foundCategory.isPresent());
    }
}