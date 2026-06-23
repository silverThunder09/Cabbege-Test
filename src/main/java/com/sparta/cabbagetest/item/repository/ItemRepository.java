package com.sparta.cabbagetest.item.repository;

import com.sparta.cabbagetest.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
