package com.example.ReseniaPOO.dto;

/* 
* En este DTO se define el Rating Response que es el rating promedio y la cantidad de reseñas para un producto
* los atributos que se van a recibir son el sku, promedio y cantidad
* el sku es el identificador del producto
* el promedio es el rating promedio del producto
* la cantidad es la cantidad de reseñas del producto
*/

public class RatingResponseDTO {
    // inicializamos los atributos del DTO
    private String sku;
    private Double promedio;
    private long cantidad;

    // desarrollo del constructor de la clase
    public RatingResponseDTO(String sku, Double promedio, long cantidad) {
        this.sku = sku;
        this.promedio = promedio;
        this.cantidad = cantidad;
    }

    // Getters y Setters

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        // Asignamos el valor recibido al atributo sku
        this.sku = sku;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        // Asignamos el valor recibido al atributo promedio
        this.promedio = promedio;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        // Asignamos el valor recibido al atributo cantidad
        this.cantidad = cantidad;
    }
}
