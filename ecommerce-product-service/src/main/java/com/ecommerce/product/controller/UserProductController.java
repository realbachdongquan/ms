package com.ecommerce.product.controller;

import com.ecommerce.product.entity.Product;
import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user_role")
@RequiredArgsConstructor
public class UserProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final ProductService advertiseService;

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts(){

        LOGGER.info("UserProductController | getAllProducts is started");

        LOGGER.info("UserProductController | getAllProducts size : " + advertiseService.getAllProducts().size());

        return ResponseEntity.ok(advertiseService.getAllProducts());
    }

    @GetMapping("/Product/{ProductId}")
    public ResponseEntity<Product> getProductById(@PathVariable String ProductId){

        LOGGER.info("UserProductController | getProductById is started");

        LOGGER.info("UserProductController | getProductById | ProductId :  " + ProductId);

        return ResponseEntity.ok(advertiseService.getProductById(ProductId));
    }
}
