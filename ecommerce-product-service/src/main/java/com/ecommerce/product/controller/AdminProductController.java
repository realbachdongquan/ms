package com.ecommerce.product.controller;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/admin")
@RequiredArgsConstructor
public class AdminProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    //private final ProductService advertiseService;
    private final AdminService adminService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest ProductRequest, @PathVariable String userId){

        LOGGER.info("AdminProductController | createProduct is started");

        adminService.createProduct(ProductRequest,userId);

        LOGGER.info("AdminProductController | createProduct | Product Created");

        return ResponseEntity.status(HttpStatus.CREATED).body("Product Created");
    }

    @PutMapping("/update/{ProductId}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest ProductRequest ,@PathVariable String ProductId){

        LOGGER.info("AdminProductController | updateProduct is started");

        LOGGER.info("AdminProductController | updateProduct | ProductId : " + ProductId);

        adminService.updateProduct(ProductRequest,ProductId);

        LOGGER.info("AdminProductController | createProduct | Product Created");

        return ResponseEntity.status(HttpStatus.OK).body("Product Updated");
    }

    @DeleteMapping("/delete/{ProductId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String ProductId){

        LOGGER.info("AdminProductController | updateProduct is started");

        LOGGER.info("AdminProductController | updateProduct | ProductId : " + ProductId);

        adminService.deleteProduct(ProductId);

        LOGGER.info("AdminProductController | createProduct | Product Deleted");

        return ResponseEntity.status(HttpStatus.OK).body("Product Deleted");
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts(){

        LOGGER.info("AdminProductController | getAllProducts is started");

        return ResponseEntity.ok(adminService.getAllProductsForAdmin());
    }

    @GetMapping("/Product/{ProductId}")
    public ResponseEntity<Product> getProductById(@PathVariable String ProductId){

        LOGGER.info("AdminProductController | getProductById is started");

        LOGGER.info("AdminProductController | getProductById | getProductById " + ProductId);

        return ResponseEntity.ok(adminService.getProductByIdForAdmin(ProductId));
    }

    @GetMapping("/Product/{ProductId}/approve")
    public ResponseEntity<?> approveProduct(@PathVariable String ProductId){

        LOGGER.info("AdminProductController | approveProduct is started");

        LOGGER.info("AdminProductController | approveProduct | getProductById " + ProductId);

        Product Product = null;
        try{
            Product = adminService.approveProduct(ProductId);
        }catch (Exception e){
            LOGGER.info("AdminProductController | approveProduct | error " + e.getMessage());
        }

        LOGGER.info("AdminProductController | approveProduct | Approved ");

        return ResponseEntity.ok("Approved");
    }

    @GetMapping("/Product/{ProductId}/reject")
    public ResponseEntity<?> rejectProduct(@PathVariable String ProductId){

        LOGGER.info("AdminProductController | rejectProduct is started");

        LOGGER.info("AdminProductController | rejectProduct | getProductById " + ProductId);

        Product Product = null;
        try{
            Product = adminService.rejectProduct(ProductId);
        }catch (Exception e){
            LOGGER.info("AdminProductController | rejectProduct | error " + e.getMessage());
        }

        LOGGER.info("AdminProductController | approveProduct | Rejected ");

        return ResponseEntity.ok("Rejected");
    }
}
