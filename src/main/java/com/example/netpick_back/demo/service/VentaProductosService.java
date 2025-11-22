package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.VentaProductos;
import com.example.netpick_back.demo.repository.VentaProductosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VentaProductosService {

    private final VentaProductosRepository ventaProductosRepository;

    public VentaProductosService(VentaProductosRepository ventaProductosRepository) {
        this.ventaProductosRepository = ventaProductosRepository;
    }

    public List<VentaProductos> findAll() {
        return ventaProductosRepository.findAll();
    }

    public VentaProductos findById(Integer id) {
        return ventaProductosRepository.findById(id).orElse(null);
    }

    public VentaProductos save(VentaProductos ventaProductos) {
        return ventaProductosRepository.save(ventaProductos);
    }

    public VentaProductos partialUpdate(VentaProductos ventaProductos) {
        VentaProductos existing = ventaProductosRepository.findById(ventaProductos.getIdVentaProdutos()).orElse(null);
        if (existing != null) {
            if (ventaProductos.getVenta() != null) {
                existing.setVenta(ventaProductos.getVenta());
            }
            if (ventaProductos.getProducto() != null) {
                existing.setProducto(ventaProductos.getProducto());
            }
            return ventaProductosRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        ventaProductosRepository.deleteById(id);
    }
}