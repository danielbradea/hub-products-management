package com.bid90.hpm.service;

import com.bid90.hpm.exceptions.ProductException;
import com.bid90.hpm.model.Product;
import com.bid90.hpm.model.dto.ProductDto;
import com.bid90.hpm.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest{

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private ProductServiceImpl productService;

    Product product1;
    Product product2;

    @BeforeEach
    public void setup() {
       product1 = new Product();
        product1.setId(1L);
        product1.setName("Test Product 1");
        product1.setPrice(10.0);
        product2 = new Product();
        product2.setId(2L);
        product2.setName("Test Product 2");
        product2.setPrice(14.44);
        productRepository.saveAll(List.of(product1,product2));
    }

    @Test
    void test_getAll(){
        var pageable = PageRequest.of(0,10);
        when(productRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(product1,product2)));

        Page<Product> result = productService.getAll(pageable);

        assertEquals(2, result.getTotalElements(), "Total elements should be 2");
        assertEquals(1L, result.getContent().get(0).getId(), "Product ID should match");
        assertEquals("Test Product 1", result.getContent().get(0).getName(), "Product name should match");
        assertEquals(10.0, result.getContent().get(0).getPrice(), "Product price should match");

    }

    @Test
    void test_getOne(){
        var productId = 1L;
        when(productRepository.getProductById(1L)).thenReturn(Optional.of(product1));
        Product result = productService.getOne(1L);
        assertEquals(1L, result.getId(), "Product ID should match");
        assertEquals("Test Product 1", result.getName(), "Product name should match");
        assertEquals(10.0, result.getPrice(), "Product price should match");
    }

    @Test
    void test_getOne_productNotFound(){
        var productId = 5L;
        when(productRepository.getProductById(productId)).thenReturn(Optional.empty());

        ProductException exception = assertThrows(ProductException.class, () -> productService.getOne(productId));
        assertEquals(404, exception.getStatusCodes(), "Status code should be 404" );

    }


    @Test
    void test_add(){
        var productDto = new ProductDto();
        productDto.setName("New Product");
        productDto.setPrice(15.0);

        Product savedProduct = new Product();
        savedProduct.setId(3L);
        savedProduct.setName(productDto.getName());
        savedProduct.setPrice(productDto.getPrice());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        Product result = productService.add(productDto);

        assertEquals(3L, result.getId(), "Product ID should match");
        assertEquals("New Product", result.getName(), "Product name should match");
        assertEquals(15.0, result.getPrice(), "Product price should match");

    }


    @Test
    void test_update(){
        var productId = 1L;
        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Product");
        productDto.setPrice(20.0);

        when(productRepository.getProductById(productId)).thenReturn(Optional.of(product1));

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName(productDto.getName());
        updatedProduct.setPrice(productDto.getPrice());
        when(productRepository.save(product1)).thenReturn(updatedProduct);

        Product result = productService.update(productId, productDto);

        assertEquals(productId, result.getId(), "Product ID should match");
        assertEquals(productDto.getName(), result.getName(), "Product name should match");
        assertEquals(productDto.getPrice(), result.getPrice(), "Product price should match");

    }

    @Test
    void test_update_productNotFound(){
        var productId = 5L;

        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Product");
        productDto.setPrice(20.0);

        when(productRepository.getProductById(productId)).thenReturn(Optional.empty());

        ProductException exception = assertThrows(ProductException.class, () -> productService.update(productId, productDto));
        assertEquals(404, exception.getStatusCodes(), "Status code should be 404" );

    }


    @Test
    void test_delete(){
        var productId = 1L;

        when(productRepository.getProductById(productId)).thenReturn(Optional.of(product1));
        productService.delete(productId);

    }


    @Test
    void test_delete_productNotFound(){
        var productId = 5L;

        when(productRepository.getProductById(productId)).thenReturn(Optional.empty());

        ProductException exception = assertThrows(ProductException.class, () -> productService.delete(productId));
        assertEquals(404, exception.getStatusCodes(), "Status code should be 404" );

    }






}
