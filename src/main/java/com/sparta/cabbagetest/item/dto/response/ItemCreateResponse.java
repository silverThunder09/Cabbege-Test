package com.sparta.cabbagetest.item.dto.response;

import com.sparta.cabbagetest.item.domain.ConditionType;
import com.sparta.cabbagetest.item.domain.Item;
import com.sparta.cabbagetest.item.domain.TradeStatus;
import com.sparta.cabbagetest.item.domain.TradeType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ItemCreateResponse {

    private final Long id;
    private final Long sellerId;
    private final Long categoryId;
    private final TradeType tradeType;
    private final String title;
    private final String description;
    private final long initialPrice;
    private final ConditionType conditionType;
    private final TradeStatus tradeStatus;
    private final long viewCount;
    private final long likeCount;
    private final long inquiryCount;
    private final Boolean isDraft;
    private final LocalDateTime createdAt;

    private ItemCreateResponse(
            Long id,
            Long sellerId,
            Long categoryId,
            TradeType tradeType,
            String title,
            String description,
            long initialPrice,
            ConditionType conditionType,
            TradeStatus tradeStatus,
            long viewCount,
            long likeCount,
            long inquiryCount,
            Boolean isDraft,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.tradeType = tradeType;
        this.title = title;
        this.description = description;
        this.initialPrice = initialPrice;
        this.conditionType = conditionType;
        this.tradeStatus = tradeStatus;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.inquiryCount = inquiryCount;
        this.isDraft = isDraft;
        this.createdAt = createdAt;
    }

    public static ItemCreateResponse from(Item item) {
        return new ItemCreateResponse(
                item.getId(),
                item.getSeller().getId(),
                item.getCategory().getId(),
                item.getTradeType(),
                item.getTitle(),
                item.getDescription(),
                item.getInitialPrice(),
                item.getConditionType(),
                item.getTradeStatus(),
                item.getViewCount(),
                item.getLikeCount(),
                item.getInquiryCount(),
                item.isDraft(),
                item.getCreatedAt()
        );
    }
}
