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
    private String productoSku; 
    private String usuarioId;
    private Integer rating;
    private String comentario; 
    
    @CreationTimestamp
    @Column(updatable = false, name = "createdAt") 
    @JsonProperty("creadaEn")
    private Timestamp createdAt;
    
    //constructor, getters y setters

        public Review() {
        }

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
