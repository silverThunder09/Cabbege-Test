package com.example.cabbagemarket10.domain.item.domain;

import com.example.cabbagemarket10.domain.category.domain.Category;
import com.example.cabbagemarket10.domain.client.domain.Client;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Client seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type", nullable = false, length = 30)
    private TradeType tradeType;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "initial_price", nullable = false)
    private long initialPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type", nullable = false, length = 30)
    private ConditionType conditionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_status", nullable = false, length = 30)
    private TradeStatus tradeStatus;

    @Column(name = "view_count", nullable = false)
    private long viewCount;

    @Column(name = "like_count", nullable = false)
    private long likeCount;

    @Column(name = "inquiry_count", nullable = false)
    private long inquiryCount;

    @Column(name = "is_draft", nullable = false)
    private boolean draft;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    protected Item() {
    }

    private Item(
            Client seller,
            Category category,
            TradeType tradeType,
            String title,
            String description,
            long initialPrice,
            ConditionType conditionType
    ) {
        this.seller = seller;
        this.category = category;
        this.tradeType = tradeType;
        this.title = title;
        this.description = description;
        this.initialPrice = initialPrice;
        this.conditionType = conditionType;
        this.tradeStatus = TradeStatus.ON_SALE;
        this.viewCount = 0L;
        this.likeCount = 0L;
        this.inquiryCount = 0L;
        this.draft = false;
        this.deleted = false;
    }

    public static Item create(
            Client seller,
            Category category,
            TradeType tradeType,
            String title,
            String description,
            long initialPrice,
            ConditionType conditionType
    ) {
        return new Item(seller, category, tradeType, title, description, initialPrice, conditionType);
    }

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Client getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getInitialPrice() {
        return initialPrice;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public long getViewCount() {
        return viewCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public long getInquiryCount() {
        return inquiryCount;
    }

    public boolean isDraft() {
        return draft;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
