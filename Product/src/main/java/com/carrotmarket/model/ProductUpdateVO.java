package com.carrotmarket.model;

import java.math.BigDecimal;

public class ProductUpdateVO {
    private final String productName;
    private final Long price;
    private final String content;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final String preferredLocation;

    public ProductUpdateVO(String productName, Long price, String content,
                           BigDecimal latitude, BigDecimal longitude, String preferredLocation) {
        this.productName = productName;
        this.price = price;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.preferredLocation = preferredLocation;
    }

    public String getProductName() {
        return productName;
    }

    public Long getPrice() {
        return price;
    }

    public String getContent() {
        return content;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }
}