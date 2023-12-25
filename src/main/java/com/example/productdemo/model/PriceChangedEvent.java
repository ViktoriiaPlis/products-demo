package com.example.productdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceChangedEvent {

    private UUID id;
    private long price;
    private UUID categoryId;

}
