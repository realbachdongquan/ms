package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.entity.Product;

import java.util.List;

public interface AdminService {

    void createProduct(ProductRequest ProductRequest, String userId);

    void updateProduct(ProductRequest ProductRequest, String ProductId);

    void deleteProduct(String ProductId);

    Product approveProduct(String ProductId);

    Product rejectProduct(String ProductId);

    List<Product> getAllProductsForAdmin();

    Product getProductByIdForAdmin(String ProductId);

}
