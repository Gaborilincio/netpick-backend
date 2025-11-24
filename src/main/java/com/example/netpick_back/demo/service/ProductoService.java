package com.example.netpick_back.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Producto;
import com.example.netpick_back.demo.repository.ProductoRepository;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findFilteredProducts(Integer categoriaId, Integer minPrice, Integer maxPrice) {
        
        Specification<Producto> spec = (root, query, criteriaBuilder) -> {
            
            List<Predicate> predicates = new ArrayList<>();

            if (categoriaId != null && categoriaId != 0) { 
                predicates.add(criteriaBuilder.equal(
                    root.get("categoria").get("idCategoria"), categoriaId)
                );
            }

            if (minPrice != null && minPrice >= 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("precio"), minPrice)
                );
            }

            if (maxPrice != null && maxPrice > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("precio"), maxPrice)
                );
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return productoRepository.findAll(spec);
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto partialUpdate(Producto producto) {
        Producto existing = productoRepository.findById(producto.getIdProducto()).orElse(null);
        if (existing != null) {
            if (producto.getNombre() != null) {
                existing.setNombre(producto.getNombre());
            }
            if (producto.getDescripcion() != null) {
                existing.setDescripcion(producto.getDescripcion());
            }
            if (producto.getPrecio() != null) {
                existing.setPrecio(producto.getPrecio());
            }
            if (producto.getStock() != null) {
                existing.setStock(producto.getStock());
            }
            if (producto.getLinkImagen() != null) {
                existing.setLinkImagen(producto.getLinkImagen());
            }
            if (producto.getCategoria() != null) {
                existing.setCategoria(producto.getCategoria());
            }
            return productoRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        productoRepository.deleteById(id);
    }
}