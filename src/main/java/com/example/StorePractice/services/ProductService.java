package com.example.StorePractice.services;

import com.example.StorePractice.annotations.LogUpdate;
import com.example.StorePractice.exceptions.ProductsServiceException;
import com.example.StorePractice.exceptions.RateLimiterException;
import com.example.StorePractice.models.Product;
import com.example.StorePractice.models.ProductDTO;
import com.example.StorePractice.models.Reviews;
import com.example.StorePractice.models.ReviewsDTO;
import com.example.StorePractice.payload.request.ProductRequest;
import com.example.StorePractice.payload.request.ReviewRequest;
import com.example.StorePractice.payload.response.GenericResponses;
import com.example.StorePractice.payload.response.ResponseEnum;
import com.example.StorePractice.reposetories.ProductRepo;
import com.example.StorePractice.reposetories.ReviewesRepo;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    public ProductRepo productRepo;

    @Autowired
    public ReviewesRepo reviewesRepo;

    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    public RedisTemplate<String, List<ReviewsDTO>> reviewRedisTemplet;

    private  static final String PRODUCT_KEY = "_product";
    private  static final String REVIEW_KEY = "_review";

    public List<Product> findAllProducts(){return productRepo.findAll();}

    @SneakyThrows
    @LogUpdate
    public GenericResponses deleteProductById(@NonNull ProductRequest productRequest){
        try {
            redisTemplate.opsForHash().delete(PRODUCT_KEY, productRequest.getId());
            productRepo.deleteById(productRequest.getId());
            return GenericResponses.builder()
                    .message("Product was deleted")
                    .id(productRequest.getId())
                    .build();
         }catch (RequestNotPermitted e){
        throw new RateLimiterException("Rate limit exceeded. Please try again later.", e);
        }
    }

    @SneakyThrows
    @Transactional
    @LogUpdate
    public ProductDTO findProductById(@NonNull ProductRequest productRequest){
        try {
            Product cachedProduct = (Product) redisTemplate.opsForHash().get(PRODUCT_KEY, productRequest.getId());
            ProductDTO productResponse;

            if (cachedProduct != null) {
                List<Reviews> reviewsList = (List<Reviews>) redisTemplate.opsForHash().get(REVIEW_KEY, productRequest.getId());
                cachedProduct.setReviews(reviewsList);
                productResponse = mapProduct(cachedProduct);
                return productResponse;
            }

            Product product = productRepo.findById(productRequest.getId())
                    .orElseThrow(() -> new ProductsServiceException("product was not found for the id: "+ productRequest.getId()));
            productResponse = mapProduct(product);
            redisTemplate.opsForHash().put(PRODUCT_KEY, productResponse.getId(), product);
            productResponse.setReviews(getCechedReviews(product.getId(), product));

            return productResponse;
        }catch (RequestNotPermitted e){
            throw new RateLimiterException("Rate limit exceeded. Please try again later.", e);
        }
    }


    private ProductDTO mapProduct(Product product){
        return ProductDTO.builder()
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


    @Transactional
    private List<ReviewsDTO> getCechedReviews(Long id, Product cachedProduc){
        List<ReviewsDTO>  reviewsDTOS = reviewRedisTemplet.opsForValue().get(REVIEW_KEY+id);
        if(reviewsDTOS!= null){
            return reviewsDTOS;
        }
        if(cachedProduc.getReviews() != null && !cachedProduc.getReviews().isEmpty()){
            List<Reviews> reviewsList = cachedProduc.getReviews();
            reviewsDTOS = reviewsList.stream().map(this::mapToReviewDTO).collect(Collectors.toList());
        }
            reviewRedisTemplet.opsForValue().set(REVIEW_KEY+id,reviewsDTOS);
            return reviewsDTOS;
    }


    private ReviewsDTO mapToReviewDTO(Reviews review) {
        ReviewsDTO reviewDTO = new ReviewsDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setRating(review.getRating());
        return reviewDTO;
    }
    @SneakyThrows
    @Transactional
    @LogUpdate
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
    @LogUpdate
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
            redisTemplate.opsForValue().set(PRODUCT_KEY, mapProduct(existingProduct));

            return GenericResponses.builder()
                    .id(existingProduct.getId())
                    .title(existingProduct.getTitle())
                    .message(ResponseEnum.ADDED.toString())
                    .build();
        }
        throw new ProductsServiceException("Item with ID " + productRequest.getId() + " was not found or could not be deleted");
    }

    @LogUpdate
    @SneakyThrows
    @Transactional
    public GenericResponses addReview(@NonNull ProductRequest productRequest){
        Product  product =  productRepo.findById(productRequest.getId()).orElseThrow( () -> new ProductsServiceException("product was not found for the id: "+ productRequest.getId()));

        if(!productRequest.getReviewRequests().isEmpty()) {
            List<ReviewRequest> reviewRequest = productRequest.getReviewRequests();
            for(int i = 0 ; i< reviewRequest.size();i++)
            {
                Reviews reviews = Reviews.builder()
                        .rating(reviewRequest.get(i).getRating())
                        .reviewerName(reviewRequest.get(i).getReviewerName())
                        .reviewerEmail(reviewRequest.get(i).getReviewerEmail())
                        .date(reviewRequest.get(i).getDate())
                        .comment(reviewRequest.get(i).getComment())
                        .build();

                List<Reviews> NewReviews = product.getReviews();
                NewReviews.add(reviews);
                product.setReviews(NewReviews);
                reviewRedisTemplet.opsForHash().put(REVIEW_KEY, reviews.getId(), reviews);
            }
            productRepo.save(product);

            return GenericResponses.builder()
                    .title("reviews added successfully ")
                    .message(ResponseEnum.ADDED.toString())
                    .build();
        }
        return GenericResponses.builder()
                .message("There was not Review submitted")
                .id(productRequest.getId())
                .build();
    }

}
