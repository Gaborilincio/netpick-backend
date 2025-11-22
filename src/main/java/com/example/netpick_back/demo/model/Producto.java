package com.example.netpick_back.demo.model;

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
@Table (name = "producto")


public class Producto {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    
    @Column(name = "nombreProducto", length = 50, nullable = false)
    private String nombre;
     
    @Column(name = "descripcionProducto", length = 300, nullable = false)
    private String descripcion;
    
    @Column(name = "precioProducto", length = 50, nullable = false)
    private Integer precio;
    
    @Column(name = "stockProducto", length = 50, nullable = false)
    private Integer stock;
    
    @Column(name = "linkImagenProducto", nullable = false)
    private String linkImagen;

    @ManyToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    private Categoria categoria;

    public Producto(String nombre, String descripcion, Integer precio, Integer stock, String linkImagen, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.linkImagen = linkImagen;
        this.categoria = categoria;
    }
}
