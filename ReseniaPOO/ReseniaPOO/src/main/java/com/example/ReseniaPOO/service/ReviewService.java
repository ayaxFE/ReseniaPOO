package com.example.ReseniaPOO.service;

// todos los imports necesarios para el funcionamiento de la clase
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

// anotacion que se usa para decir que esta es la parte de Service del Spring Boot
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Lógica para 7.1 Crear Reseña
     */
    public Review createReview(ReviewInputDTO reviewInput) {
        /**
         * Validación Anti-spam (Código 409 duplicada) sirve para evitar que un mismo
         * usuario
         * envíe múltiples reseñas para el mismo producto
         */
        if (reviewRepository.existsByProductoSkuAndUsuarioId(reviewInput.getProductoSku(),
                reviewInput.getUsuarioId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El usuario ya ha enviado una reseña para este producto");
        }

        // Convertir DTO a Entidad
        Review review = new Review();
        review.setProductoSku(reviewInput.getProductoSku());
        review.setUsuarioId(reviewInput.getUsuarioId());
        review.setRating(reviewInput.getRating());
        review.setComentario(reviewInput.getComentario());

        return reviewRepository.save(review);
    }

    /**
     * Lógica para 7.2 Listar Reseñas
     * aca podemos obtener las reseñas de un producto específico, con opción de
     * filtrar por rating mínimo y paginación
     * Obtener reseñas de un producto específico con filtros y paginación
     */
    public List<Review> getReviews(String sku, Integer minRating, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        if (minRating != null) {
            // Busca por SKU y Rating mínimo
            return reviewRepository.findByProductoSkuAndRatingGreaterThanEqualOrderByCreatedAtDesc(sku, minRating,
                    pageable);
        } else {
            // Busca solo por SKU
            return reviewRepository.findByProductoSkuOrderByCreatedAtDesc(sku, pageable);
        }
    }

    /**
     * Lógica para 7.3 Rating Promedio
     * desde este apartado podemos obtener el rating promedio y la cantidad de
     * reseñas para un producto específico
     * Obtener rating promedio y cantidad de reseñas
     */
    public RatingResponseDTO getAverageRating(String sku) {
        // Obtiene el promedio usando la Query personalizada del repositorio
        Double promedio = reviewRepository.getAverageRatingByProductoSku(sku);

        // Cuenta el total de reseñas
        long cantidad = reviewRepository.countByProductoSku(sku);

        // si el promedio es null, se le asigna 0.0 como valor por defecto
        if (promedio == null) {
            promedio = 0.0;
        }

        // Constructor de DTO coincidencia con estos parámetros
        return new RatingResponseDTO(sku, promedio, cantidad);
    }

    /**
     * Lógica para 7.4 Eliminar Reseña
     * desde este apartado podemos eliminar una reseña por su ID
     * Eliminar reseña por ID
     */
    public void deleteReview(Integer id) {
        // verifica si la reseña existe
        if (!reviewRepository.existsById(id)) {
            // si no existe lanza una excepcion que indica que la reseña no se encontro con
            // el id indicado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseña no encontrada con ID: " + id);
        }
        // si existe la elimina
        reviewRepository.deleteById(id);
    }

}