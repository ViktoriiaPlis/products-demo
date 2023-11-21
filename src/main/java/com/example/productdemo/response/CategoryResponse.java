package com.example.productdemo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    @Schema(description = "Идентификатор")
    @NotBlank
    private UUID id;

    @Schema(description = "Наименование категории")
    @NotBlank
    private String category;

    @Schema(description = "Краткое описание")
    @NotBlank
    private String description;
}
