package com.example.cabbagemarket10.domain.item.controller;

import com.example.cabbagemarket10.domain.item.dto.request.ItemCreateRequest;
import com.example.cabbagemarket10.domain.item.dto.response.ItemCreateResponse;
import com.example.cabbagemarket10.domain.item.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemCreateResponse> createItem(
            Authentication authentication,
            @Valid @RequestBody ItemCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.createItem(authentication.getName(), request));
    }
}
