package com.example.netpick_back.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netpick_back.demo.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByIdUsuario (Integer idUsuario);
    List<Usuario> findByNombre (String nombre);
    Optional<Usuario> findByCorreo(String correo);
}