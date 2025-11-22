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

import com.example.netpick_back.demo.model.MetodoEnvio;
import com.example.netpick_back.demo.service.MetodoEnvioService;

@RestController
@RequestMapping("/api/v1/metodoenvio")
public class MetodoEnvioController {

    @Autowired
    private MetodoEnvioService metodoenvioService;

    @GetMapping
    public ResponseEntity<List<MetodoEnvio>> getAllMetodoEnvios() {
        List<MetodoEnvio> list = metodoenvioService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoEnvio> getMetodoEnvioById(@PathVariable Integer id) {
        MetodoEnvio metodoenvio = metodoenvioService.findById(id);
        if (metodoenvio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(metodoenvio);
    }

    @PostMapping
    public ResponseEntity<MetodoEnvio> createMetodoEnvio(@RequestBody MetodoEnvio metodoenvio) {
        return ResponseEntity.status(201).body(metodoenvioService.save(metodoenvio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoEnvio> updateMetodoEnvio(@PathVariable Integer id, @RequestBody MetodoEnvio metodoenvio) {
        metodoenvio.setIdMetodoEnvio(id);
        MetodoEnvio updatedMetodoEnvio = metodoenvioService.save(metodoenvio);
        if (updatedMetodoEnvio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMetodoEnvio);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MetodoEnvio> partialUpdateMetodoEnvio(@PathVariable Integer id, @RequestBody MetodoEnvio metodoenvio) {
        MetodoEnvio existingMetodoEnvio = metodoenvioService.findById(id);
        if (existingMetodoEnvio == null) {
            return ResponseEntity.notFound().build();
        }
        metodoenvio.setIdMetodoEnvio(id);
        return ResponseEntity.ok(metodoenvioService.partialUpdate(metodoenvio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetodoEnvio(@PathVariable Integer id) {
        metodoenvioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}