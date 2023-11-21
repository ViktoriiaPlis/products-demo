package com.example.productdemo.service;

import com.example.productdemo.dao.CategoryDao;
import com.example.productdemo.dao.ProductDao;
import com.example.productdemo.entity.CategoryEntity;
import com.example.productdemo.request.CategoryRequest;
import com.example.productdemo.response.CategoryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class CategoryService {
    private final ProductDao productDao;
    private final CategoryDao categoryDao;
    private final ObjectMapper objectMapper;

    public CategoryService(CategoryDao categoryDao, ProductDao productDao, ObjectMapper objectMapper) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.objectMapper = objectMapper;
    }

    public CategoryEntity findCategory(String category) {
        Optional<CategoryEntity> foundCategory = categoryDao.findByNameAndDeletedAtIsNull(category);

        if (foundCategory.isPresent()) {
            return foundCategory.get();
        } else throw new IllegalStateException("Category not found : " + category);
    }

    @Transactional
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = new CategoryResponse();
        CategoryEntity category = new CategoryEntity(
                categoryRequest.getCategory(),
                categoryRequest.getDescription());
        CategoryEntity savedCategory = categoryDao.save(category);
        categoryResponse.setId(savedCategory.getId());
        categoryResponse.setCategory(savedCategory.getName());
        categoryResponse.setDescription(savedCategory.getDescription());
        return categoryResponse;
    }

    @Transactional
    public void deleteCategory(UUID id) {
        Optional<CategoryEntity> entity = categoryDao.findById(id);
        if (entity.isPresent()) {
            productDao.deactivateProductsByCategory(id);
            entity.get().setDeletedAt(Instant.now());
            categoryDao.save(entity.get());
        } else {
            throw new IllegalStateException("Category not found : id" + id);
        }
    }

    @Transactional
    public CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest) {
        CategoryResponse response = new CategoryResponse();
        Optional<CategoryEntity> foundedCategory = categoryDao.findById(id);

        if (foundedCategory.isPresent()) {
            foundedCategory.get().setName(categoryRequest.getCategory());
            foundedCategory.get().setDescription(categoryRequest.getDescription());
            foundedCategory.get().setUpdatedAt(Instant.now());
            categoryDao.save(foundedCategory.get());
            response = objectMapper.convertValue(foundedCategory, CategoryResponse.class);
        } else {
            throw new IllegalStateException("Category not found : id" + id);
        }
        return response;
    }
}
