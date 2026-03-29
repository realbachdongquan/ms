package com.ecommerce.product.convertor;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static Product ProductRequestToProduct(ProductRequest ProductRequest){
        Product Product = new Product();
        Product.setPrice(ProductRequest.getPrice());
        Product.setTitle(ProductRequest.getTitle());
        return Product;
    }
}
