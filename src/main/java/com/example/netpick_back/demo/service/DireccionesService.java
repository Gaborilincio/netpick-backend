package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Direcciones;
import com.example.netpick_back.demo.repository.DireccionesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class DireccionesService {

    @Autowired
    private DireccionesRepository direccionesRepository;

    public List<Direcciones> findAll() {
        return direccionesRepository.findAll();
    }

    public Direcciones findById(Integer id) {
        Direcciones direcciones = direccionesRepository.findByIdDireccion(id).orElse(null);
        return direcciones;
    }

    public Direcciones save(Direcciones direcciones) {
        return direccionesRepository.save(direcciones);
    }

    public Direcciones partialUpdate(Direcciones direcciones){
        Direcciones existingDirecciones = direccionesRepository.findByIdDireccion(direcciones.getIdDireccion()).orElse(null);
        if (existingDirecciones != null) {
            if (direcciones.getDireccion()!= null) {
                existingDirecciones.setDireccion(direcciones.getDireccion());
            }
            if (direcciones.getCodigoPostal()!= null){
                existingDirecciones.setCodigoPostal(direcciones.getCodigoPostal());
            }
            if (direcciones.getPais()!= null){
                existingDirecciones.setPais(direcciones.getPais());
            }
            return direccionesRepository.save(existingDirecciones);
        }
        return null;
    }

    public void deleteById(Integer id) {
        direccionesRepository.deleteById(id);
    }
}