package com.example.productdemo.service;

import com.example.productdemo.dao.CategoryDao;
import com.example.productdemo.dao.ProductDao;
import com.example.productdemo.dao.ProductValueSpecification;
import com.example.productdemo.entity.CategoryEntity;
import com.example.productdemo.entity.ProductEntity;
import com.example.productdemo.model.ProductChangedEvent;
import com.example.productdemo.request.ProductRequest;
import com.example.productdemo.response.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductService {
    private final ProductDao productDao;
    private final CategoryDao categoryDao;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topicProductChanged;

    public ProductService(ProductDao productDao, CategoryDao categoryDao, ObjectMapper objectMapper,
                          KafkaTemplate<String, Object> kafkaTemplate,
                          @Value("${app.kafka.topic.products-changed}") String topicProductChanged) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.topicProductChanged = topicProductChanged;
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        ProductEntity product = new ProductEntity(
                productRequest.getProductName(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.getPicture(),
                productRequest.getCategoryId(),
                productRequest.getStatus());
        ProductEntity savedProduct = productDao.save(product);
        ProductResponse response = objectMapper.convertValue(savedProduct, ProductResponse.class);
        kafkaTemplate.send(topicProductChanged, new ProductChangedEvent(savedProduct.getName(), savedProduct.getDescription(),
                savedProduct.getPrice(), savedProduct.getPicture(), savedProduct.getCategoryId(), savedProduct.getStatus()));
        return response;
    }

    @Transactional
    public void deleteProduct(UUID id) {

        Optional<ProductEntity> entity = productDao.findById(id);

        if (entity.isPresent()) {
            entity.get().setDeletedAt(Instant.now());
            productDao.save(entity.get());
        } else {
            throw new IllegalStateException("Product not found : id" + id);
        }

    }

    public List<ProductResponse> findProduct(String category, String product, Long lowPrice, Long highPrice) {
        List<ProductResponse> products = new ArrayList<>();
        UUID categoryId = null;
        if (!category.isEmpty()) {
            CategoryEntity categoryEntity = categoryDao.findByName(category);
            if (categoryEntity != null)
                categoryId = categoryEntity.getId();
        } else {
            throw new IllegalStateException("Product not found");
        }
        ProductValueSpecification filter = new ProductValueSpecification(categoryId, product, lowPrice, highPrice);
        List<ProductEntity> entities = productDao.findAll(filter);
        for (ProductEntity entity : entities) {
            products.add(new ProductResponse(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getPrice(),
                    entity.getPicture(),
                    categoryId,
                    entity.getCreatedAt(),
                    entity.getStatus()));
        }
        return products;
    }

    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {
        Optional<ProductEntity> product = productDao.findById(id);
        ProductResponse response = new ProductResponse();
        if (product.isPresent()) {
            product.get().setName(productRequest.getProductName());
            product.get().setDescription(productRequest.getDescription());
            product.get().setPrice(productRequest.getPrice());
            product.get().setPicture(productRequest.getPicture());
            product.get().setCategoryId(productRequest.getCategoryId());
            product.get().setStatus(productRequest.getStatus());
            product.get().setUpdatedAt(Instant.now());
            productDao.save(product.get());
            response = objectMapper.convertValue(product, ProductResponse.class);

        } else {
            throw new IllegalStateException("Product not found : id" + id);
        }
        return response;
    }
}
