package com.carrotmarket.model.kafka;

import com.carrotmarket.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDeletedEvent {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    @JsonProperty("deleted_at")
    private LocalDateTime deletedAt;

    public ProductDeletedEvent(Product product) {
        this.productId = product.getId();
        this.isDeleted = product.getIsDeleted();
        this.deletedAt = product.getDeletedAt();
    }
}