package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Producto;
import com.example.netpick_back.demo.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Integer id) {
        Producto producto = productoRepository.findByIdProducto(id).orElse(null);
        return producto;
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto partialUpdate(Producto producto) {
        Producto existingProducto = productoRepository.findByIdProducto(producto.getIdProducto()).orElse(null);
        if (existingProducto == null) {
            return null;
        }
        if (producto.getNombre() != null) {
            existingProducto.setNombre(producto.getNombre());
        }
        if (producto.getPrecio() != null) {
            existingProducto.setPrecio(producto.getPrecio());
        }
        if (producto.getDescripcion() != null) {
            existingProducto.setDescripcion(producto.getDescripcion());
        }
        if (producto.getLinkImagen() != null) {
            existingProducto.setLinkImagen(producto.getLinkImagen());
        }
        if (producto.getStock() != null) {
            existingProducto.setStock(producto.getStock());
        }
        return productoRepository.save(existingProducto);
    }

    public void deleteById(Integer id) {
        productoRepository.deleteById(id);
    }
}