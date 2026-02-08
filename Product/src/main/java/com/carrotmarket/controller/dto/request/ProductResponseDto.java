package com.carrotmarket.controller.dto.request;

import com.carrotmarket.model.Product;
import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String productName,
        Long price,
        String content,
        BigDecimal latitude,
        BigDecimal longitude,
        String preferredLocation,
        Long chatCount,
        Long likeCount,
        Long viewCount,
        Boolean isDeleted
) {
    public ProductResponseDto(Product product) {
        this(
                product.getId(),
                product.getProductName(),
                product.getPrice(),
                product.getContent(),
                product.getLatitude(),
                product.getLongitude(),
                product.getPreferredLocation(),
                product.getChatCount(),
                product.getLikeCount(),
                product.getViewCount(),
                product.getIsDeleted()
        );
    }
}