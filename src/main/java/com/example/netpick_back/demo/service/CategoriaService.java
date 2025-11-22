package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Categoria;
import com.example.netpick_back.demo.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria partialUpdate(Categoria categoria) {
        Categoria existingCategoria = categoriaRepository.findById(categoria.getIdCategoria()).orElse(null);
        
        if (existingCategoria != null) {
            if (categoria.getNombre() != null) {
                existingCategoria.setNombre(categoria.getNombre());
            }
            if (categoria.getDescripcion() != null) {
                existingCategoria.setDescripcion(categoria.getDescripcion());
            }
            return categoriaRepository.save(existingCategoria);
        }
        return null;
    }

    public void deleteById(Integer id) {
        categoriaRepository.deleteById(id);
    }
}