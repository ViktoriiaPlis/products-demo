package com.example.productdemo.consumer;

import com.example.productdemo.model.ProductPromoEvent;
import com.example.productdemo.service.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductPromoConsumer {

    private final ProductService productService;

    public ProductPromoConsumer(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = {"${app.kafka.topic.products-promo}"}, idIsGroup = true)
    public void priceChanged(ProductPromoEvent productPromoEvent) {
        productService.updateProductPriceById(productPromoEvent.getProductId(), productPromoEvent.getPrice());
    }


}
