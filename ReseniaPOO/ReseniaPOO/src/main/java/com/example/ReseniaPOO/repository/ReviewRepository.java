package com.example.ReseniaPOO.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ReseniaPOO.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}