package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Estado;
import com.example.netpick_back.demo.repository.EstadoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    public Estado findById(Integer id) {
        return estadoRepository.findById(id).orElse(null);
    }

    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado partialUpdate(Estado estado) {
        Estado existing = estadoRepository.findById(estado.getIdEstado()).orElse(null);
        if (existing != null) {
            if (estado.getNombre() != null) {
                existing.setNombre(estado.getNombre());
            }
            return estadoRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        estadoRepository.deleteById(id);
    }
}