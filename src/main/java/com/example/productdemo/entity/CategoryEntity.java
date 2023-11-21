package com.example.productdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
@Entity
public class CategoryEntity extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
