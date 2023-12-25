package com.example.productdemo.consumer;

import com.example.productdemo.model.PriceChangedEvent;
import com.example.productdemo.request.ProductRequest;
import com.example.productdemo.service.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PriceChangedConsumer {

    private final ProductService productService;

    public PriceChangedConsumer(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = {"${app.kafka.topic.products-promo}"}, idIsGroup = true)
    public void priceChanged(PriceChangedEvent priceChangedEvent) {
        productService.updateProduct(priceChangedEvent.getId(),
                new ProductRequest(null, null, priceChangedEvent.getPrice(), null, priceChangedEvent.getCategoryId(), null));

    }


}
