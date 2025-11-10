package com.example.ReseniaPOO.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.ReseniaPOO.model.Review;

public class ReviewController {
      @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<Review> createReview(    
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
}
