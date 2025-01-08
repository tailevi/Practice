package com.example.StorePractice.services;

import com.example.StorePractice.models.Product;
import com.example.StorePractice.models.Reviews;
import com.example.StorePractice.payload.response.ProductResponse;
import com.example.StorePractice.payload.response.ProductsResponse;
import com.example.StorePractice.payload.response.ReviewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductRequestService {
    @Value("${dummy-json}")
    public static String url;
    @Autowired
    private RestTemplate URLRequest;

    public ProductsResponse findAll(){
        ProductsResponse prod = URLRequest.getForObject(url, ProductsResponse.class);
        assert prod != null;
        List<Product> pordList = prod.getProducts().stream()
                .map(this::mapToProductEntity)
                .collect(Collectors.toList());
        return prod;
    }

    private Product mapToProductEntity(ProductResponse response) {
        Product product = Product.builder()
                .id(response.getId())
                .title(response.getTitle())
                .description(response.getDescription())
                .category(response.getCategory())
                .price(response.getPrice())
                .discountPercentage(response.getDiscountPercentage())
                .rating(response.getRating())
                .stock(response.getStock())
                .tags(response.getTags())
                .brand(response.getBrand())
                .sku(response.getSku())
                .weight(response.getWeight())
                .warrantyInformation(response.getWarrantyInformation())
                .shippingInformation(response.getShippingInformation())
                .availabilityStatus(response.getAvailabilityStatus())
                .returnPolicy(response.getReturnPolicy())
                .thumbnail(response.getThumbnail())
                .images(response.getImages()).build();

        List<Reviews> reviewsList = response.getReviews().stream()
                .map(reviewResponse -> mapToReviewEntity(reviewResponse, product))
                .collect(Collectors.toList());
        product.setReviews(reviewsList);
        return product;
    }

    private Reviews mapToReviewEntity(ReviewsResponse reviewResponse, Product product) {
        return Reviews.builder()
                .rating(reviewResponse.getRating())
                .comment(reviewResponse.getComment())
                .date(reviewResponse.getDate())
                .reviewerName(reviewResponse.getReviewerName())
                .reviewerEmail(reviewResponse.getReviewerEmail())
                .build();
    }
}
