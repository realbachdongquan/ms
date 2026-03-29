package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(String ProductId);

}
