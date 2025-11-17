package com.example.ReseniaPOO.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.ReseniaPOO.dto.RatingResponseDTO;
import com.example.ReseniaPOO.dto.ReviewInputDTO;
import com.example.ReseniaPOO.model.Review;
import com.example.ReseniaPOO.repository.ReviewRepository;
import com.example.ReseniaPOO.service.ReviewService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/reviews")

public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody ReviewInputDTO reviewInput) {
        ReviewService reviewService = null;
        Review newReview = reviewService.createReview(reviewInput);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<List<Review>> getReviewsByProduct(
            @PathVariable String sku,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        ReviewService reviewService = null;
        List<Review> reviews = reviewService.getReviews(sku, minRating, page, pageSize);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{sku}/rating")
    public ResponseEntity<RatingResponseDTO> getAverageRating(@PathVariable String sku) {
        ReviewService reviewService = null;
        RatingResponseDTO ratingData = reviewService.getAverageRating(sku);
        return ResponseEntity.ok(ratingData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        ReviewService reviewService = null;
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }


}
