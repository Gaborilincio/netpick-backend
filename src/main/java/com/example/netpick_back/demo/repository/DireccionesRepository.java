package com.example.netpick_back.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netpick_back.demo.model.Direcciones;
import com.example.netpick_back.demo.model.Usuario;

import java.util.List;


@Repository
public interface DireccionesRepository extends JpaRepository<Direcciones, Integer> {

    Optional<Direcciones> findByIdDireccion (Integer idDireccion);
    List<Direcciones> findByUsuario(Usuario usuario);
}
