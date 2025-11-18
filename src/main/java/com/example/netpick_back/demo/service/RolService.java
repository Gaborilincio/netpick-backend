package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Rol;
import com.example.netpick_back.demo.repository.RolRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Rol findById(Integer id) {
        Rol rol = rolRepository.findByIdRol(id).orElse(null);
        return rol;
    }

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol partialUpdate(Rol rol) {
        Rol existingRol = rolRepository.findByIdRol(rol.getIdRol()).orElse(null);
        if (existingRol == null) {
            return null;
        }
        if (rol.getNombre() != null) {
            existingRol.setNombre(rol.getNombre());
        }
        return rolRepository.save(existingRol);
    }

    public void deleteById(Integer id) {
        rolRepository.deleteById(id);
    }
}