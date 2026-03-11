package com.example.demo.controller;

import com.example.demo.common.result.PageResult;
import com.example.demo.common.result.Result;
import com.example.demo.dto.request.ProductCreateRequest;
import com.example.demo.dto.response.ProductVO;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Result<PageResult<ProductVO>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return Result.success(productService.getProducts(pageable));
    }

    @GetMapping("/search")
    public Result<PageResult<ProductVO>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return Result.success(productService.searchProducts(keyword, pageable));
    }

    @GetMapping("/{id}")
    public Result<ProductVO> getProduct(@PathVariable Long id) {
        return Result.success(productService.getProductById(id));
    }

    @PostMapping
    public Result<ProductVO> createProduct(@RequestBody ProductCreateRequest request, Authentication auth) {
        // 实际项目中从 auth 获取用户 ID
        return Result.success(productService.createProduct(request, 1L));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteProduct(@PathVariable Long id, Authentication auth) {
        productService.deleteProduct(id, 1L);
        return Result.success("删除成功");
    }
}