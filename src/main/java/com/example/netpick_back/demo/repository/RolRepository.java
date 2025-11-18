package com.example.netpick_back.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netpick_back.demo.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByIdRol (Integer idRol);
    List<Rol> findByNombre (String nombre);
}
