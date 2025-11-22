package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Rol;
import com.example.netpick_back.demo.repository.RolRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Rol findById(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol partialUpdate(Rol rol) {
        Rol existing = rolRepository.findById(rol.getIdRol()).orElse(null);
        if (existing != null) {
            if (rol.getNombre() != null) {
                existing.setNombre(rol.getNombre());
            }
            return rolRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        rolRepository.deleteById(id);
    }
}