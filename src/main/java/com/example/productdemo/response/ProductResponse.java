package com.example.productdemo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    @Schema(description = "id продукта")
    private UUID id;
    @Schema(description = "Наименование продукта")
    private String name;
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Цена")
    private long price;

    @Schema(description = "Изображение продукта")
    private String picture;

    @Schema(description = "Категория продукта")
    private UUID categoryId;

    @Schema(description = "Дата создания")
    private Instant createdAt;

    @Schema(description = "Статус")
    private Short status;

}
