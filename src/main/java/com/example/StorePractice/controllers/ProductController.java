package com.example.StorePractice.controllers;

import com.example.StorePractice.models.Product;
import com.example.StorePractice.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
