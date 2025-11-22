package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.MetodoPago;
import com.example.netpick_back.demo.repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    public MetodoPagoService(MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public List<MetodoPago> findAll() {
        return metodoPagoRepository.findAll();
    }

    public MetodoPago findById(Integer id) {
        return metodoPagoRepository.findById(id).orElse(null);
    }

    public MetodoPago save(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    public MetodoPago partialUpdate(MetodoPago metodoPago) {
        MetodoPago existing = metodoPagoRepository.findById(metodoPago.getIdMetodoPago()).orElse(null);
        if (existing != null) {
            if (metodoPago.getNombre() != null) {
                existing.setNombre(metodoPago.getNombre());
            }
            return metodoPagoRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        metodoPagoRepository.deleteById(id);
    }
}