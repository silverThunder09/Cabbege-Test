package com.example.cabbagemarket10.domain.item.service;

import com.example.cabbagemarket10.domain.category.domain.Category;
import com.example.cabbagemarket10.domain.category.repository.CategoryRepository;
import com.example.cabbagemarket10.domain.client.domain.Client;
import com.example.cabbagemarket10.domain.client.repository.ClientRepository;
import com.example.cabbagemarket10.domain.item.domain.Item;
import com.example.cabbagemarket10.domain.item.dto.request.ItemCreateRequest;
import com.example.cabbagemarket10.domain.item.dto.response.ItemCreateResponse;
import com.example.cabbagemarket10.domain.item.exception.CategoryNotFoundException;
import com.example.cabbagemarket10.domain.item.exception.SellerNotFoundException;
import com.example.cabbagemarket10.domain.item.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ClientRepository clientRepository;

    public ItemService(
            ItemRepository itemRepository,
            CategoryRepository categoryRepository,
            ClientRepository clientRepository
    ) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public ItemCreateResponse createItem(String sellerEmail, ItemCreateRequest request) {
        Client seller = clientRepository.findByEmail(sellerEmail)
                .orElseThrow(SellerNotFoundException::new);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Item item = Item.create(
                seller,
                category,
                request.getTradeType(),
                request.getTitle(),
                request.getDescription(),
                request.getInitialPrice(),
                request.getConditionType()
        );

        return ItemCreateResponse.from(itemRepository.save(item));
    }
}
