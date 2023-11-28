package com.example.productdemo.controller;

import com.example.productdemo.entity.CategoryEntity;
import com.example.productdemo.request.CategoryRequest;
import com.example.productdemo.response.CategoryResponse;
import com.example.productdemo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Validated
@Tag(name = "category")
@RequestMapping("/category")

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создание категории")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
    public CategoryResponse createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удаление категории")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновление категории")
    public CategoryResponse updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable UUID id) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @GetMapping()
    @Operation(summary = "Поиск категорий")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
    @ResponseStatus(HttpStatus.OK)
    public CategoryEntity findCategory(@RequestParam(name = "category", required = false) String category) {
        return categoryService.findCategory(category);
    }
}
