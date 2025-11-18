package com.example.netpick_back.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netpick_back.demo.model.MetodoPago;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    Optional<MetodoPago> findByIdMetodoPago (Integer idMetodoPago);
    List<MetodoPago> findByNombre (String nombre);
}
