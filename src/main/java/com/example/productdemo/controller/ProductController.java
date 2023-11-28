package com.example.productdemo.controller;

import com.example.productdemo.request.ProductRequest;
import com.example.productdemo.response.ProductResponse;
import com.example.productdemo.service.ProductService;
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

import java.util.List;
import java.util.UUID;


@RestController
@Validated
@Tag(name = "product")
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создание продукта")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
    public ProductResponse createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удаление продукта")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновление продукта")
    public ProductResponse updateProduct(@PathVariable UUID id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @GetMapping()
    @Operation(summary = "Поиск продуктов")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findProduct(@RequestParam(name = "category", required = false) String category,
                                             @RequestParam(name = "product", required = false) String product,
                                             @RequestParam(name = "low-price", required = false) Long lowPrice,
                                             @RequestParam(name = "high-price", required = false) Long highPrice) {
        return productService.findProduct(category, product, lowPrice, highPrice);
    }
}
