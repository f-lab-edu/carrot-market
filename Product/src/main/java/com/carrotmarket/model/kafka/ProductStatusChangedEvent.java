package com.carrotmarket.model.kafka;

import com.carrotmarket.model.ProductStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatusChangedEvent {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("old_status")
    private String oldStatus;

    @JsonProperty("new_status")
    private String newStatus;

    @JsonProperty("changed_at")
    private LocalDateTime changedAt;

    public ProductStatusChangedEvent(Long productId, ProductStatus oldStatus, ProductStatus newStatus) {
        this.productId = productId;
        this.oldStatus = oldStatus.name();
        this.newStatus = newStatus.name();
        this.changedAt = LocalDateTime.now();
    }
}