package com.example.productdemo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель создание продукта")
public class ProductRequest {

    @Schema(description = "Наименование продукта")
    @NotBlank
    private String productName;

    @Schema(description = "Описание")
    @NotBlank
    private String description;

    @Schema(description = "Цена")
    @NotBlank
    private long price;

    @Schema(description = "Изображение")
    private String picture;

    @Schema(description = "Категория")
    @NotBlank
    private UUID categoryId;

    @Schema(description = "Статус")
    @NotNull
    private Short status;
}
