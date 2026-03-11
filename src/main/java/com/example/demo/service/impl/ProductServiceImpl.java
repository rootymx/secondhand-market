package com.example.demo.service.impl;

import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.constant.ErrorCode;
import com.example.demo.common.result.PageResult;
import com.example.demo.dto.request.ProductCreateRequest;
import com.example.demo.dto.response.ProductVO;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ProductVO createProduct(ProductCreateRequest request, Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND.getCode(), "卖家不存在"));
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
        product.setSeller(seller);
        return convertToVO(productRepository.save(product));
    }

    @Override
    public ProductVO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND.getCode(), "商品不存在"));
        product.setViewCount(product.getViewCount() + 1);
        productRepository.save(product);
        return convertToVO(product);
    }

    @Override
    public PageResult<ProductVO> getProducts(Pageable pageable) {
        Page<Product> page = productRepository.findByStatus("ON_SALE", pageable);
        return new PageResult<>(
                page.getContent().stream().map(this::convertToVO).collect(Collectors.toList()),
                page.getTotalElements(),
                (long) page.getNumber(),
                (long) page.getSize()
        );
    }

    @Override
    public PageResult<ProductVO> searchProducts(String keyword, Pageable pageable) {
        Page<Product> page = productRepository.searchByKeyword(keyword, pageable);
        return new PageResult<>(
                page.getContent().stream().map(this::convertToVO).collect(Collectors.toList()),
                page.getTotalElements(),
                (long) page.getNumber(),
                (long) page.getSize()
        );
    }

    @Override
    @Transactional
    public void deleteProduct(Long id, Long sellerId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND.getCode(), "商品不存在"));
        if (!product.getSeller().getId().equals(sellerId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN.getCode(), "无权删除此商品");
        }
        productRepository.delete(product);
    }

    private ProductVO convertToVO(Product product) {
        ProductVO vo = new ProductVO();
        vo.setId(product.getId());
        vo.setTitle(product.getTitle());
        vo.setDescription(product.getDescription());
        vo.setPrice(product.getPrice());
        vo.setCategory(product.getCategory());
        vo.setStatus(product.getStatus());
        vo.setImageUrl(product.getImageUrl());
        vo.setViewCount(product.getViewCount());
        vo.setSellerId(product.getSeller().getId());
        vo.setSellerName(product.getSeller().getUsername());
        vo.setCreatedAt(product.getCreatedAt());
        return vo;
    }
}
