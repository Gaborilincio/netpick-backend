package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Estado;
import com.example.netpick_back.demo.repository.EstadoRepository;
import com.example.netpick_back.demo.service.EstadoService;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    public Estado findById(Integer id) {
        Estado estado = estadoRepository.findByIdEstado(id).orElse(null);
        return estado;
    }

    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado partialUpdate(Estado estado) {
        Estado existingEstado = estadoRepository.findByIdEstado(estado.getIdEstado()).orElse(null);
        if (existingEstado == null) {
            return null;
        }
        if (estado.getNombre() != null) {
            existingEstado.setNombre(estado.getNombre());
        }
        return estadoRepository.save(existingEstado);
    }

    public void deleteById(Integer id) {
        estadoRepository.deleteById(id);
    }
}