package com.example.productdemo.dao;

import com.example.productdemo.entity.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Component
@Repository

public interface ProductDao extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET status = 1 WHERE category_id = :categoryId", nativeQuery = true)
    void deactivateProductsByCategory(@Param("categoryId") UUID categoryId);
}
