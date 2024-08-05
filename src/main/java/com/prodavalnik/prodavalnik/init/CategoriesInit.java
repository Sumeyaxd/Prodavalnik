package com.prodavalnik.prodavalnik.init;

import com.prodavalnik.prodavalnik.model.entity.Category;
import com.prodavalnik.prodavalnik.model.enums.CategoryEnum;
import com.prodavalnik.prodavalnik.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.prodavalnik.prodavalnik.model.enums.CategoryEnum.*;

@Component
public class CategoriesInit implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoriesInit(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {

        if (this.categoryRepository.count() == 0) {

            Arrays.stream(CategoryEnum.values())
                    .forEach(categoryName -> {
                        Category category = new Category();
                        category.setName(categoryName);

                        String description = switch (categoryName) {
                            case CLOTHES -> "Buy this stylish dress to look amazing at any event";
                            case ELECTRONICS -> "Furnish your home with this comfortable sofa that combines comfort and elegance";
                            case FURNITURE -> "Get this new smartphone to enjoy the latest technologies and features.";
                        };

                        category.setDescription(description);
                        this.categoryRepository.saveAndFlush(category);
                    });
        }
    }
}

