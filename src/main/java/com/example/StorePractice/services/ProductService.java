package com.example.StorePractice.services;

import com.example.StorePractice.exceptions.ProductsServiceException;
import com.example.StorePractice.models.Product;
import com.example.StorePractice.payload.request.ProductRequest;
import com.example.StorePractice.payload.response.GenericResponses;
import com.example.StorePractice.payload.response.ProductResponse;
import com.example.StorePractice.payload.response.ResponseEnum;
import com.example.StorePractice.reposetories.ProductRepo;
import com.example.StorePractice.reposetories.ReviewesRepo;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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

    @SneakyThrows
    @Transactional
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
    @SneakyThrows
    @Transactional
    public GenericResponses AddProduct( @NonNull ProductRequest productRequest){
        Product product = Product.builder()
                .title(productRequest.getTitle())
                .brand(productRequest.getBrand())
                .price(productRequest.getPrice())
                .sku(productRequest.getSku())
                .availabilityStatus(productRequest.getAvailabilityStatus())
                .category(productRequest.getCategory())
                .description(productRequest.getDescription())
                .stock(productRequest.getStock())
                .discountPercentage(productRequest.getDiscountPercentage())
                .images(productRequest.getImages())
                .weight(productRequest.getWeight())
                .thumbnail(productRequest.getThumbnail())
                .returnPolicy(productRequest.getReturnPolicy())
                .warrantyInformation(productRequest.getWarrantyInformation())
                .shippingInformation(productRequest.getShippingInformation())
                .build();
        productRepo.save(product);
        return GenericResponses.builder()
                .id(product.getId())
                .title(product.getTitle())
                .message(ResponseEnum.ADDED.toString())
                .build();
    }


    @SneakyThrows
    @Transactional
    public GenericResponses updateProduct(@NonNull ProductRequest productRequest){
        Optional<Product> productOptional = productRepo.findById(productRequest.getId());

        if(productOptional.isPresent()){
            Product existingProduct = productOptional.get();

            Optional.ofNullable(productRequest.getTitle()).ifPresent(existingProduct::setTitle);
            Optional.ofNullable(productRequest.getDescription()).ifPresent(existingProduct::setDescription);
            Optional.ofNullable(productRequest.getCategory()).ifPresent(existingProduct::setCategory);
            Optional.ofNullable(productRequest.getPrice()).ifPresent(existingProduct::setPrice);
            Optional.ofNullable(productRequest.getDiscountPercentage()).ifPresent(existingProduct::setDiscountPercentage);
            Optional.ofNullable(productRequest.getRating()).ifPresent(existingProduct::setRating);
            Optional.ofNullable(productRequest.getStock()).ifPresent(existingProduct::setStock);
            Optional.ofNullable(productRequest.getBrand()).ifPresent(existingProduct::setBrand);
            Optional.ofNullable(productRequest.getSku()).ifPresent(existingProduct::setSku);
            Optional.ofNullable(productRequest.getWeight()).ifPresent(existingProduct::setWeight);
            Optional.ofNullable(productRequest.getWarrantyInformation()).ifPresent(existingProduct::setWarrantyInformation);
            Optional.ofNullable(productRequest.getShippingInformation()).ifPresent(existingProduct::setShippingInformation);
            Optional.ofNullable(productRequest.getAvailabilityStatus()).ifPresent(existingProduct::setAvailabilityStatus);
            Optional.ofNullable(productRequest.getReturnPolicy()).ifPresent(existingProduct::setReturnPolicy);
            Optional.ofNullable(productRequest.getThumbnail()).ifPresent(existingProduct::setThumbnail);
            Optional.ofNullable(productRequest.getImages()).ifPresent(existingProduct::setImages);

            productRepo.save(existingProduct);
            return GenericResponses.builder()
                    .id(existingProduct.getId())
                    .title(existingProduct.getTitle())
                    .message(ResponseEnum.ADDED.toString())
                    .build();
        }
        throw new ProductsServiceException("Item with ID " + productRequest.getId() + " was not found or could not be deleted");

    }


}