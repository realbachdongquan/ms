package com.ecommerce.product.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {

    private Long id;
    private String title;
    private Long viewCount;
}
