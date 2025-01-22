package com.example.StorePractice.controllers;

import com.example.StorePractice.models.Product;
import com.example.StorePractice.models.ProductDTO;
import com.example.StorePractice.payload.request.ProductRequest;
import com.example.StorePractice.payload.response.GenericResponses;
import com.example.StorePractice.payload.response.ProductResponse;
import com.example.StorePractice.payload.response.ResponseEnum;
import com.example.StorePractice.services.ProductService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @DeleteMapping(value = "/deleteProduct")
    public ResponseEntity<GenericResponses> deleteProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.deleteProductById(productRequest));
    }

    @RateLimiter(name = "default")
    @GetMapping(value = "/findProductById")
    public ResponseEntity<ProductDTO> findProductById(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.findProductById(productRequest));
    }

    @PostMapping(value = "/addProduct")
    public ResponseEntity<GenericResponses> addProduct(@RequestBody ProductRequest productRequest){
        return  ResponseEntity.ok(productService.AddProduct(productRequest));
    }

    @PostMapping(value = "/updateProduct")
    public ResponseEntity<GenericResponses> updateProduct(@RequestBody ProductRequest productRequest){
        return  ResponseEntity.ok(productService.updateProduct(productRequest));
    }

}
