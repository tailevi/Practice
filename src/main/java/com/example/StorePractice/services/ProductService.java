package com.example.StorePractice.services;

import com.example.StorePractice.models.Product;
import com.example.StorePractice.payload.response.ProductResponse;
import com.example.StorePractice.payload.response.ProductsResponse;
import com.example.StorePractice.reposetories.ProductRepo;
import com.example.StorePractice.reposetories.ReviewesRepo;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    public ProductRepo productRepo;

    @Autowired
    public ReviewesRepo reviewesRepo;

    public List<Product> findAllProducts(){return productRepo.findAll();}

    @SneakyThrows
    public String deleteProductById(@NonNull long id){
        productRepo.deleteById(id);
        return "Product with the following id: "+ id +" was deleted along with his reviews";
    }

    @SneakyThrows
    public ProductResponse findProductById(@NonNull long id){
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException());
        ProductResponse productResponse = mapProduct(product);
        return productResponse;
    }
    private  ProductResponse mapProduct(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .brand(product.getBrand())
                .price(product.getPrice())
                .sku(product.getSku())
                .availabilityStatus(product.getAvailabilityStatus())
                .category(product.getCategory())
                .description(product.getDescription())
                .stock(product.getStock())
                .discountPercentage(product.getDiscountPercentage())
                .images(product.getImages())
                .weight(product.getWeight())
                .thumbnail(product.getThumbnail())
                .returnPolicy(product.getReturnPolicy())
                .warrantyInformation(product.getWarrantyInformation())
                .shippingInformation(product.getShippingInformation())
                .build();
    }
}
