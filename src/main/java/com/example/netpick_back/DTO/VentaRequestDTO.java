package com.example.netpick_back.DTO;

import java.util.List;
import lombok.Data;

@Data
public class VentaRequestDTO {
    private Integer idUsuario;
    private Integer idMetodoPago;
    private Integer idMetodoEnvio;
    private Integer idEstado;
    private List<VentaDetalleDTO> productos; 
}