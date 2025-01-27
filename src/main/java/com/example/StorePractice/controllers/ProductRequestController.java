package com.example.StorePractice.controllers;

import com.example.StorePractice.payload.request.ProductRequest;
import com.example.StorePractice.payload.response.ProductResponse;
import com.example.StorePractice.payload.response.ProductsResponse;
import com.example.StorePractice.payload.response.ResponseEnum;
import com.example.StorePractice.services.ProductRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productRequest")
public class ProductRequestController {

    @Autowired
    private ProductRequestService productRequestService;


    @RequestMapping(value = "/returnAllProducts")
    public ResponseEntity<ProductsResponse> returnAllProducts(){
        return ResponseEntity.ok( productRequestService.findAll());
    }

    @DeleteMapping(value ="/deleteProduct")
    public ResponseEntity<ResponseEnum> deleteProduct(@RequestBody ProductRequest productRequest){
        return  ResponseEntity.ok(productRequestService.deleteProduct(productRequest.getId()));
    }
    @RequestMapping(value ="/returnProductById")
    public ResponseEntity<ProductResponse> returnProductById(@RequestBody ProductRequest productRequest){
        return  ResponseEntity.ok(productRequestService.findProductById(productRequest.getId()));
    }

    @PatchMapping(value ="/updateProduct")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok( productRequestService.updateProduct(productRequest));
    }

    @PostMapping(value ="/addProduct")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productRequestService.addProduct(productRequest));
    }

}
