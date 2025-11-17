package com.example.netpick_back.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netpick_back.demo.model.Direcciones;

@Repository
public interface DireccionesRepository extends JpaRepository<Direcciones, Integer> {

    Optional<Direcciones> findByIdDireccion (Integer idDireccion);
    List<Direcciones> findByCorreoUsuario (Integer usuarioCorreo);
}
