package com.carrotmarket.model.kafka;

import com.carrotmarket.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {

    @JsonProperty("product_id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("price")
    private Long price;

    @JsonProperty("content")
    private String content;

    @JsonProperty("latitude")
    private BigDecimal latitude;

    @JsonProperty("longitude")
    private BigDecimal longitude;

    @JsonProperty("preferred_location")
    private String preferredLocation;

    @JsonProperty("status")
    private String status;

    @JsonProperty("chat_count")
    private Long chatCount;

    @JsonProperty("like_count")
    private Long likeCount;

    @JsonProperty("view_count")
    private Long viewCount;

    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    @JsonProperty("deleted_at")
    private LocalDateTime deletedAt;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public ProductCreatedEvent(Product product) {
        this.id = product.getId();
        this.userId = product.getUserId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.content = product.getContent();
        this.latitude = product.getLatitude();
        this.longitude = product.getLongitude();
        this.preferredLocation = product.getPreferredLocation();
        this.status = product.getStatus().name();
        this.chatCount = product.getChatCount();
        this.likeCount = product.getLikeCount();
        this.viewCount = product.getViewCount();
        this.isDeleted = product.getIsDeleted();
        this.deletedAt = product.getDeletedAt();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}