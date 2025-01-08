package com.example.StorePractice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@Table(name ="reviews")
@NoArgsConstructor
@AllArgsConstructor
public class reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    Integer rating;
    String commnet;
    String date;
    String reviewerName;
    String reviewerEmail;


}
