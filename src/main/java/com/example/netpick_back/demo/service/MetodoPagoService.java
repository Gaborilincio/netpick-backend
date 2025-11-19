package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.MetodoPago;
import com.example.netpick_back.demo.repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodopagoRepository;

    public List<MetodoPago> findAll() {
        return metodopagoRepository.findAll();
    }

    public MetodoPago findById(Integer id) {
        MetodoPago metodopago = metodopagoRepository.findByIdMetodoPago(id).orElse(null);
        return metodopago;
    }

    public MetodoPago save(MetodoPago metodopago) {
        return metodopagoRepository.save(metodopago);
    }

    public MetodoPago partialUpdate(MetodoPago metodopago) {
        MetodoPago existingMetodoPago = metodopagoRepository.findByIdMetodoPago(metodopago.getIdMetodoPago()).orElse(null);
        if (existingMetodoPago == null) {
            return null;
        }
        if (metodopago.getNombre() != null) {
            existingMetodoPago.setNombre(metodopago.getNombre());
        }
        return metodopagoRepository.save(existingMetodoPago);
    }

    public void deleteById(Integer id) {
        metodopagoRepository.deleteById(id);
    }
}