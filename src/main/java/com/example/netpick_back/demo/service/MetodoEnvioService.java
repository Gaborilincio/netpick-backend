package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.MetodoEnvio;
import com.example.netpick_back.demo.repository.MetodoEnvioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class MetodoEnvioService {

    @Autowired
    private MetodoEnvioRepository metodoenvioRepository;

    public List<MetodoEnvio> findAll() {
        return metodoenvioRepository.findAll();
    }

    public MetodoEnvio findById(Integer id) {
        MetodoEnvio metodoenvio = metodoenvioRepository.findByIdMetodoEnvio(id).orElse(null);
        return metodoenvio;
    }

    public MetodoEnvio save(MetodoEnvio metodoenvio) {
        return metodoenvioRepository.save(metodoenvio);
    }

    public MetodoEnvio partialUpdate(MetodoEnvio metodoenvio) {
        MetodoEnvio existingMetodoEnvio = metodoenvioRepository.findByIdMetodoEnvio(metodoenvio.getIdMetodoEnvio()).orElse(null);
        if (existingMetodoEnvio == null) {
            return null;
        }
        if (metodoenvio.getNombre() != null) {
            existingMetodoEnvio.setNombre(metodoenvio.getNombre());
        }
        return metodoenvioRepository.save(existingMetodoEnvio);
    }

    public void deleteById(Integer id) {
        metodoenvioRepository.deleteById(id);
    }
}