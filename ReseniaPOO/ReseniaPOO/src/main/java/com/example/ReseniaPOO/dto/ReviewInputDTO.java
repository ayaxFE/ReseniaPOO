package com.example.ReseniaPOO.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewInputDTO {
    @NotBlank(message = "productoSku no puede estar vacío")
    private String productoSku;

    @NotBlank(message = "usuarioId no puede estar vacío")
    private String usuarioId;

    @NotNull(message = "rating no puede ser nulo")
    @Min(value = 1, message = "Rating debe ser mínimo 1")
    @Max(value = 5, message = "Rating debe ser máximo 5")
    private Integer rating;

    private String comentario;

    //Getters y Setters

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
}
