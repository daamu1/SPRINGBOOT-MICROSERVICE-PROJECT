package com.daamu.service;

import com.daamu.DTO.ProductRequest;
import com.daamu.DTO.ProductResponse;
import com.daamu.model.Product;
import com.daamu.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void createNewProduct(ProductRequest productRequest) {
        Product product= Product.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .productDescription(productRequest.getProductDescription())
                .build();
       productRepository.save(product);
       log.info("Product {} is saved successfully",product.getProductId());
    }
    public List<ProductResponse> getAllProduct()
    {
        List<Product> products= productRepository.findAll();
        return   products.stream().map(this:: mapToProductResponse).toList();
    }
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .price(product.getPrice())
                .productDescription(product.getProductDescription())
                .productName(product.getProductName())
                .build();

    }


}
