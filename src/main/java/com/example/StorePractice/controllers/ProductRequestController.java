package com.example.StorePractice.controllers;

import com.example.StorePractice.payload.response.ProductResponse;
import com.example.StorePractice.payload.response.ProductsResponse;
import com.example.StorePractice.services.ProductRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productRequest")
public class ProductRequestController {

    @Autowired
    private ProductRequestService productRequestService;

    @RequestMapping(value = "/returnAllProducts")
    public ResponseEntity<ProductsResponse> returnAllProducts(){
        return ResponseEntity.ok( productRequestService.findAll());
    }

}
