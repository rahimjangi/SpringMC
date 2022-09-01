package com.raiseup.microservice.productservice.service;

import com.raiseup.microservice.productservice.dto.ProductRequest;
import com.raiseup.microservice.productservice.dto.ProductResponse;
import com.raiseup.microservice.productservice.model.Product;
import com.raiseup.microservice.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        ProductResponse productResponse = new ProductResponse();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(productRequest, product);
        Product savedProduct = productRepository.save(product);
        modelMapper.map(savedProduct, productResponse);
        log.info("Product {} saved!", product.getId());
        return productResponse;
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(this::productResponseMapper)
                .collect(Collectors.toList());
    }

    private ProductResponse productResponseMapper(Product product) {
        ProductResponse productResponse = new ProductResponse();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(product, productResponse);
        return productResponse;
    }
}
