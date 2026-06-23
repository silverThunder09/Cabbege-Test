package com.sparta.cabbagetest.item.service;

import com.sparta.cabbagetest.category.domain.Category;
import com.sparta.cabbagetest.category.repository.CategoryRepository;
import com.sparta.cabbagetest.client.domain.Client;
import com.sparta.cabbagetest.client.repository.ClientRepository;
import com.sparta.cabbagetest.item.domain.Item;
import com.sparta.cabbagetest.item.dto.ItemCreateRequest;
import com.sparta.cabbagetest.item.dto.ItemCreateResponse;
import com.sparta.cabbagetest.item.exception.CategoryNotFoundException;
import com.sparta.cabbagetest.item.exception.SellerNotFoundException;
import com.sparta.cabbagetest.item.repository.ItemRepository;
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
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Item item = Item.create(
                seller,
                category,
                request.tradeType(),
                request.title(),
                request.description(),
                request.initialPrice(),
                request.conditionType()
        );

        return ItemCreateResponse.from(itemRepository.save(item));
    }
}
