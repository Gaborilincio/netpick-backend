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

import com.example.netpick_back.demo.model.MetodoPago;
import com.example.netpick_back.demo.service.MetodoPagoService;

@RestController
@RequestMapping("/api/metodopago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodopagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPago>> getAllMetodoPagos() {
        List<MetodoPago> list = metodopagoService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> getMetodoPagoById(@PathVariable Integer id) {
        MetodoPago metodopago = metodopagoService.findById(id);
        if (metodopago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(metodopago);
    }

    @PostMapping
    public ResponseEntity<MetodoPago> createMetodoPago(@RequestBody MetodoPago metodopago) {
        return ResponseEntity.status(201).body(metodopagoService.save(metodopago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> updateMetodoPago(@PathVariable Integer id, @RequestBody MetodoPago metodopago) {
        metodopago.setIdMetodoPago(id);
        MetodoPago updatedMetodoPago = metodopagoService.save(metodopago);
        if (updatedMetodoPago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMetodoPago);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MetodoPago> partialUpdateMetodoPago(@PathVariable Integer id, @RequestBody MetodoPago metodopago) {
        MetodoPago existingMetodoPago = metodopagoService.findById(id);
        if (existingMetodoPago == null) {
            return ResponseEntity.notFound().build();
        }
        metodopago.setIdMetodoPago(id);
        return ResponseEntity.ok(metodopagoService.partialUpdate(metodopago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetodoPago(@PathVariable Integer id) {
        metodopagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}