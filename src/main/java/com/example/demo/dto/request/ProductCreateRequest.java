package com.example.demo.dto.request;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductCreateRequest {
    @NotBlank(message = "标题不能为空")
    @Size(max = 200, message = "标题最多 200 字")
    private String title;

    @Size(max = 2000, message = "描述最多 2000 字")
    private String description;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于 0")
    private BigDecimal price;

    @NotBlank(message = "分类不能为空")
    private String category;

    private String imageUrl;
}
