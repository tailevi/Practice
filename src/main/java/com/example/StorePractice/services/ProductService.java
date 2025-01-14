package com.example.StorePractice.services;

import com.example.StorePractice.models.Product;
import com.example.StorePractice.payload.response.ProductsResponse;
import com.example.StorePractice.reposetories.ProductRepo;
import com.example.StorePractice.reposetories.ReviewesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    public ProductRepo productRepo;

    @Autowired
    public ReviewesRepo reviewesRepo;

    public List<Product> findAllProducts(){return productRepo.findAll();}


}
