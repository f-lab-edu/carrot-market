package com.carrotmarket.controller.dto.request;

import com.carrotmarket.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreatedProductRequestDto(
        @NotNull(message = "userId는 필수입니다")
        Long userId,

        @NotBlank(message = "productName은 필수입니다")
        String productName,

        @NotNull(message = "price는 필수입니다")
        Long price,

        @NotBlank(message = "content는 필수입니다")
        String content,

        @NotNull(message = "latitude는 필수입니다")
        BigDecimal latitude,

        @NotNull(message = "longitude는 필수입니다")
        BigDecimal longitude,

        @NotBlank(message = "preferredLocation은 필수입니다")
        String preferredLocation
) {
    public Product toEntity() {
        return new Product(
                userId,
                productName,
                price,
                content,
                latitude,
                longitude,
                preferredLocation
        );
    }
}