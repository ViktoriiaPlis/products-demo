package com.example.productdemo.consumer;

import com.example.productdemo.model.ProductCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductCreatedConsumer {
    @KafkaListener(topics = {"${app.kafka.topic.products-created}"}, idIsGroup = true)
    public void productCreated(ProductCreatedEvent productCreatedEvent) {
        System.out.println(productCreatedEvent);
    }
}
