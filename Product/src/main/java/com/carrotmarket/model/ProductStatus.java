package com.carrotmarket.model;

import com.carrotmarket.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.carrotmarket.exception.ExceptionCode.INVALID_PRODUCT_STATUS_CODE;

@Getter
@AllArgsConstructor
public enum ProductStatus {

    ON_SALE("판매중"),
    RESERVED("예약중"),
    SOLD_OUT("거래완료");

    private final int code = this.ordinal();
    private final String description;

    public static ProductStatus getByOrdinary(int code) {
        return java.util.Arrays.stream(ProductStatus.values())
                .filter(status -> status.ordinal() == code)
                .findFirst()
                .orElseThrow(() -> new CustomException(INVALID_PRODUCT_STATUS_CODE));
    }


}