package com.example.netpick_back.DTO;

import lombok.Data;

@Data
public class VentaDetalleDTO { 
    private String nombreProducto; 
    private Integer precioUnitario; 
    private Integer cantidad; 

}