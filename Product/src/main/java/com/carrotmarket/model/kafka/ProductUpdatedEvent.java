package com.carrotmarket.model.kafka;

import com.carrotmarket.model.Product;
import com.carrotmarket.model.ProductUpdateVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductUpdatedEvent {

    @JsonProperty("product_id")
    private Long productId;

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

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public ProductUpdatedEvent(Product product, ProductUpdateVO updateVO) {
        this.productId = product.getId();
        this.userId = product.getUserId();
        this.productName = updateVO.getProductName();
        this.price = updateVO.getPrice();
        this.content = updateVO.getContent();
        this.latitude = updateVO.getLatitude();
        this.longitude = updateVO.getLongitude();
        this.preferredLocation = updateVO.getPreferredLocation();
        this.updatedAt = product.getUpdatedAt();
    }
}