package com.example.netpick_back.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class VentaHistorialDTO {
    private Integer idVenta;
    private LocalDateTime fechaVenta;
    private Integer totalVenta;
    private String estadoNombre;
    private List<VentaDetalleDTO> productos;
}