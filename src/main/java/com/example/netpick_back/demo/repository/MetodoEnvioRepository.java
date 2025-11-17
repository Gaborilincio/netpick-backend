package com.example.netpick_back.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netpick_back.demo.model.MetodoEnvio;

@Repository
public interface MetodoEnvioRepository extends JpaRepository<MetodoEnvio, Integer> {
    List<MetodoEnvio> findByIdMetodoEnvio (Integer idMetodoEnvio);
    List<MetodoEnvio> findByNombre (String nombre);
}
