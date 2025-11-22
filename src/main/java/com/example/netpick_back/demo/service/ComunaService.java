package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Comuna;
import com.example.netpick_back.demo.repository.ComunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {

    private final ComunaRepository comunaRepository;

    public ComunaService(ComunaRepository comunaRepository) {
        this.comunaRepository = comunaRepository;
    }

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }

    public Comuna findById(Integer id) {
        return comunaRepository.findById(id).orElse(null);
    }

    public Comuna save(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    public Comuna partialUpdate(Comuna comuna) {
        Comuna existing = comunaRepository.findById(comuna.getIdComuna()).orElse(null);
        if (existing != null) {
            if (comuna.getNombre() != null) {
                existing.setNombre(comuna.getNombre());
            }
            if (comuna.getRegion() != null) {
                existing.setRegion(comuna.getRegion());
            }
            return comunaRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        comunaRepository.deleteById(id);
    }
}