package com.carrotmarket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Table(name = "products", indexes = {
        @Index(name = "idx_products_user_id", columnList = "user_id"),
        @Index(name = "idx_products_created_at", columnList = "created_at")
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 255)
    private String productName;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private BigDecimal latitude;

    @Column(nullable = false)
    private BigDecimal longitude;

    @Column(nullable = false, length = 255)
    private String preferredLocation;

    @Column(nullable = false)
    private Long chatCount = 0L;

    @Column(nullable = false)
    private Long likeCount = 0L;

    @Column(nullable = false)
    private Long viewCount = 0L;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Product(Long userId, String productName, Long price, String content, BigDecimal latitude,
                   BigDecimal longitude, String preferredLocation) {
        this.userId = userId;
        this.productName = productName;
        this.price = price;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.preferredLocation = preferredLocation;
    }

    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void update(ProductUpdateVO productUpdateVO) {
        if (productUpdateVO.getProductName() != null) {
            this.productName = productUpdateVO.getProductName();
        }
        if (productUpdateVO.getPrice() != null) {
            this.price = productUpdateVO.getPrice();
        }
        if (productUpdateVO.getContent() != null) {
            this.content = productUpdateVO.getContent();
        }
        if (productUpdateVO.getLatitude() != null) {
            this.latitude = productUpdateVO.getLatitude();
        }
        if (productUpdateVO.getLongitude() != null) {
            this.longitude = productUpdateVO.getLongitude();
        }
        if (productUpdateVO.getPreferredLocation() != null) {
            this.preferredLocation = productUpdateVO.getPreferredLocation();
        }
        this.updatedAt = LocalDateTime.now();
    }

}