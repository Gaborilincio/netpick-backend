package com.example.netpick_back.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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

}
