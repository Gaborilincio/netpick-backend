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
@Table(name = "direcciones")
public class Direcciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDireccion;

    @Column(name = "direccion", length = 50, nullable = false)
    private String direccion;
    
    @Column(name = "codigoPostal", length = 50, nullable = false)
    private String codigoPostal;
    
    @Column(name = "pais", length = 50, nullable = false)
    private String pais;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "idComuna", nullable = false)
    private Comuna comuna;

    public Direcciones(String direccion, String codigoPostal, String pais, Usuario usuario, Comuna comuna) {
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.usuario = usuario;
        this.comuna = comuna;
    }
}
