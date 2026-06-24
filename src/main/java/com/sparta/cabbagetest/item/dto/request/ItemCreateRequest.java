package com.sparta.cabbagetest.item.dto.request;

import com.sparta.cabbagetest.item.domain.ConditionType;
import com.sparta.cabbagetest.item.domain.TradeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemCreateRequest {

    @NotNull(message = "카테고리는 필수입니다.")
    private Long categoryId;

    @NotNull(message = "거래 방식은 필수입니다.")
    private TradeType tradeType;

    @NotBlank(message = "상품명은 필수입니다.")
    @Size(max = 200, message = "상품명은 최대 200자까지 입력할 수 있습니다.")
    private String title;

    @NotBlank(message = "상품 설명은 필수입니다.")
    private String description;

    @NotNull(message = "시작가는 필수입니다.")
    @PositiveOrZero(message = "시작가는 0 이상이어야 합니다.")
    private Long initialPrice;

    @NotNull(message = "상품 상태는 필수입니다.")
    private ConditionType conditionType;
}
