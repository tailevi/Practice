package com.example.StorePractice.controllers;

import com.example.StorePractice.models.Product;
import com.example.StorePractice.payload.request.ProductRequest;
import com.example.StorePractice.payload.response.GenericResponses;
import com.example.StorePractice.payload.response.ProductResponse;
import com.example.StorePractice.services.ProductService;
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
    public ResponseEntity<String > deleteProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.deleteProductById(productRequest.getId()));
    }

    @GetMapping(value = "/findProductById")
    public ResponseEntity<ProductResponse> findProductById(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.findProductById(productRequest.getId()));
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
