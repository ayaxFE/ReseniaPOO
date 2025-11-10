package com.example.ReseniaPOO.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ReseniaPOO.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    boolean existsByProductoSkuAndUsuarioId(String productoSku, String usuarioId);
    
    List<Review> findByProductoSkuOrderByCreatedAtDesc(String productoSku, Pageable pageable);
    List<Review> findByProductoSkuAndRatingGreaterThanEqualOrderByCreatedAtDesc(String productoSku, Integer minRating, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productoSku = ?1")
    Double getAverageRatingByProductoSku(String productoSku);

    long countByProductoSku(String productoSku);

}