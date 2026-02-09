package com.carrotmarket.controller;

import com.carrotmarket.controller.dto.request.CreatedProductRequestDto;
import com.carrotmarket.controller.dto.request.ProductResponseDto;
import com.carrotmarket.controller.dto.request.UpdateProductRequestDto;
import com.carrotmarket.model.Product;
import com.carrotmarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@Validated @RequestBody CreatedProductRequestDto requestDto) {
        Product createdProduct = productService.createProduct(requestDto.toEntity());
        return ResponseEntity
                .ok(createdProduct.getId());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Long> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequestDto requestDto) {
        Product updatedProduct = productService.updateProduct(id, requestDto.toVO());
        return ResponseEntity
                .ok(updatedProduct.getId());
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    /**
     * 관리자용 상품 단건 조회 api
     */
    @GetMapping("/admin/{id}")
    public ResponseEntity<ProductResponseDto> getProductForAdmin(@PathVariable Long id) {
        Product product = productService.getProductForAdmin(id);
        return ResponseEntity
                .ok(new ProductResponseDto(product));
    }

    @PostMapping("/{productId}/status")
    public ResponseEntity<Void> changeProductStatus(@PathVariable Long productId, @RequestParam int statusCode) {
        productService.changeProductStatus(productId, statusCode);
        return ResponseEntity
                .noContent()
                .build();
    }

}