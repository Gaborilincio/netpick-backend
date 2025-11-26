package com.example.netpick_back.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.netpick_back.demo.model.Producto;
import com.example.netpick_back.demo.service.ProductoService;

@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> list = productoService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        return ResponseEntity.status(201).body(productoService.save(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
            @PathVariable Integer id,
            @RequestBody Producto producto) {

        Producto existing = productoService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

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

        Producto updated = productoService.save(existing);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Producto> partialUpdateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto existingProducto = productoService.findById(id);
        if (existingProducto == null) {
            return ResponseEntity.notFound().build();
        }
        producto.setIdProducto(id);
        return ResponseEntity.ok(productoService.partialUpdate(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
