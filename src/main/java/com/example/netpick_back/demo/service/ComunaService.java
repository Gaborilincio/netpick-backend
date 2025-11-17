package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Comuna;
import com.example.netpick_back.demo.repository.ComunaRepository;
import com.example.netpick_back.demo.service.ComunaService;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }

    public Comuna findById(Integer id) {
        Comuna comuna = comunaRepository.findByIdComuna(id).orElse(null);
        return comuna;
    }

    public Comuna save(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    public Comuna partialUpdate(Comuna comuna){
        Comuna existingComuna = comunaRepository.findByIdComuna(comuna.getIdComuna()).orElse(null);
        if (existingComuna != null) {
            if (comuna.getNombre() != null) {
                existingComuna.setNombre(comuna.getNombre());
            }
            return comunaRepository.save(existingComuna);
        }
        return null;
    }

    public void deleteById(Integer id) {
        comunaRepository.deleteById(id);
    }
}