package com.bid90.hpm.controller;

import com.bid90.hpm.model.Product;
import com.bid90.hpm.model.dto.ProductDto;
import com.bid90.hpm.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Get a paginated list of products. Auth required",
            description = """
                    Returns a paginated list of products. The endpoint supports pagination parameters like page number , page size and sort by field. \n
                    ex:{"page": 0,"size": 10,"sort": ["id,desc" ]} \n
                    "id,desc" -> means descending sort by property id \n
                    """,
            security = @SecurityRequirement(name = "basicAuth")
    )
    @GetMapping
    Page<Product> listProduct(Pageable pageable){
      return productService.getAll(pageable);
    }

    @Operation(
            summary = "Get product. Auth required",
            description = "Get product by id",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @GetMapping("/{id}")
    Product getOne(@PathVariable("id") Long id){
        return productService.getOne(id);
    }

    @Operation(
            summary = "Add product. Auth 'admin' required",
            description = "Add new product",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @PostMapping()
    Product add(@RequestBody ProductDto productDto){
        return productService.add(productDto);
    }

    @Operation(
            summary = "Update product. Auth 'admin' required",
            description = "Update existing product",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @PatchMapping("/{id}")
    Product update(@PathVariable("id") Long id, @RequestBody ProductDto productDto){
        return productService.update(id,productDto);
    }

    @Operation(
            summary = "Delete product. Auth 'admin' required",
            description = "Delete existing product",
            security = @SecurityRequirement(name = "basicAuth")
    )
    @DeleteMapping("/{id}")
    void deleteOne(@PathVariable("id") Long id){
        productService.delete(id);
    }

}
