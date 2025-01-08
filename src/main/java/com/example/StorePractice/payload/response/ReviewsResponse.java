package com.example.StorePractice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsResponse implements Serializable {
    Integer rating;
    String comment;
    String date;
    String reviewerName;
    String reviewerEmail;
}
