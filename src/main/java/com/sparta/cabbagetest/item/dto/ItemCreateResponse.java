package com.sparta.cabbagetest.item.dto;

import com.sparta.cabbagetest.item.domain.ConditionType;
import com.sparta.cabbagetest.item.domain.Item;
import com.sparta.cabbagetest.item.domain.TradeStatus;
import com.sparta.cabbagetest.item.domain.TradeType;
import java.time.LocalDateTime;

public record ItemCreateResponse(
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
