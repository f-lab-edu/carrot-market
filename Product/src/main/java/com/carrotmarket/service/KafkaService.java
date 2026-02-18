package com.carrotmarket.service;

import com.carrotmarket.model.Product;
import com.carrotmarket.model.ProductStatus;
import com.carrotmarket.model.ProductUpdateVO;
import com.carrotmarket.model.kafka.ProductCreatedEvent;
import com.carrotmarket.model.kafka.ProductDeletedEvent;
import com.carrotmarket.model.kafka.ProductStatusChangedEvent;
import com.carrotmarket.model.kafka.ProductUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.info("Sending ProductCreatedEvent to topic [{}] - {}", PRODUCT_CREATED_TOPIC, event);
        productCreatedKafkaTemplate.send(PRODUCT_CREATED_TOPIC, product.getId().toString(), event)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("✓ ProductCreatedEvent sent successfully to topic [{}]", PRODUCT_CREATED_TOPIC);
                } else {
                    log.error("✗ ProductCreatedEvent failed to topic [{}]", PRODUCT_CREATED_TOPIC, ex);
                }
            });
    }

    public void publishProductUpdatedEvent(Product product, ProductUpdateVO updateVO) {
        ProductUpdatedEvent event = new ProductUpdatedEvent(product, updateVO);
        log.info("Sending ProductUpdatedEvent to topic [{}] - {}", PRODUCT_UPDATED_TOPIC, event);
        productUpdatedKafkaTemplate.send(PRODUCT_UPDATED_TOPIC, product.getId().toString(), event)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("✓ ProductUpdatedEvent sent successfully to topic [{}]", PRODUCT_UPDATED_TOPIC);
                } else {
                    log.error("✗ ProductUpdatedEvent failed to topic [{}]", PRODUCT_UPDATED_TOPIC, ex);
                }
            });
    }

    public void publishProductDeletedEvent(Product product) {
        ProductDeletedEvent event = new ProductDeletedEvent(product);
        log.info("Sending ProductDeletedEvent to topic [{}] - {}", PRODUCT_DELETED_TOPIC, event);
        productDeletedKafkaTemplate.send(PRODUCT_DELETED_TOPIC, product.getId().toString(), event)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("✓ ProductDeletedEvent sent successfully to topic [{}]", PRODUCT_DELETED_TOPIC);
                } else {
                    log.error("✗ ProductDeletedEvent failed to topic [{}]", PRODUCT_DELETED_TOPIC, ex);
                }
            });
    }

    public void publishProductStatusChangedEvent(Long productId, ProductStatus oldStatus, ProductStatus newStatus) {
        ProductStatusChangedEvent event = new ProductStatusChangedEvent(productId, oldStatus, newStatus);
        log.info("Sending ProductStatusChangedEvent to topic [{}] - {}", PRODUCT_STATUS_TOPIC, event);
        productStatusKafkaTemplate.send(PRODUCT_STATUS_TOPIC, productId.toString(), event)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("✓ ProductStatusChangedEvent sent successfully to topic [{}]", PRODUCT_STATUS_TOPIC);
                } else {
                    log.error("✗ ProductStatusChangedEvent failed to topic [{}]", PRODUCT_STATUS_TOPIC, ex);
                }
            });
    }

}