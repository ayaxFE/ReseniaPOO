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

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // --- CORRECCIÓN AQUÍ ---
    // Le decimos a Java que "productoSku" corresponde a la columna "producto_sku"
    @Column(name = "producto_sku") 
    private String productoSku;

    // --- CORRECCIÓN AQUÍ ---
    // Le decimos a Java que "usuarioId" corresponde a la columna "usuario_id"
    @Column(name = "usuario_id")
    private String usuarioId;

    private Integer rating;
    
    // "comentario" coincide en ambos lados, no necesita @Column obligatorio, 
    // pero si quieres ser explícito puedes ponerlo.
    private String comentario;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    @JsonProperty("creadaEn")
    private Timestamp createdAt;

    public Review() {
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getProductoSku() { return productoSku; }
    public void setProductoSku(String productoSku) { this.productoSku = productoSku; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}