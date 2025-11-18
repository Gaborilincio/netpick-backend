package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Venta;
import com.example.netpick_back.demo.repository.VentaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    public Venta findById(Integer id) {
        Venta venta = ventaRepository.findByIdVenta(id).orElse(null);
        return venta;
    }

    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    public Venta partialUpdate(Venta venta) {
        Venta existingVenta = ventaRepository.findByIdVenta(venta.getIdVenta()).orElse(null);
        if (existingVenta == null) {
            return null;
        }
        if (venta.getEstado() != null) {
            existingVenta.setEstado(venta.getEstado());
        }
        if (venta.getMetodoPago() != null) {
            existingVenta.setMetodoPago(venta.getMetodoPago());
        }
        if (venta.getMetodoEnvio() != null) {
            existingVenta.setMetodoEnvio(venta.getMetodoEnvio());
        }
        if (venta.getUsuario() != null) {
            existingVenta.setUsuario(venta.getUsuario());
        }
        return ventaRepository.save(existingVenta);
    }

    public void deleteById(Integer id) {
        ventaRepository.deleteById(id);
    }
}