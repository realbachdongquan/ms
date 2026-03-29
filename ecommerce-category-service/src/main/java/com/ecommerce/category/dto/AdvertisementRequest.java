package com.ecommerce.category.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdvertisementRequest {

    private String title;
    private BigDecimal price;
}
