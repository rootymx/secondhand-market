package com.example.demo.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductVO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String status;
    private String imageUrl;
    private Integer viewCount;
    private Long sellerId;
    private String sellerName;
    private LocalDateTime createdAt;
}