package com.example.netpick_back.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "metodoenvio")
public class MetodoEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMetodoEnvio;

    @Column(name = "nombreMetodoEnvio", length = 50, nullable = false)
    private String nombre;

    public MetodoEnvio(String nombre) {
        this.nombre = nombre;
    }

}
