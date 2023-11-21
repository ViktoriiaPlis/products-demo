package com.example.productdemo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель создание категории")
public class CategoryRequest {
    @Schema(description = "Наименование категории")
    @NotBlank
    private String category;

    @Schema(description = "Краткое описание")
    @NotBlank
    private String description;
}
