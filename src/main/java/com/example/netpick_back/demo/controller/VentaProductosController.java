package com.example.netpick_back.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.netpick_back.demo.model.VentaProductos;
import com.example.netpick_back.demo.service.VentaProductosService;

@RestController
@RequestMapping("/api/ventaproductos")
public class VentaProductosController {

    @Autowired
    private VentaProductosService ventaProductosService;

    @GetMapping
    public ResponseEntity<List<VentaProductos>> getAllVentaProductos() {
        List<VentaProductos> list = ventaProductosService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaProductos> getVentaProductosById(@PathVariable Integer id) {
        VentaProductos ventaProductos = ventaProductosService.findById(id);
        if (ventaProductos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ventaProductos);
    }

    @PostMapping
    public ResponseEntity<VentaProductos> createVentaProductos(@RequestBody VentaProductos ventaProductos) {
        return ResponseEntity.status(201).body(ventaProductosService.save(ventaProductos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaProductos> updateVentaProductos(@PathVariable Integer id, @RequestBody VentaProductos ventaProductos) {
        ventaProductos.setIdVentaProdutos(id);
        VentaProductos updatedVentaProductos = ventaProductosService.save(ventaProductos);
        if (updatedVentaProductos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedVentaProductos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VentaProductos> partialUpdateVentaProductos(@PathVariable Integer id, @RequestBody VentaProductos ventaProductos) {
        VentaProductos existingVentaProductos = ventaProductosService.findById(id);
        if (existingVentaProductos == null) {
            return ResponseEntity.notFound().build();
        }
        ventaProductos.setIdVentaProdutos(id);
        return ResponseEntity.ok(ventaProductosService.partialUpdate(ventaProductos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVentaProductos(@PathVariable Integer id) {
        ventaProductosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}