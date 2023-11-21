package com.example.productdemo.dao;

import com.example.productdemo.entity.ProductEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductValueSpecification implements Specification<ProductEntity> {
    private final String product;
    private final Long lowPrice;
    private final Long highPrice;
    private final UUID categoryId;

    public ProductValueSpecification(UUID categoryId, String product, Long lowPrice, Long highPrice) {
        this.categoryId = categoryId;
        this.product = product;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
    }

    @Override
    public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (categoryId != null) {
            predicates.add(criteriaBuilder.equal(root.get("categoryId"), categoryId));
        }
        if (product != null) {
            predicates.add(criteriaBuilder.equal(root.get("name"), product));
        }
        if (lowPrice != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), lowPrice));
        }
        if (highPrice != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), highPrice));
        }
        return predicates.stream()
                .reduce(criteriaBuilder::and)
                .orElse(criteriaBuilder.conjunction());
    }
}

