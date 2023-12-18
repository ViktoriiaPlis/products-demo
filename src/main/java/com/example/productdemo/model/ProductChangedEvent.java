package com.example.productdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductChangedEvent {
    private String productName;

    private String description;

    private long price;

    private String picture;

    private UUID categoryId;

    private Short status;

}
