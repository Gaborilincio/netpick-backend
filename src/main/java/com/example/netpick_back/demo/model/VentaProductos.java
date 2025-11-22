package com.example.netpick_back.demo.model;

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
@Table (name = "ventaproductos")

public class VentaProductos {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idVentaProdutos;

    @ManyToOne
    @JoinColumn(name = "idVenta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    public VentaProductos(Venta venta, Producto producto) {
        this.venta = venta;
        this.producto = producto;
    }
}
