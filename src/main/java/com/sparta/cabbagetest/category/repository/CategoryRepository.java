package com.sparta.cabbagetest.category.repository;

import com.sparta.cabbagetest.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
