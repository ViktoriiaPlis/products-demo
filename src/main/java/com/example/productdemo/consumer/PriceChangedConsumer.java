package com.example.productdemo.consumer;

import com.example.productdemo.model.PriceChangedEvent;
import com.example.productdemo.service.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PriceChangedConsumer {

    private final ProductService productService;

    public PriceChangedConsumer(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = {"${app.kafka.topic.products-promo}"},
            idIsGroup = true,
            properties = {"ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG=StringDeserializer.class, " +
                    "ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG = JsonDeserializer.class," +
                    "JsonSerializer.ADD_TYPE_INFO_HEADERS = false," +
                    "ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS = StringDeserializer.class," +
                    "ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS = JsonDeserializer.class"
            }
    )
    public void priceChanged(PriceChangedEvent priceChangedEvent) {
        System.out.println("priceChanged......");
//        System.out.println(priceChangedEvent.getId() + "  " + priceChangedEvent.getProductName() + "  " + priceChangedEvent.getPrice());
//        productService.updateProduct(priceChangedEvent.getId(),
//                new ProductRequest(null, null, priceChangedEvent.getPrice(), null, null, null));

    }


}
