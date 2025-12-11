package com.example.netpick_back.demo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "venta")

public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenta;

    @ManyToOne
    @JoinColumn(name = "idMetodoPago", nullable = false)
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "idMetodoEnvio", nullable = false)
    private MetodoEnvio metodoEnvio;

    @ManyToOne
    @JoinColumn(name = "idEstado", nullable = false)
    private Estado estado;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "ventas"})
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @CreationTimestamp 
    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta; 
    
    @Column(name = "total_venta", nullable = false)
    private Integer totalVenta;

    public Venta(MetodoPago metodoPago, MetodoEnvio metodoEnvio, Estado estado, Usuario usuario) {
        this.metodoPago = metodoPago;
        this.metodoEnvio = metodoEnvio;
        this.estado = estado;
        this.usuario = usuario;
    }

}
