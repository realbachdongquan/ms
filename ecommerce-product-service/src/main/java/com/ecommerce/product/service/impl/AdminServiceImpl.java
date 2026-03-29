package com.ecommerce.product.service.impl;

import com.ecommerce.product.convertor.ProductMapper;
import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductState;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.AdminService;
import com.ecommerce.product.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final ProductRepository ProductRepository;
    private final MessageService messagingService;

    @Override
    public void createProduct(ProductRequest ProductRequest, String userId) {

        LOGGER.info("AdminServiceImpl | createProduct is started");

        LOGGER.info("AdminServiceImpl | createProduct | userId : " + userId);

        Product createdProduct = ProductMapper.ProductRequestToProduct(ProductRequest);
        createdProduct.setUserId(Long.valueOf(userId));
        createdProduct.setViewCount(1L);
        createdProduct.setState(ProductState.WAITING);

        LOGGER.info("AdminServiceImpl | createProduct | createdProduct state : " + createdProduct.getState().toString());

        ProductRepository.save(createdProduct);
    }

    @Override
    public void updateProduct(ProductRequest ProductRequest, String ProductId) {

        LOGGER.info("AdminServiceImpl | updateProduct is started");

        LOGGER.info("AdminServiceImpl | updateProduct | ProductId : " + ProductId);

        Optional<Product> optionalProduct = ProductRepository.findById(Long.valueOf(ProductId));

        Product updatedProduct = optionalProduct.get();

        LOGGER.info("AdminServiceImpl | updateProduct | updatedProduct title : " + updatedProduct.getTitle());

        if (updatedProduct.getPrice() != ProductRequest.getPrice()){
            updatedProduct.setPrice(ProductRequest.getPrice());
        }
        if (updatedProduct.getTitle() != ProductRequest.getTitle()){
            updatedProduct.setTitle(ProductRequest.getTitle());
        }

        updatedProduct.setState(ProductState.WAITING);

        LOGGER.info("AdminServiceImpl | updateProduct | updatedProduct state : " + updatedProduct.getState().toString());

        ProductRepository.save(updatedProduct);
    }

    @Override
    public void deleteProduct(String ProductId) {

        LOGGER.info("AdminServiceImpl | deleteProduct is started");

        LOGGER.info("AdminServiceImpl | deleteProduct | ProductId : " + ProductId);

        Optional<Product> optionalProduct = ProductRepository.findById(Long.valueOf(ProductId));
        Product deletedProduct = optionalProduct.get();

        LOGGER.info("AdminServiceImpl | deleteProduct | deletedProduct title : " + deletedProduct.getTitle());

        ProductRepository.delete(deletedProduct);
    }

    @Override
    public Product approveProduct(String ProductId) {

        LOGGER.info("AdminServiceImpl | approveProduct is started");

        LOGGER.info("AdminServiceImpl | approveProduct | ProductId : " + ProductId);

        Optional<Product> optionalAdvertise = ProductRepository.findById(Long.valueOf(ProductId));

        Product Product = optionalAdvertise.get();

        LOGGER.info("AdminServiceImpl | approveProduct | Product title : " + Product.getTitle());

        Product.setState(ProductState.APPROVED);

        // To access Product ID , use saveAndFlush
        ProductRepository.saveAndFlush(Product);

        messagingService.sendMessage(Product);

        return Product;
    }

    @Override
    public Product rejectProduct(String ProductId) {

        LOGGER.info("AdminServiceImpl | rejectProduct is started");

        LOGGER.info("AdminServiceImpl | rejectProduct | ProductId : " + ProductId);

        Optional<Product> optionalAdvertise = ProductRepository.findById(Long.valueOf(ProductId));

        Product Product = optionalAdvertise.get();

        LOGGER.info("AdminServiceImpl | approveProduct | Product title : " + Product.getTitle());

        Product.setState(ProductState.REJECTED);

        ProductRepository.save(Product);

        return Product;
    }

    @Override
    public List<Product> getAllProductsForAdmin() {
        LOGGER.info("AdminServiceImpl | getAllProductsForAdmin is started");

        List<Product> advertiseList = ProductRepository.findAll();

        LOGGER.info("AdminServiceImpl | getAllProducts | advertiseList size : " + advertiseList.size());

        return advertiseList;
    }

    @Override
    public Product getProductByIdForAdmin(String ProductId) {

        LOGGER.info("ProductServiceImpl | getProductById is started");

        Optional<Product> optionalProduct = ProductRepository.findById(Long.valueOf(ProductId));

        Product Product = optionalProduct.get();

        LOGGER.info("ProductServiceImpl | getProductById | Product title : " + Product.getTitle());

        return Product;
    }
}
