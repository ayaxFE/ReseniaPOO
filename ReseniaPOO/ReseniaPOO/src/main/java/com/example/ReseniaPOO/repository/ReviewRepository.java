package com.example.ReseniaPOO.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ReseniaPOO.model.Review;

// se indica que aca tenemos el repositorio de la base de datos 
@Repository

/*
 * esta es la interface que extiende de JpaRepository que es la interface que
 * proporciona Spring Data JPA para realizar
 * operaciones CRUD en la base de datos como insertar, actualizar, eliminar y
 * consultar
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // indica si ya hay una reseña existente de un usuario para un producto
    boolean existsByProductoSkuAndUsuarioId(String productoSku, String usuarioId);

    // esta linea busca la reseña de un producto ordenada por fecha de creacion
    List<Review> findByProductoSkuOrderByCreatedAtDesc(String productoSku, Pageable pageable);

    List<Review> findByProductoSkuAndRatingGreaterThanEqualOrderByCreatedAtDesc(String productoSku, Integer minRating,
        Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productoSku = ?1")
    Double getAverageRatingByProductoSku(String productoSku);

    // esta linea cuenta la cantidad de reseñas de un producto
    long countByProductoSku(String productoSku);
}

/*
 * el Repository es un componente que se encarga de interactuar con la base de
 * datos
 * en resumen define los endpoint para obtener las reseñas de un producto
 * ayudandonos con lo que es las operaciones CRUD mencionadas en la presentacion
 * Spring Boot
 */