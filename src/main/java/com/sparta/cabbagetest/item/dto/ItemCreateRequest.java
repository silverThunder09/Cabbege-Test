package com.sparta.cabbagetest.item.dto;

import com.sparta.cabbagetest.item.domain.ConditionType;
import com.sparta.cabbagetest.item.domain.TradeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ItemCreateRequest(
        @NotNull Long categoryId,
        @NotNull TradeType tradeType,
        @NotBlank @Size(max = 200) String title,
        @NotBlank String description,
        @PositiveOrZero long initialPrice,
        @NotNull ConditionType conditionType
) {
}
