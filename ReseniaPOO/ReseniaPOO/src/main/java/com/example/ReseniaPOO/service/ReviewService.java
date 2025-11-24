package com.example.ReseniaPOO.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ReseniaPOO.dto.RatingResponseDTO;
import com.example.ReseniaPOO.dto.ReviewInputDTO;
import com.example.ReseniaPOO.model.Review;
import com.example.ReseniaPOO.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Obtener reseñas de un producto específico con filtros y paginación
     */
    public List<Review> getReviews(String sku, Integer minRating, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        if (minRating != null) {
            // Busca por SKU y Rating mínimo
            return reviewRepository.findByProductoSkuAndRatingGreaterThanEqualOrderByCreatedAtDesc(sku, minRating, pageable);
        } else {
            // Busca solo por SKU
            return reviewRepository.findByProductoSkuOrderByCreatedAtDesc(sku, pageable);
        }
    }

    /**
     * Obtener rating promedio y cantidad de reseñas
     */
    public RatingResponseDTO getAverageRating(String sku) {
        // Obtiene el promedio usando la Query personalizada del repositorio
        Double promedio = reviewRepository.getAverageRatingByProductoSku(sku);
        
        // Cuenta el total de reseñas
        long cantidad = reviewRepository.countByProductoSku(sku);

        if (promedio == null) {
            promedio = 0.0;
        }

        // Constructor de DTO coincidencia con estos parámetros
        return new RatingResponseDTO(sku, promedio, cantidad);
    }

    /**
     * Eliminar reseña por ID
     */
    public void deleteReview(Integer id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseña no encontrada con ID: " + id);
        }
        reviewRepository.deleteById(id);
    }

    public Review createReview(ReviewInputDTO reviewInput) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createReview'");
    }
}