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
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(name = "nombreUsuario", length = 50, nullable = false)
    private String nombre;

    @Column(name = "correoUsuario", length = 50, nullable = false, unique = true)
    private String correo;

    @Column(name = "claveUsuario", length = 100, nullable = false)
    private String clave;

    @Column(name = "telefonoUsuario", length = 50, nullable = false)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    public Usuario(String nombre, String correo, String clave, String telefono, Rol rol) {
    this.nombre = nombre;
    this.correo = correo;
    this.clave = clave;
    this.telefono = telefono;
    this.rol = rol;
}
}
