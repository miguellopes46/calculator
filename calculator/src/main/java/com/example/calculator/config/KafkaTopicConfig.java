package com.example.calculator.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic calcRequestsTopic() {
        return TopicBuilder.name("calc_requests")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic calcResponsesTopic() {
        return TopicBuilder.name("calc_responses")
                .partitions(1)
                .replicas(1)
                .build();
    }
}