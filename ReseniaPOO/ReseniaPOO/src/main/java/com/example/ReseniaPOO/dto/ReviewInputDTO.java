package com.example.ReseniaPOO.dto;
// import del tipo jakarta validation para las validaciones de los campos 
// utilizado por Spring Boot para validar los datos de entrada en los DTOs
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewInputDTO {
    // evaluamos que el productoSku ingresado no sea nulo o vacio
    @NotBlank(message = "productoSku no puede estar vacío")
    private String productoSku;

    // evaluamos que el usuarioId ingresado no sea nulo o vacio
    @NotBlank(message = "usuarioId no puede estar vacío")
    private String usuarioId;

    // podemos evaluar que el rating entre entre el limite de 1 a 5 y que no sea un nulo el valor resibido
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
        // Asignamos el valor recibido al atributo productoSku
        this.productoSku = productoSku; 
    }

    public String getUsuarioId() { 
        return usuarioId; 
    }

    public void setUsuarioId(String usuarioId) { 
        // Asignamos el valor recibido al atributo usuarioId
        this.usuarioId = usuarioId; 
    }

    public Integer getRating() { 
        return rating; 
    }

    public void setRating(Integer rating) { 
        // Asignamos el valor recibido al atributo rating
        this.rating = rating; 
    }

    public String getComentario() { 
        return comentario; 
    }

    public void setComentario(String comentario) { 
        // asignamos al valor recibido al atributo comentario
        this.comentario = comentario; 
    }
}
