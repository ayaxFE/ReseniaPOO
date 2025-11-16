package com.example.ReseniaPOO.dto;

public class RatingResponseDTO {
    private String sku;
    private Double promedio;
    private long cantidad;

    public RatingResponseDTO(String sku, Double promedio, long cantidad) {
        this.sku = sku;
        this.promedio = promedio;
        this.cantidad = cantidad;
    }

    //Getters y Setters

    public String getSku() { 
        return sku; 
    }
    
    public void setSku(String sku) { 
        this.sku = sku; 
    }

    public Double getPromedio() { 
        return promedio; 
    }

    public void setPromedio(Double promedio) { 
        this.promedio = promedio; 
    }

    public long getCantidad() { 
        return cantidad; 
    }

    public void setCantidad(long cantidad) { 
        this.cantidad = cantidad; 
    }
}
