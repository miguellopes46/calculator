package com.example.rest.config;

import com.example.rest.dtos.OperationResponseDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
//
    @Bean
    public ConsumerFactory<String, OperationResponseDTO> responseConsumerFactory() {
        JsonDeserializer<OperationResponseDTO> deserializer = new JsonDeserializer<>(OperationResponseDTO.class);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092",
                        ConsumerConfig.GROUP_ID_CONFIG, "rest-group",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OperationResponseDTO> responseKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OperationResponseDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(responseConsumerFactory());
        return factory;
    }
}
