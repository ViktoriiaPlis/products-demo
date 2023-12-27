package com.example.productdemo.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic topicProductCreated(@Value("${app.kafka.topic.products-created}") String topic) {
        return TopicBuilder.name(topic).partitions(1).build();
    }

    @Bean
    public NewTopic topicPriceIncreased(@Value("${app.kafka.topic.price-increased}") String topic) {
        return TopicBuilder.name(topic).partitions(1).build();
    }

    @Bean
    public NewTopic topicPriceDecreased(@Value("${app.kafka.topic.price-decreased}") String topic) {
        return TopicBuilder.name(topic).partitions(1).build();
    }

    @Bean
    public NewTopic topicUserLogined(@Value("${app.kafka.topic.user-logined}") String topic) {
        return TopicBuilder.name(topic).partitions(1).build();
    }
}
