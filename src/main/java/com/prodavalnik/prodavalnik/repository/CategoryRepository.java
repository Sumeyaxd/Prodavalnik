package com.prodavalnik.prodavalnik.repository;

import com.prodavalnik.prodavalnik.model.entity.Category;
import com.prodavalnik.prodavalnik.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryEnum name);
}
