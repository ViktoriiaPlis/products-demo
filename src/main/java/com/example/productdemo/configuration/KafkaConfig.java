package com.example.productdemo.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic topicProductChanged(@Value("${app.kafka.topic.products-changed}") String topic) {
        return TopicBuilder.name(topic).partitions(1).build();
    }
}
