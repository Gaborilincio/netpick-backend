package com.example.netpick_back.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Categoria {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    @Column(name = "nombreCategoria", length = 50, nullable = false)
    private String nombre;

    @Column(name = "nombreDescripción", length = 300, nullable = false)
    private String descripcion;
}
