package com.sparta.cabbagetest.item.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparta.cabbagetest.category.domain.Category;
import com.sparta.cabbagetest.category.repository.CategoryRepository;
import com.sparta.cabbagetest.client.domain.Client;
import com.sparta.cabbagetest.client.repository.ClientRepository;
import com.sparta.cabbagetest.item.domain.Item;
import com.sparta.cabbagetest.item.repository.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:item-create-test;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        itemRepository.deleteAll();
        categoryRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void 상품등록_성공시_상품을_생성하고_기본값을_반환한다() throws Exception {
        Client seller = saveClient("seller@example.com");
        Category category = categoryRepository.save(Category.create("채소", 1));
        String request = """
                {
                  "categoryId": %d,
                  "tradeType": "SALE",
                  "title": "싱싱한 배추",
                  "description": "오늘 수확한 배추입니다.",
                  "initialPrice": 12000,
                  "conditionType": "NEW"
                }
                """.formatted(category.getId());

        mockMvc.perform(post("/api/items")
                        .with(user("seller@example.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sellerId").value(seller.getId()))
                .andExpect(jsonPath("$.categoryId").value(category.getId()))
                .andExpect(jsonPath("$.tradeType").value("SALE"))
                .andExpect(jsonPath("$.title").value("싱싱한 배추"))
                .andExpect(jsonPath("$.initialPrice").value(12000))
                .andExpect(jsonPath("$.conditionType").value("NEW"))
                .andExpect(jsonPath("$.tradeStatus").value("ON_SALE"))
                .andExpect(jsonPath("$.viewCount").value(0))
                .andExpect(jsonPath("$.likeCount").value(0))
                .andExpect(jsonPath("$.inquiryCount").value(0))
                .andExpect(jsonPath("$.isDraft").value(false));

        Item item = itemRepository.findAll().get(0);
        assertThat(item.getSeller().getId()).isEqualTo(seller.getId());
        assertThat(item.getCategory().getId()).isEqualTo(category.getId());
        assertThat(item.isDeleted()).isFalse();
    }

    @Test
    void 존재하지_않는_카테고리이면_상품등록에_실패한다() throws Exception {
        saveClient("seller@example.com");
        String request = """
                {
                  "categoryId": 999,
                  "tradeType": "SALE",
                  "title": "싱싱한 배추",
                  "description": "오늘 수확한 배추입니다.",
                  "initialPrice": 12000,
                  "conditionType": "NEW"
                }
                """;

        mockMvc.perform(post("/api/items")
                        .with(user("seller@example.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }

    @Test
    void 필수값이_누락되면_상품등록에_실패한다() throws Exception {
        saveClient("seller@example.com");
        String request = """
                {
                  "tradeType": "SALE",
                  "description": "오늘 수확한 배추입니다.",
                  "initialPrice": 12000,
                  "conditionType": "NEW"
                }
                """;

        mockMvc.perform(post("/api/items")
                        .with(user("seller@example.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    private Client saveClient(String email) {
        return clientRepository.save(Client.create(
                email,
                passwordEncoder.encode("password123!"),
                "판매자",
                "홍길동",
                null
        ));
    }
}
