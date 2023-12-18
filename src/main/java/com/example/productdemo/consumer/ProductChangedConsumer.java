package com.example.productdemo.consumer;

import com.example.productdemo.model.ProductChangedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductChangedConsumer {
    @KafkaListener(topics = {"${app.kafka.topic.products-changed}"}, idIsGroup = true)
    public void productChanged(ProductChangedEvent productChangedEvent) {
        System.out.println(productChangedEvent);
    }
}
