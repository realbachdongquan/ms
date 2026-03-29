package com.ecommerce.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    private String title;
    private BigDecimal price;
}
