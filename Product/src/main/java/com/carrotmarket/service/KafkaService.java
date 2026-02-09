package com.carrotmarket.service;

import com.carrotmarket.model.Product;
import com.carrotmarket.model.ProductStatus;
import com.carrotmarket.model.ProductUpdateVO;
import com.carrotmarket.model.kafka.ProductCreatedEvent;
import com.carrotmarket.model.kafka.ProductDeletedEvent;
import com.carrotmarket.model.kafka.ProductStatusChangedEvent;
import com.carrotmarket.model.kafka.ProductUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, ProductStatusChangedEvent> productStatusKafkaTemplate;
    private final KafkaTemplate<String, ProductCreatedEvent> productCreatedKafkaTemplate;
    private final KafkaTemplate<String, ProductUpdatedEvent> productUpdatedKafkaTemplate;
    private final KafkaTemplate<String, ProductDeletedEvent> productDeletedKafkaTemplate;

    private static final String PRODUCT_STATUS_TOPIC = "product.status.changed";
    private static final String PRODUCT_CREATED_TOPIC = "product.created";
    private static final String PRODUCT_UPDATED_TOPIC = "product.updated";
    private static final String PRODUCT_DELETED_TOPIC = "product.deleted";

    public void publishProductCreatedEvent(Product product) {
        ProductCreatedEvent event = new ProductCreatedEvent(product);
        productCreatedKafkaTemplate.send(PRODUCT_CREATED_TOPIC, product.getId().toString(), event);
    }

    public void publishProductUpdatedEvent(Product product, ProductUpdateVO updateVO) {
        ProductUpdatedEvent event = new ProductUpdatedEvent(product, updateVO);
        productUpdatedKafkaTemplate.send(PRODUCT_UPDATED_TOPIC, product.getId().toString(), event);
    }

    public void publishProductDeletedEvent(Product product) {
        ProductDeletedEvent event = new ProductDeletedEvent(product);
        productDeletedKafkaTemplate.send(PRODUCT_DELETED_TOPIC, product.getId().toString(), event);
    }

    public void publishProductStatusChangedEvent(Long productId, ProductStatus oldStatus, ProductStatus newStatus) {
        ProductStatusChangedEvent event = new ProductStatusChangedEvent(productId, oldStatus, newStatus);
        productStatusKafkaTemplate.send(PRODUCT_STATUS_TOPIC, productId.toString(), event);
    }

}