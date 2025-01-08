package com.example.StorePractice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Data
@Table(name ="product")
@NoArgsConstructor
@AllArgsConstructor
public class products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    String title;
    String description;
    String category;
    Integer price;
    Float discountPercentage;
    Float rating;
    Integer stock;
    List<String> tags;
    String brand;
    String sku;
    Integer weight;
    String warrantyInformation;
    String shippingInformation;
    String availabilityStatus;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    List<reviews> reviews;
    String returnPolicy;
    String thumbnail;
    List<String>images;
}
