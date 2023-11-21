package com.example.productdemo.dao;

import com.example.productdemo.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Component
@Repository
public interface CategoryDao extends JpaRepository<CategoryEntity, UUID> {
    public Optional<CategoryEntity> findByNameAndDeletedAtIsNull(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE category SET name = :name, description =:description WHERE id = :id", nativeQuery = true)
    public void updateCategoryEntityByIdAndNameAndDescription(UUID id, String name, String description);


    CategoryEntity findByName(String name);
}
