package com.example.cabbagemarket10.domain.item.repository;

import com.example.cabbagemarket10.domain.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
