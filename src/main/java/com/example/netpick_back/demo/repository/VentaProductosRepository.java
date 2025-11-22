package com.example.netpick_back.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netpick_back.demo.model.VentaProductos;

@Repository
public interface VentaProductosRepository extends JpaRepository<VentaProductos, Integer> {

}