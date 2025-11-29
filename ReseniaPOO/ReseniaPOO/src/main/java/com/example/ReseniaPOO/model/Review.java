package com.example.ReseniaPOO.model;

import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Definición de la entidad Review mapeada a la tabla "reviews" en la base de datos
@Entity
@Table(name = "reviews")

/*
 * la funcion del Review es la de almacenar las reseñas de los productos
 * la entidad Review tiene los siguientes atributos:
 * id: identificador de la reseña
 * productoSku: identificador del producto
 * usuarioId: identificador del usuario
 * rating: calificacion de la reseña
 * comentario: texto de la reseña
 * createdAt: fecha de creacion de la reseña
 */
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // inicializamos los atributos de la entidad Review
    private Integer id;
    private String productoSku;
    private String usuarioId;
    private Integer rating;
    private String comentario;

    // anotacion para que Hibernate asigne automaticamente la fecha y hora de
    // creacion del registro
    // CreadaEn indica la fecha de creación de la reseña
    // es usado por Spring Data JPA para mapear el campo createdAt a la columna
    // "createdAt" en la tabla "reviews"
    @CreationTimestamp
    @Column(updatable = false, name = "createdAt")
    @JsonProperty("creadaEn")
    private Timestamp createdAt;

    // constructor vacio para que JPA pueda crear instancias de la entidad Review
    public Review() {
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductoSku() {
        return productoSku;
    }

    public void setProductoSku(String productoSku) {
        this.productoSku = productoSku;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
