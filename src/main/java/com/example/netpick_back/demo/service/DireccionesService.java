package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Direcciones;
import com.example.netpick_back.demo.repository.DireccionesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DireccionesService {

    private final DireccionesRepository direccionesRepository;

    public DireccionesService(DireccionesRepository direccionesRepository) {
        this.direccionesRepository = direccionesRepository;
    }

    public List<Direcciones> findAll() {
        return direccionesRepository.findAll();
    }

    public Direcciones findById(Integer id) {
        return direccionesRepository.findById(id).orElse(null);
    }

    public Direcciones save(Direcciones direcciones) {
        return direccionesRepository.save(direcciones);
    }

    public Direcciones partialUpdate(Direcciones direcciones) {
        Direcciones existing = direccionesRepository.findById(direcciones.getIdDireccion()).orElse(null);
        if (existing != null) {
            if (direcciones.getDireccion() != null) {
                existing.setDireccion(direcciones.getDireccion());
            }
            if (direcciones.getCodigoPostal() != null) {
                existing.setCodigoPostal(direcciones.getCodigoPostal());
            }
            if (direcciones.getPais() != null) {
                existing.setPais(direcciones.getPais());
            }
            if (direcciones.getUsuario() != null) {
                existing.setUsuario(direcciones.getUsuario());
            }
            if (direcciones.getComuna() != null) {
                existing.setComuna(direcciones.getComuna());
            }
            return direccionesRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        direccionesRepository.deleteById(id);
    }
}