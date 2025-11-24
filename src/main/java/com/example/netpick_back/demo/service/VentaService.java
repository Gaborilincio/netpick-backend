package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Venta;
import com.example.netpick_back.demo.repository.VentaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    public List<Venta> findByUsuarioId(Integer userId) {
        return ventaRepository.findByUsuarioIdUsuario(userId);
    }   

    public Venta findById(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }

    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    public Venta partialUpdate(Venta venta) {
        Venta existing = ventaRepository.findById(venta.getIdVenta()).orElse(null);
        if (existing != null) {
            if (venta.getMetodoPago() != null) {
                existing.setMetodoPago(venta.getMetodoPago());
            }
            if (venta.getMetodoEnvio() != null) {
                existing.setMetodoEnvio(venta.getMetodoEnvio());
            }
            if (venta.getEstado() != null) {
                existing.setEstado(venta.getEstado());
            }
            if (venta.getUsuario() != null) {
                existing.setUsuario(venta.getUsuario());
            }
            return ventaRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        ventaRepository.deleteById(id);
    }
}