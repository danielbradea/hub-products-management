package com.bid90.hpm.service;

import com.bid90.hpm.model.Product;
import com.bid90.hpm.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {


    Page<Product> getAll(Pageable pageable);

    Product getOne(Long id);

    Product add(ProductDto productDto);

    Product update(Long id, ProductDto productDto);

    void delete(Long id);
}
