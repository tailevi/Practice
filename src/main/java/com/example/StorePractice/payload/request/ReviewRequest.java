package com.example.StorePractice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    Long id;
    Integer rating;
    String comment;
    String date;
    String reviewerName;
    String reviewerEmail;
}
