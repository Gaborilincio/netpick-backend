package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.MetodoEnvio;
import com.example.netpick_back.demo.repository.MetodoEnvioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MetodoEnvioService {

    private final MetodoEnvioRepository metodoEnvioRepository;

    public MetodoEnvioService(MetodoEnvioRepository metodoEnvioRepository) {
        this.metodoEnvioRepository = metodoEnvioRepository;
    }

    public List<MetodoEnvio> findAll() {
        return metodoEnvioRepository.findAll();
    }

    public MetodoEnvio findById(Integer id) {
        return metodoEnvioRepository.findById(id).orElse(null);
    }

    public MetodoEnvio save(MetodoEnvio metodoEnvio) {
        return metodoEnvioRepository.save(metodoEnvio);
    }

    public MetodoEnvio partialUpdate(MetodoEnvio metodoEnvio) {
        MetodoEnvio existing = metodoEnvioRepository.findById(metodoEnvio.getIdMetodoEnvio()).orElse(null);
        if (existing != null) {
            if (metodoEnvio.getNombre() != null) {
                existing.setNombre(metodoEnvio.getNombre());
            }
            return metodoEnvioRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        metodoEnvioRepository.deleteById(id);
    }
}