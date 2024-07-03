package com.bid90.hpm.service;

import com.bid90.hpm.exceptions.GlobalExceptionHandler;
import com.bid90.hpm.exceptions.ProductException;
import com.bid90.hpm.model.Product;
import com.bid90.hpm.model.dto.ProductDto;
import com.bid90.hpm.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getOne(Long id) {
        return productRepository.getProductById(id)
                .orElseThrow(()->new ProductException("Product with id: "+ id +" not found",404));
    }

    @Override
    public Product add(ProductDto productDto) {
        var product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        var newProduct =  productRepository.save(product);
        logger.info("New product was added");
        return newProduct;
    }

    @Override
    public Product update(Long id, ProductDto productDto) {
        var product = productRepository.getProductById(id)
                .orElseThrow(()->new ProductException("Product with id: "+ id +" not found",404));

        Optional.ofNullable(productDto.getName()).ifPresent(product::setName);
        Optional.ofNullable(productDto.getPrice()).ifPresent(product::setPrice);

        var updatedProduct =  productRepository.save(product);
        logger.info("Product with id: "+id+" updated");
        return updatedProduct;
    }

    @Override
    public void delete(Long id) {
        var product = productRepository.getProductById(id)
                .orElseThrow(()->new ProductException("Product with id: "+ id +" not found",404));
        productRepository.delete(product);
        logger.info("Product with id: "+id+" was deleted");
    }
}
