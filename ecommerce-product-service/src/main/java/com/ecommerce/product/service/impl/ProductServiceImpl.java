package com.ecommerce.product.service.impl;

import com.ecommerce.product.convertor.ProductMapper;
import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductState;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final ProductRepository ProductRepository;


    @Override
    public List<Product> getAllProducts() {

        LOGGER.info("ProductServiceImpl | getAllProducts is started");

        List<Product> advertiseList = ProductRepository.findAll();

        LOGGER.info("ProductServiceImpl | getAllProducts | advertiseList size : " + advertiseList.size());

        advertiseList.stream().filter(Product-> Product.getState() == ProductState.APPROVED).forEach(
                Product -> Product.setViewCount(Product.getViewCount()+1));

        LOGGER.info("ProductServiceImpl | getAllProducts | advertiseList size : " + advertiseList.size());

        return advertiseList;
    }

    @Override
    public Product getProductById(String ProductId) {

        LOGGER.info("ProductServiceImpl | getProductById is started");

        Optional<Product> optionalProduct = ProductRepository.findById(Long.valueOf(ProductId));

        Product Product = optionalProduct.get();

        LOGGER.info("ProductServiceImpl | getProductById | Product title : " + Product.getTitle());

        Product.setViewCount(Product.getViewCount()+1);

        return Product;
    }
}
