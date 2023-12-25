package com.example.productdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDecreaseEvent {
    private UUID id;
    private String name;
    private long oldPrice;
    private long newPrice;
}
