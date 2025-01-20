package com.example.StorePractice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Builder
@Data
@Table(name ="reviews")
@NoArgsConstructor
@AllArgsConstructor
public class Reviews implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    Integer rating;
    String comment;
    String date;
    String reviewerName;
    String reviewerEmail;


}
