package com.daamu.config;


import com.daamu.event.OrderPlacedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Component
public class KafkaOrderPlacedEventProducerConfiguration {
//    @Value("${spring.kafka.bootstrap-servers}")
    private static final String bootstrapServers="localhost:9092";
    @Bean
    public Map<String, Object> propCustomerConfig() {
        Map<String, Object> prop = new HashMap<>();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // Corrected the variable name

        // Set the key and value serializers
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Changed to correct JsonSerializer class

        return prop;
    }

    @Bean
    public ProducerFactory<String, OrderPlacedEvent> producerCustomerFactory() {
        return new DefaultKafkaProducerFactory<>(propCustomerConfig());
    }

    @Bean
    public KafkaTemplate<String, OrderPlacedEvent> kafkaCustomerTemplate(ProducerFactory<String, OrderPlacedEvent> producerCustomerFactory) {
        return new KafkaTemplate<>(producerCustomerFactory);
    }
}