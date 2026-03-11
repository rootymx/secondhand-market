package com.example.demo.service;

import com.example.demo.dto.request.ProductCreateRequest;
import com.example.demo.dto.response.ProductVO;
import com.example.demo.common.result.PageResult;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductVO createProduct(ProductCreateRequest request, Long sellerId);
    ProductVO getProductById(Long id);
    PageResult<ProductVO> getProducts(Pageable pageable);
    PageResult<ProductVO> searchProducts(String keyword, Pageable pageable);
    void deleteProduct(Long id, Long sellerId);
}