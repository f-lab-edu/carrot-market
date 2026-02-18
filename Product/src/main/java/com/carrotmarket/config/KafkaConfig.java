package com.carrotmarket.config;

import com.carrotmarket.model.kafka.ProductCreatedEvent;
import com.carrotmarket.model.kafka.ProductDeletedEvent;
import com.carrotmarket.model.kafka.ProductStatusChangedEvent;
import com.carrotmarket.model.kafka.ProductUpdatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"removal", "deprecation"})
@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    private <T> ProducerFactory<String, T> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        props.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 540000);

        JsonSerializer<T> jsonSerializer = new JsonSerializer<>(objectMapper());
        jsonSerializer.setAddTypeInfo(false);

        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(), jsonSerializer);
    }

    @Bean
    public KafkaTemplate<String, ProductStatusChangedEvent> productStatusKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, ProductCreatedEvent> productCreatedKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, ProductUpdatedEvent> productUpdatedKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, ProductDeletedEvent> productDeletedKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}