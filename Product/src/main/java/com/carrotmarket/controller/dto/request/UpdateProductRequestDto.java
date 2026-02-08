package com.carrotmarket.controller.dto.request;

import com.carrotmarket.model.ProductUpdateVO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateProductRequestDto(
        @NotNull(message = "productId는 필수입니다")
        Long productId,

        @Nullable
        String productName,

        @Nullable
        Long price,

        @Nullable
        String content,

        @Nullable
        BigDecimal latitude,

        @Nullable
        BigDecimal longitude,

        @Nullable
        String preferredLocation
) {
    public ProductUpdateVO toVO() {
        return new ProductUpdateVO(
                productName,
                price,
                content,
                latitude,
                longitude,
                preferredLocation
        );
    }
}