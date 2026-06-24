package com.example.cabbagemarket10.domain.category.repository;

import com.example.cabbagemarket10.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
